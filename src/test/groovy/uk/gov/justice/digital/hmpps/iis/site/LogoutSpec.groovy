package uk.gov.justice.digital.hmpps.iis.site

import uk.gov.justice.digital.hmpps.iis.pages.DisclaimerPage
import uk.gov.justice.digital.hmpps.iis.pages.IndexPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.SsoLoginPage
import uk.gov.justice.digital.hmpps.iis.util.SignOnBaseSpec

class LogoutSpec extends SignOnBaseSpec {

    def setupSpec() {
        signOut()
    }

    def 'No logout link shown if not logged in'() {

        when: 'I have not accepted the disclaimer thus not completed logging in'
        to IndexPage
        if (browser.isAt(SsoLoginPage)) {
            println 'Interactive sign on detected'
            signIn
        }
        at DisclaimerPage

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
        signIn()
    }
}
