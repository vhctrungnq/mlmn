<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Center Month Report</title>
<content tag="heading">CENTER QOS MONTHLY REPORT</content>

<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center/hr.htm?region=${temp.region}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo Giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center/dy.htm?region=${temp.region}"><span>Báo cáo Ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/center/mn.htm?region=${temp.region}&startMonth=${startMonth}&startYear=${startYear}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo Tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center/qr.htm?region=${temp.region}"><span>Báo cáo Quý</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center/yr.htm?region=${temp.region}"><span>Báo cáo Năm</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="mn.htm" name = "frmSample" onSubmit = "return ValidateForm()">			  			
			        Trung Tâm
					<select name="region" id="region" onchange="xl()">
					<option value="">--Tất cả--</option>
					        <c:forEach var="temp" items="${regionlist}">
					              <c:choose>
					                <c:when test="${temp.region == region}">
					                    <option value="${temp.region}" selected="selected">${temp.region}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${temp.region}">${temp.region}</option>
					                </c:otherwise>
					              </c:choose>
					    </c:forEach>
					</select>

	            	&nbsp;Từ tháng <select name="startMonth" id="startMonth" onchange="xl()">
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
	            	&nbsp;Tới tháng <select name="endMonth" id="endMonth" onchange="xl()">
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
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
</div>
	
<div  style="overflow: auto;">
		<display:table name="${Mnregion}" id="Mnregion" requestURI="" pagesize="100" class="simple3" export="true">
		    <display:column property ="region"  titleKey="REGION" />
		    <display:column property="month" titleKey="MONTH"/>
	    	<display:column property="year" titleKey="YEAR"/>
		     <display:column property="tDrpr" titleKey="T_DRPR"/>
		    <display:column property="tEmpdr" titleKey="T_EMPDR"/>
		    <display:column property="tBlkr" titleKey="T_BLKR"/>
		  	<display:column property="tNblkr" titleKey="T_NBLKR"/>
		    <display:column property="tHoblkr" titleKey="T_HOBLKR"/>
		    <display:column property="tAsr" titleKey="T_ASR"/>
		    <display:column property="tAvailhf" titleKey="T_AVAILHF"/>
		    <display:column property="tCap" titleKey="T_CAP"/>
		    <display:column property="tOtraf" titleKey="T_OTRAF"/>
		  	<display:column property="tGos" titleKey="T_GOS"/>
		    <display:column property="sBlkr" titleKey="S_BLKR"/>
		    <display:column property="sDrpr" titleKey="S_DRPR"/>
		    <display:column property="sSsr" titleKey="S_SSR"/>
		  	<display:column property="cssr" titleKey="CSSR"/>
		    <display:column property="tUtil" titleKey="T_UTIL"/>
		    <display:column property="ldPagesLoad" titleKey="LD_PAGES_LOAD"/>
		    <display:column property="haftratePercent" titleKey="HAFTRATE_PERCENT"/>
		  	<display:column property="trxVar" titleKey="TRX_VAR"/>
		    <display:column property="tchVar" titleKey="TCH_VAR"/>
		    <display:column property="tchreq" titleKey="TCHREQ"/>
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
	function isDate(dtStr){
		var year = dtStr;
		if (year.length != 4 || year==0 || year<minYear || year>maxYear || year != year.getfullyear()){
			alert("Vui lòng nhập năm trong khoảng từ "+minYear+" đến "+maxYear);
			return false;
		}
		return true;
	}

function ValidateForm(){
	var dt=document.frmSample.startYear;
	var dt1=document.frmSample.endYear;
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