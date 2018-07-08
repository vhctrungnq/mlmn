
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<title><spring:message code="header.title.processesList"/></title>
<body class="section-4" />
	<form></form>
	 

<content tag="heading"><spring:message code="header.title.processesList"/></content> 
<%-- <div class="content">Tên công việc:&nbsp;</div><div class="danhMuc">${working_mana_name}</div> --%>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form name="frmIndex" commandName="filterProcesses" method="post" action="processes.htm?id_work_mana=${id_work_mana}">
					<table   border="0" cellspacing="1" cellpadding="0">
						<tr> 
							<td class="wid10 mwid70"><spring:message code="title.qLTienDoCongViec.title"/></td>
							<td class="wid30 mwid100"><form:input path="title" cssClass="wid90" /></td>
							
							<td class="wid15 mwid100"><spring:message code="title.qLTienDoCongViec.assess"/></td>
							<td class="wid30">
								<form:select path="assess" class="wid90" id="assess">
									<option value="">--Tất cả--</option>
			         				<c:forEach var="items" items="${sysParameterByCodeList}">
						              	<c:choose>
						                <c:when test="${items.name == tinhTrangCBB}">
						                    <option value="${items.name}" selected="selected">${items.name}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.name}">${items.name}</option>
						                </c:otherwise>
						              	</c:choose>
						    		</c:forEach>
								</form:select>
							</td>
							
							<td><input type="submit" class="button" name="filter"
								value="<spring:message code="button.search"/>" /></td>
						</tr>
					</table>
				</form:form>
				</td> 
		</tr> 
		<tr>
			<td align="right">
            	<a href="formProcesses.htm?id_work_mana=${id_work_mana}"><spring:message code="button.add"/></a>&nbsp;
        	</td>
		</tr>
		
		<tr> 
			<td>
				<div style="width:100%;overflow:auto; ">
					<display:table name="${works_processesList}" class="simple2" id="item"
						requestURI="" pagesize="15" export="true" sort="external" defaultsort="1">
					  	<display:column property="title" titleKey="title.qLTienDoCongViec.title" sortable="true" sortName="TITLE"/>
						<display:column property="actualDate" format="{0,date,dd/MM/yyyy HH:mm:ss}" titleKey="title.qLTienDoCongViec.actualDate" sortable="true" sortName="ACTUAL_DATE"/>
						<display:column property="estimateDate" format="{0,date,dd/MM/yyyy HH:mm:ss}" titleKey="title.qLTienDoCongViec.estimateDate" sortable="true" sortName="ESTIMATE_DATE"/>
						<display:column property="content" titleKey="title.qLTienDoCongViec.content" sortable="true" sortName="CONTENT"/>
						<display:column property="remark" titleKey="title.qLTienDoCongViec.remark" sortable="true" sortName="REMARK"/>
						<display:column property="assess" titleKey="title.qLTienDoCongViec.assess" sortable="true" sortName="ASSESS"/>				
						
						<display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
							<a href="formProcesses.htm?id=${item.id}&id_work_mana=${item.idWorks}"><spring:message code="button.edit"/></a>&nbsp;
		    				<a href="deleteProcesses.htm?id=${item.id}&id_work_mana=${item.idWorks}" onclick="return confirm('<spring:message code="messsage.confirm.delete"/>')"><spring:message code="button.delete"/></a>&nbsp;
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
	<tr> 
		<td class="styleVeTrangTruoc ftsize12">
			<a href="formContentDetails.htm?id=${id_work_mana}"><spring:message code="button.vetrangtruoc"/></a>
		</td>	
	</tr>
</table>

<script type="text/javascript">
	
	function focusIt()
	{
	  var mytext = document.getElementById("title");
	  mytext.focus();
	}

	onload = focusIt;
	
	
</script>