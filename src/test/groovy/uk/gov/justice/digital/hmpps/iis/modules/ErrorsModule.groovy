package uk.gov.justice.digital.hmpps.iis.modules

import geb.Module


class ErrorsModule extends Module{

    def summaryShown(){
        return summary.isDisplayed() && summary.verifyNotEmpty()
    }

    def hasLink(href, text){
        return hasLink(href) && linksText.contains(text)
    }

    def hasLink(href){
        return links.contains(href)
    }

    static content = {

        summary { $("#errors") }

        links { summary.find('a')*.attr('href') }

        linksText { summary.find('a')*.text() }
    }
}
