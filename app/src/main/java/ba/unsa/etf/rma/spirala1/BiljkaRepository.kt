package ba.unsa.etf.rma.spirala1

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BiljkaRepository(context: Context) {
    private val biljkaDao: BiljkaDAO = BiljkaDatabase.getDatabase(context).biljkaDao()

    private val aaallBiljkas = MutableLiveData<List<Biljka>>()
    val allBiljkas: LiveData<List<Biljka>> get() = aaallBiljkas

    init { refreshAllBiljkas() }

    suspend fun saveBiljka(biljka: Biljka): Boolean {
        return biljkaDao.saveBiljka(biljka) != false
    }

    suspend fun fixOfflineBiljka(): Int {
        val offlineBiljkas = biljkaDao.getOfflineBiljkas()
        var updatedCount = 0

        for (biljka in offlineBiljkas) {
            val fixedBiljka = TrefleDAO().fixData(biljka) // Your fixData method implementation
            if (biljka != fixedBiljka) {
                biljkaDao.updateBiljka(fixedBiljka)
                updatedCount++
            }
        }

        return updatedCount
    }

    suspend fun addImage(idBiljke: Long, bitmap: Bitmap): Boolean {
        val biljka = biljkaDao.getBiljkaById(idBiljke)
        return if (biljka != null) {
            val biljkaImage = BiljkaBitmap(idBiljke, bitmap)
            biljkaDao.addImage(biljkaImage) > 0
        } else {
            false
        }
    }

    suspend fun getAllBiljkas(): List<Biljka> {
        return biljkaDao.getAllBiljkas()
    }

    suspend fun clearData() {
        biljkaDao.clearData()
    }

    private fun refreshAllBiljkas() {
        CoroutineScope(Dispatchers.IO).launch {
            val biljkas = biljkaDao.getAllBiljkas()
            aaallBiljkas.postValue(biljkas)
        }
    }
}