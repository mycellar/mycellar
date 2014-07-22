angular.module('mycellar.directives.form.domain.wine.appellation', [
  'mycellar.directives.form.domain.wine.region',
  'mycellar.resources.wine.appellations'
]);

angular.module('mycellar.directives.form.domain.wine.appellation').directive('appellationForm', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/wine/appellation-form.tpl.html',
      scope: {
        form: '=',
        appellation: '=',
        postLabel: '@'
      }
    }
  }
]).directive('appellation', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'partials/directives/form/wine/appellation.tpl.html',
      scope: {
        form: '=',
        appellation: '=',
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
        '$scope', '$location', 'Appellations', 'AdminAppellations',
        function($scope, $location, Appellations, AdminAppellations) {
          var resource;
          if ($location.path().match(/\/admin/)) {
            resource = AdminAppellations;
          } else {
            resource = Appellations;
          }
          $scope.errors = [];
          $scope.appellations = resource.like;
          $scope.new = function() {
            $scope.newAppellation = {};
            $scope.showSub = true;
          };
          $scope.cancel = function() {
            $scope.showSub = false;
          };
          $scope.ok = function() {
            resource.validate($scope.newAppellation, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if ($scope.subAppellationForm[property] != undefined) {
                    $scope.subAppellationForm[property].$setValidity(value.errorKey, false);
                  }
                });
                $scope.errors.push(value);
              } else {
                $scope.appellation = $scope.newAppellation;
                $scope.showSub = false;
              }
            });
          };
        }
      ]
    }
  }
]);
