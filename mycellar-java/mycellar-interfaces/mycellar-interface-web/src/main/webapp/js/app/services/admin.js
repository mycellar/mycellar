'use strict';

function configurationServiceProvider() {
  this.$get = function($resource, $http) {
    return {
      resource: {
        list: $resource('/api/domain/admin/configurations'),
        item: $resource('/api/domain/admin/configuration/:configurationId')
      }
    };
  };
};

angular.module('mycellar')
  .provider('configurationService', configurationServiceProvider);
