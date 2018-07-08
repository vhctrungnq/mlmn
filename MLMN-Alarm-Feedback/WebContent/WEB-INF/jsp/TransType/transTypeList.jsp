
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
						<td style="width:60px; padding-left:5px;"><fmt:message key="Mã trạm"/></td>
						<td>
							<%-- <select name="siteId" id="siteId" style="width: 130px;height:20px; padding-top: 4px;">
							 <option value=""><fmt:message key="global.All"/></option>
								<c:forEach var="item" items="${siteIdList}">
									<c:choose>
						                <c:when test="${item.value == siteId}">
						                    <option value="${item.value}" selected="selected">${item.value}</option>
						                </c:when>
										<c:otherwise>
											<option value="${item.value}">${item.value}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>  --%>
							<input type ="text" name="siteId" id="siteId" style="width: 160px" value="${siteId}">
						</td>
						<td style="padding-left: 5px;width: 80px;"><fmt:message key="Loại trạm"/></td>
						<td>
							<input type ="text"  value="${siteType2G}" name="siteType2G" id="siteType2G" size="25" width="80px">
						</td>
						<td style="width:100px; padding-left:5px;">
							<fmt:message key="Loại truyền dẫn"/>
						</td>
						<td>
							 <input type ="text" name="name" id="name" style="width: 160px" value="${name}">
						</td>
						<td style="padding-left: 5px;" ><fmt:message key="Đối tác 2G"/>&nbsp;&nbsp;
						
							<input type ="text"  value="${dip}" name="dip" id="dip" size="20"  width="80px">
						</td>
						<td>
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
		            <a href="upload.htm"><fmt:message key="button.upload"/></a>
		        </td>
		    </tr>
	   	</c:if>
	  </table>
  		<div id="doublescroll" >
	<display:table name="${alarmList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="siteid" titleKey="Mã trạm" sortable="true" sortName="SITEID"  style="max-width:100px;"/>
		<display:column property="siteType2g"  titleKey="Loại trạm 2G" sortable="true" sortName="SITE_TYPE_2G" style="max-width:60px;"/>
		<display:column property="fullname"  titleKey="Loại truyền dẫn 2G" sortable="true" sortName="FULLNAME"  style="max-width:100px;"/>
		<display:column property="dipPartner2g"  titleKey="Đối tác 2G" sortable="true" sortName="DIP_PARTNER_2G" style="max-width:150px;"/>
		<display:column property="nodeb"  titleKey="NodeB" sortable="true" sortName="NODEB" style="max-width:50px;" />
		<display:column property="dipPartner3g"  titleKey="Đối tác 3G" sortable="true" sortName="DIP_PARTNER_3G" />
		<display:column property="dipType3g"  titleKey="Loại truyền dẫn 3G" sortable="true" sortName="DIP_TYPE_3G" />
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
	<!-- <script type="text/javascript">
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
		var terms = split( this.value );
		// remove the current input
		terms.pop();
		// add the selected item
		terms.push( ui.item.value );
		// add placeholder to get the comma-and-space at the end
		terms.push( "" );
		this.value = terms.join( "," );
		return false;
		}
		});
	});
</script>    	
<script type="text/javascript">
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${siteType2GList}" var="listOfNames">
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
		$( "#siteType2G" )
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
		var bscTp = $("#siteType2G").val();
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
<script type="text/javascript">
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
<script type="text/javascript">
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${siteIdList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadsiteId(availableTags);
	});

	function loadsiteId(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$( "#siteId" )
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