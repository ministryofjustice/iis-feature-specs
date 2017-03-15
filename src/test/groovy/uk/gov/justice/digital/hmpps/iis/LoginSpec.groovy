package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import geb.spock.GebSpec
import spock.lang.Shared
import uk.gov.justice.digital.hmpps.iis.pages.IndexPage
import uk.gov.justice.digital.hmpps.iis.pages.LoginPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

class LoginSpec extends GebReportingSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def 'Redirect to login page if not logged in'() {

        when:
        via IndexPage

        then:
        at LoginPage
    }

    def 'Show disclaimer on login page'() {

        when: 'I open the login page'
        to LoginPage

        then: 'I see the login page'
        at LoginPage

        and: 'I see the login disclaimer confirmation message'
        disclaimerConfirmation.isDisplayed()
    }

    def 'Username is mandatory for login'() {

        when: 'I open the login page'
        to LoginPage

        and: 'I sign in without supplying a user id'
        logIn('', hoaUi.password, true)

        then: 'I see an error message'
        errors.summaryShown()

        and: 'A message linked to the user id input'
        errors.hasLink(browser.currentUrl + '#loginId')
    }

    def 'Correct password is mandatory for login'() {

        when: 'I open the login page'
        to LoginPage

        and: 'I sign in without supplying the right password'
        logIn(hoaUi.username, 'not-the-right-password', true)

        then: 'I see an error message'
        errors.summaryShown()

        and: 'A message linked to the user id input'
        errors.hasLink(browser.currentUrl + '#loginId')
    }

    def 'Mandatory to confirm disclaimer before logging in'() {

        when: 'I open the login page'
        to LoginPage

        and: 'I sign in without confirming the disclaimer'
        logIn(hoaUi.username, hoaUi.password, false)

        then: 'I see an error message'
        errors.summaryShown()

        and: 'A message linked to the disclaimer input'
        errors.hasLinkWithText(browser.currentUrl + '#disclaimer', 'You must confirm that you understand the disclaimer')
    }

    def 'Successful login leads to search page'() {

        when: 'I open the login page'
        to LoginPage

        and: 'I sign in'
        logIn(hoaUi.username, hoaUi.password, true)

        then: 'I see the search page'
        at SearchPage
    }
}
