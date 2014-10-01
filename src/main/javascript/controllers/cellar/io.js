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
          return Cellars.getAllForCurrentUser().$promise;
        }],
       tableContext: ['tableService', 'Movements', function(tableService, Movements) {
         return tableService.createTableContext(
           Movements.getAllForCellar, 
           ['date', 'date'], 
           {cellarId: 0}
         );
       }]
      }
    });
  }
]);

angular.module('mycellar.controllers.cellar.io').controller('InputOutputController', [
  '$scope', 'cellars', 'tableContext',
  function($scope, cellars, tableContext) {
    $scope.tableContext = tableContext;
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
