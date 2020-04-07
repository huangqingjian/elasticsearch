! function(t) {
    function a(i) {
        if (e[i]) return e[i].exports;
        var n = e[i] = {
            i: i,
            l: !1,
            exports: {}
        };
        return t[i].call(n.exports, n, n.exports, a), n.l = !0, n.exports
    }
    var e = {};
    a.m = t, a.c = e, a.d = function(t, e, i) {
        a.o(t, e) || Object.defineProperty(t, e, {
            enumerable: !0,
            get: i
        })
    }, a.r = function(t) {
        "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(t, Symbol.toStringTag, {
            value: "Module"
        }), Object.defineProperty(t, "__esModule", {
            value: !0
        })
    }, a.t = function(t, e) {
        if (1 & e && (t = a(t)), 8 & e) return t;
        if (4 & e && "object" == typeof t && t && t.__esModule) return t;
        var i = Object.create(null);
        if (a.r(i), Object.defineProperty(i, "default", {
            enumerable: !0,
            value: t
        }), 2 & e && "string" != typeof t)
            for (var n in t) a.d(i, n, function(a) {
                return t[a]
            }.bind(null, n));
        return i
    }, a.n = function(t) {
        var e = t && t.__esModule ? function() {
            return t.default
        } : function() {
            return t
        };
        return a.d(e, "a", e), e
    }, a.o = function(t, a) {
        return Object.prototype.hasOwnProperty.call(t, a)
    }, a.p = "", a(a.s = 0)
}([function(t, a) {
    var e = {
        maxPage: 7,
        startPage: 1,
        currentPage: 8,
        totalItemCount: 10,
        totalPageCount: 15,
        callback: $.noop
    };
    window.pagination = function(t) {
        t = $.extend({}, e, t), this.$pagination = t.pagination, this.maxPage = t.maxPage, this.startPage = t.startPage, this.totalItemCount = t.totalItemCount, this.totalPageCount = t.totalPageCount, this.currentPage = t.currentPage, this.callback = t.callback, this.item_count = Math.floor(t.maxPage / 2), this.$pageList = $(""), this.init()
    }, pagination.prototype.init = function() {
        var t = "";
        this.totalItemCount && (t = '<span class="page-count">当前共有<i class="page-count_num">' + this.totalItemCount + "</i>条</span>");
        var a = this.renderPageItem(),
            e = '<span class="btn-first-page item">上一页</span><div class="pageList-wrap"></div><span class="btn-next-page item">下一页</span>' + t;
        this.$pagination.append(e), $(".pageList-wrap", this.$pagination).append(a), this.addEvent()
    }, pagination.prototype.renderPageItem = function() {
        var t, a = 1,
            e = "",
            i = "",
            n = "";
        this.totalPageCount < this.maxPage ? t = this.totalPageCount + 1 : (i = '<li class="item eliplise"><span>...</span></li>', a = this.currentPage - this.item_count, t = this.currentPage + this.item_count + 1, this.currentPage < 5 && (a = 1, t = this.maxPage + 1), this.currentPage >= 5 && t <= this.totalPageCount && (e = '<li class="item eliplise" ><span>...</span></li>'), t > this.totalPageCount && (t = this.totalPageCount + 1, a = this.totalPageCount - this.maxPage + 1, i = "", e = '<li class="item eliplise" ><span>...</span></li>')), n += e;
        for (var s = a; s < t; s++) n += '<li class="item" value=' + s + "><span>" + s + "</span></li>";
        return n + i
    }, pagination.prototype.addEvent = function() {
        this.$pagination.find(".item[value=" + this.currentPage + "]").addClass("active"), this.$pagination.off("click").on("click", ".item[value]", _.throttle(function(t) {
            var a = $(t.target).closest(".item");
            this.currentPage = parseInt(a.attr("value")), 1 === this.currentPage ? $(".btn-first-page").addClass("disabled") : $(".btn-first-page").removeClass("disabled"), this.currentPage === this.totalPageCount ? $(".btn-next-page").addClass("disabled") : $(".btn-next-page").removeClass("disabled"), $(".pageList-wrap", this.$pagination).empty().append(this.renderPageItem()), this.$pagination.find(".item[value=" + this.currentPage + "]").addClass("active"), this.callback(this.currentPage)
        }.bind(this), 300)), $(".btn-first-page").on("click", function() {
            1 !== parseInt($(".pagination .item.active").attr("value")) ? ($(this).removeClass("disabled"), $(".pagination .item.active").prev().click()) : $(this).addClass("disabled")
        }), $(".btn-next-page").on("click", function() {
            parseInt($(".pagination .item.active").attr("value")) !== parseInt($(this).closest(".pagination").attr("totalPageNum")) ? $(".pagination .item.active").next().click() : $(this).addClass("disabled")
        })
    }
}]);