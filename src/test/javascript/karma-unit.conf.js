var shared = require('./karma-shared.conf');
shared.browsers = ['PhantomJS'];

module.exports = function(config) {
  config.set(shared);
};
