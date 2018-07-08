<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/js/maps/css/styles.css" />
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=true"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/maps/MobileMap.js"></script>

<div align="center"><h2>THÔNG TIN TRẠM</h2></div>
<table class="simple2">
    <tr>
    	<td class="wid8 mwid110"><fmt:message key="siteMap.siteName"/></td>
    	<td class="wid15">${siteInfoList.siteid}</td>
    	<td rowspan="12" colspan="6" align="center">
			<div id="canvas_holder" >
				<div id="map_canvas" style="height: 475px"></div>
			</div>
		</td>
	</tr>
	<tr>
		<td rowspan="2" style="vertical-align:middle;"><fmt:message key="siteMap.vendor"/></td>
		<td>
			2G:&nbsp;${siteInfoList.vendor}
			
		</td>
	</tr>
	<tr>
		<td>
			3G:&nbsp;${siteInfoList.vendor3g}
		</td>
	</tr>
	<tr>
		<td><fmt:message key="siteMap.bsc"/></td>
		<td>
			${siteInfoList.bscid}
		</td>
	</tr>
	<tr>
		<td><fmt:message key="siteMap.rnc"/></td>
		<td>
			${siteInfoList.rncid}
		</td>
	</tr>
	<tr>
		<td rowspan="4" style="vertical-align:middle;"><fmt:message key="siteMap.transType"/></td>
		<td>
			Loại truyền dẫn:&nbsp;${siteInfoList.transType}
		</td>
	</tr>
	<tr>
		<td>
			Đối tác truyền dẫn:&nbsp;${siteInfoList.vendorTrans}
		</td>
	</tr>
	<tr>
		<td>
			Băng thông:&nbsp;${siteInfoList.bandwidthStr}
		</td>
	</tr>
	<tr>
		<td>
			Điểm cuối:&nbsp;${siteInfoList.nextHop}
		</td>
	</tr>
	<tr>
		<td><fmt:message key="siteMap.longitude"/></td>
		<td>
			${siteInfoList.longitude}
		</td>
	</tr>
	<tr>
		<td><fmt:message key="siteMap.latitude"/></td>
		<td>
			${siteInfoList.latitude}
		</td>
	</tr>
	<tr>
		<td><fmt:message key="siteMap.ibc"/></td>
		<td>
			${siteInfoList.ibcName}
		</td>
	</tr>
	<tr>
		<td>Cấp trạm</td>
		<td>${siteInfoList.type}</td>
		<td>Accu(giờ):&nbsp;${siteInfoList.targetUCTT}</td>
		<td>Border:&nbsp;${siteInfoList.cellBorder}</td>
		<td>Blacklist:&nbsp;${siteInfoList.cellBlacklist}</td>
		<td><a href="/mlmn/iso/iso-inventory/list.htm?locationName=${siteInfoList.siteid}">Xem inventory</a></td>
		<td>iWork:&nbsp;${siteInfoList.avgMonthTraff}</td>
	</tr>
	<tr>
		<td><fmt:message key="siteMap.address"/></td>
		<td colspan="6">
			${siteInfoList.address}
		</td>
	</tr>
	<tr>
		<td><fmt:message key="Thông tin Xã, Huyện, Tỉnh"/></td>
		<td colspan="6">
			${siteInfoList.villageName}
		</td>
	</tr>
	<tr>
		<td><fmt:message key="Thông tin Tổ/Nhóm"/></td>
		<td colspan="6">
			${siteInfoList.tnInfo}
		</td>
	</tr>
	<tr>
		<td><fmt:message key="Tổ trưởng"/></td>
		<td colspan="6">
			${siteInfoList.tt}
		</td>
	</tr>
	<tr>
		<td><fmt:message key="Nhóm trưởng"/></td>
		<td colspan="6">
			${siteInfoList.nt}
		</td>
	</tr>
	
	<tr>
		<td><fmt:message key="Thông tin thuê bao"/></td>
		<td colspan="6">
			${siteInfoList.subscribersInfo}
		</td>
	</tr>
	<%-- <tr>
		<td rowspan="2" style="vertical-align:middle;"><fmt:message key="siteMap.subscriber"/></td>
		<td colspan="6">2G:&nbsp;${siteInfoList.subscriber}</td>
	</tr>
	<tr>
		<td colspan="6">3G:&nbsp;${siteInfoList.subscriber3g}</td>
	</tr> --%>
</table>
<table width="100%">
	<tr>
		<td><h3>Thông tin KPI</h3></td>
	</tr>
	<tr>
		<td>
			<div class="tableStandar">
					<display:table name="${traffic2g3gList}"  id="item1" requestURI="" pagesize="50" >
						<display:column format="{0,date,dd/MM/yyyy}" property="day" titleKey="siteMap.day" />	
						<display:column property="hour" titleKey="siteMap.hour" class="rightColumnMana"/>
						<display:column property="network" titleKey="siteMap.network"/>
						<display:column property="bscid" titleKey="siteMap.bscidRncid" />	
						<display:column property="cellid" titleKey="siteMap.cellid" />
						<display:column property="tTraf" titleKey="siteMap.tTraf" class="rightColumnMana"/>		
						<display:column property="data" titleKey="siteMap.data" class="rightColumnMana"/>
						<display:column property="cssr" titleKey="siteMap.cssr" class="rightColumnMana"/>
						<display:column property="tDrpr" titleKey="siteMap.tDrpr" class="rightColumnMana"/>
						
					</display:table>
				</div>
		</td>
	</tr>
</table>
<table width="100%">
	<tr>
		<td><h3>Danh sách cảnh báo</h3></td>
	</tr>
	<tr>
		<td>
			<div class="tableStandar">
					<display:table name="${rAlarmLogInfoList}"  id="item" requestURI="" pagesize="50" >
						<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${item_rowNum}"/></display:column>
						<display:column property="ne" titleKey="siteMap.ne" />
						<display:column property="cellid" titleKey="siteMap.cellid" /> 
						<display:column format="{0,date,dd/MM/yyyy HH:mm:ss}" property="sdate" titleKey="siteMap.sdate" />				
						<display:column property="severity" titleKey="siteMap.severity" class="R_ALARM_LOG"/>
						<display:column property="alarmType" titleKey="siteMap.alarmType" />
						<display:column property="alarmName" titleKey="siteMap.alarmName" />
						<display:column property="alarmInfo" titleKey="siteMap.alarmInfo" />
										
					</display:table>
				</div>
		</td>
	</tr>
</table>
<script type="text/javascript">
$(function() {
	${highlightKPI}
	${highlight}				
}); 

/* $(document).ready(function(){
	var trs= $('#item1 tbody tr').first().html(); 
	trs='<tr>';
	trs=trs + '<th rowspan="2"><fmt:message key="siteMap.day"/></th>';
	trs=trs + '<th rowspan="2"><fmt:message key="siteMap.hour"/></th>';
	trs=trs + '<th rowspan="2"><fmt:message key="siteMap.bscid"/></th>';
	trs=trs + '<th rowspan="2"><fmt:message key="siteMap.cellid"/></th>';
	trs=trs + '<th colspan="4"><fmt:message key="siteMap.2g"/></th>';
	trs=trs + '<th colspan="4"><fmt:message key="siteMap.3g"/></th>';
	trs=trs + '</tr>';
	trs=trs +'<tr>';
	trs=trs +'<th><fmt:message key="siteMap.tTraf"/></th>';
	trs=trs +'<th><fmt:message key="siteMap.data"/></th>';
	trs=trs +'<th><fmt:message key="siteMap.cssr"/></th>';
	trs=trs +'<th><fmt:message key="siteMap.tDrpr"/></th>';
	trs=trs +'<th><fmt:message key="siteMap.tTraf"/></th>';
	trs=trs +'<th><fmt:message key="siteMap.data"/></th>';
	trs=trs +'<th><fmt:message key="siteMap.cssr"/></th>';
	trs=trs +'<th><fmt:message key="siteMap.tDrpr"/></th>';
	trs=trs +'</tr>';
	$('#item1 thead').html(trs);
}); */
</script>
<script type="text/javascript">

	var mobileMap = null;
	var trackers = [];
	var currentTrackerUin = null;

	$(document).ready(function(){
		mobileMap = new MobileMap();
		mobileMap.initialize(trackers);
		
		var siteid = '<c:out value="${siteInfoList.siteid}"/>';	
		var latitude = '<c:out value="${siteInfoList.latitude}"/>';	
		var longitude = '<c:out value="${siteInfoList.longitude}"/>';	
		
		if(!siteid || !latitude || !longitude) {
			return;
		} 
		mobileMap.panToTracker(siteid, latitude, longitude);
			
	});
</script>