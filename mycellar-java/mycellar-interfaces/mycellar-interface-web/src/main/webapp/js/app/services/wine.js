'use strict';

function countryServiceProvider() {
  this.$get = function($resource, $http) {
    return {
      getAllLike: function (countryName) {
        return $http.get("/api/domain/wine/countries?count=15&filters=name,"+countryName+"&first=0&sort=name,asc").then(function(response){
          return response.data.list;
        });
      },
      resource: {
        list: $resource('/api/domain/wine/countries'),
        item: $resource('/api/domain/wine/country/:countryId')
      }
    };
  };
};

function regionServiceProvider() {
  this.$get = function($resource, $http) {
    return {
      getAllLike: function (regionName) {
        return $http.get("/api/domain/wine/regions?count=15&filters=name,"+regionName+"&first=0&sort=name,asc").then(function(response){
          return response.data.list;
        });
      },
      resource: {
        list: $resource('/api/domain/wine/regions'),
        item: $resource('/api/domain/wine/region/:regionId')
      }
    };
  };
};

function appellationServiceProvider() {
  this.$get = function($resource, $http) {
    return {
      getAllLike: function (appellationName) {
        return $http.get("/api/domain/wine/appellations?count=15&filters=name,"+appellationName+"&first=0&sort=name,asc").then(function(response){
          return response.data.list;
        });
      },
      resource: {
        list: $resource('/api/domain/wine/appellations'),
        item: $resource('/api/domain/wine/appellation/:appellationId')
      }
    };
  };
};

function producerServiceProvider() {
  this.$get = function($resource, $http) {
    return {
      getAllLike: function (producerName) {
        return $http.get("/api/domain/wine/producers?count=15&filters=name,"+producerName+"&first=0&sort=name,asc").then(function(response){
          return response.data.list;
        });
      },
      resource: {
        list: $resource('/api/domain/wine/producers'),
        item: $resource('/api/domain/wine/producer/:producerId')
      }
    };
  };
};

function wineServiceProvider() {
  this.$get = function($resource, $http) {
    return {
      resource: {
        list: $resource('/api/domain/wine/wines'),
        item: $resource('/api/domain/wine/wine/:wineId')
      }
    };
  };
};

angular.module('mycellar')
  .provider('countryService', countryServiceProvider)
  .provider('regionService', regionServiceProvider)
  .provider('appellationService', appellationServiceProvider)
  .provider('producerService', producerServiceProvider)
  .provider('wineService', wineServiceProvider);