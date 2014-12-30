angular.module('mycellar.controllers.admin.tools', [
  'ngRoute',
  'mycellar.resources.wine.wines',
  'mycellar.resources.wine.countries',
  'mycellar.resources.wine.regions',
  'mycellar.resources.wine.appellations',
  'mycellar.resources.wine.producers'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/admin/newVintages', {
      templateUrl: 'partials/views/admin/newVintages.tpl.html',
      controller: 'AdminNewVintagesController',
      resolve: {
        wines: ['AdminWines', function(Wines) {
          return Wines.get({
            first: 0,
            count: 20,
            sort: [
              'producer.name,asc',
              'name,asc',
              'vintage,asc'
            ]
          }).$promise;
        }]
      }
    }).when('/admin/nextBookingEvent', {
      templateUrl: 'partials/views/admin/nextBookingEvent.tpl.html',
      controller: 'AdminNextBookingEventController'
    });
  }
]);

angular.module('mycellar.controllers.admin.tools').controller('AdminNewVintagesController', [
  '$scope', '$http', 'AdminWines', 'wines', 'search',
  function ($scope, $http, Wines, wines, search) {
    $scope.messages = [];
    $scope.errors = [];
    $scope.search = '';
    $scope.pageCount = 20;
    $scope.wines = wines.list;
    $scope.size = wines.count;
    $scope.vintages = {
      from: 2000,
      to: 2010
    };
    $scope.toggleHidden = function() {
      search.toggleHidden();
      $scope.search = '';
    };
    $scope.clearSearch = function() {
      search.clearSearch();
      $scope.search = '';
    };
    $scope.createVintages = function() {
      function createVintage(wines, idxs) {
        var idx = idxs.pop();
        if (idx != null) {
          $http.post("/api/admin/tools/createVintages?from="+$scope.vintages.from+"&to="+$scope.vintages.to, wines[idx]).then(function(response) {
            $scope.messages.push({wine: wines[idx], count: response.data.length});
            createVintage(wines, idxs);
          }, function(response) {
            if (response.data.errorKey != undefined) {
              $scope.errors.push({wine: wines[idx], errorKey: response.data.errorKey});
            } else if (response.data.internalError != undefined) {
              $scope.errors.push({wine: wines[idx], errorKey: response.data.internalError});
            } else {
              $scope.errors.push({wine: wines[idx], errorKey: 'Erreur ' + response.status});
            }
            createVintage(wines, idxs);
          });
        }
      }
      if ($scope.wines.length > 0) {
        $scope.messages = [];
        $scope.errors = [];
        var idxs = [];
        for (var idx in $scope.wines) {
          idxs.push(idx);
        }
        createVintage($scope.wines, idxs);
        createVintage($scope.wines, idxs);
        createVintage($scope.wines, idxs);
      }
    };
    var getWines = function(parameters) {
      if (parameters.input === $scope.search) {
        var params = {
          first: parameters.first,
          count: $scope.pageCount,
          sort: [
            'producer.name,asc',
            'name,asc',
            'vintage,asc'
          ]
        };
        if ($scope.search != null && $scope.search != '') {
          params['like'] = $scope.search;
        }
        return Wines.get(params, parameters.callback);
      }
    };
    $scope.$watch('search', function(newValue, oldValue) {
      if (newValue !== oldValue) {
        $scope.messages = [];
        $scope.errors = [];
        return search.scheduleSearch(getWines, {
          input: newValue,
          first: 0,
          callback: function(value) {
            $scope.wines = value.list;
            $scope.size = value.count;
          }
        });
      }
    });
  }
]);

angular.module('mycellar.controllers.admin.tools').controller('AdminNextBookingEventController', [
  '$scope', '$location', 'AdminBookingEvents',
  function ($scope, $location, BookingEvents) {
    $scope.nextBookingEvent = function() {
      if ($scope.fix.bookingEvent != null) {
        BookingEvents.nextBookingEvent({id: $scope.fix.bookingEvent.id}).$promise.then(function(result) {
          $location.path('/admin/domain/booking/bookingEvent/' + result.id);
        });
      }
    };
  }
]);