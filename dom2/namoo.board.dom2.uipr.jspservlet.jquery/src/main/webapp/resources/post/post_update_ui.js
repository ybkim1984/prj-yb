/** 
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kwlee@nextree.co.kr">Lee, Kiwang</a>
 * @since 2015. 3. 10.
 * 
 */
// init-func =====================================

// Namespace
var nsPost = nsPost || {};

// Initailize.
$(document).ready(function () {
	
	nsPost.bindEvent();
});

// Bind Event.
nsPost.bindEvent = function () {
	$("#btn_save").click(function(){
		nsPost.update();
	});
	$("#btn_cancel").click(function(){
		location.href = Const.CTX+"/posting/detail?boardUsid="+$("#input_boardUsid").val()+"&postingUsid="+$("#input_postingUsId").val();
	});
}


// process-func ==================================


