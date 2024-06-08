package ba.unsa.etf.rma.spirala1

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BiljkaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveBiljka(biljka: Biljka): Boolean
        //spašava biljku u bazu. Vraća true ako je uspješno dodana biljka inače vraća false





    //ove metode su tu samo da posluže za lakšu izradu metoda
    //koje će se moći pozvati kroz Repository klasu
    @Query("SELECT * FROM Biljka WHERE online_checked = 0")
    suspend fun getOfflineBiljkas(): List<Biljka>

    @Update
    suspend fun updateBiljka(biljka: Biljka) : Int

    @Query("SELECT * FROM biljka WHERE id = :idBiljke")
    suspend fun getBiljkaById(idBiljke: Long): Biljka?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImage(biljkaBitmap: BiljkaBitmap): Long






    @Update
    suspend fun fixOfflineBiljka():Int
        //pokreće fixData metodu za sve biljke iz baze koje imaju vrijednost atributa onlineChecked na false i ažurira biljke u bazi na osnovu rezultata poziva metode.
        //Vraća broj ažuriranih biljaka u bazi. Biljka se smatra da je ažurirana ako je bar jedan atribut nakon poziva fixData metode promijenjen.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImage(idBiljke: Long,bitmap: Bitmap):Boolean
        //dodaje sliku biljke u bazu i vraća true ako je uspješno dodavanje, a ako slika za zadanu biljku postoji ili biljka ne postoji vraća false

    @Query("SELECT * FROM Biljka")
    suspend fun getAllBiljkas():List<Biljka>
        //vraća sve biljke iz baze podataka

    @Query("DELETE FROM Biljka")
    suspend fun clearData()
        //briše sve biljke i slike iz baze podataka
}