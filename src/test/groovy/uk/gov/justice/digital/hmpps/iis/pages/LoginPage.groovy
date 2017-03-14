package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule

class LoginPage extends Page {

    static url = '/login'

    static at = {
        browser.currentUrl.contains('/login')
        title == 'Enter user id and password'
    }

    def logIn(user, pass, disclaimer) {
        $('form').loginId = user
        $('form').pwd = pass
        if (disclaimer) {
            $('label', for: 'disclaimer').click()
        }
        $('#signin').click()
    }

    static content = {

        header { module(HeaderModule) }

        disclaimerConfirmation { $("#disclaimerConfirmation") }

        errors { module(ErrorsModule) }
    }
}
