package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import uk.gov.justice.digital.hmpps.iis.pages.*

class MultiSearchSpec extends GebReportingSpec {

    def setup() {
        to DisclaimerPage
        continueConfirmed
    }

    def cleanup() {
        to LogoutPage
    }

    def 'Select all search types shows all search pages in order'() {

        given: 'I am on the search page'
        to SearchPage

        and: 'I select all search types'
        selectSearchOptions(['identifier', 'names', 'dob'])

        when: 'I continue'
        proceed()

        then: 'I see the ID search page'
        at IdentifierPage

        when: 'when I continue with valid inputs'
        searchForm.using([
                prisonNumber: 'AA123456'
        ])

        then: 'I see the name search'
        at NamesPage

        when: 'when I continue with valid inputs'
        searchForm.using([
                surname: 'surname'
        ])

        then: 'I see the dob search'
        at DobPage
    }

}
