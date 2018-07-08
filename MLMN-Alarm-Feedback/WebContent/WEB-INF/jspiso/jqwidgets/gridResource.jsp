<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div id='jqxWidget' style="margin-top:1px;">
    	<div style="float: right;margin-bottom:3px;width:180px;">
        	<table border="0" cellspacing="1" cellpadding="0" width="100%">
				<tr>
					<td align="right"><input type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' />
      				</td>
					<td style="width:33px"><div style="float: right;" id="jqxlistbox"></div></td>
				</tr>
			</table>
        </div><br>
    	
        <div id="jqxgrid"></div>
        <div id='Menu'>
            <ul>
				<li><fmt:message key="global.button.addNew"/></li>
	            <li><fmt:message key="global.button.editSelected"/></li>
		   		<li><fmt:message key="global.button.deleteMultiSelected"/></li>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
		   		<li><fmt:message key="global.link.cardNew"/></li>
		   		<li><fmt:message key="global.link.cardMove"/></li>
		   		<li><fmt:message key="global.link.cardRemove"/></li>
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
    var inputStatus =  "<c:out value='${inputStatus}'/>";
    $('#addNew').jqxButton({ theme: theme });
    $('#addNew').click(function () {
    	
		window.location = 'form.htm?inputStatus=' + inputStatus;
	});
    
    $('#importFile').jqxButton({ theme: theme });
    $('#importFile').click(function () {
		window.location = 'upload.htm';
	});
	
 	// Create context menu
	var contextMenu = $('#Menu').jqxMenu({ width: 200, height: 198, autoOpenPopup: false, mode: 'popup', theme: theme });
		$('#jqxgrid').on('contextmenu', function () {
		return false;
	});
    // handle context menu clicks.
   	//var strWhere =  "<c:out value='${strWhere}'/>";
   	
	$('#Menu').on('itemclick', function (event) {
	var args = event.args;
	var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
	var row = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
	var productCode = row.productCode;
	if(productCode == null)
		productCode = "";
	var seriNo = row.seriNo;
	if(seriNo == null)
		seriNo = "";
	// add new row
	if ($.trim($(args).text()) == '<fmt:message key="global.button.addNew"/>') {
		window.location = 'form.htm?inputStatus=' + inputStatus;
		}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
		window.location = 'form.htm?id='+row.id+'&inputStatus='+inputStatus;
	}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteMultiSelected"/>') {
			var r=confirm('<fmt:message key="messsage.confirm.delete"/>');
			if (r==true)
			  {
				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        				idList+=cond+row.id;
        				cond=",";
        			}
        			window.location = 'delete.htm?idList='+idList+'&inputStatus='+inputStatus;
        		}
			  }
			return false;
        }
	// export data
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>'){
		//$("#jqxgrid").jqxGrid('exportdata', 'xls', exportFileName);
		
		window.open('${pageContext.request.contextPath}/iso/quan-ly-thiet-bi/exportResource.htm?deptCode='+"<c:out value='${deptCode}'/>"+
				'&team='+"<c:out value='${team}'/>"+
				'&subTeam='+"<c:out value='${subTeam}'/>"+
	        	'&province='+"<c:out value='${province}'/>"+
	        	'&district='+"<c:out value='${district}'/>"+
	        	 '&neType='+"<c:out value='${neType}'/>"+
	        	 '&ne='+"<c:out value='${neName}'/>"+
	        	 '&neGroup='+"<c:out value='${neGroup}'/>"+
	        	 '&locationName='+"<c:out value='${locationName}'/>"+
	        	 '&vendor='+"<c:out value='${vendor}'/>"+
	        	 '&productCode='+"<c:out value='${productCode}'/>"+
	        	 '&productName='+"<c:out value='${productName}'/>"+
	        	 '&seriNo='+"<c:out value='${seriNo}'/>"+
	        	 '&inputStatus='+"<c:out value='${inputStatus}'/>"+
	        	 '&status='+"<c:out value='${status}'/>"+
	        	 '&strWhere='+$("#strWhere").val()+
	        	 '&sortfield='+$("#sortfield").val()+
	        	 '&sortorder='+$("#sortorder").val()
	        	 ,'_blank');
	}
	else if ($.trim($(args).text()) == '<fmt:message key="global.link.cardNew"/>'){
		window.location = '${pageContext.request.contextPath}/iso/theo-doi-tai-nguyen-mang-luoi/list.htm?status=N&productCode=' + productCode + '&seriNo=' + seriNo;
	}
	else if ($.trim($(args).text()) == '<fmt:message key="global.link.cardMove"/>'){
		window.location = '${pageContext.request.contextPath}/iso/theo-doi-tai-nguyen-mang-luoi/list.htm?status=H&productCode=' + productCode + '&seriNo=' + seriNo;
	}
	else if ($.trim($(args).text()) == '<fmt:message key="global.link.cardRemove"/>'){
		window.location = '${pageContext.request.contextPath}/iso/theo-doi-tai-nguyen-mang-luoi/list.htm?status=R&productCode=' + productCode + '&seriNo=' + seriNo;
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