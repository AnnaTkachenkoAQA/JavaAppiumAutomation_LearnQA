package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static  String
            MY_LISTS_LINK="xpath://android.widget.FrameLayout[@*='My lists']";

    public NavigationUI (AppiumDriver driver) {
        super(driver);
    }


    public void clickMyLists (){
       this.waitForElementAndClick(
                MY_LISTS_LINK,
                "Cannot find navigation button to My lists",
                5
        );
    }
}
