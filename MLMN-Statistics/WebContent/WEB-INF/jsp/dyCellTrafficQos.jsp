<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>cell traffic qos</title>
<content tag="heading">CELL TRAFFIC QOS REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/tTraffic/hr.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell/tTraffic/dy.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  	  <form method="get" action="dy.htm" name="frmSample" onSubmit="return ValidateForm()">
			       BSC 
			        <select name="bscid" id="bscid" onchange="xl()">
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
				    &nbsp;&nbsp;CELL 
			        <select name="cellid" id="cellid" onchange="xl()">
						<option  value="">--Select Cell--</option>
				        <c:forEach var="cell" items="${cellList}">
			              <c:choose>
			                <c:when test="${cell.cellid == cellid}">
			                    <option value="${cell.cellid}" selected="selected">${cell.cellid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${cell.cellid}">${cell.cellid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          	  </form>
	            </td>
	        </tr>		
		</table>
		<br/>
	
		<div style="overflow: auto;">
			<display:table name="${dyCellTrafficQos}" id="dyCellTrafficQos" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>	    
			    <display:column property="bscid" titleKey="BSC" sortable="true"/>	    
			    <display:column property="cellid" titleKey="CELL" headerClass="hide" class="hide"/>  
			    <display:column titleKey="CELL" media="html" sortable="true" sortProperty="cellid">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/tTraffic/hr.htm?bscid=${dyCellTrafficQos.bscid}&cellid=${dyCellTrafficQos.cellid }&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyCellTrafficQos.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyCellTrafficQos.day}"/>">${dyCellTrafficQos.cellid}</a>
			    </display:column>	
			    <display:column property ="f1" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EMPD" sortable="true"/>
			    <display:column property ="f2" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic" sortable="true"/>
			    <display:column property="f3" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic FR" sortable="true"/>
			    <display:column property="f4" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic HR" sortable="true"/>
			    <display:column property ="f5" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic UL FullRate" sortable="true"/>
			    <display:column property ="f6" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic UL HalfRate" sortable="true"/>
			    <display:column property="f7" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic OL FullRate" sortable="true"/> 
			    <display:column property="f8" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic OL HalfRate" sortable="true"/>
			</display:table>
		</div>

		
		<br>
		
	<div id="f1Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f2Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f3Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f4Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f5Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f6Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f7Chart" style="width: 1000px; margin: 1em auto"></div>
	<br>
	<div id="f8Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f9Chart" style="width: 1000px; margin: 1em auto"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${f1Chart}
${f2Chart}
${f3Chart}
${f4Chart}
${f5Chart}
${f6Chart}
${f7Chart}
${f8Chart}
${f9Chart}
<script type="text/javascript">
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
		var cache = {},
		lastXhr;
		$("select#bscid").change(function(){
			$.getJSON("${pageContext.request.contextPath}/ajax/getCellOfBsc.htm",{bscid: $(this).val()}, function(j){
				var options = '<option  value="">--Select Cell--</option>';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].cellid + '">' + j[i].cellid + '</option>';
				}
				$("#cellid").html(options);
				$('#cellid option:first').attr('selected', 'selected');
			})
		})
	});
</script>