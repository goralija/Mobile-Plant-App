package ba.unsa.etf.rma.spirala1

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class TrefleDAO {
    //Sve metode su suspend tipa unutar kojih se kreira corutine koja će izvršiti datu funkcionalnost
    private var defaultBitmap: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888).apply {
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

    /*
    suspend fun fixData(biljka: Biljka): Biljka = withContext(Dispatchers.IO) {
        // korištenjem web servisa na osnovu latinskog naziva biljke pronalazi potrebne podatke i zatim popunjava nepotpuna/netačna polja u okviru proslijeđene biljke
        // kao rezultat se vraća biljka sa ispravnim podacima
        // Ako web servis vrati vrijednost nekog atributa null onda taj dio provjere ne radimo
        // podatke koje je potrebno provjeriti putem web servisa su:
        // -	Porodica biljke - treba odgovarati atributu family.name, ako ne odgovara u biljki koju ćete vratiti upišite vrijednost sa web servisa
        // -    Jela - biljke koje imaju atribut edible false ne smiju imati jela i unutar medicinskog upozorenja treba biti podstring “NIJE JESTIVO” i lista jela treba biti prazna
        // -    Medicinsko upozorenje - biljke koje imaju atribut main_species.specifications.toxicity različit od “none” trebaju unutar medicinskog upozorenja imati podstring “TOKSIČNO”, ako medicinsko upozorenje ne sadrži podstring “TOKSIČNO” na kraj stringa dodajte “ TOKSIČNO” u rezultujućoj biljci.
        // -    Zemljište - u zavisnosti od parametra main_species.specifications.growth.soil_texture. Ako neki odabrani tip zemljišta ne ispunjava sljedeće uslove obrišite ga u rezultujućoj biljici ili dodajte ako nije dodan tip koji ispunjava uslov.
        //          tip zemljišta (soil_texture) -> SLJUNOVITO (9), KRECNJACKO (10), GLINENO (1-2), PJESKOVITO (3-4), ILOVACA (5-6), CRNICA (7-8)
        // -    Klimatski tip - u zavisnosti od parametara light i atmospheric_humidity. Slično kao za tip zemljišta i ovdje dodajte odgovarajući tip ili obrišite tip koji ne zadovoljava uslove i to upišite u rezultujuću biljku.
        //          tip klime (light, atmospheric_humidity) -> SREDOZEMNA (6-9, 1-5), TROPSKA (8-10, 7-10), SUBTROPSKA (6-9, 5-8), UMJERENA (4-7, 3-7), SUHA (7-9, 1-2), PLANINSKA (0-5, 3-7)
        try {
            val latinName = biljka.naziv
            val urlString = "https://trefle.io/api/v1/plants/search?q=$latinName&token=YOUR_API_TOKEN"
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(response)
                val plantData = json.getJSONArray("data").getJSONObject(0)

                // Update family name
                val familyName = plantData.getJSONObject("family").getString("name")
                if (biljka.porodica != familyName) {
                    biljka.porodica = familyName
                }

                // Update edible status and meals
                val isEdible = plantData.getBoolean("edible")
                if (!isEdible) {
                    biljka.jela = listOf()
                    if (!biljka.medicinskoUpozorenje?.contains("NIJE JESTIVO")) {
                        biljka.medicinskoUpozorenje += " NIJE JESTIVO"
                    }
                }

                // Update medicinal warning
                val toxicity = plantData.getJSONObject("main_species").getJSONObject("specifications").getString("toxicity")
                if (toxicity != "none" && !biljka.medicinskoUpozorenje?.contains("TOKSIČNO")) {
                    biljka.medicinskoUpozorenje += " TOKSIČNO"
                }

                // Update soil texture
                val soilTexture = plantData.getJSONObject("main_species").getJSONObject("specifications").getJSONArray("growth").getString("soil_texture")
                val validSoils = listOf("SLJUNOVITO", "KRECNJACKO", "GLINENO", "PJESKOVITO", "ILOVACA", "CRNICA")
                biljka.zemljisniTipovi = biljka.zemljisniTipovi.filter { validSoils.contains(it) }.toMutableList()
                if (!biljka.zemljisniTipovi.contains(soilTexture)) {
                    biljka.zemljisniTipovi.add(soilTexture)
                }

                // Update climate types
                val light = plantData.getJSONObject("main_species").getJSONObject("specifications").getInt("light")
                val humidity = plantData.getJSONObject("main_species").getJSONObject("specifications").getInt("atmospheric_humidity")
                val validClimates = mapOf(
                    "SREDOZEMNA" to (6..9 to 1..5),
                    "TROPSKA" to (8..10 to 7..10),
                    "SUBTROPSKA" to (6..9 to 5..8),
                    "UMJERENA" to (4..7 to 3..7),
                    "SUHA" to (7..9 to 1..2),
                    "PLANINSKA" to (0..5 to 3..7)
                )

                biljka.klimatskiTipovi = biljka.klimatskiTipovi.filter {
                    validClimates[it]?.first?.contains(light) == true && validClimates[it]?.second?.contains(humidity) == true
                }.toMutableList()

                return@withContext biljka
            } else {
                return@withContext biljka
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext biljka
        }
    }

     */

    suspend fun getPlantsWithFlowerColor(flower_color: String, substr: String): List<Biljka> = withContext(Dispatchers.IO) {
        // vraća listu biljaka koje imaju boju cvijeta flower_color i sadrže podstring substr
        // Sve atribute biljaka koje ne možete dobiti sa web servisa ili koji su null postavite na null ili na prazan string
        try {
            val urlString =
                "https://trefle.io/api/v1/plants?filter[flower_color]=$flower_color&token=THshD1EMlApPzeiN2RDOxz_c6E8Cu1iPRFjsK-mTJF0"
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(response)
                val plants = json.getJSONArray("data")
                val filteredPlants = mutableListOf<Biljka>()

                for (i in 0 until plants.length()) {
                    val plant = plants.getJSONObject(i)
                    val plantName = plant.getString("common_name")
                    if (plantName.contains(substr, true)) {
                        val naziv = plant.optString("common_name", null)
                        val porodica = plant.optJSONObject("family")?.optString("name", null)
                        val medicinskoUpozorenje = plant.optString("medicinal", null)
                        val medicinskeKoristi =
                            listOf<MedicinskaKorist>()  // Assuming this needs to be filled with actual data if available
                        val profilOkusa = ProfilOkusaBiljke.MENTA  // Assuming a default value
                        val jela =
                            listOf<String>()  // Assuming this needs to be filled with actual data if available
                        val klimatskiTipovi =
                            listOf<KlimatskiTip>()  // Assuming this needs to be filled with actual data if available
                        val zemljisniTipovi =
                            listOf<Zemljište>()  // Assuming this needs to be filled with actual data if available

                        val biljka = Biljka(
                            naziv = naziv,
                            porodica = porodica,
                            medicinskoUpozorenje = medicinskoUpozorenje,
                            medicinskeKoristi = medicinskeKoristi,
                            profilOkusa = profilOkusa,
                            jela = jela,
                            klimatskiTipovi = klimatskiTipovi,
                            zemljisniTipovi = zemljisniTipovi
                        )

                        filteredPlants.add(biljka)
                    }
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