package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.pages.DobPage
import uk.gov.justice.digital.hmpps.iis.pages.IdentifierPage
import uk.gov.justice.digital.hmpps.iis.pages.LoginPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.NamesPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Stepwise
class SearchOptionsSpec extends GebSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setupSpec() {
        to LoginPage
        logIn(hoaUi.username, hoaUi.password, true)
    }

    def cleanupSpec() {
        to LogoutPage
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
        searchOptions(option)
        proceed()

        then: 'I see the corresponding search page'
        at searchOptionPage

        where:
        option       | searchOptionPage
        'dob'        | DobPage
        'names'      | NamesPage
        'identifier' | IdentifierPage
    }

}
