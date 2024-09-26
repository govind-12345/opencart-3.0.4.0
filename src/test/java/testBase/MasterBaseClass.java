package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class MasterBaseClass {
    public ThreadLocal<WebDriver> driver = new ThreadLocal<>();  // ThreadLocal to handle WebDriver instances for parallel execution
    public Logger logger;  // logger initialization
    public Properties pr;

    // Retrieve the correct WebDriver instance for the current thread
    public WebDriver getDriver() {
        return driver.get();
    }

    @BeforeClass(groups = {"Sanity", "Regression", "Master", "DataDriven"})
    @Parameters({"os", "browser"})
    public void setup(String os, String br) throws IOException, Exception {
        FileReader file = new FileReader("./src/test/resources/configure.properties");
        pr = new Properties();
        pr.load(file);

        logger = LogManager.getLogger(this.getClass());   // log4j2
        logger.info("Test setup is starting...");

        if (pr.getProperty("execution_env").equalsIgnoreCase("Remote")) {
            logger.info("Test is Running in Remote Environment");

            DesiredCapabilities capabilities = new DesiredCapabilities();

            // Operating system
            if (os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN10);
                logger.info(os + " Operating system selected");
            } else if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
                logger.info(os + " Operating system selected");
            }
            else if (os.equalsIgnoreCase("linux")) {
                capabilities.setPlatform(Platform.LINUX);
                logger.info(os + " Operating system selected");
            }else {
                logger.error("Invalid Operating system specified: " + os);
                throw new Exception("No Matching operating System");
            }

            // Browser
            switch (br.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    logger.info("Chrome browser selected");
                    break;
                case "edge":
                    capabilities.setBrowserName("MicrosoftEdge");
                    logger.info("Edge browser selected");
                    break;
                case "firefox":
                    capabilities.setBrowserName("firefox");
                    logger.info("firefox browser selected");
                    break;
                case "safari":
                    capabilities.setBrowserName("safari");
                    logger.info("Safari browser selected");
                    break;
                default:
                    logger.error("Invalid browser specified: " + br);
                    throw new Exception("Invalid browser specified.");
            }

            // Connect to the Remote WebDriver or Selenium Grid
            URI gridUri = new URI("http://localhost:4444/wd/hub");
            driver.set(new RemoteWebDriver(gridUri.toURL(), capabilities));

        } else if (pr.getProperty("execution_env").equalsIgnoreCase("Local")) {
            logger.info("Test is Running in Local Environment");

            // Browser
            switch (br.toLowerCase()) {
                case "chrome":
                    driver.set(new ChromeDriver());
                    logger.info("Chrome browser selected");
                    break;
                case "edge":
                    driver.set(new EdgeDriver());
                    logger.info("Edge browser selected");
                    break;
                case "firefox":
                    driver.set(new FirefoxDriver());
                    logger.info("Firefox browser selected");
                    break;
                default:
                    logger.error("Invalid browser specified: " + br);
                    throw new Exception("Invalid browser specified.");
            }
        }

        // Common WebDriver setup
        WebDriver CurrentDriver = getDriver();  // Retrieve the correct driver
        CurrentDriver.manage().deleteAllCookies();
        CurrentDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        CurrentDriver.get(pr.getProperty("url"));
        CurrentDriver.manage().window().maximize();
        logger.info("Browser setup complete, navigated to: " + pr.getProperty("url"));
    }

    @AfterClass(groups = {"Sanity", "Regression", "Master", "DataDriven"})
    public void tearDown() {
    	 // Common WebDriver setup
        WebDriver currentDriver = getDriver();   // Retrieve the correct driver
        
        if (currentDriver != null) {
            logger.info("Closing the browser.");
            currentDriver.quit();
            driver.remove();  // Remove the WebDriver instance for the current thread
        } else {
            logger.warn("Driver is null, nothing to quit.");
        }
    }

    // Generate random strings
    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomAlphaNumeric() {
        String randomString = RandomStringUtils.randomAlphabetic(3);
        String randomNumber = RandomStringUtils.randomNumeric(3);
        return randomString + "@" + randomNumber;
    }

    // Capture a screenshot
    public String CaptureScreen(String tname) {
        WebDriver currentDriver = getDriver();
        if (currentDriver == null) {
            logger.error("Cannot capture screenshot because WebDriver is not initialized.");
            return null;
        }

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) currentDriver;

        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String targetFilePath = System.getProperty("user.dir") + "\\Screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);
        sourceFile.renameTo(targetFile);
        return targetFilePath;
    }

}
