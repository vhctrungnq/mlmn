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
    						&lt;<fmt:message key="costExpenses.expensesSuper"/>&gt;,
    						&lt;<fmt:message key="costExpenses.expensesCode"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="costExpenses.expensesName"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="costExpenses.description"/>&gt;,
    						&lt;<fmt:message key="costExpenses.isEnable"/>&gt;,
    						&lt;<fmt:message key="costExpenses.ordering"/>&gt;,
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/CostExpenses.xls" title="Danh sách cáp" style="color: blue; ">CostExpenses.xls</a></li>
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
					<display:column property="expensesSuper" titleKey="costExpenses.expensesSuper" />
	  				<display:column property="expensesCode" titleKey="costExpenses.expensesCode"  class="NOT_NULL"/>
				  	<%--<display:column property="workId"  titleKey="costExpenses.workId" sortable="true" sortName="WORK_ID" />--%>
					<display:column property="expensesName" titleKey="costExpenses.expensesName"  class="NOT_NULL"/>
					<display:column property="description" titleKey="costExpenses.description" />
					<display:column property="isEnable" titleKey="costExpenses.isEnable" />
					<display:column property="orderingStr" titleKey="costExpenses.ordering" />
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
					<display:column property="expensesSuper" titleKey="costExpenses.expensesSuper" />
	  				<display:column property="expensesCode" titleKey="costExpenses.expensesCode"  class="NOT_NULL" />
				  	<display:column property="expensesName" titleKey="costExpenses.expensesName"  class="NOT_NULL"/>
					<display:column property="description" titleKey="costExpenses.description" sortable="true" sortName="DESCRIPTION" />
					<display:column property="isEnable" titleKey="costExpenses.isEnable" sortable="true" sortName="IS_ENABLE" />
					<display:column property="orderingStr" titleKey="costExpenses.ordering" sortable="true" sortName="ORDERING" />
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