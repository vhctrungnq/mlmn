<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <!-- Danh sach xuat va hoan tra tai san -->
 <style type="text/css"> 
    .wid95{
    	width:95%;
    } 
    .wid80{
    	width:80%; 
    }
    
	#doublescroll { overflow: auto; overflow-y: hidden; }   
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
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
<title>${title}</title>
<content tag="heading">${title}</content><div>
<form:form commandName="filter" method="post" action="list.htm"> 
	<table style = "width:100%">
		<tr>
			<td style="width:80px;"><fmt:message key="asExportWarehouse.productCode" /></td>
			<td >
				<form:input type="text" path ="productCode" class="wid90"/>
			</td>  
			
			<td style="width:70px;"><fmt:message key="asExportWarehouse.assetsName" /></td>
			<td colspan="3">
				<form:input type="text" path ="assetsName" class="wid95"/>
			</td> 
			
			<td style="width:60px;"><fmt:message key="asExportWarehouse.serialNo" /></td>
			<td >
				<form:input type="text"  path ="serialNo" class="wid90"/>
			</td>
		</tr>
		<tr>  
			<td ><fmt:message key="asExportWarehouse.organization" /></td>
			<td >
				<form:input type="text"  path ="organization" class="wid90"/>
			</td> 
			
			<td><fmt:message key="asExportWarehouse.departmentId" /></td>
			<td >
				<form:input type="text"  path ="departmentId" class="wid90"/>
			</td> 
			
			<td style="width:60px;"><fmt:message key="asExportWarehouse.sdate" /></td>
			<td>
				<input type ="text" id="sdate" name = "sdate" value="${sdate}" class="wid80"/> 
			</td>
			
			<td><fmt:message key="asExportWarehouse.edate" /></td>
			<td>
				<input type ="text" id="edate" name = "edate" value="${edate}" class="wid80"/> 
			</td>
		</tr>
		
		<tr>
			<td ><fmt:message key="asExportWarehouse.votesNo" /></td>
			<td >
				<form:input type="text"  path ="votesNo" class="wid90"/>
			</td> 
			
			<td ><fmt:message key="asExportWarehouse.usersEx" /></td>
			<td >
				<form:input type="text" path ="usersEx" class="wid90"/>
			</td>
			
			<td ><fmt:message key="asExportWarehouse.vendor" /></td>
			<td >
				<form:input type="text" path ="vendor" class="wid90"/>
			</td>
			
			<td colspan="2">
				<input class="button" type="submit" id="submit" value="<fmt:message key="button.search"/>"/>
				<input type="button" class="button" value="In phiếu xuất" id= "btReport">
			</td> 
			
			<td align="right" style = "width:150px;">
				<a href="form.htm"><fmt:message key="button.add"/></a>&nbsp;-
	            <a href="upload.htm"><fmt:message key="button.upload"/></a>&nbsp;  
	        </td>
		</tr>
	</table>
</form:form>
</div>

<div style="overflow:auto;">
	<display:table name="${asExportWarehouse}" class="simple2" id="asExportWarehouse" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px !important;"> <c:out value="${asExportWarehouse_rowNum}" /> </display:column>	
		<%-- <display:column property="idAssets" titleKey="asExportWarehouse.idAssets" sortable="true" sortName="ID_ASSETS"/> --%>
		<display:column property="productCode" titleKey="asExportWarehouse.productCode" sortable="true" sortName="PRODUCT_CODE"/>
		<display:column property="productName" titleKey="asExportWarehouse.assetsName" sortable="true" sortName="PRODUCT_NAME"/>
		<display:column property="serialNo" titleKey="asExportWarehouse.serialNo" sortable="true" sortName="SERIAL_NO"/>
		<display:column property="unit" titleKey="asExportWarehouse.unit" sortable="true" sortName="UNIT"/>  
		<display:column property="amountEx" titleKey="asExportWarehouse.amountEx" sortable="true" sortName="AMOUNT_EX"/> 
		<display:column property="exportDate" titleKey="asExportWarehouse.exportDate" format="{0,date,dd/MM/yyyy}" sortable="true" sortName="EXPORT_DATE"/> 
		<display:column property="votesNo" titleKey="asExportWarehouse.votesNo" sortable="true" sortName="VOTES_NO"/>
		<display:column property="usersEx" titleKey="asExportWarehouse.usersEx" sortable="true" sortName="USERS_EX"/> 
		<display:column property="amountReturn" titleKey="asExportWarehouse.amountReturn" sortable="true" sortName="AMOUNT_RETURN"/> 
		<display:column property="dateReturn" titleKey="asExportWarehouse.dateReturn" format="{0,date,dd/MM/yyyy}" sortable="true" sortName="DATE_RETURN"/> 
		<display:column property="vendor" titleKey="asExportWarehouse.vendor" sortable="true" sortName="VENDOR"/> 
		<display:column property="organization" titleKey="asExportWarehouse.organization" sortable="true" sortName="ORGANIZATION"/>
		<display:column property="departmentId" titleKey="asExportWarehouse.departmentId" sortable="true" sortName="DEPARTMENT_ID"/>   
		<display:column property="users" titleKey="asExportWarehouse.users" sortable="true" sortName="USERS"/>  
		<display:column property="description" titleKey="asExportWarehouse.description" sortable="true" sortName="DESCRIPTION"/>   
		
		<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="width:90px !important;">
				<a href="form.htm?id=${asExportWarehouse.id}"><fmt:message key="button.edit"/></a>&nbsp;
		 			<a href="delete.htm?id=${asExportWarehouse.id}"
						onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')">
				<fmt:message key="button.delete"/></a>&nbsp; 
		 	</display:column>
		 	
		<%-- <c:if test="${checkquanly==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="width:90px !important;">
				<a href="form.htm?id=${asExportWarehouse.id}"><fmt:message key="button.edit"/></a>&nbsp;
		 			<a href="delete.htm?id=${asExportWarehouse.id}"
						onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')">
				<fmt:message key="button.delete"/></a>&nbsp; 
		 	</display:column>
		</c:if> --%>
		
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript"> $(".chzn-select").chosen(); $(".chzn-select-deselect").chosen({allow_single_deselect:true}); </script>

<script type="text/javascript">
		$("#btReport").click(function(){
				var productCode = encodeURIComponent($("#productCode").val());
				var assetsName = encodeURIComponent($("#assetsName").val());
				var serialNo = encodeURIComponent($("#serialNo").val());
				var vendor = encodeURIComponent($("#vendor").val()); 
				var donViSd = encodeURIComponent($("#departmentId").val());
				var boPhanSd = encodeURIComponent($("#organization").val()); 
				var sophieu = encodeURIComponent($("#votesNo").val());
				var usersEx = encodeURIComponent($("#usersEx").val());
				var sdate = encodeURIComponent($("#sdate").val());
				var edate = encodeURIComponent($("#edate").val());
				
				window.open('${pageContext.request.contextPath}/assets/xuat-tai-san/inPhieuDocx.htm?productCode='+productCode+'&assetsName='+assetsName+
						'&serialNo='+serialNo+'&vendor='+vendor+'&boPhanSd='+boPhanSd+'&donViSd='+donViSd+'&sophieu='+sophieu+
						'&sdate='+sdate+'&edate='+edate+'&usersEx='+usersEx,'_blank','width=300,height=200,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0','yes|no|1|0',true);
		});
</script>
<script type="text/javascript">
	$(function() {
		$( "#sdate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		$( "#edate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
	});
</script>

<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}
</script>

 <script type="text/javascript">
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${productCodeList}" var="listOfNames">
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
