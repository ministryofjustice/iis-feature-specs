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

        paroleRef { $('#parole-refs') }

        pnc { $('#pnc') }

        subjectIdNumber { (subjectId.text() - 'Prison number').trim() }

        backToResults { $('a', text: contains('Back'))}

        sectionNames { $('#inmate-nav').find('li') }

        sectionLinks { $('#inmate-nav').find('a') }

        section { sectionName ->
            $('#' + sectionName).click()
        }

        saveToPdf { $('#saveLink a', text: contains('Save as PDF')) }

        category { $('#category') }
        establishment { $('#establishment') }
        courtName { $('#courtName') }
        sentenceLength { $('#sentenceLength') }
    }
}
