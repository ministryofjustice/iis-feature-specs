package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import uk.gov.justice.digital.hmpps.iis.pages.DisclaimerPage
import uk.gov.justice.digital.hmpps.iis.pages.IndexPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage

class LogoutSpec extends GebReportingSpec {

    def cleanup() {
        to LogoutPage
    }

    def 'No logout link shown if not logged in'() {

        when: 'I have not completed logging in'
        to DisclaimerPage

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

    def 'logout redirects to SSO profile'(){

        given:
        loggedIn()

        when: 'I log out'
        via LogoutPage

        then: 'I see the SSO profile page'
        browser.currentUrl.contains('/profile')
    }

    def loggedIn() {
        to DisclaimerPage
        continueConfirmed
    }
}
