package uk.gov.justice.digital.hmpps.iis.util

import groovy.util.logging.Slf4j


@Slf4j
class HoaUi {
    String indexUri
    String username
    String password

    HoaUi() {
        username = System.getenv('IIS_USERNAME') ?: 'todd'
        password = System.getenv('IIS_PASSWORD') ?: 'fyuw8086'

        indexUri = (System.getenv('IIS_URI') ?: "http://localhost:3000/")
        log.info("indexUri: ${indexUri}")
    }

}
