<!-- 
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 1. 9.
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${ctx}/home">소셜보드</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li ><a href="${ctx}/home">홈</a></li>
                <li ><a href="${ctx}/boards">게시판 관리</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${ctx}/logout">로그아웃 [${loginUser.name}]</a></li>
                
            </ul>
        </div>
    </div>
</div>