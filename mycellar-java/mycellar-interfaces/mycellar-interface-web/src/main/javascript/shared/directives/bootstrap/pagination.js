angular.module('mycellar.directives.bootstrap.pagination', []);

angular.module('mycellar.directives.bootstrap.pagination').constant('paginationConfig', {
  boundaryLinks: false,
  directionLinks: true,
  firstText: 'First',
  previousText: 'Previous',
  nextText: 'Next',
  lastText: 'Last',
  rotate: true
});

angular.module('mycellar.directives.bootstrap.pagination').directive('pagination', [
  'paginationConfig',
  function (config) {
    return {
      restrict: 'EA',
      scope: {
        numPages: '=',
        currentPage: '=',
        maxSize: '=',
        onSelectPage: '&'
      },
      controller: function($scope, $interpolate) {
        this.currentPage = 1;
        
        this.noPrevious = function () {
          return this.currentPage === 1;
        };
        this.noNext = function () {
          return this.currentPage === $scope.numPages;
        };
        this.isActive = function (page) {
          return this.currentPage === page;
        };
        
        this.reset = function () {
          $scope.pages = [];
          this.currentPage = parseInt($scope.currentPage, 10);
          
          if (this.currentPage > $scope.numPages) {
            $scope.selectPage($scope.numPages);
          }
        };
        
        var self = this;
        $scope.selectPage = function (page) {
          if (!self.isActive(page) && page > 0 && page <= $scope.numPages) {
            $scope.currentPage = page;
            $scope.onSelectPage({page: page})(page); // WHY ?????
          }
        };
        
        this.getAttributeValue = function (attribute, defaultValue, interpolate) {
          return angular.isDefined(attribute) ? (interpolate ? $interpolate(attribute)($scope.parent) : $scope.$parent.$eval(attribute)) : defaultValue;
        };
      },
      templateUrl: 'partials/directives/bootstrap/pagination.tpl.html',
      replace: true,
      link: function (scope, element, attrs, controller) {
        var boundaryLinks = controller.getAttributeValue(attrs.boundaryLinks, config.boundaryLinks),
        directionLinks = controller.getAttributeValue(attrs.directionLinks, config.directionsLinks),
        firstText = controller.getAttributeValue(attrs.firstText, config.firstText),
        previousText = controller.getAttributeValue(attrs.previousText, config.previousText),
        nextText = controller.getAttributeValue(attrs.nextText, config.nextText),
        lastText = controller.getAttributeValue(attrs.lastText, config.lastText),
        rotate = controller.getAttributeValue(attrs.rotate, config.rotate);
        
        function makePage (number, text, isActive, isDisabled) {
          return {
            number: number,
            text: text,
            active: isActive,
            disabled: isDisabled
          };
        }
        
        scope.$watch('numPages + currentPage + maxSize', function () {
          controller.reset();
          
          var startPage = 1, endPage = scope.numPages;
          var isMaxSized = (angular.isDefined(scope.maxSize) && scope.maxSize < scope.numPages);
          
          if (isMaxSized) {
            if (rotate) {
              startPage = Math.max(controller.currentPage - Math.floor(scope.maxSize/2), 1);
              endPage = startPage + scope.maxSize + 1;
              
              if (endPage > scope.numPages) {
                endPage = scope.numPages;
                startPage = endPage - scope.maxSize + 1;
              }
            } else {
              startPage = ((Math.cell(controller.currentPage / scope.maxSize) - 1) * scope.maxSize) + 1;
              endPage = Math.min(startPage + scope.maxSize - 1, scope.numPages);
            }
          }
          
          for (var number = startPage; number <= endPage; number++) {
            var page = makePage(number, number, controller.isActive(number), false);
            scope.pages.push(page);
          }
          
          if (isMaxSized && !rotate) {
            if (startPage > 1) {
              var previousPageSet = makePage(startPage - 1, '...', false, false);
              scope.pages.unshift(previousPageSet);
            }
            if (endPage < scope.numPages) {
              var nextPageSet = makePage(endPage + 1, '...', false, false);
              scope.pages.push(nextPageSet);
            }
          }
          
          if (directionLinks) {
            var previousPage = makePage(controller.currentPage - 1, previousText, false, controller.noPrevious());
            scope.pages.unshift(previousPage);
            
            var nextPage = makePage(controller.currentPage + 1, nextText, false, controller.noNext());
            scope.pages.push(nextPage);
          }
          
          if (boundaryLinks) {
            var firstPage = makePage(1, firstText, false, controller.noPrevious());
            scope.pages.unshift(firstPage);
            
            var lastPage = makePage(scope.numPages, lastText, false, controller.noNext());
            scope.pages.push(lastPage);
          }
        });
      }
    };
  }
]);