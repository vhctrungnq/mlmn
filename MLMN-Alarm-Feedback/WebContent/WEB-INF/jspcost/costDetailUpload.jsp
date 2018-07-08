<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title>${titleU}</title>
<content tag="heading">${titleU}</content> 
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
 <form:form  method="post" action="upload.htm" enctype="multipart/form-data">
	<table class="simple2">	
		<tr>
			<td style="width:150px; "><fmt:message key="costDetail.departmentId"/></td>
			<td >		
				<select name="departmentId" id="departmentId" style="width: 170px;height:20px; padding-top: 4px;">
	           		<c:forEach var="item" items="${departmentList}">
						<c:choose>
			                <c:when test="${item.deptCode == departmentId}">
			                    <option value="${item.deptCode}" selected="selected">${item.deptCode}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.deptCode}">${item.deptCode}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
			</td>
			<td style="width:120px;padding-left: 10px;"><fmt:message key="costDetail.costYear"/><span class="note">(*)</span></td>
			<td >
				<select name="costMonth" id="costMonth" style="width: 70px;height:20px; padding-top: 4px;">
						<c:forEach begin="1" end="12" var="item">
							<c:choose>
								<c:when test="${costMonth == item}">
									<option value="${item}" selected="selected">${item}</option>
								</c:when>
								<c:otherwise>
									<option value="${item}">${item}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>/
					<select name="costYear" id="costYear" style="width: 70px;height:20px; padding-top: 4px;">
						<c:forEach begin="2010" end="${year +20}" var="item">
							<c:choose>
								<c:when test="${costYear == item}">
									<option value="${item}" selected="selected">${item}</option>
								</c:when>
								<c:otherwise>
									<option value="${item}">${item}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
			</td>
		</tr>
    	<tr style="height:20px;" >
    		<td width="150px"><b><fmt:message key="cautruc.filepath"/> <font color="red">(*)</font></b></td>
    		<td colspan="3"><input type="file" size="110" name="filePath" id="filePath" class="button" />
    			<input type="submit" name="upload" id="upload" class="button" value="<fmt:message key="global.button.import"/>" />
    		</td>
    	</tr>
    	<tr>
    		<td><b>
    			<fmt:message key="global.FileExample"/></b>
    		</td>
    		<td colspan="3">
    		<ul>
    			<li><fmt:message key="global.formatFile"/></li>
						<li>Cấu trúc file: 
    					<code>&lt;STT&gt;,
    						&lt;<fmt:message key="costDetail.expensesCode"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="costDetail.taskNo"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="costDetail.workCode"/>&gt;,
    						&lt;<fmt:message key="costDetail.workName"/>&gt;,
    						&lt;<fmt:message key="costDetail.deliveryPlanYear"/>&gt;,
    						&lt;<fmt:message key="costDetail.adjustmentPlanYear"/>&gt;,
    						&lt;<fmt:message key="costDetail.doneToLastm"/>&gt;,
    						&lt;<fmt:message key="costDetail.deliveryPlanCurrentm"/>&gt;,
    						&lt;<fmt:message key="costDetail.doneCurrentm"/>&gt;,
    						&lt;<fmt:message key="costDetail.comulativeCurrentm"/>&gt;,
    						&lt;<fmt:message key="costDetail.remainingCost"/>&gt;,
    						&lt;<fmt:message key="costDetail.rateDoneLastm"/>&gt;,
    					<%-- 	&lt;<fmt:message key="costDetail.leaderResponsive"/>&gt;,
    						&lt;<fmt:message key="costDetail.reasonNotDone"/>&gt;, --%>
    						&lt;<fmt:message key="costDetail.jobClassification"/>&gt;,
    						&lt;<fmt:message key="costDetail.adjustLastm"/>&gt;,
    						&lt;<fmt:message key="costDetail.statusPlan"/>&gt;,
    						<%-- &lt;<fmt:message key="costDetail.contractName"/>&gt;,
    						&lt;<fmt:message key="costDetail.contractNo"/>&gt;,
    						&lt;<fmt:message key="costDetail.contractDate"/>&gt;,
    						&lt;<fmt:message key="costDetail.contractType"/>&gt;,
    						&lt;<fmt:message key="costDetail.departmentDoneContract"/>&gt;, --%>
    						&lt;<fmt:message key="costDetail.description"/>&gt;
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/costDetail.xls" title="Danh sách cáp" style="color: blue; ">costDetail.xls</a></li>
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
					<display:column property="expensesCode" titleKey="costDetail.expensesCode" style="max-width:40px;"/>
				  	<display:column property="taskNo" titleKey="costDetail.taskNo" style="min-with:200px;max-width:200px;"/>
				  	<display:column property="workCode" titleKey="costDetail.workCode"  style="min-with:200px;max-width:200px;"/>
				  	<display:column property="workName" titleKey="costDetail.workName"  style="max-width:70px;"/>
					<display:column property="deliveryPlanYear" titleKey="costDetail.deliveryPlanYear" style="max-width:70px;"/>
					<display:column property="adjustmentPlanYear" titleKey="costDetail.adjustmentPlanYear"   style="max-width:70px;"/>
					<display:column property="doneToLastm" titleKey="costDetail.doneToLastm"   style="max-width:70px;"/>
					<display:column property="deliveryPlanCurrentm" titleKey="costDetail.deliveryPlanCurrentm"  style="max-width:70px;"/>
					<display:column property="doneCurrentm" titleKey="costDetail.doneCurrentm"  style="max-width:70px;"/>
					<display:column property="comulativeCurrentm" titleKey="costDetail.comulativeCurrentm"  style="max-width:70px;"/>
					<display:column property="remainingCost" titleKey="costDetail.remainingCost"  style="max-width:70px;"/>
					<display:column property="rateDoneLastm" titleKey="costDetail.rateDoneLastm"   style="max-width:70px;"/>
					<%-- <display:column property="leaderResponsive" titleKey="costDetail.leaderResponsive"  style="max-width:70px;"/>
					<display:column property="reasonNotDone" titleKey="costDetail.reasonNotDone" style="max-width:70px;"/> --%>
					<display:column property="jobClassification" titleKey="costDetail.jobClassification"  style="max-width:70px;"/>
					<display:column property="adjustLastm" titleKey="costDetail.adjustLastm" style="max-width:70px;"/>
					<display:column property="statusPlan" titleKey="costDetail.statusPlan" style="max-width:70px;"/>
					<%-- <display:column property="contractName" titleKey="costDetail.contractName"  style="max-width:70px;"/>
					<display:column property="contractNo" titleKey="costDetail.contractNo" style="max-width:70px;"/>
					<display:column property="contractDate" titleKey="costDetail.contractDate" style="max-width:70px;"/>
					<display:column property="contractType" titleKey="costDetail.contractType"  style="max-width:70px;"/>
					<display:column property="departmentDoneContract" titleKey="costDetail.departmentDoneContract"  style="max-width:70px;"/> --%>
					<display:column property="description" titleKey="costDetail.description" style="max-width:70px;"/>
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
					<display:column property="expensesCode" titleKey="costDetail.expensesCode" style="max-width:40px;"/>
				  	<display:column property="taskNo" titleKey="costDetail.taskNo" style="min-with:200px;max-width:200px;"/>
				  	<display:column property="workCode" titleKey="costDetail.workCode"  style="min-with:200px;max-width:200px;"/>
				  	<display:column property="workName" titleKey="costDetail.workName"  style="max-width:70px;"/>
					<display:column property="deliveryPlanYear" titleKey="costDetail.deliveryPlanYear" style="max-width:70px;"/>
					<display:column property="adjustmentPlanYear" titleKey="costDetail.adjustmentPlanYear"   style="max-width:70px;"/>
					<display:column property="doneToLastm" titleKey="costDetail.doneToLastm"   style="max-width:70px;"/>
					<display:column property="deliveryPlanCurrentm" titleKey="costDetail.deliveryPlanCurrentm"  style="max-width:70px;"/>
					<display:column property="doneCurrentm" titleKey="costDetail.doneCurrentm"  style="max-width:70px;"/>
					<display:column property="comulativeCurrentm" titleKey="costDetail.comulativeCurrentm"  style="max-width:70px;"/>
					<display:column property="remainingCost" titleKey="costDetail.remainingCost"  style="max-width:70px;"/>
					<display:column property="rateDoneLastm" titleKey="costDetail.rateDoneLastm"   style="max-width:70px;"/>
					<%-- <display:column property="leaderResponsive" titleKey="costDetail.leaderResponsive"  style="max-width:70px;"/>
					<display:column property="reasonNotDone" titleKey="costDetail.reasonNotDone" style="max-width:70px;"/> --%>
					<display:column property="jobClassification" titleKey="costDetail.jobClassification"  style="max-width:70px;"/>
					<display:column property="adjustLastm" titleKey="costDetail.adjustLastm" style="max-width:70px;"/>
					<display:column property="statusPlan" titleKey="costDetail.statusPlan" style="max-width:70px;"/>
					<%-- <display:column property="contractName" titleKey="costDetail.contractName"  style="max-width:70px;"/>
					<display:column property="contractNo" titleKey="costDetail.contractNo" style="max-width:70px;"/>
					<display:column property="contractDate" titleKey="costDetail.contractDate" style="max-width:70px;"/>
					<display:column property="contractType" titleKey="costDetail.contractType"  style="max-width:70px;"/>
					<display:column property="departmentDoneContract" titleKey="costDetail.departmentDoneContract"  style="max-width:70px;"/> --%>
					<display:column property="description" titleKey="costDetail.description" style="max-width:70px;"/>
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