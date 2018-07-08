<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<content tag="heading">BIỂU ĐỒ TẢI IPBB THEO TUẦN</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/chart/rp/ipbb/dy.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/chart/rp/ipbb/wk.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/chart/rp/ipbb/mn.htm"><span>Báo cáo tháng</span></a></li>
</ul>

<table width="100%" class="form">	
	<tr>
	<form:form method="get" action="wk.htm">
		<td style = "width:80px;"><fmt:message key="general.week"/></td>&nbsp;
			<td style = "width:100px;">
				<input type="text" id="week" name="week" value="${week}" style = "width:60%;"/>&nbsp;
          			<img alt="calendar" title="Click to choose the week " id="chooseWeek" style="cursor: pointer; " src="${pageContext.request.contextPath}/images/calendar.png"/>
	    </td>&nbsp;
	    <td style = "width:70px;"><fmt:message key = "general.year" /> </td>
	    <td style = "width:80px;">
	    	<input id = "year" name = "year" value = "${year }" maxlength="4" style = "width:80%;">
	    </td>
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
   	<td > <div id = "chartIn" style = "max-width:100%; height:500px"></div></td>
   </tr>	
   <tr style = "height:50px;"> </tr>
     <tr>
   	<td> <div id = "chartOut" style = "max-width:100%; height:500px"></div></td>
   </tr>	
</table>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highstock.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting_v4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
${chartIn }
${chartOut }
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
	Calendar.setup({
	    inputField		:	"week",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseWeek",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
</script>	