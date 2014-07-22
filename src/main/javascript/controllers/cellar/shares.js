angular.module('mycellar.controllers.cellar.shares', [
  'ngRoute',
  'mycellar.resources.stock.cellars',
  'mycellar.resources.stock.cellarShares',
  'mycellar.services.table'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/cellar/shares', {
      templateUrl: 'partials/views/cellar/shares.tpl.html',
      controller: 'SharesController',
      resolve: {
        cellars: ['Cellars', function(Cellars){
          return Cellars.getAllForCurrentUser();
        }],
        tableContext: ['tableService', 'CellarShares', function(tableService, CellarShares) {
          return tableService.createTableContext(CellarShares.getAllForCellar, ['email'], {cellarId: 0});
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.cellar.shares').controller('SharesController', [
  '$scope', 'cellars', 'tableContext',
  function($scope, cellars, tableContext) {
    $scope.cellarsResource = cellars;
    $scope.tableContext = tableContext;
    $scope.$watch('cellarsResource.list', function() {
      if ($scope.cellarsResource.list != undefined && $scope.cellarsResource.list.length > 0) {
        $scope.cellars = $scope.cellarsResource.list;
        $scope.selectCellar($scope.cellars[0]);
      }
    });

    $scope.selectCellar = function(cellar) {
      $scope.cellar = cellar;
    };

    var started = false;
    $scope.$watch('cellar.id', function (value) {
      if ($scope.cellar != null && $scope.cellar.id != undefined) {
        $scope.tableContext.parameters.cellarId = $scope.cellar.id;
        if (!started) {
          started = true;
          $scope.tableContext.setPage(1);
        }
      }
    });
  }
]);
