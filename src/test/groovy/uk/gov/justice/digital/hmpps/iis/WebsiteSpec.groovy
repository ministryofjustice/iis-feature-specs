package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebReportingSpec
import geb.spock.GebSpec
import uk.gov.justice.digital.hmpps.iis.pages.IndexPage
import uk.gov.justice.digital.hmpps.iis.pages.LoginPage


class WebsiteSpec extends GebReportingSpec {

    def 'Application title is shown'(){

        when: 'Viewing the website'
        to LoginPage

        then: 'application title is shown'
        header.applicationTitle == 'Inmate information system'
    }

    def 'feedback link does nothing'(){

        given: 'Viewing the website'
        to LoginPage

        when: 'I click the feedback link'
        header.feedbackLink.click()

        then: 'nothing happens'
        at LoginPage
    }

}
