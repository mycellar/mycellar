angular.module('mycellar.directives.error.span', [
  'ngMessages'
]);

angular.module('mycellar.directives.error.span').directive('errorSpan', [function() {
  return {
    restrict: 'E',
    replace: true,
    templateUrl: 'partials/directives/error/span.tpl.html',
    scope: {
      ctrl: '='
    }
  }
}]);
