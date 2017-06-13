package uk.gov.justice.digital.hmpps.iis.pages.subject

import uk.gov.justice.digital.hmpps.iis.pages.SubjectDetailsPage


class SentencesPage extends SubjectDetailsPage {

    static at = {
        browser.currentUrl.contains('/subject/')
        browser.currentUrl.endsWith('/sentences')
    }


    static content = {
        sentenceHistoryList { $('div.sentence') }
    }
}
