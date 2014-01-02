var shared = require('../protractor.conf');
shared.capabilities.browserName = ['chrome'];
shared.capabilities.platform = ['Windows 8.1'];

module.exports = function(config) {
  config.set(shared);
};
