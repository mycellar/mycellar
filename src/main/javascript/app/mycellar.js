var mycellar  = window.mycellar || (window.mycellar = {});
var version = {
  full: '0.7.0-SNAPSHOT'
};
angular.extend(mycellar, {
  'version': version
});

angular.module('mycellar', [
  'ngRoute',
  'mycellar.loading',
  'mycellar.services.bootstrap',
  'mycellar.services.menu',
  'mycellar.services.security',
  'mycellar.directives.bootstrap',
  'mycellar.controllers.home',
  'mycellar.controllers.login',
  'mycellar.controllers.account',
  'mycellar.controllers.admin',
  'mycellar.controllers.booking',
  'mycellar.controllers.navigation',
  'mycellar.controllers.password'
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
  firstText: '«',
  previousText: '‹',
  nextText: '›',
  lastText: '»',
  rotate: true,
  itemsPerPage: 10
});
