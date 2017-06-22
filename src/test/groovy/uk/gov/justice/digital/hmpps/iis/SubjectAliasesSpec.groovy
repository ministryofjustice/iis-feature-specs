package uk.gov.justice.digital.hmpps.iis

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.pages.SubjectDetailsPage
import uk.gov.justice.digital.hmpps.iis.pages.subject.AddressesPage
import uk.gov.justice.digital.hmpps.iis.pages.subject.AliasesPage

@Stepwise
class SubjectAliasesSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'List of aliases shown, excluding duplicates, and excluding the one matching loss-of-liberty'() {

        when: 'I view the aliases section'
        gotoAliasesPage([surname: 'surnamea'])

        then: 'I see the aliases for that subject'
        aliasList.size() == 2

        and: 'Aliases have name and dob correctly capitalized, when present'
        $('#alias0-name').text().equals('Othera A Aliasa')
        $('#alias0-dob').text().contains('01/01/1980')
        $('#alias1-name').text().equals('Otherb B Aliasb')
        $('#alias1-dob').text().contains('02/01/1980')
    }

//    def 'Alias is clickable to perform search for alias name + dob' () {
//
//        when: 'I view the aliases section'
//        gotoAliasesPage([surname: 'surnamea'])
//
//        and: 'I click the first alias'
//        $('#aliasSearch0').click()
//
//        then: 'I see the search results for that alias'
//        at SearchResultsPage
//    }


    private void gotoAliasesPage(query) {
        performSearch(query)
        resultItemLinks[0].click()
        at SubjectDetailsPage
        section('aliases')
        at AliasesPage
    }

    private void performSearch(query) {
        to SearchPage
        selectSearchOptions(['names'])
        proceed()
        searchForm.using(query)
    }
}
