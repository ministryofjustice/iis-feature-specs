package uk.gov.justice.digital.hmpps.iis.modules

import geb.Module


class TabsModule extends Module {

    static content = {

        comparisonTab(required: false) { $('#comparisonTab') }
        hpaTab(required: false) { $('#hpaTab') }

    }
}
