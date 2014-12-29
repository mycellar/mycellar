angular.module('mycellar.controllers.cellar.io', [
  'ngRoute',
  'mycellar.resources.stock.cellars',
  'mycellar.resources.stock.movements'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/cellar/io', {
      templateUrl: 'partials/views/cellar/io.tpl.html',
      controller: 'InputOutputController',
      resolve: {
        cellars: ['Cellars', function(Cellars) {
          return Cellars.get({
            first: 0,
            count: 10,
            sort: ['name,asc']
          }).$promise;
        }],
        movements: ['Movements', function(Movements) {
          return Movements.getAllForCellar({
            first: 0,
            count: 50,
            sort: ['date,desc']
          }).$promise;
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.cellar.io').controller('InputOutputController', [
  '$scope', 'cellars', 'movements',
  function($scope, cellars, movements, Movements) {
    $scope.pageCount = 50;
    var getMovements = function(parameters) {
      var params = {
        first: parameters.first, 
        count: $scope.pageCount, 
        sort: [
          'date,desc'
        ]
      };
      if (parameters.cellar != null) {
        params['cellarId'] = parameters.cellar.id;
      }
      return Movements.getAllForCellar(params, parameters.callback);
    };
    $scope.selectCellar = function(cellar) {
      $scope.cellar = cellar;
      getMovements({
        cellar: $scope.cellar,
        first: 0,
        callback: function(value) {
          $scope.movements = value.list;
          $scope.size = value.count;
        }
      });
    };
    $scope.more = function() {
      getMovements({
        cellar: $scope.cellar,
        first: $scope.movements.length,
        callback: function(value) {
          $scope.movements = $scope.movements.concat(value.list);
        }
      });
    };
    $scope.movements = movements.list;
    $scope.size = movements.count;
  }
]);
