<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
.content{
text-align: center;
}
</style>
<!-- DoaiNH viết chức năng quản lí thông tin mat bang ngay 16-10-2017-->
<title>Quản lí thông tin mặt bằng</title>
<h3>Quản lí thông tin mặt bằng</h3>
<table style = "border:0; width:100%;cellspacing:0; cellpadding:0;class:form">	
	<tr>
		<td style = "align:left;">
			<form:form method = "get" action = "thong-tin-mat-bang.htm">
				<table> 
				 	<tr>
						<td>Mã trạm</td>
						<td> <input id = "maTram" value = "${maTram}" name = "maTram"/> </td>
						<td style="padding-left: 30px;">Mã hợp đồng</td>
						<td> <input id = "idHopdong" value = "${idHopdong}" name = "idHopdong"/></td>
						<td style="padding-left: 30px;">Số hợp đồng</td>
						<td> <input id = "sohd" value = "${sohd}" name = "sohd"/></td>
						<td style="padding-left: 30px;">Tỉnh </td>
						<td> <div id = "tinh"  name = "tinh"></div></td>
				 	</tr>
				 	<tr>
						<td>Huyện</td>
						<td> 
						<select id="huyen" name="huyen" style="width:174px;text-align:center">
							<option value="">--Chọn huyện--</option>
		       				<c:forEach var="items" items="${hProvincesCodeList}">
				              	<c:choose>
				                <c:when test="${items.district == huyen}">
				                    <option value="${items.district}" selected="selected">${items.district}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.district}">${items.district}</option>
				                </c:otherwise>
				              	</c:choose>
					    	</c:forEach>
						</select>
						</td>
						<td style="padding-left: 30px;">Hình thức sở hữu</td>
						<td> <input id = "htSohuu" value = "${htSohuu}" name = "htSohuu"/></td>
						<td style="padding-left: 30px;">Đơn vị sở hữu</td>
						<td> <input id = "dvSohuu" value = "${dvSohuu}" name = "dvSohuu"/></td>
						<td style="padding-left: 30px;">Chủ hợp đồng</td>
						<td> <input id = "chuthehd" value = "${chuthehd}" name = "chuthehd"/></td>
					</tr>
					<tr>
						<td>Đơn giá</td>
						<td> <input id = "dongiathangNovat" value = "${dongiathangNovat}" name = "dongiathangNovat"/></td>
						<td style="padding-left: 30px;">Ngày kết thúc</td>
						<td> <input id = "hdNgayketthuc" value = "${hdNgayketthuc}" name = "hdNgayketthuc"/>
							<img alt="calendar" title="Chon ngay bat dau" id="choosehdNgayKetThuc" 
								style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>
						<td style="padding-left: 30px;">Ngày tính tiền</td>
						<td> <input id = "ngaytinhtien" value = "${ngaytinhtien}" name = "ngaytinhtien"/>
							<img alt="calendar" title="Chon ngay bat dau" id="chooseNgayTinhTien" 
								style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>
					
						<td style="padding-left: 30px;">
							<input class="button" type="submit" id="submit" value="Tìm kiếm" />
						</td>
					</tr>
				</table>
			</form:form>
		
	</tr>
	<tr>
		<td>
				<div id='jqxWidget' style="margin-top:5px;">
			   <div style="float: right;margin-bottom:3px;width:300px;">
			        	<table style = "border:0; cellspacing:1; cellpadding:0; width:100%;">
							<tr>
								<td align="right">
									<input type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' />
								</td>
							</tr>	
					</table>
		        </div><br>  
				<div class = "content" id="jqxgrid"></div>
				<div id='Menu'>
					<ul>
						<li><fmt:message key="global.button.addNew" /></li>
						<li><fmt:message key="global.button.editSelected" /></li>
						<li><fmt:message key="global.form.xoa" /></li>
						<li><fmt:message key="global.button.exportExcel" /></li>
					</ul>
				</div>
			</div>
		</td>
	</tr>


</table>
<script type="text/javascript">
$('#addNew').click(function () {	
	window.location = 'formTTMB.htm';
	
});
</script>
<script type="text/javascript">
 	var maTram = "<c:out value = '${maTram}'/>";
 	var idHopdong = "<c:out value = '${idHopdong}'/>";
 	var sohd = "<c:out value = '${sohd}'/>";
 	var tinh = "<c:out value = '${tinh}'/>";
 	var huyen = "<c:out value = '${huyen}'/>";
 	var htSohuu = "<c:out value = '${htSohuu}'/>";
 	var dvSohuu = "<c:out value = '${dvSohuu}'/>";
 	var dai = "<c:out value = '${dai}'/>";
 	var chuthehd = "<c:out value = '${chuthehd}'/>";
 	var dongiathangNovat = "<c:out value = '${dongiathangNovat}'/>";
 	var hdNgayketthuc = "<c:out value = '${hdNgayketthuc}'/>";
 	var ngaytinhtien = "<c:out value = '${ngaytinhtien}'/>";

    $(document).ready(function () {
        var url = "${pageContext.request.contextPath}/mat-bang/report/dataTTMatBang.htm?maTram=" 
            + maTram + "&idHopdong=" + idHopdong + "&sohd=" + sohd + "&tinh="
             + tinh + "&huyen=" + huyen + "&htSohuu=" + htSohuu + "&dvSohuu=" + dvSohuu + "&dai="
             + dai + "&chuthehd=" + chuthehd+ "&dongiathangNovat=" + dongiathangNovat + 
             "&hdNgayketthuc=" + hdNgayketthuc + "&ngaytinhtien=" + ngaytinhtien;
        // prepare the data
        var source =
        {
            datatype: "json",
            datafields: [
            	{ name: 'id', type: 'number' },
                { name: 'maTram', type: 'string' },
                { name: 'idHopdong', type: 'string' },
                { name: 'sohd', type: 'string' },
                { name: 'tinh', type: 'string' },
                { name: 'htSohuu', type: 'string' },
                { name: 'chuthehd', type: 'string' },
                { name: 'hdNgayhieuluc', type: 'date' },
                { name: 'hdNgayketthuc', type: 'date' },
                { name: 'ngayThanhToan', type: 'date' },
                { name: 'dongiathangNovat', type: 'string' },
                { name: 'sotienggNam', type: 'string' }
            ],
            id : 'id',
            url: url
        };
        var dataAdapter = new $.jqx.dataAdapter(source);
        $("#jqxgrid").jqxGrid(
        {
            width:' 100%',
            source: dataAdapter,
            columnsresize: true,
            selectionmode : 'multiplerowsextended',
            showfilterrow: true,
            filterable: true,
            sortable: true,
            pageable: true,
            altrows: true,
            columnsresize: true,
            columnsreorder: true,
            scrollmode: 'logical',
            	 columns: [
                 	{
                         text: 'No.', sortable: false, filterable: false, editable: false,
                         groupable: false, draggable: false, resizable: false,
                         datafield: '', columntype: 'number', width: 50,cellsalign: 'center', align: 'center',
                         cellsrenderer: function (row, column, value) {
                             return "<div style='margin:4px;'>" + (value + 1) + "</div>";
                         }
                     },
              { text: 'Mã trạm', datafield: 'maTram',cellsalign: 'center', align: 'center', width: 150 },
              { text: 'Mã hợp đồng', datafield: 'idHopdong',cellsalign: 'center', align: 'center', width: 150 },
              { text: 'Số hợp đồng', datafield: 'sohd',cellsalign: 'center', align: 'center', width: 150 },
              { text: 'Tỉnh ', datafield: 'tinh',cellsalign: 'center', align: 'center', width: 150 },
              { text: 'Hình thức sở hữu', datafield: 'htSohuu',cellsalign: 'center', align: 'center', minwidth: 150 },
              { text: 'Chủ hợp đồng', datafield: 'chuthehd',cellsalign: 'center', align: 'center', minwidth:  150},
              { text: 'Ngày bắt đầu', datafield: 'hdNgayhieuluc',cellsalign: 'center', align: 'center', minwidth:  150 },
              { text: 'Ngày kết thúc', datafield: 'hdNgayketthuc',cellsalign: 'center', align: 'center', width: 150 },
              { text: 'Ngày thanh toán', datafield: 'ngayThanhToan',cellsalign: 'center', align: 'center', width: 150 },
              { text: 'Đơn giá/Tháng', datafield: 'dongiathangNovat',cellsalign: 'center', align: 'center', width: 150 },
              { text: 'Số tiền/Năm ', datafield: 'sotienggNam',cellsalign: 'center', align: 'center', width: 150 }
              ]
        });

        var contextMenu = $('#Menu').jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: getTheme() });
        $('#jqxgrid').on('contextmenu', function () {
            return false;
        });
        $('#jqxgrid').on('rowclick', function (event) {
        	if (event.args.rightclick) {
        		$('#jqxgrid').jqxGrid('selectrow', event.args.rowindex);
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
    			window.location = 'formTTMB.htm';
    		}
    	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
    	   	  	var rowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
    	        var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
    	       // alert(row.id);
    	   		window.location = 'formTTMB.htm?id='+row.id;   
    	   	     
    	    }
    	    	if ($.trim($(args).text()) == '<fmt:message key="global.form.xoa"/>')  {
        	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
        	    	if (answer)
        	    	{
        				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        	    		var idList="";
        	    		var cond="";
        	    		if (selectedrowindexes != null) {
        	    			for (var i=0; i<selectedrowindexes.length; i++) {
        	    				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        	    				idList+=cond+row.id;
        	    				cond=",";
        	    			}
        	    			window.location = 'delete.htm?id='+idList;    
        	    		}
        			}
    	    } 
    			 var exportFileName =  "${exportFileName}";
    			if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    				 window.open('${pageContext.request.contextPath}/mat-bang/report/exportData_Export_TT_Mat_Bang.htm?maTram=' 
            + maTram + "&idHopdong=" + idHopdong + "&sohd=" + sohd + "&tinh="
             + tinh + "&huyen=" + huyen + "&htSohuu=" + htSohuu + "&dvSohuu=" + dvSohuu + "&chuthehd=" + chuthehd+ "&dongiathangNovat=" + dongiathangNovat + 
             "&hdNgayketthuc=" + hdNgayketthuc + "&ngaytinhtien=" + ngaytinhtien); 
    		    }
    	 });   
    });
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script>
var theme= getTheme();
//combobox tinh
var urlprovince = "${pageContext.request.contextPath}/ajax/getDSTinh.htm?region="+$("#region").val();
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
</script>
<script>


$(document).ready(function(){
	Calendar.setup({
	    inputField		:	"hdNgayketthuc",	// id of the input field
	    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
	    button			:   "choosehdNgayKetThuc",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
	
	Calendar.setup({
	    inputField		:	"ngaytinhtien",	// id of the input field
	    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
	    button			:   "chooseNgayTinhTien",   	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 

});
</script>
