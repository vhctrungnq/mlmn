<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>${tittle}</title>
<content tag="heading">${tittle}</content>
<form:form commandName="filter" method="GET" action="list.htm">
<input type="hidden" id="type" name ="type" value="${type}"/>
	<table >
		
			<tr style="padding-right: 20px;">  
          		
          		<td>Từ ngày</td>
          		<td > 
					<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="15" maxlength="10" style="width:200px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				
				<td>Đến ngày</td>
				<td>
					<input type ="text"  value="${edate}" name="edate" id="edate" size="15" maxlength="10" style="width:200px">
		   			<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					
				</td>
				
				<td style="padding-left: 5px;"><fmt:message key="PhanBoChiPhi.tinh"/></td>
				<td>
					<input type="text" id="tinh"  name="tinh" style="width: 200px;" />
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


<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"sdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"edate",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    
	
</script>

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
    
    
    
    var exportFileName =  "<c:out value='${exportFileName}'/>";
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    	$("#gridReport").jqxGrid('exportdata', 'xls', exportFileName);
    }
});
  
</script> 