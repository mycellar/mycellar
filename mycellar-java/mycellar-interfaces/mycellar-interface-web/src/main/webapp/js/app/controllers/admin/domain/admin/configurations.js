'use strict';

angular.module('mycellar').controller({
  AdminDomainConfigurationsController: function ($scope, $resource, $http, $location) {
    $scope.sort = {
      properties: [
        'key'
      ],
      ways: {
        key: 'asc',
        value: null,
        email: null
      }
    };
    
    $scope.tableOptions = {
      itemResource: $resource('/api/domain/admin/configurations')
    };
    $scope.edit = function(itemId) {
      $location.path('/admin/domain/admin/configuration/' + itemId);
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
  },
  AdminDomainConfigurationController: function ($scope, $resource, $route, $location) {
    var configurationId = $route.current.params.configurationId;
    $scope.configurationResource = $resource('/api/domain/admin/configuration/:configurationId');
    $scope.configuration = $scope.configurationResource.get({configurationId: configurationId});
    $scope.save = function () {
      $scope.backup = {};
      angular.copy($scope.configuration, $scope.backup);
      $scope.configuration.$save(function (value, headers) {
        if (value.id != undefined) {
          $scope.backup = undefined;
          $location.path('/admin/domain/admin/configurations/');
        } else if (value.errorKey != undefined) {
          for (var property in value.properties) {
            $scope.form[value.properties[property]].$setValidity(value.errorKey, false);
          }
          angular.copy($scope.backup, $scope.configuration);
        } else {
          $scope.form.$setValidity('Error occured.', false);
          angular.copy($scope.backup, $scope.configuration);
        }
      });
    };
    $scope.cancel = function () {
      $location.path('/admin/domain/admin/configurations/');
    };
  }
});