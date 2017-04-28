package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.DisclaimerPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.NamesPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage

@Stepwise
class NameSearchSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Name search requires at least one input'() {

        given: 'I am on the search by name page'
        toNamesPage()

        when: 'I search with no inputs'
        searchForm.using([
                forename : '',
                forename2: '',
                surname  : ''
        ])

        then: 'I see an error message'
        errors.summaryShown()
    }

    def 'valid name leads to search results page'() {

        given: 'I am on the search by name page'
        toNamesPage()

        when: 'I search for a valid name'
        searchForm.using([
                surname: 'surnamea'
        ])

        then: 'I see the search results page'
        at SearchResultsPage

        and: 'I see the number of results returned'
        searchResultHeading.text().contains('1 result')

        and: 'I see a new search link'
        newSearchLink.isDisplayed()
    }

    def toNamesPage() {
        to SearchPage
        selectSearchOptions(['names'])
        proceed()
    }
}
