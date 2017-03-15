package uk.gov.justice.digital.hmpps.iis.modules

import geb.Module
import uk.gov.justice.digital.hmpps.iis.pages.DobPage
import uk.gov.justice.digital.hmpps.iis.pages.IdentifierPage
import uk.gov.justice.digital.hmpps.iis.pages.NamesPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage


class SearchFormModule extends Module {

    def using(criteria) {

        criteria.each { key, value ->
            $('form')[key] = value
        }

        $('#continue').click([SearchResultsPage, DobPage, NamesPage, IdentifierPage])
    }
}
