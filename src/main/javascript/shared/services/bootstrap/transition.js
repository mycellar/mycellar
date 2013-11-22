angular.module('mycellar.services.bootstrap.transition', []);

angular.module('mycellar.services.bootstrap.transition').factory('$transition', [
  '$q', '$timeout', '$rootScope', 
  function($q, $timeout, $rootScope) {
    var $transition = function(element, trigger, options) {
      options = options || {};
      var deferred = $q.defer();
      var endEventName = $transition[options.animation ? "animationEndEventName" : "transitionEndEventName"];
  
      var transitionEndHandler = function(event) {
        $rootScope.$apply(function() {
          element.unbind(endEventName, transitionEndHandler);
          deferred.resolve(element);
        });
      };
  
      if (endEventName) {
        element.bind(endEventName, transitionEndHandler);
      }
  
      // Wrap in a timeout to allow the browser time to update the DOM before the transition is to occur
      $timeout(function() {
        if ( angular.isString(trigger) ) {
          element.addClass(trigger);
        } else if ( angular.isFunction(trigger) ) {
          trigger(element);
        } else if ( angular.isObject(trigger) ) {
          element.css(trigger);
        }
        //If browser does not support transitions, instantly resolve
        if ( !endEventName ) {
          deferred.resolve(element);
        }
      });
  
      // Add our custom cancel function to the promise that is returned
      // We can call this if we are about to run a new transition, which we know will prevent this transition from ending,
      // i.e. it will therefore never raise a transitionEnd event for that transition
      deferred.promise.cancel = function() {
        if ( endEventName ) {
          element.unbind(endEventName, transitionEndHandler);
        }
        deferred.reject('Transition cancelled');
      };

      // Emulate transitionend event, useful when support is assumed to be
      // available, but may not actually be used due to a transition property
      // not being used in CSS (for example, in versions of firefox prior to 16,
      // only -moz-transition is supported -- and is not used in Bootstrap3's CSS
      // -- As such, no transitionend event would be fired due to no transition
      // ever taking place. This method allows a fallback for such browsers.)
      deferred.promise.emulateTransitionEnd = function(duration) {
        var called = false;
        deferred.promise.then(
          function() { called = true; },
          function() { called = true; }
        );

        var callback = function() {
          if ( !called ) {
            // If we got here, we probably aren't going to get a real 
            // transitionend event. Emit a dummy to the handler.
            element.triggerHandler(endEventName);
          }        
        };

        $timeout(callback, duration);
        return deferred.promise;
      };

      return deferred.promise;
    };

    // Work out the name of the transitionEnd event
    var transElement = document.createElement('trans');
    var transitionEndEventNames = {
      'WebkitTransition': 'webkitTransitionEnd',
      'MozTransition': 'transitionend',
      'OTransition': 'oTransitionEnd',
      'transition': 'transitionend'
    };
    var animationEndEventNames = {
      'WebkitTransition': 'webkitAnimationEnd',
      'MozTransition': 'animationend',
      'OTransition': 'oAnimationEnd',
      'transition': 'animationend'
    };
    function findEndEventName(endEventNames) {
      for (var name in endEventNames){
        if (transElement.style[name] !== undefined) {
          return endEventNames[name];
        }
      }
    }
    $transition.transitionEndEventName = findEndEventName(transitionEndEventNames);
    $transition.animationEndEventName = findEndEventName(animationEndEventNames);
    return $transition;
  }
]);
