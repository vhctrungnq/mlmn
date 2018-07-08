<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<content tag="heading">BIỂU ĐỒ TẢI THIẾT BỊ 30 NGÀY GẦN NHẤT</content>

<table width="100%" class="form">	
	<tr>
	<form:form method="get" action="luy-ke.htm">
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
