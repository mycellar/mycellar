describe("Unit: Testing ResetPasswordRequestController", function() {

  beforeEach(angular.mock.module('mycellar.controllers.password'));

  it('should have a ResetPasswordRequestController controller', function() {
    expect(mycellar.ResetPasswordRequestController).not.to.equal(null);
  });

  it('should have a properly working ResetPasswordRequestController controller', inject(function($rootScope, $controller, $httpBackend) {
    var $scope = $rootScope.$new();
    var ctrl = $controller('ResetPasswordRequestController', {
      $scope: $scope,
      $routeParams: {}
    });
  }));

});

describe("Unit: Testing ResetPasswordController", function() {

  beforeEach(angular.mock.module('mycellar.controllers.password'));

  it('should have a ResetPasswordController controller', function() {
    expect(mycellar.ResetPasswordController).not.to.equal(null);
  });

  it('should have a properly working ResetPasswordController controller', inject(function($rootScope, $controller, $httpBackend) {
    var $scope = $rootScope.$new();
    var ctrl = $controller('ResetPasswordController', {
      $scope: $scope,
      key: 'toto',
      email: 't@t.fr',
      $routeParams: {key:'toto'}
    });
  }));

});