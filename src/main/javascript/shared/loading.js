angular.module('mycellar.loading', []);

angular.module('mycellar.loading').factory('loadingService', function() {
  var service = {
    requestCount: 0,
    isLoading: function() {
      return service.requestCount > 0;
    }
  };
  return service;
});

angular.module('mycellar.loading').config([
  '$httpProvider',
  function($httpProvider) {
    $httpProvider.interceptors.push([
      '$q', 'loadingService',
      function($q, loadingService) {
        return {
          request: function(config) {
            loadingService.requestCount++;
            return config || $q.when(config);
          },
          response: function(response) {
            loadingService.requestCount--;
            return $q.when(response);
          },
          responseError: function(rejection) {
            loadingService.requestCount--;
            return $q.reject(rejection);
          }
        };
      }
    ]);
  }
]);

angular.module('mycellar.loading').controller('LoadingCtrl', [
  '$scope', 'loadingService',
    function($scope, loadingService) {
    $scope.$watch(function() {
      return loadingService.isLoading();
    }, function(value) { 
      $scope.loading = value;
    });
  }
]);
