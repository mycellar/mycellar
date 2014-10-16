angular.module('mycellar.controllers.cellar.shares', [
  'ngRoute',
  'mycellar.resources.stock.cellars',
  'mycellar.resources.stock.cellarShares'
], [
  '$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/cellar/shares', {
      templateUrl: 'partials/views/cellar/shares.tpl.html',
      controller: 'SharesController',
      resolve: {
        cellarShares: ['CellarShares', function(CellarShares){
          return CellarShares.getAllForCurrentUser({
            first: 0,
            count: 10
          }).$promise;
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.cellar.shares').controller('SharesController', [
  '$scope', 'cellarShares', 'CellarShares', '$route',
  function($scope, cellarShares, CellarShares, $route) {
    $scope.pageCount = 10;
    $scope.cellarShares = cellarShares.list;
    $scope.size = cellarShares.count;
    $scope.edit = function(cellarShare) {
      CellarShares.get({id: cellarShare.id}, function(cellarShare) {
        $scope.cellarShare = cellarShare;
      });
    };
    $scope.new = function() {
      $scope.cellarShare = new CellarShares();
    };
    $scope.cancel = function() {
      $scope.cellarShare = null;
    };
    $scope.save = function() {
      $scope.backup = {};
      angular.copy($scope.cellarShare, $scope.backup);
      $scope.cellarShare.$save(function (value, headers) {
        if (value.errorKey != undefined) {
          errors.push({errorKey: value.errorKey});
          angular.copy($scope.backup, $scope.cellarShare);
        } else if (value.internalError != undefined) {
          errors.push({errorKey: value.internalError});
          angular.copy($scope.backup, $scope.cellarShare);
        } else {
          $route.reload();
        }
      });
    };
    $scope.delete = function(cellarShare, event) {
      CellarShares.delete({id: cellarShare.id}, function() {
        $route.reload();
      });
      event.stopPropagation();
    };
    $scope.more = function() {
      return getCellarShares({
        first: $scope.cellarShares.length,
        callback: function(value) {
          $scope.cellarShares = $scope.cellarShares.concat(value.list);
        }
      });
    };
    var getCellarShares = function(parameters) {
      var params = {
        first: parameters.first, 
        count: $scope.pageCount, 
        sort: [
          'cellar.name,asc'
        ]
      };
      return CellarShares.getAllForCurrentUser(params, parameters.callback);
    }
  }
]);
