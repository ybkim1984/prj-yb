<!-- 
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 1. 9.
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>

    <title>소셜보드</title>
    <%@ include file="/WEB-INF/views/layout/common.jsp" %>

</head>
<body>

<!-- Main Navigation ========================================================================================== -->
<%@ include file="/WEB-INF/views/layout/menu.jsp" %>

<!-- Header ========================================================================================== -->

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
                </ol>
            </div>
        </div>
    </div>


<!-- Container ======================================================================================= -->
<div class="container">
    <div class="row">
      
        <div style="z-index:1020" class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
            <div class="list-group panel panel-success">
                <div class="panel-heading list-group-item text-center hidden-xs">
                    <h4>게시판</h4>
                </div>
                <div>
                    <c:if test="${empty boardList}">
                        <a class="list-group-item hidden-xs">개설된 게시판이 없습니다.</a>
                    </c:if>
                    <c:forEach var="board" items="${boardList}">
                        <a href="${ctx}/postings?boardUsid=${board.usid}&page=1" class="list-group-item hidden-xs">${board.name}</a>
                    </c:forEach>
                </div>

            </div>
        </div>
        <c:if test="${empty boardList}">
            <div class="col-sm-9 col-lg-9">
                <div>
                    <h5>게시판 관리에서 게시판을 등록해주세요.</h5>
                </div>
            </div>
        </c:if>

    </div>

<!-- Footer ========================================================================================== -->
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
</div>

</body>
</html>