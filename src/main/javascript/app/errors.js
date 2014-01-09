angular.module('mycellar.controllers.errors', [
  'ngRoute',
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/403', {
      templateUrl: 'partials/403.tpl.html'
    });
    $routeProvider.when('/404', {
      templateUrl: 'partials/404.tpl.html'
    });
  }
]);
