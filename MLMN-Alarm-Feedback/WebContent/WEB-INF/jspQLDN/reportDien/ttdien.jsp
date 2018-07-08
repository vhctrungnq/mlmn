<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <style>     
        .green {
            color: black\9;
            background-color: #b6ff00\9;
        }
        .yellow {
            color: black\9;
            background-color: yellow\9;
        }
        .red {
            color: black\9;
            background-color: #e83636\9;
        }
        .green:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected), .jqx-widget .green:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected) {
            color: black;
            background-color: #b6ff00;
        }
        .yellow:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected), .jqx-widget .yellow:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected) {
            color: black;
            background-color: yellow;
        }
        .red:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected), .jqx-widget .red:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected) {
            color: black;
            background-color: #e83636;
        }
    </style>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div class="clear"></div>
<div class="ui-tabs-panel">

<form:form commandName="filter" method="GET" action="thanh-toan-dien.htm">
<input type="hidden" id="type" name ="type" value="${type}"/>
<table >
		
			<tr> 
				
				<td><fmt:message key="qldnTramTTDien.tgttTq"/></td>
				<td >
					<div id="tgttTq" name="tgttTq" style = "width: 100%"></div>
				</td>
				<td style="padding-left: 10px;width:130px">Từ <fmt:message key="qldnTramTTDien.thangQuyTt"/></td>
				<td>
					<input type="text" id="thangQuyTtF" name="thangQuyTtF" value="${thangQuyTtF}" style="width: 70px;"/>
				&nbsp;&nbsp;-&nbsp;&nbsp;
					<input type="text" id="namTtF" name="namTtF" value="${namTtF}" style="width: 70px;"/>
				</td>
				<td  style="padding-left: 10px;width:130px">Tới <fmt:message key="qldnTramTTDien.thangQuyTt"/></td>
				<td>
					<input type="text" id="thangQuyTtTo" name="thangQuyTtTo" value="${thangQuyTtTo}" style="width: 70px;"/>
				&nbsp;&nbsp;-&nbsp;&nbsp;
					<input type="text" id="namTtTo" name="namTtTo" value="${namTtTo}" style="width: 70px;"/>
				</td>
		</tr>
		<tr>
				<td>Region</td>
				<td>
			        <div id='region'  name="region"></div>
		        </td>
				<td style="padding-left: 10px;width:130px"><fmt:message key="alarmLog.province"/></td>
				<td>
					<div id='province'></div>
				</td>
				<td style="padding-left: 10px;" colspan="2">
					<input class="button" type="submit" id="button" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
		</table>
	</form:form>
</div>

<br/>
<div id="gridReport"></div>
<div id='menuReport'>
            <ul>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>
<div class="clear"></div>

<script type="text/javascript">
${gridReport}


//handle context menu 
$("#menuReport").on('itemclick', function (event) {
	    var args = event.args;
	    var exportFileName =  "<c:out value='${exportFileName}'/>";
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	
	    	window.open('${pageContext.request.contextPath}/dien/thanh-toan-tram/exportData.htm?idTram='+"<c:out value='${idTram}'/>"+
    	        	'&loaitram='+"<c:out value='${loaitram}'/>"+
    	        	 '&tinh='+"<c:out value='${tinh}'/>"+
    	        	 '&nvNhap='+"<c:out value='${nvNhap}'/>"+
    	        	 '&tgttTq='+"<c:out value='${tgttTq}'/>"+
    	        	 '&thangQuyTt='+"<c:out value='${thangQuyTt}'/>"+
    	        	 '&namTt='+"<c:out value='${namTt}'/>"+
    	        	 '&dgLoai='+"<c:out value='${dgLoai}'/>"+
    	        	 '&status='+"<c:out value='${status}'/>"+
    	        	 '&ngayTttw='+"<c:out value='${ngayTttw}'/>"+
    	        	 '&type='+"<c:out value='${type}'/>"
    	        	 ,'_blank');
	    }
});

$(document).ready(function(){

		// Khai bao sdate, edate
		var theme =  getTheme();
		$('#button').jqxButton({ theme: theme });
		// Create a jqxComboBox dgLoai
		var urltgttTq = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=TG_THANH_TOAN";
        // prepare the data
        var sourcetgttTq =
        {
            datatype: "json",
            datafields: [
                { name: 'name'}
            ],
            url: urltgttTq,
            async: false
        };
        var dataAdaptertgttTq = new $.jqx.dataAdapter(sourcetgttTq);
        // Create a jqxComboBox
        $("#tgttTq").jqxComboBox({ source: dataAdaptertgttTq, displayMember: "name", valueMember: "name", width: '100%', height: 20, theme: theme,autoOpen: true  });
        var tgttTq =  "<c:out value='${tgttTq}'/>";
        if(tgttTq=="")
			$('#tgttTq').val('ALL');
		else
			$('#tgttTq').val(tgttTq); 
	  //combobox region
		var urlRegion = "${pageContext.request.contextPath}/ajax/getRegionList.htm";
	    var sourceRegion =
	    {
	       datatype: "json",
	       url: urlRegion,
	       datafields: [
	                     { name: 'name'},
	                     { name: 'value'}
	                 ],
	        async: false
	   };
	    var dataAdapterregion = new $.jqx.dataAdapter(sourceRegion);
	    $("#region").jqxDropDownList({source: dataAdapterregion, displayMember: "name", valueMember: "name",checkboxes: true, width: 120, autoDropDownHeight: true, theme: theme,enableHover: true });           

	    var cbregion = '<c:out value="${region}"/>';
	   // alert(dept);
		if(cbregion=="")
			$('#region').val('Choose');
		else
		{
			var regionVar = cbregion.split(",");
			if (regionVar != null) {
				for (var i=0; i<regionVar.length; i++) {
					$("#region").jqxDropDownList('checkItem', regionVar[i] ); 
				}
			}
		}    
		
		//combobox region
		var urlprovince = "${pageContext.request.contextPath}/ajax/getDSTinh.htm?region="+$("#region").val();
	    var sourceprovince =
	    {
	       datatype: "json",
	       url: urlprovince,
	       datafields: [
	                     { name: 'province'}
	                 ],
	        async: false
	   };
	    var dataAdapterprovince = new $.jqx.dataAdapter(sourceprovince);
	    $("#province").jqxDropDownList({source: dataAdapterprovince, displayMember: "province", valueMember: "province",checkboxes: true, width: 180,itemHeight: 15, theme: theme,enableHover: true });           

	    var province = '<c:out value="${province}"/>';
	   // alert(dept);
		if(province=="")
			$('#province').val('Choose');
		else
		{
			var provinceVar = province.split(",");
			if (provinceVar != null) {
				for (var i=0; i<provinceVar.length; i++) {
					$("#province").jqxDropDownList('checkItem', provinceVar[i] ); 
				}
			}
		}    
		
		//region change
		 $("#region").change(function(){
				var urlprovince = "${pageContext.request.contextPath}/ajax/getDSTinh.htm?region="+$("#region").val();
			    var sourceprovince =
			    {
			       datatype: "json",
			       url: urlprovince,
			       datafields: [
			                     { name: 'province'}
			                 ],
			        async: false
			   };
			    var dataAdapterprovince = new $.jqx.dataAdapter(sourceprovince);
			    $("#province").jqxDropDownList({source: dataAdapterprovince, displayMember: "province", valueMember: "province",checkboxes: true, width: 180, itemHeight: 15, theme: theme,enableHover: true });           

			});
		    
	   
	   
});
</script>
