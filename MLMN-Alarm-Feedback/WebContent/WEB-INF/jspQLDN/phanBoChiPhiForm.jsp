
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Thêm mới</title>
<content tag="heading">Thêm mới</content>

<form:form commandName="PhanBoChiPhi" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
           <td><fmt:message key="PhanBoChiPhi.year"/></td>
           <td colspan="3"> 
           	<form:input type ="text" path="year"  class = "long" rows="10" maxlength="400"/><font color="red">${errorYear}</font>
          </td>
       </tr>
       
       <tr>
           <td><fmt:message key="PhanBoChiPhi.tinh"/></td>
           <td colspan="3"> 
           <form:input type="text" path="tinh"  style="width: 100px;"/>
          </td>
       </tr>
	    <tr>
           <td><fmt:message key="PhanBoChiPhi.loaiChiPhi"/></td>
           <td  colspan="3"> 
           		<div id='loaiChiPhi' name= 'loaiChiPhi'></div>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="PhanBoChiPhi.month1"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="month1" maxlength="400"  rows="3" /><font color="red">${errorMonth1}</font>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="PhanBoChiPhi.month2"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="month2" maxlength="400"  rows="3" /><font color="red">${errorMonth2}</font>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="PhanBoChiPhi.month3"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="month3" maxlength="400"  rows="3" /><font color="red">${errorMonth3}</font>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="PhanBoChiPhi.month4"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="month4" maxlength="400"  rows="3" /><font color="red">${errorMonth4}</font>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="PhanBoChiPhi.month5"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="month5" maxlength="400"  rows="3" /><font color="red">${errorMonth5}</font>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="PhanBoChiPhi.month6"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="month6" maxlength="400"  rows="3" /><font color="red">${errorMonth6}</font>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="PhanBoChiPhi.month7"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="month7" maxlength="400"  rows="3" /><font color="red">${errorMonth7}</font>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="PhanBoChiPhi.month8"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="month8" maxlength="400"  rows="3" /><font color="red">${errorMonth8}</font>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="PhanBoChiPhi.month9"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="month9" maxlength="400"  rows="3" /><font color="red">${errorMonth9}</font>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="PhanBoChiPhi.month10"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="month10" maxlength="400"  rows="3" /><font color="red">${errorMonth10}</font>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="PhanBoChiPhi.month11"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="month11" maxlength="400"  rows="3" /><font color="red">${errorMonth11}</font>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="PhanBoChiPhi.month12"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="month12" maxlength="400"  rows="3" /><font color="red">${errorMonth12}</font>
          </td>
       </tr>
     	
       
       <tr>
           <td></td>
           <td colspan="3">
           		<input type="submit" class="button" id = "submit" value="<fmt:message key="global.form.luulai"/>" />
           	  	<input type="button" class="button" id = "btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="sitelist.htm"'>	
           </td>
       </tr>
    </table>
</form:form>
<style>
    .error {
    	color: red;
    }
</style> 

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

 

<script type="text/javascript">
var theme =  getTheme(); 
$('#submit').jqxButton({ theme: theme });
$('#btCancel').jqxButton({ theme: theme });

$(document).ready(function(){
	
	
	//Input province
    ${provinceList}
    $("#tinh").jqxInput({ height: 20, width: '160px', theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#tinh").jqxInput({ query: item });
            response(provinceList);
        }
    });
    var tinh =  "<c:out value='${tinh}'/>";
    if(tinh!="")
  $('#tinh').val(tinh);
	     //loai chi phi
	        var urlstatus = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=LOAI_CHI_PHI";
	        // prepare the data
	        var sourcestatus =
	        {
	            datatype: "json",
	            datafields: [
	                { name: 'name'},
	                { name: 'value'}
	            ],
	            url: urlstatus,
	            async: false
	        };
	        var dataAdapterstatus = new $.jqx.dataAdapter(sourcestatus);
	        // Create a jqxComboBox
	        $("#loaiChiPhi").jqxComboBox({ source: dataAdapterstatus, displayMember: "value", valueMember: "name", width: '160px',height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
	        var loaiChiPhi =  "<c:out value='${loaiChiPhi}'/>";
	        if(loaiChiPhi=="")
	   $('#loaiChiPhi').val('ALL');
	  else
	   $('#loaiChiPhi').val(loaiChiPhi);

	});
</script>
</body>
</html>