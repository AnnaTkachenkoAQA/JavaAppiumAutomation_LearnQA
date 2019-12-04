package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static
    {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "css:a#ca-watch";

        OPTIONS_REMOVE_FROM_MYLISTS_BUTTON="css:a#ca-watch.watched";
        FIRST_WORD_AT_TITLE= "xpath://*[@id='mf-section-0']//b";//#mf-section-0 > p > b //*[@id="mf-section-0"]/p/b  //*[@id="mf-section-0"]/p[2]/b
        TITLE_FROM_CONTENTS_TPL="xpath://XCUIElementTypeStaticText[@name='{TITLE}']";


    }

    public MWArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }
}
