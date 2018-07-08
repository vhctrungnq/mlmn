<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>MSC DAY REPORT</title>
<content tag="heading">MSC TRUNK ROUTE PERFORMANCE CALL DAILY REPORT</content>

<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/core-era/trunkroute-performence/msc/hr.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core-era/trunkroute-performence/msc/dy.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>

<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  	  <form method="get" action="dy.htm" name = "frmSample" onSubmit = "return ValidateForm()">
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
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          	  </form>
	            </td>
	        </tr>		
		</table>
		<br/>
	</div>
		<div  id="doublescroll">
		<display:table name="${dyMSCTrunk}" id="dyMSCTrunk" requestURI="" pagesize="100" class="simple3" export="true">
		    <display:column property="mscid" titleKey="SYSTEM" headerClass="hide" class="hide"/>
		    <display:column title="SYSTEM" media="html" sortable="true">
		   	 	<a href="${pageContext.request.contextPath}/report/core-era/trunkroute-performence/msc/hr.htm?mscid=${dyMSCTrunk.mscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyMSCTrunk.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyMSCTrunk.day}"/>">${dyMSCTrunk.mscid}</a>
		    </display:column>
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>
		    <display:column property="numAcc" titleKey="Number of accumulations" sortable="true"/>
		    <display:column property="numDevices" titleKey="Number of devices" sortable="true"/>
		    <display:column property="incAccTraffLevel" titleKey="Inc Acumulated traffic level" sortable="true"/>
		    <display:column property="ougAccTraffLevel" titleKey="Out Acumulated traffic level" sortable="true"/>
		    <display:column property="incDetSeizuer" titleKey="Inc detected seizures" sortable="true"/>
		    <display:column property="ougDetSeizuer" titleKey="Out detected seizures" sortable="true"/>
		    <display:column property="incCongs" titleKey="Inc calls congestion" sortable="true"/>
		    <display:column property="ougCongs" titleKey="Out calls congestion" sortable="true"/>
		    <display:column property="incAnsws" titleKey="Inc B-answers" sortable="true"/>
		    <display:column property="outAnsws" titleKey="Out B-answers" sortable="true"/>
		    <display:column property="incTraff" titleKey="Inc Traffic" sortable="true"/>
		    <display:column property="ougTraff" titleKey="Out Traffic" sortable="true"/>
		    <display:column property="succSeizuerAtt" titleKey="Succ Seizure Atempts (%)" sortable="true"/>
		    <display:column property="succIncSeizuerAtt" titleKey="Succ Inc Seizure Atempts (%)" sortable="true"/>
		    <display:column property="succOugSeizuerAtt" titleKey="Succ Out Seizure Atempts (%)" sortable="true"/>
		    <display:column property="trunkUtil" titleKey="TRUNK Utiliration (%)" sortable="true"/>
		    <display:column property="trunkCallAnswerRatio" titleKey="TRUNK Call Answers ratio (%)" sortable="true"/>
		    <display:column property="incBanswerRatio" titleKey="Inc Banswer ratio (%)" sortable="true"/>
		    <display:column property="outBanswerRatio" titleKey="Out Banswer ratio (%)" sortable="true"/>
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
		$( "#endDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
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

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getMscid.htm", request, function( data, status, xhr ) {
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