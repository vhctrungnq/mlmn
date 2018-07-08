
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title} NĂM ${costYear} CỦA ĐƠN VỊ:  <span id ="deptName"></span></content> 	
<style>

.dpnone {
	display: none;
}

.number {
	text-align: right;
}

.pdl15 { padding-left:15px; }

.hidden {display: none;}

.bd {border-right:1px solid !important;
	width:0px;
 	padding: 0 !important;
 	}
#doublescroll { overflow: auto; overflow-y: hidden; }  

</style>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
<div>
<form:form commandName="costPlan" method="POST" action="list.htm">
	<input type="hidden" name="highlight" id="highlight" value="${highlight}">
	<input type="hidden" name="highlight1" id="highlight1" value="${highlight1}">
	
	<table>
		<tr>
			<td><fmt:message key="costPlan.costYear"/></td>
				<td>
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
			<td   style="width: 80px; padding-left: 5px;"><fmt:message key="costPlan.departmentId"/></td>
			<td>
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
			
			<td style="width:120px; padding-left: 5px;"><fmt:message key="costPlan.expensesSupper"/> </td>
			<td>
				<form:select path="expensesSupper" style="width: 370px;height:20px; padding-top: 4px;">
					<option value=""><fmt:message key="global.Chosse"/></option>
	           		<c:forEach var="item" items="${expensesSupperList}">
						<c:choose>
			                <c:when test="${item.expensesCode == costPlan.expensesSupper}">
			                    <option value="${item.expensesCode}" selected="selected">${item.expensesName}</option>
			                </c:when>
							<c:otherwise>	
								<option value="${item.expensesCode}">${item.expensesName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select> 
			</td>
			<td style="padding-left: 5px;width: 50px;"><fmt:message key="costPlan.expensesName"/></td>
			<td>
				<form:input path="expensesName" width="80px" size="25"/>
				
			</td>
			
			</tr>
			<tr><td colspan="8" id="checkboxs1">
					<span>
						<fmt:message key="costPlan.checkAll"/>
						<c:choose>
							<c:when test="${checkAll != null}">
								<input type="checkbox" name="checkAll1" id="checkAll1" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="checkAll1" id="checkAll1" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					<span>
						<fmt:message key="costPlan.distributemonth"/>&nbsp;&nbsp;
						<c:choose>
							<c:when test="${costPlan.distributionPlan1Str != null}">
								<input type="checkbox" name="distributionPlan1Str" id="distributionPlan1Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan1Str" id="distributionPlan1Str" class="month">
							</c:otherwise>
						</c:choose>
				        
  					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month2"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan2Str != null}">
								<input type="checkbox" name="distributionPlan2Str" id="distributionPlan2Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan2Str" id="distributionPlan2Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month3"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan3Str!= null}">
								<input type="checkbox" name="distributionPlan3Str" id="distributionPlan3Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan3Str" id="distributionPlan3Str" class="month">
							</c:otherwise>
						</c:choose>  
					</span>
					<span class="pdl15">
						<fmt:message key="costPlan.month4"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan4Str != null}">
								<input type="checkbox" name="distributionPlan4Str" id="distributionPlan4Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan4Str" id="distributionPlan4Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month5"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan5Str != null}">
								<input type="checkbox" name="distributionPlan5Str" id="distributionPlan5Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan5Str" id="distributionPlan5Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month6"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan6Str != null}">
								<input type="checkbox" name="distributionPlan6Str" id="distributionPlan6Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan6Str" id="distributionPlan6Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month7"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan7Str != null}">
								<input type="checkbox" name="distributionPlan7Str" id="distributionPlan7Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan7Str" id="distributionPlan7Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month8"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan8Str != null}">
								<input type="checkbox" name="distributionPlan8Str" id="distributionPlan8Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan8Str" id="distributionPlan8Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month9"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan9Str != null}">
								<input type="checkbox" name="distributionPlan9Str" id="distributionPlan9Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan9Str" id="distributionPlan9Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month10"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan10Str != null}">
								<input type="checkbox" name="distributionPlan10Str" id="distributionPlan10Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan10Str" id="distributionPlan10Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month11"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan11Str != null}">
								<input type="checkbox" name="distributionPlan11Str" id="distributionPlan11Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan11Str" id="distributionPlan11Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					<span class="pdl15">
						<fmt:message key="costPlan.month12"/>
						<c:choose>
							<c:when test="${costPlan.distributionPlan12Str != null}">
								<input type="checkbox" name="distributionPlan12Str" id="distributionPlan12Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="distributionPlan12Str" id="distributionPlan12Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
				
				</td></tr>
			
			<tr>
				
				<td colspan="8" id="checkboxs">
					<span>
						<fmt:message key="costPlan.checkAll"/>
						<c:choose>
							<c:when test="${checkAll != null}">
								<input type="checkbox" name="checkAll" id="checkAll" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="checkAll" id=checkAll class="month">
							</c:otherwise>
						</c:choose>
					</span>
					<span>
						<fmt:message key="costPlan.month1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<c:choose>
							<c:when test="${costPlan.deliveryPlan1Str != null}">
								<input type="checkbox" name="deliveryPlan1Str" id="deliveryPlan1Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="deliveryPlan1Str" id="deliveryPlan1Str" class="month">
							</c:otherwise>
						</c:choose>
				        
  					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month2"/>
						<c:choose>
							<c:when test="${costPlan.deliveryPlan2Str != null}">
								<input type="checkbox" name="deliveryPlan2Str" id="deliveryPlan2Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="deliveryPlan2Str" id="deliveryPlan2Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month3"/>
						<c:choose>
							<c:when test="${costPlan.deliveryPlan3Str!= null}">
								<input type="checkbox" name="deliveryPlan3Str" id="deliveryPlan3Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="deliveryPlan3Str" id="deliveryPlan3Str" class="month">
							</c:otherwise>
						</c:choose>  
					</span>
					<span class="pdl15">
						<fmt:message key="costPlan.month4"/>
						<c:choose>
							<c:when test="${costPlan.deliveryPlan4Str != null}">
								<input type="checkbox" name="deliveryPlan4Str" id="deliveryPlan4Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="deliveryPlan4Str" id="deliveryPlan4Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month5"/>
						<c:choose>
							<c:when test="${costPlan.deliveryPlan5Str != null}">
								<input type="checkbox" name="deliveryPlan5Str" id="deliveryPlan5Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="deliveryPlan5Str" id="deliveryPlan5Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month6"/>
						<c:choose>
							<c:when test="${costPlan.deliveryPlan6Str != null}">
								<input type="checkbox" name="deliveryPlan6Str" id="deliveryPlan6Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="deliveryPlan6Str" id="deliveryPlan6Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month7"/>
						<c:choose>
							<c:when test="${costPlan.deliveryPlan7Str != null}">
								<input type="checkbox" name="deliveryPlan7Str" id="deliveryPlan7Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="deliveryPlan7Str" id="deliveryPlan7Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month8"/>
						<c:choose>
							<c:when test="${costPlan.deliveryPlan8Str != null}">
								<input type="checkbox" name="deliveryPlan8Str" id="deliveryPlan8Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="deliveryPlan8Str" id="deliveryPlan8Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month9"/>
						<c:choose>
							<c:when test="${costPlan.deliveryPlan9Str != null}">
								<input type="checkbox" name="deliveryPlan9Str" id="deliveryPlan9Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="deliveryPlan9Str" id="deliveryPlan9Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month10"/>
						<c:choose>
							<c:when test="${costPlan.deliveryPlan10Str != null}">
								<input type="checkbox" name="deliveryPlan10Str" id="deliveryPlan10Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="deliveryPlan10Str" id="deliveryPlan10Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<fmt:message key="costPlan.month11"/>
						<c:choose>
							<c:when test="${costPlan.deliveryPlan11Str != null}">
								<input type="checkbox" name="deliveryPlan11Str" id="deliveryPlan11Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="deliveryPlan11Str" id="deliveryPlan11Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					<span class="pdl15">
						<fmt:message key="costPlan.month12"/>
						<c:choose>
							<c:when test="${costPlan.deliveryPlan12Str != null}">
								<input type="checkbox" name="deliveryPlan12Str" id="deliveryPlan12Str" checked="checked" class="month">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="deliveryPlan12Str" id="deliveryPlan12Str" class="month">
							</c:otherwise>
						</c:choose>
					</span>
					<span class="pdl15">
					<input class="button" type="submit" value="<fmt:message key="button.search"/>" />
					<input class="button" type="button" id= "btReport" name="btReport" value="<fmt:message key="button.report"/>" />
					</span>
				</td>
				
				
			</tr>
	</table>
</form:form>
</div>
<div class="ftsize12" align="right"> 
	<span><a href="form.htm" title="Thêm mới">Thêm mới</a></span> - 
	<span><a href="upload.htm" title="Import">Import file</a></span> 
</div>
  <div id="doublescroll" >
	<display:table name="${costPlanList}"  class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="rootNo"  class="dpnone ROOT_NO" headerClass="dpnone" media="html"/>
		<display:column property="expensesSupper" titleKey="costPlan.expensesSupper" sortable="true" sortName="EXPENSES_SUPPER" class="dpnone EXPENSES_SUPER" headerClass="dpnone" media="html"/>
	  	<display:column property="expensesCode" titleKey="costPlan.expensesCode" sortable="true" sortName="EXPENSES_CODE" style="max-width:40px;"/>
	  	<display:column property="expensesName" titleKey="costPlan.expensesName" sortable="true" sortName="EXPENSES_NAME" style="min-with:200px;max-width:200px;"/>
	  	<display:column property="deliveryYear" titleKey="costPlan.deliveryYear" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_YEAR" style="max-width:70px;" class="number"/>
		<display:column property="distributionYear" titleKey="costPlan.distributionYear" format="{0,number,###,###,###.#}" sortable="true" sortName="DISTRIBUTION_YEAR" style="max-width:70px;" class="number"/>
		<display:column headerClass="bd" class="bd"/>
		<display:column property="distributionPlan1" titleKey="costPlan.distributionPlan1" format="{0,number,###,###,###.#}" sortable="true" sortName="DISTRIBUTION_PLAN_1"  headerClass="distributionPlan1Str hidden" class="distributionPlan1Str hidden number number" style="max-width:70px;"/>
		<display:column property="distributionPlan2" titleKey="costPlan.distributionPlan2" format="{0,number,###,###,###.#}" sortable="true" sortName="DISTRIBUTION_PLAN_2" headerClass="distributionPlan2Str hidden" class="distributionPlan2Str hidden number" style="max-width:70px;"/>
		<display:column property="distributionPlan3" titleKey="costPlan.distributionPlan3" format="{0,number,###,###,###.#}" sortable="true" sortName="DISTRIBUTION_PLAN_3" headerClass="distributionPlan3Str hidden" class="distributionPlan3Str hidden number" style="max-width:70px;"/>
		<display:column property="distributionPlan4" titleKey="costPlan.distributionPlan4" format="{0,number,###,###,###.#}" sortable="true" sortName="DISTRIBUTION_PLAN_4" headerClass="distributionPlan4Str hidden" class="distributionPlan4Str hidden number" style="max-width:70px;"/>
		<display:column property="distributionPlan5" titleKey="costPlan.distributionPlan5" format="{0,number,###,###,###.#}" sortable="true" sortName="DISTRIBUTION_PLAN_5" headerClass="distributionPlan5Str hidden" class="distributionPlan5Str hidden number" style="max-width:70px;"/>
		<display:column property="distributionPlan6" titleKey="costPlan.distributionPlan6" format="{0,number,###,###,###.#}" sortable="true" sortName="DISTRIBUTION_PLAN_6" headerClass="distributionPlan6Str hidden" class="distributionPlan6Str hidden number" style="max-width:70px;"/>
		<display:column property="distributionPlan7" titleKey="costPlan.distributionPlan7" format="{0,number,###,###,###.#}" sortable="true" sortName="DISTRIBUTION_PLAN_7" headerClass="distributionPlan7Str hidden" class="distributionPlan7Str hidden number" style="max-width:70px;"/>
		<display:column property="distributionPlan8" titleKey="costPlan.distributionPlan8" format="{0,number,###,###,###.#}" sortable="true" sortName="DISTRIBUTION_PLAN_8" headerClass="distributionPlan8Str hidden" class="distributionPlan8Str hidden number" style="max-width:70px;"/>
		<display:column property="distributionPlan9" titleKey="costPlan.distributionPlan9" format="{0,number,###,###,###.#}" sortable="true" sortName="DISTRIBUTION_PLAN_9" headerClass="distributionPlan9Str hidden" class="distributionPlan9Str hidden number" style="max-width:70px;"/>
		<display:column property="distributionPlan10" titleKey="costPlan.distributionPlan10" format="{0,number,###,###,###.#}"sortable="true" sortName="DISTRIBUTION_PLAN_10" headerClass="distributionPlan10Str hidden" class="distributionPlan10Str hidden number" style="max-width:70px;"/>
		<display:column property="distributionPlan11" titleKey="costPlan.distributionPlan11" format="{0,number,###,###,###.#}" sortable="true" sortName="DISTRIBUTION_PLAN_11" headerClass="distributionPlan11Str hidden" class="distributionPlan11Str hidden number" style="max-width:70px;"/>
		<display:column property="distributionPlan12" titleKey="costPlan.distributionPlan12" format="{0,number,###,###,###.#}" sortable="true" sortName="DISTRIBUTION_PLAN_12" headerClass="distributionPlan12Str hidden" class="distributionPlan12Str hidden number" style="max-width:70px;"/>
		<display:column class="bd" headerClass="bd"/>
		<display:column property="deliveryPlan1" titleKey="costPlan.deliveryPlan1" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_1" headerClass="deliveryPlan1Str hidden" class="deliveryPlan1Str hidden number" style="max-width:70px;"/>
		<display:column property="deliveryPlan2" titleKey="costPlan.deliveryPlan2" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_2" headerClass="deliveryPlan2Str hidden" class="deliveryPlan2Str hidden number" style="max-width:70px;"/>
		<display:column property="deliveryPlan3" titleKey="costPlan.deliveryPlan3" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_3" headerClass="deliveryPlan3Str hidden" class="deliveryPlan3Str hidden number" style="max-width:70px;"/>
		<display:column property="deliveryPlan4" titleKey="costPlan.deliveryPlan4" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_4" headerClass="deliveryPlan4Str hidden" class="deliveryPlan4Str hidden number" style="max-width:70px;"/>
		<display:column property="deliveryPlan5" titleKey="costPlan.deliveryPlan5" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_5" headerClass="deliveryPlan5Str hidden" class="deliveryPlan5Str hidden number" style="max-width:70px;"/>
		<display:column property="deliveryPlan6" titleKey="costPlan.deliveryPlan6" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_6" headerClass="deliveryPlan6Str hidden" class="deliveryPlan6Str hidden number" style="max-width:70px;"/>
		<display:column property="deliveryPlan7" titleKey="costPlan.deliveryPlan7" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_7" headerClass="deliveryPlan7Str hidden" class="deliveryPlan7Str hidden number" style="max-width:70px;"/>
		<display:column property="deliveryPlan8" titleKey="costPlan.deliveryPlan8" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_8" headerClass="deliveryPlan8Str hidden" class="deliveryPlan8Str hidden number" style="max-width:70px;"/>
		<display:column property="deliveryPlan9" titleKey="costPlan.deliveryPlan9" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_9" headerClass="deliveryPlan9Str hidden" class="deliveryPlan9Str hidden number" style="max-width:70px;"/>
		<display:column property="deliveryPlan10" titleKey="costPlan.deliveryPlan10" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_10" headerClass="deliveryPlan10Str hidden" class="deliveryPlan10Str hidden number" style="max-width:70px;"/>
		<display:column property="deliveryPlan11" titleKey="costPlan.deliveryPlan11" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_11" headerClass="deliveryPlan11Str hidden" class="deliveryPlan11Str hidden number" style="max-width:70px;"/>
		<display:column property="deliveryPlan12" titleKey="costPlan.deliveryPlan12" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_12" headerClass="deliveryPlan12Str hidden" class="deliveryPlan12Str hidden number" style="max-width:70px;"/>
		<display:column headerClass="bd" class="bd"/>
		<display:column property="deliverySum" titleKey="costPlan.deliverySum" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_SUM" style="min-width:70px;" class="number"/>
		<display:column property="distributionSum" titleKey="costPlan.distributionSum" format="{0,number,###,###,###.#}" sortable="true" sortName="DISTRIBUTION_SUM"  style="min-width:70px;"  class="number"/>
		<display:column property="description" titleKey="costPlan.description" sortable="true" sortName="DESCRIPTION" />
		<display:column titleKey="global.management" media="html" class="centerColumnMana">
 			<c:choose>
				<c:when test="${empty item.rootNo || item.rootNo==0}">
					<a href="form.htm?id=${item.id}&code=${item.expensesCode}&departmentId=${item.departmentId}&costYear=${item.costYear}" title="${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
 					<a href="#" onclick="javascript:DeletecostPlan('${item.expensesSupper}','${item.departmentId}','${item.costYear}','${item.id}');return false;"><fmt:message key="global.form.xoa"/></a>
		
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

<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("departmentId");
	  mytext.focus();
	}
	onload = focusIt;

	$(function() {
		${highlight}				
	  	});   
</script>

  
  <script type="text/javascript">
function DeletecostPlan(expensesSuper,departmentId,costYear,id) {
	var r = false;
	
				r = confirm('<fmt:message key="messsage.confirm.delete"/>');
				if (r == true) {
					$.getJSON("${pageContext.request.contextPath}/cost/costPlan/ajax/deletecostPlan.htm", {id: id,expensesSuper:expensesSuper,departmentId: departmentId,costYear: costYear}, function(i){
						$("#costPlan").submit();
					});
				}
			}

$("#expensesCode").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cost/ajax/get-costPlan-inf-search.htm?focus=expensesCode',
});

$("#expensesName").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cost/ajax/get-costPlan-inf-search.htm?focus=expensesName',
});

/*$("#workId").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cost/ajax/get-costPlan-inf-search.htm?focus=workId',
});*/

</script>
<script type="text/javascript">
$(document).ready(function() {
	
	$('#checkboxs input:checked').each(function() {
		var ID = $(this).attr('id');
		$("."+ID).removeClass("hidden");
	});

	$('#checkboxs1 input:checked').each(function() {
		var ID = $(this).attr('id');
		$("."+ID).removeClass("hidden");
	});
});
$("#checkAll").click(function() {
    // this function will get executed every time the #home element is clicked (or tab-spacebar changed)
    if($("#checkAll").is(":checked")) // "this" refers to the element that fired the event
    {
       
    	$('#checkboxs input:checkbox:not(:checked)').each(function() {
    		
    		var ID = $(this).attr('id');
    		$("#" + ID).attr('checked',true);
    		$("."+ID).removeClass("hidden");
    	});
    }
    else
    {
    	$('#checkboxs input:checked').each(function() {
    		$(this).removeAttr('checked');
    		var ID = $(this).attr('id');
    		$("."+ID).addClass("hidden");
    	});
    }
});

$("#checkAll1").click(function() {
    // this function will get executed every time the #home element is clicked (or tab-spacebar changed)
    if($("#checkAll1").is(":checked")) // "this" refers to the element that fired the event
    {
       
    	$('#checkboxs1 input:checkbox:not(:checked)').each(function() {
    		
    		var ID = $(this).attr('id');
    		$("#" + ID).attr('checked',true);
    		$("."+ID).removeClass("hidden");
    	});
    }
    else
    {
    	$('#checkboxs1 input:checked').each(function() {
    		$(this).removeAttr('checked');
    		var ID = $(this).attr('id');
    		$("."+ID).addClass("hidden");
    	});
    }
});
 function DoubleScroll(element) {
    var scrollbar= document.createElement('div');
    scrollbar.appendChild(document.createElement('div'));
    scrollbar.style.overflow= 'auto';
    scrollbar.style.overflowY= 'hidden';
    scrollbar.firstChild.style.width= element.scrollWidth+'px';
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
	var ID = $(this).attr('id');
	
	if ($(this).is(':checked')) {
		$("."+ID).removeClass("hidden");
	} else {
		$("."+ID).addClass("hidden");
	}
});

$("#departmentId").change(function(){
	 var departmentId = $(this).find("option:selected").text();
   	$("#deptName").val(departmentId);
	});

$(document).ready(function(){

	var departmentId = $("#departmentId").find("option:selected").text();
  	$("#deptName").text(departmentId.toUpperCase());

});
</script>
<script type="text/javascript">
	$("#btReport").click(function(){
			window.open('${pageContext.request.contextPath}/cost/costPlan/report.htm?departmentId=' + $("select#departmentId").val()+
					'&costYear=' + $("select#costYear").val()+
					'&expensesSupper=' + $("select#expensesSupper").val()+
					'&expensesName=' + $("#expensesName").val(),
					'_blank','width=300,height=200,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0','yes|no|1|0',true);
		
	});
</script> 