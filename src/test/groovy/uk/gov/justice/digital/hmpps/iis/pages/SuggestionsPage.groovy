package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule

class SuggestionsPage extends Page {

    static url = '/search/suggestions'

    static at = {
        browser.currentUrl.endsWith(url)
    }

    static content = {


    }
}
