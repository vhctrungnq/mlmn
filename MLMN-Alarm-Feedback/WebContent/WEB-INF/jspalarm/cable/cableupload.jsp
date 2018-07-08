<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
 	#success { overflow: auto; overflow-y: hidden; }  
    #success p { margin: 0; padding: 1em; white-space: nowrap; }
    
    #failed { overflow: auto; overflow-y: hidden; }  
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<%-- <title><fmt:message key="cableTransmission.upload"/></title>--%>
<title>${titleU}</title>
<content tag="heading">${titleU}</content> 

<content tag="heading"><fmt:message key="cableTransmission.upload"/></content>
 <form:form  method="post" action="upload.htm?typeC=${typeC}" enctype="multipart/form-data">
 <%-- <input type="hidden" name="typeC" id="typeC" value="${typeC}">--%>
	<table class="simple2">	
    	<tr style="height:20px;" >
    		<td width="150px"><b><fmt:message key="cautruc.filepath"/> <font color="red">(*)</font></b></td>
    		<td><input type="file" size="110" name="filePath" id="filePath" class="button" />
    			<input type="submit" name="upload" id="upload" class="button" value="<fmt:message key="global.button.import"/>" />
    		</td>
    	</tr>
    	<tr>
    		<td><b>
    			<fmt:message key="global.FileExample"/></b>
    		</td>
    		<td>
    		<ul>
    			<li><fmt:message key="global.formatFile"/></li>
    			<c:choose>
    				<c:when test="${typeC == 'GMSC'}">
						<li>Cấu trúc file: 
    					<code>&lt;STT&gt;,
    						&lt;<fmt:message key="cableTransmission.cableId"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.flowConnection"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.enSource"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPortSource"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfDdfSource"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfDdfVendor1"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.transmission"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfDdfVendor2"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPortDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.enDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.vendor"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.bandwith"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.description"/>&gt;
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/CapTruyenDan_GMSC.xls" title="Danh sách cáp" style="color: blue; ">CapTruyenDan_GMSC.xls</a></li>
    			
					</c:when>
					<c:when test="${typeC == 'NodeTDQH'}">
						<li>Cấu trúc file: 
    					<code>&lt;STT&gt;,
    						&lt;<fmt:message key="cableTransmission.cableId"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.flowConnection"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.enSource"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPortSource"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfSource"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.positionEt"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPort1"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odf1"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPort2"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odf2"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfVendor1"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.transmission"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfVendor2"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPortDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.enDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.vendor"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.bandwith"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.description"/>&gt;
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/CapTruyenDan_NodeTDQH.xls" title="Danh sách cáp" style="color: blue; ">CapTruyenDan_BSC_NodeTDQH.xls</a></li>
    			
					</c:when>
					<c:when test="${typeC == 'BSC'}">
						<li>Cấu trúc file: 
    					<code>&lt;STT&gt;,
    						&lt;<fmt:message key="cableTransmission.cableId"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.flowConnection"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.enSource"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPortSource"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfSource"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.positionEt"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPort1"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odf1"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPort2"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odf2"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfVendor1"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.transmission"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfVendor2"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPortDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.enDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.vendor"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.bandwith"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.description"/>&gt;
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/CapTruyenDan_BSC.xls" title="Danh sách cáp" style="color: blue; ">CapTruyenDan_BSC.xls</a></li>
    			
					</c:when>
					<c:when test="${typeC == 'BSC_MGW'}">
						<li>Cấu trúc file: 
    					<code>&lt;STT&gt;,
    						&lt;<fmt:message key="cableTransmission.cableId"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.flowConnection"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.enSource"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPortSource"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfSource"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.positionEt"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPort1"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odf1"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPort2"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odf2"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfVendor1"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.transmission"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfVendor2"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfReplace"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPortDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.enDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.vendor"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.bandwith"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.description"/>&gt;
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/CapTruyenDan_BSC_MGW.xls" title="Danh sách cáp" style="color: blue; ">CapTruyenDan_BSC_MGW.xls</a></li>
    				</c:when>
					<c:when test="${typeC == 'IPBB_LTT'}">
							<li>Cấu trúc file: 
    					<code>&lt;STT&gt;,
    						&lt;<fmt:message key="cableTransmission.cableId"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.flowConnection"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.enSource"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPortSource"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfSource"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfOsn1"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfOsn2"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfVendor1"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.transmission"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfVendor2"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfOsnLtt3"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfOsnLtt4"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPortDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.enDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.vendor"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.bandwith"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.description"/>&gt;
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/CapTruyenDan_IPBB_LTT.xls" title="Danh sách cáp" style="color: blue; ">CapTruyenDan_IPBB_LTT.xls</a></li>
	    			</c:when>
					<c:otherwise>
						<li>Cấu trúc file: 
    					<code>&lt;STT&gt;,
    						&lt;<fmt:message key="cableTransmission.cableId"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.flowConnection"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.enSource"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPortSource"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfSource"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfVendor1"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.transmission"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfVendor2"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.odfDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.cardPortDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.enDestination"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.vendor"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.bandwith"/>&gt;,
    						&lt;<fmt:message key="cableTransmission.description"/>&gt;
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/CapTruyenDan_IPBB.xls" title="Danh sách cáp" style="color: blue; ">CapTruyenDan_IPBB.xls</a></li>
	    			</c:otherwise>
				</c:choose>
				<li><fmt:message key="global.chuY1"/></li>
    			</ul>
    			
    		</td>
    	</tr>
    	
    </table>
    
    <c:if test="${status != null}">
    	<div class="error">${status} ${statusExists}</div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu cáp không hợp lệ ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item1_rowNum}" />
					</display:column>
					<display:column property="directionId" titleKey="cableTransmission.directionId" class="NOT_NULL"/>
					<display:column property="cableId" titleKey="cableTransmission.cableId" class="NOT_NULL"/>
					<display:column property="flowConnection" titleKey="cableTransmission.flowConnection" class="NOT_NULL"/>
					<display:column property="enSource" titleKey="cableTransmission.enSource" class="NOT_NULL"/>
					<display:column property="cardPortSource" titleKey="cableTransmission.cardPortSource"/>
					<c:choose>
			            <c:when test="${typeC == 'GMSC'}">
			               <display:column property="odfSource" titleKey="cableTransmission.odfDdfSource"/>
						</c:when>
						<c:otherwise>
							<display:column property="odfSource" titleKey="cableTransmission.odfSource"/>
						</c:otherwise>
					</c:choose>
				
					<c:if test="${typeC == 'BSC' || typeC == 'BSC_MGW'}">
						<display:column property="positionEt" titleKey="cableTransmission.positionEt"/>
						<display:column property="cardPort1" titleKey="cableTransmission.cardPort1"/>
						<display:column property="odf1" titleKey="cableTransmission.odf1"/>
						<display:column property="cardPort2" titleKey="cableTransmission.cardPort2"/>
						<display:column property="odf2" titleKey="cableTransmission.odf2"/>
				   	</c:if>
					<c:if test="${typeC == 'IPBB_LTT'}">
						<display:column property="odf1" titleKey="cableTransmission.odfOsn1"/>
						<display:column property="odf2" titleKey="cableTransmission.odfOsn2"/>
					</c:if>
					<c:choose>
			            <c:when test="${typeC == 'GMSC'}">
			               <display:column property="odfVendor1" titleKey="cableTransmission.odfDdfVendor1"/>
							<display:column property="transmission" titleKey="cableTransmission.transmission"/>
							<display:column property="odfVendor2" titleKey="cableTransmission.odfDdfVendor2"/>
						</c:when>
						<c:otherwise>
							<display:column property="odfVendor1" titleKey="cableTransmission.odfVendor1"/>
							<display:column property="transmission" titleKey="cableTransmission.transmission"/>
							<display:column property="odfVendor2" titleKey="cableTransmission.odfVendor2"/>
						</c:otherwise>
					</c:choose>
					<c:if test="${typeC == 'BSC_MGW'}">
						<display:column property="odfReplace" titleKey="cableTransmission.odfReplace" />
				   	</c:if>
				   	<c:if test="${typeC == 'IPBB_LTT'}">
						<display:column property="odfOsnLtt3" titleKey="cableTransmission.odfOsnLtt3"/>
						<display:column property="odfOsnLtt4" titleKey="cableTransmission.odfOsnLtt4"/>
				   	</c:if>
					<display:column property="odfDestination" titleKey="cableTransmission.odfDestination"/>
					<display:column property="cardPortDestination" titleKey="cableTransmission.cardPortDestination"/>
					
					<display:column property="enDestination" titleKey="cableTransmission.enDestination"/>
					<display:column property="vendor" titleKey="cableTransmission.vendor"/>
					<display:column property="bandwith" titleKey="cableTransmission.bandwith"/>
					<display:column property="description" titleKey="cableTransmission.description" />
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu cáp hợp lệ ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item2_rowNum}" />
					</display:column>
					<display:column property="directionId" titleKey="cableTransmission.directionId" class="NOT_NULL"/>
					<display:column property="cableId" titleKey="cableTransmission.cableId" class="NOT_NULL"/>
					<display:column property="flowConnection" titleKey="cableTransmission.flowConnection" class="NOT_NULL"/>
					<display:column property="enSource" titleKey="cableTransmission.enSource" class="NOT_NULL"/>
					<display:column property="cardPortSource" titleKey="cableTransmission.cardPortSource"/>
					<c:choose>
			            <c:when test="${typeC == 'GMSC'}">
			               <display:column property="odfSource" titleKey="cableTransmission.odfDdfSource"/>
						</c:when>
						<c:otherwise>
							<display:column property="odfSource" titleKey="cableTransmission.odfSource"/>
						</c:otherwise>
					</c:choose>
				
					<c:if test="${typeC == 'BSC' || typeC == 'BSC_MGW'}">
						<display:column property="positionEt" titleKey="cableTransmission.positionEt"/>
						<display:column property="cardPort1" titleKey="cableTransmission.cardPort1"/>
						<display:column property="odf1" titleKey="cableTransmission.odf1"/>
						<display:column property="cardPort2" titleKey="cableTransmission.cardPort2"/>
						<display:column property="odf2" titleKey="cableTransmission.odf2"/>
				   	</c:if>
					<c:if test="${typeC == 'IPBB_LTT'}">
						<display:column property="odf1" titleKey="cableTransmission.odfOsn1"/>
						<display:column property="odf2" titleKey="cableTransmission.odfOsn2"/>
					</c:if>
					<c:choose>
			            <c:when test="${typeC == 'GMSC'}">
			               <display:column property="odfVendor1" titleKey="cableTransmission.odfDdfVendor1"/>
							<display:column property="transmission" titleKey="cableTransmission.transmission"/>
							<display:column property="odfVendor2" titleKey="cableTransmission.odfDdfVendor2"/>
						</c:when>
						<c:otherwise>
							<display:column property="odfVendor1" titleKey="cableTransmission.odfVendor1"/>
							<display:column property="transmission" titleKey="cableTransmission.transmission"/>
							<display:column property="odfVendor2" titleKey="cableTransmission.odfVendor2"/>
						</c:otherwise>
					</c:choose>
					<c:if test="${typeC == 'BSC_MGW'}">
						<display:column property="odfReplace" titleKey="cableTransmission.odfReplace" />
				   	</c:if>
				   	<c:if test="${typeC == 'IPBB_LTT'}">
						<display:column property="odfOsnLtt3" titleKey="cableTransmission.odfOsnLtt3"/>
						<display:column property="odfOsnLtt4" titleKey="cableTransmission.odfOsnLtt4"/>
				   	</c:if>
					<display:column property="odfDestination" titleKey="cableTransmission.odfDestination"/>
					<display:column property="cardPortDestination" titleKey="cableTransmission.cardPortDestination"/>
					
					<display:column property="enDestination" titleKey="cableTransmission.enDestination"/>
					<display:column property="vendor" titleKey="cableTransmission.vendor"/>
					<display:column property="bandwith" titleKey="cableTransmission.bandwith"/>
					<display:column property="description" titleKey="cableTransmission.description" />
				</display:table>
			</div>
		</div>
	</c:if>
    
	<table>
		<tr>
			<td>
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm?typeC=${typeC}"'>
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