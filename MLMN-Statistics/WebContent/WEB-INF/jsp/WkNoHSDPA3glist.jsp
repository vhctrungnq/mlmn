<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>No HSDPA data cells day report</title>
<content tag="heading">NO HSDPA DATA CELLS WEEKLY REPORT</content>

<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell-nohsdpa-data/dy.htm?"><span>Báo cáo ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/cell-nohsdpa-data/wk.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startWeek=${startWeek}&startYear=${startYear}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell-nohsdpa-data/mn.htm?"><span>Báo cáo tháng</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
 <form:form commandName="filter" method="post" action="wk.htm" name = "frmSample" onSubmit = "return ValidateForm()">
  <table width="100%" class="form">
   <tr>
       <td align="left">
         		 	RNC <form:input path="bscid" size="10"/>&nbsp;&nbsp;
     			 	CELL <form:input path="cellid" size="10"/>&nbsp;&nbsp;
     			  	&nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" maxlength="2" onchange ="javascript:checkNumber(document.frmSample.startWeek);">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" maxlength="2" onchange ="javascript:checkNumber(document.frmSample.endWeek);">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
             </td>
         </tr>  
  </table>
 </form:form>
 <br/>
</div> 
 
<div  style="overflow: auto;">
  <display:table name="${Vwkcell3g}" id="Vwkcell3g" requestURI="" pagesize="100" class="simple3" export="true">
		    <display:column property="bscid" titleKey="RNC"/>
		     <display:column property ="cellid"  titleKey="UCELL" />
		    <display:column property="week" titleKey="WEEK"/>
	 	    <display:column property="year" titleKey="YEAR"/>
	 	    <display:column property="speechTraff" titleKey="SPEECH_TRAFF"/>
		    <display:column property="hsupaUlThroughtput" titleKey="HSUPA_UL_THROUGHTPUT"/>
		    <display:column property="hsdpaDlThroughtput" titleKey="HSDPA_DL_THROUGHTPUT"/>
		</display:table>
 </div>
 
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />  

<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
} 
function CalcKeyCode(aChar) {
	   var character = aChar.substring(0,1);
	   var code = aChar.charCodeAt(0);
	   return code;
	 }

	function checkNumber(val) {
		var strPass = val.value;
		var strLength = strPass.length;
		for(var i=1; i<= strLength;i++){
		  var lchar = val.value.charAt((strLength) - i);
		  var cCode = CalcKeyCode(lchar);

		  if (cCode < 48 || cCode > 57 ) {
		    var myNumber = val.value.substring(0, (strLength)-i);
		    val.value = myNumber;
		  }
		}
		  var sub = document.getElementById("submit");
			sub.focus();
		  return false;
		}
	var minYear=1900;
	var maxYear=2100;
	var minWeek=1;
	var maxWeek=53;
	function isDate(dtStr){
		var year = dtStr;
		if (year.length != 4 || year==0 || year<minYear || year>maxYear || year != year.getfullyear()){
			alert("Vui lòng nhập năm trong khoảng từ "+minYear+" đến "+maxYear);
			return false;
		}
		return true;
	}
	function isDate2(dtStr){
		var week = dtStr;
		if (week==0 || week<minWeek || week>maxWeek){
			alert("Vui lòng nhập tuần trong khoảng từ "+minWeek+" đến "+maxWeek);
			return false;
		}
		return true;
	}

	function ValidateForm(){
		
		var dt=document.frmSample.startYear;
		var dt1=document.frmSample.endYear;
		var wk=document.frmSample.startWeek;
		var wk1=document.frmSample.endWeek;
		
		if (isDate2(wk.value)==false){
			wk.focus();
			return false;
		}
		if (isDate2(wk1.value)==false){
			wk1.focus();
			return false;
		}
		if (isDate(dt.value)==false){
			dt.focus();
			return false;
		}
		if (isDate(dt1.value)==false){
			dt1.focus();
			return false;
		}
		
	    return true;
	 }
</script>
<script type="text/javascript">
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
		   var cache = {},
		  lastXhr;
		  $( "#bscid" ).autocomplete({
		   minLength: 2,
		   source: function( request, response ) {
		    var term = request.term;
		    if ( term in cache ) {
		     response( cache[ term ] );
		     return;
		    }
		
		    lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc3G.htm", request, function( data, status, xhr ) {
		     cache[ term ] = data;
		     if ( xhr === lastXhr ) {
		      response( data );
		     }
		    });
		   } 
		  });
		  var cacheCell= {},
		  lastXhrCell;
		  $( "#cellid" ).autocomplete({
		   minLength: 3,
		   source: function( request, response ) {
		    var term = request.term;
		    if ( term in cacheCell ) {
		     response( cacheCell[ term ] );
		     return;
		    }
		
		    lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell3G.htm", request, function( data, status, xhr ) {
		     cacheCell[ term ] = data;
		     if ( xhr === lastXhrCell ) {
		      response( data );
		     }
		    });
		   }
		  });
		 });
</script>