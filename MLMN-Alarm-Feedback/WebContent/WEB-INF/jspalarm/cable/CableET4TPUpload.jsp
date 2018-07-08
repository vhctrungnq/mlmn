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
    		<ul style="list-style-type: none;">
    			<li><fmt:message key="global.formatFile"/></li>
    			<li>Cấu trúc file: 
    					<code>
    						&lt;<fmt:message key="cabledropet4tp.area"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cabledropet4tp.ddfStart"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cabledropet4tp.transission"/>&gt;,
    						&lt;<fmt:message key="cabledropet4tp.channelName"/>&gt;,
    						&lt;<fmt:message key="cabledropet4tp.ddfFinish"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="cabledropet4tp.description"/>&gt;
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/DropET4TP.xls" title="Danh sách thông tin đầu mối liên lạc" style="color: blue; ">DropET4TP.xls</a></li>
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
    		<div><b>Dữ liệu DROP ET4 Thông Phòng không hợp lệ ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="700" >
					<display:column class="centerColumnMana" title="STT">
						<c:out value="${item1_rowNum}" />
					</display:column>
					<display:column property="area" titleKey="cabledropet4tp.area" class="NOT_NULL"/>
					<display:column property="ddfStart" titleKey="cabledropet4tp.ddfStart"class="NOT_NULL"/>
				  	<display:column property="transission" titleKey="cabledropet4tp.transission" />
				  	<display:column property="channelName" titleKey="cabledropet4tp.channelName"/>
					<display:column property="ddfFinish" titleKey="cabledropet4tp.ddfFinish" class="NOT_NULL"/>
					<display:column property="description" titleKey="cabledropet4tp.description" />
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu DROP ET4 Thông Phòng hợp lệ ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700" >
					<display:column class="centerColumnMana" title="STT">
						<c:out value="${item2_rowNum}" />
					</display:column>
				<display:column property="area" titleKey="cabledropet4tp.area" class="NOT_NULL"/>
					<display:column property="ddfStart" titleKey="cabledropet4tp.ddfStart"class="NOT_NULL"/>
				  	<display:column property="transission" titleKey="cabledropet4tp.transission" />
				  	<display:column property="channelName" titleKey="cabledropet4tp.channelName"/>
					<display:column property="ddfFinish" titleKey="cabledropet4tp.ddfFinish" class="NOT_NULL"/>
					<display:column property="description" titleKey="cabledropet4tp.description" />
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