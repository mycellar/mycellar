'use strict';

angular.module('mycellar').controller({
  AdminNewVintagesController: function ($scope, $http, $timeout, countryService, tableService, wineService, regionService, producerService, appellationService) {
    $scope.tableOptions = {
      itemResource: wineService.resource.list,
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
    $scope.countries = countryService.getAllLike;
    $scope.regions = regionService.getAllLike;
    $scope.appellations = appellationService.getAllLike;
    $scope.producers = producerService.getAllLike;
  },
  AdminListsController: function () {
  }
});