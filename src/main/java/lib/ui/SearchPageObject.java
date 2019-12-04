package lib.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

abstract public class SearchPageObject extends MainPageObject {

      protected static String
            SEARCH_INIT_ELEMENT ,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_RESULT_TITLE,
            SEARCH_CLEAR_TEXT,
            SEARCH_RESULT_TITLE_TPL;


    public SearchPageObject (RemoteWebDriver driver) {
        super(driver);
    }

    /*TEMPLATES METHODS*/
    private static String getResultSearchElement (String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}",substring );
    }
    private static String getResultSearchElementFromTitle (String title) {
        return SEARCH_RESULT_TITLE_TPL.replace("{TITLE}",title );
    }
    /*TEMPLATES METHODS*/


    public void initSearchInput() {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find 'Search Wikipedia' input",
                5);
        this.waitForElementPresent(
                SEARCH_INIT_ELEMENT,
                "Cannot find 'Search Wikipedia' input after clicking",
                5);
    }

    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find close search button",
                5
        );
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present",
                5
        );
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click close search button",
                5
        );
    }

    public void clickClearSearchField(){
        this.waitForElementAndClick(
                SEARCH_CLEAR_TEXT,
                "Cannot find and click clear search field button",
                5
        );
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cannot find and type into search input",
                5
        );
    }


    public void waitForSearchResult(String substring) {
        String search_result_xpath= getResultSearchElement(substring);
        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find search results with substring "+substring
        );
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath= getResultSearchElement(substring);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search results with substring "+substring,
                10
        );
    }

    public void clickByArticleWithTitle(String title){
        String search_result_xpath= getResultSearchElementFromTitle(title);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search results with substring "+title,
                10
        );
    }

    public int getAmountOfFoundArticles (){
       this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Can not find anything by the request",
                15
        );
       return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel (){
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Can not find empty result element", 15);
    }

    public void assertThereIsNoResultsOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    public String getTextFromEmptySearchField(){
        return this.waitForElementAndGetAttribute(SEARCH_INPUT, "text","Can not find search input", 5);
    }

    public String getTextFromEmptySearchFieldForIos(){
        return this.waitForElementAndGetAttribute(SEARCH_INPUT, "name","Can not find search input", 5);
    }

    public String getTextFromEmptySearchFieldForMW(){
        return this.waitForElementAndGetAttribute(SEARCH_INPUT, "placeholder","Can not find search input", 5);
    }

    public String[] getAllTopicsAtSearchResults(){
        List<WebElement> topics = this.waitForElementsPresent(
                SEARCH_RESULT_TITLE,
                "Cannot find any topic searching by 'qa'",
                15
        );

        String[] topics_title ;
        topics_title =new String [topics.size()];

        for (WebElement element: topics){
            for(int i=0;i<topics.size();i++) {
                String topic_name = element.getAttribute("text");
                topic_name = topic_name.toLowerCase();
                topics_title[i]=topic_name;
            }
        }
        return topics_title;
    }

    public String[] getAllTopicsAtSearchResultsForIos(){
        List<WebElement> topics = this.waitForElementsPresent(
                SEARCH_RESULT_TITLE,
                "Cannot find any topic searching by 'qa'",
                15
        );

        String[] topics_title ;
        topics_title =new String [topics.size()];

        for (WebElement element: topics){
            for(int i=0;i<topics.size();i++) {
                String topic_name = element.getAttribute("name");
                topic_name = topic_name.toLowerCase();
                topics_title[i]=topic_name;
            }
        }
        return topics_title;
    }


    public void waitForSearchResultToDisappear(){
        this.waitForElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "Topics for searching are still displayed",
                5
        );
    }

}
