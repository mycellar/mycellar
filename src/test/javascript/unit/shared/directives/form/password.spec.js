describe('Unit testing Directive: password', function() {
  var element, scope, compile,
    validTemplate = '<form name="form">' +
                      '<input ng-model="model.password" name="password" />' +
                      '<input ng-model="model.password2" name="password2" match="{{model.password}}" />' +
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
    scope.form.password2.$setViewValue('test');
    scope.$digest();
    expect(scope.model.password).to.equals('test');
    expect(scope.model.password).to.equals(scope.model.password2);
    expect(scope.form.$valid).to.be.true;
  });

  it('should not pass with differents values', function() {
    scope.model = { password: '', password2: '' };
    element = createDirective();
    scope.form.password.$setViewValue('test');
    scope.$digest();
    scope.form.password2.$setViewValue('te');
    scope.$digest();
    expect(scope.model.password).to.equals('test');
    expect(scope.model.password2).to.equals(undefined);
    expect(scope.form.$valid).to.be.false;
  });
});