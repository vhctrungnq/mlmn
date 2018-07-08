<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>Báo cáo Cell QoS 3G</title>
<content tag="heading">CELL TRAFFIC 3G WEEKLY REPORT</content>
<ul class="ui-tabs-nav">
	<!--li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/hr/list.htm"><span>Báo cáo giờ</span></a></li-->
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell-traffic/dy.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/cell-traffic/wk.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell-traffic/mn.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="wk.htm">
					Trung tâm 
			  			<select name="region" id="region">
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
			  		&nbsp;PROVINCE 
			        <select name="province">
			        	<option value="">Tất cả</option>
				        <c:forEach var="prv" items="${provinceList}">
			              <c:choose>
			                <c:when test="${prv.province == province}">
			                    <option value="${prv.province}" selected="selected">${prv.province}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${prv.province}">${prv.province}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
				    </select>
			        
			        &nbsp;RNC 
			        <select name="bscid">
			        	<option value="">Tất cả</option>
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
			        &nbsp;CELL <input value="${cellid}" name="cellid" id="cellid" size="10">
	            	&nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
					&nbsp;<input type="submit" class="button" name="submit" value="View Report" />
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
<div  id="doublescroll">
	<display:table name="${vRpWkCell}" id="vRpWkCell" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1" partialList="true" size="${totalSize}">
		<display:column property="week" titleKey="Week" sortable="true" sortName="week" />
	    <display:column property="year" titleKey="Year" sortable="true" sortName="year"/>
	    <display:column property="region" titleKey="TT" sortable="true" sortName="region" />
	    <display:column property="province" titleKey="Province" sortable="true"/>
	    <display:column property="bscid" titleKey="RNC" sortable="true"/>
	   
	    <display:column property="siteid" titleKey="SITE" sortable="true" sortName="siteid"/>
	    <display:column property="cellid" titleKey="CELL" sortable="true" />
	    <display:column property="speechTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"  sortable="true" sortName="SPEECH_TRAFF"/>
	    <display:column property="cs64Traff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_TRAFF" sortable="true" sortName="CS64_TRAFF"/>
	    <display:column property="psr99UlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_TRAFF_CELL"  sortable="true" sortName="PSR99_UL_TRAFF_CELL"/>
	    <display:column property="psr99DlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_TRAFF_CELL"  sortable="true" sortName="PSR99_DL_TRAFF_CELL"/>
	    <display:column property="hsupaUlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_TRAFF_CELL"  sortable="true" sortName="HSUPA_UL_TRAFF_CELL"/>
	    <display:column property ="hsdpaDlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_TRAFF_CELL"  sortable="true" sortName="HSDPA_DL_TRAFF_CELL"/>
	    <display:column property="psr99UlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_THROUGHTPUT" sortable="true" sortName="PSR99_UL_THROUGHTPUT"/>
	    <display:column property="psr99DlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_THROUGHTPUT" sortable="true" sortName="PSR99_DL_THROUGHTPUT"/>
	    <display:column property="hsupaUlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_THROUGHTPUT" sortable="true" sortName="HSUPA_UL_THROUGHTPUT"/>
	    <display:column property="hsdpaDlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_THROUGHTPUT" sortable="true" sortName="HSDPA_DL_THROUGHTPUT"class="margin"/>
	</display:table>
</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
	Calendar.setup({
	    inputField		:	"startWeek",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseStartWeek",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
	Calendar.setup({
	    inputField		:	"endWeek",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseEndWeek",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});

	$(function() {
		${highlight}
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