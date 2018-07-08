
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
	
	.dpn {
		display:none;
	}
	
</style>
<title>${title}</title>
<content tag="heading">${title}</content> 	

<table class="form" style="width:100%;">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post"	action="list.htm">
				<table >
					<tr> 
						<td style="width:100px; padding-left:5px;"><fmt:message key="hQualityNetwork.groupName"/></td>
						<td>
							<select name="groupName" id="groupName" style="width: 130px;height:20px; padding-top: 4px;">
							 <option value=""><fmt:message key="global.All"/></option>
								<c:forEach var="item" items="${groupNameList}">
									<c:choose>
						                <c:when test="${item.value == groupName}">
						                    <option value="${item.value}" selected="selected">${item.value}</option>
						                </c:when>
										<c:otherwise>
											<option value="${item.value}">${item.value}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select> 
						</td>
						<td style="width:100px; padding-left:5px;">
							<fmt:message key="hQualityNetwork.qualityName"/>
						</td>
						<td>
							 <input type ="text" name="qualityName" id="qualityName" style="width: 200px" value="${qualityName}">
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
		<tr>
				<td class="wid10 mwid60 ftsize12" align="right" colspan="6">
		            <a href="form.htm"><fmt:message key="button.add"/></a>
		        </td>
		</tr>
	   	
	  </table>
  		<div id="doublescroll" >
	<display:table name="${alarmList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="groupName" titleKey="hQualityNetwork.groupName" sortable="true" sortName="GROUP_NAME"  style="max-width:150px;"/>
	  	<display:column property="qualityCode"  titleKey="hQualityNetwork.qualityCode" sortable="true" sortName="QUALITY_CODE" style="max-width:100px;"/>
		<display:column property="qualityName"  titleKey="hQualityNetwork.qualityName" sortable="true" sortName="QUALITY_NAME"  style="max-width:150px;"/>
		<display:column property="qualityCondition" titleKey="hQualityNetwork.qualityCondition" sortable="true" sortName="QUALITY_CONDITION" class="dpn" headerClass="dpn"/>
		<display:column titleKey="hQualityNetwork.qualityCondition" class="centerColumnMana" sortable="true" sortName="QUALITY_CONDITION"  media="html" style="max-width:40px;">
			<c:out value="${item.qualityCondition}"></c:out>
   		</display:column>
		<display:column property="qualityValue"  titleKey="hQualityNetwork.qualityValue" sortable="true" sortName="QUALITY_VALUE" style="max-width:100px;"/>
		<display:column property="qualityUnit"  titleKey="hQualityNetwork.qualityUnit" sortable="true" sortName="QUALITY_UNIT" style="max-width:40px;"/>
		<display:column property="ordering"  titleKey="hQualityNetwork.ordering" sortable="true" sortName="ORDERING" style="min-width:30px;" />
		<display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
				<a href="form.htm?id=${item.id}"><fmt:message key="button.edit"/></a>&nbsp;
   				<a href="delete.htm?id=${item.id}"
						onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="button.delete"/></a>&nbsp;
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
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${qualityNameList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadQuality(availableTags);
	});

	$("#groupName").change(function(){
		$.getJSON("${pageContext.request.contextPath}/ajax/getQualityByGroup.htm",{groupName: $(this).val()}, function(j){
			
			var availableTags = [];
			for (var i = 0; i < j.length; i++) {
				availableTags[i] = j[i];
			}
			
			loadQuality(availableTags);
		});
	
	});
	function loadQuality(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$( "#qualityName" )
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
		terms.push( ui.item.value );
		// add placeholder to get the comma-and-space at the end
		terms.push( "" );
		this.value = terms;
		return false;
		}
		});
	}	
</script>    	


