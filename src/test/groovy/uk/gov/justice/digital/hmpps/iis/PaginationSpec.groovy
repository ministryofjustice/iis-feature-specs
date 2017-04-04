package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import groovyx.net.http.URIBuilder
import spock.lang.Ignore
import spock.lang.Shared
import uk.gov.justice.digital.hmpps.iis.pages.DisclaimerPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

class PaginationSpec extends GebReportingSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setup() {
        to DisclaimerPage
        continueConfirmed
    }

    def cleanup() {
        to LogoutPage
    }

    //@Ignore("Need to make PhantomJS work with the GDS javascript that shows/hides elements when clicking radio button")
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
        pageIndicator.text().startsWith('2 of ')

        and: 'I see a previous page link'
        previousPageLink.isDisplayed()

        and: 'the next page link is disabled'
        // todo need predefined data to control exact page count
        //  nextPageLabel.hasClass('inactive')

        when: 'I click previous'
        previousPageLink.click()

        then: 'I see the first page'
        new URIBuilder(browser.currentUrl).query.page == '1'
        pageIndicator.text().startsWith('1 of ')
    }

    def searchReturningMultipleResults() {
        to SearchPage
        selectSearchOptions(['dob'])
        proceed()

        searchType('age')
        searchForm.using([
                age: '33-38'
        ])
    }

}
