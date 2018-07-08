<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>
<title><fmt:message key="title.hCell.formUpload"/></title>
<content tag="heading"><fmt:message key="title.hCell.formUpload"/></content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
	<table class="simple2">
		<tr>
			<td class="wid10 mwid140"><b><fmt:message key="hProvincesCode.file"/></b></td>
			<td><input class="button" type="file" name="file" size="90"/>&nbsp;
			<input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.button.import"/>"/></td>
		</tr>
		<tr>
				<td>
				<b><fmt:message key="hProvincesCode.dinhDangFile"/></b>
				</td>
				<td>
					<ul style="list-style-type: none;">
						<li>File import là file (.xls)</li>
						<li>Dữ liệu trong file có dạng: 
							<code>
							&lt;<fmt:message key="hCell.bscid"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="hCell.siteid"/>&gt;, 
							&lt;<fmt:message key="hCell.sitename"/>&gt;, 
							&lt;<fmt:message key="hCell.cellid"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="hCell.cellname"/>&gt;,
							&lt;<fmt:message key="hCell.launchDate"/>&gt;, 
							&lt;<fmt:message key="hCell.lastActive"/>&gt;,
							&lt;<fmt:message key="hCell.vendor"/>&gt;, 
							&lt;<fmt:message key="hCell.vendorTrans"/>&gt;,
							&lt;<fmt:message key="hCell.transType"/>&gt;, 
							&lt;<fmt:message key="hCell.nextHop"/>&gt;,
							&lt;<fmt:message key="hCell.bandwidth"/>&gt;,
							&lt;<fmt:message key="hcell.bandwidthStr"/>&gt;,
							&lt;<fmt:message key="hCell.region"/>&gt;, 
							&lt;<fmt:message key="hCell.location"/>&gt;, 
							&lt;<fmt:message key="hCell.area"/>&gt;, 
							&lt;<fmt:message key="hCell.province"/>&gt;, 
							&lt;<fmt:message key="hCell.district"/>&gt;, 
							&lt;<fmt:message key="hCell.villageName"/>&gt;, 
							&lt;<fmt:message key="hCell.longitude"/>&gt;, 
							&lt;<fmt:message key="hCell.latitude"/>&gt;,
							&lt;<fmt:message key="hCell.address"/>&gt;, 
							&lt;<fmt:message key="hCell.cgi"/>&gt;,
							&lt;<fmt:message key="hCell.cid"/>&gt;, 
							&lt;<fmt:message key="hCell.lac"/>&gt;, 
							&lt;<fmt:message key="hCell.subscriber"/>&gt;,
							&lt;<fmt:message key="hcell.subscribersInfo"/>&gt;,
							&lt;<fmt:message key="hCell.isIbc"/>&gt;, 
							&lt;<fmt:message key="hCell.cellBorder"/>&gt;, 
							&lt;<fmt:message key="hCell.cellBlacklist"/>&gt;, 
							&lt;<fmt:message key="hCell.seaCell"/>&gt;, 
							&lt;<fmt:message key="hCell.specialCell"/>&gt;, 
							&lt;<fmt:message key="hCell.siteLevel"/>&gt;, 
							&lt;<fmt:message key="hCell.transConfigs"/>&gt;, 
							&lt;<fmt:message key="hCell.delayStandar"/>&gt;,
							&lt;<fmt:message key="hCell.batteryDuration"/>&gt;, 
							&lt;<fmt:message key="hCell.phone"/>&gt;, 
							&lt;<fmt:message key="hCell.targetUCTT"/>&gt;, 
							&lt;<fmt:message key="hCell.tgUcttdhNgay"/>&gt;, 
							&lt;<fmt:message key="hCell.tgUcttdhDem"/>&gt;,
							&lt;<fmt:message key="hCell.tgBackupAcquyMoi"/>&gt;, 
							&lt;<fmt:message key="hCell.tgBackupAcquyCu"/>&gt;,
							&lt;<fmt:message key="hCell.timeCapnhatMoi"/>&gt;, 
							&lt;<fmt:message key="hCell.timeCapnhatCu"/>&gt;,
							&lt;<fmt:message key="hCell.doLechTgBackupAcquy"/>&gt;,
							&lt;<fmt:message key="hCell.mcc"/>&gt;,
							&lt;<fmt:message key="hCell.mnc"/>&gt;,
							&lt;<fmt:message key="hCell.addressEng"/>&gt;,
							&lt;<fmt:message key="hCell.azimuth"/>&gt;,
							&lt;<fmt:message key="hCell.description"/>&gt;.
							&lt;<fmt:message key="hcell.siteDistance"/>&gt;,
							&lt;<fmt:message key="hcell.khoangCachTram"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="CellExample" href="${pageContext.request.contextPath}/upload/hCellExample.xls">CellExample.xls</a>
						</li>
					</ul>
			</td>
		</tr>
	</table>
	<c:if test="${status != null}">
    	<div class="error">${status} ${statusExists}</div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu cell không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
					<display:column class="centerColumnMana" titleKey="global.list.STT" > <c:out value="${item1_rowNum}"/> </display:column>
					<display:column property="bscid" titleKey="hCell.bscid" class="NOT_NULL"/>
					<display:column property="siteid" titleKey="hCell.siteid" />
					<display:column property="sitename" titleKey="hCell.sitename" />
					<display:column property="cellid" titleKey="hCell.cellid" class="NOT_NULL"/>  
					<display:column property="cellname" titleKey="hCell.cellname"/>
					<display:column property ="launchDateI"  titleKey="hCell.launchDate"/> 
					<display:column property ="lastActiveI" titleKey="hCell.lastActive"/> 
					<display:column property="vendor" titleKey="hCell.vendor"/>
					<display:column property="vendorTrans" titleKey="hCell.vendorTrans"/>
					<display:column property="transType" titleKey="hCell.transType"/>
					<display:column property="nextHop" titleKey="hCell.nextHop"/>
					<display:column property="bandwidth" titleKey="hCell.bandwidth"/>
					<display:column property="bandwidthStr" titleKey="hCell.bandwidthStr"/>
					<display:column property="subscriber" titleKey="hCell.subscriber"/>
					<display:column property="subscribersInfo" titleKey="hCell.subscribersInfo"/>
					<display:column property="region" titleKey="hCell.region"/>
					<display:column property="location" titleKey="hCell.location"/>
					<display:column property="area" titleKey="hCell.area"/>
					<display:column property="province" titleKey="hCell.province"/>
					<display:column property="district" titleKey="hCell.district"/>
					<display:column property="villageName" titleKey="hCell.villageName"/>
					<display:column property="longitude" titleKey="hCell.longitude"/>
					<display:column property="latitude" titleKey="hCell.latitude"/>
					<display:column property="address" titleKey="hCell.address"/>
					<display:column property="cgi" titleKey="hCell.cgi"/>
					<display:column property="cid" titleKey="hCell.cid"/>
					<display:column property="lac" titleKey="hCell.lac"/>
					<display:column property ="isIbc" titleKey="hCell.isIbc"/> 
					<display:column property ="cellBorder" titleKey="hCell.cellBorder"/> 
					<display:column property ="cellBlacklist" titleKey="hCell.cellBlacklist"/> 
					<display:column property ="seaCell" titleKey="hCell.seaCell"/> 
					<display:column property ="specialCell" titleKey="hCell.specialCell"/> 
					<display:column property ="siteLevel" titleKey="hCell.siteLevel"/> 
					<display:column property ="transConfigs" titleKey="hCell.transConfigs"/> 
					<display:column property ="delayStandar" titleKey="hCell.delayStandar"/>
					<display:column property ="batteryDuration" titleKey="hCell.batteryDuration"/> 
					<display:column property ="phone" titleKey="hCell.phone"/> 
					<display:column property ="targetUCTT" titleKey="hCell.targetUCTT"/> 
					<display:column property ="tgUcttdhNgay" titleKey="hCell.tgUcttdhNgay"/> 
					<display:column property ="tgUcttdhDem" titleKey="hCell.tgUcttdhDem"/> 
					<display:column property ="tgBackupAcquyMoi" titleKey="hCell.tgBackupAcquyMoi"/> 
				   	<display:column property ="tgBackupAcquyCu" titleKey="hCell.tgBackupAcquyCu"/> 
				   	<display:column property ="timeCapnhatMoi1" titleKey="hCell.timeCapnhatMoi"/> 
				   	<display:column property ="timeCapnhatCu1" titleKey="hCell.timeCapnhatCu"/> 
				   	<display:column property ="doLechTgBackupAcquy" titleKey="hCell.doLechTgBackupAcquy"/> 
				   	<display:column property ="mcc" titleKey="hCell.mcc"/> 
				   	<display:column property ="mnc" titleKey="hCell.mnc"/> 
				   	<display:column property ="addressEng" titleKey="hCell.addressEng"/> 
				   	<display:column property ="azimuth" titleKey="hCell.azimuth"/> 
				    <display:column property="description" titleKey="hCell.description"/>
				    <display:column property ="siteDistance" titleKey="hCell.siteDistance"/> 
				    <display:column property="description" titleKey="hCell.khoangCachTram"/>
						    
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu cell hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
					<display:column class="centerColumnMana" titleKey="global.list.STT" > <c:out value="${item1_rowNum}"/> </display:column>
					<display:column property="bscid" titleKey="hCell.bscid" class="NOT_NULL"/>
					<display:column property="siteid" titleKey="hCell.siteid" />
					<display:column property="sitename" titleKey="hCell.sitename" />
					<display:column property="cellid" titleKey="hCell.cellid" class="NOT_NULL"/>  
					<display:column property="cellname" titleKey="hCell.cellname"/>
					<display:column property ="launchDateI"  titleKey="hCell.launchDate"/> 
					<display:column property ="lastActiveI" titleKey="hCell.lastActive"/> 
					<display:column property="vendor" titleKey="hCell.vendor"/>
					<display:column property="vendorTrans" titleKey="hCell.vendorTrans"/>
					<display:column property="transType" titleKey="hCell.transType"/>
					<display:column property="nextHop" titleKey="hCell.nextHop"/>
					<display:column property="bandwidth" titleKey="hCell.bandwidth"/>
					<display:column property="bandwidthStr" titleKey="hCell.bandwidthStr"/>
					<display:column property="subscriber" titleKey="hCell.subscriber"/>
					<display:column property="subscribersInfo" titleKey="hCell.subscribersInfo"/>
					<display:column property="region" titleKey="hCell.region"/>
					<display:column property="location" titleKey="hCell.location"/>
					<display:column property="area" titleKey="hCell.area"/>
					<display:column property="province" titleKey="hCell.province"/>
					<display:column property="district" titleKey="hCell.district"/>
					<display:column property="villageName" titleKey="hCell.villageName"/>
					<display:column property="longitude" titleKey="hCell.longitude"/>
					<display:column property="latitude" titleKey="hCell.latitude"/>
					<display:column property="address" titleKey="hCell.address"/>
					<display:column property="cgi" titleKey="hCell.cgi"/>
					<display:column property="cid" titleKey="hCell.cid"/>
					<display:column property="lac" titleKey="hCell.lac"/>
					<display:column property ="isIbc" titleKey="hCell.isIbc"/> 
					<display:column property ="cellBorder" titleKey="hCell.cellBorder"/> 
					<display:column property ="cellBlacklist" titleKey="hCell.cellBlacklist"/> 
					<display:column property ="seaCell" titleKey="hCell.seaCell"/> 
					<display:column property ="specialCell" titleKey="hCell.specialCell"/> 
					<display:column property ="siteLevel" titleKey="hCell.siteLevel"/> 
					<display:column property ="transConfigs" titleKey="hCell.transConfigs"/> 
					<display:column property ="delayStandar" titleKey="hCell.delayStandar"/>
					<display:column property ="batteryDuration" titleKey="hCell.batteryDuration"/> 
					<display:column property ="phone" titleKey="hCell.phone"/> 
					<display:column property ="targetUCTT" titleKey="hCell.targetUCTT"/> 
					<display:column property ="tgUcttdhNgay" titleKey="hCell.tgUcttdhNgay"/> 
					<display:column property ="tgUcttdhDem" titleKey="hCell.tgUcttdhDem"/> 
					<display:column property ="tgBackupAcquyMoi" titleKey="hCell.tgBackupAcquyMoi"/> 
				   	<display:column property ="tgBackupAcquyCu" titleKey="hCell.tgBackupAcquyCu"/> 
				   	<display:column property ="timeCapnhatMoi1" titleKey="hCell.timeCapnhatMoi"/> 
				   	<display:column property ="timeCapnhatCu1" titleKey="hCell.timeCapnhatCu"/> 
				   	<display:column property ="doLechTgBackupAcquy" titleKey="hCell.doLechTgBackupAcquy"/> 
				   	<display:column property ="mcc" titleKey="hCell.mcc"/> 
				   	<display:column property ="mnc" titleKey="hCell.mnc"/> 
				   	<display:column property ="addressEng" titleKey="hCell.addressEng"/> 
				   	<display:column property ="azimuth" titleKey="hCell.azimuth"/> 
				    <display:column property="description" titleKey="hCell.description"/>
				    <display:column property ="siteDistance" titleKey="hCell.siteDistance"/> 
				    <display:column property="description" titleKey="hCell.khoangCachTram"/>
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
