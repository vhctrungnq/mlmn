<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>DANH SÁCH THÔNG TIN CHI PHÍ KẾ HOẠCH PHÂN BỔ THEO TỈNH</title>
<content tag="heading">DANH SÁCH THÔNG TIN CHI PHÍ KẾ HOẠCH PHÂN BỔ THEO TỈNH</content>
<form:form commandName="filter" method="GET" action="list.htm">
	<table >
		
			<tr> 
				
				
				<td style="padding-left: 5px;"><fmt:message key="PhanBoChiPhi.year"/></td>
				<td>
					<input type="text" id="year"  name="year" style="width: 200px;" />
				</td>
				<td style="padding-left: 5px;"><fmt:message key="PhanBoChiPhi.tinh"/></td>
				<td>
					<input type="text" id="tinh"  name="tinh" style="width: 200px;" />
				</td>
				<td style="padding-left: 5px;"><fmt:message key="PhanBoChiPhi.loaiChiPhi"/></td>
				<td>
					<div id='loaiChiPhi' name='loaiChiPhi'></div>
				</td>
				<td style="padding-left: 5px;">
					<input class="button" type="submit" id="submit" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
		</table>
	</form:form>

<table style="width:100%">
	<tr>
		<td align="right" class="ftsize12">
		<input class="button"  type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' onclick='window.location="form.htm"' />
		</td>
		<td style="width: 20px"><div style="float: right;" id="listColumn"></div></td>
	</tr>
</table>
<div id="gridReport"></div>
<div id='menuReport'>
            <ul>
		   		<li><fmt:message key="global.button.editSelected"/></li>
	            <li><fmt:message key="global.button.deleteMultiSelected"/></li>
	            <li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>



<script type="text/javascript">
$(document).ready(function(){
		// Khai bao sdate, edate
		var theme =  getTheme();
		$('#submit').jqxButton({ theme: theme });
		$('#addNew').jqxButton({ theme: theme });
		// Create a jqxMultile Input
	var renderer = function (itemValue, inputValue) {
        var terms = inputValue.split(/,\s*/);
        var value = inputValue;
     
         if (inputValue.indexOf(itemValue) < 0)
	    	{
      
        	 // remove the current input
             terms.pop();
             // add the selected item
	    	 terms.push(itemValue);
	         // add placeholder to get the comma-and-space at the end
	         terms.push("");
	         value = terms.join(",");
	    	}
       
        return value;
    };
	 // Input CELL
	 	
	 	
     
        // year
        $("#year").jqxInput({placeHolder: "Enter year", height: 20, width: 300, minLength: 0, maxLength: 200, theme: theme});
        var year =  "<c:out value='${year}'/>";
        if(year!=null)
        	
        	$('#year').val(year); 
      //Input province
        ${provinceList}
        $("#tinh").jqxInput({ height: 20, width: '160px', theme: theme,
            source: function (query, response) {
                var item = query.split(/,\s*/).pop();
                // update the search query.
                $("#tinh").jqxInput({ query: item });
                response(provinceList);
            },
            renderer: renderer
        });
        var tinh =  "<c:out value='${tinh}'/>";
        if(tinh!="")
      $('#tinh').val(tinh);
        
        //loai chi phi
        var urlstatus = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=LOAI_CHI_PHI";
        // prepare the data
        var sourcestatus =
        {
            datatype: "json",
            datafields: [
                { name: 'name'},
                { name: 'value'}
            ],
            url: urlstatus,
            async: false
        };
        var dataAdapterstatus = new $.jqx.dataAdapter(sourcestatus);
        // Create a jqxComboBox
        $("#loaiChiPhi").jqxComboBox({ source: dataAdapterstatus, displayMember: "value", valueMember: "name", width: '250px',height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
        var loaiChiPhi =  "<c:out value='${loaiChiPhi}'/>";
        if(loaiChiPhi=="")
   $('#loaiChiPhi').val('ALL');
  else
   $('#loaiChiPhi').val(loaiChiPhi);
});		 
</script>
<script type="text/javascript">
${gridReport}
// handle context menu clicks.
$("#menuReport").on('itemclick', function (event) {
    var args = event.args;
    
    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
   	  	var rowindex = $("#gridReport").jqxGrid('getselectedrowindex');
        var row = $("#gridReport").jqxGrid('getrowdata', rowindex);
       // alert(row.id);
    	window.location = 'form.htm?id='+row.id;   
    }
    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteMultiSelected"/>')  {
    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
    	if (answer)
    	{
			var selectedrowindexes = $('#gridReport').jqxGrid('getselectedrowindexes'); 
    		var idList="";
    		var cond="";
    		//alert(selectedrowindexes);
    		//var rowIndexList = selectedrowindexes.split(",");
    		if (selectedrowindexes != null) {
    			for (var i=0; i<selectedrowindexes.length; i++) {
    				var row = $("#gridReport").jqxGrid('getrowdata', selectedrowindexes[i]);
    				idList+=cond+row.id;
    				cond=",";
    			}
    			// alert(idList);
    			window.location = 'delete.htm?idList='+idList;
    		}
			}
    }
    var exportFileName =  "<c:out value='${exportFileName}'/>";
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    	$("#gridReport").jqxGrid('exportdata', 'xls', exportFileName);
    }
});
  
</script> 	