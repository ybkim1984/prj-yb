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
                    <li><a href="${ctx}/boards">게시판 관리</a></li>
                    <li class="active">게시판 개설</li>
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

       
        <div class="col-sm-9 col-lg-9">
            <div>
                <h3>게시판 개설</h3>
            </div>

          
            <div class="table-responsive">
                <div class="well">
                    <form class="bs-example form-horizontal" action="${ctx}/board/create" method="post">
                        <fieldset>
                            <div class="form-group">
                                <label class="col-lg-2 control-label">게시판명</label>

                                <div class="col-lg-10">
                                    <input type="text" class="form-control" id="" name="boardName">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-lg-2 control-label">팀선택</label>

                                <div class="col-lg-10">
                                    <select name="teamUsid" class="form-control">
                                        <c:forEach var="team" items="${teams}">
                                            <option value="${team.usid}">${team.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-lg-2 control-label">설정</label>

                                <div class="col-lg-10">
                                    <div class="checkbox">
                                        <label><input type="checkbox" name="commentable"> 댓글사용</label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-lg-10 col-lg-offset-2">
                                    <button type="submit" class="btn btn-primary">확인</button>
                                    <a href="${ctx}/boards"  class="btn btn-default">취소</a>
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