<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title><fmt:message key="title.hsgsn.formUpload"/></title>
<content tag="heading"><fmt:message key="title.hsgsn.formUpload"/></content>
 	
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
							&lt;<fmt:message key="hsgsn.region"/>&gt;, 
							&lt;<fmt:message key="hsgsn.sgsnid"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="hsgsn.sgsnName"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="hsgsn.gbAttachCapacityLicense"/>&gt;, 
							&lt;<fmt:message key="hsgsn.totalAttachCapacityLicense"/>&gt;, 
							&lt;<fmt:message key="hsgsn.thoughputCapacityLicense"/>&gt;, 
							&lt;<fmt:message key="hsgsn.pdpContextCapacityLicense"/>&gt;, 
							&lt;<fmt:message key="hsgsn.locationName"/>&gt;,
							&lt;<fmt:message key="hMsc.dept"/>&gt;,
							&lt;<fmt:message key="hMsc.team"/>&gt;,
							&lt;<fmt:message key="hMsc.subTeam"/>&gt;,
							&lt;<fmt:message key="hMsc.neGroup"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="SgsnExample" href="${pageContext.request.contextPath}/upload/SgsnExample.xls">SgsnExample.xls</a>
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
    		<div><b>Dữ liệu sgsn không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
							<display:column class="centerColumnMana" titleKey="global.list.STT" > <c:out value="${item1_rowNum}"/> </display:column>
							<display:column property="region" titleKey="hsgsn.region" />  
							<display:column property="sgsnid" titleKey="hsgsn.sgsnid" class="NOT_NULL"/>
							<display:column property="sgsnName" titleKey="hsgsn.sgsnName" class="NOT_NULL"/>  
							<display:column class="rightColumnMana" property="gbAttachCapacityLicense" titleKey="hsgsn.gbAttachCapacityLicense"/>
							<display:column class="rightColumnMana" property="totalAttachCapacityLicense" titleKey="hsgsn.totalAttachCapacityLicense"/>
							<display:column class="rightColumnMana" property="thoughputCapacityLicense" titleKey="hsgsn.thoughputCapacityLicense" />
							<display:column class="rightColumnMana" property="pdpContextCapacityLicense" titleKey="hsgsn.pdpContextCapacityLicense" />
							<display:column property="locationName" titleKey="hsgsn.locationName"/>
							<display:column property="dept" titleKey="hsgsn.dept"/>
							<display:column property="team" titleKey="hsgsn.team"/>
							<display:column property="subTeam" titleKey="hsgsn.subTeam"/>
							<display:column property="neGroup" titleKey="hsgsn.neGroup"/>
							
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu sgsn hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
							<display:column class="centerColumnMana" titleKey="global.list.STT" > <c:out value="${item2_rowNum}"/> </display:column>
							<display:column property="region" titleKey="hsgsn.region" />  
							<display:column property="sgsnid" titleKey="hsgsn.sgsnid" class="NOT_NULL"/>
							<display:column property="sgsnName" titleKey="hsgsn.sgsnName" class="NOT_NULL"/>  
							<display:column class="rightColumnMana" property="gbAttachCapacityLicense" titleKey="hsgsn.gbAttachCapacityLicense"/>
							<display:column class="rightColumnMana" property="totalAttachCapacityLicense" titleKey="hsgsn.totalAttachCapacityLicense"/>
							<display:column class="rightColumnMana" property="thoughputCapacityLicense" titleKey="hsgsn.thoughputCapacityLicense" />
							<display:column class="rightColumnMana" property="pdpContextCapacityLicense" titleKey="hsgsn.pdpContextCapacityLicense" />
							<display:column property="locationName" titleKey="hsgsn.locationName"/>
							<display:column property="dept" titleKey="hsgsn.dept"/>
							<display:column property="team" titleKey="hsgsn.team"/>
							<display:column property="subTeam" titleKey="hsgsn.subTeam"/>
							<display:column property="neGroup" titleKey="hsgsn.neGroup"/>	
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
