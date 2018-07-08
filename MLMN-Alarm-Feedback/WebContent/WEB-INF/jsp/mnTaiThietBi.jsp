<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<content tag="heading">BIỂU ĐỒ TẢI THIẾT BỊ THEO THÁNG</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/chart/rp/tai-thiet-bi/dy.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/chart/rp/tai-thiet-bi/wk.htm"><span>Báo cáo tuần</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/chart/tai-thiet-bi/ipbb/mn.htm"><span>Báo cáo tháng</span></a></li>
</ul>

<table width="100%" class="form">	
	<tr>
	<form:form method="get" action="mn.htm">&nbsp;
	 <td style = "width:140px;">  
			<fmt:message key="general.month"/>	
			<select name="month" style = "width:60px;">
	     	<option value=""></option>
	      	<c:forEach var="m" items="${monthList}">
	           <c:choose>
	             <c:when test="${m == month}">
	                 <option value="${m}" selected="selected">${m}</option>
	             </c:when>
	             <c:otherwise>
	                 <option value="${m}">${m}</option>
	             </c:otherwise>
	           </c:choose>
	   		</c:forEach>
	  		</select>&nbsp;
	  	</td>
	    <td style = "width:70px;"><fmt:message key = "general.year" /> </td>
	    <td style = "width:80px;">
	    	<input id = "year" name = "year" value = "${year }" maxlength="4" style = "width:80%;">
	    </td>
	    <td style = "width:170px;height:20px;">  
			<fmt:message key="general.region"/>	&nbsp;
			<select name="region" id = "region">
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
	  		<input value="${min}" name="min" id="min" maxlength="3" style = "width:3%" >&lt;
			    	Ngưỡng tải &lt;<input value="${max}" name="max" id="max" maxlength="3" style = "width:3%">&nbsp;
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
