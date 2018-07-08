<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title>${titleSystem}</title>
<content tag="heading">${titleSystem}</content>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post" action="danh-sach.htm">
					<table border="0" cellspacing="1" cellpadding="0">
						<tr>
				        	<td class="wid10 mwid90"><fmt:message key="qLPhongBan.heThong"/></td>
							<td class="wid20">
			           			<select name="system" class="wid90">
			           				<option value="">--Tất cả--</option>
			           				<c:forEach var="items" items="${systemList}">
						              <c:choose>
						                <c:when test="${items.value == systemCBB}">
						                    <option value="${items.value}" selected="selected">${items.name}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.value}">${items.name}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
			           			</select>
           					</td>
							<td class="wid10 mwid70"><fmt:message key="qLNhomNguoiDung.tenNhom"/></td>
							<td class="wid20"><form:input path="groupName" cssClass="wid90" /></td>
							
							<td><input class="button" type="submit" class="button" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>				
					</table>
				</form:form>
				</td>
				
				<td>
				</td> 
		</tr>
		<tr>
			<td></td>
			<td class="wid10 mwid70" align="right">
				<a href="form.htm"><fmt:message key="global.form.themmoi"/></a>&nbsp;
				</td> 
		</tr>
		
		<tr>
			<td colspan="2">
				
				<div style="width:100%;overflow:auto; ">
					<display:table name="${sysGroupUserList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true" >
						<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="groupName" titleKey="qLNhomNguoiDung.tenNhom" sortable="true" sortName="GROUP_NAME"/>
						<display:column property="name" titleKey="qLPhongBan.heThong" sortable="true" sortName="NAME"/>				
						<display:column class="centerColumnMana" property="ordering" titleKey="qLNhomNguoiDung.sapXep" sortable="true" sortName="ORDERING"/>
						<display:column property="description" titleKey="qLNhomNguoiDung.dienGiai" sortable="true" sortName="DESCRIPTION"/>
						
						<display:column titleKey="global.management" media="html" class="centerColumnMana">
							<a href="form.htm?id=${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
		    				<a href="delete.htm?id=${item.id}"
									onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="global.form.xoa"/></a>&nbsp;
		    			</display:column>
		    			
						<display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header" value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
				
			</td>
			<td></td>
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
