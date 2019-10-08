package com.mltest.dockerselenium;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.chrome.ChromeOptions;



/**
 * A class that provides the basics for all of our Selenium needs between all of our
 * tests.  This avoids the need to have us constantly look up data, attempt to find
 * paths, replicate code, etc.  We wanted to create a set of common tests that could
 * be executed and use this to ensure that it's all in a single location so that we
 * don't waste our time with all this boilerplate crap.
 */
public abstract class AbstractSeleniumTest {

  /**
   * The {@code Logger} to use in cooperation with all generic test instances.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AbstractSeleniumTest.class);

  /**
   * The {@code Path} where we want to store screenshots on the local machine.
   */
  private static Path screenshotPath;

  /**
   * The driver instance to use for test execution.
   */
  protected RemoteWebDriver driver;

  /**
   * The date/time when the last known test started.
   */
  private long timeStart;

  /**
   * Basic setup routine that allows us to get things started in a proper fashion
   * so that we are not constantly worried about doing this in each and every test
   * method that we run.
   * @throws Exception if anything goes horribly wrong during testing
   */
  @BeforeClass(alwaysRun = true)
  public static void initTest() throws Exception {
    String now = DateTimeFormat.forPattern("yyyy-MM-dd_HH-mm").print(DateTime.now());
    screenshotPath = Paths.get(System.getProperty("user.home")).resolve("Desktop").resolve("SeleniumExample").resolve(now);

    if (!Files.exists(screenshotPath)) {
      Files.createDirectories(screenshotPath);
    }

    LOG.info("Screenshots will be saved to {}", screenshotPath.toAbsolutePath());
  }

/**

@BeforeClass()
public void LogIn(String LogInPageAddress){

    MLConstant MyConstant = new MLConstant();
    LOG.info("MLConstant.URL :", MLConstant.URL);

  LOG.info("Log to MoneyLion Website: ");
  driver.navigate().to(LogInPageAddress);
}
*/

  /**
   * Requests that a new driver be created before every test execution.
   * @throws Exception if anything goes horribly wrong during setup
   */
  @BeforeMethod
  public void connectRemote() throws Exception {
    // connect to the remote instance
    URL remoteUrl = new URL("http://127.0.0.1:4444/wd/hub");
    
    /**
    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    driver = new RemoteWebDriver(remoteUrl, capabilities);
    driver.manage.window.maximize();
  */

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--start-maximized");
    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    
    driver = new RemoteWebDriver(remoteUrl, capabilities);

    // start the test
    timeStart = System.currentTimeMillis();
  }

  /**
   * Requests that we always close the driver used for execution after every test.
   */
  @AfterMethod(alwaysRun = true)
  public void closeRemote() {
    driver.close();

    long duration = System.currentTimeMillis() - timeStart;
    String humanDuration = DurationFormatUtils.formatDuration(duration, "m'm' s's' S'ms'", true);
    LOG.info("Test took {}", humanDuration);
  }

  /**
   * Requests that we take a screenshot and save it somewhere -- we'll log where it goes.
   * @param driver the Selenium driver instance to use to take the screenshot
   * @param <T> the {@code WebDriver} extension that allows us to take screenshots
   */
  protected static <T extends WebDriver & TakesScreenshot> void takeScreenshot(final T driver) {
    // maximize the window and let it finish rendering
    try {
      driver.manage().window().maximize();
      Thread.sleep(5_000);
    } catch (InterruptedException e) {
      LOG.warn("Error waiting for screen to maximize: {}", e.getMessage(), e);
    }

    // actually take the screenshot
    try {
      String screenshotFileName = DateTimeFormat.forPattern("HHmmss").print(DateTime.now()) + ".png";
      Path saveTo = screenshotPath.resolve(screenshotFileName);
      Files.write(saveTo, driver.getScreenshotAs(OutputType.BYTES));
      LOG.info("Screenshot saved to {}", saveTo.toAbsolutePath());
    } catch (IOException e) {
      LOG.error("Unable to take screenshot: {}", e.getMessage(), e);
    }
  }
}
