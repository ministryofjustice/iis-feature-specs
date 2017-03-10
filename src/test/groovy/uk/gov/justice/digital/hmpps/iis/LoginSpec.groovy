package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import groovy.util.logging.Slf4j
import spock.lang.Shared
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Slf4j
class LoginSpec extends GebSpec {

    private static final String disclaimerText =
            "Access and use by you of this application to access NOMS data constitutes acceptance of any relevant " +
                    "information security policies defined by your employer. Your employer may monitor and record use of this " +
                    "application. Inappropriate use of this application or the data within may lead to disciplinary action and or " +
                    "legal proceedings."

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def 'Redirect to login page if not logged in'() {

        when: 'I open the index page'
        go hoaUi.iisUri

        then: 'I see the login page'
        title == 'Enter user id and password'
    }


    def 'Show disclaimer on login page'() {

        when: 'I open the login page'
        go hoaUi.iisUri + 'login'

        then: 'I see the login page title'
        title == 'Enter user id and password'

        and: 'I see the login disclaimer'
        $("#disclaimerText").text() == disclaimerText
    }
}
