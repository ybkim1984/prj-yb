/** 
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kwlee@nextree.co.kr">Lee, Kiwang</a>
 * @since 2015. 3. 10.
 * 
 * @title board UI JS
 * 
 */

// init-func =====================================

// Namespace
var nsBoard = nsBoard || {};

// Initailize.
$(document).ready(function () {
	
	nsBoard.bindEvent();
});

// Bind Event.
nsBoard.bindEvent = function () {
	$("#btn_save").click(function(){
		nsBoard.checkBoardName(nsBoard.createBoard);
	});
	$("#btn_cancel").click(function(){
		location.href = Const.CTX + nbConst.urlPath.boards;
	});
	
}


// process-func ==================================


