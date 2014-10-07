var expect = require('./tools/chai.tool');
var LoginTool = require('./tools/login.tool');

describe('E2E: Testing account', function() {

  it('should load the account page', function() {
    var page = new AccountPage();
    page.get();

    LoginTool.login();

    expect(browser.driver.getCurrentUrl()).to.eventually.match(/account/);
    expect(page.title.isDisplayed()).to.eventually.be.true;
    expect(page.title.getText()).to.eventually.equal('Mon compte');

    LoginTool.logout();
  });

});

var AccountPage = function() {
  this.get = function() {
    browser.polymerGet('/account');
  };

  this.title = element(by.xpath('//body//header/h1'));
};
