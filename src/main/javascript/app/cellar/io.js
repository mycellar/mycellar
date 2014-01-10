angular.module('mycellar.controllers.cellar.io', [
  'ngRoute',
  'mycellar.resources.stock.cellars',
  'mycellar.resources.stock.movements',
  'mycellar.services.table'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/cellar/io', {
      templateUrl: 'partials/cellar/io.tpl.html',
      controller: 'InputOutputController',
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

angular.module('mycellar.controllers.cellar.io').controller('InputOutputController', [
  '$scope', 'cellars', 'Movements', 'tableContext',
  function($scope, cellars, Movements, tableContext) {
    $scope.cellarsResource = cellars;
    $scope.$watch('cellarsResource.list', function() {
      if ($scope.cellarsResource.list != undefined && $scope.cellarsResource.list.length > 0) {
        $scope.owners = [];
        $scope.ownersMap = {};
        $scope.cellarsResource.list.forEach(function(cellar) {
          if ($scope.ownersMap[cellar.owner.email] == null) {
            $scope.owners.push(cellar.owner);
            $scope.ownersMap[cellar.owner.email] = [];
          }
          $scope.ownersMap[cellar.owner.email].push(cellar);
        });
        $scope.selectCellar($scope.ownersMap[$scope.owners[0].email][0]);
      }
    });

    $scope.selectCellar = function(cellar) {
      $scope.cellar = cellar;
    };

    $scope.tableOptions = {
        itemResource: Movements.getAllForCellar,
        defaultSort: ['date', 'date'],
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
