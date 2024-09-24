package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

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

public class BaseClass 
{
    public static WebDriver driver;
    public Logger logger;  // Static logger initialization
    public Properties pr;
    
    @BeforeClass(groups= {"Sanity","Regression","Master","DataDriven"})
    @Parameters({"os","browser"})
    public void setup(String os,String br) throws IOException, Exception
    {
        FileReader file = new FileReader("./src/test/resources/configure.properties");
            pr = new Properties();
            pr.load(file);
            
        logger = LogManager.getLogger(this.getClass());  //log4j2
        logger.info("Test setup is starting...");
       
        if(pr.getProperty("execution_env").equalsIgnoreCase("Remote"))
        {
        	logger.info("Test is Running....."+pr.getProperty("execution_env")+" Environment");
        	DesiredCapabilities capabilities=new DesiredCapabilities();
        	
        	//operating system
        	if(os.equalsIgnoreCase("windows")) {
        		capabilities.setPlatform(Platform.WIN10);
                logger.info(os+" Operating system selected");
        	}
        	else if(os.equalsIgnoreCase("mac")) {
        		capabilities.setPlatform(Platform.MAC);
        		logger.info(os+" Operating system selected");
        	}else {
        		logger.error("Invalid Operating system specified: " + os);
        		System.out.println("No Matching operating System");
        		return;
        	}
        	
        	//browser
        	 switch (br.toLowerCase()) {
             case "chrome":
            	 capabilities.setBrowserName("chrome");
                 logger.info("Chrome browser selected");
                 break;
             case "edge":
            	 capabilities.setBrowserName("MicrosoftEdge");
                 logger.info("Edge browser selected");
                 break;
             case "safari":
            	 capabilities.setBrowserName("safari");
                 logger.info("safari browser selected");
                 break;
             default:
                 logger.error("Invalid browser specified: " + br);
                 System.out.println("Invalid browser...");
                 return;
             }
        	 
        	 
        	// Connect to the Remote WebDriver or Selenium Grid
        	 URI gridUri = new URI("http://localhost:4444/wd/hub");
        	 driver = new RemoteWebDriver(gridUri.toURL(), capabilities);
        }
        
        
        if(pr.getProperty("execution_env").equalsIgnoreCase("Local")) {
        logger.info("Test is Running....."+pr.getProperty("execution_env")+" Environment");
        switch (br.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                logger.info("Chrome browser selected");
                break;
            case "edge":
                driver = new EdgeDriver();
                logger.info("Edge browser selected");
                break;
            case "firefox":
                driver = new FirefoxDriver();
                logger.info("Firefox browser selected");
                break;
            default:
                logger.error("Invalid browser specified: " + br);
                System.out.println("Invalid browser...");
                return;
              }
        
        }
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(pr.getProperty("url"));
        driver.manage().window().maximize();
        logger.info("Browser setup complete, navigated to: " +pr.getProperty("url"));
    }
    
    @AfterClass(groups= {"Sanity","Regression","Master","DataDriven"})
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
    
    public String CaptureScreen(String tname) {
    	String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());   //timeStamp
    	TakesScreenshot takesScreenshot=(TakesScreenshot)driver;
    	File sourcefile=takesScreenshot.getScreenshotAs(OutputType.FILE);
    	String targetFilepath=System.getProperty("user.dir") + "\\Screenshots\\"+ tname + "_" + timeStamp +".png";

    	File targetfile=new File(targetFilepath);
    	sourcefile.renameTo(targetfile);
    	return targetFilepath;
    }
    
    
}
