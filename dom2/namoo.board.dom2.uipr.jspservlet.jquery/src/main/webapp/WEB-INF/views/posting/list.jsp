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
                            <td>
                                <a href="${ctx}/posting/detail?boardUsid=${socialBoard.usid}&postingUsid=${posting.usid}">${posting.title}</a>
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
                    
                    <c:if test="${postings.totalPageCount > 1}">
                        <li><a href="${ctx}/postings?boardUsid=${boardUsid}&page=1">«</a></li>
                       
                        <c:if test="${postings.navigateStartPage > 1}">
                           <li><a href="${ctx}/postings?boardUsid=${boardUsid}&page=${postings.navigateStartPage -1}">&lt;</a></li>
                        </c:if>   
                    </c:if>
                    
                    <c:forEach var="i" begin="${postings.navigateStartPage}"
                               end="${postings.navigateStartPage + postings.navigatePageCount - 1}" step="1">
                        <li ${postings.page eq i ? 'class="active"' : ''} >
                            <a href="${ctx}/postings?boardUsid=${boardUsid}&page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                    
                    <c:if test="${postings.totalPageCount > 1}">
                        <c:if test="${postings.totalPageCount >= postings.navigateStartPage + postings.navigatePageCount}">
                            <li><a href="${ctx}/postings?boardUsid=${boardUsid}&page=${postings.navigateStartPage + postings.navigatePageCount}">&gt;</a></li>
                        </c:if>
                        <li><a href="${ctx}/postings?boardUsid=${boardUsid}&page=${postings.totalPageCount}">»</a></li>
                    </c:if>
                  
                </ul>
            </div>
           
            <div class="text-right">
                <a href="${ctx}/posting/create?boardUsid=${socialBoard.usid}">
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