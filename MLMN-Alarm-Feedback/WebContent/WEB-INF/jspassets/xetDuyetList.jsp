<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title><fmt:message key="sidebar.proposedWarehouse.xetDuyet"/></title>
<content tag="heading"><fmt:message key="sidebar.proposedWarehouse.xetDuyet"/></content>

<form:form commandName="filter" name="checkform" method="post" action="list.htm">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
			<tr> 
				<td align="left" colspan="2">
						
						<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr>
								<td class="wid7 mwid70"><fmt:message key="proposedWarehouse.boPhanSd"/></td>
								<td class="wid20">
									<form:input path="boPhanSd" cssClass="wid90"/>
								</td>
								<td class="wid7 mwid70"><fmt:message key="proposedWarehouse.donViSd"/></td>
								<td class="wid20"><form:input path="donViSd" cssClass="wid90"/></td>
								 
								<td class="wid7 mwid90"><fmt:message key="proposedWarehouse.nguoiSd"/></td>
								<td class="wid20"><form:input path="nguoiSd" cssClass="wid90"/></td>
										 
							</tr>
							<tr>
								<td><fmt:message key="proposedWarehouse.ngayXuat"/></td>
								<td>
									<input type="text" id="exportDateFrom" name="exportDateFrom" value="${exportDateFrom}" class="wid50" maxlength="20"/>
	          						<img alt="calendar" title="Click to choose the start date" id="chooseExportDateFrom" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	          					</td>
	          					<td><fmt:message key="proposedWarehouse.denNgay"/></td>
	          					<td>
	          						<input type="text" id="exportDateTo" name="exportDateTo" value="${exportDateTo}" class="wid50" maxlength="20"/>
	          						<img alt="calendar" title="Click to choose the end date" id="chooseExportDateTo" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
								</td>
	          					<td><fmt:message key="proposedWarehouse.createdBy"/></td>
								<td ><form:input path="createdBy" cssClass="wid90"/></td>
								<td><input type="submit" class="button" name="filter" onclick="setAction('timKiem')"
									value="<fmt:message key="global.form.timkiem"/>" /></td>
							</tr>				
						</table>
					
					</td>
			</tr>
			<tr>
				<td colspan="2">
					<div style="width:100%;overflow:auto; ">
						<display:table name="${xetDuyetList}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true">
							<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
							<display:column style="max-width:150px;word-wrap: break-word;" property="boPhanSd" titleKey="proposedWarehouse.boPhanSd" sortable="true" sortName="BO_PHAN_SD"/>
							<display:column style="max-width:150px;word-wrap: break-word;" property="donViSd" titleKey="proposedWarehouse.donViSd" sortable="true" sortName="DON_VI_SD"/>
							<display:column property="nguoiSd" titleKey="proposedWarehouse.nguoiSd" sortable="true" sortName="NGUOI_SD"/>
							<display:column property="ngayXuat" format="{0,date,dd/MM/yyyy}" titleKey="proposedWarehouse.ngayXuat" sortable="true" sortName="NGAY_XUAT"/>
							<display:column property="productName" titleKey="proposedWarehouse.productName" sortable="true" sortName="PRODUCT_NAME"/>
							<display:column property="productCode" titleKey="proposedWarehouse.productCode" sortable="true" sortName="PRODUCT_CODE"/>
							<display:column property="serialNo" titleKey="proposedWarehouse.serialNo" sortable="true" sortName="SERIAL_NO"/>
							<display:column property="soLuong" titleKey="proposedWarehouse.soLuong" headerClass="hide" class="hide" sortable="true" sortName="SO_LUONG"/>
							<display:column titleKey="proposedWarehouse.soLuong" class="test" media="html">
								<input style="width:30px; font-size:12px;" type = "text"  value="${item.soLuong}" id = "soluong_${item_rowNum}" >
							</display:column>
							
							<display:column property="createdBy" titleKey="proposedWarehouse.createdBy" sortable="true" sortName="CREATED_BY"/>
							
							<display:column property="lyDoXuat" titleKey="proposedWarehouse.lyDoXuat" sortable="true" sortName="LY_DO_XUAT"/>
							<display:column property="description" titleKey="proposedWarehouse.description" sortable="true" sortName="DESCRIPTION"/>
							<display:column titleKey="global.management" media="html" class="centerColumnMana" >
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
			
			<tr>
				<td align="left" colspan="2">
						<div>
							<input type="hidden" id="keySoLuong" name="keySoLuong" value="" />
					  		<input type="hidden" id="keyId" name="keyId" value="" />
						</div>
						<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr>
								<td class="wid9 mwid110"><fmt:message key="proposedWarehouse.soPhieuXuat"/>&nbsp;<font color="red">(*)</font></td>
								<td class="wid20">
									<input id="votesNo" name="votesNo" value="${votesNo}" class="wid90"/>&nbsp;<span class="error">${errorVotesNo}</span>
								</td>
								<td class="wid9 mwid100"><fmt:message key="proposedWarehouse.nguoiXuat"/></td>
								<td class="wid20"><input id="usersEx" name="usersEx" value="${usersEx}" class="wid90"/></td>
								 
							</tr>
							<tr>
								<td><fmt:message key="proposedWarehouse.nguonLayThietBi"/></td>
								<td><input id="description" name="description"  value="${description}"  class="wid90"/></td>
								
								<td><fmt:message key="proposedWarehouse.baoGomThietBi"/></td>
								<td><input id="baoGomThietBi" name="baoGomThietBi"  value="${baoGomThietBi}"  class="wid90"/></td>
								
								<td>
									<input class="button" type="submit" name="btnXetDuyet" id="btnXetDuyet" value="<fmt:message key="global.form.xetDuyet"/>" onclick="setAction('xetDuyet')"/>&nbsp;
									<input type="hidden" id="action" name="action">
								</td>
							</tr>				
						</table>
					
				</td>
			</tr>
	</table>
</form:form>
<div style="padding-top:4px">
	<table>
		<tr> 
			<td>
				<input type = "text" value = "${row_num}" style="display: none;" id = "txtRowNum"/>
				<input type = "text" value = "${rowId}" style="display: none;" id = "rowId"/>
				<div style="margin-left:600px;display: none;" class="loading fl">
					<img src="${pageContext.request.contextPath}/images/icon/barIndicator.gif">
				</div>
			</td>
		</tr> 
	</table> 
</div>

<div id="dialog-confirm" title="Thông báo" style="padding: 10px;display:none;">
	<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
	Bạn có muốn in phiếu xuất kho không?</p>
</div>

<script type="text/javascript">

$(function() {

	var id = '<c:out value="${highlight}"/>';
	
	var mySplitResult = id.split(",");
	
	$('#item>tbody>tr').each( function(){
 	    	 
    	 	var rowIndex = $(this).closest("tr").index();
    	 	var value = rowIndex;
    	 	
    		for(var i = 0; i < mySplitResult.length; i++){
    			
 	    		if(value == mySplitResult[i] && mySplitResult[i] != "")
 	 			  $(this).find("td").css({ 'background-color' : '#FFA07A', 'text-decoration': 'none'});
 	    	}  	 
   	 });

	var action = '<c:out value="${action}"/>';
	var dialog = '<c:out value="${dialog}"/>';
	
	if(action == 'xetDuyet' && dialog == 'Y'){
		/* $("#dialog-confirm").dialog({
			resizable: false,
			height: 120,
			modal: true,
			buttons: {
				"Đồng ý": function() { */
					var boPhanSd = encodeURIComponent($("#boPhanSd").val());
					var donViSd = encodeURIComponent($("#donViSd").val());
					var nguoiSd = encodeURIComponent($("#nguoiSd").val());
					var description = encodeURIComponent($("#description").val());
					var usersEx = encodeURIComponent($("#usersEx").val());
					var soPhieu = encodeURIComponent($("#votesNo").val());
					var baoGomThietBi = encodeURIComponent($("#baoGomThietBi").val());
					
				    var myWindow = window.open('xetDuyetDocx.htm?boPhanSd=' + boPhanSd+'&donViSd=' + donViSd+'&nguoiSd=' + nguoiSd
							+'&exportDateFrom=' + $("#exportDateFrom").val()+'&exportDateTo=' + $("#exportDateTo").val()+'&description=' + description+'&usersEx=' + usersEx+'&soPhieu=' + soPhieu+'&baoGomThietBi=' + baoGomThietBi,'_blank','width=200,height=100');

				    //$(this).dialog("close");
				    
				    loop(myWindow);
				    
				/* },
				"Không": function() {
					submitAll();
				},
				"Hủy": function() {
					$(this).dialog("close");
				}
			}
		}); */
	}
	 
});
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
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

  	var errorVotesNo = '<c:out value="${errorVotesNo}"/>';
	
	if(errorVotesNo !=""){
		  var mytext = document.getElementById("votesNo");
		  mytext.focus();
	}
	else if(document.checkform.boPhanSd.value == "")
	{
			var mytext = document.getElementById("boPhanSd");
		  	mytext.focus();
	}
}

onload = focusIt;
</script>

<script type="text/javascript">
		$("#btReport").click(function(){
			/* var type= $("select#report").val();
			if (type=='Excel')
			{ */
				var boPhanSd = encodeURIComponent($("#boPhanSd").val());
				var donViSd = encodeURIComponent($("#donViSd").val());
				var nguoiSd = encodeURIComponent($("#nguoiSd").val());
				var description = encodeURIComponent($("#description").val());
				var usersEx = encodeURIComponent($("#usersEx").val());
				var soPhieu = encodeURIComponent($("#votesNo").val());
				var baoGomThietBi = encodeURIComponent($("#baoGomThietBi").val());
				//alert(usersEx);
				window.open('${pageContext.request.contextPath}/assets/xet-duyet-thiet-bi/xetDuyetDocx.htm?boPhanSd=' + boPhanSd+'&donViSd=' + donViSd+'&nguoiSd=' + nguoiSd
						+'&exportDateFrom=' + $("#exportDateFrom").val()+'&exportDateTo=' + $("#exportDateTo").val()+'&description=' + description+'&usersEx=' + usersEx+'&soPhieu=' + soPhieu+'&baoGomThietBi=' + baoGomThietBi,'_blank','width=300,height=200,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0','yes|no|1|0',true);
				
			 /*}
			if (type=='Pdf')
			{
				window.open('/VMSC2-Alarm-Feedback/assets/xet-duyet-thiet-bi/xetDuyetPdf.htm?boPhanSd=' + $("#boPhanSd").val()+'&donViSd=' + $("#donViSd").val()+'&nguoiSd=' + $("#nguoiSd").val()
						+'&exportDateFrom=' + $("#exportDateFrom").val()+'&exportDateTo=' + $("#exportDateTo").val()+'&description=' + $("#description").val()+'&usersEx=' + $("#usersEx").val(),'_blank','width=300,height=200,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0','yes|no|1|0',true);
			} */
			
			/* $.getJSON("/VMSC2-Alarm-Feedback/assets/xet-duyet-thiet-bi/xetDuyetDocx.htm", {boPhanSd : encodeURI($("#boPhanSd").val()), donViSd: encodeURI($("#donViSd").val()), nguoiSd: encodeURI($("#nguoiSd").val()), 
				exportDateFrom: $('#exportDateFrom').val(), exportDateTo : $('#exportDateTo').val(), description: encodeURI($("#description").val()), usersEx: encodeURI($("#usersEx").val()) }, function(j){
					window.open('','_blank','width=300,height=200,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0','yes|no|1|0',true);				
			});*/

				
		});
</script>

<script type="text/javascript">
function get_alias() {
	$(".loading").css('display', 'block');
	var rowId = $("#rowId").val();
	var row_num = $("#txtRowNum").val(); 
	
	var soLuong = "";
	for(var i = 1; i <= row_num; i++)
	{ 
		if($("#soluong_"+i).val() != "" || $("#soluong_"+i).val() != null){
			soLuong = soLuong + $("#soluong_"+i).val() + ","; 
		}else{
			soLuong = soLuong + "0,";
		} 
	} 
	
	var keySoLuong = document.getElementById("keySoLuong");
	var keyId = document.getElementById("keyId");
	
	if (soLuong != "") {
		keySoLuong.value = soLuong;
		keyId.value = rowId;
		$(".loading").css('display', 'none');
	}
	
}

function setAction(value) {
	var action = document.getElementById("action");
	action.value = value;
	get_alias();
	return true;
}

function submitAll() {
	
	document.getElementById("votesNo").value = '';
	document.getElementById("usersEx").value = '';
	document.getElementById("description").value = '';
	document.getElementById("baoGomThietBi").value = '';
	
	$('#filter').submit();
}

function loop(myWindow){
	if (myWindow.closed == true) {

		submitAll();
	} else {

		setTimeout(function(){

			submitAll();
			},6000);
	}
}

/*$("#btnXetDuyet").click(function() {

	//submitAll();
	//var dialog = '<c:out value="${dialog}"/>';
	
	//if(dialog == "Y"){

		 $("#dialog-confirm").dialog({
			resizable: false,
			height: 120,
			modal: true,
			buttons: {
				"Đồng ý": function() {
					var boPhanSd = encodeURIComponent($("#boPhanSd").val());
					var donViSd = encodeURIComponent($("#donViSd").val());
					var nguoiSd = encodeURIComponent($("#nguoiSd").val());
					var description = encodeURIComponent($("#description").val());
					var usersEx = encodeURIComponent($("#usersEx").val());
					var soPhieu = encodeURIComponent($("#votesNo").val());
					var baoGomThietBi = encodeURIComponent($("#baoGomThietBi").val());
					
				    var myWindow = window.open('xetDuyetDocx.htm?boPhanSd=' + boPhanSd+'&donViSd=' + donViSd+'&nguoiSd=' + nguoiSd
							+'&exportDateFrom=' + $("#exportDateFrom").val()+'&exportDateTo=' + $("#exportDateTo").val()+'&description=' + description+'&usersEx=' + usersEx+'&soPhieu=' + soPhieu+'&baoGomThietBi=' + baoGomThietBi,'_blank','width=200,height=100');

				    $(this).dialog("close");
				    
				    loop(myWindow);
				},
				"Không": function() {
					submitAll();
				},
				"Hủy": function() {
					$(this).dialog("close");
				}
			}
		}); 
		//}
	
});*/

</script>