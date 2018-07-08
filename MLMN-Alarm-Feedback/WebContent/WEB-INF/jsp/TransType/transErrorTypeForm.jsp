<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<title>${titleF}</title>
<content tag="heading">${titleF}</content>
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
<form:form commandName="hAlTransErrorType" name="checkform" id="myform" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
           <td style="width: 150px;"><fmt:message key="hAlTransErrorType.groupName"/><font color="red">(*)</font></td>
           <td>
	           	<form:input type ="text" path="groupName" name="groupName" id="groupName"  maxlength="50" style="width:200px;" rows="3"/>
           		<font color="red"><form:errors path="groupName"/></font>
           </td>
         </tr>
         <tr>
          <td ><fmt:message key="hAlTransErrorType.name"/><font color="red">(*)</font></td>
           	<td>
           		<form:input type ="text" path="name" maxlength="50" style="width:200px;" rows="3"/>
          		<font color="red"><form:errors path="name"/></font>
           	</td>
       
      	</tr> 
     	
        <tr>
           <td><fmt:message key="hAlTransErrorType.description"/></td>
           <td>
           		<form:textarea path="description" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" name="description" id="description" rows="10" maxlength="200"/>
	    	</td>            
         </tr> 
      	<tr>  
           <td></td>
           <td>
               <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="list.htm"'>
					
           </td>
       </tr>
    </table>
</form:form>

<style>
    .error {
    	color: red;
    }
</style> 


<script type="text/javascript">

function focusIt()
{
	if(document.checkform.groupName.value==""){
	  var mytext = document.getElementById("groupName");
	  mytext.focus();
	}
}

onload = focusIt;
</script>
<script type="text/javascript">
$(function() {
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
	this.value = ui.item.value;
	return false;
	}
	});
}	
</script>    	

