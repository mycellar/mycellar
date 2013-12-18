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
      templateUrl: 'partials/directives/form/wine/format.tpl.html',
      scope: {
        form: '=',
        format: '=',
        postLabel: '@'
      },
      controller: function($scope, Formats, $filter) {
        var formatFilter = $filter('formatRenderer');
        $scope.renderFormat = function(format) {
          if (format != null) {
            return formatFilter(format);
          } else {
            return '';
          }
        };
        $scope.formats = Formats.nameLike;
        $scope.new = function() {
          $scope.newProducer = {};
          $scope.showSub = true;
        };
        $scope.cancel = function() {
          $scope.showSub = false;
        };
        $scope.ok = function() {
          Formats.validate($scope.newProducer, function (value, headers) {
            if (value.internalError != undefined) {
              $scope.subProducerForm.$setValidity('Error occured.', false);
            } else if (value.errorKey != undefined) {
              for (var property in value.properties) {
                $scope.subProducerForm[value.properties[property]].$setValidity(value.errorKey, false);
              }
            } else {
              $scope.format = $scope.newProducer;
              $scope.showSub = false;
            }
          });
        };
      }
    }
  }
]);
