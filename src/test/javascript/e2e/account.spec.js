describe('E2E: Testing account', function() {

  it('should login', function() {
    login('test@test.com', 'test');
  });

  it('should load the account page', function() {
    var page = new AccountPage();
    page.get();

    expect(browser.driver.getCurrentUrl()).toMatch(/account/);
    expect(page.title.isDisplayed()).toBe(true);
    expect(page.title.getText()).toEqual('Mon compte');
  });

  it('should change email', function() {
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
  });

  it('should change password', function() {
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
    logout();
    login('test@test.com', 'test2');

    // rollback change
    page.get();
    page.changePassword.click();
    page.oldPassword.sendKeys('test2');
    page.password1.sendKeys('test');
    page.password2.sendKeys('test');
    page.doChangePassword.click();
    logout();
    login('test@test.com', 'test');
  });

  it('should logout', function() {
    logout();
  });

});

var AccountPage = function() {
  this.get = function() {
    browser.get('/account');
  };

  this.title = element(by.xpath('/html/body/div[2]/div[2]/header/h1'));
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

function login(mail, password) {
  browser.get('/');
  element(by.xpath('//form[@name="loginForm"]//input[@id="email"]')).sendKeys(mail);
  element(by.xpath('//form[@name="loginForm"]//input[@id="password"]')).sendKeys(password);
  element(by.xpath('//form[@name="loginForm"]//input[@type="submit"]')).click();
  expect(element(by.xpath('//a[@title="Mon compte"]')).getText()).toEqual(mail);
}

function logout() {
  browser.get('/');
  element(by.xpath('//a[@title="Se déconnecter"]')).click();
  expect(element(by.xpath('//a[@title="Se déconnecter"]')).isDisplayed()).toBe(false);
}
