package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule

class SearchPage extends Page {

    static url = '/search'

    static at = {
        title == 'What information do you have on the inmate?'
        browser.currentUrl.contains('/search')
    }

    def proceed() {
        $('#continue').click()
    }

    def searchOptions(options) {
        options.each {
            $('label', for: it).click()
        }
    }

    static content = {

        header { module(HeaderModule) }

        errors { module(ErrorsModule) }

        searchOptions { $('form').opt }
    }
}
