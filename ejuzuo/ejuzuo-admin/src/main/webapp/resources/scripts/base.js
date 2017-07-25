define([ 'jquery', 
         'bootstrap', 
         'moment', 
         'pace', 
         'base-validation', 
         'jquery.slimscroll', 
         'PNotify', 
         'bootstrap-datetimepicker' ], 
		
		function($, _bootstrap, moment, pace, baseValidation, slimscroll, PNotify) {
	
	'use strict';
	
	$.ajaxSetup({
		error: function(jqXHR, textStatus, errorThrown) {
			if (jqXHR.status === 401) {
				console.log('Global Ajax Error 401 : ' + jqXHR.responseJSON.msg);
				
				new PNotify({
					title : jqXHR.responseJSON.msg || '登录超时/未登录',
					text : '登录超时/未登录, 3秒后将跳转到登录页',
					type : 'error'
				});
				
				setTimeout(function(){
					window.location.href = '/login';
				}, 3000);
			}
			else if (jqXHR.status === 403) {
				console.log('Global Ajax Error 403 : ' + jqXHR.responseJSON.msg);
				
				new PNotify({
					title : jqXHR.responseJSON.msg || '权限不足',
					text : '您没有相应的权限执行当前操作!',
					type : 'error'
				});
			}
			else if (jqXHR.status === 0) {
				console.log('Global Ajax Error 0 : Maybe net work error or server already shutdown');
				
				new PNotify({
					title : '也许服务器挂了',
					type : 'error'
				});
			}
			else {
				if (!jqXHR.responseJSON) {
					console.log('Global Ajax Error ' + jqXHR.status + ': 我也不知道发生啥问题了');
					new PNotify({
						title : '未知异常 ' + jqXHR.status,
						type : 'error'
					});
				} 
				else {
					console.log('Global Ajax Error : ' + jqXHR.responseJSON.msg);
					
					new PNotify({
						title : jqXHR.responseJSON.msg || '异常',
						type : 'error'
					});
				}
			}
		}
	});
	
	// for bootstrap table
	$('body').on('load-error.bs.table', function(event, status){
		if (status === 401) {
			console.log('Global Ajax Error 401 of btable');
			
			new PNotify({
				title : '登录超时/未登录',
				text : '登录超时/未登录, 3秒后将跳转到登录页',
				type : 'error'
			});
			
			setTimeout(function(){
				window.location.href = '/login';
			}, 3000);
		}
		else if (status === 403) {
			console.log('Global Ajax Error 403 of btable');
			
			new PNotify({
				title : '权限不足',
				text : '您没有相应的权限执行当前操作!',
				type : 'error'
			});
		}
		else if (status === 0) {
			console.log('Global Ajax Error 0 of btable : Maybe net work error or server already shutdown');
			
			new PNotify({
				title : '也许服务器挂了',
				type : 'error'
			});
		}
		else {
			console.log('Global Ajax Error of btable : 未知异常 ' + status);
			
			new PNotify({
				title : '未知异常 ' + status,
				type : 'error'
			});
		}
	});
	
	function executePageSimpleAjaxAction(url, title, text, pageId) {
		return $.ajax({
			url: url,
			type: 'POST',
			dataType: 'json',
			complete: function() {
//				$('#page').bootstrapTable('refresh');
			},
			error : function(jqXHR, textStatus, errorThrown) {
				if (!!jqXHR.responseJSON.msg) {
					text += '<br/>' + jqXHR.responseJSON.msg;
				}
				new PNotify({
					title: title + '异常',
					text: text,
					type: 'error'
				});
			},
			success : function(response) {
				if (!response.success) {
					if (!!response.msg) {
						text += '<br/>' + response.msg;
					}
					new PNotify({
						title: title + '失败',
						text: text,
						type: 'error'
					});
				} else {
					new PNotify({
						title: title + '成功',
						text: text,
						type: 'success'
					});
					$(pageId || '#page').bootstrapTable('refresh');
				}
			}
		});
	}
	
	pace.start();
	
	$('#nav-sidebar-toggle').on('click', function(){
		var $body = $('body');
		var $icon = $('#nav-sidebar-toggle-icon');
		var show = $body.hasClass('sidebar-show');
		if (show) {
			$body.removeClass('sidebar-show').addClass('sidebar-hide');
			$icon.removeClass('fa-angle-double-left').addClass('fa-angle-double-right');
		} else {
			$body.removeClass('sidebar-hide').addClass('sidebar-show');
			$icon.removeClass('fa-angle-double-right').addClass('fa-angle-double-left');
		}
	});
	
	var $sidebar = $(".nav-sidebar-wrapper.collapsed #js-nav-sidebar");
	if ($sidebar.length > 0) {
		if ($sidebar.length > 0) {
			$sidebar.slimScroll({
		        width: '50px',
		        height: '100%',
		        size: '3px'
		    });
		}
	} else {
		$sidebar = $("#js-nav-sidebar");
		$sidebar.slimScroll({
	        width: '200px',
	        height: '100%',
	        size: '3px'
	    });
	}
//	$('.slimScrollDiv').css('overflow', '');
//	$("#js-nav-sidebar").css('overflow', '');
	
	function initSimpleFilterDateRangePicker(idPrefix, defaultDateRange) {
		
		var ids = {};
		if (!!idPrefix) {
			ids.beginDate = '#'+idPrefix+'-beginDate';
			ids.endDate = '#'+idPrefix+'-endDate';
			ids.beginDateGroup = '#'+idPrefix+'-beginDate-group';
			ids.endDateGroup = '#'+idPrefix+'-endDate-group';
		} else {
			ids.beginDate = '#filter-beginDate';
			ids.endDate = '#filter-endDate';
			ids.beginDateGroup = '#filter-beginDate-group';
			ids.endDateGroup = '#filter-endDate-group';
		}
		
		var threeMonthsAgo = moment().subtract(3, 'months').hours(0).minutes(0).seconds(0);
		var today = moment().hours(23).minutes(59).seconds(59);
		
		if (defaultDateRange !== false) {
			var fiveDayAgo = moment().subtract(5, 'days').hours(0).minutes(0).seconds(0);
			$(ids.beginDate).val(fiveDayAgo.format('YYYY-MM-DD'));
			$(ids.endDate).val(today.format('YYYY-MM-DD'));
		}
		
		$(ids.beginDateGroup).datetimepicker({
			format : "YYYY-MM-DD",
			locale : 'zh-cn',
			minDate: threeMonthsAgo,
			maxDate: today,
			showTodayButton : true,
			showClear : true,
			showClose : true
		});
		$(ids.endDateGroup).datetimepicker({
			format : "YYYY-MM-DD",
			locale : 'zh-cn',
			minDate: threeMonthsAgo,
			maxDate: today,
			showTodayButton : true,
			showClear : true,
			showClose : true
		});
		$(ids.beginDateGroup).on("dp.change", function(e) {
			if (!e.date) {
				$(ids.endDateGroup).data("DateTimePicker").minDate(false);
			} else {
				e.date.hour(0).minute(0).second(0).millisecond(0);
				$(ids.endDateGroup).data("DateTimePicker").minDate(e.date);
			}
		});
		$(ids.endDateGroup).on("dp.change", function(e) {
			if (!e.date) {
				$(ids.beginDateGroup).data("DateTimePicker").maxDate(false);
			} else {
				e.date.hour(23).minute(59).second(59).millisecond(999);
				$(ids.beginDateGroup).data("DateTimePicker").maxDate(e.date);
			}
		});
		
	}
	
	function initFilterDateTimeRangePicker(idPrefix) {
		
		var ids = {};
		if (!!idPrefix) {
			ids.beginDate = '#'+idPrefix+'-beginDate';
			ids.endDate = '#'+idPrefix+'-endDate';
			ids.beginDateGroup = '#'+idPrefix+'-beginDate-group';
			ids.endDateGroup = '#'+idPrefix+'-endDate-group';
		} else {
			ids.beginDate = '#filter-beginDate';
			ids.endDate = '#filter-endDate';
			ids.beginDateGroup = '#filter-beginDate-group';
			ids.endDateGroup = '#filter-endDate-group';
		}
		
		var firstBegin = true;
		
		var threeMonthsAgo = moment().subtract(3, 'months').hours(0).minutes(0).seconds(0);
		var today = moment().hours(23).minutes(59).seconds(59);
		
		$(ids.beginDateGroup).datetimepicker({
			format : "YYYY-MM-DD HH:mm:ss",
			locale : 'zh-cn',
			minDate: threeMonthsAgo,
			maxDate: today,
			showTodayButton : true,
			showClear : true,
			showClose : true
		});
		$(ids.endDateGroup).datetimepicker({
			format : "YYYY-MM-DD HH:mm:ss",
			locale : 'zh-cn',
			minDate: threeMonthsAgo,
			maxDate: today,
			showTodayButton : true,
			showClear : true,
			showClose : true
		});
		$(ids.beginDateGroup).on("dp.change", function(e) {
			if (!e.date) {
				firstBegin = true;
				$(ids.endDateGroup).data("DateTimePicker").minDate(false);
			} else {
				e.date.hour(0).minute(0).second(0).millisecond(0);
				$(ids.endDateGroup).data("DateTimePicker").minDate(e.date);
				
				if (firstBegin) {
					firstBegin = false;
					$(ids.beginDateGroup).data("DateTimePicker").date(e.date);
				}
			}
		});
		$(ids.endDateGroup).on("dp.change", function(e) {
			if (!e.date) {
				$(ids.beginDateGroup).data("DateTimePicker").maxDate(false);
			} else {
				e.date.hour(23).minute(59).second(59).millisecond(999);
				$(ids.beginDateGroup).data("DateTimePicker").maxDate(e.date);
			}
		});
		
	}
	
	return {
		fmtCurrency : function(value, fixed) {
			if (!value && value !== 0) return null;
			if (!fixed) {
				fixed = 2;
			}
			return value.toFixed(fixed).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
		},
		fmtCurrencyFixed0 : function(value) {
			if (!value && value !== 0) return null;
			var tmp = value.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
			return tmp.replace(/\.\d*/, '');
		},
		fmtCurrencyFixed2 : function(value) {
			if (!value && value !== 0) return null;
			return value.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
		},
		fmtNumberFixed2 : function(value) {
			if (!value && value !== 0) return null;
			return value.toFixed(2);
		},
		splitDate : function(value) {
        	if (!!value) {
        		return value.split(" ")[0];
        	} else {
        		return null;
        	}
        },
        initSimpleFilterDateRangePicker: initSimpleFilterDateRangePicker,
        initFilterDateTimeRangePicker: initFilterDateTimeRangePicker,
        executePageSimpleAjaxAction: executePageSimpleAjaxAction
	};
	
});
