<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <style>
 .shiftId
{
color:blue;
}
 </style>  
<div id="jqxWidget" style="font-size: 13px; font-family: Verdana; float: left;width:100%;">
       <div style="float: right;" id="jqxlistbox"></div>
        <div id="jqxgrid"></div>
        <div id='Menu'>
	        <ul>
	        	<li><fmt:message key="global.button.detailFB"/></li>
            	<li><fmt:message key="button.xuLyFBTheoLo"/></li>
            	<li><fmt:message key="global.button.LaDiemDen"/></li>
            	<li><fmt:message key="global.button.KhongLaDiemDen"/></li>
            	<li><fmt:message key="global.button.removeFB"/></li>
            	<li><fmt:message key="global.button.deleteFB"/></li>
            	<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
	     </div>
 </div>

<script type="text/javascript">
$(document).ready(function () {
var theme = getTheme();
    
	//Get URL for get data
    var url = "<c:out value='${url}'/>";
    url = url.replace(/amp;/g,'');
    
	// prepare the data source
    ${datafields}
   
    var source =
    {
    	datatype: "json",
        datafields: datafields,
        url: url,
        pagenum: 0,
        pagesize: 100
	   
    };
    var dataAdapter = new $.jqx.dataAdapter(source);
 	// renderer for grid cells. 
   var numberrenderer = function (row, column, value) {
        return '<div style="text-align: center; margin-top: 5px;">' + (1 + value) + '</div>';
    };
    var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
		return '<div id="timeLeftFirst_'+rowdata.id +'" class="timeLeft" style="width:100%;padding-top: 10px;text-align: center;">'+rowdata.tgConLai+'</div>';
    	//return '<div id="timeLeftFirst_'+rowdata.id +'">'+convertDateToString(rowdata.deadline)+'</div>';
    	//return '<div id="timeLeftFirst_'+rowdata.id +'"></div>';
    };
	var cellsrenderer48H = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
		return '<div id="timeTelecom48h_'+rowdata.id +'" class="timeLeft" style="width:100%;padding-top: 10px;text-align: center;">'+rowdata.fbDVT48hour+'</div>';
	};
	var cellsrenderer5day = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
		return '<div id="timeTelecom5day_'+rowdata.id +'" class="timeLeft" style="width:100%;padding-top: 10px;text-align: center;">'+rowdata.fbDVT5day+'</div>';
	};
	var cellsrendererSTB = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
			if (rowdata.vipCode!= '0'){
				return '<div style="width:100%;padding-top: 10px;"><a href="checkedList.htm?checkedList='+rowdata.id+'" class="timeLeft" >'+rowdata.subscribers+'</a></div>';
			}
			else
			{
				return '<div style="width:100%;padding-top: 10px;"><a href="checkedList.htm?checkedList='+rowdata.id+'"  >'+rowdata.subscribers+'</a></div>';
			}
	};
	var cellsrendererFeebacked = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
		if (rowdata.isFeedbacked == 'Y'){
			return '<div style="width:100%;text-align: center;padding-top: 10px;"><input type="checkbox" name="is_feedbacked" value="'+rowdata.isFeedbacked+'" checked="checked"/></div>';
		}
		else
		{
			return '<div style="width:100%;text-align: center;padding-top: 10px;"><input type="checkbox" name="is_feedbacked" value="'+rowdata.isFeedbacked+'"/></div>';
			
		}
	};
	var cellsrendererSendTelecom = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
		
		if (rowdata.fbSendTelecom == 'Y'){
			return '<div style="width:100%;text-align: center;padding-top: 10px;"><input  type="checkbox" name="fb_send_telecom" value="'+rowdata.fbSendTelecom+'" checked="checked"/></div>';
		}
		else
		{
			return '<div style="width:100%;text-align: center;padding-top: 10px;"><input type="checkbox" name="fb_send_telecom" value="'+rowdata.fbSendTelecom+'"/></div>';
			
		}
	};
    //call funtion add column	
   	${column}
 
    // filter
    $("#jqxgrid").jqxGrid(
    {
    	width: '100%',
    	height: 600,
    	autorowheight: true,
       	source: dataAdapter,
        theme: theme,
        showfilterrow: true,
        filterable: true,
        sortable: true,
        pageable: true,
        altrows: true,
        columnsresize: true,
        columnsreorder: true,
        scrollmode: 'deferred',
        pagesizeoptions: ['50', '100', '200', '300', '400', '500'],
        selectionmode: 'checkbox',
        columns: column
    });
  //dropdownlist an hien cot
    //call funtion add listSource	
   	${listSource}
   	
    $("#jqxlistbox").jqxDropDownList({ checkboxes: true, source: listSource, width: 15, height: 15, theme: theme, dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });
   // $("#jqxlistbox").jqxDropDownList('checkIndex', 0);
    // subscribe to the checkChange event.
    $("#jqxlistbox").on('checkChange', function (event) {
    	if (event.args) {
            var item = event.args.item;
            if (item) {
               if (item.checked==true)
                {
                	$("#jqxgrid").jqxGrid('showcolumn', item.value);                      
            	}
               else
                {
         	   		$("#jqxgrid").jqxGrid('hidecolumn', item.value);
                }
        	}
    	}
    });
  
	//button export data
	var exportFileName =  "<c:out value='${exportFileName}'/>";
	// create context menu
	var contextMenu = $("#Menu").jqxMenu({ width: 200, autoOpenPopup: false, mode: 'popup', theme: theme });
	$("#jqxgrid").on('contextmenu', function () {
	    return false;
	});
	$("#jqxgrid").on('rowclick', function (event) {
	    if (event.args.rightclick) {
	        $("#jqxgrid").jqxGrid('selectrow', event.args.rowindex);
	        var scrollTop = $(window).scrollTop();
	        var scrollLeft = $(window).scrollLeft();
	        contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);
	        return false;
	    }
	});
	
	  // handle context menu clicks.
    $("#Menu").on('itemclick', function (event) {
        var args = event.args;
        
        if ($.trim($(args).text()) == '<fmt:message key="global.button.detailFB"/>') {
       	  	var rowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
            var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
        	window.location = 'checkedList.htm?checkedList='+row.id;   
        }
        if ($.trim($(args).text()) == '<fmt:message key="button.xuLyFBTheoLo"/>') {
        	var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
    		var idList="";
    		var cond="";
    		//alert(selectedrowindexes);
    		//var rowIndexList = selectedrowindexes.split(",");
    		if (selectedrowindexes != null) {
    			for (var i=0; i<selectedrowindexes.length; i++) {
    				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
    				idList+=cond+row.id;
    				cond=",";
    			}
    			// alert(idList);
    			window.location = 'checkedList.htm?checkedList='+idList; 
    		}
        	  
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteFB"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		//alert(selectedrowindexes);
        		//var rowIndexList = selectedrowindexes.split(",");
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        				idList+=cond+row.id;
        				cond=",";
        			}
        			// alert(idList);
        			window.location = 'delete.htm?idList='+idList;
        		}
        		
        	}
          
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.LaDiemDen"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.LaDiemDen"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		//alert(selectedrowindexes);
        		//var rowIndexList = selectedrowindexes.split(",");
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        				idList+=cond+row.id;
        				cond=",";
        			}
        			// alert(idList);
        			window.location = 'checkDiemDen.htm?idList='+idList+'&status=Y';
        		}
        		
        	}
          
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.KhongLaDiemDen"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.KhongLaDiemDen"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		//alert(selectedrowindexes);
        		//var rowIndexList = selectedrowindexes.split(",");
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        				idList+=cond+row.id;
        				cond=",";
        			}
        			// alert(idList);
        			window.location = 'checkDiemDen.htm?idList='+idList+'&status=N';
        		}
        		
        	}
          
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.removeFB"/>')  {
        	var rowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
            var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
            //hiển thị popup nội dung chuyển FB
         	var id=row.id;
      		var subscribersMove=row.subscribers;
      		var networkTypeMove=row.networkType;
      		var fbContentMove=row.fbContent;
      		var responsMove=row.responseContent;
      		var fbTypeMove=row.fbType;
      		var deptProcessMove=row.deptProcess;
      		var teamMove=row.team;
      		$("#jqxwindow").jqxWindow('open');	
        	$("#id").text(id);
			$("#subscribersMove").text(subscribersMove);
			$("#networkTypeMove").text(networkTypeMove);
			$("#fbContentMove").text(fbContentMove);
			$("#responsMove").text(responsMove);
			$("#fbTypeMove").val(fbTypeMove);
			$("#deptProcessMove").val(deptProcessMove);
			$("#teamMove").val(teamMove);
			
        } 
		if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
			window.open('${pageContext.request.contextPath}/feedback/general-feedback/export.htm?fbcode='+"<c:out value='${fbcode}'/>"+'&fbtype='+"<c:out value='${loaiPACBB}'/>"+
    	 			'&networkType='+"<c:out value='${loaiMangCBB}'/>"+
    	 			'&thoiGianPAFrom='+"<c:out value='${thoiGianPAFrom}'/>"+
    	 			'&thoiGianPATo='+"<c:out value='${thoiGianPATo}'/>"+
    	 			'&subscribers='+"<c:out value='${subscribers}'/>"+
    	 			'&subscriberType='+"<c:out value='${loaiTBCBB}'/>"+
    	 			'&thoiGianXLFrom='+"<c:out value='${thoiGianXLFrom}'/>"+
    	 			'&thoiGianXLTo='+"<c:out value='${thoiGianXLTo}'/>"+
    	 			'&deptProcess='+"<c:out value='${deptProcessCBB}'/>"+
    	 			'&team='+"<c:out value='${teamCBB}'/>"+
    	 			'&status='+"<c:out value='${status}'/>"+
    	 			'&province='+"<c:out value='${tinhThanhCBB}'/>"+
    	 			'&district='+"<c:out value='${quanHuyenCBB}'/>"+
    	 			'&wards='+"<c:out value='${phuongXaCBB}'/>"+
    	 			'&vipCode='+"<c:out value='${vipCBB}'/>"+
    	 			'&fbIbc='+"<c:out value='${fbIbcCBB}'/>"+
    	 			'&fbSendTelecom='+"<c:out value='${fbSendTelecom}'/>"+
    	 			'&function='+"<c:out value='${function}'/>"
    			 ,'_blank');
        }
    });
        
});

//convert thanh ngay gio:phut:giay
function convertDateToString(time){
	var d = new Date();
	var timestam = (time-d)/1000;
	var day=0;
	var hour=0;
	var minute=0;
	var second=0;
	var rex='';
	if (timestam < 0)
	{
		rex='-';
		timestam = (-1)*timestam;
	}
	
	day = parseInt(timestam/86400);
	timestam=timestam%86400;
	hour = parseInt(timestam/3600);
	timestam=timestam%3600;
	minute = parseInt(timestam/60);
	second= parseInt(timestam%60);
	rs= rex + day+' '+hour+':'+minute+':'+second
	return rs;
}
</script>

