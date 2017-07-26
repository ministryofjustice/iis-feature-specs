package uk.gov.justice.digital.hmpps.iis

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SubjectDetailsPage
import uk.gov.justice.digital.hmpps.iis.pages.subject.MovementsPage

@Stepwise
class SubjectMovementsSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'List of movements shown'() {

        when: 'I view the movements section'
        gotoMovementsPage([prisonNumber: 'AB111111'])

        then: 'I see the list of movements'
        movementsList.size() == 4
    }


    private void gotoMovementsPage(query) {
        performSearch(query)
        resultItemLinks[0].click()
        at SubjectDetailsPage
        section('movements')
        at MovementsPage
    }

    private void performSearch(query) {
        to SearchPage
        selectSearchOptions(['identifier'])
        proceed()
        searchForm.using(query)
    }
}
