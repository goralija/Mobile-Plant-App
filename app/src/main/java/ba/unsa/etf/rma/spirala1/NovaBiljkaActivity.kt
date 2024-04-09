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
    private val medicinskaMolba = R.id.molbaMedicinskaKorist
    private val klimaMolba = R.id.molbaKlimatskiTip
    private val zemljisteMolba = R.id.molbaZemljisniTip
    private val okusMolba = R.id.molbaProfilOkusa
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
        var ind = -1
        dodajJeloBtn.text = "Dodaj jelo"

        jelaLV.setOnItemClickListener { _, _, position, _ ->
            val odabranoJelo = jelaLV.getItemAtPosition(position).toString()
            jeloET.setText(odabranoJelo)
            dodajJeloBtn.text = "Izmijeni jelo"
            ind = position
        }

        dodajJeloBtn.setOnClickListener {
            val novoJelo = jeloET.text.toString().trim()
            if (novoJelo.isNotEmpty()) {
                if (dodajJeloBtn.text == "Izmijeni jelo") {
                    if (jelaList.any { it.equals(novoJelo, ignoreCase = true) }) {
                        jeloET.error =
                            "Jelo tog imena već postoji. Svako jelo može se dodati samo jednom"
                    }
                    else if (ind != -1) {
                        jelaList[ind] = novoJelo
                        ind = -1
                    }
                } else {
                    if (jelaList.any { it.equals(novoJelo, ignoreCase = true) }) jeloET.error =
                        "Jelo tog imena već postoji. Svako jelo može se dodati samo jednom"
                     else jelaList.add(novoJelo)
                }
                jeloET.setText("")
                updateJelaListView()
                dodajJeloBtn.text = "Dodaj jelo"
            }
            else if (novoJelo.isEmpty() && dodajJeloBtn.text == "Izmijeni jelo" && ind != -1)
            {
                jelaList.removeAt(ind)
                updateJelaListView()
                dodajJeloBtn.text = "Dodaj jelo"
            }
        }

        dodajBiljkuBtn.setOnClickListener {
            if (validateInputs()) {
                val intent = Intent(this, MainActivity::class.java)
                //treba dodati još da se za Recycler View servira dodata biljka, uz sve one koje
                // su vec bile tu

                // Dodavanje dodatnih podataka u Intent ako je potrebno
                // Na primjer: intent.putExtra("naziv", nazivET.text.toString())
                startActivity(intent)
                finish()
            }
        }

        uslikajBiljkuBtn.setOnClickListener{
            //inicira intent za slikanje slike nakon čega sliku prikazuje u ImageView elementu (R
            // .id.slikaIV)
            //slika se za sada ne mora prenositi iz forme za dodavanje, u RV se moze i dalje
            // drzati obicna slika
        }
    }

    private fun updateJelaListView() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jelaList)
        jelaLV.adapter = adapter
    }

    private fun validateInputs(): Boolean {
        var valid = true

        if (nazivET.text.isEmpty() || nazivET.text.length !in 2..20) {
            nazivET.error = "Naziv mora imati između 2 i 20 znakova"
            valid = false
            return valid
        }
        if (porodicaET.text.isEmpty() || porodicaET.text.length !in 2..20) {
            porodicaET.error = "Porodica mora imati između 2 i 20 znakova"
            valid = false
            return valid
        }
        if (medicinskoUpozorenjeET.text.isEmpty() || medicinskoUpozorenjeET.text.length !in 2..20) {
            medicinskoUpozorenjeET.error = "Upozorenje mora imati između 2 i 20 znakova"
            valid = false
            return valid
        }
        if (jelaList.isEmpty()) {
            jeloET.error = "Dodaj barem jedno jelo"
            valid = false
            return valid
        }
        val listViews = listOf(medicinskaKoristLV, klimatskiTipLV, zemljisniTipLV, profilOkusaLV)
        valid = listViews.all { it.checkedItemPosition != ListView.INVALID_POSITION }
        if (!valid) {
            dodajBiljkuBtn.error = "ne valja"
            dodajBiljkuBtn.error.toString()
        }


        return valid
    }
}
