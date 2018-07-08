<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>[mlmn_sts] <decorator:title/></title>
		
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/default.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/messages.css" />		
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/redmond/jquery-ui-1.8.5.custom.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/common.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-ui-1.8.6.custom.min.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/nav-h.css" />
	</head>
	<body>
		<div id="header" class="rounded-corners">
			<div style="float:left">
			<h1>
				<a href="/mlmn_web/welcome.htm">
					<img src="${pageContext.request.contextPath}/images/logo.png" alt="vms" vspace="10" border="0" />
				</a>
			</h1>
			
		</div>
		<div style="clear:both;"></div>
		</div>
		
		 <div id = "menu" style="z-index: 1;position: relative;">
			<%@ include	file="/commons/menu.jsp"%>
		</div>
		
		<div id="contents">
			  <h1><decorator:getProperty property="page.heading"/></h1>
			  <%@ include file="/commons/messages.jsp" %>
			  <decorator:body/>
		</div>
		
		<p class="small" style="margin-top: 5px;">&copy; 2013-2014 <a href="http://www.vhc.com.vn/" target="_blank">VMS</a></p>
	</body>
</html>