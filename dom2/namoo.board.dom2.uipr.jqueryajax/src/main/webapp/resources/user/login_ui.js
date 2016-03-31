/** 
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:yongjunlee@nextree.co.kr">Lee, Yongjun</a>
 * @since 2015. 1. 22.
 * 
 * @title Login UI JS
 *  
 */

// init-func =====================================

// Namespace
var nsLogin = nsLogin || {};

// Initailize.
$(document).ready(function () {
	
	nsLogin.bindEvent();
});

// Bind Event.
nsLogin.bindEvent = function () {
	
	// Login
	$('#btn_login').click(function () {
		nsLogin.checkLogin();
	});
	$('#input_Email').keypress(function(e) {
	    if (e.keyCode == 13) nsLogin.checkLogin();        
	});
	$('#input_Password').keypress(function(e) {
	    if (e.keyCode == 13) nsLogin.checkLogin();        
	});
	
}


// process-func ==================================

nsLogin.checkLogin = function() {
	//
	if (!$('#input_Email').val()) {
		alert('Email을 입력하세요.');
		return;
	}
	if (!$('#input_Password').val()) {
		alert('비밀번호를 입력하세요.');
		return;
	}
	
	nsLogin.doLogin();
}

