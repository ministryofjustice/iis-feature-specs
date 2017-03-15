package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import geb.spock.GebSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.DobPage
import uk.gov.justice.digital.hmpps.iis.pages.IdentifierPage
import uk.gov.justice.digital.hmpps.iis.pages.LoginPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.NamesPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Stepwise
class MultiSearchSpec extends GebReportingSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setupSpec() {
        to LoginPage
        logIn(hoaUi.username, hoaUi.password, true)
    }

    def cleanupSpec() {
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
                prisonNumber: 'AA000000'
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
