<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Danh sách Cell 3G có lưu lượng &lt; 10 Erlang</title>
<content tag="heading">LIST CELLS 3G TRAFFIC &lt; 10 ERLANG BY RNC</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-cell/list.htm"><span>Cell có lưu lượng &lt; 10 erl</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-rnc/list.htm"><span>Cell có lưu lượng &lt; 10 erl của RNC</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-rnc/list.htm?bscid=${bscid}"><span>Cell có lưu lượng &lt; 10 erl của bscid</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-province/list.htm"><span>Cell có lưu lượng &lt; 10 erl của Province</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-region/list.htm"><span>Cell có lưu lượng &lt; 10 erl của Center</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-rnc/list.htm?bscid=${bscid}"><span>Theo ngày</span></a>&nbsp;
	<span>Theo tuần</span>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-rnc/mn/list.htm?bscid=${bscid}"><span>Theo tháng</span></a>
	<br/>
		<table width="100%" class="form">
			<tr>
			  <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateFormWeek()">
					RNC <input name="bscid" id="bscid" value="${bscid}" size="10" onchange="xl()">
			        &nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" maxlength="2" onchange ="javascript:checkNumber(document.frmSample.startWeek);">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" maxlength="2" onchange="javascript:checkNumber(document.frmSample.endWeek);">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	          </form>
	          </td>
	        </tr>		
		</table>
	<br/>
	<div  style="overflow: auto;">
		<display:table name="${wkBadCellList}" id="wkBadCell" requestURI="" pagesize="100" class="simple3" export="true">		
		    <display:column property="week" titleKey="Week"/>
		    <display:column property="year" titleKey="Year"/>
		    <display:column property ="bscid" titleKey="RNC" />
		    <display:column property="badCellR" titleKey="% BAD CELL"/>
		</display:table>
	</div>
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
	  for(var i =0;i<strLength+1;i++){
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
	function xl(){
		var sub = document.getElementById("submit");
		sub.focus();
	} 
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
</script>
