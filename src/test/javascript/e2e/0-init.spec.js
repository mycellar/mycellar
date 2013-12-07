describe('E2E configure webdriverjs', function() {
  it('should be configured', function() {
    browser.driver.manage().timeouts().setScriptTimeout(5000);
    browser.driver.manage().timeouts().implicitlyWait(5000);
  })
});