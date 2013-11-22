describe("E2E: Testing reset-password-request", function() {

  it('should load the reset-password-request page', function() {
    var page = new ResetPasswordRequestPage();
    page.get();

    expect(page.getEmail().isDisplayed()).toBe(true);
  });

  it('should ask a reset password', function() {
    var page = new ResetPasswordRequestPage();
    page.get();

    expect(page.getRequestSentText().isDisplayed()).toBe(false);

    page.getEmail().sendKeys('t@t.fr');
    page.getRequestPasswordResetButton().click();

    expect(page.getRequestSentText().isDisplayed()).toBe(true);
  });

});

var ResetPasswordRequestPage = function() {
  this.get = function() {
    browser.get('/reset-password-request');
  };

  this.getEmail = function() {
    return element(by.xpath('//form[@name="form"]//input[@id="email"]'));
  };

  this.getRequestPasswordResetButton = function() {
    return element(by.xpath('//form[@name="form"]//input[@type="submit"]'));
  };

  this.getRequestSentText = function() {
    return element(by.xpath('/html/body/div[2]/div[2]/section/div'));
  };
};
