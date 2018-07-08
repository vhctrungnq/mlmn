
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>CẬP NHẬT THÔNG TIN TRẠM THANH TOÁN</title>
<content tag="heading">CẬP NHẬT THÔNG TIN TRẠM THANH TOÁN</content>

<form:form commandName="thongTinTram" method="post" action="formHCM.htm"> 
	<input type="hidden" id="typeF" name="typeF" value="${typeF}"/>
	
	<table class="simple2"> 
		<tr>
         	<td colspan="4"><b>1. Thông tin trạm </b></td>
         </tr>
      <tr>
           <td style="width: 150px;"><fmt:message key="QldnThongTinTram.idTram"/><font color="red">(*)</font></td>
			<td style="width: 250px;">
				<form:input type ="text" path="idTram" maxlength="400"  class = "long" rows="3" /><font color="red">${erroridTram}</font>

			</td>
			<td style="width: 150px;"><fmt:message key="QldnThongTinTram.loaitram"/><font color="red">(*)</font></td>
			<td>
				<div id="loaitram"  name="loaitram"></div><font color="red">${errorLoaitram}</font>
			</td>
		</tr>
		
        <tr>
         	<td style="width: 150px;"><fmt:message key="QldnThongTinTram.makh"/><font color="red">(*)</font></td>
			<td>
				<form:input type ="text" path="makh" maxlength="400"  class = "long" rows="3" /><font color="red">${errorMakh}</font>
         	</td>
         	<td style="width: 150px;"><fmt:message key="QldnThongTinTram.soct"/></td>
			<td>
				<form:input type ="text" path="soct" maxlength="400"  class = "long" rows="3" />
         	</td>
          </tr>
        <tr>	
			<td style="width: 150px;"><fmt:message key="QldnThongTinTram.dienDvth"/></td>
			<td>
				<div id="dienDvth" name="dienDvth" ></div>
			</td>
			<td style="width: 150px;"><fmt:message key="QldnThongTinTram.diachitram"/></td>
			<td>
				<form:input type ="text" path="diachitram" maxlength="400"  class = "long" rows="3" />
         	</td>
         </tr>
          <tr>
          	<td  style="width:100px"><fmt:message key="QldnThongTinTram.daiVT"/></td>
			<td>
				<c:choose>
					<c:when test="${isRoleManager=='Y'}">
						<form:select path="nhom" class="wid60" >
			   				<c:forEach var="items" items="${maPhongList}">
				              <c:choose>
				                <c:when test="${items.deptCode == thongTinTram.nhom}">
				                    <option value="${items.deptCode}" selected="selected">${items.deptCode}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.deptCode}">${items.deptCode}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			           	</form:select>
					</c:when>
					<c:otherwise>
						<form:hidden path="nhom" maxlength="400"  class = "long" rows="3" />
						<b>${nhom}</b>
					</c:otherwise>
				</c:choose>
				
				<!-- <input type="text" id="daiVT" name="daiVT" style="width: 100px;"/> -->
			</td>
         	<td style="width: 150px;"><fmt:message key="QldnThongTinTram.tu2G"/></td>
			<td>
				<form:input type ="text" path="tu2G" maxlength="400"  class = "long" rows="3" />
         	</td>
         <tr>
         <tr>
         	<td style="width: 150px;"><fmt:message key="QldnThongTinTram.tu3G"/></td>
			<td>
				<form:input type ="text" path="tu3G" maxlength="400"  class = "long" rows="3" />
         	</td>
         	<td style="width: 150px;"><fmt:message key="QldnThongTinTram.tu4G"/></td>
			<td>
				<form:input type ="text" path="tu4G" maxlength="400"  class = "long" rows="3" />
         	</td>
         	
         </tr>
          <tr>
         	
         	<td style="width: 150px;"><fmt:message key="QldnThongTinTram.ghichu"/></td>
			<td colspan="3">
				<form:input type ="text" path="ghichu" maxlength="400"  class = "long" rows="3" />
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
</form:form>
<style>
    .error {
    	color: red;
    }
</style> 

<script type="text/javascript">
var theme =  getTheme(); 
$('#submit').jqxButton({ theme: theme });
$('#btCancel').jqxButton({ theme: theme });

$(document).ready(function(){
	
	 //input causeby
   $("#diachitram").jqxInput({placeHolder: "Địa chỉ trạm", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#makh").jqxInput({placeHolder: "Mã KH ", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#soct").jqxInput({placeHolder: "Số công tơ", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#ghichu").jqxInput({placeHolder: "Ghi chú", height: 20, width: '97%', minLength: 0, maxLength: 4000, theme: theme});
    $("#tu2G").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#tu3G").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#tu4G").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    
   /*  $("#tentramomc").jqxInput({placeHolder: "Tên trạm OMC", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
 	$("#chitietNc").jqxInput({placeHolder: "Chi tiết nguồn cung", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#sohd").jqxInput({placeHolder: "Số HĐ", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#tontai").jqxInput({placeHolder: "Tồn tại", height: 20, width: '97%', minLength: 0, maxLength: 4000, theme: theme});
    $("#cstramDk").jqxInput({placeHolder: "Công suất dự kiến", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#dnttDk").jqxInput({placeHolder: "Điện năng tiêu thụ dự kiến", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#giaiphaptietkiem").jqxInput({placeHolder: " Giải pháp tiết kiệm", height: 20, width: '97%', minLength: 0, maxLength: 4000, theme: theme});
    $("#dg1Gia").jqxInput({placeHolder: "Đơn giá là 01 giá", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#dg3Muc1").jqxInput({placeHolder: "Đơn giá mức 1", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#dg3Muc2").jqxInput({placeHolder: "Đơn giá mức 2", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#dg3Muc3").jqxInput({placeHolder: "Đơn giá mức 3", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#dgTlKd").jqxInput({placeHolder: "% sử dụng ĐN (KD)", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#dgTlKdGia").jqxInput({placeHolder: "Đơn giá (KD)", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
    $("#dgTlSx").jqxInput({placeHolder: "% sử dụng ĐN (SX)", height: 20, width: '97%', minLength: 0, maxLength: 400, theme: theme});
     */
    
 // Create a jqxMultile Input
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
	         //value = terms.join(",");
	    	 value = terms;
	    	}
       
        return value;
    };
  //Input team
	 var idTramList=[];
	$.getJSON("${pageContext.request.contextPath}/ajax/getSiteList.htm", function(j){
		//$.getJSON("${pageContext.request.contextPath}/ajax/getSiteByNetwork.htm", function(j){
		idTramList =j;
	   });
  $("#idTram").jqxInput({ height: 20, width: '97%', theme: theme,
      source: function (query, response) {
          var item = query.split(/,\s*/).pop();
          // update the search query.
          $("#idTram").jqxInput({ query: item });
          response(idTramList);
      }
  });
  
   
	// Create a jqxComboBox vendor
       	var urlloaitram = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=LOAI_TRAM";
        // prepare the data
        var sourceloaitram =
        {
            datatype: "json",
            datafields: [
                { name: 'value' },
                { name: 'name' }
            ],
            url: urlloaitram,
            async: false
        };
        var dataAdapterloaitram = new $.jqx.dataAdapter(sourceloaitram);
        // Create a jqxComboBox
        $("#loaitram").jqxComboBox({ source: dataAdapterloaitram, displayMember: "value", valueMember: "name", width: '97%',height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
        var loaitram =  "<c:out value='${loaitram}'/>";
        if(loaitram=="")
			$('#loaitram').val('ALL');
		else
			$('#loaitram').val(loaitram);
        // Create a jqxComboBox dienDvth
		var urldienDvth = "${pageContext.request.contextPath}/ajax/getDonViThuHuong.htm";
        // prepare the data
        var sourcedienDvth =
        {
            datatype: "json",
            datafields: [
                { name: 'tenDv'}
            ],
            url: urldienDvth,
            async: false
        };
        var dataAdapterdienDvth = new $.jqx.dataAdapter(sourcedienDvth);
        // Create a jqxComboBox
        $("#dienDvth").jqxComboBox({ source: dataAdapterdienDvth, displayMember: "tenDv", valueMember: "tenDv", width: '97%', height: 20, theme: theme,autoOpen: true  });
        var dienDvth =  "<c:out value='${dienDvth}'/>";
        if(dienDvth=="")
			$('#dienDvth').val('ALL');
		else
			$('#dienDvth').val(dienDvth);
	/*  // Create a jqxComboBox nguonccd
		var urlnguonccd = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=NGUON_CUNG_CAP_DIEN";
        // prepare the data
        var sourcenguonccd =
        {
            datatype: "json",
            datafields: [
                { name: 'name'}
            ],
            url: urlnguonccd,
            async: false
        };
        var dataAdapternguonccd = new $.jqx.dataAdapter(sourcenguonccd);
        // Create a jqxComboBox
        $("#nguonccd").jqxComboBox({ source: dataAdapternguonccd, displayMember: "name", valueMember: "name", width: '97%', height: 20, theme: theme,autoOpen: true  });
        var nguonccd =  "<c:out value='${nguonccd}'/>";
        if(nguonccd=="")
			$('#nguonccd').val('ALL');
		else
			$('#nguonccd').val(nguonccd);
	
        // Create a jqxComboBox httt
		var urlhttt = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=HINH_THUC_THANH_TOAN";
        // prepare the data
        var sourcehttt =
        {
            datatype: "json",
            datafields: [
                { name: 'name'}
            ],
            url: urlhttt,
            async: false
        };
        var dataAdapterhttt = new $.jqx.dataAdapter(sourcehttt);
        // Create a jqxComboBox
        $("#httt").jqxComboBox({ source: dataAdapterhttt, displayMember: "name", valueMember: "name", width: '97%', height: 20, theme: theme,autoOpen: true  });
        var httt =  "<c:out value='${httt}'/>";
        if(httt=="")
			$('#httt').val('ALL');
		else
			$('#httt').val(httt);
		
       
		
        // Create a jqxComboBox dienDvth
		var urltgttTq = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=THOI_GIAN_TT_TQ";
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
        $("#tgttTq").jqxComboBox({ source: dataAdaptertgttTq, displayMember: "name", valueMember: "name", width: '97%', height: 20, theme: theme,autoOpen: true  });
        var tgttTq =  "<c:out value='${tgttTq}'/>";
        if(tgttTq=="")
			$('#tgttTq').val('ALL');
		else
			$('#tgttTq').val(tgttTq);
		
        // Create a jqxComboBox tgttCus
		var urltgttCus = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=THOI_GIAN_TT_CUS";
        // prepare the data
        var sourcetgttCus =
        {
            datatype: "json",
            datafields: [
                { name: 'name'}
            ],
            url: urltgttCus,
            async: false
        };
        var dataAdaptertgttCus = new $.jqx.dataAdapter(sourcetgttCus);
        // Create a jqxComboBox
        $("#tgttCus").jqxComboBox({ source: dataAdaptertgttCus, displayMember: "name", valueMember: "name", width: '97%', height: 20, theme: theme,autoOpen: true  });
        var tgttCus =  "<c:out value='${tgttCus}'/>";
        if(tgttCus=="")
			$('#tgttCus').val('ALL');
		else
			$('#tgttCus').val(tgttCus);
		
        
     // Create a jqxComboBox dgLoai
		var urldgLoai = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=DON_GIA_LOAI";
        // prepare the data
        var sourcedgLoai =
        {
            datatype: "json",
            datafields: [
                { name: 'name'},
                { name: 'value'}
            ],
            url: urldgLoai,
            async: false
        };
        var dataAdapterdgLoai = new $.jqx.dataAdapter(sourcedgLoai);
        // Create a jqxComboBox
        $("#dgLoai").jqxComboBox({ source: dataAdapterdgLoai, displayMember: "value", valueMember: "value", width: '97%', height: 20, theme: theme,autoOpen: true  });
        var dgLoai =  "<c:out value='${dgLoai}'/>";
        if(dgLoai=="")
			$('#dgLoai').val('ALL');
		else
		{
			$('#dgLoai').val(dgLoai);
			if (dgLoai=='01 giá')
			{
				$('#dg1Gia').show();
			    $('#dg3Gia').hide();
			    $('#dgTyLe').hide();
			}
			else if (dgLoai=='03 giá')
			{
				$('#dg1Gia').hide();
			    $('#dg3Gia').show();
			    $('#dgTyLe').hide();
			}
			else if (dgLoai=='Tỷ lệ')
			{
				$('#dg1Gia').hide();
			    $('#dg3Gia').hide();
			    $('#dgTyLe').show();
			}
		}
			
		
	 $("#dgLoai").change(function(){
		 var Loai =$("#dgLoai").val();
		 
		 if (Loai=='01 giá')
			{
			 alert(Loai);
			    document.getElementById('dg1Gia').style.display = 'block';
			    document.getElementById('dg3Gia').style.display = 'none';
			    document.getElementById('dgTyLe').style.display = 'none';
			    
			    $('#dg1Gia').val("");
			    
			    $('#dg3Muc2').val("");
			    $('#dg3Muc3').val("");
			    $('#dgTlKd').val("");
			    $('#dgTlKdGia').val("");
			    $('#dgTlSx').val("");
			    $('#dgTlSxGia').val("");
			}
			else if (Loai=='03 giá')
			{
				alert(Loai);
				  document.getElementById('dg1Gia').style.display = 'none';
				    document.getElementById('dg3Gia').style.display = 'block';
				    document.getElementById('dgTyLe').style.display = 'none';
			    $('#dg1Gia').val("");
			    $('#dgTlKd').val("");
			    $('#dgTlKdGia').val("");
			    $('#dgTlSx').val("");
			    $('#dgTlSxGia').val("");
			}
			else if (Loai=='Tỷ lệ')
			{
				alert(Loai);
				 document.getElementById('dg1Gia').style.display = 'none';
				    document.getElementById('dg3Gia').style.display = 'none';
				    document.getElementById('dgTyLe').style.display = 'block';
			    $('#dg1Gia').val("");
			    $('#dg3Muc1').val("");
			    $('#dg3Muc2').val("");
			    $('#dg3Muc3').val("");
			}
	});		
	
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
     $("#nguoittdien").jqxDropDownList({source: dataAdapterUser, displayMember: "username", valueMember: "username",width: 160, height: 20, theme: theme,autoOpen: true,enableHover: true });           
    var nguoittdien = '<c:out value="${nguoittdien}"/>';
    if(nguoittdien=="")
   	{
		$('#nguoittdien').val('Choose');
   	}
	else
	{

		$('#nguoittdien').val(nguoittdien);
	}
     */
    
});
   
</script>
</body>
</html>