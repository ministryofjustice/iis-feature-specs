package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import uk.gov.justice.digital.hmpps.iis.pages.DisclaimerPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.pages.SubjectDetailsPage

class ViewSubjectSpec extends GebReportingSpec {

    def setup() {
        to DisclaimerPage
        continueConfirmed
    }

    def cleanup() {
        to LogoutPage
    }

    def 'Select search result to view subject detail'() {

        when: 'I do a search that gives some results'
        performSearch([surname: 'ali'])

        and: 'I click the first result'
        resultItemLinks[0].click()

        then: 'I see the subject details page'
        at SubjectDetailsPage

        and: 'I see the surname I searched for in upper case'
        subjectName.text().contains('ALI')

        and: 'I see the prison identifier'
        subjectId.verifyNotEmpty()

        and: 'I see the summary view'
        browser.currentUrl.endsWith('/summary')

        and: 'The page url contains the same prison identifier'
        browser.currentUrl.contains(subjectIdNumber)
    }

    def 'When viewing a subject I can return to the search results'() {

        when: 'I view a subject'
        performSearch([surname: 'ali'])
        resultItemLinks[0].click()

        then: 'I see the subject page'
        at SubjectDetailsPage

        and: 'There is a link back to search results'
        backToResults.isDisplayed()

        when: 'I click the back to results link'
        backToResults.click()

        then: 'I see the search results page'
        at SearchResultsPage

    }

    private void performSearch(query) {
        to SearchPage
        selectSearchOptions(['names'])
        proceed()
        searchForm.using(query)
    }

}
