
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>  
	.ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	* we use height instead, but this forces the menu to always be this tall
	*/
	* html .ui-autocomplete {
		height: 200px;
	}
</style>
<title>${title}</title>
<content tag="heading">${title}</content> 	

<table class="form" style="width:100%;">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post"	action="list.htm">
				<table >
					<tr> 
						<td style="width:130px; padding-left:5px;"><fmt:message key="hAlTransErrorType.groupName"/></td>
						<td>
							<input type ="text" name="groupName" id="groupName" style="width: 160px" value="${groupName}">
						</td>
						<td style="width:130px; padding-left:5px;">
							<fmt:message key="hAlTransErrorType.name"/>
						</td>
						<td>
							 <input type ="text" name="name" id="name" style="width: 160px" value="${name}">
						</td>
						<td style="padding-left:5px;">
							<input class="button" type="submit" class="button"
								value="<fmt:message key="button.search"/>" />
						</td>
							
						</tr>
					</table>
				</form:form>
			</td> 
		</tr> 
		<c:if test="${checkCaTruc==true}">
			<tr>
				<td class="wid10 mwid60 ftsize12" align="right" colspan="6">
		            <a href="form.htm"><fmt:message key="button.add"/></a>
		        </td>
		    </tr>
	   	</c:if>
	  </table>
  		<div id="doublescroll" >
	<display:table name="${alarmList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="groupName" titleKey="hAlTransErrorType.groupName" sortable="true" sortName="GROUP_NAME" />
	  	<display:column property="name"  titleKey="hAlTransErrorType.name" sortable="true" sortName="NAME"/>
		<display:column property="description"  titleKey="hAlTransErrorType.description" sortable="true" sortName="DESCRIPTION" />
		<c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="max-width:40px;min-width:40px;">
				<a href="form.htm?id=${item.id}"><fmt:message key="button.edit"/></a>&nbsp;
   				<a href="delete.htm?id=${item.id}"
						onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="button.delete"/></a>&nbsp;
   			</display:column>
	   	</c:if>
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>
<script type="text/javascript">
$(function() {
	var availableTags = [];
	var i = 0;
	<c:forEach items="${nameList}" var="listOfNames1">
		availableTags[i] = "<c:out value='${listOfNames1}' escapeXml='false' />";
		i = i + 1;
	</c:forEach>
	loadTransErrorType(availableTags);
	var availableTags2 = [];
	var i = 0;
	<c:forEach items="${groupNameList}" var="listOfNames1">
		availableTags2[i] = "<c:out value='${listOfNames1}' escapeXml='false' />";
		i = i + 1;
	</c:forEach>
	loadGrouptransType(availableTags2);
});
function loadGrouptransType(availableTags){
	function split( val ) {
	return val.split( /,\s*/ );
	}
	function extractLast( term ) {
	return split( term ).pop();
	}
	$("#groupName")
	// don't navigate away from the field on tab when selecting an item
	.bind( "keydown", function( event ) {
	if ( event.keyCode === $.ui.keyCode.TAB &&
	$( this ).data( "autocomplete" ).menu.active ) {
	event.preventDefault();
	}
	})
	.autocomplete({
	minLength: 0,
	source: function( request, response ) {
	// delegate back to autocomplete, but extract the last term
	response( $.ui.autocomplete.filter(
	availableTags, extractLast( request.term ) ) );
	},
	focus: function() {
	// prevent value inserted on focus
	return false;
	},
	select: function( event, ui ) {
		var terms = split( this.value );
		// remove the current input
		terms.pop();
		//check and add the selected item
		var temp = ui.item.value;
		var bscTp = $("#groupName").val();
		var bscL = bscTp.split(",");
		var kt = false;
		for (var i=0; i<bscL.length; i++) {
			if (temp==bscL[i])
				kt=true;
		}
		if (kt==false)
		{
			terms.push( ui.item.value );
			// add placeholder to get the comma-and-space at the end
			terms.push( "" );
			this.value = terms.join( "," );
		}
	return false;
	}
	});
}	
function loadTransErrorType(availableTags){
	function split( val ) {
	return val.split( /,\s*/ );
	}
	function extractLast( term ) {
	return split( term ).pop();
	}
	$("#name")
	// don't navigate away from the field on tab when selecting an item
	.bind( "keydown", function( event ) {
	if ( event.keyCode === $.ui.keyCode.TAB &&
	$( this ).data( "autocomplete" ).menu.active ) {
	event.preventDefault();
	}
	})
	.autocomplete({
	minLength: 0,
	source: function( request, response ) {
	// delegate back to autocomplete, but extract the last term
	response( $.ui.autocomplete.filter(
	availableTags, extractLast( request.term ) ) );
	},
	focus: function() {
	// prevent value inserted on focus
	return false;
	},
	select: function( event, ui ) {
		var terms = split( this.value );
		// remove the current input
		terms.pop();
		//check and add the selected item
		var temp = ui.item.value;
		var bscTp = $("#name").val();
		var bscL = bscTp.split(",");
		var kt = false;
		for (var i=0; i<bscL.length; i++) {
			if (temp==bscL[i])
				kt=true;
		}
		if (kt==false)
		{
			terms.push( ui.item.value );
			// add placeholder to get the comma-and-space at the end
			terms.push( "" );
			this.value = terms.join( "," );
		}
	return false;
	}
	});
}
</script> 
