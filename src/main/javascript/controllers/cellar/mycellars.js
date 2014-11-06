angular.module('mycellar.controllers.cellar.mycellars', [
  'ngRoute',
  'mycellar.resources.stock.cellars',
  'mycellar.resources.stock.stocks'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/cellar/mycellars', {
      templateUrl: 'partials/views/cellar/mycellars.tpl.html',
      controller: 'MyCellarsController',
      resolve: {
        cellars: ['Cellars', function(Cellars) {
          return Cellars.get({
            first: 0,
            count: 10,
            sort: ['name,asc']
          }).$promise;
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
  '$scope', 'cellars', 'wines', 'Stocks', '$location', 'search',
  function($scope, cellars, wines, Stocks, $location, search) {
    var getWines = function(parameters) {
      if (parameters.input === $scope.search) {
        var params = {
          first: parameters.first, 
          count: 50, 
          sort: [
            'bottle.wine.producer.name,asc',
            'bottle.wine.name,asc',
            'bottle.wine.vintage,asc'
          ]
        };
        if (parameters.cellar != null) {
          params['cellarId'] = parameters.cellar.id;
        }
        if ($scope.search != null && $scope.search != '') {
          params['input'] = $scope.search;
        }
        return Stocks.getWinesForCellar(params, parameters.callback);
      }
    };

    var cellarCallback = function(value, cellar) {
      $scope.wines = value.list;
      $scope.size = value.count;
      $scope.cellar = cellar;
    };

    $scope.search = '';
    if (cellars != null) {
      $scope.cellars = cellars.list;
    }
    if (wines != null) {
      cellarCallback(wines, null);
    }

    $scope.selectWine = function(wine) {
      $location.path('/cellar/mywine/' + wine.id);
    };

    $scope.selectCellar = function(cellar) {
      getWines({
        input: $scope.search, 
        cellar: cellar, 
        first: 0, 
        callback: function(value) {
          cellarCallback(value, cellar);
        }
      });
    };

    $scope.more = function() {
      getWines({
        input: $scope.search,
        cellar: $scope.cellar,
        first: $scope.wines.length,
        callback: function(value) {
          $scope.wines = $scope.wines.concat(value.list);
        }
      });
    };

    $scope.toggleHidden = function() {
      search.toggleHidden();
      $scope.search = '';
    };
    $scope.clearSearch = function() {
      search.clearSearch();
      $scope.search = '';
    };
    $scope.$watch('search', function(newValue, oldValue) {
      if (newValue !== oldValue) {
        return search.scheduleSearch(getWines, {
          input: newValue,
          cellar: $scope.cellar,
          first: 0,
          callback: function(value) {
            cellarCallback(value, $scope.cellar);
          }
        });
      }
    });
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
