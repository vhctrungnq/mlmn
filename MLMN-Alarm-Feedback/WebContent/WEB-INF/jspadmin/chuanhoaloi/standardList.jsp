<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title>${titleSystem}</title>
<content tag="heading">${titleSystem}</content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left">
				<form:form commandName="filter" method="post" action="danh-sach.htm">
				<table >
				    <tr>
				   		<td class="wid10 mwid80"><fmt:message key="chuanhoaloi.vendor"/>  </td>
						<td class="wid20">
		    			<select name="vendor" id="code" class="wid90">
								<option value=""><fmt:message key="global.All"/></option>
								<c:forEach var="vendorP" items="${vendorList}">
									<c:choose>
						                <c:when test="${vendorP.name==vendor}">
						                    <option value="${vendorP.name}" selected="selected">${codeP.value}</option>
						                </c:when>
										<c:otherwise>
											<option value="${vendorP.name}">${codeP.value}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select> 
						</td>
			  
				    	<td class="wid10 mwid80"><fmt:message key="chuanhoaloi.network"/> </td>
				    	<td class="wid20">
				    		<select name="network" id="network" class="wid90">
								<option value=""><fmt:message key="global.All"/></option>
								<c:forEach var="item" items="${networkList}">
									<c:choose>
						                <c:when test="${item.name==vendor}">
						                    <option value="${item.name}" selected="selected">${item.value}</option>
						                </c:when>
										<c:otherwise>
											<option value="${item.name}">${item.value}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select> 
				    	</td>
				    	
				    	<td class="wid10 mwid80"><fmt:message key="chuanhoaloi.alarmName"/> </td>
				    	<td class="wid20">
				    		<input type="text" name="alarmName" id="alarmName" value="${alarmName}" class="wid90"/>
				    	</td>
				    	<td>
				    		<input type="submit" class="button" value="<fmt:message key="global.form.timkiem"/>" />
				    	</td>
				    </tr>
			    </table>	
				</form:form>
			</td>
				
				<td>
				</td> 
		</tr>
		<tr>
			<td colspan="2" align="right">
				 <a href="form.htm" title="<fmt:message key="global.form.themmoi"/>"><fmt:message key="global.form.themmoi"/> </a>
		 		<a href="upload.htm"><fmt:message key="global.button.import"/></a>&nbsp;
		 	</td> 
		 </tr>
		 <tr>
		 	<td colspan="2">
		 		<div style="width: 100%; height: 100%; overflow:auto;">
					<display:table name="${standardList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
						<display:column class="centerColumnMana" title="STT" > <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="vendor" titleKey="chuanhoaloi.vendor" sortable="true" sortName="VENDOR"/>  
						<display:column property="network" titleKey="chuanhoaloi.network" sortable="true" sortName="NETWORK"/>  
						<display:column property="alarmName" titleKey="chuanhoaloi.alarmName" sortable="true" sortName="ALARM_NAME"/>  
						<display:column property="alarmNameMapping" titleKey="chuanhoaloi.alarmNameMapping" sortable="true" sortName="ALARM_NAME_MAPPING"/>  
						<display:column property="description" titleKey="chuanhoaloi.description" sortable="true" sortName="DESCRIPTION"/>  
						<display:column class="centerColumnMana" property="ordering" titleKey="thamso.ordering" sortable="true" sortName="ORDERING"/>
						<display:column media="html" titleKey="global.management" class="centerColumnMana">
							<a href="form.htm?id=${item.id}" title="<fmt:message key="global.form.sua"/>"><fmt:message key="global.form.sua"/></a>&nbsp;
							<a href="delete.htm?id=${item.id}" onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>?')" title="<fmt:message key="global.form.xoa"/>"><fmt:message key="global.form.xoa"/></a>&nbsp;
						</display:column>
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv"/>
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls"/>
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml"/>
					</display:table>
					</div> 
		 	</td>
		 </tr>
</table>
