// A reference configuration file.
if (!process.env.SAUCE_USERNAME) {
  var seleniumAddress = 'http://localhost:4444/wd/hub';
}
  
exports.config = {
  sauceUser: process.env.SAUCE_USERNAME,
  sauceKey: process.env.SAUCE_ACCESS_KEY,
  seleniumAddress: seleniumAddress,

  specs: [
    './e2e/**/*.js'
  ],

  capabilities: {
    'browserName': 'chrome',
    'build': process.env.TRAVIS_BUILD_NUMBER,
    'name': 'Travis #' + process.env.TRAVIS_BUILD_NUMBER,
    'tunnel-identifier': process.env.TRAVIS_JOB_NUMBER
  },

  baseUrl: 'http://localhost:8080',
  jasmineNodeOpts: {
    // onComplete will be called just before the driver quits.
    onComplete: null,
    // If true, display spec names.
    isVerbose: false,
    // If true, print colors to the terminal.
    showColors: false,
    // If true, include stack traces in failures.
    includeStackTrace: true,
    // Default time to wait in ms before a test fails.
    defaultTimeoutInterval: 30000
  }
};
