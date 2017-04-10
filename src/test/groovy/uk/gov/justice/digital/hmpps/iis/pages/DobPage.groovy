package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule
import uk.gov.justice.digital.hmpps.iis.modules.SearchFormModule
import org.openqa.selenium.Keys

class DobPage extends Page {

    static url = '/search/dob'

    static at = {
        browser.currentUrl.endsWith(url)
    }

    static content = {

        header { module(HeaderModule) }

        errors { module(ErrorsModule) }

        searchForm { module(SearchFormModule) }

        searchType{ type ->

            // Work-around for govuk.frontend-toolkit show-hide content javascript in PhantomJS
            $("#${type}_container").jquery.removeClass('js-hidden')
            $("#${type}_container").jquery.attr('aria-hidden', 'false')
            $("#${type}_container").jquery.attr('aria-expanded', 'true')

            // Simply clicking it is good enough in ChromeDriver
            $('label', for: "opt${type.capitalize()}").click()
        }
    }
}
