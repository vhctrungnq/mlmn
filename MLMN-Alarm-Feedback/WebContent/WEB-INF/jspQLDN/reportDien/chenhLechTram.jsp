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
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div class="clear"></div>
<div class="ui-tabs-panel">

<form:form commandName="filter" method="GET" action="chenh-lech_tram.htm">
<table >
		
			<tr> 
				
				<td><fmt:message key="qldnTramTTDien.tgttTq"/></td>
				<td style="width:130px">
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
				<c:if test="${isRolaManager=='Y'}">
					<td style="padding-left: 10px;"><fmt:message key="QldnThongTinTram.daiVT"/></td>
					<td >
						<input  id="daiVT" name ="daiVT" value="${daiVT}"  />
					</td>
				</c:if>
				
				<td style="padding-left: 10px;">
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
            	<li><fmt:message key="global.button.daiVTXuLy"/></li>
            	<li><fmt:message key="global.button.BieuDo"/></li>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
		   		
	        </ul>
</div>
<div class="clear"></div>
<div id="window">
<div>ĐVT cập nhật cách thức xử lý</div>
		<div>
		<table class="simple2">
			  <tr>
		        <td>Nguyên nhân</td>
		           <td>
		           		<input type="hidden" id="idTram" name ="idTram""/>
		      			<div id='nguyenNhan' style = "width: 100%"></div>
			    	</td>
			  </tr>
			  <tr>
		         
		    	<td>ĐVT xử lý</td>
	            <td > 
	          		 <textarea  id="kqToKt" rows="4" cols="38">  </textarea> 
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
<div class="clear"></div>

<div id="windowChart">
	<div>Biểu đồ biến động điện năng</div>
	<div id="chart" style="width:100%; height:500px"></div>
 </div> 
<script type="text/javascript">
${gridReport}
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
    //Input province
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
	
	 var theme =  getTheme();
	// create jqxWindow.
	$("#window").jqxWindow({ resizable: true, theme: theme, autoOpen: false, minWidth: 500, maxWidth: 800, minHeight: 200,isModal: true});
	$("#windowChart").jqxWindow({ resizable: true, theme: theme, autoOpen: false, minWidth: 1000, maxWidth: 1500, minHeight: 200,isModal: true});

	//handle context menu 
	$("#menuReport").on('itemclick', function (event) {
		    var args = event.args;
		    var exportFileName =  "<c:out value='${exportFileName}'/>";
		    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
		    	$("#gridReport").jqxGrid('exportdata', 'xls', exportFileName);
		    }
		    if ($.trim($(args).text()) == '<fmt:message key="global.button.BieuDo"/>')  {
		    	var startM= $("#thangQuyTtF").val();
		    	var startY= $("#namTtF").val();
		    	var endM= $("#thangQuyTtTo").val();
		    	var endY= $("#namTtTo").val();
		    	var sampleData = [];
		    	var series = [];
		    	var minValue=0;
		    	var maxValue=0;
		    	var interval=10;
		    	var first=true;
		    	var selectedrowindexes = $("#gridReport").jqxGrid('getselectedrowindexes'); 
		    	while ((startY<endY)||(startY==endY&&startM<=endM))
				{
		    		var objData={};
		    		
		    		objData["Day"]='T'+startM+'_'+startY; 
		    		
		    		if (selectedrowindexes != null) {
		    			for (var i=0; i<selectedrowindexes.length; i++) {
		    				var row = $("#gridReport").jqxGrid('getrowdata', selectedrowindexes[i]);
		    				var key = row.ID_TRAM;
		    				objData[""+key+""] = $("#gridReport").jqxGrid('getcellvalue', row, 'T'+startM+'_'+startY);
		    				if (first==true)
	    					{
		    					var objSer={};
			    				objSer.dataField=key;
			    				objSer.displayText=key;
			    				series.push(objSer);
	    					}
		    				if (objData[""+key+""]>maxValue)
		    				{
		    					maxValue=objData[""+key+""];
		    				}
		    				if (objData[""+key+""]<minValue)
	    					{
		    					minValue=objData[""+key+""];
	    					}
		    			}
		    		}
		    		first= false;
		    		sampleData.push(objData);
		    	//	alert(objData);
					if (startM==12)
					{   
						startM=1;
						startY++;
					}
					else
						startM++;
				}
		    	interval = (maxValue-minValue)/10;
		    	//alert(series);
		    	// prepare jqxChart settings
            	var settings = {
                	title: "Biến động điện năng của trạm giữa các tháng",
                	description: "",
	                enableAnimations: true,
	                showLegend: true,
	                padding: { left: 10, top: 10, right: 15, bottom: 10 },
	                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	                source: sampleData,
	                colorScheme: 'scheme01',
	                categoryAxis: {
	                    dataField: 'Day',
	                    unitInterval: 1,
	                    tickMarks: { visible: true, interval: 1 },
	                    gridLinesInterval: { visible: true, interval: 1 },
	                    valuesOnTicks: false,
	                    padding: { bottom: 10 }
	                },
	                
	                seriesGroups:
	                    [
	                        {
	                            type: 'line',
	                            valueAxis: {
	        	                    unitInterval: interval,
	        	                    minValue: minValue,
	        	                    maxValue: maxValue,
	        	                    title: { text: 'Điện năng' },
	        	                    labels: { horizontalAlignment: 'right' }
	        	                },
	                            series:series
	                        }
	                    ]
            	};
            	// setup the chart
	           
	            $("#windowChart").jqxWindow('open');
	            $("#chart").jqxChart(settings);
		    }
		    
		    else if ($.trim($(args).text()) == '<fmt:message key="global.button.daiVTXuLy"/>')
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
		        
		        var selectedrowindexes = $('#gridReport').jqxGrid('getselectedrowindexes'); 
	    		var idList="";
	    		var cond="";
	    		//alert(selectedrowindexes);
	    		//var rowIndexList = selectedrowindexes.split(",");
	    		if (selectedrowindexes != null) {
	    			for (var i=0; i<selectedrowindexes.length; i++) {
	    				var row = $("#gridReport").jqxGrid('getrowdata', selectedrowindexes[i]);
	    				idList+=cond+row.ID_TRAM;
	    				cond=",";
	    			}
	    		}
	    		 $('#idTram').val(idList);
	    		 $('#kqToKt').text(row.KQ_TO_KT);
		        var nguyenNhan =  row.NGUYEN_NHAN;
		        if(nguyenNhan=="")
					$('#nguyenNhan').val('ALL');
				else
					$('#nguyenNhan').val(nguyenNhan); 
		        
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
			$.getJSON("${pageContext.request.contextPath}/dien/report/daiVTXuLy.htm",{idTram:$("#idTramList").val(),tgttTq:$("#tgttTq").val(),thangQuyTt:$("#thangQuyTtTo").val(),namTt:$("#namTtTo").val(),nguyenNhan:$("#nguyenNhan").val(),kqToKt:$("#kqToKt").text()}, function(j){
		    		
				 if (j==1)
					 {
					 	alert("Đài VT cập nhật thông tin xử lý thành công!");
					 	 $("#window").jqxWindow('close');
					 }
				 else
					 {
					 	alert("Đài VT cập nhật thông tin xử lý không thành công!");
					 }
	    	   });
		}
	   
	});
});





</script>
