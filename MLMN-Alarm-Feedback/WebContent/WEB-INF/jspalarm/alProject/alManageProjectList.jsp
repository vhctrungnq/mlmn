<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:choose>
  <c:when test="${type == 'ON_AIR'}">
      <title><fmt:message key="title.alManageProject.onAirList"/></title>
	  <content tag="heading"><fmt:message key="title.alManageProject.onAirList"/></content>
  </c:when>
  <c:otherwise>
  	<title><fmt:message key="title.alManageProject.followList"/></title>
	<content tag="heading"><fmt:message key="title.alManageProject.followList"/></content>
  </c:otherwise>
</c:choose>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post" action="list.htm?type=${type}">
					<table width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
						<td style="width:70px;"><fmt:message key="shiftFes.dept" /></td>
							<td style="width:100px;">
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
							<td style="width:100px;padding-left: 5px;"><fmt:message key="alManageProject.projectCode"/></td>
							<td style="width:150px;">
								<input type="text" id="projectCode" name="projectCode" value="${projectCode}" />
			           		</td>
							<td style="width:100px;padding-left: 5px;"><fmt:message key="alManageProject.projectName"/></td>
							<td style="width:250px;">
								<input type="text" id="projectName" name="projectName" value="${projectName}" />
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
			    	<div style="float: right;margin-bottom:3px;width:180px;">
			        	<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr>
							<c:if test="${checkRegion==true}">
								<td align="right"><input type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' />
			      				</td>
			      			</c:if>
								<td style="width:33px"><div style="float: right;" id="jqxlistbox"></div></td>
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
$("#projectName").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#btSearch").jqxButton({theme: theme});
$('#addNew').jqxButton({ theme: theme });
$('#addNew').click(function () {
	window.location = 'form.htm?type=' + type;
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
		var exportFileName =  "<c:out value='${exportFileName}'/>";
		$("#jqxgrid").jqxGrid('exportdata', 'xls', exportFileName);
	}
	});
	
if(type == 'ON_AIR')	{
	//call view detail    
	$("#jqxgrid").on('cellselect', function (event) 
	{	
	    var columnheader = $("#jqxgrid").jqxGrid('getcolumn', event.args.datafield).text; 
	  	if (columnheader =='Mã dự án')
	    {
	    	var rowindex = event.args.rowindex;
	    	var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
	    	var projectCode = '';
	    	if(row.projectCode!=null)
	    	{
	    		projectCode=row.projectCode;
	    	}
	    	
	    	window.open('${pageContext.request.contextPath}/alarm/al-manage-on-air/list.htm?projectCode=' + projectCode,'_blank');
	    }
	    
	});
}

if(type == 'FOLLOW')	{
	//call view detail    
	$("#jqxgrid").on('cellselect', function (event) 
	{	
	    var columnheader = $("#jqxgrid").jqxGrid('getcolumn', event.args.datafield).text; 
	  	if (columnheader =='Mã dự án')
	    {
	    	var rowindex = event.args.rowindex;
	    	var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
	    	var projectCode = '';
	    	if(row.projectCode!=null)
	    	{
	    		projectCode=row.projectCode;
	    	}
	    	
	    	window.open('${pageContext.request.contextPath}/alarm/al-manage-track/list.htm?type='+row.projectType+'&projectCode=' + projectCode,'_blank');
	    }
	    
	});
}

function focusIt()
{
  var mytext = document.getElementById("projectCode");
  mytext.focus();
}
onload = focusIt;
</script>