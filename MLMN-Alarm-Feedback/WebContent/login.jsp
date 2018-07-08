<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Basics -->
	<title>Login</title>
 
	
	
<body>
	<!-- CSS -->
	<link rel="stylesheet" href="styles/login_form/css/reset.css">
	<link rel="stylesheet" href="styles/login_form/css/animate.css">
	<link rel="stylesheet" href="styles/login_form/css/styles.css">
	

	<!-- Main HTML -->
	<!-- Begin Page Content -->
	
	<div id="container" >
		
		<form id="loginForm" name="loginForm" action="j_spring_security_check" method="post">
		<p><a class = "title" href="#">
					   Login ${moduleC2}</a>
					<c:if test="${not empty param.authfailed}">
					    <p><a>Wrong Username or Password</a>
					</c:if>
					
					<c:if test="${not empty param.loggedout}">
					    <p><a href="#">
					    You have been successfully logged out.</a>
					    
					</c:if>
					
					<c:if test="${not empty errorFalseModule}">
					   <p><a href="#">
					    You don't roles.</a>
					    
					</c:if>
					
					<c:if test="${not empty noticeLogin}">
					   <p><a href="#">
					    Login failed!</a>
					  
					</c:if>
		<label for="name">Username</label>
		
		<input type="name" name="j_username" id="j_username">
		
		<label for="username">Password</label>
		
		
		<input type="password" name="j_password" id="j_password">
		<img style = "padding-left: 265px" width="22" height="22" alt="" src="/mlmn/images/home.png">
		<a href = "/mlmn_web/welcome.htm" style="padding-top:10px;">Home</a>
		<div id="lower">
		
		 <input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" value="true"/>
		 <label class="check" for="_spring_security_remember_me"  value="true">Keep me logged in</label>
		
		<input name="submit" id="submit" type="submit" value="Login">
		
		</div>
		
		</form>
		
	</div>
	
	
	<!-- End Page Content -->
	
</body> 