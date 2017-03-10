package uk.gov.justice.digital.hmpps.iis.util

import groovy.util.logging.Slf4j


@Slf4j
class HoaUi {
    String iisUri
    String username
    String password

    HoaUi() {
        username = System.getenv('IIS_USERNAME') ?: 'user'
        password = System.getenv('IIS_PASSWORD') ?: 'password'

        iisUri = (System.getenv('IIS_URI') ?: "http://localhost:3000/")
        log.info("iisUri: ${iisUri}")
    }

}
