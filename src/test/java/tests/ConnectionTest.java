package tests;

import org.testng.annotations.Test;

import BaseTest.BaseTest;
import BasePage.BasePage;

public class ConnectionTest extends BaseTest {
	@Test
	public void TestingConnection() {
		BasePage base = new BasePage(driver, wait);
		driver.get(url);
		base.waitForElementToBeClickable(base.getElementByText("button", "Add Element"));
		wait(5000);
	}
}
