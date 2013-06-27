'use strict';

angular.module('mycellar').directive({
  'mycellarInput': function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/input.html',
      scope: {
        form: '=',
        model: '=',
        label: '@',
        inputId: '@id',
        inputName: '@name',
        inputClass: '@class'
      },
      link: function(scope, iElement, iAttrs, controller) {
        iAttrs.$observe('propertyName', function(value) {
          scope.propertyName = value || scope.inputName;
        });
        iAttrs.$observe('type', function(value) {
          scope.inputType = value || 'text';
        });
        iAttrs.$observe('form', function(value) {
          scope.input = value[scope.inputId];
        });
        iAttrs.$observe('inputId', function(value) {
          scope.input = scope.form[value];
        });
      }
    }
  },
  'mycellarTextarea': function() {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/directives/form/textarea.html',
      scope: {
        form: '=',
        model: '=',
        label: '@',
        textareaId: '@id',
        textareaName: '@name',
        textareaClass: '@class',
        rows: '@rows',
      },
      link: function(scope, iElement, iAttrs, controller) {
        iAttrs.$observe('propertyName', function(value) {
          scope.propertyName = value || scope.textareaName;
        });
      },
      controller: function($scope) {
        $scope.textarea = $scope.form[$scope.textareaId];
      }
    }
  }
});
