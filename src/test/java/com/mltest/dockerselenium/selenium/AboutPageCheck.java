package com.mltest.dockerselenium.selenium;

import com.mltest.dockerselenium.AbstractSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.mltest.dockerselenium.selenium.MLConstant;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeClass;



public class AboutPageCheck extends AbstractSeleniumTest {
@Parameters({"MLMainUrl"})
@BeforeClass
    public void setup(String MLMainUrl) {
        System.err.println("Browser name in @BeforeClass is " + MLMainUrl);
    }

public void LookAndClickCss(String selector){

	WebDriverWait wait = new WebDriverWait(driver,6);
	WebElement menuButton = wait.until
	(ExpectedConditions.elementToBeClickable(
		By.cssSelector(selector)
		)
	);
	if(menuButton.isDisplayed()){
		LOG.info("Element was found!");
		menuButton.click()
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
	LOG.info("Looking for menuButton ...");

	LookAndClickCss("#root > div > nav > div.navItemWrapper-0-4 > div.menuButton-0-20");
	
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
private static final Logger LOG = LoggerFactory.getLogger(AboutPageCheck.class);

} 

/**

@DataProvider(name = "LogInPageAddress")
public static Object[][] getPageAddress() {
	return new Object[][]{
		{"https://dashboard.moneylion.com/login"},
    };

}
*/



