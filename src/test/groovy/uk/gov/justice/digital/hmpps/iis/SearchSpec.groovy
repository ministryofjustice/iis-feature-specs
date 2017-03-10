package uk.gov.justice.digital.hmpps.iis

import geb.spock.GebSpec
import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.util.HoaUi

@Slf4j
class SearchSpec extends GebSpec {

    @Shared
    private HoaUi hoaUi = new HoaUi()

    def 'Must select a search option'() {

        given: 'I am on the search page'
        goToSearch()

        when: 'I continue without selecting a search option'
        $('#continue').click()

        then: 'I see an error message'
        $("#errors").verifyNotEmpty()
    }

    def 'Can search by ID, name or DOB'() {

        when: 'I am on the search page'
        goToSearch()

        then: 'I see the 3 search options'
        $('form').opt.size == 3
    }

    @Ignore
    @Unroll
    def '#option option leads to #searchPage search page'() {

        given: 'I am on the search page'
        goToSearch()

        when: 'I choose a search option'
        $(option).click()
        $('#continue').click()

        then: 'I see the corresponding search page'
        browser.currentUrl.contains('search/' + searchPage)

        where:
        option        | searchPage
        '#dob'        | 'dob'
        '#names'      | 'names'
        '#identifier' | 'identifier'
    }

    def 'dob option leads to dob search page'() {

        given: 'I am on the search page'
        goToSearch()

        when: 'I choose a search option'
        $('#dob').click()
        $('#continue').click()

        then: 'I see the corresponding search page'
        browser.currentUrl.contains('search/' + 'dob')
    }

    def 'identifier option leads to identifier search page'() {

        given: 'I am on the search page'
        goToSearch()

        when: 'I choose a search option'
        $('#identifier').click()
        $('#continue').click()

        then: 'I see the corresponding search page'
        browser.currentUrl.contains('search/' + 'identifier')
    }

    def 'names option leads to names search page'() {

        given: 'I am on the search page'
        goToSearch()

        when: 'I choose a search option'
        $('#names').click()
        $('#continue').click()

        then: 'I see the corresponding search page'
        browser.currentUrl.contains('search/' + 'names')
    }

    def goToSearch() {
        logIn()
        go hoaUi.indexUri + 'search'
        assert browser.currentUrl.contains('/search')
    }

    def logIn() {
        go hoaUi.indexUri
        assert browser.currentUrl.contains('/login')
        $('form').loginId = hoaUi.username
        $('form').pwd = hoaUi.password
        $('#disclaimerlabel').click()
        $('#signin').click()
    }
}
