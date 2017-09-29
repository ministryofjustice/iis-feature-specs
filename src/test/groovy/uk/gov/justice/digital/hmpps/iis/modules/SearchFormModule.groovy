package uk.gov.justice.digital.hmpps.iis.modules

import geb.Module
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage

class SearchFormModule extends Module {

    static content = {

        clearSearch { $('#clearSearch') }

        idSearch { $('label', for: 'identifier') }

        nameAgeSearch{ $('label', for: 'nameAge') }

        otherSearch{ $('label', for: 'other') }

        identifiers { criteria ->
            idSearch.click()
            using(criteria)
            submit
        }

        nameAge { criteria ->
            nameAgeSearch.click()
            using(criteria)
            submit
        }

        address { criteria ->
            otherSearch.click()
            using(criteria)
            submit
        }

        using { criteria ->
            criteria.each { key, value ->
                $('form')[key] = value
            }
        }

        submit {
            searchButton.click()
        }

        searchButton(to: [SearchResultsPage, SearchPage]) { $('#submit') }

        newSearch { clearSearch.click()  }
    }
}
