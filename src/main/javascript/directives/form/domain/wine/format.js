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
        var autocomplete = element[0].querySelector('autocomplete-input');
        autocomplete.$.control.addEventListener('input', function() {
          scope.input = autocomplete.inputValue;
          scope.$apply();
        });
        scope.$watch('possibles', function(value) {
          autocomplete.possibles = value;
        });
        autocomplete.render = scope.renderFormat;
        autocomplete.clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        autocomplete.setValue = function(value) {
          scope.setFormat(value);
          scope.$apply();
        };
        scope.$watch('format', function() {
          autocomplete.value = scope.format;
        });
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
          };

          $scope.createMode = false;
          $scope.new = function() {
            $scope.createMode = true;
            $scope.newFormat = {
              name: $scope.input
            };
          };
          $scope.cancel = function() {
            $scope.createMode = false;
            $scope.newFormat = null;
          };
          $scope.validate = function() {
            resource.validate($scope.newFormat, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if ($scope.subFormatForm[property] != undefined) {
                    $scope.subFormatForm[property].$setValidity(value.errorKey, false);
                  }
                });
              } else {
                $scope.format = $scope.newFormat;
                $scope.createMode = false;
              }
            });
          };
        }
      ]
    }
  }
]);
