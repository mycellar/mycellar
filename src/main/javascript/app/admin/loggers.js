angular.module('mycellar.controllers.admin.loggers', [
  'ngRoute',
  'mycellar.resources.admin.loggers',
  'mycellar.services.table'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/admin/loggers', {
      templateUrl: 'partials/admin/loggers.tpl.html',
      controller: 'AdminLoggersController',
      resolve: {
        loggers: ['Loggers', function(Loggers){
          return Loggers.query();
        }]
      }
    });
  }
]);

angular.module('mycellar.controllers.admin.loggers').controller('AdminLoggersController', [
  '$scope', 'loggers', 'Loggers', 'tableService',
  function ($scope, loggers, Loggers, tableService) {
    $scope.tableContext = tableService.createTableContext();
    $scope.loggers = loggers;
    $scope.changeLevel = function(logger, level) {
      Loggers.changeLevel({name: logger.name, level: level}, function() {
        $scope.loggers = Loggers.query();
      });
    };
  }
]);
