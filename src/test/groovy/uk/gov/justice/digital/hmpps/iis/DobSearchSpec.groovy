package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Stepwise
class DobSearchSpec extends GebSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setupSpec() {
        logIn()
    }

    def cleanupSpec() {
        logOut()
    }

    def 'Dob search requires at least one input'() {

        given: 'I am on the search by name page'
        goToSearchFor('dob')

        when: 'I search with no inputs'
        $('form').dobDay = ''
        $('form').dobMonth = ''
        $('form').dobYear = ''
        $('#continue').click()

        then: 'I see an error message'
        $("#errors").verifyNotEmpty()

    }

    def 'valid dob leads to search results page'() {

        given: 'I am on the search by name page'
        goToSearchFor('dob')

        when: 'I search for a valid dob'
        $('form').dobDay = 1
        $('form').dobMonth = 1
        $('form').dobYear = 1970
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

    def 'age search requires an age or age range'(){

        given: 'I am on the search by name page'
        goToSearchFor('dob')

        and: 'I choose age search'
        $('label', for: 'optAge').click()

        when: 'I search with no inputs'
        $('form').age = ''
        $('#continue').click()

        then: 'I see an error message'
        $("#errors").verifyNotEmpty()
    }

    @Unroll
    def 'invalid age range #range rejected for age range search'(){

        given: 'I am on the search by name page'
        goToSearchFor('dob')

        and: 'I choose age search'
        $('label', for: 'optAge').click()

        when: 'I search with an invalid age range'
        $('form').age = range
        $('#continue').click()

        then: 'I see an error message'
        $("#errors").verifyNotEmpty()

        where:
        range << ['30-100', '39-38', '31-31', '31--32', '31-', '-31']
    }


    def 'valid age leads to search results page'() {

        given: 'I am on the search by name page'
        goToSearchFor('dob')

        and: 'I choose age search'
        $('label', for: 'optAge').click()

        when: 'I search for a valid age'
        $('form').age = 30
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
