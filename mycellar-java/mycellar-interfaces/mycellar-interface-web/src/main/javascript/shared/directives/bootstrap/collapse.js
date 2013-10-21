angular.module('mycellar.directives.bootstrap.collapse', [
  'mycellar.services.bootstrap.transition'
]);

angular.module('mycellar.directives.bootstrap.collapse').directive('collapse', [
  '$transition',
  function($transition) {
    var fixUpHeight = function (scope, element, height) {
      element.css({height: height});
      var x = element[0].offsetHeight;
    };
    
    return {
      link: function (scope, element, attrs) {
        var initialAnimSkip = true;
        scope.$watch(attrs.collapse, function (value) {
          if (value) {
            collapse();
          } else {
            expand();
          }
        });
        
        var currentTransition;
        var doTransition = function (change) {
          if (currentTransition) {
            currentTransition.cancel();
          }
          currentTransition = $transition(element, change);
          currentTransition.then(
            function() { currentTransition = undefined; },
            function() { currentTransition = undefined; }
          );
          return currentTransition;
        }
        
        var expand = function () {
          element.removeClass('collapse');
          element.removeClass('in');
          if (initialAnimSkip) {
            initialAnimSkip = false;
            endExpand();
          } else {
            element.addClass('collapsing');
            doTransition({height: element[0].scrollHeight + 'px'}).then(function () {
              endExpand();
            });
          }
        };
        var endExpand = function () {
          element.removeClass('collapsing');
          element.addClass('in');
          fixUpHeight(scope, element, 'auto');
        };
        
        var collapse = function () {
          element.removeClass('in');
          element.removeClass('collapse');
          if (initialAnimSkip) {
            initialAnimSkip = false;
            endCollapse();
          } else {
            fixUpHeight(scope, element, element[0].scrollHeight + 'px');
            element.addClass('collapsing');
            doTransition({height: 0}).then(function () {
              endCollapse();
            });
          }
          
        };
        var endCollapse = function () {
          element.removeClass('collapsing');
          element.addClass('collapse');
          fixUpHeight(scope, element, 0);
        };
      }
    };
  }
]);