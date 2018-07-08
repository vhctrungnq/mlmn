<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<content tag="heading">BIỂU ĐỒ TẢI THIẾT BỊ THEO NGÀY</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/chart/rp/tai-thiet-bi/dy.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/chart/rp/tai-thiet-bi/wk.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/chart/rp/tai-thiet-bi/mn.htm"><span>Báo cáo tháng</span></a></li>
</ul>

<table width="100%" class="form">	
	<tr>
	<form:form method="get" action="dy.htm">
		<td style = "width:80px;"><fmt:message key="general.date"/></td>&nbsp;
			<td style = "width:215px;">
				<input type="text" id="date" name="date" value="${date}" />&nbsp;
          			<img alt="calendar" title="Click to choose the date " id="chooseDate" style="cursor: pointer; " src="${pageContext.request.contextPath}/images/calendar.png"/>
	    </td>&nbsp;
	    <td style = "width:170px;">  
			<fmt:message key="general.region"/>	
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
	  		</select>&nbsp;
	  	</td>
	  	<td>	
	  		<input value="${min}" name="min" id="min" maxlength="3" class="wid3">&lt;
			    	Ngưỡng tải &lt;<input value="${max}" name="max" id="max" maxlength="3" class="wid3">&nbsp;&nbsp;&nbsp;
	        <input type="submit" class="button" name="submit" value="View Report" />
	     </td>
	   </form:form>     	      
	   </tr>  
</table>
<br>
<table width = "100%">	   
   <tr>
   	<td > <div id = "cpuChart" style = "max-width:100%; height:500px"></div></td>
   </tr>	
   <tr style = "height:50px;"> </tr>
     <tr>
   	<td> <div id = "memoryChart" style = "max-width:100%; height:500px"></div></td>
   </tr>	
</table>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highstock.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting_v4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
${cpuChart }
${memoryChart }
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
	Calendar.setup({
	    inputField		:	"date",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseDate",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
</script>	