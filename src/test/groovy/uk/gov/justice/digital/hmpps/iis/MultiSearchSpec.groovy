package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.*

@Stepwise
class MultiSearchSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
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
