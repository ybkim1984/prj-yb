/**
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:yongjunlee@nextree.co.kr">Lee, Yongjun</a>
 * @since 2015. 1. 22.
 * 
 * @title Namoo-board Common-util JS
 * 
 */

var nbUtil = nbUtil || {} ;

/**
 * 서버 요청/응답 처리 유틸
 */
nbUtil.sendModule = (function() {

	var ajaxProcess = function (url, sendtype, isAsyn, paramData, successFunc, errorFunc) {
		
		if(!url) url = nbConst.urlPath.logout;
		if(!sendtype) sendtype = 'get';
		if(!isAsyn) isAsyn = true;
		
		url = Const.CTX + url;
		
		$.ajax(url, {
			type : sendtype
			, asyn : isAsyn
			, dataType: 'json'
			, data : paramData
			, contentType : 'application/x-www-form-urlencoded;charset=UTF-8'
			, success : function (result) {
				if(typeof successFunc === 'function') {
					successFunc(result);
				}
			}
			, error:function(request, status, error){
				if(typeof errorFunc === 'function') {
					errorFunc(request, status, error);
				} else {
					alert('code:'+request.status+'\n'+'message:'+request.responseText+'\n'+'error:'+error);
				}
	       }
		});
	};
	
	return {
		ajaxProcess : ajaxProcess
	};
	
}());