package vn.com.vhc.vmsc2.statistics.web.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.BadcellConfig;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;

public class Helper {
	public static String checkColumns(String[] checkList, int startColumn) {
		String s = "";
		int column = 0;
		for (int i = 0; i < checkList.length; i++) {
			column = startColumn + i;
			s += "$('#" + checkList[i] + "').change(function() {";
			s += "if ($(this).is(':checked')) {";
			s += "$('.simple2 td:nth-child(" + column + "),.simple2 th:nth-child(" + column + ")').show();";
			s += "} ";
			s += "else {";
			s += "$('.simple2 td:nth-child(" + column + "),.simple2 th:nth-child(" + column + ")').hide();";
			s += "}";
			s += "});";
		}
		return s;
	}

	public static String checkColumns(String[] checkList, String tableId, int startColumn) {
		String s = "";
		int column = 0;
		int endColumn = startColumn + checkList.length - 1;
		s += "<input type=\"checkbox\" class=\"checkAll\" checked=\"checked\" /><b>Uncheck all</b>&nbsp;";
		for (int i = 0; i < checkList.length; i++) {
			if (checkList[i].length() == 0) {
				s += "<input type=\"checkbox\" style=\"display:none\" class=\"cb-element\" name=\"" + checkList[i] + "\" id=\"" + checkList[i] + "\"/>"
						+ checkList[i];
			} else
				s += "<input type=\"checkbox\" class=\"cb-element\" name=\"" + checkList[i] + "\" id=\"" + checkList[i] + "\" checked=\"checked\"/> "
						+ checkList[i] + "&nbsp;";
		}
		s += "<script type=\"text/javascript\">";
		s += "$(document).ready(function() {";
		s += "$( '.checkAll' ).live( 'change', function() {";
		s += "$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );";
		s += "$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );";
		s += "if($( this ).is( ':checked' )){";
		for (int i = 0; i < checkList.length; i++) {
			column = startColumn + i;
			if (checkList[i].length() > 0) {
				s += "$('#" + tableId + " td:nth-child(" + column + "),#" + tableId + " th:nth-child(" + column + ")').show();";
			}
		}
		s += "} else {";
		s += "for (var i=" + startColumn + ";i<=" + endColumn + ";i++)";
		s += "{";
		s += "$('#" + tableId + " td:nth-child('+i+'),#" + tableId + " th:nth-child('+i+')').hide();";
		s += "}";
		s += "}";
		s += "});";

		s += "$( '.cb-element' ).live( 'change', function() {";
		s += "$( '.cb-element' ).length == $( '.cb-element:checked' ).length ? $( '.checkAll' ).attr( 'checked', 'checked' ).next().text( 'Uncheck All' ) : $( '.checkAll' ).attr( 'checked', '' ).next().text( 'Check All' );";
		s += "});";

		for (int i = 0; i < checkList.length; i++) {
			column = startColumn + i;
			if (checkList[i].length() > 0) {
				s += "$('#" + checkList[i] + "').change(function() {";
				s += "if ($(this).is(':checked')) {";
				s += "$('.simple2 td:nth-child(" + column + "),.simple2 th:nth-child(" + column + ")').show();";
				s += "} ";
				s += "else {";
				s += "$('.simple2 td:nth-child(" + column + "),.simple2 th:nth-child(" + column + ")').hide();";
				s += "}";
				s += "});";
			}
		}
		s += "});";
		s += "</script>";
		return s;
	}

	public static String int2String(Integer i) {
		String s = "";

		if (i != null)
			s = i.toString();

		return s;
	}

	public static Float int2Float(Integer i) {
		try {
			Float f = i.floatValue();
			return f;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public static Float string2Float(String str) {
		try {
			Float f = Float.valueOf(str);
			return f;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Float bigdecimal2Float(BigDecimal d) {
		try {
			Float f = d.floatValue();
			return f;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public static Double bigdecimal2Double(BigDecimal d) {
		try {
			double f = d.doubleValue();
			return f;
		} catch (NullPointerException e) {
			return (double) 0;
		}
	}

	public static Double float2Double(Float f) {
		try {
			double d = f.doubleValue();
			return d;
		} catch (NullPointerException e) {
			return (double) 0;
		}
	}

	public static Double int2Double(Integer i) {
		try {
			double d = i.doubleValue();
			return d;
		} catch (NullPointerException e) {
			return (double) 0;
		}
	}

	public static Integer string2Int(String str) {
		try {
			Integer f = Integer.valueOf(str);
			return f;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static String float2String(Float f) {
		String s = "";

		if (f != null)
			s = f.toString();

		return s;
	}

	public static String highLightKpi(List<BadcellConfig> badcellConfigList, String tableId) {
		String s = "$('#" + tableId + ">tbody>tr').each( function(){";
		s += "var vendor = $(this).find(\".vendor\").text();";
		for (BadcellConfig badcellConfig : badcellConfigList) {
			s += "if(vendor == \"" + badcellConfig.getVendor() + "\"){";
			s += "var " + badcellConfig.getKpi() + " = parseFloat($(this).find(\"." + badcellConfig.getKpi() + "\").text());";
			s += "if(" + badcellConfig.getKpi() + badcellConfig.getFormula() + badcellConfig.getValue() + "){";
			s += "$(this).find('." + badcellConfig.getKpi() + "').css('background-color', 'red');";
			s += "}";
			s += "}";
		}
		s += "});";
		return s;
	}

	public static String highLight(List<HighlightConfig> highlightConfigList, String tableId) {
		String s = "$('#" + tableId + ">tbody>tr').each( function(){";
		for (HighlightConfig highlightConfig : highlightConfigList) {
			s += "var " + highlightConfig.getKey() + " = parseFloat($(this).find(\"." + highlightConfig.getKey() + "\").text());";
			s += "if(" + highlightConfig.getKey() + highlightConfig.getFormula() + highlightConfig.getValue() + "){";
			s += "$(this).find('." + highlightConfig.getKpi() + "').css({" + highlightConfig.getStyle() + "});";
			s += "}";
		}
		s += "});";
		return s;
	}

	// To mau ca dong
	public static String highLightLine(List<HighlightConfig> highlightConfigList, String tableId) {
		String s = "$('#" + tableId + ">tbody>tr').each( function() { ";
		for (HighlightConfig highlightConfig : highlightConfigList) {
			s += "var value = $(this).find(\"." + highlightConfig.getKey() + "\").text();";
			s += "if( value" + highlightConfig.getFormula() + "'"+highlightConfig.getKpi()+"'" + "){";
			s += "$(this).find(\"td\").css({" + highlightConfig.getStyle() + "});";
			s += "}";
		}
		s += "});";
		return s;
	}

	// To mau theo cot
	public static String highLightKpis(List<HighlightConfig> highlightConfigList, String tableId) {
		String s = "$('#" + tableId + ">tbody>tr').each( function(){";
		for (HighlightConfig highlightConfig : highlightConfigList) {
			s += "var " + highlightConfig.getKey() + " = $(this).find(\"." + highlightConfig.getKey() + "\").text();";
			s += "if(" + highlightConfig.getKey() + highlightConfig.getFormula() + "'Không đat'){";
			s += "$(this).find('." + highlightConfig.getKpi() + "').css({" + highlightConfig.getStyle() + "});";
			s += "}";
		}
		s += "});";
		return s;
	}
	// To mau bang MN_IPBB
		public static String highLightIPBB(List<HighlightConfig> highlightConfigList, String tableId) {
			String s = "$('#" + tableId + ">tbody>tr').each( function(){";
			for (HighlightConfig highlightConfig : highlightConfigList) {
				s += "var " + highlightConfig.getKey() + " = parseFloat($(this).find(\"." + highlightConfig.getKey() + "\").text());";
				s += "var max  = parseFloat($(this).find(\".REF_VALUE\").text());";
				s += "if(" + highlightConfig.getKey() + highlightConfig.getFormula() + "max){";
				s += "$(this).find('." + highlightConfig.getKpi() + "').css({" + highlightConfig.getStyle() + "});";
				s += "}";
			}
			s += "});";
			return s;
		}
	public static String intFormat(Integer num) {
		NumberFormat intFormat = new DecimalFormat("#,###,###");
		return intFormat.format(num);
	}

	public static Float floatFormat(Float num) {
		NumberFormat floatFormat = new DecimalFormat("#,###,##0.##");
		return string2Float(floatFormat.format(num));
	}

	public static Integer getQuarter() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month < 4)
			return 1;
		else if (month >= 4 && month <= 6)
			return 2;
		else if (month >= 7 && month <= 9)
			return 3;
		else
			return 4;
	}

	// to tung o
	public static String highLightEricsson2g(List<HighlightConfig> highlightConfigList, String tableId) {
		String s = "$('#" + tableId + ">tbody>tr').each( function(){";
		for (HighlightConfig highlightConfig : highlightConfigList) {
			s += "var value = $(this).find(\"." + highlightConfig.getKey() + "\").text();";
			s += "if( value" + highlightConfig.getFormula() + "" + highlightConfig.getValue().trim() + "" + "){";
			s += "$(this).find('." + highlightConfig.getKpi() + "').css({" + highlightConfig.getStyle() + "});";
			s += "}";
		}
		s += "});";
		return s;
	}
	
	//to tung o
	public static String highLightRAlarmLogInfo(List<HighlightConfig> highlightConfigList, String tableId) {

		String s = "$('#" + tableId + ">tbody>tr').each(function(){ ";
		for (HighlightConfig highlightConfig : highlightConfigList) {
			s += "var " + highlightConfig.getKpi() + " = $(this).find(\"." + highlightConfig.getKpi() + "\").text();";
			s += "if(" + highlightConfig.getKpi() + highlightConfig.getFormula() + "'" + highlightConfig.getValue() + "'" + "){";
			s += "$(this).find('." + highlightConfig.getKpi() + "').css({" + highlightConfig.getStyle() + "});";
			s += "}";
		}
		s += "});";
		return s;
	}
	
	// To hang du lieu
	public static String highLightRows(List<HighlightConfig> highlightConfigList, String tableId) {

		
		String s = "$('#" + tableId + ">tbody>tr').each(function(){ ";
		for (HighlightConfig highlightConfig : highlightConfigList) {
			s += "var " + highlightConfig.getKpi() + " = $(this).find(\"." + highlightConfig.getKpi() + "\").text();";
			s += "if(" + highlightConfig.getKpi() + highlightConfig.getFormula() + "'" + highlightConfig.getValue() + "'" + "){";
			s += "$(this).find(\"td\").css({"+highlightConfig.getStyle()+"});";
			s += "}";
		}
		s += "});";
		System.out.println(s);
		return s;
	}
	
	/**
	 * @author ganaxy 20.10.2015
	 * 
	 * To mau du lieu utilization in & out theo dieu kien
	 * > 70% = mau vang
	 * > 80% = mau cam
	 * > 90& = mau do
	 * */
	public static String to_mau_link_tai(List<HighlightConfig> highlightConfigList, String tableId) {
		String s = "$('#" + tableId + ">tbody>tr').each( function(){";
		for (HighlightConfig highlightConfig : highlightConfigList) {
			s += "var " + highlightConfig.getKpi() + " = parseFloat($(this).find(\"." + highlightConfig.getKpi() + "\").text());";
			s += "if(" + highlightConfig.getKpi() + highlightConfig.getFormula() + highlightConfig.getValue() + "){";
			s += "$(this).find('." + highlightConfig.getKpi() + "').css({" + highlightConfig.getStyle() + "});";
			s += "}";
		}
		s += "});";
		return s;
	}
}
