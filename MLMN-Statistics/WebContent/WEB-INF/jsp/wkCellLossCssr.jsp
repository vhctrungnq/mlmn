<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>CELL WEEK REPORT</title>
<content tag="heading">CELL LOW CSSR WEEKLY REPORT</content>
<ul class="ui-tabs-nav">
  <!-- <li class=""><a href="${pageContext.request.contextPath}/report/loss-cssr/cell/hr.htm?"><span>Báo cáo giờ</span></a></li> -->
  <li class=""><a href="${pageContext.request.contextPath}/report/loss-cssr/cell/dy.htm?"><span>Báo cáo ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/loss-cssr/cell/wk.htm?&startWeek=${startWeek}&startYear=${startYear}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/loss-cssr/cell/mn.htm?"><span>Báo cáo tháng</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
	<form:form method="get" commandName="filter" action="wk.htm" name="frmSample" onSubmit="return ValidateFormWeek()">
		<table width="100%" class="form">
			<tr>
			    <td align="left">
					BSC <form:input path="bscid" size="10" />
					&nbsp;CELL <form:input path="cellid" size="10" />
	                &nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" maxlength="2" onchange ="javascript:checkNumber(document.frmSample.startWeek);">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" maxlength="2" onchange="javascript:checkNumber(document.frmSample.endWeek);">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form:form>
	</div>
	<br/>
	
	
<div  style="overflow: auto;">
		<display:table name="${wkCellLossCssr}" id="wkCellLossCssr" requestURI="" pagesize="100" class="simple3" export="true">		    
		    <display:column property="week" titleKey="WEEK"/>
		    <display:column property="year" titleKey="YEAR"/>
		    <display:column property ="bscid"  titleKey="BSC" />
		    <display:column property ="cellid"  titleKey="CELL" />
		    <display:column property ="cellname"  titleKey="CELLNAME" />
		    <display:column property="tDef" titleKey="T_DEF"/>
		    <display:column property="tAvail" titleKey="T_AVAIL"/>
		    <display:column property="tAtts" titleKey="T_ATTS"/>
		    <display:column property="tSeizs" titleKey="T_SEIZS"/>
		    <display:column property="tDrps" titleKey="T_DRPS"/>
		    <display:column property="tBlks" titleKey="T_BLKS"/>
		    <display:column property="sAtts" titleKey="S_ATTS"/>
		    <display:column property="sDef" titleKey="S_DEF"/>
		    <display:column property="sSeizs" titleKey="S_SEIZS"/>
		    <display:column property="sDrps" titleKey="S_DRPS"/>
		    <display:column property="sBlks" titleKey="S_BLKS"/>
		    <display:column property="cssr" titleKey="CSSR"/>
		</display:table>
	</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
function CalcKeyCode(aChar) {
	  var character = aChar.substring(0,1);
	  var code = aChar.charCodeAt(0);
	  return code;
	}

function checkNumber(val) {
	  var strPass = val.value;
	  var strLength = strPass.length;
	  for(var i =0; i< strLength+1;i++){
		  var lchar = val.value.charAt((strLength) - i);
		  var cCode = CalcKeyCode(lchar);
	
		  if (cCode < 48 || cCode > 57 ) {
		    var myNumber = val.value.substring(0, (strLength) - i);
		    val.value = myNumber;
		  }
	  }
	  var sub = document.getElementById("submit");
		sub.focus();
	  return false;
	}
$(function() {
		Calendar.setup({
		    inputField		:	"startWeek",	// id of the input field
		    ifFormat		:	"%W",   	// format of the input field
		    button			:   "chooseStartWeek",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		});
		Calendar.setup({
		    inputField		:	"endWeek",	// id of the input field
		    ifFormat		:	"%W",   	// format of the input field
		    button			:   "chooseEndWeek",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		}); 
		var cachebsc = {},cachecell = {},
		lastXhrBsc,
		lastXhCell;
		$( "#bscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cachebsc ) {
					response( cachebsc[ term ] );
					return;
				}

				lastXhrBsc = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
					cachebsc[ term ] = data;
					if ( xhr === lastXhrBsc ) {
						response( data );
					}
				});
			}
		});
		$( "#cellid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cachecell ) {
					response( cachecell[ term ] );
					return;
				}

				lastXhCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell.htm", request, function( data, status, xhr ) {
					cachecell[ term ] = data;
					if ( xhr === lastXhCell ) {
						response( data );
					}
				});
			}
		});
	});
</script>
