var shared = require('../protractor.conf');
shared.capabilities.browserName = ['chrome'];
shared.capabilities.platform = ['Linux'];

module.exports = function(config) {
  config.set(shared);
};
