<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">
    #doublescroll { overflow: auto; overflow-y: hidden; }
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
    .textrt{
    	text-align: right;
    }
    .table-layout{
    	width:100%;
    	border-style:solid;
		border-width:1px;
		border-color: #B9D3EE;
    }
    .heare-1
    {
    	text-align: center;
    	background: #DDDDDD;
    	font-size: 17px;
    	color: #CC3300;
    	border-style:solid;
    	border-color: #B9D3EE;
    	border-width:1px;
    	text-shadow: 1px;
    	padding-bottom: 1px;
    	font-family:"Times New Roman", Times, serif;
    }
</style>
<%
        // you can do this as a scriptlet on the page, but i put it into a taglib...
        org.displaytag.decorator.MultilevelTotalTableDecorator subtotals = new org.displaytag.decorator.MultilevelTotalTableDecorator();
        //subtotals.setGrandTotalDescription("&nbsp;");    
        // optional, defaults to Grand Total
       // subtotals.setSubtotalLabel("&nbsp;", null);
        pageContext.getRequest().setAttribute("subtotaler", subtotals);
%>

<title>Trang chủ</title>
<content tag="heading"><center>BÁO CÁO MẠNG LƯỚI NGÀY ${day}</center></content>
	<form method="post" action="welcome.htm" enctype="multipart/form-data">
	<div id="reportEmail">
		<input type="hidden" id="action" name="action">
		<table style="width:100%;" class="form">
			<tr>
				<td align="left" style="width:120px;">
			        Ngày
			   		<input type ="text"  value="${day}" name="day" id="day" size="10" maxlength="10" style="width:70px">
			   		<img alt="calendar" title="Click to choose the from date" id="chooseday" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				 </td>
			    <td>
	            	<input type="submit" class="button" onclick="setAction('filter')" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	
<!-- THANG PX -->
<table class = "table-layout">
	<tr>
		<td class = "heare-1">
			<b>ACCESS NETWORK 2G</b>
		</td>
	</tr>
	<tr>
		<td>
		<div class = "tableStandar">
			<display:table name="${vRpDySummary2G}" id="vRpDySummary2G" requestURI="" pagesize="100" export="true">
					<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true" headerClass="master-header-1" />
				    <display:column property="team" title ="System" sortable="true" headerClass="master-header-1" class="styleTime"/>
				    <display:column property="sites"  title="Sites" sortable="true" headerClass="master-header-3"  class="textrt"/>
				    <display:column property="cells"  title="Cells" sortable="true" headerClass="master-header-3"  class="textrt"/>
				    <display:column property="numTrx" title="Trxs" sortable="true" headerClass="master-header-3"  class="textrt" />
				    <display:column property="tTraf" titleKey="T_TRAF" sortable="true" headerClass="master-header-3"  class="textrt"/>
				    <display:column property="tTrafh" titleKey="T_TRAFH" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="haftRatePercent" titleKey="HAFTRATE_PERCENT" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="tDrpr" titleKey="T_DRPR" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="tBlkr" titleKey="T_BLKR" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="sBlkr" titleKey="S_BLKR" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="cssr" titleKey="CSSR" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="edgeDlData" titleKey="GPRS DL Data (MB)" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="edgeUlData" titleKey="GPRS UL Data (MB)" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="gprsDlData" titleKey="EGPRS DL Data (MB)" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="gprsUlData" titleKey="EGPRS UL Data (MB)" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="totalData2g" titleKey="Total Data 2G(MB)" sortable="true" headerClass="master-header-3" class="textrt"/>
			</display:table>
		</div>
			
			
		</td>
	</tr>
	<tr>
		<td class = "heare-1">
			<b>ACCESS NETWORK 3G</b>
		</td>
	</tr>
	<tr>
		<td>
		<div class = "tableStandar">
			<display:table name="${vRpDySummary3G}" id="vRpDySummary3G" requestURI="" pagesize="100"  export="true">
					<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true" headerClass="master-header-1"/>
				    <display:column property="rnc" title ="System" sortable="true" headerClass="master-header-1" class="styleTime"/>
				    <display:column property="nodeb"  titleKey="NodeBs" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="ucell"  titleKey="UCells" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="vpErl" titleKey="VP_ERL" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="amrErl" titleKey="AMR_ERL" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="vpCssr" titleKey="VP_CSSR" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="voiceCssr" titleKey="VOICE_CSSR" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="csDropRate" titleKey="CS_DROP_RATE" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property ="psR99DropRate" titleKey="PS_R99_DROP_RATE" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="psHsdpaDropRate" titleKey="PS_HSDPA_DROP_RATE" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="psR99DlData" titleKey="PS_R99_DL_DATA" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property ="hsdpaDlData" titleKey="HSDPA_DL_DATA" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="hsdpaDlThrouthput" titleKey="HSDPA_DL_THROUTHPUT" sortable="true" headerClass="master-header-3" class="textrt"/>
				    <display:column property="psMbmsDlThrouthput" titleKey="PS_MBMS_DL_THROUTHPUT" sortable="true" headerClass="master-header-3" class="textrt"/>
			</display:table>
			</div>
		</td>
	</tr>
	<tr>
		<td class = "heare-1">
			<b>CORE NETWORK CS</b>
		</td>
	</tr>
	<tr>
		<td>
		<div class = "tableStandar">
			<display:table name="${vRpDySummaryCoreCs}" id="vRpDySummaryCoreCs" requestURI="" pagesize="100"  export="true">
					<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true" headerClass="master-header-1"/>
				    <display:column property="mscid" title="System" sortable="true" headerClass="master-header-1"/>
					<display:column property ="callSetUp"  titleKey="Call Set-up Success (%)"  sortable="true" headerClass="master-header-3" class="textrt"/>
					<display:column property ="process"  titleKey="Process (%)"  sortable="true" headerClass="master-header-3" class="textrt"/>
					<display:column property ="luRegSucc"  titleKey="Location Update Success (%)"  sortable="true" headerClass="master-header-3" class="textrt"/>
					<display:column property ="piSuccAuth"  titleKey="Authentication (%)"  sortable="true" headerClass="master-header-3" class="textrt"/>
					<display:column property ="cirsucr"  titleKey="CIRSucR(%)"  sortable="true" headerClass="master-header-3" class="textrt"/>
					<display:column property ="endUserGsmSucc"  titleKey="GSM End-User Perceived Paging Success (%)"  sortable="true" headerClass="master-header-3" class="textrt"/>
					<display:column property ="succrInterMsc"  titleKey="Inter-MSC Handover Success, GSM (%)"  sortable="true" headerClass="master-header-3" class="textrt"/>
					<display:column property ="succrU2gInterMscHo"  titleKey="Inter-MSC Handover Success, U2G (%)"  sortable="true" headerClass="master-header-3" class="textrt"/>
					<display:column property ="succrIntraMscHoGsm"  titleKey="Intra-MSC Handover Success, GSM (%)"  sortable="true" headerClass="master-header-3" class="textrt"/>
					<display:column property ="succrIntraMscHoU2g"  titleKey="Intra-MSC Handover Success, U2G (%)"  sortable="true" headerClass="master-header-3" class="textrt"/>
					<display:column property ="maxCpLoad"  titleKey="CP Load"  sortable="true" headerClass="master-header-3" class="textrt"/>
					<display:column property ="smsmosucr"  titleKey="SMSMOSucR (%)"  sortable="true" headerClass="master-header-3" class="textrt"/>
					<display:column property ="smsmtsucr"  titleKey="SMSMTSucR (%)"  sortable="true" headerClass="master-header-3" class="textrt"/>
					
			</display:table>
			</div>
		</td>
	</tr>
	<tr>
		<td class = "heare-1">
			<b>CORE NETWORK PS</b>
		</td>
	</tr>
	<tr>
		<td>
		<div class = "tableStandar">
		<display:table name="${vRpDySummaryCorePs}" id="vRpDySummaryCorePs" requestURI="" pagesize="100"  export="true" sort="list">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"   headerClass="master-header-1"/>		    
			    <display:column property="sgsnid"  titleKey="System" sortable="true" sortProperty="sgsnid"  headerClass="master-header-1"/>
			    <display:column property="utilSubs" titleKey="Util Subs (%)" sortable="true" headerClass="master-header-3" class="textrt"/>
				<display:column property="utilPdpcontext" titleKey="Util PDP" sortable="true" headerClass="master-header-3" class="textrt"/>
				<display:column property="attachedSub" titleKey="Attached Subs" sortable="true" headerClass="master-header-3" class="textrt"/>
				<display:column property="attachSr" titleKey=" Attach SR (%)" sortable="true" headerClass="master-header-3" class="textrt"/>
				<display:column property="pssr" titleKey=" PSSR (%)" sortable="true" headerClass="master-header-3" class="textrt"/>
				<display:column property="intraRauSucr" titleKey=" Intra Rau Sucr (%)" sortable="true" headerClass="master-header-3" class="textrt"/>
				<display:column property="interRauSucr" titleKey=" Inter Rau Sucr (%)" sortable="true" headerClass="master-header-3" class="textrt"/>
				<display:setProperty name="export.csv.filename" value="SGSNList.csv"/>
			    <display:setProperty name="export.excel.filename" value="SGSNList.xls"/>
			    <display:setProperty name="export.xml.filename" value="SGSNList.xml"/>
		</display:table>
		</div>
		</td>
	</tr>
	
</table>

<table class = "table-layout" style="margin-top:4px;">
	<tr>
		<td class = "heare-1">
			<b>THỐNG KÊ CHỈ TIÊU SXKD</b>
		</td>
	</tr>
	<tr>
		<td>
			<input type = "hidden" value = "${row_num}"  id = "txtRowNum"/>
			<input type = "hidden" value = "${txtId}"  id = "txtId"/>
			<div style="margin-left:600px;display: none;" class="loading fl">
				<img src="${pageContext.request.contextPath}/images/icon/barIndicator.gif">
			</div>
		</td>
	</tr>
	<tr>
		<td>
		<div class = "tableStandar">
				<display:table name="${vRbDyQualityNetwork}" id="vRbDyQualityNetwork" requestURI="" pagesize="100"  export="true">
				    <display:column titleKey="hQualityNetwork.groupName" sortable="true"  sortName="GROUP_NAME" total="true"  group="1" class="GROUP_NAME dpn" headerClass="master-header-3" media="html">
						<c:out value="${vRbDyQualityNetwork.groupName}"></c:out>
			   		</display:column>
				   <%-- <display:column property="groupName"  titleKey="hQualityNetwork.groupName"  class="dpn" headerClass="dpn"/>--%>
				   	<display:column property="qualityName"  titleKey="hQualityNetwork.qualityName" sortable="true" sortName="QUALITY_NAME" headerClass="master-header-3"/>
					<display:column titleKey="hQualityNetwork.quality" sortable="true" sortName="QUALITY"  style="max-width:40px;" media="html" headerClass="master-header-3">
						<c:out value="${vRbDyQualityNetwork.quality}"></c:out>
			   		</display:column>
			   		<display:column titleKey="hQualityNetwork.dyValue" sortable="true" sortName="DY_VALUE" style="width:100px;" headerClass="master-header-3">
						<input type = "text"  value="${vRbDyQualityNetwork.dyValue}" id = "dyvalue_${vRbDyQualityNetwork_rowNum}" >
			   		</display:column>
<%-- 			   		<display:column property="quality"  titleKey="hQualityNetwork.quality" sortable="true" sortName="QUALITY" class="dpn" headerClass="dpn"/> 
					<display:column property="dyValue"  titleKey="hQualityNetwork.dyValue" sortable="true" sortName="DY_VALUE" style="max-width:200px;"/>--%>
					<display:column property="reviews"  titleKey="hQualityNetwork.reviews" sortable="true" sortName="REVIEWS" style="max-width:70px;" headerClass="master-header-3"/>
					
		   			<display:setProperty name="export.csv.include_header" value="true" />
					<display:setProperty name="export.excel.include_header" value="true" />
					<display:setProperty name="export.xml.include_header" value="true" />
					<display:setProperty name="export.xml.filename" value="${exportFileName1}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName1}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName1}.xls" />
					<display:setProperty name="export.xml.filename" value="${exportFileName1}.xml" />
					
				</display:table>
				</div>
		</td>
	</tr>
	<tr><td><input id = "btnSave" type="button" class="button" name="save" value="<fmt:message key="button.save"/>" /></td></tr>
</table>
</div>
<table class = "table-layout" style="margin-top:4px;">
	<tr>
		<td class = "heare-1">
			<b>ĐÁNH GIÁ MẠNG LƯỚI</b>
		</td>
	</tr>
	<tr>
		<td>
		<div class = "tableStandar">
			<display:table name="${danhGiaMLList}"   id="danhGiaML" requestURI="" sort="external" defaultsort="1" export="true">
			<display:column property="teamProcess"  titleKey="warning.teamProcess" sortable="true" sortName="TEAM_PROCESS" style="max-width:150px;"/>
			<display:column property="actionProcess"  titleKey="warning.methodTreatment" sortable="true" sortName="ACTION_PROCESS" style="min-width:700px;" />
			<display:column property="userProcess"  titleKey="danhGiaML.userExcute" sortable="true" sortName="USER_PROCESS" style="max-width:100px;"/>
			<c:if test="${checkCaTruc==true}">
				<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:60px;">
					<a href="${pageContext.request.contextPath}/welcome.htm?id=${danhGiaML.id}&day=${day}#danhGia">Edit</a>&nbsp;
					<a href="${pageContext.request.contextPath}/deldanhGia.htm?id=${danhGiaML.id}&userProcess=${danhGiaML.userProcess}"  onclick="return confirm('Bạn có thực sự muốn xóa không?')">Delete</a>&nbsp;
	   			</display:column>
   			</c:if>
			<display:setProperty name="export.csv.include_header" value="true" />
			<display:setProperty name="export.excel.include_header" value="true" />
			<display:setProperty name="export.xml.include_header" value="true" />
			<display:setProperty name="export.xml.filename" value="${exportFileName3}.xml" />
			<display:setProperty name="export.csv.filename" value="${exportFileName3}.csv" />
			<display:setProperty name="export.excel.filename" value="${exportFileName3}.xls" />
			<display:setProperty name="export.xml.filename" value="${exportFileName3}.xml" />
		</display:table>
		</div>
		</td>
	</tr>
	
</table>

<table class = "table-layout" style="margin-top:4px;">
	<tr>
		<td class = "heare-1">
			<b>THÔNG TIN NHẬN XÉT ĐÁNH GIÁ MẠNG LƯỚI</b>
		</td>
	</tr>
	<tr>
		<td>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
			<div style="overflow: auto;" id="danhGia">
			<input type="hidden" name="userProcess" id="userProcess" value="${userProcess}">
			<input type="hidden" name="id" id="id" value="${id}">
			
			<table class="simple2"> 
				<tr>
				<td><fmt:message key="danhGiaMangLuoi.dept"/><font color="red">(*)</font></td>
           		<td style="width:200px;">
           			<div id='dept'></div>
				</td>
		          <td style="width:140px;text-align:left; padding-left:10px">
		           		<p><fmt:message key="danhGiaMangLuoi.teamProcess"/></p>
		          </td>
		          <td>
		          		<div id='teamProcess'></div>
		         </td>
		         
		          
		       </tr>
		       <tr>  
		       		<td style="width:140px;text-align:left; padding-left:10px"><fmt:message key="danhGiaMangLuoi.actionPeocess"/></td>
		         	<td colspan="3"> 
		           		<textarea cols="80" rows="10"  name="actionProcess" id="actionProcess" maxlength="900" >${actionProcess}</textarea>
						 <script type="text/javascript">
							CKEDITOR.replace( 'actionProcess',
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
					<td align="left">
						<input type="submit" class="button" onclick="setAction('save')" name="save" value="<fmt:message key="button.save"/>" />
		          		<input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="welcome.htm?day=${day}"'>
		           </td>
		       </tr>
		    </table>
			</div>
		</td>
	</tr>
	<tr>	
		<td class = "heare-1">
			<b>GỬI EMAIL BÁO CÁO HÀNG NGÀY</b>
		</td>
	</tr>
	<tr>	
		<td>
			<table class="wid100 simple2">
			<tr>
				<td style="width:140px;text-align:left; padding-left:10px">Người gửi</td>
				<td style="text-align:left" colspan="2">
					<b>${userFrom}</b>
					<input type="hidden" id="e_password" value="${password}">
				</td>
			</tr>
			
			<tr>
				<td style="width:140px;text-align:left; padding-left:10px">Người nhận</td>
				<td style="width:150px;">
					<div id="sendToCb" align="left"></div>
				</td>
				<td><input type="text" name="e_email" id="e_email"  value="${email}"/></td>
				<%-- <input type="text" id="e_email" style="width:100%;height: 22px;" value="${email}"> --%>
			</tr>
			
			<%-- <tr>
				<td style="width:140px;text-align:left; padding-left:10px">Tệp đính kèm</td>
				<td colspan="2">
					<div id="fileAttTable" style="text-align:left; padding:5px;line-height:18px">
						<c:forEach var="item" items="${fileAtt}" varStatus="rowCounter">
							<div>
								<input type="checkbox" id="fileAtt" checked="checked" name="user_group[]" value="${item}">
								<a href="${pageContext.request.contextPath}/form/file/download.htm?filename=${item}">${item}</a>
							</div>
						</c:forEach>
					</div>
					
					<div style="margin-bottom: 7px">
						<!-- <div class="queue-item">
							<div id="queue-upload"></div>
							<div id="queue"></div>
						</div> -->
						
						<input id="file_upload" name="file_upload" type="file" multiple="true">
					</div>
				</td>
			</tr> --%>
			
			<tr>
				<td style="width:140px;text-align:left; padding-left:10px">Nội dung</td>
				<td colspan="2">
					<textarea style="width:100%; height: 220px" name="e_content" id="e_content" maxlength="900" ></textarea>
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
		</td>
	</tr>
</table>
</form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadifive/jquery.uploadifive.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/uploadifive/uploadifive.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
Calendar.setup({
    inputField		:	"day",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseday",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 	
</script>
<!-- <script type="text/javascript">
	$(function() {
		$('#file_upload').uploadifive({
			'method'			: 'post',
			'auto'         		: true,
			'multi' 			: true,
			'formData'			: {'path' : 'documents'},
			'queueID'      		: 'queue',
			'uploadScript' 		: '${pageContext.request.contextPath}/form/upload/docpa.htm',
			'fileObjName' 		: 'filedata',
			'onInit'		: function() {},
			'onSelect'         	: function() {
				$('#file_upload').uploadifive('upload');
			},
			'onUploadComplete' : function(file, data) {
				
				var from = data.indexOf('"name":"') + 8; 
				var to = data.indexOf('}') - 1; 

				var name = data.substring(from, to);
				
				addFileId(name);
			}
		});
	});

	function getUrlFileName(v_sysFileId, v_fileName) {
		return '<a href="/file/download.html?id=' + v_sysFileId + '">' + v_fileName + '</a>';
	}

	function addFileId(data) {
		$("#fileAttTable").append('<div><input type="checkbox" id="fileAtt" checked="checked" name="user_group[]" value="'+data+'"> <a href="${pageContext.request.contextPath}/form/file/download.htm?filename='+data+'">'+data+'</a></div>');
	}
</script> -->

<script type="text/javascript">
//ComboBox Team
var theme =  getTheme();
	
	var urldept = "${pageContext.request.contextPath}/ajax/getDeptListAlarm.htm";
	// prepare the data
	var sourcedept =
	{
	datatype: "json",
	url: urldept,
	datafields: [
	            { name: 'deptCode'},
	            { name: 'deptValue'},
	            { name: 'deptName'}
	        ],
	async: false
	};
	var dataAdapterdept = new $.jqx.dataAdapter(sourcedept);
	$("#dept").jqxDropDownList({ source: dataAdapterdept,displayMember: "deptName", valueMember: "deptName", width: 200,height: 25,theme: theme,autoOpen: false,enableHover: false,disabled: true });
	var dept =  "<c:out value='${dept}'/>";
	//alert(dept);
	if(dept!=null&&dept!="")
	{
	//	$('#dept').val(dept); 
		$("#dept").jqxDropDownList('selectItem', dept ); 
	}
	else
	{
		$("#dept").jqxDropDownList({selectedIndex: 0 }); 
	}

	var urlTeam = "${pageContext.request.contextPath}/ajax/getTeamListAlarmFull.htm?dept="+dept;
		// prepare the data
	var sourceTeam =
	{
	   datatype: "json",
	   url: urlTeam,
	   datafields: [
	                { name: 'deptCode'},
	                { name: 'deptValue'},
                    { name: 'deptName'}
	            ],
	   async: false
	};
	var dataAdapterTeam = new $.jqx.dataAdapter(sourceTeam);
	$("#teamProcess").jqxDropDownList({ source: dataAdapterTeam,displayMember: "deptName", valueMember: "deptName", width: 200,height: 25,theme: theme,autoOpen: false,enableHover: false,disabled: true});
	var team =  "<c:out value='${team}'/>";
	//alert(team);
	if(team!=null&&team!="")
	{
		//$('#teamProcess').val(team); 
		$("#teamProcess").jqxDropDownList('selectItem', team ); 
	}
	else
		{
			$("#teamProcess").jqxDropDownList({selectedIndex: 0 }); 
		}
	$("#dept").change(function(){
		  var dept = $("#dept").val();
	  	  if (dept==null||dept=='null')
			  {
	  			dept='';
			  }
	  	 $.getJSON("${pageContext.request.contextPath}/ajax/getTeamListAlarmFull.htm",{dept:dept}, function(k){
	  		 var teamList=[];
	  			teamList =k;
	  			$("#teamProcess").jqxDropDownList({source: teamList});
	  		});	
	   });	
	
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
   ${groupList}
    $("#e_email").jqxInput({ height: 20, width: '100%', theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#e_email").jqxInput({ query: item });
            response(groupList);
        },
        renderer: renderer
    });
    $("#sendToCb").jqxComboBox({ source: groupList, height: 20, width: '100%', theme: theme ,autoDropDownHeight: true });
    $('#sendToCb').on('select', function (event) 
  		{
  			var itemB= $('#e_email').val();
  			var item = $("#sendToCb").jqxComboBox('getSelectedItem');
  			if (itemB!='')
  				{
  					if (itemB.indexOf(item.label)==-1)
  						{itemB+=","+item.label ;}
  				}
  			else
  				{
  					itemB=item.label ;
  				}
  			$("#e_email").val(itemB);
  			//alert(itemB);
  		});
	
	//CLICK GUI MAIL
	$("#btnEmail").click(function(){
		
		$('#formEmail').dialog({
	        modal: true,
	        open: function () {
	            $(this).load('${pageContext.request.contextPath}/form/email.htm #contents');
	        },
	        height: 410,
	        width: 550,
	        title: 'Gửi báo cáo hàng ngày'
	    });
	});
			
	$("#btnEmailSubmit").click(function(){
		
		var password = $("#e_password").val();
		if (password == '') {
			alert('Người dùng chưa có mật khẩu email');
			$("#e_password").focus();
			return;
		}

		var email = $("#e_email").val();
		if (email == '') {
			alert('Vui lòng nhập email người nhận');
			$("#e_email").focus();
			return;
		}
		
	/* 	var values = new Array();
		$.each($("input[name='user_group[]']:checked"), function() {
			values.push($(this).val());
			
		}); */
		
		//if (values.length == 0) {
		//	alert('Chọn tệp đính kèm');
		//	return;
		//}
		
		var content	= CKEDITOR.instances['e_content'].getData();
		// var content = $("#e_content").val();
		var report = $("#reportEmail").html();

		$.ajax({
			type: "POST",
			url: "${pageContext.request.contextPath}/form/send.htm",
			data:{ 
				//fileAtt: encodeURI(values.join('|')), 
				content: encodeURI(content), 
				password: encodeURI(password),
				email: encodeURI(email),
				report: encodeURI(report)
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
				
				if (data.status == 'success') {
					alert('Cập nhật thông tin gửi email thành công. Vui lòng chờ trong ít phút.');
					$("#formEmail").dialog('close');
				} else {
					alert(data.cause);
				}
			}
		});
		
	});
</script>


<script>
$(document).ready(function(){
	var i=0;
	$('#vRbDyQualityNetwork>tbody>tr').each( function() { 
    	 var value = $(this).find(".subtotal-header").text();
    	 i++;
    	 if( value!=null && value !=''){
			//alert(value);
			var trs='<td class="subtotal-header group-'+i+'" colspan="6">'+value+'</td>';
			$(this).html(trs);	 		 
	    	}
	 	});


	var action =  $("#action").val();
	if (action=='save')
	{
		$("html, body").animate({ scrollTop: $(document).height() }, "fast");
	}
});

function setAction(value) {
	  var action = document.getElementById("action");
	  action.value = value;

	  return true;
	 }

	 function deleteFile(name) {
		 alert(name);
	}
</script>
<script type="text/javascript">
	$("#btnSave").click(function(){	
	var row_num = $("#txtRowNum").val();
	var row_id = $("#txtId").val();
	var values = "";
	for(var i = 1; i <= row_num; i++)
	{
		
		values = values + $("#dyvalue_"+i).val() + ",";
	}
	//alert(values);
	$(".loading").css('display', 'block');
	$.ajax({
		url: "${pageContext.request.contextPath}/form/save-data.htm",
		data:{ value: values, rowsid: row_id },
		error: function(){},
		beforeSend: function(){},
		complete: function(){},
		success: function(data){
			//alert('Thành công');
			
			if(data.status == 'success')
				{
				$(".loading").css('display', 'none');
				alert('Cập nhật thành công');
				}
				
			else
				alert('Cập nhật thất bại');
			//timeout = setTimeout(10000);
			//
		}
		
	
	});
	
	
});
</script>