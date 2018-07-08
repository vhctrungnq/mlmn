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
<form:form method="post" commandName="costDetail" action="form.htm">
	
	<div>
		<form:hidden path="id"/>
		<form:hidden path="rootNo"/>
		<form:hidden path="workSuper"/>
		<form:hidden path="deptName"/>
		<input type="hidden" name="superID" id="superID" value="${superID}">
		<input type="hidden" name="superName" id="superName" value="${superName}">
		<input type="hidden" name="taskSup" id="taskSup" value="${taskSup}">
		<input type="hidden" name="superRoot" id="superRoot" value="${superRoot}">
	</div>
	<table class="simple2" >
		
		<tr>
			<td ><fmt:message key="costDetail.departmentId"/><span class="note">(*)</span></td>
			<td >	
				<b><i>${costDetail.deptName}</i></b><form:hidden path="departmentId" />
				<form:errors path="departmentId" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costDetail.costDate"/><span class="note">(*)</span></td>
			<td >
				<b><i>${costDetail.costMonth}</i></b><form:hidden path="costMonth" />
				 - 
				<b><i>${costDetail.costYear}</i></b><form:hidden path="costYear" />
			</td>
			
				<c:choose>
	                <c:when test="${empty costDetail.id}">
		                <td style="padding-left: 10px;"><fmt:message key="costDetail.expensesCodeSuper"/> </td>
						<td>
		                	<b><i>${costDetail.expensesCode}</i></b><form:hidden path="expensesCode" /><form:errors path="expensesCode" class="error"/>
		                </td>
		                <td style="padding-left: 10px;"><fmt:message key="costDetail.expensesSupper"/> </td>
						<td>
		                	<b><i>${superName}</i></b>
		                </td>
	                </c:when>
	                <c:otherwise>
	                	 <td style="padding-left: 10px;"><fmt:message key="costDetail.expensesCode"/> </td>
						<td >
		                    <b><i>${costDetail.expensesCode}</i></b><form:hidden path="expensesCode" />
							<form:errors path="expensesCode" class="error"/>
						</td>
						<td style="padding-left: 10px;"><fmt:message key="costDetail.expensesSupper"/> </td>
						<td>
		                	<b><i>${superName}</i></b>
		                </td>
	                </c:otherwise>
	            </c:choose>  	
			
			
		</tr>
		<tr>
			<td style="width: 80px;"><fmt:message key="costDetail.taskNo"/></td>
			<td >
				<form:input type ="text" path="taskNo"  maxlength="20"/>
				<br/><form:errors path="taskNo" class="error"/>
			</td>
			<td style="width:160px;padding-left: 10px; "><fmt:message key="costDetail.workCode"/></td>
			<td>
				<form:input type ="text" path="workCode"  maxlength="20"/>
				<br/><form:errors path="workCode" class="error"/>
			</td>
			
			<td style="width:160px;padding-left: 10px; "><fmt:message key="costDetail.workName"/><span class="note">(*)</span></td>
			<td colspan="3">
				<c:choose>
	                <c:when test="${empty costDetail.id}">
	                	<form:select  path="workId"  style="width: 140px;height:20px; padding-top: 4px;">
			           		<option value=""><fmt:message key="global.Chosse"/></option>
			           		<c:forEach var="item" items="${workList}">
								<c:choose>
					                <c:when test="${item.id == costDetail.workId}">
					                    <option value="${item.id}" selected="selected">${item.workName}</option>
					                </c:when>
									<c:otherwise>
										<option value="${item.id}">${item.workName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>	
						<form:input type ="text" path="workName"  maxlength="200" style="width:66%;"/>
						<br/><form:errors path="workName" class="error"/>
	                </c:when>
	                <c:otherwise>
	                	<form:hidden path="workId"/>
	                	<form:input type ="text" path="workName"  maxlength="200" style="width:95%;"/>
						<br/><form:errors path="workName" class="error"/>
	                </c:otherwise>
	            </c:choose>  	
				
			</td>
		</tr>
		<tr>
			<td style="width:120px; "><fmt:message key="costDetail.deliveryPlanYear"/></td>
			<td>
				<form:input type ="text" path="deliveryPlanYear"  maxlength="10"/>
				<br/><form:errors path="deliveryPlanYear" class="error"/>
			</td>
			<td style="width:120px;padding-left: 10px; "><fmt:message key="costDetail.adjustmentPlanYear"/></td>
			<td>
				<form:input type ="text" path="adjustmentPlanYear"  maxlength="10"/>
				<br/><form:errors path="adjustmentPlanYear" class="error"/>
			</td>
			<td style="padding-left: 10px; "><fmt:message key="costDetail.doneToLastm"/></td>
			<td>
				<form:input type ="text" path="doneToLastm"  maxlength="10"/>
				<br/><form:errors path="doneToLastm" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costDetail.deliveryPlanCurrentm"/></td>
			<td>
				<form:input type ="text" path="deliveryPlanCurrentm"  maxlength="10"/>
				<br/><form:errors path="deliveryPlanCurrentm" class="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="costDetail.doneCurrentm"/></td>
			<td>
				<form:input type ="text" path="doneCurrentm"  maxlength="10"/>
				<br/><form:errors path="doneCurrentm" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costDetail.comulativeCurrentm"/></td>
			<td>
				<form:input type ="text" path="comulativeCurrentm"  maxlength="10"/>
				<br/><form:errors path="comulativeCurrentm" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costDetail.remainingCost"/></td>
			<td>
				<form:input type ="text" path="remainingCost"  maxlength="10"/>
				<br/><form:errors path="remainingCost" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costDetail.rateDoneLastm"/></td>
			<td>
				<form:input type ="text" path="rateDoneLastm"  maxlength="10"/>
				<br/><form:errors path="rateDoneLastm" class="error"/>
			</td>
		</tr>
		<tr>
		<%-- 	<td><fmt:message key="costDetail.leaderResponsive"/></td>
			<td>
				<form:input type ="text" path="leaderResponsive"  maxlength="40"/>
				<br/><form:errors path="leaderResponsive" class="error"/>
			</td> --%>
			<td ><fmt:message key="costDetail.jobClassification"/></td>
			<td>
				<form:input type ="text" path="jobClassification"  maxlength="150"/>
				<br/><form:errors path="jobClassification" class="error"/>
			</td>
			<%-- <td style="padding-left: 10px;"><fmt:message key="costDetail.reasonNotDone"/></td>
			<td>
				<form:input type ="text" path="reasonNotDone"  maxlength="400"/>
				<br/><form:errors path="reasonNotDone" class="error"/>
			</td> --%>
			<td style="padding-left: 10px;"><fmt:message key="costDetail.adjustLastm"/></td>
			<td>
				<form:input type ="text" path="adjustLastm"  maxlength="10"/>
				<br/><form:errors path="adjustLastm" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costDetail.statusPlan"/></td>
			<td colspan="3">
				<form:select  path="statusPlan"  style="width: 140px;height:20px; padding-top: 4px;">
	           		<option value=""><fmt:message key="global.Chosse"/></option>
	           		<c:forEach var="item" items="${statusPlanList}">
						<c:choose>
			                <c:when test="${item.name == costDetail.statusPlan}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>	
				
				<br/><form:errors path="statusPlan" class="error"/>
			</td>
			
		</tr>
		<%-- <tr>
			
			<td><fmt:message key="costDetail.contractName"/></td>
			<td>
				<form:input type ="text" path="contractName"  maxlength="200"/>
				<br/><form:errors path="contractName" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costDetail.contractNo"/></td>
			<td>
				<form:input type ="text" path="contractNo"  maxlength="10"/>
				<br/><form:errors path="contractNo" class="error"/>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costDetail.contractDate"/></td>
			<td>
				<input type ="text"  value="${contractDate}" name="contractDate" id="contractDate" size="16" maxlength="16" style="width:100px">
	   			<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				<font color="red">${errorContractDate}<form:errors path="contractDate"/></font>
			</td>
			<td style="padding-left: 10px;"><fmt:message key="costDetail.contractType"/></td>
			<td>
				<form:input type ="text" path="contractType"  maxlength="200"/>
				<br/><form:errors path="contractType" class="error"/>
			</td>
		</tr> --%>
		<tr>
			
			<%-- <td style="padding-left: 10px;"><fmt:message key="costDetail.departmentDoneContract"/></td>
			<td colspan="5">
				<form:select  path="departmentDoneContract"  style="width: 140px;height:20px; padding-top: 4px;">
	           		
	           		<option value=""><fmt:message key="global.Chosse"/></option>
	           		<c:forEach var="item" items="${departmentList}">
						<c:choose>
			                <c:when test="${item.deptCode == costDetail.departmentDoneContract}">
			                    <option value="${item.deptCode}" selected="selected">${item.deptCode}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.deptCode}">${item.deptCode}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>	
				<br/><form:errors path="departmentDoneContract" class="error"/>
			</td> --%>
		</tr>
		<tr>
			<td><fmt:message key="costDetail.description"/></td>
			<td colspan="7">
				<form:textarea path="description" style="height: 50px" class="wid100" maxlength="200"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td colspan="7">
				<input type="hidden" id="action" name="action">
				<input type="submit" class="button" onclick="setAction('saveAndAdd')" name="saveAndAdd" value="<fmt:message key="global.button.saveAndAdd"/>"/>
       			<input type="submit" class="button" name="save"  onclick="setAction('save')" value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="<%=request.getContextPath() %>/cost/costDetail/list.htm"'>
			</td>
		</tr>
	</table>
</form:form>
 <div id="doublescroll" >
	<display:table name="${costDetailList}"  class="simple2" id="item" requestURI="" pagesize="50" >
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="rootNo"  class="dpnone ROOT_NO" headerClass="dpnone" media="html"/>
		<display:column property="workSuper" titleKey="costDetail.workSuper"  class="dpnone WORK_SUPER" headerClass="dpnone" media="html"/>
	  	<display:column property="expensesCode" titleKey="costDetail.expensesCode"  style="min-width:70px;" />
	  	<display:column property="taskNo" titleKey="costDetail.taskNo"  style="min-with:70px;" />
	  	<display:column property="workCode" titleKey="costDetail.workCode"  style="min-width:70px;" />
	  	<display:column property="workName" titleKey="costDetail.workName" style="min-width:200px;max-width:200px;" />
		<display:column property="deliveryPlanYear" titleKey="costDetail.deliveryPlanYear" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="adjustmentPlanYear" titleKey="costDetail.adjustmentPlanYear" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="doneToLastm" title="Đã TH hết T${cost_monthLast}"  format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlanCurrentm" title="KH giao T${costMonth}" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="doneCurrentm" title="Đã TH T${costMonth}" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="comulativeCurrentm" title="Lũy kế hết T${costMonth}"  format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="remainingCost" titleKey="costDetail.remainingCost" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="rateDoneLastmStr" title="%TH/ĐK T${cost_monthLast}"  format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<%-- <display:column property="leaderResponsive" titleKey="costDetail.leaderResponsive"  style="min-width:70px;"/>
		<display:column property="reasonNotDone" titleKey="costDetail.reasonNotDone"  style="min-width:70px;"/> --%>
		<display:column property="jobClassification" titleKey="costDetail.jobClassification" style="min-width:70px;" />
		<display:column property="adjustLastm" title="Điều chỉnh T${cost_monthLast}" format="{0,number,###,###,###.#}" style="min-width:70px;" class="number"/>
		<display:column property="statusPlan" titleKey="costDetail.statusPlan" style="min-width:70px;"/>
		<%-- <display:column property="contractName" titleKey="costDetail.contractName"  style="min-width:70px;"/>
		<display:column property="contractNo" titleKey="costDetail.contractNo" style="min-width:70px;"/>
		<display:column property="contractDate" titleKey="costDetail.contractDate" format="{0,date,dd/MM/yyyy}" style="min-width:70px;"/>
		<display:column property="contractType" titleKey="costDetail.contractType"  style="min-width:70px;"/>
		<display:column property="departmentDoneContract" titleKey="costDetail.departmentDoneContract" style="min-width:70px;"/> --%>
		<display:column property="description" titleKey="costDetail.description" style="min-width:70px;"/>
		<display:column titleKey="global.management" media="html" class="centerColumnMana">
 			<a href="form.htm?superID=${item.id}&deptName=${deptName}" title="${item.id}"><fmt:message key="global.form.detail"/></a>&nbsp;
			<a href="form.htm?id=${item.id}&deptName=${deptName}&superID=${item.workSuper}" title="${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
		</display:column>
	</display:table>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<script type="text/javascript">
 /* Calendar.setup({
    inputField		:	"contractDate",	// id of the input field   
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   true					// double-click mode
}); */

$("#jobClassification").autocomplete({
	source: '${pageContext.request.contextPath}/cost/costDetail/ajax/get-costDetail-inf-search.htm?focus=jobClassification',
});

$("#contractType").autocomplete({
	source: '${pageContext.request.contextPath}/cost/costDetail/ajax/get-costDetail-inf-search.htm?focus=contractType',
});

$("#reasonNotDone").autocomplete({
	source: '${pageContext.request.contextPath}/cost/costDetail/ajax/get-costDetail-inf-search.htm?focus=reasonNotDone',
});

$("#workName").autocomplete({
	source: '${pageContext.request.contextPath}/cost/costDetail/ajax/get-workName-inf-search.htm?expensesCode='+$("#expensesCode").val(),
});

function setAction(value) {
	  var action = document.getElementById("action");
	  action.value = value;

	  return true;
	 }
	
$(function() {
	${highlight}	
		
	 $("#workId").change(function(){
		 var workID = $(this).find("option:selected").text().replace(/_/g,'');
	    	$("#workName").val(workID);
		});
		
	 $("#comulativeCurrentm").focus(function () {
	    	var thlast= $("#doneToLastm").val();
		    var thcurent = $("#doneCurrentm").val();
		    if (thlast==''|| thlast== null)
		    {
	    	 thlast=0;
		    }
		    if (thcurent==''|| thcurent== null)
		    {
		    	thcurent=0;
		    }
		    var kq= parseFloat(thlast) + parseFloat(thcurent);
	    	
	    	$("#comulativeCurrentm").val(kq);
		});
		
	 $("#remainingCost").focus(function () {
	    	var thlast= $("#doneToLastm").val();
		    var thcurent = $("#doneCurrentm").val();
		    var khnam = $("#deliveryPlanYear").val();
		    if (thlast==''|| thlast== null)
		    {
	    	 thlast=0;
		    }
		    if (thcurent==''|| thcurent== null)
		    {
		    	thcurent=0;
		    }
		    if (khnam==''|| khnam== null)
		    {
		    	khnam=0;
		    }
		    var kq= parseFloat(khnam) - parseFloat(thlast) - parseFloat(thcurent);
	    	
	    	$("#remainingCost").val(kq);
		});

	 $("#doneToLastm").change(function () {
	    	var thlast= $("#doneToLastm").val();
		    var thcurent = $("#doneCurrentm").val();
		    var khnam = $("#deliveryPlanYear").val();
		    if (thlast==''|| thlast== null)
		    {
	    	 thlast=0;
		    }
		    if (thcurent==''|| thcurent== null)
		    {
		    	thcurent=0;
		    }
		    if (khnam==''|| khnam== null)
		    {
		    	khnam=0;
		    }
		    var kq1= parseFloat(thlast) + parseFloat(thcurent);
	    	
		    var kq= parseFloat(khnam)-parseFloat(kq1);
	    	
	    	
	    	$("#comulativeCurrentm").val(kq1);
	    	$("#remainingCost").val(kq);
		});
	 $("#doneCurrentm").change(function () {
	    	var thlast= $("#doneToLastm").val();
		    var thcurent = $("#doneCurrentm").val();
		    var khth = $("#deliveryPlanCurrentm").val();
		    var khnam = $("#deliveryPlanYear").val();
		    if (thlast==''|| thlast== null)
		    {
	    	 thlast=0;
		    }
		    if (thcurent==''|| thcurent== null)
		    {
		    	thcurent=0;
		    }
		    if (khnam==''|| khnam== null)
		    {
		    	khnam=0;
		    }

		    if (khth==''|| khth== null||khth==0)
		    {
		    	$("#rateDoneLastm").val('');
		    }
		    else
		    {
		    	var kq2= parseFloat(thcurent)*100/parseFloat(khth);
	    		
		    	$("#rateDoneLastm").val(kq2);
		    }
		    var kq1= parseFloat(thlast) + parseFloat(thcurent);
	    	
		    var kq= parseFloat(khnam)-parseFloat(kq1);

		   	$("#comulativeCurrentm").val(kq1);
	    	$("#remainingCost").val(kq);
	    	
		});
	 $("#deliveryPlanYear").change(function () {
	    	var thlast= $("#doneToLastm").val();
		    var thcurent = $("#doneCurrentm").val();
		    var khnam = $("#deliveryPlanYear").val();
		    if (thlast==''|| thlast== null)
		    {
	    	 thlast=0;
		    }
		    if (thcurent==''|| thcurent== null)
		    {
		    	thcurent=0;
		    }
		    if (khnam==''|| khnam== null)
		    {
		    	khnam=0;
		    }
		    var kq1= parseFloat(thlast) + parseFloat(thcurent);
	    	
		    var kq= parseFloat(khnam)-parseFloat(kq1);
	    	
	    	$("#comulativeCurrentm").val(kq1);
	    	$("#remainingCost").val(kq);
		});

	 $("#deliveryPlanCurrentm").change(function () {
	    	var thcurent = $("#doneCurrentm").val();
		    var khth = $("#deliveryPlanCurrentm").val();
		    
		    if (thcurent==''|| thcurent== null)
		    {
		    	thcurent=0;
		    }
		    
		    if (khth==''|| khth== null||khth==0)
		    {
		    	$("#rateDoneLastm").val('');
		    }
		    else
		    {
		    	var kq2= parseFloat(thcurent)*100/parseFloat(khth);
	    		
		    	$("#rateDoneLastm").val(kq2);
		    }
		});

	 $("#rateDoneLastm").focus(function () {
		 var thcurent = $("#doneCurrentm").val();
		    var khth = $("#deliveryPlanCurrentm").val();
		    
		    if (thcurent==''|| thcurent== null)
		    {
		    	thcurent=0;
		    }
		    
		    if (khth==''|| khth== null||khth==0)
		    {$("#rateDoneLastm").val('');
		   }
		    else
		    {
		    	var kq2= parseFloat(thcurent)*100/parseFloat(khth);
	    		
		    	$("#rateDoneLastm").val(kq2);
		    }

		    
		});
   
}); 

function DoubleScroll(element) {
    var scrollbar= document.createElement('div');
    scrollbar.appendChild(document.createElement('div'));
    scrollbar.style.overflow= 'auto';
    scrollbar.style.overflowY= 'hidden';
   // scrollbar.firstChild.style.width= element.scrollWidth+'px';
    scrollbar.firstChild.style.width= '2070px';
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




</script>

