'use strict';

angular.module('mycellar').controller({
  AdminDomainProducersController: function ($scope, $resource, $http, $location) {
    $scope.sort = {
      properties: [
        'name',
      ],
      ways: {
        name: 'asc',
      }
    };
    $scope.filters = {
      name: ''
    }
    $scope.filtersIsCollapsed = true;
    
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/wine/producers')
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/wine/producer/' + itemId);
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
  },
  AdminDomainProducerController: function ($scope, $resource, $route, $location) {
    var producerId = $route.current.params.producerId;
    $scope.producerResource = $resource('/api/domain/wine/producer/:producerId');
    $scope.producer = $scope.producerResource.get({producerId: producerId});
    $scope.save = function () {
      $scope.backup = {};
      angular.copy($scope.producer, $scope.backup);
      $scope.producer.$save(function (value, headers) {
        if (value.id != undefined) {
          $scope.backup = undefined;
          $location.path('/admin/domain/wine/producers/');
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.form[value.properties[property]].$setValidity(value.errorKey, false);
          }
          angular.copy($scope.backup, $scope.producer);
        } else {
          $scope.form.$setValidity('Error occured.', false);
          angular.copy($scope.backup, $scope.producer);
        }
      });
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/wine/producers/');
    };
  }
});