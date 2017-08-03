package uk.gov.justice.digital.hmpps.iis

import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.pages.SuggestionsPage

@Stepwise
class SearchSuggestionsSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'searches with no suggestable terms do not show suggestions link'() {

        when: 'I do a search that generates no suggestions'
        performSearch('names', [forename: 'a'])

        then: 'I do not see the suggestions link'
        !suggestionsLink.isPresent()
    }

    def 'searches with suggestions show suggestions link'() {

        when: 'I do a search that generates sugegstions'
        performSearch('names', [forename: 'first', surname: 'last'])

        then: 'I see the suggestions link'
        suggestionsLink.isPresent()
    }

    def 'forename search suggests using initial'(){

        when: 'I view the suggestions for a search by forename'
        viewSuggestionsFor('names', [forename: 'first'])

        then: 'I see the suggestion - search by initial'
        $('a', text: 'Search using an initial only in first name:').isDisplayed()

        and: 'The suggested initial is shown'
        $('#forenameInitialSuggestion').text() == 'F'
    }

    def 'surname search suggests using wildcard or shortened with wildcard'() {

        when: 'I view the suggestions for a search by surname'
        viewSuggestionsFor('names', [surname: 'last'])

        then: 'I see the suggestion - add wilcard'
        $('a', text: 'Add a wildcard to the surname:').isDisplayed()

        and: 'The suggested wildcard is shown'
        $('#surnameWildcardSuggestion').text() == 'Last%'

        and: 'I see the suggestion - shorten and add wilcard'
        $('a', text: 'Shorten the surname by 2 characters and add a wildcard:').isDisplayed()

        and: 'The suggested shortened wildcard is shown'
        $('#surnameShortWildcardSuggestion').text() == 'La%'
    }

    def 'forename and surname search suggests swapping'() {

        when: 'I view the suggestions for a search by forename and surname'
        viewSuggestionsFor('names', [forename: 'first', surname: 'last'])

        then: 'I see the suggestion - swap names'
        $('a', text: 'Swap surname and forename:').isDisplayed()

        and: 'The suggested wildcard is shown'
        $('#nameSwapSuggestion').text() == 'Last, First'
    }

    def 'No suggestion for surname that already has wildcard'(){

        when: 'I view the suggestions for a search with a wildcarded surname'
        performSearch('names', [surname: 'last%'])

        then: 'I do not see the suggestions link'
        !suggestionsLink.isPresent()
    }

    def 'No shorten suggestion when surname is already short'(){

        when: 'I view the suggestions for a search by surname'
        viewSuggestionsFor('names', [surname: 'la'])

        then: 'I see the suggestion - add wilcard'
        $('a', text: 'Add a wildcard to the surname:').isDisplayed()

        and: 'I do not see the suggestion to shorten and add wildcard'
        ! $('a', text: 'Shorten the surname by 2 characters and add a wildcard:').isDisplayed()
    }

    def 'Dob search suggests age range' () {

        when: 'I view the suggestions for a search by age'
        viewSuggestionsFor('dob', [dobDay  : '1',
                                   dobMonth: '1',
                                   dobYear : '1980'])

        then: 'I see the suggestion - age range'
        $('a', text: 'Change the date of birth to an age range:').isDisplayed()

        and: 'The suggested wildcard is shown'
        $('#dobAgeRangeSuggestion').text() == '35-39'
    }

    def 'Age search suggests age range' () {

        when: 'I view the suggestions for a search by age'
        viewSuggestionsFor('dob', [age  : '30'])

        then: 'I see the suggestion - age range'
        $('a', text: 'Change the age to an age range:').isDisplayed()

        and: 'The suggested wildcard is shown'
        $('#ageAgeRangeSuggestion').text() == '28-32'
    }

    def 'Clicking a suggestion applies the suggestion to the search'() {

        when: 'I search by a surname that has no matches'
        performSearch('names', [surname: 'surname'])

        then: 'I see there are no results'
        resultItems.size() == 0

        when: 'I view the suggestions'
        suggestionsLink.click()

        then: 'I see the suggestion - add wilcard'
        $('a', text: 'Add a wildcard to the surname:').isDisplayed()

        when: 'I click the suggestion'
        $('a', text: 'Add a wildcard to the surname:').click()

        then: 'I see matching results'
        resultItems.size() == 10
    }


    def viewSuggestionsFor(option, query){
        performSearch(option, query)
        suggestionsLink.click()
        at SuggestionsPage
    }

    def performSearch(option, query) {
        to SearchPage
        selectSearchOptions([option])
        proceed()
        if(query['age']) {searchType('age')}
        searchForm.using(query)

        at SearchResultsPage
    }
}
