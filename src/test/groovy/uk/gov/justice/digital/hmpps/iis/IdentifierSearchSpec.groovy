package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.pages.DisclaimerPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage

@Stepwise
class IdentifierSearchSpec extends GebReportingSpec {

    @Shared
    private List<String> invalidIdentifiers = ['', '   ', '!', '*'] // html limits to 8 chars

    @Shared
    private String validIdentifier = 'AA123456'

    def setupSpec() {
        to DisclaimerPage
        continueConfirmed
    }

    def cleanupSpec() {
        to LogoutPage
    }

    @Unroll
    def 'Identifier search rejects invalid input #identifier - must be 1 to 8 alpha-numeric'() {

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
