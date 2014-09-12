angular.module('mycellar.directives.bind', []);

angular.module('mycellar.directives.bind').directive('bindPolymerInput', [
  '$parse',
  function($parse) {
    return {
      restrict: 'A',
      link: function(scope, element, attrs) {
        scope.$watch(attrs.bindPolymerInput, function(value) {
          element[0].value = value;
        });
        element[0].addEventListener('input', function() {
          $parse(attrs.bindPolymerInput).assign(scope, element[0].inputValue);
          scope.$apply();
        });
      }
    }
  }
]);
