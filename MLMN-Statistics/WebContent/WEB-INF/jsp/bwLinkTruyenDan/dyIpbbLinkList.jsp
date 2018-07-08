<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo BW link truyền dẫn ngày</title>
<content tag="heading">Báo cáo BW link truyền dẫn ngày</content>

<style>
	table.simple td, table.simple th {
		max-width: 190px;
	}
	
	.pageSize {
		width: 400px; 
		float: right; 
		text-align: right; 
		padding-bottom: 4px;
	}
	
	#doublescroll { overflow: auto; overflow-y: hidden; }
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
	
	.wid35{
		width: 35%;
	}
	.wid15{
		width: 15%;
	}
	.wid3{
		width: 3%;
	}
	.wid5{
		width: 5%;
	}
</style>
	
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ipbb-link/dy-link.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/ipbb-link/wk-link.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/ipbb-link/mn-link.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/ipbb-link/qr-link.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/ipbb-link/yr-link.htm"><span>Báo cáo năm</span></a></li>
</ul>

<br>
	<form method="get" action="dy-link.htm">
		<table style="width:100%;" class="form">
			<tr>
				<td align="left">
					Direction 
			  		<select name="direction" id="direction" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${directionList}">
				              <c:choose>
				                <c:when test="${items.direction == direction}">
				                    <option value="${items.direction}" selected="selected">${items.direction}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.direction}">${items.direction}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
	               &nbsp;&nbsp;Link 
			  		<select name="link" id="link" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${linkList}">
				              <c:choose>
				                <c:when test="${items.link == link}">
				                    <option value="${items.link}" selected="selected">${items.link}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.link}">${items.link}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
			        &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;đến ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit"value="Tìm kiếm"/>
	            </td>
	        </tr>		
		</table>
	</form> 
	
<div  style="overflow: auto;" class="wid100" >
	<display:table name="${dyList}" id="dyList" requestURI="" pagesize="100" class="simple2" export="true">
			<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="day" sortName="day" sortable="true" headerClass="master-header-1"/>
			<display:column property="direction" titleKey="direction" sortName="direction" sortable="true" headerClass="master-header-2"/>
			<display:column property="link" titleKey="link" sortName="link" sortable="true" headerClass="master-header-2"/>
    		<display:column property="bandWidth" titleKey="bandWidth" sortName="bandWidth" sortable="true" headerClass="master-header-2 margin"  class="margin"/>
    		<display:column property="inKbitSecond" titleKey="inKbitSecond" sortName="inKbitSecond" sortable="true" headerClass="master-header-3"/>
    		<display:column property="inUtilization" titleKey="inUtilization" sortName="inUtilization" sortable="true" headerClass="master-header-3 margin" class="margin OUT_UTILIZATION"/>
    		<display:column property="outKbitSecond" titleKey="outKbitSecond" sortName="outKbitSecond" sortable="true" headerClass="master-header-4"/>
    		<display:column property="outUtilization" titleKey="outUtilization" sortName="outUtilization" sortable="true" headerClass="master-header-4" class="IN_UTILIZATION"/>
	</display:table>
</div> 

<script type="text/javascript" >
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
