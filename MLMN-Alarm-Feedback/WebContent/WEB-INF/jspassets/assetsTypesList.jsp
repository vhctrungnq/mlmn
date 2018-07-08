<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title><fmt:message key="sidebar.admin.assetsTypes"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.assetsTypes"/></content>
<style>

.number {
	text-align: right;
}

</style>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form:form commandName="filter" method="post" action="list.htm">
					<table border="0" cellspacing="1" cellpadding="0" width="100%">
						<tr> 
							<td class="wid10 mwid150"><fmt:message key="assetsTypes.asTypesId"/></td>
							<td class="wid20"><form:input path="asTypesId" cssClass="wid90"/></td>
							
							<td class="wid10 mwid150"><fmt:message key="assetsTypes.asTypesName"/></td>
							<td class="wid20"><form:input path="asTypesName" cssClass="wid90"/></td>
									 
							<td><input class="button" type="submit" class="button" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>				
					</table>
				</form:form>
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
					<display:table name="${assetsTypesList}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true" >
						<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="warehourse" titleKey="assetsTypes.warehourse" sortable="true" sortName="WAREHOURSE"/>
						<display:column property="asTypesId" titleKey="assetsTypes.asTypesId" sortable="true" sortName="AS_TYPES_ID"/>
						<display:column property="asTypesName" titleKey="assetsTypes.asTypesName" sortable="true" sortName="AS_TYPES_NAME"/>
						<display:column property="amount" titleKey="assetsTypes.amount" sortable="true" sortName="AMOUNT" class="number"/>
						<display:column property="amountUsed" titleKey="assetsTypes.amountUsed" sortable="true" sortName="AMOUNT_USED" class="number"/>
						<display:column property="amountNotUsed" titleKey="assetsTypes.amountNotUsed" sortable="true" sortName="AMOUNT_NOT_USED" class="number"/>
						<display:column class="centerColumnMana number" property="ordering" titleKey="assetsTypes.ordering" sortable="true" sortName="ORDERING"/>
						<display:column class="centerColumnMana" property="description" titleKey="assetsTypes.description" sortable="true" sortName="DESCRIPTION"/>
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
		</tr>
</table>

<script type="text/javascript">
	
	function focusIt()
	{
	  var mytext = document.getElementById("asTypesId");
	  mytext.focus();
	}

	onload = focusIt;
</script>
