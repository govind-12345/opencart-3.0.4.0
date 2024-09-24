package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRegistrationPage extends BasePage
{
	//constructor
	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}
	
	//Locators
	@FindBy(xpath="//input[@id='input-firstname']") WebElement textfirstname;
	@FindBy(xpath="//input[@id='input-lastname']") WebElement textlastname;
	@FindBy(xpath="//input[@id='input-email']") WebElement textemail;
	@FindBy(xpath="//input[@id='input-telephone']") WebElement texttelephone;
	@FindBy(xpath="//input[@id='input-password']") WebElement textpassword;
	@FindBy(xpath="//input[@id='input-confirm']") WebElement textconfirmpassword;
	@FindBy(xpath="//label[normalize-space()='Yes']") WebElement rdbuttonyes;
	@FindBy(xpath="//input[@value='0']") WebElement rdbuttonno;
	@FindBy(xpath="//input[@name='agree']") WebElement policycheckbox;
	@FindBy(xpath="//input[@value='Continue']") WebElement btncontinue;
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']") WebElement msgconfirmation;
	
	//ActionMethods
	
	public void setfirstname(String FirstName) {
		textfirstname.sendKeys(FirstName);
	}
	
	public void setlastname(String LastName) {
		textlastname.sendKeys(LastName);
	}
	
	public void setemail(String Email) {
		textemail.sendKeys(Email);
	}
	
	public void setTelephone(String Telephone_number) {
		texttelephone.sendKeys(Telephone_number);
	}
	
	public void setpassword(String Password) {
		textpassword.sendKeys(Password);
	}
	
	public void setConfirm_password(String Password) {
		textconfirmpassword.sendKeys(Password);
	}
	
	public void setrd_btn_yes() {
		rdbuttonyes.click();;
	}
	
	public void setrd_btn_no() {
		rdbuttonno.click();
	}
	
	public void setpolicy_checkbox() {
		policycheckbox.click();
	}
	
	public void clickcontinue() {
		//sol1
		btncontinue.click();
		
		//sol2
		//btncontinuos.submit();
		
		//sol3
		//Actions act=new Actions(driver);
		//act.moveToElement(btncontinue).click().perform();
		
		//sol4
		//JavascriptExecutor js=(JavascriptExecutor)driver;
		//js.executeScript("arguments[0].click();",btncontinue);
		
		//sol5
		//btncontinue.sendKeys(Keys.RETURN);
		
		//sol6
		//WebDriverWait mywait=new WebDriverWait(driver,Duration.ofSeconds(10));
		//mywait.until(ExpectedConditions.elementToBeClickable(btncontinue));
	}
	
	public String getConfirmationMsg() {
		try {
			return msgconfirmation.getText();
		}catch(Exception e) {
			return (e.getMessage());
		}
	}

}
