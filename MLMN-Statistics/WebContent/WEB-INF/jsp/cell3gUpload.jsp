<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>
<title><fmt:message key="title.hCell3g.formUpload"/></title>
<content tag="heading"><fmt:message key="title.hCell3g.formUpload"/></content>
 	
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
							&lt;<fmt:message key="hCell3g.bscid"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="hCell3g.type"/>&gt;, 
							&lt;<fmt:message key="hCell3g.siteid"/>&gt;, 
							&lt;<fmt:message key="hCell3g.sitename"/>&gt;, 
							&lt;<fmt:message key="hCell3g.cellid"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="hCell3g.cellname"/>&gt;, 
							&lt;<fmt:message key="hCell3g.vendor"/>&gt;, 
							&lt;<fmt:message key="hCell.vendorTrans"/>&gt;,
							&lt;<fmt:message key="hCell3g.transType"/>&gt;, 
							&lt;<fmt:message key="hCell.bandwidth"/>&gt;,
							&lt;<fmt:message key="hCell.nextHop"/>&gt;,
							&lt;<fmt:message key="hCell.subscriber"/>&gt;,
							&lt;<fmt:message key="hCell3g.region"/>&gt;, 
							&lt;<fmt:message key="hCell3g.area"/>&gt;, 
							&lt;<fmt:message key="hCell3g.province"/>&gt;, 
							&lt;<fmt:message key="hCell3g.district"/>&gt;, 
							&lt;<fmt:message key="hCell3g.villageName"/>&gt;, 
							&lt;<fmt:message key="hCell3g.address"/>&gt;, 
							&lt;<fmt:message key="hCell3g.longitude"/>&gt;, 
							&lt;<fmt:message key="hCell3g.latitude"/>&gt;, 
							&lt;<fmt:message key="hCell3g.cid"/>&gt;, 
							&lt;<fmt:message key="hCell3g.lac"/>&gt;, 
							&lt;<fmt:message key="hCell3g.cellBorder"/>&gt;, 
							&lt;<fmt:message key="hCell3g.cellBlacklist"/>&gt;, 
							&lt;<fmt:message key="hCell.seaCell"/>&gt;, 
							&lt;<fmt:message key="hCell.specialCell"/>&gt;, 
							&lt;<fmt:message key="hCell3g.isIbc"/>&gt;, 
							&lt;<fmt:message key="hCell.siteLevel"/>&gt;, 
							&lt;<fmt:message key="hCell.transConfigs"/>&gt;, 
							&lt;<fmt:message key="hCell.delayStandar"/>&gt;, 
							&lt;<fmt:message key="hCell.launchDate"/>&gt;, 
							&lt;<fmt:message key="hCell.lastActive"/>&gt;,
							&lt;<fmt:message key="hCell.batteryDuration"/>&gt;, 
							&lt;<fmt:message key="hCell.phone"/>&gt;,  
							&lt;<fmt:message key="hCell.targetUCTT"/>&gt;,  
							&lt;<fmt:message key="hCell3g.tgUcttdhNgay"/>&gt;, 
							&lt;<fmt:message key="hCell3g.tgUcttdhDem"/>&gt;,  
							&lt;<fmt:message key="hCell.tgBackupAcquyMoi"/>&gt;, 
							&lt;<fmt:message key="hCell.tgBackupAcquyCu"/>&gt;,
							&lt;<fmt:message key="hCell.timeCapnhatMoi"/>&gt;, 
							&lt;<fmt:message key="hCell.timeCapnhatCu"/>&gt;,
							&lt;<fmt:message key="hCell.doLechTgBackupAcquy"/>&gt;,
							&lt;<fmt:message key="hCell3g.description"/>&gt;,
						 	&lt;<fmt:message key="hcell.createDate"/>&gt;, 
							&lt;<fmt:message key="hcell.siteDistance"/>&gt;,
							&lt;<fmt:message key="hcell.bandwidthStr"/>&gt;, 
							&lt;<fmt:message key="hcell.subscribersInfo"/>&gt;,
							&lt;<fmt:message key="hcell.khoangCachTram"/>&gt;
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="Cell3gExample" href="${pageContext.request.contextPath}/upload/hCellExample3g.xls">Cell3gExample.xls</a>
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
    		<div><b>Dữ liệu cell 3g không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
							<display:column class="centerColumnMana" titleKey="global.list.STT" > <c:out value="${item1_rowNum}"/> </display:column>
							<display:column property="bscid" titleKey="hCell3g.bscid" class="NOT_NULL"/>
							<display:column property="type" titleKey="hCell3g.type"/>
							<display:column property="siteid" titleKey="hCell3g.siteid" />
							<display:column property="siteName" titleKey="hCell3g.sitename" />
							<display:column property="cellid" titleKey="hCell3g.cellid" class="NOT_NULL"/>  
							<display:column property="cellName" titleKey="hCell3g.cellname"/>
							<display:column property="vendor" titleKey="hCell3g.vendor"/>
							<display:column property="vendorTrans" titleKey="hCell.vendorTrans"/>
							<display:column property="transType" titleKey="hCell3g.transType"/>
							<display:column property="bandwidth" titleKey="hCell.bandwidth"/>
							<display:column property="nextHop" titleKey="hCell.nextHop"/>
							<display:column property="subscriber" titleKey="hCell.subscriber"/>
							<display:column property="region" titleKey="hCell3g.region"/>
							<display:column property="area" titleKey="hCell3g.area"/>
							<display:column property="province" titleKey="hCell3g.province"/>
							<display:column property="district" titleKey="hCell3g.district"/>
							<display:column property="villageName" titleKey="hCell3g.villageName"/>
							<display:column property="address" titleKey="hCell3g.address"/>
							<display:column property="longitude" titleKey="hCell3g.longitude"/>
							<display:column property="latitude" titleKey="hCell3g.latitude"/>
							<display:column property="cid" titleKey="hCell3g.cid"/>
							<display:column property="lac" titleKey="hCell3g.lac"/>
							<display:column property="cellBorder" titleKey="hCell3g.cellBorder"/>
							<display:column property="cellBlacklist" titleKey="hCell3g.cellBlacklist"/>
							<display:column property ="seaCell" titleKey="hCell.seaCell"/> 
						    <display:column property ="specialCell" titleKey="hCell.specialCell"/> 
						    <display:column property ="siteLevel" titleKey="hCell.siteLevel" /> 
						    <display:column property ="transConfigs" titleKey="hCell.transConfigs" /> 
						    <display:column property ="delayStandar" titleKey="hCell.delayStandar" /> 
						    <display:column property ="launchDateI"  titleKey="hCell.launchDate"/> 
							<display:column property ="lastActiveI" titleKey="hCell.lastActive"/> 
						   	<display:column property ="isIbc" titleKey="hCell.isIbc"/> 
						   	<display:column property ="batteryDuration" titleKey="hCell.batteryDuration"/> 
						   	<display:column property ="phone" titleKey="hCell.phone"/>   
						   	<display:column property ="targetUctt" titleKey="hCell.targetUCTT"/> 
						   	<display:column property ="tgUcttdhNgay" titleKey="hCell3g.tgUcttdhNgay"/> 
						   	<display:column property ="tgUcttdhDem" titleKey="hCell3g.tgUcttdhDem"/> 
						   	<display:column property ="tgBackupAcquyMoi" titleKey="hCell.tgBackupAcquyMoi"/> 
						   	<display:column property ="tgBackupAcquyCu" titleKey="hCell.tgBackupAcquyCu"/> 
						   	<display:column property ="timeCapnhatMoi1" titleKey="hCell.timeCapnhatMoi"/> 
						   	<display:column property ="timeCapnhatCu1" titleKey="hCell.timeCapnhatCu"/> 
						   	<display:column property ="doLechTgBackupAcquy" titleKey="hCell.doLechTgBackupAcquy"/> 
							<display:column property="description" titleKey="hCell3g.description"/>
						   	<display:column property ="createDate" titleKey="hcell.createDate"/> 
						   	<display:column property ="siteDistance" titleKey="hcell.siteDistance"/> 
						   	<display:column property ="bandwidthStr" titleKey="hcell.bandwidthStr"/> 
						   	<display:column property ="subscribersInfo" titleKey="hcell.subscribersInfo"/> 
						   	<display:column property ="khoangCachTram" titleKey="hcell.khoangCachTram"/> 
			
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu cell 3g hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
							<display:column class="centerColumnMana" titleKey="global.list.STT" > <c:out value="${item2_rowNum}"/> </display:column>
							<display:column property="bscid" titleKey="hCell3g.bscid" class="NOT_NULL"/>
							<display:column property="type" titleKey="hCell3g.type"/>
							<display:column property="siteid" titleKey="hCell3g.siteid" />
							<display:column property="siteName" titleKey="hCell3g.sitename" />
							<display:column property="cellid" titleKey="hCell3g.cellid" class="NOT_NULL"/>  
							<display:column property="cellName" titleKey="hCell3g.cellname"/>
							<display:column property="vendor" titleKey="hCell3g.vendor"/>
							<display:column property="vendorTrans" titleKey="hCell.vendorTrans"/>
							<display:column property="transType" titleKey="hCell3g.transType"/>
							<display:column property="bandwidth" titleKey="hCell.bandwidth"/>
							<display:column property="nextHop" titleKey="hCell.nextHop"/>
							<display:column property="subscriber" titleKey="hCell.subscriber"/>
							<display:column property="region" titleKey="hCell3g.region"/>
							<display:column property="area" titleKey="hCell3g.area"/>
							<display:column property="province" titleKey="hCell3g.province"/>
							<display:column property="district" titleKey="hCell3g.district"/>
							<display:column property="villageName" titleKey="hCell3g.villageName"/>
							<display:column property="address" titleKey="hCell3g.address"/>
							<display:column property="longitude" titleKey="hCell3g.longitude"/>
							<display:column property="latitude" titleKey="hCell3g.latitude"/>
							<display:column property="cid" titleKey="hCell3g.cid"/>
							<display:column property="lac" titleKey="hCell3g.lac"/>
							<display:column property="cellBorder" titleKey="hCell3g.cellBorder"/>
							<display:column property="cellBlacklist" titleKey="hCell3g.cellBlacklist"/>
							<display:column property ="seaCell" titleKey="hCell.seaCell"/> 
						    <display:column property ="specialCell" titleKey="hCell.specialCell"/> 
						    <display:column property ="siteLevel" titleKey="hCell.siteLevel" /> 
						    <display:column property ="transConfigs" titleKey="hCell.transConfigs" /> 
						    <display:column property ="delayStandar" titleKey="hCell.delayStandar" /> 
						    <display:column property ="launchDateI"  titleKey="hCell.launchDate"/> 
							<display:column property ="lastActiveI" titleKey="hCell.lastActive"/> 
						   	<display:column property ="isIbc" titleKey="hCell.isIbc"/> 
						   	<display:column property ="batteryDuration" titleKey="hCell.batteryDuration"/>
						   	<display:column property ="phone" titleKey="hCell.phone"/>  
						   	<display:column property ="targetUctt" titleKey="hCell.targetUCTT"/> 
						   	<display:column property ="tgUcttdhNgay" titleKey="hCell3g.tgUcttdhNgay"/> 
						   	<display:column property ="tgUcttdhDem" titleKey="hCell3g.tgUcttdhDem"/>
						   	<display:column property ="tgBackupAcquyMoi" titleKey="hCell.tgBackupAcquyMoi"/> 
						   	<display:column property ="tgBackupAcquyCu" titleKey="hCell.tgBackupAcquyCu"/> 
						   	<display:column property ="timeCapnhatMoi1" titleKey="hCell.timeCapnhatMoi"/> 
						   	<display:column property ="timeCapnhatCu1" titleKey="hCell.timeCapnhatCu"/> 
						   	<display:column property ="doLechTgBackupAcquy" titleKey="hCell.doLechTgBackupAcquy"/> 
							<display:column property="description" titleKey="hCell3g.description"/>
						   	<display:column property ="createDate" titleKey="hcell.createDate"/> 
						   	<display:column property ="siteDistance" titleKey="hcell.siteDistance"/> 
						   	<display:column property ="bandwidthStr" titleKey="hcell.bandwidthStr"/> 
						   	<display:column property ="subscribersInfo" titleKey="hcell.subscribersInfo"/> 
						   	<display:column property ="khoangCachTram" titleKey="hcell.khoangCachTram"/> 
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
