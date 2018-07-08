<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>[mlmn_sts] <decorator:title/></title>
		
		<script type="text/javascript" src="/MLMN-Statistics/scripts/jquery-ui-1.8.6.custom.min.js"></script>

		<link type="text/css" href="/MLMN-Statistics/styles/menu.css" rel="stylesheet" />
	    <script type="text/javascript" src="/MLMN-Statistics/scripts/jquery.js"></script>
	    <script type="text/javascript" src="/MLMN-Statistics/scripts/menu.js"></script>
        <link rel="stylesheet" type="text/css" href="/MLMN-Statistics/styles/default.css" />
        <link rel="stylesheet" type="text/css" href="/MLMN-Statistics/styles/nav-h.css" />
        <link rel="stylesheet" type="text/css" href="/MLMN-Statistics/styles/messages.css" />		
		<link rel="stylesheet" type="text/css" href="/MLMN-Statistics/styles/redmond/jquery-ui-1.8.5.custom.css" />
	</head>
	<body>
		<div id="header" >
			<a href="/MLMN-Statistics" style="margin-left:20px;"><img src="/MLMN-web/images/Mobifone_Logo.png" alt="vms" vspace="10" width="300" height="100" border="0"></a>
			<div align="right"><a href="/MLMN-web" style="margin-right:20px; color: #FFFFF"><font color="white">Back to Others</font></a></div>
		</div>
		
		<div id="menu1">
			<%@ include	file="/commons/menu.jsp"%>
		</div>
		
		<div id="contents">
			  <h1><decorator:getProperty property="page.heading"/></h1>
			  <%@ include file="/commons/messages.jsp" %>
			  <decorator:body/>
		</div>
		
		<p class="small" style="margin-top: 5px;">&copy; 2012-2013 <a href="http://www.mobifone.com.vn/" target="_blank">VMS</a></p>
	</body>
</html>