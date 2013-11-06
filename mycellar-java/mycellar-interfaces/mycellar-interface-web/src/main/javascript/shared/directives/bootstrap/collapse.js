angular.module('mycellar.directives.bootstrap.collapse', [
  'mycellar.services.bootstrap.transition'
]);

angular.module('mycellar.directives.bootstrap.collapse').directive('collapse', [
  '$transition', 
  function($transition) {
    // CSS transitions don't work with height: auto, so we have to manually change the height to a
    // specific value and then once the animation completes, we can reset the height to auto.
    // Unfortunately if you do this while the CSS transitions are specified (i.e. in the CSS class
    // "collapse") then you trigger a change to height 0 in between.
    // The fix is to remove the "collapse" CSS class while changing the height back to auto - phew!
    var fixUpHeight = function(scope, element, height) {
      // We remove the collapse CSS class to prevent a transition when we change to height: auto
      var collapse = element.removeClass('collapse');
      element.css({ height: height });
      // It appears that  reading offsetWidth makes the browser realise that we have changed the
      // height already :-/
      var x = element[0].offsetWidth;
      if (collapse) {
        element.addClass('collapse');
      }
    };
  
    return {
      link: function(scope, element, attrs) {
  
        var isCollapsed;
        var initialAnimSkip = true;
        scope.$watch(function (){ return element[0].scrollHeight; }, function (value) {
          //The listener is called when scollHeight changes
          //It actually does on 2 scenarios: 
          // 1. Parent is set to display none
          // 2. angular bindings inside are resolved
          //When we have a change of scrollHeight we are setting again the correct height if the group is opened
          if (element[0].scrollHeight !== 0) {
            if (!isCollapsed) {
              if (initialAnimSkip) {
                fixUpHeight(scope, element, element[0].scrollHeight + 'px');
              } else {
                fixUpHeight(scope, element, 'auto');
              }
            }
          }
        });
        
        scope.$watch(attrs.collapse, function(value) {
          if (value) {
            collapse();
          } else {
            expand();
          }
        });

        // Some jQuery-like functionality, based on implementation in Prototype.
        //
        // There is a problem with these: We're instantiating them for every
        // instance of the directive, and that's not very good.
        //
        // But we do need a more robust way to calculate dimensions of an item,
        // scrollWidth/scrollHeight is not super reliable, and we can't rely on
        // jQuery or Prototype or any other framework being used.
        var helpers = {
          style: function(element, prop) {
            var elem = element;
            if(typeof elem.length === 'number') {
              elem = elem[0];
            }
            function camelcase(name) {
              return name.replace(/-+(.)?/g, function(match, chr) {
                return chr ? chr.toUpperCase() : '';
              });
            }
            prop = prop === 'float' ? 'cssFloat' : camelcase(prop);
            var value = elem.style[prop];
            if (!value || value === 'auto') {
              var css = window.getComputedStyle(elem, null);
              value = css ? css[prop] : null;
            }
            if (prop === 'opacity') {
              return value ? parseFloat(value) : 1.0;
            }
            return value === 'auto' ? null : value;
          },

          size: function(element) {
            var dom = element[0];
            var display = helpers.style(element, 'display');

            if (display && display !== 'none') {
              // Fast case: rely on offset dimensions
              return { width: dom.offsetWidth, height: dom.offsetHeight };
            }

            // Slow case -- Save original CSS properties, update the CSS, and then
            // use offset dimensions, and restore the original CSS
            var currentStyle = dom.style;
            var originalStyles = {
              visibility: currentStyle.visibility,
              position:   currentStyle.position,
              display:    currentStyle.display
            };

            var newStyles = {
              visibility: 'hidden',
              display:    'block'
            };

            // Switching `fixed` to `absolute` causes issues in Safari.
            if (originalStyles.position !== 'fixed') {
              newStyles.position = 'absolute';
            }

            // Quickly swap-in styles which would allow us to utilize offset dimensions
            element.css(newStyles);

            var dimensions = {
              width:  dom.offsetWidth,
              height: dom.offsetHeight
            };

            // And restore the original styles
            element.css(originalStyles);

            return dimensions;
          },

          width: function(element, value) {
            if(typeof value === 'number' || typeof value === 'string') {
              if(typeof value === 'number') {
                value = value + 'px';
              }
              element.css({ 'width': value });
              return;
            }
            return helpers.size(element).width;
          },

          height: function(element, value) {
            if(typeof value === 'number' || typeof value === 'string') {
              if(typeof value === 'number') {
                value = value + 'px';
              }
              element.css({ 'height': value });
              return;
            }
            return helpers.size(element).height;
          },

          dimension: function() {
            var hasWidth = element.hasClass('width');
            return hasWidth ? 'width' : 'height';
          }
        };

        var events = {
          beforeShow: function(dimension, dimensions) {
            element
              .removeClass('collapse')
              .removeClass('collapsed')
              .addClass('collapsing');
            helpers[dimension](element, 0);
          },

          beforeHide: function(dimension, dimensions) {
            // Read offsetHeight and reset height:
            helpers[dimension](element, dimensions[dimension] + "px");
            var unused = element[0].offsetWidth,
                unused2 = element[0].offsetHeight;
            element
              .addClass('collapsing')
              .removeClass('collapse')
              .removeClass('in');
          },

          afterShow: function(dimension) {
            element
              .removeClass('collapsing')
              .addClass('in');
            helpers[dimension](element, 'auto');
            isCollapsed = false;
          },

          afterHide: function(dimension) {
            element
              .removeClass('collapsing')
              .addClass('collapsed')
              .addClass('collapse');
            isCollapsed = true;
          }
        };

        var currentTransition;
        var doTransition = function(showing, pixels) {
          if (currentTransition || showing === element.hasClass('in')) {
            return;
          }
          var dimension = helpers.dimension();
          var dimensions = helpers.size(element);
          var name = showing ? 'Show' : 'Hide';

          events['before' + name](dimension, dimensions);

          var query = {};
          var makeUpper = function(name) {
            return name.charAt(0).toUpperCase() + name.slice(1);
          };
          if(pixels==='scroll') {
            pixels = element[0][pixels + makeUpper(dimension)];
          }
          if(typeof pixels === 'number') {
            pixels = pixels + "px";
          }
          query[dimension] = pixels;
          currentTransition = $transition(element,query).emulateTransitionEnd(350);
          currentTransition.then(
            function() {
              events['after' + name](dimension);
              currentTransition = undefined;
            },
            function(reason) {
              var descr = showing ? 'expansion' : 'collapse';
              currentTransition = undefined;
            }
          );
          return currentTransition;
        };

        var expand = function() {
          if (initialAnimSkip || !$transition.transitionEndEventName) {
            initialAnimSkip = false;
            var dimension = helpers.dimension();
            helpers[dimension](element, 'auto');
            element
              .removeClass('collapse')
              .removeClass('collapsed');
            events.afterShow(dimension);
          } else {
            doTransition(true, 'scroll');
          }
        };

        var collapse = function() {
          if (initialAnimSkip || !$transition.transitionEndEventName) {
            initialAnimSkip = false;
            var dimension = helpers.dimension();
            helpers[dimension](element, 0);
            element.removeClass('in');
            events.afterHide(dimension);
          } else {
            doTransition(false, '0');
          }
        };
      }
    };
  }
]);
