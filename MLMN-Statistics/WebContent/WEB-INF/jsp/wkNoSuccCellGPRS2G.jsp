<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<title>NO SUCCESS CELL GPRS WEEK REPORT</title>
<content tag="heading">NO SUCCESS CELL GPRS WEEK REPORT</content>

<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-no-succ-gprs/hr.htm"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-no-succ-gprs/dy.htm"><span>Báo cáo ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-no-succ-gprs/wk.htm?bscid=${bsc.bscid}&cellid=${cellid}&startWeek=${startWeek}&startYear=${startYear}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-no-succ-gprs/mn.htm"><span>Báo cáo tháng</span></a></li>
</ul>

<div class="ui-tabs-panel">
	<form:form  method="get" commandName="filter" action="wk.htm" name = "frmSample" onSubmit = "return ValidateFormWeek()">
		<table width ="100%" class="form">
			<tr>
			    <td align="left">
					BSC
					<select name="bscid" id="bscid" onchange="xl()" >
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
					CELL <input value="${cellid}" name="cellid" id="cellid" size="10">
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
	<br/>
</div>
<div  style="overflow: auto;">
		<display:table name="${vWkCellData2G}" id="vWkCellData2G" requestURI="" pagesize="100" class="simple3" export="true">
		  <display:column property="week" titleKey="WEEK"/>
	    	<display:column property="year" titleKey="YEAR"/>
		    <display:column property ="bscid"  titleKey="BSCID" />
		    <display:column property="cellid" titleKey="CELLID"/>
		    <display:column property="ulTbfReq" titleKey="UL_TBF_REQ"/>
		    <display:column property="ulTbfFail" titleKey="UL_TBF_FAIL"/>
		    <display:column property="ulTbfSucr" titleKey="UL_TBF_SUCR"/>
		    <display:column property="dlTbfReq" titleKey="DL_TBF_REQ"/>
		    <display:column property="dlTbfFail" titleKey="DL_TBF_FAIL"/>
		    <display:column property="dlTbfSucr" titleKey="DL_TBF_SUCR"/>
		    <display:column property="gprsUlData" titleKey="GPRS_UL_DATA"/>
		    <display:column property="gprsDlData" titleKey="GPRS_DL_DATA"/>
		    <display:column property="edgeUlData" titleKey="EDGE_UL_DATA"/>
		    <display:column property="edgeDlData" titleKey="EDGE_DL_DATA"/>		    
		    <display:column property="gprsUlDataThroughput" titleKey="GPRS_UL_DATA_THROUGHPUT"/>
		    <display:column property="gprsDlDataThroughput" titleKey="GPRS_DL_DATA_THROUGHPUT"/>
		    <display:column property="edgeUlDataThroughput" titleKey="EDGE_UL_DATA_THROUGHPUT"/>
		    <display:column property="edgeDlDataThroughput" titleKey="EDGE_DL_DATA_THROUGHPUT"/>
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
	var cacheCell = {}, lastXhrCell;
	$( "#cellid" ).autocomplete({
		minLength: 2,
		source: function( request, response ) {
			var term = request.term;
			if ( term in cacheCell ) {
				response( cacheCell[ term ] );
				return;
			}

			lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell.htm", request, function( data, status, xhr ) {
				cacheCell[ term ] = data;
				if ( xhr === lastXhrCell ) {
					response( data );
				}
			});
		}
	});
</script>