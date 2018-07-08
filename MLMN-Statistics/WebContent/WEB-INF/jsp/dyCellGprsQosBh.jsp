<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo GPRS-QoS Cell ${cellid}</title>
<content tag="heading">Báo cáo GPRS-QOS Cell ${cellid}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/hr/detail.htm?cellid=${cellid}&bscid=${bscid}&day=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/dy/detail.htm?cellid=${cellid}&bscid=${bscid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/wk/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/mn/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/dy/bh.htm?cellid=${cellid}&bscid=${bscid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/wk/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/mn/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="bh.htm">
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
	            	Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
		<table class="form">
	    	<tr>
	    		<td>
	    			<b>Chọn chỉ số hiển thị: </b>
	    		</td>
	    	</tr>
	        <tr>
	        	<td>${checkColumns}</td>
			</tr>
</table>
<br/>
	<div  style="overflow: auto;">
<display:table name="${vRpDyCellGprsQosBh}" id="vRpDyCellGprsQosBh" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
		<display:column titleKey="Busy Hour">
		   	${vRpDyCellGprsQosBh.bh}:00
		</display:column> 
	    <display:column property="province" titleKey="PROVINCE"/>
	    <display:column property="bscid" titleKey="BSC"/> 
	    <display:column property="cellid" titleKey="CELL"/>
	    <display:column property="bhDlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_REQ"/>
	    <display:column property ="bhDlTbfSucr" titleKey="DL_TBF_SUCR" />
	    <display:column property="bhUlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_REQ" />
	    <display:column property ="bhUlTbfSucr" titleKey="UL_TBF_SUCR" />
	    <display:column property="bhGdlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GDL_TRAF"/> 
	    <display:column property="bhGulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GUL_TRAF"/>
	    <display:column property="bhEdlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDL_TRAF"/>
	    <display:column property ="bhEulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_TRAF" />
	</display:table>
</div>
	<br/>
	<div id="dlTbfSucrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="ulTbfSucrChart" style="width: 1000px; margin: 1em auto"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${dlTbfSucrChart}
${ulTbfSucrChart}

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
			});
		});
	});
</script>
