package uk.gov.justice.digital.hmpps.iis

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SubjectDetailsPage
import uk.gov.justice.digital.hmpps.iis.pages.subject.OffencesPage

@Stepwise
class SubjectOffencesSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'List of offences shown'() {

        when: 'I view the offences section'
        gotoOffencesPage([prisonNumber: 'AB111111'])

        then: 'I see the list of offences'
        offencesList.size() == 3
    }


    private void gotoOffencesPage(query) {
        performSearch(query)
        resultItemLinks[0].click()
        at SubjectDetailsPage
        section('offences')
        at OffencesPage
    }

    private void performSearch(query) {
        to SearchPage
        selectSearchOptions(['identifier'])
        proceed()
        searchForm.using(query)
    }
}
