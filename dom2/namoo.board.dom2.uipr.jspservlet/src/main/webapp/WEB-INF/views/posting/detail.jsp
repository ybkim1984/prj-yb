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
                    <li class="active">${socialBoard.name}</li>
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
                        <a href="${ctx}/postings?boardUsid=${board.usid}&page=1" class="list-group-item hidden-xs">${board.name}</a>
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
                         <a href="${ctx}/posting?boardUsid=${boardUsid}&postingUsid=${posting.usid}"
                            class="glyphicon glyphicon-cog pull-right" style="padding:10px">수정</a>
                         <a href="${ctx}/posting/delete?boardUsid=${boardUsid}&postingUsid=${posting.usid}"
                            class="glyphicon glyphicon-trash pull-right" style="padding:10px">삭제</a>
                       
                    </div>
                    <br>

                    <p style="padding:20px">
                        ${posting.contents.contents}
                    </p>

                    <c:if test="${socialBoard.commentable}">
                        <c:if test="${posting.option.commentable}">
                            <table class="table" style="font-size: 13px; padding :20px">
                                <c:forEach var="comment" items="${posting.contents.commentBag.comments}">
                                    <tr>
                                        <td>
                                            <c:if test="${!posting.option.anonymousComment}"><strong>${comment.writerEmail}</strong>
                                            </c:if>

                                        </td>
                                        <td class="text-right">

                                            <fmt:formatDate value="${comment.writtenTime}" pattern="yyyy-MM-dd"/>
                                            <a class="glyphicon glyphicon-trash"
                                               href="${ctx}/posting/comment?boardUsid=${boardUsid}&postingUsid=${posting.usid}&sequence=${comment.sequence}"></a>

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
                                <form action="${ctx}/posting/detail?boardUsid=${boardUsid}&postingUsid=${posting.usid}"
                                      method="post" role="form">
                                    <div>
                                        <input type="hidden" name="email" value="${loginUser.email}">
                                        <textarea class="input_write_comment" name="comment" placeholder="댓글 쓰기"></textarea>
                                        <input type="submit" class="comment_submit" value="전송">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </c:if>
                </c:if>
            </div>

            <div class="text-center">
                <a href="${ctx}/postings?boardUsid=${boardUsid}&page=1">
                    <button type="button" class="btn btn-default">목록</button>
                </a>
            </div>
        </div>
    </div>

    <!-- Footer ========================================================================================== -->
    <%@ include file="/WEB-INF/views/layout/footer.jsp" %>
</div>

</body>
</html>