package ba.unsa.etf.rma.spirala1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class NovaBiljkaActivity : AppCompatActivity() {

    private lateinit var nazivET: EditText
    private lateinit var porodicaET: EditText
    private lateinit var medicinskoUpozorenjeET: EditText
    private lateinit var jeloET: EditText
    private lateinit var medicinskaKoristLV: ListView
    private lateinit var klimatskiTipLV: ListView
    private lateinit var zemljisniTipLV: ListView
    private lateinit var profilOkusaLV: ListView
    private lateinit var jelaLV: ListView
    private lateinit var dodajJeloBtn: MaterialButton
    private lateinit var dodajBiljkuBtn: MaterialButton
    private lateinit var uslikajBiljkuBtn: MaterialButton
    private val jelaList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nova_biljka)
        initializeViews()
        setupListViews()
        setupButtons()
    }

    private fun initializeViews() {
        // Inicijalizacija svih View elemenata
        nazivET = findViewById(R.id.nazivET)
        porodicaET = findViewById(R.id.porodicaET)
        medicinskoUpozorenjeET = findViewById(R.id.medicinskoUpozorenjeET)
        jeloET = findViewById(R.id.jeloET)
        medicinskaKoristLV = findViewById(R.id.medicinskaKoristLV)
        klimatskiTipLV = findViewById(R.id.klimatskiTipLV)
        zemljisniTipLV = findViewById(R.id.zemljisniTipLV)
        profilOkusaLV = findViewById(R.id.profilOkusaLV)
        jelaLV = findViewById(R.id.jelaLV)
        dodajJeloBtn = findViewById(R.id.dodajJeloBtn)
        dodajBiljkuBtn = findViewById(R.id.dodajBiljkuBtn)
        uslikajBiljkuBtn = findViewById(R.id.uslikajBiljkuBtn)
    }

    private fun setupListViews() {
        val medicinskaKoristListView: ListView = findViewById(R.id.medicinskaKoristLV)
        val medicinskaKoristArray = MedicinskaKorist.values().map { it.toString() }.toTypedArray()
        val medicinskaKoristAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, medicinskaKoristArray)
        medicinskaKoristListView.adapter = medicinskaKoristAdapter

        val klimatskiTipListView: ListView = findViewById(R.id.klimatskiTipLV)
        val klimatskiTipArray = KlimatskiTip.values().map { it.toString() }.toTypedArray()
        val klimatskiTipAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, klimatskiTipArray)
        klimatskiTipListView.adapter = klimatskiTipAdapter

        val zemljisniTipListView: ListView = findViewById(R.id.zemljisniTipLV)
        val zemljisniTipArray = Zemljište.values().map { it.toString() }.toTypedArray()
        val zemljisniTipAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, zemljisniTipArray)
        zemljisniTipListView.adapter = zemljisniTipAdapter

        val ProfilOkusaListView: ListView = findViewById(R.id.profilOkusaLV)
        val profilOkusaArray = ProfilOkusaBiljke.values().map { it.toString() }.toTypedArray()
        val profilOkusaAdapter = ArrayAdapter(this, android.R.layout
            .simple_list_item_multiple_choice, profilOkusaArray)
        ProfilOkusaListView.adapter = profilOkusaAdapter

        profilOkusaLV.choiceMode = ListView.CHOICE_MODE_SINGLE
        zemljisniTipLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        klimatskiTipLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        jelaLV.choiceMode = ListView.CHOICE_MODE_NONE

    }

    private fun setupButtons() {
        dodajJeloBtn.setOnClickListener {
            val novoJelo = jeloET.text.toString().trim()
            if (novoJelo.isNotEmpty()) {
                val index = jelaList.indexOfFirst { it.equals(novoJelo, ignoreCase = true) }
                if (index != -1) {
                    jelaList[index] = novoJelo
                } else {
                    jelaList.add(novoJelo)
                }
                jeloET.setText("")
                updateJelaListView()
            }
        }

        dodajBiljkuBtn.setOnClickListener {
            if (validateInputs()) {
                // Kreiranje Intent-a za povratak na MainActivity
                val intent = Intent(this, MainActivity::class.java)
                // Dodavanje dodatnih podataka u Intent ako je potrebno
                // Na primjer: intent.putExtra("naziv", nazivET.text.toString())
                // Slanje Intent-a i završetak trenutne aktivnosti
                startActivity(intent)
                finish()
            }
        }

        // Postavljanje click listener-a za ostala dugmad...
    }

    private fun updateJelaListView() {
        // Ažuriranje adaptera za list view sa jelima
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jelaList)
        jelaLV.adapter = adapter
    }

    private fun validateInputs(): Boolean {
        var valid = true

        if (nazivET.text.isEmpty() || nazivET.text.length !in 2..20) {
            nazivET.error = "Naziv mora imati između 2 i 20 znakova"
            valid = false
        }
        if (jelaList.isEmpty()) {
            dodajJeloBtn.error = "Dodaj barem jedno jelo"
            valid = false
        }
        // Dodati ostale validacije ovdje...

        return valid
    }


    // Implementacija ostalih funkcionalnosti i validacija...
}
