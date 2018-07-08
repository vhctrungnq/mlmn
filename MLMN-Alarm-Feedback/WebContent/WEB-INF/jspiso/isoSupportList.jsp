<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title><fmt:message key="sidebar.admin.isoSupport"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.isoSupport"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form method="post" action="list.htm">
					<table border="0" cellspacing="1" cellpadding="0" width="100%">
						<tr> 
							<td class="wid6 mwid70"><fmt:message key="isoSupport.ne"/></td>
							<td class="wid15">	
						       	<input type="text" id="ne" name="ne" value="${neName}" class="wid90" />
							</td>
							
							<td class="wid6 mwid70"><fmt:message key="isoSupport.boardName"/></td>
							<td class="wid15">
						       	<input type="text" id="boardName" name="boardName" value="${boardName}" class="wid90" />
							</td>
							<td class="wid7 mwid80"><fmt:message key="isoSupport.unit"/></td>
							<td class="wid15">
								<input type="text" id="unit" name="unit" value="${unit}" class="wid90" />
							</td>		 
							<td>
								<input type="submit" value="<fmt:message key="global.form.timkiem"/>" id='jqxSubmitButton' />
							</td>
						</tr>				
					</table>
				</form>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				
				<%@ include file="/WEB-INF/jspiso/jqwidgets/gridSimple.jsp" %>
			</td>
		</tr>
</table>

<script type="text/javascript">
$(document).ready(function () {
	var theme = getTheme();
	$("#jqxSubmitButton").jqxButton({theme: theme});
	$("#ne").jqxInput({ height: 20, minLength: 1, theme: theme });
	$("#boardName").jqxInput({ height: 20, minLength: 1, theme: theme });
	$("#unit").jqxInput({ height: 20, minLength: 1, theme: theme });
	
});

function focusIt()
{
  var mytext = document.getElementById("ne");
  mytext.focus();
}

onload = focusIt;

</script>
