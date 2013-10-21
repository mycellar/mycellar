angular.module('mycellar.loading', []).factory('loadingService', function() {
  var service = {
    requestCount: 0,
    isLoading: function() {
      return service.requestCount > 0;
    }
  };
  return service;
}).factory('onStartInterceptor', function(loadingService) {
  return function (data, headersGetter) {
    loadingService.requestCount++;
    return data;
  };
}).factory('delayedPromise', function($q, $timeout){
  return function(promise, delay) {
    var deferred = $q.defer();
    var delayedHandler = function() {
      $timeout(function() { deferred.resolve(promise); }, delay);
    };
    promise.then(delayedHandler, delayedHandler);
    return deferred.promise;
  };
}).factory('onCompleteInterceptor', function(loadingService, delayedPromise) {
  return function(promise) {
    var decrementRequestCount = function(response) {
      loadingService.requestCount--;
      return response;
    };
    // Normally we would just chain on to the promise but ...
    return promise.then(decrementRequestCount, decrementRequestCount);
    // ... we are delaying the response by 2 secs to allow the loading to be seen.
    // return delayedPromise(promise, 2000).then(decrementRequestCount, decrementRequestCount);
  };
}).config(function($httpProvider) {
  $httpProvider.responseInterceptors.push('onCompleteInterceptor');
}).run(function($http, onStartInterceptor) {
  $http.defaults.transformRequest.push(onStartInterceptor);
}).controller('LoadingCtrl', function($scope, loadingService) {
  $scope.$watch(function() { return loadingService.isLoading(); }, function(value) { $scope.loading = value; });
});