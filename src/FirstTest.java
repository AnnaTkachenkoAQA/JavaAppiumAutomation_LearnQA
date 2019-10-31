import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception{

        DesiredCapabilities capabilities= new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "Users/anna/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

    }

    @After
    public void tearDown(){
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.quit();
    }

    @Test
    public void firstTest(){

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath ("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java''",
                15
        );
    }

    @Test
    public void testCancelSearch(){

        waitForElementAndClick (
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClear(
                By.id("search_src_text"),
                "Search field can not ne find",
                5
        );

        waitForElementAndClick (
                By.id("search_close_btn"),
                "Cannot find close search button",
                5
        );

        waitForElementNotPresent(
                By.id("search_close_btn"),
                "X is still present on the page",
                5
        );

    }

    @Test
    public void testCompareArticleTitle() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java''",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title);

    }

    @Test
    public void testCheckTextAtSearchField(){

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        WebElement search_field = waitForElementPresent(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Cannot find search input",
                5
        );

        String textAtSearchField = search_field.getAttribute("text");

        Assert.assertEquals(
                "Text at search field is unexpected",
                "Search…",
                textAtSearchField
        );
    }

    @Test
    public void testSearchAndCancel () {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "qa",
                "Cannot find search input",
                5
        );

        List <WebElement> topics = waitForElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Cannot find any topic searching by 'qa'",
                15
        );

        Assert.assertTrue("Found <= 1 results of searching", topics.size()>1);

        waitForElementAndClick (
                By.id("search_close_btn"),
                "Cannot find close search button",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Topics for searching by 'qa' are still displayed",
                5
        );


    }

    @Test
    public void testCheckSearchedWordAtResultsOfSearching () {

        String searchedValue= "qa";

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchedValue,
                "Cannot find search input",
                5
        );

        List<WebElement> topics = waitForElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "No such search results",
                15
        );

        for (WebElement element: topics){
            String topic_name  = element.getAttribute("text") ;
            topic_name = topic_name.toLowerCase();
            boolean isResultContainsSearchedWord =  topic_name.contains(searchedValue);
            Assert.assertTrue("Topic is not contained searched word", isResultContainsSearchedWord);
        }
    }

    @Test
    public void testSwipeArticle() {

       waitForElementAndClick(
                        By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                        "Cannot find 'Search Wikipedia' input",
                        5
                );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium' topic searching by 'Appium''",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Can not find the end of article",
                20
        );

    }

    @Test
    public void saveFirstArticleToMyList() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java''",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        waitForMenuToRender();

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Can not find option to adding article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Can not find 'Got it' overlay",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Can not find input for list's name",
                5
        );

        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Can not put text to the article's folder",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Can not press OK button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='"+ name_of_folder +"']"),
                "Cannot find created folder",
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Can not find saved article"
        );


        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String search_line= "Linkin park diskography";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        String search_result_locator="//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(search_result_locator),
                "Can not find anything by the request "+search_line,
                15
        );

        int amount_of_search_results = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue("We found too few results!", amount_of_search_results>0);
    }

    @Test
    public void testAmountOfEmptySearch(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String search_line= "zxcvasdfqwer";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        String search_result_locator="//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty result label by the request "+ search_line,
                15
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request "+search_line
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String search_line= "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " +search_line,
                15
        );

        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can not find title of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can not find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation);

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_2nd_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can not find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_2nd_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String search_line= "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " +search_line,
                5
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find article after returning from background" ,
                5
        );


    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String first_searched_value = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                first_searched_value,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + first_searched_value,
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        waitForMenuToRender();

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Can not find option to adding article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Can not find 'Got it' overlay",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Can not find input for list's name",
                5
        );

        String name_of_list = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_list,
                "Can not put text to the article's folder",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Can not press OK button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );


        //--------Searching the second article
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String second_searched_value="Appium";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                second_searched_value,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find topic searching by " +second_searched_value,
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        waitForMenuToRender();

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Can not find option to adding article to reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='"+name_of_list+"']"),
                "Can not find created folder with name " +name_of_list,
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='"+name_of_list+"']"),
                "Cannot find created folder",
                10
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Can not find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Appium']"),
                "Second article is not displayed at list",
                5
        );

       WebElement title_element= waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Title of article is not found",
                5
        );

        Assert.assertEquals( "Title of article has unexpected value",
                title_element.getAttribute("text"),
                second_searched_value);

    }

    @Test
    public void testAssertTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String first_searched_value = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                first_searched_value,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + first_searched_value,
                5
        );

        assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title"
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message+"\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message ) {
        return waitForElementPresent(by, error_message, 5);
    }

    private List <WebElement> waitForElementsPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message+"\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    private  WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 5);
        element.click();
        return  element;
    }

    private  WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        MobileElement element = (MobileElement) waitForElementPresent(by, error_message, 5);
        element.setValue(value);
        return  element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message+"\n");
        return  wait.until (ExpectedConditions.invisibilityOfElementLocated(by));

    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    protected void waitForMenuToRender () {
        waitForElementPresent(
                By.xpath("//*[@text='Change language']"),
                "Can not find option to change language",
                5);
        waitForElementPresent(
                By.xpath("//*[@text='Share link']"),
                "Can not find option to share article",
                5);
        waitForElementPresent(
                By.xpath("//*[@text='Add to reading list']"),
                "Can not find option to add article to list",
                5);
        waitForElementPresent(
                By.xpath("//*[@text='Find in page']"),
                "Can not find option to find smth at article",
                5);
       /* waitForElementPresent(
                By.xpath("//*[@text='Similar pages']"),
                "Can not find option to find similar articles",
                5); */
        waitForElementPresent(
                By.xpath("//*[@text='Font and theme']"),
                "Can not find option to change font and theme",
                5);
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);

        Dimension size = driver.manage().window().getSize();
        int x= size.width /2;
        int start_y = (int) (size.height*0.8);
        int end_y= (int) (size.height *0.2);

        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement (By by, String error_message, int max_swipes) {
        int already_swiped=0;
        while(driver.findElements(by).size() ==0) {
            if(already_swiped>max_swipes){
                waitForElementPresent(by, "Cannot find element by swiping up. \n" +error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }

    }

    protected void swipeElementToLeft(By by, String error_message){
       WebElement element= waitForElementPresent( by, error_message, 15);

       int left_x= element.getLocation().getX();
       int right_x = left_x + element.getSize().getWidth();
       int upper_y = element.getLocation().getY();
       int lower_y=upper_y+element.getSize().getHeight();
       int middle_y= (lower_y+upper_y)/2;

        TouchAction action = new TouchAction(driver);

        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent (By by, String error_message) {
        int amount_of_elements =  getAmountOfElements(by);
        if (amount_of_elements>0){
            String default_message = "An element " + by.toString() + " supposed to be not present";
            throw new AssertionError(default_message + " "+ error_message);
        }
    }

    private void assertElementPresent (By by, String error_message) {
        int amount_of_elements =  getAmountOfElements(by);
        if (amount_of_elements<0){
            String default_message = "An element " + by.toString() + " supposed to be present";
            throw new AssertionError(default_message + " "+ error_message);
        }
    }

    private String waitForElementAndGetAttribute (By by, String attribute, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent( by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}
