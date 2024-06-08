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
        return withContext(Dispatchers.IO) {
            biljkaDao.saveBiljka(biljka)
        }
    }

    suspend fun fixOfflineBiljka(): Int {
        return withContext(Dispatchers.IO) {
            biljkaDao.fixOfflineBiljka()
        }
    }

    suspend fun addImage(idBiljke: Long, bitmap: Bitmap): Boolean {
        return withContext(Dispatchers.IO) {
            biljkaDao.addImage(idBiljke, bitmap)
        }
    }

    suspend fun clearData() {
        withContext(Dispatchers.IO) {
            biljkaDao.clearData()
        }
    }

    private fun refreshAllBiljkas() {
        CoroutineScope(Dispatchers.IO).launch {
            val biljkas = biljkaDao.getAllBiljkas()
            aaallBiljkas.postValue(biljkas)
        }
    }

    fun getAllBiljkas(): LiveData<List<Biljka>> { return allBiljkas }
}