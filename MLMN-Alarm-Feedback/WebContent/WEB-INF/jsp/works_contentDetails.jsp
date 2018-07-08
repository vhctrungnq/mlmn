<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
.jqx-grid-cell > div {
    height: 100%;
    margin: 0 !important;
}
    
</style>

<style>
	.uploadifive-button {
		margin-right: 10px;
	}
	
	.queue-item {
		overflow: auto;
		margin-bottom: 10px;
		padding: 0 3px 3px;
	}
	
	.uploadifive-queue-item {
	    margin-top: 3px;
    	padding: 1px 10px;
	}
	
	.uploadifive-queue-item .close {
    background: url("${pageContext.request.contextPath}/js/uploadifive/uploadifive-cancel.png") no-repeat scroll 0 0 transparent;
    display: block;
    float: left;
    height: 16px;
    text-indent: -9999px;
    width: 16px;
	}
	
	.filename{
	color: #0560A6;
	}
</style>
<title><spring:message code="header.title.contentFormDetails"/></title>
<content tag="heading"> </content>
 <div style="font-family: times New Roman;font-weight:bold;font-size: 20px;color:gray;"><spring:message code="Chi tiết công việc: "/><span style="color:red;">${w_working_details.tenCongViec}</span><spring:message code="  Loại công việc: "/><span style="color:red;">${w_working_typesByIdWorks.name}</span></div>

<div style="font: 12px arial,helvetica,sans-serif;">
<form:form commandName="w_working_details" name="checkform" method="post" action="formContentDetails.htm" enctype="multipart/form-data">
<div>
		<form:hidden path="id"/>
		<form:hidden path="wwmFkId"/>
		<form:hidden path="dayShift"/>
		<form:hidden path="shift"/>
		<form:hidden path="idWorkTypes"/>
		<form:hidden path="yearShift"/>
		<form:hidden path="weekShift"/>
		<input value="${received}" id=received name="received" type="hidden"/>
		<input type="hidden" value="${type}" name="type" id="type" size="10" maxlength="10">
		<input type="hidden" value="${note}" name="note" id="note" size="10" maxlength="10">
		<input type="hidden" value="${isAllowRate}" name="isAllowRate" id="isAllowRate" size="10" maxlength="10">
		<input type="hidden" value="${statusUpdate}" name="statusUpdate" id="statusUpdate" size="10" maxlength="10">
		<input type="hidden" value="${maCVCha}" name="maCVCha" id="maCVCha" size="10" maxlength="10">
		<input type="hidden" value="${status}" name="status" id="status" size="10" maxlength="10">
	</div>
<table class="simple2">
	 <tr>
           <td style="vertical-align:middle;" class="wid15 mwid120 bgf1"><b><spring:message code="title.qLDanhSachCongViec.maCongViec"/></b></td>
           <td class="wid35 mwid250">
           		<form:hidden path="maCongViec"/>${w_working_details.maCongViec}
           </td>
           <td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.nguoiGiaoViec"/></b></td>
           <td>
           		<form:hidden path="nguoiGiaoViec"/>${w_working_details.tenNguoiGiaoViec}&nbsp;<span>&lt;${getEmailOfNguoiGiaoViec.email}&gt;</span>
           </td>
           
      </tr> 
      
      <c:choose>
      	<c:when test="${statusUpdate==1}">
      	<tr>
           <td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.tenCongViec"/></b></td>
           <td colspan="3">
           		<div class="timeLeft" style="font-size: 13px;"><form:hidden path="tenCongViec"/>${w_working_details.tenCongViec}</div>
           </td>
      	</tr>
      	 <tr>
      			<td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.nguoiChuTri"/></b></td>
		         <td>
		           		<div id='cbnguoiChuTri'></div>
			           	<form:hidden path="nguoiChuTri" value="${w_working_details.nguoiChuTri}"  id="nguoiChuTri"/>
				          &nbsp;<form:errors path="nguoiChuTri" cssClass="error"/>
		         </td>
		         <td style="vertical-align:middle;" class="wid15 mwid160 bgf1"><b><spring:message code="title.qLDanhSachCongViec.nguoiNhanViec"/></b></td>
		           <td>
		           		<div id='cbnguoiNhanViec'></div>
			           <form:hidden path="nguoiNhanViec" id="nguoiNhanViec" value="${w_working_details.nguoiNhanViec}"/>
			             &nbsp;<form:errors path="nguoiNhanViec" cssClass="error"/>
		           </td>
		    </tr>
		    <tr>
		           <td><b><spring:message code="title.qLDanhSachCongViec.ccEmail"/></b></td>
		           <td ><form:input type="text" path="ccEmail"  id ="ccEmail"/>
		           <!-- <div id='ccEmail'></div> -->
		           </td>
		        	<td style="vertical-align:middle;" class="bgf1"><b>Tên trạm:</b></td>
			        <td>
			        		<form:input path="site" maxlength="250" cssClass="wid40"/>&nbsp;<form:errors path="site" cssClass="error"/>
			        		
			        		&nbsp;&nbsp;<b>Lat-Long:</b>
			       			&nbsp;&nbsp;<a target="_blank"  href="https://www.google.com/maps/preview?q=${w_working_details.latitude},${w_working_details.longitude}">${w_working_details.latitude}</a>
			      			 &nbsp;&nbsp;- &nbsp;&nbsp;
			      			 <a target="_blank"  href="https://www.google.com/maps/preview?q=${w_working_details.latitude},${w_working_details.longitude}">${w_working_details.longitude}</a>
			      			
			      	 </td>
		      </tr>
		      <tr>
		      		<td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.ngayGiaoViec"/></b></td>
		           <td>
		           		<input type="text" id="assignDate" name="assignDate" value="${w_working_details.ngayGiaoViec}" class="wid30" maxlength="30"/>
        				<img alt="calendar" title="Click to choose the assign date" id="chooseAssignDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;<form:errors path="assignDate" cssClass="error"/>
        			</td>
		      		<td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.ngayGiaoHoanThanh"/></b></td>
		           <td>
		           		<input type="text" id="estimateDate" name="estimateDate" value="${w_working_details.ngayGiaoHoanThanh}" class="wid30" maxlength="30"/>
	        			<img alt="calendar" title="Click to choose the estimate date" id="chooseEstimateDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;<form:errors path="estimateDate" cssClass="error"/>
	        
		           </td>           
		      </tr>
		      <tr>
	      		<td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.tomTatNoiDung"/></b></td>
	           	<td colspan="3">
	           		<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
					<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
					
			           		<form:textarea cols="80" rows="3"  path="tomTatNoiDung" maxlength="900" ></form:textarea>
							 <script type="text/javascript">
								CKEDITOR.replace( 'tomTatNoiDung',
								{
									toolbar :
										[
											['NewPage','-','Undo','Redo'],
											['Find','Replace','-','SelectAll','RemoveFormat'],
											['Link', 'Unlink', 'Image'],
											['FontSize', 'Bold', 'Italic','Underline'],
											['NumberedList','BulletedList','-','Blockquote'],
											['TextColor', '-', 'Smiley','SpecialChar', '-', 'Maximize']
										],
									height: '100px'
								});
						 	</script>
	           	</td>
		      </tr>
		      <tr>
		      	<td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.noiDungCongViec"/></b></td>
		        <td colspan="3">
		        		<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
		 				<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
			
		           		<form:textarea cols="80" rows="8"  path="noiDung" maxlength="900" ></form:textarea>
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
		      
	 
				  <tr >
			      	<td><b><fmt:message key="isoEquipment.fileAttach"/></b></td>
			           <td colspan="3"><input id="file_upload" name="file_upload" type="file" multiple="true"></td>
						<!-- <td><input class="button" type="file" size="50%" name="file" id="file"/></td> -->
			      </tr>
				  <tr>
				      	<td style="vertical-align:middle;"><b><spring:message code="title.qLDanhSachCongViec.tepCongVan"/></b></td>
				      	<td colspan="3">
				      			<input type="hidden" id="fileId" name="fileId" value="${file_attachment}"/>
								<div style="margin-bottom: 7px">
									<div class="queue-item">
										<div id="queue-upload"></div>
										<div id="queue"></div>
									</div>
									
									
								</div>
				      	</td>
				      </tr>
      	</c:when>
      	<c:otherwise>
      		<tr>
	           <td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.tenCongViec"/></b></td>
	           <td>
	           		<div class="timeLeft" style="font-size: 13px;"><form:hidden path="tenCongViec"/>${w_working_details.tenCongViec}</div>
	           </td>
     
      			<td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.nguoiChuTri"/></b></td>
		         <td>
		           		 <form:hidden path="tenNguoiChuTri" />${w_working_details.tenNguoiChuTri}&nbsp;<span>&lt;${getEmailOfNguoiChuTri}&gt;</span>
		         </td>
		           
		      </tr>
		      <tr>
		      		<td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.ngayGiaoViec"/></b></td>
		           <td>
		           		 <form:hidden path="assignDate" />${w_working_details.ngayGiaoViec}
		           	</td>
		           <td style="vertical-align:middle;" class="wid15 mwid160 bgf1"><b><spring:message code="title.qLDanhSachCongViec.nguoiNhanViec"/></b></td>
		           <td>
		           		<form:hidden path="nguoiNhanViec" />${w_working_details.tenNguoiNhanViec}&nbsp;<span>&lt;${getEmailOfNguoiNhanViec}&gt;</span>
		           </td>
		           
		      </tr>
		      <tr>
		      		<td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.ngayGiaoHoanThanh"/></b></td>
		           <td>
		           		<form:hidden path="estimateDate" />${w_working_details.ngayGiaoHoanThanh}
		           </td>
		           <td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.thoiGianConLai"/></b></td>
		           <td>
		           		<div id="timeLeftFirst" class="timeLeft"></div>
		           	</td>                
		      </tr>
		      <tr>
	      		<td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.tomTatNoiDung"/></b></td>
	           <td colspan="3">
	           		<form:hidden path="tomTatNoiDung" />${ w_working_details.tomTatNoiDung}
	           </td>
		      </tr>
		      <tr>
		      	<td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.noiDungCongViec"/></b></td>
		        <td colspan="3">
		        		<form:hidden path="noiDung" />${w_working_details.noiDung}
		        </td>
		      </tr>
		      <tr>
			      	<td style="vertical-align:middle;" class="bgf1"><b>Tên trạm:</b></td>
			        <td>
			        		<form:hidden path="site" /><a target="_blank"  href="${pageContext.request.contextPath}/map/list.htm?siteid=${w_working_details.site}">${w_working_details.site}</a>
			        </td>
			        <td style="vertical-align:middle;" class="bgf1"><b>Lat-Long:</b></td>
			        <td>
					        <form:hidden path="longitude" />
					        <form:hidden path="latitude" />
			        		<a target="_blank"  href="https://www.google.com/maps/preview?q=${w_working_details.latitude},${w_working_details.longitude}">${w_working_details.latitude}</a>
			      			 &nbsp;&nbsp;- &nbsp;&nbsp;
			      			 <a target="_blank"  href="https://www.google.com/maps/preview?q=${w_working_details.latitude},${w_working_details.longitude}">${w_working_details.longitude}</a>
			      			
			        </td>
			      </tr>
		      <tr>
		      	<td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.tepCongVan"/></b></td>
		        <td colspan="3">
		       		 <input type="hidden" id="fileId" name="fileId" value="${file_attachment}"/>
		       		<c:forEach var="item" items="${file_attachment}">
		       			<a class="styleTepCongVan" href="exportTepCongVan.htm?filePath=${item.filePath}&&filename=${item.fileName}">${item.fileName }</a><br/>
		       		</c:forEach>
		        </td>
		      </tr>
      	</c:otherwise>
      </c:choose>
           
      <c:choose>
      	<c:when test="${statusUpdate==3}">
      		  <tr>
		      		<td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.tinhTrang"/></b></td>
		           <td colspan="3">
		           		<c:forEach var="items" items="${getTinhtrangList}">
				              	<c:choose>
				                <c:when test="${items.value == tinhTrang}">
				                    <p>${items.name}</p>
				                </c:when>
				              	</c:choose>
					    </c:forEach>
		           </td>
		      </tr>
		      <tr>
		      	<td style="vertical-align:middle;" class="bgf1">
	    			<b><spring:message code="title.qLDanhSachCongViec.hoanThanhCV"/></b>
	    		</td>
	    		<td><form:hidden path="actualDate" />${w_working_details.ngayHoanThanh}
    			</td>
    			<td style="vertical-align:middle;" class="bgf1"><b>Tiến độ hoàn thành:</b></td>
	    		<td>${w_working_details.finishRate}<form:hidden path="finishRate" />
	    		<input type="hidden" id="action" name="action"></td>
	    		
		      </tr>
		</c:when>
      	<c:otherwise>
      		<tr> 
	           <td style="vertical-align:middle;" class="bgf1"><b><spring:message code="title.qLDanhSachCongViec.tinhTrang"/></b></td>
	           <td >
	           	<div class="psr ovh select" style="width:140px;">
						<form:select path="tinhTrang" class="wid100 select">
	          				<c:forEach var="items" items="${getTinhtrangList}">
				              	<c:choose>
				                <c:when test="${items.value == tinhTrang}">
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
			  <td style="vertical-align:middle;" class="bgf1"><b> Đánh giá:</b></td>
	    		<td>
	    			<div class="psr ovh select" style="width:140px;">
					    	<form:select path="assessResult" class="wid100 select">
	          				<c:forEach var="items" items="${getDanhGiaList}">
				              	<c:choose>
				                <c:when test="${items.value == assessResult}">
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
	     		<td style="vertical-align:middle;" class="bgf1">
	    			<b><spring:message code="title.qLDanhSachCongViec.hoanThanhCV"/></b>
	    		</td>
	    		<td>
	    			<input type="text" id="actualDate" name="actualDate" value="${w_working_details.ngayHoanThanh}" size="20" maxlength="30"/>
	    			<img alt="calendar" title="Click to choose the actual date" id="chooseActualDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;<form:errors path="actualDate" cssClass="error"/>&nbsp;
	    		</td>
	    		<td style="vertical-align:middle;" class="bgf1">
	    			<b>Tiến độ hoàn thành:</b>
	    		</td>
	    		<td>
	    			<input type="text"  id = "finishRate" name="finishRate" value="${w_working_details.finishRate}" size="10" maxlength="10"/>(%) 
	             	<input type="hidden" id="action" name="action">
	    		</td>
	    	</tr>
		</c:otherwise>
      </c:choose>
			
	  
      <tr>
	      	<td style="vertical-align:middle;" class="bgf1"><b><spring:message code="Nhận xét công việc"/></b></td>
	        <td colspan="3">
	       		<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
				<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
				
		           		<form:textarea cols="80" rows="5"  path="danhGia" maxlength="900" ></form:textarea>
						 <script type="text/javascript">
							CKEDITOR.replace( 'danhGia',
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
      <%-- <c:if test="${isAllowRate == 1}"> --%>
		      <tr>
		   		 <td colspan="4"  align="right">
		   		 	<input class="button" type="submit" onclick="setAction('save')" id="NgayHTCongViec" value="<spring:message code="button.saveRate"/>" />
		            <input class="button" type="submit" onclick="setAction('send')" id="guiMail" value="<spring:message code="button.saveAndSend"/>"/>
		   		 </td>
		      </tr>
		  <%--  </c:if> --%>
    </table>
    <div style="font-family: times New Roman;font-weight:bold;font-size: 18px;color:gray;padding-top:10px;"><spring:message code="Danh sách công việc liên quan"/></div>
             <c:if test="${isAllowRate == 1 && status != 'QUA_HAN'}">
             <div  align="right">
             <a href="formContent.htm?idWorkTypes=${w_working_details.idWorkTypes}&deptCode=${w_working_details.dept}&wwmFkId=${w_working_details.id}&note=form&maCVCha=${w_working_details.maCongViec}"><spring:message code="button.add"/></a>
             </div>
             </c:if>
            <div class = "tableStandar">
            
            <display:table name="${works_childrenList}" class="simple2" id="item2" requestURI="" pagesize="15"  sort="external" defaultsort="1">
  				<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${item2_rowNum}"/> </display:column>
			    <display:column property="maCongViec" class="link" href="formContentDetails.htm" paramId="id" paramProperty="id" titleKey="title.qLDanhSachCongViec.maCongViec" sortable="true" sortName="MA_CONG_VIEC">  
          			</display:column> 
				<display:column style="width:250px;word-wrap: break-word;" property="tenCongViec" class="link" href="formContentDetails.htm?type=${type}&wwmFkId=${item2.wwmFkId}&note=form" paramId="id" paramProperty="id" titleKey="title.qLDanhSachCongViec.tenCongViec" sortable="true" sortName="TEN_CONG_VIEC">  
          			</display:column>					
				<display:column property="tenNguoiGiaoViec" titleKey="title.qLDanhSachCongViec.nguoiGiaoViec" sortable="true" sortName="TEN_NGUOI_GIAO_VIEC"/>
				<display:column property="tenNguoiChuTri" titleKey="title.qLDanhSachCongViec.nguoiChuTri" sortable="true" sortName="TEN_NGUOI_CHU_TRI"/>
				<display:column property="tenNguoiNhanViec" titleKey="title.qLDanhSachCongViec.nguoiNhanViec" sortable="true" sortName="TEN_NGUOI_NHAN_VIEC"/>						
				<display:column property="assignDate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="title.qLDanhSachCongViec.ngayGiaoViec" sortable="true" sortName="ASSIGN_DATE"/>
				<display:column property="estimateDate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="title.qLDanhSachCongViec.ngayGiaoHoanThanh" sortable="true" sortName="ESTIMATE_DATE"/>
				<display:column property="actualDate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="title.qLDanhSachCongViec.ngayHoanThanh" sortable="true" sortName="ACTUAL_DATE"/>
				<display:column property="finishRate" titleKey="% HT" class="HT rightColumnMana"  sortable="true" sortName="FINISH_RATE" />
				<display:column property="tenTinhTrang" titleKey="title.qLDanhSachCongViec.tinhTrang" sortable="true" sortName="TEN_TINH_TRANG"/>				
				<display:column property="comments" titleKey="title.qLDanhSachCongViec.comments" sortable="true" sortName="COMMENTS"/>				
						<%-- <c:if test="${received=='N' }"> --%>
					<display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
					<%-- <c:choose> --%>
		 				<%-- <c:when test="${item2.nguoiGiaoViec == username || item2.nguoiChuTri == username ||fn:contains(item2.nguoiNhanViec, username)  ||item2.createdBy == username}"> --%>
						<c:if test="${isAllowRate == 1}">
							<a href="formContent.htm?id=${item2.id}&idWorkTypes=${item2.idWorkTypes}&deptCode=${deptCode}&wwmFkId=${item2.wwmFkId}&note=form&maCVCha=${w_working_details.maCongViec}"><spring:message code="button.edit"/></a>&nbsp;
		    				<a href="deleteContent.htm?id=${item2.id}&wwmFkId=${item2.wwmFkId}&note=form" onclick="return confirm('<spring:message code="messsage.confirm.delete"/>')"><spring:message code="button.delete"/></a>&nbsp;
		    			</c:if>
	    			<%-- </c:choose>	 --%>
	    			</display:column>
				<%-- </c:if> --%>
				<display:setProperty name="export.csv.include_header" value="true" />
				<display:setProperty name="export.excel.include_header" value="true" />
				<display:setProperty name="export.xml.include_header" value="true" />							
				
				<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
				<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
				<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
			</display:table>
			</div>
   	<c:choose>
               <c:when test="${userIsNguoiChuTri == 'Y'}">
               <div style="font-family: times New Roman;font-weight:bold;font-size: 18px;color:gray;padding-top:10px;"><spring:message code="Theo dõi tiến độ công việc"/></div>
                <div  align="right">
                <a href="formProcesses.htm?id_work_mana=${id_work_mana}"><spring:message code="button.add"/></a>&nbsp;
                </div>
               <div class = "tableStandar">
               <display:table name="${works_processesList}" class="simple2" id="item"
						requestURI="" pagesize="15"  sort="external" defaultsort="1">
					  	<display:column property="title" titleKey="title.qLTienDoCongViec.title" sortable="true" sortName="TITLE"  style="min-width:200px;max-width:200px;"/>
						<display:column property="actualDate" format="{0,date,dd/MM/yyyy HH:mm:ss}" titleKey="title.qLTienDoCongViec.actualDate" sortable="true" sortName="ACTUAL_DATE"  style="min-width:120px;max-width:120px;"/>
						<display:column property="estimateDate" format="{0,date,dd/MM/yyyy HH:mm:ss}" titleKey="title.qLTienDoCongViec.estimateDate" sortable="true" sortName="ESTIMATE_DATE" style="min-width:120px;max-width:120px;"/>
						<display:column property="content" titleKey="title.qLTienDoCongViec.content" sortable="true" sortName="CONTENT" style="min-width:200px;"/>
						<display:column property="remark" titleKey="title.qLTienDoCongViec.remark" sortable="true" sortName="REMARK" style="min-width:200px;"/>
						<display:column property="assess" titleKey="title.qLTienDoCongViec.assess" sortable="true" sortName="ASSESS" style="min-width:120px;max-width:120px;"/>				
						
						<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:60px;">
							<a href="formProcesses.htm?id=${item.id}&id_work_mana=${item.idWorks}"><spring:message code="button.edit"/></a>&nbsp;
		    				<a href="deleteProcesses.htm?id=${item.id}&id_work_mana=${item.idWorks}" onclick="return confirm('<spring:message code="messsage.confirm.delete"/>')"><spring:message code="button.delete"/></a>&nbsp;
		    			</display:column>
		    			
		    			
						<display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header" value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />							
						
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
				</display:table>
				</div>
               </c:when>
               <c:otherwise>
               </c:otherwise>
     </c:choose>
    
    <div class="pb_pt10">
		    <div style="font-family: times New Roman;font-weight:bold;font-size: 18px;color:gray;"><spring:message code="Thảo luận góp ý công việc"/></div>
		    
		    <c:forEach var="itemWorkingProcessesListByIdWorks" items="${workingProcessesListByIdWorks}">
		    	<div id="commentid_${itemWorkingProcessesListByIdWorks.id}">
	            	<ul class="uiUfi">
	            			<li class="ufiNub uiListItem"><i></i></li>
	            			<li class="ufiItem" style="background-color: #65B8DA;color:#FFF;padding: 5px 0 3px 10px;">
	            				<h4 class="fleft"><span>${itemWorkingProcessesListByIdWorks.title} - ngày bàn giao: ${itemWorkingProcessesListByIdWorks.actualDateStr}</span></h4>
	            				<div align="right"><input id="collapse_box_top_${itemWorkingProcessesListByIdWorks.id}" class="collapse" type="button" value="^" onclick="hiddenClick('box_top_${itemWorkingProcessesListByIdWorks.id}')">
	            				</div>
	            			</li>
	            			
	            			<div id="box_top_${itemWorkingProcessesListByIdWorks.id}">
	            				<li class="ufiItem">
		            				<table class="simple2 bgroundFFF">
		            					<tr>
		            						<td class="wid10 mwid150"><b><spring:message code="title.qLTienDoCongViec.actualDate"/></b></td>
		            						<td class="wid20">${itemWorkingProcessesListByIdWorks.actualDateStr}</td>
		            						<td class="wid10 mwid140"><b><spring:message code="title.qLTienDoCongViec.estimateDate"/></b></td>
		            						<td class="wid20">${itemWorkingProcessesListByIdWorks.estimateDateStr}</td>	
		            						<td class="wid10 mwid130"><b><spring:message code="title.qLTienDoCongViec.assess"/></b></td>
		            						<td class="wid20">${itemWorkingProcessesListByIdWorks.assess}</td>
		            					</tr>
		            					<tr>
		            						<td><b><spring:message code="title.qLTienDoCongViec.content"/></b></td>
		            						<td colspan="5">${itemWorkingProcessesListByIdWorks.content}</td>
		            					</tr>
		            					<tr>
		            						<td ><b><spring:message code="title.qLTienDoCongViec.remark"/></b></td>
		            						<td colspan="5">${itemWorkingProcessesListByIdWorks.remark}</td>            						
		            					</tr>
		            				</table>
	            				</li>
	            				<div id="comment_all_${itemWorkingProcessesListByIdWorks.id}">
				            	<c:forEach var="itemfeedbacksList" items="${feedbacksList}">
				            	 <c:choose>
				            	 	<c:when test="${itemWorkingProcessesListByIdWorks.id == itemfeedbacksList.idProcesses}">
						            	<li class="ufiItem">
						            		<div class="clearfix" id="myform">
						            			<c:choose>
								                <c:when test="${userFullName.username == itemfeedbacksList.nguoiNhanXet}">
								                	<a class="floatRightC uiCloseButton" title="Xóa bình luận" onClick="xoa_comment(${itemfeedbacksList.id}, ${itemWorkingProcessesListByIdWorks.id})"></a>
								             </c:when>
								                <c:otherwise>
								                </c:otherwise>
						        				</c:choose>
						            			<div class="_29k">
													<div class="commentContent">
														<span class="noiDungComment">${itemfeedbacksList.fullname}</span>
														<span class="commentBody">${itemfeedbacksList.noiDung}</span>
													</div>
													<div class="commentActions fsm fwn fcg">
														<span class="grey">${itemfeedbacksList.thoiGianFormat}</span>
													</div>
												</div>
						            		</div>
						            	</li>
						            </c:when>
						            <c:otherwise>
						            </c:otherwise>
						           </c:choose>
				            	</c:forEach>
				            	</div>
				            	<li class="ufiItem">
				            		<div class="comment-form">
										<div class="avatar">
										</div>
										<p class="stTextalign">
											<textarea id="noiDungComment_${itemWorkingProcessesListByIdWorks.id}" class="comment-textarea" oid="IWZ9ZOBC" rows="3" cols="10" name="noiDungComment"></textarea>
										</p>
										<p>
											<input class="button buttonR" type="button" id="sendComment_${itemWorkingProcessesListByIdWorks.id}" onclick="commentClick(${itemWorkingProcessesListByIdWorks.id})" value="<spring:message code="title.qLDanhSachCongViec.guiBinhLuan"/>"/>
											<span>Nhấn Shift + Enter để xuống dòng.</span>
											<span id="_commentChars" class="comment-char"></span>
										</p>
									</div>
			            		</li>
			            	</div>	
	            	</ul>
            	</div>
		    </c:forEach>    
    </div>
</form:form>

<div class="styleVeTrangTruoc">

	<c:if test="${type!='shift'&&type!='cvcd'}">
		<c:choose>
               	<c:when test="${note == 'form'}">
               		<a href="formContentDetails.htm?id=${w_working_details.id}&type="><spring:message code="button.vetrangtruoc"/></a>
                </c:when>
                <c:otherwise>
                   <%--  <a href="list.htm"><spring:message code="button.vetrangtruoc"/></a> --%>
                    <a href="javascript:history.back();"><spring:message code="button.vetrangtruoc"/></a> 
                </c:otherwise>
               </c:choose>
		
	</c:if>
	<c:if test="${type=='shift'}">
		<a href="${pageContext.request.contextPath}/working/list.htm?type="><spring:message code="button.vetrangtruoc"/></a>
	</c:if>
	<c:if test="${type=='cvcd'}">
		<a href="${pageContext.request.contextPath}/caTruc/list.htm"><spring:message code="button.vetrangtruoc"/></a>
	</c:if>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min-1.8.9.js"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.jcountdown1.3.js"></script>

<script type="text/javascript">

Calendar.setup({
    inputField		:	"assignDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
    button			:   "chooseAssignDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"estimateDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
    button			:   "chooseEstimateDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});
function xoa_tep(id){
	var r = confirm('Bạn có chắc chắn muốn xóa không?');
	if (r==true)
	{
		$.getJSON("${pageContext.request.contextPath}/w_working_managements/deleteFile.htm",{id: id, idWorkMana: $("#id").val()}, function(j){
		
		var tabX = '';
		for (var i = 0; i < j.length; i++) {
			
			tabX += '<div id="uploadifive-file_upload-file-' + j[i].id +'" class="uploadifive-queue-item complete">';
			tabX += '<a class="close" title="Xóa tệp" onClick="xoa_tep(' + j[i].id + ')">X</a>';
			tabX += '<div>';
			tabX += '<span class="filename">' + j[i].fileName + '</span>';
			tabX += '</div></div>';
		}
		$("#idDeleteFile").html(tabX);
		
		});
	}
};
$(document).ready(function(){
	$("#timeLeftFirst").countdown({
		
		date: '<fmt:formatDate value="${w_working_details.estimateDate}" pattern="MM dd, yyyy HH:mm"/>', //Counting TO a date
		htmlTemplate: "%{d} <span class=\"cd-time\">ngày</span> %{h} <span class=\"cd-time\">:</span> %{m} <span class=\"cd-time\">:</span> %{s} <span class=\"cd-time\"></span>",
		//date: "july 1, 2013 19:24", //Counting TO a date 
		onChange: function( event, timer ){
			
		},
		onComplete: function( event ){
			var status = '<c:out value="${status}"/>';
			if (status == 'QUA_HAN') {
				$(this).html("Quá hạn");
			} else{
				$(this).html("Hoàn thành");
			}
		},
		leadingZero: true,
		direction: "down"
	});
});

//Ẩn block chi tiết tiến độ
$(function() {
	$("#content").hide();
	var x = [];
	var i = 0;
	<c:forEach items="${workingProcessesListByIdWorks}" var="listOfNames">
		x[i] = "<c:out value='${listOfNames.id}' escapeXml='false' />";
		i = i + 1;
	</c:forEach>
	
	for(var i = 0; i < x.length - 1; i++){
		$("#box_top_" + x[i]).hide();		
	};
});

function hiddenClick(id) {
 $("#" + id).slideToggle();
}

function addContent(id){
 $("#content").slideToggle();	
}

function setAction(value) {
	var action = document.getElementById("action");
	action.value = value;

	return true;
}
</script>
<script type="text/javascript">

function focusIt()
{
  var mytext = document.getElementById("actualDate");
  mytext.focus();
}
onload = focusIt;

function xoa_comment(idCom, idProcesses){
	var r = confirm('Bạn có chắc chắn muốn xóa không?')
	if (r==true)
	  {
		var userName = '<c:out value="${username}"/>';
		$.getJSON("${pageContext.request.contextPath}/ajax/deleteComment.htm",{idComment:idCom, id:$("#id").val(), idProcesses: idProcesses}, function(j){
		
		var tabX = '';
		for (var i = 0; i < j.length; i++) {
			tabX += '<li class="ufiItem">';
			tabX += '<div class="clearfix">';
			if(j[i].createdBy == userName){
				tabX += '<a class="floatRightC uiCloseButton" onclick="xoa_comment(' + j[i].id + ', ' + j[i].idProcesses + ')"></a>';
			}
			tabX += '<div class="_29k">';
			tabX += '<div class="commentContent">';
			tabX += ' <span class="noiDungComment">' + j[i].fullname + '</span> ';
			tabX += ' <span class="commentBody">' + j[i].noiDung + '</span> ';
			tabX += '</div>';
			tabX += '<div class="commentActions fsm fwn fcg">';
			tabX += '<span class="grey">' + j[i].thoiGianFormat + '</span>';
			tabX += '</div></div></div></li>';

		}
		$("#comment_all_" + idProcesses).html(tabX);

		$("#noiDungComment_" + idProcesses).focus();
		});
	  }
}


function commentClick(id) {
var userName = '<c:out value="${username}"/>';
	$.getJSON("${pageContext.request.contextPath}/ajax/getCommentOfUser.htm",{id:$("#id").val(), noiDungComment: encodeURI($("#noiDungComment_" + id).val()), userName:userName, idProcesses:id }, function(j){
		
		var tab = '';
		for (var i = 0; i < j.length; i++) {

			tab += '<li class="ufiItem">';
			tab += '<div class="clearfix">';
			if(j[i].createdBy == userName){
				tab += '<a class="floatRightC uiCloseButton" onclick="xoa_comment(' + j[i].id + ', ' + j[i].idProcesses + ')"></a>';
			}
			tab += '<div class="_29k">';
			tab += '<div class="commentContent">';
			tab += ' <span class="noiDungComment">' + j[i].fullname + '</span> ';
			tab += ' <span class="commentBody">' + j[i].noiDung + '</span> ';
			tab += '</div>';
			tab += '<div class="commentActions fsm fwn fcg">';
			tab += '<span class="grey">' + j[i].thoiGianFormat + '</span>';
			tab += '</div></div></div></li>';
		}
		$("#comment_all_" + id).html(tab);

		$("#noiDungComment_" + id).val('');
		$("#noiDungComment_" + id).focus();
	});
	
};

Calendar.setup({
    inputField		:	"actualDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
    button			:   "chooseActualDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});

$(document).ready(function(){
	
	
	
	var theme =  getTheme(); 
	//prepare the data
	var depCode='<c:out value="${deptCode}"/>';
	var nguoiGiaoViec = '<c:out value="${w_working_details.nguoiGiaoViec}"/>';
	//nhan viec, chu tri
	var urlUser = "${pageContext.request.contextPath}/ajax/getUserForWork.htm?deptCode=&diliver="+nguoiGiaoViec;
	var sourceUser =
	{
	    datatype: "json",
	    url: urlUser,
	    datafields: [
	                 { name: 'username'},
	                 { name: 'fullname'},
	                 { name: 'maPhong'},
	                 { name: 'position'}
	             ],
	    async: false
	};
	var dataAdapterUser = new $.jqx.dataAdapter(sourceUser);
	$("#cbnguoiNhanViec").jqxComboBox({source: dataAdapterUser, displayMember: "username", valueMember: "username",multiSelect: true, width: "98%", height: 25, theme: theme,autoOpen: true,autoComplete: true
		,renderer: function (index, label, value) {
	        var item = dataAdapterUser.records[index];
	        if (item != null) {
	        	var label = item.username + "(" + item.fullname+ " - " + item.maPhong  +" - " + item.position  + ")";
	            return label;
	        }
	        return "";
	    }
	               
	});           
	var nguoiNhanViec = '<c:out value="${w_working_details.tenNguoiNhanViec}"/>';
	if(nguoiNhanViec=="")
		$('#cbnguoiNhanViec').val('');
	else
	{
	//	$("#cbnguoiNhanViec").text(nguoiNhanViec );
		var nguoiNhanViecVar = nguoiNhanViec.split(",");
		if (nguoiNhanViecVar != null) {
			for (var i=0; i<nguoiNhanViecVar.length; i++) {
				$("#cbnguoiNhanViec").jqxComboBox('selectItem', nguoiNhanViecVar[i] );  
			}
		}
	}
	//trigger selection changes.
	$("#cbnguoiNhanViec").on('change', function (event) {
		var items = $("#cbnguoiNhanViec").jqxComboBox('getSelectedItems');
		var selectedItems = "";
	    $.each(items, function (index) {
	    	selectedItems += this.label;
	        if (items.length - 1 != index) {
	            selectedItems += ", ";
	        }
	    });
	    $("#nguoiNhanViec").val(selectedItems);
	});
	//nguoi chu tri

	 $('#cbnguoiChuTri').jqxComboBox({source: dataAdapterUser, displayMember: "username", valueMember: "username", width: "97%", height: 25, theme: theme,autoOpen: true,autoComplete: true,dropDownHeight: 120,
		 renderer: function (index, label, value) {
	         var item = dataAdapterUser.records[index];
	         if (item != null) {
	        	 var label = item.username + "(" + item.fullname+ " - " + item.maPhong  +" - " + item.position  + ")";
	             return label;
	         }
	         return "";
	     }
	                
	  });
	           
	var nguoiChuTri = '<c:out value="${w_working_details.tenNguoiChuTri}"/>';
	if(nguoiChuTri=="")
		$('#cbnguoiChuTri').val('');
	else
	{
		$("#cbnguoiChuTri").val(nguoiChuTri );
	}
	//trigger selection changes.
	$("#cbnguoiChuTri").on('change', function (event) {
		 var args = event.args;
		    if (args) {
	    	 var item = args.item;
		   	 var value = item.value;
	   		 $("#nguoiChuTri").val(value);
		    }
	});

	var ccEmail = '<c:out value="${w_working_details.ccEmail}"/>';

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
	${ccEmailList}
	$("#ccEmail").jqxInput({ height: 20, width: '100%', theme: theme,
	    source: function (query, response) {
	        var item = query.split(/,\s*/).pop();
	        // update the search query.
	        $("#ccEmail").jqxInput({ query: item });
	        response(ccEmailList);
	    },
	    renderer: renderer
	});
	// alert(bscid);
		if(ccEmail!="")
			$('#ccEmail').val(ccEmail);
	 ${cellList}
	 	$("#site").jqxInput({ height: 20, width: 200, theme: theme,
	        source: cellList
	    });
		//alert(cellid);
        if(site!="")
			$('#site').val(site);
	});
</script>
<script src="<%=request.getContextPath() %>/js/uploadifive/jquery.uploadifive.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/uploadifive/uploadifive.css">
<link href="<%=request.getContextPath() %>/js/ajaxupload/fileuploader.css" rel="stylesheet" type="text/css">	
<script src="<%=request.getContextPath() %>/js/ajaxupload/fileuploader.js" type="text/javascript"></script>

<script type="text/javascript">
$(function() {
	$('#file_upload').uploadifive({
		
		'method'			: 'post',
		'auto'         		: true,
		'multi' 			: true,
		'formData'			: {'path' : 'documents'},
		'queueID'      		: 'queue',
		'uploadScript' 		: '${pageContext.request.contextPath}/w_working_managements/upload/docpa.htm',
		'fileObjName' 		: 'filedata',
		'onInit'		: function() {
			var fileId = $('#fileId').val();
			//alert(fileId);
			if (fileId != '') {
				var fileList = fileId.split(",");
				
					for (var i=0; i<fileList.length; i++) {
						var fileA =fileList[i];
						
						$.getJSON("${pageContext.request.contextPath}/w_working_managements/getFile.htm",{fileId: fileA}, function(j){
							if (j==null) {
								return;
							}
							$('#queue-upload').append(
								'<div class="uploadifive-queue-item" id="uploadifive-file_upload-file--' + j.id + '">' +
								'	<a href="javascript:removeAttachFile(' + j.id + ')" class="close">X</a>' +
								'	<div>'+
								'		<span class="filename"><a href="${pageContext.request.contextPath}/w_working_managements/download.htm?id='+ j.id +'">'+ j.fileName +'</a></span>'+
								'		<span class="fileinfo"></span>'+
								'	</div>' +
								'	<div class="progress" style="display: none;">' +
								'		<div class="progress-bar"></div>' +
								'	</div>' +
								'</div>'
							);
						});
					}
			}
		},
		'onSelect'         	: function() {
			$('#file_upload').uploadifive('upload');
		},
		'onUploadComplete' : function(fileA, data) {
			//alert(fileA);
			//alert(data);
			addFileId(data);
		}
	});
});

function removeAttachFile(v_sysFileId) {
	
	//$.getJSON("${pageContext.request.contextPath}/file/delete.htm",{id: v_sysFileId}, function(j){});
	$.getJSON("${pageContext.request.contextPath}/w_working_managements/deleteFile.htm",{id: v_sysFileId, idWorkMana: ""}, function(j){});
		
	var item = $('#uploadifive-file_upload-file--'+v_sysFileId);
	item.hide("fast");
	item.html('');
	var file_upload = '';
	var fileId = $('#fileId').val();
	if (fileId != '') {
		var fileList = fileId.split(",");

		if (fileList != null) {
			for (var i=0; i<fileList.length; i++) {
				if (fileList[i] != v_sysFileId) {
					file_upload += fileList[i] + ',';
				}
			}
		}
	}
	
	$("#fileId").val(file_upload);
}

 function getUrlFileName(v_sysFileId, v_fileName) {
	return '<a href="${pageContext.request.contextPath}/w_working_managements/download.htm?id=' + v_sysFileId + '">' + v_fileName + '</a>';
}

function addFileId(data) {
	var fileId = $("#fileId").val();
	$("#fileId").val(fileId + ',' + data );
	
}
</script>