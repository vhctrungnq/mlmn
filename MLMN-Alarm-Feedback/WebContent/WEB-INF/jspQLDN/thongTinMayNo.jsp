<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
.content{
text-align: center;
}
</style>
<!-- DoaiNH viết chức năng quản lí thông tin máy nổ ngày 10-10-2017-->
<title>Quản lí thông tin máy nổ</title>
<h3>Quản lí thông tin máy nổ</h3>
<table style = "border:0; width:100%;cellspacing:0; cellpadding:0;class:form">	
	<tr>
		<td style = "align:left;">
			<form:form method = "get" action = "list.htm">
				<table> 
				 	<tr>
						<td>ID Trạm</td>
						<td> <input id = "idTram" value = "${idTram}" name = "idTram"/> </td>
						<td style="padding-left: 30px;">ID ĐTXD </td>
						<td> <input id = "idDtxd" value = "${idDtxd}" name = "idDtxd"/></td>
						<td style="padding-left: 30px;">Tỉnh </td>
						<td> <div id = "tinh"  name = "tinh"></div></td>
						<td style="padding-left: 30px;">Tên trạm </td>
						<td> <input id = "tenTram" value = "${tenTram}" name = "tenTram"/></td>
						
				 	</tr>
				 	<tr>
						<td>Tổ</td>
						<td> <input id = "to" value = "${to}" name = "to"/></td>
						<td style="padding-left: 30px;">Nhóm</td>
						<td> <input id = "nhom" value = "${nhom}" name = "nhom"/></td>
						<td style="padding-left: 30px;">Loại thanh toán</td>
						<td> <div id = "loaiThanhToan"  name = "loaiThanhToan"></div></td>
						<td style="padding-left: 30px;">Tên XHH/VTT</td>
						<td> <input id = "tenXhhVtt" value = "${tenXhhVtt}" name = "tenXhhVtt"/></td>
						
					</tr>
					<tr>
						<td>Hiệu máy nổ</td>
						<td> <input id = "hieuMayNo" value = "${hieuMayNo}" name = "hieuMayNo"/></td>
						<td style="padding-left: 30px;">Công suất</td>
						<td> <input id = "congSuat" value = "${congSuat}" name = "congSuat"/></td>
						<td style="padding-left: 30px;">Loại nhiên liệu</td>
						<td> <div id = "loaiNhienLieu"  name = "loaiNhienLieu"></div></td>
						<td style="padding-left: 30px;">Định mức</td>
						<td> <input id = "dinhMuc" value = "${dinhMuc}" name = "dinhMuc"/></td>
						
					</tr>
					<tr>
						<td>AST</td>
						<td> <div id = "ATS" name = "ATS"></div></td>
						<td style="padding-left: 30px;">
							<input class="button" type="submit" id="submit" value="Tìm kiếm" />
						</td>
					</tr>
				</table>
			</form:form>
		
	</tr>
	<tr>
		<td>
				<div id='jqxWidget' style="margin-top:5px;"></br>
			   <div style="float: right;margin-bottom:3px;width:300px;">
			        	<table style = "border:0; cellspacing:1; cellpadding:0; width:100%;">
							<tr>
								<td align="right">
									<input type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' />
									<input type="button" value="<fmt:message key="qldn.import"/>" id='importFile'   onclick='window.location="${pageContext.request.contextPath}/import-qldn/upload.htm?typeFile=QLDN_THONG_TIN_MAY_NO"'/>
									<input type="button" value="Export" id="exportFile"/>
		
								</td>
							</tr>	
					</table>
		        </div><br>  
		        <div id="gridData"></div>
				<div id='Menu'>
				            <ul>
				            	<li><fmt:message key="global.button.editSelected" /></li>
								<li><fmt:message key="global.form.xoa" /></li>
					        </ul>
				</div>
			</div>
		</td>
	</tr>


</table>

<script type="text/javascript">
$(document).ready(function () {
    
	${gridData}
 	var idTram = "<c:out value = '${idTram}'/>";
 	var idDtxd = "<c:out value = '${idDtxd}'/>";
 	var tenTram = "<c:out value = '${tenTram}'/>";
 	var tinh = "<c:out value = '${tinh}'/>";
 	var to = "<c:out value = '${to}'/>";
 	var nhom = "<c:out value = '${nhom}'/>";
 	var loaiThanhToan = "<c:out value = '${loaiThanhToan}'/>";
 	var tenXhhVtt = "<c:out value = '${tenXhhVtt}'/>";
 	var hieuMayNo = "<c:out value = '${hieuMayNo}'/>";
 	var congSuat = "<c:out value = '${congSuat}'/>";
 	var dinhMuc = "<c:out value = '${dinhMuc}'/>";
 	var loaiNhienLieu = "<c:out value = '${loaiNhienLieu}'/>";
 	var ats = "<c:out value = '${ats}'/>";
 	
 	var theme =  getTheme();
 	$('#addNew').jqxButton({ theme: theme });
 	$('#importFile').jqxButton({ theme: theme });
 	$('#exportFile').jqxButton({ theme: theme });
 	$('#addNew').click(function () {	
 		window.location = 'formTTMN.htm';
 		
 	});
 	
 	$("#exportFile").click(function (event) {
 		var exportFileName =  "${exportFileName}";
		window.open('${pageContext.request.contextPath}/dien/report/exportData_Export_TT_MayNo.htm?idTram='+ idTram 
						 + "&idDtxd=" + idDtxd + "&tenTram=" + tenTram + "&tinh="+ tinh 
						 + "&to=" + to + "&nhom=" + nhom + "&loaiThanhToan=" + loaiThanhToan + "&tenXhhVtt="+ tenXhhVtt
						 + "&hieuMayNo=" + hieuMayNo+ "&congSuat=" + congSuat + "&dinhMuc=" + dinhMuc + "&loaiNhienLieu="+ loaiNhienLieu + "&ats=" + ats);
	  
 	});
       var contextMenu = $('#Menu').jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: getTheme() });
        $('#gridData').on('contextmenu', function () {
            return false;
        });
        $('#gridData').on('rowclick', function (event) {
        	if (event.args.rightclick) {
        		$('#gridData').jqxGrid('selectrow', event.args.rowindex);
        		var scrollTop = $(window).scrollTop();
        		var scrollLeft = $(window).scrollLeft();
        		contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, 
                		parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);
        		return false;
        	}       		
        });

    	// handle context menu clicks.
    	 $("#Menu").on('itemclick', function (event) {
    	    var args = event.args;
    	 // add new row
    		if ($.trim($(args).text()) == '<fmt:message key="global.button.addNew"/>') {	
    			window.location = 'formTTMN.htm';
    		}
    	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
    	   	  	var rowindex = $("#gridData").jqxGrid('getselectedrowindex');
    	        var row = $("#gridData").jqxGrid('getrowdata', rowindex);
    	       // alert(row.id);
    	   		window.location = 'formTTMN.htm?id='+row.id;   
    	   	     
    	    }
    	    	if ($.trim($(args).text()) == '<fmt:message key="global.form.xoa"/>')  {
        	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
        	    	if (answer)
        	    	{
        				var selectedrowindexes = $('#gridData').jqxGrid('getselectedrowindexes'); 
        	    		var idList="";
        	    		var cond="";
        	    		if (selectedrowindexes != null) {
        	    			for (var i=0; i<selectedrowindexes.length; i++) {
        	    				var row = $("#gridData").jqxGrid('getrowdata', selectedrowindexes[i]);
        	    				idList+=cond+row.id;
        	    				cond=",";
        	    			}
        	    			window.location = 'delete.htm?id='+idList;    
        	    		}
        			}
    	    } 
    			
    	 });  
    });
</script>
<script>
var theme= getTheme();
//combobox tinh
var urlprovince = "${pageContext.request.contextPath}/ajax/getDSTinh.htm?region=";
   var sourceprovince =
   {
      datatype: "json",
      url: urlprovince,
      datafields: [
                    { name: 'province'}
                ],
       async: false
  };
   var dataAdapterprovince = new $.jqx.dataAdapter(sourceprovince);
   $("#tinh").jqxDropDownList({source: dataAdapterprovince, displayMember: "province",  valueMember: "province",checkboxes: true,  width: 172,  theme: theme,enableHover: true });           

   var province = '<c:out value="${tinh}"/>';
  // alert(dept);
if(province=="")
 $('#tinh').val('Choose');
else
{
 var provinceVar = province.split(",");
 if (provinceVar != null) {
  for (var i=0; i<provinceVar.length; i++) {
   $("#tinh").jqxDropDownList('checkItem', provinceVar[i] ); 
  }
 }
}
//Create a jqxComboBox vendor
var urlloaithanhtoan = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=LOAI_THANH_TOAN";
// prepare the data
var sourceloaithanhtoan =
{
    datatype: "json",
    datafields: [
        { name: 'value' },
        { name: 'name' }
    ],
    url: urlloaithanhtoan,
    async: false
};
var dataAdapterloaithanhtoan = new $.jqx.dataAdapter(sourceloaithanhtoan);
// Create a jqxComboBox
$("#loaiThanhToan").jqxComboBox({ source: dataAdapterloaithanhtoan, displayMember: "value", valueMember: "name", width: 172,height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
var loaithanhtoan =  "<c:out value='${loaiThanhToan}'/>";
if(loaithanhtoan=="")
$('#loaiThanhToan').val('');
else
$('#loaiThanhToan').val(loaithanhtoan);

var urlloainhienlieu = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=LOAI_NHIEN_LIEU";
//prepare the data
var sourceloainhienlieu =
{
 datatype: "json",
 datafields: [
     { name: 'value' },
     { name: 'name' }
 ],
 url: urlloainhienlieu,
 async: false
};
var dataAdapterloainhienlieu = new $.jqx.dataAdapter(sourceloainhienlieu);
//Create a jqxComboBox
$("#loaiNhienLieu").jqxComboBox({ source: dataAdapterloainhienlieu, displayMember: "value", valueMember: "name",  width: 172,height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
var loainhienlieu =  "<c:out value='${loaiNhienLieu}'/>";
if(loainhienlieu=="")
$('#loaiNhienLieu').val('');
else
$('#loaiNhienLieu').val(loainhienlieu);

var urlATS = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=ATS";
//prepare the data
var sourceATS =
{
datatype: "json",
datafields: [
   { name: 'value' },
   { name: 'name' }
],
url: urlATS,
async: false
};
var dataAdapterATS = new $.jqx.dataAdapter(sourceATS);
//Create a jqxComboBox
$("#ATS").jqxComboBox({ source: dataAdapterATS, displayMember: "value", valueMember: "name", width: 150,height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
var ATS =  "<c:out value='${ats}'/>";
if(ATS=="")
$('#ATS').val('');
else
$('#ATS').val(ATS);
</script>
