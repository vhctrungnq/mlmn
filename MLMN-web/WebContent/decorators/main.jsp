<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
   prefix="decorator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Welcome</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/interface.js"></script>
	<link href="${pageContext.request.contextPath}/styles/style_dock.css" rel="stylesheet" type="text/css" />
</head>
<body>
      <div id="container">
		<div id="header">
			<%@ include file="/includes/header.jsp"%>
		</div>
		<div id="content">
			<decorator:body />
		</div>
		<div id="footer">
		<%@ include file="/includes/footer.jsp"%>
		</div>
   </div>
</body>
</html>