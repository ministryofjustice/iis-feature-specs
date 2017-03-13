package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import org.openqa.selenium.By
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

// @Slf4j - can't use this with Spock/Groovy closures containing asserts
@Stepwise
class MultiSearchSpec extends GebSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setupSpec() {
        logIn()
    }

    def cleanupSpec() {
        logOut()
    }

    def 'Select all search types shows all search pages in order'() {

        given: 'I am on the search page'
        goToSearch()

        and: 'I select all search types'
        $('#identifierlabel').click()
        $('#nameslabel').click()
        $('#doblabel').click()

        when: 'I continue'
        $('#continue').click()

        then: 'I see the ID search page'
        browser.currentUrl.contains('search/identifier')

        when: 'when I continue with valid inputs'
        $('form').prisonNumber = 'AA000000'
        $('#continue').click()

        then: 'I see the name search'
        browser.currentUrl.contains('search/names')

        when: 'when I continue with valid inputs'
        $('form').surname = 'surname'
        $('#continue').click()

        then: 'I see the dob search'
        browser.currentUrl.contains('search/dob')
    }

    def goToSearch() {
        go hoaUi.indexUri + 'search'
        assert browser.currentUrl.contains('/search')
    }

    def logIn() {
        go hoaUi.indexUri
        assert browser.currentUrl.contains('/login')
//        Thread.sleep(1000)
        $('form').loginId = hoaUi.username
        $('form').pwd = hoaUi.password
        $('#disclaimerlabel').click()
        $('#signin').click()
    }

    def logOut() {
        go hoaUi.indexUri + 'logOut'
        assert browser.currentUrl.contains('/login')
    }
}
