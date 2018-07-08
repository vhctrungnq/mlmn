<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title><fmt:message key="sidebar.admin.asImportWarehouse"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.asImportWarehouse"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form:form commandName="filter" method="post" action="list.htm">
					<table border="0" cellspacing="1" cellpadding="0" width="100%">
						<tr>
							<td class="wid7 mwid80"><fmt:message key="asImportWarehouse.asTypesName"/></td>
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
							<td class="wid7 mwid80"><fmt:message key="asImportWarehouse.productName"/></td>
							<td class="wid15"><form:input path="productName" cssClass="wid90"/></td>
							 
							<td class="wid7 mwid80"><fmt:message key="asImportWarehouse.productCode"/></td>
							<td class="wid15"><form:input path="productCode" cssClass="wid90"/></td>
							
							<td class="wid7 mwid80"><fmt:message key="asImportWarehouse.serialNo"/></td>
							<td class="wid15"><form:input path="serialNo" cssClass="wid90"/></td>
									 
						</tr>
						<tr>
							<td><fmt:message key="asImportWarehouse.importDate"/></td>
							<td>
								<input type="text" id="importDateFrom" name="importDateFrom" value="${importDateFrom}" class="wid50" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the start date" id="chooseImportDateFrom" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
          					</td>
          					<td><fmt:message key="asImportWarehouse.denNgay"/></td>
          					<td>
          						<input type="text" id="importDateTo" name="importDateTo" value="${importDateTo}" class="wid50" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the end date" id="chooseImportDateTo" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							
							<td><fmt:message key="asImportWarehouse.ject"/></td>
          					<td><form:input path="ject" cssClass="wid90"/></td>
          					
          					<td><fmt:message key="asImportWarehouse.vendor"/></td>
          					<td><form:input path="vendor" cssClass="wid90"/></td>
          					
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
				<a href="upload.htm"><fmt:message key="global.button.import"/></a>
			</td> 
		</tr>
			
		<tr>
			<td colspan="2">
				<div style="width:100%;overflow:auto; ">
					<display:table name="${asImportWarehouseList}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true" >
						<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column style="max-width:150px;word-wrap: break-word;" property="asTypesName" titleKey="asImportWarehouse.asTypesName" sortable="true" sortName="AS_TYPES_NAME"/>
						<display:column property="productName" titleKey="asImportWarehouse.productName" sortable="true" sortName="PRODUCT_NAME"/>
						<display:column property="productCode" titleKey="asImportWarehouse.productCode" sortable="true" sortName="PRODUCT_CODE"/>
						<display:column property="serialNo" titleKey="asImportWarehouse.serialNo" sortable="true" sortName="SERIAL_NO"/>
						<display:column class="centerColumnMana" property="amount" titleKey="asImportWarehouse.amount" sortable="true" sortName="AMOUNT" />
						<display:column class="centerColumnMana" property="dangSd" titleKey="asImportWarehouse.dangSd" sortable="true" sortName="DANG_SD" />
						<display:column class="centerColumnMana" property="chuaSd" titleKey="asImportWarehouse.chuaSd" sortable="true" sortName="CHUA_SD" />
						
						<display:column class="centerColumnMana" property="unit" titleKey="asImportWarehouse.unit" sortable="true" sortName="UNIT"/>
						<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="importDate" titleKey="asImportWarehouse.importDate" sortable="true" sortName="IMPORT_DATE"/>
						<display:column class="centerColumnMana" format="{0,date,dd/MM/yyyy}" property="produceDate" titleKey="asImportWarehouse.produceDate" sortable="true" sortName="PRODUCE_DATE"/>
						<display:column property="vendor" titleKey="asImportWarehouse.vendor" sortable="true" sortName="VENDOR"/>
						<display:column property="ject" titleKey="asImportWarehouse.ject" sortable="true" sortName="JECT"/>
						<display:column property="description" titleKey="asImportWarehouse.description" sortable="true" sortName="DESCRIPTION"/>
						<display:column titleKey="global.management" style="width:100px;word-wrap: break-word;" media="html" class="centerColumnMana" >
							<a href="xuatKho.htm?id=${item.id}"><fmt:message key="global.form.xuatKho"/></a>&nbsp;
							
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
	
	if(messagesXuatKho == 'Y')
		alert("Trong kho không còn thiết bị");
});
</script>

