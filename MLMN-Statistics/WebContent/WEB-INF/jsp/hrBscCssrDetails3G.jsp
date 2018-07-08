<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>rnc speech cssr hourly report</title>
<content tag="heading">SPEECH CSSR RNC ${bscid}</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/rnc/cssr/hr.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/cssr/dy.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="hr.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
				<td align="left">
					Trung tâm 
			  		<select name="region" id="region" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${regionList}">
				              <c:choose>
				                <c:when test="${items.region == region}">
				                    <option value="${items.region}" selected="selected">${items.region}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.region}">${items.region}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
			        &nbsp;RNC 
			        <select name="bscid" id="bscid" style="width: 163px">
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
	            	&nbsp;Từ <select name="startHour" id="startHour"onchange="xl()">
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
	                &nbsp;<input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                &nbsp;Đến <select name="endHour" id="endHour" onchange="xl()">
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
	                &nbsp;<input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	
	<div  id="doublescroll">
	<display:table name="${hrBscCssrDetails3G}" id="hrBscCssrDetails3G" requestURI="" pagesize="100" class="simple5" export="true">
		<display:column property="region" titleKey="Center" sortable="true" headerClass="master-header-1"/>
	    <display:column property="bscid" titleKey="RNC Name" sortable="true"  headerClass="master-header-1"/>
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Day" sortable="true" headerClass="master-header-1"/>
	    <display:column titleKey="Hour" sortable="true"  headerClass="master-header-1 margin" class="margin" sortProperty="hour">
	    	${hrBscCssrDetails3G.hour}:00
	    </display:column>
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
	<div id="csSpeechCssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="psR99CssrChart" style="width: 1000px; margin: 1em auto"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${csSpeechCssrChart}
${psR99CssrChart}

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