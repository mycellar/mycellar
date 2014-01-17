angular.module('mycellar.directives.error.business', []);

angular.module('mycellar.directives.error.business').directive('business', [function() {
  return {
    require: '?ngModel',
    link: function(scope, elm, attr, ctrl) {
      if (!ctrl) return;
      attr.business = true;

      var validator = function(value) {
        if (attr.business) {
          for (name in ctrl.$error) {
            if (name.search('BusinessError_') == 0) {
              ctrl.$setValidity(name, true);
            }
          }
        }
      };

      ctrl.$viewChangeListeners.push(validator);
    }
  };
}]);
