<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxwindow.js"></script>
    
<style type="text/css">
    .textrt{
    	text-align: right;
    }
    
    .textct {
    	text-align: center;
    }
    	.ui-multiselect {
		width:255 !important;
	}
	
	
 .blue
{
color:blue;
}
</style>

<title>${title}</title>
<content tag="heading">${title}</content>
<%-- <input type="hidden" value="${ngayTruc}" name="ngayTruc" id="ngayTruc">
<input type="hidden" value="${ca}" name="ca" id="ca"> --%>
<!-- -------------------------------------------------------------------------
------------------------------------------------------------------------- -->	
<table style="width:100%">
	<tr>
		<td align="left"><h2><fmt:message key="catruc.congvieccodinh"/></h2></td>
		<td align="right" class="ftsize12">
		<c:if test="${checkTruongCa==true}">	
			<input class="button"  type="button" id="btAdd" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/working/form.htm?id=&type=cvcd&note=caTruc"' />
		</c:if>
		</td>
		<td style="width: 20px"><div style="float: right;" id="listColumn"></div></td>
	</tr>
</table>
<div id="gridWork"></div>
 <div id='menuReport'>
            <ul>
	            <c:if test="${checkTruongCa==true}">	
		   			<li><fmt:message key="global.button.deleteSelected"/></li>
				</c:if>
				<li><fmt:message key="global.button.editSelected"/></li>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>
		
	<br/>
<!-- -------------------------------------------------------------------------
------------------------------------------------------------------------- -->
<!-- -------------------------------------------------------------------------
------------------------------------------------------------------------- -->	
<table style="width:100%">
	<tr>
		<td align="left"><h2><fmt:message key="catruc.canhBao2G"/></h2></td>
		<td align="right" class="ftsize12">
		<c:if test="${checkCaTruc==true}">	
			<input class="button"  type="button" id="btUpdate" value="<fmt:message key="global.button.UpdateEnd"/>" onclick='window.location="updateEnd.htm?warningTp=2G&ca=${ca}&ngayTruc=${ngayTruc}"' />
			<input class="button"  type="button" id="btAdd1" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="form23G.htm?warningTp=2G"' />
		</c:if>
		</td>
		<td style="width: 20px"><div style="float: right;" id="jqxlistbox2G"></div></td>
	</tr>
</table>
<div id="grid2G"></div>
 <div id='menu2G'>
            <ul>
            	<c:if test="${checkCaTruc==true}">	
					<li><fmt:message key="global.button.editSelected"/></li>
		   			<li><fmt:message key="global.button.deleteSelected"/></li>
				</c:if>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>
		
	<br/>
<!-- -------------------------------------------------------------------------
------------------------------------------------------------------------- -->

<table style="width:100%">
	<tr>
		<td align="left"><h2><fmt:message key="catruc.canhBao3G"/></h2></td>
		<td align="right" class="ftsize12">
			<c:if test="${checkCaTruc==true}">	
				<input class="button"  type="button" id="btUpdate1" value="<fmt:message key="global.button.UpdateEnd"/>"  onclick='window.location="updateEnd.htm?warningTp=3G&ca=${ca}&ngayTruc=${ngayTruc}"' />
				<input class="button"  type="button" id="btAdd2" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="form23G.htm?warningTp=3G"' />
			</c:if>
		</td>
		<td style="width: 20px"><div style="float: right;" id="jqxlistbox3G"></div>
		</td>
	</tr>
</table>
<div id="grid3G"></div>	
<div id='menu3G'>
            <ul>
				<c:if test="${checkCaTruc==true}">	
					<li><fmt:message key="global.button.editSelected"/></li>
		   			<li><fmt:message key="global.button.deleteSelected"/></li>
				</c:if>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>
<br/>
<!-- -------------------------------------------------------------------------
------------------------------------------------------------------------- -->	

<table style="width:100%">
	<tr>
		<td align="left"><h2><fmt:message key="catruc.canhBaoPSCore"/></h2></td>
		<td align="right" class="ftsize12">
			<c:if test="${checkCaTruc==true}">	
				<input class="button"  type="button" id="btUpdate2" value="<fmt:message key="global.button.UpdateEnd"/>"  onclick='window.location="updateEnd.htm?warningTp=PS_CORE&ca=${ca}&ngayTruc=${ngayTruc}"' />
				<input class="button" type="button" id="btAdd3" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/warning/form.htm?warningTp=PS_CORE&note=caTruc"' />
		</c:if>
		</td>
		<td style="width: 20px"><div style="float: right;" id="jqxlistboxPSCore"></div></td>
	</tr>
</table>
	
	<div id="gridPSCore"></div>		
	<div id='menuPSCore'>
            <ul>
				<c:if test="${checkCaTruc==true}">	
					<li><fmt:message key="global.button.editSelected"/></li>
		   			<li><fmt:message key="global.button.deleteSelected"/></li>
				</c:if>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>
<br/>

<!-- -------------------------------------------------------------------------
------------------------------------------------------------------------- -->	

<table style="width:100%">
	<tr>
		<td align="left"><h2><fmt:message key="catruc.canhBaoCSCore"/></h2></td>
		<td align="right" class="ftsize12">
			<c:if test="${checkCaTruc==true}">	
				<input class="button"  type="button" id="btUpdate3" value="<fmt:message key="global.button.UpdateEnd"/>" onclick='window.location="updateEnd.htm?warningTp=CS_CORE&ca=${ca}&ngayTruc=${ngayTruc}"' />
				<input class="button" type="button" id="btAdd4" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/warning/form.htm?warningTp=CS_CORE&note=caTruc"' />
		</c:if>
		</td>
		<td style="width: 20px"><div style="float: right;" id="jqxlistboxCSCore"></div></td>
	</tr>
</table>
	
	<div id="gridCSCore"></div>		
	<div id='menuCSCore'>
            <ul>
				<c:if test="${checkCaTruc==true}">	
					<li><fmt:message key="global.button.editSelected"/></li>
		   			<li><fmt:message key="global.button.deleteSelected"/></li>
				</c:if>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>
<br/>
<!-- -------------------------------------------------------------------------
------------------------------------------------------------------------- -->	
<table style="width:100%">
	<tr>
		<td align="left"><h2><fmt:message key="catruc.canhBaoIPBB"/></h2></td>
		<td align="right" class="ftsize12">
			<c:if test="${checkCaTruc==true}">	
				<input class="button"  type="button" id="btUpdate4" value="<fmt:message key="global.button.UpdateEnd"/>" onclick='window.location="updateEnd.htm?warningTp=IPBB&ca=${ca}&ngayTruc=${ngayTruc}"' />
				<input class="button" type="button" id="btAdd5" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/warning/form.htm?warningTp=IPBB&note=caTruc"' />  
			</c:if>
		</td>
		<td style="width: 20px"><div style="float: right;" id="jqxlistboxIPBB"></div></td>
	</tr>
</table>

<div id="gridIPBB"></div>	
<div id='menuIPBB'>
            <ul>
				<c:if test="${checkCaTruc==true}">	
					<li><fmt:message key="global.button.editSelected"/></li>
		   			<li><fmt:message key="global.button.deleteSelected"/></li>
				</c:if>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>	
	<br/>
<!-- -------------------------------------------------------------------------
-------------------------------------------------------------------------	 -->
<table style="width:100%">
	<tr>
		<td align="left"><h2><fmt:message key="catruc.canhBaoSuCoLon"/></h2></td>
		<td align="right" class="ftsize12">
			<c:if test="${checkCaTruc==true}">	
				<input class="button" type="button" id="btAdd6" value="<fmt:message key="global.button.addNew"/>"  onclick='window.location="form23G.htm?warningTp=SU_CO_LON"' />
				  
		 	</c:if>
		</td>
		<td style="width: 20px"><div style="float: right;" id="jqxlistboxSCL"></div></td>
	</tr>
</table>

<div id="gridSCL"></div>
<div id='menuSCL'>
            <ul>
				<c:if test="${checkCaTruc==true}">	
					<li><fmt:message key="global.button.editSelected"/></li>
		   			<li><fmt:message key="global.button.deleteSelected"/></li>
		   			<li><fmt:message key="global.button.sendMessage"/></li>
				</c:if>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>			
<br/>
<!-- -------------------------------------------------------------------------
-------------------------------------------------------------------------	 -->
<table style="width:100%">
	<tr>
		<td align="left"><h2><fmt:message key="catruc.congViecHieuChinhMR"/></h2></td>
		<td align="right" class="ftsize12">
			<c:if test="${checkCaTruc==true}">	
				<input class="button" type="button" id="btAdd7" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/alarmExtend/form.htm?note=caTruc"' />
		 	</c:if>
		</td>
		<td style="width: 20px"><div style="float: right;" id="jqxlistboxHCMR"></div></td>
	</tr>
</table>

<div id="gridHCMR"></div>
<div id='menuHCMR'>
            <ul>
				<c:if test="${checkCaTruc==true}">	
					<li><fmt:message key="global.button.editSelected"/></li>
		   			<li><fmt:message key="global.button.deleteSelected"/></li>
		   			<li><fmt:message key="global.button.sendMessage"/></li>
				</c:if>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>		
<br/>

<!-- -------------------------------------------------------------------------
-------------------------------------------------------------------------	 -->
<table style="width:100%">
	<tr>
		<td align="left"><h2><fmt:message key="catruc.gsnhietdoSite"/></h2></td>
		<td align="right" class="ftsize12">
			<c:if test="${checkCaTruc==true}">	
				<input class="button" type="button" id="btAdd8" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/monitor/temperatureForm.htm?note=caTruc"' />
		 		
		 	</c:if>
		</td>
		<td style="width: 20px"><div style="float: right;" id="jqxlistboxGSSite"></div></td>
	</tr>
</table>

<div id="gridGSSite"></div>
<div id='menuGSSite'>
            <ul>
				<c:if test="${checkCaTruc==true}">	
					<li><fmt:message key="global.button.editSelected"/></li>
		   			<li><fmt:message key="global.button.deleteSelected"/></li>
		   			<li><fmt:message key="global.button.sendMessage"/></li>
				</c:if>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>		
<br/>
<!-- -------------------------------------------------------------------------
-------------------------------------------------------------------------	 -->
<%-- <h2><a id="banGiao" onclick="hiddenClick('giaoCa')"><fmt:message key="catruc.chuyenGiaoCaTruc"/></a></h2> --%>
<c:if test="${checkCaTruc==true}">	
<table><tr><td>
	<h2><fmt:message key="catruc.chuyenGiaoCaTruc"/></h2>
</td></tr></table>
	<div style="display:none;" id="openCloseIdentifier_giaoCa"></div>
	<div id="giaoCa" >
	<form:form commandName="catruc" method="post" id ="foo" name="foo" action="list.htm"> 	
		<form:hidden path="shiftId"/>
		<input type="hidden" value="${xacnhanTC}" name="xacnhanTC" id="xacnhanTC">
		<font color="red">${errorBanGiao}</font>
				<table class="simple2"> 
			      <tr>
			           <td colspan="7">
			           		<b><span><fmt:message key="catruc.banGiaoKhac"/><font color="red">(*)</font></span></b>
			           </td>
			      </tr>
			      <tr>
			      		
			      		<td colspan="7"> 
			      		<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
						<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
		           		<textarea style="width:100%; height: 220px" name="noiDung" id="noiDung" maxlength="900" ></textarea>
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
				  	 <td colspan="7">
			           		<b><span><fmt:message key="catruc.kyNhanGiaoCa"/></span></b>
			          </td>
				  </tr>
				  <tr>
				  	 <td colspan="4">
			           		<b><span><fmt:message key="catruc.benBanGiao"/></span></b>
			          </td>
			          <td colspan="3">
			           		<b><span><fmt:message key="catruc.benNhanCa"/></span></b>
			          </td>
				  </tr>
				  <tr>
				  	<td><fmt:message key="catruc.giaoUsername"/><font color="red">(*)</font></td>
				  	<td colspan="3">
				  	<c:choose>
			                <c:when test="${first==false}">
			                   ${catruc.giaoUsername}<form:hidden path="giaoUsername" maxlength="100"/>
			                </c:when>
							<c:otherwise>
								<form:input path="giaoUsername" name="giaoUsername" id="giaoUsername" maxlength="100" style="width:255px;"/>
						
							</c:otherwise>
					</c:choose>
				  	<%-- 	<form:input path="giaoUsername" maxlength="50" style="width:255px;" value="${giaoUsername}"/> --%>
				  		
				  		<font color="red"><form:errors path="giaoUsername" /></font>
				  	</td>
				  	<td><fmt:message key="catruc.nhanUsername"/><font color="red">(*)</font></td>
				  	<td colspan="2">
						<form:input path="nhanUsername" name="nhanUsername" id="nhanUsername" maxlength="100" style="width:255px;"/>
						<font color="red"><form:errors path="nhanUsername" /></font>
				  	</td>
				  </tr>
				   <tr>
				  	<td><fmt:message key="catruc.giaoCaVien"/><font color="red">(*)</font></td>
				  	<td colspan="3">
				  		<c:choose>
			                <c:when test="${first==false}">
			                  ${catruc.giaoCaVien}<form:hidden path="giaoCaVien" maxlength="100"/>
			                </c:when>
							<c:otherwise>
								<!-- <select name="giaoCaVien" id="giaoCaVien" multiple="multiple" style="width:100%;"></select>  -->
							<form:input path="giaoCaVien" name="giaoCaVien" id="giaoCaVien" maxlength="100" style="width:255px;"/>
				  		
							</c:otherwise>
						</c:choose>
				  		<font color="red"><form:errors path="giaoCaVien" /></font>
				  	</td>
				  	<td><fmt:message key="catruc.nhanCaVien"/><font color="red">(*)</font></td>
				  	<td colspan="2">
				  		<!-- <select name="nhanCaVien" id="nhanCaVien" multiple="multiple" style="width:250px;"></select>  -->
				  		<form:input path="nhanCaVien" name="nhanCaVien" id="nhanCaVien" maxlength="100" style="width:255px;"/>
				  		<font color="red"><form:errors path="nhanCaVien" /></font>
				  	</td>
				  </tr>
				   <tr>
				  	<td><fmt:message key="catruc.ca"/><font color="red">(*)</font></td>
				  	
			  		<c:choose>
		                <c:when test="${first==false}">
			                <td colspan="3">
			                   ${catruc.giaoCaTruc}<form:hidden path="giaoCaTruc" maxlength="50"/>,${giaoNgayTruc}<form:hidden path="giaoNgayTruc" name="giaoNgayTruc" id="giaoNgayTruc" maxlength="50"/>
			                </td>
		                </c:when>
						<c:otherwise>
						<td>
							<form:select path="giaoCaTruc"  style="width: 80px;height:20px; padding-top: 4px;">
								<c:forEach var="item" items="${caList}">
									<c:choose>
						                <c:when test="${item.name == giaoCaTruc}">
						                    <option value="${item.name}" selected="selected">${item.value}</option>
						                </c:when>
										<c:otherwise>
											<option value="${item.name}">${item.value}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</form:select> 
							<font color="red"><form:errors path="giaoCaTruc" /></font>
						</td>
						<td colspan="2">
							&nbsp;<input type="text" value="${giaoNgayTruc}" name="giaoNgayTruc" id="giaoNgayTruc" size="25" maxlength="10">
							 <img alt="calendar" title="Click to choose the start date" id="choosegiaoNgayTruc" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					  		<font color="red">${errorStartTime}<form:errors path="giaoNgayTruc" /></font>
						</td>
						</c:otherwise>
					</c:choose>
					
				  	<td><fmt:message key="catruc.ca"/><font color="red">(*)</font></td>
				  	<td style="width: 80px;">
				  		<form:select path="nhanCaTruc"  style="width: 80px;height:20px; padding-top: 4px;">
							<c:forEach var="item" items="${caList}">
								<c:choose>
					                <c:when test="${item.value == nhanCaTruc}">
					                    <option value="${item.value}" selected="selected">${item.value}</option>
					                </c:when>
									<c:otherwise>
										<option value="${item.value}">${item.value}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select> 
						<font color="red"><form:errors path="nhanCaTruc" /></font>
					</td>
					<td>
						&nbsp;<input type="text" value="${nhanNgayTruc}" name="nhanNgayTruc" id="nhanNgayTruc" onformchange="functionNextShift()"  size="25" maxlength="10">
				  	 	<img alt="calendar" title="Click to choose the start date" id="chooseNhanNgayTruc"  style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						<font color="red">${errorEndTime}<form:errors path="nhanNgayTruc" /></font>
				  	</td>
				  </tr>
				   <tr>
				  	<td><fmt:message key="catruc.xacnhan"/><font color="red">(*)</font></td>
				  	<td colspan="3">
				  		<input type="password" value="${passwordGiao}" name="passwordGiao" id="passwordGiao" size="40" maxlength="50">
			        </td>
				  	<td><fmt:message key="catruc.xacnhan"/><font color="red">(*)</font></td>
				  	<td colspan="3">
				  		<input type="password" value="${passwordNhan}" name="passwordNhan" id="passwordNhan" size="40" maxlength="50">
				  	</td>
				  </tr>  
			       <tr>
			           <td colspan="8" align="center">
			               	<input type="submit" class="button" id = "btxacnhan"  value="<fmt:message key="button.xacnhan"/>" />
			            	<input type="button" id="btSendSMS" name="btSendSMS" class="button" value="<fmt:message key="button.sendSMS"/>"> 
			            </td>
			       </tr>
			    </table>
			
			</form:form>
			</div>
			
<div id="jqxwindow">
		<div><fmt:message key="alShift.titleSend"/></div>
		<div>
       	<table class="simple2">
       		<tr>
       			<td style="width:70px;"><fmt:message key="alShift.sendTo"/></td>
				<td style="width:120px;">
					<div id="sendToCb" align="left"></div>
				</td>
				<td><input type="text" name="sendTo" id="sendTo"/></td>
			</tr>
			<tr>
				<td><fmt:message key="alShift.title"/></td>
				<td colspan="2">
					 <div id="title"></div>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="alShift.content"/></td>
				<td colspan="2">
					<textarea rows="3" cols="85" id="content" name="content" style="width: 100%;"></textarea>
       			</td>
       		</tr>
       		<tr>
       			<td></td>
       			<td colspan="2">
       				<input type="button" class="button" value="Send" id="btSend" />
                    <input type="button" class="button" value="Cancel" id="btCancel" />
                </td>
       		</tr>
       </table>
     </div>
</div>
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
	    inputField		:	"giaoNgayTruc",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "choosegiaoNgayTruc",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	});
	Calendar.setup({
	    inputField		:	"nhanNgayTruc",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseNhanNgayTruc",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	});
	
	</script>
</c:if>
<script type="text/javascript">

${grid2G}
${grid3G}
${gridPSCore}
${gridIPBB}
${gridSCL}
${gridHCMR}
${gridGSSite}
${gridCSCore}
${gridWork}

var theme =  getTheme(); 
$('#btSend').jqxButton({ theme: theme });
$('#btCancel').jqxButton({ theme: theme });
$('#btSendSMS').jqxButton({ theme: theme });
$('#btxacnhan').jqxButton({ theme: theme });
$('#btAdd').jqxButton({ theme: theme });
$('#btAdd1').jqxButton({ theme: theme });
$('#btAdd2').jqxButton({ theme: theme });
$('#btAdd3').jqxButton({ theme: theme });
$('#btAdd4').jqxButton({ theme: theme });
$('#btAdd5').jqxButton({ theme: theme });
$('#btAdd6').jqxButton({ theme: theme });
$('#btAdd7').jqxButton({ theme: theme });
$('#btAdd8').jqxButton({ theme: theme });
$('#btUpdate').jqxButton({ theme: theme });
$('#btUpdate1').jqxButton({ theme: theme });
$('#btUpdate2').jqxButton({ theme: theme });
$('#btUpdate3').jqxButton({ theme: theme });
$('#btUpdate4').jqxButton({ theme: theme });
//handle context menu cong viec co dinh trong ca
$("#menuReport").on('itemclick', function (event) {
	    var args = event.args;
	    var rowindex = $("#gridWork").jqxGrid('getselectedrowindex');
	    var row = $("#gridWork").jqxGrid('getrowdata', rowindex);
	    var exportFileName =  "<c:out value='${exportFileNameWorkShift}'/>";
	    
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
	   	  	window.location = '${pageContext.request.contextPath}/w_working_managements/formContentDetails.htm?id='+row.id+'&type=cvcd';   
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	    	if (answer)
	    	{
	    		window.location = '${pageContext.request.contextPath}/working/delete.htm?id='+row.id+'&type=cvcd&note=caTruc';  
	    	}
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridWork").jqxGrid('exportdata', 'xls', exportFileName);
       	 	
	    }
});



//handle context menu 2G
$("#menu2G").on('itemclick', function (event) {
	    var args = event.args;
	    var rowindex = $("#grid2G").jqxGrid('getselectedrowindex');
	    var row = $("#grid2G").jqxGrid('getrowdata', rowindex);
	    var exportFileName =  "<c:out value='${exportFileName2G}'/>";
	    
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
	   	  	window.location = 'form23G.htm?id='+row.id+'&warningTp=2G';   
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	    	if (answer)
	    	{
	    		window.location = 'delete23G.htm?id='+row.id+'&warningTp=2G';  
	    	}
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#grid2G").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});

// handle context menu 3G
$("#menu3G").on('itemclick', function (event) {
	    var args = event.args;
	    var rowindex = $("#grid3G").jqxGrid('getselectedrowindex');
	    var row = $("#grid3G").jqxGrid('getrowdata', rowindex);
	    var exportFileName =  "<c:out value='${exportFileName3G}'/>";
	    
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
	   	  	window.location = 'form23G.htm?id='+row.id+'&warningTp=3G';   
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	    	if (answer)
	    	{
	    		window.location = 'delete23G.htm?id='+row.id+'&warningTp=3G';  
	    	}
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#grid3G").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});

// handle context menu 3G
$("#menuPSCore").on('itemclick', function (event) {
	    var args = event.args;
	    var rowindex = $("#gridPSCore").jqxGrid('getselectedrowindex');
	    var row = $("#gridPSCore").jqxGrid('getrowdata', rowindex);
	    var exportFileName =  "<c:out value='${exportFileNamePSCore}'/>";
	    
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
	   	  	window.location = '${pageContext.request.contextPath}/warning/form.htm?id='+row.id+'&warningTp=PS_CORE&note=caTruc';   
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	    	if (answer)
	    	{
	    		window.location = '${pageContext.request.contextPath}/warning/delete.htm?id='+row.id+'&warningTp=PS_CORE&note=caTruc';  
	    	}
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridPSCore").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});


//handle context menu 3G
$("#menuCSCore").on('itemclick', function (event) {
	    var args = event.args;
	    var rowindex = $("#gridCSCore").jqxGrid('getselectedrowindex');
	    var row = $("#gridCSCore").jqxGrid('getrowdata', rowindex);
	    var exportFileName =  "<c:out value='${exportFileNameCSCore}'/>";
	    
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
	   	  	window.location = '${pageContext.request.contextPath}/warning/form.htm?id='+row.id+'&warningTp=CS_CORE&note=caTruc';   
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	    	if (answer)
	    	{
	    		window.location = '${pageContext.request.contextPath}/warning/delete.htm?id='+row.id+'&warningTp=CS_CORE&note=caTruc';  
	    	}
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridCSCore").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});

// handle context menu 3G
$("#menuIPBB").on('itemclick', function (event) {
	    var args = event.args;
	    var rowindex = $("#gridIPBB").jqxGrid('getselectedrowindex');
	    var row = $("#gridIPBB").jqxGrid('getrowdata', rowindex);
	    var exportFileName =  "<c:out value='${exportFileNameIPBB}'/>";
	    
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
	   	  	window.location = '${pageContext.request.contextPath}/warning/form.htm?id='+row.id+'&warningTp=IPBB&note=caTruc';   
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	    	if (answer)
	    	{
	    		window.location = '${pageContext.request.contextPath}/warning/delete.htm?id='+row.id+'&warningTp=IPBB&note=caTruc';  
	    	}
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridIPBB").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});

// handle context menu 3G
$("#menuSCL").on('itemclick', function (event) {
	    var args = event.args;
	    var rowindex = $("#gridSCL").jqxGrid('getselectedrowindex');
	    var row = $("#gridSCL").jqxGrid('getrowdata', rowindex);
	    var exportFileName =  "<c:out value='${exportFileNameSCL}'/>";
	    
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
	    	
	   	  	window.location = 'form23G.htm?id='+row.id+'&warningTp=SU_CO_LON';   
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	    	if (answer)
	    	{
	    		window.location = '${pageContext.request.contextPath}/warning/delete.htm?id='+row.id+'&warningTp=SU_CO_LON&note=caTruc';  
	    	}
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridSCL").jqxGrid('exportdata', 'xls', exportFileName);
	    }
	    
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.sendMessage"/>')  {
	    	var content=row.alarm+".\n";
	    	content+="Thiet bi:"+row.system+".\n";
	    	
	    	$("#title").val("SU_CO");
    		$("#sendTo").val("");
        	$("#sendToCb").val("");
	        $("#content").val(content);
	        var mainDemoContainer = $('#menuSCL');
	        var offset = mainDemoContainer.offset();
	    	$('#jqxwindow').jqxWindow({position: { x: 150, y: offset.top - 200}});
	       	$("#jqxwindow").jqxWindow('open');
	    }
});

// handle context menu 3G
$("#menuHCMR").on('itemclick', function (event) {
	    var args = event.args;
	    var rowindex = $("#gridHCMR").jqxGrid('getselectedrowindex');
	    var row = $("#gridHCMR").jqxGrid('getrowdata', rowindex);
	    var exportFileName =  "<c:out value='${exportFileNameHCMR}'/>";
	
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
	   	  	window.location = '${pageContext.request.contextPath}/alarmExtend/form.htm?id='+row.id+'&note=caTruc';   
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	    	if (answer)
	    	{
	    		window.location = '${pageContext.request.contextPath}/alarmExtend/delete.htm?id='+row.id+'&note=caTruc';  
	    	}
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridHCMR").jqxGrid('exportdata', 'xls', exportFileName);
	    }
	    
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.sendMessage"/>')  {
	    	var area='';
	    	if (row.area!=null)
	    		{
	    			area=row.area;
	    		}
	    	var content="CV_HCMR: "+row.alarm+"\n";
	    	content+="Element :"+area+"\n";
	    	
	    	$("#title").val("CV_HCMR");
    		$("#sendTo").val("");
        	$("#sendToCb").val("");
	        $("#content").val(content);
	        var mainDemoContainer = $('#gridHCMR');
	        var offset = mainDemoContainer.offset();
	    	$('#jqxwindow').jqxWindow({position: { x: 150, y: offset.top - 200}});
	       	$("#jqxwindow").jqxWindow('open');
	    }
});
function dateToYMDHMS(date) {
	//alert(date);
    var d = date.getDate();
    var m = date.getMonth()+1;
    var y = date.getFullYear();
    var h = date.getHours();
    var mi = date.getMinutes();
    var s = date.getSeconds();
    //alert(d+'-'+m+'-'+y);
    return '' + (d <= 9 ? '0' + d : d) + '/' + (m<=9 ? '0' + m : m) + '/' + y+ ' ' + (h<=9 ? '0' + h : h)+ ':' + (mi<=9 ? '0' + mi : mi)+ ':' + (s<=9 ? '0' + s : s) ;
}
//handle context menu giam sat nhiet do site
$("#menuGSSite").on('itemclick', function (event) {
	    var args = event.args;
	    var rowindex = $("#gridGSSite").jqxGrid('getselectedrowindex');
	    var row = $("#gridGSSite").jqxGrid('getrowdata', rowindex);
	    var exportFileName =  "<c:out value='${exportFileNameGSSite}'/>";
	    
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
	   	  	window.location = '${pageContext.request.contextPath}/monitor/temperatureForm.htm?id='+row.id+'&note=caTruc';   
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	    	if (answer)
	    	{
	    		window.location = '${pageContext.request.contextPath}/temperaturedelete.htm?id='+row.id+'&note=caTruc';  
	    	}
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridGSSite").jqxGrid('exportdata', 'xls', exportFileName);
	    }
	    
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.sendMessage"/>')  {
	    	var temperature='';
	    	if (row.temperature!=null)
	    		{
	    		temperature=row.temperature;
	    		}
	    	var humidity='';
	    	if (row.humidity!=null)
	    		{
	    		humidity=row.humidity;
	    		}
	    	var statusFridge='';
	    	if (row.statusFridge!=null)
	    		{
	    		statusFridge=row.statusFridge;
	    		}
	    	var statusElectricity='';
	    	if (row.statusElectricity!=null)
	    		{
	    		statusElectricity=row.statusElectricity;
	    		}
	    	var checkDate='';
	    	if (row.checkDate!=null)
	    		{
	    		checkDate=dateToYMDHMS(new Date(row.checkDate));
	    		}
	    	var contactUser='';
	    	if (row.contactUser!=null)
	    		{
	    		contactUser=row.contactUser;
	    		}
	    	var content="TINH_HINH_HA_TANG ("+row.site+"):\n";
	    	content+="Nhiet do :"+temperature+"\n";
	    	content+="Do am    :"+humidity+"\n";
	    	content+="May lanh :"+statusFridge+"\n";
	    	content+="Dien luoi:"+statusElectricity+"\n";
	    	content+="Thoi gian:"+checkDate+"\n";
	    	content+="Lien he  :"+contactUser+"\n";
	    	
	    	$("#title").val("GIAM_SAT_NHIET_DO_SITE");
    		$("#sendTo").val("");
        	$("#sendToCb").val("");
	        $("#content").val(content);
	        var mainDemoContainer = $('#gridGSSite');
	        var offset = mainDemoContainer.offset();
	    	//$('#jqxwindow').jqxWindow({position: { x: offset.left - 100, y: offset.top - 200}});
	    	$('#jqxwindow').jqxWindow({position: { x: 150, y: offset.top - 200}});
	       	$("#jqxwindow").jqxWindow('open');
	    }
});
</script>
<script type="text/javascript">

function setAction(value) {
	  var action = document.getElementById("action");
	  action.value = value;

	  return true;
	 }
	 
function hiddenClick(id) {
	$("#" + id).slideToggle();

	if ($("#openCloseIdentifier_" + id).is(":hidden")) {
		$("#openCloseIdentifier_" + id).show();
	}
	else {
		$("#openCloseIdentifier_" + id).hide();
	}
}
$(document).ready(function(){
	
	var xacnhanTC =  $("#xacnhanTC").val();
	if (xacnhanTC=='bottom')
	{
		$("html, body").animate({ scrollTop: $(document).height() }, "fast");
	}
	
});
</script>
<script type="text/javascript">
var theme =  getTheme();
$(document).ready(function(){

	// create jqxWindow.
 	
    $("#jqxwindow").jqxWindow({ resizable: true, theme: theme, autoOpen: false, width: 800, minHeight: 250,isModal: true});
  //Create a jqxDropDownList title
   var urlWarningType = "${pageContext.request.contextPath}/ajax/getBrandName.htm";
    // prepare the data
    var sourceWarningType =
    {
        datatype: "json",
        datafields: [
            { name: 'name' },
            { name: 'value' }
        ],
        url: urlWarningType,
        async: false
    };
    var dataAdapterWarningType = new $.jqx.dataAdapter(sourceWarningType);
    $("#title").jqxComboBox({ source: dataAdapterWarningType, displayMember: "value", valueMember: "name", width: '100%',height: 20,theme: theme,autoDropDownHeight: true,autoOpen: true });
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
	         terms.push("");
	         value = terms.join(",");
	    	}
       
        return value;
    };
  //Create a jqxDropDownList title
   ${groupList}
    $("#sendTo").jqxInput({ height: 20, width: '100%', theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#sendTo").jqxInput({ query: item });
            response(groupList);
        },
        renderer: renderer
    });
   $("#sendToCb").jqxComboBox({ source: groupList, height: 20, width: '100%', theme: theme ,autoDropDownHeight: true }); 
  //Create a jqxDropDownList title
   $('#sendToCb').on('select', function (event) 
  		{
  			 var itemB= $('#sendTo').val();
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
  		
  			$("#sendTo").val(itemB);
  			//alert(itemB);
  		});
   
    $("#btSendSMS").click(function (event) {
    	$("#sendTo").val("");
    	$("#sendToCb").val("");
    	$("#title").val("");
    	$("#content").val("");
    	var mainDemoContainer = $('#btSendSMS');
        var offset = mainDemoContainer.offset();
    	$('#jqxwindow').jqxWindow({position: { x: offset.left - 400, y: offset.top - 400}});	
       	$("#jqxwindow").jqxWindow('open');
    });

    $("#btCancel").click(function (event) {
        $("#jqxwindow").jqxWindow('close');  
    });
 

    $("#btSend").click(function (event) {
    	//alert($("#sendToCb").val());
    	var title="";
    	if ($("#title").val()!='')
    		title = $("#title").jqxComboBox('getSelectedItem').value;
    	
    	var content = title+": "+$("#content").val();
    	//alert(content);
    	var smsContents = {};
    	smsContents.isdn       = $("#sendTo").val();
    	smsContents.alarmType  = title;
    	smsContents.message    = content;
    	if (smsContents.message!=null&&smsContents.message!=''&&smsContents.alarmType!=null&&smsContents.alarmType!=''&&smsContents.isdn!=null&&smsContents.isdn!='')
    		{
    			
    						$.ajax({
    				    	    type: "POST",
    				    	    url: "${pageContext.request.contextPath}/caTruc/sendSMS.htm",
    				    	    data: JSON.stringify(smsContents),
    				    	    dataType: 'json',
    				    	    contentType: 'application/json',
    				    	    mimeType: 'application/json',
    				    	    beforeSend: function(){},
    				    	    complete: function(){},
    				    	    success: function(data){
    				    	    	var obj = data.value;
    				    	
    				         	if (obj!='1')
    				    	    		{
    				    	    			alert("Gửi SMS không thành công tới các số điện thoại:"+obj);
    				    	    		}
    				    	    	else
    				    	    		{
	    				    	    		$("#jqxwindow").jqxWindow('close');
		    				               	alert("Gửi SMS thành công");
		    				               	$("#sendTo").val("");
		    				            	$("#sendToCb").val("");
		    				            	$("#title").val("");
		    				            	$("#content").val("");
		    				            	
		    				    	    }
    				    	    }
    				    	});
		    	
		    		}
		    	else
		    		{
		    			alert("Gửi SMS không thành công");
		    		}
		    });
		   
		});
	
	$("#nhanCaTruc").change(function(){
		functionNextShift();
	});

	function functionNextShift()
	{
		
		var nhanNgayTruc = $("#nhanNgayTruc").val();
		var nhanCaTruc = $("#nhanCaTruc").val();
		if (nhanNgayTruc==null)
			{
			nhanNgayTruc='';
			}
		if (nhanCaTruc==null)
		{
			nhanCaTruc='';
		}
		if (nhanNgayTruc != '' &&nhanCaTruc != '') {
			$.getJSON("${pageContext.request.contextPath}/ajax/getNextShift.htm",{ngaytruc : nhanNgayTruc,catruc:nhanCaTruc}, function(j){
				var nhanCatruong= j.catruong;
				var nhanCaVien=j.cavien;
				var nhanUCTT=j.uctt;
				//alert(nhanCatruong+'-'+nhanCaVien+'-'+nhanUCTT);
				$("#nhanUsername").val(nhanCatruong);
				$("#nhanCaVien").val(nhanCaVien);
			
			});
		}
	}
</script>