package ba.unsa.etf.rma.spirala1

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineStart
import java.io.ByteArrayOutputStream
import kotlin.io.encoding.Base64

class Converters {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromMedicinskaKoristList(value: List<MedicinskaKorist>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toMedicinskaKoristList(value: String): List<MedicinskaKorist> {
        val listType = object : TypeToken<List<MedicinskaKorist>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromKlimatskiTipList(value: List<KlimatskiTip>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toKlimatskiTipList(value: String): List<KlimatskiTip> {
        val listType = object : TypeToken<List<KlimatskiTip>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromZemljisteList(value: List<Zemljište>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toZemljisteList(value: String): List<Zemljište> {
        val listType = object : TypeToken<List<Zemljište>>() {}.type
        return Gson().fromJson(value, listType)
    }
}