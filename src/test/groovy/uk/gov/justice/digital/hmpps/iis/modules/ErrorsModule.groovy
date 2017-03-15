package uk.gov.justice.digital.hmpps.iis.modules

import geb.Module


class ErrorsModule extends Module {

    static content = {

        summary { $("#errors") }

        summaryShown { summary.isDisplayed() && summary.verifyNotEmpty() }

        links { summary.find('a')*.attr('href') }

        hasLinkWithText { href, text ->
            links.contains(href) && linksText.contains(text)
        }

        hasLink { href ->
            links.contains(href)
        }

        linksText { summary.find('a')*.text() }
    }
}
