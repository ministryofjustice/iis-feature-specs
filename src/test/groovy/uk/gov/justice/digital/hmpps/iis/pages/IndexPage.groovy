package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule

class IndexPage extends Page{

    static url = '/'

    static content = {
        header { module(HeaderModule) }
    }

}
