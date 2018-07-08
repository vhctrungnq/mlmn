<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>THỐNG KÊ CHI TIẾT THEO ĐƠN VỊ XHH, VNPT</title>
<content tag="heading">THỐNG KÊ CHI TIẾT THEO ĐƠN VỊ XHH, VNPT</content>
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
		<td><b>1. Bảng thống kê XHH</b> 
		</td>
	</tr>
	<tr>
		<td style="width: 20px"><div style="float: right;" id="listColumn"></div></td>
	</tr>
</table>
<div id="gridReport1"></div>
<div id='menuReport1'>
            <ul>
		   		
	            <li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>

<table style="width:100%">
	<tr> 
		<td><b>2. Bảng thống kê VNPT</b> 
		</td>
	</tr>
	<tr>
		<td style="width: 20px"><div style="float: right;" id="listColumn"></div></td>
	</tr>
</table>
<div id="gridReport2"></div>
<div id='menuReport2'>
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
${gridReport1}
// handle context menu clicks.
$("#menuReport1").on('itemclick', function (event) {
    var args = event.args;
    
    
    var exportFileName1 =  "<c:out value='${exportFileName1}'/>";
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    	$("#gridReport1").jqxGrid('exportdata', 'xls', exportFileName1);
    }
});
  
</script>

<script type="text/javascript">
${gridReport2}
// handle context menu clicks.
$("#menuReport2").on('itemclick', function (event) {
    var args = event.args;
    
    
    var exportFileName2 =  "<c:out value='${exportFileName2}'/>";
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    	$("#gridReport2").jqxGrid('exportdata', 'xls', exportFileName2);
    }
});
  
</script>