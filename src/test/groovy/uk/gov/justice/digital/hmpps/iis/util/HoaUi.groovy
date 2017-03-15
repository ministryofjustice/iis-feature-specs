package uk.gov.justice.digital.hmpps.iis.util

class HoaUi {
    String indexUri
    String username
    String password

    int pageSize = 5

    HoaUi() {
        username = System.getenv('IIS_USER') ?: 'user'
        password = System.getenv('IIS_PASSWORD') ?: 'password'

        indexUri = (System.getenv('IIS_URI') ?: "http://localhost:3000")
    }

}
