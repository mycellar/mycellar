var expect = require('../tools/chai.tool');
var LoginTool = require('../tools/login.tool');

describe('E2E: Testing booking reports', function() {

  beforeEach(function() {
    LoginTool.login();
  });

  it('should load the reports page', function() {
    var page = new BookingReportsPage();
    page.get();

    expect(browser.driver.getCurrentUrl()).to.eventually.match(/booking\/reports/);
    expect(page.title.getText()).to.eventually.equal('Suivi des réservations');
  });

  it('should display the report of the selected booking event', function(done) {
    var page = new BookingReportsPage();
    page.get();

    expect(page.eventName.getText()).to.eventually.equal("Campagne de test 2 du 2000-01-01 au 3000-01-01");
    page.secondLine.click();
    expect(page.eventName.getText()).to.eventually.equal("Campagne de test 1 du 2000-01-01 au 3000-01-01");
    page.firstLine.click();
    expect(page.eventName.getText()).to.eventually.equal("Campagne de test 2 du 2000-01-01 au 3000-01-01");
  });

  it('should display the customers of the selected booking line', function() {
    var page = new BookingReportsPage();
    page.get();

    var firstBottle;
    page.firstBottleText.getText().then(function(value) {
      firstBottle = value;
    });
    page.firstBottleButton.click();
    expect(page.bottleName.getText()).to.eventually.equal("Domaine Armand Rousseau - Chambertin - 2005 - Rouge Bouteille (0,75L) 200,00 €");
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
  this.firstLine = element(by.xpath('//body/div[3]//nav/ul[@class="nav nav-pills nav-stacked"]/li[1]/a'));
  this.secondLine = element(by.xpath('//body/div[3]//nav/ul[@class="nav nav-pills nav-stacked"]/li[2]/a'));
  this.eventName = element(by.xpath('//section//h2'));
  this.firstBottleButton = element(by.xpath('//section/div/table/tbody/tr[1]/td[1]/a'));
  this.firstBottleText = element(by.xpath('//section[1]/div/table/tbody/tr[1]/td[2]'));
  this.bottleName = element(by.xpath('//section[2]/h3'));
};
