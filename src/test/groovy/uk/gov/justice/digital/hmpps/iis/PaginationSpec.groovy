package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import groovyx.net.http.URIBuilder
import org.openqa.selenium.Keys
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.DisclaimerPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Stepwise
class PaginationSpec extends SignOnBaseSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Search with multiple pages of results shows paging controls'() {

        when: 'I do a search that returns multiple pages of results'
        searchReturningMultipleResults()

        then: 'There are #hoaUi.pageSize results listed'
        resultItems.size() == 10

        and: 'I see a next page link'
        nextPageLink.isDisplayed()

        and: 'the previous page link is disabled'
        previousPageLabel.hasClass('inactive')

        when: 'I click next'
        nextPageLink.click()

        then: 'I see the second page'
        new URIBuilder(browser.currentUrl).query.page == '2'
        $('#paginationInput').value() == '2'

        and: 'I see a previous page link'
        previousPageLink.isDisplayed()

        when: 'I click previous'
        previousPageLink.click()

        then: 'I see the first page'
        new URIBuilder(browser.currentUrl).query.page == '1'
        $('#paginationInput').value() == '1'
    }

    def 'Search with multiple pages allows page skipping'() {

        when: 'I do a search that returns multiple pages of results'
        searchReturningMultipleResults()

        then: 'There are #hoaUi.pageSize results listed'
        resultItems.size() == 10

        when: 'I input 2 in page box'
        $('form').pageNumber = 2
        $('#paginationInput') << Keys.ENTER

        then: 'I see the second page'
        new URIBuilder(browser.currentUrl).query.page == '2'
        $('#paginationInput').value() == '2'

        when: 'I input 1 in page box'
        $('form').pageNumber = 1
        $('#paginationInput') << Keys.ENTER

        then: 'I see the first page'
        new URIBuilder(browser.currentUrl).query.page == '1'
        $('#paginationInput').value() == '1'
    }



    def searchReturningMultipleResults() {
        to SearchPage
        selectSearchOptions(['dob'])
        proceed()

        searchType('age')
        searchForm.using([
                age: '35-40'
        ])
    }

}
