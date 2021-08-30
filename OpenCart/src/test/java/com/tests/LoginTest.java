package com.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.pages.LoginPage;

public class LoginTest extends TestBase{
	LoginPage lp;
	@BeforeMethod
	public void setUp() {
		driver=initialization();
		lp=new LoginPage(driver);
	}
	@Test
	public void verifyLogin() {
		Assert.assertTrue(lp.login());
	}
}
