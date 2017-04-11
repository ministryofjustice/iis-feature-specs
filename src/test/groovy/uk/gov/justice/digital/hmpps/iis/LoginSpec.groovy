package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.DisclaimerPage
import uk.gov.justice.digital.hmpps.iis.pages.IndexPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SsoLoginPage
import uk.gov.justice.digital.hmpps.iis.pages.SsoProfilePage

@Stepwise
class LoginSpec extends GebReportingSpec {

    def setupSpec() {
        logout()
    }

    def cleanupSpec() {
        logout()
    }

    def 'Redirect to disclaimer page if not logged in'() {

        when:
        to IndexPage

        then:
        if (browser.isAt(SsoLoginPage)) {
            println 'Interactive sign on detected'
            signIn
        }

        then:
        at DisclaimerPage
    }

    def 'Show disclaimer on disclaimer page'() {

        when: 'I arrive at the disclaimer page'
        to DisclaimerPage

        then: 'I see the login disclaimer confirmation message'
        disclaimerConfirmation.isDisplayed()
    }

    def 'Mandatory to confirm disclaimer before logging in'() {

        when: 'I arrive at the disclaimer page'
        to DisclaimerPage

        and: 'I continue without confirming the disclaimer'
        continueUnconfirmed

        then: 'I return to the disclaimer page'
        at DisclaimerPage

        and: 'I see an error message'
        errors.summaryShown()

        and: 'A message linked to the disclaimer input'
        errors.hasLinkWithText(browser.currentUrl + '#disclaimer', 'You must confirm that you understand the disclaimer')
    }

    def 'Accepting disclaimer leads to search page'() {

        when: 'I arrive at the disclaimer page'
        to DisclaimerPage

        and: 'I accept the disclaimer and continue'
        continueConfirmed

        then: 'I see the search page'
        at SearchPage
    }

    def logout() {
        to LogoutPage
        if (browser.isAt(SsoProfilePage)) {
            println 'Interactive sign out detected'
            signOut
        }
    }
}
