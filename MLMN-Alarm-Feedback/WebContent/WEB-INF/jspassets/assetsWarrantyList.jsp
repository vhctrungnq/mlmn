<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title><fmt:message key="sidebar.admin.assetsWarranty"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.assetsWarranty"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form:form commandName="filter" method="post" action="list.htm">
					<table border="0" cellspacing="1" cellpadding="0" width="100%">
						<tr>
							<td class="wid7 mwid80"><fmt:message key="assetsWarranty.asTypesID"/></td>
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
							 
							<td class="wid7 mwid80"><fmt:message key="assetsWarranty.productCode"/></td>
							<td class="wid15"><form:input path="productCode" cssClass="wid90"/></td>
							
							<td class="wid7 mwid80">
								<fmt:message key="assetsWarranty.csr"/>
							</td>
							<td class="wid15">
								<form:input path="csr" cssClass="wid90"/>
							</td>
							
							<td class="wid7 mwid80"><fmt:message key="assetsWarranty.serialNo"/></td>
							<td class="wid15"><form:input path="serialNo" cssClass="wid90"/></td>
						</tr>
						<tr> 
							<td><fmt:message key="assetsWarranty.sentDate"/></td>
							<td>
								<input type="text" id="sentDateFrom" name="sentDateFrom" value="${sentDateFrom}" class="wid50" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the start date" id="chooseSentDateFrom" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							<td><fmt:message key="assetsWarranty.denNgay"/></td>
							<td>
								<input type="text" id="sentDateTo" name="sentDateTo" value="${sentDateTo}" class="wid50" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the end date" id="chooseSentDateTo" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
          					</td>
          					<td>
								<fmt:message key="assetsWarranty.vendor"/>
							</td>
							<td>
								<form:input path="vendor" cssClass="wid90"/>
							</td>
          					<td><fmt:message key="assetsWarranty.deliveryNo"/></td>
          					<td><form:input path="deliveryNo" cssClass="wid90"/></td>
							<td><input class="button" type="submit" class="button" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>				
					</table>
				</form:form>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<a href="form.htm"><fmt:message key="global.form.themmoi"/></a>&nbsp;
				<a href="upload.htm"><fmt:message key="global.button.import"/></a>&nbsp;
			</td> 
		</tr>
		<tr>
			<td colspan="2">
				<div style="width:100%;overflow:auto; ">
					<display:table name="${assetsWarrantyList}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true" >
						<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column style="max-width:45px;word-wrap: break-word;" property="csr" titleKey="assetsWarranty.csr" sortable="true" sortName="CSR"/>
						<display:column style="max-width:60px;word-wrap: break-word;" property="function" titleKey="assetsWarranty.function" sortable="true" sortName="FUNCTION"/>
						<display:column property="asTypesName" titleKey="assetsWarranty.asTypesID" sortable="true" sortName="AS_TYPES_NAME"/>
						<display:column style="max-width:80px;word-wrap: break-word;" property="productCode" titleKey="assetsWarranty.productCode" sortable="true" sortName="PRODUCT_CODE"/>
						<display:column style="max-width:35px;word-wrap: break-word;" property="vendor" titleKey="assetsWarranty.vendor" sortable="true" sortName="VENDOR"/>
						<display:column style="max-width:75px;word-wrap: break-word;" property="serialNo" titleKey="assetsWarranty.serialNo" sortable="true" sortName="SERIAL_NO"/>
						<display:column style="max-width:50px;word-wrap: break-word;" property="serialNoScan" titleKey="assetsWarranty.serialNoScan" sortable="true" sortName="SERIAL_NO_SCAN"/>
						<display:column property="sentDate" format="{0,date,dd/MM/yyyy}" titleKey="assetsWarranty.sentDate" sortable="true" sortName="SENT_DATE"/>
						<display:column property="sameUnit" titleKey="assetsWarranty.sameUnit" sortable="true" sortName="SAME_UNIT"/>
						<display:column style="max-width:140px;word-wrap: break-word;" property="nameJect" titleKey="assetsWarranty.ject" sortable="true" sortName="NAME_JECT"/>
						<display:column style="max-width:50px;word-wrap: break-word;" property="serialNoRep" titleKey="assetsWarranty.serialNoRep" sortable="true" sortName="SERIAL_NO_REP"/>
						<display:column property="receivedDate" format="{0,date,dd/MM/yyyy}" titleKey="assetsWarranty.receivedDate" sortable="true" sortName="RECEIVED_DATE"/>
						<display:column style="max-width:50px;word-wrap: break-word;" class="centerColumnMana" property="deliveryNo" titleKey="assetsWarranty.deliveryNo" sortable="true" sortName="DELIVERY_NO"/>
						<display:column style="max-width:20px;word-wrap: break-word;" class="centerColumnMana" property="qty" titleKey="assetsWarranty.qty" sortable="true" sortName="QTY"/>
						<display:column style="max-width:35px;word-wrap: break-word;" class="centerColumnMana" property="departmentId" titleKey="assetsWarranty.departmentId" sortable="true" sortName="DEPARTMENT_ID"/>
						<display:column property="returnDate" format="{0,date,dd/MM/yyyy}" titleKey="assetsWarranty.returnDate" sortable="true" sortName="RETURN_DATE"/>
						<display:column style="max-width:140px;word-wrap: break-word;" class="centerColumnMana" property="description" titleKey="assetsWarranty.description" sortable="true" sortName="DESCRIPTION"/>
						<display:column titleKey="global.management" media="html" class="centerColumnMana">
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
			</td>
		</tr>
</table>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script type="text/javascript">
Calendar.setup({
    inputField		:	"sentDateFrom",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseSentDateFrom",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"sentDateTo",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseSentDateTo",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
  var mytext = document.getElementById("productCode");
  mytext.focus();
}

onload = focusIt;
</script>
<script type="text/javascript">
$(document).ready(function(){
	var trs='<tr>';
	trs=trs + '<th rowspan="2"><fmt:message key="global.list.No"/></th>';
	trs=trs + '<th rowspan="2"><fmt:message key="assetsWarranty.csr"/></th>';
	trs=trs + '<th rowspan="2"><fmt:message key="assetsWarranty.function"/></th>';
	trs=trs + '<th rowspan="2"><fmt:message key="assetsWarranty.asTypesID"/></th>';
	trs=trs + '<th rowspan="2"><fmt:message key="assetsWarranty.productCode"/></th>';
	trs=trs + '<th rowspan="2"><fmt:message key="assetsWarranty.vendor"/></th>';
	trs=trs +'<th colspan="5">CARD SENT</th>';
	trs=trs +'<th colspan="4">CARD RECEIVED</th>';
	trs=trs +'<th colspan="3">DEPARTMENT</th>';
	trs=trs + '<th rowspan="2"><fmt:message key="global.management"/></th>';
	trs=trs +'</tr>';
	
	trs=trs +'<tr><th><fmt:message key="assetsWarranty.serialNo"/></th>';
    trs=trs +'<th ><fmt:message key="assetsWarranty.serialNoScan"/></th>';
    trs=trs +'<th ><fmt:message key="assetsWarranty.sentDate"/></th>';
    trs=trs +'<th ><fmt:message key="assetsWarranty.sameUnit"/></th>';
    trs=trs +'<th ><fmt:message key="assetsWarranty.ject"/></th>';
    trs=trs +'<th ><fmt:message key="assetsWarranty.serialNoRep"/></th>';
    trs=trs +'<th ><fmt:message key="assetsWarranty.receivedDate"/></th>';
    trs=trs +'<th ><fmt:message key="assetsWarranty.deliveryNo"/></th>';
    trs=trs +'<th ><fmt:message key="assetsWarranty.qty"/></th>';
    trs=trs +'<th ><fmt:message key="assetsWarranty.departmentId"/></th>';
    trs=trs +'<th ><fmt:message key="assetsWarranty.returnDate"/></th>';
    trs=trs +'<th ><fmt:message key="assetsWarranty.description"/></th></tr>';
	$('#item thead').html(trs);
});
</script>
