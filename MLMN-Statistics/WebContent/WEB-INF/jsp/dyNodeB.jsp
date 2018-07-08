<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>MSC DAY REPORT</title>
<content tag="heading">GIÁM SÁT CHẤT LƯỢNG NODEB ${title} REPORT</content>    
<%@ include file="/WEB-INF/jsp/includes/filter2G.jsp" %>
<div  id="doublescroll">
		<display:table name="${nodeBList}" id="nodeBList" requestURI="" pagesize="100" class="simple2" export="true">
		    <c:choose> 
			    <c:when test="${level=='dy'}">
				    <display:column property="day" titleKey="Day" format="{0,date,dd/MM/yyyy}" sortable="true" headerClass="master-header-1"/>
				    <display:column  property="region" titleKey="Region" sortable="true" headerClass="master-header-1"/>
			    	<display:column  property="bscid" titleKey="Bscid" sortable="true" headerClass="master-header-1"/>
			    	<display:column property="linkid" titleKey="NodeB" headerClass="hide" class="hide"/>
				    <display:column titleKey="NodeB" media="html" sortable="true" headerClass="master-header-1">
				   	 	<a href="${pageContext.request.contextPath}/report/core/${linkReport}/hr.htm?nodeid=${nodeBList.linkid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${nodeBList.day}"/>">${nodeBList.linkid}</a>
				    </display:column>
			    	<display:column  property="bandwidth" titleKey="Bandwidth" sortable="true" headerClass="master-header-1"/>
			    	<display:column  property="vendorTrans" titleKey="Đối tác truyền dẫn" sortable="true" headerClass="master-header-1"/>
			    	
			    </c:when>
			    <c:when test="${level=='hr'}">
			       <display:column property="day" titleKey="Day" format="{0,date,dd/MM/yyyy}" sortable="true" headerClass="master-header-1"/>
			       <display:column property="hour" titleKey="Hour"  sortable="true" headerClass="master-header-1"/>
			       <display:column  property="region" titleKey="Region" sortable="true" headerClass="master-header-1"/>
			       <display:column  property="bscid" titleKey="Bscid" sortable="true" headerClass="master-header-1"/>
			    	
			       <display:column  property="linkid" titleKey="NodeB" sortable="true" headerClass="master-header-1"/>
			       <display:column  property="bandwidth" titleKey="Bandwidth" sortable="true" headerClass="master-header-1"/>
			       <display:column  property="vendorTrans" titleKey="Đối tác truyền dẫn" sortable="true" headerClass="master-header-1"/>
			    	
			    </c:when>
			    
			</c:choose> 
			<display:column class="AVG_DELAY rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="avgDelay"  titleKey="Avg Delay"  sortable="true" />
			<display:column class="MAX_DELAY rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="maxDelay"  titleKey="Max Delay"  sortable="true" />
			<display:column class="PACKET_LOSS rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="packetLoss"  titleKey="Packet Loss(%)"  sortable="true" />
			
			<display:column  property ="transType"  titleKey="Loại truyền dẫn"  sortable="true"  headerClass="master-header-4"/>
			<display:column  property ="transConfigs"  titleKey="Cấu hình truyền dẫn"  sortable="true"  headerClass="master-header-4"/>
			<display:column  property ="delayStandar"  titleKey="Chuẩn delay"  sortable="true"  class="rightColumnMana"  headerClass="master-header-4"/>    
			<display:column  property="lossStandar" titleKey="ĐG chỉ tiêu" sortable="true" headerClass="master-header-4"/>
			<c:if test="${level == 'dy'}">
			  	<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="dataload"  titleKey="Dataload (%)"  sortable="true" headerClass="master-header-4" />
			</c:if> 
			</display:table>
	</div>
 <c:choose> 
 	<c:when test="${level=='hr'}">
        <%@ include file="/WEB-INF/jsp/includes/scriptHourly.jsp" %>
    </c:when>
    <c:when test="${level=='dy'}">
        <%@ include file="/WEB-INF/jsp/includes/scriptDaily.jsp" %>
    </c:when> 
</c:choose>
