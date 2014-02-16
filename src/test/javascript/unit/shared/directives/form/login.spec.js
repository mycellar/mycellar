describe('Unit Testing: Directive: login', function() {
  var element, scope, compile, security,
    validTemplate = '<div><login-form /></div>';

  function createDirective(template) {
  var elm = compile(template || validTemplate)(scope);
  scope.$digest();
  return elm;
  };
  beforeEach(function() {
    module(function($provide) {
      $provide.value('security', {});
    });
    module('mycellar.directives.form.login', 'partials/directives/form/login-form.tpl.html');
    inject(function($rootScope, $compile, _security_) {
      scope = $rootScope.$new();
      compile = $compile;
      security = _security_;
    });
  });

  it('should render a login form', function() {
    element = createDirective();
    var form = element.find('form');
    expect(form).not.to.be.empty;
    expect(form.find('input').length).to.equals(5);
    expect(form.find('input')[0].id).to.equals('email');
    expect(form.find('input')[1].id).to.equals('password');
    expect(form.find('input')[2].type).to.equals('submit');
    expect(form.find('input')[3].type).to.equals('button');
    expect(form.find('input')[4].type).to.equals('button');
  });

  it('should permit login', function() {
    var email = 'e@mail.com';
    var password = 'pass';

    security.login = function() {};
    var mock = sinon.mock(security);
    mock.expects("login").once().withExactArgs(email, password).returns({then: function() {}});

    element = createDirective();
    var directiveScope = element.find('form').isolateScope();

    directiveScope.loginForm.email.$setViewValue(email);
    directiveScope.loginForm.password.$setViewValue(password);
    scope.$digest();

    expect(directiveScope.email).to.equals(email);
    expect(directiveScope.password).to.equals(password);

    angular.element(element.find('input')[2]).triggerHandler('click');

    mock.verify();
  });
});
