angular.module('mycellar.services.search', [
]);

angular.module('mycellar.services.search').factory('search', [
  '$timeout',
  function($timeout) {
    // Declare the timeout promise var outside the function scope
    // so that stacked calls can be cancelled later 
    var timeoutPromise;

    var search = {};
    search.toggleHidden = function(scope, attr) {
      var hiddenElements = document.querySelectorAll('core-toolbar[main]>[hidden]');
      var notHiddenElements = document.querySelectorAll('core-toolbar[main]>:not([hidden])');
      angular.forEach(hiddenElements, function(element) {
        element.removeAttribute('hidden');
      });
      angular.forEach(notHiddenElements, function(element) {
        element.setAttribute('hidden', '');
      });
      var searchInput = document.querySelector('core-input#search');
      if (!searchInput.hasAttribute('hidden')) {
        searchInput.focus();
      }
    };
    search.clearSearch = function(scope, attr) {
      var searchInput = document.querySelector('core-input#search');
      searchInput.focus();
    };
    search.scheduleSearch = function(method, parameters) {
      if (timeoutPromise) {
        $timeout.cancel(timeoutPromise);
      }
      timeoutPromise = $timeout(function () {
        method(parameters);
      }, 300);
    };
    return search;
  }
]);
