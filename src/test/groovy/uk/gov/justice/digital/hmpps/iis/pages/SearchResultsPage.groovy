package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule

class SearchResultsPage extends Page {

    static url = '/search/results' // has optional query parameter 'page=i' where i is integer

    static at = {
        browser.currentUrl.toURL().getPath().endsWith(url)
    }

    static content = {

        header { module(HeaderModule) }

        errors { module(ErrorsModule) }

        newSearchLink(to: SearchPage) { $('a', href: '/search', text: 'New search') }

        searchResultHeading { $('#contentTitle') }

        resultItems { $('div.inmateDetails') }

        firstResultItem { $('div.inmateDetails', 0) }

        resultItemLinks { resultItems.find('a') }

        nextPageLink { $('a', text: 'Next') }
        previousPageLink { $('a', text: 'Previous')}

        nextPageLabel { $('span', text: 'Next') }
        previousPageLabel { $('span', text: 'Previous') }

        paginationForm { $('form') }
        paginationInput { $(paginationForm).pageNumber }

        pageIndicator { $('#pageIndicator') }

        filterValues { $('form')['filter'] }

        filterControls { $('form').find('input', name: 'filter') }

        activeFilters { $('input').filter('.filtering-option-active') }
        inactiveFilters { $('input').filter('.filtering-option') }

        searchTerms { $('.searchedTerm') }
        firstSearchTerm { $('.searchedTerm', 0) }
        firstSearchTermLink(to: [EditNamesPage, EditDobPage, EditIdentifierPage]) { firstSearchTerm.find('div a') }
    }
}
