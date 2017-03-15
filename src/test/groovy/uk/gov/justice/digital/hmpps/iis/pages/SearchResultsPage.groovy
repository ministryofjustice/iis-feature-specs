package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule

class SearchResultsPage extends Page {

    static url = '/search/results'

    static at = {
        browser.currentUrl.contains('/search/results')
    }

    static content = {

        header { module(HeaderModule) }

        errors { module(ErrorsModule) }

        newSearchLink { $('a', href: '/search') }

        searchResultHeading { $('#contentTitle') }

        resultItems { $('div.inmate-details') }

        resultItemLinks { resultItems.find('a') }

        nextPageLink { $('a', text: 'Next') }
        previousPageLink { $('a', text: 'Previous')}

        nextPageLabel { $('span', text: 'Next') }
        previousPageLabel { $('span', text: 'Previous') }

        pageIndicator { $('#pageIndicator') }
    }
}