var mycellar  = window.mycellar || (window.mycellar = {});
var version = {
  full: '0.7.3-SNAPSHOT'
};
angular.extend(mycellar, {
  'version': version
});

angular.module('mycellar', [
  'ngRoute',
  'ui.bootstrap',
  'http-auth-interceptor',
  'mycellar.loading',
  'mycellar.services.menu',
  'mycellar.services.security',
  'mycellar.controllers.errors',
  'mycellar.controllers.home',
  'mycellar.controllers.login',
  'mycellar.controllers.account',
  'mycellar.controllers.vinopedia',
  'mycellar.controllers.admin',
  'mycellar.controllers.booking',
  'mycellar.controllers.cellar',
  'mycellar.controllers.navigation',
  'mycellar.controllers.password',
  'mycellar.directives.form.login'
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
  'security', 'authService', '$rootScope', '$modal', '$location',
  function(security, authService, $rootScope, $modal, $location) {
    var loginModal;
    $rootScope.$on('event:auth-loginRequired', function() {
      if (loginModal == null) {
        loginModal = $modal.open({
          templateUrl: 'partials/modals/login.tpl.html'
        });
        loginModal.result.then(function(result) {
          security.login(result.email, result.password);
        }, function(reason) {
          authService.loginCancelled(reason.requestPassword, reason.reason);
          security.logout();
        });
      }
    });
    $rootScope.$on('event:auth-loginConfirmed', function() {
      loginModal = null;
    });
    $rootScope.$on('event:auth-loginCancelled', function(event, requestPassword) {
      if (requestPassword) {
        $location.path('/reset-password-request');
      } else {
        $location.path('/');
      }
      loginModal = null;
    });

    // Get the current user when the application starts
    // (in case they are still logged in from a previous session)
    security.requestCurrentUser();
  }
]);

angular.module('mycellar').constant('paginationConfig', {
  boundaryLinks: true,
  directionLinks: true,
  firstText: '«',
  previousText: '‹',
  nextText: '›',
  lastText: '»',
  rotate: true,
  itemsPerPage: 10
});
