angular.module('mycellar.directives.form.domain.wine.format', [
  'mycellar.resources.wine.formats'
]);

angular.module('mycellar.directives.form.domain.wine.format').directive('formatForm', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/wine/format-form.tpl.html',
      scope: {
        form: '=',
        format: '=',
        postLabel: '@'
      }
    }
  }
]).directive('format', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'partials/directives/form/wine/format.tpl.html',
      scope: {
        form: '=',
        format: '=',
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
        '$scope', '$location', '$filter', 'Formats', 'AdminFormats',
        function($scope, $location, $filter, Formats, AdminFormats) {
          var formatFilter = $filter('formatRenderer');
          $scope.renderFormat = function(format) {
            if (format != null) {
              return formatFilter(format);
            } else {
              return '';
            }
          };
          var resource;
          if ($location.path().match(/\/admin/)) {
            resource = AdminFormats;
          } else {
            resource = Formats;
          }
          $scope.errors = [];
          $scope.formats = resource.like;
          $scope.new = function() {
            $scope.newFormat = {};
            $scope.showSub = true;
          };
          $scope.cancel = function() {
            $scope.showSub = false;
          };
          $scope.ok = function() {
            $scope.errors = [];
            resource.validate($scope.newFormat, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if($scope.subFormatForm[property] != undefined) {
                    $scope.subFormatForm[value.properties[property]].$setValidity(value.errorKey, false);
                  }
                });
                $scope.errors.push(value);
              } else {
                $scope.format = $scope.newFormat;
                $scope.showSub = false;
              }
            });
          };
        }
      ]
    }
  }
]);
