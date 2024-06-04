package ba.unsa.etf.rma.spirala1

import android.os.Parcel
import android.os.Parcelable

data class Biljka(
    var naziv: String,
    var porodica: String,
    var medicinskoUpozorenje: String?,
    val medicinskeKoristi: List<MedicinskaKorist>,
    val profilOkusa: ProfilOkusaBiljke?,
    var jela: List<String>,
    var klimatskiTipovi: List<KlimatskiTip>,
    var zemljisniTipovi: List<Zemljište>,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.createStringArrayList()?.map { MedicinskaKorist.valueOf(it) }?.toMutableList() ?: mutableListOf(),
        parcel.readSerializable() as? ProfilOkusaBiljke,
        parcel.createStringArrayList()?.toMutableList() ?: mutableListOf(),
        parcel.createStringArrayList()?.map { KlimatskiTip.valueOf(it) }?.toMutableList() ?: mutableListOf(),
        parcel.createStringArrayList()?.map { Zemljište.valueOf(it) }?.toMutableList() ?: mutableListOf()
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
}