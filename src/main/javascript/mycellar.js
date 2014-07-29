var mycellar  = window.mycellar || (window.mycellar = {});
var version = {
  full: '0.8.6-SNAPSHOT'
};
angular.extend(mycellar, {
  'version': version
});

angular.module('mycellar', [
  'ngRoute',
  'ngAnimate',
  'http-auth-interceptor',
  'angular-loading-bar',
  'mycellar.services.menu',
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
  'mycellar.controllers.navigation',
  'mycellar.controllers.password',
  'mycellar.directives.bind'
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
    // (in case they are still logged in from a previous session)
    security.requestCurrentUser();

    // plug polymer drawer tap
    var started = false;
    var ready = function() {
      var leftDrawerButtons = document.querySelectorAll('[left-drawer-button]');
      angular.forEach(leftDrawerButtons, function(leftDrawerButton) {
        leftDrawerButton.addEventListener('tap', function(e) {
          document.querySelector('core-drawer-panel:not(.right-drawer)').togglePanel();
        });
      });
      var rightDrawerButtons = document.querySelectorAll('[right-drawer-button]');
      angular.forEach(rightDrawerButtons, function(rightDrawerButton) {
        rightDrawerButton.addEventListener('tap', function(e) {
          document.querySelector('core-drawer-panel.right-drawer').togglePanel();
        });
      });
    };
    $rootScope.$on('$viewContentLoaded', function() {
      if (!started) {
        window.addEventListener('polymer-ready', ready);
        started = true;
      } else {
        ready();
      }
    });
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
