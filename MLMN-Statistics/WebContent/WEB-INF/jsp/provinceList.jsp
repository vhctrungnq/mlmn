<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="title.hProvincesCode.list"/></title>
<body class="section-4"/>
<content tag="heading"><fmt:message key="title.hProvincesCode.list"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
    <tr>
    	<td align="left">
			<form:form commandName="filter" method="post" action="list.htm">
				<table width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td class="wid8 mwid70"><fmt:message key="hProvincesCode.dept"/></td>
						<td class="wid15">
							<select name="dept" class="wid90" id="dept">
		           				<option value="">--Tất cả--</option>
		           				<c:forEach var="items" items="${deptList}">
					              <c:choose>
					                <c:when test="${items.deptCode == deptCBB}">
					                    <option value="${items.deptCode}" selected="selected">${items.deptCode}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${items.deptCode}">${items.deptCode}</option>
					                </c:otherwise>
					              </c:choose>
							    </c:forEach>
		           			</select>
						</td>
					    <td class="wid8 mwid90"><fmt:message key="hProvincesCode.code"/></td>
					    <td class="wid15"><form:input path="code" cssClass="wid90" /></td>
					    <td class="wid8 mwid80"><fmt:message key="hProvincesCode.branch"/></td>
						<td class="wid15"><form:input path="branch" cssClass="wid90" /></td>
						
			        </tr>
			        <tr>
			        	<td><fmt:message key="hProvincesCode.location"/></td>
						<td><form:input path="location" cssClass="wid90" /></td>
			        	<td><fmt:message key="hProvincesCode.province"/></td>
					    <td><form:input path="province" cssClass="wid90" /></td>
					    <td><fmt:message key="hProvincesCode.district"/></td>
					    <td><form:input path="district" cssClass="wid90" /></td>
					    <td><input class="button" type="submit" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
			        </tr>		
				</table>
			</form:form>
        </td>
        <td></td>
    </tr>
    <c:if test="${checkRoleManager==true}">	
    <tr>
    	<td>
				</td>
		<td class="wid10 mwid140" align="right">
			<a href="form.htm"><fmt:message key="global.form.themmoi"/></a>&nbsp;
			<a href="upload.htm"><fmt:message key="global.button.import"/></a>&nbsp;
		</td> 
	</tr>
	</c:if>
    <tr>
    	<td colspan="2">			
			<div style="width:100%;overflow:auto; ">
			    <display:table name="${provinceList}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" export="true">
			    <display:column class="centerColumnMana" titleKey="global.list.STT">
					<c:out value="${item_rowNum}" />
				</display:column>
				<display:column property="region" titleKey="hProvincesCode.region" sortable="true" sortName="REGION"/>
				<display:column property="location" titleKey="hProvincesCode.location" sortable="true" sortName="LOCATION"/>
				<display:column property ="branch" titleKey="hProvincesCode.branch" sortable="true" sortName="BRANCH"/>
			    <display:column property="code" titleKey="hProvincesCode.code" sortable="true" sortName="CODE"/>  
			    <display:column property ="province" titleKey="hProvincesCode.province" sortable="true" sortName="PROVINCE"/> 
			    <display:column property ="district" titleKey="hProvincesCode.district" sortable="true" sortName="DISTRICT"/>    
			    <display:column property ="dept" titleKey="hProvincesCode.dept" sortable="true" sortName="DEPT"/>  
			    <display:column property ="team" titleKey="hProvincesCode.team" sortable="true" sortName="TEAM"/>
			    <display:column property ="groups" titleKey="hProvincesCode.groups" sortable="true" sortName="GROUPS"/>
			   	<c:if test="${checkRoleManager==true}">	
			   	<display:column class="centerColumnMana" titleKey="global.management" media="html">
			    	<a href="form.htm?id=${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
			    	<a href="delete.htm?id=${item.id}"
			    	   onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')" ><fmt:message key="global.form.xoa"/></a>&nbsp;
			    </display:column>
			    </c:if>
			    <display:setProperty name="export.csv.include_header" value="true"/>
		    	<display:setProperty name="export.excel.include_header" value="true"/>
		    	<display:setProperty name="export.xml.include_header" value="true"/>
	    		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml"/>
	    		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv"/>
		    	<display:setProperty name="export.excel.filename" value="${exportFileName}.xls"/>
			</display:table>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
function focusIt()
{
  var mytext = document.getElementById("code");
  mytext.focus();
}
onload = focusIt;
</script>
