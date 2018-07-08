<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
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
    					<code>&lt;STT&gt;,
    						&lt;<fmt:message key="costWork.expensesCode"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="costWork.workSuper"/>&gt;,
    						&lt;<fmt:message key="costWork.workName"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="costWork.workContent"/>&gt;,
    						&lt;<fmt:message key="costWork.isEnable"/>&gt;,
    						&lt;<fmt:message key="costWork.ordering"/>&gt;,
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/CostWork.xls" title="Danh sách cáp" style="color: blue; ">CostWork.xls</a></li>
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
    		<div><b>Dữ liệu không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item1_rowNum}" />
					</display:column>
					<display:column property="expensesCode" titleKey="costWork.expensesCode" class="NOT_NULL"/>
				  	<display:column property="workSuperStr" titleKey="costWork.workSuper"  />
				  	<display:column property="workName" titleKey="costWork.workName" class="NOT_NULL" />
				  	<display:column property="workContent" titleKey="costWork.workContent" sortable="true" sortName="WORK_CONTENT" style="min-width:400px;"/>
					<display:column property="isEnable" titleKey="costWork.isEnable" sortable="true" sortName="IS_ENABLE" style="max-width:60px;"/>
					<display:column property="orderingStr" titleKey="costWork.ordering" sortable="true" sortName="ORDERING" style="max-width:60px;"/>
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
					<display:column property="expensesCode" titleKey="costWork.expensesCode" class="NOT_NULL"/>
				  	<display:column property="workSuperStr" titleKey="costWork.workSuper"/>
				  	<display:column property="workName" titleKey="costWork.workName" class="NOT_NULL"/>
				  	<display:column property="workContent" titleKey="costWork.workContent" sortable="true" sortName="WORK_CONTENT" style="min-width:400px;"/>
					<display:column property="isEnable" titleKey="costWork.isEnable" sortable="true" sortName="IS_ENABLE" style="max-width:60px;"/>
					<display:column property="orderingStr" titleKey="costWork.ordering" sortable="true" sortName="ORDERING" style="max-width:60px;"/>
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
   					  ${highlight1}
   						});

    	$('#item1>tbody>tr').each(
   	    	 function(){
  					  ${highlight1}
  					});
		}); 
</script>