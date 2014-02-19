angular.module('mycellar.resources.admin.loggers', ['ngResource']);

angular.module('mycellar.resources.admin.loggers').factory('Loggers', [
  '$resource', 
  function ($resource) {
    var Loggers = $resource('/api/admin/loggers/loggers', {}, {
      changeLevel: {url: '/api/admin/loggers/changeLevel', method: 'POST'}
    });

    return Loggers;
  }
]);
