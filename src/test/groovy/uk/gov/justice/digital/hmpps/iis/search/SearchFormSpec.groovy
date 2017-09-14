package uk.gov.justice.digital.hmpps.iis.search

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.util.SignOnBaseSpec

@Stepwise
class SearchFormSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }



    def 'each type of search is independent of the others'() {

        given: 'I am on the search page'
        to SearchPage

        and: 'I enter a valid name for prisoner 1'
        searchForm.nameAgeSearch.click()
        searchForm.using([
                surname: 'surnamea'
        ])

        and: 'I enter a valid address for prisoner 2'
        searchForm.otherSearch.click()
        searchForm.using([
                address: '4 street road'
        ])

        when: 'I submit the search'
        searchForm.submit

        then: 'I find prisoner B because the name from the other form was ignored'
        firstResultItem.find('.prisonNumber')[0].text().contains('AB111112')
    }


}
