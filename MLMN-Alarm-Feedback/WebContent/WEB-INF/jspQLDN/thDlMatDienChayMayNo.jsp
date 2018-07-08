<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>TỔNG HỢP DỮ LIỆU MẤT ĐIỆN CHẠY MÁY NỔ TỪ ALARM</title>
<content tag="heading">TỔNG HỢP DỮ LIỆU MẤT ĐIỆN CHẠY MÁY NỔ TỪ ALARM</content>
<form:form commandName="filter" method="GET" action="list.htm">

	<table >
		
			<tr style="padding-right: 20px;">  
          		
          		<td>Từ ngày</td>
          		<td > 
					<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="13" maxlength="10" style="width:110px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				
				<td style="padding-left: 5px;">Đến ngày</td>
				<td>
					<input type ="text"  value="${edate}" name="edate" id="edate" size="13" maxlength="10" style="width:11s0px">
		   			<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					
				</td>
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.bscid"/></td>
				<td>
					<input type="text" id="bscid"  name="bscid"  style="width: 100px;" />
				</td>
				
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.cellTK"/></td>
				<td>
					<input type="text" id="cellid"  name="cellid" style="width: 100px;" />
				</td>
			</tr>
			<tr>
				<td ><fmt:message key="alarmLog.vendor"/></td>
				<td>
				   <div id="vendor" name="vendor"></div>
					<%-- <input type="text" id="vendor"  name="vendor" value="${vendor}" style="width: 100px;" /> --%>
				</td>
				
				<td style="padding-left: 5px;width:100px"><fmt:message key="LossPower.region"/></td>
				<td>
				  <div id="region"  name="region"></div>
				<%-- 	<input type="text" id="region"  name="region" value="${region}" style="width: 100px;" /> --%>
				</td>
				
				<td style="padding-left: 5px;width:100px"><fmt:message key="alarmLog.province"/></td>
				<td>
					 <div id="province" name="province"></div>
					<!-- <input type="text" id="province"  name="province" style="width: 100px;" /> -->
				</td>
				<td style="padding-left: 5px;width: 100px;"><fmt:message key="alarmLog.district"/></td>
				<td>
					 <div id="district" name="district"></div>
					<%-- <input type="text" id="district"  name="district" value="${district}" style="width: 100px;" /> --%>
				</td>
				<td style="padding-left: 5px;">
					<input class="button" type="submit" id="submit" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
				
			</tr>
		</table>
	</form:form>

<table style="width:100%">
	<tr>
		<td style="width: 20px"><div style="float: right;" id="listColumn"></div></td>
	</tr>
</table>
<div id="gridReport"></div>
<div id='menuReport'>
            <ul>
		   		
	            <li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"sdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"edate",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    
	
</script>

<script type="text/javascript">

$(document).ready(function(){
		
		var theme =  getTheme();
		$('#submit').jqxButton({ theme: theme }); 
        var render = function (itemValue, inputValue) {
            var terms = inputValue.split(/,\s*/);
            var value = inputValue;
         
             if (inputValue.indexOf(itemValue) < 0)
    	    	{
          
            	 // remove the current input
                 terms.pop();
                 // add the selected item
    	    	 terms.push(itemValue);
    	         // add placeholder to get the comma-and-space at the end
    	         terms.push("");
    	         value = terms.join(",");
    	    	}
           
            return value;
        };
        
        $("#cellid").jqxInput({ height: 20, width: 200, theme: theme});
        ${bscList}
	    $("#bscid").jqxInput({ height: 20, width: 200, theme: theme,
	        source: function (query, response) {
	            var item = query.split(/,\s*/).pop();
	            // update the search query.
	            $("#bscid").jqxInput({ query: item });
	            response(bscList);
	        },
	        renderer: render
	    });
	    var bscid =  "<c:out value='${bscid}'/>";
	   // alert(bscid);
        if(bscid!="")
			$('#bscid').val(bscid);
	 	
     
	// Create a jqxComboBox vendor
       	var urlVendor = "${pageContext.request.contextPath}/ajax/getVendor.htm";
        // prepare the data
        var sourceVendor =
        {
            datatype: "json",
            datafields: [
                { name: 'value' },
                { name: 'name' }
            ],
            url: urlVendor,
            async: false
        };
        var dataAdapterVendor = new $.jqx.dataAdapter(sourceVendor);
        // Create a jqxComboBox
        $("#vendor").jqxComboBox({ source: dataAdapterVendor, displayMember: "value", valueMember: "name", width: 120,height: '20px',itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
        var vendor =  "<c:out value='${vendor}'/>";
        if(vendor=="")
			$('#vendor').val('');
		else
			$('#vendor').val(vendor);
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
	    $("#province").jqxDropDownList({source: dataAdapterprovince, displayMember: "province", valueMember: "province",checkboxes: true, width: 180, autoDropDownHeight: true, theme: theme,enableHover: true });           

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
		// combobox district 
		// Input district
	 	
        var urldistrict = "${pageContext.request.contextPath}/ajax/getDistrictByProvince.htm?region="+$("#region").val()+"&province="+$("#province").val();
	    var sourcedistrict =
	    {
	       datatype: "json",
	       url: urldistrict,
	       datafields: [
	                     { name: 'district'}
	                 ],
	        async: false
	   };
	    var dataAdapterdistrict = new $.jqx.dataAdapter(sourcedistrict);
	    $("#district").jqxDropDownList({source: dataAdapterdistrict, displayMember: "district", valueMember: "district",checkboxes: true, width: 180, autoDropDownHeight: true, theme: theme,enableHover: true });           

	    var district = '<c:out value="${district}"/>';
	   // alert(dept);
		if(district=="")
			$('#district').val('Choose');
		else
		{
			var districtVar = district.split(",");
			if (districtVar != null) {
				for (var i=0; i<districtVar.length; i++) {
					$("#district").jqxDropDownList('checkItem', districtVar[i] ); 
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
			    $("#province").jqxDropDownList({source: dataAdapterprovince, displayMember: "province", valueMember: "province",checkboxes: true, width: 180, autoDropDownHeight: true, theme: theme,enableHover: true });           

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
			    $("#province").jqxDropDownList({source: dataAdapterprovince, displayMember: "province", valueMember: "province",checkboxes: true, width: 180, autoDropDownHeight: true, theme: theme,enableHover: true });           
			  
			    var urldistrict = "${pageContext.request.contextPath}/ajax/getDistrictByProvince.htm?region="+$("#region").val()+"&province="+$("#province").val();
			    var sourcedistrict =
			    {
			       datatype: "json",
			       url: urldistrict,
			       datafields: [
			                     { name: 'district'}
			                 ],
			        async: false
			   };
			    var dataAdapterdistrict = new $.jqx.dataAdapter(sourcedistrict);
			    $("#district").jqxDropDownList({source: dataAdapterdistrict, displayMember: "district", valueMember: "district",checkboxes: true, width: 180, autoDropDownHeight: true, theme: theme,enableHover: true });           

			});
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
	 	
}); 	
     
        
      
</script>
<script type="text/javascript">
${gridReport}
// handle context menu clicks.
$("#menuReport").on('itemclick', function (event) {
    var args = event.args;
    
    
    
    var exportFileName =  "<c:out value='${exportFileName}'/>";
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
		/*   $("#gridData").jqxGrid('exportdata', 'xls', exportFileName);    */
    	 window.open('${pageContext.request.contextPath}/qldn/tong-hop-du-lieu-mat-dien-chay-may-no/exportData.htm?sdate='+"<c:out value='${sdate}'/>"+
	        	 '&edate='+"<c:out value='${edate}'/>"+
	        	 '&bscid='+"<c:out value='${bscid}'/>"+
	        	 '&cellid='+"<c:out value='${cellid}'/>"+
	        	 '&vendor='+"<c:out value='${vendor}'/>"+
	        	 '&province='+"<c:out value='${province}'/>"+
	        	 '&district='+"<c:out value='${district}'/>"+
	        	 '&region='+"<c:out value='${region}'/>"
	        	 ,'_blank');
    }
});
  
</script> 