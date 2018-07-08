<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title><fmt:message key="sidebar.admin.asCards"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.asCards"/></content>

<style type="text/css">
    
	#doublescroll { overflow: auto; overflow-y: hidden; }   
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
    .EDATE{
		width:130px !important;
	}
	
     #dvtTeamProcess {
     visibility: visible !important;
    }
   
    #acLow {
     visibility: visible !important;
    }
    
     #bscid_chzn {

		width: 220px !important;
	}
    .ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	* we use height instead, but this forces the menu to always be this tall
	*/
	* html .ui-autocomplete {
		height: 200px;
	}
</style>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form:form commandName="filter" method="post" action="list.htm">
					<table border="0" cellspacing="1" cellpadding="0" width="100%">
						<tr>
							<td class="wid7 mwid80"><fmt:message key="asCards.asTypesName"/></td>
							<td class="wid15">
								<select id="asTypesId" name="asTypesId" class="wid90">
									<option value="">--Tất cả--</option>
					   				<c:forEach var="items" items="${asTypesIDList}">
						              	<c:choose>
						                <c:when test="${items.asTypesId == asTypesIDCBB}">
						                    <option value="${items.asTypesId}" selected="selected">${items.asTypesName}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.asTypesId}">${items.asTypesName}</option>
						                </c:otherwise>
						              	</c:choose>
							    	</c:forEach>
								</select>
							</td>
							<td class="wid7 mwid80"><fmt:message key="asCards.productName"/></td>
							<td class="wid15"><form:input path="productName" cssClass="wid90"/></td>
							 
							<td class="wid7 mwid80"><fmt:message key="asCards.productCode"/></td>
							<td class="wid15"><form:input path="productCode" cssClass="wid90"/></td>
							
							<td class="wid7 mwid80"><fmt:message key="asCards.serialNo"/></td>
							<td class="wid15"><form:input path="serialNo" cssClass="wid90"/></td>
									 
						</tr>
						<tr>
							<td><fmt:message key="asCards.importDate"/></td>
							<td>
								<input type="text" id="importDateFrom" name="importDateFrom" value="${importDateFrom}" class="wid50" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the start date" id="chooseImportDateFrom" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
          					</td>
          					<td><fmt:message key="asCards.denNgay"/></td>
          					<td>
          						<input type="text" id="importDateTo" name="importDateTo" value="${importDateTo}" class="wid50" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the end date" id="chooseImportDateTo" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							
							<td><fmt:message key="asCards.warehourse" /></td>
							<td>
								<form:input type="text" path="warehourse" class="wid90"/>
							</td>
							
							<td><fmt:message key="asCards.ject"/></td>
          					<td><form:input path="ject" cssClass="wid90"/></td>
          					
          					
						</tr>
						<tr>  
							<td><fmt:message key="asExportWarehouse.organization"/></td>
							<td><form:input type="text" path="organization" class="wid90"/></td> 
							
							<td><fmt:message key="asExportWarehouse.departmentId"/></td>
							<td><form:input type="text" path="departmentId" class="wid90"/></td> 
							
							<td><fmt:message key="asExportWarehouse.sdate"/></td>
							<td>
          						<input type="text" id="exportDateFrom" name="exportDateFrom" value="${exportDateFrom}" class="wid50" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the start date" id="chooseExportDateFrom" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							
							<td><fmt:message key="asExportWarehouse.edate"/></td>
							<td>
          						<input type="text" id="exportDateTo" name="exportDateTo" value="${exportDateTo}" class="wid50" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the end date" id="chooseExportDateTo" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
						</tr>
						<tr>
							<td><fmt:message key="asExportWarehouse.votesNo" /></td>
							<td>
								<form:input type="text" path="votesNo" class="wid90"/>
							</td> 
							
							<td><fmt:message key="asExportWarehouse.usersEx" /></td>
							<td>
								<form:input type="text" path="usersEx" class="wid90"/>
							</td>
							
							<td><fmt:message key="asCards.vendor"/></td>
          					<td><form:input path="vendor" cssClass="wid90"/></td>
							
							<td colspan="2"><input class="button" type="submit" class="button" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" />&nbsp;
								<input type="button" class="button" value="In phiếu xuất" id= "btReport">
							</td> 
						</tr>				
					</table>
				</form:form>
			</td>
		</tr>
		
		<tr>
			<td colspan="2" align="right">
				<a href="form.htm"><fmt:message key="global.form.themmoi"/></a>&nbsp;
				<a href="upload.htm"><fmt:message key="global.button.import"/></a>
			</td> 
		</tr>
			
		
</table>


 <div id="doublescroll" >
	<display:table name="${asCardsList}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true" >
		<display:column property="warehourse" titleKey="asCards.warehourse" sortable="true" sortName="WAREHOURSE"/>
		<display:column style="max-width:150px;word-wrap: break-word;" property="asTypesName" titleKey="asCards.asTypesName" sortable="true" sortName="AS_TYPES_NAME"/>
		<display:column class="centerColumnMana" property="stt" titleKey="global.list.STT" sortable="true" sortName="STT"/>
		<display:column property="productName" titleKey="asCards.productName" sortable="true" sortName="PRODUCT_NAME"/>
		<display:column property="productCode" titleKey="asCards.productCode" sortable="true" sortName="PRODUCT_CODE"/>
		<display:column property="serialNo" titleKey="asCards.serialNo" sortable="true" sortName="SERIAL_NO"/>
		<display:column class="rightColumnMana" property="amount" titleKey="asCards.amount" sortable="true" sortName="AMOUNT" />
		
		<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="importDate" titleKey="asCards.importDate" sortable="true" sortName="IMPORT_DATE"/>
		<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="exportDate" titleKey="asCards.exportDate" sortable="true" sortName="EXPORT_DATE"/>
		<display:column property="ject" titleKey="asCards.ject" sortable="true" sortName="JECT"/>
		<display:column property="departmentId" titleKey="asCards.departmentId" sortable="true" sortName="DEPARTMENT_ID"/>
		<display:column property="description" titleKey="asCards.description" sortable="true" sortName="DESCRIPTION"/>
		<display:column property="votesNo" titleKey="asCards.votesNo" sortable="true" sortName="VOTES_NO"/>
		<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="dateReturn" titleKey="asCards.dateReturn" sortable="true" sortName="DATE_RETURN"/>
		<display:column property="vendor" titleKey="asCards.vendor" sortable="true" sortName="VENDOR"/>
		<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="warrantyDate" titleKey="asCards.warrantyDate" sortable="true" sortName="WARRANTY_DATE"/>
		
		<display:column titleKey="global.management" style="width:100px;word-wrap: break-word;" media="html" class="centerColumnMana" >
			<a href="xuatKhoCards.htm?id=${item.id}"><fmt:message key="global.form.xuatKho"/></a>&nbsp;
			
			<a href="form.htm?id=${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
			<a href="delete.htm?id=${item.id}"
						onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="global.form.xoa"/></a>&nbsp;
				
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
		$("#btReport").click(function(){
				var asTypesId = encodeURIComponent($("#asTypesId").val());
				var productCode = encodeURIComponent($("#productCode").val());
				var productName = encodeURIComponent($("#productName").val());
				var serialNo = encodeURIComponent($("#serialNo").val());
				var vendor = encodeURIComponent($("#vendor").val());
				var boPhanSd = encodeURIComponent($("#organization").val());  
				var donViSd = encodeURIComponent($("#departmentId").val());
				var sophieu = encodeURIComponent($("#votesNo").val());
				var importDateFrom = encodeURIComponent($("#importDateFrom").val());
				var importDateTo = encodeURIComponent($("#importDateTo").val());
				var usersEx = encodeURIComponent($("#usersEx").val());
				var ject = encodeURIComponent($("#ject").val());
				var exportDateFrom = encodeURIComponent($("#exportDateFrom").val());
				var exportDateTo = encodeURIComponent($("#exportDateTo").val());
				var warehourse = encodeURIComponent($("#warehourse").val());
				
				window.open('${pageContext.request.contextPath}/assets/quan-ly-card/inPhieuDocx.htm?asTypesId='+asTypesId+'&productCode='+productCode+'&productName='+productName+
						'&serialNo='+serialNo+'&vendor='+vendor+'&boPhanSd='+boPhanSd+'&donViSd='+donViSd+'&sophieu='+sophieu+
						'&importDateFrom='+importDateFrom+'&importDateTo='+importDateTo+'&usersEx='+usersEx+
						'&ject='+ject+'&exportDateFrom='+exportDateFrom+'&exportDateTo='+exportDateTo+'&warehourse='+warehourse,'_blank','width=300,height=200,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0','yes|no|1|0',true);
		});
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
Calendar.setup({
    inputField		:	"importDateFrom",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseImportDateFrom",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"importDateTo",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseImportDateTo",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"exportDateFrom",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseExportDateFrom",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"exportDateTo",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseExportDateTo",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});


function focusIt()
{
  var mytext = document.getElementById("productName");
  mytext.focus();
}

onload = focusIt;
</script>

<script type="text/javascript">
$(function() {
	var messagesXuatKho = '<c:out value="${messagesXuatKho}"/>';
	if(messagesXuatKho == "Y")
		alert("Trong kho không còn thiết bị");
});

</script>

<script type="text/javascript">
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
</script>