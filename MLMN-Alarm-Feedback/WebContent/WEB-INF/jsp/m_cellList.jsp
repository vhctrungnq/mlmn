
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<title><spring:message code="header.title.m_cellList"/></title>
<body class="section-4" />
	<form></form>
	 

<content tag="heading"><spring:message code="header.title.m_cellList"/></content>  	
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post"	action="list.htm">
					<table   border="0" cellspacing="1" cellpadding="0">
						<tr> 
							<td><spring:message code="m_cellForm.lable.cellid"/>&nbsp;</td>
							<td><form:input path="cellid" size="10" />&nbsp;</td>
							
							<td>SITEID&nbsp;</td>
							<td><form:input path="siteid" size="15"/>&nbsp;</td>
							
							<td><spring:message code="m_cellForm.lable.vendor"/>&nbsp;</td>
							<td><form:input path="vendor" size="15"/>&nbsp;</td> 
						
							<td>MC_TYPE&nbsp;</td>
							<td><form:input path="mcType" size="15"/>&nbsp;</td>
							
							<td>BSCID&nbsp;</td>
							<td><form:input path="bscid" size="15"/>&nbsp;</td>
							
							<td>DISTRICT&nbsp;</td>
							<td><form:input path="district" size="15"/>&nbsp;</td>
							
							<td><input type="submit" class="button" name="filter"
								value="<spring:message code="button.search"/>"/></td>
						</tr>
					</table>
				</form:form>
				</td> 
		</tr> 
		<td align="right">
            <a href="upload.htm">Upload file</a>&nbsp;
            <a href="form.htm">Thêm mới</a>&nbsp;
        </td>
		<tr> 
			<td>
				<div style="width:100%;overflow:auto; ">
					<display:table name="${m_cellList}" class="simple2" id="m_cell"
						requestURI="" pagesize="20" export="true" partialList="true"  sort="list" size="${totalSize}">
					  
						<display:column property="cellid" title="CELLID" />
						<display:column property="cellname" title="CELLNAME" />
						<display:column property="siteid" title="SITEID" />
						<display:column property="sitename" title="SITENAME" />
						<display:column property="vendor" title="VENDOR" />
						<display:column property="mcType" title="MC_TYPE" />
						<display:column property="cgi" title="CGI" />						
						<display:column property="avgMonthTraff" title="AVG_MONTH_TRAFF" />
						<display:column property="lastActive" format="{0,date,dd/MM/yyyy}" title="LAST_ACTIVE" />
						<display:column property="launchDate" format="{0,date,dd/MM/yyyy}" title="LAUNCH_DATE" />
						<display:column property="province" title="PROVINCE" />						
						<display:column property="region" title="REGION" />
						<display:column property="description" title="DESCRIPTION" />
						<display:column property="bscid" title="BSCID" />
						<display:column property="district" title="DISTRICT" />
			
						<display:column title="Quản lý" media="html" class="centerColumnMana">
							<a href="form.htm?cellid=${m_cell.cellid}"><spring:message code="button.edit"/></a>&nbsp;
		    				<a href="delete.htm?cellid=${m_cell.cellid}"
									onclick="return confirm('<spring:message code="messsage.confirm.delete"/>')"><spring:message code="button.delete"/></a>&nbsp;
		    			</display:column>
						<display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header"
							value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />
						<display:setProperty name="export.xml.filename"
							value="m_cellList.xml" />
						<display:setProperty name="export.csv.filename"
							value="m_cellList.csv" />
					<display:setProperty name="export.excel.filename"
							value="m_cellList.xls" />


					<display:footer> <tr> <td>Total Bill:</td> <td><c:out value="${totals.id}" /></td> <tr> </display:footer> 
				</display:table>
			</div>
		</td>
	 
	</tr>
</table>
<script type="text/javascript">
	$(function() {
		$('ul#subnav-3').find(":contains('M_CELL')").css('color', '#f00');
	});
	
	function focusIt()
	{
	  var mytext = document.getElementById("cellid");
	  mytext.focus();
	}

	onload = focusIt;
</script>