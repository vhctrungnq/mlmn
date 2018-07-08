<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Danh sách Bad Cell 3G</title>
<content tag="heading">LIST BAD CELL 3G BY RNC MONTHLY</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell/by-cell/list.htm"><span>Bad Cell</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell/by-rnc/list.htm?bscid=${bscid}"><span>Bad Cell by RNC</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell/by-district/list.htm"><span>Bad Cell by District</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell/by-province/list.htm"><span>Bad Cell by Province</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell/by-region/list.htm"><span>Bad Cell by Center</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<a href="${pageContext.request.contextPath}/report/radio3g/bad-cell/by-rnc/list.htm?bscid=${bscid}"><span>theo ngày</span></a>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio3g/bad-cell/by-rnc/wk/list.htm?bscid=${bscid}"><span>theo tuần</span></a>&nbsp;
	<span>Theo tháng</span>
	<br/>
	
		<table width="100%" class="form">
			<tr>
			  <td align="left">
			  <form method="get" action="list.htm" name = "frmSample" onSubmit = "return ValidateForm()">
					RNC <input name="bscid" id="bscid" value="${bscid}" size="10" onchange="xl()">
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
	<div  style="overflow: auto;">
		<display:table name="${mnBadCellList}" id="mnBadCell" requestURI="" pagesize="100" class="simple3" export="true">		
		    <display:column property="month" titleKey="MONTH"/>
		    <display:column property="year" titleKey="YEAR"/>
		    <display:column property ="bscid" titleKey="RNC" />
		    <display:column property="badCellR" titleKey="% BAD CELL"/>
		</display:table>
	</div>
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