<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
								<div class="wid70 psr ovh select">
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
								<div class="wid70 psr ovh select">
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
								<div class="wid70 psr ovh select">
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
								<div class="wid70 psr ovh select">
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
							<td colspan="2" class="pl10">
								<input type="submit" class="button" name="filter" value="Tìm kiếm" />&nbsp;
								<input type="button" class="button" value="<fmt:message key="global.form.xoadk"/>" onclick="removeText()">	
							</td>
						</tr>
					</table>
				</form:form>
				</td>
		</tr>
	</table>
	
<link href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" rel="stylesheet">
<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.jcountdown1.3.js"></script>	

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

}
onload = focusIt;

</script>