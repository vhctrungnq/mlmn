
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<title><spring:message code="header.title.m_bscList"/></title>
<body class="section-4" />
	<form></form>
	 

<content tag="heading"><spring:message code="header.title.m_bscList"/></content> 
	
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post"	action="list.htm">
					<table   border="0" cellspacing="1" cellpadding="0">
						<tr> 
							<td><spring:message code="m_bscForm.lable.bscid"/>&nbsp;</td>
							<td><form:input path="bscid" size="10" />&nbsp;</td>
							
							<td><spring:message code="m_bscForm.lable.vendor"/>&nbsp;</td>
							<td><form:input path="vendor" size="15"/>&nbsp;</td> 
						
							<td>LAUNCH_DATE&nbsp;</td>
							<td><input value="${launchDate}" name="launchDate" id="launchDate" size="10" maxlength="10"><img alt="calendar" title="Click to choose the launch date" id="chooseLaunchDate" style="cursor: pointer;" src="/VMSC2-Alarm/images/calendar.png"/>&nbsp;</td>
							
							<td>BSC_TYPE&nbsp;</td>
							<td><form:input path="bscType" size="15"/>&nbsp;</td>
							
							<td>MSCID&nbsp;</td>
							<td><form:input path="mscid" size="15"/>&nbsp;</td>
							
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
					<display:table name="${m_bscList}" class="simple2" id="m_bsc"
						requestURI="" pagesize="20" export="true" partialList="true" sort="list" size="${totalSize}">
					  
						<display:column property="bscid" title="BSCID" />
						<display:column property="vendor" title="VENDOR" />
						<display:column property="trau" title="TRAU" />
						<display:column property="trx" title="TRX" />						
						<display:column property="launchDate" format="{0,date,dd/MM/yyyy}" title="LAUNCH_DATE" />
						<display:column property="bscType" title="BSC_TYPE" />
						<display:column property="ordering" title="ORDERING" />						
						<display:column property="createDate" format="{0,date,dd/MM/yyyy}" title="CREATE_DATE" />
						<display:column property="modifyDate" format="{0,date,dd/MM/yyyy}" title="MODIFY_DATE" />
						<display:column property="depId" title="DEP_ID" />
						<display:column property="mscid" title="MSCID" />
						<display:column property="region" title="REGION" /> 
			
						<display:column title="Quản lý" media="html" class="centerColumnMana">
							<a href="form.htm?bscid=${m_bsc.bscid}"><spring:message code="button.edit"/></a>&nbsp;
		    				<a href="delete.htm?bscid=${m_bsc.bscid}"
									onclick="return confirm('<spring:message code="messsage.confirm.delete"/>')"><spring:message code="button.delete"/></a>&nbsp;
		    			</display:column>
						<display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header"
							value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />
						<display:setProperty name="export.xml.filename"
							value="m_bscList.xml" />
						<display:setProperty name="export.csv.filename"
							value="m_bscList.csv" />
					<display:setProperty name="export.excel.filename"
							value="m_bscList.xls" />


					<display:footer> <tr> <td>Total Bill:</td> <td><c:out value="${totals.id}" /></td> <tr> </display:footer> 
				</display:table>
			</div>
		</td>
	 
	</tr>
</table>
<script type="text/javascript" src="/VMSC2-Alarm-Feedback/scripts/calendar.js"></script>
<script type="text/javascript" src="/VMSC2-Alarm-Feedback/scripts/calendar_en.js"></script>
<script type="text/javascript" src="/VMSC2-Alarm-Feedback/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="/VMSC2-Alarm-Feedback/styles/calendar-blue.css" />

<script type="text/javascript">
	Calendar.setup({
	    inputField		:	"launchDate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseLaunchDate",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
	
	
	$(function() {
		$('ul#subnav-3').find(":contains('M_BSC')").css('color', '#f00');
	});
	
	
	function focusIt()
	{
	  var mytext = document.getElementById("bscid");
	  mytext.focus();
	}

	onload = focusIt;
</script>