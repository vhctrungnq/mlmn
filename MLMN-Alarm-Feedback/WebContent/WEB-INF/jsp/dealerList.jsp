<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<title><spring:message code="header.title.dealerList"/></title>
<body class="section-4" />
	<form></form>
	 

<content tag="heading"><spring:message code="header.title.dealerList"/></content> 

<table border="0" width="100%" cellspacing="0" cellpadding="0"
		class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post"  	action="list.htm">
					<table   border="0" cellspacing="1" cellpadding="0">
						<tr> 
							<td><input type="hidden" id="id"> code</td>
							<td><form:input path="code" />&nbsp;</td>
							<td><spring:message code="dealerForm.lable.abbreviation"/> </td>
							<td><form:input path="abbreviation" />&nbsp;</td> 
						
							<td>vn_name</td>
							<td><form:input path="vn_name" />&nbsp;</td>
							<td>en_name</td>
							<td><form:input path="en_name" />&nbsp;</td>
							<td><input type="submit" class="button" name="filter"
								value="<spring:message code="button.search"/>" /></td>
						</tr>
					</table>
				</form:form>
				</td> 
		</tr> 
		<td align="right">
            <a href="upload.htm">Upload file</a>&nbsp;
            <a href="form.htm?id=-1">Thêm mới</a>&nbsp;
        </td>
		<tr> 
			<td>
				<div style="width:100%;overflow:auto; ">
					<display:table name="${dealerList}" class="simple2" id="dealer"
						requestURI="" pagesize="20" export="true" partialList="true"  sort="list" defaultsort="2"
						size="${totalSize}">
					  
						<display:column property="id" title="id" />
						<display:column property="code" title="code" sortProperty="code"  sortable ="true"/>
						<display:column property="abbreviation" title="abbreviation" />
						<display:column property="vn_name" title="vn_name" />
						<display:column property="en_name" title="en_name" /> 
						
						<display:column title="Quản lý" media="html">
							<a href="form.htm?id=${dealer.id}"><spring:message code="button.edit"/></a>&nbsp;
		    				<a href="delete.htm?id=${dealer.id}"
									onclick="return confirm('<spring:message code="messsage.confirm.delete"/>')"><spring:message code="button.delete"/></a>&nbsp;
		    			</display:column>
						<display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header"
							value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />
						<display:setProperty name="export.xml.filename"
							value="DealerList.xml" />
						<display:setProperty name="export.csv.filename"
							value="DealerList.csv" />
					<display:setProperty name="export.excel.filename"
							value="DealerList.xls" />


					<display:footer> <tr> <td>Total Bill:</td> <td><c:out value="${totals.id}" /></td> <tr> </display:footer> 
				</display:table>
			</div>
		</td>
	 
	</tr>
</table>
<script type="text/javascript">
	$(function() {
		$('ul#subnav-3').find(":contains('DEALER')").css('color', '#f00');
	});
</script>