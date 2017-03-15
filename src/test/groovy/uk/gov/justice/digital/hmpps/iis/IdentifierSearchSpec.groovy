package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import geb.spock.GebSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.pages.*
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Stepwise
class IdentifierSearchSpec extends GebReportingSpec {

    @Shared
    private List<String> invalidIdentifiers = ['', '   ', 'AAA', 'AA00000', 'AA00000A']

    @Shared
    private String validIdentifier = 'AA000000'

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setupSpec() {
        to LoginPage
        logIn(hoaUi.username, hoaUi.password, true)
    }

    def cleanupSpec() {
        to LogoutPage
    }

    @Unroll
    def 'Identifier search rejects invalid input #identifier'() {

        given: 'I am on the search by identifier page'
        toIdentifierPage()

        when: 'I search for an identifier'
        searchForm.using([
                prisonNumber: identifier
        ])

        then: 'I see an error message'
        errors.summaryShown()

        where:
        identifier << invalidIdentifiers
    }

    def 'valid identifier leads to search results page'() {

        given: 'I am on the search by identifier page'
        toIdentifierPage()

        when: 'I search for a valid identifier'
        searchForm.using([
                prisonNumber: validIdentifier
        ])

        then: 'I see the search results page'
        at SearchResultsPage

        and: 'I see the number of results returned'
        searchResultHeading.verifyNotEmpty()

        and: 'I see a new search link'
        newSearchLink.isDisplayed()
    }

    private void toIdentifierPage() {
        to SearchPage
        selectSearchOptions(['identifier'])
        proceed()
    }
}
