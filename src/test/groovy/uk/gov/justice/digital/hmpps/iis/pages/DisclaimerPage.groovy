package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule

class DisclaimerPage extends Page {

    static url = '/disclaimer'

    static at = {
        browser.currentUrl.contains('/disclaimer')
        title == 'Usage'
    }

    static content = {

        header { module(HeaderModule) }

        disclaimerConfirmation { $("#disclaimerConfirmation") }

        errors { module(ErrorsModule) }

        continueButton(to: [DisclaimerPage, SearchPage]) { $('#continue') }

        disclaimerControl { $('label', for: 'disclaimer') }

        continueConfirmed {
            disclaimerControl.click()
            continueButton.click()
        }

        continueUnconfirmed {
            continueButton.click()
        }

    }
}
