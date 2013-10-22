angular.module('mycellar.directives.bootstrap.bindHtml', []);

angular.module('mycellar.directives.bootstrap.bindHtml').directive('bindHtmlUnsafe', [ 
  function () {
    return function (scope, element, attr) {
      element.addClass('ng-binding').data('$binding', attr.bindHtmlUnsafe);
      scope.$watch(attr.bindHtmlUnsafe, function bindHtmlUnsafeWatchAction(value) {
        element.html(value || '');
      });
    };
  }
]);