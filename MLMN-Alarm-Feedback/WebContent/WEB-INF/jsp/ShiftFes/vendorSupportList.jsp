<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
  <style type="text/css">  
  	.time{
  		width:110px;
  	}
</style>
<title>${title}</title>
<content tag="heading">${title}</content> 
<div>
<form:form commandName="filter" method="post" action="list.htm"> 
	<table style="width:100%">
		<tr>
			<td align="left">
				<table border="0" cellspacing="1" cellpadding="0">
					<tr>
						
						<td class="wid6 mwid70"><fmt:message key="vendorSupport.filter.stime" /></td>
						<td class="wid15">
							<input type ="text"  value="${sTime}" id = "sTime" name = "sTime" class = "wid70"/>
				   			<img alt="calendar" title="Click chọn thời gian bắt đầu" id="choosestime" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>
						
						<td class="wid5 mwid70"><fmt:message key="vendorSupport.filter.etime" /></td>
						<td class="wid15">
							<input type ="text"  value="${eTime}" id = "eTime" name = "eTime" class = "wid70"/>
				   			<img alt="calendar" title="Click chọn thời gian kết thúc" id="chooseetime" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>
						<td  class="wid5 mwid70"><fmt:message key="qLPhongBan.region"/></td>
						<td class="wid8">
					        <select id="regionTk" name = "regionTk">
					        		<option value=""></option>
				   					<c:forEach var="items" items="${regionList}">
						              <c:choose>
						                <c:when test="${items.name == regionTk}">
						                    <option value="${items.name}" selected="selected">${items.value}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.name}">${items.value}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
				           		</select>	
					        
				        </td>
						<td class="wid8 mwid70"><fmt:message key="vendorSupport.supportType" /></td>
						<td class="wid15">
							<form:input type="text" value="${supportType}" path ="supportType" class="wid90"/>
						</td>	
						<td class="wid8 mwid70"><fmt:message key="vendorSupport.system" /></td>
						<td class="wid15">
							<form:input type="text" value="${system}" path ="system" class="wid90"/>
						</td>
						
					</tr>
					<tr>	
						<td><fmt:message key="vendorSupport.fullName" /></td>
						<td>
							<form:input type="text" value="${fullName}" path ="fullName" class="wid90"/>
						</td> 
							
						<td><fmt:message key="vendorSupport.idNumber" /></td>
						<td>
							<form:input type="text" value="${idNumber}" path ="idNumber" class="wid90"/>
						</td>
						<td ><fmt:message key="vendorSupport.vendor" /></td>
						<td >
							 <form:input type="text" value="${vendor}" path ="vendor" class="wid90"/> 
							<%--<div id='vendor'></div>--%>
						</td> 
						
						
						<td><fmt:message key="vendorSupport.phone" /></td>
						<td>
							<form:input type="text" value="${phone}" path ="phone" class="wid90"/>
						</td>
						
						<td>
							<input class="button" type="submit" id="submit" value="<fmt:message key="button.search"/>"/>
						</td>   
					</tr>
				</table>
			</td>
			<td></td>
		</tr>	
		<c:if test="${checkRegion==true}">
			<tr>
				<td></td>
				<td class="wid10 mwid150" align="right">
						<a href="form.htm"><fmt:message key="global.form.themmoi"/></a>&nbsp;
						<a href="upload.htm"><fmt:message key="global.button.import"/></a>&nbsp;
				</td> 
			</tr>
		</c:if>
	</table>
</form:form>
</div>

<div style="overflow: auto;"  class="tableStandar">
	<display:table name="${shiftVendorList}" id="alShiftVendor" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px !important;"> <c:out value="${alShiftVendor_rowNum}" /> </display:column>
	  	<display:column property="stime" titleKey="vendorSupport.stime" format="{0,date,dd/MM/yyyy HH:mm}"/>
	  	<display:column property="etime" titleKey="vendorSupport.etime" format="{0,date,dd/MM/yyyy HH:mm}"/> 
	  	<display:column property="vendor" titleKey="vendorSupport.vendor"/>
		<display:column property="system" titleKey="vendorSupport.system"/>
	  	<display:column property="fullname" titleKey="vendorSupport.fullName"/>
	  	<display:column property="phone" titleKey="vendorSupport.phone"/>
		<display:column property="email" titleKey="vendorSupport.email"/>
		<display:column property="idNumber" titleKey="vendorSupport.idNumber"/>
		<display:column property="jobTitle" titleKey="vendorSupport.jobTitle"/>
		<display:column property="region" titleKey="vendorSupport.region"/>
	  	<display:column property="supportType" titleKey="vendorSupport.supportType"/>
	  	<display:column property="device" titleKey="vendorSupport.device"/>
	  	<display:column property="deviceNumber" titleKey="vendorSupport.deviceNumber"/>
	  	<display:column property="leader" titleKey="vendorSupport.leader"/>
	  	<display:column property="description" titleKey="vendorSupport.description"/>
		<c:if test="${checkRegion==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="width:90px !important;">
				<a href="form.htm?id=${alShiftVendor.id}"><fmt:message key="button.edit"/></a>&nbsp;
		 			<a href="delete.htm?id=${alShiftVendor.id}"
						onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')">
				<fmt:message key="button.delete"/></a>&nbsp; 
		 	</display:column>
		</c:if>	
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
<script type="text/javascript">
	
    Calendar.setup({
        inputField		:	"sTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosestime",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"eTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseetime",   	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    }); 
</script>

<script type="text/javascript">
	$(document).ready(function(){
		var trs='<tr>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="global.list.STT"/></th>';
	    trs=trs +'<th colspan="2" class=""><fmt:message key="shiftFes.group.time"/></th>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="vendorSupport.vendor"/></th>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="vendorSupport.system"/></th>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="vendorSupport.fullName"/></th>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="vendorSupport.phone"/></th>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="vendorSupport.email"/></th>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="vendorSupport.idNumber"/></th>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="vendorSupport.jobTitle"/></th>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="vendorSupport.region"/></th>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="vendorSupport.supportType"/></th>'; 
	    trs=trs +'<th colspan="2" class=""><fmt:message key="vendorSupport.laptop"/></th>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="vendorSupport.leader"/></th>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="vendorSupport.description"/></th>'; 
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="title.quanLy"/></th></tr>'; 
	    
	   	trs=trs +'<tr><th class="time"><fmt:message key="vendorSupport.stime"/></th>';
	    trs=trs +'<th class="time"><fmt:message key="vendorSupport.etime"/></th>';
	    trs=trs +'<th class=""><fmt:message key="vendorSupport.device"/></th>'; 
	    trs=trs +'<th class=""><fmt:message key="vendorSupport.deviceNumber"/></th></tr>'; 
	    
	$('#alShiftVendor thead').html(trs);
	});
</script>
<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}

function focusIt()
{
  var mytext = document.getElementById("sTime");
  mytext.focus();
}

onload = focusIt;
/* $(document).ready(function(){
	var theme =  getTheme();	
	// Create a jqxComboBox vendor
		var urlVendor = "${pageContext.request.contextPath}/ajax/getVendor.htm";
	// prepare the data
	var sourceVendor =
	{
	    datatype: "json",
	    datafields: [
	        { name: 'value' },
	        { name: 'name' }
	    ],
	    url: urlVendor,
	    async: false
	};
	var dataAdapterVendor = new $.jqx.dataAdapter(sourceVendor);
	// Create a jqxComboBox
	$("#vendor").jqxComboBox({ source: dataAdapterVendor, displayMember: "value", valueMember: "name", width: 120,height: '20px',itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
	var vendor =  "<c:out value='${vendor}'/>";
	if(vendor=="")
		$('#vendor').val('');
	else
		$('#vendor').val(vendor);
}); */
</script>