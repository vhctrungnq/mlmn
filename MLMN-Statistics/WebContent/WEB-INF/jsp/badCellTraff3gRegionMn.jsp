<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Danh sách Cell 3G có lưu lượng &lt; 10 Erlang</title>
<content tag="heading">LIST CELLS 3G TRAFFIC &lt; 10 ERLANG BY REGION</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-cell/list.htm"><span>Cell có lưu lượng &lt; 10 erl</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-rnc/list.htm?endDate=${endDate}"><span>Cell có lưu lượng &lt; 10 erl của RNC</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-district/list.htm"><span>Cell có lưu lượng &lt; 10 erl của District</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-province/list.htm"><span>Cell có lưu lượng &lt; 10 erl của Province</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-region/list.htm?region=${region}"><span>Cell có lưu lượng &lt; 10 erl của Center</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-region/list.htm?region=${region}"><span>Theo ngày</span></a>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-region/wk/list.htm?region=${region}"><span>Theo tuần</span></a>&nbsp;
	<span>Theo tháng</span>
	<br/>
	
		<table width="100%" class="form">
			<tr>
			  <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateFormYear()">				
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
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	          </form>
	          </td>
	        </tr>		
		</table>
	<br/>
	<div  style="overflow: auto;">
		<display:table name="${mnBadCellList}" id="mnBadCell" requestURI="" pagesize="100" class="simple3" export="true">		
		    <display:column property="month" titleKey="MONTH"/>
		    <display:column property="year" titleKey="YEAR"/>
		    <display:column property ="region" titleKey="TT" />
		    <display:column property="badCellR" titleKey="% BAD CELL"/>
		</display:table>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
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
</script>
