var shared = require('./karma-shared.conf');
shared.browsers = ['PhantomJS'];
shared.colors = false;

module.exports = function(config) {
  config.set(shared);
};
