package uk.gov.justice.digital.hmpps.iis.comparison

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.ComparisonPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.util.SignOnBaseSpec

@Stepwise
class ShortlistSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'I see details for each prisoner in the shortlist in the order added'(){

        given: 'I have filled the shortlist in a certain order'
        fillShortlist([5,3,4])

        when: 'I view the comparison'
        tabs.comparisonTab.find('a').click()

        then: 'I see the comparison page'
        at ComparisonPage

        and: 'I see the shortlist size in the tab title'
        tabs.comparisonTab.text().contains('Comparison (3)')

        and: 'I see 3 comparison sections'
        comparisonSections.size() == 3

        and: 'I see 3 remove from shortlist links'
        removeFromShortlistLinks.size() == 3

        and: 'I see the prisoners in the same order they were added'
        comparisonSections[0].text().contains('SURNAMEC')
        comparisonSections[1].text().contains('SURNAMEA')
        comparisonSections[2].text().contains('SURNAMEB')

        when: 'I remove a prisoner'
        removeFromShortlistLinks[1].click()

        then: 'The remaining list is still in the added order'
        comparisonSections[0].text().contains('SURNAMEC')
        comparisonSections[1].text().contains('SURNAMEB')
    }

    def 'When I remove a prisoner from the shortlist and return to search results the shortlist is still correct'() {

        given: 'I fill the shortlist'
        fillShortlist()

        when: 'I view the comparison'
        tabs.comparisonTabLink.click()

        then: 'I see the comparison page'
        at ComparisonPage

        when: 'I remove a prisoner'
        removeFromShortlistLinks[0].click()

        and: 'I return to the search results'
        tabs.hpaTabLink.click();

        then: 'I see an updated notice'
        at SearchResultsPage
        shortlistNotice.text().contains('Prisoner added to the shortlist')

        and: 'I see the reduced shortlist size'
        tabs.comparisonTab.text().contains('Comparison (2)')
    }

    def 'When I remove the last prisoner from the shortlist I return to the search results page'() {

        given: 'I fill the shortlist'
        fillShortlist()

        when: 'I view the comparison'
        tabs.comparisonTabLink.click()

        then: 'I see the comparison page'
        at ComparisonPage

        when: 'I remove a prisoner'
        removeFromShortlistLinks[0].click()

        then: 'I see 2 comparison sections'
        comparisonSections.size() == 2

        when: 'I remove the next two'
        removeFromShortlistLinks[0].click()
        removeFromShortlistLinks[0].click()

        then: 'I see the search results page'
        at SearchResultsPage
    }

    private void fillShortlist(resultItems = [0,1,2]){
        performSearch([surname: 'sur%']);
        addToShortlistLinks[resultItems[0]].click()
        addToShortlistLinks[resultItems[1]].click()
        addToShortlistLinks[resultItems[2]].click()
    }

    private void performSearch(query) {
        to SearchPage
        searchForm.nameAge(query)
        at SearchResultsPage
    }
}
