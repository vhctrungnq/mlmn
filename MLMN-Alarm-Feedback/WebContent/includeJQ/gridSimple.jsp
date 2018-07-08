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
 </div>

<script type="text/javascript">
$(document).ready(function () {
var theme = getTheme();
    
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
        altrows: true,
        columnsresize: true,
        columnsreorder: true,
        scrollmode: 'logical',
        selectionmode: 'singlerow',
        pagesizeoptions: ['50', '100', '200', '300', '400', '500'],
        //selectionmode: 'multiplecellsextended',
        columns: column
    });
  //dropdownlist an hien cot
    //call funtion add listSource	
   	${listSource}
   	
    $("#jqxlistbox").jqxDropDownList({ checkboxes: true, source: listSource, width: 15, height: 15, theme: theme, dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });
   // $("#jqxlistbox").jqxDropDownList('checkIndex', 0);
    // subscribe to the checkChange event.
    $("#jqxlistbox").on('checkChange', function (event) {
    	if (event.args) {
            var item = event.args.item;
            if (item) {
               if (item.checked==true)
                {
                  
                	$("#jqxgrid").jqxGrid('showcolumn', item.value);                      
            	}
               else
                {
         	   		$("#jqxgrid").jqxGrid('hidecolumn', item.value);
                }
        	}
    	}
    });
});
    </script>