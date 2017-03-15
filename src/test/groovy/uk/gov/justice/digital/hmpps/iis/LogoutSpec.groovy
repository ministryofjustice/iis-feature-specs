package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import geb.spock.GebSpec
import spock.lang.Shared
import uk.gov.justice.digital.hmpps.iis.pages.IndexPage
import uk.gov.justice.digital.hmpps.iis.pages.LoginPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

class LogoutSpec extends GebReportingSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def 'No logout link shown if not logged in'() {

        when: 'I open the login page'
        to LoginPage

        then: 'I do not see the logout link'
        header.logoutLinkDisplayed == false
    }

    def 'Logout link shown when logged in'() {

        given:
        loggedIn()

        when: 'I open the index page'
        to IndexPage

        then: 'I see the logout link'
        header.logoutLink.isDisplayed()
    }

    def 'logout returns to login page'(){

        given:
        loggedIn()

        when: 'I log out'
        via LogoutPage

        then: 'I see the login page'
        at LoginPage
    }

    def loggedIn() {
        to LoginPage
        logIn(hoaUi.username, hoaUi.password, true)
    }
}
