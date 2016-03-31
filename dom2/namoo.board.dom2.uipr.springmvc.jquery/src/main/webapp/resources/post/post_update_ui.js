/** 
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kizellee@nextree.co.kr">kizellee</a>
 * @since 2015. 2. 06.
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
		location.href = Const.CTX+"/board/"+$("#input_boardUsid").val()+"/posts?page=1";
	});
}


// process-func ==================================


