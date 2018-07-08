<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
  <style type="text/css">
  
  .styleArea{
  	width:135px !important;
  }
  .stylestylePhone{
  	min-width:150px !important;
  	max-width:200px !important;
  }
    
	#doublescroll { overflow: auto; overflow-y: hidden; }   
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
     #dvtTeamProcess {
     visibility: visible !important;
    }
   
    #acLow {
     visibility: visible !important;
    }
    
     #bscid_chzn {

		width: 220px !important;
	}
    .ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	* we use height instead, but this forces the menu to always be this tall
	*/
	* html .ui-autocomplete {
		height: 200px;
	}
</style>
<title>${title}</title>
<content tag="heading">${title}</content><div>

</div>
<br>
<%
        // you can do this as a scriptlet on the page, but i put it into a taglib...
        org.displaytag.decorator.MultilevelTotalTableDecorator subtotals = new org.displaytag.decorator.MultilevelTotalTableDecorator();
        subtotals.setGrandTotalDescription("&nbsp;");    // optional, defaults to Grand Total
        subtotals.setSubtotalLabel("&nbsp;", null);
        pageContext.getRequest().setAttribute("subtotaler", subtotals);
%>
<div style="overflow: auto;">
	<display:table name="${vAlShifttemp}" class="simple2" id="vAlShifttemp" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true" >
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px !important;"> <c:out value="${vAlShifttemp_rowNum}" /> </display:column>
	  	<display:column property="session" titleKey="alshiptcalendar.session" sortable="true" sortName="SESSION_SHIFT"  group="1"/>
		<display:column property="thu2" titleKey="alshiptcalendar.thu2" sortable="true" sortName="THU_2" class="styleArea" />
	  	<display:column property="thu3" titleKey="alshiptcalendar.thu3" sortable="true" sortName="THU_3" class="styleArea" />
	  	<display:column property="thu4" titleKey="alshiptcalendar.thu4" sortable="true" sortName="THU_4" class="styleArea" />
		<display:column property="thu5" titleKey="alshiptcalendar.thu5" sortable="true" sortName="THU_5" class="styleArea" />
		<display:column property="thu6" titleKey="alshiptcalendar.thu6"  sortable="true" sortName="THU_6" class="styleArea" />
		<display:column property="thu7" titleKey="alshiptcalendar.thu7" sortable="true" sortName="THU_7" class="styleArea" />
		<display:column property="cn" titleKey="alshiptcalendar.cn" sortable="true" sortName="CN" class="styleArea" />
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>
<form:form  method="post" action="formduyet.htm">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">

	<table style = "width:100%">
		<tr>
			<td align="right" >
				<input  style="width:70px" class="button" type="submit" id="submit" value="<fmt:message key="Duyệt"/>"/>
				<input  style="width:70px" class="button" onClick='window.location="list.htm"' value="<fmt:message key="Quay lại"/>"/>
	        </td>
		</tr>
	</table>
</form:form>
<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}
</script>