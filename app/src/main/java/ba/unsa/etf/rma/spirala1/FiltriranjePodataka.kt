package ba.unsa.etf.rma.spirala1

fun filtrirajMedicinski(biljke: List<Biljka>, referentnaBiljka: Biljka) : List<Biljka> {
    return biljke.filter { biljka ->
        biljka.medicinskeKoristi.any { korist ->
            referentnaBiljka.medicinskeKoristi.any { it.opis == korist.opis }
        }
    }
}

fun filtrirajKuharski(biljke: List<Biljka>, referentnaBiljka: Biljka) : List<Biljka> {
    return biljke.filter { biljka ->
        biljka.profilOkusa == referentnaBiljka.profilOkusa || biljka.jela.any { jelo ->
            referentnaBiljka.jela.contains(jelo)
        }
    }
}

fun filtrirajBotanicki(biljke: List<Biljka>, referentnaBiljka: Biljka) : List<Biljka> {
    return biljke.filter { biljka ->
        biljka.porodica == referentnaBiljka.porodica && biljka.zemljisniTipovi.any { zemljiste ->
            referentnaBiljka.zemljisniTipovi.any { it == zemljiste }
        } && biljka.klimatskiTipovi.any { klima ->
            referentnaBiljka.klimatskiTipovi.any { it == klima }
        }
    }
}