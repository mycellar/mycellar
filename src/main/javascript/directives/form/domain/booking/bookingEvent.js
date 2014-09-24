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
        $scope.bookingEventStart = new Date($scope.bookingEvent.start);
        $scope.bookingEventEnd = new Date($scope.bookingEvent.end);
        $scope.$watch('bookingEventStart', function() {
          $scope.bookingEvent.start = $filter('date')($scope.bookingEventStart, 'yyyy-MM-dd');
        });
        $scope.$watch('bookingEventEnd', function() {
          $scope.bookingEvent.end = $filter('date')($scope.bookingEventEnd, 'yyyy-MM-dd');
        });

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

        $scope.moveUp = function(bookingBottle) {
          var position = bookingBottle.position;
          $scope.bookingEvent.bottles[position - 1].position++;
          $scope.bookingEvent.bottles[position].position--;
          $scope.bookingEvent.bottles[position] = $scope.bookingEvent.bottles.splice(position - 1, 1, $scope.bookingEvent.bottles[position])[0];
        };
        $scope.moveDown = function(bookingBottle) {
          var position = bookingBottle.position;
          $scope.bookingEvent.bottles[position].position++;
          $scope.bookingEvent.bottles[position + 1].position--;
          $scope.bookingEvent.bottles[position] = $scope.bookingEvent.bottles.splice(position + 1, 1, $scope.bookingEvent.bottles[position])[0];
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
        element[0].$.control.addEventListener('input', function() {
          scope.input = element[0].$.control.inputValue;
          scope.$apply();
        });
        scope.$watch('possibles', function(value) {
          element[0].possibles = value;
        });
        element[0].render = scope.renderBookingEvent;
        element[0].clearInput = function() {
          scope.input = '';
          scope.$apply();
        };
        element[0].setValue = function(value) {
          scope.setBookingEvent(value);
          scope.$apply();
        };
        element[0].value = scope.bookingEvent;
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
          }
        }
      ]
    }
  }
]);
