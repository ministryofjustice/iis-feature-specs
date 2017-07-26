package uk.gov.justice.digital.hmpps.iis

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SubjectDetailsPage
import uk.gov.justice.digital.hmpps.iis.pages.subject.AdjudicationsPage

@Stepwise
class SubjectOffencesInCustodySpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'List of offences in custody shown'() {

        when: 'I view the offences in custody section'
        gotoOffencesInCustodyPage([prisonNumber: 'AB111111'])

        then: 'I see the list of offences in custody'
        offencesInCustodyList.size() == 4
    }


    private void gotoOffencesInCustodyPage(query) {
        performSearch(query)
        resultItemLinks[0].click()
        at SubjectDetailsPage
        section('offencesincustody')
        at AdjudicationsPage
    }

    private void performSearch(query) {
        to SearchPage
        selectSearchOptions(['identifier'])
        proceed()
        searchForm.using(query)
    }
}
