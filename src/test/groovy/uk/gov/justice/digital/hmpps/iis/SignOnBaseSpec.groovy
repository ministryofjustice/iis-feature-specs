package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import uk.gov.justice.digital.hmpps.iis.pages.DisclaimerPage
import uk.gov.justice.digital.hmpps.iis.pages.IndexPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.SsoLoginPage
import uk.gov.justice.digital.hmpps.iis.pages.SsoProfilePage


class SignOnBaseSpec extends GebReportingSpec{

    def signIn() {
        to IndexPage
        if (browser.isAt(SsoLoginPage)) {
            println 'Interactive sign on detected'
            signIn
        }
        at DisclaimerPage
        continueConfirmed
    }

    def signOut() {
        to LogoutPage
        if (browser.isAt(SsoProfilePage)) {
            println 'Interactive sign out detected'
            signOut
        }
    }
}
