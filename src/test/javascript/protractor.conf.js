exports.config = {
  sauceUser: process.env.SAUCE_USERNAME,
  sauceKey: process.env.SAUCE_ACCESS_KEY,

  specs: [
    './e2e/**/*.spec.js',
    './e2e/**/*.spec.pc.js',
    './e2e/**/*.spec.plus.js'
  ],

  capabilities: {
    'browserName': 'chrome',
    'build': process.env.TRAVIS_BUILD_NUMBER,
    'name': 'Travis #' + process.env.TRAVIS_BUILD_NUMBER,
    'tunnel-identifier': process.env.TRAVIS_JOB_NUMBER
  },

  baseUrl: 'http://localhost:8080',
  framework: 'mocha',
  mochaOpts: {
    reporter: 'spec',
    slow: 3000
  }
};
