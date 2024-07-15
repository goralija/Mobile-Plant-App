package ba.unsa.etf.rma.spirala1

import android.annotation.SuppressLint
import android.content.Intent
import android.database.CursorWindow
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Field


class MainActivity : AppCompatActivity() {
    private lateinit var biljkaDAO: BiljkaDAO
    private lateinit var biljkePogled: RecyclerView
    private lateinit var biljkeAdapter: BiljkaListAdapter
    private var biljkeLista: List<Biljka> = listOf()
    private var modPrikaza = "Medicinski fokus"

    private lateinit var pretragaPolje: ConstraintLayout
    private lateinit var pretragaET: EditText
    private lateinit var bojaSPIN: Spinner
    private lateinit var brzaPretraga: Button


    private val itemClickListener = object : BiljkaListAdapter.OnItemClickListener {
        override fun onItemClick(biljka: Biljka) {
            when (modPrikaza) {
                getString(R.string.botanic) -> {
                    val filteredList = filtrirajBotanicki(biljkeLista, biljka)
                    biljkeLista = filteredList.toMutableList()
                    biljkeAdapter.updateBiljke(filteredList)
                }
                getString(R.string.medical) -> {
                    val filteredList = filtrirajMedicinski(biljkeLista, biljka)
                    biljkeLista = filteredList.toMutableList()
                    biljkeAdapter.updateBiljke(filteredList)
                }
                getString(R.string.cook) -> {
                    val filteredList = filtrirajKuharski(biljkeLista, biljka)
                    biljkeLista = filteredList.toMutableList()
                    biljkeAdapter.updateBiljke(filteredList)
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    fun setBotanicMode(isBotanic: Boolean) {
        val visibility = if (isBotanic) View.VISIBLE else View.GONE
        pretragaET.visibility = visibility
        bojaSPIN.visibility = visibility
        brzaPretraga.visibility = visibility
        biljkePogled.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = if (isBotanic) 358 else 0
        }
    }

    private fun updateRecyclerView(plants: List<Biljka>, enableClicks: Boolean) {
        val recyclerView: RecyclerView = findViewById(R.id.biljkeRV)
        val adapter = recyclerView.adapter as BiljkaListAdapter
        adapter.updateBiljke(plants, enableClicks)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        try {
            val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.setAccessible(true)
            field.set(null, 100 * 1024 * 1024) //the 100MB is the new size
        } catch (e: Exception) {
            Log.e("error",e.toString())
        }

        biljkaDAO = BiljkaDatabase.getDatabase(this@MainActivity).biljkaDao()
        //clearDatabase()

        biljkePogled = findViewById(R.id.biljkeRV)
        biljkePogled.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        biljkeAdapter = BiljkaListAdapter(listOf(), modPrikaza, itemClickListener, this)
        biljkePogled.adapter = biljkeAdapter

        fetchAllBiljkas()

        val resetButton = findViewById<Button>(R.id.resetBtn)
        resetButton.setOnClickListener {
            fetchAllBiljkas()
        }

        val novaBiljkaBtn = findViewById<FloatingActionButton>(R.id.novaBiljkaBtn)
        novaBiljkaBtn.setOnClickListener {
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            startActivity(intent)
        }

        val spinner = findViewById<Spinner>(R.id.modSpinner)
        val options = arrayOf<String>(getString(R.string.medical),getString(R.string.cook),
            getString(R.string.botanic))
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedOption = parent.getItemAtPosition(position) as String
                Toast.makeText(this@MainActivity, "Selected: $selectedOption", Toast.LENGTH_SHORT).show()
                setBotanicMode(false)
                modPrikaza = selectedOption
                biljkeAdapter = BiljkaListAdapter(listOf(), modPrikaza, itemClickListener, this@MainActivity)
                biljkePogled.adapter = biljkeAdapter

                biljkeAdapter.updateBiljke(biljkeLista)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        //dio koji dodajemo kako bi se prikazali novi elementi u botanickom modu

        pretragaET = findViewById(R.id.pretragaET)
        bojaSPIN = findViewById(R.id.bojaSPIN)
        brzaPretraga = findViewById(R.id.brzaPretraga)
        pretragaPolje = findViewById(R.id.pretragaPolje)
        ArrayAdapter.createFromResource(
            this,
            R.array.colors_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bojaSPIN.adapter = adapter
        }

        brzaPretraga.setOnClickListener {
            val searchText = pretragaET.text.toString()
            val selectedColor = bojaSPIN.selectedItem?.toString()
            if (searchText.isNotEmpty() && !selectedColor.isNullOrEmpty()) {
                    val scope = CoroutineScope(Job() + Dispatchers.Main)
                    scope.launch {
                        val result = TrefleDAO().getPlantsWithFlowerColor(selectedColor, searchText)
                        updateRecyclerView(result,false)
                    }
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Please enter search text and select a color.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setBotanicMode(false)
        }
    }

    private fun clearDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            biljkaDAO.clearData()
        }
    }
    private fun fetchAllBiljkas() {
        CoroutineScope(Dispatchers.IO).launch {
            biljkeLista = biljkaDAO.getAllBiljkas()
            withContext(Dispatchers.Main) {
                biljkeAdapter.updateBiljke(biljkeLista)
            }
        }
    }
}