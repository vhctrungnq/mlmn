<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>MSC HOURLY REPORT</title>
<content tag="heading">MSC TRUNKROUTE ROUTE PERFORMANCE HOURLY REPORT</content>

<div class="ui-tabs-panel">
	<form method="get" action="detail.htm" name = "frmSample" onSubmit = "return ValidateForm()">
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
	                Từ <select name="startHour" id="startHour" onchange="xl()">
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
	                Đến <select name="endHour" id="endHour" onchange="xl()">
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
	
<div  id="doublescroll">
		<display:table name="${hrMSCTrunk}" id="hrMSCTrunk" requestURI="" pagesize="100" class="simple3" export="true">
			 <display:column property="mscid" titleKey="SYSTEM"/>
			 <display:column property="routeid" titleKey="ROUTE" headerClass="hide" class="hide"/>
			 <display:column title="ROUTE" media="html">
		   	 	<a target="_blank" href="${pageContext.request.contextPath}/report/core-era/trunkroute-performence/msc/list.htm?routeid=${hrMSCTrunk.routeid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${hrMSCTrunk.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${hrMSCTrunk.day}"/>">${hrMSCTrunk.routeid}</a>
		    </display:column>
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
		    <display:column property ="hour" format="{0,number}:00" titleKey="HOUR" />
		    <display:column property="numAcc" titleKey="Number of accumulations"/>
		    <display:column property="numDevices" titleKey="Number of devices"/>
		    <display:column property="incAccTraffLevel" titleKey="Inc Acumulated traffic level"/>
		    <display:column property="ougAccTraffLevel" titleKey="Out Acumulated traffic level"/>
		    <display:column property="incDetSeizuer" titleKey="Inc detected seizures"/>
		    <display:column property="ougDetSeizuer" titleKey="Out detected seizures"/>
		    <display:column property="incCongs" titleKey="Inc calls congestion"/>
		    <display:column property="ougCongs" titleKey="Out calls congestion"/>
		    <display:column property="incAnsws" titleKey="Inc B-answers"/>
		    <display:column property="outAnsws" titleKey="Out B-answers"/>
		    <display:column property="incTraff" titleKey="Inc Traffic"/>
		    <display:column property="ougTraff" titleKey="Out Traffic"/>
		    <display:column property="succSeizuerAtt" titleKey="Succ Seizure Atempts (%)"/>
		    <display:column property="succIncSeizuerAtt" titleKey="Succ Inc Seizure Atempts (%)"/>
		    <display:column property="succOugSeizuerAtt" titleKey="Succ Out Seizure Atempts (%)"/>
		    <display:column property="trunkUtil" titleKey="TRUNK Utiliration (%)"/>
		    <display:column property="trunkCallAnswerRatio" titleKey="TRUNK Call Answers ratio (%)"/>
		    <display:column property="incBanswerRatio" titleKey="Inc Banswer ratio (%)"/>
		    <display:column property="outBanswerRatio" titleKey="Out Banswer ratio (%)"/>
		</display:table>
	</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type = "text/javascript">
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
		$("#endDate").datepicker({
			dateFormat: "dd/mm/yy",
			showOn:"button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});

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