<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>Cell traffic movement</title>

<content tag="heading">CELL UNSTABLE TRAFFIC 3G</content>
<ul class="ui-tabs-nav">
   <li class="${hr }"><a href="${pageContext.request.contextPath}/report/radio/cell/traffic-movment-3g/hr.htm"><span>Báo cáo giờ</span></a></li>
   <li class="${dy }"><a href="${pageContext.request.contextPath}/report/radio/cell/traffic-movment-3g/dy.htm"><span>Báo cáo ngày</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="${level}.htm" name="frmSample" onSubmit="return ValidateForm()">
		<table width="100%" class="form">
			<tr>
			<td align="left">
			        Bscid 
			        <select name="bscid" id="bscid" onchange="xl()">
						<option value="">--Select BSC--</option>
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
				    &nbsp;&nbsp;Cellid
				    <input value="${cellid}" name="cellid" id="cellid" size="10" >   
				    <c:if test="${level=='hr'}">
					    Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
					Từ giờ <select name="startHour" id="startHour" onchange="xl()">
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
			               	 </select>&nbsp;
	                Tới giờ <select name="endHour" id="endHour" onchange="xl()">
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
			               	 </select>&nbsp;
	              &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	                  </c:if>
				    <c:if test="${level=='dy'}">
					    Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
		            	&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
		            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
				    </c:if>
	            	
	            </td>
	        </tr>		
		</table>
	  </form>
	<div id="doublescroll">
		<display:table name="${cellMovementList}" id="cellMovementList" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày" headerClass="master-header-1" sortable="true" sortName="DAY"/>
			    <display:column property="bscid" titleKey="BSCID"  headerClass="master-header-1"  sortable="true" />
			    <display:column property="cellid" titleKey="CELLID"  headerClass="hide" class="hide"/>
			    <display:column titleKey="CELLID"  headerClass="master-header-1 margin" media="html" sortable="true" class="margin" sortName="cellid">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/traffic-movment-3g/hr.htm?cellid=${cellMovementList.cellid}&bscid=${cellMovementList.bscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${cellMovementList.day}"/>">${cellMovementList.cellid}</a>
			    </display:column>
			    <c:if test="${level=='dy'}">
				    <display:column property ="hsDowntime" titleKey="HS_DOWNTIME" headerClass="master-header-2"  class="rightColumnMana" sortable="true"/>
				    <display:column property="eulDowntime" titleKey="EUL_DOWNTIME" class="rightColumnMana" headerClass="master-header-2" sortable="true"/>
				    <display:column property ="cellDowntime" titleKey="CELL_DOWNTIME" headerClass="master-header-2"  class="rightColumnMana" sortable="true"/>
				</c:if>
				    <display:column property="cs64Traff" titleKey="CS64_TRAFF" class="rightColumnMana" headerClass="master-header-2" sortable="true"/>
				    <display:column property="newTraffic" titleKey="New Traffic" class="rightColumnMana" headerClass="master-header-2" sortable="true"/>
				<c:if test="${level=='dy'}">
				    <display:column property="oldTraffic" titleKey="Old Traffic" class="rightColumnMana" headerClass="master-header-2" sortable="true"/>
				    <display:column property="trafficBias" titleKey="Bias Traffic" class="rightColumnMana" headerClass="master-header-2" sortable="true"/>
				    <display:column property="assessment" titleKey="Assessment"  headerClass="master-header-4" sortable="true"/>
				</c:if>
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