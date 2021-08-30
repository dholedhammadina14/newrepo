package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

import com.utils.Utility;

public class AccountPinPage extends TestBase{
	WebDriver driver;
	public AccountPinPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy (id="input-pin")
	private WebElement txtPin;
	@FindBy (xpath="//button[@type='submit']")
	private WebElement btnContinue;
	@FindBy (partialLinkText="Edit your account details")
	private WebElement accountDetails;
	public boolean accountPin() {
		Utility.sendkeys(txtPin, readProperty("pin"));
		Utility.submit(btnContinue);
		String actTitle=driver.getTitle();
		String expTitle="OpenCart - Your Account";
		if(actTitle.equals(expTitle)) {
			System.out.println("Account Page is open. ");
			return true;
		}
		else {
			System.out.println("pin is wrong. ");
			return false;
		}
	}
	public AccountEditPage navigateToAccountEditPage() {
		accountDetails.click();
		return new AccountEditPage(driver);
	}
		
	
}
