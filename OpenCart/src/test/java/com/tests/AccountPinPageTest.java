package com.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.pages.AccountPinPage;

public class AccountPinPageTest extends TestBase {
	WebDriver driver;
	AccountPinPage ap=null;
	@BeforeMethod
	public void setUp() {
		driver=initialization();
		ap=loadLoginPage(driver).navigateToAccountPin();
		
	}
	@Test
	public void verifyAccountPin() {
		Assert.assertTrue(ap.accountPin());
	}
	
			
}
