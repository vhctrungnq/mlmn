<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>highlight list</title>
<body class="section-4"/>
<content tag="heading">DANH SÁCH HIGHLIGHT</content>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr>
    	<td align="left">
        </td>
        <td align="right">
            <a href="form.htm">Thêm mới</a>&nbsp;
        </td>
    </tr>
    <tr height="5"><td colspan="2"/></tr>
    <tr><td colspan="2">			
		<div  style="overflow: auto;">
<display:table name="${highlightConfigList}" class="simple2" id="highlightConfig" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true">
		    <display:column property="id" title="ID"/>
		    <display:column property="kpi" title="KPI"/>
		    <display:column property="key" title="KEY"/>
		    <display:column property="formula" title="CÔNG THỨC"/>
		    <display:column property="value" title="GIÁ TRỊ"/>
		    <display:column title="KIỂU GIÁ TRỊ" >
		    	<div style="${highlightConfig.style}">${highlightConfig.style}</div>
		    </display:column>
		    <display:column title="QUẢN LÝ" media="html">
		    	<a href="form.htm?id=${highlightConfig.id}">Sửa</a>&nbsp;
		    	<a href="delete.htm?id=${highlightConfig.id}"
		    	   onclick="return confirm('Bạn có chắc muốn xóa?')" >Xóa</a>&nbsp;
		    </display:column>
		    	<display:setProperty name="export.csv.filename" value="HighlightConfig.csv"/>
	<display:setProperty name="export.excel.filename" value="HighlightConfig.xls"/>
	<display:setProperty name="export.xml.filename" value="HighlightConfig.xml"/>
		</display:table>
</div>
	</td></tr>
</table>
