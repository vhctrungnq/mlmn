
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title} THÁNG ${costMonth}/${costYear} CỦA ĐƠN VỊ: <span id ="deptName"></span></content> 	
<style>

.dpnone {
	display: none;
}

.number {
	text-align: right;
}

.pdl15 { padding-left:15px; }

.hidden {display: none;}

.pdt {padding-top:30px; }

#doublescroll { overflow: auto; overflow-y: auto; margin-top:-10px; }  

</style>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
<div>
<form:form commandName="costDetail" method="POST" action="list.htm">
	<input type="hidden" name="highlight" id="highlight" value="${highlight}">
	<%-- <input type="hidden" name="deptName" id="deptName" value="${deptName}"> --%>
	<table style="width: 100%;">
		<tr>
			<td style="width: 70px;"><fmt:message key="costDetail.departmentId"/></td>
			<td style="width: 200px;">
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
			<td style="width:50px; padding-left: 5px;"><fmt:message key="costDetail.costDate"/></td>
				<td style="width:120px;">
					<select name="costMonth" id="costMonth" style="width: 40px;height:20px; padding-top: 4px;">
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
					</select> - 
					<select name="costYear" id="costYear" style="width: 60px;height:20px; padding-top: 4px;">
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
			<td style="width:90px; padding-left: 5px;"><fmt:message key="costDetail.expensesSupper"/> </td>
			<td style="width: 200px;">
				<form:select path="expensesCode" style="width: 200px;height:20px; padding-top: 4px;">
					<option value=""><fmt:message key="global.Chosse"/></option>
	           		<c:forEach var="item" items="${expensesList}">
						<c:choose>
			                <c:when test="${item.expensesCode == costDetail.expensesCode}">
			                    <option value="${item.expensesCode}" selected="selected">${item.expensesName}</option>
			                </c:when>
							<c:otherwise>	
								<option value="${item.expensesCode}">${item.expensesName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select> 
			</td>
			<td style="padding-left: 5px;width: 120px;"><fmt:message key="costDetail.workName"/></td>
			<td style="width: 120px;">
				<form:input path="workName" width="80px" size="25"/>
			</td>
			<td style="padding-left: 5px;">
					<input class="button" type="submit"  value="<fmt:message key="button.search"/>" />
					<input class="button" type="button" id="btLapKH" value="<fmt:message key="global.lapKH"/>"  >
				</td>
			</tr>
	</table>
</form:form>
</div>
<div class="paddingTop10" align="right">
	<input class="button" type="button" id="btChuyen" name="btChuyen" value="<fmt:message key="global.btChuyen"/>" />
	<input class="button" type="button" id= "btReportKH" name="btReportKH" value="<fmt:message key="button.reportKH"/>" />
	<input class="button" type="button" id= "btReportTH" name="btReportTH" value="<fmt:message key="button.reportTH"/>" />
</div>
 <h2 class="pdt" style="margin-top: -50px;">A. CHI TIẾT KẾ HOẠCH GIAO</h2>
 <div id="doublescroll" >
 <display:table name="${costDetailList}"  class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column class="centerColumnMana selectAllCheck" style="width:30px;" title="<input type='checkbox' id='selectAllCheck' onClick='javascript:funcSelectAll()' value='Select All' />" media="html">
				<c:if test="${empty item.rootNo || item.rootNo >100 || item.rootNo==0}">
					<input  type="checkbox" name="lang" class ="idplan" value="${item.id}"/>
				</c:if>
	    </display:column><display:column property="rootNo"  class="dpnone ROOT_NO" headerClass="dpnone" media="html"/>
		<display:column property="workSuper" titleKey="costDetail.workSuper" sortable="true" sortName="WORK_SUPER" class="dpnone WORK_SUPER" headerClass="dpnone" media="html"/>
	  	<display:column property="expensesCode" titleKey="costDetail.expensesCode" sortable="true" sortName="EXPENSES_CODE" style="min-width:70px;" group="1"/>
	  	<display:column property="taskNo" titleKey="costDetail.taskNo" sortable="true" sortName="TASK_NO" style="min-width:70px;"/>
	  	<display:column property="workCode" titleKey="costDetail.workCode" sortable="true" sortName="WORK_CODE" style="min-width:70px;"/>
	  	<display:column property="workName" titleKey="costDetail.workName" sortable="true" sortName="WORK_NAME" style="min-width:200px;max-width:200px;"/>
		<display:column property="deliveryPlanYear" titleKey="costDetail.deliveryPlanYear"  format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_YEAR" style="min-width:70px;" class="number"/>
		<display:column property="adjustmentPlanYear" titleKey="costDetail.adjustmentPlanYear" format="{0,number,###,###,###.#}" sortable="true" sortName="ADJUSTMENT_PLAN_YEAR"  style="min-width:70px;" class="number"/>
		<display:column property="doneToLastm" title="Đã TH hết T${cost_monthLast}" format="{0,number,###,###,###.#}" sortable="true" sortName="DONE_TO_LASTM"  style="min-width:70px;" class="number"/>
		<display:column property="deliveryPlanCurrentm" title="KH giao T${costMonth}" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_CURRENTM" style="min-width:70px;" class="number"/>
		<display:column property="doneCurrentm" title="Đã TH T${costMonth}" format="{0,number,###,###,###.#}" sortable="true" sortName="DONE_CURRENTM" style="min-width:70px;" class="number"/>
		<display:column property="comulativeCurrentm" title="Lũy kế hết T${costMonth}" format="{0,number,###,###,###.#}" sortable="true" sortName="COMULATIVE_CURRENTM" style="min-width:70px;" class="number"/>
		<display:column property="remainingCost" titleKey="costDetail.remainingCost" format="{0,number,###,###,###.#}" sortable="true" sortName="REMAINING_COST" style="min-width:70px;" class="number"/>
		<display:column property="rateDoneLastmStr" title="%TH/ĐK T${cost_monthLast}"  sortable="true" sortName="RATE_DONE_LASTM"  style="min-width:70px;" class="number"/>
		<%-- <display:column property="leaderResponsive" titleKey="costDetail.leaderResponsive" sortable="true" sortName="LEADER_RESPONSIVE"  style="min-width:70px;"/>
		<display:column property="reasonNotDone" titleKey="costDetail.reasonNotDone" sortable="true" sortName="REASON_NOT_DONE"  style="min-width:70px;"/>
		 --%>
		 <display:column property="jobClassification" titleKey="costDetail.jobClassification" sortable="true" sortName="JOB_CLASSIFICATION"  style="min-width:70px;"/>
		 <display:column property="adjustLastm" title="Điều chỉnh T${cost_monthLast}" format="{0,number,###,###,###.#}" sortable="true" sortName="ADJUST_LASTM"  style="min-width:70px;"  class="number"/>
		<display:column property="statusPlan" titleKey="costDetail.statusPlan" sortable="true" sortName="STATUS_PLAN"  style="min-width:70px;"/>
		<%-- <display:column property="contractName" titleKey="costDetail.contractName" sortable="true" sortName="CONTRACT_NAME"  style="min-width:100px;"/>
		<display:column property="contractNo" titleKey="costDetail.contractNo" sortable="true" sortName="CONTRACT_NO"  style="min-width:70px;"/>
		<display:column property="contractDate" titleKey="costDetail.contractDate" format="{0,date,dd/MM/yyyy}" sortable="true" sortName="CONTRACT_DATE"  style="min-width:70px;"/>
		<display:column property="contractType" titleKey="costDetail.contractType" sortable="true" sortName="CONTRACT_TYPE"  style="min-width:70px;"/>
		<display:column property="departmentDoneContract" titleKey="costDetail.departmentDoneContract" sortable="true"  sortName="DEPARTMENT_DONE_CONTRACT" style="min-width:70px;"/>
		 --%>
		 <display:column property="description" titleKey="costDetail.description" sortable="true" sortName="DESCRIPTION"  style="min-width:70px;"/>
		<display:column titleKey="global.management" media="html" class="centerColumnMana">
 			<a href="form.htm?superID=${item.id}" title="${item.id}"><fmt:message key="global.form.detail"/></a>&nbsp;
			<a href="form.htm?id=${item.id}&superID=${item.workSuper}" title="${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
			<a href="#" onclick="javascript:DeletecostDetail('${item.id}');return false;"><fmt:message key="global.form.xoa"/></a>
		</display:column>
	   
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>

<div id="resultA" class="pdt">
	<%-- <jsp:scriptlet>
	     org.displaytag.decorator.TotalTableDecorator totals = new org.displaytag.decorator.TotalTableDecorator();
	     totals.setTotalLabel("Tổng");
	     pageContext.setAttribute("totals", totals);
	  	</jsp:scriptlet> --%>
	<h2>B. TỔNG HỢP THỰC</h2>
	<display:table name="${sumList}"  class="simple2" id="item1" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true" decorator="org.displaytag.decorator.TotalTableDecorator">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item1_rowNum}"/> </display:column>
		<display:column property="jobClassification" titleKey="costDetail.jobClassification" sortable="true" sortName="JOB_CLASSIFICATION"  />
		<display:column property="countTask" titleKey="costDetail.countTask" sortable="true" sortName="COUNT_TASK"  class="number" total="true" format="{0,number,0}"/>
		<display:column property="doneToLastm" title="Đã TH hết T${cost_monthLast}" format="{0,number,###,###,###.#}" sortable="true" sortName="DONE_TO_LASTM"  class="number" total="true"/>
		<display:column property="deliveryPlanCurrentm" title="KH giao T${costMonth}" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_CURRENTM"  class="number" total="true" />
		<display:column property="doneCurrentm" title="Đã TH T${costMonth}" format="{0,number,###,###,###.#}" sortable="true" sortName="DONE_CURRENTM" class="number" total="true"/>
		<display:column property="comulativeCurrentm" title="Lũy kế hết T${costMonth}" format="{0,number,###,###,###.#}" sortable="true" sortName="COMULATIVE_CURRENTM" class="number" total="true"/>
		<display:column property="remainingCost" titleKey="costDetail.remainingCost" format="{0,number,###,###,###.#}" sortable="true" sortName="REMAINING_COST" class="number" total="true"/>
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName1}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName1}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName1}.xls" />

	</display:table>
</div>

<div id="resultB" class="pdt">
	<h2>C. CHI TIẾT HỦY/CHƯA THỰC HIỆN KH CHI PHÍ GIAO TRONG THÁNG ${costMonth}/${costYear} </h2>
	<display:table name="${notDoneList}"  class="simple2" id="item2" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true" decorator="org.displaytag.decorator.TotalTableDecorator">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item2_rowNum}"/> </display:column>
		<display:column property="workCode" titleKey="costDetail.workCode" sortable="true" sortName="WORK_CODE" />
	  	<display:column property="deliveryPlanCurrentm" titleKey="costDetail.moneyNotUse" format="{0,number,###,###,###.#}" sortable="true" sortName="DELIVERY_PLAN_CURRENTM" class="number"  total="true"/>
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName2}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName2}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName2}.xls" />

	</display:table>
</div>

<div id="resultC" class="pdt">
	<h2>D. CÁC CÔNG VIỆC PHÁT SINH TRONG KẾ HOẠCH THÁNG ${costMonth}/${costYear} </h2>
	<display:table name="${ariseNewList}"  class="simple2" id="item3" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true" decorator="org.displaytag.decorator.TotalTableDecorator">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item3_rowNum}"/> </display:column>
		<display:column property="workName" titleKey="costDetail.workName" sortable="true" sortName="WORK_NAME" />
		<display:column property="doneCurrentm" titleKey="costDetail.arise" format="{0,number,###,###,###.#}" sortable="true" sortName="DONE_CURRENTM" class="number" total="true"/>
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName3}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName3}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName3}.xls" />

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
function DeletecostDetail(id) {
	var r = false;
	$.getJSON("${pageContext.request.contextPath}/cost/costDetail/ajax/checkBeforDelete.htm", {id:id}, function(j){
		if (j==1)
			{
				r = confirm('Kế hoạch chi phí chi tiết đang có ràng buộc');
				$("#costDetail").submit();
			}
		else
			{
				r = confirm('<fmt:message key="messsage.confirm.delete"/>');
				if (r == true) {
					$.getJSON("${pageContext.request.contextPath}/cost/costDetail/ajax/deletecostDetail.htm", {id: id}, function(i){
						$("#costDetail").submit();
					});
				}
			}
	});
}

function LapKeHoachChoThang() {
	var departmentId = $("#departmentId").val();
	var costMonth = $("#costMonth").val() ;
	var costYear = $("#costYear").val();
	var r = false;
	
	$.getJSON("${pageContext.request.contextPath}/cost/costDetail/ajax/checkBeforCreatePlan.htm", {departmentId: departmentId,costMonth: costMonth,costYear: costYear}, function(j){
		if (j==1)
			{
				r = confirm('Kế hoạch chi phí chi tiết của tháng '+ costMonth +' đã được lập.Bạn chắc chắn muốn cập nhật thông tin?');
				if (r == true) {
					$.getJSON("${pageContext.request.contextPath}/cost/costDetail/ajax/createPlan.htm", {departmentId: departmentId,costMonth: costMonth,costYear: costYear}, function(i){
						$("#costDetail").submit();
					});
				}
			}
		else
			{
				r = confirm('Bạn chắc chắn muốn lập kế hoạch chi phí chi tiết cho tháng '+costMonth+' ?');
				if (r == true) {
					$.getJSON("${pageContext.request.contextPath}/cost/costDetail/ajax/createPlan.htm", {departmentId: departmentId,costMonth: costMonth,costYear: costYear}, function(i){
						$("#costDetail").submit();
					});
				}
			}
		
	});
}
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

$("#departmentId").change(function(){
	 var departmentId = $(this).find("option:selected").text();
  	$("#deptName").val(departmentId);
	});

$(document).ready(function(){

	var departmentId = $("#departmentId").find("option:selected").text();
  	$("#deptName").text(departmentId.toUpperCase());
  	
	$('#item1 tbody tr:last td').css({color: 'red', fontWeight: 'bolder'});
 	$('table#item1 tr:last td:nth-child(2)').html('Tổng');

 	$('#item2 tbody tr:last td').css({color: 'red', fontWeight: 'bolder'});
 	$('table#item2 tr:last td:nth-child(2)').html('Tổng');

 	$('#item3 tbody tr:last td').css({color: 'red', fontWeight: 'bolder'});
 	$('table#item3 tr:last td:nth-child(2)').html('Tổng');
});


</script>

<script type="text/javascript">
$("#btLapKH").click(function(){
	var departmentId = $("#departmentId").val();
	var costMonth = $("#costMonth").val() ;
	var costYear = $("#costYear").val();
		/* window.open('${pageContext.request.contextPath}/cost/costDetail/createPlan.htm?departmentId=' + departmentId+
				'&costMonth=' +costMonth+
				'&costYear=' + costYear,
				'_blank','location=1,menubar=1,scrollbars=1,status=1,toolbar=1,resizable=1,fullscreen=1','yes|no|1|0',true); */
		window.location = '${pageContext.request.contextPath}/cost/costDetail/createPlan.htm?departmentId='+departmentId+'&costMonth='+costMonth+'&costYear='+costYear;
	
});
</script> 

<script type="text/javascript">

	$('#btChuyen').click(function(){
		var val = [];
		var checkedList = "";
		var departmentId = $("#departmentId").val();
		var costMonth = $("#costMonth").val();
		var costYear = $("#costYear").val();
			$('#item input:checked').each(function() {
	    		if($(this).val() != "Select All" && $(this).val() != "on"){
					checkedList += $(this).val() + "-";}
	    	});
	    
		$.getJSON("${pageContext.request.contextPath}/cost/costDetail/ajax/movePlan.htm", {departmentId: departmentId,costMonth: costMonth,costYear: costYear,checkedList : checkedList}, function(i){
			if (costMonth==12)
				{
				costMonth=1;
				costYear++;
				}
			else
				{
				costMonth++;
				}
			window.location = '${pageContext.request.contextPath}/cost/costDetail/list.htm?departmentId='+departmentId+'&costMonth='+costMonth+'&costYear='+costYear;
		});

		
	
	}); 

    	$("#selectAllCheck").click(function() {
    		 if($("#selectAllCheck").is(":checked")) // "this" refers to the element that fired the event
    	    {
    	       
    	    	$('#item input:checkbox:not(:checked)').each(function() {
    	    		$(this).attr('checked',true);
    	    	});
    	    }
    	    else
    	    {
    	    	$('#item input:checked').each(function() {
    	    		$(this).removeAttr('checked');
    	    	});
    	    }  

    		
    	
    	});
    	
    	

</script>

<script type="text/javascript">
$("#btReportKH").click(function(){
		window.open('${pageContext.request.contextPath}/cost/costDetail/reportKH.htm?departmentId=' + $("select#departmentId").val()+
				'&costYear=' + $("select#costYear").val()+
				'&costMonth=' + $("select#costMonth").val(),
				'_blank','width=300,height=200,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0','yes|no|1|0',true);
	
});

$("#btReportTH").click(function(){
	window.open('${pageContext.request.contextPath}/cost/costDetail/reportTH.htm?departmentId=' + $("select#departmentId").val()+
			'&costYear=' + $("select#costYear").val()+
			'&costMonth=' + $("select#costMonth").val(),
			'_blank','width=300,height=200,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0','yes|no|1|0',true);

});
</script> 
