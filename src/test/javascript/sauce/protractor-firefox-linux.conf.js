var shared = require('../protractor.conf');
shared.capabilities.browserName = ['firefox'];
shared.capabilities.platform = ['Linux'];

module.exports = function(config) {
  config.set(shared);
};
