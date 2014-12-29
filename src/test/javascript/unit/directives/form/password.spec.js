describe('Unit testing Directive: password', function() {
  var element, scope, compile,
    validTemplate = '<form name="form">' +
                      '<input type="text" ng-model="model.password" name="password" />' +
                      '<input type="text" ng-model="model.password2" name="password2" match="form.password" />' +
                    '</form>';
  function createDirective(template) {
    var elm = compile(template || validTemplate)(scope);
    scope.$digest();
    return elm;
  };
  beforeEach(function() {
    angular.mock.module('mycellar.directives.form.password');
    inject(function($rootScope, $compile) {
      scope = $rootScope.$new();
      compile = $compile;
    });
  });

  it('should pass with same values', function() {
    scope.model = { password: '', password2: '' };
    element = createDirective();
    scope.form.password.$setViewValue('test');
    scope.$digest();
    expect(scope.model.password).to.equals('test');
    scope.form.password2.$setViewValue('test');
    scope.$digest();
    expect(scope.model.password2).to.equals('test');
    expect(scope.form.$valid).to.be.true;
  });

  it('should not pass with differents values', function() {
    scope.model = { password: '', password2: '' };
    element = createDirective();
    scope.form.password.$setViewValue('test');
    scope.$digest();
    expect(scope.model.password).to.equals('test');
    scope.form.password2.$setViewValue('te');
    scope.$digest();
    expect(scope.model.password2).to.equals(undefined);
    expect(scope.form.$valid).to.be.false;
  });
});