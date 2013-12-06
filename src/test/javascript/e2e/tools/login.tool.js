module.exports = {

  loginWith: function(mail, password) {
    var loginPage = new LoginPage();
    loginPage.get();

    loginPage.emailInput.sendKeys(mail);
    loginPage.passwordInput.sendKeys(password);
    loginPage.loginButton.click();

    expect(loginPage.accountLink.getText()).toEqual(mail);
  },

  login: function() {
    this.loginWith('test@test.com', 'test');
  },

  logout: function() {
    var loginPage = new LoginPage();
    loginPage.get();

    loginPage.logoutButton.click();

    expect(loginPage.logoutButton.isDisplayed()).toBe(false);
  }

};

var LoginPage = function() {
  this.get = function() {
    browser.get('/login');
  }

  this.emailInput = element(by.xpath('//form[@name="loginForm"]//input[@id="email"]'));
  this.passwordInput = element(by.xpath('//form[@name="loginForm"]//input[@id="password"]'));
  this.loginButton = element(by.xpath('//form[@name="loginForm"]//input[@type="submit"]'));
  this.logoutButton = element(by.xpath('//a[@title="Se déconnecter"]'));
  this.accountLink = element(by.xpath('//a[@title="Mon compte"]'));
};
