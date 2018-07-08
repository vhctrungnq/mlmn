<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">
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
<title><fmt:message key="sidebar.admin.assetsInventorying"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.assetsInventorying"/></content>
<div class="ui-tabs-panel">
<table style = "border:0; width:100%; cellspacing:0; cellpadding:0;" class="form">
		<tr> 
			<td align="left" colspan="2">
			<form:form commandName="filter" method="post" action="list.htm">
					<table style="border:0; cellspacing:1; cellpadding:0; width:100%;">
						<tr>
							<td class="wid7 mwid80"><fmt:message key="assetsInventorying.dotKiemKe"/></td>
							<td class="wid15"><form:input path="dotKiemKe" cssClass="wid90"/></td> 
							 
							<td class="wid7 mwid100"><fmt:message key="assetsInventorying.productCode"/></td>
							<td class="wid15"><form:input path="productCode" cssClass="wid90"/></td>
							
							<td class="wid7 mwid100"><fmt:message key="assetsInventorying.productName"/></td>
							<td class="wid15"><form:input path="productName" cssClass="wid90"/></td>
							
							<td class="wid7 mwid60"><fmt:message key="assetsInventorying.serialNo"/></td>
	         				<td class="wid15"><form:input path="serialNo" cssClass="wid90"/></td>
						</tr>
						<tr>
							<td><fmt:message key="assetsInventorying.departmentUse"/></td>
							<td>
								<form:input path="departmentUse" cssClass="wid90"/>
							</td>
							<td><fmt:message key="assetsInventorying.departmentId"/></td>
							<td>
								<form:input path="departmentId" cssClass="wid90"/>
	         				</td>
	         				<td><fmt:message key="assetsInventorying.users"/></td>
							<td>
								<form:input path="users" cssClass="wid90"/>
	         				</td>
	         				<td><fmt:message key="assetsInventorying.lech"/></td>
							<td>
								<select id="lech" name="lech" class="wid90">
									<option value="">--Tất cả--</option>
					   				<c:forEach var="items" items="${lechList}">
						              	<c:choose>
						                <c:when test="${items.value == lech}">
						                    <option value="${items.value}" selected="selected">${items.name}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.value}">${items.name}</option>
						                </c:otherwise>
						              	</c:choose>
							    	</c:forEach>
								</select>
							</td>
							<td><input class="button" type="submit" class="button" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>				
					</table>
				</form:form>
			</td>
		</tr>
		<tr>
			<td></td>
			<td class="wid10 mwid100" align="right">
				<a href="upload.htm"><fmt:message key="button.upload"/></a>&nbsp;-
				<a href="kiem-ke.htm?insert=1"><fmt:message key="title.tabControls.kiemKe"/></a>&nbsp;
				<%-- <a onClick="myFunction()"><fmt:message key="title.tabControls.kiemKe"/></a>&nbsp;- --%>
				<%-- <a href="dieu-chinh.htm"><fmt:message key="title.tabControls.dieuChinh"/></a>&nbsp; --%>
			</td> 
		</tr>
		<tr>
			<td colspan="2">
				<div style="width:100%;overflow:auto; ">
					<display:table name="${assetsInventoryingList}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true" >
						<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="dotKiemKe" titleKey="assetsInventorying.dotKiemKe" sortable="true" sortName="DOT_KIEM_KE"/>
						<display:column property="importDate" titleKey="assetsInventorying.ngayKiemKe" format = "{0,date,dd/MM/yyyy}" sortable="true" sortName="IMPORT_DATE"/>
						<display:column property="productCode" titleKey="assetsInventorying.productCode" sortable="true" sortName="PRODUCT_CODE"/>
						<display:column property="productName" titleKey="assetsInventorying.productName" sortable="true" sortName="PRODUCT_NAME"/>
						<display:column property="serialNo" titleKey="assetsInventorying.serialNo" sortable="true" sortName="SERIAL_NO"/>
						<display:column class="centerColumnMana" property="slDangSd" titleKey="assetsInventorying.dangSD" />
						<display:column class="centerColumnMana" property="slKhongSd" titleKey="assetsInventorying.khongSD" />
						<display:column class="centerColumnMana" property="slBaoHanh" titleKey="assetsInventorying.baoHanh" />
						<display:column class="centerColumnMana" property="slSoSach" titleKey="assetsInventorying.ss" />
						<display:column class="centerColumnMana" property="amount" titleKey="assetsInventorying.amount" />
						<display:column class="centerColumnMana" property="lech" titleKey="assetsInventorying.lech" />
						<display:column property="departmentUse" titleKey="assetsInventorying.departmentUse" sortable="true" sortName="DEPARTMENT_USE"/>
						<display:column property="departmentId" titleKey="assetsInventorying.departmentId" sortable="true" sortName="DEPARTMENT_ID"/>
						<display:column property="users" titleKey="assetsInventorying.users" sortable="true" sortName="USERS"/>
						<display:column class="centerColumnMana" property="createdBy" titleKey="assetsInventorying.createdBy" sortable="true" sortName="CREATED_BY"/>
						<display:column property="description" titleKey="assetsInventorying.description" sortable="true" sortName="DESCRIPTION"/>
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
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript"> $(".chzn-select").chosen(); $(".chzn-select-deselect").chosen({allow_single_deselect:true}); </script>

<!-- <script type="text/javascript">
function myFunction()
{
	var dotkiemke=window.prompt("Nhập tháng kiểm kê ");
	if(dotkiemke == ''){
		myFunction();
	}else{
		alert(dotkiemke);
		window.open('${pageContext.request.contextPath}/assets/kiem-ke-tai-san/kiem-ke.htm?dotkiemke='+ dotkiemke, '_self');
	}
}

</script>   -->
<script type="text/javascript">

function focusIt()
{
  var mytext = document.getElementById("productCode");
  mytext.focus();
}

onload = focusIt;
</script>    

<script type="text/javascript">
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${productCodeList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadBsc1(availableTags);
	}); 
	
	function loadBsc1(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$( "#productCode" )
		// don't navigate away from the field on tab when selecting an item
		.bind( "keydown", function( event ) {
		if ( event.keyCode === $.ui.keyCode.TAB &&
		$( this ).data( "autocomplete" ).menu.active ) {
		event.preventDefault();
		}
		})
		.autocomplete({
		minLength: 0,
		source: function( request, response ) {
		// delegate back to autocomplete, but extract the last term
		response( $.ui.autocomplete.filter(
		availableTags, extractLast( request.term ) ) );
		},
		focus: function() {
		// prevent value inserted on focus
		return false;
		},
		select: function( event, ui ) {
		var terms = split( this.value );
		// remove the current input
		terms.pop();
		//check and add the selected item
		var temp = ui.item.value;
		var bscTp = $("#productCode").val();
		var bscL = bscTp.split(",");
		var kt = false;
		for (var i=0; i<bscL.length; i++) {
			if (temp==bscL[i])
				kt=true;
		}
		if (kt==false)
		{
			terms.push( ui.item.value );
			// add placeholder to get the comma-and-space at the end
			terms.push( "" );
			this.value = terms.join( "," );
		}
		return false;
		}
		});
	}	
</script>

<script type="text/javascript">
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${dotKiemKeList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadBsc(availableTags);
	}); 
	
	function loadBsc(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$( "#dotKiemKe" )
		// don't navigate away from the field on tab when selecting an item
		.bind( "keydown", function( event ) {
		if ( event.keyCode === $.ui.keyCode.TAB &&
		$( this ).data( "autocomplete" ).menu.active ) {
		event.preventDefault();
		}
		})
		.autocomplete({
		minLength: 0,
		source: function( request, response ) {
		// delegate back to autocomplete, but extract the last term
		response( $.ui.autocomplete.filter(
		availableTags, extractLast( request.term ) ) );
		},
		focus: function() {
		// prevent value inserted on focus
		return false;
		},
		select: function( event, ui ) {
		var terms = split( this.value );
		// remove the current input
		terms.pop();
		//check and add the selected item
		var temp = ui.item.value;
		var bscTp = $("#dotKiemKe").val();
		var bscL = bscTp.split(",");
		var kt = false;
		for (var i=0; i<bscL.length; i++) {
			if (temp==bscL[i])
				kt=true;
		}
		if (kt==false)
		{
			terms.push( ui.item.value );
			// add placeholder to get the comma-and-space at the end
			terms.push( "" );
			this.value = terms.join( "," );
		}
		return false;
		}
		});
	}	
</script>