package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSArticlePageObject extends ArticlePageObject {
    static
    {
        TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
        TITLE_FROM_CONTENTS_TPL="xpath://XCUIElementTypeStaticText[@name='{TITLE}']";
        OPTIONS_CONTENTS_BUTTON="id:Table of contents";
        CLOSE_CONTENTS_BUTTON="id:Close Table of contents";
    }

    public IOSArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }
}
