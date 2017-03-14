package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Stepwise
class NameSearchSpec extends GebSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setupSpec() {
        logIn()
    }

    def cleanupSpec() {
        logOut()
    }

    def 'Name search requires at least one input'() {

        given: 'I am on the search by name page'
        goToSearchFor('names')

        when: 'I search with no inputs'
        $('form').forename = ''
        $('form').forename2 = ''
        $('form').surname = ''
        $('#continue').click()

        then: 'I see an error message'
        $("#errors").verifyNotEmpty()
    }

    def 'valid name leads to search results page'() {

        given: 'I am on the search by name page'
        goToSearchFor('names')

        when: 'I search for a valid name'
        $('form').surname = 'validname'
        $('#continue').click()

        then: 'I see the search results page'
        browser.currentUrl.contains('search/results')

        and: 'I see the number of results returned'
        with($('#contentTitle').text()) {
            contains('search returned')
            contains('results')
        }

        and: 'I see a new search link'
        $('a', href: '/search').isDisplayed()
    }

    def goToSearchFor(option) {
        // go hoaUi.indexUri + 'search/' + option
        // unable to go directly to page because the code expects search option to be in session from /search
        go hoaUi.indexUri + 'search'
        $('label', for: option).click()
        $('#continue').click()
        assert browser.currentUrl.contains(option)
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
