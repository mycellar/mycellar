angular.module('mycellar.resources.admin.configurations', ['ngResource']);

angular.module('mycellar.resources.admin.configurations').factory('Configurations', ['$resource', '$q', function ($resource, $q) {

  var Configurations = $resource('/api/domain/admin/configurations');
  var Configuration = $resource('/api/domain/admin/configuration/:configurationId');

  Configurations.count = function () {
    var deferred = $q.defer();
    Configurations.get({count: 0}, function(result) {
      $q.when(result.count).then(function(value) {
        deferred.resolve(value);
      }, function(value) {
        deferred.reject(value);
      });
    });
    return deferred.promise;
  };
  
  Configurations.getById = function(id) {
    return Configuration.get({configurationId: id});
  };

  return Configurations;
}]);
