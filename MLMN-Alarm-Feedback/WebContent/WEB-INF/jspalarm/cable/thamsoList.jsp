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
				    	<td class="wid10 mwid80"><fmt:message key="thamso.code"/>  </td>
				    	<td class="wid20">
				    		<select name="code" id="code" class="wid90">
								<option value=""><fmt:message key="global.All"/></option>
								<c:forEach var="codeP" items="${distinctMaThamSo}">
									<c:choose>
						                <c:when test="${codeP.code==CodeCBB}">
						                    <option value="${codeP.code}" selected="selected">${codeP.code}</option>
						                </c:when>
										<c:otherwise>
											<option value="${codeP.code}">${codeP.code}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select> 
				    	</td>
				    	<td class="wid10 mwid80"><fmt:message key="thamso.name"/> </td>
				    	<td class="wid20">
				    		<input type="text" name="name" id="name" value="${name}" class="wid90"/>
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
		<c:if test="${isManager=='Y' }">
		 <tr>
			<td colspan="2" align="right">
				 <a href="form.htm" title="<fmt:message key="global.form.themmoi"/>"><fmt:message key="global.form.themmoi"/> </a>
		 	</td> 
		 </tr>
		</c:if>
		
		 <tr>
		 	<td colspan="2">
		 		<div style="width: 100%; height: 100%; overflow:auto;">
					<display:table name="${thamSoList}" class="simple2" id="item" requestURI="" pagesize="15" sort="external" defaultsort="1" export="true">
						<display:column class="centerColumnMana" title="STT" > <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="code" titleKey="thamso.code" sortable="true" sortName="CODE"/>
						<display:column property="name" titleKey="thamso.name" sortable="true" sortName="NAME"/>  
					<%-- 	<display:column property="value" titleKey="thamso.value" sortable="true" sortName="VALUE"/>--%>
						<display:column titleKey="thamso.value"  sortable="true" sortName="VALUE"  style="max-width:40px;">
							<c:out value="${item.value}"></c:out>
				   		</display:column>
						<display:column class="centerColumnMana" property="ordering" titleKey="thamso.ordering" sortable="true" sortName="ORDERING"/>
						<display:column property="description" titleKey="thamso.remark" sortable="true" sortName="DESCRIPTION"/>
						<c:if test="${isManager=='Y' }">
							 <display:column media="html" titleKey="global.management" class="centerColumnMana">
								<a href="form.htm?id=${item.id}" title="<fmt:message key="global.form.sua"/>"><fmt:message key="global.form.sua"/></a>&nbsp;
								<a href="delete.htm?id=${item.id}&code=${codeed}&name=${name}" onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>?')" title="<fmt:message key="global.form.xoa"/>"><fmt:message key="global.form.xoa"/></a>&nbsp;
							</display:column>
						</c:if>
						
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv"/>
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls"/>
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml"/>
					</display:table>
					</div> 
		 	</td>
		 </tr>
</table>
