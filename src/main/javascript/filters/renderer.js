angular.module('mycellar').filter('wineRenderer', ['wineColorRendererFilter', function(wineColorRenderer) {
  return function(wine) {
    return wine.producer.name +
        ' - ' + wine.appellation.name +
        (wine.name != null && wine.name.length > 0 ? ' - ' + wine.name : '') +
        (wine.vintage != null ? ' - ' + wine.vintage : '') +
        (wine.color != null ? ' - ' + wineColorRenderer(wine.color) : '');
  };
}]).filter('countryRenderer', function() {
  return function(country) {
    return country.name;
  };
}).filter('regionRenderer', ['countryRendererFilter', function(countryRenderer) {
  return function(region) {
    return region.name +
        (region.country != null ? ' - ' + countryRenderer(region.country) : '');
  };
}]).filter('appellationRenderer', ['appellationSubRendererFilter', function(appellationSubRenderer) {
  return function(appellation) {
    return appellation.name + ' - ' + appellationSubRenderer(appellation);
  };
}]).filter('appellationSubRenderer', ['countryRendererFilter', 'regionRendererFilter', function(countryRenderer, regionRenderer) {
  return function(appellation) {
    return (appellation.region != null ? regionRenderer(appellation.region) : '') +
        (appellation.country != null ? countryRenderer(appellation.country) : '');
  };
}]).filter('bookingEventRenderer', function(dateFilter) {
  return function(bookingEvent) {
    return bookingEvent.name + ' (du ' + dateFilter(bookingEvent.start, 'longDate') +
        ' au ' + dateFilter(bookingEvent.end, 'longDate') + ')';
  };
}).filter('formatRenderer', function(numberFilter) {
  return function(format) {
    return format.name + ' (' + numberFilter(format.capacity) + 'L)';
  }
}).filter('producerRenderer', function() {
  return function(producer) {
    return producer.name;
  }
}).filter('userRenderer', function() {
  return function(user, format) {
    if (format === 'long') {
      return user.lastname + ' ' + user.firstname + ' (' + user.email + ')';
    }
    return user.lastname + ' ' + user.firstname;;    
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
  };
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
  };
}).filter('accessRightRenderer', function() {
  return function(accessRight) {
    switch(accessRight) {
    case 'READ':
      return 'Lecture seule';
    case 'MODIFY':
      return 'Modification';
    }
    return accessRight;
  };
}).filter('movementTypeRenderer', function() {
  return function(type) {
    switch(type) {
    case 'I':
      return 'Entrée';
    case 'O':
      return 'Sortie';
    }
    return type;
  };
});
