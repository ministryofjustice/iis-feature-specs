package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.LoginPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.NamesPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Stepwise
class NameSearchSpec extends GebSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setupSpec() {
        to LoginPage
        logIn(hoaUi.username, hoaUi.password, true)
    }

    def cleanupSpec() {
        to LogoutPage
    }

    def 'Name search requires at least one input'() {

        given: 'I am on the search by name page'
        toNamesPage()

        when: 'I search with no inputs'
        searchForm.using([
                forename : '',
                forename2: '',
                surname  : ''
        ])

        then: 'I see an error message'
        errors.summaryShown()
    }

    def 'valid name leads to search results page'() {

        given: 'I am on the search by name page'
        toNamesPage()

        when: 'I search for a valid name'
        searchForm.using([
                surname: 'validname'
        ])

        then: 'I see the search results page'
        at SearchResultsPage

        and: 'I see the number of results returned'
        searchResultHeading.verifyNotEmpty() // todo - with predefined data, check actual result count

        and: 'I see a new search link'
        newSearchLink.isDisplayed()
    }

    def toNamesPage() {
        to SearchPage
        selectSearchOptions(['names'])
        proceed()
    }
}
