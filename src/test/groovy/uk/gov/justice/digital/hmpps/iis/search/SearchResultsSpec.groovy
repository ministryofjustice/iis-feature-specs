package uk.gov.justice.digital.hmpps.iis.search

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.util.SignOnBaseSpec

@Stepwise
class SearchResultsSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'valid search leads to search results page'() {

        when: 'I perform a search'
        performSearch([surname: 'surnamea'])

        then: 'I see the number of results returned'
        searchResultHeading.text().contains('1')

        and: 'There are the right number of results'
        resultItems.size() == 1

        and: 'I see the prison number for results'
        firstResultItem.find('.prisonNumber')[0].verifyNotEmpty()
    }

    private void performSearch(query) {
        to SearchPage
        searchForm.nameAge(query)
        at SearchResultsPage
    }
}
