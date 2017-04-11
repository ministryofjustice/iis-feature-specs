package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

class SsoProfilePage extends Page{

    static HoaUi hoaUi = new HoaUi()

    static url = hoaUi.profileUri

    static at = {
        browser.currentUrl.startsWith(url)
    }

    static content = {
        signOut {
            $('a', href: '/users/sign_out').click()
        }
    }

}
