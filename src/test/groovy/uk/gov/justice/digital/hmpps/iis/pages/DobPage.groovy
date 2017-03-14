package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule
import uk.gov.justice.digital.hmpps.iis.modules.SearchFormModule

class DobPage extends Page {

    static url = '/search/dob'

    static at = {
        title == 'Enter inmate\'s date of birth or age/range'
        browser.currentUrl.contains('/search/dob')
    }

    def searchType(type) {
        $('label', for: 'opt' + type).click()
    }

    static content = {

        header { module(HeaderModule) }

        errors { module(ErrorsModule) }

        searchForm { module(SearchFormModule) }
    }
}
