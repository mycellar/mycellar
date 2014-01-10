angular.module('mycellar.controllers.cellar.shares', [
  'ngRoute',
  'mycellar.resources.stock.cellars',
  'mycellar.resources.stock.cellarShares',
  'mycellar.services.table'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/cellar/shares', {
      templateUrl: 'partials/cellar/shares.tpl.html',
      controller: 'SharesController',
      resolve: {
        cellars: ['Cellars', function(Cellars){
          return Cellars.getAllForCurrentUser();
        }],
        tableContext: ['tableService', function(tableService) {
          return tableService.createTableContext();
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.cellar.shares').controller('SharesController', [
  '$scope', 'cellars', 'CellarShares', 'tableContext',
  function($scope, cellars, CellarShares, tableContext) {
    $scope.cellarsResource = cellars;
    $scope.$watch('cellarsResource.list', function() {
      if ($scope.cellarsResource.list != undefined && $scope.cellarsResource.list.length > 0) {
        $scope.cellars = $scope.cellarsResource.list;
        $scope.selectCellar($scope.cellars[0]);
      }
    });

    $scope.selectCellar = function(cellar) {
      $scope.cellar = cellar;
    };

    $scope.tableOptions = {
        itemResource: CellarShares.getAllForCellar,
        defaultSort: ['email'],
        parameters : {cellarId: 0}
    };
    $scope.tableContext = tableContext;

    $scope.$watch('cellar.id', function (value) {
      if ($scope.cellar != null && $scope.cellar.id != undefined) {
        $scope.tableOptions.parameters.cellarId = $scope.cellar.id;
      }
    });
  }
]);
