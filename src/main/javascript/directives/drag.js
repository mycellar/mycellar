angular.module('mycellar.directives.drag', []);

angular.module('mycellar.directives.drag').directive('dragItem', [
  '$parse',
  function($parse) {
    return {
      restrict: 'A',
      link: function(scope, element, attrs) {
        element[0].setAttribute('draggable', 'true');
        element[0].addEventListener('dragstart', function(event) {
          event = event.originalEvent || event;
          event.dataTransfer.setData('Text', scope.$eval(attrs.dragItem));
          event.dataTransfer.effectAllowed = 'move';
          element[0].classList.add('dragging');
          event.stopPropagation();
        });
        element[0].addEventListener('dragend', function(event) {
          event = event.originalEvent || event;
          $parse(attrs.postDrag)(scope);
          element[0].classList.remove('dragging');
          event.stopPropagation();
        });
      }
    }
  }
]);

angular.module('mycellar.directives.drag').directive('dragDrop', [
  '$timeout',
  function($timeout) {
    return {
      restrict: 'A',
      link: function(scope, element, attrs) {
        var listNode = element[0];
        var item;
        var placeholderNode;
        listNode.addEventListener('dragover', function(event) {
          event = event.originalEvent || event;
          if (placeholderNode == null) {
            items = listNode.querySelectorAll('[drag-item]')
            placeholderNode = angular.element('<' + items[0].nodeName + ' class="drag-placeholder"></' + items[0].nodeName + '>')[0]
          }
          if (placeholderNode.parentNode !== listNode) {
            listNode.appendChild(placeholderNode);
          }
          var target = event.target;
          var offset = null;
          while (target != null && target.parentNode != null && target.parentNode !== listNode) {
            if (offset == null) {
              offset = target.offsetTop;
            }
            target = target.parentNode;
          }
          if (offset == null) {
            offest = 0;
          }
          if (target != null && target !== placeholderNode) {
            var beforeOrAfter = (offset + event.offsetY) < target.offsetHeight / 2;
            listNode.insertBefore(placeholderNode, beforeOrAfter ? target : target.nextSibling);
          }

          listNode.classList.add('dragover');
          event.preventDefault();
          event.stopPropagation();
          return false;
        });
        listNode.addEventListener('drop', function(event) {
          event = event.originalEvent || event;
          var index = JSON.parse(event.dataTransfer.getData("Text")
                      || event.dataTransfer.getData("text/plain"));
          var targetArray = scope.$eval(attrs.dragDrop);
          var indexTo = 0;
          for (var i = 0; i < items.length; i++) {
            if (placeholderNode.previousElementSibling === items[i]) {
              indexTo = i + 1;
            } else if (placeholderNode.nextElementSibling === items[i]) {
              indexTo = i;
            }
          }
          scope.$apply(function() {
            var toMove = targetArray[index];
            targetArray.splice(index, 1);
            if (indexTo > index) {
              indexTo--;
            }
            targetArray.splice(indexTo, 0, toMove);
          });
          placeholderNode.remove();
          placeholderNode = null;
          listNode.classList.remove('dragover');
          event.preventDefault();
          event.stopPropagation();
          return false;
        });
        listNode.addEventListener('dragleave', function(event) {
          event = event.originalEvent || event;
          listNode.classList.remove('dragover');
          $timeout(function() {
            if (!listNode.classList.contains('dragover')) {
              placeholderNode.remove();
            }
          }, 100);
        });
      }
    };
  }
]);
