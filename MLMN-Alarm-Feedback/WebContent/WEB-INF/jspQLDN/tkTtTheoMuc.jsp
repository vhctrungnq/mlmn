<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>${tittle}</title>
<content tag="heading">${tittle}</content>
<form:form commandName="filter" method="GET" action="list.htm">
<input type="hidden" id="type" name ="type" value="${type}"/>
	<table >
		
			<tr style="padding-right: 20px;">  
          		
          		<td style="padding-left: 5px;"><fmt:message key="TkTTTheoMuc.month"/></td>
				<td>
					<input type="text" id="month"  name="month" value="${month}" style="width: 100px;" />
				</td>
				<td style="padding-left: 5px;"><fmt:message key="TkTTTheoMuc.year"/></td>
				<td>
					<input type="text" id="year"  name="year" value="${year}" style="width: 100px;" />
				</td>
				<td style="padding-left: 5px;"><fmt:message key="PhanBoChiPhi.tinh"/></td>
				<td>
					<input type="text" id="tinh"  name="tinh" style="width: 200px;" />
				</td>
				<br/>
				<td style="padding-left: 5px;"><fmt:message key="TkTTTheoMuc.dm1"/></td>
				<td>
					<input type="text" id="dinhmuc1"  name="dinhmuc1" value="${dinhmuc1}" style="width: 200px;" />
				</td>
				<td style="padding-left: 5px;"><fmt:message key="TkTTTheoMuc.dm2"/></td>
				<td>
					<input type="text" id="dinhmuc2"  name="dinhmuc2" value="${dinhmuc2}" style="width: 200px;" />
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
		   		<li><fmt:message key="global.button.editSelected"/></li>
	            <li><fmt:message key="global.button.deleteMultiSelected"/></li>
	            <li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>




<script type="text/javascript">

$(document).ready(function(){
		
		var theme =  getTheme();
		$('#submit').jqxButton({ theme: theme }); 
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
		// Create a jqxMultile Input
	
	 // Input CELL
	 	
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