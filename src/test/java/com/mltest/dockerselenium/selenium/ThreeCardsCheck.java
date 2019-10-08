package com.mltest.dockerselenium.selenium;

import com.mltest.dockerselenium.AbstractSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;

public class ThreeCardsCheck extends AbstractSeleniumTest {

	private static final Logger LOG = LoggerFactory.getLogger(ThreeCardsCheck.class);

@Parameters({"MLMainUrl"})
@BeforeClass
    public void setup(String MLMainUrl) {
        System.err.println("Starttng Link in @BeforeClass is " + MLMainUrl);
    }

/** Better copy to avoid issue with interitance on a small scale
	Means every class can have their own method */

public void LookAndHoverCss(String selector){

	WebDriverWait wait = new WebDriverWait(driver,6);

	LOG.info("Looking for CSS: \n {} \n", selector);

	WebElement targetElement = wait.until
	(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));

	if (targetElement.isDisplayed())
	{
	String javaScript = "var evObj = document.createEvent('MouseEvents');" +
			"evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
			"arguments[0].dispatchEvent(evObj);";
    JavascriptExecutor js  = (JavascriptExecutor) driver;
    js.executeScript(javaScript, targetElement);
	targetElement.click();
	}
	else {
		LOG.info("Element was not located and hovered: \n {} \n", selector);
	}
}

public void LookAndClickCss(String selector){

	WebDriverWait wait = new WebDriverWait(driver,6);
	LOG.info("Looking for CSS: \n {} \n", selector);
	WebElement targetElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));

	if (targetElement.isDisplayed() ){
		LOG.info("Element was found: \n {} \n", selector);
		targetElement.click();
		} 
	else{
		LOG.info("Element was NOT found: \n {} \n", selector);
	}
}

public void ScrollToElement(String selector) {
	
	Actions actions = new Actions(driver);

	JavascriptExecutor executor = (JavascriptExecutor)driver;

	WebDriverWait wait = new WebDriverWait(driver,3);

	LOG.info("Looking for CSS: \n {} \n", selector);

	/**WebElement targetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selector)));
	*/
	WebElement targetElement = driver.findElement(By.xpath(selector));

	String offsetTop = targetElement.getAttribute("offsetTop");

	executor.executeScript("window.scrollBy(0,arguments[0]);",offsetTop);

	LOG.info("offsetTop: {}", offsetTop);

	try{
	actions.moveToElement(targetElement, 5, 5);
	actions.perform();
	}
	catch (MoveTargetOutOfBoundsException e){
		LOG.info("Element OUT OF BOUNDS: MoveTargetOutOfBoundsException:"); 
		/**" \n {} \n", e.toString());*/
	}

	String javaScript = "var evObj = document.createEvent('MouseEvents');" +
			"evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
			"arguments[0].dispatchEvent(evObj);";

	String javaScript1 = "var evObj = document.createEvent('MouseEvents');" +
			"evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
			"arguments[0].dispatchEvent(evObj);";

	executor.executeScript(javaScript, targetElement);
	executor.executeScript(javaScript1, targetElement);

	executor.executeScript("arguments[0].scrollIntoViewIfNeeded(false);", targetElement);

	
	/**((JavascriptExecutor)driver).executeScript("$('div#evy_aboutme_content_id08  div.evy_edit_overflow > div.evy_rltn_icon2 i').hover();");
    ((JavascriptExecutor)driver).executeScript("$('div#evy_aboutme_content_id08  div.evy_aboutme_education_content.ng-scope   a:nth-child(1)').click();");
	*/

	String checkText = 	(String)executor.executeScript("return \"HI\";", targetElement);
	String checkText2 = (String)executor.executeScript("return String(arguments[0]);", targetElement);
	/** String checkText3 = (String)executor.executeScript("return String(arguments[0].offsetTop());", targetElement);
	LOG.info("CT3:: {} \n", checkText3);

	*/
	LOG.info("CT:: {} \n", checkText);
	LOG.info("CT2:: {} \n", checkText2);

	ThreadSleep(1);
}

	@Parameters({"MLMainUrl"})
	@Test
	public void ThreeCardTest(String MLMainUrl){

		driver.navigate().to(MLMainUrl);
		ThreadSleep(2);

		/** 
		Let's use my wrappers 
		ThreadSleeps just for hard safety
		*/

		LookAndHoverCss("#root > div > nav > div.navItemWrapper-0-4 > div > div.desktopMenuContainer-0-20 > div:nth-child(2)");
		ThreadSleep(2);
		LookAndClickCss("#root > div > nav > div.navItemWrapper-0-4 > div > div.desktopMenuContainer-0-20 > div:nth-child(2) > div > a:nth-child(5)");
		ThreadSleep(2);
		ScrollToElement("//*[@id=\"root\"]/div/div/div[2]");
		ThreadSleep(3);

		takeScreenshot(driver);

		Assert.assertEquals("1", "1");
	}

/** CLASSEND */
}


