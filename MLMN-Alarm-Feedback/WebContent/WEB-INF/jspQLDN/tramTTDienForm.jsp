
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
	<div style="width:30%;float: left;"  id="divTimKiem" >
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
	
	<div style="width:70%;overflow: auto;float: right;" >
		<table class="simple2"> 
			<tr>
				<td colspan="4"><b><span>---- Thanh toán trạm -----</span></b></td>
			</tr>
			 <c:if test="${type!='TW'}">
	      	<tr>
	           <td style="width: 150px;"><fmt:message key="qldnTramTTDien.idTram"/><font color="red">(*)</font></td>
				<td style="width: 250px;">
					<form:input type ="text" path="idTram" maxlength="400"  class = "long" rows="3"/><font color="red">${erroridTram}</font>
				
				</td>
				<td style="width: 180px;"><fmt:message key="qldnTramTTDien.hinhThucTt"/></td>
				<td>
					<form:input type ="text" path="hinhThucTt" maxlength="400"  class = "long" rows="3" />
					<input type="hidden" id="tienDienThangTr" />
					<input type="hidden" id="dnttDk"/>
					<input type="hidden" id="dgLoai"/>
					<input type="hidden" id="dg1Gia"/>
					<input type="hidden" id="dg3Muc1"/>
					<input type="hidden" id="dg3Muc2"/>
					<input type="hidden" id="dg3Muc3"/>
					
	         	</td>
			</tr>
	        <tr>
				<td style="width: 150px;"><fmt:message key="qldnTramTTDien.tgttTq"/><font color="red">(*)</font></td>
				<td>
					<div id="tgttTq" name="tgttTq"></div><font color="red">${errortgttTq}</font>
				</td>
				<td style="width: 150px;"><fmt:message key="qldnTramTTDien.nvNhap"/></td>
				<td>
						<div id="nvNhap" name="nvNhap"></div>
				</td>
	         </tr>
	         <tr>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.thangQuyTt"/><font color="red">(*)</font></td>
				<td>
					<form:input type ="text" path="thangQuyTt" maxlength="400"  class = "long" rows="3" /><font color="red">${errorthangQuyTt}</font>
	     		</td>
	     		<td><fmt:message key="qldnTramTTDien.tuNgay"/><font color="red">(*)</font></td>
				<td>
						<input type ="text"  value="${tuNgay}" name="tuNgay" id="tuNgay" size="17" maxlength="19" style="width:150px">
			   			 <img alt="calendar" title="Click to choose the from date" id="choosetuNgay" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						<font color="red">${errortuNgay}<form:errors path="tuNgay"/></font>
				</td>
	         </tr>
	         <tr>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.namTt"/><font color="red">(*)</font></td>
				<td>
					<form:input type ="text" path="namTt" maxlength="400"  class = "long" rows="3" /><font color="red">${errornamTt}</font>
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
					<div id="dg3Gia" style="display:none">
	         	 		&nbsp;&nbsp;-&nbsp;&nbsp;
	         	 		<form:input type ="text" path="csc2" maxlength="400"  class = "long" rows="3" />
	         			&nbsp;&nbsp;-&nbsp;&nbsp;
	         			<form:input type ="text" path="csc3" maxlength="400"  class = "long" rows="3" />
					</div>
				</td>
	        </tr>
			<tr>
				<td><fmt:message key="qldnTramTTDien.csm"/></td>
	         	<td colspan="3">
					<form:input type ="text" path="csm1" maxlength="400"  class = "long" rows="3" />
					<div id="dg3Gia" style="display:none">
	         	 		&nbsp;&nbsp;-&nbsp;&nbsp;
	         	 		<form:input type ="text" path="csm2" maxlength="400"  class = "long" rows="3" />
	         			&nbsp;&nbsp;-&nbsp;&nbsp;
	         			<form:input type ="text" path="csm3" maxlength="400"  class = "long" rows="3" />
					</div>
					
				</td>
	        </tr>
	        <tr>
		        <td colspan="4">
		        	<input type="button" class="button" id = "btTinh" value="Hỗ trợ tính tiền" >	
		        </td>
		        
	        </tr>
	        <tr>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.dienNangTt"/><font color="red">(*)</font></td>
				<td>
					<form:input type ="text" path="dienNangTt" maxlength="400"  class = "long" rows="3" /><font color="red">${errorMakh}</font>
	         	</td>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.chenhLechDn"/></td>
				<td>
					<form:input type ="text" path="chenhLechDn" maxlength="400"  class = "long" rows="3" />
	         	</td>
	         </tr>
	         <tr>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.tienTt"/></td>
				<td>
					<form:input type ="text" path="tienTt" maxlength="400"  class = "long" rows="3" />
	         	</td>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.chenhLechTien"/></td>
				<td>
					<form:input type ="text" path="chenhLechTien" maxlength="400"  class = "long" rows="3" />
	         	</td>
	         </tr>
	         <tr>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.tyLe"/></td>
				<td>
					<form:input type ="text" path="tyLe" maxlength="400"  class = "long" rows="3" />
	         	</td>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.kqToKt"/></td>
				<td>
					<form:input type ="text" path="kqToKt" maxlength="400"  class = "long" rows="3" />
	         	</td>
	         </tr>
	         <tr>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.ghiChu"/></td>
				<td colspan="3">
					<form:input type ="text" path="ghiChu" maxlength="400"  class = "long" rows="3" />
	         	</td>
	         </tr>
	        </c:if>
	        <c:if test="${type=='TW'}">
	        <tr>
	           <td style="width: 150px;"><fmt:message key="qldnTramTTDien.idTram"/><font color="red">(*)</font></td>
				<td style="width: 250px;">
					<div id="idTramdiv">${qldnTramTTDien.idTram}</div><form:hidden path="idTram" />  
					
				</td>
				<td style="width: 180px;"><fmt:message key="qldnTramTTDien.hinhThucTt"/></td>
				<td>
					<div id="hinhThucTtdiv">${qldnTramTTDien.hinhThucTt}</div><form:hidden path="hinhThucTt" />
	         	</td>
			</tr>
	        <tr>
				<td style="width: 150px;"><fmt:message key="qldnTramTTDien.tgttTq"/><font color="red">(*)</font></td>
				<td>
					<div id="tgttTqdiv">${qldnTramTTDien.tgttTq}</div><form:hidden path="tgttTq"/>
				</td>
				<td style="width: 150px;"><fmt:message key="qldnTramTTDien.nvNhap"/></td>
				<td>
						<div id="nvNhapdiv">${qldnTramTTDien.nvNhap}</div><form:hidden path="nvNhap"/>
				</td>
	         </tr>
	         <tr>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.thangQuyTt"/><font color="red">(*)</font></td>
				<td>
					<div id="thangQuyTtdiv">${qldnTramTTDien.thangQuyTt}</div><form:hidden path="thangQuyTt" />
	     		</td>
	     		<td><fmt:message key="qldnTramTTDien.tuNgay"/><font color="red">(*)</font></td>
				<td>
						<div id="tuNgaydiv">${tuNgay}</div><input type="hidden" id="tuNgay" name ="tuNgay"  value="${tuNgay}"/>
				</td>
	         </tr>
	         <tr>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.namTt"/><font color="red">(*)</font></td>
				<td>
					<div id="namTtdiv">${qldnTramTTDien.namTt}</div><form:hidden path="namTt" maxlength="400"  class = "long" rows="3" /><font color="red">${errornamTt}</font>
	         	</td>
	         	<td><fmt:message key="qldnTramTTDien.denNgay"/><font color="red">(*)</font></td>
				<td>
						
						<div id="denNgaydiv">${denNgay}</div><input type="hidden" id="denNgay" name ="denNgay"  value="${denNgay}"/>
				</td>
	         </tr>
	         <tr>
				<td><fmt:message key="qldnTramTTDien.csc"/></td>
	         	<td colspan="3">
					<div id="csc1div">${qldnTramTTDien.csc1}</div><form:hidden path="csc1" maxlength="400"  class = "long" rows="3" />
					<div id="dg3Gia" style="display:none">
	         	 		&nbsp;&nbsp;-&nbsp;&nbsp;
	         	 		<div id="csc2div">${qldnTramTTDien.csc2}</div><form:hidden path="csc2" maxlength="400"  class = "long" rows="3" />
	         			&nbsp;&nbsp;-&nbsp;&nbsp;
	         			<div id="csc3div">${qldnTramTTDien.csc3}</div><form:hidden path="csc3" maxlength="400"  class = "long" rows="3" />
					</div>
					
				</td>
	        </tr>
			<tr>
				<td><fmt:message key="qldnTramTTDien.csm"/></td>
	         	<td colspan="3">
					<div id="csm1div">${qldnTramTTDien.csm1}</div><form:hidden path="csm1" maxlength="400"  class = "long" rows="3" />
					<div id="dg3Gia" style="display:none">
	         	 		&nbsp;&nbsp;-&nbsp;&nbsp;
	         	 		<div id="csm2div">${qldnTramTTDien.csm2}</div><form:hidden path="csm2" maxlength="400"  class = "long" rows="3" />
	         			&nbsp;&nbsp;-&nbsp;&nbsp;
	         			<div id="csm3div">${qldnTramTTDien.csm3}</div><form:hidden path="csm3" maxlength="400"  class = "long" rows="3" />
					</div>
					
				</td>
	        </tr>
	        
	        <tr>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.dienNangTt"/><font color="red">(*)</font></td>
				<td>
					<div id="dienNangTtdiv">${qldnTramTTDien.dienNangTt}</div><form:hidden path="dienNangTt" maxlength="400"  class = "long" rows="3" /><font color="red">${errorMakh}</font>
	         	</td>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.chenhLechDn"/></td>
				<td>
					<div id="chenhLechDndiv">${qldnTramTTDien.chenhLechDn}</div><form:hidden path="chenhLechDn" maxlength="400"  class = "long" rows="3" />
	         	</td>
	         </tr>
	         <tr>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.tienTt"/></td>
				<td>
					<div id="tienTtdiv">${qldnTramTTDien.tienTt}</div><form:hidden path="tienTt" maxlength="400"  class = "long" rows="3" />
	         	</td>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.chenhLechTien"/></td>
				<td>
					<div id="chenhLechTiendiv">${qldnTramTTDien.chenhLechTien}</div><form:hidden path="chenhLechTien" />
	         	</td>
	         </tr>
	         <tr>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.tyLe"/></td>
				<td>
					<div id="tyLediv">${qldnTramTTDien.tyLe}</div><form:hidden path="tyLe"  /> 
					
	         	</td>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.kqToKt"/></td>
				<td>
					<div id="kqToKtdiv">${qldnTramTTDien.kqToKt}</div><form:hidden path="kqToKt"  />
	         	</td>
	         </tr>
	         <tr>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.ghiChu"/></td>
				<td colspan="3">
					<div id="ghiChudiv">${qldnTramTTDien.ghiChu}</div><form:hidden path="ghiChu"/>
	         	</td>
	         </tr>
	         <tr>
	         	<td colspan="4"><b>---- Thanh toán tạm ứng ----- </b></td>
	         </tr>
			<tr>
				<td style="width: 150px;"><fmt:message key="qldnTramTTDien.ngayTttw"/><font color="red">(*)</font></td>
				<td>
					<input type ="text"  value="${ngayTttw}" name="ngayTttw" id="ngayTttw" size="17" maxlength="19" style="width:150px">
				   	<img alt="calendar" title="Click to choose the to date" id="choosengayTttw" style="cursor: pointer; position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					<font color="red">${errorngayTttw}<form:errors path="ngayTttw"/></font>
				</td>
				
				<td style="width: 150px;"><fmt:message key="qldnTramTTDien.ngayUnc"/><font color="red">(*)</font></td>
				<td>
					<input type ="text"  value="${ngayUnc}" name="ngayUnc" id="ngayUnc" size="17" maxlength="19" style="width:150px">
				   	<img alt="calendar" title="Click to choose the to date" id="choosengayUnc" style="cursor: pointer; position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					<font color="red">${errorngayUnc}<form:errors path="ngayUnc"/></font>
				</td>
				
	     	 </tr> 
	         
			<tr>	
				<td style="width: 150px;"><fmt:message key="qldnTramTTDien.soHoaDon"/></td>
				<td>
					<form:input type ="text" path="soHoaDon" maxlength="400"  class = "long" rows="3" />
	         	</td>
	         	<td style="width: 150px;"><fmt:message key="qldnTramTTDien.soHskt"/></td>
				<td>
					<form:input type ="text" path="soHskt" maxlength="400"  class = "long" rows="3" />
	         	</td>
	         </tr>
	         </c:if>
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
    var type =  "<c:out value='${type}'/>";
    if (type=='TW')
    	{ 	
		    Calendar.setup({
		        inputField		:	"ngayTttw",	// id of the input field
		        ifFormat		:	"%d/%m/%Y",   	// format of the input field
		        button			:   "choosengayTttw",  	// trigger for the calendar (button ID)
		        showsTime		:	true,
		        singleClick		:   false					// double-click mode
		    });
		    Calendar.setup({
		        inputField		:	"ngayUnc",	// id of the input field
		        ifFormat		:	"%d/%m/%Y",   	// format of the input field
		        button			:   "choosengayUnc",  	// trigger for the calendar (button ID)
		        showsTime		:	true,
		        singleClick		:   false					// double-click mode
		    });
    	}
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
	if (type!='TW')
	{  
			 $("#idTram").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
			 $("#thangQuyTt").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
		  	$("#namTt").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
		    $("#csm1").jqxInput({ height: 20, width: '30%', minLength: 0, maxLength: 400, theme: theme});
		    $("#csm2").jqxInput({height: 20, width: '30%', minLength: 0, maxLength: 400, theme: theme});
		    $("#csm3").jqxInput({ height: 20, width: '30%', minLength: 0, maxLength: 400, theme: theme});
		    $("#dienNangTt").jqxInput({height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
		    $("#chenhLechDn").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
		    $("#tienTt").jqxInput({height: 20, width: '97%', minLength: 0, maxLength: 4000, theme: theme});
		    $("#chenhLechTien").jqxInput({height: 20, width: '97%', minLength: 0, maxLength: 4000, theme: theme});
		    $("#tyLe").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
		    $("#kqToKt").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
		    $("#ghiChu").jqxInput({height: 20, width: '97%', minLength: 0, maxLength: 4000, theme: theme});
		    $("#hinhThucTt").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
		    $("#csc1").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
		    $("#csc2").jqxInput({height: 20, width: '97%', minLength: 0, maxLength: 4000, theme: theme});
		    $("#csc3").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
		    
		 // Create a jqxComboBox
		    $("#tgttTq").jqxComboBox({ source: dataAdaptertgttTq, displayMember: "name", valueMember: "name", width: '100%', height: 20, theme: theme,autoOpen: true  });
		    var tgttTq =  "<c:out value='${tgttTq}'/>";
		    if(tgttTq=="")
				$('#tgttTq').val('ALL');
			else
				$('#tgttTq').val(tgttTq); 
		
		    
			// ComboBox nguoittdien
		    var urlUser = "";
			urlUser = "${pageContext.request.contextPath}/ajax/getUserByDeparment.htm?dept=&team=";
		 	// prepare the data
		    var sourceUser =
		    {
		        datatype: "json",
		        url: urlUser,
		        datafields: [
		                     { name: 'username'},
		                     { name: 'fullname'}
		                 ],
		        async: false
		    };
		    var dataAdapterUser = new $.jqx.dataAdapter(sourceUser);
		     $("#nvNhap").jqxDropDownList({source: dataAdapterUser, displayMember: "username", valueMember: "username",width: 160, height: 20, theme: theme,autoOpen: true,enableHover: true });           
		    var nvNhap = '<c:out value="${qldnTramTTDien.nvNhap}"/>';
		    if(nvNhap=="")
		   	{
				$('#nvNhap').val('Choose');
		   	}
			else
			{
				$('#nvNhap').val(nvNhap);
			}
	}
	else
	{ 
    	$("#soHoaDon").jqxInput({height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
	    $("#soHskt").jqxInput({height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
	}
    
    
    var username="<c:out value='${username}'/>";
    var tinh=  $("#tinh").val();
    var huyen=$("#huyen").val();
    var tgttTqTK=$("#tgttTqTK").val();
    var thangQuyTtTK=$("#thangQuyTtTK").val();
    var namTtTK=$("#namTtTK").val();
    var urlDsTram = "${pageContext.request.contextPath}/ajax/getDSTramTTDien.htm?trangThai=N&tinh="+tinh+"&huyen="+huyen+"&tgttTqTK="+tgttTqTK+"&thangQuyTtTK="+thangQuyTtTK+"&namTtTK="+namTtTK+"&username="+username+"&type="+type;
    // prepare the data
    var sourceDsTram =
    {
        datatype: "json",
        datafields: [
              { name: 'idTram'},
              { name: 'tinh'},
              { name: 'huyen'},
              { name: 'httt'},
              { name: 'dnttDk'},
              { name: 'dgLoai'},
              { name: 'dg1Gia'},
              { name: 'dg3Muc1'},
              { name: 'dg3Muc2'},
              { name: 'dg3Muc3'},
              { name: 'dgTlKd'},
              { name: 'dgTlKdGia'},
              { name: 'dgTlSx'},
              { name: 'dgTlSxGia'},
              { name: 'tienDienThangTr'}
        ],
        url: urlDsTram,
        async: false
    };
    var dataAdapterDsTram = new $.jqx.dataAdapter(sourceDsTram);
    // Create a jqxComboBox
    $("#dsMaTram").jqxGrid(
        {
            width: '97%',
            height:"200px",
            pageable: true,
            source: dataAdapterDsTram,
            columnsResize: true,
            showfilterrow: true,
			filterable: true,
			pagesizeoptions: ['50', '100', '200', '300', '400', '500'],
            columns: [
               { text: 'Mã trạm', dataField: 'idTram', width: 100 },
               { text: 'Tên trạm OMC', dataField: 'tentramomc', width: 100 },
               { text: 'Tỉnh', dataField: 'tinh',hidden:true },
               { text: 'Huyện', dataField: 'huyen',hidden:true },
               { text: 'Httt', dataField: 'httt', width: 100},
               { text: 'Định mức', dataField: 'dnttDk', width: 100 },
               { text: 'dgLoai', dataField: 'dgLoai',hidden:true },
               { text: 'dg1Gia', dataField: 'dg1Gia',hidden:true },
               { text: 'dg3Muc1', dataField: 'dg3Muc1',hidden:true },
               { text: 'dg3Muc2', dataField: 'dg3Muc2',hidden:true },
               { text: 'dg3Muc3', dataField: 'dg3Muc3',hidden:true },
               { text: 'dgTlKd', dataField: 'dgTlKd',hidden:true },
               { text: 'dgTlKdGia', dataField: 'dgTlKdGia',hidden:true },
               { text: 'dgTlSx', dataField: 'dgTlSx',hidden:true },
               { text: 'dgTlSxGia', dataField: 'dgTlSxGia',hidden:true },
               { text: 'Tiền điện tháng trước', dataField: 'tienDienThangTr', width: 100 }
          ]
        });
   
    $("#btFilter").click(function(){
    	 // tram thanh toan
        var username="<c:out value='${username}'/>";
        var tinh=  $("#tinh").val();
        var huyen=$("#huyen").val();
        var tgttTqTK=$("#tgttTqTK").val();
        var thangQuyTtTK=$("#thangQuyTtTK").val();
        var namTtTK=$("#namTtTK").val();
        if (tgttTqTK==''||thangQuyTtTK==''||namTtTK=='')
        	{
        		alert("Yêu cầu nhập các thông tin * trước khi chọn tìm kiếm");
        	}
        else
        	{
	        var urlDsTram = "${pageContext.request.contextPath}/ajax/getDSTramTTDien.htm?trangThai=N&tinh="+tinh+"&huyen="+huyen+"&tgttTqTK="+tgttTqTK+"&thangQuyTtTK="+thangQuyTtTK+"&namTtTK="+namTtTK+"&username="+username+"&type="+type;
	        // prepare the data
	        var sourceDsTram =
	        {
	            datatype: "json",
	            datafields: [
	                  { name: 'idTram'},
	                  { name: 'tinh'},
	                  { name: 'huyen'},
	                  { name: 'httt'},
	                  { name: 'dnttDk'},
	                  { name: 'dgLoai'},
	                  { name: 'dg1Gia'},
	                  { name: 'dg3Muc1'},
	                  { name: 'dg3Muc2'},
	                  { name: 'dg3Muc3'},
	                  { name: 'dgTlKd'},
	                  { name: 'dgTlKdGia'},
	                  { name: 'dgTlSx'},
	                  { name: 'dgTlSxGia'},
	                  { name: 'tienDienThangTr'},
	                  { name: 'csm1Old'},
	                  { name: 'csm2Old'},
	                  { name: 'csm3Old'}
	            ],
	            url: urlDsTram,
	            async: false
	        };
	        var dataAdapterDsTram = new $.jqx.dataAdapter(sourceDsTram);
	        // Create a jqxComboBox
	        $("#dsMaTram").jqxGrid(
	            {
	                width: '97%',
	                height:"200px",
	                pageable: true,
	                source: dataAdapterDsTram,
	                columnsResize: true,
	                columns: [
	                  { text: 'Mã trạm', dataField: 'idTram', width: 100 },
	                  { text: 'Tên trạm OMC', dataField: 'tentramomc', width: 100 },
	                  { text: 'Tỉnh', dataField: 'tinh',hidden:true },
	                  { text: 'Huyện', dataField: 'huyen',hidden:true },
	                  { text: 'Httt', dataField: 'httt', width: 100},
	                  { text: 'Định mức', dataField: 'dnttDk', width: 100 },
	                  { text: 'dgLoai', dataField: 'dgLoai',hidden:true },
	                  { text: 'dg1Gia', dataField: 'dg1Gia',hidden:true },
	                  { text: 'dg3Muc1', dataField: 'dg3Muc1',hidden:true },
	                  { text: 'dg3Muc2', dataField: 'dg3Muc2',hidden:true },
	                  { text: 'dg3Muc3', dataField: 'dg3Muc3',hidden:true },
	                  { text: 'dgTlKd', dataField: 'dgTlKd',hidden:true },
	                  { text: 'dgTlKdGia', dataField: 'dgTlKdGia',hidden:true },
	                  { text: 'dgTlSx', dataField: 'dgTlSx',hidden:true },
	                  { text: 'dgTlSxGia', dataField: 'dgTlSxGia',hidden:true },
	                  { text: 'Tiền điện tháng trước', dataField: 'tienDienThangTr', width: 100 },
	                  { text: 'csm1Old', dataField: 'csm1Old',hidden:true },
	                  { text: 'csm2Old', dataField: 'csm2Old',hidden:true },
	                  { text: 'csm3Old', dataField: 'csm3Old',hidden:true }
	                 ]
	            });
        	}
    }); 
    
    $('#dsMaTram').on('rowselect', function (event) 
    		{
    	
    		    // event args.
    		    
    var args = event.args;
    // row's bound index.
    var rowBoundIndex = args.rowindex;
    // row's data. The row's data object or null(when all rows are being selected or unselected with a single action). If you have a datafield called "firstName", to access the row's firstName, use var firstName = rowData.firstName;
    var row = args.row;
    		    
    		    $('#idTram').val(row.idTram);
    		    $('#hinhThucTt').val(row.httt);
		 		$('#dnttDk').val(row.dnttDk);
		 		$('#tienDienThangTr').val(row.tienDienThangTr);
		 		$('#csc1').val(row.csm1Old);
		 		$('#csc2').val(row.csm2Old);
		 		$('#csc3').val(row.csm3Old);
		 		var dgLoai = row.dgLoai;
		 		$('#dgLoai').val(dgLoai);
		 		if (dgLoai=='01 giá'|| dgLoai=='Tỷ lệ')
				{
					$('#dg3Gia').hide();
				    $('#dg1Gia').val(row.dg1Gia);
				  
				}
				else if (dgLoai=='03 giá')
				{
					$('#dg3Gia').show();
				    $('#dg3Muc1').val(row.dg3Muc1);
				    $('#dg3Muc2').val(row.dg3Muc2);
				    $('#dg3Muc3').val(row.dg3Muc3);
				}
		 		
		 		if (type=='TW')
	 			{
		 			$.getJSON("${pageContext.request.contextPath}/ajax/getTramTTDien.htm",{idTram:row.idTram,tgttTqTK:$('#tgttTqTK').val(), thangQuyTtTK:$('#thangQuyTtTK').val(), namTtTK:$('#namTtTK').val()}, function(j){
		 				//alert('qq');
		 				$('#csm1').val(j.csm1);
		 		    	$('#csm2').val(j.csm2);
		 		    	$('#csm3').val(j.csm3);
		 		    	$('#idTram').val(j.idTram);
		 		    	$('#hinhThucTt').val(j.hinhThucTt);
		 		    	$('#dienNangTt').val(j.dienNangTt);
		 		    	$('#chenhLechDn').val(j.chenhLechDn);
		 		    	$('#tienTt').val(j.tienTt);
		 		    	$('#chenhLechTien').val(j.chenhLechTien);
		 		    	$('#tyLe').val(j.tyLe);
		 		    	$('#tgttTq').val(j.tgttTq);
		 		    	$('#nvNhap').val(j.nvNhap);
		 		    	$('#thangQuyTt').val(j.thangQuyTt);
		 		    	$('#namTt').val(j.namTt);
		 		    	$('#kqToKt').val(j.kqToKt);
		 		    	$('#ghiChu').val(j.ghiChu);
		 		    	$('#tuNgay').val(j.tuNgayStr);
		 		    	$('#denNgay').val(j.denNgayStr);
		 		    	$('#csc1').val(j.csc1);
		 		    	$('#csc2').val(j.csc2);
		 		    	$('#csc3').val(j.csc3);
		 		    	
		 		    	$('#csm1div').text(j.csm1);
		 		    	$('#csm2div').text(j.csm2);
		 		    	$('#csm3div').text(j.csm3);
		 		    	$('#csc1div').val(j.csc1);
		 		    	$('#csc2div').val(j.csc2);
		 		    	$('#csc3div').val(j.csc3);
		 		    	$('#idTramdiv').text(j.idTram);
		 		    	$('#hinhThucTtdiv').text(j.hinhThucTt);
		 		    	$('#dienNangTtdiv').text(j.dienNangTt);
		 		    	$('#chenhLechDndiv').text(j.chenhLechDn);
		 		    	$('#tienTtdiv').text(j.tienTt);
		 		    	$('#chenhLechTiendiv').text(j.chenhLechTien);
		 		    	$('#tyLediv').text(j.tyLe);
		 		    	$('#tgttTqdiv').text(j.tgttTq);
		 		    	$('#nvNhapdiv').text(j.nvNhap);
		 		    	$('#thangQuyTtdiv').text(j.thangQuyTt);
		 		    	$('#namTtdiv').text(j.namTt);
		 		    	$('#kqToKtdiv').text(j.kqToKt);
		 		    	$('#ghiChudiv').text(j.ghiChu);
		 		    	$('#tuNgaydiv').text(j.tuNgayStr);
		 		    	$('#denNgaydiv').text(j.denNgayStr);
		 		    	$("#soHoaDon").val("");
		 			    $("#soHskt").val("");
		 			   	$("#ngayTttw").val("");
		 			  	$("#ngayUnc").val("");
		 		    
		 			   });
	 			}
    	});
    
    $('#btTinh').click(function(){
    	var csm1 = $('#csm1').val();
    	var csm2 = $('#csm2').val();
    	var csm3 = $('#csm3').val();
    	var csc1 = $('#csc1').val();
    	var csc2 = $('#csc2').val();
    	var csc3 = $('#csc3').val();
    	if (csc1!=null&&csc1!='')
    	{
    	
	    	var dgLoai = $('#dgLoai').val();
	    	var dienNangTt = (csm1 - csc1)+(csm2 - csc2)+(csm3 - csc3);
	    	$('#dienNangTt').val(dienNangTt);
	    	
	    	// chenh lech dien nang
	    	var dnttDk = $('#dnttDk').val();
	    	var chenhLechDn= dienNangTt - dnttDk;
	    	$('#chenhLechDn').val(chenhLechDn);
	    	var tiendien=0;
	    	//tinh tien dien tieu thu
	    	 if (dgLoai=='01 giá')
			{
	    		var dg1Gia = $('#dg1Gia').val();
				tiendien= csm1*dg1Gia;
				$('#tienTt').val(tiendien);
			}
			else if (dgLoai=='03 giá')
			{
				var dg3Muc1 = $('#dg3Muc1').val();
	   	    	var dg3Muc2 = $('#dg3Muc2').val();
	   	    	var dg3Muc3 = $('#dg3Muc3').val();
	   	    	tiendien= csm1*dg3Muc1+csm2*dg3Muc2+ csm3*dg3Muc3;
				$('#tienTt').val(tiendien);
			}
	    	 
	    	//chenh lech tien so voi thang truoc & ty le
			var tienDienThangTr = $('#tienDienThangTr').val();
	     	var chenhLechTien= tiendien - tienDienThangTr;
	     	var tyLe= chenhLechTien*100/tiendien;
	     	$('#chenhLechTien').val(chenhLechTien);
	     	$('#tyLe').val(tyLe);
    	}
    	else
    		{
	    		var tiendien = $('#tienTt').val();
		     	var chenhLechTien= $('#chenhLechTien').val();
		     	var tyLe= chenhLechTien*100/tiendien;
		     	$('#tyLe').val(tyLe);
    		}
    	
    });
    
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
    ${provinceList}
    $("#tinh").jqxInput({ height: 20, width: 230, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#tinh").jqxInput({ query: item });
            response(provinceList);
        },
        renderer: renderer
    });
    var tinh =  "<c:out value='${tinh}'/>";
    if(tinh!="")
		$('#tinh').val(tinh);
    
 // Input district
 	${districtList}
	$("#huyen").jqxInput({ height: 20, width: 230, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#huyen").jqxInput({ query: item });
            response(districtList);
        },
        renderer: renderer
    });
 	var huyen =  "<c:out value='${huyen}'/>";
 	if(huyen!="") {
		$('#huyen').val(huyen);
		//alert(district);
    }
 	
    
    
});
   
</script>
</body>
</html>