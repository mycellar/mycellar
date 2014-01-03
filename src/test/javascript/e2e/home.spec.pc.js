describe('E2E: Testing home', function() {

  describe('with a tiny window', function() {
    beforeEach(function() {
      // Reduce windows size to display collapse nav
      browser.driver.manage().window().setSize(400,400);
    });

    it('should have an expand/collapse nav', function() {
      var homePage = new HomePage();
      homePage.get();

      expect(homePage.getNavToggle().isDisplayed()).toBe(true);
      expect(homePage.getNav().isDisplayed()).toBe(false);

      // expand nav
      homePage.getNavToggle().click();
      expect(homePage.getNav().isDisplayed()).toBe(true);

      // collapse nav
      homePage.getNavToggle().click();
      browser.driver.sleep(200); // FIXME why sleep is neede ?
      expect(homePage.getNav().isDisplayed()).toBe(false);
    });
    
    afterEach(function() {
      // FIXME resize to oldSize (cannot re-uset getSize())
      browser.driver.manage().window().setSize(800,800);
    });
    
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
