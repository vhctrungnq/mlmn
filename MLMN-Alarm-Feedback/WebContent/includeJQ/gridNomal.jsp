<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <style>
 .shiftId
{
color:blue;
}
 </style>  
<div id="jqxWidget" style="font-size: 13px; font-family: Verdana; float: left;width:100%;">
        <div style="float: right;" id="jqxlistbox"></div>
        <div id="jqxgrid"></div>
        <div id='Menu'>
	        <ul>
	        	<c:if test="${checkCaTruc==true}">
					<li><fmt:message key="global.button.editSelected"/></li>
	            	<li><fmt:message key="global.button.deleteMultiSelected"/></li>
		   		</c:if>
	            <li><fmt:message key="global.button.exportExcel"/></li>
	            <li><fmt:message key="global.button.removeFilter"/></li>
	            
	        </ul>
	       </div>
  </div>

<script type="text/javascript">
$(document).ready(function () {
	var theme =  getTheme();
	//Get URL for get data
    var url = "<c:out value='${url}'/>";
    url = url.replace(/amp;/g,'');
   
    var totalRows = "<c:out value='${totalRows}'/>";
  //  alert(totalRows);
	// prepare the data source
    ${datafields}
   
    var source =
    {
    	datatype: "json",
        datafields: datafields,
        url: url,
    	totalrecords: parseInt(totalRows),
    	filter: function(){
    		$('#jqxgrid').jqxGrid('updatebounddata');
    		},
    	sort: function(){
    		$('#jqxgrid').jqxGrid('updatebounddata');
    		}
	   
    };
    
    var dataAdapter = new $.jqx.dataAdapter(source);
     // To mau mot cot
     	${highlight}
  //to mau dong
  	var colorrowrenderer = function (row, columnfield, value, defaulthtml, columnproperties) {
	var valueKey = $('#jqxgrid').jqxGrid('getrowdata', row).shiftId;
	 if( valueKey!=null){ 
		  $("#row"+row+"jqxgrid").addClass("shiftId");
		}; 
  	};	  
 	// renderer for grid cells. 
   var numberrenderer = function (row, column, value) {
        return '<div style="text-align: center; margin-top: 5px;">' + (1 + value) + '</div>';
    };

    //call funtion add column	
   	${column}
 
    // filter
    $("#jqxgrid").jqxGrid(
    {
    	width: '100%',
    	pagerheight: 20,
       	source: dataAdapter,
        theme: theme,
        showfilterrow: true,
        filterable: true,
        sortable: true,
        pageable: true,
        //autorowheight: true,
        autoheight: true,
        altrows: true,
        columnsresize: true,
        columnsreorder: true,
        scrollmode: 'deferred',
        virtualmode: true,
        selectionmode:'checkbox',
        //localization: getLocalization(),
        pagesizeoptions: ['50', '100', '200', '300', '400', '500'],
        pagesize: 100,
        //groupable: true,
        rendergridrows: function(obj){
    		return obj.data; },
    		
    		//return dataAdapter; },
        //selectionmode: 'multiplecellsextended',
        columns: column
    });
    //button export data
    var exportFileName =  "<c:out value='${exportFileName}'/>";
 // create context menu
    var contextMenu = $("#Menu").jqxMenu({ width: 200, height: 120, autoOpenPopup: false, mode: 'popup', theme: theme });
    $("#jqxgrid").on('contextmenu', function () {
        return false;
    });
    // handle context menu clicks.
    $("#Menu").on('itemclick', function (event) {
        var args = event.args;
        
        if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
       	  	var rowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
            var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
        	window.location = 'form.htm?id='+row.id+'&type='+"<c:out value='${type}'/>"+'&function='+"<c:out value='${function}'/>";   
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteMultiSelected"/>')  {
        	var answer = confirm ("Are you sure you want to delete from the database?");
        	if (answer)
        	{
				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		alert(selectedrowindexes);
        		//var rowIndexList = selectedrowindexes.split(",");
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        				idList+=cond+row.id;
        				cond=",";
        			}
        			window.location = 'delete.htm?idList='+idList+'&type='+"<c:out value='${type}'/>"+'&function='+"<c:out value='${function}'/>";
        		}
        		alert(idList);
        		/* var rowid = $("#jqxgrid").jqxGrid('getrowid', rowindex);
                 $("#jqxgrid").jqxGrid('deleterow', rowid); */
              
        	}
          
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.ackRow"/>')  {
        	var answer = confirm ("Are you sure you want to ack rows from the database?");
        	if (answer)
        	{
				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        				//alert(row.edate);
        				if (row.edate==null||row.edate=='')
        					{
        						//alert(row.sdate);
		        				idList+=cond+row.id;
		        				cond=",";
        					}
        			}
        			
        				window.location = 'ack.htm?idList='+idList+'&type='+"<c:out value='${type}'/>"+'&function='+"<c:out value='${function}'/>"+'&inshift=N';
	
				}
    		} 
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.ackRowShift"/>')  {
        	var answer = confirm ("Are you sure you want to ack rows and copy to shift?");
        	if (answer)
        	{
				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        				if (row.edate==null||row.edate=='')
        					{
        						//alert(row.edate);
		        				idList+=cond+row.id;
		        				cond=",";
        					}
        			}
        			window.location = 'ack.htm?idList='+idList+'&type='+"<c:out value='${type}'/>"+'&function='+"<c:out value='${function}'/>"+'&inshift=Y';
        		}
    		} 
        }
       if ($.trim($(args).text()) == "Remove filter")  {
        	$("#jqxgrid").jqxGrid('clearfilters');
       
        if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	 //$("#jqxgrid").jqxGrid('exportdata', 'xls', 'rAlarmLog');     
        	 window.open('${pageContext.request.contextPath}/exportExcel/alarmLog.htm?sdate='+"<c:out value='${sdate}'/>"+
        	'&edate='+"<c:out value='${edate}'/>"+
        	 '&bscid='+"<c:out value='${bscid}'/>"+
        	 '&cellid='+"<c:out value='${cellid}'/>"+
        	 '&vendor='+"<c:out value='${vendor}'/>"+
        	 '&district='+"<c:out value='${district}'/>"+
        	 '&alarmName='+"<c:out value='${alarmName}'/>"+
        	 '&severity='+"<c:out value='${severity}'/>"+
        	 '&type='+"<c:out value='${type}'/>"+
        	 '&function='+"<c:out value='${function}'/>"+
        	 '&province='+"<c:out value='${province}'/>"+
        	 '&team='+"<c:out value='${team}'/>"+
        	 '&alarmType='+"<c:out value='${alarmType}'/>"+
        	 '&alarmMappingName='+"<c:out value='${alarmMappingName}'/>"+
        	 '&typeExport=xls','_blank');
             
        }
      /* if ($.trim($(args).text()) == "Export to CVS")  {
        	 $("#jqxgrid").jqxGrid('exportdata', 'csv', exportFileName);
        } */
    });
    $("#jqxgrid").on('rowclick', function (event) {
        if (event.args.rightclick) {
            $("#jqxgrid").jqxGrid('selectrow', event.args.rowindex);
            var scrollTop = $(window).scrollTop();
            var scrollLeft = $(window).scrollLeft();
            contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);
            return false;
        }
    });
});
    </script>