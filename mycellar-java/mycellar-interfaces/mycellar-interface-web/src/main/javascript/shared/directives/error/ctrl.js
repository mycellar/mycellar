angular.module('directives.error.ctrl', []);

angular.module('directives.error.ctrl').directive('errorCtrl', [function() {
  return {
    restrict: 'E',
    replace: true,
    templateUrl: 'partials/directives/error/ctrl.tpl.html',
    scope: {
      ctrl: '='
    }
  }
}]);
