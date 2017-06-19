package uk.gov.justice.digital.hmpps.iis

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage

@Stepwise
class FilteringSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Filtering options are displayed'() {

        when: 'I have performed a search'
        performSearch([surname: 'surnamea'])

        then: 'I see three filtering options'
        filters.size() == 3

        and: 'I see the right filter option names'
        filters.containsAll([
                'Male',
                'Female',
                'HDC'
        ])

        and: 'No filters are active'
        activeFilters == null
        inactiveFilters.size() == 3
    }

    private void performSearch(query) {
        to SearchPage
        selectSearchOptions(['names'])
        proceed()
        searchForm.using(query)

        at SearchResultsPage
    }
}
