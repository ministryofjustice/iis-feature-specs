package uk.gov.justice.digital.hmpps.iis.modules

import geb.Module


class TabsModule extends Module {

    static content = {

        comparisonTab(required: false) { $('#comparisonTab') }
        comparisonTabLink(required: false) { comparisonTab.find('a') }

        hpaTab(required: false) { $('#hpaTab') }
        hpaTabLink(required: false) { hpaTab.find('a') }
    }
}
