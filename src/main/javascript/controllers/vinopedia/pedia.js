angular.module('mycellar.controllers.vinopedia.pedia', [
  'ngRoute',
  'mycellar.services.table',
  'mycellar.resources.wine.wines'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/vinopedia', {
      templateUrl: 'partials/views/vinopedia/pedia.tpl.html',
      controller: 'VinopediaController',
      resolve: {
        tableContext: ['tableService', 'Wines', function(tableService, Wines) {
          var tableContext = tableService.createTableContext(Wines.get, [
            'appellation.region.country.name',
            'appellation.region.name',
            'appellation.name',
            'producer.name',
            'name', 
            'vintage'
          ]);
          return tableContext.setPage(1).promise;
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.vinopedia.pedia').controller('VinopediaController', [
  '$scope', 'tableContext',
  function ($scope, tableContext) {
    $scope.tableContext = tableContext;
  }
]);
