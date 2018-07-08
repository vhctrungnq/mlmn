<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleF}</title>
<content tag="heading">${titleF}</content> 
<style>
.note{color:red; float:right;padding-right: 7px;}


.dpnone {
	display: none;
}
.number {
	text-align: right;
}
.pdl15 { padding-left:15px; }

.hidden {display: none;}

#doublescroll { overflow: auto; overflow-y: hidden; }  

</style>


<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
<form:form method="post" commandName="costPlan" action="form.htm">
	
	<div>
		<form:hidden path="id"/>
		<form:hidden path="rootNo"/>
		<form:hidden path="quantity"/>
		<form:hidden path="doneToLasty"/>
		<input type="hidden" id="expensesSuperOld" name="expensesSuperOld" value="${expensesSuperOld}"/>
		<input type="hidden" id="departmentIdOld" name="departmentIdOld" value="${departmentIdOld}"/>
		<input type="hidden" id="costYearOld" name="costYearOld" value="${costYearOld}"/>
	</div>
	<table class="simple2" >
		
		<tr>
			<td ><fmt:message key="costPlan.departmentId"/><span class="note">(*)</span></td>
			<td >	
				<c:choose>
	                <c:when test="${empty costPlan.id}">
	                	<form:select  path="departmentId"  style="width: 200px;height:20px; padding-top: 4px;">
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
						</form:select>	
	                </c:when>
	                <c:otherwise>
	                    <b><i>${costPlan.departmentName}</i></b><form:hidden path="departmentId" />
	                </c:otherwise>
	            </c:choose>  	
				
				<form:errors path="departmentId" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costPlan.costYear"/><span class="note">(*)</span></td>
			<td colspan="5">
				<c:choose>
	                <c:when test="${empty costPlan.id}">
	                	<select name="costYear" id="costYear" style="width: 70px;height:20px; padding-top: 4px;">
						<c:forEach begin="2010" end="${year +20}" var="item">
							<c:choose>
								<c:when test="${costYear== item}">
									<option value="${item}" selected="selected">${item}</option>
								</c:when>
								<c:otherwise>
									<option value="${item}">${item}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
	                </c:when>
	                <c:otherwise>
	                    <b><i>${costYear}</i></b><form:hidden path="costYear" />
	                </c:otherwise>
	            </c:choose>  
				
					<form:errors path="costYear" class="error"/>
			</td>
			
		</tr>
		<tr>
			<td style="width:120px; "><fmt:message key="costPlan.expensesSupper"/> <span class="note">(*)</span> </td>
			<td>
				<c:choose>
	                <c:when test="${empty costPlan.id}">
	                	<select name="expensesSupper" id="expensesSupper" style="width: 200px;height:20px; padding-top: 4px;">
			           		<option value=""><fmt:message key="global.Chosse"/></option>
			           		<c:forEach var="item" items="${expensesSupperList}">
								<c:choose>
					                <c:when test="${item.expensesCode == expensesSupper}">
					                    <option value="${item.expensesCode}" selected="selected">${item.expensesName}</option>
					                </c:when>
									<c:otherwise>	
										<option value="${item.expensesCode}">${item.expensesName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select> 
	                </c:when>
	                <c:otherwise>
	                    <b><i>${expensesSupperName}</i></b><form:hidden path="expensesSupper" />
	                </c:otherwise>
	            </c:choose>  
				
				<form:errors path="expensesSupper" class="error"/>
			</td>
			<td style="width:120px;padding-left: 10px; "><fmt:message key="costPlan.expensesName"/> <span class="note">(*)</span> </td>
			<td>
				<select name="expensesCode" id="expensesCode" style="width: 140px;height:20px; padding-top: 4px;">
	           		<c:forEach var="item" items="${expensesNameList}">
						<c:choose>
			                <c:when test="${item.expensesCode == expensesCode}">
			                    <option value="${item.expensesCode}" selected="selected">${item.expensesName}</option>
			                </c:when>
							<c:otherwise>	
								<option value="${item.expensesCode}">${item.expensesName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
				<form:errors path="expensesCode" class="error"/>
			</td>
			<td style="width:120px;padding-left: 10px; "><fmt:message key="costPlan.deliveryYear"/></td>
			<td>
				<form:input type ="text" path="deliveryYear"  maxlength="15" />
				<br/><form:errors path="deliveryYear" class="error"/>
			</td>
			<td style="width:120px;padding-left: 10px; "><fmt:message key="costPlan.distributionYear"/></td>
			<td>
				<form:input type ="text" path="distributionYear"  maxlength="15" />
				<br/><form:errors path="distributionYear" class="error"/>
			</td>
		</tr>
		<tr><td colspan="8" id="checkboxs1">
					<span>
						<fmt:message key="costPlan.checkAll"/>
						<c:choose>
							<c:when test="${checkAll != null}">
								<input type="checkbox" name="checkAll1" id="checkAll1" checked="checked" >
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="checkAll1" id="checkAll1">
							</c:otherwise>
						</c:choose>
					</span>
					<span>
						<fmt:message key="costPlan.distributemonth"/>&nbsp;&nbsp;
						<c:choose>
							<c:when test="${costPlan.distributionPlan1Str != null}">
								<input type="checkbox" name="distributionPlan1Str" id="distributionPlan1Str" checked="checked" class="month2">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan1Str" id="distributionPlan1Str" class="month2">
							</c:otherwise>
						</c:choose>
				        
  					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month2"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan2Str != null}">
								<input type="checkbox" name="distributionPlan2Str" id="distributionPlan2Str" checked="checked" class="month2">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan2Str" id="distributionPlan2Str" class="month2">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month3"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan3Str!= null}">
								<input type="checkbox" name="distributionPlan3Str" id="distributionPlan3Str" checked="checked" class="month2">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan3Str" id="distributionPlan3Str" class="month2">
							</c:otherwise>
						</c:choose>  
					</span>
					<span class="pdl15">
						<fmt:message key="costPlan.month4"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan4Str != null}">
								<input type="checkbox" name="distributionPlan4Str" id="distributionPlan4Str" checked="checked" class="month2">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan4Str" id="distributionPlan4Str" class="month2">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month5"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan5Str != null}">
								<input type="checkbox" name="distributionPlan5Str" id="distributionPlan5Str" checked="checked" class="month2">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan5Str" id="distributionPlan5Str" class="month2">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month6"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan6Str != null}">
								<input type="checkbox" name="distributionPlan6Str" id="distributionPlan6Str" checked="checked" class="month2">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan6Str" id="distributionPlan6Str" class="month2">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month7"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan7Str != null}">
								<input type="checkbox" name="distributionPlan7Str" id="distributionPlan7Str" checked="checked" class="month2">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan7Str" id="distributionPlan7Str" class="month2">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month8"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan8Str != null}">
								<input type="checkbox" name="distributionPlan8Str" id="distributionPlan8Str" checked="checked" class="month2">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan8Str" id="distributionPlan8Str" class="month2">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month9"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan9Str != null}">
								<input type="checkbox" name="distributionPlan9Str" id="distributionPlan9Str" checked="checked" class="month2">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan9Str" id="distributionPlan9Str" class="month2">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month10"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan10Str != null}">
								<input type="checkbox" name="distributionPlan10Str" id="distributionPlan10Str" checked="checked" class="month2">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan10Str" id="distributionPlan10Str" class="month2">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month11"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan11Str != null}">
								<input type="checkbox" name="distributionPlan11Str" id="distributionPlan11Str" checked="checked" class="month2">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan11Str" id="distributionPlan11Str" class="month2">
							</c:otherwise>
						</c:choose>
					</span>
					<span class="pdl15">
						<fmt:message key="costPlan.month12"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan12Str != null}">
								<input type="checkbox" name="distributionPlan12Str" id="distributionPlan12Str" checked="checked" class="month2">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan12Str" id="distributionPlan12Str" class="month2">
							</c:otherwise>
						</c:choose>
					</span>
					<input class="button" type="button" id= "btPhanBo"  value="<fmt:message key="global.phanbo"/>">
			
				</td></tr>
		<tr>
			
			<td><fmt:message key="costPlan.distributionPlan1"/></td>
			<td>
				<form:input type ="text" path="distributionPlan1"  maxlength="15" class="month"/>
				<br/><form:errors path="distributionPlan1" class="error"/>
			</td>
			
			<td style="padding-left: 10px;"><fmt:message key="costPlan.distributionPlan2"/></td>
			<td>
				<form:input type ="text" path="distributionPlan2" maxlength="15" class="month"/>
				<br/><form:errors path="distributionPlan2" class="error"/>
			</td>
			
			<td style="padding-left: 10px;"><fmt:message key="costPlan.distributionPlan3"/></td>
			<td>
				<form:input type ="text" path="distributionPlan3" maxlength="15" class="month"/>
				<br/><form:errors path="distributionPlan3" class="error"/>
			</td>
			
			<td style="padding-left: 10px;"><fmt:message key="costPlan.distributionPlan4"/></td>
			<td>
				<form:input type ="text" path="distributionPlan4" maxlength="15" class="month"/>
				<br/><form:errors path="distributionPlan4" class="error"/>
			</td>
		</tr>
		
		<tr>
			
			<td ><fmt:message key="costPlan.distributionPlan5"/></td>
			<td>
				<form:input type ="text" path="distributionPlan5" maxlength="15" class="month"/>
				<br/><form:errors path="distributionPlan5" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costPlan.distributionPlan6"/></td>
			<td>
				<form:input type ="text" path="distributionPlan6" maxlength="15" class="month"/>
				<br/><form:errors path="distributionPlan6" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costPlan.distributionPlan7"/></td>
			<td>
				<form:input type ="text" path="distributionPlan7" maxlength="15" class="month"/>
				<br/><form:errors path="distributionPlan7" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costPlan.distributionPlan8"/></td>
			<td>
				<form:input type ="text" path="distributionPlan8" maxlength="15" class="month"/>
				<br/><form:errors path="distributionPlan8" class="error"/>
			</td>
		</tr>
		
		<tr>
			
			<td ><fmt:message key="costPlan.distributionPlan9"/></td>
			<td>
				<form:input type ="text" path="distributionPlan9" maxlength="15" class="month"/>
				<br/><form:errors path="distributionPlan9" class="error"/>
			</td>
			
			<td style="padding-left: 10px;"><fmt:message key="costPlan.distributionPlan10"/></td>
			<td>
				<form:input type ="text" path="distributionPlan10" maxlength="15" class="month"/>
				<br/><form:errors path="distributionPlan10" class="error"/>
			</td>
			
			<td style="padding-left: 10px;"><fmt:message key="costPlan.distributionPlan11"/></td>
			<td>
				<form:input type ="text" path="distributionPlan11" maxlength="15" class="month"/>
				<br/><form:errors path="distributionPlan11" class="error"/>
			</td>
			
			<td style="padding-left: 10px;"><fmt:message key="costPlan.distributionPlan12"/></td>
			<td>
				<form:input type ="text" path="distributionPlan12" maxlength="15" class="month"/>
				<br/><form:errors path="distributionPlan12" class="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="costPlan.deliveryPlan1"/></td>
			<td>
				<form:input type ="text" path="deliveryPlan1"  maxlength="15"/>
				<br/><form:errors path="deliveryPlan1" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costPlan.deliveryPlan2"/></td>
			<td>
				<form:input type ="text" path="deliveryPlan2" maxlength="15"/>
				<br/><form:errors path="deliveryPlan2" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costPlan.deliveryPlan3"/></td>
			<td>
				<form:input type ="text" path="deliveryPlan3" maxlength="15"/>
				<br/><form:errors path="deliveryPlan3" class="error"/>
			</td>
			
			<td style="padding-left: 10px;"><fmt:message key="costPlan.deliveryPlan4"/></td>
			<td>
				<form:input type ="text" path="deliveryPlan4" maxlength="15"/>
				<br/><form:errors path="deliveryPlan4" class="error"/>
			</td>
			
		</tr>
		<tr>
			<td><fmt:message key="costPlan.deliveryPlan5"/></td>
			<td>
				<form:input type ="text" path="deliveryPlan5" maxlength="15"/>
				<br/><form:errors path="deliveryPlan5" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costPlan.deliveryPlan6"/></td>
			<td>
				<form:input type ="text" path="deliveryPlan6" maxlength="15"/>
				<br/><form:errors path="deliveryPlan6" class="error"/>
			</td>
			
			<td style="padding-left: 10px;"><fmt:message key="costPlan.deliveryPlan7"/></td>
			<td>
				<form:input type ="text" path="deliveryPlan7" maxlength="15"/>
				<br/><form:errors path="deliveryPlan7" class="error"/>
			</td>
			
			<td style="padding-left: 10px;"><fmt:message key="costPlan.deliveryPlan8"/></td>
			<td>
				<form:input type ="text" path="deliveryPlan8" maxlength="15"/>
				<br/><form:errors path="deliveryPlan8" class="error"/>
			</td>
			
		</tr>
		<tr>
			<td><fmt:message key="costPlan.deliveryPlan9"/></td>
			<td>
				<form:input type ="text" path="deliveryPlan9" maxlength="15"/>
				<br/><form:errors path="deliveryPlan9" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costPlan.deliveryPlan10"/></td>
			<td>
				<form:input type ="text" path="deliveryPlan10" maxlength="15"/>
				<br/><form:errors path="deliveryPlan10" class="error"/>
			</td>
			<td><fmt:message key="costPlan.deliveryPlan11"/></td>
			<td>
				<form:input type ="text" path="deliveryPlan11" maxlength="15"/>
				<br/><form:errors path="deliveryPlan11" class="error"/>
			</td>
			
			<td style="padding-left: 10px;"><fmt:message key="costPlan.deliveryPlan12"/></td>
			<td>
				<form:input type ="text" path="deliveryPlan12" maxlength="15"/>
				<br/><form:errors path="deliveryPlan12" class="error"/>
			</td>
			
		</tr>
		<tr>
			<td><fmt:message key="costPlan.description"/></td>
			<td colspan="7">
				<form:textarea path="description" style="height: 50px" class="wid100" maxlength="900"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td colspan="7">
				<input type="hidden" id="action" name="action">
				<input type="submit" class="button" onclick="setAction('saveAndAdd')" name="saveAndAdd" value="<fmt:message key="global.button.saveAndAdd"/>"/>
       			<input type="submit" class="button" name="save"  onclick="setAction('save')" value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="<%=request.getContextPath() %>/cost/costPlan/list.htm"'>
			</td>
		</tr>
	</table>
</form:form>
<div id="doublescroll" >
	<display:table name="${costPlanList}"  class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="rootNo"  class="dpnone ROOT_NO" headerClass="dpnone" media="html"/>
		<display:column property="expensesSupper" titleKey="costPlan.expensesSupper" class="dpnone EXPENSES_SUPER" headerClass="dpnone" media="html"/>
	  	<display:column property="expensesCode" titleKey="costPlan.expensesCode"  style="min-width:70px;"/>
	  	<display:column property="expensesName" titleKey="costPlan.expensesName" style="min-width:200px;max-width:200px;"/>
	  	<display:column property="deliveryYear" titleKey="costPlan.deliveryYear"  format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="distributionYear" titleKey="costPlan.distributionYear" format="{0,number,###,###,###.#}"  style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlan1" titleKey="costPlan.deliveryPlan1" format="{0,number,###,###,###.#}"  style="min-width:70px;" class="number" />
		<display:column property="distributionPlan1" titleKey="costPlan.distributionPlan1"  format="{0,number,###,###,###.#}"  style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlan2" titleKey="costPlan.deliveryPlan2" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="distributionPlan2" titleKey="costPlan.distributionPlan2" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlan3" titleKey="costPlan.deliveryPlan3" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="distributionPlan3" titleKey="costPlan.distributionPlan3" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlan4" titleKey="costPlan.deliveryPlan4" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="distributionPlan4" titleKey="costPlan.distributionPlan4" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlan5" titleKey="costPlan.deliveryPlan5" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="distributionPlan5" titleKey="costPlan.distributionPlan5" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlan6" titleKey="costPlan.deliveryPlan6" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="distributionPlan6" titleKey="costPlan.distributionPlan6"format="{0,number,###,###,###.#}"  style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlan7" titleKey="costPlan.deliveryPlan7" format="{0,number,###,###,###.#}"  style="min-width:70px;" class="number"/>
		<display:column property="distributionPlan7" titleKey="costPlan.distributionPlan7" format="{0,number,###,###,###.#}"  style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlan8" titleKey="costPlan.deliveryPlan8" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="distributionPlan8" titleKey="costPlan.distributionPlan8"  format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlan9" titleKey="costPlan.deliveryPlan9" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="distributionPlan9" titleKey="costPlan.distributionPlan9"  format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlan10" titleKey="costPlan.deliveryPlan10" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="distributionPlan10" titleKey="costPlan.distributionPlan10" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlan11" titleKey="costPlan.deliveryPlan11" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="distributionPlan11" titleKey="costPlan.distributionPlan11" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlan12" titleKey="costPlan.deliveryPlan12" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="distributionPlan12" titleKey="costPlan.distributionPlan12" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="description" titleKey="costPlan.description"/>
		<display:column titleKey="global.management" media="html" class="centerColumnMana">
 			<c:choose>
				<c:when test="${empty item.rootNo || item.rootNo==0}">
					<a href="form.htm?id=${item.id}&code=${item.expensesCode}&departmentId=${item.departmentId}&costYear=${item.costYear}" title="${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
 				</c:when>
				<c:otherwise>
					<a href="form.htm?code=${item.expensesCode}&departmentId=${item.departmentId}&costYear=${item.costYear}" title="Thêm mới">Thêm</a>
				</c:otherwise>
			</c:choose>
 			
 		</display:column>
	   		
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript">

function setAction(value) {
	  var action = document.getElementById("action");
	  action.value = value;

	  return true;
	 }

$(function() {
	${highlight}				
  	});  

/*phan bo*/
$('.month2').change(function() {
	var count = 0;
	 
 	$('input.month2').each(function() {
 	 	if ($(this).is(":checked")) {
			count++;
 	 	}
	});
	 $("#quantity").val(count);

	// alert($("#quantity").val());
	  
});

$("#checkAll1").click(function() {
	
    // this function will get executed every time the #home element is clicked (or tab-spacebar changed)
    if($("#checkAll1").is(":checked")) // "this" refers to the element that fired the event
    {
    	
    	$('#checkboxs1 input:checkbox:not(:checked)').each(function() {
    		
    		var ID = $(this).attr('id');
    		$("#" + ID).attr('checked',true);
    		
    	});
    }
    else
    {
    	$('#checkboxs1 input:checked').each(function() {
    		$(this).removeAttr('checked'); 
    	});
    }

    var count = 0;
	 
    $('input.month2').each(function() {
 	 	if ($(this).is(":checked")) {
			count++;
 	 	}
	});
	 $("#quantity").val(count);
	 
   // alert($("#quantity").val());
});


$("#btPhanBo").click(function(){
	var distributionYear = $("#distributionYear").val();
	var quality = $("#quantity").val();
	var unitCost= 0.0;
	if (distributionYear!=null && distributionYear!=''&&quality!=null&&quality!='')
		{
			unitCost = distributionYear/quality;
			$('#checkboxs1 input:checked').each(function() {
				var ID = $(this).attr('id');
				var IDinput = ID.replace('Str','');
				$("#" + IDinput).val(unitCost);
	    	});
		}
});


/* lít danh sach theo expensuper */
$(function() {
    

    $("#expensesSupper").change(function(){
		if ($("#expensesSupper").val() == '' || $("#expensesSupper").val() == null) {
		} else {

			$.getJSON("${pageContext.request.contextPath}/cost/costPlan/ajax/getExpensesChild.htm",{expensesSupper: $(this).val()}, function(j){
				var options = '';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].expensesCode + '">' + j[i].expensesName+ '</option>';
				}
				$("#expensesCode").html(options);
			});

			$.getJSON("${pageContext.request.contextPath}/cost/costPlan/ajax/PlanSameList.htm",{departmentId: $("#departmentId").val(),costYear: $("#costYear").val(),expensesCode: $(this).val()}, function(j){
				
				var trs = '';
				trs=trs + '<table id="item" class="simple2">';
				trs=trs + '<thead>';
				trs=trs + '<tr><th  ><fmt:message key="global.list.STT"/></th>';
				trs=trs + '<th class="dpnone ID"><fmt:message key="costPlan.id"/></th>';
				trs=trs + '<th class="dpnone ROOT_NO"><fmt:message key="costPlan.rootNo"/></th>';
				trs=trs + '<th class="dpnone SHIFT_ID"><fmt:message key="costPlan.expensesSupper"/></th>';
			    trs=trs + '<th style="min-width:70px;"><fmt:message key="costPlan.expensesCode"/></th>';
			    trs=trs + '<th style="max-width:200px;min-width:200px;"><fmt:message key="costPlan.expensesName"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.deliveryYear"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.distributionYear"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.deliveryPlan1"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.distributionPlan1"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.deliveryPlan2"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.distributionPlan2"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.deliveryPlan3"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.distributionPlan3"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.deliveryPlan4"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.distributionPlan4"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.deliveryPlan5"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.distributionPlan5"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.deliveryPlan6"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.distributionPlan6"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.deliveryPlan7"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.distributionPlan7"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.deliveryPlan8"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.distributionPlan8"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.deliveryPlan9"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.distributionPlan9"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.deliveryPlan10"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.distributionPlan10"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.deliveryPlan11"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.distributionPlan11"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.deliveryPlan12"/></th>';
			    trs=trs +'<th style="min-width:70px;"><fmt:message key="costPlan.distributionPlan12"/></th>';
			    trs=trs +'<th><fmt:message key="costPlan.description"/></th>';
			    trs=trs +'<th><fmt:message key="global.management"/></th></tr>';
			    trs=trs + '</thead>';
				trs=trs + '<tbody>';
				if (j!=null)
				{
					
					for (var i = 0; i < j.length; i++) {
	
						
						var count=i+1;
						
						trs += '<tr><td>'+count+'</td>';
						
					if (j[i].id!=null) {
						
			    		trs=trs + '<td class="dpnone ID">'+j[i].id+'</td>';
					}
				    else
				    {
			    		trs=trs + '<td class="dpnone ID"></td>';
					}  
					
					if (j[i].id!=null) {
						
			    		trs=trs + '<td class="dpnone ROOT_NO">'+j[i].rootNo+'</td>';
					}
				    else
				    {
			    		trs=trs + '<td class="dpnone ROOT_NO"></td>';
					}  
					
					if (j[i].expensesSupper!=null)
					{
			    		trs=trs + '<td class="dpnone EXPENSES_SUPER">'+j[i].expensesSupper+'</td>';
					}
				    else
				    {
			    		trs=trs + '<td class="dpnone EXPENSES_SUPER"></td>';
					}  
				    if (j[i].expensesCode!=null)
					{
			    		trs=trs + '<td >'+j[i].expensesCode+'</td>';
					}
				    else
				    {
			    		trs=trs + '<td></td>';
					}  
					if (j[i].expensesName!=null)
					{
						 trs=trs + '<td>'+j[i].expensesName+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td></td>';
					}    	
					if (j[i].deliveryYear!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].deliveryYear.toMoney(1)+'</td>'; 
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;" ></td>';
					} 
					if (j[i].distributionYear!=null)
					{
						 trs=trs +'<td style="text-align: right;">'+j[i].distributionYear.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					}    
					if (j[i].deliveryPlan1!=null)
					{
						 trs=trs +'<td style="text-align: right;">'+j[i].deliveryPlan1.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					if (j[i].distributionPlan1!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].distributionPlan1.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					if (j[i].deliveryPlan2!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].deliveryPlan2.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					if (j[i].distributionPlan2!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].distributionPlan2.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					if (j[i].deliveryPlan3!=null)
					{
						 trs=trs +'<td style="text-align: right;">'+j[i].deliveryPlan3.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
				   
					
					if (j[i].distributionPlan3!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].distributionPlan3.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					if (j[i].deliveryPlan4!=null)
					{
						 trs=trs +'<td style="text-align: right;">'+j[i].deliveryPlan4.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
				   
					
					if (j[i].distributionPlan4!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].distributionPlan4.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					if (j[i].deliveryPlan5!=null)
					{
						 trs=trs +'<td style="text-align: right;">'+j[i].deliveryPlan5.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
				   
				
					if (j[i].distributionPlan5!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].distributionPlan5.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					if (j[i].deliveryPlan6!=null)
					{
						 trs=trs +'<td style="text-align: right;">'+j[i].deliveryPlan6.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
				  
					if (j[i].distributionPlan6!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].distributionPlan6.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					if (j[i].deliveryPlan7!=null)
					{
						 trs=trs +'<td style="text-align: right;">'+j[i].deliveryPlan7.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
				   
					
					if (j[i].distributionPlan7!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].distributionPlan7.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					if (j[i].deliveryPlan8!=null)
					{
						 trs=trs +'<td style="text-align: right;">'+j[i].deliveryPlan8.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
				   
					if (j[i].distributionPlan8!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].distributionPlan8.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					if (j[i].deliveryPlan9!=null)
					{
						 trs=trs +'<td style="text-align: right;">'+j[i].deliveryPlan9.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;" ></td>';
					} 
				   
					if (j[i].distributionPlan9!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].distributionPlan9.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					if (j[i].deliveryPlan10!=null)
					{
						 trs=trs +'<td style="text-align: right;>'+j[i].deliveryPlan10.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
				   
					if (j[i].distributionPlan10!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].distributionPlan10.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					if (j[i].deliveryPlan11!=null)
					{
						 trs=trs +'<td style="text-align: right;">'+j[i].deliveryPlan11.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
				   
					if (j[i].distributionPlan11!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].distributionPlan11.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					if (j[i].deliveryPlan12!=null)
					{
						 trs=trs +'<td style="text-align: right;">'+j[i].deliveryPlan12.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
				   
					if (j[i].distributionPlan12!=null)
					{
						trs=trs +'<td style="text-align: right;">'+j[i].distributionPlan12.toMoney(1)+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td style="text-align: right;"></td>';
					} 
					
					if (j[i].description!=null)
					{
						 trs=trs +'<td class="DESCRIPTION">'+j[i].description+'</td>';
					}
				    else
				    {
				    	trs=trs + '<td class="DESCRIPTION"></td>';
					} 
	
					if (j[i].rootNo==null || j[i].rootNo ==0)
						{
							trs=trs +'<td class="Edit"><a href="form.htm?id='+j[i].id+'&code='+j[i].expensesCode+'" title="'+j[i].id+'"><fmt:message key="global.form.sua"/></a></td></tr>';
						
						}
					else
						{
							trs=trs +'<td class="Edit"><a href="form.htm?code='+j[i].expensesCode+'" title="tdêm mới">Thêm</a></td></tr>';
						}
					}
				}
					trs=trs + '</tbody>';
					trs=trs + '</table>';
					$('#item').html('');
					$('#doublescroll').html(trs);
					${highlight}	
				
			});
			
		}
	});
}); 

function DoubleScroll(element) {
    var scrollbar= document.createElement('div');
    scrollbar.appendChild(document.createElement('div'));
    scrollbar.style.overflow= 'auto';
    scrollbar.style.overflowY= 'hidden';
    //scrollbar.firstChild.style.width= element.scrollWidth+'px';
    scrollbar.firstChild.style.width= '2490px';
    scrollbar.firstChild.style.paddingTop= '1px';
    scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
    scrollbar.onscroll= function() {
        element.scrollLeft= scrollbar.scrollLeft;
    };
    element.onscroll= function() {
        scrollbar.scrollLeft= element.scrollLeft;
    };
    element.parentNode.insertBefore(scrollbar, element);
}

DoubleScroll(document.getElementById('doublescroll')); 

$('.month').change(function() {
	var count = 0;
	 
 	$('input.month').each(function() {
 	 	if ($(this).val()!=null && $(this).val()!='') {
			count++;
 	 	}
	});
	 $("#quantity").val(count);

	// alert($("#quantity").val());
	  
});
</script>
<script type="text/javascript">
Number.prototype.toMoney = function(decimals, decimal_sep, thousands_sep)
{ 
   var n = this,
   c = isNaN(decimals) ? 2 : Math.abs(decimals), //if decimal is zero we must take it, it means user does not want to show any decimal
   d = decimal_sep || '.', //if no decimal separator is passed we use the dot as default decimal separator (we MUST use a decimal separator)

   /*
   according to [http://stackoverflow.com/questions/411352/how-best-to-determine-if-an-argument-is-not-sent-to-the-javascript-function]
   the fastest way to check for not defined parameter is to use typeof value === 'undefined' 
   rather than doing value === undefined.
   */   
   t = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep, //if you don't want to use a thousands separator you can pass empty string as thousands_sep value

   sign = (n < 0) ? '-' : '',

   //extracting the absolute value of the integer part of the number and converting to string
   i = parseInt(n = Math.abs(n).toFixed(c)) + '', 

   j = ((j = i.length) > 3) ? j % 3 : 0; 
   return sign + (j ? i.substr(0, j) + t : '') + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : ''); 
}
//alert(123456789.23423.toMoney(1));
//alert(123456789.6.toMoney() + '\n' + 123456789.6.toMoney(1) + '\n' + 123456789.6.toMoney(0) + '\n' + (123456).toMoney() + '\n' + (123456).toMoney(0) + '\n' + 89.67392.toMoney() + '\n' + (89).toMoney());

//some tests (do not forget parenthesis when using negative numbers and number with no decimals)
//alert((-123456789.67392).toMoney() + '\n' + (-123456789.67392).toMoney(-3));

</script>
