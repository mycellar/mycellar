angular.module('mycellar.directives.form.domain.wine.region', [
  'mycellar.directives.form.domain.wine.country',
  'mycellar.resources.wine.regions'
]);

angular.module('mycellar.directives.form.domain.wine.region').directive('regionForm', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/wine/region-form.tpl.html',
      scope: {
        form: '=',
        region: '=',
        postLabel: '@'
      }
    }
  }
]).directive('region', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'partials/directives/form/wine/region.tpl.html',
      scope: {
        form: '=',
        region: '=',
        postLabel: '@'
      },
      compile: function(element, attrs) {
        for (var attrName in attrs) {
          if (attrName.indexOf("input") == 0) {
            angular.element(element.find('input')[0]).attr(attrName.charAt(5).toLowerCase() + attrName.substring(6), attrs[attrName]);
          }
        }
      },
      controller: [
        '$scope', '$location', 'Regions', 'AdminRegions',
        function($scope, $location, Regions, AdminRegions) {
          var resource;
          if ($location.path().match(/\/admin/)) {
            resource = AdminRegions;
          } else {
            resource = Regions;
          }
          $scope.errors = [];
          $scope.regions = resource.like;
          $scope.new = function() {
            $scope.newRegion = {};
            $scope.showSub = true;
          };
          $scope.cancel = function() {
            $scope.showSub = false;
          };
          $scope.ok = function() {
            $scope.errors = [];
            resource.validate($scope.newRegion, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if ($scope.subRegionForm[property] != undefined) {
                    $scope.subRegionForm[property].$setValidity(value.errorKey, false);
                  }
                });
                $scope.errors.push(value);
              } else {
                $scope.region = $scope.newRegion;
                $scope.showSub = false;
              }
            });
          };
        }
      ]
    }
  }
]);
