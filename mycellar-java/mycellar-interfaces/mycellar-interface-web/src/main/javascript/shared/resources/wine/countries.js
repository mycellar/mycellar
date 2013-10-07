angular.module('resources.wine.countries', ['ngResource']);

angular.module('resources.wine.countries').factory('Countries', ['$resource', '$q', function ($resource, $q) {

  var Countries = $resource('/api/domain/wine/countries');
  var Country = $resource('/api/domain/wine/country/:countryId');
  
  Country.deleteById = Country.delete;
  Country.delete = function(fn) {
    return Country.deleteById({countryId: this.id}, fn);
  };
  Countries.deleteById = function(id, fn) {
    return Country.deleteById({countryId: id}, fn);
  };
  
  Countries.count = function () {
    var deferred = $q.defer();
    Countries.get({count: 0}, function(result) {
      $q.when(result.count).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };
  
  Countries.getById = function(id) {
    return Country.get({countryId: id});
  };
  
  Countries.new = function() {
    return new Country();
  };
  
  Countries.nameLike = function(name) {
    var deferred = $q.defer();
    Countries.get({first: 0, count: 15, filters: 'name,'+name, sort: 'name,asc'}, function(result) {
      $q.when(result.list).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };

  return Countries;
}]);
