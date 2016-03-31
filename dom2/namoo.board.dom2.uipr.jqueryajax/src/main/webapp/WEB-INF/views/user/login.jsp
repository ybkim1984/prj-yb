<!-- 
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:yongjunlee@nextree.co.kr">Lee, Yongjun</a>
 * @since 2015. 1. 22.
 *
 * @title Login JSP
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/layout/common.jsp" %>
    <style type="text/css">
        body {
            padding-top: 100px;
            padding-bottom: 40px;
            background-color: #ecf0f1;
        }
        .login-header {
            max-width: 400px;
            padding: 15px 29px 25px;
            margin: 0 auto;
            background-color: #2c3e50;
            color: white;
            text-align: center;
            -webkit-border-radius: 15px 15px 0px 0px;
            -moz-border-radius: 15px 15px 0px 0px;
            border-radius: 15px 15px 0px 0px;
            -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            box-shadow: 0 1px 2px rgba(0,0,0,.05);
        }
        .login-footer {
            max-width: 400px;
            margin: 0 auto 20px;
            padding-left: 10px;
        }
        .form-signin {
            max-width: 400px;
            padding: 29px;
            margin: 0 auto 20px;
            background-color: #fff;
            -webkit-border-radius: 0px 0px 15px 15px;
            -moz-border-radius: 0px 0px 15px 15px;
            border-radius: 0px 0px 15px 15px;
            -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            box-shadow: 0 1px 2px rgba(0,0,0,.05);
        }
        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 15px;
        }
        .form-signin input[type="text"],
        .form-signin input[type="password"] {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
        }
        .form-btn {
            text-align: center;
            padding-top: 20px;
        }

    </style>
    
	<script src="${ctx}/resources/user/login_ui.js"></script>
    <script src="${ctx}/resources/user/login_pres.js"></script>
    
</head>

<body>
<div class="container">

    <!-- header -->
    <div class="login-header">
        <h2 class="form-signin-heading">나무 보드</h2>
    </div>

    <div class="form-signin">
        <input id="input_Email" type="text" class="form-control" placeholder="아이디(이메일)" required>
        <input id="input_Password" type="password" class="form-control" placeholder="비밀번호" required>
        
        <div class="row form-btn">
            <button id ="btn_login" class="btn btn-large btn-warning">로그인</button>
            <button id ="btn_join" class="btn btn-large btn-default">회원가입</button>
        </div>
    </div>

    <!-- footer -->
    <div class="login-footer">
        <p>© NamooSori 2015.</p>
    </div>
</div>
</body>
</html>