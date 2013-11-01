describe('Unit: Testing security', function() {

  beforeEach(angular.mock.module('mycellar.services.security.service'));

  it('should be exposed', inject(function(security) {
    expect(security).to.not.be.null;
  }));

  it('should have a login method', inject(function(security) {
    expect(security.login).to.not.be.null;
  }));

  it('should have a logout method', inject(function(security) {
    expect(security.logout).to.not.be.null;
  }));

  it('should have a requestCurrentUser method', inject(function(security) {
    expect(security.requestCurrentUser).to.not.be.null;
  }));

  it('should have a isAuthenticated method', inject(function(security) {
    expect(security.isAuthenticated).to.not.be.null;
  }));
  
  it('should have an oldPath property', inject(function(security) {
    expect(security.oldPath).to.be.defined;
  }));

  it('should have an currentUser property', inject(function(security) {
    expect(security.currentUser).to.be.defined;
  }));

});