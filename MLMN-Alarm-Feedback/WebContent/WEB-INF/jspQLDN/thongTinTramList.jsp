<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>QUẢN LÝ THÔNG TIN TRẠM</title>
<content tag="heading">QUẢN LÝ THÔNG TIN TRẠM</content> 	

<div>
<form:form commandName="filter" method="GET" action="list.htm">
	<table >
			<tr>
				
				<%-- <td ><fmt:message key="QldnThongTinTram.ngaypsFrom"/></td>
				<td style="width:130px">
						<input type ="text"  value="${ngaypsFrom}" name="ngaypsFrom" id="ngaypsFrom" size="17" maxlength="16" style="width:100px">
			   			 <img alt="calendar" title="Click to choose the from date" id="choosengaypsFrom" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				
				<td  style="padding-left: 5px;width:130px"><fmt:message key="QldnThongTinTram.ngaypsTo"/></td>
				<td style="width:130px">
						<input type ="text"  value="${ngaypsTo}" name="ngaypsTo" id="ngaypsTo" size="17" maxlength="16" style="width:100px">
			   			 <img alt="calendar" title="Click to choose the from date" id="choosengaypsTo" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td  style="padding-left: 5px;width:130px"><fmt:message key="QldnThongTinTram.tinh"/></td>
				<td>
					<input type="text" id="tinh" name="tinh" style="width: 100px;"/>
				</td> --%>
				<td ><fmt:message key="QldnThongTinTram.loaitram"/></td>
				<td >
					<div id="loaitram"  name="loaitram" style = "width: 100%"></div>
				</td>
				
				<td style="padding-left: 10px;width:100px"><fmt:message key="QldnThongTinTram.idTram"/></td>
				<td >
					<input id="idTram" name ="idTram" value="${idTram}" />
				</td>
				<td style="padding-left: 10px;width:100px"><fmt:message key="QldnThongTinTram.makh"/></td>
				<td >
					<input  id="makh" name ="makh" value="${makh}" />
				</td>
			</tr>
			
			<%--<tr>
				
				 <td style="padding-left: 5px;"><fmt:message key="QldnThongTinTram.nguonccd"/></td>
				<td >
					<div id="nguonccd" name="nguonccd" style = "width: 100%"></div>
				</td>
				<td  style="padding-left: 5px;"><fmt:message key="QldnThongTinTram.huyen"/></td>
				<td >
					<input type="text" id="huyen" name="huyen" style="width: 100px;"/>
				</td> 
				
				
				
			</tr>--%>
			<tr>
				<%-- <td ><fmt:message key="QldnThongTinTram.httt"/></td>
				<td>
					<div id="httt" name="httt"></div>
				</td> --%>
				<td  ><fmt:message key="QldnThongTinTram.dienDvth"/></td>
				<td >
					<div id="dienDvth"  name="dienDvth" style = "width: 100%"></div>
				</td>
				<td  style="padding-left: 10px;width:100px"><fmt:message key="QldnThongTinTram.daiVT"/></td>
				<td>
					<select id="daiVT"  name="daiVT" class="wid60" >
			   				<c:forEach var="items" items="${maPhongList}">
				              <c:choose>
				                <c:when test="${items.deptCode == daiVT}">
				                    <option value="${items.deptCode}" selected="selected">${items.deptCode}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.deptCode}">${items.deptCode}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			           	</select>
				</td>
				
				
				<%-- <td style="padding-left: 5px;"><fmt:message key="QldnThongTinTram.nguoittdien"/></td>
				<td >
					<input  id="nguoittdien" name ="nguoittdien" value="${nguoittdien}" />
				</td>
				<td style="padding-left: 10px;" ><fmt:message key="QldnThongTinTram.dgLoai"/></td>
				<td>
					<div id="dgLoai" name="dgLoai" ></div>
				</td> --%>
				<td  style="padding-left: 10px;" colspan="2">
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
		<input class="button"  type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' onclick='window.location="formHCM.htm"' />
		<input type="button" value="<fmt:message key="qldn.import"/>" id='importFile'   onclick='window.location="${pageContext.request.contextPath}/import-qldn/upload.htm?typeFile=QLDN_THONG_TIN_TRAM_HCM"'/>
		<c:if test="${isRolaManager=='Y'}">
			<input type="button" value="Cập nhật định mức" id='capNhatDM'   onclick='window.location="capNhatDM.htm"'/>
		</c:if>
		</td>
		<td style="width: 20px"><div style="float: right;" id="listData"></div></td>
	</tr>
</table>
<div id="gridData"></div>
<div id='Menu'>
            <ul>
            	<li><fmt:message key="global.button.editSelected"/></li>
	            <li><fmt:message key="global.button.deleteSelected"/></li>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>
<div class="clear"></div>


<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<script type="text/javascript">
Calendar.setup({
    inputField		:	"ngaypsFrom",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "choosengaypsFrom",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"ngaypsTo",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "choosengaypsTo",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 	
</script> --%>

<script type="text/javascript">
var theme =  getTheme();
${gridData}
$("#makh").jqxInput({placeHolder: "Nhập Mã KH ", height: 20, width: '100%', minLength: 0, maxLength: 400, theme: theme});
//$("#nguoittdien").jqxInput({placeHolder: "Nhập người thanh toán ", height: 20, width: '100%', minLength: 0, maxLength: 400, theme: theme});

$(document).ready(function(){
		// Khai bao sdate, edate
		var theme =  getTheme();
		$('#btFilter').jqxButton({ theme: theme });
		$('#addNew').jqxButton({ theme: theme });
		
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
		 /* 	${siteList} */
		 	$("#idTram").jqxInput({ placeHolder: "Nhập Mã Trạm", height: 20, width: '100%', theme: theme
		    });
		 	var idTram =  "<c:out value='${idTram}'/>";
		 	//alert(cellid);
	        if(idTram!="")
				$('#idTram').val(idTram);
	
	 	
	// Create a jqxComboBox vendor
       	var urlloaitram = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=LOAI_TRAM";
        // prepare the data
        var sourceloaitram =
        {
            datatype: "json",
            datafields: [
                { name: 'value' },
                { name: 'name' }
            ],
            url: urlloaitram,
            async: false
        };
        var dataAdapterloaitram = new $.jqx.dataAdapter(sourceloaitram);
        // Create a jqxComboBox
        $("#loaitram").jqxComboBox({ source: dataAdapterloaitram, displayMember: "value", valueMember: "name", width: 120,height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
        var loaitram =  "<c:out value='${loaitram}'/>";
        if(loaitram=="")
			$('#loaitram').val('ALL');
		else
			$('#loaitram').val(loaitram);
      
	/*  // Create a jqxComboBox nguonccd
		var urlnguonccd = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=NGUON_CUNG_CAP_DIEN";
        // prepare the data
        var sourcenguonccd =
        {
            datatype: "json",
            datafields: [
                { name: 'name'}
            ],
            url: urlnguonccd,
            async: false
        };
        var dataAdapternguonccd = new $.jqx.dataAdapter(sourcenguonccd);
        // Create a jqxComboBox
        $("#nguonccd").jqxComboBox({ source: dataAdapternguonccd, displayMember: "name", valueMember: "name", width: 120, height: 20, theme: theme,autoOpen: true  });
        var nguonccd =  "<c:out value='${nguonccd}'/>";
        if(nguonccd=="")
			$('#nguonccd').val('ALL');
		else
			$('#nguonccd').val(nguonccd);
	
        // Create a jqxComboBox httt
		var urlhttt = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=HINH_THUC_THANH_TOAN";
        // prepare the data
        var sourcehttt =
        {
            datatype: "json",
            datafields: [
                { name: 'name'}
            ],
            url: urlhttt,
            async: false
        };
        var dataAdapterhttt = new $.jqx.dataAdapter(sourcehttt);
        // Create a jqxComboBox
        $("#httt").jqxComboBox({ source: dataAdapterhttt, displayMember: "name", valueMember: "name", width: 120, height: 20, theme: theme,autoOpen: true  });
        var httt =  "<c:out value='${httt}'/>";
        if(httt=="")
			$('#httt').val('ALL');
		else
			$('#httt').val(httt);
     // Create a jqxComboBox dgLoai
		var urldgLoai = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=DON_GIA_LOAI";
        // prepare the data
        var sourcedgLoai =
        {
            datatype: "json",
            datafields: [
                { name: 'name'},
                { name: 'value'}
            ],
            url: urldgLoai,
            async: false
        };
        var dataAdapterdgLoai = new $.jqx.dataAdapter(sourcedgLoai);
        // Create a jqxComboBox
        $("#dgLoai").jqxComboBox({ source: dataAdapterdgLoai, displayMember: "value", valueMember: "value", width: '97%', height: 20, theme: theme,autoOpen: true  });
        var dgLoai =  "<c:out value='${dgLoai}'/>";
        if(dgLoai=="")
			$('#dgLoai').val('ALL');
		else
			$('#dgLoai').val(dgLoai);
		
		 */
        // Create a jqxComboBox dienDvth
		var urldienDvth = "${pageContext.request.contextPath}/ajax/getDonViThuHuong.htm";
        // prepare the data
        var sourcedienDvth =
        {
            datatype: "json",
            datafields: [
                { name: 'tenDv'}
            ],
            url: urldienDvth,
            async: false
        };
        var dataAdapterdienDvth = new $.jqx.dataAdapter(sourcedienDvth);
        // Create a jqxComboBox
        $("#dienDvth").jqxComboBox({ source: dataAdapterdienDvth, displayMember: "tenDv", valueMember: "tenDv", width: 120, height: 20, theme: theme,autoOpen: true  });
        var dienDvth =  "<c:out value='${dienDvth}'/>";
        if(dienDvth=="")
			$('#dienDvth').val('ALL');
		else
			$('#dienDvth').val(dienDvth);
		
	  // handle context menu clicks.
    $("#Menu").on('itemclick', function (event) {
        var args = event.args;
        
        if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
       	  	var rowindex = $("#gridData").jqxGrid('getselectedrowindex');
            var row = $("#gridData").jqxGrid('getrowdata', rowindex);
        	window.location = 'formHCM.htm?idTram='+row.idTram+'&typeF=edit';   
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
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
        				idList+=cond+row.idTram;
        				cond=",";
        			}
        			// alert(idList);
        			window.location = 'delete.htm?idList='+idList;
        		}
   			}
        }
        var exportFileName =  "<c:out value='${exportFileName}'/>";
    	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    		window.open('${pageContext.request.contextPath}/dien/thong-tin-tram/exportData.htm?idTram='+"<c:out value='${idTram}'/>"+
    	        	 '&loaitram='+"<c:out value='${loaitram}'/>"+
    	        	 '&daiVT='+"<c:out value='${daiVT}'/>"+
    	        	 '&dienDvth='+"<c:out value='${dienDvth}'/>"+
    	        	 '&makh='+"<c:out value='${makh}'/>"
    	        	 ,'_blank');
	    }
    });
      
  });
	  

</script>