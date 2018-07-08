<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div id='jqxWidget' style="margin-top:1px">
    	<div style="float: right;margin-bottom:3px;width:180px;">
        	<table border="0" cellspacing="1" cellpadding="0" width="100%">
				<tr>
					<td><div style="float: right;" id="jqxlistbox"></div></td>
				</tr>
			</table>
        </div><br>
        <div id="jqxgrid"></div>
        <div id='Menu'>
            <ul>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
       </div>
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
    
    var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
    	return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;text-decoration: underline;">' + value + '</span>';
        
      };
      
      var aggregatesrenderer = function (aggregates, column, element, summaryData) {
  		var renderstring = "";
  		$.each(aggregates, function (key, value) {
  		var color = 'red';
  		renderstring += '<div style=\"color: ' + color + '; position: relative; margin: 6px; text-align: right; overflow: hidden;\">' + value + '</div>';
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
        showstatusbar: true,
		statusbarheight: 25,
		showaggregates: true,
        autoheight: true,
        altrows: true,
        columnsresize: true,
        columnsreorder: true,
        scrollmode: 'deferred',
        localization: getLocalization(),
        pagesizeoptions: ['50', '100', '200', '300', '400', '500'],
        columns: column
    });

  	//dropdownlist an hien cot
    //call funtion add listSource	
   	${listSource}
  	
   	$("#jqxlistbox").jqxDropDownList({ checkboxes: true, source: listSource, width: 25, height: 20, theme: theme, dropDownHorizontalAlignment: 'right',dropDownWidth: 150 });
    $("#jqxlistbox").jqxDropDownList('checkIndex', 0);
    // subscribe to the checkChange event.
    $("#jqxlistbox").on('checkChange', function (event) {
    	$("#jqxgrid").jqxGrid('beginupdate');
        if (event.args.checked) {
            $("#jqxgrid").jqxGrid('showcolumn', event.args.value);
        }
        else {
            $("#jqxgrid").jqxGrid('hidecolumn', event.args.value);
        }
        $("#jqxgrid").jqxGrid('endupdate');
    }); 
    
 	// Create context menu
	var contextMenu = $('#Menu').jqxMenu({ width: 200, height: 30, autoOpenPopup: false, mode: 'popup', theme: theme });
		$('#jqxgrid').on('contextmenu', function () {
		return false;
	});
    // handle context menu clicks.
	var exportFileName =  "<c:out value='${exportFileName}'/>";
	 $('#Menu').on('itemclick', function (event) {
	var args = event.args;
	var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
	var row = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
	// export data
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>') {
		$("#jqxgrid").jqxGrid('exportdata', 'xls', exportFileName);
		}
	
	  });
    
	$('#jqxgrid').on('rowclick', function (event) {
	    if (event.args.rightclick) {
	        $('#jqxgrid').jqxGrid('selectrow', event.args.rowindex);
	        var scrollTop = $(window).scrollTop();
	      var scrollLeft = $(window).scrollLeft();
	         contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);
	         return false;
	     }
	 });
    
});
</script>