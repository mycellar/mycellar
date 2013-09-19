var mycellar  = window.mycellar || (window.mycellar = {});
var version = {
  full: '0.6.3-SNAPSHOT'
};
angular.extend(mycellar, {
  'version': version
});

angular.module('mycellar', [
  'ui',
  'loading',
  'services.bootstrap',
  'services.menu',
  'services.security',
  'directives.bootstrap',
  'home',
  'login',
  'admin'
]);

angular.module('mycellar').config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
  $locationProvider.html5Mode(true);
  $routeProvider.otherwise({redirectTo:'/home'});
}]);

angular.module('mycellar').run([
  'security',  
  function(security) {
    // Get the current user when the application starts
    // (in case they are still logged in from a previous session)
    security.requestCurrentUser();
  }
]);

angular.module('mycellar').constant('paginationConfig', {
  boundaryLinks: true,
  directionLinks: true,
  firstText: '« First',
  previousText: '‹ Previous',
  nextText: 'Next ›',
  lastText: 'Last »',
  rotate: true
});
