package uk.gov.justice.digital.hmpps.iis

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.SignOnBaseSpec
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SubjectDetailsPage
import uk.gov.justice.digital.hmpps.iis.pages.subject.HdcInfoPage

@Stepwise
class SubjectHdcSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'List of HDC recalls and HDC history shown'() {

        when: 'I view the hdc section'
        gotoHdcPage([surname: 'surnamea'])

        then: 'I see the hdc recalls for that subject'
        recallsList.size() == 2

        and: 'I see the hdc recalls for that subject'
        historyList.size() == 4

    }

    private void gotoHdcPage(query) {
        performSearch(query)
        resultItemLinks[0].click()
        at SubjectDetailsPage
        section('hdcinfo')
        at HdcInfoPage
    }

    private void performSearch(query) {
        to SearchPage
        selectSearchOptions(['names'])
        proceed()
        searchForm.using(query)
    }
}
