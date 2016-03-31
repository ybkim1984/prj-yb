/** 
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kwlee@nextree.co.kr">Lee, Kiwang</a>
 * @since 2015. 3. 10.
 * 
 */
nsPost.create = function() {
	var paramData = {
			writerEmail : $('#input_writerEmail').val(),
			boardUsid : $('#input_boardUsid').val(),
			title : $('#input_title').val(),
			contents : $('#textarea_contents').val(),
			commentable : $("input:checkbox[id=input_commentable]").is(":checked") ? 'on' : 'off',
			anonymousComment : $("input:checkbox[id=input_anonymousComment]").is(":checked") ? 'on' : 'off'
	};
	if($.trim(paramData.title) == ''){
		alert("제목을 입력하세요.");
		return;
	}
	if($.trim(paramData.contents) == ''){
		alert("내용을 입력하세요.");
		return;
	}
	
	
	nbUtil.sendModule.ajaxProcess("/posting/create", 'POST', true,
			paramData, function(result) {
				if (result.usid != '') {
					alert("게시글이 등록되었습니다.");
					location.href =  Const.CTX+'/posting/detail?boardUsid='+result.boardUsid+'&postingUsid='+result.usid;
				} else{
					alert("게시글 등록중 오류가 발생했습니다. 관리자에게 문의하세요.");
				}
			}, function(request, status, error) {
				alert("전송 중 오류가 발생했습니다.");
			});
};

nsPost.update = function() {
	var paramData = {
			postingUsid : $('#input_postingUsId').val(),
			contents : $('#textarea_contents').val(),
			boardUsid : $('#input_boardUsid').val()
	};
	if($.trim(paramData.contents) == ''){
		alert("내용을 입력하세요.");
		return;
	}
	
	
	nbUtil.sendModule.ajaxProcess("/posting", 'POST', true,
			paramData, function(result) {
				if (result.usid != '') {
					alert("게시글이 수정되었습니다.");
					location.href =  Const.CTX+'/posting/detail?boardUsid='+paramData.boardUsid+'&postingUsid='+paramData.postingUsid;
				} else{
					alert("게시글 수정 중 오류가 발생했습니다. 관리자에게 문의하세요.");
				}
			}, function(request, status, error) {
				alert("전송 중 오류가 발생했습니다.");
			});
};

nsPost.del = function() {
	if(!confirm("삭제 하시겠습니까?")){
		return;
	}
	var boardUsid = $("#input_boardUsid").val();
	var postingUsId = $("#input_postingUsId").val();
	nbUtil.sendModule.ajaxProcess("/posting/delete?postingUsid="+postingUsId, 'POST', true,
			null, function(result) {
				if (result) {
					alert("게시글이 삭제 되었습니다.");
					location.href = Const.CTX+"/postings?boardUsid="+boardUsid+'&page=1';
				} else{
					alert("게시글 삭제 중 오류가 발생했습니다. 관리자에게 문의하세요.");
				}
			}, function(request, status, error) {
				alert("전송 중 오류가 발생했습니다.");
			});
};

nsPost.commnet = function() {
	var paramData = {
			postingUsid : $('#input_postingUsId').val(),
			comment : $('#textarea_comment').val(),
			email : $('#input_email').val(),
			boardUsid : $('#input_boardUsid').val(),
	};
	if($.trim(paramData.comment) == ''){
		alert("내용을 입력하세요.");
		return;
	}
	
	
	nbUtil.sendModule.ajaxProcess("/posting/detail", 'POST', true,
			paramData, function(result) {
				if (result) {
					alert("댓들이 등록 되었습니다.");
					location.href =  Const.CTX+'/posting/detail?boardUsid='+paramData.boardUsid+'&postingUsid='+paramData.postingUsid;
				} else{
					alert("댓글 등록 중 오류가 발생했습니다. 관리자에게 문의하세요.");
				}
			}, function(request, status, error) {
				alert("전송 중 오류가 발생했습니다.");
			});
};

nsPost.commnetDel = function(sequence) {
	var paramData = {
			boardUsid : $('#input_boardUsid').val(),
			postingUsid : $('#input_postingUsId').val(),
			sequence : sequence
	};
	
	nbUtil.sendModule.ajaxProcess("/posting/comment", 'POST', true,
			paramData, function(result) {
				if (result) {
					alert("댓들이 삭제 되었습니다.");
					location.href =  Const.CTX+'/posting/detail?boardUsid='+paramData.boardUsid+'&postingUsid='+paramData.postingUsid;
				} else{
					alert("댓글 삭제 중 오류가 발생했습니다. 관리자에게 문의하세요.");
				}
			}, function(request, status, error) {
				alert("전송 중 오류가 발생했습니다.");
			});
};

