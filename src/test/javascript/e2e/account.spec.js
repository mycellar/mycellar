var expect = require('./tools/chai.tool');
var LoginTool = require('./tools/login.tool');

describe('E2E: Testing account', function() {

  beforeEach(function() {
    browser.driver.manage().timeouts().setScriptTimeout(20000);
    browser.driver.manage().timeouts().implicitlyWait(20000);
  });

  it('should load the account page', function() {
    LoginTool.login();

    var page = new AccountPage();
    page.get();

    expect(browser.driver.getCurrentUrl()).to.eventually.match(/account/);
    expect(page.title.isDisplayed()).to.eventually.be.true;
    expect(page.title.getText()).to.eventually.equal('Mon compte');

    LoginTool.logout();
  });

});

var AccountPage = function() {
  this.get = function() {
    browser.get('/account');
  };

  this.title = element(by.xpath('//body//header/h1'));
};
