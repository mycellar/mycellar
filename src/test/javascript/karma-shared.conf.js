module.exports = {
  files: [
    //3rd Party Code
    'bower_components/angular/angular.js',
    'bower_components/angular-resource/angular-resource.js',
    'bower_components/angular-cookies/angular-cookies.js',
    'bower_components/angular-route/angular-route.js',
    'bower_components/angular-animate/angular-animate.js',
    'bower_components/angular-i18n/angular-locale_fr-fr.js',
    'bower_components/angular-loading-bar/build/loading-bar.js',
    'bower_components/angular-http-auth/src/http-auth-interceptor.js',

    //App-specific Code
    'src/main/javascript/*.js',
    'src/main/javascript/**/*.js',

    //3rd Party testing code
    'bower_components/angular-mocks/angular-mocks.js',
    'node_modules/chai/chai.js',
    'node_modules/sinon/pkg/sinon.js',

    //Test-Specific Code
    'src/test/javascript/mocha.conf.js',
    'src/test/javascript/chai.conf.js',
    'src/test/javascript/unit/**/*.js',

    //Templates
    'src/main/webapp/partials/**/*.tpl.html'
  ],
  basePath: '../../../',
  frameworks: ['mocha'],
  browsers: [],
  autoWatch: false,
  singleRun: true,
  colors: true
};
