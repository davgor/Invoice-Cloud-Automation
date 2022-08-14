package tests;

import org.testng.annotations.Test;

import BaseTest.BaseTest;
import junit.framework.Assert;
import BasePage.AddRemovePage;

public class ElementTest extends BaseTest {
	@Test
	public void Add_1_remove_1() {
		AddRemovePage base = new AddRemovePage(driver, wait);
		driver.get(url);
        base.AddElement(1);
        base.RemoveElement(1);
        int count = base.GetElementCount();
        System.out.println("Does " + base.expectedElement + " === " + count);
        Assert.assertEquals(base.expectedElement, count);
	}

    @Test
    public void Add_10_remove_1() {
		AddRemovePage base = new AddRemovePage(driver, wait);
		driver.get(url);
        base.AddElement(10);
        base.RemoveElement(1);
        int count = base.GetElementCount();
        System.out.println("Does " + base.expectedElement + " === " + count);
        Assert.assertEquals(base.expectedElement, count);
	}

    @Test
    public void Add_100() {
		AddRemovePage base = new AddRemovePage(driver, wait);
		driver.get(url);
        base.AddElement(100);
        int count = base.GetElementCount();
        System.out.println("Does " + base.expectedElement + " === " + count);
        Assert.assertEquals(base.expectedElement, count);
	}

    @Test
    public void Add_1() {
		AddRemovePage base = new AddRemovePage(driver, wait);
		driver.get(url);
        base.AddElement(1);
        int count = base.GetElementCount();
        System.out.println("Does " + base.expectedElement + " === " + count);
        Assert.assertEquals(base.expectedElement, count);
	}

    @Test
    public void Remove_1() {
		AddRemovePage base = new AddRemovePage(driver, wait);
		driver.get(url);
        base.RemoveElement(1);
        int count = base.GetElementCount();
        System.out.println("Does " + base.expectedElement + " === " + count);
        Assert.assertEquals(base.expectedElement, count);
	}
    @Test
    public void Add_1_Remove_5() {
		AddRemovePage base = new AddRemovePage(driver, wait);
		driver.get(url);
        base.AddElement(1);
        base.RemoveElement(5);
        int count = base.GetElementCount();
        System.out.println("Does " + base.expectedElement + " === " + count);
        Assert.assertEquals(base.expectedElement, count);
	}
}
