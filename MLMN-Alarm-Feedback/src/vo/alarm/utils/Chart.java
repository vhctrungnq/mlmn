package vo.alarm.utils;

import java.util.List;
import java.util.Map;

public class Chart {
	public static String basicLine(String id, String title, String titleLeft, List<String> categories, Map<String, List<Float>> series) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");

		String data = "";
		for (String key : series.keySet()) {
			data += "{";
			data += "name: '" + key + "',";
			data += "data: [";
			for (Float value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s = "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "defaultSeriesType: 'line',";
		s += "marginRight: 120,";
		s += "marginBottom: 80";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "',";
		s += "x: -20";
		s += "},";
		s += "xAxis: {";
		s += "categories: [" + cats + "],";
		s += "labels: {";
		s += "rotation: -45,";
		s += "align: 'right',";
		s += "style: {";
		s += "font: 'normal 12px Verdana, sans-serif'";
		s += "}";
		s += "}";
		s += "},";
		s += "yAxis: {";
		s += "title: {";
		s += "text: '" + titleLeft + "'";
		s += "},";
		s += "plotLines: [{";
		s += "value: 0,";
		s += "width: 1,";
		s += "color: '#808080'";
		s += "}]";
		s += "},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.series.name +'</b><br/>'+";
		s += "this.x +': '+ this.y +'';";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: -10,";
		s += "y: 100,";
		s += "borderWidth: 0";
		s += "},";
		s += "series: [" + data + "]";
		s += "});";

		s += "});";

		s += "</script>";

		return s;
	}
	
	public static String timeDataLine(String id, String title, String titleLeft, String titleReturnX, String titleReturnY, String titleBottom, Map<String, List<String>> series) {

		String data = "";
		for (String key : series.keySet()) {
			data += "{";
			data += "name: '" + key + "',";
			data += "data: [";
			for (String value : series.get(key)) {
				
				String[] tmp = value.split("-");
				data += "[" + tmp[0] + ", " + tmp[1] + " ], ";
				
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s = "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "defaultSeriesType: 'line'";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'},";
		s += "subtitle: {";
		s += "text: ''";
		s += "},";
		s += "xAxis: {";
		s += "title: {";
		s += "text: '" + titleBottom + "'}";
		s += "},";
		s += "yAxis: {";
		s += "title: {";
		s += "text: '" + titleLeft + "'},min: 0";
		s += "},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.series.name +'</b><br/>'+";
		s += "this.x +" + "' ' + '" + titleReturnX + "' + ': ' + this.y +' " + titleReturnY + "';";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: 5,";
		s += "y: 100,";
		s += "borderWidth: 0},";
		s += "series: [" + data + "]";
		s += "});";
		s += "});";
		s += "</script>";

		return s;
	}
	
	public static String logarithmicAxis(String id, String title, String titleLeft, String titleReturnX, String titleReturnY, String titleBottom, Map<String, List<String>> series) {

		String data = "";
		for (String key : series.keySet()) {
			data += "{";
			data += "name: '" + key + "',";
			data += "data: [";
			for (String value : series.get(key)) {
				
				String[] tmp = value.split("-");
				data += "[" + tmp[0] + ", " + tmp[1] + " ], ";
				
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s = "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "defaultSeriesType: 'line'";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'},";
		s += "subtitle: {";
		s += "text: ''";
		s += "},";
		s += "xAxis: {";
		s += "tickInterval: 20,";
		s += "title: {";
		s += "text: '" + titleBottom + "'}";
		s += "},";
		s += "yAxis: {";
		s += "type: 'logarithmic',";
		s += "minorTickInterval: 1,";
		s += "title: {";
		s += "text: '" + titleLeft + "'}";
		s += "},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.series.name +'</b><br/>'+";
		s += "this.x +" + "' ' + '" + titleReturnX + "' + ': ' + this.y +' " + titleReturnY + "';";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: 5,";
		s += "y: 100,";
		s += "borderWidth: 0},";
		s += "series: [" + data + "]";
		s += "});";
		s += "});";
		s += "</script>";

		return s;
	}

	public static String comboDualAxes(String id, String title, List<String> categories, Map<String, List<Float>> series) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");

		String data = "";
		for (String key : series.keySet()) {
			String[] tmp = key.split("-");
			data += "{";
			if (tmp.length > 1)
				data += "type: '" + tmp[1] + "',";
			data += "name: '" + tmp[0] + "',";
			if (tmp[0].indexOf("%") == -1)
				data += "yAxis: 1,";
			data += "data: [";
			for (Float value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s = "<script type=\"text/javascript\" src=\"/MLMN-Statistics/scripts/highcharts.js\"></script>";
		s += "<script type=\"text/javascript\" src=\"/MLMN-Statistics/scripts/exporting.js\"></script>";
		s += "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "margin: [80, 180, 80, 80],";
		s += "defaultSeriesType: 'line'";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "',";
		s += "style: {";
		s += "margin: '10px 0 0 0'";
		s += "}";
		s += "},";
		s += "xAxis: [{";
		s += "categories: [" + cats + "],";
		s += "labels: {";
		s += "rotation: -45,";
		s += "align: 'right',";
		s += "style: {";
		s += "font: 'normal 11px Verdana, sans-serif'";
		s += "}";
		s += "}";
		s += "}],";
		s += "yAxis: [{";
		s += "min: 0,";
		s += "max: 100,";
		s += "labels: {";
		s += "formatter: function() {";
		s += "return this.value +' %';";
		s += "},";
		s += "style: {";
		s += "color: '#89A54E'";
		s += "}";
		s += "},";
		s += "title: {";
		s += "text: '%',";
		s += "style: {";
		s += "color: '#89A54E'";
		s += "},";
		s += "margin: 60";
		s += "}";
		s += "}, {";
		s += "title: {";
		s += "text: '',";
		s += "margin: 70,";
		s += "style: {";
		s += "color: '#4572A7'";
		s += "}";
		s += "},";
		s += "opposite: true";
		s += "}],";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.series.name +'</b><br/>'+";
		s += "this.x +': '+ this.y+ (this.series.name.indexOf('%') != -1 ? ' %' : '');";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: 10,";
		s += "y: 100,";
		s += "borderWidth: 0";
		s += "},";
		s += "series: [" + data + "]";
		s += "});";

		s += "});";

		s += "</script>";

		return s;
	}

	public static String comboDualAxes(String id, String title, List<String> categories, Map<String, List<Float>> leftSeries, Map<String, List<Float>> rightSeries) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");

		String dataLeft = "";
		for (String key : leftSeries.keySet()) {
			String[] tmp = key.split("-");
			dataLeft += "{";
			if (tmp.length > 1)
				dataLeft += "type: '" + tmp[1] + "',";
			dataLeft += "name: '" + tmp[0] + "',";
			dataLeft += "data: [";
			for (Float value : leftSeries.get(key)) {
				dataLeft += value + ", ";
			}
			dataLeft += "]";
			dataLeft = dataLeft.replace(", ]", "]");
			dataLeft += "}, ";
		}
		dataLeft += "}";
		dataLeft = dataLeft.replace("}, }", "}");

		String dataRight = "";
		for (String key : rightSeries.keySet()) {
			String[] tmp = key.split("-");
			dataRight += "{";
			if (tmp.length > 1)
				dataRight += "type: '" + tmp[1] + "',";
			dataRight += "name: '" + tmp[0] + "',";
			dataRight += "yAxis: 1,";
			dataRight += "data: [";
			for (Float value : rightSeries.get(key)) {
				dataRight += value + ", ";
			}
			dataRight += "]";
			dataRight = dataRight.replace(", ]", "]");
			dataRight += "}, ";
		}
		dataRight += "}";
		dataRight = dataRight.replace("}, }", "}");

		String s = "<script type=\"text/javascript\" src=\"/MLMN-Statistics/scripts/highcharts.js\"></script>";
		s += "<script type=\"text/javascript\" src=\"/MLMN-Statistics/scripts/exporting.js\"></script>";
		s += "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "margin: [80, 180, 80, 80],";
		s += "defaultSeriesType: 'line'";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "',";
		s += "style: {";
		s += "margin: '10px 0 0 0'";
		s += "}";
		s += "},";
		s += "xAxis: [{";
		s += "categories: [" + cats + "],";
		s += "labels: {";
		s += "rotation: -45,";
		s += "align: 'right',";
		s += "style: {";
		s += "font: 'normal 11px Verdana, sans-serif'";
		s += "}";
		s += "}";
		s += "}],";
		s += "yAxis: [{";
		s += "min: 0,";
		s += "max: 100,";
		s += "labels: {";
		s += "formatter: function() {";
		s += "return this.value +' %';";
		s += "},";
		s += "style: {";
		s += "color: '#89A54E'";
		s += "}";
		s += "},";
		s += "title: {";
		s += "text: '%',";
		s += "style: {";
		s += "color: '#89A54E'";
		s += "},";
		s += "margin: 60";
		s += "}";
		s += "}, {";
		s += "min: 0,";
		s += "max: 100,";
		s += "labels: {";
		s += "formatter: function() {";
		s += "return this.value +' %';";
		s += "}";
		s += "},";
		s += "title: {";
		s += "text: '%',";
		s += "margin: 70,";
		s += "style: {";
		s += "color: '#4572A7'";
		s += "}";
		s += "},";
		s += "opposite: true";
		s += "}],";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.series.name +'</b><br/>'+";
		s += "this.x +': '+ this.y+ ' %';";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: 10,";
		s += "y: 100,";
		s += "borderWidth: 0";
		s += "},";
		s += "series: [" + dataLeft + ", " + dataRight + "]";
		s += "});";

		s += "});";

		s += "</script>";

		return s;
	}

	public static String multiColumn(String id, String title, String unit, String min, String max, List<String> categories, Map<String, List<Float>> series) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");

		String data = "";
		for (String key : series.keySet()) {
			String[] tmp = key.split("-");
			
			data += "{";
			if (tmp.length > 2)
				data += "color: '#" + tmp[2] + "',";
			if (tmp.length > 1)
				data += "type: '" + tmp[1] + "',";
			data += "name: '" + tmp[0] + "',";
			data += "data: [";
			for (Float value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s = "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "marginRight: 120,";
		s += "marginBottom: 80,";
		s += "defaultSeriesType: 'column'";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "xAxis: [{";
		s += "categories: [" + cats + "],";
		s += "labels: {";
		s += "rotation: -45,";
		s += "align: 'right',";
		s += "style: {";
		s += "font: 'normal 11px Verdana, sans-serif'";
		s += "}";
		s += "}";
		s += "}],";
		s += "yAxis: {";
		s += "min: "+min+",";
		s += "max: "+max+",";
		s += "title: {";
		s += "text: '" + unit + "',";
		s += "style: {";
		s += "color: '#89A54E'";
		s += "},";
		s += "margin: 50";
		s += "}";
		s += "},";
		s += "plotOptions: {";
		s += "series: {";
		s += "pointWidth: 15,";
		s += "groupPadding: 0.5";
		s += "},";
		s += "line: {";
		s += "marker: {";
		s += "enabled: false";
		s += "}";
		s += "}";
		s += "},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.series.name +'</b><br/>'+";
		s += "this.x +': '+ this.y;";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: 20,";
		s += "y: 100,";
		s += "borderWidth: 0";
		s += "},";
		s += "series: [" + data + "]";
		s += "});";

		s += "});";

		s += "</script>";

		return s;
	}
	
	public static String columnChart(String id, String title, List<String> categories, Map<String, List<Float>> series, String min, String max) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");

		String data = "";
		for (String key : series.keySet()) {
			String[] tmp = key.split("-");
			data += "{";
			if (tmp.length > 2)
				data += "color: '#" + tmp[2] + "',";
			if (tmp.length > 1)
				data += "type: '" + tmp[1] + "',";
			data += "name: '" + tmp[0] + "',";
			data += "data: [";
			for (Float value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s = "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "marginRight: 120,";
		s += "marginBottom: 80,";
		s += "defaultSeriesType: 'column'";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "xAxis: [{";
		s += "categories: [" + cats + "],";
		s += "labels: {";
		s += "rotation: -45,";
		s += "align: 'right',";
		s += "style: {";
		s += "font: 'normal 11px Verdana, sans-serif'";
		s += "}";
		s += "}";
		s += "}],";
		s += "yAxis: {";
		s += "min: " + min +",";
		if (title.indexOf("CSSR") != -1)
			s += "max: " + max + ",";
		s += "title: {";
		s += "text: '" + title + "',";
		s += "style: {";
		s += "color: '#89A54E'";
		s += "},";
		s += "margin: 60";
		s += "}";
		s += "},";
		s += "plotOptions: {";
		s += "series: {";
		s += "pointWidth: 15,";
		s += "groupPadding: 0.5";
		s += "},";
		s += "line: {";
		s += "marker: {";
		s += "enabled: false";
		s += "}";
		s += "}";
		s += "},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.series.name +'</b><br/>'+";
		s += "this.x +': '+ this.y;";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: 10,";
		s += "y: 100,";
		s += "borderWidth: 0";
		s += "},";
		s += "series: [" + data + "]";
		s += "});";

		s += "});";

		s += "</script>";

		return s;
	}
	public static String multiColumnWelcome(String id, String title, List<String> categories, Map<String, List<Float>> series) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");

		String data = "";
		for (String key : series.keySet()) {
			String[] tmp = key.split("-");
			data += "{";
			if (tmp.length > 2)
				data += "color: '#" + tmp[2] + "',";
			if (tmp.length > 1)
				data += "type: '" + tmp[1] + "',";
			data += "name: '" + tmp[0] + "',";
			data += "data: [";
			for (Float value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s = "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "marginRight: 120,";
		s += "marginBottom: 80,";
		s += "defaultSeriesType: 'column'";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "xAxis: [{";
		s += "categories: [" + cats + "],";
		s += "labels: {";
		s += "rotation: -45,";
		s += "align: 'right',";
		s += "style: {";
		s += "font: 'normal 11px Verdana, sans-serif'";
		s += "}";
		s += "}";
		s += "}],";
		s += "yAxis: {";
		//s += "min: 0,";
		if (title.indexOf("CSSR") != -1)
			s += "max: 100,";
		s += "title: {";
		s += "text: '" + title + "',";
		s += "style: {";
		s += "color: '#89A54E'";
		s += "},";
		s += "margin: 60";
		s += "}";
		s += "},";
		s += "plotOptions: {";
		s += "line: {";
		s += "marker: {";
		s += "enabled: false";
		s += "}";
		s += "}";
		s += "},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.series.name +'</b><br/>'+";
		s += "this.x +': '+ this.y;";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: 10,";
		s += "y: 100,";
		s += "borderWidth: 0";
		s += "},";
		s += "series: [" + data + "]";
		s += "});";

		s += "});";

		s += "</script>";

		return s;
	}

	public static String checkSeries(String[] checkList) {
		String s = "";

		for (int i = 0; i < checkList.length; i++) {
			s += "$('#" + checkList[i] + "').change(function() {";
			s += "if ($(this).is(':checked')) {";
			s += "chart.series[" + i + "].show();";
			s += "}";
			s += "else {";
			s += "chart.series[" + i + "].hide();";
			s += "}";
			s += "});";
		}
		/*
		 * s += "$('.checkAll').change(function() {"; s +=
		 * "if ($(this).is(':checked')) {"; for (int i = 0; i <
		 * checkList.length; i++) { s += "chart.series[" + i + "].show();"; } s
		 * += "}"; s += "else {"; for (int i = 0; i < checkList.length; i++) { s
		 * += "chart.series[" + i + "].hide();"; } s += "}"; s += "});";
		 */
		return s;
	}

	public static String stackedColumn(String id, String title, List<String> categories, Map<String, List<Float>> series) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");

		String data = "";
		for (String key : series.keySet()) {
			String[] tmp = key.split("-");
			data += "{";
			if (tmp.length > 2)
				data += "color: '#" + tmp[2] + "',";
			if (tmp.length > 1)
				data += "type: '" + tmp[1] + "',";
			data += "name: '" + tmp[0] + "',";
			if (tmp.length > 3)
				if (tmp[3].equalsIgnoreCase("r"))
					data += "yAxis: 1,";
			data += "data: [";
			for (Float value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s = "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "marginRight: 120,";
		s += "marginBottom: 100,";
		s += "defaultSeriesType: 'column'";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "xAxis: {";
		s += "categories: [" + cats + "],";
		s += "labels: {";
		s += "rotation: -45,";
		s += "align: 'right',";
		s += "style: {";
		s += "font: 'normal 11px Verdana, sans-serif'";
		s += "}";
		s += "}";
		s += "},";
		s += "yAxis: [{";
		s += "min: 0,";
		s += "labels: {";
		s += "formatter: function() {";
		s += "return this.value;";
		s += "},";
		s += "style: {";
		s += "color: '#89A54E'";
		s += "}";
		s += "},";
		s += "title: {";
		s += "text: '',";
		s += "style: {";
		s += "color: '#89A54E'";
		s += "},";
		s += "margin: 60";
		s += "}";
		s += "}, {";
		s += "min: 0,";
		s += "max: 100,";
		s += "labels: {";
		s += "formatter: function() {";
		s += "return this.value +' %';";
		s += "}";
		s += "},";
		s += "title: {";
		s += "text: '',";
		s += "margin: 70,";
		s += "style: {";
		s += "color: '#4572A7'";
		s += "}";
		s += "},";
		s += "opposite: true";
		s += "}],";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.series.name +'</b><br/>'+";
		s += "this.x +': '+ this.y;";
		s += "}";
		s += "},";
		s += "plotOptions: {";
		s += "column: {";
		s += "stacking: 'normal'";
		s += "},";
		s += "area: {";
		s += "stacking: 'normal'";
		s += "}";
		s += "},";
		s += "series: [" + data + "]";
		s += "});";

		s += "});";

		s += "</script>";

		return s;
	}
	
	public static String stackedColumnOther(String id, String title, List<String> categories, Map<String, List<Float>> series) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");

		String data = "";
		for (String key : series.keySet()) {
			String[] tmp = key.split("-");
			data += "{";
			if (tmp.length > 2)
				data += "color: '#" + tmp[2] + "',";
			if (tmp.length > 1)
				data += "type: '" + tmp[1] + "',";
			data += "name: '" + tmp[0] + "',";
			if (tmp.length > 3)
				if (tmp[3].equalsIgnoreCase("r"))
					data += "yAxis: 1,";
			data += "data: [";
			for (Float value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s = "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "marginRight: 120,";
		s += "marginBottom: 100,";
		s += "defaultSeriesType: 'column'";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "xAxis: {";
		s += "categories: [" + cats + "],";
		s += "labels: {";
		s += "rotation: -45,";
		s += "align: 'right',";
		s += "style: {";
		s += "font: 'normal 11px Verdana, sans-serif'";
		s += "}";
		s += "}";
		s += "},";
		s += "yAxis: [{";
		s += "min: 0,";
		s += "labels: {";
		s += "formatter: function() {";
		s += "return this.value;";
		s += "},";
		s += "style: {";
		s += "color: '#89A54E'";
		s += "}";
		s += "},";
		s += "title: {";
		s += "text: '',";
		s += "style: {";
		s += "color: '#89A54E'";
		s += "},";
		s += "margin: 60";
		s += "}";
		s += "}, {";
		s += "min: 0,";
		s += "labels: {";
		s += "formatter: function() {";
		s += "return this.value;";
		s += "}";
		s += "},";
		s += "title: {";
		s += "text: '',";
		s += "margin: 70,";
		s += "style: {";
		s += "color: '#4572A7'";
		s += "}";
		s += "},";
		s += "opposite: true";
		s += "}],";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.series.name +'</b><br/>'+";
		s += "this.x +': '+ this.y;";
		s += "}";
		s += "},";
		s += "plotOptions: {";
		s += "column: {";
		s += "stacking: 'normal'";
		s += "},";
		s += "area: {";
		s += "stacking: 'normal'";
		s += "}";
		s += "},";
		s += "series: [" + data + "]";
		s += "});";

		s += "});";

		s += "</script>";

		return s;
	}
	
	public static String pieChart(String id, String title,Map<String, List<String>> series) {
		String data = "";
		for (String key : series.keySet()) {
			data += "{";
				data += "type: 'pie',";
			data += "name: '" + key.toString() + "',";
			data += "data: [";
			int first = 1;
			for (String value : series.get(key)) {
				String[] tmp = value.split("-");
				if (first == 1){
					data += "{name : '"+tmp[0] + "',tlv: "+ tmp[1] +", y: "+ tmp[2] +", sliced: true, selected: true},";
				}
				else {
					data += "{name : '"+tmp[0] + "',tlv: "+ tmp[1] +", y: "+ tmp[2]+"},";
				}
				first++;
			}
			data += "]";
			data = data.replace(",]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s= "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '"+id+"',";
		s += "plotBackgroundColor: null,";
		s += "plotBorderWidth: null,";
		s += "plotShadow: false";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.point.name +'</b>: '+ this.point.tlv + '(Erl) - '+ this.y +' %';";
		s += "}";
		s += "},";
		s += "plotOptions: {";
		s += "pie: {";
		s += "allowPointSelect: true,";
		s += "cursor: 'pointer',";
		s += "dataLabels: {";
		s += "enabled: true,";
		s += "color: '#000000',";
		s += "connectorColor: '#000000',";
		s += "formatter: function() {";
		s += "return '<b>'+ this.point.name +'</b>: '+ this.point.tlv + '(Erl) - '+ this.y +' %';";
		s += "}";
		s += "}";
		s += "}";
		s += "},";
		s += "series: [" + data + "]";
		s += "});";
		s += "});";
		s += "</script>";

		return s;
	}
	
	public static String pieChartData(String id, String title,Map<String, List<String>> series) {
		String data = "";
		for (String key : series.keySet()) {
			data += "{";
				data += "type: 'pie',";
			data += "name: '" + key.toString() + "',";
			data += "data: [";
			int first = 1;
			for (String value : series.get(key)) {
				String[] tmp = value.split("-");
				if (first == 1){
					data += "{name : '"+tmp[0] + "',tlv: "+ tmp[1] +", y: "+ tmp[2] +", sliced: true, selected: true},";
				}
				else {
					data += "{name : '"+tmp[0] + "',tlv: "+ tmp[1] +", y: "+ tmp[2]+"},";
				}
				first++;
			}
			data += "]";
			data = data.replace(",]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s= "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id +"',";
		s += "plotBackgroundColor: null,";
		s += "plotBorderWidth: null,";
		s += "plotShadow: false";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.point.name +'</b>: ' + this.y +'%' + ' ('+ this.point.tlv + ')';";
		s += "}";
		s += "},";
		s += "plotOptions: {";
		s += "pie: {";
		s += "allowPointSelect: true,";
		s += "cursor: 'pointer',";
		s += "dataLabels: {";
		s += "enabled: true,";
		s += "color: '#000000',";
		s += "connectorColor: '#000000',";
		s += "formatter: function() {";
		s += "return this.y +'%';";
		s += "}";
		s += "}";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: -10,";
		s += "y: 100,";
		s += "borderWidth: 0";
		s += "},";
		s += "series: [" + data + "]";
		s += "});";
		s += "});";
		s += "</script>";

		return s;
	}
	
	public static String pieLegend(String id, String title, Map<String, List<String>> series) {
		String data = "";
		for (String key : series.keySet()) {
			data += "{";
				data += "type: 'pie',";
			data += "name: '" + key.toString() + "',";
			data += "data: [";
			int first = 1;
			for (String value : series.get(key)) {
				String[] tmp = value.split("-");
				if (first == 1){
					data += "{name : '"+tmp[0] + "',tlv: "+ tmp[1] +", y: "+ tmp[2] +", sliced: true, selected: true},";
				}
				else {
					data += "{name : '"+tmp[0] + "',tlv: "+ tmp[1] +", y: "+ tmp[2]+"},";
				}
				first++;
			}
			data += "]";
			data = data.replace(",]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s= "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id +"',";
		s += "plotBackgroundColor: null,";
		s += "plotBorderWidth: null,";
		s += "plotShadow: false";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.point.name +'</b>: ' + this.y +'%' + ' ('+ this.point.tlv + ')';";
		s += "}";
		s += "},";
		s += "plotOptions: {";
		s += "pie: {";
		s += "allowPointSelect: true,";
		s += "cursor: 'pointer',";
		s += "dataLabels: {";
		s += "enabled: true,";
		s += "color: '#000000',";
		s += "connectorColor: '#000000',";
		s += "formatter: function() {";
		s += "return this.y +'%' ;";
		s += "}";
		s += "}";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: -10,";
		s += "y: 100,";
		s += "borderWidth: 0";
		s += "},";
		s += "series: [" + data + "]";
		s += "});";
		s += "});";
		s += "</script>";

		return s;
	}
	
	public static String pieChart3D(String id, Map<String, List<String>> series) {
		String data = "";
		for (String key : series.keySet()) {
			
			String first = "";
			for (String value : series.get(key)) {
				String[] tmp = value.split("-");
				data = data + first;
				data += "{country: \"" + tmp[0] + "\", value: "+ tmp[1] +"}";
				first = ",";
			}
			
		}

		String s= "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "var legend;";
		s += "var " + id + " = [" + data + "];";
		s += "AmCharts.ready(function () {";
		s += "chart = new AmCharts.AmPieChart();";
		s += "chart.dataProvider = " + id + ";";
		s += "chart.titleField = \"country\";";
		s += "chart.valueField = \"value\";";
		s += "chart.outlineColor = \"#FFFFFF\";";
		s += "chart.outlineAlpha = 0.8;";
		s += "chart.outlineThickness = 2;";
		s += "chart.depth3D = 15;";
		s += "chart.angle = 30;";
		s += "chart.write(\"" + id + "\")";
		s += "});";
		s += "</script>";

		return s;
	}
	
	public static String columnBasic(String id, String title, List<String> categories, Map<String, List<Float>> series) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");
		
		String data = "";
		for (String key : series.keySet()) {
			data += "{name: '" + key + "',";
			data += "data: [";
			for (Float value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s= "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '"+id+"',";
		s += "defaultSeriesType: 'column'";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "xAxis: {";
		s += "categories: [";
		s += cats + ",";
		s += "]";
		s += "},";
		s += "yAxis: {";
		s += "min: 0,";
		s += "title: {";
		s += "text: ''";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "backgroundColor: '#FFFFFF',";
		s += "align: 'center',";
		s += "verticalAlign: 'bottom',";
		s += "floating: true,";
		s += "shadow: true";
		s += "},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return ''+";
		s += "this.x +': '+ this.y;";
		s += "}";
		s += "},";
		s += "plotOptions: {";
		s += "column: {";
		s += "pointPadding: 0.1,";
		s += "borderWidth: 0";
		s += "}";
		s += "},";
		s += "series: [" + data + "]";
		s += "});";
		s += "});";
		s += "</script>";

		return s;
	}
	
	public static String AreaWithMissingPoints(String id, String title, String titleBottom, String titleLeft, String titleReturnX, List<String> categories, Map<String, List<String>> series) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");

		String data = "";
		for (String key : series.keySet()) {
			data += "{";
			data += "name: '" + key + "',";
			data += "data: [";
			for (String value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s = "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "defaultSeriesType: 'area',";
		s += "spacingBottom: 30";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "xAxis: {";
		s += "categories: [" + cats + "],";
		s += "title: {";
		s += "text: '" + titleBottom + "'";
		s += "},";
		s += "labels: {";
		s += "rotation: -45,";
		s += "align: 'right',";
		s += "style: {";
		s += "font: 'normal 12px Verdana, sans-serif'";
		s += "}";
		s += "}";
		s += "},";
		s += "yAxis: {";
		s += "title: {";
		s += "text: '" + titleLeft + "'";
		s += "},";
		s += "labels: {";
		s += "formatter: function() {";
		s += "return this.value;";
		s += "}}},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.series.name +'</b><br/>'+";
		s += "this.x +" + "' ' + '" + titleReturnX + ": '+ this.y;";
		s += "}";
		s += "},";
		s += "plotOptions: {";
		s += "area: {";
		s += "fillOpacity: 0.5";
		s += "}";
		s += "},";
		s += "credits: {";
		s += "enabled: false";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: 5,";
		s += "y: 100,";
		s += "borderWidth: 0},";
		s += "series: [" + data + "]";
		s += "});";
		s += "});";
		s += "</script>";

		return s;
	}
	
	public static String StackedArea(String id, String title, String titleBottom, String titleLeft, String titleReturnX, List<String> categories, Map<String, List<String>> series) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");

		String data = "";
		for (String key : series.keySet()) {
			data += "{";
			data += "name: '" + key + "',";
			data += "data: [";
			for (String value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s = "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "defaultSeriesType: 'area',";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "xAxis: {";
		s += "categories: [" + cats + "],";
		s += "tickmarkPlacement: 'on',";
		s += "title: {";
		s += "text: '" + titleBottom + "',";
		s += "y: 20";
		s += "},";
		s += "labels: {";
		s += "rotation: -45,";
		s += "align: 'right',";
		s += "style: {";
		s += "font: 'normal 12px Verdana, sans-serif'";
		s += "}";
		s += "}";
		s += "},";
		s += "yAxis: {";
		s += "title: {";
		s += "text: '" + titleLeft + "'";
		s += "},";
		s += "labels: {";
		s += "formatter: function() {";
		s += "return this.value;";
		s += "}}},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return ''+";
		s += "this.x +" + "' ' + '" + titleReturnX + ": '+ Highcharts.numberFormat(this.y, 0, ',');";
		s += "}";
		s += "},";
		s += "plotOptions: {";
		s += "area: {";
		s += "stacking: 'normal',";
		s += "lineColor: '#666666',";
		s += "lineWidth: 1,";
		s += "marker: {";
		s += "lineWidth: 1,";
		s += "lineColor: '#666666'";
		s += "}";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: 5,";
		s += "y: 100,";
		s += "borderWidth: 0},";
		s += "series: [" + data + "]";
		s += "});";
		s += "});";
		s += "</script>";

		return s;
	}
	
	public static String columnLineChart(String id, String title, List<String> categories, Map<String, List<Float>> series,Map<String, List<Float>> series_n) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");

		String data = "";
		for (String key : series.keySet()) {
			String[] tmp = key.split("-");
			data += "{";
			if (tmp.length > 2)
				data += "color: '#" + tmp[2] + "',";
			if (tmp.length > 1)
				data += "type: '" + tmp[1] + "',";
			data += "name: '" + tmp[0] + "',";
			data += "data: [";
			for (Float value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");
		
		
		String nguong = "";
		for (String key : series_n.keySet()) {
			String[] tmp = key.split("-");
			nguong += "{";
			nguong += "name: '" + tmp[0] + "',";
			nguong += "data: [";
			for (Float value : series.get(key)) {
				nguong += value + ", ";
			}
			nguong += "]";
			nguong = nguong.replace(", ]", "]");
			nguong += "}, ";
		}
		nguong += "}";
		nguong = nguong.replace("}, }", "}");
		
		
		String s = "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "marginRight: 120,";
		s += "marginBottom: 80,";
		s += "defaultSeriesType: 'column'";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "xAxis: [{";
		s += "categories: [" + cats + "],";
		s += "labels: {";
		s += "rotation: -45,";
		s += "align: 'right',";
		s += "style: {";
		s += "font: 'normal 11px Verdana, sans-serif'";
		s += "}";
		s += "}";
		s += "}],";
		s += "yAxis: [{";
		s += "min: 85,";
		if (title.indexOf("CSSR") != -1)
			s += "max: 100,";
		s += "title: {";
		s += "text: '" + title + "',";
		s += "style: {";
		s += "color: '#89A54E'";
		s += "},";
		s += "margin: 60";
		s += "}";
		s += "}," ;
		s += "{";
		s += "title: {";
		s += "text: 'Nguong',";
		s += "color:'#89A54E'";
		s += "}";
		s +="},";
		s += "opposite: true";
		s +="],";
		s += "tooltip: {shared: true},";
		s += "legend: {";
		s += "layout:'vertical',";
		s += "align: 'left',";
		s += "x: 120,";
		s += "verticalAlign: 'top',";
		s += "y: 100,";
		s += "floating: true,";
		s += "backgroundColor: '#FFFFFF'";
		s += "},";
		s += "series: [{";
		s += "color: '#4572A7',";
		s += "type: 'column',";
		s += "yAxis: 1,";
		s += "data:["+ nguong +"]";
		s += "tooltip: {";
		s += " valueSuffix: ' mm'";
		s += "}";
		s += "},";
		s +="{";
		s += "name: 'Temperature',";
		s += "color: '#89A54E',";
		s += "type: 'spline',";
		s += "data: [" + data + "]";
		s += "tooltip: {";
        s += "valueSuffix: '%'";
        s += "}";
		s += "}";
		s += "]";
		s += "});";

		s += "});";

		s += "</script>";

		return s;
	}
	
	public static String columnBasicForFeedback(String id, String title, List<String> categories, Map<String, List<Float>> series) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");
		
		String data = "";
		for (String key : series.keySet()) {
			data += "{name: '" + key + "',";
			data += "data: [";
			for (Float value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");
		
		String s= "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '"+id+"',";
		s += "defaultSeriesType: 'column'";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "xAxis: {";
		s += "categories: [";
		s += cats + ",";
		s += "]";
		s += "},";
		s += "yAxis: {";
		s += "min: 0,";
		s += "title: {";
		s += "text: ''";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "backgroundColor: '#FFFFFF',";
		s += "align: 'center',";
		s += "verticalAlign: 'bottom',";
		s += "floating: true,";
		s += "shadow: true";
		s += "},";
		s += "tooltip: {";
		s += "    headerFormat: '<span style=\"font-size:10px\">{point.key}</span><table>',";
		s += "    pointFormat: '<tr><td style=\"color:{series.color};padding:0\">{series.name}: </td>' +";
		s += "       '<td style=\"padding:0\"><b>{point.y}</b></td></tr>',";
		s += "    footerFormat: '</table>',";
		s += "    shared: true,";
		s += "    useHTML: true";
		s += "},";
		s += "plotOptions: {";
		s += "column: {";
		s += "pointPadding: 0.1,";
		s += "borderWidth: 0";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "align: 'bottom',";
		s += "borderWidth: 0},";
		s += "series: [" + data + "]";
		s += "});";
		s += "});";
		s += "</script>";
		return s;
	}

	public static Object multiColumnInter(String id, String title,
			List<String> categories, Map<String, List<Integer>> series) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");

		String data = "";
		for (String key : series.keySet()) {
			String[] tmp = key.split("-");
			
			data += "{";
			if (tmp.length > 2)
				data += "color: '#" + tmp[2] + "',";
			if (tmp.length > 1)
				data += "type: '" + tmp[1] + "',";
			data += "name: '" + tmp[0] + "',";
			data += "data: [";
			for (Integer value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s = "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "marginRight: 120,";
		s += "marginBottom: 80,";
		s += "defaultSeriesType: 'column'";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "'";
		s += "},";
		s += "xAxis: [{";
		s += "categories: [" + cats + "],";
		s += "labels: {";
		s += "rotation: -45,";
		s += "align: 'right',";
		s += "style: {";
		s += "font: 'normal 11px Verdana, sans-serif'";
		s += "}";
		s += "}";
		s += "}],";
		s += "yAxis: {";
		s += "min: 0,";
		if (title.indexOf("CSSR") != -1)
			s += "max: 100,";
		s += "title: {";
		s += "text: '" + title + "',";
		s += "style: {";
		s += "color: '#89A54E'";
		s += "},";
		s += "margin: 60";
		s += "}";
		s += "},";
		s += "plotOptions: {";
		s += "series: {";
		s += "pointWidth: 15,";
		s += "groupPadding: 0.5";
		s += "},";
		s += "line: {";
		s += "marker: {";
		s += "enabled: false";
		s += "}";
		s += "}";
		s += "},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.series.name +'</b><br/>'+";
		s += "this.x +': '+ this.y;";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: 10,";
		s += "y: 100,";
		s += "borderWidth: 0";
		s += "},";
		s += "series: [" + data + "]";
		s += "});";

		s += "});";

		s += "</script>";

		return s;
	}
	
	public static String basicLineInter(String id, String title, String titleLeft, List<String> categories, Map<String, List<Integer>> series) {
		StringBuilder buf = new StringBuilder("");
		for (String category : categories) {
			buf.append("'" + category + "', ");
		}
		buf.append(")");
		String cats = buf.toString();
		cats = cats.replace(", )", "");

		String data = "";
		for (String key : series.keySet()) {
			data += "{";
			data += "name: '" + key + "',";
			data += "data: [";
			for (Integer value : series.get(key)) {
				data += value + ", ";
			}
			data += "]";
			data = data.replace(", ]", "]");
			data += "}, ";
		}
		data += "}";
		data = data.replace("}, }", "}");

		String s = "<script type=\"text/javascript\">";
		s += "var chart;";
		s += "$(document).ready(function() {";
		s += "chart = new Highcharts.Chart({";
		s += "chart: {";
		s += "renderTo: '" + id + "',";
		s += "defaultSeriesType: 'line',";
		s += "marginRight: 120,";
		s += "marginBottom: 80";
		s += "},";
		s += "title: {";
		s += "text: '" + title + "',";
		s += "x: -20";
		s += "},";
		s += "xAxis: {";
		s += "categories: [" + cats + "],";
		s += "labels: {";
		s += "rotation: -45,";
		s += "align: 'right',";
		s += "style: {";
		s += "font: 'normal 12px Verdana, sans-serif'";
		s += "}";
		s += "}";
		s += "},";
		s += "yAxis: {";
		s += "title: {";
		s += "text: '" + titleLeft + "'";
		s += "},";
		s += "plotLines: [{";
		s += "value: 0,";
		s += "width: 1,";
		s += "color: '#808080'";
		s += "}]";
		s += "},";
		s += "tooltip: {";
		s += "formatter: function() {";
		s += "return '<b>'+ this.series.name +'</b><br/>'+";
		s += "this.x +': '+ this.y +'';";
		s += "}";
		s += "},";
		s += "legend: {";
		s += "layout: 'vertical',";
		s += "align: 'right',";
		s += "verticalAlign: 'top',";
		s += "x: -10,";
		s += "y: 100,";
		s += "borderWidth: 0";
		s += "},";
		s += "series: [" + data + "]";
		s += "});";

		s += "});";

		s += "</script>";

		return s;
	}

	/*
	 * 
	 * Created by TrungNQ
	 * 
	 */
	public static String drawChart(String chartType, String id, String chartTitle, String titleLeft, Map<String, Number> series, int maxXaxis) {
		StringBuilder str = new StringBuilder("");
		str.append("<script type=\"text/javascript\">");
		str.append("$(function() {$('#" + id + "').highcharts({");
		str.append("chart: {renderTo: '" + id + "',type: '" + chartType + "'},");
		str.append("title: {text: '" + chartTitle + "'},");
		str.append("scrollbar: {enabled:true,barBackgroundColor: 'gray',barBorderRadius: 7,");
		str.append("barBorderWidth: 0,buttonBackgroundColor: 'gray',buttonBorderWidth: 0,");
		str.append("buttonArrowColor: 'yellow',buttonBorderRadius: 7,rifleColor: 'yellow',");
		str.append("trackBorderWidth: 1,trackBorderColor: 'silver',trackBorderRadius: 7},");
		str.append("xAxis: {categories:[");
		for (String key : series.keySet()) {
			str.append("'"+ key + "',");
		}
		str.append("]");
		str.append(",min:0,max:"+ (maxXaxis > 30 ? 29 : maxXaxis-1));
		str.append(",labels: {rotation: -45,style: {fontSize: '13px',fontFamily: 'Verdana, sans-serif' }}},");
		str.append("yAxis: {min: 0,title: {text:'" + titleLeft + "'}},");
		str.append("plotOptions:{series :{pointWidth: 25, maxPointWidth:40,groupPadding:0},");
		str.append("column:{dataLabels:{enabled:true}}},");
		str.append("legend:{enabled:false},");
		str.append("tooltip: {formatter :function() {");
		str.append("return '<b>'+this.x+'<br>" + titleLeft + ": ' +'<b>'+ this.y;}},");
		str.append("series:[{color : '#7cb5ec',data:[");
		for (String key : series.keySet()) {
			str.append(series.get(key) + ",");
		}
		str.append("]}]});});</script>");
		return str.toString().replaceAll(",]", "]");
	}
	
	/*
	 * 
	 * Create by TrungNQ
	 */
	public static String newBasicLine(String id, String title, String titleLeft, List<String> categories, Map<String, List<Number>> series) {

		StringBuilder str = new StringBuilder("");
		str.append("<script type=\"text/javascript\">");
		str.append("$(function() {$('#" + id + "').highcharts({");
		str.append("chart: {renderTo: '" + id + "',");
		str.append("defaultSeriesType: 'line',marginRight: 200,marginBottom: 80},");
		str.append("title: {text: '" + title + "',x: -20},");
		str.append("xAxis: {categories: [");
		for (String category : categories) {
			str.append("'" + category+ "',");
		}
		str.append("],labels: {rotation: -45,align: 'right',style: {font: 'normal 12px Verdana, sans-serif'");
		str.append("}}},yAxis: {title: {text: '" + titleLeft + "'},plotLines: [{value: 0,width: 1,");
		str.append("color: '#808080'}]},tooltip: {formatter: function() {return '<b>'+ this.series.name +'</b><br/>'+");
		str.append("this.x +': '+ this.y +'';}},");
		str.append("legend: {layout: 'vertical',align: 'right',verticalAlign: 'top',x: -10,y: 100,borderWidth: 0");
		str.append("},series: [");
		for (String key : series.keySet()) {
			str.append("{name:'" + key + "',data:[");
			for (Number num : series.get(key)) {
				str.append(num + ",");
			}
			str.append("]},");
		}
		str.append("]");
		str.append("});});</script>");
		return str.toString().replaceAll(",]", "]");
	}
}
