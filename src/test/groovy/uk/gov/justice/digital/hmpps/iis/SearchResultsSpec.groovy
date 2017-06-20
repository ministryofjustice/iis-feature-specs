package uk.gov.justice.digital.hmpps.iis

import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage

@Stepwise
class SearchResultsSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'valid dob leads to search results page'() {

        when: 'I have performed a search'
        performSearch([surname: 'surnamea'])

        then: 'I see the number of results returned'
        searchResultHeading.text().contains('1 result')

        and: 'There are the right number of results'
        resultItems.size() == 1

        and: 'I see a new search link'
        newSearchLink.isDisplayed()

        and: 'I see the prison number for results'
        firstResultItem.find('.prisonNumber')[0].verifyNotEmpty()
    }

    private void performSearch(query) {
        to SearchPage
        selectSearchOptions(['names'])
        proceed()
        searchForm.using(query)

        at SearchResultsPage
    }
}
