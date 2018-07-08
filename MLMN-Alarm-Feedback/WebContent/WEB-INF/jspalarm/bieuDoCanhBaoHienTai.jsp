<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleSystem}</title>
<content tag="heading">${titleSystem}</content>

<form:form id="filterController" method="post" action="list.htm">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
			<tr> 
				<td class="wid50">	
				</td>
				<td class="wid50">	
				</td> 
			</tr>
			${html}
	</table>
</form:form>

<script src="${pageContext.request.contextPath}/scripts/amcharts.js" type="text/javascript"></script>

${chartdivScript}

<script type="text/javascript">

$(document).ready(function(){
	setTimeout(function(){
		$('#filterController').submit();
	}, 
	60 * 1000);
});

</script>
