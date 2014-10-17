angular.module('mycellar.directives.form.domain.booking.bookingEvent', [
  'mycellar.directives.form.domain.wine.format',
  'mycellar.directives.form.domain.wine.wine',
  'mycellar.resources.booking.bookingEvents'
]);

angular.module('mycellar.directives.form.domain.booking.bookingEvent').directive('bookingEventForm', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/booking/bookingEvent-form.tpl.html',
      scope: {
        form: '=',
        bookingEvent: '=',
        postLabel: '@'
      },
      controller: function($scope, $filter) {
        $scope.bookingBottle = null;
        if ($scope.bookingEvent.bottles == undefined) {
          $scope.bookingEvent.bottles = [];
        }

        $scope.edit = function(bookingBottle) {
          $scope.bookingBottle = bookingBottle;
        };
        $scope.addBookingBottle = function() {
          $scope.bookingBottle = {
            bottle: {
              wine: null,
              format: null
            },
            max: 0,
            url: "http://www.cave-et-terroirs.fr/",
            price: 0,
            position: $scope.bookingEvent.bottles.length
          };
        };
        $scope.removeBookingBottle = function(bookingBottle) {
          angular.forEach($scope.bookingEvent.bottles, function(bottle) {
            if (bottle.position > bookingBottle.position) {
              bottle.position--;
            }
          });
          $scope.bookingEvent.bottles.splice($scope.bookingEvent.bottles.indexOf(bookingBottle), 1);
        };
        $scope.addBottle = function() {
          if ($scope.isNew()) {
            $scope.bookingEvent.bottles.push($scope.bookingBottle);
          }
          $scope.bookingBottle = null;
        };
        $scope.cancelBottle = function() {
          $scope.bookingBottle = null;
        };
        $scope.isNew = function() {
          return $scope.bookingBottle.position == $scope.bookingEvent.bottles.length;
        };

        $scope.bookingBottleMoved = function(bookingBottle) {
          for (var i = 0; i < $scope.bookingEvent.bottles.length; i++) {
            $scope.bookingEvent.bottles[i].position = i;
          }
        };
      }
    }
  }
]).directive('bookingEvent', [
  function() {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'partials/directives/form/booking/bookingEvent.tpl.html',
      scope: {
        bookingEvent: '=',
        label: '@'
      },
      link: function(scope, element, attrs) {
        var autocomplete = element[0].querySelector('autocomplete-input');
        autocomplete.$.control.addEventListener('input', function() {
          scope.input = autocomplete.$.control.inputValue;
          scope.$apply();
        });
        scope.$watch('possibles', function(value) {
          autocomplete.possibles = value;
        });
        autocomplete.render = scope.renderBookingEvent;
        autocomplete.clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        autocomplete.setValue = function(value) {
          scope.setBookingEvent(value);
          scope.$apply();
        };
        scope.$watch('bookingEvent', function() {
          autocomplete.value = scope.bookingEvent;
        });
      },
      controller: [
        '$scope', '$location', 'BookingEvents', 'AdminBookingEvents', '$filter',
        function($scope, $location, BookingEvents, AdminBookingEvents, $filter) {
          var resource;
          if ($location.path().match(/\/admin/)) {
            resource = AdminBookingEvents;
          } else {
            resource = BookingEvents;
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

          var bookingEventFilter = $filter('bookingEventRenderer');
          $scope.renderBookingEvent = function(bookingEvent) {
            if (bookingEvent != null) {
              return bookingEventFilter(bookingEvent);
            } else {
              return '';
            }
          };
          $scope.setBookingEvent = function(bookingEvent) {
            if (bookingEvent != null) {
              resource.get({id: bookingEvent.id}, function(value) {
                $scope.bookingEvent = value;
              });
            } else {
              $scope.bookingEvent = null;
            }
          };

          $scope.createMode = false;
          $scope.new = function() {
            $scope.createMode = true;
            $scope.newBookingEvent = {
              name: $scope.input
            };
          };
          $scope.cancel = function() {
            $scope.createMode = false;
            $scope.newBookingEvent = null;
          };
          $scope.validate = function() {
            resource.validate($scope.newBookingEvent, function (value, headers) {
              if (value.errorKey != undefined) {
                angular.forEach(value.properties, function(property) {
                  if ($scope.subBookingEventForm[property] != undefined) {
                    $scope.subBookingEventForm[property].$setValidity(value.errorKey, false);
                  }
                });
              } else {
                $scope.bookingEvent = $scope.newBookingEvent;
                $scope.createMode = false;
              }
            });
          };
        }
      ]
    }
  }
]);
