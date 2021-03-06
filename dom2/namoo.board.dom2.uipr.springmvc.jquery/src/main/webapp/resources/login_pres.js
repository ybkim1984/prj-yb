/** 
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kizellee@nextree.co.kr">kizellee</a>
 * @since 2015. 2. 06.
 * 
 */

nsLogin.doLogin = function () {
	
	var paramData = {
		inputEmail : $('#input_Email').val()
		, inputPassword : $('#input_Password').val()
	};
	// function (url, sendtype, isAsyn, paramData, successFunc, errorFunc)
	nbUtil.sendModule.ajaxProcess(
		nbConst.urlPath.login
		, 'POST'
		, true
		, paramData
		, function (result) {
			if (result) {
				location.href = Const.CTX + nbConst.urlPath.home;
			} else {
				alert("로그인에 실패하였습니다. 회원정보를 확인하세요");
			}
		}
		, function (request, status, error) {
			alert("전송 중 오류가 발생했습니다.");
		}
	);
	
}