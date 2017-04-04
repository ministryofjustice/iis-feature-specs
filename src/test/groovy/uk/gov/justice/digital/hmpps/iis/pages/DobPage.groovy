package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule
import uk.gov.justice.digital.hmpps.iis.modules.SearchFormModule

class DobPage extends Page {

    static url = '/search/dob'

    static at = {
        browser.currentUrl.contains(url)
        title == 'Enter inmate\'s date of birth or age/range'
    }

    static content = {

        header { module(HeaderModule) }

        errors { module(ErrorsModule) }

        searchForm { module(SearchFormModule) }

        searchType{ type -> $('label', for: "opt${type.capitalize()}").click() }
    }
}
