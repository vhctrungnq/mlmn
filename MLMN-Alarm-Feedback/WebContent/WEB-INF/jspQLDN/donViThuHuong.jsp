<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
.content{
text-align: center;
}
</style>

<title>Đơn vị thụ hưởng</title>
<h3>Quản lí đơn vị thụ hưởng</h3>
<table style = "border:0; width:100%;cellspacing:0; cellpadding:0;class:form">	
	<tr>
		<td style = "align:left;">
			<form:form method = "get" action = "list.htm">
				<table> 
				 	<tr>
						<td> Tên đơn vị </td>
						<td> <input id = "tenDv" value = "${tenDv}" name = "tenDv"/> </td>
						<td style="padding-left: 30px;">Mã số thuế </td>
						<td> <input id = "maSoThue" value = "${maSoThue}" name = "maSoThue"/></td>
						<td style="padding-left: 30px;">Số tài khoản </td>
						<td> <input id = "soTaiKhoan" value = "${soTaiKhoan}" name = "soTaiKhoan"/></td>
				 	</tr>
				 	<tr>
				 		<td>Tên ngân hàng </td>
						<td> <input id = "tenNganHang" value = "${tenNganHang}" name = "tenNganHang"/></td>
						<td style="padding-left: 30px;">Ghi chú </td>
						<td> <input id = "ghiChu" value = "${ghiChu}" name = "ghiChu"/></td>
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
<<script type="text/javascript">
$('#addNew').click(function () {	
	window.location = 'formDVTH.htm';
	
});
</script>
<script type="text/javascript">
 	var tenDv = "<c:out value = '${tenDv}'/>";
 	var maSoThue = "<c:out value = '${maSoThue}'/>";
 	var soTaiKhoan = "<c:out value = '${soTaiKhoan}'/>";
 	var tenNganHang = "<c:out value = '${tenNganHang}'/>";
 	var ghiChu = "<c:out value = '${ghiChu}'/>";
    $(document).ready(function () {
        var url = "${pageContext.request.contextPath}/qldn/donvithuhuong/data.htm?tenDv=" 
            + tenDv + "&maSoThue=" + maSoThue + "&soTaiKhoan=" + soTaiKhoan + "&tenNganHang="
             + tenNganHang + "&ghiChu=" + ghiChu;
        // prepare the data
        var source =
        {
            datatype: "json",
            datafields: [
                { name: 'id', type: 'number' },
                { name: 'tenDv', type: 'string' },
                { name: 'maSoThue', type: 'string' },
                { name: 'soTaiKhoan', type: 'string' },
                { name: 'tenNganHang', type: 'string' },
                { name: 'diaChiNh', type: 'string' },
                { name: 'lienHe', type: 'string' },
                { name: 'ghiChu', type: 'string' },
                { name: 'createdBy', type: 'string' },
                { name: 'modifiedBy', type: 'string' },
                { name: 'createDate', type: 'date' },
                { name: 'modifiedDate', type: 'date' }
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
              { text: 'Tên', datafield: 'tenDv',cellsalign: 'center', align: 'center', width: 150 },
              { text: 'Mã số thuế', datafield: 'maSoThue',cellsalign: 'center', align: 'center', width: 200 },
              { text: 'Tài khoản', datafield: 'soTaiKhoan',cellsalign: 'center', align: 'center', width: 200 },
              { text: 'Ngân hàng', datafield: 'tenNganHang',cellsalign: 'center', align: 'center', width: 200 },
              { text: 'Địa chỉ ngân hàng', datafield: 'diaChiNh',cellsalign: 'center', align: 'center', minwidth: 200 },
              { text: 'Liên hệ', datafield: 'lienHe',cellsalign: 'center', align: 'center', minwidth:  175},
              { text: 'Ghi chú', datafield: 'ghiChu',cellsalign: 'center', align: 'center' }
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
    			window.location = 'formDVTH.htm';
    		}
    	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
    	   	  	var rowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
    	        var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
    	       // alert(row.id);
    	   		window.location = 'formDVTH.htm?id='+row.id;;   
    	   	     
    	    }
    	    /* if ($.trim($(args).text()) == '<fmt:message key="global.form.xoa"/>')  {
    	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
    	    	if (answer)
    	    	{
    				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
    	    		var idList="";
    	    		var cond="";
    	    		//alert(selectedrowindexes);
    	    		//var rowIndexList = selectedrowindexes.split(",");
    	    		if (selectedrowindexes != null) {
    	    			for (var i=0; i<selectedrowindexes.length; i++) {
    	    				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
    	    				idList+=cond+row.id;
    	    				cond=",";
    	    			}
//    	    			alert("<c:out value = '${caTruc}'/>");
    	    			window.location = 'delete.htm?id='+idList;    
    	    		}
    			} */
    	    	if ($.trim($(args).text()) == '<fmt:message key="global.form.xoa"/>')  {
        	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
        	    	if (answer)
        	    	{
        				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        	    		var idList="";
        	    		var cond="";
        	    		//alert(selectedrowindexes);
        	    		//var rowIndexList = selectedrowindexes.split(",");
        	    		if (selectedrowindexes != null) {
        	    			for (var i=0; i<selectedrowindexes.length; i++) {
        	    				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        	    				idList+=cond+row.id;
        	    				cond=",";
        	    			}
//        	    			alert("<c:out value = '${caTruc}'/>");
        	    			window.location = 'delete.htm?id='+idList;    
        	    		}
        			}
    	    } 
    			var exportFileName =  "${exportFileName}";
    			if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    		    	$("#jqxgrid").jqxGrid('exportdata', 'xls', exportFileName);
    		    }
    	 });  
    });
</script>
