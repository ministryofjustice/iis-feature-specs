package uk.gov.justice.digital.hmpps.iis

import groovyx.net.http.URIBuilder
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage


class FilteringSpec extends SignOnBaseSpec {

    def setup() {
        signIn()
    }

    def cleanup() {
        signOut()
    }

    def 'Filtering options are not shown when there are fewer than 2 results' () {

        when: 'I have performed a search that returns a single'
        performSearch([surname: 'surnamea'])

        then: 'I do not see any filtering options'
        $('#filters').isEmpty()
    }

    def 'Filtering options are displayed when there are multiple results'() {

        when: 'I have performed a search that returns multiple results'
        performSearch([surname: 'sur%'])

        then: 'I see four filtering options'
        filterControls.size() == 4

        and: 'I see the right filter option names'
        filterValues.containsAll([
                'Male',
                'Female',
                'HDC',
                'Lifer'
        ])

        and: 'No filters are active'
        inactiveFilters.size() == 4
    }

    def 'Enabling a filter reduces the number of results and active filters are indicated' (){

        when: 'I have performed a search that returns multiple results'
        performSearch([surname: 'sur%'])

        then: 'I see the results'
        resultItems.size() == 10

        when: 'I filter for female only'
        filterControls.filter('#female').click()

        then: 'I see fewer results'
        resultItems.size() == 4

        and: 'The selected filter is active'
        activeFilters.size() == 1
        activeFilters*.value().contains('Female')

        when: 'I remove the filter'
        filterControls.filter('#female').click()

        then: 'I see all the results again'
        resultItems.size() == 10

    }

    def 'If filters lead to fewer than 2 results, filter controls are still shown' () {

        when: 'I have performed a search that returns multiple results'
        performSearch([surname: 'aliasb'])

        and: 'I filter for female only'
        filterControls.filter('#female').click()

        then: 'I see only 1 result'
        resultItems.size() == 1

        and: 'I still see the filter controls'
        activeFilters.size() == 1
        activeFilters*.value().contains('Female')
    }

    def 'Filtering resets current page to page 1' (){

        when: 'I have performed a search that returns multiple results'
        performSearch([surname: 'sur%'])

        and: 'I click next'
        nextPageLink.click()

        then: 'I am on page 2'
        new URIBuilder(browser.currentUrl).query.page == '2'

        when: 'I filter for female only'
        filterControls.filter('#female').click()

        then: 'I am on page 1'
        new URIBuilder(browser.currentUrl).query.page == '1'
    }

    private void performSearch(query) {
        to SearchPage
        selectSearchOptions(['names'])
        proceed()
        searchForm.using(query)

        at SearchResultsPage
    }
}

