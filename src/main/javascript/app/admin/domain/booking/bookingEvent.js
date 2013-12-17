angular.module('mycellar.controllers.admin.domain.booking.bookingEvent', [
  'mycellar.resources.booking.bookingEvents',
  'mycellar.resources.wine.formats',
  'mycellar.resources.wine.wines',
  'mycellar.services.admin-domain',
  'mycellar.directives.form',
  'mycellar.directives.admin-domain-nav'
]);

angular.module('mycellar.controllers.admin.domain.booking.bookingEvent').controller('AdminDomainBookingEventController', [
  '$scope', 'bookingEvent', 'adminDomainService', 'Wines', 'Formats', '$filter',
  function ($scope, bookingEvent, adminDomainService, Wines, Formats, $filter) {
    $scope.bookingEvent = bookingEvent;
    angular.extend($scope, adminDomainService.editMethods('booking', 'BookingEvent', bookingEvent, 'form'));

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

    $scope.wines = function(input) {
      return Wines.like({
        input: input,
        first: 0,
        count: 20
      });
    };
    $scope.formats = function(input) {
      return Formats.nameLike(input);
    };

    var wineFilter = $filter('wineRenderer');
    $scope.wineLabel = function(wine) {
      if (wine != null) {
        return wineFilter(wine);
      } else {
        return '';
      }
    };
    var formatFilter = $filter('formatRenderer');
    $scope.formatLabel = function(format) {
      if (format != null) {
        return formatFilter(format);
      } else {
        return '';
      }
    };
  }
]);
