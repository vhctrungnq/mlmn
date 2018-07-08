<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>bsc ibc handover list</title>
<content tag="heading">BSC HANDOVER IBC ${week}/${year}</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/bsc-ho/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/ibc/bsc-ho/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/bsc-ho/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateFormWeek()">
					Trung tâm 
			  		<select name="region" id="region" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${regionList}">
				              <c:choose>
				                <c:when test="${items.region == region}">
				                    <option value="${items.region}" selected="selected">${items.region}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.region}">${items.region}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
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
	                Tuần <input value="${week}" name="week" id="week" size="2" maxlength="2" onchange ="javascript:checkNumber(document.frmSample.week);"/>
					<img alt="calendar" title="Click to choose the week number" id="chooseWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	                Năm <input value="${year}" name="year" id="year" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.year);"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
		<div  style="overflow: auto;">
<display:table name="${vRpWkBscHoIbc}" id="vRpWkBscHoIbc" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="region" titleKey="TT"/>
			    <display:column property ="mscid" titleKey="MSCID" />
			    <display:column property="bscid" titleKey="BSCID" headerClass="hide" class="hide"/> 
			    <display:column titleKey="BSC" media="html">
			    	<a href="details.htm?bscid=${vRpWkBscHoIbc.bscid}&endWeek=${week}&endYear=${year}">${vRpWkBscHoIbc.bscid}</a>&nbsp;
			    </display:column>
			    <display:column property ="ogHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="OG_HO_ATT" />
			    <display:column property="ogHoSuc" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="OG_HO_SUC" />
			    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR(%)" />
			    <display:column property="inHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="IN_HO_ATT"/>
			    <display:column property ="inHoSuc" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="IN_HO_SUC" />
			    <display:column property ="inHoSucr" titleKey="IN_HO_SUCR(%)" />
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
        inputField		:	"week",	// id of the input field
        ifFormat		:	"%W",   	// format of the input field
        button			:   "chooseWeek",  	// trigger for the calendar (button ID)
        singleClick		:   false					// double-click mode
    });
    
	$(function() {
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
