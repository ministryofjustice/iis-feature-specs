package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule

class LogoutPage extends Page {

    static url = '/logout'

    static content = {
        header { module(HeaderModule) }
    }
}
