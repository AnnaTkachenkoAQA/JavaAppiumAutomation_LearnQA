package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;

abstract public class MyListsPageObject extends MainPageObject {

    protected static  String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            CLOSE_SYNC_POPUP;

    private static String getFolderXpathByName (String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String name_of_article) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", name_of_article);
    }


    public MyListsPageObject (AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName (String name_of_folder){

        String folder_name_xpath =getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name "+ name_of_folder,
                5
        );
    }

    public void waitForArticleToAppearByTitle(String article_title){
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Saved article is not present with title "+article_title,
                15
        );
    }

    public void waitForArticleToDisappearByTitle(String article_title){
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with title "+article_title,
                15
        );
    }

    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        String article_title_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(
                article_title_xpath,
                "Can not find saved article"
        );
        if(Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperCorner(article_title_xpath,"Can not find saved article");
        }

        this.waitForArticleToDisappearByTitle(article_title);
    }

   public void openArticleByTitle (String article_title) {
        String article_xpath= getSavedArticleXpathByTitle(article_title);
       this.waitForElementAndClick(
               article_xpath,
               "Can not find and open article at list",
               5
       );
   }

   public void closeSyncYourSavedArticlesPopup(){
        this.waitForElementAndClick(CLOSE_SYNC_POPUP, "Can not find and click close button for 'Sync your saved articles' pop-up", 5);
   }
}
