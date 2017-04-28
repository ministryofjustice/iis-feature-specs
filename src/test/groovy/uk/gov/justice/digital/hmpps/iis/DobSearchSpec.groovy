package uk.gov.justice.digital.hmpps.iis

import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage

@Stepwise
class DobSearchSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Dob search requires at least one input'() {

        given: 'At dob search page'
        toDobPage()

        when: 'I search with no inputs'
        searchForm.using([
                dobDay  : '',
                dobMonth: '',
                dobYear : ''
        ])

        then: 'I see an error message'
        errors.summaryShown()
    }

    def 'valid dob leads to search results page'() {

        given: 'At dob search page'
        toDobPage()

        when: 'I search for a valid dob'
        searchForm.using([
                dobDay  : '1',
                dobMonth: '1',
                dobYear : '1980'
        ])

        then: 'I see the search results page'
        at SearchResultsPage

        and: 'I see the number of results returned'
        searchResultHeading.verifyNotEmpty()

        and: 'I see a new search link'
        newSearchLink.isDisplayed()
    }

    def 'age search requires an age or age range'() {

        given: 'At dob search page'
        toDobPage()

        when: 'I choose age search'
        searchType('age')

        and: 'I search with no inputs'
        searchForm.using([
                age: ''
        ])

        then: 'I see an error message'
        errors.summaryShown()
    }

    @Unroll
    def 'invalid age range #range rejected for age range search'() {

        given: 'At dob search page'
        toDobPage()

        when: 'I choose age search'
        searchType('age')

        and: 'I search with an invalid age range'
        searchForm.using([
                age: range
        ])

        then: 'I see an error message'
        errors.summaryShown()

        where:
        range << ['30-36', '39-38', '31-31', '31--32', '31-', '-31'] // max 5 year spread, lowest first, not same
    }

    @Unroll
    def 'invalid age #age rejected for age search'() {

        given: 'At dob search page'
        toDobPage()

        when: 'I choose age search'
        searchType('age')

        and: 'I search with an invalid age'
        searchForm.using([
                age: age
        ])

        then: 'I see an error message'
        errors.summaryShown()

        where:
        range << ['0', '-1', '9', '200'] // min:10 max:199
    }

    def 'valid age leads to search results page'() {

        given: 'At dob search page'
        toDobPage()

        when: 'I choose age search'
        searchType('age')

        and: 'I search for a valid age'
        searchForm.using([
                age: '37'
        ])

        then: 'I see the search results page'
        at SearchResultsPage

        and: 'I see the number of results returned'
        searchResultHeading.verifyNotEmpty()

        and: 'I see a new search link'
        newSearchLink.isDisplayed()
    }

    def toDobPage() {
        to SearchPage
        selectSearchOptions(['dob'])
        proceed()
    }
}
