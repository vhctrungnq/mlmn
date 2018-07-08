package vo.alarm.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import vo.CHighlightConfigs;
import vo.CTableConfig;

public class HelpTableConfigs {

	/*Cấu hinh thuoc tinh column trong grid*/
	
	public static String getColumns(List<CTableConfig> configList) {
		String constructor="var column = [{pinned: true, text: 'No.',datafield: '', columntype: 'number', filterable: false, exportable: false, cellsrenderer: numberrenderer, width: 30,align: 'center' }";
		//	String constructor="var column = [{text: 'No.',datafield: '', columntype: 'number', filterable: false, exportable: false, cellsrenderer: numberrenderer, width: 30,align: 'center' }";
		
		//String constructor="var column = [";
		
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
			if (config.getAggregates()!=null && !config.getAggregates().equals(""))
			{
				constructor +=", aggregates: "+ config.getAggregates();
			}
			constructor +=" }";
		}
		constructor +="	] ;";
		//System.out.println(constructor);
		return constructor;
	}
	
	/* Cau hinh datafield cho grid*/
	public static String getDataFields(List<CTableConfig> configList) {
		String datafields =" var datafields = [";
		String condition="";
		for (CTableConfig config : configList) {
			datafields +=condition+"          { name: '"+config.getDataField()+"', type: '"+config.getDataType()+"' }";	
			condition=",";
		}
		datafields +="        ];";
		//System.out.println(datafields);
		return datafields;
	}
	
	/* Cau hinh  cac cot an hien cua Grid trong dropdowlist*/
	public static String getListSource(List<CTableConfig> configList) {
		String listSource =" var listSource = [";
		String condition="";
		for (CTableConfig config : configList) {
			listSource +=condition+"          {label:'"+config.getColumnName()+"', value: '"+config.getDataField()+"', checked: " + (config.getIsEnable().equals("Y")?"true":"false") + " }";	
			condition=",";
		}
		listSource +="        ];";
		//System.out.println(listSource);
		return listSource;
	}
	
	/* To mau gia tri 1 cot cua grid*/
	public static String highLightCol(List<CHighlightConfigs> highlightConfigList) {
		String s="";
		s=" var colorcolrenderer = function (row, columnfield, value, defaulthtml, columnproperties) {" ;
		if (highlightConfigList.size()>0)
		{
			s += 	"var  valueKey = $('#jqxgrid').jqxGrid('getrowdata', row)."+highlightConfigList.get(0).getKey()+";";
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
		//System.out.println(s);
		return s;
	}
	
	/* To mau gia tri 1 dong cua grid*/
	/*public static String highLightRow(List<CHighlightConfigs> highlightConfigList,String gridName) {
		String s="";
		s=" var colorrowrenderer = function (row, columnfield, value, defaulthtml, columnproperties) {" ;
		if (highlightConfigList.size()>0)
		{
			s += 	"var  valueKey = $('#"+gridName+"').jqxGrid('getrowdata', row)."+highlightConfigList.get(0).getKey()+";";
			//s += 	"alert(valueKey);";
			
			for (CHighlightConfigs highlightConfig : highlightConfigList) {
				if (highlightConfig.getValue()!=null&&!highlightConfig.getValue().equals("null"))
				{
					s += "if( valueKey"+ highlightConfig.getFormula() +"'"+ highlightConfig.getValue().trim() +"'"+"){";
				}
				else
				{
					s += "if(valueKey!='undefined' && valueKey"+ highlightConfig.getFormula() + highlightConfig.getValue()+"){";
				}
				
				s +=" $(\"#row\"+row+\""+gridName+"\").css({"+highlightConfig.getStyle()+"});";
				//s +=" $(\"#row\"+row+\""+gridName+"\").addClass(\"blue\");";
				//s +="	         return '<div style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; " + highlightConfig.getStyle().replaceAll("'", "") + "\">' + value + '</div>';";
				s +="	}";
			}
			
		}
		s += "};";
		//System.out.println(s);
		return s;
	}*/
	public static String highLightRow(List<CHighlightConfigs> highlightConfigList,String gridName,String rowrender) {
		String s="";
		//s=" var colorrowrenderer = function (row, column, value, defaultHtml) {" ;
		s=" var "+rowrender+" = function (row, column, value, defaultHtml) {" ;
		if (highlightConfigList.size()>0)
		{
			s += 	"var  valueKey = $('#"+gridName+"').jqxGrid('getrowdata', row)."+highlightConfigList.get(0).getKey()+";";
			//s += 	"alert(valueKey);";
			
			for (CHighlightConfigs highlightConfig : highlightConfigList) {
				if (highlightConfig.getValue()!=null&&!highlightConfig.getValue().equals("null"))
				{
					s += "if( valueKey"+ highlightConfig.getFormula() +"'"+ highlightConfig.getValue().trim() +"'"+"){";
				}
				else
				{
					s += "if(valueKey!='undefined' && valueKey"+ highlightConfig.getFormula() + highlightConfig.getValue()+"){";
				}
				s +=" $(\"#row\"+row+\""+gridName+"\").css({"+highlightConfig.getStyle()+"});";
				s +="return $(\"#row\"+row+\""+gridName+"\").outerHTML;}";
				
				
			}
			
		}
		s +=" return defaultHtml;";
		s += "};";
		System.out.println(s);
		return s;
	}
	
	public static String highLightOption(List<CHighlightConfigs> highlightConfigList,String gridName,String nameRender) {
		String s="";
		s="  var "+nameRender+" = function (row, columnfield, value, defaulthtml, columnproperties) {" ;
		//s += 	"alert(valueKey);";
		//s += 	"alert(value);";
		//s += 	"alert(columnfield);";
		s +=  "var  valueKey = ''; ";
		if (highlightConfigList.size()>0)
		{
			for (CHighlightConfigs highlightConfig : highlightConfigList) {
				s +=  "valueKey = $('#"+gridName+"').jqxGrid('getrowdata', row)."+highlightConfig.getKey()+";";
				//kieu to mot o tren grid
				if (highlightConfig.getTYPE_STYLE().equals("CELL")){
					if (highlightConfig.getValue()!=null&&!highlightConfig.getValue().equals("null"))
					{
						s += "if( valueKey"+ highlightConfig.getFormula() +"'"+ highlightConfig.getValue().trim() +"'"+"){";
					}
					else
					{
						s += "if(valueKey!='undefined' && valueKey"+ highlightConfig.getFormula() + highlightConfig.getValue()+"){";
					}
					s +="return '<span style=\"margin: 4px;float: ' + columnproperties.cellsalign + '; "+highlightConfig.getStyle()+"\">' + value + '</span>'; }"; 	
				}
				else //to mot dong tren grid
				{	
					if (highlightConfig.getValue()!=null&&!highlightConfig.getValue().equals("null"))
					{
						s += "if( valueKey"+ highlightConfig.getFormula() +"'"+ highlightConfig.getValue().trim() +"'"+"){";
					}
					else
					{
						s += "if(valueKey!='undefined' && valueKey"+ highlightConfig.getFormula() + highlightConfig.getValue()+"){";
					}
					s +=" $(\"#row\"+row+\""+gridName+"\").css({"+highlightConfig.getStyle()+"});";
					s +="return $(\"#row\"+row+\""+gridName+"\").outerHTML;}";
				}
				
			}
			
		}
		s +=" return defaulthtml;";
		s += "};";
		System.out.println(s);
		return s;
	}
	
	
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
							if(filtercondition.equals("GREATER_THAN"))
								where += " " +filterdatafield+ " > '"  + filtervalue +"'";
							if(filtercondition.equals("LESS_THAN"))
								where += " " +filterdatafield+ " < '"  + filtervalue +"'";
							if(filtercondition.equals("NULL"))
								where += "  " +filterdatafield+ " IS NULL ";
							if(filtercondition.equals("NOT_NULL"))
								where += "  " +filterdatafield+ " IS NOT NULL ";
							if(filtercondition.equals("EQUAL"))
								where += " UPPER(" +filterdatafield+ ") = UPPER('"  + filtervalue +"')";
							if(filtercondition.equals("NOT_EQUAL"))
								where += " UPPER(" +filterdatafield+ ") <> UPPER('"  + filtervalue +"')";
							if(filtercondition.equals("DATE_GREATER_THAN") || filtercondition.equals("GREATER_THAN_OR_EQUAL"))
							{
								if (filtervalue.length()!=19&&filtervalue.length()!=10&&filtervalue.length()!=16)
								{
									where += "1!=1";
								}
								if (filtervalue.length()==19)
								{
									where += "TRUNC(" +filterdatafield+ ",'MI') > TO_DATE('"  + filtervalue +"','DD/MM/YYYY HH24:MI:SS')";
								}
								if (filtervalue.length()==10)
								{
									where += "TRUNC(" +filterdatafield+ ") >= TO_DATE('"  + filtervalue +"','DD/MM/YYYY')";
								}
								if (filtervalue.length()==16)
								{
									where += "TRUNC(" +filterdatafield+ ",'MI') > TO_DATE('"  + filtervalue+":00" +"','DD/MM/YYYY HH24:MI:SS')";
								}
							}	
							if(filtercondition.equals("DATE_LESS_THAN") || filtercondition.equals("LESS_THAN_OR_EQUAL"))
							{
								if (filtervalue.length()!=19&&filtervalue.length()!=10&&filtervalue.length()!=16)
								{
									where += "1!=1";
								}
								if (filtervalue.length()==19)
								{
									where += "TRUNC(" +filterdatafield+ ",'MI') < TO_DATE('"  + filtervalue +"','DD/MM/YYYY HH24:MI:SS')";
								}
								if (filtervalue.length()==10)
								{
									where += "TRUNC(" +filterdatafield+ ") <= TO_DATE('"  + filtervalue +"','DD/MM/YYYY')";
								}
								if (filtervalue.length()==16)
								{
									where += "TRUNC(" +filterdatafield+ ",'MI') < TO_DATE('"  + filtervalue+":00" +"','DD/MM/YYYY HH24:MI:SS')";
								}
								
							}	
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
	
		public static String getSeriesChar(List<String> series)
		{
			String con="";
			String sierie = "[";
			for (String serie: series) {
				String field = serie.replace(" ", "_")
						.replace(",","_")
						.replace(".","_")
						.replace("…", "_")
						.replace("*", "_")
						.replace("#", "_")
						.replace(":", "_")
						.replace("/", "_")
						.replace("(", "_")
						.replace(")", "_")
						.replace("__", "_");
						
				// neu ten datafield bat dau boi mot chu so thi se bao loi tren jquery, vi vay can them mot ky tu truoc so. Chọn chu S. 
				//Chu y: khi lay data tu co so du lieu cung can thay doi ten cot nhu datafield
				if (field.startsWith("0")||field.startsWith("1")||field.startsWith("2")||field.startsWith("3")||field.startsWith("4")||
						field.startsWith("5")||field.startsWith("6")||field.startsWith("7")||field.startsWith("8")||field.startsWith("9")){
					field ="A"+field;
				}
				sierie =sierie + con+"{dataField:'" +field + "',displayText:'"+serie+"'}";
				con=",";
			}
			sierie = sierie + "]";
			return sierie;
		}
		
		public static String getDataChar(List<String>  categories)
		{
			String con="";
			String sampleData = "[";
			for (String categorie: categories) {
				sampleData = sampleData +con+"{" + categorie + "}";
				con=",";
			}
			sampleData = sampleData + "]";
			return replaceJson(sampleData);
		}
		
		public static String replaceJson(String str) {
			//String str = "[{Day:'09/09/2013',BBDBC1A:0,BBDDA1A:0,BBDDT1A:3,BBDPG1A:0,BBDTU1A:0,BBDTU2A:0,BBPBL1A:0,BBPCT1A:0,BBPDX1A:1,BBPGM1A:0,BBPLN1A:0,BBTHT1A:0,BBTHT2A:0,BBTLG1A:0,BBTPT1A:2,BBTTL1A:2,BBTTL2A:0,BBTTN1A:0,BBTTP1A:0,BDNLK1A:0,BDNTN1A:0,BDNTP1A:0,BDNXL1A:0,BLABL1H:0,BLAMH1H:0,BLATA1H:0,BLATA2H:0,BLDBA1A:6,BLDBL1A:0,BLDDA1A:0,BLDDD1A:0,BLDDL1A:3,BLDDT1A:0,BNTNH1A:0,BNTNP1A:0,BNTNS1A:0,BNTNS2A:0,BNTPR1A:0,BSG011E:0,BSG013E:0,BSG022E:0,BSG031E:0,BSG032E:0,BSG041E:0,BSG051E:0,BSG052E:0,BSG061E:0,BSG071E:0,BSG072E:0,BSG081E:0,BSG082E:0,BSG091E:0,BSG092E:0,BSG101E:0,BSG102E:0,BSG111E:0,BSGBC1E:0,BSGBC2E:0,BSGBI1E:0,BSGBI2E:0,BSGBI3E:0,BSGBT1E:0,BSGBT2E:0,BSGNB2E:0,BSGTB1E:0,BSGTB3E:0,BSGTD1E:0,BSGTD2E:0,BSGTP1E:0},{Day:'09/09/2013',BBDBC1A:0,BBDDA1A:0,BBDDT1A:0,BBDPG1A:0,BBDTU1A:0,BBDTU2A:0,BBPBL1A:0,BBPCT1A:0,BBPDX1A:0,BBPGM1A:0,BBPLN1A:0,BBTHT1A:3,BBTHT2A:5,BBTLG1A:0,BBTPT1A:0,BBTTL1A:2,BBTTL2A:0,BBTTN1A:0,BBTTP1A:0,BDNLK1A:0,BDNTN1A:0,BDNTP1A:0,BDNXL1A:0,BLABL1H:0,BLAMH1H:0,BLATA1H:0,BLATA2H:0,BLDBA1A:0,BLDBL1A:0,BLDDA1A:0,BLDDD1A:0,BLDDL1A:0,BLDDT1A:0,BNTNH1A:0,BNTNP1A:0,BNTNS1A:0,BNTNS2A:0,BNTPR1A:0,BSG011E:0,BSG013E:0,BSG022E:0,BSG031E:0,BSG032E:0,BSG041E:0,BSG051E:0,BSG052E:0,BSG061E:0,BSG071E:0,BSG072E:0,BSG081E:0,BSG082E:0,BSG091E:0,BSG092E:0,BSG101E:0,BSG102E:0,BSG111E:0,BSGBC1E:0,BSGBC2E:0,BSGBI1E:0,BSGBI2E:0,BSGBI3E:0,BSGBT1E:0,BSGBT2E:0,BSGNB2E:0,BSGTB1E:0,BSGTB3E:0,BSGTD1E:0,BSGTD2E:0,BSGTP1E:0},{Day:'09/09/2013',BBDBC1A:0,BBDDA1A:0,BBDDT1A:5,BBDPG1A:3,BBDTU1A:0,BBDTU2A:0,BBPBL1A:8,BBPCT1A:0,BBPDX1A:2,BBPGM1A:2,BBPLN1A:0,BBTHT1A:53,BBTHT2A:14,BBTLG1A:0,BBTPT1A:9,BBTTL1A:0,BBTTL2A:2,BBTTN1A:10,BBTTP1A:0,BDNLK1A:0,BDNTN1A:0,BDNTP1A:8,BDNXL1A:6,BLABL1H:0,BLAMH1H:0,BLATA1H:0,BLATA2H:0,BLDBA1A:5,BLDBL1A:14,BLDDA1A:0,BLDDD1A:0,BLDDL1A:0,BLDDT1A:0,BNTNH1A:6,BNTNP1A:2,BNTNS1A:0,BNTNS2A:7,BNTPR1A:6,BSG011E:0,BSG013E:0,BSG022E:0,BSG031E:0,BSG032E:0,BSG041E:0,BSG051E:0,BSG052E:0,BSG061E:0,BSG071E:0,BSG072E:0,BSG081E:0,BSG082E:0,BSG091E:0,BSG092E:0,BSG101E:0,BSG102E:0,BSG111E:0,BSGBC1E:0,BSGBC2E:0,BSGBI1E:0,BSGBI2E:0,BSGBI3E:0,BSGBT1E:0,BSGBT2E:0,BSGNB2E:0,BSGTB1E:0,BSGTB3E:0,BSGTD1E:0,BSGTD2E:0,BSGTP1E:0}]";
			
			str = str.replaceAll("'", "");
			str = str.replaceAll(",", "\",\"");
			str = str.replaceAll("}\"", "\"}");
			str = str.replaceAll("}]", "\"}]");
			str = str.replaceAll(":", "\":\"");
			str = str.replaceAll("},\"", "},");
			str = str.replaceAll("Day", "\"Day");
			
		
			//System.out.println(str);
			
			return str;
		}
		
		public static String getDataFieldChar(List<String> series)
		{
			String con="";
			String datafields = "[{name:'Day'},";
			for (String serie: series) {
				datafields =datafields + con+"{name:'" + serie + "'}";
				con=",";
			}
			datafields = datafields + "]";
			return datafields;
		}
		
		
		public static String lineChart(String id, String title,String description, List<String>  categories,String minDay, String maxDay,String minValue, String maxValue, List<String> series, String unitInterval)
		{
			String s = "";
			//String sampleData = getDataChar(categories);
			String sierie = getSeriesChar(series);

			String con="";
			
			String sampleData = "[";
			for (String categorie: categories) {
				sampleData = sampleData +con+"{" + categorie + "}";
				con=",";
			}
			sampleData = sampleData + "];";
			s+="$(document).ready(function () {";
			s+="    var sampleData ="+sampleData;
			s+="    var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];";
			s+="     var settings = {";
			s+="        title: '"+title+"',";
			s+="        description: '"+description+"',";
			s+="        enableAnimations: true,";
			s+="        showLegend: true,";
			s+="        padding: { left: 5, top: 5, right: 11, bottom: 5 },";
			s+="        titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },";
			s+="        source: sampleData,";
			s+="        categoryAxis:";
			s+="            {";
			s+="                text: 'Category Axis',";
			s+="                 textRotationAngle: 0,";
			s+="                dataField: 'Day',";
			s+="                formatFunction: function (value) {";
			s+="                    return  value.getDate();";
			s+="               },";
			s+="                toolTipFormatFunction: function (value) {";
			s+="                    return value.getDate() + '-' + months[value.getMonth()] + '-' + value.getFullYear();";
			s+="                },";
			s+="                showTickMarks: true,";
			s+="                type: 'date',";
			s+="                baseUnit: 'day',";
			s+="                tickMarksInterval: 1,";
			s+="                tickMarksColor: '#888888',";
			s+="                unitInterval: 1,";
			s+="                showGridLines: true,";
			s+="                gridLinesInterval: 1,";
			s+="                gridLinesColor: '#888888',";
			s+="                gridLinesDashStyle: '2,2',";
			s+="                minValue: '"+minDay+"',";
			s+="                maxValue: '"+maxDay+"',";
			s+="                valuesOnTicks: false";
			s+="            },";
			s+="        colorScheme: 'scheme01',"; 
			s+="        seriesGroups:";
			s+="            [";
			s+="                {";
			s+="                    type: 'spline',";
			s+="					symbolType: 'circle',";
			s+="                    valueAxis:";
			s+="                    {";
			//s+="                        unitInterval:"+unitInterval+",";
			s+="                        minValue: "+minValue+",";
			s+="  						formatFunction: function(value){";
			s+="  						return value.toFixed(0);";
			s+="  						},";
			s+="  						formatSettings:";
			s+=" 				            {";
			s+=" 				                decimalPlaces: 0";
			s+=" 				            },";
			//s+="                        minValue: -1,";
			//s+="                        maxValue: "+maxValue+",";
			s+="                        displayValueAxis: true,";
			s+="                        description: 'Quality',";
			s+="                         axisSize: 'auto',";
			s+="                		gridLinesDashStyle: '2,2',";
			s+="                        tickMarksColor: '#888888'";
			s+="                    },";
			s+="                    series:"+sierie;
			s+="                }";
			s+="            ]";
			s+="    };";
	            // setup the chart
			s+="$('#"+id+"').jqxChart(settings);";
			
			s+="$('#bt"+id+"').click(function () {";
		            // call the export server to create a PNG image
			s+="        $('#"+id+"').jqxChart('saveAsPNG', '"+id+"Chart"+description+".png');";
			s+="    });";
			s+="});";
			
		return s;	
		}
		
		// Tao grid
		public static String getGridReportData(List<CTableConfig> configList, String tableID, List<String>  dataList,String listSourceId, List<CTableConfig> listSource, String menu, String hightlight,String groupColumn) {
			String s = "";
			String con="";
			String sampleData = "[";
			for (String data: dataList) {
				sampleData = sampleData +con+"{" + data + "}";
				con=",";
			}
			sampleData = sampleData + "];";
			s+="$(document).ready(function () {";
			s+="    var theme = getDemoTheme();";
			s+="    var data ="+sampleData;
			s+= getDataFields(configList);
			    // prepare the data
			s+="    var source =";
			s+="    {";
			s+="        datatype: 'json',";
			s+="		datafields: datafields," ;
			s+="        localdata: data,";
			s+= "		pagenum: 0,";
			s+= "		pagesize: 100";
			s+="    };";
			s+="    var dataAdapter = new $.jqx.dataAdapter(source);";
			// to mau
			if (hightlight!=null)
			{
			s += hightlight;	
			}
			s += "   var numberrenderer = function (row, column, value) {";
			s += "        return '<div style=\"text-align: center; margin-top: 5px;\">' + (1 + value) + '</div>';";
			s += "    };";
			s += "var aggregatesrenderer = function (aggregates, column, element, summaryData) {";
			s += " var renderstring = \"\";";
			s += "    $.each(aggregates, function (key, value) {";
			s += "        var color = 'red';";
			s += "       renderstring += '<div style=\"color: ' + color + '; position: relative; margin: 6px; text-align: right; overflow: hidden;\">' + value + '</div>';";
			s += "    });";
			s += "   renderstring += \"</div>\";";
			s += "    return renderstring;";
			s += "};";
			if (groupColumn!=null && !groupColumn.equals(""))
			{
				s += groupColumn;
			}
			s += getColumns(configList);
			s += "    $('#" + tableID + "').jqxGrid(";
			s += "    {";
			s += "      width: '100%',";
			s += "      source: dataAdapter,";
			s += "      theme: getTheme(),";
			s += "      showfilterrow: true,";
			s += "      filterable: true,";
			s += "      sortable: true,";
			s += "		pageable: true,";
			s += "		height: 500,";
			s += "		showstatusbar: true,";
			s += "		statusbarheight: 25,";
	        s += "		altrows: true,";
	        s += "		showaggregates: true,";
			s += "		columnsresize: true,";
			s += "		columnsreorder: true,";
			s += "		scrollmode: 'deferred',";
			s += "		selectionmode: 'multiplerowsextended',";
			s += "		pagesizeoptions: ['50', '100', '200', '300', '400', '500'],";
			s += "		columns: column";
			if (groupColumn!=null && !groupColumn.equals(""))
			{
				s += ",columngroups: columngroups";
			}
			s += " });";
			if (menu!=null && !menu.equals(""))
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
			//list source
			if (listSourceId!=null && !listSourceId.equals(""))
			{
				s += getListSource(listSource);
				s += "$('#"+listSourceId+"').jqxDropDownList({ checkboxes: true, source: listSource, width: 15, height: 15, theme: getTheme(), dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });";
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
		
		// Tao grid
		public static String getGridPagingReportUrl(List<CTableConfig> configList, String tableID, String url,String listSourceId, List<CTableConfig> listSource, String menu, String hightlight,String typeSelectRow,String groupColumn,String autoRowheigh) {
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
			if (hightlight!=null)
			{
			s += hightlight;	
			}
			s += "   var numberrenderer = function (row, column, value) {";
			s += "        return '<div style=\"text-align: center; margin-top: 5px;\">' + (1 + value) + '</div>';";
			s += "    };";
			
			s += "var aggregatesrenderer = function (aggregates, column, element, summaryData) {";
			s += " var renderstring = \"\";";
			s += "    $.each(aggregates, function (key, value) {";
			s += "        var color = 'red';";
			s += "       renderstring += '<div style=\"color: ' + color + '; position: relative; margin: 6px; text-align: right; overflow: hidden;\">' + value + '</div>';";
			s += "    });";
			s += "   renderstring += \"</div>\";";
			s += "    return renderstring;";
			s += "};";
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
			s += "      showfilterrow: true,";
			s += "      filterable: true,";
			s += "      sortable: true,";
			s += "		pageable: true,";
			if (autoRowheigh!=null)
			{
				s += "autorowheight: true,";
			}
			else
			{
				s += "height: 500,";
			}
			s += "		showstatusbar: true,";
			s += "		statusbarheight: 25,";
	        s += "		altrows: true,";
	        s += "		showaggregates: true,";
			s += "		columnsresize: true,";
			s += "		columnsreorder: true,";
			s += "		scrollmode: 'deferred',";
			s += "selectionmode: '"+typeSelectRow+"',";
			s += "pagesizeoptions: ['50', '100', '200', '300', '400', '500'],";
			s += "columns: column";
			if (groupColumn!=null && !groupColumn.equals(""))
			{
				s += ",columngroups: columngroups";
			}
			s += " });";
			if (menu!=null && !menu.equals(""))
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
				
			}
			
			if (listSourceId!=null && !listSourceId.equals(""))
			{
				//list source
				s += getListSource(listSource);
				s += "$('#"+listSourceId+"').jqxDropDownList({ checkboxes: true, source: listSource, width: 15, height: 15, theme: getTheme(), dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });";
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
			s += " });";
			return s;
		}
		
		// Tao grid BINH THUONG
				public static String getGridPagingUrl(List<CTableConfig> configList, String tableID, String url,String listSourceId, List<CTableConfig> listSource, String menu, String hightlight,String typeSelectRow,String autoRowheigh) {
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
					if (hightlight!=null)
					{
					s += hightlight;	
					}
					s += "   var numberrenderer = function (row, column, value) {";
					s += "        return '<div style=\"text-align: center; margin-top: 5px;\">' + (1 + value) + '</div>';";
					s += "    };";
					
					s += getColumns(configList);
			        //create grid
					
					s += "$('#" + tableID + "').jqxGrid(";
					s += "{";
					s += "width: '100%',";
					s += "source: dataadapter,";
					s += "theme: getTheme(),";
					s += "showfilterrow: true,";
					s += "filterable: true,";
					if (autoRowheigh!=null)
					{
						s += "autorowheight: true,";
					}
					s += "sortable: true,";
					s += "pageable: true,";
					s += "height: 500,";
			        s += "altrows: true,";
			        s += "columnsresize: true,";
					s += "columnsreorder: true,";
					s += "scrollmode: 'logical',";
					s += "selectionmode: '"+typeSelectRow+"',";
					s += "pagesizeoptions: ['50', '100', '200', '300', '400', '500'],";
					s += "columns: column";
					s += " });";
					if (menu!=null && !menu.equals(""))
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
					
					}
					if (listSourceId!=null && !listSourceId.equals(""))
					{
						//list source
						s += getListSource(listSource);
						s += "$('#"+listSourceId+"').jqxDropDownList({ checkboxes: true, source: listSource, width: 15, height: 15, theme: getTheme(), dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });";
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
					s += " });";
					
					return s;
				}
	
		/* To mau gia tri 1 cot cua grid
			/* To mau gia tri 1 cot cua grid*/
		public static String highLightCol(List<CHighlightConfigs> highlightConfigList, String tableID) {
			String s="";
			s=" var cellsrenderer = function (row, column, value, defaultHtml) {" ;
			if (highlightConfigList.size()>0)
			{
				s += 	"var  valueKey = $('#"+tableID+"').jqxGrid('getrowdata', row)."+highlightConfigList.get(0).getKey()+";";
				for (CHighlightConfigs highlightConfig : highlightConfigList) {
					if (highlightConfig.getValue()!=null)
					{
						s += "if( valueKey"+ highlightConfig.getFormula() +"'"+ highlightConfig.getValue().trim() +"'"+"){";
					}
					else
					{
						s += "if( valueKey"+ highlightConfig.getFormula() + highlightConfig.getValue()+"){";
					}
					/*s +="	         return '<div style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; " + highlightConfig.getStyle().replaceAll("'", "") + "\">' + value + '</div>';";
					s +="	}";*/
					s +=" var element = $(defaultHtml);";
					//s += 	"alert(element[0]);";
					//'background-color': 'Yellow', 'width': '100%', 'height': '100%', 'margin': '0px' 
					s +="element.css({ "+highlightConfig.getStyle()+"});";
					s +="return element[0].outerHTML;}";
				}
				
			}
			s +=" return defaultHtml;";
			s += "};";
			System.out.println(s);
			return s;
		}
		
		public static String highLightCol(List<CHighlightConfigs> highlightConfigList, String tableID,String name) {
			String s="";
			if (name==null)
			{
				name="";
			}
			s=" var cellsrenderer"+name+" = function (row, column, value, defaultHtml) {" ;
			if (highlightConfigList.size()>0)
			{
				s += 	"var  valueKey = $('#"+tableID+"').jqxGrid('getrowdata', row)."+highlightConfigList.get(0).getKey()+";";
				for (CHighlightConfigs highlightConfig : highlightConfigList) {
					if (highlightConfig.getValue()!=null)
					{
						s += "if( valueKey"+ highlightConfig.getFormula() +"'"+ highlightConfig.getValue().trim() +"'"+"){";
					}
					else
					{
						s += "if( valueKey"+ highlightConfig.getFormula() + highlightConfig.getValue()+"){";
					}
					/*s +="	         return '<div style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; " + highlightConfig.getStyle().replaceAll("'", "") + "\">' + value + '</div>';";
					s +="	}";*/
					s +=" var element = $(defaultHtml);";
					//s += 	"alert(element[0]);";
					//'background-color': 'Yellow', 'width': '100%', 'height': '100%', 'margin': '0px' 
					s +="element.css({ "+highlightConfig.getStyle()+"});";
					s +="return element[0].outerHTML;}";
				}
				
			}
			s +=" return defaultHtml;";
			s += "};";
			System.out.println(s);
			return s;
		}

		public static String getGridReportDataString(List<CTableConfig> configList, String tableID, String data,String listSourceId, List<CTableConfig> listSource, String menu, String hightlight,String isShowaggregates,String groupColumn,String groupHeader) {
			String s = "";
			String sampleData = data;
			s+="$(document).ready(function () {";
			s+="    var theme = getDemoTheme();";
			s+="    var data ="+sampleData+";";
			s+= getDataFields(configList);
			    // prepare the data
			s+="    var source =";
			s+="    {";
			s+="        datatype: 'json',";
			s+="		datafields: datafields," ;
			s+="        localdata: data,";
			s+= "		pagenum: 0,";
			s+= "		pagesize: 100";
			s+="    };";
			s+="    var dataAdapter = new $.jqx.dataAdapter(source);";
			// to mau
			if (hightlight!=null)
			{
			s += hightlight;	
			}
			s += "   var numberrenderer = function (row, column, value) {";
			s += "        return '<div style=\"text-align: center; margin-top: 5px;\">' + (1 + value) + '</div>';";
			s += "    };";
			if (groupColumn!=null)
			{
			s += getGrouprender(configList);
			}
			if (groupHeader!=null && !groupHeader.equals(""))
			{
				s += groupHeader;
			}
			s += getColumns(configList);
			s += "    $('#" + tableID + "').jqxGrid(";
			s += "    {";
			s += "      width: '100%',";
			s += "      source: dataAdapter,";
			s += "      theme: getTheme(),";
			s += "      showfilterrow: true,";
			s += "      filterable: true,";
			s += "      sortable: true,";
			s += "		pageable: true,";
			/*if (data!= null && data.lastIndexOf("}")>30)
				s += "	height: 600,";
			else*/
				s += "	autoheight: true,";
			s += "      autorowheight: true,";
			s += "		showstatusbar: true,";
			s += "		statusbarheight: 25,";
	        s += "		altrows: true,";
	        s += "		columnsresize: true,";
			s += "		columnsreorder: true,";
			s += "		scrollmode: 'deferred',";
			s += "		selectionmode: 'singlecell',";
			if (isShowaggregates!=null&& isShowaggregates.equals("Y"))
			{
			s += "		showaggregates: true,";
			}
			s += "		pagesizeoptions: ['50', '100', '200', '300', '400', '500'],";
			s += "		columns: column";
			if (groupColumn!=null)
			{
			s += "		,groupable: true,showgroupaggregates: true, groups: ["+groupColumn+"],groupsexpandedbydefault:true";
			//s += "		,groupsrenderer: groupsrenderer,groupsexpandedbydefault:true";
			}
			if (groupHeader!=null && !groupHeader.equals(""))
			{
				s += ",columngroups: columngroups";
			}
			s += " });";
			/*if (groupColumn!=null)
			{
			s += " $('#" + tableID + "').jqxGrid('expandallgroups');";
			//s += "		,groupsrenderer: groupsrenderer,groupsexpandedbydefault:true";
			}*/
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
			
			//list source
			if (listSourceId!=null && !listSourceId.equals(""))
			{
				s += getListSource(listSource);
				s += "$('#"+listSourceId+"').jqxDropDownList({ checkboxes: true, source: listSource, width: 15, height: 15, theme: getTheme(), dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });";
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
			s += " });";
			System.out.println(s);
			return s;
		}

		public static String getGridPagingUrlInventoryHome(List<CTableConfig> configList, String tableID, String url,String listSourceId, List<CTableConfig> listSource, String menu, String hightlight,String typeSelectRow) {
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
			if (hightlight!=null)
			{
			s += hightlight;	
			}
			s += "   var numberrenderer = function (row, column, value) {";
			s += "        return '<div style=\"text-align: center; margin-top: 5px;\">' + (1 + value) + '</div>';";
			s += "    };";
			
			s += "var aggregatesrenderer = function (aggregates, column, element, summaryData) {";
			s += " var renderstring = \"\";";
			s += "    $.each(aggregates, function (key, value) {";
			s += "        var color = 'red';";
			s += "       renderstring += '<div style=\"color: ' + color + '; position: relative; margin: 6px; text-align: right; overflow: hidden;\">' + value + '</div>';";
			s += "    });";
			s += "   renderstring += \"</div>\";";
			s += "    return renderstring;";
			s += "};";
			
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
			s += "height: 500,";
	        s += "altrows: true,";
	        s += "		showstatusbar: true,";
			s += "		statusbarheight: 25,";
	        s += "		showaggregates: true,";
	        s += "columnsresize: true,";
			s += "columnsreorder: true,";
			s += "scrollmode: 'logical',";
			s += "selectionmode: '"+typeSelectRow+"',";
			s += "pagesizeoptions: ['50', '100', '200', '300', '400', '500'],";
			s += "columns: column";
			s += " });";
			if (menu!=null && !menu.equals(""))
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
				s += "$('#"+listSourceId+"').jqxDropDownList({ checkboxes: true, source: listSource, width: 15, height: 15, theme: getTheme(), dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });";
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
		
		
		// Tao grid PHAN TRANG
		public static String getGridPaginglargerData(List<CTableConfig> configList, String tableID, String url,String listSourceId, List<CTableConfig> listSource, String menu, String hightlight,String typeSelectRow,String autoRowheigh) {
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
			s += "id: 'id',";
			s += "	root: 'Rows',";
			s += "	beforeprocessing: function(data)";
			s += "	{		";
			s += "		source.totalrecords = data.totalRow;	";				
			s += "	},";
			s += "	filter: function(){";
			s += "   	$('#" + tableID + "').jqxGrid('updatebounddata');";
			s += "	},";
			s += "	sort: function(){";
			s += "   	$('#" + tableID + "').jqxGrid('updatebounddata');";
			s += "	}";
			s += "};";
			
			s += "var dataadapter = new $.jqx.dataAdapter(source, {";
			s += "loadError: function(xhr, status, error){ alert(error);}";
			s += "});";
			// to mau
			if (hightlight!=null)
			{
			s += hightlight;	
			}
			s += "   var numberrenderer = function (row, column, value) {";
			s += "        return '<div style=\"text-align: center; margin-top: 5px;\">' + (1 + value) + '</div>';";
			s += "    };";
			
			s += getColumns(configList);
	        //create grid
			
			s += "$('#" + tableID + "').jqxGrid(";
			s += "{";
			s += "width: '100%',";
			s += "source: dataadapter,";
			s += "theme: getTheme(),";
			s += "showfilterrow: true,";
			s += "filterable: true,";
			if (autoRowheigh!=null)
			{
				s += "autorowheight: true,";
			}
			else
			{
				s += "height: 500,";
			}
			s += "sortable: true,";
			s += "pageable: true,";
	        s += "altrows: true,";
	        s += "columnsresize: true,";
			s += "columnsreorder: true,";
			s += "scrollmode: 'logical',";
			s += "autoloadstate: false,";
			s += "autosavestate: false,";
			s += "pagesize: 100 ,";
			s += "virtualmode: true,";
			s += "selectionmode: '"+typeSelectRow+"',";
			s += "pagesizeoptions: ['50', '100', '200', '300', '400', '500'],";
			s += "rendergridrows: function(obj){";
			s += "	return obj.data; },";
			s += " autoshowfiltericon: true,";
			s += " altrows: true,";
			s += "columns: column";
			s += " });";
			if (menu!=null && !menu.equals(""))
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
				s += "$('#"+listSourceId+"').jqxDropDownList({ checkboxes: true, source: listSource, width: 15, height: 15, theme: getTheme(), dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });";
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
		
		/* Cau hinh datafield cho grid*/
		public static String getDataFieldsArray(List<CTableConfig> configList) {
			String datafields =" var datafields = [";
			String condition="";
			for (CTableConfig config : configList) {
				datafields +=condition+"          { name: '"+config.getDataField()+"', type: '"+config.getDataType()+"', map:'"+config.getTableColumn()+"' }";	
				condition=",";
			}
			datafields +="        ];";
			//System.out.println(datafields);
			return datafields;
		}
		/*tạo grid du lieu*/
		/*tao grouprender*/
		public static String getGrouprender(List<CTableConfig> configList) {
			String constructor="var groupsrenderer = function (text, group, expanded, data) {";
			constructor += "if (data.groupcolumn.datafield == 'day'||data.groupcolumn.datafield == 'DAY' || data.groupcolumn.datafield == 'date') {";
			constructor += "    var number = dataAdapter.formatDate(group, data.groupcolumn.cellsformat);";
			constructor += "    text = data.groupcolumn.text + ': ' + number;";
			constructor += " }";
			/*constructor += "var number = dataAdapter.formatNumber(group, data.groupcolumn.cellsformat);";
			constructor += "var text = data.groupcolumn.text + ': ' + number;";*/
			
			constructor += "if (data.subItems.length > 0) {";
			for (CTableConfig config : configList) {
				if (config.getAggregates() !=null && !config.getAggregates().equals("")){
					constructor += "var aggregate"+config.getDataField()+" = this.getcolumnaggregateddata('"+config.getDataField()+"', "+config.getAggregates()+", true, data.subItems);";
				}
			}
			constructor += "}";
			constructor += "else {";
			constructor += "    var rows = new Array();";
			constructor += "    var getRows = function (group, rows) {";
			constructor += "        if (group.subGroups.length > 0) {";
			constructor += "            for (var i = 0; i < group.subGroups.length; i++) {";
			constructor += "                getRows(group.subGroups[i], rows);";
			constructor += "            }";
			constructor += "        }";
			constructor += "        else {";
			constructor += "            for (var i = 0; i < group.subItems.length; i++) {";
			constructor += "                rows.push(group.subItems[i]);";
			constructor += "            }";
			constructor += "        }";
			constructor += "    };";
			constructor += "    getRows(data, rows);";
			for (CTableConfig config : configList) {
				if (config.getAggregates() !=null && !config.getAggregates().equals("")){
					constructor += "var aggregate"+config.getDataField()+" = this.getcolumnaggregateddata('"+config.getDataField()+"', "+config.getAggregates()+", true, rows);";
				}
			}
			constructor += "}";
			constructor += "var result='<div style=\"position: relative; height: 30px;font-weight: bold;\"><div style=\"float:left;color: red;\">' + text + '</div>'; ";
			for (CTableConfig config : configList) {
				if (config.getAggregates() !=null && !config.getAggregates().equals("")){
					constructor += " result= result+ '<div style=\" float: left;color: blue;\" >, "+config.getColumnName()+": '+ aggregate"+config.getDataField()+".sum +'</div>';";
		        }
			}
			constructor += " result= result+ '</div>';";
			constructor += " return result; };";
			System.out.println(constructor);
			return constructor;
		}
}
