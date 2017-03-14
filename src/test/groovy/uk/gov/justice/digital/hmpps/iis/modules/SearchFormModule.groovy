package uk.gov.justice.digital.hmpps.iis.modules

import geb.Module


class SearchFormModule extends Module {

    def using(criteria) {

        criteria.each { key, value ->
            $('form')[key] = value
        }

        $('#continue').click()
    }
}
