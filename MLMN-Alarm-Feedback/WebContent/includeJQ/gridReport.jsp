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
       	<div id="jqxgrid"></div>
       	<div id='Menu'>
	        <ul>
	            <li><fmt:message key="global.button.exportExcel"/></li>    
	        </ul>
	       </div>
 </div>

<script type="text/javascript">
$(document).ready(function () {
	var theme =  getTheme();
	//Get URL for get data
    var url = "<c:out value='${url}'/>";
    url = url.replace(/amp;/g,'');
  
	// prepare the data source
    ${datafields}
   
    var source =
    {
    	datatype: "json",
        datafields: datafields,
        url: url,
        pagenum: 0,
        pagesize: 100
	   
    };
   var dataAdapter = new $.jqx.dataAdapter(source);
 	// renderer for grid cells. 
   var numberrenderer = function (row, column, value) {
        return '<div style="text-align: center; margin-top: 5px;">' + (1 + value) + '</div>';
    };
    var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
    	return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;text-decoration: underline;">' + value + '</span>';
        
      };
    var aggregatesrenderer = function (aggregates, column, element, summaryData) {
        var renderstring = "";
        $.each(aggregates, function (key, value) {
            var color = 'red';
           renderstring += '<div style="color: ' + color + '; position: relative; margin: 6px; text-align: right; overflow: hidden;">' + value + '</div>';
        });
        renderstring += "</div>";
        return renderstring;
    };
    //call funtion add column	
   	${column}
   
    // filter
    $("#jqxgrid").jqxGrid(
    {
    	width: '100%',
       	source: dataAdapter,
        theme: theme,
        showfilterrow: true,
        filterable: true,
        sortable: true,
        pageable: true,
        autoheight: true,
        showstatusbar: true,
        statusbarheight: 25,
        altrows: true,
        showaggregates: true,
        columnsresize: true,
        columnsreorder: true,
        selectionmode: 'singlecell',
        scrollmode: 'deferred',
        pagesizeoptions: ['50', '100', '200', '300', '400', '500'],
        columns: column
    });
 // create context menu
    var exportName = "<c:out value='${exportFileName}'/>";
    var contextMenu = $("#Menu").jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: theme });
    $("#jqxgrid").on('contextmenu', function () {
        return false;
    });
    // handle context menu clicks.
    $("#Menu").on('itemclick', function (event) {
        var args = event.args;
        if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	 $("#jqxgrid").jqxGrid('exportdata', 'xls', exportName);     
         }
     
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