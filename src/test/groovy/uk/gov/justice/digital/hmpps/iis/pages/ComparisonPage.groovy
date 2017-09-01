package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.TabsModule


class ComparisonPage extends Page {

    static url = '/comparison'

    static at = {
        browser.currentUrl.contains(url)
    }

    static content = {

        tabs { module(TabsModule) }

        comparisonSections { $('#comparisonHeader') }

        removeFromShortlistLinks(required: false) { $('a', text: 'Remove from shortlist') }
    }
}
