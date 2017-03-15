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

    static content = {

        header { module(HeaderModule) }

        disclaimerConfirmation { $("#disclaimerConfirmation") }

        errors { module(ErrorsModule) }

        signinButton(to: [LoginPage, SearchPage]) { $('#signin') }

        loginId { $('input[name=loginId]') }

        password { $('input[name=pwd]') }

        disclaimerControl { $('label', for: 'disclaimer') }

        logIn { user, pass, disclaimer ->
            loginId.value user
            password.value pass
            if (disclaimer) {
                disclaimerControl.click()
            }
            signinButton.click()
        }
    }
}
