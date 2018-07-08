<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>cell sdcch drop qos</title>
<content tag="heading">CELL SDCCH DROP QOS DAILY REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell/sdrop/hr.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell/sdrop/dy.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  	  <form method="get" action="dy.htm">
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
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          	  </form>
	            </td>
	        </tr>		
		</table>
		<br/>
	
		<div style="overflow: auto;">
			<display:table name="${dyCellDcrsQos}" id="dyCellDcrsQos" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày" sortable="true"/>	    
			    <display:column property="bscid" titleKey="BSC" sortable="true"/>	  
			    <display:column property="cellid" titleKey="CELL" headerClass="hide" class="hide"/>  
			    <display:column titleKey="CELL" media="html" sortable="true" sortProperty="cellid">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/sdrop/hr.htm?bscid=${dyCellDcrsQos.bscid}&cellid=${dyCellDcrsQos.cellid }&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyCellDcrsQos.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyCellDcrsQos.day}"/>">${dyCellDcrsQos.cellid}</a>
			    </display:column>	
			    <display:column property ="f1" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Total No. of Dropperd SDCCH Connections" sortable="true"/>
			    <display:column property ="f2" titleKey="SDCCH DROP (%)" sortable="true"/>
			    <display:column property="f3" titleKey="SDCCH Erlang Minutes per Drop" sortable="true"/>
			    <display:column property="f4" titleKey="Drop Reason, Low SS (%)" sortable="true"/>
			    <display:column property ="f5" titleKey="Drop Reason, Bad Quality (%)" sortable="true"/>
			    <display:column property ="f6" titleKey="Drop Reason, Excessive TA(%)" sortable="true"/>
			    <display:column property="f7" titleKey="Drop Reason, Other (%)" sortable="true"/> 
			    <display:column property="f8" titleKey="Handover Lost of all SDCCH Drop (%)" sortable="true" class="margin" headerClass="margin"/>
			    <display:column property ="f9" titleKey="Data Availability (%)" sortable="true"/>
			</display:table>
		</div>
		<br>
		
	<div id="chart" style="width: 1000px; margin: 1em auto"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${chart}

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