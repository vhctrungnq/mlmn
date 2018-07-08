<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title></title>
<center><h1 tag="heading">${titleAlarmType}</h1></center>
<center><h2>NGÀY ${startDateFull}</h2></center>

<c:set var="quang" value="${count + 1}" scope="page"/>
<form:form id="filterController" method="post" action="${function}.htm?count=${quang}">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
					${html}
	</table>
</form:form>


${chartdivScript}

<script type="text/javascript">
$(document).ready(function(){
	setTimeout(function(){
		$('#filterController').submit();
	}, 
	10 * 1000);
});

</script>