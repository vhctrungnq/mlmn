<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
	<title>[mlmn] <decorator:title /></title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-ui-1.8.6.custom.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/redmond/jquery-ui-1.8.5.custom.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/nav_menu.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/messages.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/default.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/common.css" />

</head>

<body>
	<div id="contents1">
		<h1><decorator:getProperty property="page.heading"/></h1>
		<%@ include file="/commons/messages.jsp"%>
		<decorator:body />
		<div class="clear"></div>
	</div>
</body>

</html>