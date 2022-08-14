package BasePage;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	public WebDriver driver;
	public WebDriverWait wait;
	private String fluentWaitTime = "15";

	//Constructor
	public BasePage (WebDriver driver, WebDriverWait wait){
		this.driver = driver;
		this.wait = wait;
	}

	public void wait(int num) {
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void scrollToElement(By selector) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoViewIfNeeded(true);", driver.findElement(selector));
			wait(500);
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			System.out.println("Stale element triggered");
		}
	}
	public void waitForSelector(ExpectedCondition<WebElement> elementToBe, String seconds) {	
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.parse("PT" + seconds + "S"))
				.pollingEvery(Duration.parse("PT1S"))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		wait.until(elementToBe);	
	}
	//Click Method
	public void click (By selector) {
		waitForSelector(ExpectedConditions.elementToBeClickable(selector), fluentWaitTime);
		scrollToElement(selector);
		driver.findElement(selector).click();
	}
	public void waitForElementToBeClickable(By selector) {
		wait.until(ExpectedConditions.elementToBeClickable(selector));
	}
	public By getElementByText(String tag, String text) {
		return By.xpath("//" + tag + "[.='" + text + "']");
	}
	public List<WebElement> getElementList(By selector) {
		return driver.findElements(selector);
	}
	public boolean waitForPageIdle() {
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}
}
