package ba.unsa.etf.rma.spirala1

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class DatabaseTest {

    private lateinit var db: BiljkaDatabase
    private lateinit var dao: BiljkaDAO

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, BiljkaDatabase::class.java).build()
        dao = db.biljkaDao()
    }
    @After
    fun closeDb() {
        db.close()
    }

    //dodaje biljku u bazu pa provjerava sa getALLbiljkas da li je dodata
    @Test
    fun testAddAndGetBiljka() = runBlocking {
        val biljka = Biljka(
            id = 0,
            naziv = "Test Biljka",
            porodica = "Test Porodica",
            medicinskoUpozorenje = null,
            medicinskeKoristi = listOf(),
            profilOkusa = null,
            jela = listOf(),
            klimatskiTipovi = listOf(),
            zemljisniTipovi = listOf(),
            onlineChecked = false
        )
        val succesful = dao.saveBiljka(biljka)
        val allBiljkas = dao.getAllBiljkas()
        assertTrue(succesful)   // ovdje vec osiguravamo da je dodata biljka jer saveBiljka vraca true samo ako je uspjesno dodavanje
        assertEquals(1, allBiljkas.size)
        assertEquals(biljka.porodica, allBiljkas[0].porodica)
        assertEquals(biljka.naziv, allBiljkas[0].naziv)
    }

    //provjerava da li poslije clearData() getAllBiljkas daje praznu listu
    @Test
    fun testClearData() = runBlocking {
        val biljka = Biljka(
            id = 0, // default id for auto-generation
            naziv = "Test Biljka",
            porodica = "Test Porodica",
            medicinskoUpozorenje = null,
            medicinskeKoristi = listOf(),
            profilOkusa = null,
            jela = listOf(),
            klimatskiTipovi = listOf(),
            zemljisniTipovi = listOf(),
            onlineChecked = false
        )
        dao.saveBiljka(biljka)
        val allBiljkas1 = dao.getAllBiljkas()
        assertFalse(allBiljkas1.isEmpty()) //osiguravamo da je stvarno dodata biljka u bazu
        dao.clearData()
        val allBiljkas2 = dao.getAllBiljkas()
        assertTrue(allBiljkas2.isEmpty()) //garantira da clearData() stvarno brise podatke iz baze
    }
}