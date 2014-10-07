var expect = require('./chai.tool');

module.exports = {
  loginWith: function(mail, password) {
    var loginDialog = new LoginDialog();
    loginDialog.setEmail(mail);
    loginDialog.setPassword(password);
    loginDialog.login();

    expect(loginDialog.accountLink.getText()).to.eventually.equal(mail);
  },

  login: function() {
    this.loginWith('test@test.com', 'test');
  },

  logout: function() {
    var loginDialog = new LoginDialog();
    loginDialog.logoutLink.click();
    expect(loginDialog.logoutLink.isDisplayed()).to.eventually.be.false;
  }

};

var LoginDialog = function() {
  this.setEmail = function(email) {
    element(by.xpath('//*[@id="input"][@type="email"]')).sendKeys(email);
  };
  this.setPassword = function(password) {
    element(by.xpath('//*[@id="input"][@type="password"]')).sendKeys(password);
  };
  this.login = function() {
    element(by.xpath('//paper-dialog[@id="loginDialog"]//input[@type="submit"]')).click();
  };
  this.accountLink = element(by.xpath('//a[@title="Mon compte"]'));
  this.logoutLink = element(by.xpath('//a[@title="Se déconnecter"]'));
};
