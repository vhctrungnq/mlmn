<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>msc Hour report</title>
<content tag="heading">MSC MGW ACCESSIBILITY HOURLY REPORT</content>

<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/msc-mgw/hr.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/msc-mgw/dy.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
	<form method="get" action="hr.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
			    <td align="left">
					MSC
					<select name="mscid" id="mscid" onchange="xl()">
					<option value="">--Select MSC--</option>
					        <c:forEach var="msc" items="${mscList}">
					              <c:choose>
					                <c:when test="${msc.mscid == mscid}">
					                    <option value="${msc.mscid}" selected="selected">${msc.mscid}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${msc.mscid}">${msc.mscid}</option>
					                </c:otherwise>
					              </c:choose>
					    </c:forEach>
					</select>
	                Từ<select name="startHour" id="startHour">
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
	                Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                Ðến <select name="endHour" id="endHour">
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
	                Ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
	<br/>
</div>
<div  style="overflow: auto;">
		<display:table name="${hrMSCMGW}" id="hrMSCMGW" requestURI="" pagesize="100" class="simple3" export="true">
		    <display:column property="mscid" titleKey="SYSTEM"/>
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="Day" />
		    <display:column property ="hour" format="{0,number}:00" titleKey="Hour" />
		    <display:column property="mgwNotReach" titleKey="MGW not reachable MSC server (sec)"/>
		    <display:column property="mgwNotReachSignSec" titleKey="MGW not reachable MSC server due to signalling outage (sec)"/>
		    <display:column property="mgwNotReachSignTimes" titleKey="MGW not reachable MSC server due to signalling outage (times)"/>
		    <display:column property="contextSeizureSucc" titleKey="Succ context seizure (%)"/>
		    <display:column property="gcpSentReceived" titleKey="GCP sent recived msg ratio (%)"/>
		    <display:column property="gcpRetransmitted" titleKey="GCP retransmittedOT Trans Rate (%)"/>
		     <display:column property="gcpTransaction" titleKey="GCP nr Transaction pending reciecved (%)"/>
		</display:table>
	</div>

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

		$("#notaccordion").addClass("ui-accordion ui-widget ui-helper-reset ui-accordion-icons")
		.find("h3")
			.addClass("ui-accordion-header ui-helper-reset ui-state-active ui-corner-top ui-state-focus")
			.prepend('<span class="ui-icon ui-icon-triangle-1-s"/>')
			.click(function() {
				$(this).toggleClass("ui-state-active ui-corner-top ui-state-focus ui-state-default ui-corner-all")
				.find("> .ui-icon").toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s")
				.end().next().toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s").toggle();
				return false;
			})
			.next().addClass("ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom").show();
		
		var cache = {},
		lastXhr;
		$( "#mscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getMsc.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	});
</script>