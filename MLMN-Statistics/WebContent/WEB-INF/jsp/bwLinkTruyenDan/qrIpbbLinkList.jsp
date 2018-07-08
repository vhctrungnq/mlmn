<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo BW link truyền dẫn quý</title>
<content tag="heading">Báo cáo BW link truyền dẫn quý</content>

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
	<li class=""><a href="${pageContext.request.contextPath}/report/core/ipbb-link/dy-link.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/ipbb-link/wk-link.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/ipbb-link/mn-link.htm"><span>Báo cáo tháng</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ipbb-link/qr-link.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/ipbb-link/yr-link.htm"><span>Báo cáo năm</span></a></li>
</ul>

<br>
<form method="get" action="qr-link.htm">
	<table class="form wid100">
		<tr>
			<td align="left">
				Direction 
		  		<select name="direction" id="direction"  class="wid15">
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
		  		<select name="link" id="link"  class="wid35">
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

		        &nbsp;&nbsp;Từ quý <input value="${startQuarter}" name="startQuarter" id="startQuarter" maxlength="2"  class="wid3">
		        &nbsp;năm <input value="${startYear}" name="startYear" id="startYear" maxlength="4" class="wid5">
		        
                &nbsp;&nbsp;đến quý <input value="${endQuarter}" name="endQuarter" id="endQuarter" maxlength="2" class="wid3">
                &nbsp;năm <input value="${endYear}" name="endYear" id="endYear" maxlength="4" class="wid5">
		        
                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="Tìm kiếm"/>
            </td>
        </tr>		
	</table>
</form> 

<div  style="overflow: auto;" class="wid100" >
	<display:table name="${quarterList}" id="quarterList" requestURI="" pagesize="100" class="simple2" export="true">
			<display:column property="year" titleKey="year" sortName="year" sortable="true" headerClass="master-header-1"/>
			<display:column property="quarter" titleKey="quarter" sortName="quarter" sortable="true" headerClass="master-header-1 margin" class="margin"/>
			<display:column property="direction" titleKey="direction" sortName="direction" sortable="true" headerClass="master-header-2"/>
			<display:column property="link" titleKey="link" sortName="link" sortable="true" headerClass="master-header-2"/>
    		<display:column property="bandWidth" titleKey="bandWidth" sortName="bandWidth" sortable="true" headerClass="master-header-2 margin" class="margin"/>
    		<display:column property="inKbitSecond" titleKey="inKbitSecond" sortName="inKbitSecond" sortable="true" headerClass="master-header-3"/>
    		<display:column property="inUtilization" titleKey="inUtilization" sortName="inUtilization" sortable="true" headerClass="master-header-3 margin" class="margin OUT_UTILIZATION"/>
    		<display:column property="outKbitSecond" titleKey="outKbitSecond" sortName="outKbitSecond" sortable="true" headerClass="master-header-4"/>
    		<display:column property="outUtilization" titleKey="outUtilization" sortName="outUtilization" sortable="true" headerClass="master-header-4" class="IN_UTILIZATION"/>
	</display:table>
</div>

<script type="text/javascript" >

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
