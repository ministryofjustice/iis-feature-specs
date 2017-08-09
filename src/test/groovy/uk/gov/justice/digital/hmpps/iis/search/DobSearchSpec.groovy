package uk.gov.justice.digital.hmpps.iis.search

import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.util.SignOnBaseSpec

@Stepwise
class DobSearchSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Dob search requires at least one input'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search with no inputs'
        searchForm.nameAge([
                dobDay  : '',
                dobMonth: '',
                dobYear : ''
        ])

        then: 'I see an error message'
        errors.summaryShown()
    }

    def 'valid dob leads to search results page'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search for a valid dob'
        searchForm.nameAge([
                dobDay  : '1',
                dobMonth: '1',
                dobYear : '1980'
        ])

        then: 'I see the number of results returned'
        waitFor(10) {
            searchResultHeading.verifyNotEmpty()
        }
    }

    def 'age search requires an age or age range'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search with no inputs'
        searchForm.nameAge([
                age: ''
        ])

        then: 'I see an error message'
        errors.summaryShown()
    }

    @Unroll
    def 'invalid age range #range rejected for age range search'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search with an invalid age range'
        searchForm.nameAge([
                age: range
        ])

        then: 'I see an error message'
        errors.summaryShown()

        where:
        range << ['30-41', '39-38', '31-31', '31--32', '31-', '-31'] // max 5 year spread, lowest first, not same
    }

    @Unroll
    def 'invalid age #age rejected for age search'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search with an invalid age'
        searchForm.nameAge([
                age: age
        ])

        then: 'I see an error message'
        errors.summaryShown()

        where:
        range << ['0', '-1', '9', '200'] // min:10 max:199
    }

    def 'valid age leads to search results page'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search for a valid age'
        searchForm.nameAge([
                age: '37'
        ])

        then: 'I see the number of results returned'
        waitFor(10) {
            searchResultHeading.verifyNotEmpty()
        }
    }

}
