<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>cell to cells handover list</title>
<content tag="heading">LIST CELL TO CELLS HANDOVER FROM ${startDate} TO ${endDate}</content>
<ul class="ui-tabs-nav">
	<!-- li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-to-cells/hr/list.htm?bscid=${bscid}&fromCell=${fromCell}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li-->
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-to-cells/dy/list.htm?bscid=${bscid}&fromCell=${fromCell}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-to-cells/wk/list.htm?bscid=${bscid}&fromCell=${fromCell}"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-to-cells/mn/list.htm?bscid=${bscid}&fromCell=${fromCell}"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			  <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateForm()">
					FROM BSC 
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
				    &nbsp;FROM CELL 
					<select name="fromCell" id="fromCell" onchange="xl()">
						<option  value="">--Select Cell--</option>
				        <c:forEach var="cell" items="${cellList}">
			              <c:choose>
			                <c:when test="${cell.cellid == fromCell}">
			                    <option value="${cell.cellid}" selected="selected">${cell.cellid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${cell.cellid}">${cell.cellid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
	                &nbsp;&nbsp;Từ Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới Ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	          </td>
	        </tr>		
		</table>
	<br/>
	<div  style="overflow: auto;">
		<display:table name="${dyCellToCellsQos}" id="dyCellToCells" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày" sortable="true" sortName="day"/>
		    <display:column property ="bscid" titleKey="FROM BSC" sortable="true" sortName="bscid"/>
		    <display:column property="fromCell" titleKey="FROM CELL" sortable="true" sortName="from_Cell"/> 
		    <display:column property ="toBsc" titleKey="TO BSC" sortable="true" sortName="to_Bsc"/>
		    <display:column property="toCell" titleKey="TO CELL" sortable="true" sortName="to_Cell"/>
		    <display:column property ="hovercnt" titleKey="HO ATT" sortable="true" sortName="hovercnt"/>
		    <display:column property="hoversuc" titleKey="HO SUC" sortable="true" sortName="hoversuc"/>
		    <display:column property="hovesucr" titleKey="HO SUCR(%)" sortable="true" sortName="hovesucr"/>
		    <display:column property="hoverev" titleKey="HO REVERSION(%)" sortable="true" sortName="hoverev"/>
		    <display:column property="hovelost" titleKey="HO LOST(%)" sortable="true" sortName="hovelost"/>
		</display:table>
		<%-- <div class="exportlinks">Export options:
				<a href="${pageContext.request.contextPath}/exportExcel/celltocells.htm?bscid=${bscid}&fromCell=${fromCell}&startDate=${startDate}&endDate=${endDate}"><span class="export excel">Excel </span></a>
		</div> --%>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript">
	function xl(){
		var sub= document.getElementById("submit");
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
		$("select#bscid").change(function(){
			$.getJSON("${pageContext.request.contextPath}/ajax/getCellOfBsc.htm",{bscid: $(this).val()}, function(j){
				var options = '<option  value="">--Select Cell--</option>';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].cellid + '">' + j[i].cellid + '</option>';
				}
				$("#fromCell").html(options);
				$('#fromCell option:first').attr('selected', 'selected');
			})
		})
	});
</script>
