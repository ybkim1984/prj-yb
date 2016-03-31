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
	<script src="${ctx}/resources/post/post_detail_ui.js"></script>
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
                    <li><a href="${ctx}/board/${boardusid}/posts?page=1" class="active">${socialBoard.name}</a>
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
                        <a href="${ctx}/board/${board.usid}/posts?page=1" class="list-group-item hidden-xs">${board.name}</a>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="col-sm-9 col-lg-9">
            <div>
                <h3>${socialBoard.name}</h3>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    ${posting.title}
                </div>
                <div class="panel-body">
                    <div class="post">
                        
                         <strong>${posting.writerName}</strong>
                         &nbsp;<span class="text-muted">    <fmt:formatDate value="${posting.registerDate}" pattern="yyyy-MM-dd"/></span>
                         &nbsp;<span class="text-muted">${posting.readCount} 읽음</span>


                         <c:if test="${socialBoard.commentable}">
                             <c:if test="${posting.option.commentable}">
                                 <span class="glyphicon glyphicon-comment" style="padding:10px"> ${posting.contents.commentBag.comments.size()}</span>
                             </c:if>
                         </c:if>
                         <a href="javascript:" id="btn_update"
                            class="glyphicon glyphicon-cog pull-right" style="padding:10px">수정</a>
                         <a href="javascritp:" id="btn_delete"
                            class="glyphicon glyphicon-trash pull-right" style="padding:10px">삭제</a>
                       
                    </div>
                    <br>

                    <p style="padding:20px">
                        ${posting.contents.contents}
                    </p>

                    <c:if test="${socialBoard.commentable}">
                        <c:if test="${posting.option.commentable}">
                            <table class="table" style="font-size: 13px; padding :20px" id="table_comment">
                                <c:forEach var="comment" items="${posting.contents.commentBag.comments}">
                                    <tr>
                                        <td>
                                            <c:if test="${!posting.option.anonymousComment}"><strong>${comment.writerEmail}</strong>
                                            </c:if>

                                        </td>
                                        <td class="text-right">

                                            <fmt:formatDate value="${comment.writtenTime}" pattern="yyyy-MM-dd"/>
                                            <a class="glyphicon glyphicon-trash" id="${comment.sequence}" href="javascritp:"></a>

                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <p class="txt">${comment.comment}</p>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </c:if>

                </div>
                <c:if test="${socialBoard.commentable}">
                    <c:if test="${posting.option.commentable}">
                        <div class="panel-footer">
                            <div class="write_area">
                                <form >
                                    <div>
                                        <input type="hidden" name="email" id="input_email" value="${loginUser.email}">
                                        <textarea class="input_write_comment" name="comment" id="textarea_comment" placeholder="댓글 쓰기"></textarea>
                                        <a class="comment_submit" id="btn_commnet">전송</a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </c:if>
                </c:if>
            </div>

            <div class="text-center">
                <a href="${ctx}/board/${boardUsid}/posts?page=1">
                    <button type="button" class="btn btn-default">목록</button>
                </a>
            </div>
        </div>
    </div>
	<input type="hidden" name="boardUsid" id="input_boardUsid" value="${boardUsid}"/>
	<input type="hidden" name="postingUsId" id="input_postingUsId" value="${posting.usid}"/>
    <!-- Footer ========================================================================================== -->
    <%@ include file="/WEB-INF/views/layout/footer.jsp" %>
</div>

</body>
</html>