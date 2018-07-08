
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<c:choose>
<c:when test="${type=='TW'}">
<title>CẬP NHẬT THÔNG TIN TRẠM THANH TOÁN ĐIỆN TW</title>
<content tag="heading">CẬP NHẬT THÔNG TIN TRẠM THANH TOÁN ĐIỆN TW</content> 	
</c:when>
<c:otherwise>
<title>CẬP NHẬT THÔNG TIN TRẠM THANH TOÁN ĐIỆN</title>
<content tag="heading">CẬP NHẬT THÔNG TIN TRẠM THANH TOÁN ĐIỆN</content> 	
</c:otherwise>
</c:choose>
<form:form commandName="qldnTramTTDien" method="post" action="form.htm"> 
	<input type="hidden" id="type" name ="type"  value="${type}"/>
	<form:hidden path="id"/>
	<%-- <div style="width:30%;float: left;"  id="divTimKiem" >
		<table>
			<tr>
				<td colspan="2"><b><span>---- Tìm kiếm trạm chưa thanh toán ------</span></b></td>
			</tr>
			<tr>
				<td style="width: 120px;"><fmt:message key="qldnTramTTDien.tgttTq"/><font color="red">(*)</font></td>
				<td>
					<div id="tgttTqTK" name="tgttTqTK"></div>
				</td>
			</tr>
			
			<tr>
				<td style="width: 120px;"><fmt:message key="qldnTramTTDien.thangQuyTt"/><font color="red">(*)</font></td>
				<td >
					<input type ="text" id="thangQuyTtTK" name="thangQuyTtTK" value="${thangQuyTtTK}" maxlength="10" style="width: 80px;" />
					&nbsp;- &nbsp;<fmt:message key="qldnTramTTDien.namTt"/><font color="red">(*)</font>&nbsp;
					<input type ="text" id="namTtTK" name="namTtTK" value="${namTtTK}" maxlength="10"  style="width: 80px;" />
	     		</td>
	     	</tr>
	     	<tr>
				<td  style="width:120px;"><fmt:message key="qldnTramTTDien.tinh"/></td>
				<td>
					<input type="text" id="tinh" name="tinh" style="width: 100px;"/>
				</td>
				
			</tr>
			<tr>
	     		<td style="width:120px;"><fmt:message key="qldnTramTTDien.huyen"/></td>
				<td >
					<input type="text" id="huyen" name="huyen" style="width: 100px;"/>
				</td>
				
			</tr>
	         <tr>
	         	<td  colspan="2" align="center">
					<input class="button" type="button" id="btFilter" name="btFilter" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
	         </tr>
	        <tr>
	        	<td colspan="2" ><b>Danh sách trạm chưa thanh toán</b> </td>
			</tr>
	        <tr>
	        	<td colspan="2" style="width:100%;">
	         		  <div id="dsMaTram"></div>
	         	</td>
	        </tr>
			
		</table>
	</div>
	 --%>
	<div style="width:100%;overflow: auto;float: right;" >
		<table class="simple2"> 
			<tr>
				<td colspan="4"><b><span>---- Thanh toán trạm -----</span></b></td>
			</tr>
			<tr>
				<td style="width: 150px;"><fmt:message key="QldnThongTinTram.makh"/><font color="red">(*)</font></td>
				<td style="width: 250px;">
					<form:input type ="text" path="idTram" maxlength="400"  class = "long" rows="3"/><font color="red">${erroridTram}</font>
				</td>
				<td style="width: 180px;"><fmt:message key="qldnTramTTDien.hinhThucTt"/></td>
				<td>
					<form:input type ="text" path="hinhThucTt" maxlength="400"  class = "long" rows="3" />
					<input type="hidden" id="tienDienThangTr" />
					<input type="hidden" id="dienNangTtThangTr"/>
					<input type="hidden" id="dgLoai"/>
					<input type="hidden" id="dg1Gia"/>
					<input type="hidden" id="dg3Muc1"/>
					<input type="hidden" id="dg3Muc2"/>
					<input type="hidden" id="dg3Muc3"/>
					
	         	</td>
			</tr>
	         <tr>
	         	<td><fmt:message key="qldnTramTTDien.tuNgay"/><font color="red">(*)</font></td>
				<td>
						<input type ="text"  value="${tuNgay}" name="tuNgay" id="tuNgay" size="17" maxlength="19" style="width:150px">
			   			 <img alt="calendar" title="Click to choose the from date" id="choosetuNgay" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						<font color="red">${errortuNgay}<form:errors path="tuNgay"/></font>
				</td>
				<td><fmt:message key="qldnTramTTDien.denNgay"/><font color="red">(*)</font></td>
				<td>
						<input type ="text"  value="${denNgay}" name="denNgay" id="denNgay" size="17" maxlength="19" style="width:150px">
			   			 <img alt="calendar" title="Click to choose the from date" id="choosedenNgay" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						<font color="red">${errordenNgay}<form:errors path="denNgay"/></font>
				</td>
	         </tr>
	         <tr>
				<td><fmt:message key="qldnTramTTDien.csc"/></td>
	         	<td colspan="3">
					<form:input type ="text" path="csc1" maxlength="400"  class = "long" rows="3" />
					<!-- <div id="dg3Gia" style="display:none"> -->
	         	 		&nbsp;&nbsp;-&nbsp;&nbsp;
	         	 		<form:input type ="text" path="csc2" maxlength="400"  class = "long" rows="3" />
	         			&nbsp;&nbsp;-&nbsp;&nbsp;
	         			<form:input type ="text" path="csc3" maxlength="400"  class = "long" rows="3" />
					<!-- </div> -->
				</td>
	        </tr>
			<tr>
				<td><fmt:message key="qldnTramTTDien.csm"/></td>
	         	<td colspan="3">
					<form:input type ="text" path="csm1" maxlength="400"  class = "long" rows="3" />
					<!-- <div id="dg3Gia" style="display:none"> -->
	         	 		&nbsp;&nbsp;-&nbsp;&nbsp;
	         	 		<form:input type ="text" path="csm2" maxlength="400"  class = "long" rows="3" />
	         			&nbsp;&nbsp;-&nbsp;&nbsp;
	         			<form:input type ="text" path="csm3" maxlength="400"  class = "long" rows="3" />
					<!-- </div> -->
					
				</td>
	        </tr>
	        <tr>
	         	
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.tienTt"/></td>
				<td>
					<form:input type ="text" path="tienTt" maxlength="400"  class = "long" rows="3" />
	         	</td>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.ghiChu"/></td>
				<td>
					<form:input type ="text" path="ghiChu" maxlength="400"  class = "long" rows="3" />
	         	</td>
	         </tr> 
	       <tr>
	           <td></td>
	           <td colspan="3">
	           		<input type="submit" class="button" id = "submit" value="<fmt:message key="global.form.luulai"/>" />
	           	  	<input type="button" class="button" id = "btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>	
	           </td>
	       </tr>
	    </table>
	 </div>
</form:form>
<style>
    .error {
    	color: red;
    }
</style> 
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

 
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"tuNgay",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "choosetuNgay",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"denNgay",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "choosedenNgay",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
  
</script>
<script type="text/javascript">
var theme =  getTheme(); 
$('#submit').jqxButton({ theme: theme });
$('#btCancel').jqxButton({ theme: theme });
var type =  "<c:out value='${type}'/>";
$(document).ready(function(){
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
    $("#tgttTqTK").jqxComboBox({ source: dataAdaptertgttTq, displayMember: "name", valueMember: "name", width: 250, height: 20, theme: theme,autoOpen: true  });
    var tgttTqTK =  "<c:out value='${tgttTqTK}'/>";
    if(tgttTqTK=="")
		$('#tgttTqTK').val('ALL');
	else
		$('#tgttTqTK').val(tgttTqTK); 
	 //input causeby
        $("#idTram").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
		$("#thangQuyTt").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
	  	$("#namTt").jqxInput({ height: 20, width: '20%', minLength: 0, maxLength: 400, theme: theme});
	    $("#csm1").jqxInput({ placeHolder: "Chỉ số mới 1", height: 20, width: '30%', minLength: 0, maxLength: 400, theme: theme});
	    $("#csm2").jqxInput({ placeHolder: "Chỉ số mới 2",height: 20, width: '30%', minLength: 0, maxLength: 400, theme: theme});
	    $("#csm3").jqxInput({ placeHolder: "Chỉ số mới 3", height: 20, width: '30%', minLength: 0, maxLength: 400, theme: theme});
	    $("#dienNangTt").jqxInput({height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
	    $("#chenhLechDn").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
	    $("#tienTt").jqxInput({height: 20, width: '97%', minLength: 0, maxLength: 4000, theme: theme});
	    $("#chenhLechTien").jqxInput({height: 20, width: '97%', minLength: 0, maxLength: 4000, theme: theme});
	    $("#ghiChu").jqxInput({height: 20, width: '97%', minLength: 0, maxLength: 4000, theme: theme});
	    $("#hinhThucTt").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
	    $("#csc1").jqxInput({placeHolder: "Chỉ số cũ 1", height: 20, width: '30%', minLength: 0, maxLength: 400, theme: theme});
	    $("#csc2").jqxInput({placeHolder: "Chỉ số cũ 2",height: 20, width: '30%', minLength: 0, maxLength: 4000, theme: theme});
	    $("#csc3").jqxInput({placeHolder: "Chỉ số cũ 3", height: 20, width: '30%', minLength: 0, maxLength: 400, theme: theme});
		    
		
});
   
</script>
</body>
</html>