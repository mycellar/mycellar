module.exports = function(grunt) {

  grunt.loadNpmTasks('grunt-shell');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-connect');
  grunt.loadNpmTasks('grunt-karma');

  grunt.initConfig({
    shell: {
      install: {
        command: 'node ./node_modules/bower/bin/bower install'
      }
    },

    karma: {
      unit: {
        configFile: './src/test/javascript/karma-unit.conf.js',
        autoWatch: false,
        singleRun: true
      },
      unit_auto: {
        configFile: './src/test/javascript/karma-unit.conf.js'
      },
      e2e: {
        configFile: './src/test/javascript/karma-e2e.conf.js',
        autoWatch: false,
        singleRun: true
      },
      e2e_auto: {
        configFile: './src/test/javascript/karma-e2e.conf.js'
      }
    },

    watch: {
      scripts: {
        files: ['./src/main/javascript/**/*.js','./src/main/css/**/*.css'],
        tasks: ['concat'],
        options: {
          spawn: false
        },
      },
    },

    concat: {
      styles: {
        dest: './src/main/webapp/css/mycellar.css',
        src: [
          './bower_components/bootstrap/dist/css/bootstrap.css',
          './bower_components/bootstrap/dist/css/bootstrap-theme.css',
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

  grunt.registerTask('test:e2e', ['karma:e2e']);
  grunt.registerTask('test:unit', ['karma:unit']);
  grunt.registerTask('test', ['test:unit', 'test:e2e']);

  //keeping these around for legacy use
  grunt.registerTask('autotest:unit', ['karma:unit_auto']);
  grunt.registerTask('autotest:e2e', ['karma:e2e_auto']);

  //installation-related
  grunt.registerTask('install', ['shell:install','concat']);

  //defaults
  grunt.registerTask('default', ['dev']);

  //development
  grunt.registerTask('dev', ['install','watch']);
};
