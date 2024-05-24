package ba.unsa.etf.rma.spirala1

val biljke = mutableListOf(
    Biljka(
        naziv = "Bosiljak (Ocimum basilicum)",
        porodica = "Lamiaceae (usnate)",
        medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
        jela = mutableListOf("Salata od paradajza", "Punjene tikvice"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = mutableListOf(Zemljište.PJESKOVITO, Zemljište.ILOVACA)
    ),
    Biljka(
        naziv = "Nana (Mentha spicata)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
        profilOkusa = ProfilOkusaBiljke.MENTA,
        jela = mutableListOf("Jogurt sa voćem", "Gulaš"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = mutableListOf(Zemljište.GLINENO, Zemljište.CRNICA)
    ),
    Biljka(
        naziv = "Kamilica (Matricaria chamomilla)",
        porodica = "Asteraceae (glavočike)",
        medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = mutableListOf("Čaj od kamilice"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = mutableListOf(Zemljište.PJESKOVITO, Zemljište.KRECNJACKO)
    ),
    Biljka(
        naziv = "Ružmarin (Rosmarinus officinalis)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPRITISKA),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = mutableListOf("Pečeno pile", "Grah","Gulaš"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljisniTipovi = mutableListOf(Zemljište.SLJUNKOVITO, Zemljište.KRECNJACKO)
    ),
    Biljka(
        naziv = "Lavanda (Lavandula angustifolia)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.IMMUNOSUPORT),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = mutableListOf("Jogurt sa voćem"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljisniTipovi = mutableListOf(Zemljište.PJESKOVITO, Zemljište.KRECNJACKO)
    ),
    Biljka(
        naziv = "Peršun (Petroselinum crispum)",
        porodica = "Apiaceae (štitarka)",
        medicinskoUpozorenje = "Osobe koje uzimaju lijekove za razrjeđivanje krvi trebaju izbjegavati peršun u velikim količinama zbog visokog udjela vitamina K.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.IMMUNOSUPORT),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = mutableListOf("Tzatziki", "Juha od povrća"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUHA),
        zemljisniTipovi = mutableListOf(Zemljište.ILOVACA, Zemljište.GLINENO)
    ),
    Biljka(
        naziv = "Majčina dušica (Thymus vulgaris)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice jer može izazvati kontrakcije maternice.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.IMMUNOSUPORT, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = mutableListOf("Pečena piletina", "Povrće sa roštilja"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.SUHA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = mutableListOf(Zemljište.KRECNJACKO, Zemljište.PJESKOVITO)
    ),
    Biljka(
        naziv = "Kopar (Anethum graveolens)",
        porodica = "Apiaceae (štitarka)",
        medicinskoUpozorenje = "Moguća je alergijska reakcija kod osoba preosjetljivih na biljke iz porodice Apiaceae.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.REGULACIJAPROBAVE, MedicinskaKorist.IMMUNOSUPORT),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = mutableListOf("Riblji specijaliteti", "Krastavci u kiselom vrhnju"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.UMJERENA, KlimatskiTip.SREDOZEMNA),
        zemljisniTipovi = mutableListOf(Zemljište.ILOVACA, Zemljište.PJESKOVITO)
    ),
    Biljka(
        naziv = "Đumbir (Zingiber officinale)",
        porodica = "Zingiberaceae (đumbirovke)",
        medicinskoUpozorenje = "Osobe koje uzimaju lijekove za razrjeđivanje krvi ili imaju problema sa žučnom kesom trebaju izbjegavati veće količine đumbira.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.REGULACIJAPROBAVE, MedicinskaKorist.IMMUNOSUPORT),
        profilOkusa = ProfilOkusaBiljke.KORIJENASTO,
        jela = mutableListOf("Đumbir čaj", "Wok s povrćem"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.TROPSKA, KlimatskiTip.SREDOZEMNA),
        zemljisniTipovi = mutableListOf(Zemljište.CRNICA, Zemljište.ILOVACA)
    ),
    Biljka(
        naziv = "Bazga (Sambucus nigra)",
        porodica = "Adoxaceae (koprivnjače)",
        medicinskoUpozorenje = "Bazga može biti toksična ako se koristi nepravilno. Cvjetovi i bobice su jestivi, ali ostatak biljke može biti otrovan.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.IMMUNOSUPORT, MedicinskaKorist.PROTUUPALNO,
            MedicinskaKorist.REGULACIJAPRITISKA),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = mutableListOf("Bazga sirup", "Pita od bazge"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = mutableListOf(Zemljište.CRNICA, Zemljište.PJESKOVITO)
    ),
    Biljka(
        naziv = "Kopriva (Urtica dioica)",
        porodica = "Urticaceae (koprive)",
        medicinskoUpozorenje = "Kopriva može izazvati alergijske reakcije kod osoba osjetljivih na nju. Treba je koristiti oprezno.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.IMMUNOSUPORT, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = mutableListOf("Kopriva juha", "Pire od koprive"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = mutableListOf(Zemljište.ILOVACA, Zemljište.CRNICA)
    ),
    Biljka(
        naziv = "Celer (Apium graveolens)",
        porodica = "Apiaceae (štitarka)",
        medicinskoUpozorenje = "Osobe koje su alergične na biljke iz porodice Apiaceae trebaju izbjegavati celer.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = mutableListOf("Celer salata", "Celer juha"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.UMJERENA, KlimatskiTip.SREDOZEMNA),
        zemljisniTipovi = mutableListOf(Zemljište.ILOVACA, Zemljište.SLJUNKOVITO)
    ),
    Biljka(
        naziv = "Matičnjak (Melissa officinalis)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Osobe koje uzimaju lijekove za hipotireozu trebaju izbjegavati matičnjak jer može usporiti funkciju štitnjače.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = mutableListOf("Matičnjak čaj", "Salata s matičnjakom"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = mutableListOf(Zemljište.ILOVACA, Zemljište.CRNICA)
    ),
    Biljka(
        naziv = "Koromač (Foeniculum vulgare)",
        porodica = "Apiaceae (štitarka)",
        medicinskoUpozorenje = "Prekomjerna konzumacija koromača može uzrokovati fototoksičnost.",
        medicinskeKoristi = mutableListOf(MedicinskaKorist.IMMUNOSUPORT, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = mutableListOf("Pečena riba s koromačem", "Salata s koromačem"),
        klimatskiTipovi = mutableListOf(KlimatskiTip.UMJERENA, KlimatskiTip.SREDOZEMNA),
        zemljisniTipovi = mutableListOf(Zemljište.ILOVACA, Zemljište.PJESKOVITO)
    )
)


