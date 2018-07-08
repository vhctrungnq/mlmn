<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>${title}</title>
<content tag="heading">${title}</content> 	


<form:form commandName="filter" method="post" action="sitelist.htm">
	<table >
		
			<tr> 
				<td style="width:70px"><fmt:message key="monitorSite.sdate"/></td>
				<td style="width:130px">
						<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="17" maxlength="19" style="width:110px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				
				<td style="padding-left: 5px;width:30px"><fmt:message key="monitorSite.edate"/></td>
				<td style="width:130px">
					<input type ="text"  value="${edate}" name="edate" id="edate" size="17" maxlength="19" style="width:110px">
			   		<img alt="calendar" title="Click to choose the to date" id="chooseEDate" style="cursor: pointer; position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td style="padding-left: 5px;width:50px"><fmt:message key="monitorSite.network"/></td>
				<td>
					<div id='network'></div>
				</td>
				<td style="padding-left: 5px;"><fmt:message key="monitorSite.site"/></td>
				<td>
					<input type="text" id="site"  name="site" style="width: 200px;" />
				</td>
				<td style="padding-left: 5px;"><fmt:message key="monitorSite.content"/></td>
				<td>
					<input type="text" id="content"  name="content" style="width: 200px;" />
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
		<input class="button"  type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' onclick='window.location="siteform.htm"' />
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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
Calendar.setup({
    inputField		:	"sdate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseSDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"edate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseEDate",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 	
</script>
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
	 	${siteList}
	 	$("#site").jqxInput({ placeHolder: "Enter a site/cell", height: 20, width: 200, theme: theme,
	        source: function (query, response) {
	            var item = query.split(/,\s*/).pop();
	            // update the search query.
	            $("#site").jqxInput({ query: item });
	            response(siteList);
	        },
	        renderer: renderer
	    });
	 	var site =  "<c:out value='${site}'/>";
	 	//alert(cellid);
        if(site!="")
			$('#site').val(site);
     
        // Create a input alarmType
        $("#content").jqxInput({placeHolder: "Enter content", height: 20, width: 300, minLength: 0, maxLength: 200, theme: theme});
        var content =  "<c:out value='${content}'/>";
        if(content=="")
        	$('#content').val('');
        else
        	$('#content').val(content); 
        
	    //network
       var urlnetwork = "${pageContext.request.contextPath}/ajax/getNetworkList.htm";
       // prepare the data
       var sourcenetwork =
       {
           datatype: "json",
           datafields: [
               { name: 'value' },
               { name: 'name' }
           ],
           url: urlnetwork,
           async: false
       };
       var dataAdapternetwork = new $.jqx.dataAdapter(sourcenetwork);
       $("#network").jqxComboBox({ source: dataAdapternetwork, displayMember: "value", valueMember: "name",width: 80,height: 20,itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true  });
        var network =  "<c:out value='${network}'/>";
	       if(network!="") {
			$('#network').val(network);
	       }
	       else
    	   {
    	   	$('#network').val('ALL');
    	   }
	       $("#network").change(function(){
	    	   var username = "<c:out value='${username}'/>";
	    	   var network = $('#network').val();
	    		if (network=='ALL')
	    		{
	    			network='';
	    		}
	    		
	    	   $.getJSON("${pageContext.request.contextPath}/ajax/getSiteByNetwork.htm",{network:network,bscid: "", district:"",username:""}, function(j){
	    		   var siteList=[];
	    		   siteList =j;
	    		   $("#site").jqxInput({source: siteList});        
	    	   });
	    	});		
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
    	window.location = 'siteform.htm?id='+row.id;   
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
    			window.location = 'sitedelete.htm?idList='+idList;
    		}
			}
    }
    var exportFileName =  "<c:out value='${exportFileName}'/>";
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    	$("#gridReport").jqxGrid('exportdata', 'xls', exportFileName);
    }
});
  
</script>

