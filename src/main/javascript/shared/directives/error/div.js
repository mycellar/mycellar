angular.module('mycellar.directives.error.div', []);

angular.module('mycellar.directives.error.div').directive('errorDiv', [function() {
  return {
    restrict: 'E',
    replace: true,
    templateUrl: 'partials/directives/error/div.tpl.html',
    scope: {
      errors: '='
    }
  }
}]);
