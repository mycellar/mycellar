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

angular.module('mycellar.directives.bind').directive('bindPolymerSelect', [
  '$parse',
  function($parse) {
    return {
      restrict: 'A',
      link: function(scope, element, attrs) {
        scope.$watch(attrs.bindPolymerSelect, function(value) {
          element[0].selected = value;
        });
        element[0].addEventListener('core-select', function() {
          $parse(attrs.bindPolymerSelect).assign(scope, element[0].selected);
          scope.$apply();
        });
      }
    }
  }
]);
