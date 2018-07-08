<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
    .number {
	text-align: right;
	}
</style>

<title>${titleU}</title>
<content tag="heading">${titleU}</content> 
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
 <form:form  method="post" action="upload.htm" enctype="multipart/form-data">
	<table class="simple2">	
		<tr>
			<td style="width:150px; "><fmt:message key="costPlan.departmentId"/></td>
			<td >		
				<select name="departmentId" id="departmentId" style="width: 200px;height:20px; padding-top: 4px;">
	           		<c:forEach var="item" items="${departmentList}">
						<c:choose>
			                <c:when test="${item.deptCode == departmentId}">
			                    <option value="${item.deptCode}" selected="selected">${item.deptName}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.deptCode}">${item.deptName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
			</td>
			<td style="width:120px;padding-left: 10px;"><fmt:message key="costPlan.costYear"/><span class="note">(*)</span></td>
			<td >
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
    						&lt;<fmt:message key="costPlan.expensesCode"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="costPlan.expensesName"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="costPlan.deliveryYear"/>&gt;,
    						&lt;<fmt:message key="costPlan.distributionYear"/>&gt;,
    						<%-- &lt;<fmt:message key="costPlan.deliveryPlan1"/>&gt;, --%>
    						&lt;<fmt:message key="costPlan.distributionPlan1"/>&gt;,
    						<%-- &lt;<fmt:message key="costPlan.deliveryPlan2"/>&gt;, --%>
    						&lt;<fmt:message key="costPlan.distributionPlan2"/>&gt;,
    						<%-- &lt;<fmt:message key="costPlan.deliveryPlan3"/>&gt;, --%>
    						&lt;<fmt:message key="costPlan.distributionPlan3"/>&gt;,
    						<%-- &lt;<fmt:message key="costPlan.deliveryPlan4"/>&gt;, --%>
    						&lt;<fmt:message key="costPlan.distributionPlan4"/>&gt;,
    						<%-- &lt;<fmt:message key="costPlan.deliveryPlan5"/>&gt;, --%>
    						&lt;<fmt:message key="costPlan.distributionPlan5"/>&gt;,
    						<%-- &lt;<fmt:message key="costPlan.deliveryPlan6"/>&gt;, --%>
    						&lt;<fmt:message key="costPlan.distributionPlan6"/>&gt;,
    						<%-- &lt;<fmt:message key="costPlan.deliveryPlan7"/>&gt;, --%>
    						&lt;<fmt:message key="costPlan.distributionPlan7"/>&gt;,
    						<%-- &lt;<fmt:message key="costPlan.deliveryPlan8"/>&gt;, --%>
    						&lt;<fmt:message key="costPlan.distributionPlan8"/>&gt;,
    						<%-- &lt;<fmt:message key="costPlan.deliveryPlan9"/>&gt;, --%>
    						&lt;<fmt:message key="costPlan.distributionPlan9"/>&gt;,
    						<%-- &lt;<fmt:message key="costPlan.deliveryPlan10"/>&gt;, --%>
    						&lt;<fmt:message key="costPlan.distributionPlan10"/>&gt;,
    						<%-- &lt;<fmt:message key="costPlan.deliveryPlan11"/>&gt;, --%>
    						&lt;<fmt:message key="costPlan.distributionPlan11"/>&gt;,
    						<%-- &lt;<fmt:message key="costPlan.deliveryPlan12"/>&gt;, --%>
    						&lt;<fmt:message key="costPlan.distributionPlan12"/>&gt;,
    						&lt;<fmt:message key="costPlan.description"/>&gt;
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/CostPlan.xls" title="Danh sách cáp" style="color: blue; ">CostPlan.xls</a></li>
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
					<display:column property="expensesCode" titleKey="costPlan.expensesCode" class="NOT_NULL"/>
				  	<display:column property="expensesName" titleKey="costPlan.expensesName" class="NOT_NULL"/>
				  	<display:column property="deliveryYearStr" titleKey="costPlan.deliveryYear"  class="number"/>
					<display:column property="distributionYearStr" titleKey="costPlan.distributionYear"  class="number"/>
					<display:column property="distributionPlan1Str" titleKey="costPlan.distributionPlan1"  class="number"/>
					<display:column property="distributionPlan2Str" titleKey="costPlan.distributionPlan2"  class="number"/>
					<display:column property="distributionPlan3Str" titleKey="costPlan.distributionPlan3"  class="number"/>
					<display:column property="distributionPlan4Str" titleKey="costPlan.distributionPlan4"  class="number"/>
					<display:column property="distributionPlan5Str" titleKey="costPlan.distributionPlan5"  class="number"/>
					<display:column property="distributionPlan6Str" titleKey="costPlan.distributionPlan6" class="number"/>
					<display:column property="distributionPlan7Str" titleKey="costPlan.distributionPlan7"  class="number"/>
					<display:column property="distributionPlan8Str" titleKey="costPlan.distributionPlan8"  class="number"/>
					<display:column property="distributionPlan9Str" titleKey="costPlan.distributionPlan9"  class="number"/>
					<display:column property="distributionPlan10Str" titleKey="costPlan.distributionPlan10" class="number"/>
					<display:column property="distributionPlan11Str" titleKey="costPlan.distributionPlan11" class="number"/>
					<display:column property="distributionPlan12Str" titleKey="costPlan.distributionPlan12" class="number"/>
					<%-- <display:column property="deliveryPlan1Str" titleKey="costPlan.deliveryPlan1"  class="number"/>
					<display:column property="deliveryPlan2Str" titleKey="costPlan.deliveryPlan2"  class="number"/>
					<display:column property="deliveryPlan3Str" titleKey="costPlan.deliveryPlan3"  class="number"/>
					<display:column property="deliveryPlan4Str" titleKey="costPlan.deliveryPlan4" class="number"/>
					<display:column property="deliveryPlan5Str" titleKey="costPlan.deliveryPlan5" class="number"/>
					<display:column property="deliveryPlan6Str" titleKey="costPlan.deliveryPlan6"  class="number"/>
					<display:column property="deliveryPlan7Str" titleKey="costPlan.deliveryPlan7"  class="number"/>
					<display:column property="deliveryPlan8Str" titleKey="costPlan.deliveryPlan8"  class="number"/>
					<display:column property="deliveryPlan9Str" titleKey="costPlan.deliveryPlan9"  class="number"/>
					<display:column property="deliveryPlan10Str" titleKey="costPlan.deliveryPlan10" class="number"/>
					<display:column property="deliveryPlan11Str" titleKey="costPlan.deliveryPlan11" class="number"/>
					<display:column property="deliveryPlan12Str" titleKey="costPlan.deliveryPlan12" class="number"/> --%>
					<display:column property="description" titleKey="costPlan.description"/>
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
					<display:column property="expensesCode" titleKey="costPlan.expensesCode" class="NOT_NULL"/>
				  	<display:column property="expensesName" titleKey="costPlan.expensesName" class="NOT_NULL"/>
				  	<display:column property="deliveryYearStr" titleKey="costPlan.deliveryYear"  class="number"/>
					<display:column property="distributionYearStr" titleKey="costPlan.distributionYear"  class="number"/>
					<display:column property="distributionPlan1Str" titleKey="costPlan.distributionPlan1"  class="number"/>
					<display:column property="distributionPlan2Str" titleKey="costPlan.distributionPlan2"  class="number"/>
					<display:column property="distributionPlan3Str" titleKey="costPlan.distributionPlan3"  class="number"/>
					<display:column property="distributionPlan4Str" titleKey="costPlan.distributionPlan4"  class="number"/>
					<display:column property="distributionPlan5Str" titleKey="costPlan.distributionPlan5"  class="number"/>
					<display:column property="distributionPlan6Str" titleKey="costPlan.distributionPlan6" class="number"/>
					<display:column property="distributionPlan7Str" titleKey="costPlan.distributionPlan7"  class="number"/>
					<display:column property="distributionPlan8Str" titleKey="costPlan.distributionPlan8"  class="number"/>
					<display:column property="distributionPlan9Str" titleKey="costPlan.distributionPlan9"  class="number"/>
					<display:column property="distributionPlan10Str" titleKey="costPlan.distributionPlan10" class="number"/>
					<display:column property="distributionPlan11Str" titleKey="costPlan.distributionPlan11" class="number"/>
					<display:column property="distributionPlan12Str" titleKey="costPlan.distributionPlan12" class="number"/>
					<%-- <display:column property="deliveryPlan1Str" titleKey="costPlan.deliveryPlan1"  class="number"/>
					<display:column property="deliveryPlan2Str" titleKey="costPlan.deliveryPlan2"  class="number"/>
					<display:column property="deliveryPlan3Str" titleKey="costPlan.deliveryPlan3"  class="number"/>
					<display:column property="deliveryPlan4Str" titleKey="costPlan.deliveryPlan4" class="number"/>
					<display:column property="deliveryPlan5Str" titleKey="costPlan.deliveryPlan5" class="number"/>
					<display:column property="deliveryPlan6Str" titleKey="costPlan.deliveryPlan6"  class="number"/>
					<display:column property="deliveryPlan7Str" titleKey="costPlan.deliveryPlan7"  class="number"/>
					<display:column property="deliveryPlan8Str" titleKey="costPlan.deliveryPlan8"  class="number"/>
					<display:column property="deliveryPlan9Str" titleKey="costPlan.deliveryPlan9"  class="number"/>
					<display:column property="deliveryPlan10Str" titleKey="costPlan.deliveryPlan10" class="number"/>
					<display:column property="deliveryPlan11Str" titleKey="costPlan.deliveryPlan11" class="number"/>
					<display:column property="deliveryPlan12Str" titleKey="costPlan.deliveryPlan12" class="number"/> --%>
					<display:column property="description" titleKey="costPlan.description"/>
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