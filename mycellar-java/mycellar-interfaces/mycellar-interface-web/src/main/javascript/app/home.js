angular.module('home', ['resources.wine.wines'], ['$routeProvider', function($routeProvider){
  $routeProvider.when('/home', {
    templateUrl: 'partials/home.tpl.html',
    controller: 'HomeController',
    resolve: {
      winesCount: ['Wines', function(Wines){
        return Wines.count();
      }]
    }
  });
}]);

angular.module('home').controller('HomeController', ['$scope', 'winesCount', function ($scope, winesCount) {
  $scope.wineCount = winesCount;
}]);
