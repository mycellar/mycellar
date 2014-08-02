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
        cellars: ['Cellars', function(Cellars) {
          return Cellars.getAllForCurrentUser().$promise;
        }],
        wines: ['Stocks', function(Stocks) {
          return Stocks.getWinesForCellar({
            first: 0, 
            count: 50, 
            sort: [
              'bottle.wine.producer.name,asc',
              'bottle.wine.name,asc',
              'bottle.wine.vintage,asc'
            ]
          }).$promise; 
        }]
      }
    }).when('/cellar/mywine/:wineId', {
      templateUrl: 'partials/views/cellar/mywine.tpl.html',
      controller: 'MyWineController',
      resolve: {
        wine: ['Wines', '$route', function(Wines, $route) {
          var id = $route.current.params.wineId;
          return Wines.get({id: id}).$promise;
        }],
        stocks: ['Stocks', '$route', function(Stocks, $route) {
          var id = $route.current.params.wineId;
          return Stocks.getAllForWine({
            wineId: id,
            first: 0,
            count: 10,
            sort: [
              'cellar.name,asc'
            ]
          }).$promise;
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.cellar.mycellars').controller('MyCellarsController', [
  '$scope', 'cellars', 'wines', 'Stocks', '$location',
  function($scope, cellars, wines, Stocks, $location) {
    var getWines = function(cellar, first, callback) {
      var parameters = {
        first: first, 
        count: 50, 
        sort: [
          'bottle.wine.producer.name,asc',
          'bottle.wine.name,asc',
          'bottle.wine.vintage,asc'
        ]
      };
      if (cellar != null) {
        parameters['cellarId'] = cellar.id;
      }
      return Stocks.getWinesForCellar(parameters, callback);
    };
    var cellarCallback = function(value, cellar) {
      $scope.wines = value.list;
      $scope.size = value.count;
      $scope.cellar = cellar;
      if ($scope.cellar != null && $scope.cellar.name != undefined) {
        $scope.title = 'Mes caves > ' + $scope.cellar.name;
      } else {
        $scope.title = 'Mes caves > Toutes';
      }
    };

    $scope.selectWine = function(wine) {
      $location.path('/cellar/mywine/' + wine.id);
    };

    $scope.selectCellar = function(cellar) {
      getWines(cellar, 0, function(value) {
        cellarCallback(value, cellar);
      });
    };

    $scope.more = function() {
      getWines($scope.cellar, $scope.wines.length, function(value) {
        $scope.wines = $scope.wines.concat(value.list);
      });
    };

    if (cellars != null) {
      $scope.cellars = cellars.list;
    }
    if (wines != null) {
      cellarCallback(wines, null);
    }
  }
]);

angular.module('mycellar.controllers.cellar.mycellars').controller('MyWineController', [
  '$scope', 'stocks', 'wine',
  function($scope, stocks, wine) {
    $scope.wine = wine;
    if (stocks != null) {
      $scope.stocks = stocks.list;
    }
  }
]);
