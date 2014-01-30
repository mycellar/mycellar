var shared = require('./karma-shared.conf');
shared.browsers = ['PhantomJS'];
shared.preprocessors = {
  'src/main/webapp/partials/**/*.tpl.html': 'ng-html2js'
};
shared.ngHtml2JsPreprocessor = {
  stripPrefix: 'src/main/webapp/'
};
shared.reporters = ['progress'];

module.exports = function(config) {
  config.set(shared);
};
