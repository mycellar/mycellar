'use strict';

angular.module('mycellar').controller({
  AdminNewVintagesController: function ($scope, $http, $resource, $timeout) {
    $scope.sort = {
      properties: [
        'appellation.region.country.name',
        'appellation.region.name', 
        'appellation.name', 
        'producer.name',
        'name',
        'vintage'
      ],
      ways: {
        'appellation.region.country.name': 'asc',
        'appellation.region.name': 'asc',
        'appellation.name': 'asc',
        'producer.name': 'asc',
        'name': 'asc',
        'vintage': 'asc',
        'color': null,
        'type': null
      }
    };
    $scope.filters = {
      'appellation.region.country.name': '',
      'appellation.region.name': '',
      'appellation.name': '',
      'producer.name': '',
      'name': '',
      'vintage': '',
      'color': '',
      'type': ''
    };
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/wine/wines'),
    };
    $scope.sortBy = function(property) {
      if ($scope.sort.ways[property] == 'asc') {
        $scope.sort.ways[property] = 'desc';
      } else if ($scope.sort.ways[property] == 'desc') {
        $scope.sort.properties.splice($scope.sort.properties.indexOf(property), 1);
        $scope.sort.ways[property] = null;
      } else {
        $scope.sort.properties.push(property);
        $scope.sort.ways[property] = 'asc';
      }
    };
    $scope.clearFilters = function() {
      for (var filter in $scope.filters) {
        $scope.filters[filter] = '';
      }
    };
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
    $scope.countries = function (countryName) {
      return $http.get("/api/domain/wine/countries?count=15&filters=name,"+countryName+"&first=0&sort=name,asc").then(function(response){
        return response.data.list;
      });
    };
    $scope.regions = function (regionName) {
      return $http.get("/api/domain/wine/regions?count=15&filters=name,"+regionName+"&first=0&sort=name,asc").then(function(response){
        return response.data.list;
      });
    };
    $scope.appellations = function (appellationName) {
      return $http.get("/api/domain/wine/appellations?count=15&filters=name,"+appellationName+"&first=0&sort=region.country.name,asc&sort=region.name,asc&sort=name,asc").then(function(response){
        return response.data.list;
      });
    };
    $scope.producers = function (producerName) {
      return $http.get("/api/domain/wine/producers?count=15&filters=name,"+producerName+"&first=0&sort=name,asc").then(function(response){
        return response.data.list;
      });
    };
  },
  AdminListsController: function () {
  }
});