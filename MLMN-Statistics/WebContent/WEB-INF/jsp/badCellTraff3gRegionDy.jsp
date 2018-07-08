<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Danh sách Cell có lưu lượng &lt; 10 Erlang</title>
<content tag="heading">LIST CELLS 3G TRAFFIC &lt; 10 ERLANG BY REGION</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-cell/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell có lưu lượng &lt; 10 erl</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-rnc/list.htm?endDate=${endDate}"><span>Cell có lưu lượng &lt; 10 erl của RNC</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-district/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell có lưu lượng &lt; 10 erl của District</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-province/list.htm?startDate=${startDate}&endDate=${endDate}"><span>Cell có lưu lượng &lt; 10 erl của Province</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-region/list.htm?region=${region}&startDate=${startDate}&endDate=${endDate}"><span>Cell có lưu lượng &lt; 10 erl của Center</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<span>theo ngày</span>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-region/wk/list.htm?region=${region}"><span>theo tuần</span></a>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio3g/bad-cell-traff/by-region/mn/list.htm?region=${region}"><span>theo tháng</span></a>
	<br/>
	
		<table width="100%" class="form">
			<tr>
			  <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateForm()">						
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
	                &nbsp;&nbsp;Từ Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới Ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	          </td>
	        </tr>		
		</table>
	<br/>
	<div  style="overflow: auto;">
		<display:table name="${dyBadCellList}" id="dyBadCell" requestURI="" pagesize="100" class="simple3" export="true">
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
		    <display:column property ="region" titleKey="TT" />
		    <display:column property="badCell" titleKey="Số CELL có lưu lượng &lt; 10 Erl"/>
		    <display:column property="totalCell" titleKey="Tổng số Cell"/>
		    <display:column property="badCellR" titleKey="% CELL có lưu lượng &lt; 10 Erl"/>
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
	$(function() {
		$( "#startDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		$( "#endDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
	});
</script>
