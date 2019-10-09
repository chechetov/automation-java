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

import org.openqa.selenium.Point;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.Keys;

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

	/** 
	This hack is full of tears
	*
	
	Scrolls down to element by XPATH

	This twist is needed because of inability ot locate the element via Actions
	It throws MoveTargetOutOfBoundsException

	*/	
	
	// Can Also locate element with Wait
	// WebDriverWait wait = new WebDriverWait(driver,3);

	LOG.info("Looking for XPATH: \n {} \n", selector);

	// Exception here
	WebElement targetElement = driver.findElement(By.xpath(selector));

	// Trying to use Actions on element
	try{
	Actions actions = new Actions(driver);
	actions.moveToElement(targetElement, 5, 5);
	actions.perform();
	}

	catch (MoveTargetOutOfBoundsException e){
		/**
		Catching the exception to show that element is not locatable
		Moving forward
		*/
		LOG.info("Element OUT OF BOUNDS: MoveTargetOutOfBoundsException:"); 
		/**" \n {} \n", e.toString());*/
	}

	// Trying to get position with getLocation and Point
	Point targetPoint = targetElement.getLocation();
    int xcord = targetPoint.getX();
    int ycord = targetPoint.getY();

    LOG.info("getLocation X is: {}", String.valueOf(xcord));
    LOG.info("getLocation Y is: {}", String.valueOf(ycord));

    // Trying to get attributes
    // https://www.kirupa.com/html5/get_element_position_using_javascript.htm
	String offsetTop = targetElement.getAttribute("offsetTop");
	String offsetLeft = targetElement.getAttribute("offsetLeft");

	LOG.info("offsetTop: {}", offsetTop);
	LOG.info("offsetLeft: {}", offsetLeft);

	String scrollLeft = targetElement.getAttribute("scrollLeft");
	String scrollTop = targetElement.getAttribute("scrollTop");
	
	LOG.info("scrollLeft: {}", scrollLeft);
	LOG.info("scrollTop: {}", scrollTop);

	String clientTop = targetElement.getAttribute("clientTop");	
	String clientLeft = targetElement.getAttribute("clientLeft");	

	LOG.info("clientTop: {}", clientTop);
	LOG.info("clientLeft: {}", clientLeft);

	int[] CalculatedPosition = new int[2];
	//Test
	CalculatedPosition[0] = Integer.valueOf(offsetLeft) - Integer.valueOf(scrollLeft) + Integer.valueOf(clientLeft); // X
	CalculatedPosition[1] =  Integer.valueOf(offsetTop) - Integer.valueOf(scrollTop) + Integer.valueOf(clientTop); // Y

    LOG.info("calculatedPoint X is: {}", String.valueOf(CalculatedPosition[0]));
    LOG.info("calculatedPoint Y is: {}", String.valueOf(CalculatedPosition[1]));

	//Scrolling to whatever makes more sense now.. manual tuning here


	JavascriptExecutor executor = (JavascriptExecutor)driver;

	LOG.info("Scrolling To: {}",String.valueOf(ycord * 1.2));
	executor.executeScript("window.scrollTo(arguments[0],arguments[1]);",String.valueOf(xcord), String.valueOf(ycord));
	ThreadSleep(4);

	WebElement cardOne = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div[1]"));
	LOG.info("cardOne visible: {}", String.valueOf(cardOne.isDisplayed()));

	// This does the first flip
	LOG.info("Scrolling by 0.1");
	executor.executeScript("window.scrollBy(0,arguments[0]);",String.valueOf(ycord * 0.1));	
	ThreadSleep(3);
	takeScreenshot(driver);

	// This does the second flip
	LOG.info("Scrolling by 0.5");
	executor.executeScript("window.scrollBy(0,arguments[0]);",String.valueOf(ycord * 0.5));	
	ThreadSleep(3);
	takeScreenshot(driver);

}

	@Parameters({"MLMainUrl"})
	@Test
	public void ThreeCardTest(String MLMainUrl){

		//MLMainUrl = "https://www.moneylion.com/personal-finance-mobile-app";

		driver.navigate().to(MLMainUrl);

		/** 
		Let's use my wrappers 
		ThreadSleeps just for hard safety
		*/

		ThreadSleep(2);
		LookAndHoverCss("#root > div > nav > div.navItemWrapper-0-4 > div > div.desktopMenuContainer-0-20 > div:nth-child(2)");
		ThreadSleep(2);
		LookAndClickCss("#root > div > nav > div.navItemWrapper-0-4 > div > div.desktopMenuContainer-0-20 > div:nth-child(2) > div > a:nth-child(5)");
		ThreadSleep(2);
		ScrollToElement("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div");
		ThreadSleep(4);

		takeScreenshot(driver);
		Assert.assertEquals("1", "1");
	}
/** CLASSEND */
}


