describe('Unit Testing: Service: security', function() {
  var security, menuService, $httpBackend, $rootScope;

  beforeEach(function() {
    angular.mock.module('mycellar.services.security');
    angular.mock.module(function($provide) {
      $provide.value('menuService', {});
    });
    inject(function(_security_, _$httpBackend_, _$rootScope_, _menuService_) {
      security = _security_;
      $httpBackend = _$httpBackend_;
      $rootScope = _$rootScope_;
      menuService = _menuService_;
    });
  });

  it('should exist', function() {
    expect(!!security).to.be.true;
  });

  describe('which api', function() {
    it('should have a login method', function() {
      expect(security.login).to.not.be.null;
    });

    it('should have a logout method', function() {
      expect(security.logout).to.not.be.null;
    });

    it('should have a requestCurrentUser method', function() {
      expect(security.requestCurrentUser).to.not.be.null;
    });

    it('should have a isAuthenticated method', function() {
      expect(security.isAuthenticated).to.not.be.null;
    });

    it('login and logout should call backend', function() {
      var email = 'e@mail.com';
      var password = 'pass';
      var user = {user: {id: 0}};
      
      menuService.reloadMenus = sinon.spy();
      $httpBackend.expectPOST('/api/login', {email: email, password: password}).respond(user);
      security.login(email, password);
      $httpBackend.flush();
      expect($rootScope.currentUser).to.deep.equals(user);
      expect(menuService.reloadMenus.calledOnce).to.be.true;

      menuService.reloadMenus = sinon.spy();
      $httpBackend.expectPOST('/api/logout').respond('');
      security.logout();
      $httpBackend.flush();
      expect($rootScope.currentUser).to.be.null;
      expect(menuService.reloadMenus.calledOnce).to.be.true;
    });
  });

  afterEach(function() {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });
});