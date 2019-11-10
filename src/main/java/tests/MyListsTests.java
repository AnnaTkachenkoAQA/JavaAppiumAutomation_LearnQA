package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();

        String article_title =ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";

        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI= new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);

    }

    @Test
    public void testSaveTwoArticlesToMyList() {

        String first_searched_value = "Java";
        String second_searched_value="Appium";
        String name_of_list = "Learning programming";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(first_searched_value);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.addArticleToMyList(name_of_list);
        ArticlePageObject.closeArticle();

        //--------Searching the second article
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(second_searched_value);
        SearchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject.waitForTitleElement();

        ArticlePageObject.addArticleToExistingList(name_of_list);

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI=new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_list);
        MyListsPageObject.swipeByArticleToDelete("Java (programming language)");

        MyListsPageObject.openArticleByTitle("Appium");

        String title_of_second_article= ArticlePageObject.getArticleTitle();

        assertEquals( "Title of article has unexpected value",
                title_of_second_article,
                second_searched_value);
    }
}
