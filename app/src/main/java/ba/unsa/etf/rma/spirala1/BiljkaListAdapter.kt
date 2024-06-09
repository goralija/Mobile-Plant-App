package ba.unsa.etf.rma.spirala1

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ba.unsa.etf.rma.spirala1.TrefleDAO.Result
import kotlinx.coroutines.withContext
import java.net.URL

class BiljkaListAdapter(private var biljke: List<Biljka>, private var selectedMode: String,
                        private val itemClickListener: OnItemClickListener, private val mainActivity: MainActivity) :
    RecyclerView.Adapter<BiljkaListAdapter.BiljkaViewHolder>() {

    var clickablePlants: Boolean = true
    private var biljkaDAO: BiljkaDAO = BiljkaDatabase.getDatabase(mainActivity).biljkaDao()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val viewId = when (selectedMode) {
            getString(parent.context,R.string.medical) -> R.layout.medicinski_view
            getString(parent.context,R.string.cook) -> R.layout.kuharski_view
            getString(parent.context,R.string.botanic) -> R.layout.botanicki_view
            else -> R.layout.default_view // Create a default view layout
        }
        val view = LayoutInflater.from(parent.context).inflate(viewId, parent, false)
        return BiljkaViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size
    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        holder.ime.text = biljke[position].naziv
        holder.upozorenje?.text = biljke[position].medicinskoUpozorenje ?: ""
        holder.korist1?.text = biljke[position].medicinskeKoristi.getOrNull(0)?.opis ?: ""
        holder.korist2?.text = biljke[position].medicinskeKoristi.getOrNull(1)?.opis ?: ""
        holder.korist3?.text = biljke[position].medicinskeKoristi.getOrNull(2)?.opis ?: ""
        holder.jelo1?.text = biljke[position].jela.getOrNull(0) ?: ""
        holder.jelo2?.text = biljke[position].jela.getOrNull(1) ?: ""
        holder.jelo3?.text = biljke[position].jela.getOrNull(2) ?: ""
        holder.klimatskiTip?.text = biljke[position].klimatskiTipovi?.getOrNull(0)?.opis ?: ""
        holder.porodica?.text = biljke[position].porodica ?: ""
        holder.profilOkusa?.text = biljke[position].profilOkusa?.opis
        holder.zemljisniTip?.text = biljke[position].zemljisniTipovi.getOrNull(0)?.naziv ?: ""
        val context: Context = holder.slika.context

        val scope = CoroutineScope(Job() + Dispatchers.Main)

        scope.launch {
            if (position < biljke.size) {
                val bb = biljkaDAO.getImageByIdBiljke(biljke[position].id)
                if (bb == null) {
                    val result = TrefleDAO().getImage(biljke[position])
                    when (result) {
                        is Result.Success<Bitmap> -> {
                            biljkaDAO.addImage(biljke[position].id, result.data)
                            holder.slika.setImageBitmap(result?.data)
                        } else -> {}
                    }
                } else holder.slika.setImageBitmap(bb?.bitmap)
            }
        }

        if (selectedMode == getString(context, R.string.botanic))
            mainActivity.setBotanicMode(true)
        else
            mainActivity.setBotanicMode(false)

    }
    fun updateBiljke(biljke: List<Biljka>, clickable: Boolean=true) {
        this.biljke = biljke
        clickablePlants = clickable
        notifyDataSetChanged()
    }


    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView),
        View.OnClickListener {
        val slika: ImageView = itemView.findViewById(R.id.slikaItem)
        val ime: TextView = itemView.findViewById(R.id.nazivItem)
        val upozorenje: TextView? = itemView.findViewById(R.id.upozorenjeItem)
        val klimatskiTip: TextView? = itemView.findViewById(R.id.klimatskiTipItem)
        val zemljisniTip: TextView? = itemView.findViewById(R.id.zemljisniTipItem)
        val profilOkusa: TextView? = itemView.findViewById(R.id.profilOkusaItem)
        val porodica: TextView? = itemView.findViewById(R.id.porodicaItem)
        val korist1: TextView? = itemView.findViewById(R.id.korist1Item)
        val korist2: TextView? = itemView.findViewById(R.id.korist2Item)
        val korist3: TextView? = itemView.findViewById(R.id.korist3Item)
        val jelo1: TextView? = itemView.findViewById(R.id.jelo1Item)
        val jelo2: TextView? = itemView.findViewById(R.id.jelo2Item)
        val jelo3: TextView? = itemView.findViewById(R.id.jelo3Item)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (clickablePlants) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = biljke[position]
                    itemClickListener.onItemClick(clickedItem)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(biljka: Biljka)
    }
}