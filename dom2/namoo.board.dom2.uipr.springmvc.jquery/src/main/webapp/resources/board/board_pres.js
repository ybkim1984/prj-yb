/**
 * COPYRIGHT (c) Nextree Consulting 2015 This software is the proprietary of
 * Nextree Consulting.
 * 
 * @author <a href="mailto:kizellee@nextree.co.kr">kizellee</a>
 * @since 2015. 2. 06.
 * 
 * @title board Presentation JS
 * 
 */
/**
 * page url-path.
 */
nsBoard.urlPath = {
	create : '/board/create',
	update : '/board/',
	del : '/delete',
	check : '/board/check'
}


nsBoard.checkBoardName = function(callBack) {
	var paramData = {
			boardName : $('#input_boardName').val(),
	};
	
	nbUtil.sendModule.ajaxProcess(nsBoard.urlPath.check, 'POST', true,
			paramData, function(result) {
				if (result == false) {
					alert("게시판 이름이 중복되었습니다. 다름 게시판 이름을 사용하세요.");
					return;
				} else{
					callBack();
				}
			}, function(request, status, error) {
				alert("전송 중 오류가 발생했습니다.");
			});
}

nsBoard.createBoard = function() {
	var paramData = {
		boardName : $('#input_boardName').val(),
		teamUsid : $('#input_teamUsid').val(),
		commentable : $("input:checkbox[id=input_commentable]").is(":checked")
	};
	nbUtil.sendModule.ajaxProcess(nsBoard.urlPath.create, 'POST', true,
			paramData, function(result) {
				if (result != null) {
					alert("게시판이 등록되었습니다.");
					location.href = Const.CTX + nsBoard.urlPath.update+result.usid;
				} else {
					alert("게시판 등록중 오류가 발생했습니다. 관리자에게 문의하세요.");
				}
			}, function(request, status, error) {
				alert("전송 중 오류가 발생했습니다.");
			});
};

nsBoard.updateBoard = function() {
	var paramData = {
		boardName : $('#input_boardName').val()
	};
	var boardUsid =  $('#input_boardUsid').val();
	nbUtil.sendModule.ajaxProcess(nsBoard.urlPath.update+boardUsid, 'POST', true,
			paramData, function(result) {
				if (result) {
					alert("게시판이 수정되었습니다.");
					location.href = Const.CTX + nsBoard.urlPath.update+boardUsid;
				} else {
					alert("게시판 수정중 오류가 발생했습니다. 관리자에게 문의하세요.");
				}
			}, function(request, status, error) {
				alert("전송 중 오류가 발생했습니다.");
			});
};
nsBoard.deleteBoard = function() {
	if(!confirm("삭제 하시겠습니까?")){
		return;
	}
	var boardUsid =  $('#input_boardUsid').val();
	nbUtil.sendModule.ajaxProcess(nsBoard.urlPath.update+boardUsid+nsBoard.urlPath.del, 'POST', true,
			null, function(result) {
				if (result) {
					alert("게시판이 삭제되었습니다.");
					location.href = Const.CTX + nbConst.urlPath.boards;
				} else {
					alert("게시판 삭제중 오류가 발생했습니다. 관리자에게 문의하세요.");
				}
			}, function(request, status, error) {
				alert("전송 중 오류가 발생했습니다.");
			});
};


