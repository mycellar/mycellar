module.exports = function(config) {
  config.set({
    files: [
      //3rd Party Code
      'bower_components/angular/angular.js',
      'bower_components/angular-resource/angular-resource.js',
      'bower_components/angular-route/angular-route.js',
    
      //App-specific Code
      'src/main/javascript/**/*.js',
    
      //3rd Party testing code
      'bower_components/angular-mocks/angular-mocks.js',
      'node_modules/chai/chai.js',
      
      //Test-Specific Code
      'src/test/javascript/mocha.conf.js',
      'src/test/javascript/chai.conf.js',
      'src/test/javascript/unit/**/*.js'
    ],
    basePath: '../../../',
    frameworks: ['mocha'],
    reporters: ['progress'],
    browsers: ['Chrome','Firefox'],
    autoWatch: false,
    singleRun: true,
    colors: true
  });
};
