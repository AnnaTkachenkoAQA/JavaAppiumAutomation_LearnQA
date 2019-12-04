package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:body > div.mw-overlays-container > div > div.overlay-header-container.header-container.header-chrome.position-fixed > div > div.header-action > button";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class, 'wikidata-description')][contains(text(), '{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
        SEARCH_RESULT_TITLE = "css:a.title";
        SEARCH_CLEAR_TEXT="css:button.clear";
        SEARCH_RESULT_TITLE_TPL="xpath://ul/li[contains(@title, '{TITLE}')]";
    }


    public MWSearchPageObject (RemoteWebDriver driver){
        super(driver);
    }
}
