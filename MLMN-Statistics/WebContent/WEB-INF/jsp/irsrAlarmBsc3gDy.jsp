<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>irsr 3g list</title>
<content tag="heading">BSC IRSR 3G DAILY LIST</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/irsr/by-site/dy/list.htm?bscid=${bscid}&startDate=${startDate}&endDate=${endDate}"><span>ALARM by SITE</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/irsr/by-rnc/dy/list.htm?bscid=${bscid}&startDate=${startDate}&endDate=${endDate}"><span>IRSR by RNC</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/irsr/by-district/dy/list.htm?startDate=${startDate}&endDate=${endDate}"><span>IRSR by District</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/irsr/by-region/dy/list.htm?startDate=${startDate}&endDate=${endDate}"><span>IRSR by Center</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<span><b>Theo ngày</b></span>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio3g/irsr/by-rnc/mn/list.htm?bscid=${bscid}"><span><b>Theo tháng</b></span></a>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio3g/irsr/by-rnc/qr/list.htm?bscid=${bscid}"><span><b>Theo quý</b></span></a>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio3g/irsr/by-rnc/yr/list.htm?bscid=${bscid}"><span><b>Theo năm</b></span></a>
	<br/>
	
		<table width="100%" class="form">
			<tr>
			  <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateForm()">
					RNC <input name="bscid" id="bscid" value="${bscid}" size="10" onchange="xl()">
	                &nbsp;&nbsp;Từ Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới Ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	          </td>
	        </tr>		
		</table>
	<br/>
	<div  style="overflow: auto;">
		<display:table name="${dyIrsrAlarmBsc3gList}" id="dyIrsrAlarmBsc3g" requestURI="" pagesize="100" class="simple3" export="true">		
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
		    <display:column property ="bscid" titleKey="RNC" />
		    <display:column property="irsr" titleKey="% IRSR"/>
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
