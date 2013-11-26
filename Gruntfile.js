module.exports = function(grunt) {

  grunt.loadNpmTasks('grunt-open');
  grunt.loadNpmTasks('grunt-shell');
  grunt.loadNpmTasks('grunt-shell-spawn');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-protractor-runner');
  grunt.loadNpmTasks('grunt-karma');

  grunt.initConfig({
    shell: {
      options: {
        stdout: true
      },
      selenium: {
        command: 'java -jar selenium/selenium-server-standalone-2.35.0.jar -Dwebdriver.chrome.driver=selenium/chromedriver.exe',
        options: {
          stdout: false,
          async: true
        }
      },
      protractor_install: {
        command: 'node ./node_modules/protractor/bin/install_selenium_standalone'
      },
      npm_install: {
        command: 'npm install'
      },
      bower_install: {
        command: 'node ./node_modules/bower/bin/bower install'
      },
    },

    karma: {
      unit: {
        configFile: './src/test/javascript/karma-unit.conf.js',
        autoWatch: false,
        singleRun: true
      },
      unit_browsers: {
        configFile: './src/test/javascript/karma-browsers.conf.js',
        autoWatch: false,
        singleRun: true
      },
      unit_auto: {
        configFile: './src/test/javascript/karma-unit.conf.js',
        autoWatch: true,
        singleRun: false
      },
      unit_coverage: {
        configFile: './src/test/javascript/karma-unit.conf.js',
        autoWatch: false,
        singleRun: true,
        reporters: ['progress', 'coverage'],
        preprocessors: {
          './src/main/javascript/**/*.js': ['coverage']
        },
        coverageReporter: {
          type: 'html',
          dir: 'coverage/'
        }
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
        configFile: "./src/test/javascript/protractor.conf.js"
      },
      singlerun: {
      },
      debug: {
        debug: true
      },
      auto: {
        keepAlive: true,
        options: {
          args: {
            seleniumPort: 4444
          }
        }
      }
    },

    copy: {
      fonts: {
        expand: true,
        cwd: './bower_components/font-awesome/fonts/',
        src: '*',
        dest: './src/main/webapp/fonts/'
      }
    },

    concat: {
      styles: {
        dest: './src/main/webapp/css/mycellar.css',
        src: [
          './bower_components/bootstrap/dist/css/bootstrap.css',
          './bower_components/bootstrap/dist/css/bootstrap-theme.css',
          './bower_components/font-awesome/css/font-awesome.css',
          './src/main/css/mycellar.css'
        ]
      },
      scripts: {
        options: {
          separator: ';'
        },
        dest: './src/main/webapp/js/mycellar.js',
        src: [
          './bower_components/angular/angular.js',
          './bower_components/angular-resource/angular-resource.js',
          './bower_components/angular-route/angular-route.js',
          './src/main/javascript/**/*.js',
        ]
      },
    }
  });

  grunt.registerTask('test', ['test:unit', 'test:e2e']);
  grunt.registerTask('test:unit', ['karma:unit']);
  grunt.registerTask('test:e2e', ['protractor:singlerun']);
  grunt.registerTask('test:unit_browsers', ['karma:unit_browsers']);

  grunt.registerTask('debug:e2e', ['protractor:debug']);

  grunt.registerTask('autotest:unit', ['karma:unit_auto']);
  grunt.registerTask('autotest:e2e', ['shell:selenium', 'watch:protractor']);

  grunt.registerTask('test:coverage', ['karma:unit_coverage']);
  
  //installation-related
  grunt.registerTask('install', ['update','shell:protractor_install']);
  grunt.registerTask('update', ['shell:npm_install','shell:bower_install','concat','copy']);

  //defaults
  grunt.registerTask('default', ['dev']);

  //development
  grunt.registerTask('dev', ['watch:assets']);
};
