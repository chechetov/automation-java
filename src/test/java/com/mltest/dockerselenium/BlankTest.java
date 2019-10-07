package com.mltest.dockerselenium;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests our placeholder class.<br>
 * This is just a sanity thing for setting up the project.
 */
@Test (
  groups = {"Unimportant"},
  description = "Tests our placeholder class."
)
public class BlankTest {

  /**
   * Tests the basics of the class.
   */
  @Test (
    description = "Tests the basics of the class."
  )
  public void testCore() {
    Assert.assertNotNull(new Blank(), "Well that's just silly.");
  }
}
