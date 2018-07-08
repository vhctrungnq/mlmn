<display:column property="region" titleKey="TT" sortable="true" class="TT" headerClass="master-header-1"/>
		    <c:choose> 
			    <c:when test="${level=='dy'}">
			    
			    <display:column property="day" titleKey="Day" format="{0,date,dd/MM/yyyy}" sortable="true" headerClass="master-header-1"/>
			    <display:column property="mscid" titleKey="Mscid" headerClass="hide" class="hide"/>
			    <display:column titleKey="Mscid" media="html" sortable="true" headerClass="master-header-1">
			   	 	<a href="${pageContext.request.contextPath}/report/core/${linkReport}/hr.htm?mscid=${dyMscList.mscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyMscList.day}"/>">${dyMscList.mscid}</a>
			    </display:column>
			    </c:when>
			    <c:when test="${level=='hr'}">
			       <display:column property="day" titleKey="Day" format="{0,date,dd/MM/yyyy}" sortable="true" headerClass="master-header-1"/>
			       <display:column property="hour" titleKey="Hour"  sortable="true" headerClass="master-header-1"/>
			       <display:column  property="mscid" titleKey="Mscid" sortable="true" headerClass="master-header-1"/>
			    </c:when>
			    <c:when test="${level=='mn'}">
			        <display:column property="month" titleKey="Month"  sortable="true" headerClass="master-header-1"/>
	    			<display:column property="year" titleKey="Year"  sortable="true" headerClass="master-header-1"/>
	    			<display:column  property="mscid" titleKey="Mscid" sortable="true" headerClass="master-header-1"/>
			    </c:when>
			    <c:otherwise>
			        <display:column property="week" titleKey="Week"  sortable="true" headerClass="master-header-1"/>
	    			<display:column property="year" titleKey="Year"  sortable="true" headerClass="master-header-1"/>
	    			<display:column  property="mscid" titleKey="Mscid" sortable="true" headerClass="master-header-1"/>
			    </c:otherwise>
			</c:choose>