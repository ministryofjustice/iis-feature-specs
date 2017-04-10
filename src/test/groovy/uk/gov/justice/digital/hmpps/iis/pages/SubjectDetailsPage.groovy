package uk.gov.justice.digital.hmpps.iis.pages

import geb.Page
import uk.gov.justice.digital.hmpps.iis.modules.ErrorsModule
import uk.gov.justice.digital.hmpps.iis.modules.HeaderModule
import uk.gov.justice.digital.hmpps.iis.modules.SearchFormModule

class SubjectDetailsPage extends Page {

    static at = {
        browser.currentUrl.contains('/subject/')
        browser.currentUrl.endsWith('/summary')
    }

    static content = {

        header { module(HeaderModule) }

        errors { module(ErrorsModule) }

        subjectName { $('#subjectName') }

        subjectId { $('#subjectId') }

        subjectIdNumber { subjectId.text() - 'PRISON NO. ' }

        backToResults { $('a', text: contains('Back to results'))}
    }
}
