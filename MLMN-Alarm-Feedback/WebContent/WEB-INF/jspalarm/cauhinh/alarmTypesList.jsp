
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<title>${title}</title>
<content tag="heading">${title}</content> 	

<div>
<form:form commandName="filter" method="post"	action="list.htm">
<form:hidden path="id"/>
	<table >
			<tr> 
				<td style="width:50px;"><fmt:message key="alAlarmTypes.vendor"/></td>
				<td>
					<form:input path="vendor" class="wid100"/>
				</td>			
				<td style="width:50px; padding-left:5px;"><fmt:message key="alAlarmTypes.neType"/></td>
				<td>
					<form:input path="neType" class="wid100"/>
				</td>
				<td style="width:50px; padding-left:5px;"><fmt:message key="alAlarmTypes.alarmName"/></td>
				<td>
					<form:input path="alarmName" class="wid100"/>
				</td>
				<td style="width:50px; padding-left:5px;"><fmt:message key="alAlarmTypes.meaning"/></td>
				<td>
					<form:input path="meaning" class="wid100"/>
				</td>
				
			</tr>
			<tr>
				<td style="width:50px;"><fmt:message key="alAlarmTypes.alarmType"/></td>
				<td>
					<form:input path="alarmType" class="wid100"/>
				</td>
				<td style="width:50px; padding-left:5px;"><fmt:message key="alAlarmTypes.alarmMapingId"/></td>
				<td>
					<form:input path="alarmMapingId" class="wid100"/>
				</td>
				<td style="width:50px; padding-left:5px;"><fmt:message key="alAlarmTypes.alarmMapingName"/></td>
				<td>
					<form:input path="alarmMapingName" class="wid100"/>
				</td>
				<td style="padding-left:5px;" colspan="2">
					<input class="button" type="submit" class="button" value="<fmt:message key="button.search"/>" />
				</td>
			</tr>
		</table>
	</form:form>
</div>
<div class="ftsize12" align="right" style="margin-top:5px"> 
       <a href="form.htm"><fmt:message key="button.add"/></a>
       <a href="upload.htm"><fmt:message key="button.upload"/></a>
</div>
	 
 <div id="doublescroll" >
	<display:table name="${alarmTypesList}" class="simple2" id="item" requestURI="" pagesize="15" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="vendor" titleKey="alAlarmTypes.vendor" sortable="true" sortName="VENDOR"  style="min-width:120px;max-width:120px;"/>
	  	<display:column property="neType"  titleKey="alAlarmTypes.neType" sortable="true" sortName="NE_TYPE"  style="min-width:80px;max-width:80px;"/>
		<display:column property="alarmName"  titleKey="alAlarmTypes.alarmName" sortable="true" sortName="ALARM_NAME" style="min-width:40px;max-width:40px;"/>
		<display:column property="alarmId"  titleKey="alAlarmTypes.alarmId" sortable="true" sortName="ALARM_ID" style="min-width:100px;"/>
		<display:column property="meaning"  titleKey="alAlarmTypes.meaning" sortable="true" sortName="MEANING" style="min-width:100px;" />
		<display:column property="alarmMapingId" titleKey="alAlarmTypes.alarmMapingId" sortable="true" sortName="ALARM_MAPING_ID" style="min-width:100px;"/>
		<display:column property="alarmMapingName" titleKey="alAlarmTypes.alarmMapingName" sortable="true" sortName="ALARM_MAPING_NAME" style="min-width:100px;"/>
		<display:column property="alarmType" titleKey="alAlarmTypes.alarmType" sortable="true" sortName="ALARM_TYPE" style="min-width:100px;"/>
		<display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
			<a href="form.htm?id=${item.id}"><fmt:message key="button.edit"/></a>&nbsp;
  			<a href="delete.htm?id=${item.id}"
			onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="button.delete"/></a>&nbsp;
  			</display:column>
	   	<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
	</div>
	
<script type="text/javascript">

$("#vendor").autocomplete({
	source: '/VMSC2-Alarm-Feedback/alarmTypes/ajax/get-inf-search.htm?focus=vendor',
});
$("#neType").autocomplete({
	source: '/VMSC2-Alarm-Feedback/alarmTypes/ajax/get-inf-search.htm?focus=neType',
});
$("#alarmName").autocomplete({
	source: '/VMSC2-Alarm-Feedback/alarmTypes/ajax/get-inf-search.htm?focus=alarmName',
});
$("#alarmMapingId").autocomplete({
	source: '/VMSC2-Alarm-Feedback/alarmTypes/ajax/get-inf-search.htm?focus=alarmMapingId',
});
$("#meaning").autocomplete({
	source: '/VMSC2-Alarm-Feedback/alarmTypes/ajax/get-inf-search.htm?focus=meaning',
});
$("#alarmMapingName").autocomplete({
	source: '/VMSC2-Alarm-Feedback/alarmTypes/ajax/get-inf-search.htm?focus=alarmMapingName',
});
$("#alarmType").autocomplete({
	source: '/VMSC2-Alarm-Feedback/alarmTypes/ajax/get-inf-search.htm?focus=alarmType',
});

</script>