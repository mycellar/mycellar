var shared = require('./karma-shared.conf');
shared.browsers = ['PhantomJS'];
shared.preprocessors = {
  'src/main/javascript/**/*.js': 'coverage',
  'src/main/webapp/partials/**/*.tpl.html': 'ng-html2js'
};
shared.ngHtml2JsPreprocessor = {
  stripPrefix: 'src/main/webapp/'
};
shared.reporters = ['progress', 'coverage'];
shared.coverageReporter = {
  type: 'lcov',
  dir: 'coverage/'
};

module.exports = function(config) {
  config.set(shared);
};
