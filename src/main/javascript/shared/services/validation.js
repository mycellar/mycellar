angular.module('mycellar.services.validation', [
]);

angular.module('mycellar.services.validation').factory('validityHelper', [
  function() {
    var validityHelper = {};
    validityHelper.sinceChanged = function(ctrl, key) {
      ctrl.$setValidity(key, false);
      ctrl.$validators[key] = function() {
        delete ctrl.$validators[key];
        return true;
      };
    };
    return validityHelper;
  }
]);
