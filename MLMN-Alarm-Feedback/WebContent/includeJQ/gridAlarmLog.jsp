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
	            	<li><fmt:message key="global.button.ackRow"/></li>
	            	<li><fmt:message key="global.button.unackRow"/></li>
	            	<li><fmt:message key="global.button.ackRowShift"/></li>
		   		</c:if>
		   		<c:if test="${function == 'nonfinish'}">
					<li><fmt:message key="global.button.editSelected"/></li>
		   		</c:if>
	            <li><fmt:message key="global.button.exportExcel"/></li>
	            <li><fmt:message key="global.button.exportCSV"/></li>
	            <li><fmt:message key="global.button.exportTXT"/></li>
	        </ul>
	       </div>
  </div>

<script type="text/javascript">

	
$(document).ready(function () {
	var theme =  getTheme();
	
	
	//dropdownlist an hien cot
    //call funtion add listSource	
   	${listSource}
    $("#jqxlistbox").jqxDropDownList({ checkboxes: true, source: listSource, width: 15, height: 15, theme: theme, dropDownHorizontalAlignment: 'right',dropDownWidth: 170 });
    // load the selected dropdpwnlist item when the page is reloaded.
    $("#jqxlistbox").on('checkChange', function (event) {
    	if (event.args) {
            var item = event.args.item;
            if (item) {
            	$("#jqxgrid").jqxGrid('beginupdate');
               if (item.checked==true)
                {
            	  	$("#jqxgrid").jqxGrid('showcolumn', item.value);                      
            	}
               else
                {
         	   		$("#jqxgrid").jqxGrid('hidecolumn', item.value);
                }
               $("#jqxgrid").jqxGrid('endupdate');
             
        	}
       }
    	
    });
  
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
     		$('#jqxgrid').jqxGrid('updatebounddata');
     		},
     	sort: function(){
     		$('#jqxgrid').jqxGrid('updatebounddata');
     		}


    };
    
    var dataAdapter = new $.jqx.dataAdapter(source);
    /* 
     $("#jqxgrid").bind('bindingcomplete', function (event) {
    	 var selectedItem = $.jqx.cookie.cookie("jqxDropDownList_jqxlistbox");
   	  		//  alert(selectedItem);
	   	    if (selectedItem) {
	   	    	var listVar = selectedItem.split(",");
	   			if (listVar != null) {
	   				$("#jqxlistbox").jqxDropDownList('uncheckAll');
	   				for (var i=0; i<listVar.length; i++) {
	   					$("#jqxlistbox").jqxDropDownList('checkItem', listVar[i] ); 
	   					$("#jqxgrid").jqxGrid('showcolumn',  listVar[i]);        
	   				}
	   			}	
	   	    }  
    });  */
    
     // To mau mot cot
     	${highlight}
  //to mau dong
  	var colorrowrenderer = function (row, columnfield, value, defaulthtml, columnproperties) {
	var valueKey = $('#jqxgrid').jqxGrid('getrowdata', row).shiftId;
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
    $("#jqxgrid").jqxGrid(
    {
    	width: '100%',
    	height: 500,
    	source: dataAdapter,
        theme: theme,
        filterable: true,
        showfilterrow: true,
        sortable: true, 
        pageable: true,
    	altrows: true,
        columnsresize: true,
        columnsreorder: true,
        autoloadstate: false,
        autosavestate: false,
       	pagesize: 100 ,
       	virtualmode: true,
        pagesizeoptions: ['50', '100', '200', '300', '400', '500'],
        rendergridrows: function(obj){
    		return obj.data; },
    	 autoshowfiltericon: true,
		ready: function () {
			 var localizationObject = {
               filterstringcomparisonoperators: ['contains', 'does not contain','null','not null'],
               // filter numeric comparison operators.
               filternumericcomparisonoperators: ['less than', 'greater than','null','not null'],
               // filter date comparison operators.
               filterdatecomparisonoperators: ['date less than', 'date greater than','null','not null'],
               // filter bool comparison operators.
               filterbooleancomparisonoperators: ['equal', 'not equal','null','not null']
           	}
           	$("#jqxgrid").jqxGrid('localizestrings', localizationObject);
			 
	       },
	       updatefilterconditions: function (type, defaultconditions) {
	           var stringcomparisonoperators = ['CONTAINS', 'DOES_NOT_CONTAIN','NULL','NOT_NULL'];
	           var numericcomparisonoperators = ['LESS_THAN', 'GREATER_THAN','NULL','NOT_NULL'];
	           var datecomparisonoperators = ['DATE_LESS_THAN', 'DATE_GREATER_THAN','NULL','NOT_NULL'];
	           var booleancomparisonoperators = ['EQUAL', 'NOT_EQUAL','NULL','NOT_NULL'];
	           switch (type) {
	               case 'stringfilter':
	                   return stringcomparisonoperators;
	               case 'numericfilter':
	                   return numericcomparisonoperators;
	               case 'datefilter':
	                   return datecomparisonoperators;
	               case 'booleanfilter':
	                   return booleancomparisonoperators;
	           }
	       },
	       updatefilterpanel: function (filtertypedropdown1, filtertypedropdown2, filteroperatordropdown, filterinputfield1, filterinputfield2, filterbutton, clearbutton,
	                 columnfilter, filtertype, filterconditions) {
	                    var index1 = 0;
	                    var index2 = 0;
	                    if (columnfilter != null) {
	                        var filter1 = columnfilter.getfilterat(0);
	                        var filter2 = columnfilter.getfilterat(1);
	                        if (filter1) {
	                            index1 = filterconditions.indexOf(filter1.comparisonoperator);
	                            var value1 = filter1.filtervalue;
	                            filterinputfield1.val(value1);
	                        }
	                        if (filter2) {
	                            index2 = filterconditions.indexOf(filter2.comparisonoperator);
	                            var value2 = filter2.filtervalue;
	                            filterinputfield2.val(value2);
	                        }
	                    }
	                    else
                    	{
	                    	filteroperatordropdown.jqxDropDownList({ autoDropDownHeight: true, selectedIndex: 1 });
                    	}
	                    filtertypedropdown1.jqxDropDownList({ autoDropDownHeight: true, selectedIndex: index1 });
	                    filtertypedropdown2.jqxDropDownList({ autoDropDownHeight: true, selectedIndex: index2 });
	                },
           selectionmode: 'multiplerowsextended',
            altrows: true,
    		columns: column
    });
    
    
    //button export data
    var exportFileName =  "<c:out value='${exportFileName}'/>";
 // create context menu
    var contextMenu = $("#Menu").jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: theme });
    $("#jqxgrid").on('contextmenu', function () {
        return false;
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