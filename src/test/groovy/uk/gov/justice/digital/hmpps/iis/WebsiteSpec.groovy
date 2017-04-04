package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import uk.gov.justice.digital.hmpps.iis.pages.DisclaimerPage
import uk.gov.justice.digital.hmpps.iis.pages.FeedbackPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage

class WebsiteSpec extends GebReportingSpec {

    def setup() {
        to DisclaimerPage
        continueConfirmed
    }

    def cleanup() {
        to LogoutPage
    }

    def 'Application title is shown'(){

        when: 'Viewing the website'
        to SearchPage

        then: 'application title is shown'
        header.applicationTitle == 'Historical Prisoner Application'
    }

    def 'feedback link shows feedback page'(){

        given: 'Viewing the website'
        to SearchPage

        when: 'I click the feedback link'
        header.feedbackLink.click()

        then: 'I see the feedback page'
        at FeedbackPage

        and: 'I see an email link'
        feedbackMailtoLink.isDisplayed()
    }

}
