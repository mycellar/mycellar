module.exports = {

  loginWith: function(mail, password) {
    var homePage = new HomePage();
    homePage.get();

    homePage.emailInput.sendKeys(mail);
    homePage.passwordInput.sendKeys(password);
    homePage.loginButton.click();

    expect(homePage.accountLink.getText()).toEqual(mail);
  },

  login: function() {
    this.loginWith('test@test.com', 'test');
  },

  logout: function() {
    var homePage = new HomePage();
    homePage.get();

    homePage.logoutButton.click();

    expect(homePage.logoutButton.isDisplayed()).toBe(false);
  }

};

var HomePage = function() {
  this.get = function() {
    browser.get('/');
  }

  this.emailInput = element(by.xpath('//form[@name="loginForm"]//input[@id="email"]'));
  this.passwordInput = element(by.xpath('//form[@name="loginForm"]//input[@id="password"]'));
  this.loginButton = element(by.xpath('//form[@name="loginForm"]//input[@type="submit"]'));
  this.logoutButton = element(by.xpath('//a[@title="Se déconnecter"]'));
  this.accountLink = element(by.xpath('//a[@title="Mon compte"]'));
};
