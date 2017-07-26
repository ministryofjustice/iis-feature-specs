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

        when: 'I performed a search'
        performSearch([surname: 'surnamea'])

        then: 'I see the number of results returned'
        searchResultHeading.text().contains('1')

        and: 'There are the right number of results'
        resultItems.size() == 1

        and: 'I see a new search link'
        newSearchLink.isDisplayed()

        and: 'I see the prison number for results'
        firstResultItem.find('.prisonNumber')[0].verifyNotEmpty()
    }

    def 'edit form can be accessed for search inputs'() {

        when: 'I performed a search'
        performSearch([surname: 'surnamea'])

        then: 'I see the inputs for each item that I have searched'
        searchTerms.size() == 1

        and: 'The search that I made is displayed'
        firstSearchTerm.text().contains('Surnamea')

        and: 'The change link is displayed'
        firstSearchTermLink.text().equals('change')

        and: 'The change link points to the edit page with correct query'
        firstSearchTermLink.attr('href').contains('search/edit?formItem=name')
    }

    private void performSearch(query) {
        to SearchPage
        selectSearchOptions(['names'])
        proceed()
        searchForm.using(query)

        at SearchResultsPage
    }
}
