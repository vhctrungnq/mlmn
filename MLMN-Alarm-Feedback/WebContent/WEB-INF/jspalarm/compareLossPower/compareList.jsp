
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- <meta http-equiv="Refresh" content="20; URL="> -->
<style type="text/css">   
 	/* #doublescroll { overflow: auto; overflow-y: hidden; }  
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; } */
    
     #dvtTeamProcess {
     visibility: visible !important;
    }
   
    #acLow {
     visibility: visible !important;
    }
    
     #bscid_chzn {

		width: 220px !important;
	}
    .ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	* we use height instead, but this forces the menu to always be this tall
	*/
	* html .ui-autocomplete {
		height: 200px;
	}
</style>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div>
<form:form commandName="filter" method="post" action="compareList.htm?type=${type}">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
<input id="strWhere" name="strWhere" value="" type="hidden"/>
	<input id="sortfield" name="sortfield" value="" type="hidden"/>
	<input id="sortorder" name="sortorder" value="" type="hidden"/>
	<table>
		<tr>
			<td style="width: 100px;"><fmt:message key="comparePower.day"/></td>
			<td  style="width: 200px;">
				<input type ="text"  value="${startTime}" name="startTime" id="startTime" size="15" maxlength="10" style="width:70px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			-
				<input type ="text"  value="${endTime}" name="endTime" id="endTime" size="15" maxlength="10" style="width:70px">
	   			<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				
			</td>
			<td  style="padding-left: 5px;width:100px;"><fmt:message key="comparePower.dept"/></td>
           	<td><div id='dept'></div></td>
           	<td  style="padding-left: 5px;width:80px;"><fmt:message key="comparePower.team"/></td>
           	<td><input type ="text"  value="${teamProcess}" name="teamProcess" id="teamProcess" size="17" width="80px"></td>
           
			<td style="padding-left: 5px;width: 100px;"><fmt:message key="comparePower.province"/></td>
			<td>
				 <input type="text" id="province" name="province" style="width: 100px;"/>
			</td>
			
			
		</tr>
		<tr>	
			<td ><fmt:message key="comparePower.district"/></td>
			<td >
				 <input type="text"  value="${district}"  id="district" name="district" style="width: 100px;"/>
			</td>
			<td style="padding-left: 5px;"><fmt:message key="comparePower.bscid"/></td>
			<td>
				 <input type="text" value="${bscid}"  id="bscid" name="bscid" style="width: 200px;"/>
			</td>
			<td style="padding-left: 5px;"><fmt:message key="comparePower.cellid"/></td>
			<td >
				<input type ="text"  value="${cellid}" name="cellid" id="cellid" size="17" width="80px">
			</td>
			<td style="padding-left: 5px;"><fmt:message key="comparePower.siteName"/></td>
			<td>
			    <input type ="text" value="${siteName}" name="siteName" id="siteName" width="80px" size="25">
			</td>
			<td><input class="button" type="submit" id="btFilter" name="btFilter" value="<fmt:message key="button.search"/>" /></td>
			
		</tr>
		
	</table>
</form:form>
</div>
<div class="clear"></div>	
<table style="width:100%">
	<tr>
		<td align="right" class="ftsize12">
			<c:if test="${checkCaTruc==true}">
				<input class="button"  type="button" value="<fmt:message key="global.button.import"/>" id='import' onclick='window.location="compareUpload.htm?type=${type}"' />
	   		</c:if>
		</td>
		<td style="width: 20px"> <div style="float: right;" id="jqxlistbox"></div></td>
	
	</tr>
</table>
<c:if test="${type=='all'}">
	<div><a>DANH SÁCH DỮ LIỆU MẤT ĐIỆN KHỚP THEO THỜI GIAN MẤT AC</a></div>
</c:if>
<div id="jqxgrid"></div>
<div id='Menu'>
    <ul>
		<li><fmt:message key="global.button.editSelected"/></li>
        <li><fmt:message key="global.button.deleteMultiSelected"/></li>
   		<li><fmt:message key="global.button.exportExcel"/></li>
    </ul>
  </div>
<c:if test="${type=='all'}">
<div class="clear"></div>
<br>
<div><a>DANH SÁCH DỮ LIỆU MẤT ĐIỆN KHÔNG KHỚP THEO THỜI GIAN MẤT AC</a></div>
<div id="jqxgridUnCompare"></div>
<div id='MenuUnCompare'>
    <ul>
		<li><fmt:message key="global.button.editSelected"/></li>
        <li><fmt:message key="global.button.deleteMultiSelected"/></li>
   		<li><fmt:message key="global.button.exportExcel"/></li>
    </ul>
  </div>

<div class="clear"></div>
 </c:if>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<!-- 
<script type="text/javascript"> $(".chzn-select").chosen(); $(".chzn-select-deselect").chosen({allow_single_deselect:true}); </script>

 -->
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"startTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"endTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    
	
</script>
<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("startTime");
	  mytext.focus();
	};
	onload = focusIt;
	
</script>
 
  

<script type="text/javascript">
var type =  "<c:out value='${type}'/>";
// Khai bao sdate, edate
var theme =  getTheme();
${jqxgrid}
if (type=='all')
{
${jqxgridUnCompare}
};
/* $(document).ready(function(){
 */	
	$('#btFilter').jqxButton({ theme: theme });
	$('#import').jqxButton({ theme: theme });

	 // ComboBox dept
	    var urldept = "${pageContext.request.contextPath}/ajax/getDeptListAlarm.htm";
	  	// prepare the data
	   var sourcedept =
	   {
	       datatype: "json",
	       url: urldept,
	       datafields: [
	                    { name: 'deptCode'},
	                    { name: 'deptValue'},
	                    { name: 'deptName'}
	                ],
	       async: false
	   };
	   var dataAdapterdept = new $.jqx.dataAdapter(sourcedept);
	  $("#dept").jqxComboBox({ source: dataAdapterdept,displayMember: "deptCode", valueMember: "deptCode", width: 200,height: 25,theme: theme,autoOpen: true,enableHover: true });
	   var dept =  "<c:out value='${dept}'/>";
		if(dept!=null&&dept!="")
			$('#dept').val(dept); 
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
	    	};
       
        return value;
    };
	$("#siteName").jqxInput({height: 20, width: 200, minLength: 0, maxLength: 900, theme: theme});
	 //Input BSC
    ${bscList}
    $("#bscid").jqxInput({ height: 20, width: 200, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#bscid").jqxInput({ query: item });
            response(bscList);
        },
        renderer: renderer
    });
    var bscid =  "<c:out value='${bscid}'/>";
   // alert(bscid);
    if(bscid!="")
		$('#bscid').val(bscid);
	
    
 // Input team
 	${teamList}
 	$("#teamProcess").jqxInput({ height: 20, width: 200, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#teamProcess").jqxInput({ query: item });
            response(teamList);
        },
        renderer: renderer
    });
 	var teamProcess =  "<c:out value='${teamProcess}'/>";
 	//alert(cellid);
    if(teamProcess!="")
		$('#teamProcess').val(teamProcess);
    
	
  $("#dept").on('change', function () {
	   
	  var dept = $('#dept').val();
		if (dept=='ALL')
		{
			dept='';
		};
  	   $.getJSON("${pageContext.request.contextPath}/ajax/getTeamListAlarm.htm",{dept:dept}, function(j){
  		   var teamList=[];
  		 	teamList =j;
  		 	$("#teamProcess").jqxInput({source: teamList});        
  	   });
	});	
	//Input province
    ${provinceList}
    $("#province").jqxInput({ height: 20, width: 200, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#province").jqxInput({ query: item });
            response(provinceList);
        },
        renderer: renderer
    });
    var province =  "<c:out value='${province}'/>";
   // alert(bscid);
    if(province!="")
		$('#province').val(province);
    
	// Input district
 	${districtList}
	$("#district").jqxInput({ height: 20, width: 200, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#district").jqxInput({ query: item });
            response(districtList);
        },
        renderer: renderer
    });
 	var district =  "<c:out value='${district}'/>";
 
    if(district!="") 
		$('#district').val(district);
		//alert(district);
	
	$("#province").on('change', function () {
	
		$.getJSON("${pageContext.request.contextPath}/ajax/getDistrictList.htm",{province:$('#province').val(),username:'',team:''}, function(j){
			districtList =j;
			   $("#district").jqxInput({source: function (query, response) {
		           var item = query.split(/,\s*/).pop();
		           // update the search query.
		           $("#district").jqxInput({ query: item });
		           response(districtList);
		       },
		       renderer: renderer
			    });
		   });	
  	  
	});		
	// Input CELL
 	
     
	function dateToYMDHMS(date) {
	//alert(date);
    var d = date.getDate();
    var m = date.getMonth()+1;
    var y = date.getFullYear();
    var h = date.getHours();
    var mi = date.getMinutes();
    var s = date.getSeconds();
    //alert(d+'-'+m+'-'+y);
    return '' + (d <= 9 ? '0' + d : d) + '/' + (m<=9 ? '0' + m : m) + '/' + y+ ' ' + (h<=9 ? '0' + h : h)+ ':' + (mi<=9 ? '0' + mi : mi)+ ':' + (s<=9 ? '0' + s : s) ;
}	;
	  // handle context menu clicks.
    $("#Menu").on('itemclick', function (event) {
        var args = event.args;
        
        if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
       	  	var rowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
            var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
            var sdate ='';
            var idlog='';
            var id='';
            if (row.id!=null &&row.id!=''&row.id!='null')
        	{
            	id = row.id;
        	};
            if (row.idLog!=null &&row.idLog!=''&row.idLog!='null')
        	{
            	idlog = row.idLog;
        	};
            if (row.sdate!=null &&row.sdate!=''&row.sdate!='null')
            	{
            		sdate = dateToYMDHMS(new Date(row.sdate));
            	};
            var shiftsdate ='';
            if (row.shiftSdate!=null &&row.shiftSdate!=''&row.shiftSdate!='null')
            	{
            		shiftsdate =dateToYMDHMS(new Date(row.shiftSdate));
            	};
        	window.location = 'compareForm.htm?id='+id+'&idLog='+idlog+'&bscid='+row.bscid+'&cellid='+row.cellid+'&sdate='+sdate+'&shiftSdate='+shiftsdate;   
        };
        if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteMultiSelected"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var idLogList="";
        		var cond="";
        		var con2="";
        		//alert(selectedrowindexes);
        		//var rowIndexList = selectedrowindexes.split(",");
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        				 if (row.idLog!=null &&row.idLog!=''&row.idLog!='null')
        		        	{
        					 	idLogList+=cond+row.idLog;
             					cond=",";
        		        	};
        				 
        				 if (row.id!=null &&row.id!=''&row.id!='null')
	     		        	{
	        					 idList+=con2+row.id;
	     					 	con2=",";
	     		        	};
        				
        			};
        			// alert(idList);
        			window.location = 'deleteCompare.htm?idList='+idList+'&idLogList='+idLogList+'&type='+type;
        		};
   			};
        };
      /*   var exportFileName =  "<c:out value='${exportFileName}'/>"; */
    	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    		window.open('${pageContext.request.contextPath}/LossPower/exportResource.htm?startTime='+"<c:out value='${startTime}'/>"+
    				'&endTime='+"<c:out value='${endTime}'/>"+
    				'&cellid='+"<c:out value='${cellid}'/>"+
    	        	'&teamProcess='+"<c:out value='${teamProcess}'/>"+
    	        	'&siteName='+"<c:out value='${siteName}'/>"+
    	        	 '&province='+"<c:out value='${province}'/>"+
    	        	 '&district='+"<c:out value='${district}'/>"+
    	        	 '&bscid='+"<c:out value='${bscid}'/>"+
    	        	 '&strWhere='+$("#strWhere").val()+
    	        	 '&sortfield='+$("#sortfield").val()+
    	        	 '&sortorder='+$("#sortorder").val()
    	        	 ,'_blank');
	    };
    });
      
 /*  }); */
	  

</script>
  