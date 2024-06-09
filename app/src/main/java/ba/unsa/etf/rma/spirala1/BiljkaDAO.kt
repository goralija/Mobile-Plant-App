package ba.unsa.etf.rma.spirala1

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Dao
interface BiljkaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImage(biljkaBitmap: BiljkaBitmap): Long

    @Query("SELECT * FROM biljka WHERE id = :idBiljke LIMIT 1")
    suspend fun getBiljkaById(idBiljke: Long): Biljka?

    @Query("SELECT * FROM biljka_bitmap WHERE idBiljke = :idBiljke LIMIT 1")
    suspend fun getImageByIdBiljke(idBiljke: Long): BiljkaBitmap?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImage(idBiljke: Long,bitmap: Bitmap):Boolean {
        return withContext(Dispatchers.IO) {
            val biljka = getBiljkaById(idBiljke)
            val existingImage = getImageByIdBiljke(idBiljke)

            if (biljka == null || existingImage != null) {
                false
            } else {
                val biljkaBitmap = BiljkaBitmap(idBiljke = idBiljke, bitmap = bitmap)
                insertImage(biljkaBitmap) != -1L
            }
        }
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

    @Query("DELETE FROM biljka_bitmap")
    suspend fun clearBiljkaBitmaps()

    @Transaction
    suspend fun clearData() {
        clearBiljkas()
        clearBiljkaBitmaps()
    }
}