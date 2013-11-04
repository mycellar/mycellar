describe('Unit: Testing password directive', function() {
  var $scope;
  beforeEach(angular.mock.module('mycellar.directives.password'));
  beforeEach(inject(function($compile, $rootScope) {
    $scope = $rootScope;
    var element = angular.element(
      '<form name="form">' +
        '<input ng-model="model.password" name="password" equals="{{model.password2}}" />' +
        '<input ng-model="model.password2" name="password2" equals="{{model.password}}" />' +
      '</form>'
    );
    $scope.model = { password: '', password2: '' }
    $compile(element)($scope);
    $scope.$digest();
  }));

  it('should pass with same values', function() {
    $scope.form.password.$setViewValue('test');
    $scope.form.password2.$setViewValue('test');
    $scope.$digest();
    expect($scope.model.password).to.equals('test');
    expect($scope.model.password).to.equals($scope.model.password2);
    expect($scope.form.$valid).to.be.true;
  });

  it('should not pass with differents values', function() {
    $scope.form.password.$setViewValue('test');
    $scope.form.password2.$setViewValue('te');
    $scope.$digest();
    expect($scope.model.password).to.equals('test');
    expect($scope.model.password2).to.equals('te');
    expect($scope.form.$valid).to.be.false;
  });
});