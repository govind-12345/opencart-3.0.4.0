package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.MasterBaseClass;

public class TC002_LoginTest extends MasterBaseClass
{
	@Test(groups= {"Sanity","Master"})
	public void verify_Login() {
		logger.info("*****Starting TC002_LoginTest*****");
		// Common WebDriver setup
	    WebDriver currentdriver = getDriver();  // Use current thread's driver
		
		try {
			//HomePage
			HomePage hp=new HomePage(currentdriver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			//LoginPage
			LoginPage lp=new LoginPage(currentdriver);
			lp.SetEmail(pr.getProperty("email"));
			lp.SetPassword(pr.getProperty("pwd"));
			lp.ClickLogin();
			
			//MyAccount
			MyAccountPage macc=new MyAccountPage(currentdriver);
			boolean targetPage=macc.isMyAccountPageExists();
			
			Assert.assertTrue(targetPage);  //Assert.assertEquals(targetPage, true,"Login failed");
		}
		catch(Exception e) {
			Assert.fail();
		}
		logger.info("*****Finished TC_002_LoginTest*****");
	}

}
