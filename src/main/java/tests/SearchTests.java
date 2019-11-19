package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();

    }

    @Test
    public void testAmountOfNotEmptySearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String search_line= "Linkin park discography";
        SearchPageObject.typeSearchLine(search_line);

        int amount_of_search_results= SearchPageObject.getAmountOfFoundArticles();

        assertTrue("We found too few results!", amount_of_search_results>0);
    }

    @Test
    public void testAmountOfEmptySearch(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();

        String search_line= "zxcvasdfqwer";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultsOfSearch();
    }

    @Test
    public void testCheckTextAtSearchField(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        if(Platform.getInstance().isAndroid()) {
            String textAtSearchField = SearchPageObject.getTextFromEmptySearchField();

            assertEquals(
                    "Text at search field is unexpected",
                    "Search…",
                    textAtSearchField
            );
        }
        else{
            String textAtSearchField = SearchPageObject.getTextFromEmptySearchFieldForIos();

            assertEquals(
                    "Text at search field is unexpected",
                    "Search Wikipedia",
                    textAtSearchField
            );
        }
    }

    @Test
    public void testSearchAndCancel () {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("qa");
        int amount_of_articles= SearchPageObject.getAmountOfFoundArticles();

        assertTrue("Found <= 1 results of searching", amount_of_articles>1);

        if(Platform.getInstance().isAndroid()) {
            SearchPageObject.clickCancelSearch();
        }
        else{
            SearchPageObject.clickClearSearchField();
        }

        SearchPageObject.waitForSearchResultToDisappear();
    }

    @Test
    public void testCheckSearchedWordAtResultsOfSearching () {

        String searchedValue= "qa";
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchedValue);

        String[] topics;

        if(Platform.getInstance().isAndroid()) {
            topics = SearchPageObject.getAllTopicsAtSearchResults();
        }
        else {
            topics = SearchPageObject.getAllTopicsAtSearchResultsForIos();
        }

        for (int i=0; i<topics.length-1; i++){
            boolean isResultContainsSearchedWord =  topics[i].contains(searchedValue);
            assertTrue("Topic is not contained searched word", isResultContainsSearchedWord);
        }
    }
}
