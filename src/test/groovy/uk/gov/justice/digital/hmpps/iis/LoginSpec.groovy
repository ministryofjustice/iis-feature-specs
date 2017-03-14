package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import spock.lang.Shared
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

class LoginSpec extends GebSpec {

    @Shared
    private String disclaimerConfirmation = 'I confirm that I understand'

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def 'Redirect to login page if not logged in'() {

        when: 'I open the index page'
        go hoaUi.indexUri

        then: 'I see the login page'
        browser.currentUrl.contains('/login')
        title == 'Enter user id and password'
    }

    def 'Show disclaimer on login page'() {

        when: 'I open the login page'
        go hoaUi.indexUri + 'login'

        then: 'I see the login page'
        browser.currentUrl.contains('/login')
        title == 'Enter user id and password'

        and: 'I see the login disclaimer confirmation message'
        $("#disclaimerConfirmation").text() == disclaimerConfirmation
    }

    def 'Username is mandatory for login'() {

        when: 'I open the login page'
        go hoaUi.indexUri + 'login'

        and: 'I sign in without supplying a user id'
        $('form').loginId = ''
        $('form').pwd = hoaUi.password
        $('label', for: 'disclaimer').click()
        $('#signin').click()

        then: 'I see an error message'
        $("#errors").verifyNotEmpty()

        and: 'A message linked to the user id input'
        $('a', href: '#loginId').isDisplayed()
    }

    def 'Password is mandatory for login'() {

        when: 'I open the login page'
        go hoaUi.indexUri + 'login'

        and: 'I sign in without supplying a user id'
        $('form').loginId = hoaUi.username
        $('form').pwd = 'not-the-right-password'
        $('label', for: 'disclaimer').click()
        $('#signin').click()

        then: 'I see an error message'
        $("#errors").verifyNotEmpty()

        and: 'A message linked to the user id input'
        $('a', href: '#loginId').isDisplayed()
    }

    def 'Mandatory to confirm disclaimer before logging in'() {

        when: 'I open the login page'
        go hoaUi.indexUri + 'login'

        and: 'I sign in without confirming the disclaimer'
        $('form').loginId = hoaUi.username
        $('form').pwd = hoaUi.password
        $('#signin').click()

        then: 'I see an error message'
        $("#errors").verifyNotEmpty()

        and: 'A message linked to the disclaimer input'
        $('a', href: '#disclaimer').text() == 'You must confirm that you understand the disclaimer'
    }

    def 'Successful login leads to search page'() {

        when: 'I open the login page'
        go hoaUi.indexUri + 'login'

        and: 'I sign in'
        $('form').loginId = hoaUi.username
        $('form').pwd = hoaUi.password
        $('label', for: 'disclaimer').click()
        $('#signin').click()

        then: 'I see the search page'
        title == 'What information do you have on the inmate?'
        browser.currentUrl.contains('/search')
    }
}
