var shared = require('./karma-shared.conf');
shared.browsers = ['Chrome', 'Firefox'];

module.exports = function(config) {
  config.set(shared);
};
