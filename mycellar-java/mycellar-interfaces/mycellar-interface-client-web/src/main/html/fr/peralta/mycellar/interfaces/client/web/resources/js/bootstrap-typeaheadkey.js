!function($){

  "use strict"; // jshint ;_;


 /* TYPEAHEADKEY PUBLIC CLASS DEFINITION
  * ==================================== */

  var Typeaheadkey = function (element, options) {
    this.$element = $(element)
    this.$hiddenid = $("#"+options.hiddenid)
    this.options = $.extend({}, $.fn.typeaheadkey.defaults, options)
    this.matcher = this.options.matcher || this.matcher
    this.sorter = this.options.sorter || this.sorter
    this.highlighter = this.options.highlighter || this.highlighter
    this.updater = this.options.updater || this.updater
    this.sourceUrl = this.options.sourceUrl
    this.updateUrl = this.options.updateUrl
    this.$menu = $(this.options.menu)
    this.shown = false
    this.listen()
  }

  Typeaheadkey.prototype = {
    constructor: Typeaheadkey
  }
  
  $.extend(Typeaheadkey.prototype, $.fn.typeahead.Constructor.prototype, {
    select: function () {
      var val = this.$menu.find('.active').attr('data-value')
      this.$element
        .val(this.updater(val))
        .change()
      var id = this.$menu.find('.active').attr('data-id')
      this.$hiddenid
        .val(id)
      var attrs = {
        u: this.updateUrl,
        c: this.$hiddenid.attr('id')
      }
      Wicket.Ajax.ajax(attrs)
      return this.hide()
    },
    render: function(items) {
  		var that = this
  
      items = $(items).map(function(i, item) {
        i = $(that.options.item).attr('data-value', item.val)
        i.attr('data-id', item.id)
        i.find('a').html(that.highlighter(item.val))
        return i[0]
      })
  
      items.first().addClass('active')
      this.$menu.html(items)
      return this	
  	},
  	matcher: function (item) {
      return ~item.val.toLowerCase().indexOf(this.query.toLowerCase())
    },
    sorter: function (items) {
      var beginswith = []
        , caseSensitive = []
        , caseInsensitive = []
        , item
  
      while (item = items.shift()) {
        if (!item.val.toLowerCase().indexOf(this.query.toLowerCase())) beginswith.push(item)
        else if (~item.val.indexOf(this.query)) caseSensitive.push(item)
        else caseInsensitive.push(item)
      }
  
      return beginswith.concat(caseSensitive, caseInsensitive)
    },
    keyup: function (e) {
      this.$hiddenid.val('')
      switch(e.keyCode) {
        case 40: // down arrow
        case 38: // up arrow
        case 16: // shift
        case 17: // ctrl
        case 18: // alt
          break

        case 9: // tab
        case 13: // enter
          if (!this.shown) return
          this.select()
          this.$element.blur()
          break

        case 27: // escape
          if (!this.shown) return
          this.hide()
          this.$element.blur()
          break

        default:
          this.lookup()
      }
    },
    blur: function (e) {
      var that = this
      this.$hiddenid.val() == '' && this.$element.val('')
      setTimeout(function () { that.hide() }, 150)
    },
    source: function (query, process) {
      var attrs = {
        u: this.sourceUrl,
        ep: {term: query},
        dt: 'json',
        wr: false,
        sh: [
          function(attributes, jqXHR, data, textStatus) {
            process(data);
          }
        ]
      }
      Wicket.Ajax.ajax(attrs)
    }
  });
  
  /* TYPEAHEADKEY PLUGIN DEFINITION
   * ============================== */
  
  $.fn.typeaheadkey = function (option) {
    return this.each(function () {
      var $this = $(this)
        , data = $this.data('typeaheadkey')
        , options = typeof option == 'object' && option
      if (!data) $this.data('typeaheadkey', (data = new Typeaheadkey(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }
  
  $.fn.typeaheadkey.defaults = {
    items: 8
  , menu: '<ul class="typeahead dropdown-menu"></ul>'
  , item: '<li><a href="#"></a></li>'
  , minLength: 1
  }
  
  $.fn.typeaheadkey.Constructor = Typeaheadkey
  
}(window.jQuery);
