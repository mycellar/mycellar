angular.module('mycellar.controllers.errors', [
  'ngRoute',
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/403', {
      templateUrl: 'partials/views/403.tpl.html'
    });
    $routeProvider.when('/404', {
      templateUrl: 'partials/views/404.tpl.html'
    });
  }
]);
