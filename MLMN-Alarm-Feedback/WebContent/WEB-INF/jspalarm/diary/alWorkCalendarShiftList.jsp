<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title><fmt:message key="title.alWorkCalendar.lichTrucCa"/></title>   
<form id="checkForm" name = 'frmSample' method="post" action="list.htm" >
<div class="body-content"></div>
<div>
		<table style="width:60% ;border: 1px solid #c4cde0; padding: 5px;">
			<tr>	
				<td  class="wid6 mwid70">
			        	<fmt:message key="qLPhongBan.region"/>
		        </td>
		        <td class="wid10">
			        <select id="region" name="region" class="wid120">
		   					<c:forEach var="items" items="${regionList}">
				              <c:choose>
				                <c:when test="${items.name == region}">
				                    <option value="${items.name}" selected="selected">${items.value}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.name}">${items.value}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
		           		</select>	
			        
		        </td>
				<td class="wid5 mwid40"><fmt:message key="alWorkCalendar.week" /></td>
				<td class="wid7">
					<input type="text" value="${week}" name="week" id="week" class="wid90" onchange ="javascript:checkNumber(document.frmSample.week);"/>
				</td>
				<td class="wid5 mwid40"><fmt:message key="alWorkCalendar.year" /></td>
				<td class="wid8">
					<input type="text" value="${year}" name="year" id="year" class="wid90" onchange ="javascript:checkNumber(document.frmSample.year);"/>
				</td>
				<td class="wid6 mwid40"><fmt:message key="alWorkCalendar.department"/></td>
				<td class="wid30">
					<select name="department" id="department" class="wid90" >
						<option value="">--Tất cả--</option>
		           		<c:forEach var="item" items="${departmentList}">
							<c:choose>
				                <c:when test="${item.deptValue == department}">
				                    <option value="${item.deptValue}" selected="selected">${item.deptName}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item.deptValue}">${item.deptName}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> 
				</td>
				<td class="wid5 mwid40"><fmt:message key="alWorkCalendar.team"/></td>
				<td class="wid30">
					<select name="team" id="team" class="wid90">
						<option value="">--Tất cả--</option>
		           		<c:forEach var="item" items="${teamList}">
							<c:choose>
				                <c:when test="${item.deptValue == team}">
				                    <option value="${item.deptValue}" selected="selected">${item.deptName}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item.deptValue}">${item.deptName}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> 
				</td>
				<td>
					<input class="button" type="submit" id="btSearch" name="btSearch" onclick="setAction('search')" value="<fmt:message key="button.search"/>"/>
				</td>
			</tr>
		</table>
</div>

<br>
<table style="width:100%;">
<tr>
	<td>
		<table>
			<tr>
				<td align="center">
				<p style = "font-family:Times New Roman,Georgia,Serif;font-size:20px;">${head}</p>
				</td>
			</tr>
			<c:if test="${not empty departmentName}">
				<tr>
					<td align="center" style = "text-align:center;">
						<b style = "font-family:Times New Roman,Georgia,Serif;font-size:15px;">${departmentName}</b>
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty teamName}">
				<tr>
					<td align="center" style = "font-family:Times New Roman,Georgia,Serif;font-size:15px;text-align:center;">
						<b>${teamName}</b>
					</td>
				</tr>
			</c:if>
		</table>
	</td>
	
</tr>
</table>
<table style="width:100%;">
	<tr>
		<td align="center">
		<b style = "font-family:Times New Roman,Georgia,Serif;font-size:25px;"><fmt:message key="alWorkCalendar.ltc"/></b>
		</td>
	</tr>
	
	<tr>
		<td align="center">
		<b>	Tuần ${week} (${dateShift})</b>
		</td>
	</tr>
</table>

<%
        // you can do this as a scriptlet on the page, but i put it into a taglib...
        org.displaytag.decorator.MultilevelTotalTableDecorator subtotals = new org.displaytag.decorator.MultilevelTotalTableDecorator();
        subtotals.setGrandTotalDescription("&nbsp;");    // optional, defaults to Grand Total
        subtotals.setSubtotalLabel("&nbsp;", null);
        pageContext.getRequest().setAttribute("subtotaler", subtotals);
%>
<div style="overflow: auto;">
	<display:table name="${alWorkCalendarShiftList}" class="simple2" id="item" requestURI="" decorator="subtotaler" export="false" >
	  	<display:column property="shift" titleKey="alWorkCalendar.caTruc" total="true" group="1" style="border:0;border-left:1px solid #ddd;"/>
		<display:column property="function" titleKey="alWorkCalendar.function" class="TRUONG_CA"/>
		
		<%-- <display:column property="monday" titleKey="alWorkCalendar.thu2" headerClass="hide" class="hide" />
		<display:column property="tuesday" titleKey="alWorkCalendar.thu3"  headerClass="hide" class="hide" />
		<display:column property="wednesday" titleKey="alWorkCalendar.thu4"  headerClass="hide" class="hide" />
		<display:column property="thursday" titleKey="alWorkCalendar.thu5"  headerClass="hide" class="hide" />
		<display:column property="friday" titleKey="alWorkCalendar.thu6"  headerClass="hide" class="hide" />
		<display:column property="saturday" titleKey="alWorkCalendar.thu7"  headerClass="hide" class="hide" />
		<display:column property="sunday" titleKey="alWorkCalendar.cn"  headerClass="hide" class="hide" /> --%>
		
		<display:column class="centerColumnMana" titleKey="alWorkCalendar.thu2" media="html">
			<c:choose>
				<c:when test="${not empty item.mondayBefore}">
 					<div class="ele"><a class="imgTip_${item.mondayId}" href="#">${item.monday}</a></div><a class="imgTip_${item.mondayId}${item.mondayIdBefore}" href="#">${item.mondayBefore}</a>
				</c:when>
				<c:otherwise>
					<a class="imgTip_${item.mondayId}" href="#">${item.monday}</a>
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column class="centerColumnMana" titleKey="alWorkCalendar.thu3" media="html">
			<c:choose>
				<c:when test="${not empty item.tuesdayBefore}">
 					<div class="ele"><a class="imgTip_${item.tuesdayId}" href="#">${item.tuesday}</a></div><a class="imgTip_${item.tuesdayId}${item.tuesdayIdBefore}" href="#">${item.tuesdayBefore}</a>
				</c:when>
				<c:otherwise>
					<a class="imgTip_${item.tuesdayId}" href="#">${item.tuesday}</a>
				</c:otherwise>
			</c:choose>
		</display:column> 
		<display:column class="centerColumnMana" titleKey="alWorkCalendar.thu4" media="html">
			<c:choose>
				<c:when test="${not empty item.wednesdayBefore}">
 					<div class="ele"><a class="imgTip_${item.wednesdayId}" href="#">${item.wednesday}</a></div><a class="imgTip_${item.wednesdayId}${item.wednesdayIdBefore}" href="#">${item.wednesdayBefore}</a>
				</c:when>
				<c:otherwise>
					<a class="imgTip_${item.wednesdayId}" href="#">${item.wednesday}</a>
				</c:otherwise>
			</c:choose>
		</display:column>			
	  	<display:column class="centerColumnMana" titleKey="alWorkCalendar.thu5" media="html">
			<c:choose>
				<c:when test="${not empty item.thursdayBefore}">
 					<div class="ele"><a class="imgTip_${item.thursdayId}" href="#">${item.thursday}</a></div><a class="imgTip_${item.thursdayId}${item.thursdayIdBefore}" href="#">${item.thursdayBefore}</a>
				</c:when>
				<c:otherwise>
					<a class="imgTip_${item.thursdayId}" href="#">${item.thursday}</a>
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column class="centerColumnMana" titleKey="alWorkCalendar.thu6" media="html">
			<c:choose>
				<c:when test="${not empty item.fridayBefore}">
 					<div class="ele"><a class="imgTip_${item.fridayId}" href="#">${item.friday}</a></div><a class="imgTip_${item.fridayId}${item.fridayIdBefore}" href="#">${item.fridayBefore}</a>
				</c:when>
				<c:otherwise>
					<a class="imgTip_${item.fridayId}" href="#">${item.friday}</a>
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column class="centerColumnMana" titleKey="alWorkCalendar.thu7" media="html">
			<c:choose>
				<c:when test="${not empty item.saturdayBefore}">
 					<div class="ele"><a class="imgTip_${item.saturdayId}" href="#">${item.saturday}</a></div><a class="imgTip_${item.saturdayId}${item.saturdayIdBefore}" href="#">${item.saturdayBefore}</a>
				</c:when>
				<c:otherwise>
					<a class="imgTip_${item.saturdayId}" href="#">${item.saturday}</a>
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column class="centerColumnMana" titleKey="alWorkCalendar.cn" media="html">
			<c:choose>
				<c:when test="${not empty item.sundayBefore}">
 					<div class="ele"><a class="imgTip_${item.sundayId}" href="#">${item.sunday}</a></div><a class="imgTip_${item.sundayId}${item.sundayIdBefore}" href="#">${item.sundayBefore}</a>
				</c:when>
				<c:otherwise>
					<a class="imgTip_${item.sundayId}" href="#">${item.sunday}</a>
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column property="shift" titleKey="alWorkCalendar.caTruc" media="html" headerClass="hide" class="hide SHIFT_VALUE" />
		
		<%-- <display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />  --%>
	</display:table>
</div>
<div></div>
<h2><fmt:message key="catruc.dsNoiDung"/></h2>
<div>
	<display:table name="${workList}"  class="simple2" id="itemNoiDung" requestURI="" pagesize="50" sort="external" defaultsort="1" >
	  	<display:column property="tenCongViec" titleKey="title.qLDanhSachCongViec.tenCongViec"  sortable="true" style="width:200px" sortName="TEN_CONG_VIEC"/>
		<display:column property="noiDung" titleKey="title.qLDanhSachCongViec.noiDung" sortable="true" sortName="NOI_DUNG" />
	  	<display:column property="nguoiGiaoViec" titleKey="title.qLDanhSachCongViec.nguoiGiaoViec" sortable="true" sortName="NGUOI_GIAO_VIEC" style="width:100px"/>
	  	<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="width:60px;">
				<a href="list.htm?action=&year=${year}&week=${week}&department=${department}&team=${team}&email=&tenCongViec=&noiDung=&idwork=${itemNoiDung.id}&region=${region}">Edit</a>&nbsp;
	 			<a href="deleteNoiDung.htm?idwork=${itemNoiDung.id}&user=${itemNoiDung.nguoiGiaoViec}&region=${region}" onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')">Delete</a>&nbsp;
	 		</display:column>
	   	
	</display:table>
</div>
<div>
	<table class="simple2"> 
      <tr>
           <td colspan="2">
           		<b><span><fmt:message key="qLDanhSachCongViec.noiDungCaTruc"/></span></b>
           </td>
      </tr>
      <tr>
           <td style="width:120px;">
           		<span><fmt:message key="title.qLDanhSachCongViec.tenCongViec"/></span>
           </td>
           <td>
           		<div id='tenCongViecCB'></div>
           		<input type="hidden" id="idwork" name="idwork" value="${idwork}"/>
           		<input type="hidden" id="tenCongViec" name="tenCongViec" value="${tenCongViec}"/>
           </td>
      </tr>
      <tr>
           <td colspan="2">
           		<span><fmt:message key="title.qLDanhSachCongViec.noiDung"/></span>
           </td>
      </tr>
      <tr>	
      		<td colspan="2"> 
      		<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
          		<textarea style="width:100%; height: 220px" name="noiDung" id="noiDung" maxlength="900" >${noiDung}</textarea>
			 <script type="text/javascript">
				CKEDITOR.replace( 'noiDung',
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
      	<td align="left">
      		<input id="btsave" type="submit" class="button" name="btsave" onclick="setAction('saveContent')" value="<fmt:message key="global.form.luulai"/>" />
			<input type="button" class="button"  id="btreset" name="btreset" value="<fmt:message key="global.form.reset"/>">
      	</td>
      	<td align="right">
      		<c:if test="${isRoleManager == 'Y' }">
				<input id="shiftMode" type="submit" class="button" name="shiftMode" onclick="setAction('shiftMode')" value="${shiftMode}" >
      		</c:if>
      	</td>
      </tr>
	</table>
</div>

<table style="width:100%;border: 1px solid #c4cde0; padding: 5px;">
	<tr>
		<td style="width:100px;"><b><fmt:message key="alWorkCalendar.emailNhan"/></b></td>
		<td>
			<input type="text" id="email" name="email" value="${email}"  style="width:90%;"/>
			
		</td>
		<td style="width:70px;"><input id="btnSendMail" type="submit" class="button" name="btnSendMail" onclick="setAction('sendMail')" value="<fmt:message key="button.sendMail"/>" />
			<input type="hidden" id="action" name="action"></td>
	</tr>
	<tr>
		<td><b><fmt:message key="alWorkCalendar.subjectMail"/></b></td>
		<td colspan="2">
			<input type="text" id="subject" name="subject" value="${subject}" style="width:100%;"/>
			
		</td>
	</tr>
		<tr>	
			<td><b><fmt:message key="alWorkCalendar.conentHeader"/></b></td>
      		<td colspan="2"> 
      		<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
          		<textarea style="width:100%; height: 150px" name="headerContent" id="headerContent" maxlength="900" >${headerContent}</textarea>
			 <script type="text/javascript">
				CKEDITOR.replace( 'headerContent',
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
</table>
</form>
<script type="text/javascript">
var theme = getTheme();
$('#btSearch').jqxButton({ theme: theme });
$('#btnSendMail').jqxButton({ theme: theme });
$('#btreset').jqxButton({ theme: theme });
$('#btsave').jqxButton({ theme: theme });
$('#shiftMode').jqxButton({ theme: theme });
$("#email").jqxInput({ width: '99%', height: 20, minLength: 1, theme: theme });
$("#subject").jqxInput({ width: '100%', height: 20, minLength: 1, theme: theme });
</script>
<script type="text/javascript">
function CalcKeyCode(aChar) {
	  var character = aChar.substring(0,1);
	  var code = aChar.charCodeAt(0);
	  return code;
	}

function checkNumber(val) {
  var strPass = val.value;
  var strLength = strPass.length;
  for(var i =0;i<strLength+1;i++){
  var lchar = val.value.charAt((strLength) - i);
  var cCode = CalcKeyCode(lchar);
  if (cCode < 48 || cCode > 57 ) {
    var myNumber = val.value.substring(0, (strLength) - i);
    val.value = myNumber;
  }
  }
  
  return false;
}
</script>
<script type="text/javascript">
$(function() {
	${highlight}	
	${highlightTruongCa}
	
	var i = -1;
	$('#item>tbody>tr').each(function(){ 
		
		var TRUONG_CA = $(this).find(".TRUONG_CA").text().toLowerCase();
		if(TRUONG_CA == 'trưởng ca'){
			i = 1;
		}
		i++;
		if(i%2==0 && TRUONG_CA != 'trưởng ca' && TRUONG_CA != '')
			$(this).find("td").css({'background-color' : '#FFFFFF'});
	});
}); 

 $(document).ready(function(){
	var trs= $('#item tbody tr').first().html(); 
	trs='<tr>';
	trs=trs + '<th style="background-color: #EEEEEE;" rowspan="2"><fmt:message key="alWorkCalendar.caTruc"/></th>';
	trs=trs + '<th style="background-color: #EEEEEE;" rowspan="2"><fmt:message key="alWorkCalendar.function"/></th>';
	trs=trs + '<th style="background-color: #EEEEEE;"><fmt:message key="alWorkCalendar.thu2"/></th>';
	trs=trs + '<th style="background-color: #EEEEEE;"><fmt:message key="alWorkCalendar.thu3"/></th>';
	trs=trs + '<th style="background-color: #EEEEEE;"><fmt:message key="alWorkCalendar.thu4"/></th>';
	trs=trs + '<th style="background-color: #EEEEEE;"><fmt:message key="alWorkCalendar.thu5"/></th>';
	trs=trs + '<th style="background-color: #EEEEEE;"><fmt:message key="alWorkCalendar.thu6"/></th>';
	trs=trs + '<th style="background-color: #EEEEEE;"><fmt:message key="alWorkCalendar.thu7"/></th>';
	trs=trs + '<th style="background-color: #EEEEEE;"><fmt:message key="alWorkCalendar.cn"/></th>';
	trs=trs + '</tr>';
	trs=trs +'<tr>';
	trs=trs +'<th style="background-color: #EEEEEE;">${mondayValue}</th>';
	trs=trs +'<th style="background-color: #EEEEEE;">${tuesdayValue}</th>';
	trs=trs +'<th style="background-color: #EEEEEE;">${wednesdayValue}</th>';
	trs=trs +'<th style="background-color: #EEEEEE;">${thursdayValue}</th>';
	trs=trs +'<th style="background-color: #EEEEEE;">${fridayValue}</th>';
	trs=trs +'<th style="background-color: #EEEEEE;">${saturdayValue}</th>';
	trs=trs +'<th style="background-color: #EEEEEE;">${sundayValue}</th>';
	trs=trs +'</tr>';
	$('#item thead').html(trs);
	
});
 
 function setAction(value) {
		var action = document.getElementById("action");
		action.value = value;

		return true;
	}
 
 $(function(){
	   strikeThrough($('.ele')); 
	});

	function strikeThrough(ele){
	  ele.css('textDecoration', 'line-through');   
	}
</script>

<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/js/tinyTips/tinyTips.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tinyTips/jquery.tinyTips.js"></script>

${tinyTips}
<script type="text/javascript">
$('#department').change(function(){
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
	
	$.getJSON("${pageContext.request.contextPath}/alarm/al-work-calendar-shift/loadTeam.htm", {department: $('#department').val()}, function(j){
		 var options = '';
		 options += '<option value="">--Tất cả--</option>';
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].deptValue + '">' + j[i].deptName + '</option>';
			}
		$("#team").html(options);
		$('#team option:first').attr('selected', 'selected');
	});
});

//Create a jqxComboBox vendor
	var urlTencongviec = "${pageContext.request.contextPath}/ajax/getTenCongViec.htm";
	// prepare the data
	var sourceTencongviec  =
	{
	    datatype: "json",
	    datafields: [
	        { name: 'name' }
	    ],
	    url: urlTencongviec ,
	    async: false
	};
	var dataAdapterTencongviec  = new $.jqx.dataAdapter(sourceTencongviec );
	// Create a jqxComboBox
	$("#tenCongViecCB").jqxComboBox({ source: dataAdapterTencongviec , displayMember: "name", valueMember: "name", width: 300,height: '20px',itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
	var tenCongViec =  "<c:out value='${tenCongViec}'/>";
	if(tenCongViec=="")
		{
			$("#tenCongViecCB").jqxComboBox({selectedIndex: 0 }); 
			var item = $("#tenCongViecCB").jqxComboBox('getItem', 0 ); 
			$('#tenCongViec').val(item.value);
		}
	else
		$("#tenCongViecCB").val(tenCongViec); 
	$('#tenCongViecCB').on('change', function (event) 
			{
			    var args = event.args;
			    if (args) {
			    // index represents the item's index.                          
			    var item = args.item;
			    // get item's label and value.
			    var label = item.label;
			   	$('#tenCongViec').val(label);
			}
			}); 
			      
//reset lai noi dung
$('#btreset').on('click', function () { 
	//alert("aaa");
	//$("#noiDung").val("");
	CKEDITOR.instances.noiDung.setData( '' );
	
});
</script>