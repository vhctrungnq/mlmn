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
<form:form commandName="hAlTransType" name="checkform" id="myform" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
           <td style="width: 150px;"><fmt:message key="Mã trạm"/><font color="red">(*)</font></td>
           <td>
	           <%-- <select name="groupName" id="groupName" style="width: 160px;height:20px; padding-top: 4px;">
	           		<c:forEach var="item" items="${groupNameList}">
						<c:choose>
			                <c:when test="${item.value == team}">
			                    <option value="${item.value}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.value}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>  --%>
				<form:input type ="text" path="siteid" maxlength="20" style="width:200px;" rows="3"/>
           		<font color="red"><form:errors path="siteid"/></font>
           </td>
        </tr> 
        <tr>
           		<td style="width: 120px;"><fmt:message key="Loại trạm 2G"/></td>
           		<td>
           				<form:input type ="text" path="siteType2g" maxlength="200" style="width:200px;" />
  
	       		</td>
        </tr>
        <tr>
           <td style="width: 120px;"><fmt:message key="Loại truyền dẫn 2G"/></td>
           <td>
           			<form:input type ="text" path="name" maxlength="50" style="width:200px;" rows="3"/>
          			<font color="red"><form:errors path="name"/></font>
           </td>
        </tr>
       <tr>
           <td><fmt:message key="Đối tác 2G"/></td>
           <td>
           		<form:input type ="text" path="dipPartner2g" maxlength="200" style="width:200px;" rows="3"/>
	    	</td>
	    </tr>
	    <tr>
	    	<td><fmt:message key="NodeB"/></td>
            <td>
           		<form:input type ="text" path="nodeb" maxlength="20" style="width:200px;" rows="3"/>
	    	</td>
	    </tr>
        <tr>
           <td><fmt:message key="Loại truyền dẫn 3G"/></td>
           <td  colspan="5">
           		<form:input path="dipType3g" rows="10" maxlength="200" style="width:200px;"/>
	    	</td>            
         </tr> 
         <tr>
           <td><fmt:message key="Đối tác 3G"/></td>
           <td  colspan="5">
           		<form:input path="dipPartner3g" rows="10" maxlength="200" style="width:200px;"/>
	    	</td>            
         </tr> 
      	<tr>  
           <td></td>
           <td colspan="5">
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
<!-- <script type="text/javascript">
$(function() {
	var availableTags2 = [];
	var i = 0;
	<c:forEach items="${transTypeList}" var="listOfNames1">
		availableTags2[i] = "<c:out value='${listOfNames1}' escapeXml='false' />";
		i = i + 1;
	</c:forEach>
	loadtransType(availableTags2);
});
function loadtransType(availableTags){
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
	// var terms = split( this.value );
	// remove the current input
	//terms.pop();
	//terms.push( ui.item.value );
	// add placeholder to get the comma-and-space at the end
	//terms.push( "" );
	//this.value = terms;
	this.value = ui.item.value;
	return false;
	}
	});
}	
</script>    	

<script type="text/javascript">
	$(function() {
		//Load Dip
		var cacheCell = {}, lastXhrCell;
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$( "#dip" )
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
		/*response( $.ui.autocomplete.filter(
		availableTags, extractLast( request.term ) ) );*/
			var term = extractLast( request.term );
			if ( term in cacheCell ) {
				response( cacheCell[ term ] );
				return;
			}
			lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getDipList.htm", {term: term}, function( data, status, xhr ) {
				cacheCell[ term ] = data;
				if ( xhr === lastXhrCell ) {
					response( data );
				}
			});
			
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
	});
</script>    	
<script type="text/javascript">
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${bscidList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadBsc(availableTags);
	});

	function loadBsc(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$( "#bscid" )
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

<script type="text/javascript">
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${groupNameList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadgroupName(availableTags);
	});

	function loadgroupName(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$( "#groupName" )
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
</script> -->
