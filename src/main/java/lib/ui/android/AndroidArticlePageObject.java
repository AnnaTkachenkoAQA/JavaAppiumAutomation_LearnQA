package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidArticlePageObject extends ArticlePageObject {

    static
    {
        TITLE = "id:org.wikipedia:id/view_page_title_text";
        FOOTER_ELEMENT = "xpath://*[@text='View page in browser']";
        OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']";
        ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button";
        MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
        MY_LIST_OK_BUTTON = "xpath://*[@text='OK']";
        OPTIONS_CHANGE_LANGUAGE_BUTTON = "xpath://*[@text='Change language']";
        OPTIONS_SHARE_LINK_BUTTON = "xpath://*[@text='Share link']";
        OPTIONS_FIND_IN_PAGE_BUTTON = "xpath://*[@text='Find in page']";
        OPTIONS_FONT_AND_THEME_BUTTON = "xpath://*[@text='Font and theme']";
        CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@*='Navigate up']";
        EXISTING_LIST_LINK_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{NAME_OF_LIST}']";
    }

    public AndroidArticlePageObject (RemoteWebDriver driver){
        super(driver);
    }
}
