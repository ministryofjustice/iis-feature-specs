package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

// @Slf4j - can't use this with Spock/Groovy closures containing asserts
@Stepwise
class IdentifierSearchSpec extends GebSpec {

    private static final List<String> invalidIdentifiers = ['', '   ', 'AAA', 'AA00000', 'AA00000A']

    private static final String validIdentifier = 'AA000000'

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def setupSpec() {
        logIn()
    }

    def cleanupSpec() {
        logOut()
    }

    @Unroll
    def 'Identifier search rejects invalid input #identifier'() {

        given: 'I am on the search by identifier page'
        goToSearchFor('identifier')

        when: 'I search for an identifier'
        $('form').prisonNumber = identifier
        $('#continue').click()

        then: 'I see an error message'
        $("#errors").verifyNotEmpty()

        where:
        identifier << invalidIdentifiers
    }

    def 'valid identifier leads to search results page'() {

        given: 'I am on the search by identifier page'
        goToSearchFor('identifier')

        when: 'I search for a valid identifier'
        $('form').prisonNumber = validIdentifier
        $('#continue').click()

        then: 'I see the search results page'
        browser.currentUrl.contains('search/results')

        and: 'I see the number of results returned'
        with($('#contentTitle').text()) {
            contains('search returned')
            contains('results')
        }

        and: 'I see a new search link'
        $(By.xpath('//a[@href="/search"]')).isDisplayed()
    }

    def goToSearchFor(option) {
        // go hoaUi.indexUri + 'search/' + option
        // unable to go directly to page because the code expects search option to be in session from /search
        go hoaUi.indexUri + 'search'
        $('#' + option + 'label').click()
        $('#continue').click()
        assert browser.currentUrl.contains(option)
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
