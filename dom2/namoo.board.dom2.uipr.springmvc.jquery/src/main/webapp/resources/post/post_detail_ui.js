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
	$("#btn_update").click(function(){
		location.href = Const.CTX+"/board/"+$("#input_boardUsid").val()+"/post/"+$("#input_postingUsId").val()+"/update";
	});
	$("#btn_delete").click(function(){
		nsPost.del();
	});
	$("#btn_commnet").click(function(){
		nsPost.commnet();
	})
	$("#table_comment .glyphicon-trash").click(function(){
		nsPost.commnetDel($(this).attr("id"));
	})
	
}


// process-func ==================================


