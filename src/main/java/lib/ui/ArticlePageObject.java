package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject {

    protected static  String
            TITLE ,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            OPTIONS_CHANGE_LANGUAGE_BUTTON,
            OPTIONS_SHARE_LINK_BUTTON,
            OPTIONS_FIND_IN_PAGE_BUTTON,
            OPTIONS_FONT_AND_THEME_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            EXISTING_LIST_LINK_TPL,
            TITLE_FROM_CONTENTS_TPL,
            OPTIONS_CONTENTS_BUTTON,
            CLOSE_CONTENTS_BUTTON;


    public ArticlePageObject (AppiumDriver driver) {
        super(driver);
    }

    /*TEMPLATES METHODS*/
    private static String getListElement (String name_of_list) {
        return EXISTING_LIST_LINK_TPL.replace("{NAME_OF_LIST}", name_of_list );
    }

    private static String getArticleTitleFromContents (String article_title){
        return TITLE_FROM_CONTENTS_TPL.replace("{TITLE}", article_title);
    }
    /*TEMPLATES METHODS*/

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(TITLE, "Cannot find title of article", 15 );
    }

    public WebElement waitForTitleFromContents(String article_title){
        return this.waitForElementPresent(getArticleTitleFromContents(article_title),"Can not find article title from contents: "+article_title, 15);
    }

    public String getArticleTitle () {
        WebElement titleElement= waitForTitleElement();
        if(Platform.getInstance().isAndroid()){
            return titleElement.getAttribute("text");
        }else {
            return titleElement.getAttribute("name");
        }
    }

    public String getAttributeArticleTitleFromContents (String article_title) {
        WebElement titleElement = waitForTitleFromContents(article_title);
        if(Platform.getInstance().isAndroid()){
            return titleElement.getAttribute("text");
        } else {
            return titleElement.getAttribute("name");
        }
    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(FOOTER_ELEMENT, "Can not find the end of article", 40);
        }
        else{
            this.swipeUpTillElementAppear(FOOTER_ELEMENT,"Can not find the end of article", 40 );
        }
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

    public void addArticlesToMySaved(){
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can not find and click 'Add to saved' button",
                5
        );
    }

    public void clickContentsOption (){
        this.waitForElementAndClick(
                OPTIONS_CONTENTS_BUTTON,
                "Can not find and click Contents button",
                5
        );
    }

    public void closeContents(){
        this.waitForElementAndClick(
                CLOSE_CONTENTS_BUTTON,
                "Can not find and click CLose Contents button",
                5
        );
    }
}
