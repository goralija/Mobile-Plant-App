package ba.unsa.etf.rma.spirala1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var biljkePogled: RecyclerView
    private lateinit var biljkeAdapter: BiljkaListAdapter
    private var biljkeLista = biljke
    private var modPrikaza = "Medicinski fokus"

    private val itemClickListener = object : BiljkaListAdapter.OnItemClickListener {
        override fun onItemClick(biljka: Biljka) {
            when (modPrikaza) {
                getString(R.string.botanic) -> {
                    val filteredList = filtrirajBotanicki(biljke, biljka)
                    biljkeLista = filteredList
                    biljkeAdapter.updateBiljke(filteredList)
                }
                getString(R.string.medical) -> {
                    val filteredList = filtrirajMedicinski(biljke, biljka)
                    biljkeLista = filteredList
                    biljkeAdapter.updateBiljke(filteredList)
                }
                getString(R.string.cook) -> {
                    val filteredList = filtrirajKuharski(biljke, biljka)
                    biljkeLista = filteredList
                    biljkeAdapter.updateBiljke(filteredList)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val resetButton = findViewById<Button>(R.id.resetBtn)
        resetButton.setOnClickListener {
            biljkeLista = biljke
            biljkeAdapter.updateBiljke(biljkeLista)
        }

        val novaBiljkaBtn = findViewById<FloatingActionButton>(R.id.novaBiljkaBtn)
        novaBiljkaBtn.setOnClickListener {
            // Create an intent to open the new activity
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            // Start the new activity
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
                modPrikaza = selectedOption

                biljkePogled = findViewById(R.id.biljkeRV)
                biljkePogled.layoutManager = LinearLayoutManager(
                    parent.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                biljkeAdapter = BiljkaListAdapter(listOf(),modPrikaza, itemClickListener)
                biljkePogled.adapter = biljkeAdapter

                biljkeAdapter.updateBiljke(biljkeLista)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }
}