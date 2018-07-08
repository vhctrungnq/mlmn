
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">    
	#doublescroll { overflow: auto; overflow-y: hidden; }    
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style> 
<title><spring:message code="header.title.working_typesList"/></title>
<body class="section-4" />
	<form></form>
	 

<content tag="heading"><spring:message code="header.title.working_typesList"/></content> 	
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post"	action="list.htm">
					<table   border="0" cellspacing="1" cellpadding="0">
						<tr> 
							<td class="wid15 mwid110"><spring:message code="title.qLDanhMucCongViec.loaiCongViec"/></td>
							<td class="wid30"><form:input path="name" cssClass="wid90" /></td>
							
							<td class="wid9 mwid60"><spring:message code="title.qLDanhMucCongViec.sapXep"/></td>
							<td class="wid30"><form:input path="orderingStr" cssClass="wid90" /></td> 
							
							<td><input class="button" type="submit" class="button" name="filter"
								value="<spring:message code="button.search"/>" /></td>
						</tr>
					</table>
				</form:form>
				</td> 
		</tr> 
		<tr>
			<td align="right">
            	<a href="form.htm"><spring:message code="button.add"/></a>
        	</td>
		</tr>
</table>
<div id="doublescroll">
	<display:table name="${working_typesList}" class="simple2" id="item" requestURI="" pagesize="15" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column class="wid40" property="name" titleKey="title.qLDanhMucCongViec.loaiCongViec"/>
		<display:column property="ordering" titleKey="title.qLDanhMucCongViec.sapXep" class="centerColumnMana" />
		<display:column property="createdBy" titleKey="title.qLDanhMucCongViec.nguoiTao"/>
		<display:column property="createDate" format="{0,date,dd/MM/yyyy}" titleKey="title.qLDanhMucCongViec.ngayTao"/>
		<display:column property="modifiedBy"  titleKey="title.qLDanhMucCongViec.nguoiCapNhat"/>
		<display:column property="modifyDate" format="{0,date,dd/MM/yyyy}" titleKey="title.qLDanhMucCongViec.ngayCapNhat"/>
		
		<display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
			<a href="form.htm?id=${item.id}"><spring:message code="button.edit"/></a>&nbsp;
  				<a href="delete.htm?id=${item.id}"
					onclick="return confirm('<spring:message code="messsage.confirm.delete"/>')"><spring:message code="button.delete"/></a>&nbsp;
  			</display:column>
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
	</display:table>
</div>
<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("name");
	  mytext.focus();
	}

	onload = focusIt;
</script>