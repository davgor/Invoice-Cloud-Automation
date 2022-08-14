package BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

public class AddRemovePage extends BasePage{

    public AddRemovePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public int expectedElement = 0;

    public void AddElement(int count) {
        for (int i = 1; i <= count; i++ ) {
            click(getElementByText("button", "Add Element"));// Click on said element
            expectedElement++;// Increment the counter needed
        }
    }
    public void RemoveElement(int count) {
        for (int i = 1; i <= count; i++ ) {
            if (expectedElement != 0) {
                click(By.cssSelector(".added-manually"));
                expectedElement--;// Increment the counter needed
            } else {
                System.out.println("No elements expected");
                Assert.assertEquals(expectedElement, GetElementCount());
            }
        }
    }
    public int GetElementCount() {
        return getElementList(By.cssSelector(".added-manually")).size();
    }
}
