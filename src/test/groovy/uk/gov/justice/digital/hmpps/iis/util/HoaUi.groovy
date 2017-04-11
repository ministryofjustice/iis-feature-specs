package uk.gov.justice.digital.hmpps.iis.util

class HoaUi {
    String indexUri
    String user
    String password
    String signonUri
    String profileUri

    int pageSize = 5

    HoaUi() {
        indexUri = (System.getenv('IIS_URI') ?: 'http://localhost:3000')
        user = (System.getenv('IIS_USER') ?: 'user')
        password = (System.getenv('IIS_PASSWORD') ?: 'password')
        signonUri = (System.getenv('SIGNON_URI') ?: 'http://localhost:3001/oauth/authorize')
        profileUri = (System.getenv('PROFILE_URI') ?: 'http://localhost:3001/profile')
    }

}
