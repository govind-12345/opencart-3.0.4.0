package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.MasterBaseClass;

public class TC001_AccountRegistractionTest extends MasterBaseClass
{	
	@Test(groups= {"Regression","Master"})
	public void TC001_AccountRegistrationTest() throws Exception
	{
	logger.info("*****Starting TC001_AccountRegisterationPage*****");
	// Common WebDriver setup
    WebDriver currentdriver = getDriver();  // Use current thread's driver
	
	try {
	HomePage hp=new HomePage(currentdriver);
	hp.clickMyAccount();
	logger.info("Clicked on MyAccountLnk......");
	hp.clickRegister();
	logger.info("Clicked on RegisterLnk......");
	
	
	AccountRegistrationPage regpage=new AccountRegistrationPage(currentdriver);
    logger.info("Providing customer details....");
	regpage.setfirstname(randomString().toUpperCase());
	regpage.setlastname(randomString().toUpperCase());
	regpage.setemail(randomString()+"@gmail.com");
	regpage.setTelephone(randomNumber());
	
	String password=randomAlphaNumeric();
	regpage.setpassword(password);
	regpage.setConfirm_password(password);
	regpage.setrd_btn_no();
	regpage.setpolicy_checkbox();
	
	regpage.clickcontinue();
	logger.info("Clicked on Continous button.....");
	
	logger.info("validating expected message.....");
	String confmsg=regpage.getConfirmationMsg();
//	Assert.assertEquals(confmsg,"Your Account Has Been Created");      // if this line after more line then this line is not use because execution end.
    
	if(confmsg.equals("Your Account Has Been Created!")) {
		Assert.assertTrue(true);
	}else {
		logger.error("Test failed......");
		logger.debug("Debug logs...");
		Assert.assertTrue(false);
	}
	}catch(Exception e) {
		Assert.fail();
	}
	logger.info("*****finished TC001_AccountRegestrationTest*****");
	}
}
