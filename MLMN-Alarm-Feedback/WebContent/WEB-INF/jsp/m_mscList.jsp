
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<title><spring:message code="header.title.m_mscList"/></title>
<body class="section-4" />
	<form></form>
	 

<content tag="heading"><spring:message code="header.title.m_mscList"/></content> 	
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post"	action="list.htm">
					<table   border="0" cellspacing="1" cellpadding="0">
						<tr> 
							<td><spring:message code="m_mscForm.lable.mscid"/>&nbsp;</td>
							<td><form:input path="mscid" size="10" />&nbsp;</td>
							
							<td><spring:message code="m_mscForm.lable.vender"/>&nbsp;</td>
							<td><form:input path="vender" size="15"/>&nbsp;</td> 
						
							<td>MSU_CAPACITY&nbsp;</td>
							<td><form:input path="msuCapacity" size="15"/>&nbsp;</td>
							
							<td>MM_TYPE&nbsp;</td>
							<td><form:input path="mmType" size="15"/>&nbsp;</td>
							
							<td>REGION&nbsp;</td>
							<td><form:input path="region" size="15"/>&nbsp;</td>
							
							<td><input type="submit" class="button" name="filter"
								value="<spring:message code="button.search"/>" /></td>
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
					<display:table name="${m_mscList}" class="simple2" id="m_msc"
						requestURI="" pagesize="20" export="true" partialList="true"  sort="list" size="${totalSize}">
					  
						<display:column property="mscid" titleKey="MSC" />
						<display:column property="vender" title="VENDER" />
						<display:column property="msuCapacity" title="MSU_CAPACITY" />
						<display:column property="mmType" title="MM_TYPE" />
						<display:column property="ordering" title="ORDERING" />
						<display:column property="createDate" format="{0,date,dd/MM/yyyy}" title="CREATE_DATE" />
						<display:column property="modifyDate" format="{0,date,dd/MM/yyyy}" title="MODIFY_DATE" />
						<display:column property="region" title="REGION" /> 
						
						<display:column title="Quản lý" media="html" class="centerColumnMana">
							<a href="form.htm?mscid=${m_msc.mscid}"><spring:message code="button.edit"/></a>&nbsp;
		    				<a href="delete.htm?mscid=${m_msc.mscid}"
									onclick="return confirm('<spring:message code="messsage.confirm.delete"/>')"><spring:message code="button.delete"/></a>&nbsp;
		    			</display:column>
						<display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header"
							value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />
						<display:setProperty name="export.xml.filename"
							value="m_mscList.xml" />
						<display:setProperty name="export.csv.filename"
							value="m_mscList.csv" />
					<display:setProperty name="export.excel.filename"
							value="m_mscList.xls" />


					<display:footer> <tr> <td>Total Bill:</td> <td><c:out value="${totals.id}" /></td> <tr> </display:footer> 
				</display:table>
			</div>
		</td>
	 
	</tr>
</table>
<script type="text/javascript">
	$(function() {
		$('ul#subnav-3').find(":contains('M_MSC')").css('color', '#f00');
	});
	
	function focusIt()
	{
	  var mytext = document.getElementById("mscid");
	  mytext.focus();
	}

	onload = focusIt;
</script>