<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>
<title><fmt:message key="title.cableTransmissionPartners.formUpload"/></title>
<content tag="heading"><fmt:message key="title.cableTransmissionPartners.formUpload"/></content>
 	
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
							&lt;<fmt:message key="cableTransmissionPartners.vendor"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="cableTransmissionPartners.odfName"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="cableTransmissionPartners.fiberCable"/>&gt;, 
							&lt;<fmt:message key="cableTransmissionPartners.odfPos"/>&gt;,
							&lt;<fmt:message key="cableTransmissionPartners.transmission"/>&gt;,  
							&lt;<fmt:message key="cableTransmissionPartners.threadName"/>&gt;,
							&lt;<fmt:message key="cableTransmissionPartners.speed"/>&gt;,
							&lt;<fmt:message key="cableTransmissionPartners.odfDestination"/>&gt;,
							&lt;<fmt:message key="cableTransmissionPartners.portDestination"/>&gt;,
							&lt;<fmt:message key="cableTransmissionPartners.purposeUse"/>&gt;,
							&lt;<fmt:message key="cableTransmissionPartners.site"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="CableTransmissionPartnersExample" href="${pageContext.request.contextPath}/upload/example/CableTransmissionPartnersExample.xls">CableTransmissionPartnersExample.xls</a>
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
    		<div><b>Dữ liệu truyền dẫn thuê đối tác không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
							<display:column class="centerColumnMana" title="No." > <c:out value="${item1_rowNum}"/> </display:column>
							<display:column property="vendor" titleKey="cableTransmissionPartners.vendor" class="NOT_NULL"/>
							<display:column property="odfName" titleKey="cableTransmissionPartners.odfName" class="NOT_NULL"/>
							<display:column property="fiberCable" titleKey="cableTransmissionPartners.fiberCable" />  
							<display:column property="odfPos" titleKey="cableTransmissionPartners.odfPos" />  
							<display:column property="transmission" titleKey="cableTransmissionPartners.transmission" />
							<display:column property="threadName" titleKey="cableTransmissionPartners.threadName" />
							<display:column property="speed" titleKey="cableTransmissionPartners.speed" />  
							<display:column property="odfDestination" titleKey="cableTransmissionPartners.odfDestination" />  
							<display:column property="portDestination" titleKey="cableTransmissionPartners.portDestination" />
							<display:column property="purposeUse" titleKey="cableTransmissionPartners.purposeUse" />
							<display:column property="site" titleKey="cableTransmissionPartners.site" />		
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu truyền dẫn thuê đối tác hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
							<display:column class="centerColumnMana" title="No." > <c:out value="${item2_rowNum}"/> </display:column>
							<display:column property="vendor" titleKey="cableTransmissionPartners.vendor" class="NOT_NULL"/>
							<display:column property="odfName" titleKey="cableTransmissionPartners.odfName" class="NOT_NULL"/>
							<display:column property="fiberCable" titleKey="cableTransmissionPartners.fiberCable" />  
							<display:column property="odfPos" titleKey="cableTransmissionPartners.odfPos" />  
							<display:column property="transmission" titleKey="cableTransmissionPartners.transmission" />
							<display:column property="threadName" titleKey="cableTransmissionPartners.threadName" />
							<display:column property="speed" titleKey="cableTransmissionPartners.speed" />  
							<display:column property="odfDestination" titleKey="cableTransmissionPartners.odfDestination" />  
							<display:column property="portDestination" titleKey="cableTransmissionPartners.portDestination" />
							<display:column property="purposeUse" titleKey="cableTransmissionPartners.purposeUse" />
							<display:column property="site" titleKey="cableTransmissionPartners.site" />		
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