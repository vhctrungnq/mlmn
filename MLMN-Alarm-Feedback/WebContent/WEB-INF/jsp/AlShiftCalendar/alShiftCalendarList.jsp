<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
  <style type="text/css">
 table.simple2 td.Thu2,td.Thu3,td.Thu4,td.Thu5, td.Thu6, td.Thu7, td.cn
 {
	width: 145px;
 }
  .styleArea{
  }
 
	#doublescroll { overflow: auto; overflow-y: hidden; }   
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
     #dvtTeamProcess {
     visibility: visible !important;
    }
   
    .ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	* we use height instead, but this forces the menu to always be this tall
	*/
	* html .ui-autocomplete {
		height: 200px;
	}
</style>
<title>${title}</title>
<content tag="heading">${title}</content><div>
<form id="formId" method="post" action="list.htm" name = 'frmSample' onSubmit="return checkValidateForm()">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
<table style = "width:50% ;border: 1px solid #c4cde0;
	padding: 5px;">
		<tr>	
			<td  style="width:40px"><fmt:message key="alshiptcalendar.week" /></td>
			<td  style="width:40px">
				<input type="text" value="${week}" name="week" id="week" class="wid100" onchange ="javascript:checkNumber(document.frmSample.week);"/>
			</td>
			<td  style="width:40px; padding-left:10px">năm</td>
			<td  style="width:40px">
				<input type="text" value="${year}"  name="year" id="year" class="wid100" onchange ="javascript:checkNumber(document.frmSample.year);"/>
			</td>
			<td style="width:50px;padding-left:10px">Tổ</td>
			<td  style="width:110px">
				<select name="team" id="team" class="wid150" onchange="xl()">
					<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${teamList}">
						<c:choose>
			                <c:when test="${item.value == team}">
			                    <option value="${item.value}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.value}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
			</td>
			<td style="width:70px;padding-left:10px"><fmt:message key="alshiptcalendar.area" /></td>
			<td  style="width:160px">
				<select  name="area" id="area" class="wid150" onchange="xl()">
					<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${areaList}">
						<c:choose>
			                <c:when test="${item.name == area}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
			</td>
			<td  style="width:70px">
				<input class="button" type="submit" id="submit" value="<fmt:message key="button.search"/>"/>
			</td> 
			
			
		</tr>
	</table>
	
</form>
</div>
<table style = "width:100%">
		<tr>
			<td align="right"  style="width:70px">
			<c:if test="${checkCaTruc=='2'}">
	            <a href="upload.htm"><fmt:message key="button.upload"/></a>&nbsp;
	        </c:if>
	      
	        </td>
		</tr>
</table>
<br>

<table style = "width:50%;">
	<tr>
		<td style = "text-align:center;">
		<b style = "font-family:Times New Roman,Georgia,Serif;font-size:20px;"><fmt:message key="TRỰC LÃNH ĐẠO VÀ ĐIỀU HÀNH THÔNG TIN"/></b>
		</td>
	</tr>
	
	<tr>
		<td style = "text-align:center;">
		<b>	TỪ NGÀY ${sDate} ĐẾN NGÀY ${eDate}</b>
		</td>
	</tr>
</table>
<div style="overflow: auto;">
	<display:table name="${vAlShiftLeader}" style = "width:50%" class="simple2" id="vAlShiftLeader" requestURI="" pagesize="10" export="true" >
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px !important;"> <c:out value="${vAlShiftLeader_rowNum}" /> </display:column>
	  	<display:column property="team" titleKey="alshiptcalendar.team"  />
		<display:column property="name" titleKey="Tên"  />
		<display:column property="regency" titleKey="Chức vụ" />
		<c:if test="${checkCaTruc=='2'}">
	          <display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="width:50px !important;">
			<a href="deleteLeader.htm?id=${vAlShiftLeader.id}"
					onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')">
			<fmt:message key="button.delete"/></a>&nbsp;
			</display:column>
	     </c:if>
	     
	</display:table>
	
</div>



<br>
<table style = "width:100%;">
<tr>
	<td style = "width:30%;">
		<table>
			<tr>
				<td>
				<p style = "font-family:Times New Roman,Georgia,Serif;font-size:20px;"><fmt:message key="alshiptcalendar.tt"/></p>
				</td>
			</tr>
			<tr>
				<td style = "text-align:center;">
				<b style = "font-family:Times New Roman,Georgia,Serif;font-size:15px;">	<fmt:message key="alshiptcalendar.ddh"/></b>
				</td>
			</tr>
			<tr>
				<td style = "font-family:Times New Roman,Georgia,Serif;font-size:15px;text-align:center;">
				<b>Tổ ${team} - Khu vực ${area}</b>
				</td>
			</tr>
		</table>
	</td>
	<td style = "width:50%;">
	<table>
			<tr>
				<td style = "padding-left: 30px; ">
				<b style = "font-family:Times New Roman,Georgia,Serif;font-size:25px;"><fmt:message key="alshiptcalendar.ltc"/></b>
				</td>
			</tr>
			
			<tr>
				<td>
				<b>	Từ ngày ${sDate} đến ngày ${eDate}</b>
				</td>
			</tr>
		</table>
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
	<display:table name="${vAlShiftList}" class="simple2" id="vAlShiftList" requestURI="" pagesize="50"  export="true" >
	  	<%-- <display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px !important;"> <c:out value="${vAlShift_rowNum}" /> </display:column> --%>
	  	<display:column property="session" titleKey="alshiptcalendar.session" sortName="SESSION"  class = "session" group="1" />
		<c:choose>
		<c:when test="${edit==true}">
		 	<display:column titleKey="alshiptcalendar.thu2"  class="Thu2">
				<input type = "text"  value="${vAlShiftList.thu2}" id = "valuet2_${vAlShiftList_rowNum}" >
			</display:column>
			<display:column titleKey="alshiptcalendar.thu3" class="Thu3">
				<input type = "text"  value="${vAlShiftList.thu3}" id = "valuet3_${vAlShiftList_rowNum}" >
			</display:column>
			<display:column titleKey="alshiptcalendar.thu4"   class="Thu4">
				<input type = "text"  value="${vAlShiftList.thu4}" id = "valuet4_${vAlShiftList_rowNum}" >
			</display:column>
			<display:column titleKey="alshiptcalendar.thu5"  class="Thu5">
				<input type = "text"  value="${vAlShiftList.thu5}" id = "valuet5_${vAlShiftList_rowNum}" >
			</display:column>
			<display:column titleKey="alshiptcalendar.thu6"   class="Thu6">
				<input type = "text"  value="${vAlShiftList.thu6}" id = "valuet6_${vAlShiftList_rowNum}" >
			</display:column>
			<display:column titleKey="alshiptcalendar.thu7" class="Thu7">
				<input type = "text"  value="${vAlShiftList.thu7}" id = "valuet7_${vAlShiftList_rowNum}" >
			</display:column>
			<display:column titleKey="alshiptcalendar.cn"   class="cn">
				<input type = "text"  value="${vAlShiftList.cn}" id = "valuecn_${vAlShiftList_rowNum}" >
			</display:column>
		</c:when>
		<c:otherwise>
			<display:column property="thu2" titleKey="alshiptcalendar.thu2"  class="Thu2"/>
			<display:column property="thu3" titleKey="alshiptcalendar.thu3"  class="Thu3"/>
		  	<display:column property="thu4" titleKey="alshiptcalendar.thu4"  class="Thu4"/>
			<display:column property="thu5" titleKey="alshiptcalendar.thu5"  class="Thu5"/>
			<display:column property="thu6" titleKey="alshiptcalendar.thu6"  class="Thu6"/>
			<display:column property="thu7" titleKey="alshiptcalendar.thu7" class="Thu7"/>
			<display:column property="cn" titleKey="alshiptcalendar.cn"  class="cn" />
			<%-- <c:if test="${checkCaTruc==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" >
				
			 		<a href="delete.htm?id=${vAlShiftList.id}"
						onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')">
					<fmt:message key="button.delete"/></a>&nbsp;
			 	
			 </display:column>
			 </c:if> --%>
		</c:otherwise>
		</c:choose>
	</display:table>
	
	<table style = "width: 99%">
	<tr>
	<td>
		<input type = "text" value = "${row_num}" style="display: none;" id = "txtRowNum"/>
			<input type = "text" value = "${rowId}" style="display: none;" id = "rowId"/>
			<div style="margin-left:600px;display: none;" class="loading fl">
				<img src="${pageContext.request.contextPath}/images/icon/barIndicator.gif">
			</div>
	</td>
	</tr>
	<tr>
		<td >
			<div style = "width:99% ;border: 1px solid #c4cde0;
			padding: 5px;">
			<b>Lưu ý</b>
			<c:choose>
				<c:when test="${edit==true}">
					<textarea cols="80" rows="7" name="notice" id="txtnotice" maxlength="900" style="width:100%" >${notice}</textarea>
				</c:when>
				<c:otherwise>
					${notice}
				</c:otherwise>
			</c:choose>
			</div>
		</td>
	</tr>
	
	 <tr>
		<td align="left">
		<c:if test="${checkCaTruc=='1' || checkCaTruc=='2'}">
			<input id ="btnDoica" type="button" class="button" value="<fmt:message key="Đổi ca"/>">
			<c:if test="${edit==true}">
				<input id = "btnSave" type="button" class="button" name="save" value="<fmt:message key="button.save"/>" />
			</c:if>
		</c:if>
		<c:if test="${checkCaTruc=='2'}">
			<input id = "btnDelete" type="button" class="button" name="delete" value="<fmt:message key="Xóa toàn bộ"/>" />
		</c:if>
		</td>
	</tr>
		
			
	
	
	</table>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript">
var week = $("#week").val();
var year = $("#year").val();
var team = $("#team").val();
var area = $("#area").val();
$('#btnDoica').click(function() {
	window.open('${pageContext.request.contextPath}/system/alshiftcalendar/list.htm?edit=true&week='+ week + '&year=' + year + '&team=' + team + '&area=' + area, '_self');
	
});	
$("#btnSave").live("click", function(event){
	$(".loading").css('display', 'block');
	var rowId = $("#rowId").val();
	var row_num = $("#txtRowNum").val();
	var valuenotice = "";
	valuenotice =  $("#txtnotice").val();
	var valuet2 = "",valuet3 = "", valuet4 = "",valuet5 = "",valuet6 = "", valuet7 = "",valuecn = "";
	for(var i = 1; i <= row_num; i++)
	{
		valuet2 = valuet2 + $("#valuet2_"+i).val() + ",";
		valuet3 = valuet3 + $("#valuet3_"+i).val() + ",";
		valuet4 = valuet4 + $("#valuet4_"+i).val() + ",";
		valuet5 = valuet5 + $("#valuet5_"+i).val() + ",";
		valuet6 = valuet6 + $("#valuet6_"+i).val() + ",";
		valuet7 = valuet7 + $("#valuet7_"+i).val() + ",";
		valuecn = valuecn + $("#valuecn_"+i).val() + ",";
	}
	//alert(valuecn);
	
	$.ajax({
		url: "${pageContext.request.contextPath}/system/alshiftcalendar/form/save-alshift.htm",
		type:"POST",
		data:{ key_t2: valuet2,key_t3: valuet3,key_t4: valuet4,key_t5: valuet5,key_t6: valuet6,key_t7: valuet7,key_cn: valuecn, key_id: rowId, key_notice: valuenotice },
		error: function(){},
		beforeSend: function(){},
		complete: function(){},
		success: function(data){
		if(data.status == 'success')
		{
			$(".loading").css('display', 'none');
			alert('Cập nhật thành công');
		}
		window.open('${pageContext.request.contextPath}/system/alshiftcalendar/list.htm?week='+ week + '&year=' + year + '&team=' + team + '&area=' + area, '_self');
		}
		
	
	});
	
});
$('#btnDelete').click(function() {
	var x=window.confirm("Bạn có chắc muốn xóa toàn bộ lịch trực ca?");
	if (x)
	{
		window.open('${pageContext.request.contextPath}/system/alshiftcalendar/delete.htm?week='+ week + '&year=' + year + '&team=' + team + '&area=' + area, '_self');

	}
	
});
</script>
<script type="text/javascript">
<!--
$('#vAlShiftList>tbody>tr').each( function(){
	var thu2 = $(this).find(".Thu2").text();
	var thu3 = $(this).find(".Thu3").text();
	var thu4 = $(this).find(".Thu4").text();
	var thu5 = $(this).find(".Thu5").text();
	var thu6 = $(this).find(".Thu6").text();
	var thu7 = $(this).find(".Thu7").text();
	var cn = $(this).find(".cn").text();
	var session = $(this).find(".session").text();
	var rowIndex = $(this).closest("tr").index();
	if(rowIndex >=2 && rowIndex <= 9)
	{
		$(this).find(".Thu2").css('background','#CCFFFF');
		$(this).find(".Thu3").css('background','#FFFFCC');
		$(this).find(".Thu4").css('background','#FFFF66');
		$(this).find(".Thu5").css('background','#FFA07A');
		$(this).find(".Thu6").css('background','#FFC0CB');
		$(this).find(".Thu7").css('background','#CCFFFF');
		$(this).find(".cn").css('background','#FFFFCC');
	}
	if(rowIndex >=10 && rowIndex <= 17)
	{
		$(this).find(".Thu2").css('background','#FFFF66');
		$(this).find(".Thu3").css('background','#FFA07A');
		$(this).find(".Thu4").css('background','#FFC0CB');
		$(this).find(".Thu5").css('background','#CCFFFF');
		$(this).find(".Thu6").css('background','#FFFFCC');
		$(this).find(".Thu7").css('background','#FFFF66');
		$(this).find(".cn").css('background','#FFA07A');
	}
	if(rowIndex >=18 && rowIndex <= 25)
	{
		$(this).find(".Thu2").css('background','#FFC0CB');
		$(this).find(".Thu3").css('background','#CCFFFF');
		$(this).find(".Thu4").css('background','#FFFFCC');
		$(this).find(".Thu5").css('background','#FFFF66');
		$(this).find(".Thu6").css('background','#FFA07A');
		$(this).find(".Thu7").css('background','#FFC0CB');
		$(this).find(".cn").css('background','#CCFFFF');
	}

	
	if(session.indexOf("Ngày")>-1)
	{
		$(this).css('background','#FFFF99');
	}
	if(session.indexOf("Khu vực")>-1)
	{
		$(this).css('background','#B0E0E6');
		$(this).css('color','#EE0000');
	}
	if(thu2.indexOf("*") > -1)
	{
		$(this).find(".Thu2").css('font-weight','bold');
	}
	if(thu3.indexOf("*") > -1)
	{
		
		
		$(this).find(".Thu3").css('font-weight','bold');
	}
	if(thu4.indexOf("*") > -1)
	{
		
		
		$(this).find(".Thu4").css('font-weight','bold');
	}
	if(thu5.indexOf("*") > -1)
	{
		
		
		$(this).find(".Thu5").css('font-weight','bold');
	}
	if(thu6.indexOf("*") > -1)
	{
	
		
		$(this).find(".Thu6").css('font-weight','bold');
	}
	if(thu7.indexOf("*") > -1)
	{
		
		
		$(this).find(".Thu7").css('font-weight','bold');
	}
	if(cn.indexOf("*") > -1)
	{
		
	
		$(this).find(".cn").css('font-weight','bold');
	}
});
//-->
</script>
<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}
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