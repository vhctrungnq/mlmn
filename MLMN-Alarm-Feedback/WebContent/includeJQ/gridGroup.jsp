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
  </div>

<script type="text/javascript">

	
$(document).ready(function () {
	var theme =  getTheme();
	
	
	//dropdownlist an hien cot
    //call funtion add listSource	
   	${listSource}
    $("#jqxlistbox").jqxDropDownList({ checkboxes: true, source: listSource, width: 15, height: 15, theme: theme, dropDownHorizontalAlignment: 'right',dropDownWidth: 170 });

  // subscribe to the checkChange event.
 //  var stateGrid =null;
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
              //checked
 
        	}
       }
    	
    });
  
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
        filter: function(){
            $('#jqxgrid').jqxGrid('updatebounddata');
            },
        sort: function(){
    		$('#jqxgrid').jqxGrid('updatebounddata');
    	},
        root: 'Rows',
       	beforeprocessing: function(data)
		{
       		source.totalrecords = data[0].TotalRows;	
       		$("#strWhere").val(data[1].strWhere);
        	$("#sortfield").val(data[2].sortfield);
        	$("#sortorder").val(data[3].sortorder);
		},
    	pagesize: 100
    };
    
    var dataAdapter = new $.jqx.dataAdapter(source);
    //dinh nghia dinh dang du lieu: dinh dang ngay thang, so
    var getLocalization = function () {
        var localizationobj = {};
    
        localizationobj.firstDay = 1;
        localizationobj.percentsymbol = "%";
        localizationobj.currencysymbol = "€";
        localizationobj.currencysymbolposition = "after";
        localizationobj.decimalseparator = ".";
        localizationobj.thousandsseparator = ",";
        var days = {
            // full day names
            names: ["Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"],
            // abbreviated day names
            namesAbbr: ["Sonn", "Mon", "Dien", "Mitt", "Donn", "Fre", "Sams"],
            // shortest day names
            namesShort: ["So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"]
        };
        localizationobj.days = days;
        var months = {
            // full month names (13 months for lunar calendards -- 13th month should be "" if not lunar)
            names: ["Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember", ""],
            // abbreviated month names
            namesAbbr: ["Jan", "Feb", "Mär", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dez", ""]
        };
        var patterns = {
            d: "dd/MM/yyyy",
            D: "dddd, d. MMMM yyyy",
            t: "HH:mm",
            T: "HH:mm:ss",
            f: "dddd, d. MMMM yyyy HH:mm",
            F: "dddd, d. MMMM yyyy HH:mm:ss",
            M: "dd MMMM",
            Y: "MMMM yyyy"
        }
        localizationobj.patterns = patterns;
        localizationobj.months = months;
 
        return localizationobj;
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
    	source: dataAdapter,
    	showfilterrow: true,
        theme: theme,
        filterable: true,
        sortable: true, 
        pageable: true,
        autoheight: true,
        altrows: true,
        columnsresize: true,
        columnsreorder: true,
        localization: getLocalization(),
        pagesizeoptions: ['50', '100', '200', '300', '400', '500'],
        virtualmode: true,
        rendergridrows: function(obj){
    		return obj.data; },
    	selectionmode: 'multiplerowsextended',
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