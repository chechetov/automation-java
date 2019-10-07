package com.mltest.dockerselenium.selenium;

import com.mltest.dockerselenium.AbstractSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests a basic search on Google.
 */

@Test(
  groups = {"Selenium"},
  description = "Tests a basic search on Google."
)
public class SearchOnGoogleTest extends AbstractSeleniumTest {

  /**
   * The {@code Logger} to use in cooperation with all test instances.
   */
  private static final Logger LOG = LoggerFactory.getLogger(SearchOnGoogleTest.class);

  /**
   * Tests a basic search.
   * @param searchTerm the search term to use on Google
   * @throws Exception if anything goes horribly wrong
   */
  @Test(
    dataProvider = "getSearchTerms",
    description = "Tests a basic search."
  )
  public void testSearch(final String searchTerm) throws Exception {
    // log what we're doing
    LOG.info("Google Search : \"{}\"", searchTerm);

    // go to Google
    driver.navigate().to("http://www.google.com");

    // enter in our search term & submit
    WebElement searchField = driver.findElement(By.name("q"));
    searchField.sendKeys(searchTerm);
    searchField.submit();

    // now, make sure we have a set of results
    //   ... this is just a sample, so we'll just count the number of anchors
    int anchorCount = driver.findElementsByTagName("a").size();
    LOG.info("Google Search : \"{}\" : resulted in {} anchors on the page", searchTerm, anchorCount);

    // now take a screenshot
    takeScreenshot(driver);
  }

  /**
   * Retrieves a list of search terms to use against Google.
   * @return a list of search terms to use against Google
   */
  @DataProvider(name = "getSearchTerms")
  public static Object[][] getSearchTerms() {
    return new Object[][]{
      {"Gwar"},
      {"Selenium"},
      {"Richar Stallman"},
      {"St. IGNUcius"},
      {"obnoxious"},
      {"silly"},
    };
  }
}

public class MoneyLionMembershipPage extends AbstractSeleniumTest{

private static final Logger LOG = LoggerFactory.getLogger(SearchOnGoogleTest.class);


}
