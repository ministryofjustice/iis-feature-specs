package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule

class PrintPage extends Page {

    static url = '/print/'

    static at = {
        browser.currentUrl.contains(url)
    }

    static content = {

        header { module(HeaderModule) }

        errors { module(ErrorsModule) }

        printOptions { $('form').printOption }

        saveButton(to: [PrintPage]) { $('#saveAsPdf') }

        proceed { saveButton.click() }

        backLink { $('a.link-back', 0) }

        selectPrintOptions { options ->
            options.each {
                $('label', for: it).click()
            }
        }
    }
}
