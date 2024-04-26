package ba.unsa.etf.rma.spirala1

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
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

        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo(), click())
        onView(withText(KlimatskiTip.PLANINSKA.opis)).perform(click())
        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo(), click())
        onView(withText(Zemljište.GLINENO.naziv)).perform(click())
        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo(), click())
        onView(withText(MedicinskaKorist.SMIRENJE.opis)).perform(click())

        onView(withId(R.id.profilOkusaLV)).perform(scrollTo(), click())
        onView(withText(ProfilOkusaBiljke.AROMATICNO.opis)).perform(click())

        onView(withId(R.id.jeloET)).perform(scrollTo(), typeText("aa"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withText("Potrebno je da označite barem po jednu stavku sa svake liste " +
                "navedene iznad")).check(doesNotExist())
    }
}