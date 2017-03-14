package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import groovyx.net.http.URIBuilder
import spock.lang.Shared
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.util.HoaUi


class PaginationSpec extends GebSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setupSpec() {
        logIn()
    }

    def cleanupSpec() {
        logOut()
    }

    def 'Search with 2 pages of results shows paging controls'() {

        when: 'I do a search that returns 2 pages of results'
        searchReturningMultipleResults()

        then: 'There are #hoaUi.pageSize results listed'
        $('div.inmate-details').size() == hoaUi.pageSize

        and: 'I see a next page link'
        $('a', text: 'Next').isDisplayed()

        and: 'the previous page link is disabled'
        $('span', text: 'Previous').hasClass('inactive')

        when: 'I click next'
        $('a', text: 'Next').click()

        then: 'I see the second page'
        new URIBuilder(browser.currentUrl).query.page == '2'
        $('#pageIndicator').text() == '2 of 2'

        and: 'I see a previous page link'
        $('a', text: 'Previous').isDisplayed()

        and: 'the next page link is disabled'
        $('span', text: 'Next').hasClass('inactive')

        when: 'I click previous'
        $('a', text: 'Previous').click()

        then: 'I see the first page'
        new URIBuilder(browser.currentUrl).query.page == '1'
        $('#pageIndicator').text() == '1 of 2'
    }

    def searchReturningMultipleResults() {
        goToSearchFor('dob')
        $('label', for: 'optAge').click()
        $('form').age = '33-38'
        $('#continue').click()
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
        go hoaUi.indexUri + 'logout'
        assert browser.currentUrl.contains('/login')
    }
}
