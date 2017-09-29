package uk.gov.justice.digital.hmpps.iis.search

import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.util.SignOnBaseSpec

@Stepwise
class AddressSearchSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Address search requires at least two address elements'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search by address with no inputs'
        searchForm.address([
                address: ''
        ])

        then: 'I see an error message'
        errors.summaryShown()

        when: 'I search by address with only one element'
        searchForm.address([
                address: 'street'
        ])

        then: 'I see an error message'
        errors.summaryShown()
    }

    def 'valid address search leads to search results page'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search for a valid name'
        searchForm.address([
                address: 'street road'
        ])

        then: 'I see the number of results returned'
        searchResultHeading.text().contains('2')
    }

    @Unroll
    def 'search with #type is allowed'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search for a valid name'
        searchForm.address([
                address: address
        ])

        then: 'I see the correct result'
        firstResultItem.find('.prisonNumber')[0].text().contains(prisonNumber)

        where:
        type                   | address          | prisonNumber
        'number and street'    | '1 street road'  | 'AB111111'
        'number and post code' | '4 L11 4AB'      | 'AB111112'
        'full post code'       | 'L11 4AB'        | 'AB111112'
        'street and post code' | 'street L11 1AB' | 'AB111111'
        'street and town'      | 'street town e'  | 'AB111112'
    }

    @Unroll
    def '#punctuation is removed'() {

        given: 'I am on the search page'
        to SearchPage

        when: 'I search for a valid name'
        searchForm.address([
                address: address
        ])

        then: 'I see the correct result'
        firstResultItem.find('.prisonNumber')[0].text().contains(prisonNumber)

        where:
        punctuation  | address          | prisonNumber
        'apostrophe' | "1 stree't road" | 'AB111111'
        'comma'      | "1,street road"  | 'AB111111'
        'period'     | "1.street road"  | 'AB111111'
    }

}
