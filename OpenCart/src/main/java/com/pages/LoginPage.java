package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;
import com.utils.Utility;

public class LoginPage extends TestBase{
	WebDriver driver =null;
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy (id="input-email")
	private WebElement txtEmail;
	@FindBy (id="input-password")
	private WebElement txtPassword;
	@FindBy (xpath="//button[@type='submit']")
	private WebElement btnSignin;
	@FindBy (partialLinkText="Create an OpenCart account")
	private WebElement Register;
	@FindBy (id="input-pin")
	private WebElement txtPin;
	@FindBy (xpath="//button[@type='submit']")
	private WebElement btnContinue;
	
	public boolean login() {
		Utility.sendkeys(txtEmail,readProperty("email"));
		Utility.sendkeys(txtPassword, readProperty("password"));
		Utility.submit(btnSignin);
		String actTitle=driver.getTitle();
		String expTite="Account PIN";
		if(actTitle.equals(expTite)) {
			pageLogs().info("Account PIN is Open.");
			return true;
		}else {
			pageLogs().info("something is wrong.");
			return false;
		}
	}
	public AccountPinPage navigateToAccountPin() {
		Utility.sendkeys(txtEmail,readProperty("email"));
		Utility.sendkeys(txtPassword, readProperty("password"));
		Utility.submit(btnSignin);
		return new AccountPinPage(driver);
	}
	public RegisterPage navigateToRegisterPage() {
		Utility.click(Register);
		return new RegisterPage(driver);
	}

}
