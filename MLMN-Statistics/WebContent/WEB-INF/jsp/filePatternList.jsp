<!-- ThanhLV -->
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="title.cFilePatterns.list"/></title>
<content tag="heading"><fmt:message key="title.cFilePatterns.list"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
    <tr>
    	<td align="left">
			<form:form commandName="filter" method="post" action="list.htm">
				<table width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td class="wid8 mwid90"><fmt:message key="cFilePatterns.filePattern"/></td>
						<td class="wid15">
							<form:input path="filePattern" cssClass="wid90"/>
						</td>
					    <td class="wid8 mwid90"><fmt:message key="cFilePatterns.nodeType"/></td>
					    <td class="wid15"><form:input path="nodeType" cssClass="wid90" /></td>
					    <td class="wid8 mwid80"><fmt:message key="cFilePatterns.convertClass"/></td>
						<td class="wid15"><form:input path="convertClass" cssClass="wid90" /></td>
						
			        </tr>
			        <tr>
			        	<td><fmt:message key="cFilePatterns.rawTable"/></td>
						<td>
							<form:input path="rawTable" cssClass="wid90"/>
						</td>
			        	<td><fmt:message key="cFilePatterns.status"/></td>
					    <td>
					    	<form:select path="status" cssClass="wid90">
			                    <form:option value="" label="Tất cả"/>
			                    <form:option value="Y" label="Đang sử dụng"/>
			                    <form:option value="N" label="Không sử dụng"/>
			                </form:select>
					    </td>
					    <td></td>
					    <td><input class="button" type="submit" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
			        </tr>		
				</table>
			</form:form>
        </td>
        <td></td>
    </tr>
    <tr>
    	<td>
		</td>
		<td class="wid10 mwid140" align="right">
			<a href="form.htm"><fmt:message key="global.form.themmoi"/></a>&nbsp;
			<a href="upload.htm"><fmt:message key="global.button.import"/></a>&nbsp;
		</td> 
	</tr>
    <tr>
    	<td colspan="2">			
			<div  style="overflow: auto;">
				<display:table name="${filePatternList}" id="item" class="simple2" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true">
						<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="filePattern" titleKey="cFilePatterns.filePattern"/>
						<display:column property="nodeType" titleKey="cFilePatterns.nodeType"/>
						<display:column property="rawTable" titleKey="cFilePatterns.rawTable"/>
						<display:column property="convertClass" titleKey="cFilePatterns.convertClass"/>
						<display:column property="trangthai" titleKey="cFilePatterns.status"/>
						<display:column property="subDir" titleKey="cFilePatterns.subDir" headerClass="hide" class="hide" media="html"/>
						<display:column property="nodePatternGroup" titleKey="cFilePatterns.nodePatternGroup" headerClass="hide" class="hide" media="html"/>
						<display:column property="timePatternGroup" titleKey="cFilePatterns.timePatternGroup" headerClass="hide" class="hide" media="html"/>
						<display:column property="separator" titleKey="cFilePatterns.separator" headerClass="hide" class="hide" media="html"/>
						<display:column property="commentChar" titleKey="cFilePatterns.commentChar" headerClass="hide" class="hide" media="html"/>
						<display:column property="timePattern" titleKey="cFilePatterns.timePattern" headerClass="hide" class="hide" media="html"/>
						<display:column property="importRule" titleKey="cFilePatterns.importRule" headerClass="hide" class="hide" media="html"/>
					    <display:column class="centerColumnMana" titleKey="global.management" media="html">
					    	<a href="form.htm?patternId=${item.patternId}"><fmt:message key="global.form.sua"/></a>&nbsp;
					    	<a href="delete.htm?patternId=${item.patternId}"
					    	  onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="global.form.xoa"/></a>
					    </display:column>
					    
					    <display:setProperty name="export.csv.include_header" value="true"/>
					    <display:setProperty name="export.excel.include_header" value="true"/>
					    <display:setProperty name="export.xml.include_header" value="false"/>
				    	<display:setProperty name="export.csv.filename" value="FilePatternList.csv"/>
					    <display:setProperty name="export.excel.filename" value="FilePatternList.xls"/>
					    <display:setProperty name="export.xml.filename" value="FilePatternList.xml"/>
					    
				</display:table>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
function focusIt()
{
  var mytext = document.getElementById("filePattern");
  mytext.focus();
}
onload = focusIt;
</script>
