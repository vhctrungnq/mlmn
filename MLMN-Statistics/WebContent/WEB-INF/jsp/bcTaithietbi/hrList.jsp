<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title}</content>

<style>
	table.simple td, table.simple th {
		max-width: 190px;
	} 
	 
	#doublescroll { overflow: auto; overflow-y: hidden; }
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
	
	.wid35{
		width: 35%;
	}
	.wid8{
		width: 8%;
	}
	.wid2{
		width: 2%;
	}
	.wid5{
		width: 5%;
	}
</style>
	
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/taithietbi/hr.htm?type=${type}"><span>Báo cáo giờ</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/taithietbi/dy.htm?type=${type}"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/taithietbi/wk.htm?type=${type}"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/taithietbi/mn.htm?type=${type}"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/taithietbi/qr.htm?type=${type}"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/taithietbi/yr.htm?type=${type}"><span>Báo cáo năm</span></a></li>
</ul>

<br>
	<form method="post" action="hr.htm?type=${type}">
		<table style="width:100%;" class="form">
			<tr>
				<td align="left">
					Region
					<select name="region">
			        	<option value="">Tất cả</option>
				        <c:forEach var="item" items="${regionList}">
			              <c:choose>
			                <c:when test="${item.value == region}">
			                    <option value="${item.value}" selected="selected">${item.name}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${item.value}">${item.name}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
			    	</select>&nbsp;&nbsp;
			    	
			    	<input value="${nguongduoi}" name="nguongduoi" id="nguongduoi" maxlength="2" class="wid2">&nbsp;&lt;
			    	Ngưỡng tải &nbsp;&lt;&nbsp;<input value="${nguongtren}" name="nguongtren" id="nguongtren" maxlength="2" class="wid2">&nbsp;&nbsp;
			    	
					Ne <input value="${neid}" name="ne" id="ne" class="wid8">&nbsp;&nbsp;
					Slot <input value="${slot}" name="slot" id="slot" maxlength="2" class="wid2">&nbsp;&nbsp;
					Từ <input value="${sHour}" name="sHour" id="sHour" maxlength="2" class="wid2">&nbsp;giờ&nbsp;&nbsp;
			        ngày <input value="${sDate}" name="sDate" id="sDate" class="wid8">&nbsp;&nbsp;
			        Đến <input value="${eHour}" name="eHour" id="eHour" maxlength="2" class="wid2">&nbsp;giờ&nbsp;&nbsp;
			        ngày <input value="${eDate}" name="eDate" id="eDate" class="wid8">&nbsp;&nbsp;
		            <input type="submit" class="button" name="submit" id="submit" value="Tìm kiếm"/>
	            </td>
	        </tr>		
		</table>
	</form> 
	
<div  style="overflow: auto;">
	<display:table name="${dataList}" id="item" requestURI="" pagesize="100" class="simple2" export="true">
			<display:column property="day" format="{0,date,dd/MM/yyyy}" title="Ngày" sortName="day" sortable="true" headerClass="master-header-1"/>
			<display:column property="hour" title="Giờ" sortName="hour" sortable="true" headerClass="master-header-1 margin" class="margin"/>
			<display:column property="region" title="Region" sortName="region" sortable="true" headerClass="master-header-2"/>
			<display:column property="ne" title="Ne" sortName="ne" sortable="true" headerClass="master-header-2"/>
    		<display:column property="slot" title="Slot" sortName="slot" sortable="true" headerClass="master-header-2 margin" class="margin"/>
    		<display:column property="cpuAve" title="Cpu Ave" sortName="cpu_ave" sortable="true" headerClass="master-header-3"/>
    		<display:column property="cpuMax" title="Cpu Max" sortName="cpu_max" sortable="true" headerClass="master-header-3 margin" class="margin cpu_max"/>
    		<display:column property="cache" title="Cache" sortName="cache" sortable="true" headerClass="master-header-4"/>
    		<display:column property="memory" title="Memory" sortName="memory" sortable="true" headerClass="master-header-4"/>
			
			<display:setProperty name="export.csv.filename" value="${exportFileName}.csv"/>
			<display:setProperty name="export.excel.filename" value="${exportFileName}.xls"/>
			<display:setProperty name="export.xml.filename" value="${exportFileName}.xml"/>
	</display:table>
</div> 

<script type="text/javascript" >
$( "#sDate" ).datepicker({
	dateFormat: "dd/mm/yy",
	showOn: "button",
	buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
	buttonImageOnly: true
});
$( "#eDate" ).datepicker({
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
