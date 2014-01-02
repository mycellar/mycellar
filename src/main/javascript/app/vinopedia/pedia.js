angular.module('mycellar.controllers.vinopedia.pedia', [
  'ngRoute',
  'mycellar.services.table',
  'mycellar.resources.wine.wines'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/vinopedia', {
      templateUrl: 'partials/vinopedia/pedia.tpl.html',
      controller: 'VinopediaController'
    });
  }
]);

angular.module('mycellar.controllers.vinopedia.pedia').controller('VinopediaController', [
  '$scope', 'tableService', 'Wines',
  function ($scope, tableService, Wines) {
    $scope.tableOptions = {
      itemResource: Wines.get,
      defaultSort: [
        'appellation.region.country.name',
        'appellation.region.name',
        'appellation.name',
        'producer.name',
        'name', 
        'vintage'
      ]
    };
    $scope.tableContext = tableService.createTableContext();
    $scope.errors = [];
  }
]);
