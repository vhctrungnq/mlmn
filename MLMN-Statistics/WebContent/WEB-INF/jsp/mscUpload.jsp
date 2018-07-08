<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>
<title><fmt:message key="title.hMsc.formUpload"/></title>
<content tag="heading"><fmt:message key="title.hMsc.formUpload"/></content>

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
							&lt;<fmt:message key="hMsc.mscid"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="hMsc.vendor"/>&gt;, 
							&lt;<fmt:message key="hMsc.location"/>&gt;, 
							&lt;<fmt:message key="hMsc.locationName"/>&gt;, 
							&lt;<fmt:message key="hMsc.msuCapacity"/>&gt;, 
							&lt;<fmt:message key="hMsc.longitude"/>&gt;, 
							&lt;<fmt:message key="hMsc.latitude"/>&gt;, 
							&lt;<fmt:message key="hMsc.dept"/>&gt;,
							&lt;<fmt:message key="hMsc.team"/>&gt;,
							&lt;<fmt:message key="hMsc.subTeam"/>&gt;,
							&lt;<fmt:message key="hMsc.neGroup"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="MscExample" href="${pageContext.request.contextPath}/upload/MscExample.xls">MscExample.xls</a>
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
    		<div><b>Dữ liệu msc không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
							<display:column class="centerColumnMana" titleKey="global.list.STT" > <c:out value="${item1_rowNum}"/> </display:column>
							<display:column property="mscid" titleKey="hMsc.mscid" class="NOT_NULL"/>
							<display:column property="vendor" titleKey="hMsc.vendor" />  
							<display:column property="location" titleKey="hMsc.location"/>
							<display:column property="locationName" titleKey="hMsc.locationName"/>
							<display:column class="rightColumnMana" property="msuCapacity" titleKey="hMsc.msuCapacity" />
							<display:column property="longitude" titleKey="hMsc.longitude" />
							<display:column property="latitude" titleKey="hMsc.latitude"/>
							<display:column property="dept" titleKey="hMsc.dept"/>
							<display:column property="team" titleKey="hMsc.team"/>
							<display:column property="subTeam" titleKey="hMsc.subTeam"/>
							<display:column property="neGroup" titleKey="hMsc.neGroup"/>
							
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu msc hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
							<display:column class="centerColumnMana" titleKey="global.list.STT" > <c:out value="${item2_rowNum}"/> </display:column>
							<display:column property="mscid" titleKey="hMsc.mscid" class="NOT_NULL"/>
							<display:column property="vendor" titleKey="hMsc.vendor" />  
							<display:column property="location" titleKey="hMsc.location"/>
							<display:column property="locationName" titleKey="hMsc.locationName"/>
							<display:column class="rightColumnMana" property="msuCapacity" titleKey="hMsc.msuCapacity" />
							<display:column property="longitude" titleKey="hMsc.longitude" />
							<display:column property="latitude" titleKey="hMsc.latitude"/>
							<display:column property="dept" titleKey="hMsc.dept"/>
							<display:column property="team" titleKey="hMsc.team"/>
							<display:column property="subTeam" titleKey="hMsc.subTeam"/>
							<display:column property="neGroup" titleKey="hMsc.neGroup"/>	
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
