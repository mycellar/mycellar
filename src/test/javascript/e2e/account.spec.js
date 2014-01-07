var LoginTool = require('./tools/login.tool');

describe('E2E: Testing account', function() {

  beforeEach(function() {
    browser.driver.manage().timeouts().setScriptTimeout(10000);
    browser.driver.manage().timeouts().implicitlyWait(10000);
  });

  it('should load the account page', function() {
    LoginTool.login();

    var page = new AccountPage();
    page.get();

    expect(browser.driver.getCurrentUrl()).toMatch(/account/);
    expect(page.title.isDisplayed()).toBe(true);
    expect(page.title.getText()).toEqual('Mon compte');

    LoginTool.logout();
  });

});

var AccountPage = function() {
  this.get = function() {
    browser.get('/account');
  };

  this.title = element(by.xpath('/html/body/div[2]/div[2]/header/h1'));
};
