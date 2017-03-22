package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.*
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Stepwise
class ViewDetailsSpec extends GebReportingSpec {

    def setupSpec() {
        to DisclaimerPage
        continueConfirmed
    }

    def cleanupSpec() {
        to LogoutPage
    }

    def 'Select search result to view detail'() {

        when: 'I do a search that gives some results'
        to SearchPage
        selectSearchOptions(['names'])
        proceed()

        searchForm.using([
                surname: 'scott'
        ])

        and: 'I click the first result'
        resultItemLinks[0].click()

        then: 'I see the subject details page'
        at SubjectDetailsPage

        and: 'I see the surname I searched for in upper case'
        subjectName.text().contains('SCOTT')

        and: 'I see the prison identifier'
        subjectId.verifyNotEmpty()

        and: 'The page url contains the same prison identifier'
        browser.currentUrl.endsWith(subjectIdNumber)
    }

}
