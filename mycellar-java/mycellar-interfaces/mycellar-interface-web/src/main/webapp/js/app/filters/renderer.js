'use strict';

angular.module('mycellar').filter('wineRenderer', function() {
  return function(wine) {
    return wine.producer.name + " - " +
        wine.name + " - " +
        wine.vintage;
  }
});