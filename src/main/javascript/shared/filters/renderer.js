angular.module('mycellar').filter('wineRenderer', function() {
  return function(wine) {
    return wine.producer.name +
        (wine.name != null ? " - " + wine.name : '') +
        (wine.vintage != null ? " - " + wine.vintage : '') +
        ' - ' + wine.type +
        ' - ' + wine.color;
  }
}).filter('formatRenderer', function() {
  return function(format) {
    return format.name + ' (' + format.capacity + 'L)';
  }
}).filter('customerRenderer', function() {
  return function(customer) {
    return customer.lastname + ' ' + customer.firstname;
  }
});