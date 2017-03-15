package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.LoginPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.NamesPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.pages.SubjectDetailsPage
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Stepwise
class ViewDetailsSpec extends GebSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setupSpec() {
        to LoginPage
        logIn(hoaUi.username, hoaUi.password, true)
    }

    def cleanupSpec() {
        to LogoutPage
    }

    def 'Select search result to view detail' (){

        when: 'I do a search that gives soem results'
        to SearchPage
        selectSearchOptions(['names'])
        proceed()
        via NamesPage
        searchForm.using([
                surname: 'scott'
        ])
        via SearchResultsPage

        and: 'I click the first result'
        resultItemLinks[0].click()

        then: 'I see the subject details page'
        at SubjectDetailsPage

        and: 'I see the surname I searched for in upper case'
        subjectName.text().contains('SCOTT')

        and: 'I see the prison identifier'
        subjectId.verifyNotEmpty()
    }

}
