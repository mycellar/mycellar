angular.module('mycellar.directives.form.password', []);

angular.module('mycellar.directives.form.password').directive('match', [
  function() {
    return {
      restrict: 'A',
      require: '?ngModel',
      link: function(scope, elem, attrs, ctrl) {
        if (!ctrl) return;

        var match = '';
        attrs.$observe('match', function(value) {
          match = value;
          ctrl.$validate();
        });
        ctrl.$validators.match = function(value) {
          return match === value;
        };
      }
    }
  }
]);
