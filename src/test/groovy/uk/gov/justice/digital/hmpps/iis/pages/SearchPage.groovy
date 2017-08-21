package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule
import uk.gov.justice.digital.hmpps.iis.modules.SearchFormModule

class SearchPage extends Page {

    static url = '/search'

    static at = {
        waitFor(5) {
            browser.currentUrl.endsWith(url)
        }
    }

    static content = {

        header { module(HeaderModule) }

        errors { module(ErrorsModule) }

        searchForm { module(SearchFormModule)}

        nomisTab(required: false) { $('#nomisTab') }
        hpaTab { $('#hpaTab') }
    }
}
