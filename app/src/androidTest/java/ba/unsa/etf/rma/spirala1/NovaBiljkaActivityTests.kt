package ba.unsa.etf.rma.spirala1

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class NovaBiljkaActivityTests {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(NovaBiljkaActivity::class.java)

    @Test
    fun testInvalidInputs() {
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())

        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Naziv mora imati između 2 i 20 znakova")))
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Porodica mora imati između 2 i 20 znakova")))
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Upozorenje mora imati između 2 i 20 znakova")))
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Dodaj barem jedno jelo")))
    }

    @Test
    fun testValidInputs() {
        onView(withId(R.id.nazivET)).perform(typeText("Test Naziv"), closeSoftKeyboard())
        onView(withId(R.id.porodicaET)).perform(typeText("Test Porodica"), closeSoftKeyboard())
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("Test Upozorenje"), closeSoftKeyboard())

        onView(withText("PROTUUPALNO")).perform(click())
        onView(withText("SMIRENJE")).perform(click())

        onView(withId(R.id.dodajBiljkuBtn)).perform(click())

        onView(withId(R.id.nazivET)).check(doesNotExist())
    }



    //testira se prikazivanje slike biljke nakon klika na dugme R.id.uslikajBiljkuBtn
}