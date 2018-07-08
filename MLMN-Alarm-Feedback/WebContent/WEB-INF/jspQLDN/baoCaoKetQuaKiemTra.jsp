<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>KẾT QUẢ ĐÀI VIỄN THÔNG KIỂM TRA </title>
<content tag="heading">KẾT QUẢ ĐÀI VIỄN THÔNG KIỂM TRA </content>
<form:form commandName="filter" method="GET" action="list.htm">

	<table >
		
			<tr style="padding-right: 20px;">  
          		
          		
				<td style="padding-left: 5px;"><fmt:message key="BaoCaoKetQua.month"/></td>
				<td>
					<input type="text" id="month"  name="month" value="${month}" style="width: 100px;" />
				</td>
				
				<td style="padding-left: 5px;"><fmt:message key="BaoCaoKetQua.year"/></td>
				<td>
					<input type="text" id="year"  name="year" value="${year}" style="width: 100px;" />
				</td>
				
				<td style="padding-left: 5px;"><fmt:message key="BaoCaoKetQua.nhom"/></td>
				
				<td>
					<input type="text" id="nhom"  name="nhom" value="${nhom}" style="width: 200px;" />
				</td>
				
				<td style="padding-left: 5px;"><fmt:message key="qldnTramTTDien.nguong"/></td>
				<td style="width:150px;">
					<div id="nguong"  name="nguong" style = "width: 100%"></div>
				</td>
				
				<td style="padding-left: 5px;">
					<input class="button" type="submit" id="submit" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
		</table>
	</form:form>

<table style="width:100%">
	<tr>
		<td style="width: 20px"><div style="float: right;" id="listColumn"></div></td>
	</tr>
</table>
<div id="gridReport"></div>
<div id='menuReport'>
            <ul>
		   		
	            <li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>

<script type="text/javascript">

$(document).ready(function(){
		
		var theme =  getTheme();
		$('#submit').jqxButton({ theme: theme }); 
		//Create a jqxComboBox vendor
		var urlnguong = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=QLDN_NGUONG_BC_DAI_VT";
		// prepare the data
		var sourcenguong =
		{
		    datatype: "json",
		    datafields: [
		        { name: 'value' },
		        { name: 'name' }
		    ],
		    url: urlnguong,
		    async: false
		};
		var dataAdapternguong = new $.jqx.dataAdapter(sourcenguong);
		// Create a jqxComboBox
		$("#nguong").jqxComboBox({ source: dataAdapternguong, displayMember: "value", valueMember: "name", width: '150px',height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
		var nguong =  "<c:out value='${nguong}'/>";
		if(nguong=="")
			$("#nguong").val("0");
		else
			$("#nguong").val(nguong);
		
       
}); 	
     
        
      
</script>
<script type="text/javascript">
${gridReport}
// handle context menu clicks.
$("#menuReport").on('itemclick', function (event) {
    var args = event.args;
    var exportFileName =  "<c:out value='${exportFileName}'/>";
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    	$("#gridReport").jqxGrid('exportdata', 'xls', exportFileName);
    }
});

</script> 