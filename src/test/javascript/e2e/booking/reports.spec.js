LoginTool = require('../tools/login.tool');

describe('E2E: Testing booking reports', function() {

  beforeEach(function() {
    LoginTool.login();
  });

  it('should load the reports page', function() {
    var page = new BookingReportsPage();
    page.get();

    expect(browser.driver.getCurrentUrl()).toMatch(/booking\/reports/);
    expect(page.title.getText()).toEqual('Suivi des réservations');
  });

  it('should display the report of the selected booking event', function() {
    var page = new BookingReportsPage();
    page.get();

    expect(page.eventName.getText()).toMatch(page.firstLine.getText());
    page.secondLine.click();
    expect(page.eventName.getText()).toMatch(page.secondLine.getText());
    page.firstLine.click();
    expect(page.eventName.getText()).toMatch(page.firstLine.getText());
  });

  it('should display the customers of the selected booking line', function() {
    var page = new BookingReportsPage();
    page.get();

    page.firstBottleButton.click();
    expect(page.bottleName.getText()).toMatch(page.firstBottleText.getText());
  });

  afterEach(function() {
    LoginTool.logout();
  });

});

var BookingReportsPage = function() {
  this.get = function() {
    browser.get('/booking/reports');
  };

  this.title = element(by.xpath('//header/h1'));
  this.firstLine = element(by.xpath('//body/div[2]/div[2]//nav/ul[@class="nav nav-pills nav-stacked"]/li[1]/a'));
  this.secondLine = element(by.xpath('//body/div[2]/div[2]//nav/ul[@class="nav nav-pills nav-stacked"]/li[2]/a'));
  this.eventName = element(by.xpath('//section//h2'));
  this.firstBottleButton = element(by.xpath('//section/table/tbody/tr[1]/td[1]/a'));
  this.firstBottleText = element(by.xpath('//section[1]/table/tbody/tr[1]/td[2]'));
  this.bottleName = element(by.xpath('//section[2]/h3'));
};
