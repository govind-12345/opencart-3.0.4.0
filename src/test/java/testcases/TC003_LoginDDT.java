package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.MasterBaseClass;
import utilities.DataProviders;

/*
 Data is valid ----login success-----testPass-----logout
 Data is valid ----login failed-----testFail
 
 Data is invalid ----login success-----testFail-----logout
 Data is invalid ----login failed-----testPass
*/
public class TC003_LoginDDT extends MasterBaseClass
{	
	@Test(dataProvider = "LoginData", dataProviderClass=DataProviders.class , groups={"DataDriven"})
	public void verify_LoginDDT(String email,String pwd,String exp) {
		 logger.info("*****Starting TC003_LoginDDT*****");
		 
		// Common WebDriver setup
		    WebDriver currentdriver = getDriver();  // Use current thread's driver
		    
		 try {
			 //HomePage
			 HomePage hp=new HomePage(currentdriver);
			 hp.clickMyAccount();
			 hp.clickLogin();
			 
			 //LoginPage
			 LoginPage lp=new LoginPage(currentdriver);
			 lp.SetEmail(email);
			 lp.SetPassword(pwd);
			 lp.ClickLogin();
			 
			 //MyAccount
			 MyAccountPage macc=new MyAccountPage(currentdriver);
			 boolean targetPage=macc.isMyAccountPageExists();
			 
			 if(exp.equalsIgnoreCase("Valid")) {
				 if(targetPage==true) {
				 macc.ClickLogout();
				 Assert.assertTrue(true);
			 }else {
				 Assert.assertTrue(false);
			    }
			 }
		 
		 if(exp.equalsIgnoreCase("invalid")){
			 if(targetPage==true) {
			 macc.ClickLogout();
			 Assert.assertTrue(false);
		 }else {
			 Assert.assertTrue(false);
		    }
		 }
		 logger.info("*****Finished TC003_LoginDDT*****");
			 }
    catch(Exception e)
		 {
		Assert.fail();
	   }
	}
}
