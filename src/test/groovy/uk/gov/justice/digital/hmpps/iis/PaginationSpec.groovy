package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import groovyx.net.http.URIBuilder
import spock.lang.Shared
import uk.gov.justice.digital.hmpps.iis.pages.DobPage
import uk.gov.justice.digital.hmpps.iis.pages.LoginPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

class PaginationSpec extends GebSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setupSpec() {
        to LoginPage
        logIn(hoaUi.username, hoaUi.password, true)
    }

    def cleanupSpec() {
        to LogoutPage
    }

    def 'Search with 2 pages of results shows paging controls'() {

        when: 'I do a search that returns 2 pages of results'
        searchReturningMultipleResults()

        then: 'There are #hoaUi.pageSize results listed'
        resultItems.size() == hoaUi.pageSize

        and: 'I see a next page link'
        nextPageLink.isDisplayed()

        and: 'the previous page link is disabled'
        previousPageLabel.hasClass('inactive')

        when: 'I click next'
        nextPageLink.click()

        then: 'I see the second page'
        new URIBuilder(browser.currentUrl).query.page == '2'
        pageIndicator.text() == '2 of 2'

        and: 'I see a previous page link'
        previousPageLink.isDisplayed()

        and: 'the next page link is disabled'
        nextPageLabel.hasClass('inactive')

        when: 'I click previous'
        previousPageLink.click()

        then: 'I see the first page'
        new URIBuilder(browser.currentUrl).query.page == '1'
        pageIndicator.text() == '1 of 2'
    }

    def searchReturningMultipleResults() {
        to SearchPage
        selectSearchOptions(['dob'])
        proceed()

        page DobPage
        searchType('age')
        searchForm.using([
                age: '33-38'
        ])

        page SearchResultsPage
    }




}
