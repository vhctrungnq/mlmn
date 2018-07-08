<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    #doublescroll { overflow: auto; overflow-y: hidden; }
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; } 
  #tableEditor {
    position: absolute;
    top: 300px;
    padding: 5px;
    border: 1px solid #000;
    background: #fff;
}
</style>
<title><fmt:message key="title.qLThongTinPhanAnh"/></title>
<div class="body-content"></div>
<div class="flw15">
	<div class="block-cat">
		<div class="header-block">
			<span class="box-title"><fmt:message key="title.qLThongTinPhanAnh"/></span>
		</div>
		<div id="nav" class="content-block">
			<c:forEach var="item" items="${getListTTPACount}">   
           		<div class="element-box">
           			<a id="${item.remark}" title="${item.name}" href="${item.remark}.htm"> ${item.name}&nbsp;(${item.countTrangThai}) </a>
           		</div>
           </c:forEach>
		</div>
	</div>
</div>

<div id="content" class="frw84">
	<table border="0" width="100%" cellspacing="5" cellpadding="0" class="form">
		<tr>
			<td align="left"><form:form name="frmIndex" commandName="filter" method="post" action="list.htm">
					<table   border="0" cellspacing="5" cellpadding="0">
						<tr>
							<td class="wid12 mwid110"></td>
							<td class="wid20"></td>
							<td class="wid12 mwid140"></td>
							<td class="wid20"></td>
							<td class="wid12 mwid130"></td>
							<td class="wid20"></td>
						</tr>
						<tr>
							<td><spring:message code="qLThongTinPhanAnh.loaiPhanAnh"/></td>
							<td colspan="3">
								<div class="psr ovh select">
									<form:select path="fbType" class="select" id="selectFbType">
										<option value="">--Tất cả--</option>
						 				<c:forEach var="items" items="${loaiPAList}">
						              	<c:choose>
						                <c:when test="${items.code == loaiPACBB}">
						                    <option value="${items.code}" selected="selected">${items.name}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.code}">${items.name}</option>
						                </c:otherwise>
						              	</c:choose>
								    	</c:forEach>
							          </form:select>
							    </div>
							</td>
							<td class="pl10"><spring:message code="qLThongTinPhanAnh.loaiMang"/></td>
							<td>
								<div class="wid110 psr ovh select">
									<form:select path="networkType" class="select" id="selectNetworkType">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="items" items="${loaiMangList}">
							              	<c:choose>
							                <c:when test="${items.value == loaiMangCBB}">
							                    <option value="${items.value}" selected="selected">${items.name}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.value}">${items.name}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</form:select>
		          				</div>
							</td>
						</tr>
						<tr>
							<td><spring:message code="qLThongTinPhanAnh.thoiGianPA"/></td>
							<td>
								<div class="fl" style="width:39%"><input id="thoiGianPAFrom" name="thoiGianPAFrom" value="${thoiGianPAFrom}" class="wid98" type="text"></div>
								<div class="fl" style="width:20%; text-align:center;"><spring:message code="qLThongTinPhanAnh.to"/></div>
								<div class="fr" style="width:39%"><input id="thoiGianPATo" name="thoiGianPATo" value="${thoiGianPATo}" class="wid98" type="text"></div>
							</td>
							<td class="pl10"><spring:message code="qLThongTinPhanAnh.thueBaoPA"/></td>
							<td><form:input path="subscribers" cssClass="wid100"/></td>
							<td class="pl10"><spring:message code="qLThongTinPhanAnh.loaiThueBao"/></td>
							<td>
								<div class="wid110 psr ovh select">
									<form:select path="subscriberType" class="select">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="items" items="${loaiTBList}">
							              	<c:choose>
							                <c:when test="${items.value == loaiTBCBB}">
							                    <option value="${items.value}" selected="selected">${items.name}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.value}">${items.name}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</form:select>
		          				</div>
							</td>
						</tr>
						<tr>
							<td><spring:message code="qLThongTinPhanAnh.thoiGianXL"/></td>
							<td>
								<div class="fl" style="width:39%"><input id="thoiGianXLFrom" name="thoiGianXLFrom" value="${thoiGianXLFrom}" class="wid98" type="text"></div>
								<div class="fl" style="width:20%; text-align:center;"><spring:message code="qLThongTinPhanAnh.to"/></div>
								<div class="fr" style="width:39%"><input id="thoiGianXLTo" name="thoiGianXLTo" value="${thoiGianXLTo}" class="wid98" type="text"></div>
							</td>
							<td class="pl10"><spring:message code="qLThongTinPhanAnh.phongDai"/></td>
							<td>
								<div class="psr ovh select">
									<select id="deptProcess" name="deptProcess" class="select">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="items" items="${deptProcessList}">
							              	<c:choose>
							                <c:when test="${items.deptCode == deptProcessCBB}">
							                    <option value="${items.deptCode}" selected="selected">${items.deptCode}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.deptCode}">${items.deptCode}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</select>
		          				</div>
							</td>
							<td class="pl10"><spring:message code="qLThongTinPhanAnh.toXuLy"/></td>
							<td>
								<div class="wid110 psr ovh select">
									<select id="team" name="team" class="select">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="items" items="${teamList}">
							              	<c:choose>
							                <c:when test="${items.team == teamCBB}">
							                    <option value="${items.team}" selected="selected">${items.team}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.team}">${items.team}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</select>
		          				</div>
							</td>
						</tr>
						<tr>
							<td><spring:message code="qLThongTinPhanAnh.quan"/></td>
							<td>
								<div class="psr ovh select">
									<select id="district" name="district" class="select">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="items" items="${quanHuyenList}">
							              	<c:choose>
							                <c:when test="${items.code == tinhThanhCBB && items.district == quanHuyenCBB}">
							                    <option value="${items.code}//${items.district}" selected="selected">${items.districtName}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.code}//${items.district}">${items.districtName}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</select>
		          				</div>
							</td>
							<td class="pl10"><spring:message code="qLThongTinPhanAnh.phuong"/></td>
							<td>
								<div class="psr ovh select">
									<select id="wards" name="wards" class="select">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="items" items="${phuongXaList}">
							              	<c:choose>
							                <c:when test="${items.village == phuongXaCBB}">
							                    <option value="${items.village}" selected="selected">${items.villageName}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.village}">${items.villageName}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</select>
		          				</div>
							</td>
							<td class="pl10"><spring:message code="qLThongTinPhanAnh.trangThai"/></td>
							<td>
								<div class="wid110 psr ovh select">
									<form:select path="status" class="select">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="items" items="${QLTTFBList}">
							              	<c:choose>
							                <c:when test="${items.value == trangThaiCBB}">
							                    <option value="${items.value}" selected="selected">${items.name}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.value}">${items.name}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</form:select>
		          				</div>
							</td>
						</tr>
						<tr>
							<td><spring:message code="qLThongTinPhanAnh.vip"/></td>
							<td>
								<div class="psr ovh select">
									<select id="vipCode" name="vipCode" class="select">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="items" items="${fbVipList}">
							              	<c:choose>
							                <c:when test="${items.vipCode == vipCBB}">
							                    <option value="${items.vipCode}" selected="selected">${items.vipName}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.vipCode}">${items.vipName}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</select>
		          				</div>
							</td>
							<td class="pl10"><spring:message code="qLThongTinPhanAnh.fbIbc"/></td>
							<td>
								<div class="psr ovh select">
									<select id="fbIbc" name="fbIbc" class="select">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="items" items="${fbIbcList}">
							              	<c:choose>
							                <c:when test="${items.value == fbIbcCBB}">
							                    <option value="${items.value}" selected="selected">${items.name}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.value}">${items.name}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</select>
		          				</div>
							</td>
						 <td class="pl10"><spring:message code="qLThongTinPhanAnh.fbSendTelecom"/></td>
							<td>
							<div class="wid60 psr ovh select">
									<form:select path="fbSendTelecom" class="select" id="fbSendTelecom">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="status" items="${statusList}">
							              	<c:choose>
							                <c:when test="${status.name == fbSendTelecom}">
							                    <option value="${status.name}" selected="selected">${status.value}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${status.name}">${status.value}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</form:select>
		          					</div>
		          					&nbsp;&nbsp;&nbsp;<input type="submit" class="button" name="filter" value="Tìm kiếm" />
							</td>  
						</tr>
						<tr>
							<td colspan="6" align="right">
								 <input type="hidden" name="isManager" id="isManager" value="${isManager}">
								 <input type="hidden" id="checkColumn" name="checkColumn" value="${checkColumn}"/>
								 <input type="hidden" id="tableWidth" name="tableWidth" value="${tableWidth}"/>
 								 <input class="button" type="button" id = "edit" value="Chọn cột hiển thị" >
								 &nbsp;<input class="button" type="button" value="Lấy dữ liệu từ feedback tập trung" onClick='window.location="getDataFeedback.htm"'>
								 &nbsp;<input type="button" class="button" value="<fmt:message key="global.form.xoadk"/>" onclick="removeText()">
							 </td>
						</tr>
					</table>
				</form:form>
				</td>
		</tr>
	</table>
</div> 
 <div class="flw100" >  
 <div id="doublescroll">
	<form:form name="displ">
	<div class="tableStandar">
		 	<display:table   name="${feedbackList}"  style="width:200%" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true">
		 			
		 			<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${item_rowNum}"/> </display:column>
		 			<display:column class="centerColumnMana" style="width:25px;" title="<input type='checkbox'  name='selectAllCheck' onClick='javascript:funcSelectAll(\"fb_id\",\"selectAllCheck\")'   value='Select All' />" media="html">
								<input class="selectall" type="checkbox" name="fb_id" value="${item.id}"/>
					</display:column>
					
					<display:column style="max-width:90px;word-wrap: break-word;" class="link centerColumnMana" href="checkedList.htm" paramId="checkedList" paramProperty="id" titleKey="qLThongTinPhanAnh.thueBao" media="html">
		 				<div>
			 				<c:choose>
			 				<c:when test="${item.vipCode != '0'}">
				 				<span class="timeLeft">
				 					<c:if test="${not empty item.subscribers}">${item.subscribers}<br></c:if>
								</span>
			 				</c:when>
			 				<c:otherwise>
			 					<c:if test="${not empty item.subscribers}">${item.subscribers}<br></c:if>
			 				</c:otherwise>
			 				</c:choose>
		 				</div>
		 			</display:column>
		 			
		 			<display:column property="subscribers" titleKey="qLThongTinPhanAnh.thueBao" headerClass="hide" class="hide" />
		 			<display:column property="networkType" style="max-width:150px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.loaiMang" sortable="true" sortName="NETWORK_TYPE"/>
		 			<display:column property="nameSubscriberType" style="max-width:150px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.loaiThueBao" sortable="true" sortName="NAME_SUBSCRIBER_TYPE"/>
		 			<display:column property="vipName" style="max-width:150px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.vip" sortable="true" sortName="VIP_NAME"/>
		 			<display:column property="districtName" style="max-width:150px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.quan" sortable="true" sortName="DISTRICT_NAME"/>
		 			<display:column property="address" style="max-width:150px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.address" sortable="true" sortName="ADDRESS"/>
		 			<display:column property="deptProcess" titleKey="qLThongTinPhanAnh.phongDai" sortable="true" sortName="DEPT_PROCESS"/>
		 			<display:column property="team" titleKey="qLThongTinPhanAnh.toXuLy" sortable="true" sortName="TEAM"/>
		 		    
		 			<display:column property="fbName" style="max-width:150px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.loaiPhanAnh" sortable="true" sortName="FB_NAME"/>
		 			<display:column property="fbQty" style="max-width:150px;word-wrap: break-word;" class="rightColumnMana" titleKey="qLThongTinPhanAnh.fbQty" sortable="true" sortName="FB_QTY"/>
		 			<display:column property="fbContent" style="max-width:410px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.noiDung" sortable="true" sortName="FB_CONTENT"/>						
		 			
		 			<display:column property="fbDate" format="{0,date,dd/MM/yyyy HH:mm:ss}" style="max-width:90px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.thoiGianPA1" sortable="true" sortName="FB_DATE"/>
		 			 
		 			
		 			<display:column class="centerColumnMana" titleKey="qLThongTinPhanAnh.thoiGianConLai" media="html">		
						<div id="timeLeftFirst_${item.id}" class="timeLeft"></div>
					</display:column>
		 			
		 			<display:column property="nameStatus" titleKey="qLThongTinPhanAnh.trangThai" sortable="true" sortName="NAME_STATUS"/>	
					<display:column property="processStatus" titleKey="qLThongTinPhanAnh.processStatus" sortable="true" sortName="PROCESS_STATUS"/>
					<display:column property="processHandleMethod" titleKey="qLThongTinPhanAnh.processHandleMethod" sortable="true" sortName="PROCESS_HANDLE_METHOD"/>
					<display:column property="processResultLocal" titleKey="qLThongTinPhanAnh.processResultLocal" sortable="true" sortName="PROCESS_RESULT_LOCAL"/>
					<display:column property="processMotional" titleKey="qLThongTinPhanAnh.processMotional" sortable="true" sortName="PROCESS_MOTIONAL"/>
					<display:column property="fbIbcName" titleKey="qLThongTinPhanAnh.fbIbc" sortable="true" sortName="FB_IBC_NAME"/>
					<display:column property="fbComment" titleKey="qLThongTinPhanAnh.fbComment" sortable="true" sortName="FB_COMMENT"  style="max-width:410px;word-wrap: break-word;"/>
 					 <display:column class="centerColumnMana" titleKey="qLThongTinPhanAnh.checkTimeTelecom" media="html">		
						<div id="timeTelecom_${item.id}" class="timeLeft"></div>
					</display:column>
					
					<display:column class="centerColumnMana" style="width:80px;" titleKey="qLThongTinPhanAnh.fbSendTelecom" media="html">
									<c:choose>
										<c:when test="${item.fbSendTelecom == 'Y'}">
											<input class="selectall" type="checkbox" name="fb_send_telecom" value="${item.fbSendTelecom}" checked="checked"/>
										</c:when>
										<c:otherwise>
											<input class="selectall" type="checkbox" name="fb_send_telecom" value="${item.fbSendTelecom}"/>
										</c:otherwise>
									</c:choose>
	          		</display:column>
	          		<c:if test="${isManager=='Y' }">
	          					<display:column class="centerColumnMana" style="width:80px;" titleKey="qLThongTinPhanAnh.isFeedbacked" media="html">
									<c:choose>
										<c:when test="${item.isFeedbacked == 'Y'}">
											<input class="selectall" type="checkbox" name="is_feedbacked" value="${item.isFeedbacked}" checked="checked"/>
										</c:when>
										<c:otherwise>
											<input class="selectall" type="checkbox" name="is_feedbacked" value="${item.isFeedbacked}"/>
										</c:otherwise>
									</c:choose>
	          					</display:column>
								<display:column titleKey="global.management" media="html" class="centerColumnMana">
										<a class="linkUnderline" href="delete.htm?id=${item.id}&deleteBy=${trangThaiCBB}"
												onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')">Xóa</a>&nbsp;
				    			</display:column>
	          		</c:if>
	          		
					
		 			<display:setProperty name="export.csv.include_header" value="true" />
		 			<display:setProperty name="export.excel.include_header" value="true" />
		 			<display:setProperty name="export.xml.include_header" value="true" />
		 			<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		 			<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		 			<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
		 	</display:table>
		 	 </div>
	</form:form>
 	</div>
 </div>
 <div style="padding-top:5px;float: right;">
 	<input id="xuLyFBTheoLo" name="xuLyFBTheoLo" type="button" class="button" value="<spring:message code="button.xuLyFBTheoLo"/>" />
 </div>

 
<link href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" rel="stylesheet"> 

<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" /> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.jcountdown1.3.js"></script>

 
<script type="text/javascript">
$(function() {
	var x = [];
	var i = 0;
	<c:forEach items="${feedbackList}" var="listOfNames">
		x[i] = "<c:out value='${listOfNames.id}' escapeXml='false' />";
		
		var tgConLai = "<c:out value='${listOfNames.tgConLai}' escapeXml='false' />";
		if(tgConLai == 'HOAN_THANH'){
			var tgXuLy = "<c:out value='${listOfNames.tgXuLy}' escapeXml='false' />";
			$("#timeLeftFirst_" +  x[i]).html(tgXuLy);
			
			}
		else{
			$("#timeLeftFirst_" +  x[i]).countdown({
				
				date: '<fmt:formatDate value="${listOfNames.deadline}" pattern="MM dd, yyyy HH:mm"/>', //Counting TO a date
				htmlTemplate: "%{d} <span class=\"cd-time\">ngày</span> %{h} <span class=\"cd-time\">:</span> %{m} <span class=\"cd-time\">:</span> %{s} <span class=\"cd-time\"></span>", 
				onChange: function( event, timer ){
					
				},
				onComplete: function( event ){
					var status = "<c:out value='${listOfNames.tgConLai}' escapeXml='false' />";
					if (status == 'HOAN_THANH') {
						$(this).html("Hoàn thành");
					} else {
						$(this).html("Quá hạn");
					}
					
				},
				leadingZero: true,
				direction: "down"
			});
		}
		
		i = i + 1;
	</c:forEach>
	
});
</script>
<script type="text/javascript">
$(function() {
	var x = [];
	var i = 0;
	<c:forEach items="${feedbackList}" var="listOfNames">
		x[i] = "<c:out value='${listOfNames.id}' escapeXml='false' />";
		var isFeedbacked = "<c:out value='${listOfNames.isFeedbacked }' escapeXml='false' />";
		
		var tgConLai = "<c:out value='${listOfNames.tgConLai}' escapeXml='false' />";
 		var checkTimeTelecom = "<c:out value='${listOfNames.checkTimeTelecom}' escapeXml='false' />";
		if(tgConLai == 'HOAN_THANH' && isFeedbacked == 'Y'){
			var tgXuLy = "<c:out value='${listOfNames.tgXuLy}' escapeXml='false' />";
			$("#timeTelecom_" +  x[i]).html(tgXuLy);
			
			}
		else if(checkTimeTelecom=='')
		{
		}
		else{
			$("#timeTelecom_" +  x[i]).countdown({
				
				date: '<fmt:formatDate value="${listOfNames.checkTimeTelecom}" pattern="MM dd, yyyy HH:mm"/>', //Counting TO a date
				htmlTemplate: "%{d} <span class=\"cd-time\">ngày</span> %{h} <span class=\"cd-time\">:</span> %{m} <span class=\"cd-time\">:</span> %{s} <span class=\"cd-time\"></span>", 
				onChange: function( event, timer ){
					
				},
				onComplete: function( event ){
					var status = "<c:out value='${listOfNames.tgConLai}' escapeXml='false' />";
					if (status == 'HOAN_THANH') {
						$(this).html("Hoàn thành"  && isFeedbacked == 'Y');
					} else {
						$(this).html("Quá hạn");
					}
					
				},
				leadingZero: true,
				direction: "down"
			});
		}
		
		i = i + 1;
	</c:forEach>
	
});
</script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript">

$(document).ready(function(){

	$('#' + '<c:out value="${urlForm}"/>').addClass("selected");

	$( "#thoiGianPAFrom" ).datepicker();
	$( "#thoiGianPAFrom" ).datepicker( "option", "dateFormat", "dd/mm/yy" );
	$( "#thoiGianPAFrom" ).val('<c:out value="${thoiGianPAFrom}"/>');

	$( "#thoiGianPATo" ).datepicker();
	$( "#thoiGianPATo" ).datepicker( "option", "dateFormat", "dd/mm/yy" );
	$( "#thoiGianPATo" ).val('<c:out value="${thoiGianPATo}"/>');

	$( "#thoiGianXLFrom" ).datepicker();
	$( "#thoiGianXLFrom" ).datepicker( "option", "dateFormat", "dd/mm/yy" );
	$( "#thoiGianXLFrom" ).val('<c:out value="${thoiGianXLFrom}"/>');

	$( "#thoiGianXLTo" ).datepicker();
	$( "#thoiGianXLTo" ).datepicker( "option", "dateFormat", "dd/mm/yy" );
	$( "#thoiGianXLTo" ).val('<c:out value="${thoiGianXLTo}"/>');

});

function funcSelectAll()
{
   if(document.forms['displ'].selectAllCheck.checked==true)
   {
            for (var a=0; a < document.forms['displ'].lang.length; a++){
                 document.displ.lang[a].checked = true;            
           }
     }
     else
     {
           for (var a=0; a < document.forms['displ'].lang.length; a++){
                  document.displ.lang[a].checked = false;          
           }
     }          

}

function focusIt()
{

  var mytext = document.getElementById("subscribers");
  mytext.focus();
  var checkColumn = document.getElementById("checkColumn").value;
  var tableWidth = document.getElementById("tableWidth").value;
  var temp = new Array(); 
  if(checkColumn.indexOf(";") >= 0)
  {
	  var i = 0;
	  temp = checkColumn.split(";");
	  for (a in temp ) {
		  i ++;
		  var tags = $('#item th:nth-child(' + i + '), #item td:nth-child(' + i + ')');
		  if(temp[a]=='Y')
			  tags.show();
		  else
			  tags.hide();
	  }
	  
	  $("#item").css({"width":tableWidth});
   }
  
  
}
onload = focusIt;

</script>
<script type="text/javascript">
$('#xuLyFBTheoLo').click(function(){
	var val = [];
	var checkedList = "";
	
	$("input[type='checkbox'][name='fb_id']").each(function(i){
		if($(this).is(':checked'))
		{
			if($(this).val() != "Select All" && $(this).val() != "on"){
				checkedList += $(this).val() + "-";}
		}
		
	});

	if(checkedList != "")
		window.location = '${pageContext.request.contextPath}/feedback/general-feedback/checkedList.htm?checkedList=' + checkedList;
		
});
</script>

<script type="text/javascript">
$('#deptProcess').change(function(){

	$.ajax({
		  url: "${pageContext.request.contextPath}/feedback/quan-ly-thong-tin-lpa/ajax/loadProvince.htm",
		  beforeSend: function( ) {
			  $('#load').remove();
				$('.body-content').append('<span id="load">LOADING...</span>');
				$('#load').fadeIn('normal', loadContent);

				function loadContent() {
					$('#result').load('', '', showNewContent());
				}
				
				function showNewContent() {
					$('#result').show('normal', hideLoader());
				}
				
				function hideLoader() {
					$('#load').fadeOut('normal');
				}
		  },
		  data:{deptProcess: $('#deptProcess').val()}}).done(function(j) {
			  var options = '';
			  options += '<option value="">--Tất cả--</option>';	
			  for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].code + '//' + j[i].district + '">' + j[i].districtName + '</option>';
				}
			$("#district").html(options);
			$('#district option:first').attr('selected', 'selected');
		    
		  });
	
	loadTeam();
});

function loadTeam(){
	$.getJSON("${pageContext.request.contextPath}/feedback/so-luong-pa-theo-to-vien-thong/loadTeam.htm", {deptProcess: $('#deptProcess').val()}, function(j){
		var options = '';
		  options += '<option value="">--Tất cả--</option>';
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].team + '">' + j[i].team + '</option>';
			}
		$("#team").html(options);
		$('#team option:first').attr('selected', 'selected');
	});
};

function funcSelectAll(checkboxName, checkboxNameAll)
{
	checkboxes = document.getElementsByName(checkboxName);
   if(document.getElementsByName(checkboxNameAll)[0].checked==true)
   {
           for(var i=0, n=checkboxes.length;i<n;i++) {
        	    checkboxes[i].checked = true;
        	  }
     }
     else
     {
           for(var i=0, n=checkboxes.length;i<n;i++) {
       	    checkboxes[i].checked = false;
       	  }
     }          
}
</script>

<script type="text/javascript">

$('#district').change(function(){

	$.ajax({
	  url: "${pageContext.request.contextPath}/feedback/quan-ly-thong-tin-lpa/ajax/loadPhuongXa.htm",
	  beforeSend: function( ) {
		  $('#load').remove();
			$('.body-content').append('<span id="load">LOADING...</span>');
			$('#load').fadeIn('normal', loadContent);

			function loadContent() {
				$('#result').load('', '', showNewContent());
			}
			
			function showNewContent() {
				$('#result').show('normal', hideLoader());
			}
			
			function hideLoader() {
				$('#load').fadeOut('normal');
			}
	  },
	  data:{provinceDistrict: encodeURI($('#district').val())}}).done(function(j) {
		  var options = '';
		  options += '<option value="">--Tất cả--</option>';	
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].village + '">' + j[i].villageName + '</option>';
			}
		$("#wards").html(options);
		$('#wards option:first').attr('selected', 'selected');
	    
	  });
	  
});

</script>

<script type="text/javascript">
	function removeText()
	{
		document.getElementById("selectFbType").selectedIndex = 0;
		document.getElementById("selectNetworkType").selectedIndex = 0;
		document.getElementById("thoiGianPAFrom").value = '<c:out value="${thoiGianPAFrom}"/>';
		document.getElementById("thoiGianPATo").value = '<c:out value="${thoiGianPATo}"/>';
		document.getElementById("subscribers").value = '';
		document.getElementById("subscriberType").selectedIndex = 0;
		document.getElementById("thoiGianXLFrom").value = '';
		document.getElementById("thoiGianXLTo").value = '';
		document.getElementById("deptProcess").selectedIndex = 0;
		document.getElementById("team").selectedIndex = 0;
		$("#status option[value=" + '<c:out value="${trangThaiCBB}"/>' + "]").attr('selected', 'selected');
		
		document.getElementById("district").selectedIndex = 0;
		document.getElementById("wards").selectedIndex = 0;
		document.getElementById("vipCode").selectedIndex = 0;
	}

	$(function() {
    	$('#item>tbody>tr').each(
    	    	 function(){
        	    	 var value = '<c:out value="${trangThaiCBB}"/>';
        	    	 if( value!=null && value =='0'){
        	    		 //$(this).find("td").css({ 'color' : 'red', 'text-decoration': 'none'});
         	    		${highlight0}
            	    	}
        	    	 else if( value!=null && value =='1'){
        	    		 
         	    		${highlight1}
            	    	}
        	    	 else if( value!=null && value =='2'){
        	    		 
          	    		${highlight2}
             	    	}
        	    	 else{
         	    	 	//$(this).find("td").css({'color' : 'black', 'text-decoration': 'none'});
            	    	 ${highlight3}
         	    	 	}
    	});
	});
</script>
 
<script language="javascript" type="text/javascript" >
 $('#edit').click(function() {
    var headers = $('#item th').map(function() {
        var th =  $(this);
        return {
            text: th.text(),
            shown: th.css('display') != 'none'
        };
    });
    
    var h = ['<div class=\"tableStandar\" id=tableEditor><button id=done>Done</button><label for="checkbox_id" style=\"margin-left: 10px; font-size:12px; font-weight:bold;\">Check All</label><input style=\"margin-left: 5px;\" id=unCheckAll type="checkbox" checked/><table><thead><tr>'];
    $.each(headers, function() {
        h.push('<th><input class=checkbox1 type=checkbox',
               (this.shown ? ' checked ' : ' '),
               '/> ',
               this.text,
               '</th>');
    });
    
    h.push('</tr></thead></table></div>');

    
    $('body').append(h.join(''));

    $("#unCheckAll").change(function () {
        $("input:checkbox.checkbox1").prop('checked', $(this).prop("checked"));
        $("#unCheckAll").value("Check All");
    });
    
    
    $('#done').click(function() {
        var showHeaders = $('#tableEditor input').map(function() { return this.checked; });
        var checkColumn=""; 
       
        var g = 0;
        var t = 0;
        $.each(showHeaders, function(i, show) {
            var cssIndex = i;
            var tags = $('#item th:nth-child(' + cssIndex + '), #item td:nth-child(' + cssIndex + ')');
            if (show)
            {
            	 tags.show();
            	 checkColumn += "Y" + ";";
            	 t += tags.width();
            }
             else
            {
            	 checkColumn += "N" + ";";
            	 tags.hide();
            	 g += tags.width();
            }
           
        });  

        var s = 2600 - g;
        if(s > 1324)
        {

    	
		        $('#tableWidth').val(s+"px");
				$("#item").css({"width":s+"px"})  ;
        }
        else
        {
        		$('#tableWidth').val("100%");
        		$("#item").css({"width":"100%"})  ;
        }
        $('#checkColumn').val(checkColumn.substring(0,checkColumn.length-1));
        $('#tableEditor').remove();
        return false;
    });
    
    return false;
});
  </script>
<script type="text/javascript">
    function DoubleScroll(element) {
        var scrollbar= document.createElement('div');
        scrollbar.appendChild(document.createElement('div'));
        scrollbar.style.overflow= 'auto';
        scrollbar.style.overflowY= 'hidden';
        scrollbar.firstChild.style.width= element.scrollWidth+'px';
        scrollbar.firstChild.style.paddingTop= '1px';
        scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
        scrollbar.onscroll= function() {
            element.scrollLeft= scrollbar.scrollLeft;
        };
        element.onscroll= function() {
            scrollbar.scrollLeft= element.scrollLeft;
        };
        element.parentNode.insertBefore(scrollbar, element);
    }

    DoubleScroll(document.getElementById('doublescroll'));
</script>