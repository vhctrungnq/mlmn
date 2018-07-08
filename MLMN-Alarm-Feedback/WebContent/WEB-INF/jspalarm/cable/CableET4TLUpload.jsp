<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
 	#success { overflow: auto; overflow-y: hidden; }  
    #success p { margin: 0; padding: 1em; white-space: nowrap; }
    
    #failed { overflow: auto; overflow-y: hidden; }  
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title>${titleU}</title>
<content tag="heading">${titleU}</content>
 <form:form  method="post" action="upload.htm" enctype="multipart/form-data">
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
    					<code>
    						&lt;<fmt:message key="cabledropet4tl.area"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cabledropet4tl.dipL2Name"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cabledropet4tl.labelL2Name"/>&gt;,
    						&lt;<fmt:message key="cabledropet4tl.locationL2"/>&gt;,
    						&lt;<fmt:message key="cabledropet4tl.dipL10Name"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cabledropet4tl.labelL10Name"/>&gt;,
    						&lt;<fmt:message key="cabledropet4tl.locationL10"/>&gt;,
    						&lt;<fmt:message key="cabledropet4tl.locationL10New"/>&gt;,
    						&lt;<fmt:message key="cabledropet4tl.locationSgsn"/>&gt;,
    						&lt;<fmt:message key="cabledropet4tl.et4L2L10"/>&gt;,
    						&lt;<fmt:message key="cabledropet4tl.description"/>&gt;
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/ET4TL.xls" title="Danh sách thông tin ET4 Thông Lầu" style="color: blue; ">ET4TL.xls</a></li>
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
    		<div><b>Dữ liệu DROP ET4 Thông Lầu không hợp lệ ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item1_rowNum}" />
					</display:column>
					<display:column property="area" titleKey="cabledropet4tl.area"  class="NOT_NULL"/>
					<display:column property="directionCon" titleKey="cabledropet4tl.directionCon"  class="NOT_NULL"/>
				  	<display:column property="dipL2Name" titleKey="cabledropet4tl.dipL2Name" class="NOT_NULL"/>
				  	<display:column property="labelL2Name" titleKey="cabledropet4tl.labelL2Name"/>
					<display:column property="locationL2" titleKey="cabledropet4tl.locationL2"/>
					<display:column property="dipL10Name" titleKey="cabledropet4tl.dipL10Name" class="NOT_NULL"/>
					<display:column property="labelL10Name" titleKey="cabledropet4tl.labelL10Name"/>
					<display:column property="locationL10" titleKey="cabledropet4tl.locationL10"/>
					<display:column property="locationL10New" titleKey="cabledropet4tl.locationL10New"/>
					<display:column property="locationSgsn" titleKey="cabledropet4tl.locationSgsn"/>
					<display:column property="et4L2L10" titleKey="cabledropet4tl.et4L2L10"/>
					<display:column property="description" titleKey="cabledropet4tl.description"/>
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu DROP ET4 Thông Lầu hợp lệ ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item2_rowNum}" />
					</display:column>
					<display:column property="area" titleKey="cabledropet4tl.area" class="NOT_NULL"/>
					<display:column property="directionCon" titleKey="cabledropet4tl.directionCon" class="NOT_NULL"/>
				  	<display:column property="dipL2Name" titleKey="cabledropet4tl.dipL2Name" class="NOT_NULL"/>
				  	<display:column property="labelL2Name" titleKey="cabledropet4tl.labelL2Name"/>
					<display:column property="locationL2" titleKey="cabledropet4tl.locationL2" />
					<display:column property="dipL10Name" titleKey="cabledropet4tl.dipL10Name" class="NOT_NULL"/>
					<display:column property="labelL10Name" titleKey="cabledropet4tl.labelL10Name"/>
					<display:column property="locationL10" titleKey="cabledropet4tl.locationL10"/>
					<display:column property="locationL10New" titleKey="cabledropet4tl.locationL10New"/>
					<display:column property="locationSgsn" titleKey="cabledropet4tl.locationSgsn"/>
					<display:column property="et4L2L10" titleKey="cabledropet4tl.et4L2L10"/>
					<display:column property="description" titleKey="cabledropet4tl.description"/>
				</display:table>
			</div>
		</div>
	</c:if>
    
	<table>
		<tr>
			<td>
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