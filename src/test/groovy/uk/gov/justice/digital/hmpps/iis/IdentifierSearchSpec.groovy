package uk.gov.justice.digital.hmpps.iis

import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage

@Stepwise
class IdentifierSearchSpec extends SignOnBaseSpec {

    @Shared
    private String validPrisonNumber = 'AB111111'

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Identifier search does not validate prison number format'() {

        given: 'I am on the search by identifier page'
        toIdentifierPage()

        when: 'I search for a prison number'
        searchForm.using([
                prisonNumber: 'bad-format'
        ])

        then: 'I see the search results page'
        at SearchResultsPage
    }

    def 'Identifier search rejects when no type of identifier supplied'() {

        given: 'I am on the search by identifier page'
        toIdentifierPage()

        when: 'I search without any identifier'
        searchForm.using([
                prisonNumber: '',
                pncNumber: '',
                croNumber: ''
        ])

        then: 'I see an error message'
        errors.summaryShown()
    }

    def 'valid prison number leads to search results page'() {

        given: 'I am on the search by identifier page'
        toIdentifierPage()

        when: 'I search for a valid prison number'
        searchForm.using([
                prisonNumber: validPrisonNumber
        ])

        then: 'I see the search results page'
        at SearchResultsPage

        and: 'I see the number of results returned'
        searchResultHeading.text().contains('3 results')

        and: 'I see a new search link'
        newSearchLink.isDisplayed()
    }

    def 'any PNC is allowed' () {

        given: 'I am on the search by identifier page'
        toIdentifierPage()

        when: 'I search for a valid pnc number'
        searchForm.using([
                pncNumber: 'Anything*^67'
        ])

        then: 'I see the search results page'
        at SearchResultsPage
    }

    def 'any CRO is allowed' () {

        given: 'I am on the search by identifier page'
        toIdentifierPage()

        when: 'I search for a valid cro number'
        searchForm.using([
                croNumber: 'Anything*^67'
        ])

        then: 'I see the search results page'
        at SearchResultsPage
    }

    def 'PNC/CRO search handles case where multiple person refs with same PNC/CRO'(){

        given: 'I am on the search by identifier page'
        toIdentifierPage()

        when: 'I search for a CRO that has 2 associated person refs'
        searchForm.using([
                croNumber: '012345/CR0'
        ])

        then: 'I see the search results page'
        at SearchResultsPage

        and: 'There are four results (2 aliases also shown)'
        resultItems.size() == 4

    }

    private void toIdentifierPage() {
        to SearchPage
        selectSearchOptions(['identifier'])
        proceed()
    }
}
