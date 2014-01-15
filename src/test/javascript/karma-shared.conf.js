module.exports = {
  files: [
    //3rd Party Code
    'bower_components/angular/angular.js',
    'bower_components/angular-resource/angular-resource.js',
    'bower_components/angular-route/angular-route.js',
    'bower_components/angular-i18n/angular-locale_fr-fr.js',
    'bower_components/angular-ui-bootstrap-bower/ui-bootstrap-tpls.js',

    //App-specific Code
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
  reporters: ['progress'],
  browsers: [],
  preprocessors: {
    'src/main/webapp/partials/**/*.tpl.html': 'ng-html2js'
  },
  ngHtml2JsPreprocessor: {
    // strip this from the file path
    stripPrefix: 'src/main/webapp/'
  },
  autoWatch: false,
  singleRun: true,
  colors: true
};
