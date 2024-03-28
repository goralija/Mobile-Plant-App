package ba.unsa.etf.rma.spirala1

data class Biljka(
    val naziv : String,
    val porodica : String,
    val medicinskoUpozorenje : String,
    val medicinskeKoristi : List<MedicinskaKorist>,
    val profilOkusa : ProfilOkusaBiljke,
    val jela : List<String>,
    val klimatskiTipovi : List<KlimatskiTip>,
    val zemljisniTipovi : List<Zemljište>,
    )