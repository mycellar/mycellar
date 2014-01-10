angular.module('mycellar.controllers.cellar.mycellars', [
  'ngRoute',
  'mycellar.resources.stock.cellars',
  'mycellar.resources.stock.stocks',
  'mycellar.services.table'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/cellar/mycellars', {
      templateUrl: 'partials/cellar/mycellars.tpl.html',
      controller: 'MyCellarsController',
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

angular.module('mycellar.controllers.cellar.mycellars').controller('MyCellarsController', [
  '$scope', 'cellars', 'Stocks', 'tableContext',
  function($scope, cellars, Stocks, tableContext) {
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
        itemResource: Stocks.getAllForCellar,
        defaultSort: ['bottle.wine.appellation.region.country.name',
                      'bottle.wine.appellation.region.name',
                      'bottle.wine.appellation.name',
                      'bottle.wine.producer.name',
                      'bottle.wine.name',
                      'bottle.wine.vintage',
                      'bottle.format.capacity'],
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
