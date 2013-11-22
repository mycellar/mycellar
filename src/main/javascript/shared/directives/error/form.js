angular.module('mycellar.directives.error.form', []);

angular.module('mycellar.directives.error.form').directive('errorForm', [function() {
  return {
    restrict: 'E',
    replace: true,
    templateUrl: 'partials/directives/error/form.tpl.html',
    scope: {
      form: '='
    }
  }
}]);