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
</head>
<body>

<!-- Main Navigation ================================================================================= -->
<%@ include file="/WEB-INF/views/layout/menu.jsp" %>

<!-- Header ========================================================================================== -->
<header>
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="jumbotron">
                    <h2>게시판 관리</h2>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12 col-lg-12">
                <ol class="breadcrumb">
                    <li><a href="#">게시판 관리</a></li>
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
                    <c:if test="${empty boardList}">
                        <a class="list-group-item hidden-xs">개설된 게시판이 없습니다.</a>
                    </c:if>
                    <c:forEach var="board" items="${boardList}">
                        <a href="${ctx}/board?boardUsid=${board.usid}" class="list-group-item hidden-xs">${board.name}</a>
                    </c:forEach>

                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-xs-6 col-sm-12 col-md-6 col-lg-6 text-left">
                            <a class="btn btn-link btn-sm btn-block" href="${ctx}/board/create">신규게시판 개설</a>
                        </div>
                        <div class="col-xs-6 col-sm-12 col-md-6 col-lg-6 text-left">
                            <a class="btn btn-link btn-sm btn-block" href="#">전체 메일발송</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${empty boardList}">
            <div class="col-sm-9 col-lg-9">
                <div>
                    <h5>신규게시판 개설을 클릭해주세요.</h5>
                </div>
            </div>
        </c:if>


    </div>

<!-- Footer ========================================================================================== -->
 <%@ include file="/WEB-INF/views/layout/footer.jsp" %>
</div>

</body>
</html>