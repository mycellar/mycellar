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
      if ($scope.search != null && $scope.search != '') {
        parameters['input'] = $scope.search;
      }
      return Stocks.getWinesForCellar(parameters, callback);
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
      getWines(cellar, 0, function(value) {
        cellarCallback(value, cellar);
      });
    };

    $scope.more = function() {
      getWines($scope.cellar, $scope.wines.length, function(value) {
        $scope.wines = $scope.wines.concat(value.list);
      });
    };

    $scope.toggleHidden = function() {
      var hiddenElements = document.querySelectorAll('core-toolbar[main]>[hidden]');
      var notHiddenElements = document.querySelectorAll('core-toolbar[main]>:not([hidden])');
      angular.forEach(hiddenElements, function(element) {
        element.removeAttribute('hidden');
      });
      angular.forEach(notHiddenElements, function(element) {
        element.setAttribute('hidden', '');
      });
      var searchInput = document.querySelector('core-input#search');
      if (searchInput.hasAttribute('hidden')) {
        $scope.search = '';
      } else {
        searchInput.focus();
      }
    };

    $scope.clearSearch = function() {
      $scope.search = '';
      document.querySelector('core-input#search').focus();
    };

    $scope.$watch('search', function(newValue, oldValue) {
      if (newValue !== oldValue) {
        return getWines($scope.cellar, 0, function(value) {
          cellarCallback(value, $scope.cellar);
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
