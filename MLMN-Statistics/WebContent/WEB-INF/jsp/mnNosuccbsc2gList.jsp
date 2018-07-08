<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<title>bsc Month report</title>
<content tag="heading">NO SUCCESS BSC GPRS MONTHLY REPORT</content>
 
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-no-succ/hr/list.htm?bscid=${bsc.bscid}&startDate=${startDate}&startHour=${startHour}&endDate=${endDate}&endHour=${endHour}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-no-succ/dy/list.htm?bscid=${bsc.bscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-no-succ/wk/list.htm?bscid=${bsc.bscid}"><span>Báo cáo tuần</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-no-succ/mn/list.htm?bscid=${bsc.bscid}&startMonth=${startMonth}&startYear=${startYear}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<form:form  method="get" commandName="filter" action="list.htm" name = "frmSample" onSubmit = "return ValidateFormYear()">
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
	                &nbsp;Từ tháng  <select name="startMonth" id="startMonth" onchange = "xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == startMonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tháng  <select name="endMonth" id="endMonth" onchange = "xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == endMonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id = "submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form:form>
	<br/>
</div>
<div  style="overflow: auto;">
		<display:table name="${Vmnbsc2g}" id="Vmnbsc2g" requestURI="" pagesize="100" class="simple3" export="true">
		     <display:column property="month" titleKey="MONTH"/>
	    	<display:column property="year" titleKey="YEAR"/>
		    <display:column property ="bscid"  titleKey="BSCID" />
		    <display:column property="cells" titleKey="CELLS"/>
		    <display:column property="dlTbfReq" titleKey="DL_TBF_REQ"/>
		    <display:column property="dlTbfFail" titleKey="DL_TBF_FAIL"/>
		    <display:column property="dlTbfSucr" titleKey="DL_TBF_SUCR"/>
		    <display:column property="ulTbfReq" titleKey="UL_TBF_REQ"/>
		    <display:column property="ulTbfFail" titleKey="UL_TBF_FAIL"/>
		    <display:column property="ulTbfSucr" titleKey="UL_TBF_SUCR"/>
		    <display:column property="gprsDlData" titleKey="GPRS_DL_DATA"/>
		    <display:column property="gprsUlData" titleKey="GPRS_UL_DATA"/>
		    <display:column property="edgeDlData" titleKey="EDGE_DL_DATA"/>
		    <display:column property="edgeUlData" titleKey="EDGE_UL_DATA"/>
		</display:table>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>

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
	function xl(){
		var sub = document.getElementById("submit");
		sub.focus();
	  	return false;
	}
</script>	