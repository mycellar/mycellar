var baseUrl = 'http://localhost:8080';

exports.config = {
  sauceUser: process.env.SAUCE_USERNAME,
  sauceKey: process.env.SAUCE_ACCESS_KEY,

  suites: {
    all: [
      '../e2e/**/*.spec.js',
      '../e2e/**/*.spec.pc.js',
    ],
    home: '../e2e/home.spec.js'
  },

  capabilities: {
    'browserName': 'firefox',
    'version': '25',
    'platform': 'Linux',
    'build': process.env.TRAVIS_BUILD_NUMBER,
    'name': 'Travis #' + process.env.TRAVIS_BUILD_NUMBER,
    'tunnel-identifier': process.env.TRAVIS_JOB_NUMBER
  },

  baseUrl: baseUrl,
  framework: 'mocha',
  mochaOpts: {
    reporter: 'spec',
    slow: 3000,
    timeout: 300000
  },
  
  onPrepare: function() {
    browser.polymerGet = function(url) {
      browser.driver.get(baseUrl + url);
      browser.driver.executeAsyncScript(function(callback) {
        window.addEventListener('polymer-ready', callback);
      }).then(function() {
        return browser.waitForAngular();
      });
    };
  }
};
