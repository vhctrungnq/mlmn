package vo.dictionary;

import java.util.List;

import vo.CTableConfig;
/**
 * Chuc nang: Dinh nghia grid
 * @author BUIQUANG
 * Ngay tao: 01/11/2013
 */
public class TableConfigsHelper {

	/**
	 * Cau hinh datafield cho grid
	 * @param configList
	 * @return
	 */
	public static String getDataFields(List<CTableConfig> configList) {
		String datafields =" var datafields = [";
		String condition="";
		for (CTableConfig config : configList) {
			datafields +=condition+"          { name: '"+config.getDataField()+"', type: '"+config.getDataType()+"' }";	
			condition=",";
		}
		datafields +="];";
		return datafields;
	}
	
	/**
	 * Cau hinh thuoc tinh column trong grid
	 * @param configList
	 * @return
	 */
	public static String getColumns(List<CTableConfig> configList) {
		String constructor="var column = [{pinned: true, text: 'No.',datafield: '', columntype: 'number', filterable: false, exportable: false, cellsrenderer: numberrenderer, width: 30,align: 'center' }";
		
		String condition=",";
		for (CTableConfig config : configList) {
			constructor +=condition+"{ text: '"+config.getColumnName()+"', datafield: '"+config.getDataField()+"'";	
			if (config.getColumnType()!=null && !config.getColumnType().equals(""))
			{
				constructor += ", columntype: '"+config.getColumnType()+"'";
			}
			if (config.getFilterType()!=null && !config.getFilterType().equals(""))
			{
				
				constructor += ", filtertype: '"+config.getFilterType()+"'";
			}
			
			if (config.getStyle()!=null && !config.getStyle().equals(""))
			{
				constructor +=","+ config.getStyle();
			}
			if (config.getColumnGroup()!=null && !config.getColumnGroup().equals(""))
			{
				constructor +=", columngroup: '"+ config.getColumnGroup()+"'";
			}
			constructor +=" }";
		}
		constructor +="	] ;";
		return constructor;
	}
	
	/**
	 * Cau hinh  cac cot an hien cua Grid trong dropdowlist
	 * @param configList
	 * @return
	 */
	public static String getListSource(List<CTableConfig> configList) {
		String listSource =" var listSource = [";
		String condition="";
		for (CTableConfig config : configList) {
			listSource +=condition+"          {label:'"+config.getColumnName()+"', value: '"+config.getDataField()+"', checked: " + (config.getIsEnable().equals("Y")?"true":"false") + " }";	
			condition=",";
		}
		listSource +="        ];";
		return listSource;
	}
	
	/**
	 * Tao grid report co phan trang
	 * @param configList
	 * @param tableID
	 * @param url
	 * @param listSourceId
	 * @param listSource
	 * @param menu
	 * @param hightlight
	 * @param typeSelectRow
	 * @param groupColumn
	 * @return
	 */
	public static String getGridReportPaging(List<CTableConfig> configList, String tableID, String url, String listSourceId, List<CTableConfig> listSource, String menu, String hightlight, String rowsTotal, String typeSelectRow, String groupColumn) {
		String s = "";
		if (typeSelectRow==null||typeSelectRow.equals(""))
		{
			typeSelectRow="singlerow";
		}
		s += "$(document).ready(function () {";
		s += "var theme = getTheme();";
		s += getDataFields(configList);
		s += "var source =";
		s += "{";
		s += "datatype: 'json',";
		s += "datafields: datafields," ;
		s += "url:'" + url + "',";
		s += "filter: function(){";
		s += "    $('#jqxgrid').jqxGrid('updatebounddata');";
		s += "    },";
		s += "sort: function(){";
		s += "	$('#jqxgrid').jqxGrid('updatebounddata');";
		s += "},";
				s += "root: 'Rows',";
		s += "beforeprocessing: function(data)";
		s += "{";
		s += "	source.totalrecords = data[0].TotalRows;";	
		s += "	$('#strWhere').val(data[1].strWhere);";
		s += "	$('#sortfield').val(data[2].sortfield);";
		s += "	$('#sortorder').val(data[3].sortorder);";
		s += "},";
		s += "pagesize: 100";
		s += "};";
		
		s += "var dataadapter = new $.jqx.dataAdapter(source, {";
		s += "loadError: function(xhr, status, error){ alert(error);}";
		s += "});";
		
		// to mau
		if (hightlight != null)
		{
			s += "var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {";
			s += " return '<span style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;text-decoration: underline;\">' + value + '</span>';";   	
			s += "};";
		}
		s += "var numberrenderer = function (row, column, value) {";
		s += "    return '<div style=\"text-align: center; margin-top: 5px;\">' + (1 + value) + '</div>';";
		s += "};";
		if(rowsTotal != null){
			s += "var aggregatesrenderer = function (aggregates, column, element, summaryData) {";
			s += " var renderstring = \"\";";
			s += "    $.each(aggregates, function (key, value) {";
			s += "        var color = 'red';";
			s += "       renderstring += '<div style=\"color: ' + color + '; position: relative; margin: 6px; text-align: right; overflow: hidden;\">' + value + '</div>';";
			s += "    });";
			s += "   renderstring += \"</div>\";";
			s += "    return renderstring;";
			s += "};";
		}
		if (groupColumn!=null && !groupColumn.equals(""))
		{
			s += groupColumn;
		}
		s += getColumns(configList);
		
        //create grid
		s += "$('#" + tableID + "').jqxGrid(";
		s += "{";
		s += "width: '100%',";
		s += "source: dataadapter,";
		s += "theme: getTheme(),";
		s += "showfilterrow: true,";
		s += "filterable: true,";
		s += "sortable: true,";
		s += "pageable: true,";
		s += "autoheight: true,";
		if(rowsTotal != null){
			s += "		showstatusbar: true,";
			s += "		statusbarheight: 25,";
		}
        s += "altrows: true,";
        s += "showaggregates: true,";
		s += "columnsresize: true,";
		s += "columnsreorder: true,";
		s += "scrollmode: 'deferred',";
		s += "selectionmode: '"+typeSelectRow+"',";
		s += "pagesizeoptions: ['50', '100', '200', '300', '400', '500'],";
		s += "virtualmode: true,";
		s += "rendergridrows: function(obj){";
		s += "	return obj.data; },";
		s += "columns: column";
		if (groupColumn!=null && !groupColumn.equals(""))
		{
			s += ",columngroups: columngroups";
		}
		s += " });";
		if (menu != null && !menu.equals(""))
		{
			// Create context menu
			s += "	var contextMenu = $('#"+menu+"').jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: getTheme() });";
			s += "	$('#"+tableID+"').on('contextmenu', function () {";
			s += "	return false;";
			s += "	});";
		
			s += "	$('#"+tableID+"').on('rowclick', function (event) {";
			s += "	    if (event.args.rightclick) {";
			s += "	        $('#"+tableID+"').jqxGrid('selectrow', event.args.rowindex);";
			s += "	        var scrollTop = $(window).scrollTop();";
			s += "	        var scrollLeft = $(window).scrollLeft();";
			s += "          contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);";
			s += "	        return false;";
			s += "	     }";
			s += "	 });";
			s += " });";
		}
		if (listSourceId!=null && !listSourceId.equals(""))
		{
			//list source
			s += getListSource(listSource);
			s += "$('#"+listSourceId+"').jqxDropDownList({ checkboxes: true, source: listSource, width: 25, height: 20, theme: getTheme(), dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });";
			// subscribe to the checkChange event.
			s += "    $('#"+listSourceId+"').on('checkChange', function (event) {";
			s += "    	if (event.args) {";
			s += "            var item = event.args.item;";
			s += "            if (item) {";
			s += "               if (item.checked==true)";
			s += "                {";           
			s += "               	$('#"+tableID+"').jqxGrid('showcolumn', item.value);    ";                  
			s += "            	}";
			s += "               else";
			s += "                {";
			s += "         	   		$('#"+tableID+"').jqxGrid('hidecolumn', item.value);";
			s += "                }";
			s += "        	}";
			s += "    	}";
			s += "    });";
		}
		return s;
	}
	
	/**
	 * Tao grid report khong phan trang
	 * @param configList
	 * @param tableID
	 * @param url
	 * @param listSourceId
	 * @param listSource
	 * @param menu
	 * @param hightlight
	 * @param rowsTotal
	 * @param typeSelectRow
	 * @param groupColumn
	 * @return
	 */
	public static String getGridReportDontPaging(List<CTableConfig> configList, String tableID, String url, String listSourceId, List<CTableConfig> listSource, String menu, String hightlight, String rowsTotal, String typeSelectRow, String groupColumn) {
		String s = "";
		if (typeSelectRow==null||typeSelectRow.equals(""))
		{
			typeSelectRow="singlerow";
		}
		s += "$(document).ready(function () {";
		s += "var theme = getTheme();";
		s += getDataFields(configList);
		s += "var source =";
		s += "{";
		s += "datatype: 'json',";
		s += "datafields: datafields," ;
		s += "url:'" + url + "',";
		s += "pagenum: 0,";
		s += "pagesize: 100";
		s += "};";
		
		s += "var dataadapter = new $.jqx.dataAdapter(source, {";
		s += "loadError: function(xhr, status, error){ alert(error);}";
		s += "});";
		
		// to mau
		if (hightlight != null)
		{
			s += "var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {";
			s += " return '<span style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;text-decoration: underline;\">' + value + '</span>';";   	
			s += "};"; 
		}
		s += "var numberrenderer = function (row, column, value) {";
		s += "    return '<div style=\"text-align: center; margin-top: 5px;\">' + (1 + value) + '</div>';";
		s += "};";
		if(rowsTotal != null){
			s += "var aggregatesrenderer = function (aggregates, column, element, summaryData) {";
			s += " var renderstring = \"\";";
			s += "    $.each(aggregates, function (key, value) {";
			s += "        var color = 'red';";
			s += "       renderstring += '<div style=\"color: ' + color + '; position: relative; margin: 6px; text-align: right; overflow: hidden;\">' + value + '</div>';";
			s += "    });";
			s += "   renderstring += \"</div>\";";
			s += "    return renderstring;";
			s += "};";
		}
		if (groupColumn!=null && !groupColumn.equals(""))
		{
			s += groupColumn;
		}
		s += getColumns(configList);
		
        //create grid
		s += "$('#" + tableID + "').jqxGrid(";
		s += "{";
		s += "width: '100%',";
		s += "source: dataadapter,";
		s += "theme: getTheme(),";
		s += "showfilterrow: true,";
		s += "filterable: true,";
		s += "sortable: true,";
		s += "pageable: true,";
		s += "autoheight: true,";
		if(rowsTotal != null){
			s += "		showstatusbar: true,";
			s += "		statusbarheight: 25,";
		}
        s += "altrows: true,";
        s += "showaggregates: true,";
		s += "columnsresize: true,";
		s += "columnsreorder: true,";
		s += "scrollmode: 'deferred',";
		s += "selectionmode: '"+typeSelectRow+"',";
		s += "pagesizeoptions: ['50', '100', '200', '300', '400', '500'],";
		s += "columns: column";
		if (groupColumn!=null && !groupColumn.equals(""))
		{
			s += ",columngroups: columngroups";
		}
		s += " });";
		if (menu != null && !menu.equals(""))
		{
			// Create context menu
			s += "	var contextMenu = $('#"+menu+"').jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: getTheme() });";
			s += "	$('#"+tableID+"').on('contextmenu', function () {";
			s += "	return false;";
			s += "	});";
		
			s += "	$('#"+tableID+"').on('rowclick', function (event) {";
			s += "	    if (event.args.rightclick) {";
			s += "	        $('#"+tableID+"').jqxGrid('selectrow', event.args.rowindex);";
			s += "	        var scrollTop = $(window).scrollTop();";
			s += "	        var scrollLeft = $(window).scrollLeft();";
			s += "          contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);";
			s += "	        return false;";
			s += "	     }";
			s += "	 });";
			s += " });";
		}
		if (listSourceId!=null && !listSourceId.equals(""))
		{
			//list source
			s += getListSource(listSource);
			s += "$('#"+listSourceId+"').jqxDropDownList({ checkboxes: true, source: listSource, width: 25, height: 20, theme: getTheme(), dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });";
			// subscribe to the checkChange event.
			s += "    $('#"+listSourceId+"').on('checkChange', function (event) {";
			s += "    	if (event.args) {";
			s += "            var item = event.args.item;";
			s += "            if (item) {";
			s += "               if (item.checked==true)";
			s += "                {";           
			s += "               	$('#"+tableID+"').jqxGrid('showcolumn', item.value);    ";                  
			s += "            	}";
			s += "               else";
			s += "                {";
			s += "         	   		$('#"+tableID+"').jqxGrid('hidecolumn', item.value);";
			s += "                }";
			s += "        	}";
			s += "    	}";
			s += "    });";
		}
		return s;
	}
	
	/**
	 * Grid them, sua, xoa co phan trang
	 * @param configList
	 * @param tableID
	 * @param url
	 * @param listSourceId
	 * @param listSource
	 * @param menu
	 * @param hightlight
	 * @param rowsTotal
	 * @param typeSelectRow
	 * @param groupColumn
	 * @return
	 */
	public static String getGridAddAndPaging(List<CTableConfig> configList, String tableID, String url, String listSourceId, List<CTableConfig> listSource, String menu, String hightlight, String rowsTotal, String typeSelectRow, String groupColumn) {
		String s = "";
		if (typeSelectRow==null||typeSelectRow.equals(""))
		{
			typeSelectRow="singlerow";
		}
		s += "$(document).ready(function () {";
		s += "var theme = getTheme();";
		s += getDataFields(configList);
		s += "var source =";
		s += "{";
		s += "datatype: 'json',";
		s += "datafields: datafields," ;
		s += "url:'" + url + "',";
		s += "filter: function(){";
		s += "    $('#jqxgrid').jqxGrid('updatebounddata');";
		s += "    },";
		s += "sort: function(){";
		s += "	$('#jqxgrid').jqxGrid('updatebounddata');";
		s += "},";
				s += "root: 'Rows',";
		s += "beforeprocessing: function(data)";
		s += "{";
		s += "	source.totalrecords = data[0].TotalRows;";	
		s += "	$('#strWhere').val(data[1].strWhere);";
		s += "	$('#sortfield').val(data[2].sortfield);";
		s += "	$('#sortorder').val(data[3].sortorder);";
		s += "},";
		s += "pagesize: 100";
		s += "};";
		
		s += "var dataadapter = new $.jqx.dataAdapter(source, {";
		s += "loadError: function(xhr, status, error){ alert(error);}";
		s += "});";
		
		// to mau
		if (hightlight != null)
		{
			s += "var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {";
			s += " return '<span style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;text-decoration: underline;\">' + value + '</span>';";   	
			s += "};";
		}
		s += "var numberrenderer = function (row, column, value) {";
		s += "    return '<div style=\"text-align: center; margin-top: 5px;\">' + (1 + value) + '</div>';";
		s += "};";
		if(rowsTotal != null){
			s += "var aggregatesrenderer = function (aggregates, column, element, summaryData) {";
			s += " var renderstring = \"\";";
			s += "    $.each(aggregates, function (key, value) {";
			s += "        var color = 'red';";
			s += "       renderstring += '<div style=\"color: ' + color + '; position: relative; margin: 6px; text-align: right; overflow: hidden;\">' + value + '</div>';";
			s += "    });";
			s += "   renderstring += \"</div>\";";
			s += "    return renderstring;";
			s += "};";
		}
		if (groupColumn!=null && !groupColumn.equals(""))
		{
			s += groupColumn;
		}
		s += getColumns(configList);
		
        //create grid
		s += "$('#" + tableID + "').jqxGrid(";
		s += "{";
		s += "width: '100%',";
		s += "source: dataadapter,";
		s += "theme: getTheme(),";
		s += "showfilterrow: true,";
		s += "filterable: true,";
		s += "sortable: true,";
		s += "pageable: true,";
		s += "autoheight: true,";
		if(rowsTotal != null){
			s += "		showstatusbar: true,";
			s += "		statusbarheight: 25,";
		}
        s += "altrows: true,";
        s += "showaggregates: true,";
		s += "columnsresize: true,";
		s += "columnsreorder: true,";
		s += "scrollmode: 'deferred',";
		s += "selectionmode: '"+typeSelectRow+"',";
		s += "pagesizeoptions: ['50', '100', '200', '300', '400', '500'],";
		s += "virtualmode: true,";
		s += "rendergridrows: function(obj){";
		s += "	return obj.data; },";
		s += "columns: column";
		if (groupColumn!=null && !groupColumn.equals(""))
		{
			s += ",columngroups: columngroups";
		}
		s += " });";
		if (menu != null && !menu.equals(""))
		{
			// Create context menu
			s += "	var contextMenu = $('#"+menu+"').jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: getTheme() });";
			s += "	$('#"+tableID+"').on('contextmenu', function () {";
			s += "	return false;";
			s += "	});";
		
			s += "	$('#"+tableID+"').on('rowclick', function (event) {";
			s += "	    if (event.args.rightclick) {";
			s += "	        $('#"+tableID+"').jqxGrid('selectrow', event.args.rowindex);";
			s += "	        var scrollTop = $(window).scrollTop();";
			s += "	        var scrollLeft = $(window).scrollLeft();";
			s += "          contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);";
			s += "	        return false;";
			s += "	     }";
			s += "	 });";
			s += " });";
		}
		if (listSourceId!=null && !listSourceId.equals(""))
		{
			//list source
			s += getListSource(listSource);
			s += "$('#"+listSourceId+"').jqxDropDownList({ checkboxes: true, source: listSource, width: 25, height: 20, theme: getTheme(), dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });";
			// subscribe to the checkChange event.
			s += "    $('#"+listSourceId+"').on('checkChange', function (event) {";
			s += "    	if (event.args) {";
			s += "            var item = event.args.item;";
			s += "            if (item) {";
			s += "               if (item.checked==true)";
			s += "                {";           
			s += "               	$('#"+tableID+"').jqxGrid('showcolumn', item.value);    ";                  
			s += "            	}";
			s += "               else";
			s += "                {";
			s += "         	   		$('#"+tableID+"').jqxGrid('hidecolumn', item.value);";
			s += "                }";
			s += "        	}";
			s += "    	}";
			s += "    });";
		}
		return s;
	}
	
	public static String getGridAddDontPaging(List<CTableConfig> configList, String tableID, String url, String listSourceId, List<CTableConfig> listSource, String menu, String hightlight, String rowsTotal, String typeSelectRow, String groupColumn) {
		String s = "";
		if (typeSelectRow==null||typeSelectRow.equals(""))
		{
			typeSelectRow="singlerow";
		}
		s += "$(document).ready(function () {";
		s += "var theme = getTheme();";
		s += getDataFields(configList);
		s += "var source =";
		s += "{";
		s += "datatype: 'json',";
		s += "datafields: datafields," ;
		s += "url:'" + url + "',";
		s += "pagenum: 0,";
		s += "pagesize: 100";
		s += "};";
		
		s += "var dataadapter = new $.jqx.dataAdapter(source, {";
		s += "loadError: function(xhr, status, error){ alert(error);}";
		s += "});";
		
		// to mau
		if (hightlight != null)
		{
			s += "var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {";
			s += " return '<span style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;text-decoration: underline;\">' + value + '</span>';";   	
			s += "};";
		}
		s += "var numberrenderer = function (row, column, value) {";
		s += "    return '<div style=\"text-align: center; margin-top: 5px;\">' + (1 + value) + '</div>';";
		s += "};";
		if(rowsTotal != null){
			s += "var aggregatesrenderer = function (aggregates, column, element, summaryData) {";
			s += " var renderstring = \"\";";
			s += "    $.each(aggregates, function (key, value) {";
			s += "        var color = 'red';";
			s += "       renderstring += '<div style=\"color: ' + color + '; position: relative; margin: 6px; text-align: right; overflow: hidden;\">' + value + '</div>';";
			s += "    });";
			s += "   renderstring += \"</div>\";";
			s += "    return renderstring;";
			s += "};";
		}
		if (groupColumn!=null && !groupColumn.equals(""))
		{
			s += groupColumn;
		}
		s += getColumns(configList);
		
        //create grid
		s += "$('#" + tableID + "').jqxGrid(";
		s += "{";
		s += "width: '100%',";
		s += "source: dataadapter,";
		s += "theme: getTheme(),";
		s += "showfilterrow: true,";
		s += "filterable: true,";
		s += "sortable: true,";
		s += "pageable: true,";
		s += "autoheight: true,";
		if(rowsTotal != null){
			s += "		showstatusbar: true,";
			s += "		statusbarheight: 25,";
		}
        s += "altrows: true,";
        s += "showaggregates: true,";
		s += "columnsresize: true,";
		s += "columnsreorder: true,";
		s += "scrollmode: 'deferred',";
		s += "selectionmode: '"+typeSelectRow+"',";
		s += "pagesizeoptions: ['50', '100', '200', '300', '400', '500'],";
		s += "columns: column";
		if (groupColumn!=null && !groupColumn.equals(""))
		{
			s += ",columngroups: columngroups";
		}
		s += " });";
		if (menu != null && !menu.equals(""))
		{
			// Create context menu
			s += "	var contextMenu = $('#"+menu+"').jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: getTheme() });";
			s += "	$('#"+tableID+"').on('contextmenu', function () {";
			s += "	return false;";
			s += "	});";
		
			s += "	$('#"+tableID+"').on('rowclick', function (event) {";
			s += "	    if (event.args.rightclick) {";
			s += "	        $('#"+tableID+"').jqxGrid('selectrow', event.args.rowindex);";
			s += "	        var scrollTop = $(window).scrollTop();";
			s += "	        var scrollLeft = $(window).scrollLeft();";
			s += "          contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);";
			s += "	        return false;";
			s += "	     }";
			s += "	 });";
			s += " });";
		}
		if (listSourceId!=null && !listSourceId.equals(""))
		{
			//list source
			s += getListSource(listSource);
			s += "$('#"+listSourceId+"').jqxDropDownList({ checkboxes: true, source: listSource, width: 25, height: 20, theme: getTheme(), dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });";
			// subscribe to the checkChange event.
			s += "    $('#"+listSourceId+"').on('checkChange', function (event) {";
			s += "    	if (event.args) {";
			s += "            var item = event.args.item;";
			s += "            if (item) {";
			s += "               if (item.checked==true)";
			s += "                {";           
			s += "               	$('#"+tableID+"').jqxGrid('showcolumn', item.value);    ";                  
			s += "            	}";
			s += "               else";
			s += "                {";
			s += "         	   		$('#"+tableID+"').jqxGrid('hidecolumn', item.value);";
			s += "                }";
			s += "        	}";
			s += "    	}";
			s += "    });";
		}
		return s;
	}
	
	/**
	 * Tao grid report co phan trang va co chieu cao gioi han
	 * @param configList
	 * @param tableID
	 * @param url
	 * @param listSourceId
	 * @param listSource
	 * @param menu
	 * @param hightlight
	 * @param typeSelectRow
	 * @param groupColumn
	 * @return
	 */
	public static String getGridReportPagingWithHeightLimit(List<CTableConfig> configList, String tableID, String url, String listSourceId, List<CTableConfig> listSource, String menu, String hightlight, String rowsTotal, String typeSelectRow, String groupColumn, String heightLimit) {
		String s = "";
		if (typeSelectRow==null||typeSelectRow.equals(""))
		{
			typeSelectRow="singlerow";
		}
		s += "$(document).ready(function () {";
		s += "var theme = getTheme();";
		s += getDataFields(configList);
		s += "var source =";
		s += "{";
		s += "datatype: 'json',";
		s += "datafields: datafields," ;
		s += "url:'" + url + "',";
		s += "filter: function(){";
		s += "    $('#jqxgrid').jqxGrid('updatebounddata');";
		s += "    },";
		s += "sort: function(){";
		s += "	$('#jqxgrid').jqxGrid('updatebounddata');";
		s += "},";
				s += "root: 'Rows',";
		s += "beforeprocessing: function(data)";
		s += "{";
		s += "	source.totalrecords = data[0].TotalRows;";	
		s += "	$('#strWhere').val(data[1].strWhere);";
		s += "	$('#sortfield').val(data[2].sortfield);";
		s += "	$('#sortorder').val(data[3].sortorder);";
		s += "},";
		s += "pagesize: 100";
		s += "};";
		
		s += "var dataadapter = new $.jqx.dataAdapter(source, {";
		s += "loadError: function(xhr, status, error){ alert(error);}";
		s += "});";
		
		// to mau
		if (hightlight != null)
		{
			s += "var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {";
			s += " return '<span style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;text-decoration: underline;\">' + value + '</span>';";   	
			s += "};";
		}
		s += "var numberrenderer = function (row, column, value) {";
		s += "    return '<div style=\"text-align: center; margin-top: 5px;\">' + (1 + value) + '</div>';";
		s += "};";
		if(rowsTotal != null){
			s += "var aggregatesrenderer = function (aggregates, column, element, summaryData) {";
			s += " var renderstring = \"\";";
			s += "    $.each(aggregates, function (key, value) {";
			s += "        var color = 'red';";
			s += "       renderstring += '<div style=\"color: ' + color + '; position: relative; margin: 6px; text-align: right; overflow: hidden;\">' + value + '</div>';";
			s += "    });";
			s += "   renderstring += \"</div>\";";
			s += "    return renderstring;";
			s += "};";
		}
		if (groupColumn!=null && !groupColumn.equals(""))
		{
			s += groupColumn;
		}
		s += getColumns(configList);
		
        //create grid
		s += "$('#" + tableID + "').jqxGrid(";
		s += "{";
		s += "width: '100%',";
		if (heightLimit!=null && !heightLimit.equals("")){
			s += "height: " + heightLimit + ",";
		}
		s += "source: dataadapter,";
		s += "theme: getTheme(),";
		s += "showfilterrow: true,";
		s += "filterable: true,";
		s += "sortable: true,";
		s += "pageable: true,";
		if(rowsTotal != null){
			s += "		showstatusbar: true,";
			s += "		statusbarheight: 25,";
		}
        s += "altrows: true,";
        s += "showaggregates: true,";
		s += "columnsresize: true,";
		s += "columnsreorder: true,";
		s += "scrollmode: 'deferred',";
		s += "selectionmode: '"+typeSelectRow+"',";
		s += "pagesizeoptions: ['50', '100', '200', '300', '400', '500'],";
		s += "virtualmode: true,";
		s += "rendergridrows: function(obj){";
		s += "	return obj.data; },";
		s += "columns: column";
		if (groupColumn!=null && !groupColumn.equals(""))
		{
			s += ",columngroups: columngroups";
		}
		s += " });";
		if (menu != null && !menu.equals(""))
		{
			// Create context menu
			s += "	var contextMenu = $('#"+menu+"').jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: getTheme() });";
			s += "	$('#"+tableID+"').on('contextmenu', function () {";
			s += "	return false;";
			s += "	});";
		
			s += "	$('#"+tableID+"').on('rowclick', function (event) {";
			s += "	    if (event.args.rightclick) {";
			s += "	        $('#"+tableID+"').jqxGrid('selectrow', event.args.rowindex);";
			s += "	        var scrollTop = $(window).scrollTop();";
			s += "	        var scrollLeft = $(window).scrollLeft();";
			s += "          contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);";
			s += "	        return false;";
			s += "	     }";
			s += "	 });";
			s += " });";
		}
		if (listSourceId!=null && !listSourceId.equals(""))
		{
			//list source
			s += getListSource(listSource);
			s += "$('#"+listSourceId+"').jqxDropDownList({ checkboxes: true, source: listSource, width: 25, height: 20, theme: getTheme(), dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });";
			// subscribe to the checkChange event.
			s += "    $('#"+listSourceId+"').on('checkChange', function (event) {";
			s += "    	if (event.args) {";
			s += "            var item = event.args.item;";
			s += "            if (item) {";
			s += "               if (item.checked==true)";
			s += "                {";           
			s += "               	$('#"+tableID+"').jqxGrid('showcolumn', item.value);    ";                  
			s += "            	}";
			s += "               else";
			s += "                {";
			s += "         	   		$('#"+tableID+"').jqxGrid('hidecolumn', item.value);";
			s += "                }";
			s += "        	}";
			s += "    	}";
			s += "    });";
		}
		return s;
	}
}
