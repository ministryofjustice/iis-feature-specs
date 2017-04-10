package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule

class FeedbackPage extends Page {

    static url = '/feedback'

    static at = {
        browser.currentUrl.endsWith(url)
    }

    static content = {

        header { module(HeaderModule) }

        feedbackMailtoLink {  $('a', href: contains('mailto'))  }
    }
}
