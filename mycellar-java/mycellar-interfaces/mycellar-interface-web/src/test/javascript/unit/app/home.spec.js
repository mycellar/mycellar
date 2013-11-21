describe("Unit: Testing HomeController", function() {

  beforeEach(angular.mock.module('mycellar.controllers.home'));

  it('should have a HomeController controller', function() {
    expect(mycellar.HomeController).not.to.equal(null);
  });

  it('should have a properly working HomeController controller', inject(function($rootScope, $controller) {
    var $scope = $rootScope.$new();
    var ctrl = $controller('HomeController', {
      $scope : $scope,
      winesCount: 10,
      $routeParams : {}
    });
  }));

});