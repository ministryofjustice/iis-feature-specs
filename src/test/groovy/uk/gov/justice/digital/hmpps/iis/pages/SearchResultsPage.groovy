package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule
import uk.gov.justice.digital.hmpps.iis.modules.SearchFormModule

class SearchResultsPage extends Page {

    static url = '/search/results'

    static at = {
        browser.currentUrl.endsWith(url)
    }

    static content = {

        header { module(HeaderModule) }

        errors { module(ErrorsModule) }

        searchForm { module(SearchFormModule)}

        searchResultHeading { $('#contentTitle') }

        resultItems(required: false) { $('div.inmateDetails') }

        firstResultItem { $('div.inmateDetails', 0) }

        resultItemLinks { resultItems.find('a') }

        nextPageLink { $('a', text: 'Next') }
        previousPageLink { $('a', text: 'Previous') }

        nextPageLabel { $('span', text: 'Next') }
        previousPageLabel { $('span', text: 'Previous') }

        paginationForm { $('form') }
        paginationInput { $(paginationForm).pageNumber }

        pageIndicator { $('#pageIndicator') }

        filterValues { $('form')['filter'] }

        filterControls { $('form').find('input', name: 'filter') }

        activeFilters { $('input').filter('.filtering-option-active') }
        inactiveFilters { $('input').filter('.filtering-option') }

        suggestionsLink(required: false) { $('#suggestions') }
    }
}
