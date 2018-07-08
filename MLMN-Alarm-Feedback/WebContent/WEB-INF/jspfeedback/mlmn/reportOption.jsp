<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtreegrid.js"></script>   --%>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div class="ui-tabs-panel">
	<form:form commandName="filter" method="post" action="${function}.htm">
		<table >
			<tr> 
				 <c:choose>
	                <c:when test="${function== 'week'}">
	                   	<td style="width:50px">Tuần</td>
						<td style="width:150px">
								<input value="${sweek}" name="sweek" id="sweek" size="2">
								<img alt="calendar" titleKey="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		            			&nbsp;Năm <input value="${syear}" name="syear" id="syear" size="4" maxlength="4">
		            	</td>
						<%-- <td style="width:70px">Đến tuần</td>
						<td style="width:130px">
								<input value="${eweek}" name="eweek" id="eweek" size="2">
								<img alt="calendar" titleKey="Click to choose the start week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		            			&nbsp;Năm <input value="${eyear}" name="eyear" id="eyear" size="4" maxlength="4">
						</td> --%>
	                </c:when>
	                 <c:when test="${function== 'month'}">
	                   	<td style="width:50px">Tháng</td>
						<td style="width:150px">
							<select name="smonth" id="smonth" onchange="xl()">
		            				<c:forEach var="month" items="${monthList}">
							              <c:choose>
							                <c:when test="${month == smonth}">
							                    <option value="${month}" selected="selected">${month}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${month}">${month}</option>
							                </c:otherwise>
							              </c:choose>
							    </c:forEach>
				              </select>
				             &nbsp;Năm <input value="${syear}" name="syear" id="syear" size="4" maxlength="4">
				         </td>
						<%-- <td style="width:70px">Đến tháng</td>
						<td style="width:130px">
							<select name="emonth" id="emonth" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == emonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
							    </c:forEach>
				              </select>
				             &nbsp;Năm <input value="${eyear}" name="eyear" id="eyear" size="4" maxlength="4">
						</td> --%>
	                </c:when>
	                 <c:when test="${function== 'quarter'}">
	                   	<td style="width:50px">Quý</td>
						<td style="width:150px">
							<select name="smonth" id="smonth" onchange="xl()">
		            				<c:forEach var="month" items="${quarterList}">
							              <c:choose>
							                <c:when test="${month == smonth}">
							                    <option value="${month}" selected="selected">${month}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${month}">${month}</option>
							                </c:otherwise>
							              </c:choose>
							    </c:forEach>
				              </select>
				             &nbsp;Năm <input value="${syear}" name="syear" id="syear" size="4" maxlength="4">
				         </td>
						<%-- <td style="width:70px">Đến quý </td>
						<td style="width:130px">
							<select name="emonth" id="emonth" onchange="xl()">
	            				<c:forEach var="month" items="${quarterList}">
						              <c:choose>
						                <c:when test="${month == emonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
							    </c:forEach>
				              </select>
				             &nbsp;Năm <input value="${eyear}" name="eyear" id="eyear" size="4" maxlength="4">
						</td> --%>
	                </c:when>
	                <c:when test="${function== 'year'}">
	                   	<td style="width:50px">Năm</td>
						<td style="width:90px">
							<input type ="text"  value="${syear}" name="syear" id="syear" size="10" maxlength="10" style="width:70px">
					 	</td>
						<%-- <td style="width:70px">Đến năm</td>
						<td style="width:90px">
							<input type ="text"  value="${eyear}" name="eyear" id="eyear" size="10" maxlength="10" style="width:70px">
					   	</td> --%>
	                </c:when>
	              <c:when test="${function== 'luy-ke'}">
						<td>
								<input type ="hidden"  name="edate" id="edate" size="10" maxlength="10" style="width:70px">
						</td>
						<td style="width:70px">Đến ngày</td>
						<td style="width:90px">
								<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="10" maxlength="10" style="width:70px">
					   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>
	                </c:when>
	                 <c:when test="${function== 'dy'}">
						<td style="width:50px">Ngày</td>
						<td style="width:90px">
								<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="10" maxlength="10" style="width:70px">
					   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>
	                </c:when>
	                <c:otherwise>
	                    <td style="width:50px">Từ Ngày</td>
						<td style="width:90px">
								<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="10" maxlength="10" style="width:70px">
					   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>
						<td style="width:70px">Đến ngày</td>
						<td style="width:90px">
								<input type ="text"  value="${edate}" name="edate" id="edate" size="10" maxlength="10" style="width:70px">
					   			 <img alt="calendar" title="Click to choose the from date" id="chooseEDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>
	                </c:otherwise>
	             </c:choose>
				
				<td>Khu vực</td>
				<td>
			        <div id='region'></div>
		        </td>
				<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.province"/></td>
				<td>
					 <div id='province'></div>
				</td>
				<td><fmt:message key="baoCaoFeedback.baoCaoTheoNgay.loaiBaoCao"/></td>
				<td>
			        <div id='fbType'></div>
		        </td>
		        <td><spring:message code="qLThongTinPhanAnh.trangThai"/></td>
				<td>
			        <div id='status'></div>
		        </td>
				<td style="padding-left: 5px;">
					<input class="button" type="submit" id="button" value="<fmt:message key="global.form.timkiem"/>" />
					<input class="button" type="button" id="btExport" value="Report" />
				</td>
			</tr>
			
		</table>
	</form:form>
</div>
<c:forEach  items="${blockList}"  var="block">
	<br/>
	<h3>${block.blockName}</h3>
	<div id="${block.blockId}"></div>
	<div id="grid${block.blockId}"></div>
	<div id='menu${block.blockId}'>
       <ul>
   		<li><fmt:message key="global.button.exportExcel"/></li>
       </ul>
	</div>
	<div class="clear"></div>
</c:forEach>

<div id="jqxwindowGUI_MAIL">
			<div><b>Báo cáo Feedback qua email</b></div>
			<div>
			<table class="simple2">
				<tr>
					<td style="width:140px;text-align:left; padding-left:10px">Người nhận</td>
					<td style="width:150px;">
						<div id="sendToCb" align="left"></div>
					</td>
					<td><input type="text" name="e_email" id="e_email"  value="${email}"/></td>
				</tr>
				<tr>
					<td style="width:140px;text-align:left; padding-left:10px">Tiêu đề mail:</td>
					<td colspan="2"><input type="text" name="subject" id="subject"  value="${subject}"/></td>
				</tr>
				<tr>
					<td style="width:140px;text-align:left; padding-left:10px">Đề xuất & Kiến nghị</td>
					<td colspan="2">
						<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
						<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
						<textarea style="width:100%; height: 220px" name="e_content" id="e_content" maxlength="900" >${e_content}</textarea>
						<script type="text/javascript">
							CKEDITOR.replace( 'e_content',
							{
								toolbar :
									[
										['NewPage','-','Undo','Redo'],
										['Find','Replace','-','SelectAll','RemoveFormat'],
										['Link', 'Unlink', 'Image'],
										['FontSize', 'Bold', 'Italic','Underline'],
										['NumberedList','BulletedList','-','Blockquote'],
										['TextColor', '-', 'Smiley','SpecialChar', '-', 'Maximize']
									]
							});
					  	</script>
					</td>
				</tr>
				
				<tr>
					<td></td>
					<td style="text-align: left">
						<input type="button" id="btnEmailSubmit" value="Gửi" class="button">
					</td>
					<td>
						<span style="display:none;" id="statusSumit"><img height="10px" src="${pageContext.request.contextPath}/images/loading.gif"></span>
					</td>
				</tr>
	       </table>
	     </div>
	</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
var func = '<c:out value='${funct}'/>'
 if (func=='dy' || func=='luy-ke'|| func=='option' ){
	Calendar.setup({
	    inputField		:	"sdate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseSDate",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
 }
 if ( func=='luy-ke'|| func=='option' ){
	Calendar.setup({
	    inputField		:	"edate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseEDate",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	});
} 
else if (func=='week')
{
	Calendar.setup({
	    inputField		:	"sweek",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseStartWeek",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
	/* Calendar.setup({
	    inputField		:	"eweek",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseEndWeek",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	}); */
}

</script>
<script type="text/javascript">
$(document).ready(function(){
	$("#btExport").click(function () {
		var blockID= '<c:out value='${block.blockId}'/>';
    	var blockSQL= '<c:out value='${block.sqlWhere}'/>';
    	
    	window.open('${pageContext.request.contextPath}/report-feedback/export.htm?sdate='+"<c:out value='${sdate}'/>"+
	 			'&edate='+"<c:out value='${edate}'/>"+
	 			'&sweek='+"<c:out value='${sweek}'/>"+
	 			'&smonth='+"<c:out value='${smonth}'/>"+
	 			'&syear='+"<c:out value='${syear}'/>"+
	 			'&region='+"<c:out value='${region}'/>"+
	 			'&province='+"<c:out value='${province}'/>"+
	 			'&fbType='+"<c:out value='${fbType}'/>"+
	 			'&status='+"<c:out value='${status}'/>"+
	 			'&sqlWhere='+"<c:out value='${sqlWhere}'/>"+
	 			'&function='+"<c:out value='${function}'/>"+
	 			'&blockID='+blockID+
	 			'&blockSQL='+blockSQL
			 ,'_blank'); 
	});
	var theme =  getTheme();
		<c:forEach  items="${gridList}"  var="grid">
			${grid}
		</c:forEach>	
		//handle context menu 
		<c:forEach  items="${blockList}"  var="block">
			$('#menu'+ '<c:out value='${block.blockId}'/>').on('itemclick', function (event) {
				
			    var args = event.args;
			    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
			    	 $('#grid'+ '<c:out value='${block.blockId}'/>').jqxGrid('exportdata', 'xls', '<c:out value='${block.blockId}'/>'); 
			    	
			    }
			});
		</c:forEach>	
		
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
		$("#region").jqxDropDownList({source: dataAdapterregion, displayMember: "value", valueMember: "name",checkboxes: true, width: 120, autoDropDownHeight: true, theme: theme,enableHover: true });           
		
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
		var region = $("#region").val();
		//combobox province
		var urlprovince = "${pageContext.request.contextPath}/ajax/getProvinceList.htm?region="+region;
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
		$("#province").jqxDropDownList({source: dataAdapterprovince, displayMember: "province", valueMember: "province",checkboxes: true, width: 120, autoDropDownHeight: true, theme: theme,enableHover: true });           
		
		var cbprovince = '<c:out value="${province}"/>';
		// alert(dept);
		if(cbprovince=="")
			$('#province').val('Choose');
		else
		{
			var provinceVar = cbprovince.split(",");
			if (provinceVar != null) {
				for (var i=0; i<provinceVar.length; i++) {
					$("#province").jqxDropDownList('checkItem', provinceVar[i] ); 
				}
			}
		}  
		
		//combobox fbType
		var urlfbType = "${pageContext.request.contextPath}/ajax/getFBTypeList.htm";
		var sourceFbType =
		{
		   datatype: "json",
		   url: urlfbType,
		   datafields: [
		                 { name: 'code'},
		                 { name: 'name'}
		             ],
		    async: false
		};
		var dataAdapterFbType = new $.jqx.dataAdapter(sourceFbType);
		$("#fbType").jqxDropDownList({source: dataAdapterFbType, displayMember: "name", valueMember: "code",checkboxes: true, width: 170, autoDropDownHeight: true,height: '25', theme: theme,enableHover: true });           
		
		var cbfbType = '<c:out value="${fbType}"/>';
		// alert(dept);
		if(cbfbType=="")
			$('#fbType').val('Choose');
		else
		{
			var fbTypeVar = cbfbType.split(",");
			if (fbTypeVar != null) {
				for (var i=0; i<fbTypeVar.length; i++) {
					$("#fbType").jqxDropDownList('checkItem', fbTypeVar[i] ); 
				}
			}
		}  
		
		
		//combobox fb status
		var urlFbStatus = "${pageContext.request.contextPath}/ajax/getStatusFBList.htm";
		var sourceFbStatus =
		{
		   datatype: "json",
		   url: urlFbStatus,
		   datafields: [
		                 { name: 'code'},
		                 { name: 'name'}
		             ],
		    async: false
		};
		var dataAdapterFbStatus = new $.jqx.dataAdapter(sourceFbStatus);
		$("#status").jqxDropDownList({source: dataAdapterFbStatus, displayMember: "name", valueMember: "code", width: 120, autoDropDownHeight: true, theme: theme,enableHover: true });           
		
		var cbstatus = '<c:out value="${status}"/>';
		// alert(dept);
		if(cbstatus=="")
			$('#status').val('Choose');
		else
		{
			$('#status').val(cbstatus);
		}  
		

		// gui email
		 $("#subject").jqxInput({ height: 20, width: '100%', theme: theme});
		////Create a jqxDropDownList user to send mail
			var renderer = function (itemValue, inputValue) {
		        var terms = inputValue.split(/,\s*/);
		        // remove the current input
		        terms.pop();
		        // add the selected item
		        terms.push(itemValue);
		        // add placeholder to get the comma-and-space at the end
		        terms.push("");
		        var value = terms.join(",");
		        return value;
		    };
		   ${emailList}
		    $("#e_email").jqxInput({ height: 20, width: '100%', theme: theme,
		        source: function (query, response) {
		            var item = query.split(/,\s*/).pop();
		            // update the search query.
		            $("#e_email").jqxInput({ query: item });
		            response(emailList);
		        },
		        renderer: renderer
		    });
		    
		  //Create a jqxDropDownList
		    var urlGroupEmail = "${pageContext.request.contextPath}/ajax/getGroupEmail.htm";
		    // prepare the data
		    var sourceGroupEmail =
		    {
		        datatype: "json",
		        url: urlGroupEmail,
		        datafields: [
		                     { name: 'groupName'},
		                     { name: 'groupUser'}
		                 ],
		        async: false
		    };
		    var dataAdapterGroupEmail = new $.jqx.dataAdapter(sourceGroupEmail);
		    
		    $("#sendToCb").jqxComboBox({ source: dataAdapterGroupEmail,displayMember: "groupName", valueMember: "groupUser", height: 20, width: '100%', theme: theme ,autoDropDownHeight: true });
		    $('#sendToCb').on('select', function (event) 
		  		{
		  			var itemB= $('#e_email').val();
		  			var item = $("#sendToCb").jqxComboBox('getSelectedItem');
		  			
		  			if (itemB!='')
		  				{
		  					if (itemB.indexOf(item.value)==-1)
		  						{itemB+=","+item.value ;}
		  				}
		  			else
		  				{
		  					itemB=item.value ;
		  				}
		  			$("#e_email").val(itemB);
		  			//alert(itemB);
		  		});
		    var region = '<c:out value="${region}"/>';
		    var sdate = '<c:out value="${sdate}"/>';
		    var edate = '<c:out value="${edate}"/>';
		    var sweek = '<c:out value="${sweek}"/>';
		    var smonth = '<c:out value="${smonth}"/>';
		    var syear = '<c:out value="${syear}"/>';
		    var province = '<c:out value="${province}"/>';
		    var fbType = '<c:out value="${fbType}"/>';
		    var status = '<c:out value="${status}"/>';
		    var sqlWhere = '<c:out value="${sqlWhere}"/>';
		    var func = '<c:out value="${function}"/>';
		    var titlePage = '<c:out value="${title}"/>';
		    $('#btnEmailSubmit').jqxButton({ theme: theme });
		    $('#btnEmailSubmit').click(function () {
		    		//alert(content);
		    		var content	= CKEDITOR.instances['e_content'].getData();
		    		var email	= $('#e_email').val();
		    		var subject	= $('#subject').val();
		    		$.ajax({
		    			type: "POST",
		    			url: "${pageContext.request.contextPath}/report-feedback/sendMail.htm",
		    			data:{ 
		    				//tham so thong tin gui mail
		    				content: encodeURI(content), 
		    				subject: encodeURI(subject),
		    				email: encodeURI(email),
		    				titlePage: encodeURI(titlePage),
		    				//tham so tim kiem theo trang
		    				sdate: encodeURI(sdate),
		    				edate: encodeURI(edate),
		    				sweek: encodeURI(sweek),
		    				smonth: encodeURI(smonth),
		    				syear: encodeURI(syear),
		    				region: encodeURI(region),
		    				province: encodeURI(province),
		    				fbType: encodeURI(fbType),
		    				status: encodeURI(status),
		    				sqlWhere: encodeURI(sqlWhere),
		    				func: encodeURI(func)
		    			},
		    			error: function(){
		    				$('#statusSumit').css("display","none");
		    				alert('Quá trình tải dữ liệu bị lỗi. Vui lòng kiểm tra mật khẩu');
		    			},
		    			beforeSend: function(){
		    				//toggleStatusMessage('Đang tải dữ liệu..');
		    				$('#statusSumit').css("display","block");
		    			},
		    			
		    			complete: function(){},
		    			
		    			success: function(data){
		    				$('#statusSumit').css("display","none");
		    				
		    				if (data.value == '1') {
		    					alert('Cập nhật thông tin gửi email thành công. Vui lòng chờ trong ít phút.');
		    			//		$("#formEmail").dialog('close');
		    				} else {
		    					alert('Xảy ra lỗi trong quá trình gửi email');
		    				}
		    			}
		    		});
		    		
		    });
		    
		
		
});
</script>
