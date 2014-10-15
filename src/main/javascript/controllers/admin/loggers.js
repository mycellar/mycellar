angular.module('mycellar.controllers.admin.loggers', [
  'ngRoute',
  'mycellar.resources.admin.loggers'
], [
  '$routeProvider', 
  function($routeProvider){
    $routeProvider.when('/admin/loggers', {
      templateUrl: 'partials/views/admin/loggers.tpl.html',
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
  '$scope', 'loggers', 'Loggers', 'search',
  function ($scope, loggers, Loggers, search) {
    $scope.filters = {
      name: ''
    };
    $scope.loggers = loggers;
    $scope.changeLevel = function(logger, level) {
      Loggers.changeLevel({name: logger.name, level: level}, function() {
        $scope.loggers = Loggers.query();
      });
    };
    $scope.toggleHidden = function() {
      search.toggleHidden();
      $scope.filters.name = '';
    };
    $scope.clearSearch = function() {
      search.clearSearch();
      $scope.filters.name = '';
    };
  }
]);
