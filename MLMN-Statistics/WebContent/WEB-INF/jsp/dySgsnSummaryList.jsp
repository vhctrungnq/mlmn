<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>SGSN SUMMARY REPORT</title>
<content tag="heading">SGSN SUMMARY REPORT</content>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  	  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateForm()">
			        &nbsp;SGSN 
			        <select name="sgsnid" id="sgsnid" onchange="xl()">
						<option value="">--Select SGSN--</option>
				        <c:forEach var="sgsn" items="${sgsnList}">
			              <c:choose>
			                <c:when test="${sgsn.sgsnName == sgsnid}">
			                    <option value="${sgsn.sgsnName}" selected="selected">${sgsn.sgsnName}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${sgsn.sgsnName}">${sgsn.sgsnName}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
	                &nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          	  </form>
	            </td>
	        </tr>		
		</table>
		<br/>
	
		<div id="doublescroll">
			<display:table name="${vRpDySgsnSummary}" id="vRpDySgsnSummary" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"  headerClass="master-header-1"/>		    
			    <display:column property="sgsnid"  titleKey="SGSNID" sortable="true" headerClass="master-header-1" sortProperty="sgsnid"/>
			    <display:column property="utilSubs" titleKey="UTIL SUBS (%)" sortable="true" headerClass="master-header-2"/>
				<display:column property="utilPdpcontext" titleKey="UTIL PDP" sortable="true" headerClass="master-header-2"/>
				<display:column property="attachedSub" titleKey="ATTACHED SUB" sortable="true" headerClass="master-header-3"/>
				<display:column property="attachSucrAllCause" titleKey="ATTACH SUCR (%)" sortable="true" headerClass="master-header-3"/>
				<display:column property="attachSr" titleKey=" ATTACH SR (%)" sortable="true" headerClass="master-header-3"/>
				<display:column property="actPdp" titleKey="ACT PDP" sortable="true" headerClass="master-header-3"/>
				<display:column property="pssrAllCause" titleKey="PSSR (%)" sortable="true" headerClass="master-header-4"/>
				<display:column property="pssr" titleKey=" PSSR (%)" sortable="true" headerClass="master-header-4"/>
				<display:column property="userTraf" titleKey="USER TRAF GB(MB)" sortable="true" headerClass="master-header-5" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
				<display:column property="thp" titleKey="THP Gb (Mbit/s)" sortable="true" headerClass="master-header-5" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
				<display:column property="utilThp" titleKey="UTIL THP (%)" sortable="true" headerClass="master-header-2" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
				<display:column property="intraRauAllCause" titleKey="INTRA RAU SUCR (%)" sortable="true" headerClass="master-header-6"/>
				<display:column property="intraRauSucr" titleKey=" INTRA RAU SUCR (%)" sortable="true" headerClass="master-header-6"/>
				<display:column property="intraPapuAllCause" titleKey="INTRA PAPU RAU SUCR (%)" sortable="true" headerClass="master-header-6"/>
				<display:column property="intraPapuSucr" titleKey=" INTRA PAPU RAU SUCR (%)" sortable="true" headerClass="master-header-6"/>
				<display:column property="interPapuAllCause" titleKey="INTER PAPU RAU SUCR (%)" sortable="true" headerClass="master-header-7"/>
				<display:column property="interPapuSucr" titleKey=" INTER PAPU RAU SUCR (%)" sortable="true" headerClass="master-header-7"/>
				<display:column property="interAllCause" titleKey="INTER RAU SUCR (%)" sortable="true" headerClass="master-header-7"/>
				<display:column property="interRauSucr" titleKey=" INTER RAU SUCR (%)" sortable="true" headerClass="master-header-7"/>
				<display:column property="authSucr" titleKey="AUTH SUCR (%)" sortable="true" headerClass="master-header-1"/>
				<display:column property="pagingSucr" titleKey="PAGING SUCR (%)" sortable="true" headerClass="master-header-2"/>
				<display:column property="loadAvg" titleKey="LOAD AVG(%)" sortable="true" headerClass="master-header-2" />
				<display:column property="attDetach" titleKey=" ATT DETACH" sortable="true" headerClass="master-header-2 " decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
				<display:column property="nwAttDetach" titleKey=" NW ATT DETACH " sortable="true" headerClass="master-header-4" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
				<display:column property="msActPdp" titleKey=" MS Act PDP" sortable="true" headerClass="master-header-3" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
				<display:column property="grandTotal" titleKey="GRAND_TOTAL" sortable="true" headerClass="master-header-3" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
				<display:column property="subTotal" titleKey="SUB_TOTAL" sortable="true" headerClass="master-header-3" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"/>
				
				 <display:setProperty name="export.csv.filename" value="SGSNList.csv"/>
			     <display:setProperty name="export.excel.filename" value="SGSNList.xls"/>
			     <display:setProperty name="export.xml.filename" value="SGSNList.xml"/>
			</display:table>
	</div>
	<div align="center"> <h2 style="padding-top: 10px;">Biểu đồ dữ liệu 30 ngày gần nhất từ: ${startDate7} đến: ${endDate7}</h2> </div>
	<table style="width:99%">
		<tr>
			<td><div id="dlDataChartUti" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="dlDataChartAttach" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="dlDataChartPdp" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="dlDataChartThp" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="dlDataChartInterRau" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="dlDataChartAuthen" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="dlDataChartPaging" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
	</table>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
${dlDataChartUti}
${dlDataChartAttach}
${dlDataChartPdp}
${dlDataChartThp}
${dlDataChartInterRau}
${dlDataChartAuthen}
${dlDataChartPaging}

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.simpletip-1.3.1.pack.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/sgsntip.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
		
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