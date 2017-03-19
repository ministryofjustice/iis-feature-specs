package uk.gov.justice.digital.hmpps.iis.modules

import geb.Module


class HeaderModule extends Module {

    static content = {

        logoutLinkDisplayed { $('a', href: '/logout').isDisplayed() }

        logoutLink { $('a', href: '/logout') }

        feedbackLink { $('a', text: 'feedback') }

        // todo why has this stared returning more than 1 element?
        applicationTitle { $('#proposition-name')[0].text() }
    }
}
