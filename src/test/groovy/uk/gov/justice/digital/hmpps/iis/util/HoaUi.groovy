package uk.gov.justice.digital.hmpps.iis.util

class HoaUi {
    String indexUri
    String username
    String password

    HoaUi() {
        username = System.getenv('IIS_USERNAME') ?: 'todd'
        password = System.getenv('IIS_PASSWORD') ?: 'fyuw8086'

        indexUri = (System.getenv('IIS_URI') ?: "http://localhost:3000/")
    }

}
