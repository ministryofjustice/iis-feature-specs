package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import uk.gov.justice.digital.hmpps.iis.pages.DisclaimerPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SubjectDetailsPage

class ViewDetailsSpec extends GebReportingSpec {

    def setup() {
        to DisclaimerPage
        continueConfirmed
    }

    def cleanup() {
        to LogoutPage
    }

    def 'Select search result to view detail'() {

        when: 'I do a search that gives some results'
        to SearchPage
        selectSearchOptions(['names'])
        proceed()

        searchForm.using([
                surname: 'ali'
        ])

        and: 'I click the first result'
        resultItemLinks[0].click()

        then: 'I see the subject details page'
        at SubjectDetailsPage

        and: 'I see the surname I searched for in upper case'
        subjectName.text().contains('ALI')

        and: 'I see the prison identifier'
        subjectId.verifyNotEmpty()

        and: 'The page url contains the same prison identifier'
        browser.currentUrl.contains(subjectIdNumber)
    }

}
