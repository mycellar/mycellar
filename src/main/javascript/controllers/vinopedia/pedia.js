angular.module('mycellar.controllers.vinopedia.pedia', [
  'ngRoute',
  'mycellar.resources.wine.wines'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/vinopedia', {
      templateUrl: 'partials/views/vinopedia/pedia.tpl.html',
      controller: 'VinopediaController',
      resolve: {
        wines: ['Wines', function(Wines) {
          return Wines.get({
            first: 0,
            count: 50,
            sort: [
              'producer.name,asc',
              'name,asc',
              'vintage,asc'
            ]
          }).$promise;
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.vinopedia.pedia').controller('VinopediaController', [
  '$scope', 'Wines', 'wines', 'search',
  function ($scope, Wines, wines, search) {
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

    $scope.pageCount = 50;
    $scope.search = '';
    if (wines != null) {
      $scope.wines = wines.list;
      $scope.size = wines.count;
    }

    $scope.more = function() {
      getWines({
        input: $scope.search,
        cellar: $scope.cellar,
        first: $scope.wines.length,
        callback: function(value) {
          $scope.wines = $scope.wines.concat(value.list);
        }
      });
    };

    $scope.toggleHidden = function() {
      search.toggleHidden();
      $scope.search = '';
    };
    $scope.clearSearch = function() {
      search.clearSearch();
      $scope.search = '';
    };
    $scope.$watch('search', function(newValue, oldValue) {
      if (newValue !== oldValue) {
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
