package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import spock.lang.Shared
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

class LogoutSpec extends GebSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def 'No logout link shown if not logged in'() {

        when: 'I open the index page'
        go hoaUi.indexUri

        then: 'I do not see the logout link'
        !$('a', href: '/logout').isDisplayed()
    }

    def 'Logout link shown when logged in'() {

        given:
        logIn()

        when: 'I open the index page'
        go hoaUi.indexUri

        then: 'I see the logout link'
        $('a', href: '/logout').isDisplayed()
    }

    def 'logout returns to login page'(){

        given:
        logIn()

        when: 'I log out'
        go hoaUi.indexUri + 'logout'

        then: 'I see the login page'
        browser.currentUrl.contains('/login')
        title == 'Enter user id and password'
    }

    def logIn() {
        go hoaUi.indexUri
        assert browser.currentUrl.contains('/login')
        $('form').loginId = hoaUi.username
        $('form').pwd = hoaUi.password
        $('label', for: 'disclaimer').click()
        $('#signin').click()
    }
}
