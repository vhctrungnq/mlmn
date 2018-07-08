<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="title.qualityNetwork.list"/></title>
<content tag="heading"><fmt:message key="title.qualityNetwork.list"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
    <tr>
    	<td align="left">
			<form:form commandName="filter" method="post" action="list.htm">
				<table width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td class="wid8 mwid90"><fmt:message key="qualityNetwork.groupName"/></td>
						<td class="wid15">
							<form:input path="groupName" cssClass="wid90"/>
						</td>
					    <td class="wid8 mwid90"><fmt:message key="qualityNetwork.qualityCode"/></td>
					    <td class="wid15">
					    	<form:input path="qualityCode" cssClass="wid90"/>
						</td>
					    <td class="wid8 mwid90"><fmt:message key="qualityNetwork.qualityName"/></td>
						<td class="wid15"><form:input path="qualityName" cssClass="wid90" /></td>
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
			    <display:table name="${qualityNetworkList}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" export="true">
			    <display:column class="centerColumnMana" titleKey="global.list.STT">
					<c:out value="${item_rowNum}" />
				</display:column>
				<display:column property="groupName" titleKey="qualityNetwork.groupName" sortable="true" sortName="GROUP_NAME"/>
				<display:column property="qualityCode" titleKey="qualityNetwork.qualityCode" sortable="true" sortName="QUALITY_CODE"/>
				<display:column property ="qualityName" titleKey="qualityNetwork.qualityName" sortable="true" sortName="QUALITY_NAME"/>
			    <display:column property="qualityCondition" titleKey="qualityNetwork.qualityCondition" sortable="true" sortName="QUALITY_CONDITION"/>  
			    <display:column property ="qualityValue" titleKey="qualityNetwork.qualityValue" sortable="true" sortName="QUALITY_VALUE"/> 
			    <display:column property ="qualityUnit" titleKey="qualityNetwork.qualityUnit" sortable="true" sortName="QUALITY_UNIT"/>    
			    <display:column class="rightColumnMana" property ="ordering" titleKey="qualityNetwork.ordering" sortable="true" sortName="ORDERING"/>  
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
  var mytext = document.getElementById("groupName");
  mytext.focus();
}
onload = focusIt;
</script>