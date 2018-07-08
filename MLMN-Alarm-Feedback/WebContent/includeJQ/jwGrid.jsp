<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <!-- <div style="float: right;" id="jqxlistbox"></div> -->
 <div id="grid"></div>

<script type="text/javascript">

	
$(document).ready(function () {
	var theme =  getTheme();
	
	
	//dropdownlist an hien cot
    
  
	//Get URL for get data
    var url = "<c:out value='${url}'/>";
    url = url.replace(/amp;/g,'');
   
    //var totalRows = "<c:out value='${totalRows}'/>";
    //alert(totalRows);
	// prepare the data source
    ${datafields}
   
    var source =
    {
    	datatype: "json",
        datafields: datafields,
		url: url,
     	id: 'id',
    	root: 'Rows',
 		beforeprocessing: function(data)
 		{		
 			source.totalrecords = data.totalRow;					
 		},
     	filter: function(){
     		$('#grid').jqxGrid('updatebounddata');
     		},
     	sort: function(){
     		$('#grid').jqxGrid('updatebounddata');
     		}


    };
    
    var dataAdapter = new $.jqx.dataAdapter(source);
     // To mau mot cot
     ${highlight}
  //to mau dong
  	var colorrowrenderer = function (row, columnfield, value, defaulthtml, columnproperties) {
	var valueKey = $('#grid').jqxGrid('getrowdata', row).shiftId;
	 if( valueKey!=null){ 
		  $("#row"+row+"jqxgrid").addClass("shiftId");
		}; 
  	};	  
  	var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
			return '<span style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;text-decoration: underline;\">' + value + '</span>'; 	
		  };
 	// renderer for grid cells. 
   var numberrenderer = function (row, column, value) {
        return '<div style="text-align: center; margin-top: 5px;">' + (1 + value) + '</div>';
    };
   //call funtion add column	
   	${column}
   
    // filter
    $("#grid").jqxGrid(
    {
    	width: '100%',
    	source: dataAdapter,
        theme: theme,
        filterable: true,
        showfilterrow: true,
        sortable: true, 
        pageable: true,
        autoheight: true,
        autorowheight: true,
        altrows: true,
        columnsresize: true,
        columnsreorder: true,
        scrollmode: 'logical',
        autoloadstate: false,
        autosavestate: false,
       	pagesize: 100 ,
       	virtualmode: true,
        pagesizeoptions: ['50', '100', '200', '300', '400', '500'],
        rendergridrows: function(obj){
    		return obj.data; },
    	autoshowfiltericon: true,
		selectionmode: 'multiplerowsextended',
        columns: column
    });
    //button export data
   /*  var exportFileName =  "<c:out value='${exportFileName}'/>";
 // create context menu
    var contextMenu = $("#menujqx").jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: theme });
    $("#grid").on('contextmenu', function () {
        return false;
    });
    $("#grid").on('rowclick', function (event) {
        if (event.args.rightclick) {
            $("#grid").jqxGrid('selectrow', event.args.rowindex);
            var scrollTop = $(window).scrollTop();
            var scrollLeft = $(window).scrollLeft();
            contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);
            return false;
        }
    }); */
});
    </script>