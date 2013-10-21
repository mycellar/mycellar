angular.module('admin.tools', [
  'resources.wine.wines',
  'resources.wine.countries',
  'resources.wine.regions',
  'resources.wine.appellations',
  'resources.wine.producers',
  'directives.table',
  'mycellar.services.table'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/admin/newVintages', {
      templateUrl: 'partials/admin/newVintages.tpl.html',
      controller: 'AdminNewVintagesController'
    });
  }
]);

angular.module('admin.tools').controller('AdminNewVintagesController', [
  '$scope', 'Wines', 'Countries', 'Regions', 'Appellations', 'Producers', 'tableService', 
  function ($scope, Wines, Countries, Regions, Appellations, Producers, tableService) {
    $scope.tableOptions = {
      itemResource: Wines,
      defaultSort: ['appellation.region.country.name',
                    'appellation.region.name',
                    'appellation.name',
                    'producer.name',
                    'name', 
                    'vintage']
    };
    $scope.tableContext = tableService.createTableContext();
    
    $scope.messages = [];
    $scope.errors = [];
    $scope.wines = [];
    $scope.from = 2000;
    $scope.to = 2010;
    $scope.running = false;
    $scope.createVintages = function() {
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
    $scope.countries = Countries.nameLike;
    $scope.regions = Regions.nameLike;
    $scope.appellations = Appellations.nameLike;
    $scope.producers = Producers.nameLike;
  }
]);
