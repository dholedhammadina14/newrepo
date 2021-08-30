package com.utils;

import org.openqa.selenium.WebElement;

public class Utility {

	public static void sendkeys(WebElement ele,String text) {
		ele.clear();
		ele.sendKeys(text);
	}
	public static  void click(WebElement ele) {
		ele.click();
	}
	public static String getText(WebElement ele) {
		return ele.getText();
	}
	public static void submit(WebElement ele) {
		ele.submit();
	}
}
