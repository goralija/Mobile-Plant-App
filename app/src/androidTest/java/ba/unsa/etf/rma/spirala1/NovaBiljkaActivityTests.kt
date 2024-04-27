package ba.unsa.etf.rma.spirala1

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.Button
import android.widget.ImageView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import junit.framework.TestCase.assertNotNull
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class NovaBiljkaActivityTests {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(NovaBiljkaActivity::class.java)

    @Test
    fun sviUnosiIspravni() {
        onView(withId(R.id.nazivET)).perform(typeText("aa"))
        onView(withId(R.id.porodicaET)).perform(typeText("aa"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("aa"))
        onView(withId(R.id.jeloET)).perform(scrollTo(), typeText("aa"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())

        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo())
        onView(withText(MedicinskaKorist.SMIRENJE.opis)).perform(click())
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo())
        onView(withText(KlimatskiTip.PLANINSKA.opis)).perform(click())
        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo())
        onView(withText(Zemljište.GLINENO.naziv)).perform(click())

        onView(withId(R.id.profilOkusaLV)).perform(scrollTo())
        onView(withText(ProfilOkusaBiljke.AROMATICNO.opis)).perform(click())

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).check(doesNotExist())
        //zadnja linija provjerava, ako nema dugmeta za dodavanje biljke znači da smo je uspješno
        // dodali i da zato ne može da se pronađe dugme za dodavanje
    }

    @Test
    fun nedostajuEditTextPolja() {
        onView(withId(R.id.jeloET)).perform(scrollTo(), typeText("aa"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())

        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo())
        onView(withText(MedicinskaKorist.SMIRENJE.opis)).perform(click())
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo())
        onView(withText(KlimatskiTip.PLANINSKA.opis)).perform(click())
        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo())
        onView(withText(Zemljište.GLINENO.naziv)).perform(click())

        onView(withId(R.id.profilOkusaLV)).perform(scrollTo())
        onView(withText(ProfilOkusaBiljke.AROMATICNO.opis)).perform(click())

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Naziv mora imati između " +
                "2 i 20 znakova")))
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Porodica mora imati između 2 " +
                "i 20 znakova")))
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Upozorenje mora " +
                "imati između 2 i 20 znakova")))
    }

    @Test
    fun predugaIliPrekratkaEditTextPolja() {
        onView(withId(R.id.jeloET)).perform(scrollTo(), typeText("aa"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())

        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo())
        onView(withText(MedicinskaKorist.SMIRENJE.opis)).perform(click())
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo())
        onView(withText(KlimatskiTip.PLANINSKA.opis)).perform(click())
        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo())
        onView(withText(Zemljište.GLINENO.naziv)).perform(click())

        onView(withId(R.id.profilOkusaLV)).perform(scrollTo())
        onView(withText(ProfilOkusaBiljke.AROMATICNO.opis)).perform(click())

        onView(withId(R.id.nazivET)).perform(scrollTo(), typeText("a"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Naziv mora imati između 2 i 20 znakova")))

        onView(withId(R.id.nazivET)).perform(scrollTo(), typeText("aadfsiughadsfbsitewuhiuxcbgbsd"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Naziv mora imati između 2 i 20 znakova")))

        onView(withId(R.id.porodicaET)).perform(scrollTo(), click())
        onView(withId(R.id.porodicaET)).perform(scrollTo(), click(), typeText("a"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Porodica mora imati između 2 " +
                "i 20 " +
                "znakova")))

        onView(withId(R.id.porodicaET)).perform(scrollTo(), click(), typeText
            ("aadfsiughadsfbsitewuhiuxcbgbsd"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Porodica mora imati između 2 " +
                "i " +
                "20 " +
                "znakova")))

        onView(withId(R.id.medicinskoUpozorenjeET)).perform(scrollTo(), click())
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(scrollTo(), click(), typeText("a"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Upozorenje mora " +
                "imati između 2 i 20 " +
                "znakova")))

        onView(withId(R.id.medicinskoUpozorenjeET)).perform(scrollTo(), click(), typeText
            ("aadfsiughadsfbsitewuhiuxcbgbsd"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Upozorenje mora " +
                "imati između 2 i 20 " +
                "znakova")))

        onView(withId(R.id.jeloET)).perform(scrollTo(), click())
        onView(withId(R.id.jeloET)).perform(scrollTo(), click(), typeText("a"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Jelo mora " +
                "imati između 2 i 20 " +
                "znakova")))

        onView(withId(R.id.jeloET)).perform(scrollTo(), click(), typeText
            ("aadfsiughadsfbsitewuhiuxcbgbsd"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Jelo mora " +
                "imati između 2 i 20 " +
                "znakova")))
    }

    @Test
    fun nedostajeJelo() {
        onView(withId(R.id.nazivET)).perform(typeText("aa"))
        onView(withId(R.id.porodicaET)).perform(typeText("aa"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("aa"))

        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo())
        onView(withText(MedicinskaKorist.SMIRENJE.opis)).perform(click())
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo())
        onView(withText(KlimatskiTip.PLANINSKA.opis)).perform(click())
        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo())
        onView(withText(Zemljište.GLINENO.naziv)).perform(click())

        onView(withId(R.id.profilOkusaLV)).perform(scrollTo())
        onView(withText(ProfilOkusaBiljke.AROMATICNO.opis)).perform(click())

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Dodaj barem jedno jelo")))
    }

    @Test
    fun zabranaUnosaIstihJela() {
        onView(withId(R.id.nazivET)).perform(typeText("aa"))
        onView(withId(R.id.porodicaET)).perform(typeText("aa"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("aa"))

        onView(withId(R.id.jeloET)).perform(scrollTo(), typeText("aa"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.jeloET)).perform(scrollTo(), typeText("aa"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Jelo tog imena već " +
                "postoji. Svako jelo može se dodati samo jednom")))
        //provjera da se ne smije dodati novo jelo istog imena
        onView(withId(R.id.jeloET)).perform(scrollTo(), typeText("aaaa"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.jelaLV)).perform(scrollTo())
        onView(withText("aaaa")).perform(click())
        onView(withId(R.id.jeloET)).perform(scrollTo(), click(), replaceText("aa"))
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Jelo tog imena već " +
                "postoji. Svako jelo može se dodati samo jednom")))
        onView(withId(R.id.jeloET)).perform(scrollTo(), click(), typeText("d"), replaceText(""))
        //provjera da se ne smije izmijeniti jelo tako da se nakon izmjene zove kao neko drugo u
        // listi

        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo())
        onView(withText(MedicinskaKorist.SMIRENJE.opis)).perform(click())
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo())
        onView(withText(KlimatskiTip.PLANINSKA.opis)).perform(click())
        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo())
        onView(withText(Zemljište.GLINENO.naziv)).perform(click())

        onView(withId(R.id.profilOkusaLV)).perform(scrollTo())
        onView(withText(ProfilOkusaBiljke.AROMATICNO.opis)).perform(click())

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).check(doesNotExist())
    }

    @Test
    fun oznacavanjeElemenataListi() {
        onView(withId(R.id.nazivET)).perform(typeText("aa"))
        onView(withId(R.id.porodicaET)).perform(typeText("aa"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("aa"))
        onView(withId(R.id.jeloET)).perform(scrollTo(), typeText("aa"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())

        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo())
        onView(withText(MedicinskaKorist.SMIRENJE.opis)).perform(click())
        onView(withText(MedicinskaKorist.REGULACIJAPRITISKA.opis)).perform(click())
        onView(withText(MedicinskaKorist.PROTIVBOLOVA.opis)).perform(click())
        onView(withText(MedicinskaKorist.REGULACIJAPROBAVE.opis)).perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        //sad ako je ispunjeno da dugme dodajBiljkuBtn i dalje postoji na ekranu, to znači
        //da validacija nije prošla, odnosno, da i pored dobro popunjenih EditText polja
        //ne možemo dodati biljku ako na listama nismo označili barem nešto
        onView(withId(R.id.dodajBiljkuBtn)).check(matches(not(doesNotExist())))


        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo())
        onView(withText(KlimatskiTip.PLANINSKA.opis)).perform(click())
        onView(withText(KlimatskiTip.SREDOZEMNA.opis)).perform(click())
        onView(withText(KlimatskiTip.SUBTROPSKA.opis)).perform(click())
        onView(withText(KlimatskiTip.TROPSKA.opis)).perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        //sad ako je ispunjeno da dugme dodajBiljkuBtn i dalje postoji na ekranu, to znači
        //da validacija nije prošla, odnosno, da i pored dobro popunjenih EditText polja
        //ne možemo dodati biljku ako na listama nismo označili barem nešto
        onView(withId(R.id.dodajBiljkuBtn)).check(matches(not(doesNotExist())))

        onView(withText(KlimatskiTip.SUHA.opis)).check(matches(isNotChecked()))
        onView(withText(KlimatskiTip.TROPSKA.opis)).check(matches(isChecked()))
        onView(withText(KlimatskiTip.SREDOZEMNA.opis)).check(matches(isChecked()))
        //ovo garantuje da je ostalo označeno sve što smo mi kliknuli

        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo())
        onView(withText(Zemljište.GLINENO.naziv)).perform(click())
        onView(withText(Zemljište.ILOVACA.naziv)).perform(click())
        onView(withText(Zemljište.CRNICA.naziv)).perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        //sad ako je ispunjeno da dugme dodajBiljkuBtn i dalje postoji na ekranu, to znači
        //da validacija nije prošla, odnosno, da i pored dobro popunjenih EditText polja
        //ne možemo dodati biljku ako na listama nismo označili barem nešto
        onView(withId(R.id.dodajBiljkuBtn)).check(matches(not(doesNotExist())))

        onView(withText(Zemljište.CRNICA.naziv)).check(matches(isChecked()))
        onView(withText(Zemljište.GLINENO.naziv)).check(matches(isChecked()))
        onView(withText(Zemljište.ILOVACA.naziv)).check(matches(isChecked()))
        //ovo garantuje da je ostalo označeno sve što smo mi kliknuli

        onView(withId(R.id.profilOkusaLV)).perform(scrollTo())
        onView(withText(ProfilOkusaBiljke.AROMATICNO.opis)).perform(click())
        onView(withText(ProfilOkusaBiljke.LJUTO.opis)).perform(click())
        onView(withText(ProfilOkusaBiljke.SLATKI.opis)).perform(click())
        onView(withText(ProfilOkusaBiljke.MENTA.opis)).perform(click())

        onView(withText(ProfilOkusaBiljke.AROMATICNO.opis)).check(matches(isNotChecked()))
        onView(withText(ProfilOkusaBiljke.LJUTO.opis)).check(matches(isNotChecked()))
        //ovo provjerava da je ostalo označeno samo ono što smo zadnje označili

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).check(doesNotExist())
        //zadnja linija provjerava, ako nema dugmeta za dodavanje biljke znači da smo je uspješno
        // dodali i da zato ne može da se pronađe dugme za dodavanje
    }

    @Test
    fun testPrikazaSlikeNakonKlikaNaUslikajBiljkuBtn() {
        val dataIntent: Intent = Intent()
        val resultIntent = Instrumentation.ActivityResult(Activity.RESULT_OK, dataIntent)
        val scenario = ActivityScenario.launch(NovaBiljkaActivity::class.java)

        scenario.onActivity { activity -> activity.findViewById<Button>(R.id.uslikajBiljkuBtn).performClick() }

        scenario.onActivity { activity ->
            val bitmap = BitmapFactory.decodeResource(activity.resources, R.mipmap.ic_launcher)
            val imageView = activity.findViewById(R.id.slikaIV) as ImageView
            imageView.setImageBitmap(bitmap)
        }

        scenario.onActivity { activity ->
            val imageView: ImageView = activity.findViewById(R.id.slikaIV)
            assertNotNull(imageView.drawable) // Provjera je li ImageView postavljen s slikom
        }
    }
}