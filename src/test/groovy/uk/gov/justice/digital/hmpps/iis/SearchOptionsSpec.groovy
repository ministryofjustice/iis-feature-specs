package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Stepwise
class SearchOptionsSpec extends GebSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setupSpec() {
        logIn()
    }

    def cleanupSpec() {
        logOut()
    }

    def 'Must select a search option'() {

        given: 'I am on the search page'
        goToSearch()

        when: 'I continue without selecting a search option'
        $('#continue').click()

        then: 'I see an error message'
        $("#errors").verifyNotEmpty()
    }

    def 'Can search by ID, name or DOB'() {

        when: 'I am on the search page'
        goToSearch()

        then: 'I see the 3 search options'
        $('form').opt.size == 3
    }

    @Unroll
    def '#option option leads to #searchPage search page'() {

        given: 'I am on the search page'
        goToSearch()

        when: 'I choose a search option'
        $('label', for: option).click()
        $('#continue').click()

        then: 'I see the corresponding search page'
        browser.currentUrl.contains('search/' + searchPage)

        where:
        option        | searchPage
        'dob'        | 'dob'
        'names'      | 'names'
        'identifier' | 'identifier'
    }

    def goToSearch() {
        go hoaUi.indexUri + 'search'
        assert browser.currentUrl.contains('/search')
    }

    def logIn() {
        go hoaUi.indexUri
        assert browser.currentUrl.contains('/login')
        $('form').loginId = hoaUi.username
        $('form').pwd = hoaUi.password
        $('label', for: 'disclaimer').click()
        $('#signin').click()
    }

    def logOut() {
        go hoaUi.indexUri + 'logOut'
        assert browser.currentUrl.contains('/login')
    }
}
