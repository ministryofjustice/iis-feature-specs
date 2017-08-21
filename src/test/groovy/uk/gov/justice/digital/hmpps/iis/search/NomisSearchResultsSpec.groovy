package uk.gov.justice.digital.hmpps.iis.search

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.util.SignOnBaseSpec

@Stepwise
class NomisSearchResultsSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'no nomis tab until a search is performed'() {

        when: 'I am on the search page'
        to SearchPage

        then: 'I do not see a NOMIS tab'
        !nomisTab.isPresent()
    }

    def 'valid name leads to HPA search results page'() {

        when: 'I search for a valid name'
        performSearch([surname: 'surnamea'])

        then: 'The heading states that these are HPA results'
        searchResultHeading.text().contains('HPA')
    }

    def 'can switch between HPA and NOMIS results for the search'() {

        when: 'I am on the HPA search results page'
        performSearch([surname: 'surnamea'])

        and: 'I click the NOMIS tab'
        nomisTab.click()

        then: 'The heading states that these are NOMIS results'
        searchResultHeading.text().contains('NOMIS')

        when: 'I click the HPA tab'
        hpaTab.click()

        then: 'The heading states that these are HPA results'
        searchResultHeading.text().contains('HPA')
    }

    def 'new search when on NOMIS tab returns to HPA tab'(){

        when: 'I am on the HPA search results page'
        performSearch([surname: 'surnamea'])

        and: 'I click the NOMIS tab'
        nomisTab.click()

        and: 'I do a new search'
        performSearch([surname: 'surnamea'])

        then: 'The heading states that these are HPA results'
        searchResultHeading.text().contains('HPA')
    }

    def 'search with only prison number gives 0 results on NOMIS tab' (){

        when: 'I search by prison number'
        performIdSearch([prisonNumber: 'AB111111'])

        and: 'I click the NOMIS tab'
        nomisTab.click()

        then: 'There are 0 results'
        resultItems.size() == 0
    }

    private void performSearch(query) {
        to SearchPage
        searchForm.nameAge(query)
        at SearchResultsPage
    }

    private void performIdSearch(query) {
        to SearchPage
        searchForm.identifiers(query)
        at SearchResultsPage
    }
}
