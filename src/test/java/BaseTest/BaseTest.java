package BaseTest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import net.snowflake.client.jdbc.internal.google.gson.JsonObject;
import net.snowflake.client.jdbc.internal.google.gson.JsonParser;

public class BaseTest {
	public WebDriver driver;
	public WebDriverWait wait;
	public String browser;
	public String headless;
	public String url;
	public String size;
	public JsonParser parser = new JsonParser();

	@BeforeClass
	public void setup () {
		try {
			getConfigInfo();
		} catch (IOException e) {
			System.out.println("Issue with config file, setting values to default");
			browser = "chrome";
			headless = "false";
		}
		driver = setupChrome();
		wait = new WebDriverWait(driver,15);
		driver.manage().window().maximize();
	}
	
	public String getProjectDirectory() {
		return System.getProperty("user.dir");
	}

	public WebDriver setupChrome() {
		//Create a Chrome driver. All test and page classes use this driver.
		System.setProperty("webdriver.chrome.driver", getProjectDirectory() + "/chromedriver.exe");
		System.out.println(getProjectDirectory() + "\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		if(headless.equals("true")) {
			options.addArguments("window-size=" + size);
			options.addArguments("headless");
		}
		return new ChromeDriver(options);
	}

	public WebDriverWait addDriverWait(WebDriver waitingDriver, int waitTime) {
		return new WebDriverWait(waitingDriver,waitTime);
	}
	public WebDriverWait addDriverWait(WebDriver waitingDriver) {
		return new WebDriverWait(waitingDriver,15);
	}

	public void wait(int num) {
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void getConfigInfo() throws IOException {
		String configFilePath = System.getProperty("user.dir")+"\\config.json";
		JsonObject config = parser.parse(new FileReader(configFilePath)).getAsJsonObject();
		browser = config.get("browser").getAsString();
		headless = config.get("headless").getAsString();
		url = config.get("url").getAsString();
		size = config.get("size").getAsString();
	}

	@AfterMethod
	public void screenshotOnFailure(ITestResult result) {
		if(ITestResult.FAILURE==result.getStatus()){
			try{
				String[] classNameUnformated = result.getInstanceName().split("\\.");
				String className = classNameUnformated[classNameUnformated.length-1];
				System.out.println("Error in class " + className + " on page " + driver.getCurrentUrl());
				TakesScreenshot screenshot=(TakesScreenshot)driver;
				File src=screenshot.getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(src, new File(getProjectDirectory() + "\\"+className+".png"));
				System.out.println("Successfully captured a screenshot");
			}catch (Exception e){
				System.out.println("Exception while taking screenshot "+e.getMessage());
			} 
		}
	}
	
	@AfterClass
	public void teardown () {
		driver.quit();
	}

}
