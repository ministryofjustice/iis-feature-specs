package uk.gov.justice.digital.hmpps.iis.modules

import geb.Module
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage

class SearchFormModule extends Module {

    static content = {

        idSearch { $('#idFormLink') }

        nameAgeSearch{ $('#otherFormLink') }

        identifiers { criteria ->
            using(criteria)
            searchByIdButton.click()
        }

        nameAge { criteria ->
            nameAgeSearch.click()
            using(criteria)
            searchByNameAgeButton.click()
        }

        using { criteria ->
            criteria.each { key, value ->
                $('form')[key] = value
            }
        }

        searchByIdButton(to: [SearchResultsPage, SearchPage]) { $('#submitId') }
        searchByNameAgeButton(to: [SearchResultsPage, SearchPage]) { $('#submitNonId') }
    }
}
