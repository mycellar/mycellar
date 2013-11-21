describe("Unit: Testing AccountController", function() {

  beforeEach(angular.mock.module('mycellar.controllers.account'));

  it('should have a AccountController controller', function() {
    expect(mycellar.AccountController).not.to.equal(null);
  });

  it('should have a properly working AccountController controller', inject(function($rootScope, $controller) {
    var $scope = $rootScope.$new();
    var ctrl = $controller('AccountController', {
      $scope: $scope,
      security: {currentUser: {}},
      $routeParams: {}
    });
  }));
  
  describe('without user', function() {
    
    it('should have noProfile', inject(function($rootScope, $controller) {
      var $scope = $rootScope.$new();
      var ctrl = $controller('AccountController', {
        $scope: $scope,
        security: {currentUser: {}},
        $routeParams: {}
      });
      expect($scope.hasProfile).to.be.false;
    }));
    
  });
  
  describe('with BASIC user', function() {
    
    var user = {profile: 'BASIC'};
    
    it('should have correct profiles', inject(function($rootScope, $controller) {
      var $scope = $rootScope.$new();
      var ctrl = $controller('AccountController', {
        $scope: $scope,
        security: {currentUser: user},
        $routeParams: {}
      });
      expect($scope.hasProfile).to.be.true;
      expect($scope.hasBooking).to.be.false;
      expect($scope.hasAdmin).to.be.false;
      expect($scope.hasCellar).to.be.false;
    }));
    
  });
  
  describe('with BOOKING user', function() {
    
    var user = {profile: 'BOOKING'};
    
    it('should have correct profiles', inject(function($rootScope, $controller) {
      var $scope = $rootScope.$new();
      var ctrl = $controller('AccountController', {
        $scope: $scope,
        security: {currentUser: user},
        $routeParams: {}
      });
      expect($scope.hasProfile).to.be.true;
      expect($scope.hasBooking).to.be.true;
      expect($scope.hasAdmin).to.be.false;
      expect($scope.hasCellar).to.be.false;
    }));
    
    describe('with CELLAR user', function() {
      
      var user = {profile: 'CELLAR'};
      
      it('should have correct profiles', inject(function($rootScope, $controller) {
        var $scope = $rootScope.$new();
        var ctrl = $controller('AccountController', {
          $scope: $scope,
          security: {currentUser: user},
          $routeParams: {}
        });
        expect($scope.hasProfile).to.be.true;
        expect($scope.hasBooking).to.be.false;
        expect($scope.hasAdmin).to.be.false;
        expect($scope.hasCellar).to.be.true;
      }));
      
    });
    
    describe('with MYCELLAR user', function() {
      
      var user = {profile: 'MYCELLAR'};
      
      it('should have correct profiles', inject(function($rootScope, $controller) {
        var $scope = $rootScope.$new();
        var ctrl = $controller('AccountController', {
          $scope: $scope,
          security: {currentUser: user},
          $routeParams: {}
        });
        expect($scope.hasProfile).to.be.true;
        expect($scope.hasBooking).to.be.true;
        expect($scope.hasAdmin).to.be.false;
        expect($scope.hasCellar).to.be.true;
      }));
      
    });
    
    describe('with ADMIN user', function() {
      
      var user = {profile: 'ADMIN'};
      
      it('should have correct profiles', inject(function($rootScope, $controller) {
        var $scope = $rootScope.$new();
        var ctrl = $controller('AccountController', {
          $scope: $scope,
          security: {currentUser: user},
          $routeParams: {}
        });
        expect($scope.hasProfile).to.be.true;
        expect($scope.hasBooking).to.be.true;
        expect($scope.hasAdmin).to.be.true;
        expect($scope.hasCellar).to.be.true;
      }));
      
    });
    
  });

});
