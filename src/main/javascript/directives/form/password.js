angular.module('mycellar.directives.form.password', []);

angular.module('mycellar.directives.form.password').directive('match', [
  function() {
    return {
      restrict: 'A',
      require: '?ngModel',
      link: function(scope, elem, attrs, ctrl) {
        if (!ctrl) return;

        ctrl.$validators.match = function(value) {
          var other = scope.$eval(attrs.match);
          var match = other.$viewValue === value;
          other.$setValidity('match', match);
          return match;
        };
      }
    }
  }
]);
