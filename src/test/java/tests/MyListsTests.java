package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private static final  String name_of_folder = "Learning programming";
    private static final String login ="a_tkachenko_default",
                                password="gmailcom";

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

        if(Platform.getInstance().isMW()){
            AuthorizationPageObject Auth=new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            assertEquals("We are not on the same page after login",
                    article_title,
                    ArticlePageObject.getArticleTitle());

            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        if(Platform.getInstance().isIOS()){
            SearchPageObject.clickCancelSearch();
        }

        NavigationUI NavigationUI= NavigationUIFactory.get(driver);

        NavigationUI.openNavigation();
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
        else if (Platform.getInstance().isIOS()){
            ArticlePageObject.clickContentsOption();
            ArticlePageObject.waitForTitleFromContents("Java (programming language)");
            ArticlePageObject.closeContents();
            ArticlePageObject.addArticlesToMySaved();
        }
        else {
            String first_word_of_article =ArticlePageObject.waitForContentOfArticle();
            assertEquals("",
                    "Java",
                    first_word_of_article);
            try {
                Thread.sleep(500);
            }catch(Exception e){}

            ArticlePageObject.addArticlesToMySaved();

            AuthorizationPageObject Auth=new AuthorizationPageObject(driver);

            try {
                Thread.sleep(1000);
            }catch(Exception e){}

            Auth.clickAuthButton();

            try {
                Thread.sleep(1000);
            }catch(Exception e){}

            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();


        if(Platform.getInstance().isIOS()){
            SearchPageObject.clickCancelSearch();
        }

        //--------Searching the second article
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(second_searched_value);
        SearchPageObject.clickByArticleWithTitle("Appium");


        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.waitForTitleElement();
            ArticlePageObject.addArticleToExistingList(name_of_folder);
        }
        else if (Platform.getInstance().isIOS()){
            ArticlePageObject.clickContentsOption();
            ArticlePageObject.waitForTitleFromContents("Appium");
            ArticlePageObject.closeContents();
            ArticlePageObject.addArticlesToMySaved();
        }
        else{
            String first_word_of_article =ArticlePageObject.waitForContentOfArticle();
            assertEquals("",
                    "Appium",
                    first_word_of_article);

            try {
                Thread.sleep(500);
            }catch(Exception e){}
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        if(Platform.getInstance().isIOS()){
            SearchPageObject.clickCancelSearch();
        }

        NavigationUI NavigationUI=NavigationUIFactory.get(driver);

        try {
            Thread.sleep(100);
        }catch(Exception e){}
        NavigationUI.openNavigation();

        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if(Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(name_of_folder);
        }
        else if (Platform.getInstance().isIOS()){
            MyListsPageObject.closeSyncYourSavedArticlesPopup();
        }


        MyListsPageObject.swipeByArticleToDelete("Java (programming language)");

        MyListsPageObject.openArticleByTitle("Appium");

        String title_of_second_article;
        if(Platform.getInstance().isAndroid()) {
            title_of_second_article = ArticlePageObject.getArticleTitle();
        }
        else if (Platform.getInstance().isIOS()) {
            ArticlePageObject.clickContentsOption();
            title_of_second_article = ArticlePageObject.getAttributeArticleTitleFromContents("Appium");
        }
        else {
            title_of_second_article= ArticlePageObject.waitForContentOfArticle();
        }

        assertEquals( "Title of article has unexpected value",
                title_of_second_article,
                second_searched_value);
    }
}
