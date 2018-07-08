<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>cell speech cssr qos</title>
<content tag="heading">Báo cáo CELL SPEECH CSSR QOS</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/cssr/hr.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/cell/cssr/dy.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  	  <form method="get" action="dy.htm">
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
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          	  </form>
	            </td>
	        </tr>		
		</table>
		<br/>
	
		<div  id="doublescroll">
			<display:table name="${dyCellCssrDetails3G}" id="dyCellCssrDetails3G" requestURI="" pagesize="100" class="simple5" export="true" sort="list">
			    <display:column property="region" titleKey="Center" sortable="true" headerClass="master-header-1"/>
			    <display:column property="bscid" titleKey="RNC Name" sortable="true" headerClass="master-header-1"/>
			    <display:column property="cellid" titleKey="UCell Names (H)" sortable="true" headerClass="hide" class="hide"/>	    
			    <display:column titleKey="UCell Names (H)" media="html" sortable="true" headerClass="master-header-1" sortProperty="cellid">
			    	<a href="${pageContext.request.contextPath}/report/radio3g/cell/cssr/hr.htm?bscid=${dyCellCssrDetails3G.bscid}&cellid=${dyCellCssrDetails3G.cellid }&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyCellCssrDetails3G.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyCellCssrDetails3G.day}"/>">${dyCellCssrDetails3G.cellid}</a>
			    </display:column>
			    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày" sortable="true" headerClass="master-header-1 margin" class="margin"/>
			    <display:column property ="speechTraffic" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFFIC" sortable="true" headerClass="master-header-2"/>
				<display:column property ="availbility" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="AVAILBILITY" sortable="true" headerClass="master-header-2"/>
				<display:column property ="uplinkInter" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UPLINK_INTER" sortable="true" headerClass="master-header-2 margin" class="margin"/>
				<display:column property ="csSpeechCssr" titleKey="CS_SPEECH_CSSR" sortable="true" headerClass="master-header-3"/>
				<display:column property ="csRrcCssr" titleKey="CS_RRC_CSSR" sortable="true" headerClass="master-header-3"/>
				<display:column property ="csRrcOriginalCssr" titleKey="CS_RRC_ORIGINAL_CSSR" sortable="true" headerClass="master-header-3"/>
				<display:column property ="csRrcTerminalCssr" titleKey="CS_RRC_TERMINAL_CSSR" sortable="true" headerClass="master-header-3"/>
				<display:column property ="csSpeechRabCssr" titleKey="CS_SPEECH_RAB_CSSR" sortable="true" headerClass="master-header-3"/>
				<display:column property ="csNasSignalCssr" titleKey="CS_NAS_SIGNAL_CSSR" sortable="true" headerClass="master-header-3 margin" class="margin"/>
				<display:column property ="psR99Cssr" titleKey="PS_R99_CSSR" sortable="true" headerClass="master-header-1"/>
				<display:column property ="psHsupaCssr" titleKey="PS_HSUPA_CSSR" sortable="true" headerClass="master-header-1"/>
				<display:column property ="psHsdpaCssr" titleKey="PS_HSDPA_CSSR" sortable="true" headerClass="master-header-1"/>
				<display:column property ="psRrcCssr" titleKey="PS_RRC_CSSR" sortable="true" headerClass="master-header-1"/>
				<display:column property ="psRrcOriginalCssr" titleKey="PS_RRC_ORIGINAL_CSSR" sortable="true" headerClass="master-header-1"/>
				<display:column property ="psRrcTerminalCssr" titleKey="PS_RRC_TERMINAL_CSSR" sortable="true" headerClass="master-header-1"/>
				<display:column property ="psR99RabCssr" titleKey="PS_R99_RAB_CSSR" sortable="true" headerClass="master-header-1"/>
				<display:column property ="psHsupaRabCssr" titleKey="PS_HSUPA_RAB_CSSR" sortable="true" headerClass="master-header-1"/>
				<display:column property ="psHsdpaRabCssr" titleKey="PS_HSDPA_RAB_CSSR" sortable="true" headerClass="master-header-1"/>
				<display:column property ="psNasSignalCssr" titleKey="PS_NAS_SIGNAL_CSSR" sortable="true" headerClass="master-header-1 margin" class="margin"/>
				<display:column property ="rrcCsAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_CS_ATTS" sortable="true" headerClass="master-header-2"/>
				<display:column property ="rrcCsAttsFail" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_CS_ATTS_FAIL" sortable="true" headerClass="master-header-2"/>
				<display:column property ="rrcPsAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_PS_ATTS" sortable="true" headerClass="master-header-2"/>
				<display:column property ="rrcPsAttsFail" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_PS_ATTS_FAIL" sortable="true" headerClass="master-header-2 margin" class="margin"/>
				<display:column property ="rrcFailDuaUtranReject" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_FAIL_DUA_UTRAN_REJECT" sortable="true" headerClass="master-header-3"/>
				<display:column property ="rrcFailDuaRadio" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_FAIL_DUA_RADIO" sortable="true" headerClass="master-header-3"/>
				<display:column property ="rrcDuaMpLoadControlFail" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_DUA_MP_LOAD_CONTROL_FAIL" sortable="true" headerClass="master-header-3"/>
				<display:column property ="rrcDuaAdmissionControl" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_DUA_ADMISSION_CONTROL" sortable="true" headerClass="master-header-3 margin" class="margin"/>
				<display:column property ="rrcCsDuaAdmissionControl" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_CS_DUA_ADMISSION_CONTROL" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rrcCsAdmissionDeniedRbs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_CS_ADMISSION_DENIED_RBS" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rrcPsDuaAdmissionControl" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_PS_DUA_ADMISSION_CONTROL" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rrcPsAdmissionDeniedRbs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_PS_ADMISSION_DENIED_RBS" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rrcFailAfterAdmissControl" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_FAIL_AFTER_ADMISS_CONTROL" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rrcCsFailDualNodeBlock" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_CS_FAIL_DUAL_NODE_BLOCK" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rrcCsFailDualTransNetwork" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_CS_FAIL_DUAL_TRANS_NETWORK" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rrcPsFailDualNodeBlock" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_PS_FAIL_DUAL_NODE_BLOCK" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rrcPsFailDualTransNetwork" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_PS_FAIL_DUAL_TRANS_NETWORK" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rrcDisconnectDedicateChanel" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_DISCONNECT_DEDICATE_CHANEL" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rrcRadioLinkDeniedAdmCtrl" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RRC_RADIO_LINK_DENIED_ADM_CTRL" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rabSpecchRabAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RAB_SPECCH_RAB_ATTS" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rabSpecchRabAttsFail" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RAB_SPECCH_RAB_ATTS_FAIL" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rabAllServiceBlockAdmCtrl" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RAB_ALL_SERVICE_BLOCK_ADM_CTRL" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rabBlockDualToNode" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RAB_BLOCK_DUAL_TO_NODE" sortable="true" headerClass="master-header-1"/>
				<display:column property ="rabBlockDualTransNetwork" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="RAB_BLOCK_DUAL_TRANS_NETWORK" sortable="true" headerClass="master-header-1"/>
			</display:table>
		</div>
		<br>
		
	<br>
	<div id="csSpeechCssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="psR99CssrChart" style="width: 1000px; margin: 1em auto"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${csSpeechCssrChart}
${psR99CssrChart}

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