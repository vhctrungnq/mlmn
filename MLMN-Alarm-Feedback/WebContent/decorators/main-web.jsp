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
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/styles/jqx.base.css" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/scripts/jquery-1.10.2.min.js"></script>
     <%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/scripts/jquery-1.11.1.min.js"></script> --%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtooltip.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxgrid.selection.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxgrid.columnsresize.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxdata.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxgrid.export.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxdata.export.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxgrid.sort.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/scripts/gettheme.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxgrid.pager.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxgrid.filter.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxgrid.columnsreorder.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxdropdownlist.js"></script> 
   	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxlistbox.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxnumberinput.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxinput.js"></script>                            
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxdatetimeinput.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxcalendar.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxpanel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxgrid.storage.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxgrid.grouping.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxgrid.aggregates.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxcombobox.js"></script>
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/styles/jqx.classic.css" type="text/css" />
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtabs.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxchart.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtabs.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxgrid.aggregates.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxgrid.storage.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/globalization/globalize.js"></script>
    <%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxdatatable.js"></script>  --%>
    
       <script type="text/javascript">
            $(document).ready(function () {
            	var theme = getTheme();
             	// Create a jqxMenu
                $("#jqxMenu").jqxMenu({ width: '100%', height: '27px', theme: theme });
                $("#jqxMenu").css('visibility', 'visible');
            });
    </script>
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
		
		<div style="float:right;">
			<c:if test="${not empty globalCaTruc}">
		 		<table style= "width: 100%; font-size: 13px; color: white; line-height: 12px; font-family: tahoma; padding-top: 7px;opacity: 0.5;">
					<tr>
						<td style= "width: 80px;">Ca trực:</td>
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
	
	<div id="contents">
		<h1><decorator:getProperty property="page.heading"/></h1>
		<%@ include file="/commons/messages.jsp"%>
		<decorator:body />
		<div class="clear"></div>
	</div>
	
	<%@ include file="/includes/footer.jsp"%>
</body>

</html>