package uk.gov.justice.digital.hmpps.iis.subject

import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SubjectDetailsPage
import uk.gov.justice.digital.hmpps.iis.pages.subject.*
import uk.gov.justice.digital.hmpps.iis.util.SignOnBaseSpec

@Stepwise
class ViewSubjectSectionsSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
        gotoSubjectPage()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Correct list of sections is shown with current section not clickable'() {

        when: 'I view Movements section'
        $('#movements').click()

        then: 'I see the right section names in the right order'
        sectionNames*.text().containsAll([
                'Sentence summary',
                'Sentence history',
                'Movements',
                'HDC recalls and history',
                'Offences',
                'Offences in custody',
                'Aliases',
                'Addresses'
        ])

        and: 'I see the other sections as links in the right order'
        sectionLinks*.text().containsAll([
                'Sentence summary',
                'Sentence history',
                'HDC recalls and history',
                'Offences',
                'Offences in custody',
                'Aliases',
                'Addresses'
        ])
    }

    @Unroll
    def 'when viewing a subject I can choose to view #sectionId'() {

        when: 'I view #sectionId'
        $('#' + sectionId).click()

        then: ' I see the #sectionId page'
        at page

        where:
        sectionId           | page
        'sentences'         | SentencesPage
        'movements'         | MovementsPage
        'hdcinfo'           | HdcInfoPage
        'offences'          | OffencesPage
        'offencesincustody' | AdjudicationsPage
        'addresses'         | AddressesPage
        'aliases'           | AliasesPage
        'summary'           | SubjectDetailsPage // comes last because initially selected anyway
    }

    private void gotoSubjectPage() {
        performSearch([surname: 'surnamea'])
        resultItemLinks[0].click()
        at SubjectDetailsPage
    }

    private void performSearch(query) {
        to SearchPage
        searchForm.nameAge(query)
    }

}
