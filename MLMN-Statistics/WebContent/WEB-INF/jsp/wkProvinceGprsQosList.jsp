<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo Province GPRS-QoS</title>
<content tag="heading">GPRS-QOS PROVINCE WEEKLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/province-gprs-qos/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/province-gprs-qos/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/province-gprs-qos/mn/list.htm"><span>Báo cáo tháng</span></a></li>
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
					&nbsp;PROVINCE 
			        <select name="province" onchange="xl()">
			        	<option value="">Tất cả</option>
				        <c:forEach var="prv" items="${provinceList}">
			              <c:choose>
			                <c:when test="${prv.province == province}">
			                    <option value="${prv.province}" selected="selected">${prv.province}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${prv.province}">${prv.province}</option>
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
<display:table name="${vRpWkProvinceGprsQos}" id="vRpWkProvinceGprsQos" requestURI="" pagesize="100" class="simple2" export="true">
	    <display:column property ="year" titleKey="YEAR" />
	    <display:column property ="week" titleKey="WEEK" />
	    <display:column property ="region" titleKey="REGION" />
	    <display:column property="province" titleKey="PROVINCE" class="hide" headerClass="hide"/>
	    <display:column titleKey="PROVINCE" media="html">
	    	<a href="${pageContext.request.contextPath}/report/radio/province-gprs-qos/wk/details.htm?province=${vRpWkProvinceGprsQos.province}&endWeek=${week}&endYear=${year}">${vRpWkProvinceGprsQos.province}</a>&nbsp;
	    </display:column> 
	    <display:column property ="dlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_REQ" />
	    <display:column property ="dlTbfSucr" titleKey="DL_TBF_SUCR" />
	    <display:column property ="ulTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_REQ" />
	    <display:column property ="ulTbfSucr" titleKey="UL_TBF_SUCR"/>
	    <display:column property ="gdlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GDL_TRAF" />
	    <display:column property ="gulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GUL_TRAF" />
	    <display:column property ="edlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDL_TRAF" />
	    <display:column property ="eulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_TRAF" />
	    <display:column property="dataload" titleKey="DATALOAD" />
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
