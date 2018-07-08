<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
    #doublescroll { overflow: auto; overflow-y: hidden; }
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>
<title><fmt:message key="qLThongTinPhanAnh.feedbackDeletedList"/></title>
<content tag="heading"><fmt:message key="qLThongTinPhanAnh.feedbackDeletedList"/></content>
    
<%@ include file="/WEB-INF/jspfeedback/tt6/searchFeedback.jsp" %>
 <div class="flw100" >
 	<div id="doublescroll">
		<form:form name="displ">
			<display:table style="width:150%" name="${feedbackDeletedList}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true">
					 			
					<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${item_rowNum}"/> </display:column>
					
					<display:column style="max-width:90px;word-wrap: break-word;" class="centerColumnMana" titleKey="qLThongTinPhanAnh.thueBao" media="html">
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
					<display:column property="processDate" format="{0,date,dd/MM/yyyy HH:mm:ss}" style="max-width:90px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.thoiGianXL1" sortable="true" sortName="PROCESS_DATE"/>
					
					<display:column property="nameStatus" titleKey="qLThongTinPhanAnh.trangThai" sortable="true" sortName="NAME_STATUS"/>	
					<display:column property="processStatus" titleKey="qLThongTinPhanAnh.processStatus" sortable="true" sortName="PROCESS_STATUS"/>
					<display:column property="processHandleMethod" titleKey="qLThongTinPhanAnh.processHandleMethod" sortable="true" sortName="PROCESS_HANDLE_METHOD"/>
					<display:column property="processResultLocal" titleKey="qLThongTinPhanAnh.processResultLocal" sortable="true" sortName="PROCESS_RESULT_LOCAL"/>
					<display:column property="processMotional" titleKey="qLThongTinPhanAnh.processMotional" sortable="true" sortName="PROCESS_MOTIONAL"/>
					<display:column property="fbIbcName" titleKey="qLThongTinPhanAnh.fbIbc" sortable="true" sortName="FB_IBC_NAME"/>
					<display:column titleKey="global.management" media="html" class="centerColumnMana">
			  				<a class="linkUnderline" href="recovery.htm?id=${item.id}"
								onclick="return confirm('Bạn có chắc chắn muốn phục hồi bản ghi này không?')">Phục hồi</a>&nbsp;
			 			</display:column>
				
					<display:setProperty name="export.csv.include_header" value="true" />
					<display:setProperty name="export.excel.include_header" value="true" />
					<display:setProperty name="export.xml.include_header" value="true" />
					<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
			</display:table>
		</form:form>
 	 </div>
 </div>

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