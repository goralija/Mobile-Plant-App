package ba.unsa.etf.rma.spirala1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    private lateinit var slikaIV: ImageView
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
        slikaIV = findViewById(R.id.slikaIV)
    }

    private fun setupListViews() {
        val medicinskaKoristListView: ListView = findViewById(R.id.medicinskaKoristLV)
        val medicinskaKoristArray = MedicinskaKorist.values().map { it.opis }.toTypedArray()
        val medicinskaKoristAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, medicinskaKoristArray)
        medicinskaKoristListView.adapter = medicinskaKoristAdapter

        val klimatskiTipListView: ListView = findViewById(R.id.klimatskiTipLV)
        val klimatskiTipArray = KlimatskiTip.values().map { it.opis }.toTypedArray()
        val klimatskiTipAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, klimatskiTipArray)
        klimatskiTipListView.adapter = klimatskiTipAdapter

        val zemljisniTipListView: ListView = findViewById(R.id.zemljisniTipLV)
        val zemljisniTipArray = Zemljište.values().map { it.naziv }.toTypedArray()
        val zemljisniTipAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, zemljisniTipArray)
        zemljisniTipListView.adapter = zemljisniTipAdapter

        val ProfilOkusaListView: ListView = findViewById(R.id.profilOkusaLV)
        val profilOkusaArray = ProfilOkusaBiljke.values().map { it.opis }.toTypedArray()
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
            if (novoJelo.isNotEmpty() && novoJelo.length <= 20 && novoJelo.length >= 2) {
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
            else if (novoJelo.length > 20 || novoJelo.length < 2) jeloET.error = "Jelo mora imati" +
                    " između 2 i 20 znakova"
        }

        dodajBiljkuBtn.setOnClickListener {
            if (validateInputs()) {
                val naziv = nazivET.text.toString()
                val porodica = porodicaET.text.toString()
                val medicinskoUpozorenje = medicinskoUpozorenjeET.text.toString()

                val selectedMedicinskaKorist = mutableListOf<MedicinskaKorist>()
                val selectedKlimatskiTip = mutableListOf<KlimatskiTip>()
                val selectedZemljisniTip = mutableListOf<Zemljište>()

                for (i in 0 until medicinskaKoristLV.count) {
                    if (medicinskaKoristLV.isItemChecked(i))
                        selectedMedicinskaKorist.add(MedicinskaKorist.values()[i])
                }
                for (i in 0 until klimatskiTipLV.count) {
                    if (klimatskiTipLV.isItemChecked(i))
                        selectedKlimatskiTip.add(KlimatskiTip.values()[i])
                }
                for (i in 0 until zemljisniTipLV.count) {
                    if (zemljisniTipLV.isItemChecked(i))
                        selectedZemljisniTip.add(Zemljište.values()[i])
                }
                val selectedProfilOkusa = ProfilOkusaBiljke.values()[profilOkusaLV.checkedItemPosition]
                var jela = jelaList.toMutableList()
                val novaBiljka = Biljka(naziv, porodica, medicinskoUpozorenje,
                    selectedMedicinskaKorist, selectedProfilOkusa, jela, selectedKlimatskiTip,
                    selectedZemljisniTip)

                Log.v("biljkaaaa1-------",novaBiljka.toString())

                CoroutineScope(Dispatchers.Main).launch {
                    val fixedBiljka = withContext(Dispatchers.IO) {
                        TrefleDAO().fixData(novaBiljka)
                    }

                    Log.v("biljkaaaa2-------",fixedBiljka.toString())

                    biljke.add(fixedBiljka)
                    val intent = Intent(this@NovaBiljkaActivity, MainActivity::class.java).putExtra("novaBiljka", fixedBiljka)
                    startActivity(intent)
                    finish()
                }
            }
        }

        uslikajBiljkuBtn.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent,1)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Nema aplikacije za slikanje.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateJelaListView() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jelaList)
        jelaLV.adapter = adapter
    }

    private fun validateInputs(): Boolean {
        var valid = true
        if (nazivET.text.isEmpty() || nazivET.text.length !in 2..40) {
            nazivET.error = "Naziv mora imati između 2 i 40 znakova"
            valid = false
        }
        if (porodicaET.text.isEmpty() || porodicaET.text.length !in 2..20) {
            porodicaET.error = "Porodica mora imati između 2 i 20 znakova"
            valid = false
        }
        if (medicinskoUpozorenjeET.text.isEmpty() || medicinskoUpozorenjeET.text.length !in 2..20) {
            medicinskoUpozorenjeET.error = "Upozorenje mora imati između 2 i 20 znakova"
            valid = false
        }
        if (jelaList.isEmpty()) {
            jeloET.error = "Dodaj barem jedno jelo"
            valid = false
        }
        val listViews = listOf(medicinskaKoristLV, klimatskiTipLV, zemljisniTipLV, profilOkusaLV)
        if (!listViews.all { it.checkedItemCount >= 1 }) {
            dodajBiljkuBtn.error = "Potrebno je da označite barem po jednu stavku sa svake liste " +
                    "navedene iznad"
            valid = false
        }
        return valid
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            slikaIV.setImageBitmap(imageBitmap)
        }
    }
}
