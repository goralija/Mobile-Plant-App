package ba.unsa.etf.rma.spirala1

import android.os.Parcel
import android.os.Parcelable

data class Biljka(
    val naziv: String?,
    val porodica: String?,
    val medicinskoUpozorenje: String?,
    val medicinskeKoristi: List<MedicinskaKorist>,
    val profilOkusa: ProfilOkusaBiljke,
    val jela: List<String>,
    val klimatskiTipovi: List<KlimatskiTip>,
    val zemljisniTipovi: List<Zemljište>,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()?.map { MedicinskaKorist.valueOf(it) } ?: emptyList(),
        parcel.readSerializable() as ProfilOkusaBiljke,
        parcel.createStringArrayList() ?: emptyList(),
        parcel.createStringArrayList()?.map { KlimatskiTip.valueOf(it) } ?: emptyList(),
        parcel.createStringArrayList()?.map { Zemljište.valueOf(it) } ?: emptyList()
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