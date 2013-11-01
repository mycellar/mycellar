describe("E2E: Testing home route", function() {

  it('should load the homepage', function() {
    var homePage = new HomePage();
    homePage.get();
    
    expect(homePage.getWineCount().isDisplayed()).toBe(true);
  });

});

var HomePage = function() {
  this.get = function() {
    browser.get('/');
  };
  
  this.getWineCount = function() {
    return element(by.binding('wineCount'));
  }
};