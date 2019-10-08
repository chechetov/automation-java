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

/** 
    It is my wrapper to simpify things here 
    inp: css_selector, action
*/

public void LookAndActCss(String selector,String action){
	/** action = "click" || "hover" */

	WebDriverWait wait = new WebDriverWait(driver,6);

	LOG.info("Looking for CSS: \n {} \n", selector);

	WebElement targetElement = wait.until
	(ExpectedConditions.elementToBeClickable(
		By.cssSelector(selector)
		)
	);

	if(targetElement.isDisplayed()){
		
		LOG.info("Element was found!");
		
		if(action == "click"){
		targetElement.click();
		}

		if(action == "hover"){
			String javaScript = "var evObj = document.createEvent('MouseEvents');" +
			"evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
			"arguments[0].dispatchEvent(evObj);";
            JavascriptExecutor js  = (JavascriptExecutor) driver;
            js.executeScript(javaScript, targetElement);
		}
		else{
			LOG.info("NO ACTION WAS DONE SOMETHING WRONG");
		}
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

	LookAndActCss("#root > div > nav > div.navItemWrapper-0-4 > div.menuButton-0-20","click");
	
try {
	Thread.sleep(1000 * 5);   // 1 sec = 1000 millisecs
} 
catch(InterruptedException ex) {
	Thread.currentThread().interrupt();
}

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



