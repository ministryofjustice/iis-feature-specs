package uk.gov.justice.digital.hmpps.iis

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SubjectDetailsPage
import uk.gov.justice.digital.hmpps.iis.pages.subject.AddressesPage
import uk.gov.justice.digital.hmpps.iis.pages.subject.SentencesPage

@Stepwise
class SubjectSentencesSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'List of sentences shown'() {

        when: 'I view the sentences section'
        gotoSentencesPage([surname: 'surnamea'])

        then: 'I see the sentence history for that subject'
        sentenceHistoryList.size() == 4
    }



    private void gotoSentencesPage(query) {
        performSearch(query)
        resultItemLinks[0].click()
        at SubjectDetailsPage
        section('sentences')
        at SentencesPage
    }

    private void performSearch(query) {
        to SearchPage
        selectSearchOptions(['names'])
        proceed()
        searchForm.using(query)
    }
}
