<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>
<title><fmt:message key="title.cConfigAlarmType.formUpload"/></title>
<content tag="heading"><fmt:message key="title.cConfigAlarmType.formUpload"/></content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
	<table class="simple2">
		<tr>
			<td class="wid10 mwid140"><b><fmt:message key="qLNguoiDung.file"/></b></td>
			<td><input class="button" type="file" name="file" size="90"/>&nbsp;
			<input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.button.import"/>"/></td>
		</tr>
		<tr>
				<td>
				<b><fmt:message key="qLNguoiDung.dinhDangFile"/></b>
				</td>
				<td>
					<ul style="list-style-type: none;">
						<li>File import là file (.xls)</li>
						<li>Dữ liệu trong file có dạng: 
							<code>
							&lt;<fmt:message key="cConfigAlarmType.vendor"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="cConfigAlarmType.node"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="cConfigAlarmType.neType"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="cConfigAlarmType.blockColumnName"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="cConfigAlarmType.blockValue"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="cConfigAlarmType.description"/>&gt;, 
							&lt;<fmt:message key="cConfigAlarmType.alarmMappingName"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="cConfigAlarmType.alarmMappingId"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="cConfigAlarmType.alarmType"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="cConfigAlarmType.isMonitor"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="cConfigAlarmType.isSendSms"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="cConfigAlarmType.isEnable"/>&gt;<font color="red">(*)</font>,
							&lt;<fmt:message key="cConfigAlarmType.ordering"/>&gt;,
							&lt;<fmt:message key="cConfigAlarmType.rawTable"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="cConfigAlarmType.summaryType"/>&gt;,
							&lt;<fmt:message key="cConfigAlarmType.search"/>&gt;<font color="red">(*)</font>,
							&lt;<fmt:message key="cConfigAlarmType.alarmInfoColumnName"/>&gt;,
							&lt;<fmt:message key="cConfigAlarmType.alarmInfoValue"/>&gt;,
							&lt;<fmt:message key="cConfigAlarmType.brandname"/>&gt;,
							&lt;<fmt:message key="cConfigAlarmType.isSoundAlarm"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="ConfigAlarmTypeExample" href="${pageContext.request.contextPath}/upload/example/ConfigAlarmTypeExample.xls">ConfigAlarmTypeExample.xls</a>
						</li>
						<li>Chú ý:</li>
						<li>&nbsp;&nbsp;- Những thông tin được đánh dấu <font color="red">(*)</font> là thông tin nhập liệu bắt buộc. </li>
					</ul>
			</td>
		</tr>
	</table>
	<c:if test="${status != null}">
    	<div class="error">${status} ${statusExists}</div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu cấu hình cảnh báo không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
							<display:column class="centerColumnMana" title="STT" > <c:out value="${item1_rowNum}"/> </display:column>
							<display:column property="vendor" titleKey="cConfigAlarmType.vendor" class="NOT_NULL"/>
							<display:column property="node" titleKey="cConfigAlarmType.node" class="NOT_NULL"/>  
							<display:column property="neType" titleKey="cConfigAlarmType.neType" class="NOT_NULL"/>
							<display:column property="blockColumnName" titleKey="cConfigAlarmType.blockColumnName" class="NOT_NULL"/>
							<display:column property="blockValue" titleKey="cConfigAlarmType.blockValue" class="NOT_NULL"/>
							<display:column property="description" titleKey="cConfigAlarmType.description"/>
							<display:column property="alarmMappingName" titleKey="cConfigAlarmType.alarmMappingName" class="NOT_NULL"/>
							<display:column property="alarmMappingId" titleKey="cConfigAlarmType.alarmMappingId" class="NOT_NULL"/>
							<display:column property="alarmType" titleKey="cConfigAlarmType.alarmType" class="NOT_NULL"/>
							<display:column property="isMonitorName" titleKey="cConfigAlarmType.isMonitor" class="NOT_NULL"/>
							<display:column property="isSendSmsName" titleKey="cConfigAlarmType.isSendSms" class="NOT_NULL"/>
							<display:column property="isEnableName" titleKey="cConfigAlarmType.isEnable" class="NOT_NULL"/>
							<display:column property="ordering" titleKey="cConfigAlarmType.ordering"/>
							<display:column property="rawTable" titleKey="cConfigAlarmType.rawTable" class="NOT_NULL"/>
							<display:column property="summaryType" titleKey="cConfigAlarmType.summaryType"/>	
							<display:column property="search" titleKey="cConfigAlarmType.search" class="NOT_NULL"/>
							<display:column property="alarmInfoColumnName" titleKey="cConfigAlarmType.alarmInfoColumnName"/>
							<display:column property="alarmInfoValue" titleKey="cConfigAlarmType.alarmInfoValue"/>
							<display:column property="brandname" titleKey="cConfigAlarmType.brandname"/>
							<display:column property="isSoundAlarm" titleKey="cConfigAlarmType.isSoundAlarm"/>			
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu cấu hình cảnh báo hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
							<display:column class="centerColumnMana" title="STT" > <c:out value="${item2_rowNum}"/> </display:column>
							<display:column property="vendor" titleKey="cConfigAlarmType.vendor" class="NOT_NULL"/>
							<display:column property="node" titleKey="cConfigAlarmType.node" class="NOT_NULL"/>  
							<display:column property="neType" titleKey="cConfigAlarmType.neType" class="NOT_NULL"/>
							<display:column property="blockColumnName" titleKey="cConfigAlarmType.blockColumnName" class="NOT_NULL"/>
							<display:column property="blockValue" titleKey="cConfigAlarmType.blockValue" class="NOT_NULL"/>
							<display:column property="description" titleKey="cConfigAlarmType.description"/>
							<display:column property="alarmMappingName" titleKey="cConfigAlarmType.alarmMappingName" class="NOT_NULL"/>
							<display:column property="alarmMappingId" titleKey="cConfigAlarmType.alarmMappingId" class="NOT_NULL"/>
							<display:column property="alarmType" titleKey="cConfigAlarmType.alarmType" class="NOT_NULL"/>
							<display:column property="isMonitorName" titleKey="cConfigAlarmType.isMonitor" class="NOT_NULL"/>
							<display:column property="isSendSmsName" titleKey="cConfigAlarmType.isSendSms" class="NOT_NULL"/>
							<display:column property="isEnableName" titleKey="cConfigAlarmType.isEnable" class="NOT_NULL"/>
							<display:column property="ordering" titleKey="cConfigAlarmType.ordering"/>
							<display:column property="rawTable" titleKey="cConfigAlarmType.rawTable" class="NOT_NULL"/>
							<display:column property="summaryType" titleKey="cConfigAlarmType.summaryType"/>	
							<display:column property="search" titleKey="cConfigAlarmType.search" class="NOT_NULL"/>
							<display:column property="alarmInfoColumnName" titleKey="cConfigAlarmType.alarmInfoColumnName"/>
							<display:column property="alarmInfoValue" titleKey="cConfigAlarmType.alarmInfoValue"/>
							<display:column property="brandname" titleKey="cConfigAlarmType.brandname"/>	
							<display:column property="isSoundAlarm" titleKey="cConfigAlarmType.isSoundAlarm"/>		
				</display:table>
			</div>
		</div>
	</c:if>
		<table>
		<tr>
			<td >
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm"'>
			</td>
		</tr>
	</table>
</form:form>
<script type="text/javascript">  
    $(function() {
    	$('#item2>tbody>tr').each(
    	    	 function(){
   					  ${highlight}
   						});

    	$('#item1>tbody>tr').each(
   	    	 function(){
  					  ${highlight}
  					});
		}); 
</script>