<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
.form-control{
	width: 174px;
	text-align: center;
}
</style>
<title>Tổng hợp thanh toán theo tháng</title>
<!-- DoaiNH viết Tổng hợp thanh toán theo tháng ngày 04-10-2017 -->
<h3>Tổng hợp thanh toán theo tháng</h3>
<table style = "border:0; width:100%;cellspacing:0; cellpadding:0;class:form">	
	<tr>
		<td style = "align:left;">
			<form:form method = "get" action = "tong-hop-thanh-toan.htm">
				<table> 
				 	<tr>
						<td > Khu vực </td>
						<td > 
							<div style="width: 174px" id = "region" name = "region" ></div>
						</td>
						<td style="padding-left: 30px;">Tỉnh </td>
						<td> 
							<div style="width: 174px" id = "tinh" name = "tinh"></div>
						</td>
						<td style="padding-left: 30px;">Chu kỳ thanh toán </td>
						<td> 
							<div style="width: 174px" id = "chuKyTT" name ="chuKyTT"></div>
						</td>
				 	</tr>
				 	<tr>
						<td > Tháng / Quý </td>
						<td > 
							<input id = "thangQuyTt" value = "${thangQuyTt}" name = "thangQuyTt"/>
						</td>
						<%-- <%} %> --%>
						<td style="padding-left: 30px;">Năm </td>
						<td> 
							<input id = "nam" value = "${nam}" name = "nam"/>
						</td>
						<td style="padding-left: 30px; width: 172">Type </td>
						<td> 
							<div id = "type" name = "type"></div>
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
				<div id="jqxWidget" style="margin-top:5px;">
			 	 
				<div class = "content" id="jqxgrid"></div>
				<div id="Menu">
					<ul>
						<li><fmt:message key="global.button.exportExcel" /></li>
					</ul>
				</div>
			</div>
		</td>
	</tr>


</table>

<script type="text/javascript">
 	var region = "<c:out value = '${region}'/>";
 	var tinh = "<c:out value = '${tinh}'/>";
 	var chuKyTT = "<c:out value = '${chuKyTT}'/>";
 	var thangQuyTt = "<c:out value = '${thangQuyTt}'/>";
 	var nam = "<c:out value = '${nam}'/>";
 	var type = "<c:out value = '${type}'/>";
 	
 	
    $(document).ready(function () {
        var url = "${pageContext.request.contextPath}/dien/report/dataTongHopThanhToan.htm?region=" 
            + region + "&tinh=" + tinh + "&chuKyTT=" + chuKyTT + "&thangQuyTt=" + thangQuyTt
             + "&nam=" + nam + "&type=" + type ;
        // prepare the data
        var source =
        {
            datatype: "json",
            datafields: [
                { name: 'tinh', type: 'string' },
                { name: 'tongSoTram', type: 'string' },
                { name: 'unt_soLuong', type: 'string' },
                { name: 'unt_tien', type: 'string' },
                { name: 'daiTT_soLuong', type: 'string' },
                { name: 'daiTT_tien', type: 'string' },
                { name: 'tongTT_soLuong', type: 'string' },
                { name: 'tongTT_tien', type: 'string' },
                { name: 'tienTBTram', type: 'string' },
                { name: 'soLuong_chuaTT', type: 'string' },

                { name: 'tongTramCanTTTW', type: 'string' },
                { name: 'tongTramDaTTTW', type: 'string' },
                { name: 'tongTienDaTTTW', type: 'string' },
                { name: 'daXongHS', type: 'string' },
                { name: 'daHoanTatHS', type: 'string' },
                { name: 'chuaXongHS', type: 'string' },
                { name: 'chuaHoanTatHS', type: 'string' },
            ],
           
            url: url
        };
        var dataAdapter = new $.jqx.dataAdapter(source);
       /*  if(!type){ */
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
	                    text: 'No.',cellsalign: 'center', align: 'center', sortable: false, filterable: false, editable: false,
	                    groupable: false, draggable: false, resizable: false,
	                    datafield: '', columntype: 'number', width: 50,
	                    cellsrenderer: function (row, column, value) {
	                        return "<div style='margin:4px;'>" + (value + 1) + "</div>";
	                    }
	                },
	              { text: 'Tỉnh', datafield: 'tinh', cellsalign: 'center', align: 'center',width: 150 },
	              { text: 'Tổng số trạm', datafield: 'tongSoTram',cellsalign: 'center', align: 'center', width: 150 },
	              { text: 'Số lượng',columngroup: 'uyNhiemThu', datafield: 'unt_soLuong',cellsalign: 'center', align: 'center', width: 150 },
	              { text: 'Tiền',columngroup: 'uyNhiemThu', datafield: 'unt_tien',cellsalign: 'center', align: 'center', width: 150 },
	              { text: 'Số lượng',columngroup: 'daiThanToan', datafield: 'daiTT_soLuong',cellsalign: 'center', align: 'center', width: 150 },
	              { text: 'Tiền',columngroup: 'daiThanToan', datafield: 'daiTT_tien',cellsalign: 'center', align: 'center', width: 150 },
	              { text: 'Số lượng',columngroup: 'tongThanhToan', datafield: 'tongTT_soLuong',cellsalign: 'center', align: 'center', width: 150 },
	              { text: 'Tiền',columngroup: 'tongThanhToan', datafield: 'tongTT_tien',cellsalign: 'center', align: 'center', width: 150 },
	              { text: 'Tiền trung bình/ trạm', datafield: 'tienTBTram',cellsalign: 'center', align: 'center', minwidth: 150 },
	              { text: 'Số lượng trạm chưa thanh toán', datafield: 'soLuong_chuaTT',cellsalign: 'center', align: 'center', minwidth: 200 }
		          ],
		          columngroups: [
		              { text: 'Ủy nhiệm thu', align: 'center', name: 'uyNhiemThu' },
		              { text: 'Đài thanh toán', align: 'center', name: 'daiThanToan' },
		              { text: 'Tổng thanh toán', align: 'center', name: 'tongThanhToan' }
		          ]
	       	 });
	        
       }else if(type == "TW"){
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
        		                    text: 'No.',cellsalign: 'center', align: 'center', sortable: false, filterable: false, editable: false,
        		                    groupable: false, draggable: false, resizable: false,
        		                    datafield: '', columntype: 'number', width: 50,
        		                    cellsrenderer: function (row, column, value) {
        		                        return "<div style='margin:4px;'>" + (value + 1) + "</div>";
        		                    }
        		                },
        		              { text: 'Tỉnh', datafield: 'tinh', cellsalign: 'center', align: 'center',width: 170 },
        		              { text: 'Tổng trạm cần TTTW', datafield: 'tongTramCanTTTW',cellsalign: 'center', align: 'center', width: 170 },
        		              { text: 'Tổng trạm đã TTTW', datafield: 'tongTramDaTTTW',cellsalign: 'center', align: 'center', width: 170 },
        		              { text: 'Tổng tiền đã TTTW', datafield: 'tongTienDaTTTW',cellsalign: 'center', align: 'center', width: 170 },
        		              { text: 'Đã xong hồ sơ', datafield: 'daXongHS',cellsalign: 'center', align: 'center', width: 170 },
        		              { text: 'Đã hoàn tất hồ sơ', datafield: 'daHoanTatHS',cellsalign: 'center', align: 'center', width: 170 },
        		              { text: 'Chưa xong hồ sơ', datafield: 'chuaXongHS',cellsalign: 'center', align: 'center', width: 170 },
        		              { text: 'Chưa hoàn tất hồ sơ', datafield: 'chuaHoanTatHS',cellsalign: 'center', align: 'center', width: 170 }
        		          ]
        		        });
        	
        }
        var contextMenu = $("#Menu").jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: getTheme() });
        $("#jqxgrid").on('contextmenu', function () {
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
    			var exportFileName =  "${exportFileName}";
    			if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    				 window.open('${pageContext.request.contextPath}/dien/report/exportData_Export_TT_Dien.htm?region='
    				            + region + "&tinh=" + tinh + "&chuKyTT=" + chuKyTT + "&thangQuyTt=" + thangQuyTt
    				             + "&nam=" + nam + "&type=" + type);
    			     }
    	 });     
    });
    
</script>
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
   $("#tinh").jqxDropDownList({source: dataAdapterprovince, displayMember: "province" , valueMember: "province",checkboxes: true, width: 172,  theme: theme,enableHover: true });           

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
//combobox region
var urlRegion = "${pageContext.request.contextPath}/ajax/getRegionList.htm";
   var sourceRegion =
   {
      datatype: "json",
      url: urlRegion,
      datafields: [
                    { name: 'name'},
                    { name: 'value'}
                ],
       async: false
  };
   var dataAdapterregion = new $.jqx.dataAdapter(sourceRegion);
   $("#region").jqxDropDownList({source: dataAdapterregion, displayMember: "name",width: '174px' ,valueMember: "name",checkboxes: true, width: 172, autoDropDownHeight: true, theme: theme,enableHover: true });           

   var cbregion = '<c:out value="${region}"/>';
  // alert(dept);
if(cbregion=="")
 $('#region').val('Choose');
else
{
 var regionVar = cbregion.split(",");
 if (regionVar != null) {
  for (var i=0; i<regionVar.length; i++) {
   $("#region").jqxDropDownList('checkItem', regionVar[i] ); 
  }
 }
}
var urltgttTq = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=TG_THANH_TOAN";
// prepare the data
var sourcetgttTq =
{
    datatype: "json",
    datafields: [
        { name: 'name'}
    ],
    url: urltgttTq,
    async: false
};
var dataAdaptertgttTq = new $.jqx.dataAdapter(sourcetgttTq);
// Create a jqxComboBox
$("#chuKyTT").jqxComboBox({ source: dataAdaptertgttTq, displayMember: "name", valueMember: "name", width: 172, height: 20, theme: theme,autoOpen: true  });
var chuKyTT =  "<c:out value='${tgttTq}'/>";
if(chuKyTT=="")
$('#chuKyTT').val('ALL');
else
$('#chuKyTT').val(chuKyTT);

var urltype = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=TYPE";
//prepare the data
var sourcetype =
{
 datatype: "json",
 datafields: [
     { name: 'name'},
     { name: 'value'}
 ],
 url: urltype,
 async: false
};
var dataAdaptertype = new $.jqx.dataAdapter(sourcetype);
//Create a jqxComboBox
$("#type").jqxComboBox({ source: dataAdaptertype, displayMember: "name", valueMember: "value", width: 172, height: 20, theme: theme,autoOpen: true  });
var type =  "<c:out value='${type}'/>";
if(type=="")
$('#type').val('ALL');
else
$('#type').val(type);
</script>