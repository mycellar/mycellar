angular.module('mycellar.controllers.home', [
  'ngRoute',
  'mycellar.resources.wine.wines'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/home', {
      templateUrl: 'partials/views/home.tpl.html',
      controller: 'HomeController',
      resolve: {
        winesCount: ['Wines', function(Wines){
          return Wines.count();
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.home').controller('HomeController', [
  '$scope', 'winesCount', 
  function ($scope, winesCount) {
    $scope.wineCount = winesCount;
  }
]);
