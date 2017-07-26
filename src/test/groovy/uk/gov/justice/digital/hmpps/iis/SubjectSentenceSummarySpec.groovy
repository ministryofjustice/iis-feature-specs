package uk.gov.justice.digital.hmpps.iis

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SubjectDetailsPage
import uk.gov.justice.digital.hmpps.iis.pages.subject.SentencesPage

@Stepwise
class SubjectSentenceSummarySpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Sentence summary info shown'() {

        when: 'I view the sentence summary section'
        gotoSentenceSummaryPage([prisonNumber: 'AB111111'])

        then: 'I see the category'
        category.text() == 'Uncategorised (Sent Males)'

        and: 'I see the establishment'
        establishment.text() == 'Frankland'

        and: 'I see the court hearing'
        courtName.text() == 'Liskeard County Court'

        and: 'I see the effective sentence'
        sentenceLength.text() == '3027 days'
    }



    private void gotoSentenceSummaryPage(query) {
        performSearch(query)
        resultItemLinks[0].click()
        at SubjectDetailsPage
        // default section is sentence summary
    }

    private void performSearch(query) {
        to SearchPage
        selectSearchOptions(['identifier'])
        proceed()
        searchForm.using(query)
    }
}
