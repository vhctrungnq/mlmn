<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<head>

<title>[mlmn] <decorator:title /></title>

<link type="text/css" href="${pageContext.request.contextPath}/styles/menu.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/menu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-ui-1.8.6.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/default.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/nav-h.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/messages.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/redmond/jquery-ui-1.8.5.custom.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/common.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>

</head>
<body>
	
		<div id="header">
			<div style="float:left">
			<a href="/mlmn_web/welcome.htm"><img src="${pageContext.request.contextPath}/images/logo.jpg" alt="vms" vspace="10" width="200" height="60" border="0"></a>
			</div>
			
			<div style="float:right;">
			<c:if test="${not empty globalCaTruc}">
	   
   				<table style= "width: 100%; font-size: 13px; color: white; line-height: 12px; font-family: tahoma; padding-top: 7px;opacity: 0.5;">
					<tr>
						<td  style= "width: 20%;" >Ca trực:</td>
						<td>${globalCaTruc.nhanCaStr}, ${globalCaTruc.nhanDateStr}</td>
					</tr>
					<tr>
						<td>Ca trưởng:</td>
						<td>${globalCaTruc.nhanUsername}</td>
					</tr>
					<tr>
						<td>Ca viên:</td>
						<td>${globalCaTruc.nhanCaVien}</td>
					</tr>
				</table>
			</c:if>
			
			</div>
			<div style="clear:both;"></div>
			
		</div>
		<div id="menu">
		<%@ include file="/includes/header.jsp"%>
		</div>
		<div id="contents"   >
			<h1><decorator:getProperty property="page.heading"/></h1>
			<%@ include file="/commons/messages.jsp"%>
			<decorator:body />
			<div class="clear"></div>
		</div>
						
		<%@ include file="/includes/footer.jsp"%>
</body>
</html>
