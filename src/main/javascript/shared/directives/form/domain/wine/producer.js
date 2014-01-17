angular.module('mycellar.directives.form.domain.wine.producer', [
  'mycellar.resources.wine.producers'
]);

angular.module('mycellar.directives.form.domain.wine.producer').directive('producerForm', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/wine/producer-form.tpl.html',
      scope: {
        form: '=',
        producer: '=',
        postLabel: '@'
      }
    }
  }
]).directive('producer', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'partials/directives/form/wine/producer.tpl.html',
      scope: {
        form: '=',
        producer: '=',
        postLabel: '@'
      },
      compile: function(element, attrs) {
        for (var attrName in attrs) {
          if (attrName.indexOf("input") == 0) {
            angular.element(element.find('input')[0]).attr(attrName.charAt(5).toLowerCase() + attrName.substring(6), attrs[attrName]);
          }
        }
      },
      controller: function($scope, Producers) {
        $scope.errors = [];
        $scope.producers = Producers.nameLike;
        $scope.new = function() {
          $scope.newProducer = {};
          $scope.showSub = true;
        };
        $scope.cancel = function() {
          $scope.showSub = false;
        };
        $scope.ok = function() {
          $scope.errors = [];
          Producers.validate($scope.newProducer, function (value, headers) {
            if (value.errorKey != undefined) {
              angular.forEach(value.properties, function(property) {
                if ($scope.subProducerForm[property] != undefined) {
                  $scope.subProducerForm[property].$setValidity(value.errorKey, false);
                }
              });
              $scope.errors.push(value);
            } else {
              $scope.producer = $scope.newProducer;
              $scope.showSub = false;
            }
          });
        };
      }
    }
  }
]);
