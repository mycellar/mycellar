'use strict';

angular.module('mycellar').directive({
  'errorCtrl': function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/errorCtrl.html',
      scope: {
        ctrl: '='
      }
    }
  },
  'errorForm': function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/errorForm.html',
      scope: {
        form: '='
      }
    }
  }
});