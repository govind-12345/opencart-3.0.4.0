package testBase;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseClass2 
{
    public WebDriver driver;
    public Logger logger;  // Static logger initialization
    public Properties pr;
    
    @BeforeClass
    public void setup() throws IOException, Exception
    {
        FileReader file = new FileReader("./src/test/resources/configure.properties");
            pr = new Properties();
            pr.load(file);
            
        logger = LogManager.getLogger(this.getClass());  //log4j2
        logger.info("Test setup is starting...");
        
        driver=new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get(pr.getProperty("appurl"));
        driver.manage().window().maximize();
        Thread.sleep(5000);
        logger.info("Browser setup complete, navigated to: " +pr.getProperty("appurl"));
    }
    
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing the browser.");
            driver.quit();
        } else {
            logger.warn("Driver is null, nothing to quit.");
        }
    }
    
    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumber() {
        return RandomStringUtils.randomNumeric(10);
    }
    
    public String randomAlphaNumeric() {
        String randomString = RandomStringUtils.randomAlphabetic(3);
        String randomNumber = RandomStringUtils.randomNumeric(3);
        return (randomString + "@" + randomNumber);
    }
}
