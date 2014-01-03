var LoginTool = require('./tools/login.tool');

describe('E2E: Testing account', function() {

  it('should change email', function() {
    LoginTool.loginWith('test@test.com', 'test');

    var page = new AccountPage();
    page.get();

    page.changeEmail.click();
    expect(page.email1.isDisplayed()).toBe(true);
    expect(page.email2.isDisplayed()).toBe(true);
    expect(page.emailPassword.isDisplayed()).toBe(true);
    expect(page.doChangeEmail.isDisplayed()).toBe(true);
    page.email1.sendKeys('test2@test.com');
    page.email2.sendKeys('test2@test.com');
    page.emailPassword.sendKeys('test');
    page.doChangeEmail.click();
    page.get();
    expect(page.email.getText()).toEqual('test2@test.com');

    // rollback change
    page.changeEmail.click();
    page.email1.sendKeys('test@test.com');
    page.email2.sendKeys('test@test.com');
    page.emailPassword.sendKeys('test');
    page.doChangeEmail.click();
    page.get();
    expect(page.email.getText()).toEqual('test@test.com');

    LoginTool.logout();
  });

  it('should change password', function() {
    LoginTool.loginWith('test@test.com', 'test');

    var page = new AccountPage();
    page.get();

    page.changePassword.click();
    expect(page.oldPassword.isDisplayed()).toBe(true);
    expect(page.password1.isDisplayed()).toBe(true);
    expect(page.password2.isDisplayed()).toBe(true);
    expect(page.doChangePassword.isDisplayed()).toBe(true);
    page.oldPassword.sendKeys('test');
    page.password1.sendKeys('test2');
    page.password2.sendKeys('test2');
    page.doChangePassword.click();

    LoginTool.logout();
    LoginTool.loginWith('test@test.com', 'test2');

    // rollback change
    page.get();
    page.changePassword.click();
    page.oldPassword.sendKeys('test2');
    page.password1.sendKeys('test');
    page.password2.sendKeys('test');
    page.doChangePassword.click();

    LoginTool.logout();
  });

});

var AccountPage = function() {
  this.get = function() {
    browser.get('/account');
  };

  this.email = element(by.xpath('//address/span'));
  this.changeEmail = element(by.xpath('//section//section/a[1]'));
  this.email1 = element(by.xpath('//form[@name="changeEmailForm"]//input[@id="email"]'));
  this.email2 = element(by.xpath('//form[@name="changeEmailForm"]//input[@id="email2"]'));
  this.emailPassword = element(by.xpath('//form[@name="changeEmailForm"]//input[@id="password"]'));
  this.doChangeEmail = element(by.xpath('//form[@name="changeEmailForm"]//input[@type="submit"]'));
  this.changePassword = element(by.xpath('//section//section/a[2]'));
  this.oldPassword = element(by.xpath('//form[@name="changePasswordForm"]//input[@id="oldPassword"]'));
  this.password1 = element(by.xpath('//form[@name="changePasswordForm"]//input[@id="password"]'));
  this.password2 = element(by.xpath('//form[@name="changePasswordForm"]//input[@id="password2"]'));
  this.doChangePassword = element(by.xpath('//form[@name="changePasswordForm"]//input[@type="submit"]'));
};
