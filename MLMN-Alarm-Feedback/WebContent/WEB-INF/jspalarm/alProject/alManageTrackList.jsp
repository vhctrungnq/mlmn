<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:choose>
	<c:when test="${type == 'FOLLOW'}">
		<title><fmt:message key="title.alManageProject.trackList"/></title>
		<content tag="heading"><fmt:message key="title.alManageProject.trackList"/></content>
	</c:when>
	<c:when test="${type == 'UPGRADE'}">
		<title><fmt:message key="Dự án nâng cấp phần mềm"/></title>
		<content tag="heading"><fmt:message key="Dự án nâng cấp phần mềm"/></content>
	</c:when>
</c:choose>
<div>
	<input id="strWhere" name="strWhere" value="" type="hidden"/>
	<input id="sortfield" name="sortfield" value="" type="hidden"/>
	<input id="sortorder" name="sortorder" value="" type="hidden"/>
 
</div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post" action="list.htm?type=${type}">
					<table width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td class="pdl15"><fmt:message key="shiftFes.dept" /></td>
							<td class="pdl10">
								<%-- <form:input type="text" value="${dept}" path ="dept" class="wid70"/> --%>
								<select id="region" name = "region">
					        		<option value=""></option>
				   					<c:forEach var="items" items="${regionList}">
						              <c:choose>
						                <c:when test="${items.name == region}">
						                    <option value="${items.name}" selected="selected">${items.value}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.name}">${items.value}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
				           		</select>	
							</td>
							<td class="wid8 mwid90">
								<fmt:message key="alManageOnAir.projectCode"/>
							</td>
							<td class="wid15">
								<input type="text" id="projectCode" name="projectCode" value="${projectCode}" />
			           		</td>
							<td class="wid8 mwid70">
								<fmt:message key="alManageProject.siteName"/>
							</td>
							<td class="wid20">
								<input type="text" id="siteName" name="siteName" value="${siteName}" />
			           		</td>
							<td class="wid8 mwid70"><fmt:message key="alManageProject.node"/></td>
							<td class="wid20">
								<input type="text" id="node" name="node" value="${node}" />
							</td>
							<td><input class="button" id="btSearch" type="submit" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>			
					</table>
				</form:form>
				</td>
				<td class="wid6">
				</td> 
		</tr>
		<tr>
			<td colspan="2">
				<div id='jqxWidget' style="margin-top:5px;">
			    	<div style="float: right;margin-bottom:3px;width:200px;">
			        	<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr>
							<c:if test="${checkRegion==true}">
								<td align="right"><input type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' />&nbsp;
			      					<input type="button" value="<fmt:message key="global.button.import"/>" id='importFile' /></td>
							</c:if>
								<td><div style="float: right;" id="jqxlistbox"></div></td>
							</tr>
						</table>
			        </div><br>
			    	
			        <div id="jqxgrid"></div>
			        <div id='Menu'>
			            <ul>
			            <c:if test="${checkRegion==true}">
							<li><fmt:message key="global.button.addNew"/></li>
				            <li><fmt:message key="global.button.editSelected"/></li>
					   		<li><fmt:message key="global.button.deleteSelected"/></li>
					  	 </c:if>
					   		<li><fmt:message key="global.button.exportExcel"/></li>
				        </ul>
			       </div>
			    </div>
			</td>
		</tr>
</table>
<script type="text/javascript">
var theme = getTheme();

${gridManage}
var type =  "<c:out value='${type}'/>"; 
$("#projectCode").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#siteName").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#node").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#btSearch").jqxButton({theme: theme});
$('#addNew').jqxButton({ theme: theme });
$('#addNew').click(function () {
	window.location = 'form.htm?type=' + type;
});
$('#importFile').jqxButton({ theme: theme });
$('#importFile').click(function () {
	window.location = 'upload.htm?type=' + type;
});
$('#Menu').on('itemclick', function (event) {
	var args = event.args;
	var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
	var row = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
	// add new row
	if ($.trim($(args).text()) == '<fmt:message key="global.button.addNew"/>') {
		window.location = 'form.htm?type=' + type;
		}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
		window.location = 'form.htm?type=' + type + '&id='+row.id;
	}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>') {
			var r=confirm('<fmt:message key="messsage.confirm.delete"/>');
			if (r==true)
			  {
				window.location = 'delete.htm?type=' + type + '&id='+row.id;
			  }
			return false;
        }
	// export data
	else{
		window.open('${pageContext.request.contextPath}/alarm/al-manage-track/exportData.htm?siteName='+$("#siteName").val()+
	        	'&node='+$("#node").val()+
	        	'&projectCode='+$("#projectCode").val()+
	        	'&type='+"<c:out value='${type}'/>"+
	        	'&region='+$("#region").val()+
	        	 '&strWhere='+$("#strWhere").val()+
	        	 '&sortfield='+$("#sortfield").val()+
	        	 '&sortorder='+$("#sortorder").val()
	        	 ,'_blank');
	}
});


function focusIt()
{
  var mytext = document.getElementById("projectCode");
  mytext.focus();
}
onload = focusIt;
</script>