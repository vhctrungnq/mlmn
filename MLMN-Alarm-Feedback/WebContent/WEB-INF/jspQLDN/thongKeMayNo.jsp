<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>THỐNG KÊ THÔNG TIN MÁY NỔ</title>
<content tag="heading">THỐNG KÊ THÔNG TIN MÁY NỔ</content> 	

<br>
<br>
<div class="clear"></div>	
<div style="width:48%;float: left;" >
<!--Report1  -->
	<div id="gridTINH"></div>
	<div id='MenuTINH'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
	<!--Report2  -->
	<div id="gridLTT"></div>
	<div id='MenuLTT'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
	<!--Report2  -->
	<div id="gridATS"></div>
	<div id='MenuATS'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
	<!--Report2  -->
	<div id="gridLOAINL"></div>
	<div id='MenuLOAINL'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
</div>
<div style="width:4%;overflow: auto;float: left;"> </div>
<div style="width:48%;overflow: auto;float: right;" >

	<!--Report2  -->
	<div id="gridHIEUMN"></div>
	<div id='MenuHIEUMN'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
	<!--Report2  -->
	<div id="gridCS_MN"></div>
	<div id='MenuCS_MN'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
	
	
	<!--Report2  -->
	<div id="gridTEN_XHH"></div>
	<div id='MenuTEN_XHH'>
	            <ul>
			   		<li><fmt:message key="global.button.exportExcel"/></li>
		        </ul>
	</div>
	<div class="clear"></div><br>
	

</div>


<script type="text/javascript">
var theme =  getTheme();
${gridTINH}
${gridLTT}
${gridATS}
${gridLOAINL}
${gridHIEUMN}
${gridCS_MN}
${gridTEN_XHH}



$(document).ready(function(){
		
	  // handle context menu clicks.
    $("#MenuTINH").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_TINH"+"<c:out value='${dateNow}'/>";
        	  $("#gridTINH").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    $("#MenuLTT").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_LTT"+"<c:out value='${dateNow}'/>";
        	  $("#gridLTT").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    $("#MenuATS").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_ATS"+"<c:out value='${dateNow}'/>";
        	  $("#gridATS").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    $("#MenuLOAINL").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_LOAINL"+"<c:out value='${dateNow}'/>";
        	  $("#gridLOAINL").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    $("#MenuHIEUMN").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_HIEUMN"+"<c:out value='${dateNow}'/>";
        	  $("#gridHIEUMN").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    $("#MenuCS_MN").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_CS_MN"+"<c:out value='${dateNow}'/>";
        	  $("#gridCS_MN").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    $("#MenuTEN_XHH").on('itemclick', function (event) {
        var args = event.args;
       	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	var exportFileName =  "REPORT_TEN_XHH"+"<c:out value='${dateNow}'/>";
        	  $("#gridTEN_XHH").jqxGrid('exportdata', 'xls', exportFileName);   
	    }
    });
    
      
  });
	  

</script>