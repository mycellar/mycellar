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
        var autocomplete = element[0].querySelector('autocomplete-input');
        autocomplete.$.control.addEventListener('input', function() {
          scope.input = autocomplete.inputValue;
          scope.$apply();
        });
        scope.$watch('possibles', function(value) {
          autocomplete.possibles = value;
        });
        autocomplete.render = scope.renderProducer;
        autocomplete.clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        autocomplete.setValue = function(value) {
          scope.setProducer(value);
          scope.$apply();
        };
        scope.$watch('producer', function() {
          autocomplete.value = scope.producer;
        });
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
          };

          $scope.createMode = false;
          $scope.new = function() {
            $scope.createMode = true;
            $scope.newProducer = {
              name: $scope.input
            };
          };
          $scope.cancel = function() {
            $scope.createMode = false;
            $scope.newProducer = null;
          };
          $scope.validate = function() {
            resource.validate($scope.newProducer, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if ($scope.subProducerForm[property] != undefined) {
                    $scope.subProducerForm[property].$setValidity(value.errorKey, false);
                  }
                });
              } else {
                $scope.producer = $scope.newProducer;
                $scope.createMode = false;
              }
            });
          };
        }
      ]
    }
  }
]);
