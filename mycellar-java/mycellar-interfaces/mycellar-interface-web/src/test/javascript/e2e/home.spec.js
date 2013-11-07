describe("E2E: Testing home", function() {

  it('should load the homepage', function() {
    var homePage = new HomePage();
    homePage.get();

    expect(homePage.getWineCount().isDisplayed()).toBe(true);
  });

  it('should have an expand/collapse nav', function() {
    // Reduce windows size to display collapse nav
    browser.driver.manage().window().setSize(400,400);

    var homePage = new HomePage();
    homePage.get();

    expect(homePage.getNavToggle().isDisplayed()).toBe(true);
    expect(homePage.getNav().isDisplayed()).toBe(false);

    // expand nav
    homePage.getNavToggle().click();
    expect(homePage.getNav().isDisplayed()).toBe(true);

    // collapse nav
    homePage.getNavToggle().click();
    expect(homePage.getNav().isDisplayed()).toBe(false);
  });

  it('should have a register button', function() {
    var homePage = new HomePage();
    homePage.get();

    expect(homePage.getRegisterButton().isDisplayed()).toBe(true);
  });

});

var HomePage = function() {
  this.get = function() {
    browser.get('/');
  };

  this.getWineCount = function() {
    return element(by.binding('wineCount'));
  };

  this.getNavToggle = function() {
    return element(by.className('navbar-toggle'));
  };

  this.getNav = function() {
    return element(by.className('navbar-collapse'));
  };

  this.getRegisterButton = function() {
    return element(by.linkText('Créer un nouveau compte'));
  }
};
