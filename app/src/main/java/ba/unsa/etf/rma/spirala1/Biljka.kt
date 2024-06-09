package ba.unsa.etf.rma.spirala1

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity
@TypeConverters(Converters::class)
data class Biljka(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") var naziv: String,
    @ColumnInfo(name = "porodica") @SerializedName("porodica") var porodica: String,
    @ColumnInfo(name = "medicinsko_upozorenje") @SerializedName("medicinsko_upozorenje") var medicinskoUpozorenje: String?,
    @ColumnInfo(name = "medicinske_koristi") @SerializedName("medicinske_koristi") val medicinskeKoristi: List<MedicinskaKorist>,
    @ColumnInfo(name = "profil_okusa") @SerializedName("profil_okusa") val profilOkusa: ProfilOkusaBiljke?,
    @ColumnInfo(name = "jela") @SerializedName("jela") var jela: List<String>,
    @ColumnInfo(name = "klimatski_tipovi") @SerializedName("klimatski_tipovi") var klimatskiTipovi: List<KlimatskiTip>,
    @ColumnInfo(name = "zemljisni_tipovi") @SerializedName("zemljisni_tipovi") var zemljisniTipovi: List<Zemljište>,
    @ColumnInfo(name = "online_checked") @SerializedName("online_checked") var onlineChecked: Boolean,
) : Serializable


    /*

    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.createStringArrayList()?.map { MedicinskaKorist.valueOf(it) }?.toMutableList() ?: mutableListOf(),
        parcel.readSerializable() as? ProfilOkusaBiljke,
        parcel.createStringArrayList()?.toMutableList() ?: mutableListOf(),
        parcel.createStringArrayList()?.map { KlimatskiTip.valueOf(it) }?.toMutableList() ?: mutableListOf(),
        parcel.createStringArrayList()?.map { Zemljište.valueOf(it) }?.toMutableList() ?: mutableListOf(),
        false
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(naziv)
        dest.writeString(porodica)
        dest.writeString(medicinskoUpozorenje)
        dest.writeStringList(medicinskeKoristi.map { it.name })
        dest.writeSerializable(profilOkusa)
        dest.writeStringList(jela)
        dest.writeStringList(klimatskiTipovi.map { it.name })
        dest.writeStringList(zemljisniTipovi.map { it.name })
    }

    companion object CREATOR : Parcelable.Creator<Biljka> {
        override fun createFromParcel(parcel: Parcel): Biljka {
            return Biljka(parcel)
        }

        override fun newArray(size: Int): Array<Biljka?> {
            return arrayOfNulls(size)
        }
    }

} */