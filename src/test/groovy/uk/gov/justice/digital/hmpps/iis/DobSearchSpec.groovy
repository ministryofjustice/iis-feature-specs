package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.pages.*
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Stepwise
class DobSearchSpec extends GebReportingSpec {

    def setupSpec() {
        to DisclaimerPage
        continueConfirmed
    }

    def cleanupSpec() {
        to LogoutPage
    }

    @Ignore
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

    @Ignore
    def 'valid dob leads to search results page'() {

        given: 'At dob search page'
        toDobPage()

        when: 'I search for a valid dob'
        searchForm.using([
                dobDay  : '1',
                dobMonth: '1',
                dobYear : '1970'
        ])

        then: 'I see the search results page'
        at SearchResultsPage

        and: 'I see the number of results returned'
        searchResultHeading.verifyNotEmpty()

        and: 'I see a new search link'
        newSearchLink.isDisplayed()
    }

    @Ignore("Need to make PhantomJS work with the GDS javascript that shows/hides elements when clicking radio button")
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

    @Ignore("Need to make PhantomJS work with the GDS javascript that shows/hides elements when clicking radio button")
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
        range << ['30-100', '39-38', '31-31', '31--32', '31-', '-31']
    }

    @Ignore("Need to make PhantomJS work with the GDS javascript that shows/hides elements when clicking radio button")
    def 'valid age leads to search results page'() {

        given: 'At dob search page'
        toDobPage()

        when: 'I choose age search'
        searchType('age')

        and: 'I search for a valid age'
        searchForm.using([
                age: '30'
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
