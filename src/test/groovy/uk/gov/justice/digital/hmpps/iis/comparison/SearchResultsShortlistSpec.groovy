package uk.gov.justice.digital.hmpps.iis.comparison

import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.iis.pages.SearchPage
import uk.gov.justice.digital.hmpps.iis.pages.SearchResultsPage
import uk.gov.justice.digital.hmpps.iis.util.SignOnBaseSpec

@Stepwise
class SearchResultsShortlistSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Hide add to shortlist option when only one result'() {

        when: 'one search result'
        performSearch([surname: 'surnamea'])

        then: 'I do not see the add to shortlist link'
        !addToShortlistLinks.isPresent()
    }

    def 'Show add to shortlist option when 2 or more results'() {

        when: 'multiple search results'
        performSearch([surname: 'sur%']);

        then: 'Each result has an add to shortlist link'
        addToShortlistLinks.size() == 10
        addToShortlistLinks[0].value().contains('Add to shortlist')
    }

    def 'Add to shortlist shows notice and changes link to remove'() {

        when: 'multiple search results'
        performSearch([surname: 'sur%']);

        then: 'I see the add to shortlist link'
        addToShortlistLinks[0].value().contains('Add to shortlist')

        when: 'I click add to shortlist'
        addToShortlistLinks[0].click()

        then: 'I see the notice'
        shortlistNotice.verifyNotEmpty()
        shortlistNotice.text().contains('added to the shortlist')

        and: 'The link is now remove from shortlist'
        addToShortlistLinks[0].value().contains('Remove from shortlist')
    }

    def 'when 2 or more in the shortlist, notice shows link to comparison view and tab shows count'(){

        given: 'multiple search results'
        performSearch([surname: 'sur%']);

        when: 'I add two prisoners to shortlist'
        addToShortlistLinks[0].click()
        addToShortlistLinks[1].click()

        then: 'The notice shows the link to compare'
        shortlistNotice.find('a', text: 'Compare prisoners')[0].verifyNotEmpty()

        and:
        tabs.comparisonTab.text().contains('Comparison (2)')
    }

    def 'When shortlist is full, hide add to shortlist links'() {

        given: 'multiple search results'
        performSearch([surname: 'sur%']);

        when: 'I fill the shortlist'
        addToShortlistLinks[0].click()
        addToShortlistLinks[1].click()
        addToShortlistLinks[2].click()

        then: 'I see the remove from shortlist links but no add to shortlist links'
        addToShortlistLinks.size() == 3
        addToShortlistLinks.filter(value: 'Add to shortlist').size() == 0
        addToShortlistLinks.filter(value: 'Remove from shortlist').size() == 3
    }

    def 'When I remove from shortlist the notice message updates or clears'() {

        given: 'multiple search results'
        performSearch([surname: 'sur%']);

        and: 'I add two to the shortlist'
        addToShortlistLinks[0].click()
        addToShortlistLinks[1].click()

        when: 'I remove one from the shortlist'
        addToShortlistLinks[0].click()

        then: 'I see the notice'
        shortlistNotice.verifyNotEmpty()
        shortlistNotice.text().contains('removed from the shortlist')

        when: 'I empty the shortlist'
        addToShortlistLinks[1].click()

        then: 'I no longer see the notice'
        !shortlistNotice.isPresent()
    }


    private void performSearch(query) {
        to SearchPage
        searchForm.nameAge(query)
        at SearchResultsPage
    }
}
