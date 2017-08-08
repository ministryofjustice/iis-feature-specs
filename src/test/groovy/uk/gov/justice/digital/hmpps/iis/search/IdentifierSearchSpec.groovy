package uk.gov.justice.digital.hmpps.iis.search

import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.util.SignOnBaseSpec

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

        given: 'I am on the search page'
        to SearchPage

        when: 'I search for a prison number'
        searchForm.identifiers([
                prisonNumber: 'bad-format'
        ])

        then: 'I see the search results page'
        at SearchResultsPage
    }

    def 'Identifier search rejects when no type of identifier supplied'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search without any identifier'
        searchForm.identifiers([
                prisonNumber: '',
                pncNumber: '',
                croNumber: ''
        ])

        then: 'I see an error message'
        errors.summaryShown()
    }

    def 'valid prison number leads to search results page'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search for a valid prison number'
        searchForm.identifiers([
                prisonNumber: validPrisonNumber
        ])

        then: 'I see the number of results returned'
        searchResultHeading.text().contains('1')
    }

    def 'any PNC is allowed' () {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search for a valid pnc number'
        searchForm.identifiers([
                pncNumber: 'Anything*^67'
        ])

        then: 'I see the search results page'
        searchResultHeading.verifyNotEmpty()
    }

    def 'any CRO is allowed' () {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search for a valid cro number'
        searchForm.identifiers([
                croNumber: 'Anything*^67'
        ])

        then: 'I see the search results page'
        at SearchResultsPage
    }

    def 'PNC/CRO search handles case where multiple person refs with same PNC/CRO'(){

        given: 'I am on the search page'
        to SearchPage

        when: 'I search for a CRO that has 2 associated person refs'
        searchForm.identifiers([
                croNumber: '012345/CR0'
        ])

        then: 'I see the search results page'
        searchResultHeading.verifyNotEmpty()

        and: 'There are 2 results'
        resultItems.size() == 2
    }
}
