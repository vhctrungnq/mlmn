<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="odfLienTang.titleList"/></title>
<content tag="heading"><fmt:message key="odfLienTang.titleList"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form method="post" action="list.htm">
					<table border="0" cellspacing="1" cellpadding="0" width="100%" >
						<tr> 
							<td class="wid8 mwid70"><fmt:message key="odfLienTang.tenSoDo"/></td>
							<td class="wid20"><input id="schemaName" name="schemaName" value="${schemaName}" class="wid90"/></td>

							<td><input class="button" type="submit" class="button" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>				
					</table>
				</form>
				</td>

		</tr>
		<c:if test="${isManager=='Y' }">
			 <tr>
				<td></td>
				<td class="wid10 mwid70" align="right">
					<a href="form.htm"><fmt:message key="global.form.themmoi"/></a>&nbsp;
				</td> 
			</tr>
		</c:if>
		
		<tr>
			<td colspan="2">
				<div style="width:100%;overflow:auto; ">
					<display:table name="${odfTypesList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true" >
						<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column style="max-width:150px;word-wrap: break-word;" property="schemaName" titleKey="odfLienTang.tenSoDo" sortable="true" sortName="SCHEMA_NAME"/>
						<display:column style="max-width:80px;word-wrap: break-word;" property="locationPort" titleKey="odfLienTang.viTri" sortable="true" sortName="LOCATION_PORT"/>
						<display:column class="centerColumnMana" property="ordering" titleKey="odfLienTang.ordering" sortable="true" sortName="ORDERING"/>
						<display:column style="max-width:260px;word-wrap: break-word;" property="description" titleKey="odfLienTang.ghiChu" sortable="true" sortName="DESCRIPTION"/>
						
		    			<c:if test="${isManager=='Y' }">
							<display:column titleKey="global.management" media="html" class="centerColumnMana">
								<a href="form.htm?id=${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
								<a href="delete.htm?id=${item.id}"
										onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="global.form.xoa"/></a>&nbsp;
			    			</display:column>		 
						</c:if>
						<display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header" value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
			</td>
		</tr>
</table>

<script type="text/javascript">
	
	function focusIt()
	{
	  var mytext = document.getElementById("schemaName");
	  mytext.focus();
	}

	onload = focusIt;
</script>