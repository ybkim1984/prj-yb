<!-- 
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kizellee@nextree.co.kr">kizellee</a>
 * @since 2015. 2. 06.
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${ctx}/resources/layout/menu_ui.js"></script>
<div class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${ctx}/post">소셜보드</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li ><a id="menu_home" href="javascript:">홈</a></li>
                <li ><a id="menu_boards" href="javascript:">게시판 관리</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a id="menu_logout" href="javascript:">로그아웃 [${loginUser.name}]</a></li>
            </ul>
        </div>
    </div>
</div>