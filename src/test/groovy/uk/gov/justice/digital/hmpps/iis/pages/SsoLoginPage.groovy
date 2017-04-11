package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import spock.lang.Shared
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

class SsoLoginPage extends Page{

    static HoaUi hoaUi = new HoaUi()

    static url = hoaUi.signonUri

    static at = {
        browser.currentUrl.startsWith(url)
    }

    static content = {
        signIn {
            $('form')['user[email]'] = hoaUi.user
            $('form')['user[password]'] = hoaUi.password
            $('input', name: 'commit').click()
        }
    }

}
