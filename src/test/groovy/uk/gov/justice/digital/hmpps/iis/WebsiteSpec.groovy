package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.DisclaimerPage
import uk.gov.justice.digital.hmpps.iis.pages.LogoutPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Stepwise
class WebsiteSpec extends GebReportingSpec {

    def setupSpec() {
        to DisclaimerPage
        continueConfirmed
    }

    def cleanupSpec() {
        to LogoutPage
    }

    def 'Application title is shown'(){

        when: 'Viewing the website'
        to SearchPage

        then: 'application title is shown'
        header.applicationTitle == 'Inmate information system'
    }

    def 'feedback link does nothing'(){

        given: 'Viewing the website'
        to SearchPage

        when: 'I click the feedback link'
        header.feedbackLink.click()

        then: 'nothing happens'
        at SearchPage
    }

}
