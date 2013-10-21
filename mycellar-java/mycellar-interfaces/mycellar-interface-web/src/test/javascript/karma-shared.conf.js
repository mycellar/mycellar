var shared = function(config) {
  config.set({
    basePath: '../../../',
    frameworks: ['mocha'],
    reporters: ['progress'],
    browsers: ['Chrome','Firefox'],
    autoWatch: true,

    singleRun: false,
    colors: true
  });
};

shared.files = [
  //3rd Party Code
  'bower_components/angular/angular.js',
  'bower_components/angular-resource/angular-resource.js',
  'bower_components/angular-route/angular-route.js',

  //App-specific Code
  'src/main/javascript/**/*.js',

  //Test-Specific Code
  'node_modules/chai/chai.js',
  'src/test/javascript/mocha.conf.js',
  'src/test/javascript/chai.conf.js'
];

module.exports = shared;
