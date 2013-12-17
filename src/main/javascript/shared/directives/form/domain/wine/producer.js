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
      templateUrl: 'partials/directives/form/wine/producer.tpl.html',
      scope: {
        form: '=',
        producer: '=',
        postLabel: '@'
      },
      controller: function($scope, Producers) {
        $scope.producers = Producers.nameLike;
        $scope.new = function() {
          $scope.newProducer = {};
          $scope.showSub = true;
        };
        $scope.cancel = function() {
          $scope.showSub = false;
        };
        $scope.ok = function() {
          Countries.validate($scope.newProducer, function (value, headers) {
            if (value.internalError != undefined) {
              $scope.subProducerForm.$setValidity('Error occured.', false);
            } else if (value.errorKey != undefined) {
              for (var property in value.properties) {
                $scope.subProducerForm[value.properties[property]].$setValidity(value.errorKey, false);
              }
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
