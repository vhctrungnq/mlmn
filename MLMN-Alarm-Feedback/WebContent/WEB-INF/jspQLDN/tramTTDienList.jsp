<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxwindow.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxdropdownbutton.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtree.js"></script>
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
<c:choose>
<c:when test="${type=='TW'}">
<title>QUẢN LÝ THÔNG TIN TRẠM THANH TOÁN ĐIỆN TW</title>
<content tag="heading">QUẢN LÝ THÔNG TIN TRẠM THANH TOÁN ĐIỆN TW</content> 	
</c:when>
<c:when test="${type=='DOT_BIEN'}">
<title>THÔNG TIN TRẠM TIÊU THỤ ĐIỆN NĂNG VƯỢT QUÁ ĐỊNH MỨC</title>
<content tag="heading">THÔNG TIN TRẠM TIÊU THỤ ĐIỆN NĂNG VƯỢT QUÁ ĐỊNH MỨC</content> 	
</c:when>
<c:otherwise>
<title>CHÊNH LỆCH THEO ĐỊNH MỨC</title>
<content tag="heading">CHÊNH LỆCH THEO ĐỊNH MỨC</content> 	
</c:otherwise>
</c:choose>

<div>
<form:form commandName="filter" method="GET" action="list.htm">
<input type="hidden" id="type" name ="type" value="${type}"/>
	<table >
			<tr>
				<td  style="width:100px"><fmt:message key="qldnTramTTDien.thangQuyTt"/></td>
				<td>
					<input id="thangQuyTt" name="thangQuyTt" value="${thangQuyTt}" style="width: 100px;"/>
				</td>
				<td  style="padding-left: 10px;width:100px"><fmt:message key="qldnTramTTDien.namTt"/></td>
				<td>
					<input  id="namTt" name="namTt" value="${namTt}" style="width: 100px;"/>
				</td>
				<td style="padding-left: 10px;width:100px"><fmt:message key="qldnTramTTDien.loaitram"/></td>
				<td style="width:150px;">
					<div id="loaitram"  name="loaitram" style = "width: 100%"></div>
				</td>
			</tr>
			<tr>
				<td ><fmt:message key="qldnTramTTDien.idTram"/></td>
				<td >
					<input id="idTram" name ="idTram" value="${idTram}" />
				</td>
				<td style="padding-left: 10px;"><fmt:message key="QldnThongTinTram.daiVT"/></td>
				<td >
					<input  id="daiVT" name ="daiVT" value="${daiVT}"  />
				</td>
				
				<td colspan="2"  style="padding-left: 10px;" >
					<input class="button" type="submit" id="btFilter" name="btFilter" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
			
			<tr>
				
			</tr>
		</table>
	</form:form>
</div>
<div class="clear"></div>	

<table style="width:100%">
	<tr>
	
		<td align="right" class="ftsize12">
		<c:if test="${type!='DOT_BIEN'}">
			<input class="button"  type="button" value="Nhập lẻ trạm thanh toán" id='addNew' onclick='window.location="form.htm?type=${type}"' />
			<input type="button" value="<fmt:message key="qldn.import"/>" id='importFile'   onclick='window.location="${pageContext.request.contextPath}/import-qldn/upload.htm?typeFile=QLDN_TRAM_TT_DIEN_HCM"'/>
		</c:if>
			<input type="button" value="Export" id="exportFile"/>
		</td>
		<td style="width: 20px"><div style="float: right;" id="listData"></div></td>
	</tr>
</table>
<div id="gridData"></div>
<div id='Menu'>
            <ul>
            	<li><fmt:message key="global.button.editSelected"/></li>
            	<li><fmt:message key="global.button.deleteSelected"/></li>
	            <li><fmt:message key="global.button.daiVTXuLy"/></li>
	        </ul>
</div>
<div class="clear"></div>
<div id="window">
<div>ĐVT cập nhật cách thức xử lý</div>
		<div>
		<table class="simple2">
			<tr>
		        <td>Mã trạm</td>
		           <td>
		           		<!-- <input type="hidden" id="idTramList" name ="idTramList"/> -->
		      			<div id="idTramList" style = "width: 100%"></div>
			    	</td>
			  </tr>
			  <tr>
		        <td>Nguyên nhân</td>
		           <td>
		           		<div id="nguyenNhan" style = "width: 100%"></div>
			    	</td>
			  </tr>
			  <tr>
		         
		    	<td>Chi tiết nguyên nhân</td>
	            <td > 
	          		 <textarea  id="chiTietNN" name ="chiTietNN" rows="4" cols="38">  </textarea> 
	          	</td>
	         
		       </tr>
			   <tr>
	       			<td>
	       				<input type="button" class="button" value="Save" id="btSave" />
	                    <input type="button" class="button" value="Cancel" id="btCancel" />
	                </td>
	       		</tr>
	       </table>
     </div>
   </div>

<script type="text/javascript">
var theme =  getTheme();

var cellclass = function (row, columnfield, value) {
	var dotBien = $('#gridData').jqxGrid('getrowdata', row).dotBien;
	if (dotBien == 1) {return 'red';}
	if (dotBien == 2) {return 'yellow';}
};
${gridData}
$("#thangQuyTt").jqxInput({height: 20, width: '100%', minLength: 0, maxLength: 400, theme: theme});
$("#namTt").jqxInput({ height: 20, width: '100%', minLength: 0, maxLength: 400, theme: theme});

$(document).ready(function(){
		// Khai bao sdate, edate
		var theme =  getTheme();
		$('#btFilter').jqxButton({ theme: theme });
		$('#addNew').jqxButton({ theme: theme });
		$('#importFile').jqxButton({ theme: theme });
		$('#exportFile').jqxButton({ theme: theme });
		
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
		
		 //Input daiVT
	    ${daiVTList}
	    $("#daiVT").jqxInput({ placeHolder: "Nhập ĐVT ", height: 20, width: '100%', theme: theme,
	        source: function (query, response) {
	            var item = query.split(/,\s*/).pop();
	            $("#daiVT").jqxInput({ query: item });
	            response(daiVTList);
	        },
	        renderer: renderer
	    });
	    var daiVT =  "<c:out value='${daiVT}'/>";
	    if(daiVT!="")
			$('#daiVT').val(daiVT);
	 	
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
        $("#loaitram").jqxComboBox({ source: dataAdapterloaitram, displayMember: "value", valueMember: "name", width: '150px',height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
        var loaitram =  "<c:out value='${loaitram}'/>";
        if(loaitram=="")
			$('#loaitram').val('ALL');
		else
			$('#loaitram').val(loaitram);
       
    var theme =  getTheme();
	// create jqxWindow.
	$("#window").jqxWindow({ resizable: true, theme: theme, autoOpen: false, minWidth: 500, maxWidth: 800, minHeight:300,isModal: true});
	
	  // handle context menu clicks.
    $("#Menu").on('itemclick', function (event) {
        var args = event.args;
        
        if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
       	  	var rowindex = $("#gridData").jqxGrid('getselectedrowindex');
            var row = $("#gridData").jqxGrid('getrowdata', rowindex);
        	window.location = 'form.htm?id='+row.id+'&type=';   
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
        				idList+=cond+row.id;
        				cond=",";
        			}
        			// alert(idList);
        			window.location = 'delete.htm?idList='+idList+'&type=';
        		}
   			}
        }
         if ($.trim($(args).text()) == '<fmt:message key="global.button.daiVTXuLy"/>')
    	{
	    	
	    	// Create a jqxComboBox dgLoai
			var urltgttTq = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=NGUYEN_NHAN_CHENH_LECH";
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
	        $("#nguyenNhan").jqxComboBox({ source: dataAdaptertgttTq, displayMember: "name", valueMember: "name", width: '100%', height: 20, theme: theme,autoOpen: true  });
	        
	        var rowindex = $("#gridData").jqxGrid('getselectedrowindex');
            var row = $("#gridData").jqxGrid('getrowdata', rowindex);
            
	        /* var selectedrowindexes = $('#gridData').jqxGrid('getselectedrowindexes'); 
    		var idList="";
    		var cond="";
    		if (selectedrowindexes != null) {
    			for (var i=0; i<selectedrowindexes.length; i++) {
    				var row = $("#gridData").jqxGrid('getrowdata', selectedrowindexes[i]);
    				idList+=cond+row.idTram;
    				cond=",";
    			}
    		} */
    		//alert(row.idTram);
    		//alert(row.kqToKt);
    		 $("#idTramList").text(row.idTram);
    		 $('textarea#chiTietNN').text(row.kqToKt);
	        var nguyenNhan =  row.nguyenNhan;
	        if(nguyenNhan=="")
				$("#nguyenNhan").val('ALL');
			else
				$("#nguyenNhan").val(nguyenNhan); 
	        
	    	$("#window").jqxWindow('open');
    	}
        
    });
    $("#btCancel").click(function (event) {
	    $("#window").jqxWindow('close');

	});


	$("#btSave").click(function (event) {
		
		if ($("#nguyenNhan").val()=='')
			{
				alert("Chưa nhập đầy đủ thông tin bắt buộc");
			}
		else
		{
			
			$.getJSON("${pageContext.request.contextPath}/dien/report/daiVTXuLy.htm",{idTram:$("#idTramList").text(),tgttTq:$("#tgttTq").val(),thangQuyTt:$("#thangQuyTt").val(),namTt:$("#namTt").val(),nguyenNhan:$("#nguyenNhan").val(),kqToKt:$('textarea#chiTietNN').val()}, function(j){
	    		
				 if (j==1)
					 {
					 	alert("Đài VT cập nhật thông tin xử lý thành công!");
					 	$("#window").jqxWindow('close');
					 	window.location = '${pageContext.request.contextPath}/dien/thanh-toan-tram/list.htm?idTram='+$("#idTram").val()+'&loaitram='+$("#loaitram").val()+'&daiVT='+$("#daiVT").val()+'&tgttTq='+$("#tgttTq").val()+'&thangQuyTt='+$("#thangQuyTt").val()+'&namTt='+$('#namTt').val()+'&type='+$('#type').val();
						
					 }
				 else
					 {
					 	alert("Đài VT cập nhật thông tin xử lý không thành công!");
					 }
	    	   });
		}
	   
	});
	
   $("#exportFile").click(function (event) {
	   var exportFileName =  "<c:out value='${exportFileName}'/>";
   	/*   $("#gridData").jqxGrid('exportdata', 'xls', exportFileName);    */
   	 window.open('${pageContext.request.contextPath}/dien/thanh-toan-tram/exportData.htm?idTram='+"<c:out value='${idTram}'/>"+
	        	 '&loaitram='+"<c:out value='${loaitram}'/>"+
	        	 '&tinh='+"<c:out value='${tinh}'/>"+
	        	 '&daiVT='+"<c:out value='${daiVT}'/>"+
	        	 '&thangQuyTt='+"<c:out value='${thangQuyTt}'/>"+
	        	 '&namTt='+"<c:out value='${namTt}'/>"+
	        	 '&status='+"<c:out value='${status}'/>"+
	        	 '&type='+"<c:out value='${type}'/>"
	        	 ,'_blank');
		
	});
	
  });
	  

</script>