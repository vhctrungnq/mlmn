<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title>${titleU}</title>
<content tag="heading">${titleU}</content> 
 <form:form  method="post" action="upload.htm?typeC=${typeC}" enctype="multipart/form-data">
<%--  <input type="hidden" name="typeC" id="typeC" value="${typeC}"> --%>
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
    				<c:when test="${typeC == 'E1_BSG'}">
						<li>Cấu trúc file: 
    					<code>&lt;STT&gt;,
    						&lt;<fmt:message key="cableE1.rp"/>&gt;,
    						&lt;<fmt:message key="cableE1.em"/>&gt;,
    						&lt;<fmt:message key="cableE1.dev"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableE1.snt"/>&gt;,
    						&lt;<fmt:message key="cableE1.sntinl"/>&gt;,
    						&lt;<fmt:message key="cableE1.dip"/>&gt;,
    						&lt;<fmt:message key="cableE1.ddfOut"/>&gt;,
    						&lt;<fmt:message key="cableE1.state"/>&gt;,
    						&lt;<fmt:message key="cableE1.node"/>&gt;,
    						&lt;<fmt:message key="cableE1.planeNext"/>&gt;,
    						&lt;<fmt:message key="cableE1.directionTransmission"/>&gt;,
    						&lt;<fmt:message key="cableE1.description"/>&gt;,
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/CableE1_BSG.xls" title="Danh sách cáp" style="color: blue; ">CableE1_BSG.xls</a></li>
    			
					</c:when>
					<c:otherwise>
						<li>Cấu trúc file: 
    					<code>&lt;STT&gt;,
    						&lt;<fmt:message key="cableE1.rp"/>&gt;,
    						&lt;<fmt:message key="cableE1.em"/>&gt;,
    						&lt;<fmt:message key="cableE1.dev"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cableE1.snt"/>&gt;,
    						&lt;<fmt:message key="cableE1.sntinl"/>&gt;,
    						&lt;<fmt:message key="cableE1.dip"/>&gt;,
    						&lt;<fmt:message key="cableE1.ddfOut"/>&gt;,
    						&lt;<fmt:message key="cableE1.state"/>&gt;,
    						&lt;<fmt:message key="cableE1.node"/>&gt;,
    						&lt;<fmt:message key="cableE1.planeNext"/>&gt;,
    						&lt;<fmt:message key="cableE1.description"/>&gt;,
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/CableE1.xls" title="Danh sách cáp" style="color: blue; ">CableE1.xls</a></li>
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
    		<div><b>Dữ liệu cáp không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item1_rowNum}" />
					</display:column>
					<display:column property="directionId" titleKey="cableE1.directionId" class="NOT_NULL"/>
					<display:column property="rp" titleKey="cableE1.rp"/>
					<display:column property="em" titleKey="cableE1.em"/>
					<display:column property="dev" titleKey="cableE1.dev"  class="NOT_NULL"/>
					<display:column property="snt" titleKey="cableE1.snt"/>
					
					<display:column property="dip" titleKey="cableE1.dip" />
					<display:column property="sntinlStr" titleKey="cableE1.sntinl" />
					<display:column property="ddfOut" titleKey="cableE1.ddfOut"/>
					<display:column property="state" titleKey="cableE1.state"/>
					<display:column property="node" titleKey="cableE1.node"/>
					<display:column property="planeNext" titleKey="cableE1.planeNext"/>
					<c:if test="${typeC == 'E1_BSG'}">
						<display:column property="directionTransmission" titleKey="cableE1.directionTransmission"/>
					</c:if>
					<display:column property="description" titleKey="cableE1.description"/>
					
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu cáp hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item2_rowNum}" />
					</display:column>
					<display:column property="directionId" titleKey="cableE1.directionId" class="NOT_NULL"/>
					<display:column property="rp" titleKey="cableE1.rp"/>
					<display:column property="em" titleKey="cableE1.em"/>
					<display:column property="dev" titleKey="cableE1.dev"  class="NOT_NULL"/>
					<display:column property="snt" titleKey="cableE1.snt"/>
					
					<display:column property="dip" titleKey="cableE1.dip" />
					<display:column property="sntinlStr" titleKey="cableE1.sntinl" />
					<display:column property="ddfOut" titleKey="cableE1.ddfOut"/>
					<display:column property="state" titleKey="cableE1.state"/>
					<display:column property="node" titleKey="cableE1.node"/>
					<display:column property="planeNext" titleKey="cableE1.planeNext"/>
					<c:if test="${typeC == 'E1_BSG'}">
						<display:column property="directionTransmission" titleKey="cableE1.directionTransmission"/>
					</c:if>
					<display:column property="description" titleKey="cableE1.description"/>
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