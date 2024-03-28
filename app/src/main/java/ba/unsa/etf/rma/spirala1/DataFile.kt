package ba.unsa.etf.rma.spirala1

val biljke = listOf(
    Biljka(
        naziv = "Bosiljak (Ocimum basilicum)",
        porodica = "Lamiaceae (usnate)",
        medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
        jela = listOf("Salata od paradajza", "Punjene tikvice"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljište.PJESKOVITO, Zemljište.ILOVACA)
    ),
    Biljka(
        naziv = "Nana (Mentha spicata)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA,
            MedicinskaKorist.SMIRENJE),
        profilOkusa = ProfilOkusaBiljke.MENTA,
        jela = listOf("Jogurt sa voćem", "Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljište.GLINENO, Zemljište.CRNICA)
    ),
    Biljka(
        naziv = "Kamilica (Matricaria chamomilla)",
        porodica = "Asteraceae (glavočike)",
        medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Čaj od kamilice"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljište.PJESKOVITO, Zemljište.KRECNJACKO)
    ),
    Biljka(
        naziv = "Ružmarin (Rosmarinus officinalis)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPRITISKA),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Pečeno pile", "Grah","Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljište.SLJUNKOVITO, Zemljište.KRECNJACKO)
    ),
    Biljka(
        naziv = "Lavanda (Lavandula angustifolia)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.IMMUNOSUPORT, MedicinskaKorist.REGULACIJAPRITISKA),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Jogurt sa voćem"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljište.PJESKOVITO, Zemljište.KRECNJACKO)
    ),
    Biljka(
        naziv = "Peršun (Petroselinum crispum)",
        porodica = "Apiaceae (štitarka)",
        medicinskoUpozorenje = "Osobe koje uzimaju lijekove za razrjeđivanje krvi trebaju izbjegavati peršun u velikim količinama zbog visokog udjela vitamina K.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.IMMUNOSUPORT),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Tzatziki", "Juha od povrća"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljište.ILOVACA, Zemljište.GLINENO)
    ),
    Biljka(
        naziv = "Majčina dušica (Thymus vulgaris)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice jer može izazvati kontrakcije maternice.",
        medicinskeKoristi = listOf(MedicinskaKorist.IMMUNOSUPORT, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Pečena piletina", "Povrće sa roštilja"),
        klimatskiTipovi = listOf(KlimatskiTip.SUHA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljište.KRECNJACKO, Zemljište.PJESKOVITO)
    ),
    Biljka(
        naziv = "Kopar (Anethum graveolens)",
        porodica = "Apiaceae (štitarka)",
        medicinskoUpozorenje = "Moguća je alergijska reakcija kod osoba preosjetljivih na biljke iz porodice Apiaceae.",
        medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE, MedicinskaKorist.IMMUNOSUPORT),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Riblji specijaliteti", "Krastavci u kiselom vrhnju"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SREDOZEMNA),
        zemljisniTipovi = listOf(Zemljište.ILOVACA, Zemljište.PJESKOVITO)
    ),
    Biljka(
        naziv = "Đumbir (Zingiber officinale)",
        porodica = "Zingiberaceae (đumbirovke)",
        medicinskoUpozorenje = "Osobe koje uzimaju lijekove za razrjeđivanje krvi ili imaju problema sa žučnom kesom trebaju izbjegavati veće količine đumbira.",
        medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE, MedicinskaKorist.IMMUNOSUPORT),
        profilOkusa = ProfilOkusaBiljke.KORIJENASTO,
        jela = listOf("Đumbir čaj", "Wok s povrćem"),
        klimatskiTipovi = listOf(KlimatskiTip.TROPSKA, KlimatskiTip.SREDOZEMNA),
        zemljisniTipovi = listOf(Zemljište.CRNICA, Zemljište.ILOVACA)
    ),
    Biljka(
        naziv = "Bazga (Sambucus nigra)",
        porodica = "Adoxaceae (koprivnjače)",
        medicinskoUpozorenje = "Bazga može biti toksična ako se koristi nepravilno. Cvjetovi i bobice su jestivi, ali ostatak biljke može biti otrovan.",
        medicinskeKoristi = listOf(MedicinskaKorist.IMMUNOSUPORT, MedicinskaKorist.PROTUUPALNO,
            MedicinskaKorist.REGULACIJAPRITISKA),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Bazga sirup", "Pita od bazge"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljište.CRNICA, Zemljište.PJESKOVITO)
    ),
    Biljka(
        naziv = "Kopriva (Urtica dioica)",
        porodica = "Urticaceae (koprive)",
        medicinskoUpozorenje = "Kopriva može izazvati alergijske reakcije kod osoba osjetljivih na nju. Treba je koristiti oprezno.",
        medicinskeKoristi = listOf(MedicinskaKorist.IMMUNOSUPORT, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Kopriva juha", "Pire od koprive"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljište.ILOVACA, Zemljište.CRNICA)
    ),
    Biljka(
        naziv = "Celer (Apium graveolens)",
        porodica = "Apiaceae (štitarka)",
        medicinskoUpozorenje = "Osobe koje su alergične na biljke iz porodice Apiaceae trebaju izbjegavati celer.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Celer salata", "Celer juha"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SREDOZEMNA),
        zemljisniTipovi = listOf(Zemljište.ILOVACA, Zemljište.SLJUNKOVITO)
    ),
    Biljka(
        naziv = "Matičnjak (Melissa officinalis)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Osobe koje uzimaju lijekove za hipotireozu trebaju izbjegavati matičnjak jer može usporiti funkciju štitnjače.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Matičnjak čaj", "Salata s matičnjakom"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljište.ILOVACA, Zemljište.CRNICA)
    ),
    Biljka(
        naziv = "Koromač (Foeniculum vulgare)",
        porodica = "Apiaceae (štitarka)",
        medicinskoUpozorenje = "Prekomjerna konzumacija koromača može uzrokovati fototoksičnost.",
        medicinskeKoristi = listOf(MedicinskaKorist.IMMUNOSUPORT, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Pečena riba s koromačem", "Salata s koromačem"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SREDOZEMNA),
        zemljisniTipovi = listOf(Zemljište.ILOVACA, Zemljište.PJESKOVITO)
    )
)


