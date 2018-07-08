<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title><fmt:message key="title.cConfigAlarmType.list"/></title>
<content tag="heading"><fmt:message key="title.cConfigAlarmType.list"/></content>
<div>
	<input id="strWhere" name="strWhere" value="" type="hidden"/>
	<input id="sortfield" name="sortfield" value="" type="hidden"/>
	<input id="sortorder" name="sortorder" value="" type="hidden"/>
</div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post" action="list.htm">
					<table width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td class="wid8 mwid70"><fmt:message key="cConfigAlarmType.vendor"/></td>
							<td class="wid20">
								<select name="vendor" class="wid90" id="vendor">
			           				<option value="">--Tất cả--</option>
			           				<c:forEach var="items" items="${vendorList}">
						              <c:choose>
						                <c:when test="${items.value == vendorCBB}">
						                    <option value="${items.value}" selected="selected">${items.value}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.value}">${items.value}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
			           			</select>
			           		</td>
							<td class="wid12 mwid140"><fmt:message key="cConfigAlarmType.node"/></td>
							<td class="wid20">
								<select name="node" class="wid90" id="node">
			           				<option value="">--Tất cả--</option>
			           				<c:forEach var="items" items="${nodeList}">
						              <c:choose>
						                <c:when test="${items.name == nodeCBB}">
						                    <option value="${items.name}" selected="selected">${items.name}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.name}">${items.name}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
			           			</select>
							</td>
							
							<td class="wid10 mwid110"><fmt:message key="cConfigAlarmType.neType"/></td>
							<td class="wid20">
								<select name="neType" class="wid90" id="neType">
			           				<option value="">--Tất cả--</option>
			           				<c:forEach var="items" items="${neTypeList}">
						              <c:choose>
						                <c:when test="${items.value == neTypeCBB}">
						                    <option value="${items.value}" selected="selected">${items.value}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.value}">${items.value}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
			           			</select>
							</td>	
						</tr>
						<tr>
							<td><fmt:message key="cConfigAlarmType.blockValue"/></td>
							<td>
								<input type="text" id="blockValue" name="blockValue" value="${blockValue}" />
							</td>
							<td><fmt:message key="cConfigAlarmType.alarmMappingName"/></td>
							<td>
								<input type="text" id="alarmMappingName" name="alarmMappingName" value="${alarmMappingName}" />
							</td> 
							<td><fmt:message key="cConfigAlarmType.alarmMappingId"/></td>
							<td>
								<input type="text" id="alarmMappingId" name="alarmMappingId" value="${alarmMappingId}" />
							</td>
							<td></td>
							<td><input id="btSearch" class="button" type="submit" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>			
					</table>
				</form:form>
				</td>
				<td>
				</td> 
		</tr>
		<tr>
			<td colspan="2">
				<%@ include file="/WEB-INF/jspadmin/grid/gridSimple.jsp" %>
			</td>
		</tr>
</table>
<script type="text/javascript">
var theme = getTheme();
$("#btSearch").jqxButton({theme: theme});
$("#blockValue").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#alarmMappingName").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#alarmMappingId").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });

$('#Menu').on('itemclick', function (event) {
	var args = event.args;
	var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
	var row = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
	// add new row
	if ($.trim($(args).text()) == '<fmt:message key="global.button.addNew"/>') {
		
		window.location = 'form.htm';
		}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
		window.location = 'form.htm?id='+row.id;
	}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>') {
			var r=confirm('<fmt:message key="messsage.confirm.delete"/>');
			if (r==true)
			  {
				window.location = 'delete.htm?id='+row.id;
			  }
			return false;
        }
	// export data
	else{
		window.open('${pageContext.request.contextPath}/admin/c-config-alarm-type/exportData.htm?vendor='+"<c:out value='${vendorCBB}'/>"+
	        	'&node='+"<c:out value='${nodeCBB}'/>"+
	        	 '&neType='+"<c:out value='${neTypeCBB}'/>"+
	        	 '&blockValue='+"<c:out value='${blockValue}'/>"+
	        	 '&alarmMappingName='+"<c:out value='${alarmMappingName}'/>"+
	        	 '&alarmMappingId='+"<c:out value='${alarmMappingId}'/>"+
	        	 '&strWhere='+$("#strWhere").val()+
	        	 '&sortfield='+$("#sortfield").val()+
	        	 '&sortorder='+$("#sortorder").val()
	        	 ,'_blank');
	}
	});

function focusIt()
{
  var mytext = document.getElementById("blockValue");
  mytext.focus();
}
onload = focusIt;
</script>
