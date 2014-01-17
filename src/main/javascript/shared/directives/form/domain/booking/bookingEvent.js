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
      controller: function($scope) {
        $scope.bookingBottle = null;

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
        form: '=',
        bookingEvent: '=',
        postLabel: '@'
      },
      compile: function(element, attrs) {
        for (var attrName in attrs) {
          if (attrName.indexOf("input") == 0) {
            angular.element(element.find('input')[0]).attr(attrName.charAt(5).toLowerCase() + attrName.substring(6), attrs[attrName]);
          }
        }
      },
      controller: function($scope, BookingEvents) {
        $scope.errors = [];
        $scope.bookingEvents = BookingEvents.nameLike;
        $scope.new = function() {
          $scope.newBookingEvent = {};
          $scope.showSub = true;
        };
        $scope.cancel = function() {
          $scope.showSub = false;
        };
        $scope.ok = function() {
          $scope.errors = [];
          BookingEvents.validate($scope.newBookingEvent, function (value, headers) {
            if (value.errorKey != undefined) {
              angular.forEach(value.properties, function(property) {
                if ($scope.subBookingEventForm[property] != undefined) {
                  $scope.subBookingEventForm[property].$setValidity(value.errorKey, false);
                }
              });
              $scope.errors.push(value);
            } else {
              $scope.bookingEvent = $scope.newBookingEvent;
              $scope.showSub = false;
            }
          });
        };
      }
    }
  }
]);
