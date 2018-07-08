<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="title.hsgsn.list"/></title>
<content tag="heading"><fmt:message key="title.hsgsn.list"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
    <tr>
    	<td align="left">
			<form:form commandName="filter" method="post" action="list.htm">
				<table width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td class="wid8 mwid90"><fmt:message key="hsgsn.region"/></td>
						<td class="wid15">
							<%-- <form:select path="region" cssClass="wid90">
								<option value="">--Tất cả--</option>
					     		<option value="TT2" >TT2</option>
					     		<option value="TT6" >TT6</option>
					     	</form:select> --%>
					     	<form:select path="region" cssClass="wid90">
					     		<form:option value="" label="--Tất cả--"/>
								<form:option value="TT2" label="TT2"/>
								<form:option value="TT6" label="TT6"/>
			       			</form:select>
						</td>
					    <td class="wid8 mwid90"><fmt:message key="hsgsn.sgsnid"/></td>
					    <td class="wid15">
					    	<form:select path="sgsnid" class="wid90">
		           				<option value="">--Tất cả--</option>
		           				<c:forEach var="items" items="${sgsnNameList}">
					              <c:choose>
					                <c:when test="${items.sgsnid == sgsnid}">
					                    <option value="${items.sgsnid}" selected="selected">${items.sgsnid}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${items.sgsnid}">${items.sgsnid}</option>
					                </c:otherwise>
					              </c:choose>
							    </c:forEach>
		           			</form:select>
						</td>
					    <td class="wid8 mwid80"><fmt:message key="hsgsn.sgsnName"/></td>
						<td class="wid15">
							<form:select path="sgsnName" class="wid90">
			           				<option value="">--Tất cả--</option>
			           				<c:forEach var="items" items="${sgsnNameList}">
						              <c:choose>
						                <c:when test="${items.sgsnName == sgsnName}">
						                    <option value="${items.sgsnName}" selected="selected">${items.sgsnName}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.sgsnName}">${items.sgsnName}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
			           			</form:select>
						</td>
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
			<div style="overflow: auto;">
				<display:table name="${sgsnList}" id="item" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1">
				    <display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
				    <display:column property="region" titleKey="hsgsn.region"/>        
				    <display:column property="sgsnid" titleKey="hsgsn.sgsnid"/>
				    <display:column property="sgsnName" titleKey="hsgsn.sgsnName"/>
				    <display:column class="rightColumnMana" property="gbAttachCapacityLicense" titleKey="hsgsn.gbAttachCapacityLicense"/>
				    <display:column class="rightColumnMana" property="totalAttachCapacityLicense" titleKey="hsgsn.totalAttachCapacityLicense" />
				    <display:column class="rightColumnMana" property="thoughputCapacityLicense" titleKey="hsgsn.thoughputCapacityLicense" />
				    <display:column class="rightColumnMana" property="pdpContextCapacityLicense" titleKey="hsgsn.pdpContextCapacityLicense" />
				    <display:column property="locationName" titleKey="hsgsn.locationName" />
				    <display:column property="dept" titleKey="hsgsn.dept" />
				    <display:column property="team" titleKey="hsgsn.team" />
				    <display:column property="subTeam" titleKey="hsgsn.subTeam" />
				    <display:column property="neGroup" titleKey="hsgsn.neGroup" />
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
		    		<display:setProperty name="export.xml.filename" value="SgsnList.xml"/>
		    		<display:setProperty name="export.csv.filename" value="SgsnList.csv"/>
			    	<display:setProperty name="export.excel.filename" value="SgsnList.xls"/>
				</display:table>
			</div>
		</td>
	</tr>
</table>
