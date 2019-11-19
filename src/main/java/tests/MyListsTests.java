package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private static final  String name_of_folder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title =ArticlePageObject.getArticleTitle();

        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        }
        else {
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        if(Platform.getInstance().isIOS()){
            SearchPageObject.clickCancelSearch();
        }

        NavigationUI NavigationUI= NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderByName(name_of_folder);
        }
        else{
            MyListsPageObject.closeSyncYourSavedArticlesPopup();
        }

        MyListsPageObject.swipeByArticleToDelete(article_title);

    }

    @Test
    public void testSaveTwoArticlesToMyList() {

        String first_searched_value = "Java";
        String second_searched_value="Appium";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(first_searched_value);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.waitForTitleElement();
            ArticlePageObject.addArticleToMyList(name_of_folder);
        }
        else {
            ArticlePageObject.clickContentsOption();
            ArticlePageObject.waitForTitleFromContents("Java (programming language)");
            ArticlePageObject.closeContents();
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();
        if(Platform.getInstance().isIOS()){
            SearchPageObject.clickCancelSearch();
        }

        //--------Searching the second article
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(second_searched_value);
        SearchPageObject.clickByArticleWithSubstring("Appium");


        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.waitForTitleElement();
            ArticlePageObject.addArticleToExistingList(name_of_folder);
        }
        else {
            ArticlePageObject.clickContentsOption();
            ArticlePageObject.waitForTitleFromContents("Appium");
            ArticlePageObject.closeContents();
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        if(Platform.getInstance().isIOS()){
            SearchPageObject.clickCancelSearch();
        }

        NavigationUI NavigationUI=NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if(Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(name_of_folder);
        }
        else{
            MyListsPageObject.closeSyncYourSavedArticlesPopup();
        }

        MyListsPageObject.swipeByArticleToDelete("Java (programming language)");
        MyListsPageObject.openArticleByTitle("Appium");

        String title_of_second_article;
        if(Platform.getInstance().isAndroid()) {
            title_of_second_article = ArticlePageObject.getArticleTitle();
        }
        else {
            ArticlePageObject.clickContentsOption();
            title_of_second_article = ArticlePageObject.getAttributeArticleTitleFromContents("Appium");
        }

        assertEquals( "Title of article has unexpected value",
                title_of_second_article,
                second_searched_value);
    }
}
