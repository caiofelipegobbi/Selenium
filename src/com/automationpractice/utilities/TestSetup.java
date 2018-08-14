package com.automationpractice.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TestSetup {
    public static RemoteWebDriver driver;
    protected static String browser = "chrome";

    //For log generation
    protected static ExtentReports logger = new ExtentReports();
    protected static ExtentHtmlReporter htmlReporter;
    protected static ExtentTest test;
    protected static ExtentTest nodeLog;

    //Path that will save the logs
    protected static String folderName;
    protected final static String home = System.getProperty("user.dir");

    //Method to take screenshots and attach it to the log
    public static void takeScreenshot(String fileName, String description) {
        try {
            String filePath = home + "/logs/" + folderName + "/screenshot/" + fileName;
            File scrFile = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(filePath));
            test.info(description, MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Method to take a screenshot and append it to a node inside of a main log.
    public static void takeScreenshotNode(String fileName, String description, ExtentTest nodeLog) {
        try {
            String filePath = home + "/logs/" + folderName + "/screenshot/" + fileName;
            File scrFile = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(filePath));
            nodeLog.info(description, MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void takeScreenshotElement(String fileName, String description, org.openqa.selenium.WebElement element) {
        String filePath = home + "//qaAutomationSuites//Portal//logs//" + folderName + "//screenshot//" + fileName;
        File scrFile = driver.getScreenshotAs(OutputType.FILE);

        //Scroll into view
        try {
            driver.executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(1500);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        try {
            BufferedImage fullImg = ImageIO.read(scrFile);
            // Get the location of element on the page
            org.openqa.selenium.Point point = element.getLocation();
            // Get width and height of the element
            int eleWidth = element.getSize().getWidth()+5;
            int eleHeight = element.getSize().getHeight()+5;

            // Crop the entire page screenshot to get only element screenshot
            BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
            ImageIO.write(eleScreenshot, "png", scrFile);

            // Copy the element screenshot to disk
            File screenshotLocation = new File(filePath);
            FileUtils.copyFile(scrFile, screenshotLocation);
            test.info(description, MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }



    //Method used to create a new driver.
    public static void createDriver (){
        String osName = System.getProperty("os.name");
        String downloadFilepath;

        //Each browser has its driver created differently
        if(browser.equals("chrome")){
            if(osName.contains("Linux") || osName.contains("Mac OS X")){
                System.setProperty("webdriver.chrome.driver", "browsers/chromedriver");
            }
            else{
                System.setProperty("webdriver.chrome.driver", "browsers/chromedriver.exe");
            }
            downloadFilepath = home + "/downloads";
            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("plugins.plugins_disabled", new String[] {"Adobe Flash Player", "Chrome PDF Viewer"});
            chromePrefs.put("profile.default_content_settings.state.flash", 0);
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", downloadFilepath);
            ChromeOptions optionsChrome = new ChromeOptions();
            optionsChrome.setExperimentalOption("prefs", chromePrefs);
            DesiredCapabilities cap = DesiredCapabilities.chrome();
            cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            cap.setCapability(ChromeOptions.CAPABILITY, optionsChrome);
            cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

            driver = new ChromeDriver(cap);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        } else if(browser.equals("firefox")){
            if(osName.contains("Linux")){
                System.setProperty("webdriver.gecko.driver", "browsers/geckodriver");
            }else{
                System.setProperty("webdriver.gecko.driver", "browsers/geckodriver.exe");
            }

            FirefoxOptions optionsFirefox = new FirefoxOptions();

        }

    }


    //Method used to end the test.
    public static void quit () {
        try {
            if (driver != null) {
                driver.quit();
            } else {
                createDriver();
                quit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void beforeSuite() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        // get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        folderName = dateFormat.format(cal.getTime());

        File outputFolder = new File(home + "/logs/" + folderName);
        if (!outputFolder.exists()) {
            if(!outputFolder.mkdir()) {
                System.out.println("Failed to create Extent Report Directory.");
            }
        }
        htmlReporter = new ExtentHtmlReporter(outputFolder + "/log.html");
        logger.attachReporter(htmlReporter);

    }

    @AfterSuite
    public void afterSuite() {
        logger.flush();
    }

}
