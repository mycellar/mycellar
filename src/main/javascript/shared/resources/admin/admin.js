angular.module('mycellar.resources.admin.administration', []);

angular.module('mycellar.resources.admin.administration').factory('Admin', [
  '$http', '$q', 
  function ($http, $q) {
    var Admin = {
      database: function() {
        var deferred = $q.defer();
        $http.get('/api/admin/administration/database').success(function(data, status, headers, config) {
          deferred.resolve(data);
        }).error(function(data, status, headers, config) {
          deferred.reject(data);
        });
        return deferred.promise;
      }
    };

    return Admin;
  }
]);
