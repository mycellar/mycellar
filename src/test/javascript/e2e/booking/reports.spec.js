describe('E2E: Testing booking reports', function() {

  it('should load the reports page', function() {
    login('test@test.com', 'test');

    var page = new BookingReportsPage();
    page.get();

    expect(browser.driver.getCurrentUrl()).toMatch(/booking\/reports/);
    expect(page.title.getText()).toEqual('Suivi des réservations');

    logout();
  });

  it('should display the report of the selected booking event', function() {
    login('test@test.com', 'test');

    var page = new BookingReportsPage();
    page.get();

    expect(page.eventName.getText()).toMatch(page.firstLine.getText());
    page.secondLine.click();
    expect(page.eventName.getText()).toMatch(page.secondLine.getText());
    page.firstLine.click();
    expect(page.eventName.getText()).toMatch(page.firstLine.getText());

    logout();
  });

  it('should display the customers of the selected booking line', function() {
    login('test@test.com', 'test');

    var page = new BookingReportsPage();
    page.get();

    page.firstBottleButton.click();
    expect(page.bottleName.getText()).toMatch(page.firstBottleText.getText());

    logout();
  });

});

var BookingReportsPage = function() {
  this.get = function() {
    browser.get('/booking/reports');
  };

  this.title = element(by.xpath('//header/h1'));
  this.firstLine = element(by.xpath('/html/body/div[2]/div[2]/div/div[1]/nav/ul[2]/li[1]/a'));
  this.secondLine = element(by.xpath('/html/body/div[2]/div[2]/div/div[1]/nav/ul[2]/li[2]/a'));
  this.eventName = element(by.xpath('//section//h2'));
  this.firstBottleButton = element(by.xpath('//section/table/tbody/tr[1]/td[1]/a'));
  this.firstBottleText = element(by.xpath('//section[1]/table/tbody/tr[1]/td[2]'));
  this.bottleName = element(by.xpath('//section[2]/h3'));
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
