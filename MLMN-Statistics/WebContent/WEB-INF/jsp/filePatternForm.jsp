<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:choose>
  <c:when test="${FilePatternsAddEdit == 'N'}">
      <title><fmt:message key="title.cFilePatterns.formAdd"/></title>
	  <content tag="heading"><fmt:message key="title.cFilePatterns.formAdd"/></content>
  </c:when>
  <c:when test="${FilePatternsAddEdit == 'Y'}">
      <title><fmt:message key="title.cFilePatterns.formEdit"/></title>
	  <content tag="heading"><fmt:message key="title.cFilePatterns.formEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<form:form name="checkform" commandName="filePattern"  method="post" action="form.htm">
	<form:hidden path="patternId" />
	
    <table class="simple2">
        <tr>
            <td class="wid15 mwid110"><fmt:message key="cFilePatterns.filePattern"/>&nbsp;<font color="red">(*)</font></td>
            <td class="wid35"><form:input path="filePattern" cssClass="wid50" maxlength="200"/>&nbsp;<form:errors path="filePattern" cssClass="error"/> 
            </td>
            <td class="wid15 mwid110"><fmt:message key="cFilePatterns.nodeType"/></td>
            <td><form:input path="nodeType" cssClass="wid50" maxlength="30"/></td>
        </tr>
        <tr>
            <td><fmt:message key="cFilePatterns.rawTable"/></td>
            <td><form:input path="rawTable" maxlength="30" cssClass="wid50"/></td>  
            <td><fmt:message key="cFilePatterns.convertClass"/>&nbsp;<font color="red">(*)</font></td>
            <td><form:input path="convertClass" cssClass="wid50" maxlength="100"/>&nbsp;<form:errors path="convertClass" cssClass="error"/></td>
        </tr>
        <tr>
            <td><fmt:message key="cFilePatterns.subDir"/></td>
            <td><form:input path="subDir" maxlength="50" cssClass="wid50"/></td>
            <td><fmt:message key="cFilePatterns.nodePatternGroup"/></td>
            <td><form:input path="nodePatternGroup" cssClass="wid30" maxlength="2"/>&nbsp;<form:errors path="nodePatternGroup" cssClass="error"/></td>
        </tr>
        <tr>
            <td><fmt:message key="cFilePatterns.timePatternGroup"/></td>
            <td><form:input path="timePatternGroup" cssClass="wid30" maxlength="2"/>&nbsp;<form:errors path="timePatternGroup" cssClass="error"/></td>
            <td><fmt:message key="cFilePatterns.separator"/></td>
            <td><form:input path="separator" cssClass="wid30" maxlength="5"/>&nbsp;<form:errors path="separator" cssClass="error"/></td>
        </tr>
        <tr>
            <td><fmt:message key="cFilePatterns.commentChar"/></td>
            <td><form:input path="commentChar" cssClass="wid30" maxlength="5"/>&nbsp;<form:errors path="commentChar" cssClass="error"/></td>
            <td><fmt:message key="cFilePatterns.timePattern"/></td>
            <td><form:input path="timePattern" cssClass="wid30" maxlength="30"/>&nbsp;<form:errors path="timePattern" cssClass="error"/></td>
        </tr>
        <tr>
            <td><fmt:message key="cFilePatterns.importRule"/></td>
            <td>
            	<form:select path="importRule" cssClass="wid50">
                    <form:option value="F" label="File"/>
                    <form:option value="M" label="Import Mapping"/>
                </form:select>
			</td>
           <td><fmt:message key="cFilePatterns.status"/></td>
           <td>  
            	<form:select path="status" cssClass="wid50">
                    <form:option value="Y" label="Đang sử dụng"/>
                    <form:option value="N" label="Không sử dụng"/>
                </form:select>
			</td>
        </tr>
        <tr>
            <td></td>
            <td colspan="3">
                <input type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>"/>
                <input type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"' class="button">
            </td>
        </tr>
    </table>
</form:form>
<script type="text/javascript">
function focusIt()
{
	
	if(document.checkform.filePattern.value==""){
		  var mytext = document.getElementById("filePattern");
		  mytext.focus();
	}
	else if(document.checkform.convertClass.value=="")
	{
			var mytext = document.getElementById("convertClass");
			  mytext.focus();
	}
	else if(document.checkform.nodePatternGroup.value=="")
	{
			var mytext = document.getElementById("nodePatternGroup");
			  mytext.focus();
	}
	else if(document.checkform.timePatternGroup.value=="")
	{
			var mytext = document.getElementById("timePatternGroup");
			  mytext.focus();
	}
	else if(document.checkform.separator.value=="")
	{
			var mytext = document.getElementById("separator");
			  mytext.focus();
	}
	else if(document.checkform.commentChar.value=="")
	{
			var mytext = document.getElementById("commentChar");
			  mytext.focus();
	}
	else if(document.checkform.timePattern.value=="")
	{
			var mytext = document.getElementById("timePattern");
			  mytext.focus();
	}
	
}
onload = focusIt;
</script>