package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.pages.*

@Stepwise
class SearchOptionsSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Must select a search option'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I continue without selecting a search option'
        proceed()

        then: 'I see an error message'
        errors.summaryShown()
    }

    def 'Can search by ID, name or DOB'() {

        when: 'I am on the search page'
        to SearchPage

        then: 'I see the 3 search options'
        searchOptions.size == 3
    }

    @Unroll
    def '#option option leads to #option search page'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I choose a search option'
        selectSearchOptions(option)
        proceed()

        then: 'I see the corresponding search page'
        at ending

        where:
        option         | ending
        ['dob']        | DobPage
        ['names']      | NamesPage
        ['identifier'] | IdentifierPage
    }

}
