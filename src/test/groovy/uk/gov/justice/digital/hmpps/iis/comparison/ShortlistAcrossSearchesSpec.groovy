package uk.gov.justice.digital.hmpps.iis.comparison

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.ComparisonPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.util.SignOnBaseSpec

@Stepwise
class ShortlistAcrossSearchesSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'I can add a prisoner to the shortlist, do a new search, add another, and compare them'(){

        when: 'I perform a search'
        performSearch([surname: 'sur%'])

        and: 'I add a prisoner (SURNAMEA) to the shortlist'
        addToShortlistLinks[3].click()

        and: 'I do a new search'
        searchForm.newSearch
        searchForm.nameAge([surname: 'sur%'])

        and: 'I add a different prisoner (SURNAMEC) to the shortlist'
        addToShortlistLinks[5].click()

        then: 'The shortlist now has 2 entries'
        tabs.comparisonTab.text().contains('Comparison (2)')

        when: 'I view the comparison'
        tabs.comparisonTabLink.click()

        then: 'I see the two prisoners from different searches'
        at ComparisonPage
        comparisonSections[0].text().contains('SURNAMEA')
        comparisonSections[1].text().contains('SURNAMEC')
    }



    private void performSearch(query) {
        to SearchPage
        searchForm.nameAge(query)
        at SearchResultsPage
    }
}
