package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule

class SearchPage extends Page {

    static url = '/search'

    static at = {
        browser.currentUrl.endsWith(url)
    }

    static content = {

        header { module(HeaderModule) }

        errors { module(ErrorsModule) }

        searchOptions { $('form').opt }

        continueButton(to: [SearchPage, DobPage, NamesPage, IdentifierPage]) { $('#continue') }

        proceed { continueButton.click() }

        selectSearchOptions { options ->
            options.each {
                $('label', for: it).click()
            }
        }
    }
}
