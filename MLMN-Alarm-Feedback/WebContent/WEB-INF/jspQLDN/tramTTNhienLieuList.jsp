<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title}</content> 	

<div>
<form:form commandName="filter" method="GET" action="list.htm">
<input type="hidden" id="type" name ="type" value="${type}"/>
	<table >
			<tr>
				
				<td ><fmt:message key="qldnTramTTNL.ngayChayMfF"/></td>
				<td style="width:130px">
						<input type ="text"  value="${ngayChayMfF}" name="ngayChayMfF" id="ngayChayMfF" size="17" maxlength="16" style="width:100px">
			   			 <img alt="calendar" title="Click to choose the from date" id="choosengayNhapF" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				
				<td  style="padding-left: 5px;width:100px"><fmt:message key="qldnTramTTNL.ngayChayMfT"/></td>
				<td style="width:130px">
						<input type ="text"  value="${ngayChayMfT}" name="ngayChayMfT" id="ngayChayMfT" size="17" maxlength="16" style="width:100px">
			   			 <img alt="calendar" title="Click to choose the from date" id="choosengayNhapT" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				
				<td style="padding-left: 10px;width:100px"><fmt:message key="qldnTramTTNL.idTram"/></td>
				<td colspan="2">
					<input id="idTram" name ="idTram" />
				</td>
				
			</tr>
			<tr>
				<td ><fmt:message key="qldnTramTTNL.loaiNhienLieu"/></td>
				<td >
					<div id="loaiNhienLieu"  name="loaiNhienLieu" style = "width: 100%"></div>
				</td>
				<td  style="padding-left: 10px;width:100px"><fmt:message key="qldnTramTTNL.tinh"/></td>
				<td>
					<div id="tinh"  name="tinh" style = "width: 100%"></div>
				</td>
				<td style="padding-left: 10px;"><fmt:message key="qldnTramTTNL.tenXhhVtt"/></td>
				<td >
					<input  id="tenXhhVtt" name ="tenXhhVtt" />
				</td>
				
				<td  style="padding-left: 10px;" >
					<input class="button" type="submit" id="btFilter" name="btFilter" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
		</table>
	</form:form>
</div>
<div class="clear"></div>	
<table style="width:100%">
	<tr>
		<td align="right" class="ftsize12">
		<input class="button"  type="button" value="Thanh toán NL" id='addNew' onclick='window.location="form.htm?type=${type}"' />
		 <input type="button" value="<fmt:message key="qldn.import"/>" id='importFile' />
		</td>
		<td style="width: 20px"><div style="float: right;" id="listData"></div></td>
	</tr>
</table>
<div id="gridData"></div>
<div id='Menu'>
            <ul>
            	<li><fmt:message key="global.button.editSelected"/></li>
            	<li><fmt:message key="global.button.deleteMultiSelected"/></li>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>
<div class="clear"></div>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<script type="text/javascript">
Calendar.setup({
    inputField		:	"ngayChayMfF",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "choosengayNhapF",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"ngayChayMfT",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "choosengayNhapT",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 	

</script>

<script type="text/javascript">
var theme =  getTheme();
${gridData}
$("#tenXhhVtt").jqxInput({height: 20, width: '100%', minLength: 0, maxLength: 400, theme: theme});

$(document).ready(function(){
		// Khai bao sdate, edate
		var theme =  getTheme();
		$('#btFilter').jqxButton({ theme: theme });
		$('#addNew').jqxButton({ theme: theme });
		$('#importFile').jqxButton({ theme: theme });
		$('#importFile').click(function () {
	        window.location = ' ${pageContext.request.contextPath}/import-qldn/upload.htm?typeFile=QLDN_TRAM_TT_NHIEN_LIEU';
	    }); 
	    
		var renderer = function (itemValue, inputValue) {
	        var terms = inputValue.split(/,\s*/);
	        var value = inputValue;
	     
	         if (inputValue.indexOf(itemValue) < 0)
		    	{
	      
	        	 // remove the current input
	             terms.pop();
	             // add the selected item
		    	 terms.push(itemValue);
		         // add placeholder to get the comma-and-space at the end
		        // terms.push("");
		         value = terms.join(",");
		    	}
	       
	        return value;
	    };
	   		// Input site
		 	${siteList}
		 	$("#idTram").jqxInput({  height: 20, width: '100%', theme: theme,
		        source: function (query, response) {
		            var item = query.split(/,\s*/).pop();
		            // update the search query.
		            $("#idTram").jqxInput({ query: item });
		            response(siteList);
		        },
		        renderer: renderer
		    });
		 	var idTram =  "<c:out value='${idTram}'/>";
		 	//alert(cellid);
	        if(idTram!="")
				$('#idTram').val(idTram);
		
		//Input province
	   var urlprovince = "${pageContext.request.contextPath}/ajax/getDSTinh.htm?region=";
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
	    $("#tinh").jqxDropDownList({source: dataAdapterprovince, displayMember: "province", valueMember: "province",checkboxes: true, width: 180,itemHeight: 15, theme: theme,enableHover: true });           

	    var province = '<c:out value="${tinh}"/>';
	   // alert(dept);
		if(province=="")
			$('#tinh').val('Choose');
		else
		{
			var provinceVar = province.split(",");
			if (provinceVar != null) {
				for (var i=0; i<provinceVar.length; i++) {
					$("#tinh").jqxDropDownList('checkItem', provinceVar[i] ); 
				}
			}
		}    
	 	
	// Create a jqxComboBox vendor
       	var urlloaiNhienLieu = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=LOAI_NHIEN_LIEU";
        // prepare the data
        var sourceloaiNhienLieu =
        {
            datatype: "json",
            datafields: [
                { name: 'value' },
                { name: 'name' }
            ],
            url: urlloaiNhienLieu,
            async: false
        };
        var dataAdapterloaiNhienLieu = new $.jqx.dataAdapter(sourceloaiNhienLieu);
        // Create a jqxComboBox
        $("#loaiNhienLieu").jqxComboBox({ source: dataAdapterloaiNhienLieu, displayMember: "value", valueMember: "name", width: '100%',height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
        var loaiNhienLieu =  "<c:out value='${loaiNhienLieu}'/>";
        if(loaiNhienLieu=="")
			$('#loaiNhienLieu').val('ALL');
		else
			$('#loaiNhienLieu').val(loaiNhienLieu);
       
	  // handle context menu clicks.
    $("#Menu").on('itemclick', function (event) {
        var args = event.args;
        var type = "<c:out value='${type}'/>";
        if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
       	  	var rowindex = $("#gridData").jqxGrid('getselectedrowindex');
            var row = $("#gridData").jqxGrid('getrowdata', rowindex);
        	window.location = 'form.htm?type='+type+'&id='+row.id; 
        }
       
        if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteMultiSelected"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#gridData').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		//alert(selectedrowindexes);
        		//var rowIndexList = selectedrowindexes.split(",");
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#gridData").jqxGrid('getrowdata', selectedrowindexes[i]);
        				idList+=cond+row.id;
        				cond=",";
        			}
        			// alert(idList);
        			window.location = 'delete.htm?idList='+idList+'&type='+type;
        		}
   			}
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "<c:out value='${exportFileName}'/>";
        	/*   $("#gridData").jqxGrid('exportdata', 'xls', exportFileName);    */
        	 window.open('${pageContext.request.contextPath}/nhien-lieu/exportData.htm?ngayChayMfF='+"<c:out value='${ngayChayMfF}'/>"+
    	        	'&ngayChayMfT='+"<c:out value='${ngayChayMfT}'/>"+
    	        	 '&idTram='+"<c:out value='${idTram}'/>"+
    	        	 '&loaiNhienLieu='+"<c:out value='${loaiNhienLieu}'/>"+
    	        	 '&tinh='+"<c:out value='${tinh}'/>"+
    	        	 '&tenXhhVtt='+"<c:out value='${tenXhhVtt}'/>"+
    	        	 '&type='+"<c:out value='${type}'/>"
    	        	 ,'_blank'); 
	    	
	    }
    });
      
  });
	  

</script>