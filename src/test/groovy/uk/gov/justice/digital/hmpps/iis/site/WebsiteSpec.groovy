package uk.gov.justice.digital.hmpps.iis.site

import groovy.json.JsonSlurper
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.FeedbackPage
import uk.gov.justice.digital.hmpps.iis.pages.HealthPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.util.SignOnBaseSpec

@Stepwise
class WebsiteSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Application title is shown'() {

        when: 'Viewing the website'
        to SearchPage

        then: 'application title is shown'
        header.applicationTitle == 'Historical Prisoner Application'
    }

    def 'feedback link shows feedback page'() {

        given: 'Viewing the website'
        to SearchPage

        when: 'I click the feedback link'
        header.feedbackLink.click()

        then: 'I see the feedback page'
        at FeedbackPage

        and: 'I see an email link'
        feedbackMailtoLink.isDisplayed()
    }

    def 'health page shows application status'() {

        when: 'Viewing the health page'
        to HealthPage

        then: 'I see health status OK'

        def json = driver.pageSource - '<html><head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">'
        def response = new JsonSlurper().parseText(json)

        response.healthy == true
        response.checks.db == 'ok'
    }
}
