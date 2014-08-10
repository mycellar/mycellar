angular.module('mycellar.controllers.admin.tools', [
  'ngRoute',
  'mycellar.resources.wine.wines',
  'mycellar.resources.wine.countries',
  'mycellar.resources.wine.regions',
  'mycellar.resources.wine.appellations',
  'mycellar.resources.wine.producers',
  'mycellar.directives.table',
  'mycellar.services.table'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/admin/newVintages', {
      templateUrl: 'partials/views/admin/newVintages.tpl.html',
      controller: 'AdminNewVintagesController'
    });
  }
]);

angular.module('mycellar.controllers.admin.tools').controller('AdminNewVintagesController', [
  '$scope', 'AdminWines', 'AdminCountries', 'AdminRegions', 'AdminAppellations', 'AdminProducers', 'tableService', '$http', 
  function ($scope, Wines, Countries, Regions, Appellations, Producers, tableService, $http) {
    $scope.tableContext = tableService.createTableContext(Wines.get, ['appellation.region.country.name',
                                                                      'appellation.region.name',
                                                                      'appellation.name',
                                                                      'producer.name',
                                                                      'name', 
                                                                      'vintage']);
    $scope.tableContext.setPage(1);
    $scope.messages = [];
    $scope.errors = [];
    $scope.wines = [];
    $scope.from = 2000;
    $scope.to = 2010;
    $scope.running = false;
    $scope.createVintages = function() {
      $scope.wines = $scope.tableContext.items;
      $scope.count = 0;
      function createVintage(wines, idxs) {
        var idx = idxs.pop();
        if (idx != null) {
          $http.post("/api/admin/tools/createVintages?from="+$scope.from+"&to="+$scope.to, wines[idx]).then(function(response) {
            $scope.count++;
            if ($scope.count == wines.length) {
              $scope.running = false;
            }
            if (response.data.errorKey != undefined) {
              $scope.errors.push({errorKey: response.data.errorKey});
            } else if (response.data.internalError != undefined) {
              $scope.errors.push({errorKey: response.data.internalError});
            } else {
              $scope.messages.push({count: response.data.length});
            }
            createVintage(wines, idxs);
          });
        }
      }
      if ($scope.wines.length > 0) {
        $scope.running = true;
        $scope.messages = [];
        $scope.errors = [];
        var idxs = [];
        for (var idx in $scope.wines) {
          idxs.push(idx);
        }
        createVintage($scope.wines, idxs);
        createVintage($scope.wines, idxs);
        createVintage($scope.wines, idxs);
      }
    };
    $scope.countries = Countries.like;
    $scope.regions = Regions.like;
    $scope.appellations = Appellations.like;
    $scope.producers = Producers.like;
  }
]);