
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">
    .textrt{
    	text-align: right;
    }
    
    .textct {
    	text-align: center;
    }
    	.ui-multiselect {
		width:255 !important;
	}
.jqx-grid-cell > div {
    height: 100%;
    margin: 0 !important;
}
    
</style>
<title><spring:message code="header.title.contentList"/></title>
<body class="section-4" />

	<form></form>

<div class="flw20">
	<div class="block-cat">
		<div class="header-block">
			<span class="box-title"><spring:message code="header.title.danhMucCongViec"/></span>
		</div>
		<div id="danhMucCV" class="content-block" style="overflow: scroll; height: 180px;">
			<c:forEach var="item" items="${getDanhMucCongViecList}">   
           		<div class="element-box">
           			<a id="${item.id}" title="${item.name }" href="list.htm?id_Work_Types=${item.id}&received=${received}"> ${item.name } </a><span class="count">(${item.countMana}) </span>
           		</div>
           </c:forEach>
		</div>
	</div>
</div>
<div class="frw79">
	<h1><spring:message code="header.title.contentList"/></h1>
	<form:form name="frmIndex" commandName="filterContent" method="get" action="list.htm?received=${received}">
	<input value="${received}" id=received name="received" type="hidden"/>
					<table width="100%" cellspacing="5" cellpadding="1" border="0">
						<tr>
							<td  class="wid10 mwid100"><spring:message code="Phòng/Đài"/></td>
							
							<c:choose>
						      	<c:when test="${received =='A'}">
						      		<%-- 	<td class="wid20"><input id="deptName" value="${deptName}" style="width:100%" /></td> --%>
								      	<td class="wid15">
										<div class="psr ovh select">
											<select id=deptName name="deptName" class="wid100 select">
												<option value="">--Tất cả--</option>
						          				<c:forEach var="items" items="${deptList}">
									              	<c:choose>
									                <c:when test="${items.deptName == deptName}">
									                    <option value="${items.deptName}" selected="selected">${items.deptName}</option>
									                </c:when>
									                <c:otherwise>
									                    <option value="${items.deptName}">${items.deptName}</option>
									                </c:otherwise>
									              	</c:choose>
										    	</c:forEach>
				          					</select>
				          				</div>	
									</td>
						      	</c:when>
						      	<c:otherwise>
						      		<td class="wid20"><input id="deptName" value="${deptName}" style="width:100%" DISABLED /></td>
						      	</c:otherwise>
						      	</c:choose>
        
							<td  class="pl10 wid15 mwid120"><spring:message code="Tổ"/></td>
							<td class="wid15">
								<div class="psr ovh select">
									<select id="idTeam" name="idTeam" class="wid100 select">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="items" items="${teamList}">
							              	<c:choose>
							                <c:when test="${items.team == idTeam}">
							                    <option value="${items.team}" selected="selected">${items.team}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.team}">${items.team}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</select>
		          				</div>	
							</td>
							<td class="pl10 wid10 mwid130"><spring:message code="header.title.danhMucCongViec"/></td>
							<td class="wid15">
								<div class="psr ovh select">
									<select id="idWorkTypes" name="idWorkTypes" class="wid100 select">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="items" items="${getDanhMucCongViecList}">
							              	<c:choose>
							                <c:when test="${items.id == danhMucCVCBB}">
							                    <option value="${items.id}" selected="selected">${items.name}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.id}">${items.name}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</select>
		          				</div>	
							</td>
						</tr>
						<tr>
							<td><spring:message code="title.qLDanhSachCongViec.nguoiGiaoViec"/></td>
							<td>
								<div class="psr ovh select">
									<form:select path="nguoiGiaoViec" class="wid100 select" >
										<option value="">--Tất cả--</option>
						 				<c:forEach var="items" items="${fullNameList}">
						              	<c:choose>
						                <c:when test="${items.username == nguoiGiaoViecCBB}">
						                    <option value="${items.username}" selected="selected">${items.username} (${items.fullname})</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.username}">${items.username} (${items.fullname})</option>
						                </c:otherwise>
						              	</c:choose>
								    	</c:forEach>
							          </form:select>
							    </div>
							</td>
							
							<td class="pl10"><spring:message code="title.qLDanhSachCongViec.nguoiChuTriTK"/></td>
							<td>
								<div class="psr ovh select">
									<form:select path="nguoiChuTri" class="wid100 select">
										<option value="">--Tất cả--</option>
						 				<c:forEach var="items" items="${fullNameList}">
						              	<c:choose>
						                <c:when test="${items.username == nguoiChuTriCBB}">
						                    <option value="${items.username}" selected="selected">${items.username} (${items.fullname})</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.username}">${items.username} (${items.fullname})</option>
						                </c:otherwise>
						              	</c:choose>
								    	</c:forEach>
							          </form:select>
						    	</div>
							</td>
							<td class="pl10"><spring:message code="title.qLDanhSachCongViec.tinhTrang"/></td>
							<td>
								<div class="psr ovh select">
									<form:select path="tinhTrang" class="wid100 select" id="selectTinhTrang">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="items" items="${sysParameterByCodeList}">
							              	<c:choose>
							                <c:when test="${items.value == tinhTrangCBB}">
							                    <option value="${items.value}" selected="selected">${items.name}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.value}">${items.name}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</form:select>
		          				</div>
		          					
							</td>
						</tr>	
						
						<tr> 
							<td class="wid10 mwid100"><spring:message code="title.qLDanhSachCongViec.maCongViec"/></td>
							<td class="wid20"><form:input path="maCongViec" cssClass="wid100"/></td>
							
							<td class="pl10 wid15 mwid120"><spring:message code="title.qLDanhSachCongViec.tenCongViec"/></td>
							<td class="wid20" colspan="3"><form:input path="tenCongViec" cssClass="wid100"/></td>
							 
						</tr>
						<tr>
							<td>
								<spring:message code="title.qLDanhSachCongViec.ngayGiaoViec"/>
							</td>
							<td>
								<div class="fl" style="width:39%">
									<input type ="text"  value="${assignDateFrom}" name=assignDateFrom id="assignDateFrom" size="17" maxlength="16" style="width:70px">
					   			 	<img alt="calendar" title="Click to choose the from date" id="chooseassignDateFrom" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
								</div>
								<div class="fl" style="width:20%; text-align:center;"><spring:message code="title.qLDanhSachCongViec.To"/></div>
								<div class="fr" style="width:39%"><input type ="text"  value="${assignDateTo}" name=assignDateTo id="assignDateTo" size="17" maxlength="16" style="width:70px">
					   			 	<img alt="calendar" title="Click to choose the from date" id="chooseassignDateTo" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
								</div>
							</td>
							<td class="pl10">
								<spring:message code="title.qLDanhSachCongViec.thoiGianHoanThanhFrom"/>
							</td>
							<td colspan="3">
								<div class="fl" style="width:100px;">
									<input type ="text"  value="${actualDateFrom}" name=actualDateFrom id="actualDateFrom" size="17" maxlength="16" style="width:70px">
				   			 		<img alt="calendar" title="Click to choose the from date" id="chooseactualDateFrom" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
								</div>
								<div class="fl" style="width:50px; text-align:center;"><spring:message code="title.qLDanhSachCongViec.To"/></div>
								<div class="fl" style="width:100px;">
									<input type ="text"  value="${actualDateTo}" name=actualDateTo id="actualDateTo" size="17" maxlength="16" style="width:70px">
					   			 	<img alt="calendar" title="Click to choose the from date" id="chooseactualDateTo" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
								</div>
							
								</td>
						</tr>
						<tr>
							<td colspan="6"><input type="submit" class="button" name="filter"
								value="<spring:message code="button.search"/>" /></td>
						</tr>
						
					</table>
				</form:form>
				
</div>

<div class="flw100">

	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr>
			<td align="left" colspan="2">
			<ul class="ui-tabs-nav">
			 	<li class="${tabUnselected }"><a href="${pageContext.request.contextPath}/w_working_managements/list.htm?received=N&id_Work_Types=${danhMucCVCBB}"><span>Công việc đã giao</span></a></li>
				<li class="${tabSelected }"><a href="${pageContext.request.contextPath}/w_working_managements/list.htm?received=Y&id_Work_Types=${danhMucCVCBB}"><span>Công việc được giao</span></a></li>
				<%-- <c:if test="${roleManager==true}"> --%>
					<li class="${tabSelectedA }"><a href="${pageContext.request.contextPath}/w_working_managements/list.htm?received=A&id_Work_Types=${danhMucCVCBB}"><span>Quản trị công việc</span></a></li>
				<%-- </c:if> --%>
			</ul>
			</td>
			
        </tr>
        <tr>
        	<td align="right" class="ftsize12">
	           <input class="button"  type="button" id="btAdd" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/w_working_managements/formContent.htm?idWorkTypes=${danhMucCVCBB}&deptCode=${deptCode}&wwmFkId="' />
			</td>
			<td style="width: 20px"><div style="float: right;" id="jqxlistbox"></div></td>
        </tr>
</table>
<%@ include file="/includeJQ/jwGridIwork.jsp" %>	
<!-- <div id="gridWork"></div> -->
 <div id='menuReport'>
         <ul>
            <c:choose>
            	<c:when test="${roleManager=='Y' && received=='A'}">
	            		<li><fmt:message key="global.button.deleteSelected"/></li>
						<li><fmt:message key="button.devideWork"/></li>
				</c:when>
				<c:when test="${received=='Y' || received=='N'}">
	            		<li><fmt:message key="global.button.deleteSelected"/></li>
						<li><fmt:message key="button.devideWork"/></li>
				</c:when>
			</c:choose>
			<li><fmt:message key="global.button.exportExcel"/></li>
		 </ul>
 </div>

</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<script type="text/javascript">

Calendar.setup({
    inputField		:	"assignDateFrom",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseassignDateFrom",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"assignDateTo",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseassignDateTo",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"actualDateFrom",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseactualDateFrom",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"actualDateTo",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseactualDateTo",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

/* ${grid} */
var exportFileName =  "<c:out value='${exportFileName}'/>";
//create context menu
 var contextMenu = $("#menuReport").jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: theme });
 $("#grid").on('contextmenu', function () {
     return false;
 });
 $("#grid").on('rowclick', function (event) {
     if (event.args.rightclick) {
         $("#grid").jqxGrid('selectrow', event.args.rowindex);
         var scrollTop = $(window).scrollTop();
         var scrollLeft = $(window).scrollLeft();
         contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);
         return false;
     }
 });
//call funtion add listSource	
	${listSource}
 $("#jqxlistbox").jqxDropDownList({ checkboxes: true, source: listSource, width: 15, height: 15, theme: theme, dropDownHorizontalAlignment: 'right',dropDownWidth: 170 });

 $("#jqxlistbox").on('checkChange', function (event) {
 	if (event.args) {
         var item = event.args.item;
         if (item) {
         	$("#grid").jqxGrid('beginupdate');
            if (item.checked==true)
             {
         	  	$("#grid").jqxGrid('showcolumn', item.value);                      
         	}
            else
             {
      	   		$("#grid").jqxGrid('hidecolumn', item.value);
             }
            $("#grid").jqxGrid('endupdate');
          
     	}
    }
 	
 });
var theme =  getTheme(); 
$('#btAdd').jqxButton({ theme: theme });
//handle context menu cong viec co dinh trong ca
$("#menuReport").on('itemclick', function (event) {
	    var args = event.args;
	    var rowindex = $("#grid").jqxGrid('getselectedrowindex');
	    var row = $("#grid").jqxGrid('getrowdata', rowindex);
	    var exportFileName =  "<c:out value='${exportFileName}'/>";
	    var deptCode =  "<c:out value='${deptCode}'/>";
	   /*  
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
	    	
	   	  	window.location = '${pageContext.request.contextPath}/w_working_managements/formContent.htm?id='+row.id+'&idWorkTypes='+row.idWorkTypes+'&deptCode='+deptCode;   
	    } */
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	    	if (answer)
	    	{
	    		window.location = '${pageContext.request.contextPath}/w_working_managements/deleteContent.htm?id='+row.id+'&note=list&received='+$("#received").val();  
	    	}
	    }
		if ($.trim($(args).text()) == '<fmt:message key="button.devideWork"/>') {
			window.location = '${pageContext.request.contextPath}/w_working_managements/formContent.htm?idWorkTypes='+row.idWorkTypes+'&deptCode='+deptCode+'&wwmFkId='+row.id+'&note=list&maCVCha='+row.maCongViec+'&received='+$("#received").val(); 
	    }
	    /* if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#grid").jqxGrid('exportdata', 'xls', exportFileName);
	    } */
		if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	
        	var funtion = "<c:out value='${function}'/>";
        	 window.open('${pageContext.request.contextPath}/w_working_managements/export.htm?idWorkTypes='+"<c:out value='${filterContent.idWorkTypes}'/>"+
        			 '&maCongViec='+"<c:out value='${filterContent.maCongViec}'/>"+
        			 '&tenCongViec='+"<c:out value='${filterContent.tenCongViec}'/>"+
        			 '&nguoiGiaoViec='+"<c:out value='${filterContent.nguoiGiaoViec}'/>"+
     				'&nguoiChuTri='+"<c:out value='${filterContent.nguoiChuTri}'/>"+
     				'&nguoiNhanViec='+"<c:out value='${filterContent.nguoiNhanViec}'/>"+
    				'&tinhTrang='+"<c:out value='${filterContent.tinhTrang}'/>"+
    				'&actualDateFrom='+"<c:out value='${actualDateFrom}'/>"+
    				'&actualDateTo='+"<c:out value='${actualDateTo}'/>"+
    				'&assignDateFrom='+"<c:out value='${assignDateFrom}'/>"+
    				'&assignDateTo='+"<c:out value='${assignDateTo}'/>"+
    				'&column='+
    				'&order='+
    				'&received='+"<c:out value='${received}'/>"+
    				'&username='+"<c:out value='${username}'/>"+
    				'&deptName='+"<c:out value='${deptName}'/>"
        			 ,'_blank');
        	
        }
});
var cellsrendererLink = function (row, column, value) {
   /*  if (value.indexOf('#') != -1) {
        value = value.substring(0, value.indexOf('#'));
    }
    var format = { target: '"_blank"' };
    var html = $.jqx.dataFormat.formatlink(value, format);
    return html; */
    var rowValue = $("#grid").jqxGrid('getrowdata', row);
    var type =  "<c:out value='${type}'/>";
	return '<a href="${pageContext.request.contextPath}/w_working_managements/formContentDetails.htm?id='+rowValue.id+'&type='+type+'&received='+$("#received").val()+'"/>'+value+'</a>';
}

/* $("#grid").on('cellselect', function (event) 
  		{
   			var type =  "<c:out value='${type}'/>";
   			var columnheader = $("#grid").jqxGrid('getcolumn', event.args.datafield).text; 
   			//alert(columnheader);
  		  	if (columnheader =='Mã công việc' || columnheader=='Tên công việc')
  		    {
  		    	var rowindex = event.args.rowindex;
  		    	var row = $("#grid").jqxGrid('getrowdata', rowindex);
  		    	window.location = '${pageContext.request.contextPath}/w_working_managements/formContentDetails.htm?id='+row.id+'&type='+type;
  		    }
  		    
  		}); */
$(function() {
	${highlight}	
	${highlightRow}
  	});   
  	
$(document).ready(function(){
	$('#'+'<c:out value="${urlForm}"/>').addClass("selected");

	/* $( "#actualDateFrom" ).datepicker();
	$( "#actualDateFrom" ).datepicker( "option", "dateFormat", "dd/mm/yy" );
	$( "#actualDateFrom" ).val('<c:out value="${actualDateFrom}"/>');

	$( "#actualDateTo" ).datepicker();
	$( "#actualDateTo" ).datepicker( "option", "dateFormat", "dd/mm/yy" );
	$( "#actualDateTo" ).val('<c:out value="${actualDateTo}"/>');

	$( "#assignDateFrom" ).datepicker();
	$( "#assignDateFrom" ).datepicker( "option", "dateFormat", "dd/mm/yy" );
	$( "#assignDateFrom" ).val('<c:out value="${assignDateFrom}"/>');

	$( "#assignDateTo" ).datepicker();
	$( "#assignDateTo" ).datepicker( "option", "dateFormat", "dd/mm/yy" );
	$( "#assignDateTo" ).val('<c:out value="${assignDateTo}"/>'); */
});

function focusIt()
{
  var mytext = document.getElementById("maCongViec");
  mytext.focus();
}

onload = focusIt;
	
$("#deptName").change(function(){
	
		$.getJSON("${pageContext.request.contextPath}/ajax/getTeamListAlarm.htm",{dept: $(this).val()}, function(j){
			var options = '';
			for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i]+ '">' + j[i]+ '</option>';
			}
			$("#idTeam").html(options);
		});
});
</script>