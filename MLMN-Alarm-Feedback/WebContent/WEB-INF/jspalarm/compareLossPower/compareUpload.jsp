<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title>${titleU}</title>
<content tag="heading">${titleU}</content> 
 <form:form  method="post" action="compareUpload.htm" enctype="multipart/form-data">
 <input type="hidden" name="type" id="type" value="${type}">
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
						<li>Cấu trúc file: 
    					<code>&lt;STT&gt;,
    						&lt;<fmt:message key="comparePower.bscid"/>,
    						&lt;<fmt:message key="comparePower.site"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="comparePower.siteName"/>&gt;,
    						&lt;<fmt:message key="comparePower.province"/>&gt;,
    						&lt;<fmt:message key="comparePower.district"/>&gt;,
    						&lt;<fmt:message key="comparePower.dept"/>&gt;,
    						&lt;<fmt:message key="comparePower.team"/>&gt;,
    						&lt;<fmt:message key="comparePower.groups"/>&gt;,
    						&lt;<fmt:message key="comparePower.sdate"/>&gt;,
    						&lt;<fmt:message key="comparePower.edate"/>&gt;,
    						&lt;<fmt:message key="comparePower.totalLog"/>&gt;,
    						&lt;<fmt:message key="comparePower.shiftSdate"/>&gt;,
    						&lt;<fmt:message key="comparePower.shiftEdate"/>&gt;,
    						&lt;<fmt:message key="comparePower.totalCompare"/>&gt;,
    						&lt;<fmt:message key="comparePower.dolech"/>&gt;,
    						&lt;<fmt:message key="comparePower.assess"/>&gt;,
    						&lt;<fmt:message key="comparePower.description"/>&gt;,
    						&lt;<fmt:message key="comparePower.shiftUser"/>&gt;,
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/DoiSoatMatDien.xls" title="Đối soát mất điện chạy máy nổ" style="color: blue; ">DoiSoatMatDien.xls</a></li>
				<li><fmt:message key="global.chuY1"/></li>
    			
    			</ul>
    		</td>
    	</tr>
    	
    </table>
    
    <c:if test="${status != null}">
    	<div class="error">${status}</div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item1_rowNum}" />
					</display:column>
					<display:column property="bscid" titleKey="comparePower.bscid"/>
					<display:column property="cellid" titleKey="comparePower.site"   class="NOT_NULL"/>
					<display:column property="siteName" titleKey="comparePower.siteName"/>
					<display:column property="province" titleKey="comparePower.province"/>
					<display:column property="district" titleKey="comparePower.district"/>
					
					<display:column property="dept" titleKey="comparePower.dept" />
					<display:column property="team" titleKey="comparePower.team" />
					<display:column property="groups" titleKey="comparePower.groups"/>
					<display:column property="sdateI" titleKey="comparePower.sdate"/>
					<display:column property="edateI" titleKey="comparePower.edate"/>
					<display:column property="shiftSdateI" titleKey="comparePower.shiftSdate"/>
					<display:column property="shiftEdateI" titleKey="comparePower.shiftEdate"/>
					<display:column property="description" titleKey="comparePower.description"/>
					<display:column property="shiftUser" titleKey="comparePower.shiftUser"/>
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item2_rowNum}" />
					</display:column>
					<display:column property="bscid" titleKey="comparePower.bscid"/>
					<display:column property="cellid" titleKey="comparePower.site"   class="NOT_NULL"/>
					<display:column property="siteName" titleKey="comparePower.siteName"/>
					<display:column property="province" titleKey="comparePower.province"/>
					<display:column property="district" titleKey="comparePower.district"/>
					
					<display:column property="dept" titleKey="comparePower.dept" />
					<display:column property="team" titleKey="comparePower.team" />
					<display:column property="groups" titleKey="comparePower.groups"/>
					<display:column property="sdateStr" titleKey="comparePower.sdate"/>
					<display:column property="edateStr" titleKey="comparePower.edate"/>
					<display:column property="shiftSdateStr" titleKey="comparePower.shiftSdate"/>
					<display:column property="shiftEdateStr" titleKey="comparePower.shiftEdate"/>
					<display:column property="description" titleKey="comparePower.description"/>
					<display:column property="shiftUser" titleKey="comparePower.shiftUser"/>
				</display:table>
			</div>
		</div>
	</c:if>
    
	<table>
		<tr>
			<td>
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="compareList.htm?type=${type}"'>
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