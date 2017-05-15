package uk.gov.justice.digital.hmpps.iis.pages.subject

import uk.gov.justice.digital.hmpps.iis.pages.SubjectDetailsPage


class AddressesPage extends SubjectDetailsPage {

    static at = {
        browser.currentUrl.contains('/subject/')
        browser.currentUrl.endsWith('/addresses')
    }
}
