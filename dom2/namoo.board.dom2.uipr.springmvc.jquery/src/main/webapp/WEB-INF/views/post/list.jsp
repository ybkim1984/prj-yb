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
                    <h2>게시판</h2>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12 col-lg-12">
                <ol class="breadcrumb">
                    <li><a href="${ctx}/home">홈</a></li>
                    <li><a href="${ctx}/home">게시판</a></li>
                    <li><a href="${ctx}/board/${boardUsid}/posts?page=1" class="active">${socialBoard.name}</a>
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
                    <h4>게시판</h4>
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
            
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <colgroup>
                        <col width="100"/>
                        <col width="*"/>
                        <col width="120"/>
                        <col width="70"/>

                        <col width="50"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th class="text-center">번호</th>
                        <th class="text-center">제목</th>
                        <th class="text-center">작성일</th>
                        <th class="text-center">작성자</th>
                        <th class="text-center">조회</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${postings.results.size() < 1}">
                        <tr>
                            <th colspan="5" class="text-center">게시물이 존재하지 않습니다.</th>
                        </tr>
                    </c:if>
                    <c:forEach var="posting" items="${postings.results}">
                        <tr>
                            <td class="text-center">${posting.usid}</td>
                            <td><a href="${ctx}/board/${socialBoard.usid}/post/${posting.usid}">${posting.title}</a>
                            </td>
                            <td class="text-center">
                                <fmt:formatDate value="${posting.registerDate}" pattern="yyyy-MM-dd"/>
                            </td>
                            <td class="text-center">${posting.writerName}</td>
                            <td class="text-center">${posting.readCount}</td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>

           <!-- Pagination -->
            <div class="text-center">
                <ul class="pagination">
                	
                	
                    <li><a href="${ctx}/board/${boardUsid}/posts?page=1">«</a></li>
                    <c:if test="${postings.getNavigateStartPage() ne 1}">
                    <li><a href="${ctx}/board/${boardUsid}/posts?page=${postings.getNavigateStartPage() -1}">&lt;</a></li>
                    </c:if>
                  
                    <c:forEach var="i" begin="${postings.getNavigateStartPage()}"
                               end="${postings.getNavigateStartPage() + postings.getNavigatePageCount() - 1}" step="1">
                      <c:choose>     
	                      <c:when test="${postings.getPage() eq i }">   
	                      	<li class="active"><a href="${ctx}/board/${boardUsid}/posts?page=${i}">${i}</a></li>
	                      </c:when>
	                      <c:otherwise>
	                     	 <li><a href="${ctx}/board/${boardUsid}/posts?page=${i}">${i}</a></li>
	                      </c:otherwise>
                      </c:choose> 	
                    </c:forEach>
                    
                    <c:if test="${postings.getTotalPageCount() > 1}">
                    <c:if test="${postings.getTotalPageCount() > postings.getNavigateStartPage() + postings.getNavigatePageCount()}">
                    <li><a href="${ctx}/board/${boardUsid}/posts?page=${postings.getNavigateStartPage() + postings.getNavigatePageCount()}">&gt;</a></li>
                    </c:if>
                    <li><a href="${ctx}/board/${boardUsid}/posts?page=${postings.getTotalPageCount()}">»</a></li>
                    </c:if>
                  
                </ul>
            </div>
           
            <div class="text-right">
                <a href="${ctx}/board/${socialBoard.usid}/post/create">
                    <button type="button" class="btn btn btn-warning">등록</button>
                </a>
            </div>
        </div>
    </div>

<!-- Footer ========================================================================================== -->
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
</div>

</body>
</html>