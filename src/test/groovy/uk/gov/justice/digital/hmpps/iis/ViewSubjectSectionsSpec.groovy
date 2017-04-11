package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.pages.*
import uk.gov.justice.digital.hmpps.iis.pages.subject.HdcInfoPage
import uk.gov.justice.digital.hmpps.iis.pages.subject.HdcRecallPage
import uk.gov.justice.digital.hmpps.iis.pages.subject.MovementsPage
import uk.gov.justice.digital.hmpps.iis.pages.subject.OffencesPage

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
                'Summary',
                'Movements',
                'HDC history',
                'HDC recall history',
                'Offences'
        ])

        and: 'I see the other sections as links in the right order'
        sectionLinks*.text().containsAll([
                'Summary',
                'HDC history',
                'HDC recall history',
                'Offences'
        ])
    }

    @Unroll
    def 'when viewing a subject I can choose to view #sectionId'() {

        when: 'I view #sectionId'
        $('#' + sectionId).click()

        then: ' I see the #sectionId page'
        at page

        where:
        sectionId   | page
        'movements' | MovementsPage
        'hdcinfo'   | HdcInfoPage
        'hdcrecall' | HdcRecallPage
        'offences'  | OffencesPage
        'summary'   | SubjectDetailsPage // comes last because initially selected anyway
    }

    private void gotoSubjectPage() {
        performSearch([surname: 'ali'])
        resultItemLinks[0].click()
        at SubjectDetailsPage
    }

    private void performSearch(query) {
        to SearchPage
        selectSearchOptions(['names'])
        proceed()
        searchForm.using(query)
    }

}
