<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>CELL HOURLY REPORT</title>
<content tag="heading">CELL SUMMARY CENTER 2 HOURLY REPORT</content>

<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/center2/hr.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startHour=${startHour}&startDate=${startDate}&endHour=${endHour}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center2/dy.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center2/wk.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startWeek=${startWeek}&startYear=${startYear}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center2/mn.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startMonth=${startMonth}&startYear=${startYear}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
	<form:form method="get" commandName="filter" action="hr.htm" name="frmSample" onSubmit="return ValidateForm()">
		<table style="width:100%;" class="form">
			<tr>
			    <td align="left">
					BSC <form:input value="${bscid}" id="bscid" name="bscid" path="bscid" size="10" />
					&nbsp;CELL <form:input value="${cellid}" id="cellid" name="cellid" path="cellid" size="10" />
	                Từ <select name="startHour" id="startHour" onchange="xl()">
	            				<c:forEach var="hour" items="${hourList}">
						              <c:choose>
						                <c:when test="${hour == startHour}">
						                    <option value="${hour}" selected="selected">${hour}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${hour}">${hour}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp; giờ
	                Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10" onchange ="javascript:checkNumber(document.frmSample.startDate);"/>
	                Đến <select name="endHour" id="endHour" onchange="xl()">
	            				<c:forEach var="hour" items="${hourList}">
						              <c:choose>
						                <c:when test="${hour == endHour}">
						                    <option value="${hour}" selected="selected">${hour}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${hour}">${hour}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;  giờ
	                Ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10" onchange ="javascript:checkNumber(document.frmSample.endDate);"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form:form>
	</div>
	<br/>
	
	
<div  style="overflow: auto;">
		<display:table name="${VrphrCell}" id="VrphrCell" requestURI="" pagesize="100" class="simple3" export="true">		    
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
		   	<display:column property ="hour" format="{0,number}:00" titleKey="HOUR" />
		    <display:column property ="bscid"  titleKey="BSC" />
		    <display:column property ="cellid"  titleKey="CELL" />
		    <display:column property="tDrpr" titleKey="T_DRPR"/>
		    <display:column property="sDrpr" titleKey="S_DRPR"/>
		    <display:column property="cssr" titleKey="CSSR"/>
		</display:table>
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
function xl(){
	var sub=document.getElementById("submit");
	sub.focus();
}
	$(function() {
		$( "#startDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		$("#endDate").datepicker({
			dateFormat: "dd/mm/yy",
			showOn:"button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});

		var cachebsc = {},cachecell = {},
		lastXhrBsc,
		lastXhCell;
		$( "#bscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cachebsc ) {
					response( cachebsc[ term ] );
					return;
				}

				lastXhrBsc = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
					cachebsc[ term ] = data;
					if ( xhr === lastXhrBsc ) {
						response( data );
					}
				});
			}
		});
		$( "#cellid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cachecell ) {
					response( cachecell[ term ] );
					return;
				}

				lastXhCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell.htm", request, function( data, status, xhr ) {
					cachecell[ term ] = data;
					if ( xhr === lastXhCell ) {
						response( data );
					}
				});
			}
		});
	});
</script>
