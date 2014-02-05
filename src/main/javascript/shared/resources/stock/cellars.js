angular.module('mycellar.resources.stock.cellars', [
  'ngResource',
  'mycellar.services.admin.resource'
]);

angular.module('mycellar.resources.stock.cellars').factory('Cellars', [
  '$resource', '$q',
  function($resource, $q) {
    var Cellars = $resource('/api/stock/cellars/:id', {id: '@id'}, {
      getAllForCurrentUser: {
        url: '/api/stock/cellars',
        method: 'GET'
      },
      validate: {
        url: '/api/stock/validateCellar',
        method: 'POST'
      }
    });

    Cellars.count = function() {
      var deferred = $q.defer();
      Cellars.get({count: 0}, function(result) {
        $q.when(result.count).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    Cellars.nameLike = function(name) {
      var deferred = $q.defer();
      Cellars.getAllForCurrentUser({first: 0, count: 15, filters: 'name,'+name, sort: 'name,asc'}, function(result) {
        $q.when(result.list).then(function(value) {
          deferred.resolve(value);
        }, function(value) {
          deferred.reject(value);
        });
      });
      return deferred.promise;
    };

    return Cellars;
  }
]);

angular.module('mycellar.resources.stock.cellars').factory('AdminCellars', [
  'adminDomainResource',
  function (adminDomainResource) {
    return adminDomainResource.createResource({
      url: '/api/admin/domain/stock/cellars',
      validateUrl: '/api/admin/domain/stock/validateCellar',
      likeUrl: '/api/admin/domain/stock/cellars/like'
    });
  }
]);
