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
        producer: '=',
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
        element[0].render = scope.renderProducer;
        element[0].clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        element[0].setValue = function(value) {
          scope.setProducer(value);
          scope.$apply();
        };
        element[0].value = scope.producer;
      },
      controller: [
        '$scope', '$location', 'Producers', 'AdminProducers',
        function($scope, $location, Producers, AdminProducers) {
          var resource;
          if ($location.path().match(/\/admin/)) {
            resource = AdminProducers;
          } else {
            resource = Producers;
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

          $scope.renderProducer = function(producer) {
            return producer.name;
          };
          $scope.setProducer = function(producer) {
            if (producer != null) {
              resource.get({id: producer.id}, function(value) {
                $scope.producer = value;
              });
            } else {
              $scope.producer = null;
            }
          }
        }
      ]
    }
  }
]);
