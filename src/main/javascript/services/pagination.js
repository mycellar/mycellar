angular.module('mycellar.services.pagination', []);

angular.module('mycellar.services.pagination').factory('paginationService', [
  '$location', 
  function ($location) {
    var paginationService = {
      getSort: function() {
        return getStoredValue("S");
      },
      setSort: function(currentPage) {
        store("S", currentPage);
      },
      getFilters: function() {
        return getStoredValue("F");
      },
      setFilters: function(currentPage) {
        store("F", currentPage);
      },  
      getCurrentPage: function() {
        return getStoredValue("CP");
      },
      setCurrentPage: function(currentPage) {
        store("CP", currentPage);
      },
      getItemsPerPage: function() {
        return getStoredValue("IPP");
      },
      setItemsPerPage: function(itemsPerPage) {
        store("IPP", itemsPerPage);
      },
      getTotal: function() {
        return getStoredValue("T");
      },
      setTotal: function(currentPage) {
        store("T", currentPage);
      }
    };
    var data = {};
    var store = function(key, value) {
      key = key + $location.path();
      if (window.sessionStorage) {
        window.sessionStorage.setItem(key, value);
      } else {
        data[key] = value;
      }
    };
    var getStoredValue = function(key) {
      key = key + $location.path();
      if (window.sessionStorage) {
        return window.sessionStorage.getItem(key);
      } else {
        return data[key];
      }
    };
    return paginationService;
  }
]);
