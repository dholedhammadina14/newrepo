package com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.pages.LoginPage;

public class TestBase {
	public static WebDriver driver=null;
	FileInputStream fis=null;
	LoginPage lp;
	public static Logger log;
	public ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest extentTest;
	
	public String readProperty(String key) {
		Properties prop=new Properties();
		try {
			fis=new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/config.properties");
			prop.load(fis);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
	public void launchBrowser() {
		driver.get(readProperty("url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	public WebDriver initialization() {
		log.info("initialising a browser with name "+readProperty("browser"));
		if(readProperty("browser").equals("chrome")){
		System.setProperty("webdriver.chrome.driver", "E:/chromedriver.exe");
		driver=new ChromeDriver();
		launchBrowser();
		log.info("chrome browser initialised.");
		return driver;
		}else {
			System.setProperty("webdriver.gecko.driver", "E:\\chromedriver.exe");
			driver=new FirefoxDriver();
			launchBrowser();
			log.info("firefox browser initialised.");
			return driver;
		}
	}
	public String getScreenShot(String name) {
		TakesScreenshot ts=(TakesScreenshot)driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		File dest=new File(System.getProperty("user.dir")+"/screenshots/"+name+".jpj");
		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "screenshot captured";
	}
	public  LoginPage loadLoginPage(WebDriver driver) {
		lp=new LoginPage(driver);
		return lp;
	}
	@BeforeSuite
	public void StartReport() {
		 htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/OpenCartExtentReport.html");
     	// Create an object of Extent Reports
		 extent = new ExtentReports();  
		 extent.attachReporter(htmlReporter);
		 extent.setSystemInfo("Host Name", "SoftwareTestingMaterial");
     	extent.setSystemInfo("Environment", "Production");
     	extent.setSystemInfo("User Name", "Dhamma ");
     	htmlReporter.config().setDocumentTitle("e-commerence Website "); 
             // Name of the report
     	htmlReporter.config().setReportName("Extent Report of OpenCart e-commerence website."); 
             // Dark Theme
     	htmlReporter.config().setTheme(Theme.STANDARD);		
	}
	@AfterSuite
	public void endReport() {
		extent.flush();
	}
	@AfterMethod
	 public void getResult(ITestResult result) throws Exception{
	 if(result.getStatus() == ITestResult.FAILURE){
	 //MarkupHelper is used to display the output in different colors
	 extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
	 extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
	 //To capture screenshot path and store the path of the screenshot in the string "screenshotPath"
	 //We do pass the path captured by this method in to the extent reports using "logger.addScreenCapture" method. 
	 //String Scrnshot=TakeScreenshot.captuerScreenshot(driver,"TestCaseFailed");
	 String screenshotPath = getScreenShot( result.getName());
	 //To add it in the extent report 
	 extentTest.fail("Test Case Failed Snapshot is below " + extentTest.addScreenCaptureFromPath(screenshotPath));
	 }
	 else if(result.getStatus() == ITestResult.SKIP){
	 extentTest.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE)); 
	 } 
	 else if(result.getStatus() == ITestResult.SUCCESS)
	 {
	 extentTest.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
	 }
	// driver.quit();
	 }
	@BeforeTest
	public void allPages() {
		extentTest=extent.createTest("All Pages","All Pages Test Cases............");
	}
	public Logger pageLogs() {
		log = Logger.getLogger(this.getClass());
		String path = (System.getProperty("user.dir") + "/src/main/resources/log4j.properties");
		PropertyConfigurator.configure(path);
		return log;
	}
}
