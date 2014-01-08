angular.module('mycellar').filter('wineRenderer', function() {
  return function(wine) {
    var colorLabel = function(color) {
      switch(color) {
      case 'RED':
        return ' - Rouge';
      case 'WHITE':
        return ' - Blanc';
      case 'ROSE':
        return ' - Rosé';
      }
      return '';
    };
    return wine.producer.name +
        ' - ' +wine.appellation.name +
        (wine.name != null && wine.name.length > 0 ? ' - ' + wine.name : '') +
        (wine.vintage != null ? ' - ' + wine.vintage : '') +
        colorLabel(wine.color);
  }
}).filter('formatRenderer', function(numberFilter) {
  return function(format) {
    return format.name + ' (' + numberFilter(format.capacity) + 'L)';
  }
}).filter('userRenderer', function() {
  return function(user) {
    return user.lastname + ' ' + user.firstname;
  }
}).filter('wineTypeRenderer', function() {
  return function(type) {
    switch(type) {
    case 'STILL':
      return 'Tranquille';
    case 'SPARKLING':
      return 'Pétillant';
    case 'SPIRIT':
      return 'Spiritueux';
    case 'OTHER':
      return 'Autre';
    }
    return color;
  }
}).filter('wineColorRenderer', function() {
  return function(color) {
    switch(color) {
    case 'RED':
      return 'Rouge';
    case 'WHITE':
      return 'Blanc';
    case 'ROSE':
      return 'Rosé';
    case 'OTHER':
      return 'Autre';
    }
    return color;
  }
}).filter('accessRightRenderer', function() {
  return function(accessRight) {
    switch(accessRight) {
    case 'READ':
      return 'Lecture seule';
    case 'MODIFY':
      return 'Modification';
    }
    return accessRight;
  }
});
