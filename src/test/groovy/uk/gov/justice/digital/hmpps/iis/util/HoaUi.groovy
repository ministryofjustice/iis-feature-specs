package uk.gov.justice.digital.hmpps.iis.util

class HoaUi {
    String indexUri
    int pageSize = 5

    HoaUi() {
        indexUri = (System.getenv('IIS_URI') ?: "http://localhost:3000")
    }

}
