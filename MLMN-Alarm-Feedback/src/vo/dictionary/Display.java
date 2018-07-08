package vo.dictionary;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import vo.CHighlightConfigs;
import vo.CTableConfig;

public class Display {
	
	public static String getTable(List<CTableConfig> cSystemDisplayConfigs, String listSource, String highlight, String tableID, String url, String sourceBSC) {
		String s = "", data = "", columnFormat ="";
		
		
		for(int i = 0; i < cSystemDisplayConfigs.size(); i ++)
		{
			

			if(cSystemDisplayConfigs.get(i).getIsEnable().equals("Y"))
			{
			
				data += "{name:'" + cSystemDisplayConfigs.get(i).getDataField()+ "', type: '" +cSystemDisplayConfigs.get(i).getColumnType() +"'},";
				if(cSystemDisplayConfigs.get(i).getStyle()!= null)
					columnFormat += "{text:'"+ cSystemDisplayConfigs.get(i).getColumnName() + "', datafield: '" + cSystemDisplayConfigs.get(i).getDataField() + "',"+cSystemDisplayConfigs.get(i).getStyle()+"},";
				else
					columnFormat += "{text:'"+ cSystemDisplayConfigs.get(i).getColumnName() + "', datafield: '" + cSystemDisplayConfigs.get(i).getDataField() + "'},";
			}
			
			
		}
	
		data = data.substring(0, data.length()-1);
		columnFormat = columnFormat.substring(0, columnFormat.length() -1);
		
		String str[] = url.split("&");
		
		String code = str[0].substring(str[0].indexOf("=")+1,str[0].length());
		String name = str[1].substring(str[1].lastIndexOf("=")+1, str[1].length());
		s += "$(document).ready(function () {";
		s += "var theme = getTheme('ui-redmond');";
		s += "var source =";
		s += "{";
		s += "datatype: 'json',";
		s += "datafields: [" + data + "]," ;
		s += "url:'" + url + "',";
		s += "filter: function(){";
		s += "$('#" + tableID + "').jqxGrid('updatebounddata');";
		s += "},";
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
		s += highlight;		
		
		
		
        //create grid
		
		s += "$('#" + tableID + "').jqxGrid(";
		s += "{";
		s += "source: dataadapter,";
		s += "theme: theme,";
		s += "filterable: true,";
		s += "sortable: true,";
		s += "width: '100%',";
		s += "pagesizeoptions: ['100', '200', '500'],";
		// group table
		s += " groupable: true,";
		//s += "groups: ['bscid'],";
		s += "columnsresize: true,";
        s += "columnsreorder: true,";
		s += "pageable: true,";
		s += "virtualmode: true,";
		s += "pagesize:100,";
		s += "rendergridrows: function(obj){";
		s += "return obj.data; },";
		s += " columns: [";
		s += columnFormat;
		s += " ]";
		s += " });";
		
		//button export data
		//s += " var exportFileName = \"H_CELL\";";
		s += "$('#excelExport').jqxButton({ height: 25, theme: theme});";
		//s += "$('#csvExport').jqxButton({ height: 25, theme: theme});";
		s += " $('#excelExport').click(function () {";
		s += "$('#jqxgrid').jqxGrid('exportdata', 'xls', 'h_cell');";
		s += "return false;  ";
		s += "});";
		s += " $('#csvExport').click(function () {";
		s += "$('#jqxgrid').jqxGrid('exportdata', 'csv', 'h_cell');";
		s += "return false;  ";
		s += "});";
		// show/hide column
		/*s += listSource;
		s += "$('#jqxlistbox').jqxDropDownList({ checkboxes: true, source: listSource, width: 200, height: 25, theme: theme });";
		s += "$('#jqxlistbox').jqxDropDownList('checkIndex', 0);";
		s += "$('#jqxlistbox').on('checkChange', function (event) {";
		s += "	    	if (event.args) {";
		s += "	            var item = event.args.item;";
		s += "	            if (item) {";
		s += "	               if (item.checked==true)";
		s += "	                {";
		s += "	                	$('#"+tableID+"').jqxGrid('showcolumn', item.value);";                      
		s += "	            	}";
		s += "	               else";
		s += "	                {";
		s += "	         	   		$('#"+tableID+"').jqxGrid('hidecolumn', item.value);";
		s += "	                }";
		s += "	        	}";
		s += "	    	}";
		s += "	    });";*/
		// create date
		
	
		/*	s += "$('#jqxDateTimeInput').jqxDateTimeInput('setRange', '"+sdate+"', '"+edate+"' );";
		s += " $('#jqxDateTimeInput').on('change', function (event) {";
		s += "    var selection = $('#jqxDateTimeInput').jqxDateTimeInput('getRange');";
		s += "    if (selection.from != null) {";
		s += "       window.location = 'list.htm?slastactive='+selection.from.toLocaleDateString()+'&elastactive='+selection.to.toLocaleDateString();";
		s += "  }";
		s += "});";*/
		
		
		// Create context menu
		/*s += " var contextMenu = $('#Menu').jqxMenu({ width: 200, height: 58, autoOpenPopup: false, mode: 'popup', theme: theme });";
		s += "$('#jqxgrid').on('contextmenu', function () {";
		s += "return false;";
		s += "});";
	    // handle context menu clicks.
		s += "$('#Menu').on('itemclick', function (event) {";
		s += "var args = event.args;";
		s += "var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');";
		s += "var row = $('#jqxgrid').jqxGrid('getrowdata', rowindex);";
		s += "if ($.trim($(args).text()) == 'Edit Selected Row') {";
		s += "window.location = 'form.htm?cellid='+row.cellid+'&bscid='+row.bscid;";
		s += "}";
		s += "    else {";
		s += "        var rowid = $('#jqxgrid').jqxGrid('getrowid', rowindex);";
		s += "         $('#jqxgrid').jqxGrid('deleterow', rowid);";
		s += "        window.location = 'delete.htm?cellid='+rowid.cellid+'&bscid='+rowid.bscid;";
	    s += "    }";
		s += "  });";
		s += "$('#jqxgrid').on('rowclick', function (event) {";
		s += "    if (event.args.rightclick) {";
		s += "        $('#jqxgrid').jqxGrid('selectrow', event.args.rowindex);";
		s += "        var scrollTop = $(window).scrollTop();";
		s += "       var scrollLeft = $(window).scrollLeft();";
		s += "         contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);";
		s += "         return false;";
		s += "     }";
		s += " });";
		// save state, load state, add new 
		
		s += "$('#saveState').jqxButton({ theme: theme });";
		s += "$('#loadState').jqxButton({ theme: theme });";
		s += "$('#addNew').jqxButton({ theme: theme });";
		s += "var state = null;";

		// save the current state of jqxGrid.
		s += "$('#saveState').click(function () {";
		s += "state = $('#jqxgrid').jqxGrid('savestate');";
		s += "});";
		
		// load the Grid's state.
		s += "$('#loadState').click(function () {";
		s += "if (state) {";
		s += "$('#jqxgrid').jqxGrid('loadstate', state);";
		s += "}";
		s += "else {";
		s += " $('#jqxgrid').jqxGrid('loadstate');";
		s += "}";
		s += "});";
		// add new row
		s += "$('#addNew').click(function () {";
		s += "window.location = 'form.htm';";
		s += "});";
		
		s += "var listsource = " + sourceBSC;
		s +=" $('#jqxComboBox').jqxComboBox({ source: listsource, selectedIndex: 0, width: '150', height: '25', theme: theme });";
		s +=" $('#input').jqxInput({placeHolder: 'Enter a Cellid', height: 25, width: 250, minLength: 1, theme: theme }); ";
		s += "$('#jqxButton').jqxButton({ theme: theme, width: 110 });";
		if(code.equals("null"))
			s += "$('#jqxComboBox').val('ALL');";
		else
			s += "$('#jqxComboBox').val('"+code+"');";
		if(name.equals("null"))
			s += "$('#input').val('');";
		else
			s += "$('#input').val('"+name+"');";
		
		
		
		s += "$('#jqxButton').on('click', function () {";
		s += "    var selection = $('#jqxDateTimeInput').jqxDateTimeInput('getRange');";
		s += "var item = $('#jqxComboBox').jqxComboBox('getSelectedItem');";
		s += "var values = $('#input').jqxInput('val');";
		s += "    if (selection.from != null) {";
		
		s += "       window.location = 'list.htm?slastactive='+selection.from.toLocaleDateString()+'&elastactive='+selection.to.toLocaleDateString()+'&bsc='+item.value+'&cell='+values;";
		s += "  }";
		s += "});";*/
		
		s += " });";
		
		
		
		return s;
	}
	public static String filter(HttpServletRequest request)
	{
		String where = "", tmpdatafield ="", tmpfilteroperator="";
		int filterscount = 0;
		
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
		return where;
	}
	public static String getListSource(List<CTableConfig> configList) {
		String listSource =" var listSource = [";
		String condition="";
		for (CTableConfig config : configList) {
			listSource +=condition+"          {label:'"+config.getColumnName()+"', value: '"+config.getDataField()+"', checked: true }";	
			condition=",";
		}
		listSource +="        ];";
		System.out.println(listSource);
		return listSource;
	}
	/* To mau gia tri 1 cot cua grid*/
	public static String highLightCol(List<CHighlightConfigs> highlightConfigList) {
		String s="";
		s=" var cellsrenderer  = function (row, columnfield, value, defaulthtml, columnproperties) {" ;
		if (highlightConfigList.size()>0)
		{
			s += 	"var  valueKey = $('#jqxgrid').jqxGrid('getrowdata', row)."+highlightConfigList.get(0).getKey()+";";
			for (CHighlightConfigs highlightConfig : highlightConfigList) {
				
				s += "if( valueKey"+ highlightConfig.getFormula() + highlightConfig.getValue()+"){";
				
				s +="	         return '<div style=\"width:100%;height:100%; float: ' + columnproperties.cellsalign + '; " + highlightConfig.getStyle().replaceAll("'", "") + "\">' + value + '</div>';";
				s +="	}";
			}
			
		}
		s += "};";
		System.out.println(s);
		return s;
	}
	
}
