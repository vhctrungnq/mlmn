<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>M2000 REPORT</title>
<content tag="heading">M2000 REPORT</content>
 
  <ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/RpHlrM2000/hr.htm?nodeid=${node.nodeid}&startDate=${startDate}&startHour=${startHour}&endDate=${endDate}&endHour=${endHour}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/RpHlrM2000/dy.htm?nodeid=${node.nodeid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<form method="post" action="hr.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
			    <td align="left">
				NODE
					<select name="nodeid" id="nodeid" onchange="xl()">
					<option value="">--Tất cả--</option>
					        <c:forEach var="node" items="${NodeList}">
					              <c:choose>
					                <c:when test="${node.hlrid == nodeid}">
					                    <option value="${node.hlrid}" selected="selected">${node.hlrid}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${node.hlrid}">${node.hlrid}</option>
					                </c:otherwise>
					              </c:choose>
					    </c:forEach>
					</select>
	                Từ <select name="startHour" id="startHour">
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
	               <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                Đến <select name="endHour" id="endHour">
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
			               	 </select>&nbsp; giờ
	                <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
	<br/>
</div>
	
	
<div  id="doublescroll">
		<display:table name="${HrHlrM2000}" id="HrHlrM2000" requestURI="" pagesize="100" class="simple3" export="true">
		   		<display:column property="nodeid" titleKey="SYSTEM"/>		   
		    	<display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
		    	<display:column property="hour" titleKey="HOUR"/>	    
			    <display:column property="operReqs" titleKey="USCDB DCI Operation Measurement"/>
		    <display:column property="m3uaLinkRecRate" titleKey="M3UA Link Set Measurement"/>
		    <display:column property="m3uaLinkSendRate" titleKey="M3UA Signaling Link Measurement"/>
		    <display:column property="succrSendRoutInfo" titleKey="Success Rate of Send Routing Info"/>
		    <display:column property="succrCancelLocation" titleKey="Success Rate of Cancel Location"/>
		    <display:column property="succrDeleteSubsData" titleKey="Success Rate of Delete Subscriber Data"/>
		    <display:column property="succrGprsLocatUpdate" titleKey="Success Rate of Update Location"/>
		    <display:column property="succrInsertSubsData" titleKey="Success Rate of Insert Subscriber Data"/>
		    <display:column property="succrProvideRoamNumber" titleKey="Success Rate of Provide Roaming Number"/>
		    <display:column property="succrSendAuthInfo" titleKey="Success Rate of Send Authentication Info"/>
		    <display:column property="succrSendRoutInfoForGprs" titleKey="Success Rate of Send Routing Info for GPRS"/>
		    <display:column property="succrSendRoutInfoForSm" titleKey="Success Rate of Send Routing Info for SM"/>
		    <display:column property="succrUpdateLocation" titleKey="Success Rate of Update Location"/>
		    <display:column property="num2gSubs" titleKey="2G active subs count"/>
		    <display:column property="num3gSubs" titleKey="3G active subs count"/>
		    <display:column property="dataTraffer" titleKey="Including the data transfer status between FE and BE"/>
		    <display:column property="num2gActivSubs" titleKey="2G Roaming Subscribers Measurement"/>
		    <display:column property="num3gActivSubs" titleKey="3G Roaming Subscribers Measurement"/>
			</display:table>
	</div>
	
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>	
<script type = "text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
} 

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