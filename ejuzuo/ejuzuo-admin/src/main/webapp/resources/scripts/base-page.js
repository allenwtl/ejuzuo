// Uses AMD or browser globals to create a jQuery plugin.
// It does not try to register in a CommonJS environment since
// jQuery is not likely to run in those environments.
// See jqueryPluginCommonJs.js for that version.
(function(factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD. Register as an anonymous module.
		define([ 'jquery', 'doT', 'pnotify', 'pnotify.buttons' ], factory);
	} else {
		// Browser globals
		factory($, doT, PNotify);
	}
}(function($, doT, PNotify) {
	"use strict";

	// 这玩意需要重构

	if (PNotify === undefined) {
		PNotify = window.PNotify;
	}

	$.fn.AdminPage = function( options ) {

		var defaults = {
			// 分页模板 ID 选择器
			doTPageSelector : "#dot-pagination",
			// 列表模板 ID 选择器
			doTListSelector : "#dot-list",
			// 最外层 包裹 的 DOM 元素 ID 选择器
			wrapperSelector : "#page",
			// 过滤栏 查询按钮 的 ID 选择器
			searchSelector : "#filter-submit",
			// 过滤栏 查询按钮 的 校验前提条件, 携带参数 parameter
			searchValidator : undefined,
			// 分页相关数据
	    	page : {
	    		sizes : [10, 15, 20],
	    		pageSize : 15,
	    		pageNo : 1,
	    		totalPage : 1,
	    		total : 0
	    	},
	    	// 异步请求URL
	    	url : "",
	    	// 异步请求参数 默认值
	    	defaultParam : {},
	    	// 异步请求参数
	    	param : {},
			useCache: true,
	    	// 分页请求参数 本地 session cache key
	    	cacheKey : "",
	    	// 数据格式化方法 可改写新增删除数据
			dataFormat : undefined,
			// 获取 过滤栏 过滤参数
	    	getParam : function(){},
	    	// 过滤栏 是否有过滤栏 默认有
	    	filter: true,
	    	// 过滤栏 缓存 回填 回调方法
	    	initFilter: undefined,
	    	// 渲染过后的回调
	    	afterRender: function(){},
	    	
	    	pageSizeChange : function(){},
	    	
	    	pageRefresh:function(){}
	    };

		// 继承可选项参数
		var settings = $.extend( {}, defaults, options );
		if (!!options.page) {
			settings.page = $.extend( {}, defaults.page, options.page );
		}

		var _self = {};

		// session storage 相关方法
		_self.sessionStorage = {
			getItem : function(key) {
				if (typeof window.sessionStorage === 'undefined') {
					console.error("浏览器暂不支持sessionStorage!");
					return undefined;
				}

				if (typeof key !== 'string' || !key) {
					console.error("key值异常!");
					return undefined;
				}

				var rtnJson = undefined;
				try {
					var value = window.sessionStorage.getItem(key);
					rtnJson = JSON.parse(value);
				} catch (e) {
					console.error(e);
				}

				return rtnJson;

			},
			setItem : function(key, value) {
				if (typeof window.sessionStorage === 'undefined') {
					console.error("浏览器暂不支持sessionStorage!");
					return false;
				}

				if (typeof key !== 'string' || !key) {
					console.error("key值异常!");
					return false;
				}

				var val;
				if (typeof value !== 'string') {
					val = JSON.stringify(value);
				} else {
					val = value;
				}

				try {
					window.sessionStorage.setItem(key, val);
					return true;
				} catch (e) {
					console.error(e);
					return false;
				}

			},
			removeItem : function(key) {
				if (typeof window.sessionStorage === 'undefined') {
					console.error("浏览器暂不支持sessionStorage!");
					return false;
				}

				if (typeof key !== 'string' || !key) {
					console.error("key值异常!");
					return false;
				}

				try {
					window.sessionStorage.removeItem(key);
					return true;
				} catch (e) {
					console.error(e);
					return false;
				}

			},
			getFilterParam : function(key) {
				if (typeof key !== 'string' || !key) {
					console.error("key值异常!");
					return {};
				}

				return this.getItem("aicai.cms.page-filter." + key);
			},
			setFilterParam : function(key, obj) {
				if (typeof key !== 'string' || !key) {
					console.error("key值异常!");
					return false;
				}

				key = "aicai.cms.page-filter." + key;
				return this.setItem(key, obj);
			},
			getPageParam : function(key, defaultData) {
				if (typeof key !== 'string' || !key) {
					console.error("key值异常!");
					return defaultData;
				}

				return this.getItem("aicai.cms.page-param." + key) || defaultData;
			},
			setPageParam : function(key, value) {
				if (typeof key !== 'string' || !key) {
					console.error("key值异常!");
					return false;
				}

				key = "aicai.cms.page-param." + key;
				return this.setItem(key, value);
	    	}
		};

		_self.initFilter = function(key, callback) {

			if (settings.useCache) {
				if (typeof key !== 'string' || !key) {
					console.error("key值异常!");
				}
			}

			if (typeof callback !== "function") {
				console.error("callback is not a function!");
			}

			var param = {};
			if (settings.useCache && typeof key === 'string' && !!key) {
				param = this.sessionStorage.getFilterParam(key);
			}
			callback(param);

			return param;
		};

		// 编译 模板
		_self.doTFnPage = doT.template($(settings.doTPageSelector).html());
		_self.doTFnList = doT.template($(settings.doTListSelector).html());

		// 加载分页方法
		_self.renderPage = function(callback) {
			// 提取分页查询参数
			// var param = settings.param || {};
			var param = $.extend( {}, settings.defaultParam, settings.param) || {};
			param.pageSize = settings.page.pageSize || 10;
			param.pageNo = settings.page.pageNo || 1;

			$.ajax({
				type: "GET",
				dataType: "json",
				url : settings.url,
				data : param,
				beforeSend : function() {
					// $("#"+settings.loader).show();
				},
				complete : function() {
					// $("#"+settings.loader).hide();
				},
				error : function() {
					new PNotify({
				    	title: '分页加载失败！',
				    	type: 'error'
				    });
				},
				success : function(json) {
					if (json.success === false) {
						new PNotify({
					    	title: '分页加载失败！',
					    	text: json.msg || "",
					    	type: 'error'
					    });
						return;
					}

					// 分页参数 继承 返回结果
					settings.page = $.extend( {}, settings.page, {
						pageSize : json.pageSize,
			    		pageNo : json.pageNo,
			    		totalPage : json.totalPage,
			    		total : json.total
					} );

					// 空页递归
					if (!json.list.length && settings.page.pageNo !== 1) {
						settings.page.pageNo = 1;
						_self.renderPage();
						return ;
					}

					// cache page info into session storage
					settings.page.table = undefined;
					if (settings.useCache && typeof settings.cacheKey === 'string' && !!settings.cacheKey) {
						_self.sessionStorage.setPageParam(settings.cacheKey, settings.page);
					}

					// 格式化列表数据
					if ($.isFunction(settings.dataFormat)) {
						json = settings.dataFormat(json);
					}

					settings.page.table = _self.doTFnList(json);
					$(settings.wrapperSelector).html(_self.doTFnPage(settings.page));

					if ($.isFunction(settings.afterRender)) {
						settings.afterRender();
					}
					if ($.isFunction(callback)) {
						callback();
					}
				}
			});
		};

		// 设置 缓存的 过滤 参数
		if (settings.filter) {
			var tmpParam = _self.initFilter(settings.cacheKey, settings.initFilter);
			settings.param = $.extend( {}, settings.param, tmpParam);
			settings.param = $.extend( {}, settings.param, settings.defaultParam);
		}
		// 设置 缓存的 分页 参数
		if (settings.useCache && typeof settings.cacheKey === 'string' && !!settings.cacheKey) {
			var tmpPageParam = _self.sessionStorage.getPageParam(settings.cacheKey);
			settings.page = $.extend( {}, settings.page, tmpPageParam);
		}

		// 初次 渲染 分页
		if (!!_self.doTFnPage && !!_self.doTFnList && !!settings.url) {
			_self.renderPage();
		} else {
			throw "Pagination's options is invalid!";
		}

		// 分页 各 事件代理
		$(settings.searchSelector).on("click", function() {
			var $btn = $(this).attr("disabled", "disabled");
			settings.param = $.extend( {}, settings.defaultParam, settings.getParam() );
			// settings.param = settings.getParam();
			var valid = false;
			if ($.isFunction(settings.searchValidator)) {
				valid = settings.searchValidator(settings.param);
			} else {
				valid = true;
			}

			if (valid) {

				if (settings.useCache && typeof settings.cacheKey === 'string' && !!settings.cacheKey) {
					_self.sessionStorage.setFilterParam(settings.cacheKey, settings.param);
				}

				_self.renderPage(function(){
					$btn.removeAttr("disabled");
				});
			} else {
				$btn.removeAttr("disabled");
			}

		});

		$(settings.wrapperSelector)
		.on("click", ".pagination-bar-refresh", function(){
			var $btn = $(this).attr("disabled", "disabled");
			settings.pageRefresh();
			_self.renderPage(function(){
				$btn.removeAttr("disabled");
			});
		})
		.on("change", ".pagination-bar-size", function(){
			settings.page.pageSize = $(this).val();
			settings.pageSizeChange();
			_self.renderPage();
		})
		.on("click", ".pagination-bar-btn", function(){
			settings.page.pageNo = $(this).data("no");
			_self.renderPage();
		})
		.on("click", ".pagination-bar-jump", function(){
			var $btn = $(this).attr("disabled", "disabled");

			var domNum = $(settings.wrapperSelector + " .pagination-bar-jump-num");
			var pageNo = domNum.val();
			var minNo = +domNum.attr("min");
			var maxNo = +domNum.attr("max");
			var pattern = new RegExp("^[1-9][0-9]*$","g");

			if (pattern.test(pageNo) && (minNo <= +pageNo && +pageNo <= maxNo)
					&& settings.page.pageNo != pageNo) {
				settings.page.pageNo = pageNo;
				_self.renderPage(function(){
					$btn.removeAttr("disabled");
				});
			}
		});

		// 对外提供一个reload接口
		return {
			setDefaultParam : function(param) {
				settings.defaultParam = param || {};
			},
       		rerender : function(options) {

       			options = options || {};

       			// 重新获取参数
       			options.param = settings.getParam();
       			// 重新获取分页数据
       			if (options.page) {
       				options.page = $.extend( {}, settings.page, options.page );
       			}

       			settings = $.extend( {}, settings, options );

				if (settings.useCache && typeof settings.cacheKey === 'string' && !!settings.cacheKey) {
					_self.sessionStorage.setFilterParam(settings.cacheKey, settings.param);
				}

       			_self.renderPage();
       		}
       	};
	};
}));
