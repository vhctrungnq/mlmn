<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<content tag="heading">BIỂU ĐỒ CHART FEEDBACK THEO GIỜ</content>

<table width="100%">	
	<tr>
	<form:form method="get" action="list.htm" >
		<td style ="width:70px;"><fmt:message key="general.date"/>
		</td>&nbsp;
		<td style = "width:150px;">
			<input type="text" id="date" name="date" value="${date}" maxlength="10" size="8"/>&nbsp;
          	<img alt="calendar" title="Click to choose the date " id="chooseDate" style="cursor: pointer; " src="${pageContext.request.contextPath}/images/calendar.png"/>
	    </td>&nbsp;
	    <td style = "width:150px;">  
			Tỉnh 
			<select name="province">
	     	<option value="">Tất cả</option>
	      	<c:forEach var="item" items="${provinceList}">
	           <c:choose>
	             <c:when test="${item == province}">
	                 <option value="${item}" selected="selected">${item}</option>
	             </c:when>
	             <c:otherwise>
	                 <option value="${item}">${item}</option>
	             </c:otherwise>
	           </c:choose>
	   		</c:forEach>
	  		</select>
	  	</td>&nbsp;
	  	<td>
	  		<input type="submit" class="button" name="submit" value="View Report" />
	  	</td>
	  	
	   </form:form>     	      
	   </tr>  
</table>

<table width = "100%">	   
   <tr>
   	<td > <div id = "chart" style = "max-width:100%; height:500px"></div></td>
   </tr>		
</table>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highstock.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting_v4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
${chart }

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