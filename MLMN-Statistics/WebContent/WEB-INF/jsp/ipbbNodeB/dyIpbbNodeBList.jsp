<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo NodeB đối tác</title>
<content tag="heading">Báo cáo NodeB đối tác</content>

<style>
	table.simple td, table.simple th {
		max-width: 190px;
	} 
	 
	#doublescroll { overflow: auto; overflow-y: hidden; }
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
	 
	.wid10{
		width: 10%;
	}
	.wid2{
		width: 2%;
	} 
</style> 

<br>
	<form method="post" action="dy-list.htm">
		<table style="width:100%;" class="form">
			<tr>
				<td align="left">
			    	<input value="${nguongduoi}" name="nguongduoi" id="nguongduoi" maxlength="2" class="wid2">&nbsp;&lt;
			    	Ngưỡng tải &nbsp;&lt;&nbsp;<input value="${nguongtren}" name="nguongtren" id="nguongtren" maxlength="2" class="wid2">&nbsp;&nbsp;
			    	 
					Node Id <input value="${nodeId}" name="nodeId" id="nodeId" class="wid10">&nbsp;&nbsp;
					Tên trạm <input value="${tentram}" name="tentram" id="tentram" class="wid10">&nbsp;&nbsp;
					Tên đối tác <input value="${tendoitac}" name="tendoitac" id="tendoitac" class="wid10">&nbsp;&nbsp;
					Từ ngày <input value="${startDate}" name="startDate" id="startDate" class="wid10">&nbsp;&nbsp;
			        Đến ngày <input value="${endDate}" name="endDate" id="endDate" class="wid10">&nbsp;&nbsp; 
		            <input type="submit" class="button" name="submit" id="submit"value="Tìm kiếm"/>
	            </td>
	        </tr>		
		</table>
	</form> 
	
<div style="overflow: auto;">
	<display:table name="${dyIpbbNodeBList}" id="item" requestURI="" pagesize="100" class="simple2" export="true">
			<display:column property="dateTime" format="{0,date,dd/MM/yyyy}" title="Ngày" sortName="date_time" sortable="true" headerClass="master-header-1 margin" class="margin"/>
			<display:column property="tenDoiTac" title="Tên đối tác" sortName="ten_doi_tac" sortable="true" headerClass="master-header-2"/>
			<display:column property="nodebId" title="NodeB id" sortName="nodeb_id" sortable="true" headerClass="master-header-2"/> 
    		<display:column property="tenTram" title="Tên trạm" sortName="ten_tram" sortable="true" headerClass="master-header-2"/>
    		<display:column property="bw" title="Bandwith" sortName="bw" sortable="true" headerClass="master-header-2 margin" class="margin"/>
    		<display:column property="trafficTotalVolSum" title="Traffic total volume" sortName="TRAFFIC_TOTAL_VOL_SUM" sortable="true" headerClass="master-header-4"/>
    		<display:column property="trafficInVolSum" title="Traffic in volume" sortName="TRAFFIC_IN_VOL_SUM" sortable="true" headerClass="master-header-4"/>
    		<display:column property="trafficOutVolSum" title="Traffic out volume" sortName="TRAFFIC_OUT_VOL_SUM" sortable="true" headerClass="master-header-4"/>
			<display:column property="trafficTotalSpeedAvg" title="Traffic total speed" sortName="TRAFFIC_TOTAL_SPEED_AVG" sortable="true" headerClass="master-header-4"/>
			<display:column property="utilityNodeb" title="Utility" sortName="UTILITY_NODEB" sortable="true" headerClass="master-header-4"/>
			
			<display:setProperty name="export.csv.filename" value="${exportFileName}.csv"/>
			<display:setProperty name="export.excel.filename" value="${exportFileName}.xls"/>
			<display:setProperty name="export.xml.filename" value="${exportFileName}.xml"/>
	</display:table>
</div> 

<script type="text/javascript" >
$( "#startDate" ).datepicker({
	dateFormat: "dd/mm/yy",
	showOn: "button",
	buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
	buttonImageOnly: true
});
$( "#endDate" ).datepicker({
	dateFormat: "dd/mm/yy",
	showOn: "button",
	buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
	buttonImageOnly: true
});

</script>
