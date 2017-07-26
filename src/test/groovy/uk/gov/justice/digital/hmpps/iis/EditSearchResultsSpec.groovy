package uk.gov.justice.digital.hmpps.iis

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.EditDobPage
import uk.gov.justice.digital.hmpps.iis.pages.EditNamesPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage

@Stepwise
class EditSearchResultsSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'can edit a name search'() {

        when: 'I perform an name search and click to edit'
        performSearchAndClickEditFirstTerm(['names'], [surname: 'surnamea'])

        and: 'I change surname'
        searchForm.using([
                surname: 'surname%'
        ])

        then: 'I see the search results page'
        at SearchResultsPage

        and: 'I see the number of results returned'
        searchResultHeading.text().contains('12')
    }

    def 'can edit an identifier search'() {

        when: 'I perform an name search and click to edit'
        performSearchAndClickEditFirstTerm(['identifier'], [prisonNumber: 'AB111111'])

        and: 'I change the prison number'
        searchForm.using([
                prisonNumber: 'AB111112'
        ])

        then: 'I see the search results page'
        at SearchResultsPage

        and: 'I see the number of results returned'
        searchResultHeading.text().contains('1')
    }

    def 'can edit a dob search'() {

        when: 'I perform a dob search and click to edit'
        performSearchAndClickEditFirstTerm(['dob'], [age: '35-40'])

        and: 'I choose age search'
        searchType('age')

        and: 'I change age range'
        searchForm.using([
                age: '40-50'
        ])

        then: 'I see the search results page'
        at SearchResultsPage

        and: 'I see the number of results returned'
        searchResultHeading.text().contains('9')
    }

    private void performSearchAndClickEditFirstTerm(searchOptions, query) {
        to SearchPage
        selectSearchOptions(searchOptions)
        proceed()
        if (searchOptions == ['dob']) {
            searchType('age')
        }
        searchForm.using(query)

        at SearchResultsPage
        firstSearchTermLink.click()

    }
}
