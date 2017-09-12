package uk.gov.justice.digital.hmpps.iis.modules

import geb.Module
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage

class SearchFormModule extends Module {

    static content = {

        idSearch { $('label', for: 'identifier') }

        nameAgeSearch{ $('label', for: 'nameAge') }

        otherSearch{ $('label', for: 'other') }

        identifiers { criteria ->
            idSearch.click()
            using(criteria)
            searchButton.click()
        }

        nameAge { criteria ->
            nameAgeSearch.click()
            using(criteria)
            searchButton.click()
        }

        address { criteria ->
            otherSearch.click()
            using(criteria)
            searchButton.click()
        }

        using { criteria ->
            criteria.each { key, value ->
                $('form')[key] = value
            }
        }

        searchButton(to: [SearchResultsPage, SearchPage]) { $('#submit') }
    }
}
