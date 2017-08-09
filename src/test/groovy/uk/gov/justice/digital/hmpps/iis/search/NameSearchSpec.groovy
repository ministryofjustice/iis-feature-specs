package uk.gov.justice.digital.hmpps.iis.search

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.util.SignOnBaseSpec

@Stepwise
class NameSearchSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Name search requires at least one input'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search by name with no inputs'
        searchForm.nameAge([
                forename : '',
                forename2: '',
                surname  : ''
        ])

        then: 'I see an error message'
        errors.summaryShown()
    }

    def 'valid name leads to search results page'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search for a valid name'
        searchForm.nameAge([
                surname: 'surnamea'
        ])

        then: 'I see the number of results returned'
        searchResultHeading.text().contains('1')
    }

    def 'name search allows apostrophe, hyphen, and space' (){

        given: 'I am on the search page'
        to SearchPage

        when: 'I search for a valid name including allowed special characters'
        searchForm.nameAge([
                forename: "first'a",
                forename2: "mid-dlea",
                surname: "surname a"
        ])

        then: 'I see the results page, not a validation error'
        at SearchResultsPage
    }
}
