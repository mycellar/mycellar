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
        format: '=',
        label: '@'
      },
      link: function(scope, element, attrs) {
        element[0].$.control.addEventListener('input', function() {
          scope.input = element[0].$.control.inputValue;
          scope.$apply();
        });
        scope.$watch('possibles', function(value) {
          element[0].possibles = value;
        });
        element[0].render = scope.renderFormat;
        element[0].clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        element[0].setValue = function(value) {
          scope.setFormat(value);
          scope.$apply();
        };
        element[0].value = scope.format;
      },
      controller: [
        '$scope', '$location', 'Formats', 'AdminFormats', '$filter',
        function($scope, $location, Formats, AdminFormats, $filter) {
          var resource;
          if ($location.path().match(/\/admin/)) {
            resource = AdminFormats;
          } else {
            resource = Formats;
          }

          $scope.input = '';
          $scope.$watch('input', function() {
            if ($scope.input.length > 2) {
              resource.like($scope.input).then(function(value) {
                $scope.possibles = value;
              });
            } else {
              $scope.possibles = [];
            }
          });

          var formatFilter = $filter('formatRenderer');
          $scope.renderFormat = function(format) {
            if (format != null) {
              return formatFilter(format);
            } else {
              return '';
            }
          };
          $scope.setFormat = function(format) {
            if (format != null) {
              resource.get({id: format.id}, function(value) {
                $scope.format = value;
              });
            } else {
              $scope.format = null;
            }
          }
        }
      ]
    }
  }
]);
