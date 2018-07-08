<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>cell speech drop hourly report</title>
<content tag="heading">Báo cáo Speech DROP Cell ${cellid}</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/cell/drop/hr.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/drop/dy.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="hr.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
				<td align="left">
			        RNC 
			        <select name="bscid" id="bscid">
						<option value="">--Select RNC--</option>
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
	            	&nbsp;Từ <input value="${startHour}" name="startHour" id="startHour" size="4" maxlength="5"> giờ
	            	&nbsp;<input value="${startDate}"" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;Tới <input value="${endHour}" name="endHour" id="endHour" size="4" maxlength="5"> giờ
	            	&nbsp;<input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	
	<div  id="doublescroll">
	<display:table name="${hrCellDropDetails3G}" id="hrCellDropDetails3G" requestURI="" pagesize="100" class="simple5" export="true">
	    <display:column property="region" title="Center" sortable="true" headerClass="master-header-1"/>
	    <display:column property="bscid" title="RNC Name" sortable="true" headerClass="master-header-1"/>	
	    <display:column property="cellid" title="UCell Name" sortable="true" headerClass="master-header-1"/>
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" title="Day" sortable="true" headerClass="master-header-1"/>
	    <display:column title="Hour" sortable="true"  class="margin" headerClass="master-header-1 margin" sortProperty="hour">
	    	${hrCellDropDetails3G.hour}:00
	    </display:column>
	    <display:column property ="speechDrpr" titleKey="SPEECH_DRPR" class="SPEECH_DRPR" sortable="true" headerClass="master-header-2"/>
		<display:column property ="speechDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_DRPS" sortable="true" headerClass="master-header-2"/>
		<display:column property ="speechMpdrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_MPDRPS" sortable="true" headerClass="master-header-2 margin" class="margin"/>
		<display:column property ="outgoingIratHsr" titleKey="OUTGOING_IRAT_HSR" sortable="true" headerClass="master-header-3"/>
		<display:column property ="shoActionRate" titleKey="SHO_ACTION_RATE" sortable="true" headerClass="master-header-3"/>
		<display:column property ="missIntraFreqNbRate" titleKey="MISS_INTRA_FREQ_NB_RATE" sortable="true" headerClass="master-header-3"/>
		<display:column property ="congestionRate" titleKey="CONGESTION_RATE" sortable="true" headerClass="master-header-3"/>
		<display:column property ="iratHoLostRate" titleKey="IRAT_HO_LOST_RATE" sortable="true" headerClass="master-header-3"/>
		<display:column property ="uplinkOutOfSyncRate" titleKey="UPLINK_OUT_OF_SYNC_RATE" sortable="true" headerClass="master-header-3"/>
		<display:column property ="ifhoRate" titleKey="IFHO_RATE" sortable="true" headerClass="master-header-3"/>
		<display:column property ="otherRate" titleKey="OTHER_RATE" sortable="true" headerClass="master-header-3 margin" class="margin"/>
		<display:column property ="shoAction" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SHO_ACTION" sortable="true" headerClass="master-header-1"/>
		<display:column property ="missIntraFreqNb" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="MISS_INTRA_FREQ_NB" sortable="true" headerClass="master-header-1"/>
		<display:column property ="congestion" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CONGESTION" sortable="true" headerClass="master-header-1"/>
		<display:column property ="iratHoLost" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="IRAT_HO_LOST" sortable="true" headerClass="master-header-1"/>
		<display:column property ="uplinkOutOfSync" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UPLINK_OUT_OF_SYNC" sortable="true" headerClass="master-header-1"/>
		<display:column property ="ifho" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="IFHO" sortable="true" headerClass="master-header-1"/>
		<display:column property ="other" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="OTHER" sortable="true" headerClass="master-header-1"/>
	</display:table>
	</div>

	<br>
	<div id="speechDrprChart" style="width: 1000px; margin: 1em auto"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${speechDrprChart}

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
			$.getJSON("${pageContext.request.contextPath}/ajax/getCellOfBsc3G.htm",{bscid: $(this).val()}, function(j){
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

<script type="text/javascript">
    function DoubleScroll(element) {
        var scrollbar= document.createElement('div');
        scrollbar.appendChild(document.createElement('div'));
        scrollbar.style.overflow= 'auto';
        scrollbar.style.overflowY= 'hidden';
        scrollbar.firstChild.style.width= element.scrollWidth+'px';
        scrollbar.firstChild.style.paddingTop= '1px';
        scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
        scrollbar.onscroll= function() {
            element.scrollLeft= scrollbar.scrollLeft;
        };
        element.onscroll= function() {
            scrollbar.scrollLeft= element.scrollLeft;
        };
        element.parentNode.insertBefore(scrollbar, element);
    }

    DoubleScroll(document.getElementById('doublescroll'));
</script>