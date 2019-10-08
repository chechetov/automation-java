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



public class AboutPageCheck extends AbstractSeleniumTest {

	private static final Logger LOG = LoggerFactory.getLogger(AboutPageCheck.class);



@Parameters({"MLMainUrl"})
@BeforeClass
    public void setup(String MLMainUrl) {
        System.err.println("Browser name in @BeforeClass is " + MLMainUrl);
    }

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

public void LookAndClickJSElement(String selector){

	JavascriptExecutor executor = (JavascriptExecutor)driver;

	/* Need to know percentage here */
	LOG.info("Scrolling Down");
	executor.executeScript(("window.scrollBy(0,1100);"));

	ThreadSleep(10);
	WebDriverWait wait = new WebDriverWait(driver,10);

	LOG.info("Looking for Xpath: \n {} \n", selector);
	WebElement targetElement = wait.until
	(ExpectedConditions.elementToBeClickable(By.xpath(selector)));

	LOG.info("Clicking: \n {} \n", selector);
	executor.executeScript("arguments[0].click();", targetElement);
	ThreadSleep(1);
	executor.executeScript("arguments[0].click();", targetElement);

	/*
	*/
}

public void VerifyText(String selector){

	JavascriptExecutor executor = (JavascriptExecutor)driver;
	WebDriverWait wait = new WebDriverWait(driver,10);

	LOG.info("Looking for Xpath: \n {} \n", selector);

	WebElement targetElement = wait.until
	(ExpectedConditions.elementToBeClickable(By.xpath(selector)));

	LOG.info("Checking the text");
	String textToSearch = "OVER 1 MILLION BANK ACCOUNTS LINKED";

	String actualText = (String) executor.executeScript("return arguments[0].lastChild.textContent", targetElement);

	LOG.info("textToSearch : {} \n", textToSearch);
	LOG.info("actualText : {} \n", actualText);

/*
	executor.executeScript("document.querySelector(\"#root > div > div > div.block-0-72 > div > div > div:nth-child(2) > div.tab-0-94 > div > div > div > div > p\").textContent");
*/
/**
	String javascript = "$(\"#root > div > div > div.block-0-72 > div > div > div:nth-child(2) > div.arrowContainer-0-63 > div.forwardsArrowContainer-0-79\").click();";
	String javascript2 = "document.querySelector("#root > div > div > div.block-0-72 > div > div > div:nth-child(2) > div.arrowContainer-0-63 > div.forwardsArrowContainer-0-79").click()";
	executor.executeScript(javascript2);
*/
	//*[@id="root"]/div/div/div[3]/div/div/div[2]/div[2]/div[2]
	
	/**
	WebDriverWait wait = new WebDriverWait(driver,10)
	WebElement targetElement = driver.findElement(By.cssSelector(selector));
		executor.executeScript("arguments[0].scrollIntoView(true);", targetElement);

	*/
}

public void ThreadSleep(int seconds){
	try {
	LOG.info("Sleeping for : {} seconds",seconds);
	Thread.sleep(1000 * seconds);   // 1 sec = 1000 millisecs
} 
catch(InterruptedException ex) {
	Thread.currentThread().interrupt();
}

}

/** 
    It is my wrapper to simpify things here 
    inp: css_selector
*/

public void LookAndClickCss(String selector){


	WebDriverWait wait = new WebDriverWait(driver,6);

	LOG.info("Looking for CSS: \n {} \n", selector);

	WebElement targetElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));

	if (targetElement.isDisplayed() ){
		LOG.info("Element was found!");
		targetElement.click();
		} 
	else{
		LOG.info("Element was NOT found: \n {} \n", selector);
	}
	}

/** Passed via testng.xml
*/
@Parameters({"MLMainUrl"})
@Test
public void AboutTextCheck(String MLMainUrl){

	driver.navigate().to(MLMainUrl);

	/** WORKS */

	/**
	LookAndClickCss("#root > div > nav > div.navItemWrapper-0-4 > div.menuButton-0-20");
	*/
	
	/**
	LookAndHoverCss("#root > div > nav > div.navItemWrapper-0-4 > div.menuButton-0-20");
	*/
	LookAndHoverCss("#root > div > nav > div.navItemWrapper-0-4 > div > div.desktopMenuContainer-0-20 > div:nth-child(3)");
	LookAndClickCss("#root > div > nav > div.navItemWrapper-0-4 > div > div.desktopMenuContainer-0-20 > div:nth-child(3) > div > a:nth-child(1)");
	LookAndClickJSElement("//*[@id=\"root\"]/div/div/div[3]/div/div/div[2]/div[2]/div[2]");


	ThreadSleep(10);

	VerifyText("//*[@id=\"root\"]/div/div/div[3]/div/div/div[2]/div[1]/div/div/div");

	/**
	LookAndClickCss("#root > div > div > div.block-0-72 > div > div > div:nth-child(2) > div.arrowContainer-0-63 > div.forwardsArrowContainer-0-79");

	LookAndClickCss("#root > div > div > div.block-0-223 > div > div > div:nth-child(2) > div.arrowContainer-0-214 > div.forwardsArrowContainer-0-230");		/**

	LookAndActCss("","hover");
	*/


	/**
	WebElement menuButton1 = driver.findElement(By.cssSelector("#root > div > nav > div.navItemWrapper-0-4 > div.menuButton-0-20"));
	*/

}

/** CLASS END */
}



/** private static final Logger LOG = LoggerFactory.getLogger(AboutPageCheck.class); */

/**

@DataProvider(name = "LogInPageAddress")
public static Object[][] getPageAddress() {
	return new Object[][]{
		{"https://dashboard.moneylion.com/login"},
    };

}
*/



