
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>   
 
    #bscid_chzn {
		width: 220px !important;
	}
	
	.number {
	text-align: right;
}
	
</style>

<title>${title}</title>
<content tag="heading">${title}</content> 	
<div>
<form:form commandName="serviceSiteCell" method="POST" action="list.htm?netWork=${netWork}">
<input type="hidden" name="typeNetWork" id="typeNetWork" value="${typeNetWork}">
	
<table>
		<tr>
			<td ><fmt:message key="serviceSiteCell.province"/></td>
			<td>
				<select name="province" id="province" style="width: 170px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.All"/></option>
           		<c:forEach var="item" items="${provinceList}">
					<c:choose>
		                <c:when test="${item.province == province}">
		                    <option value="${item.province}" selected="selected">${item.province}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.province}">${item.province}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
			</td>
			
			<td  style="padding-left: 5px;"><fmt:message key="serviceSiteCell.district"/></td>
			<td>
				<select name="district" id="district" style="width: 170px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.All"/></option>
           		<c:forEach var="item" items="${districtList}">
					<c:choose>
		                <c:when test="${item.district == district}">
		                    <option value="${item.district}" selected="selected">${item.district}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.district}">${item.district}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
			</td>
			
			<td style="padding-left: 5px;width: 70px;"><fmt:message key="serviceSiteCell.bscid"/></td>
			<td>
				<form:input path="bscid" width="80px" size="25"/>
			</td>
			
			<td style="padding-left: 5px;width: 70px;"><fmt:message key="serviceSiteCell.address"/></td>
			<td>
				<form:input path="address" width="80px" size="25"/>
			</td>
			
			<td>
				<input class="button" type="submit" class="button" 	value="<fmt:message key="button.search"/>" />
			</td>
			</tr>
	</table>
</form:form>
</div>

  <div id="doublescroll" >
	<display:table name="${alarmList}"  class="simple2" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="bscid" titleKey="serviceSiteCell.bscid" sortable="true" sortName="BSCID" style="min-width:65px;max-width:65px;"/>
	  	<display:column property="cellid" titleKey="serviceSiteCell.cellid" sortable="true" sortName="CELLID" />
		<display:column property="sdate"  titleKey="serviceSiteCell.sdate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="SDATE"  style="min-width:105px;max-width:105px;"/>
		<display:column property="tgMCH" titleKey="serviceSiteCell.tgMCH" sortable="true" sortName="TG_MCH" class="number"/>
		<display:column property="address"  titleKey="serviceSiteCell.address" sortable="true" sortName="ADDRESS"  style="min-width:150px;"/>
		<display:column property="district"  titleKey="serviceSiteCell.district" sortable="true" sortName="DISTRICT" />
		<display:column property="province"  titleKey="serviceSiteCell.province" sortable="true" sortName="PROVINCE" />
		<display:column property="causebySy"  titleKey="serviceSiteCell.causebySystem" sortable="true" sortName="CAUSEBY_SY" style="max-width:60px;"/>
		<display:column property="causeby"  titleKey="serviceSiteCell.causes" sortable="true" sortName="CAUSEBY" style="max-width:60px;"/>
				
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>
	   
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>
<script type="text/javascript"> $(".chzn-select").chosen(); $(".chzn-select-deselect").chosen({allow_single_deselect:true}); </script>

<script type="text/javascript">
$("#province").change(function(){
	$.getJSON("${pageContext.request.contextPath}/ajax/getDistrictByProvince.htm",{province: $(this).val()}, function(j){
		
		var options = '<option value=""><fmt:message key="global.All"/></option>';
		for (var i = 0; i < j.length; i++) {
			options += '<option value="' + j[i].district + '">' + j[i].district+ '</option>';
		}
		$("#district").html(options);
	});

});

$("#district").change(function(){
	$.getJSON("${pageContext.request.contextPath}/ajax/getBSCIDByDistrict.htm",{district: $(this).val(),netWork: $("#typeNetWork").val()}, function(j){
		var availableTags = [];
		for (var i = 0; i < j.length; i++) {
			availableTags[i] = j[i];
		}
		loadBsc(availableTags);
		
	});

	$.getJSON("${pageContext.request.contextPath}/ajax/getAddressByDistrict.htm",{district: $(this).val(),netWork: $("#typeNetWork").val()}, function(j){
		var availableTags2 = [];
		for (var i = 0; i < j.length; i++) {
			availableTags2[i] = j[i];
		}
		loadAddress(availableTags2);
		
	});

});

	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${bscidList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadBsc(availableTags);
		/*load địa điểm*/
		var availableTags2 = [];
		var i2 = 0;
		<c:forEach items="${addressList}" var="listOfNames2">
			availableTags[i] = "<c:out value='${listOfNames2}' escapeXml='false' />";
			i2 = i2 + 1;
		</c:forEach>
		loadAddress(availableTags2);
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
		var terms = split( this.value );
		// remove the current input
		terms.pop();
		//check and add the selected item
		var temp = ui.item.value;
		var bscTp = $("#bscid").val();
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


	function loadAddress(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$( "#address" )
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
		terms.push( ui.item.value );
		return false;
		}
		});
	}	
</script>
 