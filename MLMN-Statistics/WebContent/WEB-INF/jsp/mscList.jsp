<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="title.hMsc.list"/></title>
<content tag="heading"><fmt:message key="title.hMsc.list"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
    <tr>
    	<td align="left">
			<form:form commandName="filter" method="post" action="list.htm">
				<table width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td class="wid8 mwid90"><fmt:message key="hMsc.mscid"/></td>
						<td class="wid15">
							<form:input path="mscid" cssClass="wid90"/>
						</td>
					    <td class="wid8 mwid90"><fmt:message key="hMsc.vendor"/></td>
					    <td class="wid15">
					    	<form:select path="vendor" class="wid90">
		           				<option value="">--Tất cả--</option>
		           				<c:forEach var="items" items="${vendorList}">
					              <c:choose>
					                <c:when test="${items.value == vendorCBB}">
					                    <option value="${items.value}" selected="selected">${items.value}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${items.value}">${items.value}</option>
					                </c:otherwise>
					              </c:choose>
							    </c:forEach>
		           			</form:select>
						</td>
					    <td class="wid8 mwid80"><fmt:message key="hMsc.location"/></td>
						<td class="wid15"><form:input path="location" cssClass="wid90" /></td>
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
				<display:table name="${mscList}" id="item" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1">
				    <display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
				    <display:column property="mscid" titleKey="hMsc.mscid"/>        
				    <display:column property="vendor" titleKey="hMsc.vendor"/>
				    <display:column property="location" titleKey="hMsc.location"/>
				    <display:column property="locationName" titleKey="hMsc.locationName"/>
				    <display:column class="rightColumnMana" property="msuCapacity" titleKey="hMsc.msuCapacity" />
				    <display:column property="longitude" titleKey="hMsc.longitude" />
				    <display:column property="latitude" titleKey="hMsc.latitude" />
				    <display:column property="dept" titleKey="hMsc.dept" />
				    <display:column property="team" titleKey="hMsc.team" />
				    <display:column property="subTeam" titleKey="hMsc.subTeam" />
				    <display:column property="neGroup" titleKey="hMsc.neGroup" />
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
		    		<display:setProperty name="export.xml.filename" value="MscList.xml"/>
		    		<display:setProperty name="export.csv.filename" value="MscList.csv"/>
			    	<display:setProperty name="export.excel.filename" value="MscList.xls"/>
				</display:table>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
function focusIt()
{
  var mytext = document.getElementById("mscid");
  mytext.focus();
}
onload = focusIt;
</script>