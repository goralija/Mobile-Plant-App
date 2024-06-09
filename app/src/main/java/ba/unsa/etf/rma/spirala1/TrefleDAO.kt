package ba.unsa.etf.rma.spirala1

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.gson.JsonNull
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.internal.GsonBuildConfig
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.jar.Manifest

class TrefleDAO {

    private lateinit var context: Context
    private lateinit var defaultBitmap: Bitmap
    private val apiKey: String = BuildConfig.API_KEY


    fun setContext(context: Context) {
        this.context = context
    }
    fun dajsliku() : Bitmap {
        return BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
    }

    fun dajLatinski(naziv: String): String {
        val regex = "\\((.*?)\\)".toRegex()
        val matchResult = regex.find(naziv)
        return if (matchResult != null) {
            matchResult.groupValues[1].split("\\s+".toRegex()).joinToString("+")
        } else {
            ""
        }
    }

    sealed class Result<out R> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }

    suspend fun getImage(biljka: Biljka): Result<Bitmap>{
        return withContext(Dispatchers.IO){
            try{
                val latinski = dajLatinski(biljka.naziv.toString())
                val url1 = "https://trefle.io/api/v1/plants/search?q=$latinski&token=$apiKey"
                val url = URL(url1)
                (url.openConnection() as? HttpURLConnection)?.run{
                    val result = this.inputStream.bufferedReader().use{it.readText()}
                    val jo = JSONObject(result)
                    var slikaurl = ""
                    val objekti = jo.getJSONArray("data")
                    if(objekti.length()>0){
                        slikaurl = objekti.getJSONObject(0).getString("image_url")
                        return@withContext Result.Success(BitmapFactory.decodeStream(URL(slikaurl).openStream()))
                    }
                }
                defaultBitmap = dajsliku()
                return@withContext Result.Success(defaultBitmap)

            }
            catch (e: MalformedURLException) {
                return@withContext Result.Error(Exception("Cannot open HttpURLConnection"))
            } catch (e: IOException) {
                return@withContext Result.Error(Exception("Cannot read stream"))
            } catch (e: JSONException) {
                return@withContext Result.Error(Exception("Cannot parse JSON"))
            }
        }
    }



    suspend fun fixData(biljka: Biljka): Biljka {
        return withContext(Dispatchers.IO) {
            try {
                val latinski = dajLatinski(biljka.naziv.toString())
                val urll = "https://trefle.io/api/v1/plants/search?q=$latinski&token=$apiKey"
                val url = URL(urll)
                var id: Int = 0

                (url.openConnection() as? HttpURLConnection)?.run {
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    val jsonArray =
                        JsonParser.parseString(result).asJsonObject.getAsJsonArray("data")
                    if (jsonArray.size() > 0) {
                        id = jsonArray[0].asJsonObject.get("id").asInt
                    } else {
                        throw Exception("No data found for the given plant name.")
                    }
                } ?: throw Exception("Failed to open connection")

                val urlll = URL("https://trefle.io/api/v1/plants/$id?token=$apiKey")
                var nova: Biljka = biljka

                (urlll.openConnection() as? HttpURLConnection)?.run {
                    val response = this.inputStream.bufferedReader().use { it.readText() }
                    val jsonObject =
                        JsonParser.parseString(response).asJsonObject.getAsJsonObject("data")

                    var name: String = ""

                    name += jsonObject.get("common_name")?.takeIf { it != JsonNull.INSTANCE }?.asString ?: ""
                    name += "("
                    name += jsonObject.get("scientific_name").asString
                    name += ")"

                    val familyName = jsonObject.getAsJsonObject("main_species").get("family")
                        ?.takeIf { it != JsonNull.INSTANCE }?.asString ?: biljka.porodica

                    val isEdible = jsonObject.getAsJsonObject("main_species").get("edible")
                        ?.takeIf { it != JsonNull.INSTANCE }?.asBoolean ?: false

                    val toxicity =
                        jsonObject.getAsJsonObject("main_species").getAsJsonObject("specifications")
                            .get("toxicity")?.takeIf { it != JsonNull.INSTANCE }?.asString ?: "none"

                    var novaJela = biljka.jela
                    if (!isEdible || toxicity != "none") novaJela = listOf()

                    val novoMedUpozorenje = StringBuilder(biljka.medicinskoUpozorenje ?: "").apply {
                        if (!isEdible) append(" NIJE JESTIVO")
                        if (toxicity != "none" && !contains("TOKSIČNO")) append(" TOKSIČNO")
                    }.toString()

                    val soilTexture1 =
                        jsonObject.getAsJsonObject("main_species").getAsJsonObject("growth")
                            .get("soil_texture")?.takeIf { it != JsonNull.INSTANCE }?.asString ?: ""
                    var noviZemljisniTipovi = mutableListOf<Zemljište>()

                    if (soilTexture1 == "9") noviZemljisniTipovi.add(Zemljište.SLJUNKOVITO)
                    if (soilTexture1 == "10") noviZemljisniTipovi.add(Zemljište.KRECNJACKO)
                    if (soilTexture1 == "1" || soilTexture1 == "2") noviZemljisniTipovi.add(Zemljište.GLINENO)
                    if (soilTexture1 == "3" || soilTexture1 == "4") noviZemljisniTipovi.add(Zemljište.PJESKOVITO)
                    if (soilTexture1 == "5" || soilTexture1 == "6") noviZemljisniTipovi.add(Zemljište.ILOVACA)
                    if (soilTexture1 == "7" || soilTexture1 == "8") noviZemljisniTipovi.add(Zemljište.CRNICA)

                    if (noviZemljisniTipovi.isEmpty()) noviZemljisniTipovi = biljka.zemljisniTipovi.toMutableList()


                    val light = jsonObject.getAsJsonObject("main_species").getAsJsonObject("growth")
                        .get("light")?.takeIf { it != JsonNull.INSTANCE }?.asInt ?: 0
                    val hum = jsonObject.getAsJsonObject("main_species").getAsJsonObject("growth")
                        .get("atmospheric_humidity")?.takeIf { it != JsonNull.INSTANCE }?.asInt ?: 0
                    var noviKlimatskiTipovi = mutableListOf<KlimatskiTip>()

                    if (light in 6..9 && hum in 1..5) noviKlimatskiTipovi.add(KlimatskiTip.SREDOZEMNA)
                    if (light in 8..10 && hum in 7..10) noviKlimatskiTipovi.add(KlimatskiTip.TROPSKA)
                    if (light in 6..9 && hum in 5..8) noviKlimatskiTipovi.add(KlimatskiTip.SUBTROPSKA)
                    if (light in 4..7 && hum in 3..7) noviKlimatskiTipovi.add(KlimatskiTip.UMJERENA)
                    if (light in 7..9 && hum in 1..2) noviKlimatskiTipovi.add(KlimatskiTip.SUHA)
                    if (light in 0..5 && hum in 3..7) noviKlimatskiTipovi.add(KlimatskiTip.PLANINSKA)

                    if (noviKlimatskiTipovi.isEmpty()) noviKlimatskiTipovi = biljka.klimatskiTipovi.toMutableList()

                    nova = nova.copy(
                        naziv = name,
                        porodica = familyName,
                        jela = novaJela,
                        medicinskoUpozorenje = novoMedUpozorenje,
                        zemljisniTipovi = noviZemljisniTipovi,
                        klimatskiTipovi = noviKlimatskiTipovi
                    )
                }

                return@withContext nova
            } catch (e: MalformedURLException) {
                throw Exception("Cannot open HttpURLConnection", e)
            } catch (e: IOException) {
                throw Exception("Cannot read stream", e)
            } catch (e: JsonParseException) {
                throw Exception("Cannot parse JSON", e)
            }
        }
    }

    suspend fun getPlantsWithFlowerColor(flowerColor: String, substr: String): List<Biljka> {
        return withContext(Dispatchers.IO) {
            try {
                var ajdijevi = emptyList<Int>().toMutableList()
                val urll = "https://trefle.io/api/v1/plants/search?q=$substr&token=$apiKey&filter[flower_color]=$flowerColor"
                val url = URL(urll)
                val listaTrazenihBiljki = mutableListOf<Biljka>()

                (url.openConnection() as? HttpURLConnection)?.run {
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    val json = JsonParser.parseString(result).asJsonObject

                    val filtriraneBiljke = json.getAsJsonArray("data")
                    for (i in 0 until filtriraneBiljke.size())
                        ajdijevi.add(filtriraneBiljke[i].asJsonObject.get("id").asInt)
                }

                for (k in 0 until ajdijevi.size) {
                    val urlll = "https://trefle.io/api/v1/plants/${ajdijevi[k]}?token=$apiKey"
                    val url2 = URL(urlll)

                    (url2.openConnection() as? HttpURLConnection)?.run {
                        val result = this.inputStream.bufferedReader().use { it.readText() }
                        val filtriraneBiljke = JsonParser.parseString(result).asJsonObject.getAsJsonObject("data")

                        val ime = filtriraneBiljke.get("common_name")?.takeIf { it != JsonNull.INSTANCE }?.asString ?: ""
                        val ime1 = filtriraneBiljke.get("scientific_name")?.takeIf { it != JsonNull.INSTANCE }?.asString ?: ""

                        val naziv = "$ime ($ime1)"

                        var hasFlowerColor = false
                        var porodica:String = ""
                        if (filtriraneBiljke.has("main_species")) {
                            porodica = filtriraneBiljke.getAsJsonObject("main_species").get("family")
                                    ?.takeIf { it != JsonNull.INSTANCE }?.asString ?: ""

                            val flower = filtriraneBiljke.getAsJsonObject("main_species")
                                .getAsJsonObject("flower")

                            var colors: List<String> = emptyList()
                            if (flower != null && flower.has("color") && !flower.get("color").isJsonNull) {
                                val colorElement = flower.get("color")
                                if (colorElement.isJsonArray) {
                                    val colorArray = colorElement.asJsonArray
                                    colors = colorArray.mapNotNull { it.asString }
                                }
                            }

                            if (colors.contains(flowerColor)) hasFlowerColor = true
                        }
                        if (hasFlowerColor) {
                            val n = Biljka(
                                naziv = naziv,
                                porodica = porodica,
                                medicinskoUpozorenje = null,
                                medicinskeKoristi = listOf(),
                                profilOkusa = null,
                                jela = listOf(),
                                klimatskiTipovi = listOf(),
                                zemljisniTipovi = listOf(),
                                onlineChecked = true,
                            )
                            listaTrazenihBiljki.add(n)
                        }
                    }
                }
                return@withContext listaTrazenihBiljki
            } catch (e: MalformedURLException) {
                throw Exception("Cannot open HttpURLConnection", e)
            } catch (e: IOException) {
                throw Exception("Cannot read stream", e)
            } catch (e: JsonParseException) {
                throw Exception("Cannot parse JSON", e)
            }
        }
    }
}