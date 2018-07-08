<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title><fmt:message key="sidebar.admin.isoEquipmentUpload"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.isoEquipmentUpload"/></content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	<div>
    	<input id="inputStatus" name="inputStatus" value="${inputStatus}" type="hidden" />
    </div>
	<table class="simple2">
		<tr>
			<td class="wid10 mwid140"><b><fmt:message key="qLNguoiDung.file"/></b></td>
			<td ><input class="button" type="file" name="file" size="90"/>&nbsp;<input type="submit" class="button" name="save" value="<fmt:message key="global.button.import"/>"/></td>
		</tr>
		<tr>
			<td>
			<b><fmt:message key="qLNguoiDung.dinhDangFile"/></b>
			</td>
			<td>
				<ul style="list-style-type: none;">
					<li>File import là file (.xls)</li>
					<li>Dữ liệu trong file có dạng: <code>
					&lt;<fmt:message key="isoEquipment.deptCode"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.team"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.subTeam"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.province"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.district"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.neType"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.codeAssetType"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.locationName"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.location"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.ne"/>&gt;<font color="red">(*)</font>, 
					&lt;<fmt:message key="isoEquipment.productCode"/>&gt;<font color="red">(*)</font>, 
					&lt;<fmt:message key="isoEquipment.seriNo"/>&gt;<font color="red">(*)</font>, 
					&lt;<fmt:message key="isoEquipment.productDate"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.neParent"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.productName"/>&gt;,  
					&lt;<fmt:message key="isoEquipment.status"/>&gt;, 
					&lt;<fmt:message key="isoLicenseSoft.vendor"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.rack"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.subrack"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.slot"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.swVersion"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.initializeDate"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.neOld"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.updated"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.omIp"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.nsei"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.spc"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.bscidRncid"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.power"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.defaultCurrent"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.defaultVoltage"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.realCurrent"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.loadFollow"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.battery"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.batteryDuration"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.maintenanceInterval"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.maintenanceDate"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.maintenanceResult"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.maintenanceSupervisor"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.maintenanceComment"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.contractNumber"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.contractDate"/>&gt;, 
					&lt;<fmt:message key="isoEquipment.description"/>&gt;.</code>
					</li>
					<li>File mẫu:&nbsp;<a style="color: blue; " title="IsoInventoryExample" href="${pageContext.request.contextPath}/upload/example/IsoInventoryExample.xls">IsoInventoryExample.xls</a></li>
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
    		<div><b>Dữ liệu thiết bị không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="700">
						<display:column class="centerColumnMana" title="No." > <c:out value="${item1_rowNum}"/> </display:column>
						<display:column property="deptCode" titleKey="isoEquipment.deptCode" />
						<display:column property="team" titleKey="isoEquipment.team" />
						<display:column property="subTeam" titleKey="isoEquipment.subTeam" />
						<display:column property="province" titleKey="isoEquipment.province" />
						<display:column property="district" titleKey="isoEquipment.district" />
						<display:column property="neType" titleKey="isoEquipment.neType" />
						<display:column property="neGroup" titleKey="isoEquipment.codeAssetType" />
						<display:column property="locationName" titleKey="isoEquipment.locationName" />
						<display:column property="location" titleKey="isoEquipment.location" />
						<display:column property="ne" titleKey="isoEquipment.ne" class="NOT_NULL"/>
						<display:column property="productCode" titleKey="isoEquipment.productCode" class="NOT_NULL" />
						<display:column property="seriNo" titleKey="isoEquipment.seriNo" class="NOT_NULL" />
						<display:column format="{0,date,dd/MM/yyyy}" property="productDate" titleKey="isoEquipment.productDate"  />
						<display:column property="neParent" titleKey="isoEquipment.neParent" />
						<display:column property="productName" titleKey="isoEquipment.productName" />
						<display:column property="statusName" titleKey="isoEquipment.status" />
						<display:column property="vendor" titleKey="isoLicenseSoft.vendor" />
						<display:column property="rack" titleKey="isoEquipment.rack" />
						<display:column property="subrack" titleKey="isoEquipment.subrack" />
						<display:column property="slot" titleKey="isoEquipment.slot"/>
						<display:column property="swVersion" titleKey="isoEquipment.swVersion" />
						<display:column format="{0,date,dd/MM/yyyy}" property="initializeDate" titleKey="isoEquipment.initializeDate" />
						<display:column property="neOld" titleKey="isoEquipment.neOld" />
						<display:column format="{0,date,dd/MM/yyyy}" property="updated" titleKey="isoEquipment.updated" />
						<display:column property="omIp" titleKey="isoEquipment.omIp" />
						<display:column property="nsei" titleKey="isoEquipment.nsei" />
						<display:column property="spc" titleKey="isoEquipment.spc" />
						<display:column property="bscidRncid" titleKey="isoEquipment.bscidRncid" />
						<display:column property="power" titleKey="isoEquipment.power" />
						<display:column property="defaultCurrent" titleKey="isoEquipment.defaultCurrent" />
						<display:column property="defaultVoltage" titleKey="isoEquipment.defaultVoltage" />
						<display:column property="realCurrent" titleKey="isoEquipment.realCurrent" />
						<display:column property="loadFollow" titleKey="isoEquipment.loadFollow" />
						<display:column property="battery" titleKey="isoEquipment.battery" />
						<display:column property="batteryDuration" titleKey="isoEquipment.batteryDuration" />
						<display:column property="maintenanceInterval" titleKey="isoEquipment.maintenanceInterval" />
						<display:column format="{0,date,dd/MM/yyyy}" property="maintenanceDate" titleKey="isoEquipment.maintenanceDate" />
						<display:column property="maintenanceResult" titleKey="isoEquipment.maintenanceResult" />
						<display:column property="maintenanceSupervisor" titleKey="isoEquipment.maintenanceSupervisor" />
						<display:column property="maintenanceComment" titleKey="isoEquipment.maintenanceComment" />
						<display:column property="contractNumber" titleKey="isoEquipment.contractNumber" />
						<display:column format="{0,date,dd/MM/yyyy}" property="contractDate" titleKey="isoEquipment.contractDate" />
						<display:column property="description" titleKey="isoEquipment.description" />
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu thiết bị hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
						<display:column class="centerColumnMana" title="No." > <c:out value="${item2_rowNum}"/> </display:column>
						<display:column property="deptCode" titleKey="isoEquipment.deptCode" />
						<display:column property="team" titleKey="isoEquipment.team" />
						<display:column property="subTeam" titleKey="isoEquipment.subTeam" />
						<display:column property="province" titleKey="isoEquipment.province" />
						<display:column property="district" titleKey="isoEquipment.district" />
						<display:column property="neType" titleKey="isoEquipment.neType" />
						<display:column property="neGroup" titleKey="isoEquipment.codeAssetType" />
						<display:column property="locationName" titleKey="isoEquipment.locationName" />
						<display:column property="location" titleKey="isoEquipment.location" />
						<display:column property="ne" titleKey="isoEquipment.ne" class="NOT_NULL" />
						<display:column property="productCode" titleKey="isoEquipment.productCode" class="NOT_NULL" />
						<display:column property="seriNo" titleKey="isoEquipment.seriNo" class="NOT_NULL" />
						<display:column format="{0,date,dd/MM/yyyy}" property="productDate" titleKey="isoEquipment.productDate"  />
						<display:column property="neParent" titleKey="isoEquipment.neParent" />
						<display:column property="productName" titleKey="isoEquipment.productName" />
						<display:column property="statusName" titleKey="isoEquipment.status" />
						<display:column property="vendor" titleKey="isoLicenseSoft.vendor" />
						<display:column property="rack" titleKey="isoEquipment.rack" />
						<display:column property="subrack" titleKey="isoEquipment.subrack" />
						<display:column property="slot" titleKey="isoEquipment.slot"/>
						<display:column property="swVersion" titleKey="isoEquipment.swVersion" />
						<display:column format="{0,date,dd/MM/yyyy}" property="initializeDate" titleKey="isoEquipment.initializeDate" />
						<display:column property="neOld" titleKey="isoEquipment.neOld" />
						<display:column format="{0,date,dd/MM/yyyy}" property="updated" titleKey="isoEquipment.updated" />
						<display:column property="omIp" titleKey="isoEquipment.omIp" />
						<display:column property="nsei" titleKey="isoEquipment.nsei" />
						<display:column property="spc" titleKey="isoEquipment.spc" />
						<display:column property="bscidRncid" titleKey="isoEquipment.bscidRncid" />
						<display:column property="power" titleKey="isoEquipment.power" />
						<display:column property="defaultCurrent" titleKey="isoEquipment.defaultCurrent" />
						<display:column property="defaultVoltage" titleKey="isoEquipment.defaultVoltage" />
						<display:column property="realCurrent" titleKey="isoEquipment.realCurrent" />
						<display:column property="loadFollow" titleKey="isoEquipment.loadFollow" />
						<display:column property="battery" titleKey="isoEquipment.battery" />
						<display:column property="batteryDuration" titleKey="isoEquipment.batteryDuration" />
						<display:column property="maintenanceInterval" titleKey="isoEquipment.maintenanceInterval" />
						<display:column format="{0,date,dd/MM/yyyy}" property="maintenanceDate" titleKey="isoEquipment.maintenanceDate" />
						<display:column property="maintenanceResult" titleKey="isoEquipment.maintenanceResult" />
						<display:column property="maintenanceSupervisor" titleKey="isoEquipment.maintenanceSupervisor" />
						<display:column property="maintenanceComment" titleKey="isoEquipment.maintenanceComment" />
						<display:column property="contractNumber" titleKey="isoEquipment.contractNumber" />
						<display:column format="{0,date,dd/MM/yyyy}" property="contractDate" titleKey="isoEquipment.contractDate" />
						<display:column property="description" titleKey="isoEquipment.description" />
				</display:table>
			</div>
		</div>
	</c:if>
	<table>
		<tr>
			<td>
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm?inputStatus=${inputStatus}"'>
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