<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">
	.ie8{display:none}
</style>
<!--[if lte IE 8]>
    <style type="text/css">
		.ie8{display:block}
		.diagram{display:none}
    </style>
<![endif]-->
    
<title>pscore diagram</title>
<content tag="heading">Sơ đồ kết nối mạng PSCORE trung tâm 2</content>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left">
            <form method="get" action="pscore.htm">
                Ngày <input value="<fmt:formatDate pattern="dd/MM/yyyy" value="${day}"/>" name="day" id="day" size="10" maxlength="10">
                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
            </form>
        </td>
        <td align="right">
            <a href="${pageContext.request.contextPath}/exportSvg.htm?type=image/jpeg&svg=${fileName}&isSvgFileName=true&filename=${fileName}">export image</a>
        </td>
    </tr>
</table>

<div class="ie8">
	Cài đặt <a href="http://www.adobe.com/svg/viewer/install/">Adobe Svg Viewer</a> để xem sơ đồ logic. 
</div>
<div class="diagram">
	<object data="${pageContext.request.contextPath}/data/${fileName}" type="image/svg+xml" height="1000" width="1300">
	  <embed src="${pageContext.request.contextPath}/data/${fileName}" type="image/svg+xml" height="1000" width="1300" pluginspage="http://www.adobe.com/svg/viewer/install/" />
	</object>
</div>

<script type="text/javascript">
	$(function() {
		$( "#day" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
	});
</script>
