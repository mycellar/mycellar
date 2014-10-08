exports.config = {
  sauceUser: process.env.SAUCE_USERNAME,
  sauceKey: process.env.SAUCE_ACCESS_KEY,

  suites: {
    all: [
      '../e2e/**/*.spec.js',
    ],
    home: '../e2e/home.spec.js'
  },

  capabilities: {
    'browserName': 'android',
    'version': '4.0',
    'platform': 'Linux',
    'device-orientation': 'portrait',
    'build': process.env.TRAVIS_BUILD_NUMBER,
    'name': 'Travis #' + process.env.TRAVIS_BUILD_NUMBER,
    'tunnel-identifier': process.env.TRAVIS_JOB_NUMBER
  },

  baseUrl: 'http://localhost:8080',
  framework: 'mocha',
  mochaOpts: {
    reporter: 'spec',
    slow: 3000,
    timeout: 300000
  }
};
