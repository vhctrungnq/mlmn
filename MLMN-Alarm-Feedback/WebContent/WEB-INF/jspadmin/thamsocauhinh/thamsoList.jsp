<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title>${titleSystem}</title>
<content tag="heading">${titleSystem}</content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left">
				<form:form commandName="filter" method="post" action="danh-sach.htm">
				<input type="hidden" name="typeCode" id="typeCode" value="${typeCode}">
				<table >
				    <tr>
				    	<c:choose>
			                <c:when test="${empty typeCode}">
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
			                </c:when>
			                <c:otherwise>
	                	 		<input type="hidden" name="code" id="code" value="${typeCode}">
			                </c:otherwise>
			           </c:choose>  	
				    	
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
		<tr>
			<td colspan="2" align="right">
				 <a href="form.htm?typeCode=${typeCode}" title="<fmt:message key="global.form.themmoi"/>"><fmt:message key="global.form.themmoi"/> </a>
		 		<a href="upload.htm?typeCode=${typeCode}"><fmt:message key="global.button.import"/></a>&nbsp;
		 	</td> 
		 </tr>
		 <tr>
		 	<td colspan="2">
		 		<div style="width: 100%; height: 100%; overflow:auto;">
					<display:table name="${thamSoList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
						<%-- <display:caption media="csv excel pdf">
								DANH SÁCH THAM SỐ CẤU HÌNH,Mã tham số:;${codeed},Tên tham số:;${name}
						</display:caption> --%>
							
						<display:column class="centerColumnMana" title="STT" > <c:out value="${item_rowNum}"/> </display:column>
						<c:if test="${empty typeCode || typeCode==''}">
							<display:column property="code" titleKey="thamso.code" sortable="true" sortName="CODE"/>
						</c:if>
						<display:column property="name" titleKey="thamso.name" sortable="true" sortName="NAME"/>  
					<%-- 	<display:column property="value" titleKey="thamso.value" sortable="true" sortName="VALUE"/>--%>
						<display:column titleKey="thamso.value"  sortable="true" sortName="VALUE"  style="min-width:150px;min-width:450px;">
							<c:out value="${item.value}"></c:out>
				   		</display:column>
						<display:column class="centerColumnMana" property="ordering" titleKey="thamso.ordering" sortable="true" sortName="ORDERING"/>
						<display:column property="dataType" titleKey="thamso.dataType" sortable="true" sortName="DATA_TYPE"/>
						<display:column property="remark" titleKey="thamso.remark" sortable="true" sortName="REMARK"/>
						<display:column media="html" titleKey="global.management" class="centerColumnMana">
							<a href="form.htm?id=${item.id}&typeCode=${typeCode}" title="<fmt:message key="global.form.sua"/>"><fmt:message key="global.form.sua"/></a>&nbsp;
							<a href="delete.htm?id=${item.id}&code=${codeed}&name=${name}&typeCode=${typeCode}" onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>?')" title="<fmt:message key="global.form.xoa"/>"><fmt:message key="global.form.xoa"/></a>&nbsp;
						</display:column>
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv"/>
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls"/>
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml"/>
					</display:table>
					</div> 
		 	</td>
		 </tr>
</table>
