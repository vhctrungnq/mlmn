<display:column property="region" title="TT" sortable="true" class="TT" headerClass="master-header-1"/>
		    <c:choose> 
			    <c:when test="${level=='dy'}">
			    
			    <display:column property="day" title="Day" format="{0,date,dd/MM/yyyy}" sortable="true" headerClass="master-header-1"/>
			    <display:column property="sgsnName" titleKey="Sgsnid" headerClass="hide" class="hide"/>
			    <display:column title="Sgsnid" media="html" sortable="true" headerClass="master-header-1">
			   	 	<a href="${pageContext.request.contextPath}/report/core/${linkReport}/hr.htm?sgsnid=${dySgsnList.sgsnName}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dySgsnList.day}"/>">${dySgsnList.sgsnName}</a>
			    </display:column>
			    </c:when>
			    <c:when test="${level=='hr'}">
			       <display:column property="day" title="Day" format="{0,date,dd/MM/yyyy}" sortable="true" headerClass="master-header-1"/>
			       <display:column property="hour" title="Hour"  sortable="true" headerClass="master-header-1"/>
			       <display:column  property="sgsnName" title="Sgsnid" sortable="true" headerClass="master-header-1"/>
			    </c:when>
			    <c:when test="${level=='mn'}">
			        <display:column property="month" title="Month"  sortable="true" headerClass="master-header-1"/>
	    			<display:column property="year" title="Year"  sortable="true" headerClass="master-header-1"/>
	    			<display:column  property="sgsnName" title="Sgsnid" sortable="true" headerClass="master-header-1"/>
			    </c:when>
			    <c:otherwise>
			        <display:column property="week" title="Week"  sortable="true" headerClass="master-header-1"/>
	    			<display:column property="year" title="Year"  sortable="true" headerClass="master-header-1"/>
	    			<display:column  property="sgsnName" title="Sgsnid" sortable="true" headerClass="master-header-1"/>
			    </c:otherwise>
			</c:choose>