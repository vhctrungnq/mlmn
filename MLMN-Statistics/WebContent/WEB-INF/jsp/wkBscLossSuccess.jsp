<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>BSC HANDOVER WEEK REPORT</title>
<content tag="heading">BSC LOW SUCCESS HADOVER WEEKLY REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/2g/bsc-low-succ/hr.htm?bscid=${bscid}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/2g/bsc-low-succ/dy.htm?bscid=${bscid}"><span>Báo cáo ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/2g/bsc-low-succ/wk.htm?bscid=${bscid}&startWeek=${startWeek}&startYear=${startYear}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/2g/bsc-low-succ/mn.htm?bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form:form method="get" action="wk.htm" commandName="filter" name="frmSample" onSubmit="return ValidateFormWeek()">				
	              	BSC 
					<select name="bscid" id="bscid" style="width: 163px">
			        <option value="">--Select BSC--</option>
				        <c:forEach var="bsc" items="${bscList}">
			              <c:choose>
			                <c:when test="${bsc.bscid == bscid}">
			                    <option value="${bsc.bscid}" selected="selected">${bsc.bscid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${bsc.bscid}">${bsc.bscid}</option>
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
	          </form:form>
	            </td>
	        </tr>		
		</table>
	<br/>
		<div  style="overflow: auto;">
		<display:table name="${vRpWkBscHo}" id="vRpWkBscHo" requestURI="" pagesize="100" class="simple2" export="true">
			    <display:column property="bscid" titleKey="BSC"/> 
				<display:column property="week" titleKey="WEEK"/>
				<display:column property="year" titleKey="YEAR"/>
			    <display:column property ="ogHoAtt" titleKey="OG_HO_ATT" />
			    <display:column property="ogHoSuc" titleKey="OG_HO_SUC"/> 
			    <display:column property ="ogHoSucr" titleKey="OG_HO_SUCR" />
			    <display:column property="inHoAtt" titleKey="IN_HO_ATT" />
			    <display:column property="inHoSuc"  titleKey="IN_HO_SUC"/>
			    <display:column property ="inHoSucr" titleKey="IN_HO_SUCR" />
		</display:table>
</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
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
		  for(var i=0; i<=strlength; i++){
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
		    ifFormat		:	"%W",   	//format of the input field
		    button			:   "chooseStartWeek",  	// trigger for the calendar (button ID)
		    singleClick		:   false				// double-click mode
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

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	});
</script>