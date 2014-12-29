var mycellar  = window.mycellar || (window.mycellar = {});
var version = {
  full: '0.9.2-SNAPSHOT'
};
angular.extend(mycellar, {
  'version': version
});

window.addEventListener('polymer-ready', function() {
  angular.bootstrap(wrap(document), ['mycellar']);
});

angular.module('mycellar', [
  'ngRoute',
  'ngAnimate',
  'http-auth-interceptor',
  'angular-loading-bar',
  'mycellar.services.menu',
  'mycellar.services.error',
  'mycellar.services.security',
  'mycellar.services.login',
  'mycellar.controllers.errors',
  'mycellar.controllers.home',
  'mycellar.controllers.login',
  'mycellar.controllers.account',
  'mycellar.controllers.vinopedia',
  'mycellar.controllers.admin',
  'mycellar.controllers.booking',
  'mycellar.controllers.cellar',
  'mycellar.controllers.contact',
  'mycellar.controllers.navigation',
  'mycellar.controllers.password',
  'mycellar.directives.bind',
  'mycellar.directives.drag',
  'mycellar.directives.error'
]);

angular.module('mycellar').config([
  '$routeProvider', '$locationProvider', '$httpProvider', 
  function ($routeProvider, $locationProvider, $httpProvider) {
  $locationProvider.html5Mode(true);
  $routeProvider.when('/', {redirectTo: '/home'}).otherwise({redirectTo:'/404'});
  $httpProvider.interceptors.push([
    '$q', '$location',
    function($q, $location) {
      return {
        responseError: function(rejection) {
          if (rejection.status === 403) {
            $location.path('/403');
          }
          return $q.reject(rejection);
        }
      };
    }
  ]);
}]);

angular.module('mycellar').run([
  'security', 'authService', '$rootScope', '$q', '$location', 'loginDialogService',
  function(security, authService, $rootScope, $q, $location, loginDialogService) {
    $rootScope.$on('event:auth-loginRequired', function() {
      loginDialogService.create();
    });
    $rootScope.$on('event:auth-loginConfirmed', function() {
      loginDialogService.destroy();
    });
    $rootScope.$on('event:auth-loginCancelled', function() {
      loginDialogService.destroy();
    });

    // Get the current user when the application starts
    // (in case he is still logged in from a previous session)
    security.requestCurrentUser();
  }
]);
