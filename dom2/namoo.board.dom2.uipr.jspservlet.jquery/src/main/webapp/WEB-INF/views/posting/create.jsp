<!-- 
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kwlee@nextree.co.kr">Lee, Kiwang</a>
 * @since 2015. 3. 10.
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>

    <title>소셜보드</title>
    <%@ include file="/WEB-INF/views/layout/common.jsp" %>
	<script src="${ctx}/resources/post/post_create_ui.js"></script>
	<script src="${ctx}/resources/post/post_pres.js"></script>
</head>
<body>

<!-- Main Navigation ========================================================================================== -->
<%@ include file="/WEB-INF/views/layout/menu.jsp" %>
<!-- Header ========================================================================================== -->
<header>
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="jumbotron">
                    <h2>게시판</h2>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12 col-lg-12">
                <ol class="breadcrumb">
                    <li><a href="${ctx}/home">홈</a></li>
                    <li><a href="${ctx}/home">게시판</a></li>
                    <li class="active">게시글 등록</li>
                </ol>
            </div>
        </div>
    </div>
</header>

<!-- Container ======================================================================================= -->
<div class="container">
    <div class="row">
        
        <div style="z-index:1020" class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
            <div class="list-group panel panel-success">
                <div class="panel-heading list-group-item text-center hidden-xs">
                    <h4>게시판</h4>
                </div>
                <div>
                    <c:forEach var="board" items="${boardList}">
                        <a href="${ctx}/postings?boardUsid=${board.usid}&page=1" class="list-group-item hidden-xs">${board.name}</a>
                    </c:forEach>
                </div>

            </div>
        </div>
        
        <div class="col-sm-9 col-lg-9">
            <div>
                <h3>게시글 등록</h3>
            </div>
          
            <div class="table-responsive">
                <div class="well">
                    <form class="bs-example form-horizontal">
                        <fieldset>
                            <input type="hidden" name="writerEmail" id="input_writerEmail" value="${loginUser.email}">
                            <input type="hidden" name="boardUsid" id="input_boardUsid" value="${boardUsid}">

                            <div class="form-group">
                                <label class="col-lg-2 control-label">제목</label>

                                <div class="col-lg-10">
                                    <input type="text" name="title" id="input_title" class="form-control">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-lg-2 control-label">내용</label>

                                <div class="col-lg-10">
                                    <textarea class="form-control" name="contents" rows="3" id="textarea_contents" ></textarea>
                                </div>
                            </div>
                            <c:if test="${commentable}">
                                <div class="form-group">
                                    <label class="col-lg-2 control-label">댓글</label>

                                    <div class="col-lg-10">

                                        <div class="checkbox">
                                            <label><input type="checkbox" name="commentable" id="input_commentable">사용안함</label>
                                        </div>

                                        <div class="checkbox">
                                            <label><input type="checkbox" name="anonymousComment" id="input_anonymousComment">무기명</label>
                                        </div>

                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <div class="col-lg-10 col-lg-offset-2">
                                    <a class="btn btn-primary" id="btn_save" >확인</a>
                                    <a class="btn btn-default" id="btn_cancel" >취소</a>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>

<!-- Footer ========================================================================================== -->
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
</div>

</body>
</html>