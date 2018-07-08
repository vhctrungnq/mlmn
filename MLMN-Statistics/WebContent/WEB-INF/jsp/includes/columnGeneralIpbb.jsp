		    <c:choose> 
			    <c:when test="${level=='dy'}">
			    
			    <display:column property="day" titleKey="Day" format="{0,date,dd/MM/yyyy}" sortable="true" headerClass="master-header-1"/>
			    <display:column  property="treeName" titleKey="Tree Name" sortable="true" headerClass="master-header-1"/>
			    <display:column property="routeName" titleKey="RouterName" sortable="true" headerClass="master-header-1"/>
			  
				<display:column property="interfaceName" titleKey="InterfaceName" sortable="true" headerClass="hide" class="hide"/>		
				  <display:column titleKey="InterfaceName" media="html" sortable="true" headerClass="master-header-1">
			   	 	<a href="${pageContext.request.contextPath}/report/core/${linkReport}/hr.htm?routeid=${dyIpbbList.routeName}&interfaceid=${dyIpbbList.interfaceName}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyIpbbList.day}"/>">${dyIpbbList.interfaceName}</a>
			    </display:column>	    
			    </c:when>
			    <c:when test="${level=='hr'}">
			       <display:column property="day" titleKey="Day" format="{0,date,dd/MM/yyyy}" sortable="true" headerClass="master-header-1"/>
			       <display:column property="hour" titleKey="Hour"  sortable="true" headerClass="master-header-1"/>
					<display:column  property="treeName" titleKey="Tree Name" sortable="true" headerClass="master-header-1"/>
			    	<display:column property="routeName" titleKey="RouterName" sortable="true" headerClass="master-header-1"/>
			    	<display:column property="interfaceName" titleKey="InterfaceName" sortable="true" headerClass="master-header-1"/>
			    			    
			    </c:when>
			    <c:when test="${level=='mn'}">
			        <display:column property="month" titleKey="Month"  sortable="true" headerClass="master-header-1"/>
	    			<display:column property="year" titleKey="Year"  sortable="true" headerClass="master-header-1"/>
					<display:column  property="treeName" titleKey="Tree Name" sortable="true" headerClass="master-header-1"/>
			    	<display:column property="routeName" titleKey="RouterName" sortable="true" headerClass="master-header-1"/>	
			    	<display:column property="interfaceName" titleKey="InterfaceName" sortable="true" headerClass="master-header-1"/>		    
			    </c:when>
			    <c:otherwise>
			        <display:column property="week" titleKey="Week"  sortable="true" headerClass="master-header-1"/>
	    			<display:column property="year" titleKey="Year"  sortable="true" headerClass="master-header-1"/>
					<display:column  property="treeName" titleKey="Tree Name" sortable="true" headerClass="master-header-1"/>
			    	<display:column property="routeName" titleKey="RouterName" sortable="true" headerClass="master-header-1"/>	
			    	<display:column property="interfaceName" titleKey="InterfaceName" sortable="true" headerClass="master-header-1"/>		    
			    </c:otherwise>
			</c:choose>