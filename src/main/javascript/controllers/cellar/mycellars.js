angular.module('mycellar.controllers.cellar.mycellars', [
  'ngRoute',
  'mycellar.resources.stock.cellars',
  'mycellar.resources.stock.stocks',
  'mycellar.services.table'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/cellar/mycellars', {
      templateUrl: 'partials/views/cellar/mycellars.tpl.html',
      controller: 'MyCellarsController',
      resolve: {
        cellars: ['Cellars', function(Cellars){
          return Cellars.getAllForCurrentUser().$promise;
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.cellar.mycellars').controller('MyCellarsController', [
  '$scope', 'cellars', 'Stocks',
  function($scope, cellars, Stocks) {
    var getStocks = function(cellar, first, callback) {
      return Stocks.getAllForCellar({
        cellarId: cellar.id, 
        first: first, 
        count: 50, 
        sort: [
          'bottle.wine.producer.name,asc',
          'bottle.wine.name,asc',
          'bottle.wine.vintage,asc',
          'bottle.format.capacity,asc'
        ]
      },
      callback);
    };

    $scope.selectCellar = function(cellar) {
      if (cellar != null && cellar.id != undefined) {
        getStocks(cellar, 0, function(value) {
          $scope.stocks = value.list;
          $scope.size = value.count;
          $scope.cellar = cellar;
        });
      }
    };

    $scope.more = function() {
      getStocks($scope.cellar, $scope.stocks.length, function(value) {
        $scope.stocks = $scope.stocks.concat(value.list);
      });
    };

    if (cellars != null) {
      $scope.cellars = cellars.list;
      if ($scope.cellars != null && $scope.cellars.length > 0) {
        $scope.selectCellar($scope.cellars[0]);
      }
    }
  }
]);
