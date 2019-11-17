package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

public class AndroidNavigationUI extends NavigationUI {

    static {
        MY_LISTS_LINK="xpath://android.widget.FrameLayout[@*='My lists']";
    }

    public AndroidNavigationUI (AppiumDriver driver){
        super(driver);
    }
}
