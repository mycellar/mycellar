angular.module('mycellar.directives.form.domain.stock.cellar', [
  'mycellar.resources.stock.cellars'
]);

angular.module('mycellar.directives.form.domain.stock.cellar').directive('cellarForm', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/stock/cellar-form.tpl.html',
      scope: {
        form: '=',
        cellar: '=',
        postLabel: '@'
      }
    }
  }
]).directive('cellar', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'partials/directives/form/stock/cellar.tpl.html',
      scope: {
        form: '=',
        cellar: '=',
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
        '$scope', '$location', 'Cellars', 'AdminCellars',
        function($scope, $route, Cellars, AdminCellars) {
          var resource;
          if ($location.path().match(/\/admin/)) {
            resource = AdminCellars;
          } else {
            resource = Cellars;
          }
          $scope.cellars = resource.like;
          $scope.errors = [];
          $scope.new = function() {
            $scope.newCellar = {};
            $scope.showSub = true;
          };
          $scope.cancel = function() {
            $scope.showSub = false;
          };
          $scope.ok = function() {
            $scope.errors = [];
            resource.validate($scope.newCellar, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if ($scope.subCellarForm[property] != undefined) {
                    $scope.subCellarForm[property].$setValidity(value.errorKey, false);
                  }
                });
                $scope.errors.push(value);
              } else {
                $scope.cellar = $scope.newCellar;
                $scope.showSub = false;
              }
            });
          };
        }
      ]
    }
  }
]);
