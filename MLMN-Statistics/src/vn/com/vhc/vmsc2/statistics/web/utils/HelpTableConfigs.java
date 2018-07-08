package vn.com.vhc.vmsc2.statistics.web.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.TableConfig;

public class HelpTableConfigs {

	/*Cấu hinh thuoc tinh column trong grid*/
	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	public static String getColumns(List<TableConfig> configList) {
		String constructor="var column = [{pinned: true, text: 'No.', columntype: 'number', filterable: false, cellsrenderer: numberrenderer, width: 30,align: 'center' }";
		String condition=",";
		for (TableConfig config : configList) {
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
			constructor +=" }";
		}
		constructor +="	] ;";
		//System.out.println(constructor);
		return constructor;
	}
	
	/* Cau hinh datafield cho grid*/
	public static String getDataFields(List<TableConfig> configList) {
		String datafields =" var datafields = [";
		String condition="";
		for (TableConfig config : configList) {
			datafields +=condition+"          { name: '"+config.getDataField()+"', type: '"+config.getDataType()+"' }";	
			condition=",";
		}
		datafields +="        ];";
		//System.out.println(datafields);
		return datafields;
	}
	
	/* Cau hinh  cac cot an hien cua Grid trong dropdowlist*/
	public static String getListSource(List<TableConfig> configList) {
		String listSource =" var listSource = [";
		String condition="";
		for (TableConfig config : configList) {
			listSource +=condition+"          {label:'"+config.getColumnName()+"', value: '"+config.getDataField()+"', checked: " + (config.getIsAble().equals("Y")?"true":"false") + " }";	
			condition=",";
		}
		listSource +="        ];";
		System.out.println(listSource);
		return listSource;
	}
	
	/* To mau gia tri 1 cot cua grid
		/* To mau gia tri 1 cot cua grid*/
	public static String highLightCol(List<HighlightConfig> highlightConfigList, String tableID) {
		String s="";
		s=" var cellsrenderer  = function (row, columnfield, value, defaulthtml, columnproperties) {" ;
		if (highlightConfigList.size()>0)
		{
			s += 	"var  valueKey = $('#"+tableID+"').jqxGrid('getrowdata', row)."+highlightConfigList.get(0).getKey()+";";
			for (HighlightConfig highlightConfig : highlightConfigList) {
				
				s += "if( valueKey"+ highlightConfig.getFormula() + highlightConfig.getValue()+"){";
				
				s +="	         return '<div style=\"width:100%;height:100%; float: ' + columnproperties.cellsalign + '; " + highlightConfig.getStyle().replaceAll("'", "") + "\">' + value + '</div>';";
				s +="	}";
			}
			
		}
		s += "};";
		
		return s;
	}
	
	/* To mau gia tri 1 dong cua grid
	public static String highLightRow(List<CHighlightConfigs> highlightConfigList) {
		String s="";
		s=" var colorrowrenderer = function (row, columnfield, value, defaulthtml, columnproperties) {" ;
		if (highlightConfigList.size()>0)
		{
			s += 	"var  valueKey = $('#"+tableID+"').jqxGrid('getrowdata', row)."+highlightConfigList.get(0).getKey()+";";
			s += 	"alert(row);";
			for (CHighlightConfigs highlightConfig : highlightConfigList) {
				if (highlightConfig.getValue()!=null)
				{
					s += "if( valueKey"+ highlightConfig.getFormula() +"'"+ highlightConfig.getValue().trim() +"'"+"){";
				}
				else
				{
					s += "if( valueKey"+ highlightConfig.getFormula() + highlightConfig.getValue()+"){";
				}
				s +="	         return '<div style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; " + highlightConfig.getStyle().replaceAll("'", "") + "\">' + value + '</div>';";
				s +="	}";
			}
			
		}
		s += "};";
		System.out.println(s);
		return s;
	}*/
	//Lay dieu kien tim kiem tren Grid
	public static String filter(HttpServletRequest request)
	{
		String where = "", tmpdatafield ="", tmpfilteroperator="";
		int filterscount = 0;
		if (request.getParameter("filterscount")!=null)
		{
			filterscount = Integer.parseInt(request.getParameter("filterscount"));
			if(filterscount > 0)
			{
				 where = "(";
				 tmpdatafield = "";
				 tmpfilteroperator="0";
				 for (int i=0; i < filterscount; i++)
				    {
						// get the filter's value.
						String filtervalue = request.getParameter("filtervalue"+i);
						// get the filter's condition.
						String  filtercondition = request.getParameter("filtercondition"+i);
						// get the filter's column.
						String  filterdatafield =request.getParameter("filterdatafield"+i);
						// get the filter's operator.
						String filteroperator =request.getParameter("filteroperator"+i); 
						
						if (tmpdatafield.equals("") == true)
						{
							tmpdatafield = filterdatafield;			
						}
						else if (tmpdatafield.equals(filterdatafield) == false)
						{
							where += ")AND(";
						}
						else if (tmpdatafield.equals(filterdatafield) == true)
						{
							if (tmpfilteroperator.equals("0")== true)
							{
								where += " AND ";
							}
							else where += " OR ";	
						}
						if(filtercondition.equals("CONTAINS"))
							where += " UPPER("+filterdatafield+") LIKE UPPER('%" + filtervalue +"%')";
						if(filtercondition.equals("DOES_NOT_CONTAIN"))
							where += " UPPER(" +filterdatafield+ ") NOT LIKE UPPER('%"  + filtervalue +"%')";
						if(filtercondition.equals("EQUAL"))
							where += " UPPER(" +filterdatafield+ ") = UPPER('"  + filtervalue +"')";
						if(filtercondition.equals("NOT_EQUAL"))
							where += " UPPER(" +filterdatafield+ ") <> UPPER('"  + filtervalue +"')";
						if(filtercondition.equals("GREATER_THAN"))
							where += " " +filterdatafield+ " > '"  + filtervalue +"'";
						if(filtercondition.equals("LESS_THAN"))
							where += " " +filterdatafield+ " < '"  + filtervalue +"'";
						if(filtercondition.equals("GREATER_THAN_OR_EQUAL"))
							where += " " +filterdatafield+ " >= '"  + filtervalue +"'";
						if(filtercondition.equals("LESS_THAN_OR_EQUAL"))
							where += " " +filterdatafield+ " <= '"  + filtervalue +"'";
						if(filtercondition.equals("STARTS_WITH"))
							where += " UPPER(" +filterdatafield+ " LIKE UPPER('"  + filtervalue +"%')";
						if(filtercondition.equals("ENDS_WITH"))
							where += " UPPER(" +filterdatafield+ ") LIKE UPPER('%"  + filtervalue +"')";
						if (i == filterscount - 1)
						{
							where += ")";
						}
						
					tmpfilteroperator =filteroperator;
					tmpdatafield = filterdatafield;			
					}
				}
			filterscount = Integer.parseInt(request.getParameter("filterscount"));
			}
		
		return where;
	}
	
	public static String getGridPagingReport(List<TableConfig> configList, String tableID, String height, String url, String listSource, String menu, String hightlight) {
		String s = "";

		s += "$(document).ready(function () {";
		s += "var theme = getTheme();";
		s += getDataFields(configList);
		s += "var source =";
		s += "{";
		s += "datatype: 'json',";
		s += "datafields: datafields," ;
		s += "url:'" + url + "',";
		
		s += "sort: function(){";
		s += "$('#" + tableID + "').jqxGrid('updatebounddata');";
		s += "},";
		s += "root: 'Rows',";
		s += "beforeprocessing: function(data){";
		s += "source.totalrecords = data[0].TotalRows;";
		s += "}";
		s += "};";
		
		s += "var dataadapter = new $.jqx.dataAdapter(source, {";
		s += "loadError: function(xhr, status, error){ alert(error);}";
		s += "});";
		// to mau
				s += hightlight;	
		
		s += "   var numberrenderer = function (row, column, value) {";
		s += "        return '<div style=\"text-align: center; margin-top: 5px;\">' + (1 + value) + '</div>';";
		s += "    };";
		s += getColumns(configList);
        //create grid
		
		s += "$('#" + tableID + "').jqxGrid(";
		s += "{";
		s += "source: dataadapter,";
		s += "theme: theme,";
		s += "sortable: true,";
		s += "width: '100%',";
		//autorowheight: true,
		if(!height.equals(""))
			s += "height: 400,";
		else
			s += "      autoheight: true,";
		s += "pagesizeoptions: ['50', '100', '200', '300', '400', '500'],";
		// group table
		s += "columnsresize: true,";
        s += "columnsreorder: true,";
		s += "pageable: true,";
		s += "virtualmode: true," ;
		s += "pagesize:100,";
		s += "rendergridrows: function(obj){";
		s += "return obj.data; },";
		s += " columns: column";
		s += " });";
		// Create context menu
		s += "	var contextMenu = $('#"+menu+"').jqxMenu({ width: 200, height: 60, autoOpenPopup: false, mode: 'popup', theme: theme });";
		s += "	$('#"+tableID+"').on('contextmenu', function () {";
		s += "	return false;";
		s += "	});";
		// handle context menu clicks.
		s += "	var exportFileName =  '"+tableID+"';";
		s += "	$('#"+menu+"').on('itemclick', function (event) {";
		s += "	var args = event.args;";
		s += "	var rowindex = $('#"+tableID+"').jqxGrid('getselectedrowindex');";
		s += "	var row = $('#"+tableID+"').jqxGrid('getrowdata', rowindex);";
		// export data
		//s += "alert($.trim($(args).text()));";
		s += "	if ($.trim($(args).text()) == 'Export Excel') {";
		s += "		$('#"+tableID+"').jqxGrid('exportdata', 'xls', exportFileName);";
		s += "		}";
		s += "	if ($.trim($(args).text()) == 'Hourly Report') {";
		s += "var dt = new Date(row.day);";
		s += "var dayt = dateToYMD(dt); ";
		//s += "alert(dayt);";
		s += " window.open('../hr/detail.htm?cellid='+row.cellid+'&bscid='+row.bscid+'&endDate='+dayt,'_blank');";
		s += "		}";
		
		s += "	  });";
		
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
		
		s += "function dateToYMD(date) {";
		s += "var d = date.getDate();";
		s += "var m = date.getMonth() + 1;";
		s += "var y = date.getFullYear();";
		s += "return '' + (d <= 9 ? '0' + d : d) + '/' + (m<=9 ? '0' + m : m) + '/' + y;";
		s += "}";
		return s;
	}
	public static String getGridReport(List<TableConfig> configList, String tableID, String height, String data, String listSource, String menu) {
		String s = "";
		s += "$(document).ready(function () {";
		s += "var theme = getTheme();";
		//Get URL for get data
		s += "var data = '"+data+"';";
		//s += "url = url.replace(/amp;/g,'');";
		// prepare the data source
		s += getDataFields(configList);
		s += "var source =";
		s += "{";
			s += "datatype: 'json',";
			s += "datafields: datafields,";
			s += "localdata: data,";
			s += "pagenum: 0,";
			s += "pagesize: 100";
		s += "};";
		s += "var dataAdapter = new $.jqx.dataAdapter(source);";
		
		/*s+= " sort: function()";
		s+= " {";
	   // update the grid and send a request to the server.
		s+= " $('#"+tableID+"').jqxGrid('updatebounddata');";
		s+= " } ";
		s+= "root: 'Rows',";
		s+= "beforeprocessing: function(data)";
		s+= "{		";
			s+= "source.totalrecords = data[0].TotalRows;		";			
		s+= "}";*/
		// renderer for grid cells. 
		s += "   var numberrenderer = function (row, column, value) {";
		s += "        return '<div style=\"text-align: center; margin-top: 5px;\">' + (1 + value) + '</div>';";
		s += "    };";
		
		 
		    // filter
		s += getColumns(configList);
		s += "    $('#"+tableID+"').jqxGrid(";
		s += "    {";
		s += "   	width: '100%',";
		s += "     	source: dataAdapter,";
		s += "      theme: theme,";
		s += "      showfilterrow: true,";
		s += "      filterable: true,";
		s += "      sortable: true,";
		s += "      pageable: true,";
		//autorowheight: true,
		if(!height.equals(""))
			s += "height: 400,";
		else
			s += "      autoheight: true,";
		
		s += "      altrows: true,";
		s += "      columnsresize: true,";
		s += "      columnsreorder: true,";
		s += "      scrollmode: 'deferred',";
		s += "      pagesizeoptions: ['50', '100', '200', '300', '400', '500'],";
		//selectionmode: 'multiplecellsextended',
		s += "      columns: column";
		s += "    });";
		
		//dropdownlist an hien cot
		//call funtion add listSource	
		s += listSource;
		
		// Create context menu
		s += "	var contextMenu = $('#"+menu+"').jqxMenu({ width: 200, height: 30, autoOpenPopup: false, mode: 'popup', theme: theme });";
		s += "	$('#"+tableID+"').on('contextmenu', function () {";
		s += "	return false;";
		s += "	});";
		// handle context menu clicks.
		s += "	var exportFileName =  '"+tableID+"';";
		s += "	$('#"+menu+"').on('itemclick', function (event) {";
		s += "	var args = event.args;";
		s += "	var rowindex = $('#"+tableID+"').jqxGrid('getselectedrowindex');";
		s += "	var row = $('#"+tableID+"').jqxGrid('getrowdata', rowindex);";
		// export data
		s += "alert($.trim($(args).text()));";
		s += "	if ($.trim($(args).text()) == 'Export Excel') {";
		s += "		$('#"+tableID+"').jqxGrid('exportdata', 'xls', exportFileName);";
		s += "		}";
		
		s += "	  });";
		
		s += "	$('#"+tableID+"').on('rowclick', function (event) {";
		s += "	    if (event.args.rightclick) {";
		s += "	        $('#"+tableID+"').jqxGrid('selectrow', event.args.rowindex);";
		s += "	        var scrollTop = $(window).scrollTop();";
		s += "	        var scrollLeft = $(window).scrollLeft();";
		s += "          contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);";
		s += "	        return false;";
		s += "	     }";
		s += "	 });";
		
		s += "	});";
		return s;
	}
	public static String areaChart(String id, String title, String description, List<String> categories, List<String> series, String maxValue, String unitInterval, String dataField)
	{
		String s = "";
		
		String datafields = "[{name:'date'},";
		String sampleData = "[";
		String sr = "[";
		for (String serie: series) {
			String[] tmp = serie.split("-");
			datafields += "{name:'" + tmp[0] + "'},";
			sr += "{dataField:'" + tmp[0] + "',displayText:'"+tmp[1]+"'},";
			
		}
		for (String categorie: categories) {
			sampleData += "{" + categorie + "},";
		}
		datafields = datafields.substring(0,datafields.length()-1) + "]";
		sr = sr.substring(0,sr.length()-1) + "]";
		sampleData = sampleData.substring(0,sampleData.length()-1) + "];";
		
		s+= "$(document).ready(function () {";
            // prepare the data
		 s+= "var sampleData =";
		 s+= sampleData;
			        s+= "var source =";
					s+= "{";
					s+= "datatype: 'tab',";
					s+= "datafields: "+datafields+"";
				//	s+= "url: '"+url+"'";
					s+= "};";
            	//	s+= "var dataAdapter = new $.jqx.dataAdapter(source, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading \"' + source.url + '\" : ' + error); } });";
            // prepare jqxChart settings
            		s+= "var settings = {";
            		s+= "title: '"+title+"',";
					s+= "description: '"+description+"',";
					s+= "enableAnimations: true,";
					s+= "showLegend: true,";
					s+= "padding: { left: 10, top: 5, right: 10, bottom: 5 },";
					s+= "titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },";
					s+= "source: sampleData,";
					s+= "categoryAxis:";
					s+= "{";
					s+= "text: 'Category Axis',";
					s+= "textRotationAngle: 0,";
					s+= "dataField: '"+dataField+"',";
					s+= "formatFunction: function (value) {";
					s+= "return value.getDate();";
					s+= "},";
					s+= "toolTipFormatFunction: function (value) {";
					s+= "var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];";
					s+= "return value.getDate() + '-' + months[value.getMonth()];";
					s+= "},";
					s+= "type: 'date',";
					s+= "baseUnit: 'day',";
					s+= "showTickMarks: true,";
					s+= "tickMarksInterval: 1,";
					s+= "tickMarksColor: '#888888'," ;
					s+= "unitInterval: 1,";
					s+= "showGridLines: true,";
					s+= "gridLinesInterval: 3,";
					s+= "gridLinesColor: '#888888',";
					s+= "valuesOnTicks: true";
					s+= "},";
					s+= "colorScheme: 'scheme03',";
					s+= "seriesGroups:";
					s+= "[";
					s+= "{";
						s+= " type: 'stackedarea',";
						s+= "valueAxis:";
						s+= "{";
							s+= "unitInterval: "+unitInterval+",";
							s+= "minValue: 0,";
							s+= "maxValue: "+maxValue+",";
							s+= "displayValueAxis: true,";
							s+= "description: 'Daily visitors',";
							//descriptionClass: 'css-class-name',
							s+= "axisSize: 'auto',";
							s+= "tickMarksColor: '#888888'";
						s+= "},";
						s+= "series: "+sr+"";
					s+= "}";
					s+= " ]";
					s+= "};";
					// setup the chart
					s+= "$('#"+id+"').jqxChart(settings);";
					s+= "});";
	return s;	
	}
	public static String basicColumnChart(String id, String title, String description, List<String> categories, List<String> series, String maxValue, String unitInterval, String dataField)
	{
		String s = "";
		
		
		String sampleData = "[";
		String sr = "[";
		for (String serie: series) {
			String[] tmp = serie.split("-");
			
			sr += "{dataField:'" + tmp[0] + "',displayText:'"+tmp[1]+"'},";
			
		}
		for (String categorie: categories) {
			sampleData += "{" + categorie + "},";
		}
	
		sr = sr.substring(0,sr.length()-1) + "]";
		sampleData = sampleData.substring(0,sampleData.length()-1) + "];";
		
		s+= "$(document).ready(function () {";
            // prepare the data
		 s+= "var sampleData =";
		 s+= sampleData;
         s+= "var settings = {";
         s+= "title: '"+title+"',";
		 s+= "description: '"+description+"',";
		 s+= "enableAnimations: true,";
		 s+= "showLegend: true,";
		 s+= "padding: { left: 10, top: 5, right: 10, bottom: 5 },";
		 s+= "titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },";
		 s+= "source: sampleData,";
		 s+= "categoryAxis:";
		 s+= "{";
		 s+= "dataField: '"+dataField+"',";
		 s+= "showGridLines: true,";
		 s+= "gridLinesInterval: 3,";
		 s+= "gridLinesColor: '#888888',";
		 s+= "},";
		 s+= "colorScheme: 'scheme01',";
					s+= "seriesGroups:";
					s+= "[";
					s+= "{";
						s+= " type: 'column',";
						s+= "valueAxis:";
						s+= "{";
							s+= "unitInterval: "+unitInterval+",";
							s+= "minValue: 0,";
							s+= "maxValue: 100,";
							s+= "displayValueAxis: true,";
							//descriptionClass: 'css-class-name',
							s+= "axisSize: 'auto',";
							s+= "tickMarksColor: '#888888'";
						s+= "},";
						s+= "series: "+sr+"";
					s+= "}";
					s+= " ]";
					s+= "};";
					// setup the chart
					s+= "$('#"+id+"').jqxChart(settings);";
					s+= "});";
	return s;	
	}
	public static String lineChart(String id, String title, String description, List<String> categories, List<String> series, String maxValue, String unitInterval, String dataField)
	{
		String s = "";
		
		String datafields = "[{name:'date'},";
		String sampleData = "[";
		String sr = "[";
		for (String serie: series) {
			String[] tmp = serie.split("-");
			datafields += "{name:'" + tmp[0] + "'},";
			sr += "{dataField:'" + tmp[0] + "',displayText:'"+tmp[1]+"'},";
			
		}
		for (String categorie: categories) {
			sampleData += "{" + categorie + "},";
		}
		datafields = datafields.substring(0,datafields.length()-1) + "]";
		sr = sr.substring(0,sr.length()-1) + "]";
		if(sampleData.length() > 1)
			sampleData = sampleData.substring(0,sampleData.length()-1) + "];";
		else
			sampleData += "];"; 
		
		s+= "$(document).ready(function () {";
            // prepare the data
		 s+= "var sampleData =";
		 s+= sampleData;
			        s+= "var source =";
					s+= "{";
					s+= "datatype: 'tab',";
					s+= "datafields: "+datafields+"";
					s+= "};";
            		s+= "var settings = {";
            		s+= "title: '"+title+"',";
					s+= "description: '"+description+"',";
					s+= "enableAnimations: true,";
					s+= "showLegend: true,";
					s+= "padding: { left: 10, top: 5, right: 10, bottom: 5 },";
					s+= "titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },";
					s+= "source: sampleData,";
					s+= "categoryAxis:";
					s+= "{";
					s+= "text: 'Category Axis',";
					s+= "textRotationAngle: 0,";
					s+= "dataField: '"+dataField+"',";
					s+= "formatFunction: function (value) {";
					s+= "return value.getDate();";
					s+= "},";
					s+= "toolTipFormatFunction: function (value) {";
					s+= "var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];";
					s+= "return value.getDate() + '-' + months[value.getMonth()];";
					s+= "},";
					s+= "type: 'date',";
					s+= "baseUnit: 'day',";
					s+= "showTickMarks: true,";
					s+= "tickMarksInterval: 1,";
					s+= "tickMarksColor: '#888888'," ;
					s+= "unitInterval: 1,";
					s+= "showGridLines: true,";
					s+= "gridLinesInterval: 3,";
					s+= "gridLinesColor: '#888888',";
					s+= "valuesOnTicks: true";
					s+= "},";
					s+= "colorScheme: 'scheme01',";
					s+= "seriesGroups:";
					s+= "[";
					s+= "{";
						s+= " type: 'line',";
						s+= "valueAxis:";
						s+= "{";
							s+= "unitInterval: "+unitInterval+",";
							s+= "minValue: 0,";
							s+= "maxValue: "+maxValue+",";
							s+= "displayValueAxis: true,";
							s+= "description: 'Daily visitors',";
							//descriptionClass: 'css-class-name',
							s+= "axisSize: 'auto',";
							s+= "tickMarksColor: '#888888'";
						s+= "},";
						s+= "series: "+sr+"";
					s+= "}";
					s+= " ]";
					s+= "};";
					// setup the chart
					s+= "$('#"+id+"').jqxChart(settings);";
					s+= "});";
	return s;	
	}
	
	public static String filterDate(Date startDate, Date endDate, String region, String province, String bscid, String cellid)
	{
		String url = "", temp = "";
		if (startDate != null)
		{
			url += "startDate="+df.format(startDate);
			temp ="&";
		}
		if (endDate != null)
		{
			url +=temp+ "endDate="+df.format(endDate);
			temp ="&";
		}
		if (region != null)
		{
			url += temp+"region="+region;
			temp ="&";
		}
		if (province != null)
		{
			url += temp+"province="+province;
			temp ="&";
		}	
		if (bscid != null)
		{
			url += temp+"bscid="+bscid;
			temp ="&";
		}
		if (cellid != null)
		{
			url += temp+"cellid="+cellid;
			temp ="&";
		}
		
		if(url.equals(""))
			url = "data.htm";
		else
			url = "data.htm?" + url;
		
	return url;	
	}
	public static String filterWeek(Integer startWeek,Integer startYear,Integer endWeek, Integer endYear, String region, String province, String bscid, String cellid)
	{
		String url = "", temp = "";
		if (startWeek != null)
		{
			url += "startWeek="+startWeek;
			temp ="&";
		}
		if (startYear != null)
		{
			url +=temp+ "startYear="+startYear;
			temp ="&";
		}
		if (endWeek != null)
		{
			url += temp+ "endWeek="+endWeek;
			temp ="&";
		}
		if (endYear != null)
		{
			url +=temp+ "endYear="+endYear;
			temp ="&";
		}
		if (region != null)
		{
			url += temp+"region="+region;
			temp ="&";
		}
		if (province != null)
		{
			url += temp+"province="+province;
			temp ="&";
		}	
		if (bscid != null)
		{
			url += temp+"bscid="+bscid;
			temp ="&";
		}
		if (cellid != null)
		{
			url += temp+"cellid="+cellid;
			temp ="&";
		}
		
		if(url.equals(""))
			url = "data.htm";
		else
			url = "data.htm?" + url;
		
	return url;	
	}
	public static String filterMonth(Integer startMonth,Integer startYear,Integer endMonth, Integer endYear, String region, String province, String bscid, String cellid)
	{
		String url = "", temp = "";
		if (startMonth != null)
		{
			url += "startMonth="+startMonth;
			temp ="&";
		}
		if (startYear != null)
		{
			url +=temp+ "startYear="+startYear;
			temp ="&";
		}
		if (endMonth != null)
		{
			url += temp+ "endMonth="+endMonth;
			temp ="&";
		}
		if (endYear != null)
		{
			url +=temp+ "endYear="+endYear;
			temp ="&";
		}
		if (region != null)
		{
			url += temp+"region="+region;
			temp ="&";
		}
		if (province != null)
		{
			url += temp+"province="+province;
			temp ="&";
		}	
		if (bscid != null)
		{
			url += temp+"bscid="+bscid;
			temp ="&";
		}
		if (cellid != null)
		{
			url += temp+"cellid="+cellid;
			temp ="&";
		}
		
		if(url.equals(""))
			url = "data.htm";
		else
			url = "data.htm?" + url;
		
	return url;	
	}
	public static String filterHour(Date startDate, Date endDate, String startHour, String endHour, String bscid, String cellid)
	{
		String url = "", temp = "";
		if (startDate != null)
		{
			url += "startDate="+df.format(startDate);
			temp ="&";
		}
		if (endDate != null)
		{
			url +=temp+ "endDate="+df.format(endDate);
			temp ="&";
		}
		if (startHour != null)
		{
			url += temp+"startHour="+startHour;
			temp ="&";
		}
		if (endHour != null)
		{
			url += temp+"endHour="+endHour;
			temp ="&";
		}	
		if (bscid != null)
		{
			url += temp+"bscid="+bscid;
			temp ="&";
		}
		if (cellid != null)
		{
			url += temp+"cellid="+cellid;
			temp ="&";
		}
		
		if(url.equals(""))
			url = "data.htm";
		else
			url = "data.htm?" + url;
		
	return url;	
	}

}
