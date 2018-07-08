<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>location gprs cs list</title>
<content tag="heading">LIST GPRS-CS REGION WEEK ${week}/${year}</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm">
					Trung tâm 
			  			<select name="region" id="region">
								<option value=""> Tất cả </option>
								<option value="TT2"> TT2 </option>
			                    <option value="TT6"> TT6 </option>
			              </select>
					LOCATION 
			        <select name="location">
			        	<option value="">Tất cả</option>
				        <c:forEach var="prv" items="${locationList}">
			              <c:choose>
			                <c:when test="${prv.location == location}">
			                    <option value="${prv.location}" selected="selected">${prv.location}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${prv.location}">${prv.location}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
				    </select>
	                Tuần <input value="${week}" name="week" id="week" size="2" maxlength="2"/>
					<img alt="calendar" title="Click to choose the week number" id="chooseWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	                Năm <input value="${year}" name="year" id="year" size="4" maxlength="4"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
		<div  id="doublescroll">
<display:table name="${vRpWkLocationGprsCs}" id="vRpWkLocationGprsCs" requestURI="" pagesize="100" class="simple2" export="true">
			    <display:column property ="region" titleKey="TT" />
			    <display:column property ="location" titleKey="LOCATION" media="html">
			    	<a href="details.htm?location=${vRpWkLocationGprsCs.location}&endWeek=${week}&endYear=${year}">${vRpWkLocationGprsCs.location}</a>&nbsp;
			    </display:column> 
			    <display:column property ="csxLevel1" titleKey="CSX_LEVEL1" />
			    <display:column property ="csxLevel2" titleKey="CSX_LEVEL2" />
			    <display:column property ="csxLevel3" titleKey="CSX_LEVEL3" />
			    <display:column property ="csxLevel4" titleKey="CSX_LEVEL4"/>
			    <display:column property ="mcsxLevel1" titleKey="MCSX_LEVEL1" />
			    <display:column property ="mcsxLevel2" titleKey="MCSX_LEVEL2" />
			    <display:column property ="mcsxLevel3" titleKey="MCSX_LEVEL3" />
			    <display:column property ="mcsxLevel4" titleKey="MCSX_LEVEL4" />
			    <display:column property ="mcsxLevel5" titleKey="MCSX_LEVEL5" />
			    <display:column property ="mcsxLevel6" titleKey="MCSX_LEVEL6"/>
			    <display:column property ="mcsxLevel7" titleKey="MCSX_LEVEL7" />
			    <display:column property ="mcsxLevel8" titleKey="MCSX_LEVEL8" />
			    <display:column property ="mcsxLevel9" titleKey="MCSX_LEVEL9" />
			    <display:column property="dataload" titleKey="DATALOAD" />
		</display:table>
</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
    Calendar.setup({
        inputField		:	"week",	// id of the input field
        ifFormat		:	"%W",   	// format of the input field
        button			:   "chooseWeek",  	// trigger for the calendar (button ID)
        singleClick		:   false					// double-click mode
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