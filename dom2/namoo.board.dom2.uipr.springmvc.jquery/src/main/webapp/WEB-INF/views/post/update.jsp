<!-- 
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kizellee@nextree.co.kr">kizellee</a>
 * @since 2015. 2. 06.
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>

    <title>소셜보드</title>
    <%@ include file="/WEB-INF/views/layout/common.jsp" %>
	<script src="${ctx}/resources/post/post_update_ui.js"></script>
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
                    <li><a href="${ctx}/post">게시판</a></li>
                    <li><a href="${ctx}/board/${boardusid}posts?page=1" class="active">${socialBoard.name}</a>
                    </li>
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
                    <h4>게시판 관리</h4>
                </div>
                <div>
                    <c:forEach var="board" items="${boardList}">
                        <a href="${ctx}/board/${board.usid}posts?page=1" class="list-group-item hidden-xs">${board.name}</a>
                    </c:forEach>
                </div>
            </div>
        </div>
     
        <div class="col-sm-9 col-lg-9">
            <div>
                <h3>${socialBoard.name}</h3>
            </div>

            <form action="${ctx}/board/${boardUsid}/post/${posting.usid}" method="post">
                <input type="hidden" name="boardUsid" value="${boardUsid}">
                <input type="hidden" name="postingUsid" value="${posting.usid}">

                <div class="panel panel-default">
                    <div class="panel-heading">
                        ${posting.title}
                    </div>
                    <div class="panel-body">
                        <div class="post">
                            <div class="write_info">
                                <span class="name">${posting.writerName}</span>
                                <span class="date"><span class="_val"><fmt:formatDate value="${posting.registerDate}"
                                                                                      pattern="yyyy-MM-dd"/></span></span>

                            </div>

                        </div>
                        <div class="form-group">
                            <textarea class="form-control" name="contents" id="textarea_contents"
                                      rows="5">${posting.contents.contents}</textarea>
                        </div>
                    </div>
                </div>

                <div class="text-center">
                    <a href="javascritp:" id="btn_save" class="btn btn-primary">저장</a>
                    <a href="${ctx}/board/${boardUsid}/post/${posting.usid}"
                       class="btn btn-default">취소</a>
                </div>
            </form>
        </div>
    </div>
	<input type="hidden" name="boardUsid" id="input_boardUsid" value="${boardUsid}"/>
	<input type="hidden" name="postingUsId" id="input_postingUsId" value="${posting.usid}"/>
<!-- Footer ========================================================================================== -->
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
</div>

</body>
</html>