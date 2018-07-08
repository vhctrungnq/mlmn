<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>msc Hour report</title>
<content tag="heading">MSC PAGING HOURLY REPORT</content>

<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core-era/msc-pag-glo/hr.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core-era/msc-pag-glo/dy.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
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
	                <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
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
	                <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
	<br/>
</div>
<div  id="doublescroll">
		<display:table name="${hrMSCPG}" id="hrMSCPG" requestURI="" pagesize="100" class="simple3" export="true">
		    <display:column property="mscid" titleKey="SYSTEM" headerClass="hide" class="hide"/>
		    <display:column title="SYSTEM" media="html">
		   	 	<a target="_blank" href="${pageContext.request.contextPath}/report/core-era/msc-pag-glo/detail.htm?mscid=${hrMSCPG.mscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${hrMSCPG.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${hrMSCPG.day}"/>">${hrMSCPG.mscid}</a>
		    </display:column>
		    <display:column property ="day"  format="{0,date,dd/MM/yyyy}" titleKey="Day" />
		    <display:column property ="hour" format="{0,number}:00" titleKey="Hour" />
		     <display:column property="firstAtt" titleKey="FIRST_ATT"/>
		    <display:column property="firstAtt" titleKey="First page attempts Iu interface"/>
		    <display:column property="firstGlobalAtt" titleKey="First global page attempts Iu interface"/>
		    <display:column property="repeatedGlobalAtt" titleKey="Repeated global page attempts Iu interface"/>
		    <display:column property="repeatedAtt" titleKey="Repeated page attempts Iu interface"/>
		    <display:column property="firstResponses" titleKey="Page respone to first page Iu interface"/>
		    <display:column property="repeatedResponses" titleKey="Page respone to repeated page Iu interface"/>
		    <display:column property="firstWcdmaSucc" titleKey="Succ WCDMA first page"/>
		    <display:column property="sysWcdmaSucc" titleKey="Succ WCDMA page system"/>
		    <display:column property="endUserWcdmaSucc" titleKey="Succ WCDMA paging end user"/>
		    <display:column property="firstGsmSucc" titleKey="Succ GMS  first page"/>
		    <display:column property="sysGsmSucc" titleKey="Succ GMS paging system"/>
		    <display:column property="endUserGsmSucc" titleKey="Succ GMS paging end user"/>
		    <display:column property="gprsSucc" titleKey="Succ GPRS paging"/>
		    <display:column property="firstGprsSucc" titleKey="Succ GPRS first page"/>
		    <display:column property="secondGprsSucc" titleKey="Succ GPRS second page"/>
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