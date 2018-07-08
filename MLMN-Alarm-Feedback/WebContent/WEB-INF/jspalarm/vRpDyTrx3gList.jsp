<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="title.vRpDyTrx3g.formList"/></title>
<content tag="heading"><fmt:message key="title.vRpDyTrx3g.formList"/></content>

<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'tong-hop'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/tong-hop.htm"><span><fmt:message key="title.tabControls.tongHop"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/chi-tiet.htm"><span><fmt:message key="title.tabControls.chiTiet"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/lich-su-tang.htm"><span><fmt:message key="title.tabControls.lichSuTang"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/lich-su-giam.htm"><span><fmt:message key="title.tabControls.lichSuGiam"/></span></a></li>
	</c:when>
 	<c:when test="${function == 'chi-tiet'}">
		<li class=""><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/tong-hop.htm"><span><fmt:message key="title.tabControls.tongHop"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/chi-tiet.htm"><span><fmt:message key="title.tabControls.chiTiet"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/lich-su-tang.htm"><span><fmt:message key="title.tabControls.lichSuTang"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/lich-su-giam.htm"><span><fmt:message key="title.tabControls.lichSuGiam"/></span></a></li>
	</c:when>
	<c:when test="${function == 'lich-su-tang'}">
		<li class=""><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/tong-hop.htm"><span><fmt:message key="title.tabControls.tongHop"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/chi-tiet.htm"><span><fmt:message key="title.tabControls.chiTiet"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/lich-su-tang.htm"><span><fmt:message key="title.tabControls.lichSuTang"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/lich-su-giam.htm"><span><fmt:message key="title.tabControls.lichSuGiam"/></span></a></li>
	</c:when>
	<c:when test="${function == 'lich-su-giam'}">
		<li class=""><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/tong-hop.htm"><span><fmt:message key="title.tabControls.tongHop"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/chi-tiet.htm"><span><fmt:message key="title.tabControls.chiTiet"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/lich-su-tang.htm"><span><fmt:message key="title.tabControls.lichSuTang"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/lich-su-giam.htm"><span><fmt:message key="title.tabControls.lichSuGiam"/></span></a></li>
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<br>

<form:form id="filterController" method="post" action="${function}.htm">
	<div>
		<input id="strWhere" name="strWhere" value="" type="hidden"/>
		<input id="sortfield" name="sortfield" value="" type="hidden"/>
		<input id="sortorder" name="sortorder" value="" type="hidden"/>
	</div>
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr>
			<td>
					<%-- <fmt:message key="label.fromDate"/>&nbsp;
					<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 8%" maxlength="20"/>
   					<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
					<fmt:message key="label.toDate"/>&nbsp;
					<input type="text" id="endDate" name="endDate" value="${endDate}" style="width: 8%" maxlength="20"/>
   					<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
   					<fmt:message key="vRpDyTrx.vendor"/>&nbsp;
   					<select name="vendor" class="wid8" id="vendor">
           				<option value="">--Tất cả--</option>
           				<c:forEach var="items" items="${vendorList}">
			              <c:choose>
			                <c:when test="${items.value == vendorCBB}">
			                    <option value="${items.value}" selected="selected">${items.value}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.value}">${items.value}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
           			</select>&nbsp;
           			<fmt:message key="vRpDyTrx3g.rncid"/>
           			<input type="text" id="rncid" name="rncid" value="${rncid}" />
				<c:choose>
				<c:when test="${function == 'chi-tiet'}">
					<fmt:message key="vRpDyTrx.siteCell"/>&nbsp;
					<input type="text" id="siteCell" name="siteCell" value="${siteCell}" />&nbsp;	 
				</c:when>
				<c:otherwise></c:otherwise>
				</c:choose> --%>
				<c:choose>
				<c:when test="${function != 'chi-tiet'}">
					<fmt:message key="label.fromDate"/>&nbsp;
					<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 6%" maxlength="20"/>
   					<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
					<fmt:message key="label.toDate"/>&nbsp;
					<input type="text" id="endDate" name="endDate" value="${endDate}" style="width: 6%" maxlength="20"/>
   					<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
				</c:when>
				<c:otherwise></c:otherwise>
				</c:choose>
				<fmt:message key="vRpDyTrx.vendor"/>&nbsp;
				<select name="vendor" class="wid8" id="vendor">
       				<option value="">--Tất cả--</option>
   					<c:forEach var="items" items="${vendorList}">
		              <c:choose>
		                <c:when test="${items.value == vendorCBB}">
		                    <option value="${items.value}" selected="selected">${items.value}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.value}</option>
		                </c:otherwise>
		              </c:choose>
			    </c:forEach>
       			</select>&nbsp;
       			<fmt:message key="vRpDyTrx3g.rncid"/>
           			<input type="text" id="rncid" name="rncid" value="${rncid}" />&nbsp;
				<c:choose>
				<c:when test="${function != 'tong-hop'}">
					<fmt:message key="vRpDyTrx.siteCell"/>&nbsp;
					<input type="text" id="siteCell" name="siteCell" value="${siteCell}" />&nbsp;	 
					<fmt:message key="vRpDyTrx.status"/>&nbsp;
   					<select name="status" class="wid6" id="status">
           				<option value="">--Tất cả--</option>
           				<c:forEach var="items" items="${statusList}">
			              <c:choose>
			                <c:when test="${items.value == status}">
			                    <option value="${items.value}" selected="selected">${items.value}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.value}">${items.value}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
           			</select>&nbsp;
				</c:when>
				</c:choose>
				
				<c:choose>
				<c:when test="${function == 'lich-su-tang' || function == 'lich-su-giam'}">
					<fmt:message key="vRpDyTrx.type"/>&nbsp;
   					<select name="type" class="wid6" id="type">
           				<option value="">--Tất cả--</option>
           				<c:forEach var="items" items="${typeList}">
			              <c:choose>
			                <c:when test="${items.value == type}">
			                    <option value="${items.value}" selected="selected">${items.value}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.value}">${items.value}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
           			</select>&nbsp;
				</c:when>
				<c:otherwise>
				</c:otherwise>
				</c:choose>
				<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />
			</td>
		</tr>
		<tr>
			<td>
				<div id='jqxWidget' style="margin-top:5px">
			    	<div style="float: right;margin-bottom:3px;width:180px;">
			        	<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr>
								<td><div style="float: right;" id="jqxlistbox"></div></td>
							</tr>
						</table>
			        </div><br>
			        <div id="jqxgrid"></div>
			        <div id='Menu'>
			            <ul>
					   		<li><fmt:message key="global.button.exportExcel"/></li>
				        </ul>
			       </div>
			    </div>
			</td>
		</tr>
	</table>
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
var functionTrx = '<c:out value="${function}"/>';
if(functionTrx != 'chi-tiet'){
	Calendar.setup({
	    inputField		:	"startDate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
	
	Calendar.setup({
	    inputField		:	"endDate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
}	
function focusIt()
{
  var mytext = document.getElementById("startDate");
  mytext.focus();
}
onload = focusIt;

</script>
<script type="text/javascript">
var theme = getTheme();

${gridReport}

//Create a jqxMultile Input
var renderer = function (itemValue, inputValue) {
    var terms = inputValue.split(/,\s*/);
    // remove the current input
    terms.pop();
    // add the selected item
    terms.push(itemValue);
    // add placeholder to get the comma-and-space at the end
    terms.push("");
    var value = terms.join(",");
    return value;
};

//Input bscid
${rncidList}
$("#rncid").jqxInput({ placeHolder: "", height: 20, width: '8%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#rncid").jqxInput({ query: item });
        response(rncidList);
    },
    renderer: renderer
});
var rncid =  "<c:out value='${rncid}'/>";
if(rncid!="")
	$('#rncid').val(rncid);
	
//Input bscid
${siteCellList}
$("#siteCell").jqxInput({ placeHolder: "", height: 20, width: '8%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#siteCell").jqxInput({ query: item });
        response(siteCellList);
    },
    renderer: renderer
});
var siteCell =  "<c:out value='${siteCell}'/>";
if(siteCell!="")
	$('#siteCell').val(siteCell);

$('#Menu').on('itemclick', function (event) {
	var args = event.args;
	// export data
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>') {
		
			window.open('${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/exportData.htm?startDate='+"<c:out value='${startDate}'/>"+
		        	 '&endDate='+"<c:out value='${endDate}'/>"+
		        	 '&vendor='+"<c:out value='${vendor}'/>"+
		        	 '&rncid='+"<c:out value='${rncid}'/>"+
		        	 '&siteCell='+"<c:out value='${siteCell}'/>"+
		        	 '&type='+"<c:out value='${type}'/>"+
		        	 '&status='+"<c:out value='${status}'/>"+
		        	 '&function='+"<c:out value='${function}'/>"+
		        	 '&strWhere='+$("#strWhere").val()+
		        	 '&sortfield='+$("#sortfield").val()+
		        	 '&sortorder='+$("#sortorder").val()
		        	 ,'_blank');
			}

});

//call view detail    
$("#jqxgrid").on('cellselect', function (event) 
{	var functionName = "<c:out value='${function}'/>";
    var columnheader = $("#jqxgrid").jqxGrid('getcolumn', event.args.datafield).text; 
  	if (columnheader =='RNCID' && functionName == 'tong-hop')
    {
    	var rowindex = event.args.rowindex;
    	var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
    	var startDate= '';
    	var endDate= '';
    	var vendor = '';
    	var rncid = '';
    	var day = new Date(row.day);
    	if(row.day!=null)
    	{
    		startDate = dateToYMD(day);
    		endDate=startDate;
    	}
    	if(row.vendor!=null)
    	{
    		vendor=row.vendor;
    	}
    	if(row.ne!=null)
    	{
    		rncid=row.ne;
    	}
    	
    	window.open('${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/chi-tiet.htm?startDate=' + startDate + '&endDate='+endDate+'&vendor='+vendor+'&rncid='+rncid,'_blank');
    }
  	
 	// SITE
  	if (columnheader =='Site Tăng')
    {
    	var rowindex = event.args.rowindex;
    	var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
    	var startDate= '';
    	var endDate= '';
    	var vendor = '';
    	var rncid = '';
    	var day = new Date(row.day);
    	if(row.day!=null)
    	{
    		startDate = dateToYMD(day);
    		endDate=startDate;
    	}
    	if(row.vendor!=null)
    	{
    		vendor=row.vendor;
    	}
    	if(row.rncid!=null)
    	{
    		rncid=row.rncid;
    	}
    	
    	window.open('${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/lich-su-tang.htm?startDate=' + startDate + '&endDate='+endDate+'&vendor='+vendor+'&rncid='+rncid+'&type=SITE','_blank');
    }
    
  	if (columnheader =='Site Giảm')
    {
    	var rowindex = event.args.rowindex;
    	var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
    	var startDate= '';
    	var endDate= '';
    	var vendor = '';
    	var rncid = '';
    	var day = new Date(row.day);
    	if(row.day!=null)
    	{
    		startDate = dateToYMD(day);
    		endDate=startDate;
    	}
    	if(row.vendor!=null)
    	{
    		vendor=row.vendor;
    	}
    	if(row.rncid!=null)
    	{
    		rncid=row.rncid;
    	}
    	
    	window.open('${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/lich-su-giam.htm?startDate=' + startDate + '&endDate='+endDate+'&vendor='+vendor+'&rncid='+rncid+'&type=SITE','_blank');
    }
  	
 	// CELL
 	if (columnheader =='Cell Tăng')
    {
    	var rowindex = event.args.rowindex;
    	var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
    	var startDate= '';
    	var endDate= '';
    	var vendor = '';
    	var rncid = '';
    	var day = new Date(row.day);
    	if(row.day!=null)
    	{
    		startDate = dateToYMD(day);
    		endDate=startDate;
    	}
    	if(row.vendor!=null)
    	{
    		vendor=row.vendor;
    	}
    	if(row.rncid!=null)
    	{
    		rncid=row.rncid;
    	}
    	
    	window.open('${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/lich-su-tang.htm?startDate=' + startDate + '&endDate='+endDate+'&vendor='+vendor+'&rncid='+rncid+'&type=CELL','_blank');
    }
 	if (columnheader =='Cell Giảm')
    {
    	var rowindex = event.args.rowindex;
    	var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
    	var startDate= '';
    	var endDate= '';
    	var vendor = '';
    	var rncid = '';
    	var day = new Date(row.day);
    	if(row.day!=null)
    	{
    		startDate = dateToYMD(day);
    		endDate=startDate;
    	}
    	if(row.vendor!=null)
    	{
    		vendor=row.vendor;
    	}
    	if(row.rncid!=null)
    	{
    		rncid=row.rncid;
    	}
    	
    	window.open('${pageContext.request.contextPath}/alarm/dy-trx-3g-up-down/lich-su-giam.htm?startDate=' + startDate + '&endDate='+endDate+'&vendor='+vendor+'&rncid='+rncid+'&type=CELL','_blank');
    }
    
});

function dateToYMD(date) {
    var d = date.getDate();
    var m = date.getMonth() + 1;
    var y = date.getFullYear();
    return '' + (d <= 9 ? '0' + d : d) + '/' + (m<=9 ? '0' + m : m) + '/' + y ;
}
</script>
