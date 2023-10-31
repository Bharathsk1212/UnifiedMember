package utility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.manager.SeleniumManager;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.reportportal.service.ReportPortal;


public class CommonUtils {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    public static WebDriver driver;
    static Properties prop = new Properties();
    static By email = By.xpath("//*[@id='email']");
    static By next = By.id("btnNext");
    static By pswd = By.xpath("//*[@id=\"password\"]");
    static By login = By.xpath("//*[@id=\"btnLogin\"]");
    static By confirm = By.xpath("//button[@id='payment-submit-btn' and contains(text(),'Continue')]");
    static By paypalLogo = By.xpath("//span[contains(@class,'MerchantLogo_text_2mWpM')]");
    static By paypalResave = By.xpath("//button[text()='Save']");
    static By cardNum = By.xpath("//input[@id='cardNumber']");
    static By cardHolderName = By.xpath("//input[@id='cardholderName']");
    static By expiryMonth = By.xpath("//input[@id='expiryMonth']");
    static By expiryYear = By.xpath("//input[@id='expiryYear']");
    static By cardCVV = By.xpath("//input[@id='securityCode']");
    static By submitForm = By.xpath("//input[@id='submitButton']");
    static By KlarnaPayLaterOption30Days = By.xpath("(//*[text()='Klarna-Pay later in 30 days'])[1]");
    static By KlarnaPayLaterOptionByInterestFree = By.xpath("(//*[text()='Klarna-Monthly financing'])[1]");
    static By KlarnaIcon = By.xpath("//input[@id='securityCode']");
    //	static By KlarnaContinueBtn = By.xpath("//img[@src='/images/buttons/makepayment.gif']");
    static By KlarnaContinueBtn = By.xpath("//a[@name = 'continue']");
    static By ProceedBtn = By.xpath("//a[contains(text(),'proceed')]");
    static By payNowButton = By.xpath("//button[contains(., 'Pay Now')]");
    static By loginNowButton = By.xpath("//*[@type='button' and contains(., 'Log In')]");
    static By PaypalAcceptCookieBtn = By.xpath("//button[text()='Accept All Cookies']");
    static By PaypalContinueBtn = By.xpath("//button[contains(text(), 'Continue')]");
    static By PaypalUINowBtn = By.xpath("//input[@type = 'submit']");
    static By PaypalCompletePurchaseBtn = By.xpath("//button[@id='payment-submit-btn']");


    private static CommonUtils instance;
    private static Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    private CommonUtils() {
        if (instance != null) {
            throw new RuntimeException();
        }
    }

    @Test
    public static void storeObjects(String cacheKey, Object value) {
        cache.put(cacheKey, value);
    }

    @Test
    public static Object getObjects(String cacheKey) {
        return cache.get(cacheKey);
    }

    @Test
    public static void clearObjects(String cacheKey) {
        cache.put(cacheKey, null);
    }

    @Test
    public static void clear() {
        cache.clear();
    }

    public static CommonUtils getInstance() {
        if (instance == null) {
            synchronized (CommonUtils.class) {
                if (instance == null) {
                    instance = new CommonUtils();
                }
            }
        }
        return instance;
    }

    private static int retryCounter = 0;

    @Test
    public static String getVoucherHash(String voucherNo, String order) throws InterruptedException, IOException {
        try {
            openBrowser(Constants.defaultBrowser,
                    Constants.getVoucherHashValue + voucherNo + "&webordernumber=" + order);
            return driver.findElement(By.xpath("/html/body")).getText();
        } finally {
            driver.quit();
        }
    }

    @Test
    public static String getfeedBackHashKey(String branch, String order, String storeid)
            throws InterruptedException, NoSuchAlgorithmException {
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//chromedriver.exe");
        driver.get("http://uat-wss2.util.ws/fbhashkey.php?branchKey=" + branch + "&orderId=" + order + "&storeId="
                + storeid);
        String data = driver.findElement(By.xpath("/html/body")).getText().trim();
        String hash = data.replaceAll("[^a-zA-Z0-9]", "");
        String getHash = hash.replaceAll("ArraybranchKeyWZorderId" + order + "storeId19000", "");
        System.out.println(getHash);
        driver.quit();
        return getHash;
    }

    @Test
    public static void openBrowser(String browserType, String url) throws IOException, InterruptedException {
        if (browserType.equalsIgnoreCase("Chrome")) {
            String env = System.getenv("JOB_NAME");
            DesiredCapabilities cap = new DesiredCapabilities();
            if (env == null || env.length() == 0) {
                ChromeOptions op = new ChromeOptions();
                SeleniumManager.getInstance().getDriverPath(op, false).getDriverPath();
                op.addArguments("--headless=new");
                op.addArguments("disable-infobars");
                op.merge(cap);
                driver = new ChromeDriver(op);
                Thread.sleep(10000);
                Dimension dm = new Dimension(1920, 1080);
                driver.manage().window().setSize(dm);
            } else {
                WebDriverManager.chromedriver().setup();
                ChromeOptions op = new ChromeOptions();
                op.addArguments("--headless=new");
                op.addArguments("disable-infobars");
                op.merge(cap);
                driver = new ChromeDriver(op);
                Thread.sleep(10000);
                Dimension dm = new Dimension(1920, 1080);
                driver.manage().window().setSize(dm);
            }
            System.out.println("window size" + driver.manage().window().getSize());
        }
        driver.get(url);
        String checkUrl = driver.getCurrentUrl();
        String title = driver.getTitle();
        System.out.println("browser title" + title);
        if (url.equals(checkUrl)) {
            System.out.println("url is open on browser" + checkUrl);
        } else {
            try {
                driver.get(url);
            } catch (Exception e) {
                e.printStackTrace();
                captureScreenOnFailure();
                System.out.println("url is not open on browser" + e);
            }

        }
    }

    public static void closeBrowser() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void moveToElementAndClick(By element) throws InterruptedException {

        try {
            WebElement webElement = driver.findElement(element);
            Point hoverItem = webElement.getLocation();
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("return window.title;");
            Thread.sleep(1000);
            executor.executeScript("window.scrollBy(0," + (hoverItem.getY()) + ");");
            executor.executeScript("arguments[0].click();", webElement);
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
            captureScreenOnFailure();
        }
    }

    public static void captureScreenOnFailure() {
        if (driver != null) {
            try {
                TakesScreenshot ts = (TakesScreenshot) driver;
                File srcFile = ts.getScreenshotAs(OutputType.FILE);
                java.util.Date d = new java.util.Date();
                org.apache.commons.io.FileUtils.copyFile(srcFile,
                        new File("./ScreenShots/" + d.toString().replace(":", "_") + ".png"));
                myImage(srcFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void myImage(File fileName) {
        ReportPortal.emitLog("attached screenshot - ReportPortal.emitLog", "INFO", Calendar.getInstance().getTime(),
                fileName);
    }

    public static void waitForElement(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            e.printStackTrace();
            captureScreenOnFailure();
        }
    }

    public static boolean paypalSaveIcon(By locator) {
        try {
            driver.findElement(locator).click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementPresent(By locator) {
        try {
            return driver.findElements(locator).size() > 0;
        } catch (Exception e) { // TODO Auto-generated
            return false;
        }
    }

    // public static void click( By locator) throws IOException {
    // while(retryCounter<=5) {
    // try {
    // WebElement element = driver.findElement(locator);
    // new Actions(driver).moveToElement(element).moveToElement(element).click()
    // .build().perform();
    // break;
    // }
    // catch(Exception e) {
    // retryCounter++;
    // }
    // }
    // }
    public static void click(int time, By locator) {
        while (retryCounter <= 5) {
            try {
                for (int i = 0; i < time; i++) {
                    if (isElementPresent(locator)) {
                        break;
                    }
                }
                WebElement element = driver.findElement(locator);
                new Actions(driver).moveToElement(element).moveToElement(element).click().build().perform();
                break;
            } catch (Exception e) {
                if (retryCounter == 5) {
                    captureScreenOnFailure();
                }
                retryCounter++;
            }
        }
    }
    /*
     * Below keyword takes single array of integer It converts first integer array
     * to String array and then takes part of list using sublist method Author -
     * Ranjan G
     */

    @Test
    public static List<String> paginateListdata(ArrayList<Integer> stringArray, int paginate) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            for (int stringValue : stringArray) {
                // Convert String to Integer, and store it into integer array list.
                result.add(String.valueOf(stringValue));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> limitArray = result.subList(1, paginate);
        return limitArray;
    }

    public static InputStream getInputStream(String str, String encoding) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(str.getBytes(encoding));
    }

    public static String extractData(String data) throws JSONException, IOException {
        String[] arr = null;
        String[] getRightDat = null;
        int count = 0;
        InputStream input = getInputStream(data, "UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader((input)));
        String result = "";
        String output = null;
        while ((result = br.readLine()) != null) {
            output = result.replace("[", "").replace("]", "");
            if (count == 0 && output.contains("orderKey")) {
                arr = data.split("\\?");
                getRightDat = arr[1].toString().split(",");
                count++;
            } else {
                break;
            }
        }
        return getRightDat[0];
    }

    public static String kidXPayPalId(String data) throws JSONException, IOException {
        String[] arr = null;
        String paypalData = null;
        InputStream input = getInputStream(data, "UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader((input)));
        String result = "";
        String output = null;
        while ((result = br.readLine()) != null) {
            output = result.replace("[", "").replace("]", "");
            arr = data.split("&");
            for (String s : arr) {
                if (s.contains("paymentId")) {
                    String[] param = s.toString().split("=");
                    paypalData = param[1].replace("[", "").replace("]", "");
                    break;
                }
            }
        }
        return paypalData;
    }

    @Test
    public static int searchStore(ArrayList<Integer> storeId1, ArrayList<Integer> storeId2)
            throws JSONException, IOException {
        int n = storeId1.size();
        int m = storeId2.size();
        int temp = 0;
        for (int i = 0; i < n; i++) {
            if (temp != 0)
                break;
            for (int j = 0; j < m; j++) {
                if (storeId1.get(i) == storeId2.get(j)) {
                    temp = storeId1.get(i);
                    System.out.println(temp);
                    System.out.println("Common Store ID for both boxes is : " + temp);
                    break;
                }

            }

        }
        return temp;
    }

    /*
     * Below method matches with given string and return the value author- Ranjan
     * Gupta
     */
    public static Pattern regexForString(String s) {
        return Pattern.compile("Test Store");
    }

    public static String extractDynamicDat(String browserType, String url, String paypalUserid, String paypalPswd)
            throws Exception {
        String networkData = null;
        openBrowser(browserType, url);
        if (isElementPresent(PaypalAcceptCookieBtn)) {
            click(1, PaypalAcceptCookieBtn);
        }
        Thread.sleep(10000);

        if (isElementPresent(loginNowButton))
            click(1, loginNowButton);
        waitForElement(email);
        driver.findElement(email).sendKeys(paypalUserid);
        String testdata = driver.findElement(email).getText().toString();
        System.out.println(testdata);
        waitForElement(next);
        click(2, next);

        waitForElement(pswd);
        driver.findElement(pswd).sendKeys(paypalPswd);
        String testdatapassword = driver.findElement(email).getText().toString();
        System.out.println(testdatapassword);
        waitForElement(login);
        click(1, login);
        Thread.sleep(10000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement element = driver.findElement(PaypalCompletePurchaseBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
        driver.findElement(PaypalCompletePurchaseBtn).click();
//		wait.until(ExpectedConditions.textMatches(paypalLogo, regexForString("John Doe's Test Store")));
//		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(confirm)));
//		click(4, confirm);
        Thread.sleep(5000);
        String scriptToExecute = "var performance = window.performance || window.mozPerformance || "
                + "window.msPerformance || window.webkitPerformance || {};"
                + " var network = performance.getEntries() || {}; return network;";
        networkData = ((JavascriptExecutor) driver).executeScript(scriptToExecute).toString();
        return networkData;
    }

    public static String getWorldPayForKidX(String browserType, String url, String cardNo, String holder, String month,
                                            String Year, String cardCVVNo) throws Exception {
        String networkData = null;
        openBrowser(browserType, url);
        waitForElement(cardNum);
        driver.findElement(cardNum).sendKeys(cardNo);
        waitForElement(cardHolderName);
        driver.findElement(cardHolderName).sendKeys(holder);
        waitForElement(expiryMonth);
        driver.findElement(expiryMonth).sendKeys(month);
        waitForElement(expiryYear);
        driver.findElement(expiryYear).sendKeys(Year);
        waitForElement(cardCVV);
        driver.findElement(cardCVV).sendKeys(cardCVVNo);
        waitForElement(submitForm);
        click(1, submitForm);
        Thread.sleep(10000);
        String scriptToExecute = "var performance = window.performance || window.mozPerformance || "
                + "window.msPerformance || window.webkitPerformance || {};"
                + " var network = performance.getEntries() || {}; return network;";
        networkData = ((JavascriptExecutor) driver).executeScript(scriptToExecute).toString();
        return networkData;
    }

    public static String getWorldpayData(String browserType, String url, String cardNo, String holder, String month,
                                         String Year, String cardCVVNo) throws Exception {
        openBrowser(browserType, url);
        String url_main = driver.getCurrentUrl();
        System.out.println("============World Pay URL===========" + url_main);
        waitForElement(cardNum);
        driver.findElement(cardNum).sendKeys(cardNo);
        waitForElement(cardHolderName);
        driver.findElement(cardHolderName).sendKeys(holder);
        waitForElement(expiryMonth);
        driver.findElement(expiryMonth).sendKeys(month);
        waitForElement(expiryYear);
        driver.findElement(expiryYear).sendKeys(Year);
        waitForElement(cardCVV);
        driver.findElement(cardCVV).sendKeys(cardCVVNo);
        waitForElement(submitForm);
        moveToElementAndClick(submitForm);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlContains("orderKey"));
        System.out.println(driver.getCurrentUrl());
        String data = driver.getCurrentUrl();
        String[] arr = data.split("\\?");
        System.out.println(arr[1]);
        return arr[1];
    }

    public static String encodeBase64(String data) {
        String encodedString = Base64.getEncoder().withoutPadding().encodeToString(data.getBytes());
        return encodedString;
    }

    @Test
    public static String extractDynamicDatFromPaypalUrl(String browserType, String url, String paypalUserid,
                                                        String paypalPswd) throws Exception {
        String paypalData = null;
        openBrowser(browserType, url);

        if (isElementPresent(loginNowButton)) {
            click(1, loginNowButton);
            waitForElement(email);
            driver.findElement(email).sendKeys(paypalUserid);
            click(2, next);
            waitForElement(pswd);
            driver.findElement(pswd).sendKeys(paypalPswd);
            driver.findElement(login).click();
            Thread.sleep(10000);
            WebElement element = driver.findElement(payNowButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(500);
            driver.findElement(payNowButton).click();
            Thread.sleep(10000);
            driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
            String data = driver.getCurrentUrl();
            String[] arr = data.split("&");
            for (String s : arr) {
                if (s.contains("token")) {
                    String[] param = s.toString().split("=");
                    paypalData = param[1].replace("[", "").replace("]", "");
                    System.out.println("Token----" + paypalData);
                }
            }
        } else {
            waitForElement(email);
            driver.findElement(email).sendKeys(paypalUserid);
            waitForElement(next);
            click(2, next);
            waitForElement(pswd);
            driver.findElement(pswd).sendKeys(paypalPswd);
            waitForElement(login);
            click(1, login);
            Thread.sleep(10000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            //wait.until(ExpectedConditions.textMatches(paypalLogo, regexForString("John Doe's Test Store")));
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(confirm)));
            click(4, confirm);
            waitForElement(paypalResave);
            paypalSaveIcon(paypalResave);
            Thread.sleep(10000);
            driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
            String data = driver.getCurrentUrl();
            String[] arr = data.split("&");
            for (String s : arr) {
                if (s.contains("paymentId")) {
                    String[] param = s.toString().split("=");
                    paypalData = param[1].replace("[", "").replace("]", "");
                }
            }
        }
        return paypalData;
    }

    @Test
    public static String extractDynamicDatFromPaypalUrlforDeX(String browserType, String url, String paypalUserid,
                                                              String paypalPswd) throws Exception {
        String paypalData = null;
        Thread.sleep(10000);
        openBrowser(browserType, url);
        Thread.sleep(40000);
        if (isElementPresent(PaypalAcceptCookieBtn)) {
            click(1, PaypalAcceptCookieBtn);
        }
        Thread.sleep(10000);
        WebElement m = driver.findElement(By.linkText("Log In"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", m);
        Thread.sleep(5000);
        waitForElement(email);
        driver.findElement(email).sendKeys(paypalUserid);
        click(2, next);
        waitForElement(pswd);
        driver.findElement(pswd).sendKeys(paypalPswd);
        driver.findElement(login).click();
        String url_main = driver.getCurrentUrl();
        System.out.println("============PAYPAL URL===========" + url_main);
        Thread.sleep(40000);
        if (isElementPresent(PaypalContinueBtn)) {
            driver.findElement(PaypalContinueBtn).click();
            Thread.sleep(10000);
            driver.findElement(PaypalUINowBtn).click();
        } else if (driver.findElement(PaypalCompletePurchaseBtn).isDisplayed()) {
            WebElement element = driver.findElement(PaypalCompletePurchaseBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(500);
            driver.findElement(PaypalCompletePurchaseBtn).click();
        } else {
            WebElement element = driver.findElement(payNowButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(500);
            driver.findElement(payNowButton).click();
        }
        Thread.sleep(10000);
        driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
        String data = driver.getCurrentUrl();
        String[] arr = data.split("&");
        for (String s : arr) {
            if (s.contains("token")) {
                String[] param = s.toString().split("=");
                paypalData = param[1].replace("[", "").replace("]", "");
                System.out.println("Token----" + paypalData);
            }
        }
        return paypalData;
    }

    /*
     * Below utility method identifies the Operating System and kills driver &
     * opened browser at run time; Author Rarya
     */
    public static void killProcess() throws Exception {
        System.out.println(System.getProperty("os.name"));
        if (System.getProperty("os.name").equalsIgnoreCase("MAC OS X")) {
            Runtime.getRuntime().exec(Constants.macKillDriver);
            Runtime.getRuntime().exec(Constants.macKillBrowser);

        } else if (System.getProperty("os.name").contains("Linux")) {
            Runtime.getRuntime().exec(Constants.linuxKillBrowser);
        } else {
            Runtime.getRuntime().exec(Constants.windKillBrowser);
            Runtime.getRuntime().exec(Constants.WindKillDriver);
        }
    }

    @Test
    public static String getUnescapedData(String data) {
        return StringEscapeUtils.unescapeJava(data);

    }

    @Test
    public static String getCurrentDate(String date) throws InterruptedException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date dat = new Date();
        System.out.println(dateFormat.format(dat)); // 2016/11/16 12:08:43
        return date;

    }
    /*
     * Create date modifier for utility purpose
     */

    @Test
    public static String getDefinedDate(String date) throws InterruptedException, ParseException {
        String inputText = date;
        DateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        String outputText = null;
        try {
            Date format = inputFormat.parse(inputText);
            outputText = outputFormat.format(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputText;
    }

    public static String toString(String data) {
        return String.format(data);
    }
    /*
     * Create date modifier for utility purpose
     */

    @Test
    public static String getExpiryDate(String date) throws InterruptedException, ParseException {
        String inputText = date;
        DateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.s", Locale.US);
        String outputText = null;
        try {
            Date format = inputFormat.parse(inputText);
            outputText = outputFormat.format(format);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputText;
    }

    @Test
    public static String getRollnum() {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * CHARS.length());
            salt.append(CHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    @Test
    public static String getAccntNum() {
        String CHARS = "1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * CHARS.length());
            salt.append(CHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    @Test
    public static String getRandomString() {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZaaccddffvvwwsdtegfjddsdsdkjklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * CHARS.length());
            salt.append(CHARS.charAt(index));
        }
        String saltStr = salt.toString().toUpperCase();
        return saltStr;
    }
    /*
     * Utility method to wait for given time Author @Rarya
     */

    @Test
    public static String getRandomStringWithLowerCase() {
        String CHARS = "aaccddffvvwwsdtegfjddsdsdkjklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * CHARS.length());
            salt.append(CHARS.charAt(index));
        }
        String saltStr = salt.toString().toLowerCase();
        return saltStr;
    }
    /*
     * Utility method to wait for given time Author @Rarya
     */

    public static void hardWait(Integer data) throws NumberFormatException, InterruptedException {
        Thread.sleep(data * 1000L);
    }

    @Test
    public static List<String> convertIntarrtoString(List<Integer> intArray, int paginate) {
        List<String> result = new ArrayList<String>();
        try {
            for (int i = 0; i < intArray.size(); i++) {
                // Convert Integer to String, and store it into string array list.
                result.add(String.valueOf(intArray.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> limitArray = result.subList(1, paginate);
        // System.out.println(limitArray);
        return limitArray;
    }

    @Test
    public static int convertStringArrayToInt(List<Integer> IntArray1, List<Integer> IntArray2) {
        int temp = 0;
        try {

            for (int i = 0; i < IntArray1.size(); i++) {
                if (temp != 0)
                    break;
                for (int j = 0; j < IntArray2.size(); j++) {
                    if (IntArray1.get(i).equals(IntArray2.get(j))) {
                        temp = IntArray1.get(i);
                        System.out.println(temp);
                        System.out.println("Common Store ID for both boxes is : " + temp);
                        break;
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Test
    public static List<String> makeStringsublist(List<String> strArray, int paginate) {
        List<String> result = new ArrayList<String>();
        try {
            for (int i = 0; i < strArray.size(); i++) {
                // Convert Integer to String, and store it into string array list.
                result.add(String.valueOf(strArray.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> limitArray = result.subList(1, paginate);
        return limitArray;
    }

    public static String getKlarnaPaymentGatway(String browserType, String url, String KlarnaPaymentOption)
            throws Exception {
        openBrowser(browserType, url);
        String option = "Klarna-Pay later in 30 days";
        if (option.equalsIgnoreCase(KlarnaPaymentOption)) {
            waitForElement(KlarnaPayLaterOption30Days);
            click(1, KlarnaPayLaterOption30Days);
        } else {
            waitForElement(KlarnaPayLaterOptionByInterestFree);
            click(2, KlarnaPayLaterOptionByInterestFree);
        }
//		waitForElement(KlarnaContinueBtn);
//		click(2, KlarnaContinueBtn);
        Thread.sleep(500);
        driver.findElement(KlarnaContinueBtn).click();
        System.out.println(driver.getCurrentUrl());
        String data = driver.getCurrentUrl();
        String[] arr = data.split("\\?");
        System.out.println(arr[1]);
        return arr[1];
    }

    public static List<String> getSplitStringData(String SplitString) {
        StringBuffer alpha = new StringBuffer(),
                num = new StringBuffer(), special = new StringBuffer();
        for (int i = 0; i < SplitString.length(); i++) {
            if (Character.isDigit(SplitString.charAt(i)))
                num.append(SplitString.charAt(i));
            else if (Character.isAlphabetic(SplitString.charAt(i)))
                alpha.append(SplitString.charAt(i));
            else
                special.append(SplitString.charAt(i));
        }
        String key = alpha.toString();
        String orderId = num.toString();
        System.out.println(key);
        System.out.println(orderId);
        ArrayList<String> anotherList = new ArrayList<String>();
        anotherList.add(key);
        anotherList.add(orderId);
        System.out.println(anotherList);
        return anotherList;
    }

    public static String getUpdateVoucher(String branchKey, String orderId) {
        return "update voucher set Redeemed = 0 , Cancelled= 0 where BranchKey ='" + branchKey + "' and OrderID=" + orderId;
    }

    public static String extractDynamicDatFromPaypalUrlForCeX(String browserType, String url, String paypalUserid,
                                                              String paypalPswd) throws Exception {
        String paypalData = null;
        openBrowser(browserType, url);
        Thread.sleep(10000);
        if (isElementPresent(PaypalAcceptCookieBtn)) {
            click(1, PaypalAcceptCookieBtn);
        }
        Thread.sleep(5000);
        if (isElementPresent(loginNowButton))
            click(1, loginNowButton);
        //WebElement m=driver.findElement(By.linkText("Log In"));
//		WebElement m=driver.findElement(By.xpath("loginButton"));
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript("arguments[0].click();", m);
        Thread.sleep(5000);
        //click(1, loginNowButton);
        waitForElement(email);
        driver.findElement(email).sendKeys(paypalUserid);
        click(2, next);
        waitForElement(pswd);
        driver.findElement(pswd).sendKeys(paypalPswd);
        driver.findElement(login).click();
        Thread.sleep(10000);
        WebElement element = driver.findElement(PaypalCompletePurchaseBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
        driver.findElement(PaypalCompletePurchaseBtn).click();
        Thread.sleep(10000);
        driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
        String data = driver.getCurrentUrl();
        String[] arr = data.split("&");
        for (String s : arr) {
            if (s.contains("token")) {
                String[] param = s.toString().split("=");
                paypalData = param[1].replace("[", "").replace("]", "");
                System.out.println("Token----" + paypalData);
            }
        }
        return paypalData;
    }

    public static String extractDynamicDatFromPaypalDex1(String browserType, String url, String paypalUserid,
                                                         String paypalPswd) throws Exception {
        String paypalData = null;
        openBrowser(browserType, url);
        Thread.sleep(40000);
        if (isElementPresent(PaypalAcceptCookieBtn)) {
            click(1, PaypalAcceptCookieBtn);
        }
        Thread.sleep(10000);

        if (isElementPresent(loginNowButton)) {
            click(1, loginNowButton);
            waitForElement(email);
            driver.findElement(email).sendKeys(paypalUserid);
            waitForElement(next);
            click(2, next);
            waitForElement(pswd);
            driver.findElement(pswd).sendKeys(paypalPswd);
            driver.findElement(login).click();
            Thread.sleep(10000);
            WebElement element = driver.findElement(PaypalCompletePurchaseBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            element.click();
            String data = driver.getCurrentUrl();
            String[] arr = data.split("&");
            for (String s : arr) {
                if (s.contains("token")) {
                    String[] param = s.toString().split("=");
                    paypalData = param[1].replace("[", "").replace("]", "");
                    System.out.println("Token----" + paypalData);
                }
            }
        } else {
            waitForElement(email);
            driver.findElement(email).sendKeys(paypalUserid);
            driver.findElement(pswd).sendKeys(paypalPswd);
            if (isElementPresent(login))
                click(1, login);
            Thread.sleep(10000);
            WebElement element = driver.findElement(PaypalCompletePurchaseBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(500);
            element.click();
        }
        String data = driver.getCurrentUrl();
        String[] arr = data.split("&");
        for (String s : arr) {
            if (s.contains("paymentId")) {
                String[] param = s.toString().split("=");
                paypalData = param[1].replace("[", "").replace("]", "");
            }
        }
        return paypalData;
    }

    public static ArrayList<String> getLimitedBoxesIds(String boxeIds) {
        String strg = boxeIds.replaceAll("\\[", "").replaceAll("\\]", "");
        List<String> myList = new ArrayList<String>(Arrays.asList(strg.split(",")));
        ArrayList<String> box = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            box.add(myList.get(i).replace("\"", ""));
        }
        return box;
    }

    public static Object getNumberOnlyFromString(String Data) {
        String numberOnly= Data.replaceAll("[^0-9]", "");
        return Integer.parseInt(numberOnly);
    }

}
