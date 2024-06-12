package ba.unsa.etf.rma.spirala1

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.TypeConverters
import androidx.room.Update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Dao
@TypeConverters (Converters::class)
interface BiljkaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(biljkaBitmap: BiljkaBitmap): Long

    @Query("SELECT * FROM biljka WHERE id = :idBiljke")
    suspend fun getBiljkaById(idBiljke: Long): Biljka?

    @Query("SELECT * FROM BiljkaBitmap WHERE idBiljke = :idBiljke")
    suspend fun getImageByIdBiljke(idBiljke: Long): BiljkaBitmap?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImage(b: BiljkaBitmap):Boolean {
        val idBiljke = b.idBiljke
        val bitmap = b.bitmap
        val biljka = getBiljkaById(idBiljke)
        val biljkaBitmap = getImageByIdBiljke(idBiljke)

        return if (biljka != null && biljkaBitmap == null) {
            insertImage(BiljkaBitmap(null, idBiljke, bitmap))
            true
        } else { false }
    }
    @Query("SELECT * FROM Biljka WHERE online_checked = 0")
    suspend fun getOfflineBiljkas(): List<Biljka>

    @Transaction
    suspend fun fixOfflineBiljka(fixData: suspend (Biljka) -> Biljka): Int {
        val offlineBiljkas = getOfflineBiljkas()
        var updatedCount = 0

        for (biljka in offlineBiljkas) {
            val fixedBiljka = fixData(biljka)
            if (biljka != fixedBiljka) {
                saveBiljka(fixedBiljka.copy(onlineChecked = true))
                updatedCount++
            }
        }

        return updatedCount
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToDatabase(biljka: Biljka): Long

    @Transaction
    suspend fun saveBiljka(biljka: Biljka): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val id = saveToDatabase(biljka)
                id != -1L
            } catch (e: Exception) {
                false
            }
        }
    }

    @Query("SELECT * FROM biljka")
    suspend fun getAllBiljkas(): List<Biljka>

    @Query("DELETE FROM biljka")
    suspend fun clearBiljkas()

    @Query("DELETE FROM BiljkaBitmap")
    suspend fun clearBiljkaBitmaps()

    @Transaction
    suspend fun clearData() {
        clearBiljkas()
        clearBiljkaBitmaps()
    }
}