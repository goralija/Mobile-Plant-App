package ba.unsa.etf.rma.spirala1

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class TrefleDAO {
    //Sve metode su suspend tipa unutar kojih se kreira corutine koja će izvršiti datu funkcionalnost
    private var defaultBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888).apply {
        eraseColor(android.graphics.Color.WHITE)
    }

    fun getLatinName(naziv: String): String? {
        val regex = Regex("\\(([^)]+)\\)")
        val matchResult = regex.find(naziv)
        return matchResult?.groups?.get(1)?.value
    }

    suspend fun getImage(biljka: Biljka): Bitmap = withContext(Dispatchers.IO) {
        // vraća bitmap slike biljke koja je proslijeđena
        // potrebno je prikazati prvu sliku koja se pronađe korištenjem pretrage trefle web servisa po latinskom nazivu biljke
        try {
            val latinName = getLatinName(biljka.naziv.toString())
            val urlString = "https://trefle.io/api/v1/plants/search?q=$latinName&token=THshD1EMlApPzeiN2RDOxz_c6E8Cu1iPRFjsK-mTJF0"
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(response)
                val imageUrl = json.getJSONArray("data").getJSONObject(0).getString("image_url")

                return@withContext BitmapFactory.decodeStream(URL(imageUrl).openStream())
            } else {
                return@withContext defaultBitmap
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext defaultBitmap
        }
    }


    suspend fun fixData(biljka: Biljka): Biljka = withContext(Dispatchers.IO) {

        var plantId: String? = null

        try {
            var latinName = biljka.naziv?.let { getLatinName(it) }
            val urlString = "https://trefle.io/api/v1/plants/search?q=$latinName&token=THshD1EMlApPzeiN2RDOxz_c6E8Cu1iPRFjsK-mTJF0"
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(response)
                val dataArray = json.getJSONArray("data")
                if (dataArray.length() > 0) {
                    plantId = dataArray.getJSONObject(0).getString("id")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (plantId == null) return@withContext biljka

        try {
            val urlString = "https://trefle.io/api/v1/plants/$plantId?token=THshD1EMlApPzeiN2RDOxz_c6E8Cu1iPRFjsK-mTJF0"
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(response)
                val plantData = json.getJSONObject("data")

                // ako ne odgovara nasa 'porodica' atributu family.name -> mijenjamo
                val familyName = plantData.getJSONObject("family").getString("name")
                if (biljka.porodica != familyName) {
                    biljka.porodica = familyName
                }

                // za !edible biljke isprazni se lista jela i dodaje se medic.upoz.
                val isEdible = plantData.getJSONObject("main_species").getBoolean("edible")
                if (!isEdible) {
                    biljka.jela = mutableListOf()
                    if (biljka.medicinskoUpozorenje?.contains("NIJE JESTIVO") != true) {
                        biljka.medicinskoUpozorenje = (biljka.medicinskoUpozorenje ?: "") + " NIJE JESTIVO"
                    }
                }

                // main_species.specifications.toxicity != none -> if(! ...TOKSIČNO...) ...TOKSIČNO
                val toxicity = plantData.getJSONObject("main_species").getJSONObject("specifications").getString("toxicity")
                if (toxicity != "none" && biljka.medicinskoUpozorenje?.contains("TOKSIČNO") != true) {
                    biljka.medicinskoUpozorenje += " TOKSIČNO"
                }

                val soilTextureMapping = mapOf(
                    1 to Zemljište.GLINENO,
                    2 to Zemljište.GLINENO,
                    3 to Zemljište.PJESKOVITO,
                    4 to Zemljište.PJESKOVITO,
                    5 to Zemljište.ILOVACA,
                    6 to Zemljište.ILOVACA,
                    7 to Zemljište.CRNICA,
                    8 to Zemljište.CRNICA,
                    9 to Zemljište.SLJUNKOVITO,
                    10 to Zemljište.KRECNJACKO
                )

                // main_species.specifications.growth.soil_texture
                val soilTextureInt = plantData.getJSONObject("main_species").getJSONObject("growth").getInt("soil_texture")

                Log.v("soil_texture -----------------> ", soilTextureInt.toString())

                val soilTexture = soilTextureMapping[soilTextureInt]
                val validSoils = listOf(Zemljište.SLJUNKOVITO, Zemljište.KRECNJACKO, Zemljište.GLINENO, Zemljište.PJESKOVITO, Zemljište.ILOVACA, Zemljište.CRNICA)
                biljka.zemljisniTipovi = biljka.zemljisniTipovi.filter { validSoils.contains(it) }.toMutableList()
                if (soilTexture != null && !biljka.zemljisniTipovi.contains(soilTexture)) {
                    (biljka.zemljisniTipovi as MutableList<Zemljište>).add(soilTexture)
                }

                val validClimates = mapOf(
                    KlimatskiTip.SREDOZEMNA to (6..9 to 1..5),
                    KlimatskiTip.TROPSKA to (8..10 to 7..10),
                    KlimatskiTip.SUBTROPSKA to (6..9 to 5..8),
                    KlimatskiTip.UMJERENA to (4..7 to 3..7),
                    KlimatskiTip.SUHA to (7..9 to 1..2),
                    KlimatskiTip.PLANINSKA to (0..5 to 3..7)
                )

                val light = plantData.getJSONObject("main_species").getJSONObject("growth").getInt("light")
                val humidity = plantData.getJSONObject("main_species").getJSONObject("growth").getInt("atmospheric_humidity")

                Log.v("light -----------------> ", light.toString())
                Log.v("humidity -----------------> ", humidity.toString())

                biljka.klimatskiTipovi = biljka.klimatskiTipovi.filter {
                    validClimates[it]?.first?.contains(light) == true && validClimates[it]?.second?.contains(humidity) == true
                }.toMutableList()

                biljka.klimatskiTipovi.add(KlimatskiTip.SREDOZEMNA)

                return@withContext biljka
            } else {
                return@withContext biljka
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext biljka
        }
    }

    suspend fun getPlantsWithFlowerColor(flower_color: String, substr: String): List<Biljka> = withContext(Dispatchers.IO) {
        try {
            val urlString = "https://trefle.io/api/v1/plants?filter[flower_color]=$flower_color&token=THshD1EMlApPzeiN2RDOxz_c6E8Cu1iPRFjsK-mTJF0"
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(response)
                val plants = json.getJSONArray("data")
                val filteredPlants = mutableListOf<Biljka>()

                // List of deferred results
                val deferredResults = mutableListOf<Deferred<Biljka>>()

                for (i in 0 until plants.length()) {
                    val plant = plants.getJSONObject(i)
                    val plantName = plant.getString("common_name") + " (" + plant.getString("scientific_name") + ")"
                    if (plantName.contains(substr, true)) {
                        val naziv = plant.optString("common_name", null) + " (" + plant.optString("scientific_name", null) + ")"

                        val biljka = Biljka(
                            naziv = naziv,
                            porodica = null,
                            medicinskoUpozorenje = null,
                            medicinskeKoristi = mutableListOf(),
                            profilOkusa = null,
                            jela = mutableListOf(),
                            klimatskiTipovi = mutableListOf(),
                            zemljisniTipovi = mutableListOf()
                        )

                        // Add async task to list of deferred results
                        val deferred = async { TrefleDAO().fixData(biljka) }
                        deferredResults.add(deferred)
                    }
                }

                // Await all deferred results
                for (deferred in deferredResults) {
                    val fixedBiljka = deferred.await()
                    filteredPlants.add(fixedBiljka)
                }

                return@withContext filteredPlants
            } else {
                return@withContext emptyList<Biljka>()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext emptyList<Biljka>()
        }
    }
}