<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
.form-control{
	width: 174px;
	text-align: center;
}
</style>
<title>Theo dõi thanh toán nhiều kỳ theo trạm</title>
<!-- DoaiNH viết Theo dõi thanh toán nhiều kỳ theo trạm ngày 29-09-2017 -->
<h3>Theo dõi thanh toán nhiều kỳ theo trạm</h3>
<table style = "border:0; width:100%;cellspacing:0; cellpadding:0;class:form">	
	<tr>
		<td style = "align:left;">
			<form:form method = "get" action = "thanh-toan-nhieu-ky.htm">
				<table> 
				 	<tr>
						<td > Tỉnh </td>
						<td > 
							<div style="width: 174px" id = "tinh" name = "tinh" ></div>
						</td>
						<td style="padding-left: 30px;">Loại trạm </td>
						<td> 
							<div style="width: 174px" id = "loaitram" name = "loaitram" ></div>
						</td>
						<td style="padding-left: 30px;">Nguồn cung cấp điện </td>
						<td> 
							<div style="width: 174px" id = "nguonccd" name = "nguonccd" ></div>
						</td>
						<td style="padding-left: 30px;">Hình thức thanh toán </td>
						<td> 
							<div style="width: 174px" id = "httt" name = "httt" ></div>
						</td>
				 	</tr>
				 	<tr>
				 		
						<td >Loại đơn giá </td>
						<td> 
							<div style="width: 174px" id = "dgLoai" name = "dgLoai" ></div>
						</td>
						<td style="padding-left: 30px;">Người thanh toán</td>
						<td> <input id = "nguoittdien" value = "${nguoittdien}" name = "nguoittdien"/></td>
						<td style="padding-left: 30px;">Mã khách hàng</td>
						<td> <input id = "makh" value = "${makh}" name = "makh"/></td>
						<td style="padding-left: 30px;">Mã trạm</td>
						<td> <input id = "idTram" value = "${idTram}" name = "idTram"/></td>
					</tr>
					<tr>
						
						<td > Từ tháng</td>
						<td> <input class ="thangnam" id = "thangquy" value = "${thangquy}" name = "thangquy"/> 
							
						</td>
						<td style="padding-left: 30px;">Năm</td>
						<td> <input class ="thangnam" id = "nam" value = "${nam}" name = "nam"/> 
						</td>
						<td style="padding-left: 30px;">Đến tháng</td>
						<td> <input class ="thangnam" id = "thangquy_t" value = "${thangquy_t}" name = "thangquy_t"/> 
						</td>
						<td style="padding-left: 30px;">Năm</td>
						<td><input class ="thangnam" id = "nam_t" value = "${nam_t}" name = "nam_t"/>
						</td>
				 	</tr>
				 	<tr>
						<td>
							
				        	<table style = "border:0; cellspacing:1; cellpadding:0; width:100%;">
								<tr>
									<td align="right">
										<input class="button" type="submit" id="submit" value="Tìm kiếm" />
									</td>
								</tr>	
							</table>
						    <br> 
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
 	var tinh = "<c:out value = '${tinh}'/>";
 	var loaitram = "<c:out value = '${loaitram}'/>";
 	var nguonccd = "<c:out value = '${nguonccd}'/>";
 	var httt = "<c:out value = '${httt}'/>";
 	var dgLoai = "<c:out value = '${dgLoai}'/>";
 	var nguoittdien = "<c:out value = '${nguoittdien}'/>";
 	var makh = "<c:out value = '${makh}'/>";
 	var idTram = "<c:out value = '${idTram}'/>";
 	var thangquy = "<c:out value = '${thangquy}'/>";
 	var nam = "<c:out value = '${nam}'/>";
 	var thangquy_t = "<c:out value = '${thangquy_t}'/>";
 	var nam_t = "<c:out value = '${nam_t}'/>";
 	
    $(document).ready(function () {
        var url = "${pageContext.request.contextPath}/dien/report/dataTTNhieuKy.htm?tinh=" 
            + tinh + "&loaitram=" + loaitram + "&nguonccd=" + nguonccd + "&httt="
             + httt + "&dgLoai=" + dgLoai+ "&nguoittdien=" + nguoittdien+ "&makh=" + makh+ "&idTram=" + idTram
             + "&thangquy=" + thangquy+ "&nam=" + nam+ "&thangquy_t=" + thangquy_t+ "&nam_t=" + nam_t;
        // prepare the data
        var source =
        {
            datatype: "json",
            datafields: [
                { name: 'idTram', type: 'string' },
                { name: 'makh', type: 'string' },
                { name: 'ngayps', type: 'date' },
                { name: 'thangpsdien', type: 'date' },
                { name: 'loaitram', type: 'string' },
                { name: 'dnttDk', type: 'string' },
                { name: 'giaiphaptietkiem', type: 'string' },
                { name: 'tgThuchien', type: 'date' },
                { name: 'dienNangTieuThu', type: 'string' },
                { name: 'tien', type: 'string' },
                { name: 'donGia', type: 'string' }	
            ],
            id: 'id',
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
              { text: 'Mã trạm', datafield: 'idTram', cellsalign: 'center', align: 'center',width: 150 },
              { text: 'Mã khách hàng', datafield: 'makh',cellsalign: 'center', align: 'center', width: 200 },
              { text: 'Ngày phát sinh', datafield: 'ngayps',cellsalign: 'center', align: 'center', width: 200 },
              { text: 'Thời gian phát sinh điện', datafield: 'thangpsdien',cellsalign: 'center', align: 'center', width: 200 },
              { text: 'Loại trạm', datafield: 'loaitram', cellsalign: 'center', align: 'center', minwidth: 200 },
              //{ text: 'Cấu hình', datafield: '', minwidth:  175},
              { text: 'Đơn giá trung bình', datafield: 'donGia',cellsalign: 'center', align: 'center',minwidth: 200 },
              { text: 'Định mức', datafield:'dnttDk',cellsalign: 'center', align: 'center', minwidth: 200 },
              { text: 'Giải pháp tiết kiệm', columngroup: 'giaiPhap',datafield: 'giaiphaptietkiem',cellsalign: 'center', align: 'center', minwidth: 200 },
              { text: 'Ngày thực hiện giải pháp',columngroup: 'giaiPhap', datafield: 'tgThuchien',cellsalign: 'center', align: 'center', minwidth: 200 },
              { text: 'Điện năng tiêu thụ trung bình tháng', columngroup: 'trungBinh', datafield: 'dienNangTieuThu',cellsalign: 'center', align: 'center', minwidth: 280 },
              { text: 'Tiền thanh toán trung bình tháng',columngroup: 'trungBinh',  datafield: 'tien',cellsalign: 'center', align: 'center', minwidth: 280 },
              { text: 'Ghi chú', datafield: 'ghichu',cellsalign: 'center', align: 'center', minwidth: 200 }
          ],
          columngroups: [
              { text: 'Giải pháp tiết kiệm', align: 'center', name: 'giaiPhap' },
              { text: 'Trung bình tháng', align: 'center', name: 'trungBinh' }
          ]
        });

        var contextMenu = $("#Menu").jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: getTheme() });
        $("#jqxgrid").on('contextmenu', function () {
            return false;
        });
        $("#jqxgrid").on('rowclick', function (event) {
        	if (event.args.rightclick) {
        		$("#jqxgrid").jqxGrid('selectrow', event.args.rowindex);
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
    				 window.open('${pageContext.request.contextPath}/dien/report/exportData_Export_ThongTin_Tram.htm?tinh='
    			            + tinh + "&loaitram=" + loaitram + "&nguonccd=" + nguonccd + "&httt="
    			             + httt + "&dgLoai=" + dgLoai+ "&nguoittdien=" + nguoittdien+ "&makh=" + makh+ "&idTram=" + idTram
    			             + "&thangquy=" + thangquy+ "&nam=" + nam+ "&thangquy_t=" + thangquy_t+ "&nam_t=" + nam_t);
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
   $("#tinh").jqxDropDownList({source: dataAdapterprovince, displayMember: "province", valueMember: "province",checkboxes: true, width: 172, autoDropDownHeight: true, theme: theme,enableHover: true });           

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
      var urlloaitram = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=LOAI_TRAM";
      // prepare the data
      var sourceloaitram =
      {
          datatype: "json",
          datafields: [
              { name: 'value' },
              { name: 'name' }
          ],
          url: urlloaitram,
          async: false
      };
      var dataAdapterloaitram = new $.jqx.dataAdapter(sourceloaitram);
      // Create a jqxComboBox
      $("#loaitram").jqxComboBox({ source: dataAdapterloaitram, displayMember: "value", valueMember: "name", width: 172,height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
      var loaitram =  "<c:out value='${loaitram}'/>";
      if(loaitram=="")
 $('#loaitram').val('ALL');
else
 $('#loaitram').val(loaitram);
//Create a jqxComboBox nguonccd
var urlnguonccd = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=NGUON_CUNG_CAP_DIEN";
      // prepare the data
      var sourcenguonccd =
      {
          datatype: "json",
          datafields: [
              { name: 'name'}
          ],
          url: urlnguonccd,
          async: false
      };
      var dataAdapternguonccd = new $.jqx.dataAdapter(sourcenguonccd);
      // Create a jqxComboBox
      $("#nguonccd").jqxComboBox({ source: dataAdapternguonccd, displayMember: "name", valueMember: "name", width: 172, height: 20, theme: theme,autoOpen: true  });
      var nguonccd =  "<c:out value='${nguonccd}'/>";
      if(nguonccd=="")
 $('#nguonccd').val('ALL');
else
 $('#nguonccd').val(nguonccd);

      // Create a jqxComboBox httt
var urlhttt = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=HINH_THUC_THANH_TOAN";
      // prepare the data
      var sourcehttt =
      {
          datatype: "json",
          datafields: [
              { name: 'name'}
          ],
          url: urlhttt,
          async: false
      };
      var dataAdapterhttt = new $.jqx.dataAdapter(sourcehttt);
      // Create a jqxComboBox
      $("#httt").jqxComboBox({ source: dataAdapterhttt, displayMember: "name", valueMember: "name", width: 172, height: 20, theme: theme,autoOpen: true  });
      var httt =  "<c:out value='${httt}'/>";
      if(httt=="")
 $('#httt').val('ALL');
else
 $('#httt').val(httt);
// Create a jqxComboBox dgLoai
var urldgLoai = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=DON_GIA_LOAI";
      // prepare the data
      var sourcedgLoai =
      {
          datatype: "json",
          datafields: [
              { name: 'name'},
              { name: 'value'}
          ],
          url: urldgLoai,
          async: false
      };
      var dataAdapterdgLoai = new $.jqx.dataAdapter(sourcedgLoai);
      // Create a jqxComboBox
      $("#dgLoai").jqxComboBox({ source: dataAdapterdgLoai, displayMember: "value", valueMember: "value", width: 172, height: 20, theme: theme,autoOpen: true  });
      var dgLoai =  "<c:out value='${dgLoai}'/>";
      if(dgLoai=="")
 $('#dgLoai').val('ALL');
else
 $('#dgLoai').val(dgLoai);
</script>