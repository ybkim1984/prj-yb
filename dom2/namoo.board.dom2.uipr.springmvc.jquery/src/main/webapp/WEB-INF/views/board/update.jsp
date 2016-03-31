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
    <script src="${ctx}/resources/board/board_update_ui.js"></script>
    <script src="${ctx}/resources/board/board_pres.js"></script>
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
                    <li><a href="${ctx}/board">게시판 관리</a></li>
                    <li><a>${socialBoard.name}</a></li>
                    <li class="active">게시판 수정</li>
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
                        <a href="${ctx}/board/${board.usid}" class="list-group-item hidden-xs">${board.name}</a>
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
                <h3>게시판 수정</h3>
            </div>
           
            <div class="table-responsive">
                <div class="well">
                	<form class="form-horizontal">
                        <fieldset>
                            <div class="form-group">
                                <label class="col-lg-2 control-label">게시판명</label>

                                <div class="col-lg-10">
                                	<input type="hidden" id="input_boardUsid" name="boardUsid" value="${socialBoard.usid}">
                                	<input type="hidden" id="input_oldName" value="${socialBoard.name}">
                                    <input type="text" class="form-control" id="input_boardName" name="boardName" value="${socialBoard.name}">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-lg-10 col-lg-offset-2">
                                    <a class="btn btn-primary" id="btn_save">확인</a>
                                    <a class="btn btn-default" id="btn_cancel">취소</a>
                                    <a href="javascript:"  class="btn btn-danger" id="btn_delete">삭제</a>
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