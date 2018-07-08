<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>cell ibc handover list</title>
<content tag="heading">LIST CELL HANDOVER STATION IBC ${day}</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/ibc/cell-ho/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/cell-ho/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/cell-ho/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm">
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
	                &nbsp;&nbsp;Ngày <input value="${day}" name="day" id="day" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
		<div  style="overflow: auto;">
		<display:table name="${vRpDyCellHoIbc}" id="vRpDyCellHoIbc" requestURI="" pagesize="100" class="simple2" export="true">
			    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
			    <display:column property ="province" titleKey="PROVINCE" />
			    <display:column property="bscid" titleKey="BSC" headerClass="hide" class="hide"/>
			    <display:column titleKey="BSC" media="html">
			    	<a href="${pageContext.request.contextPath}/report/radio/ibc/bsc-ho/hr/details.htm?bscid=${vRpDyCellHoIbc.bscid}&endDate=${day}">${vRpDyCellHoIbc.bscid}</a>
			    </display:column>
			    <display:column property="cellid" titleKey="CELL" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELL" media="html">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell-ho/hr/details.htm?bscid=${vRpDyCellHoIbc.bscid}&cellid=${vRpDyCellHoIbc.cellid}&endDate=${day}">${vRpDyCellHoIbc.cellid}</a>
			    </display:column>
			    <display:column property ="hoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"  titleKey="HO_ATT" />
			    <display:column property="hoSucr" titleKey="HO_SUCR (%)" headerClass="hide" class="hide"/>
			    <display:column titleKey="HO_SUCR (%)" media="html">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/ho/dy.htm?bscid=${vRpDyCellHoIbc.bscid}&cellid=${vRpDyCellHoIbc.cellid}&endDate=${day}">${vRpDyCellHoIbc.hoSucr}</a>
			    </display:column>
			    <display:column property ="ogHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="OG_HO_ATT" />
			    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR(%)" />
			    <display:column property="inHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="IN_HO_ATT"/>
			    <display:column property ="inHoSucr" titleKey="IN_HO_SUCR(%)" />
			    <display:column property="hoRev" titleKey="HO REVERSION(%)"/>
			    <display:column property ="hoLost" titleKey="HO LOST(%)" />
		</display:table>
</div>
</div>

<script type="text/javascript">
	$(function() {
		$( "#day" ).datepicker({
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
			});
		});
	});
</script>
