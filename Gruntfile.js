module.exports = function(grunt) {

  grunt.loadNpmTasks('grunt-shell');
  grunt.loadNpmTasks('grunt-shell-spawn');
  grunt.loadNpmTasks('grunt-concurrent');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-protractor-runner');
  grunt.loadNpmTasks('grunt-karma');
  grunt.loadNpmTasks('grunt-karma-coveralls');
  grunt.loadNpmTasks('grunt-coveralls-merge');

  grunt.initConfig({
    shell: {
      options: {
        stdout: true
      },
      selenium: {
        command: 'node ./node_modules/protractor/bin/webdriver-manager start',
        options: {
          stdout: false,
          async: true
        }
      },
      protractor_install: {
        command: 'node ./node_modules/protractor/bin/webdriver-manager update'
      },
      npm_install: {
        command: 'npm --no-color install'
      },
      bower_install: {
        command: 'node ./node_modules/bower/bin/bower --no-color install'
      }
    },

    karma: {
      unit: {
        configFile: './src/test/javascript/karma-unit.conf.js',
        colors: false
      },
      unit_debug: {
        configFile: './src/test/javascript/karma-unit.conf.js',
        browsers: ['Chrome'],
        singleRun: false
      },
      unit_browsers: {
        configFile: './src/test/javascript/karma-unit.conf.js',
        browsers: ['Chrome', 'Firefox'],
        colors: false
      },
      unit_auto: {
        configFile: './src/test/javascript/karma-unit.conf.js',
        autoWatch: true,
        singleRun: false
      },
      unit_coverage: {
        configFile: './src/test/javascript/karma-coverage.conf.js',
        colors: false
      }
    },

    coveralls: {
      options: {
        dryRun: true,
        coverage_dir: 'coverage'
      }
    },
    
    coveralls_merge: {
      options: {
        dryRun: false,
        coveralls_files: [
          'coverage/coveralls.json',
          'target/coveralls.json'
        ],
        coverage_dir: 'coverage'
      }
    },

    watch: {
      assets: {
        files: ['./src/main/javascript/**/*.js','./src/main/css/**/*.css'],
        tasks: ['concat'],
        options : {
          livereload: 7777
        }
      },
      protractor: {
        files: ['./src/main/javascript/**/*.js', './src/test/javascript/e2e/**/*.js', './src/main/webapp/partials/**/*.html'],
        tasks: ['protractor:auto']
      }
    },
    
    protractor: {
      options: {
        keepAlive: false,
        configFile: './src/test/javascript/protractor.conf.js'
      },
      singlerun: {
      },
      debug: {
        options: {
          debug: true
        }
      },
      auto: {
        options: {
          keepAlive: true,
          seleniumAddress: 'http://localhost:4444/wd/hub'
        }
      },
      sauce_chrome_windows: {
        options: {
          configFile: './src/test/javascript/sauce/protractor-chrome-windows.conf.js'
        }
      },
      sauce_chrome_linux: {
        options: {
          configFile: './src/test/javascript/sauce/protractor-chrome-linux.conf.js'
        }
      },
      sauce_firefox_windows: {
        options: {
          configFile: './src/test/javascript/sauce/protractor-firefox-windows.conf.js'
        }
      },
      sauce_firefox_linux: {
        options: {
          configFile: './src/test/javascript/sauce/protractor-firefox-linux.conf.js'
        }
      },
      sauce_iphone: {
        options: {
          configFile: './src/test/javascript/sauce/protractor-iphone.conf.js'
        }
      },
      sauce_ipad: {
        options: {
          configFile: './src/test/javascript/sauce/protractor-ipad.conf.js'
        }
      },
      sauce_android: {
        options: {
          configFile: './src/test/javascript/sauce/protractor-android.conf.js'
        }
      }
    },

    copy: {
      lib: {
        expand: true,
        cwd: './bower_components/',
        src: [
          '**/*'
        ],
        dest: './target/generated-sources/webapp/lib/'
      },
      http_auth: {
        expand: true,
        cwd: './bower_components/angular-http-auth/src/',
        src: [
          'http-auth-interceptor.js'
        ],
        dest: './target/generated-sources/webapp/lib/angular-http-auth/'
      },
      loading_bar: {
        expand: true,
        cwd: './bower_components/angular-loading-bar/build/',
        src: [
          'loading-bar.min.js',
          'loading-bar.min.js.map',
          'loading-bar.css'
        ],
        dest: './target/generated-sources/webapp/lib/angular-loading-bar/'
      }
    },

    concat: {
      styles: {
        dest: './target/generated-sources/webapp/css/mycellar.css',
        src: [
          './src/main/css/**/*.css'
        ]
      },
      scripts: {
        options: {
          separator: ';'
        },
        dest: './target/generated-sources/webapp/js/mycellar.js',
        src: [
          './src/main/javascript/mycellar.js',
          './src/main/javascript/services/**/*.js',
          './src/main/javascript/resources/**/*.js',
          './src/main/javascript/filters/**/*.js',
          './src/main/javascript/directives/**/*.js',
          './src/main/javascript/controllers/**/*.js'
        ]
      }
    },

    // 3 PC & 3 MAC (saucelabs os limitation)
    concurrent: {
      sauce_test: [
        'protractor:sauce_chrome_windows',
        'protractor:sauce_chrome_linux',
        'protractor:sauce_firefox_windows',
//        'protractor:sauce_firefox_linux',
// TODO add android compatibility
//        'protractor:sauce_android',
// TODO add iphone compatibility
//        'protractor:sauce_iphone',
//        'protractor:sauce_ipad'
      ],
      sauce_test2: [
        'protractor:sauce_firefox_linux'
      ]
    }
  });

  grunt.registerTask('test', ['test:unit', 'test:e2e']);
  grunt.registerTask('test:unit', ['karma:unit']);
  if (process.env.SAUCE_USERNAME) {
    grunt.registerTask('test:e2e', ['concurrent:sauce_test', 'concurrent:sauce_test2']);
  } else {
    grunt.registerTask('test:e2e', ['protractor:singlerun']);
  }
  grunt.registerTask('test:unit_browsers', ['karma:unit_browsers']);

  grunt.registerTask('debug:unit', ['karma:unit_debug']);
  grunt.registerTask('debug:e2e', ['protractor:debug']);

  grunt.registerTask('autotest:unit', ['karma:unit_auto']);
  grunt.registerTask('autotest:e2e', ['shell:selenium','watch:protractor']);

  grunt.registerTask('test:coverage', ['karma:unit_coverage']);
  grunt.registerTask('test:e2e_coverage', ['protractor:singlerun']);
  
  //installation-related
  grunt.registerTask('install', [
    'update',
    'shell:protractor_install'
  ]);
  grunt.registerTask('update', [
    'shell:npm_install',
    'shell:bower_install',
    'concat',
    'copy'
  ]);

  //defaults
  grunt.registerTask('default', ['dev']);

  //development
  grunt.registerTask('dev', ['watch:assets']);

  //coverage
  grunt.registerTask('coverage', ['test:coverage', 'coveralls']);
};
