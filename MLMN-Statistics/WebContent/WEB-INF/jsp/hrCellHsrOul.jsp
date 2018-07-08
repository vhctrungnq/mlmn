<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>cell ol-ul handover hourly report</title>
<content tag="heading">CELL OL-UL HANDOVER HOURLY REPORT ${cellid}</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell/ho/hr.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/ho/dy.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="hr.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
				<td align="left">
			        BSC 
			        <select name="bscid" id="bscid">
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
			        <select name="cellid" id="cellid">
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
	            	&nbsp;Từ <select name="startHour" id="startHour">
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
	            	&nbsp;<input value="${startDate}"" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;Đến <select name="endHour" id="endHour">
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
			               	 </select>&nbsp; giờ
	            	&nbsp;<input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	
	<div  style="overflow: auto;">
<display:table name="${hrCellHsrOulQosList}" id="hrCellHsrOulQos" requestURI="" pagesize="100" class="simple2" export="true">
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" title="DAY" sortable="true"/>
	    <display:column title="HOUR" sortable="true">
	    	${hrCellHsrOulQos.hour}:00
	    </display:column>
	    <display:column property="bscid" title="BSC" sortable="true"/>	
	    <display:column property="cellid" title="Cell" sortable="true" class="margin" headerClass="margin"/>
		<display:column property ="f1" title="No. of Handover Attempts to UL" sortable="true"/>
			    <display:column property ="f2" titleKey="Handover OL to UL Success (%)" sortable="true"/>
			    <display:column property="f3" titleKey="No. of Handover Attempts to UL (BQ) (%)" sortable="true"/>
			    <display:column property="f4" titleKey="Handover OL to UL Success (BQ)(%)" sortable="true"/>
			    <display:column property ="f5" titleKey="No. of Handover Attempts to OL" sortable="true"/>
			    <display:column property ="f6" titleKey="Handover UL to OL Success (%)" sortable="true"/>
			    <display:column property="f7" titleKey="No. of Handover Attempts to OL (BQ)" sortable="true"/> 
			    <display:column property="f8" titleKey="Hanover UL to OL Success (BQ)(%)" sortable="true"/>
			    <display:column property ="f9" titleKey="Data Availability (%)" class="hide" headerClass="hide" sortable="true"/>
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
