package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage
{
	//constructor
	public HomePage(WebDriver driver){
		super(driver);
	}

	//Locators
	@FindBy(xpath="//i[@class='fa fa-user']") WebElement lnkMyAccount;
	@FindBy(xpath="(//a[normalize-space()='Register'])[1]") WebElement lnkRegister;
	@FindBy(linkText="Login") WebElement lnklogin;
	
	
	//Action Methods
	  public void clickMyAccount() {
		  lnkMyAccount.click();
	    }

	    public void clickRegister() {
	    	lnkRegister.click();
	    }
	    
	    public void clickLogin() {
	    	lnklogin.click();
	    }
}
