describe('E2E: Testing booking reports', function() {

  it('should load the reports page', function() {
    login('test@test.com', 'test');

    var page = new BookingReportsPage();
    page.get();

    expect(browser.driver.getCurrentUrl()).toMatch(/booking\/reports/);
    expect(page.title.getText()).toEqual('Suivi des réservations');

    logout();
  });

});

var BookingReportsPage = function() {
  this.get = function() {
    browser.get('/booking/reports');
  };

  this.title = element(by.xpath('//header/h1'));

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
