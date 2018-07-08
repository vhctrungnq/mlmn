<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>THỐNG KÊ CHI PHÍ XHH ĐÃ THANH TOÁN</title>
<content tag="heading">THỐNG KÊ CHI PHÍ XHH ĐÃ THANH TOÁN</content>
<form:form commandName="filter" method="GET" action="list.htm">
	<table >
		
			<tr> 
				
				
				<td style="padding-left: 5px;"><fmt:message key="QLMatBang.year"/></td>
				<td>
					<input type="text" id="year"  name="year" value="${year}" style="width: 100px;" />
				</td>
				
				<td style="padding-left: 5px;"><fmt:message key="QLMatBang.tinh"/></td>
				<td>
					<input type="text" id="tinh"  name="tinh" style="width: 150px;" />
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