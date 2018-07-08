<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>THỐNG KÊ THÔNG TIN TRẠM</title>
<content tag="heading">THỐNG KÊ THÔNG TIN TRẠM</content> 	
<br>
<br>
<div class="clear"></div>	
<div style="width:48%;float: left;" >
<!--Report1  -->
	<div id="gridPROVINCE"></div>
	<div id='MenuPROVINCE'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
	<!--Report2  -->
	<div id="gridHTTT"></div>
	<div id='MenuHTTT'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
	<!--Report2  -->
	<div id="gridDG_LOAI"></div>
	<div id='MenuDG_LOAI'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
	<!--Report2  -->
	<div id="gridTGTT_TQ"></div>
	<div id='MenuTGTT_TQ'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
</div>
<div style="width:4%;overflow: auto;float: left;"> </div>
<div style="width:48%;overflow: auto;float: right;" >

	<!--Report2  -->
	<div id="gridNGUONCCD"></div>
	<div id='MenuNGUONCCD'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
	<!--Report2  -->
	<div id="gridNGUOITTDIEN"></div>
	<div id='MenuNGUOITTDIEN'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
	
	
	<!--Report2  -->
	<div id="gridLOAITRAM"></div>
	<div id='MenuLOAITRAM'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
	
<!--Report2  -->
	<div id="gridTINH_NHACC"></div>
	<div id='MenuTINH_NHACC'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
</div>


<script type="text/javascript">
var theme =  getTheme();
${gridPROVINCE}
${gridHTTT}
${gridNGUONCCD}
${gridNGUOITTDIEN}
${gridDG_LOAI}
${gridTGTT_TQ}
${gridLOAITRAM}
${gridTINH_NHACC}


$(document).ready(function(){
		
	  // handle context menu clicks.
    $("#MenuPROVINCE").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_PROVINCE"+"<c:out value='${dateNow}'/>";
        	  $("#gridPROVINCE").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    $("#MenuHTTT").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_HTTT"+"<c:out value='${dateNow}'/>";
        	  $("#gridHTTT").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    $("#MenuNGUONCCD").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_NGUONCCD"+"<c:out value='${dateNow}'/>";
        	  $("#gridNGUONCCD").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    $("#MenuNGUOITTDIEN").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_NGUOITTDIEN"+"<c:out value='${dateNow}'/>";
        	  $("#gridNGUOITTDIEN").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    $("#MenuDG_LOAI").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_DG_LOAI"+"<c:out value='${dateNow}'/>";
        	  $("#gridDG_LOAI").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    $("#MenuTGTT_TQ").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_TGTT_TQ"+"<c:out value='${dateNow}'/>";
        	  $("#gridTGTT_TQ").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    $("#MenuLOAITRAM").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_LOAITRAM"+"<c:out value='${dateNow}'/>";
        	  $("#gridLOAITRAM").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    $("#MenuTINH_NHACC").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_TINH_NHACC"+"<c:out value='${dateNow}'/>";
        	  $("#gridTINH_NHACC").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
      
  });
	  

</script>