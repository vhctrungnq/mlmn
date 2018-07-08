<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Danh sách Cell nghẽn TCH</title>
<content tag="heading">LIST CELL TRFFIC &lt; 10 ERLANG WEEKYLY OF REGION</content>
<ul class="ui-tabs-nav">
	<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-cell/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell nghẽn TCH</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-bsc/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell lưu lượng &lt; 10 Erlang của Bsc</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-district/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell lưu lượng &lt; 10 Erlang của District</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-province/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell lưu lượng &lt; 10 Erlang của Province</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-region/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell lưu lượng &lt; 10 Erlang của TT</span></a></li>
</ul>
</ul>
<div class="ui-tabs-panel">
	<a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-region/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Theo Ngày</span></a>
	<span>Theo Tuần</span>
	<a href="${pageContext.request.contextPath}/report/radio/bad-cell-traff/by-region/mnList.htm?endMonth=${endMonth}&endYear=${endYear}"><span>Theo Tháng</span></a>
		<table width="100%" class="form">
			<tr>
			  <td align="left">
			  <form method="get" action="wkList.htm" onSubmit="return ValidateFormWeek()">
					Trung tâm 
			  			<select name="region">
                        <option value="">Tất cả</option>
                        <c:forEach var="item" items="${regionList}">
                          <c:choose>
                            <c:when test="${item.region == region}">
                                <option value="${item.region}" selected="selected">${item.region}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${item.region}">${item.region}</option>
                            </c:otherwise>
                          </c:choose>
                        </c:forEach>
                    </select>
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
		    <display:column property ="week" titleKey="WEEK" sortable="true"/>
		    <display:column property ="year" titleKey="YEAR" sortable="true"/>
		    <display:column property ="region" titleKey="REGION" sortable="true"/>
		    <display:column property="badCellR" titleKey="% Cell lưu lượng < 10 Erlang" sortable="true"/>
		</display:table>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
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
	  for(var i=0;i<strLength+1;i++){
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

$(function() {   
	${highlight}
});
</script>