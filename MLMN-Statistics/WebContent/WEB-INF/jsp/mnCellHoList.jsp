<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>cell handover list</title>
<content tag="heading">CELL HANDOVER MONTHLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-ho/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm"  name="frmSample" onSubmit="return ValidateFormYear()">
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
		<display:table name="${vRpMnCellHo}" id="vRpMnCellHo" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			    <display:column property="month" titleKey="MONTH" sortable="true" sortName="month"/>
				<display:column property="year" titleKey="YEAR"  sortable="true" sortName="year"/>
			    <display:column property ="province" titleKey="PROVINCE"  sortable="true" sortName="province"/>
			    <display:column property="bscid" titleKey="BSCID" headerClass="hide" class="hide"/>
			    <display:column titleKey="BSC" media="html" sortable="true" sortProperty="bscid">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc-ho/mn/details.htm?bscid=${vRpMnCellHo.bscid}&endMonth=${month}&endYear=${year}">${vRpMnCellHo.bscid}</a>
			    </display:column> 
			    <display:column property="cellid" titleKey="CELLID" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELL" media="html" sortable="true" sortProperty="cellid">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell-ho/mn/details.htm?bscid=${vRpMnCellHo.bscid}&cellid=${vRpMnCellHo.cellid}&endMonth=${month}&endYear=${year}">${vRpMnCellHo.cellid}</a>
			    </display:column>
			    <display:column property ="hoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HO_ATT"  sortable="true" sortName="HO_ATT"/>
			    <display:column property ="hoSucr" titleKey="HO_SUCR (%)" sortable="true" sortName="HO_SUCR" class="HO_SUCR"/>
			    <display:column property ="intraHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="INTRA_HO_ATT" sortable="true" sortName="INTRA_HO_ATT"/>
			    <display:column property="intraHoSucr" titleKey="INTRA_HO_SUCR(%)" sortable="true" sortName="INTRA_HO_SUCR"/>
			    <display:column property ="ogHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="OG_HO_ATT" sortable="true" sortName="OG_HO_ATT"/>
			    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR(%)" sortable="true" sortName="OG_HO_SUCR"/>
			    <display:column property="inHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="IN_HO_ATT" sortable="true" sortName="IN_HO_ATT"/>
			    <display:column property ="inHoSucr" titleKey="IN_HO_SUCR(%)" sortable="true" sortName="IN_HO_SUCR"/>
			    <display:column property="hoRev" titleKey="HO REVERSION(%)" sortable="true" sortName="ho_Rev"/>
			    <display:column property ="hoLost" titleKey="HO LOST(%)" sortable="true" sortName="ho_Lost"/>
		</display:table>
</div>
</div>

<script type="text/javascript">
	$(function() {
		$("select#bscid").change(function(){
			$.getJSON("${pageContext.request.contextPath}/ajax/getCellOfBsc.htm",{bscid: $(this).val()}, function(j){
				var options = '<option  value="">--Select Cell--</option>';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].cellid + '">' + j[i].cellid + '</option>';
				}
				$("#cellid").html(options);
				$('#cellid option:first').attr('selected', 'selected');
			});
		});
		
		${highlight}
	});
</script>
