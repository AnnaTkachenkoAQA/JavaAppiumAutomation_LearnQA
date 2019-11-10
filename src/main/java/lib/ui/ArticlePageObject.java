package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE ="id:org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT= "xpath://*[@text='View page in browser']",
            OPTIONS_BUTTON="xpath://android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON="xpath://*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY="id:org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT= "id:org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON= "xpath://*[@text='OK']",
            OPTIONS_CHANGE_LANGUAGE_BUTTON="xpath://*[@text='Change language']",
            OPTIONS_SHARE_LINK_BUTTON="xpath://*[@text='Share link']",
            OPTIONS_FIND_IN_PAGE_BUTTON="xpath://*[@text='Find in page']",
            OPTIONS_FONT_AND_THEME_BUTTON="xpath://*[@text='Font and theme']",
            CLOSE_ARTICLE_BUTTON="xpath://android.widget.ImageButton[@*='Navigate up']",
            EXISTING_LIST_LINK_TPL="xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{NAME_OF_LIST}']";

    public ArticlePageObject (AppiumDriver driver) {
        super(driver);
    }

    /*TEMPLATES METHODS*/
    private static String getListElement (String name_of_list) {
        return EXISTING_LIST_LINK_TPL.replace("{NAME_OF_LIST}", name_of_list );
    }
    /*TEMPLATES METHODS*/

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(TITLE, "Cannot find title of article", 15 );
    }

    public String getArticleTitle () {
        return waitForTitleElement().getAttribute("text");
    }

    public void swipeToFooter(){
        this.swipeUpToFindElement(FOOTER_ELEMENT, "Can not find the end of article", 20);
    }

    public void addArticleToMyList (String name_of_folder){
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForOptionsMenuToRender();

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can not find option to adding article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Can not find 'Got it' overlay",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Can not find input for list's name",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Can not put text to the article's folder",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Can not press OK button",
                5
        );

    }

    public void waitForOptionsMenuToRender() {
        waitForElementPresent(
                OPTIONS_CHANGE_LANGUAGE_BUTTON,
                "Can not find option to change language",
                5);
        waitForElementPresent(
                OPTIONS_SHARE_LINK_BUTTON,
                "Can not find option to share article",
                5);
        waitForElementPresent(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can not find option to add article to list",
                5);
        waitForElementPresent(
                OPTIONS_FIND_IN_PAGE_BUTTON,
                "Can not find option to find smth at article",
                5);
        waitForElementPresent(
                OPTIONS_FONT_AND_THEME_BUTTON,
                "Can not find option to change font and theme",
                5);
    }

    public void closeArticle(){
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link",
                5
        );
    }

    public void addArticleToExistingList(String name_of_list) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForOptionsMenuToRender();

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can not find option to adding article to reading list",
                5
        );

        this.waitForElementAndClick(
                getListElement(name_of_list),
                "Can not find created folder with name " +name_of_list,
                5
        );
    }

    public void assertThereIsTitleOfArticle(){
        this.assertElementPresent(
                TITLE,
                "Cannot find article title"
        );

    }
}
