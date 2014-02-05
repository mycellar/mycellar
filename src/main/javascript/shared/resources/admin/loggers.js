angular.module('mycellar.resources.admin.loggers', ['ngResource']);

angular.module('mycellar.resources.admin.loggers').factory('Loggers', [
  '$resource', 
  function ($resource) {
    var Configurations = $resource('/api/admin/loggers/loggers', {}, {
      changeLevel: {url: '/api/admin/loggers/changeLevel', method: 'POST'}
    });

    return Configurations;
  }
]);
