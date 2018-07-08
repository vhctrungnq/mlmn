<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js" charset="utf-8"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/js/editortext/sample.css" />

<c:choose>
  <c:when test="${XuLyFeedback == 'N'}">
	<title><fmt:message key="title.qLThongTinPhanAnh.xuLyTungPhanAnh"/></title>
	<content tag="heading"><fmt:message key="title.qLThongTinPhanAnh.xuLyTungPhanAnh"/></content>
 </c:when>
 <c:when test="${XuLyFeedback == 'Y'}">
  	<title><fmt:message key="title.qLThongTinPhanAnh.xuLyFeedbackTheoLo"/></title>
	<content tag="heading"><fmt:message key="title.qLThongTinPhanAnh.xuLyFeedbackTheoLo"/></content>
	
	<div style="width:100%;overflow:auto;">
		<display:table name="${fbProcessList}" class="simple2" id="item" requestURI="" sort="external" defaultsort="1">
		 			<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
		 			<display:column property="fbName" style="max-width:300px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.loaiPhanAnh"/>
		 			<display:column property="fbContent" style="max-width:700px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.noiDung" />						
		 			<display:column class="centerColumnMana" property="subscribers" style="max-width:100px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.soTB" />
		 			<display:column class="centerColumnMana" property="nameSubscriberType" style="max-width:50px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.loaiThueBao" />
		 			<display:column class="centerColumnMana" property="networkType" style="max-width:40px;word-wrap: break-word;" titleKey="qLThongTinPhanAnh.loaiMang" />
		 			<display:column property="fbDate" format="{0,date,dd/MM/yyyy HH:mm:ss}" titleKey="qLThongTinPhanAnh.thoiGianPA1" />
		 	</display:table>
	</div>
 </c:when>
 <c:otherwise></c:otherwise>
</c:choose>
<div class="body-content"></div>
<form id="checkform" name="checkform" method="post" action="checkedList.htm">
	<div>
		<input type="hidden" id="checkedList" name="checkedList" value="${checkedList}"/>
	</div>
	<div>
		<table class="simple2">
			<c:choose>
			  <c:when test="${XuLyFeedback == 'N'}">
			  	<div>
			  		<input type="hidden" id="status" name="status" value="${fbProcess.status}" />
			  		<input type="hidden" id="deadline" name="deadline" value="${fbProcess.hanXuLy}" />
			  		<input type="hidden" id="centralFbId" name="centralFbId" value="${fbProcess.centralFbId}" />
			  		<input type="hidden" id="content" name="content" value="" />
			  		<input type="hidden" id="deptFbId" name="deptFbId" value="${fbProcess.deptFbId}" />
				</div>
		 		<tr>
		           <td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.loaiPhanAnh"/></b></td>
		           <td colspan="3">
		           		<%-- ${fbProcess.fbName} --%>
		           		<div class="psr ovh select wid70">
							<select name="fbType" class="select" id="fbType">
				 				<c:forEach var="items" items="${loaiPAList}">
				              	<c:choose>
				                <c:when test="${items.code == fbProcess.fbType}">
				                    <option value="${items.code}" selected="selected">${items.name}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.code}">${items.name}</option>
				                </c:otherwise>
				              	</c:choose>
						    	</c:forEach>
					          </select>
					    </div>
		           </td>
		           <td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.nguoiPhanAnh"/></b></td>
			       	<td>
			        	${fbProcess.fbUser}
			        </td>
		   		</tr>
		   		<tr>
					
			        <td class="wid12 mwid110 bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.thoiGianPA1"/></b></td>
			        <td class="wid20">
			        	${fbProcess.ngayPhanAnh}
			        </td>
			        <td class="wid12 mwid140 bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.hanXuLy"/></b></td>
			       	<td class="wid20">
			        	${fbProcess.hanXuLy}
			        </td>
			        <td class="wid12 mwid110 bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.thoiGianConLai"/></b></td>
			        <td  class="wid20">
			        	<div id="timeLeftFirst" class="timeLeft"></div>
			        </td>
			        
		   		</tr>
		   		<tr>
					<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.soThueBao"/></b></td>
			       	<td>
			        	${fbProcess.subscribers}
			        </td>
			        <td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.loaiThueBao"/></b></td>
			        <td>
			        	${fbProcess.nameSubscriberType}
			        </td>
			        <td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.loaiMang"/></b></td>
			        <td>
				        <div class="psr ovh select wid70">
				        	<select name="networkType" class="select" id="networkType">
				        		<option value=""></option>
		          				<c:forEach var="items" items="${loaiMangList}">
					              	<c:choose>
					                <c:when test="${items.value == fbProcess.networkType}">
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
		   		</tr>
		   		<tr>
					<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.quan"/></b></td>
			       	<td>
			       		<div class="psr ovh select wid70">
							<select id="district" name="district" class="select">
								<option value=""></option>
						    	<c:forEach var="items" items="${quanHuyenList}">
					              	<c:choose>
					                <c:when test="${items.code == fbProcess.province && items.district == fbProcess.district}">
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
			        <td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.phuong"/></b></td>
			        <td>
				        <div class="psr ovh select wid70">
				        	<select id="wards" name="wards" class="select">
				        		<option value=""></option>
		          				<c:forEach var="items" items="${phuongXaList}">
					              	<c:choose>
					                <c:when test="${items.village == fbProcess.wards}">
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
			        <td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.bsc_rnc"/></b></td>
			        <td>
			        	<div class="psr ovh select wid30">
				        	<select name="bscRnc" class="select" id="bscRnc">
				        		<option value=""></option>
		          				<c:forEach var="items" items="${bscList}">
					              	<c:choose>
					                <c:when test="${items.bscid == fbProcess.bscRnc}">
					                    <option value="${items.bscid}" selected="selected">${items.bscid}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${items.bscid}">${items.bscid}</option>
					                </c:otherwise>
					              	</c:choose>
						    	</c:forEach>
		   					</select>
		   				</div>&nbsp;
		   				<spring:message code="qLThongTinPhanAnh.site"/>&nbsp;
		   				<input type="text" id="site" name="site" value="${fbProcess.site}" style="width: 40%" maxlength="30"/>
			        </td>
		   		</tr>
		   		<tr>
		   			<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.phongDai"/></b></td>
		   			<td>
		   				<div class="psr ovh select wid70">
				        	<select id="deptProcess" name="deptProcess" class="select">
				        		<option value=""></option>
		          				<c:forEach var="items" items="${deptProcessList}">
					              	<c:choose>
					                <c:when test="${items.deptCode == fbProcess.deptProcess}">
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
		   			<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.toXuLy"/></b></td>
			        <td>
				        <div class="psr ovh select wid70">
				        	<select id="team" name="team" class="select">
				        		<option value=""></option>
		          				<c:forEach var="items" items="${teamList}">
					              	<c:choose>
					                <c:when test="${items.team == fbProcess.team}">
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
					<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.fbIbc"/></b></td>
		   			<td>
		   				<div class="psr ovh select wid70">
							<select id="fbIbc" name="fbIbc" class="select">
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
		   		</tr>
		   		<tr>
		   			<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.nguoiXuLy"/></b></td>
			       	<td>
				       	<div class="psr ovh select wid70">
				        	<select name="processUser" class="select" id="processUser">
		          				<c:forEach var="items" items="${nguoiXuLyList}">
					              	<c:choose>
					                <c:when test="${items.username == nguoiDangNhap}">
					                    <option value="${items.username}" selected="selected">${items.username}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${items.username}">${items.username}</option>
					                </c:otherwise>
					              	</c:choose>
						    	</c:forEach>
		   					</select>
		   				</div>
			        </td>
			        <td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.thoiGianXL"/></b></td>
			        <td>
			        	<input type="text" id="timeProcess" name="timeProcess" value="${fbProcess.thoiGianXuLy}" style="width: 50%" maxlength="20"/>
   						<img alt="calendar" title="Click to choose the process date" id="chooseTimeProcess" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
			        	<span class="error">${errortimeProcess}</span>
			        </td>
		   			<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.vip"/></b></td>
		   			<td>
		   			
			   			<c:choose>
	  						<c:when test="${XuLyFeedback == 'N'}">
			           			${fbProcess.vipName}		
			           		</c:when>
			  				<c:when test="${XuLyFeedback == 'Y'}">
			  					${khachHangVip}
			  				</c:when>
		  				<c:otherwise></c:otherwise>
		  				</c:choose>
		   			</td>
		   		</tr>
		  </c:when>
		  <c:when test="${XuLyFeedback == 'Y'}">
			  	<tr>
			  		<td class="wid12 mwid110 bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.bsc_rnc"/></b></td>
			        <td class="wid20">
			        	<div class="psr ovh select wid30">
				        	<select name="bscRnc" class="select" id="bscRnc">
				        		<option value=""></option>
		          				<c:forEach var="items" items="${bscList}">
					              	<c:choose>
					                <c:when test="${items.bscid == bscRncCBB}">
					                    <option value="${items.bscid}" selected="selected">${items.bscid}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${items.bscid}">${items.bscid}</option>
					                </c:otherwise>
					              	</c:choose>
						    	</c:forEach>
		   					</select>
		   				</div>&nbsp;
		   				<spring:message code="qLThongTinPhanAnh.site"/>&nbsp;
		   				<input type="text" id="site" name="site" value="${site}" style="width: 40%" maxlength="30"/>
			        </td>
			        
			        <td class="wid12 mwid110 bgf1" style="vertical-align:middle;" ><b><spring:message code="qLThongTinPhanAnh.phongDai"/></b></td>
		   			<td class="wid20">
		   				<div class="psr ovh select wid70">
				        	<select id="deptProcess" name="deptProcess" class="select">
				        		<option value=""></option>
		          				<c:forEach var="items" items="${deptProcessList}">
					              	<c:choose>
					                <c:when test="${items.deptCode == deptCodeCBB}">
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
		   			<td class="wid12 mwid140 bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.toXuLy"/></b></td>
			        <td class="wid20">
				        <div class="psr ovh select wid70">
				        	<select id="team" name="team" class="select">
				        		<option value=""></option>
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
			  	 	<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.nguoiXuLy"/></b></td>
			       	<td>
				       	<div class="psr ovh select wid70">
				        	<select name="processUser" class="select" id="processUser">
		          				<c:forEach var="items" items="${nguoiXuLyList}">
					              	<c:choose>
					                <c:when test="${items.username == nguoiDangNhap}">
					                    <option value="${items.username}" selected="selected">${items.username}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${items.username}">${items.username}</option>
					                </c:otherwise>
					              	</c:choose>
						    	</c:forEach>
		   					</select>
		   				</div>
			        </td>
			  	 	<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.thoiGianXL"/></b></td>
			        <td colspan="3">
			        	${thoiGianXuLy}
			        </td>
			  	 </tr>
			  	 <tr>
		   			<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.fbIbc"/></b></td>
		   			<td colspan="3">
		   				<div class="psr ovh select wid50">
							<select id="fbIbc" name="fbIbc" class="select">
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
		   			<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.vip"/></b></td>
		   			<td>
			   			<c:choose>
	  						<c:when test="${XuLyFeedback == 'N'}">
			           			${fbProcess.vipName}		
			           		</c:when>
			  				<c:when test="${XuLyFeedback == 'Y'}">
			  					${khachHangVip}
			  				</c:when>
		  				<c:otherwise></c:otherwise>
		  				</c:choose>
		   			</td>
		   		</tr>
			  </c:when>
			  <c:otherwise></c:otherwise>
			</c:choose>
				<tr>
		           <td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.noiDungPhanAnh"/></b></td>
		           <td colspan="5">
		           		<c:choose>
  						<c:when test="${XuLyFeedback == 'N'}">
		           			${fbProcess.fbContent}		
		           		</c:when>
		  				<c:when test="${XuLyFeedback == 'Y'}">
		  					${noiDungPhanAnh}
		  				</c:when>
		  				<c:otherwise></c:otherwise>
		  				</c:choose>
		           </td>
		   		</tr>
		   		<tr>
		           <td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.noiDungXuLy"/></b></td>
		           <td colspan="5">
		           		<div>
			           		<c:choose>
		  						<c:when test="${XuLyFeedback == 'N'}">
				           			${content}<input type="hidden" id="contentOld" name="contentOld" value="${content}" />
				           		</c:when>
				  				<c:when test="${XuLyFeedback == 'Y'}">
				  					${noiDungXuLy}<input type="hidden" id="contentOld" name="contentOld" value="${noiDungXuLy}" />
				  				</c:when>
				  				<c:otherwise></c:otherwise>
			  				</c:choose>
		  				</div>
		  				<div style="padding-top: 20px;">
			           		<textarea rows="10" class="wid98" id="responseContent" name="responseContent" maxlength="3000">
			           		</textarea>
		           		</div>
						<script type="text/javascript">
								CKEDITOR.replace( 'responseContent',
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
									height: '100px',
								});
								
						</script>
		           </td>
		   		</tr>
		   		<tr>
		   			<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="title.xuLyPhanAnh.nguyenNhan"/></b></td>
		   			<td colspan="5">
		   				<c:choose>
	  						<c:when test="${XuLyFeedback == 'N'}">
			           			<textarea class="wid100" id="causedby" name="causedby" maxlength="800">${fbProcess.causedby}</textarea>	
			           		</c:when>
			  				<c:when test="${XuLyFeedback == 'Y'}">
			  					<textarea class="wid100" id="causedby" name="causedby" maxlength="800">${nguyenNhan}</textarea>
			  				</c:when>
			  				<c:otherwise></c:otherwise>
		  				</c:choose>
		  			</td>
		   		</tr>
		   		<tr>
		   			<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.processStatus"/></b></td>
		   			<td colspan="5">
		   				<c:choose>
	  						<c:when test="${XuLyFeedback == 'N'}">
			           			<textarea class="wid100" id="processStatus" name="processStatus" maxlength="800">${fbProcess.processStatus}</textarea>	
			           		</c:when>
			  				<c:when test="${XuLyFeedback == 'Y'}">
			  					<textarea class="wid100" id="processStatus" name="processStatus" maxlength="800">${processStatus}</textarea>
			  				</c:when>
			  				<c:otherwise></c:otherwise>
		  				</c:choose>
		  			</td>
		   		</tr>
		   		<tr>
		   			<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.processHandleMethod"/></b></td>
		   			<td colspan="5">
		   				<c:choose>
	  						<c:when test="${XuLyFeedback == 'N'}">
			           			<textarea class="wid100" id="processHandleMethod" name="processHandleMethod" maxlength="800">${fbProcess.processHandleMethod}</textarea>	
			           		</c:when>
			  				<c:when test="${XuLyFeedback == 'Y'}">
			  					<textarea class="wid100" id="processHandleMethod" name="processHandleMethod" maxlength="800">${processHandleMethod}</textarea>
			  				</c:when>
			  				<c:otherwise></c:otherwise>
		  				</c:choose>
		  			</td>
		   		</tr>
		   		<tr>
		   			<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.processResultLocal"/></b></td>
		   			<td colspan="5">
		   				<c:choose>
	  						<c:when test="${XuLyFeedback == 'N'}">
			           			<textarea class="wid100" id="processResultLocal" name="processResultLocal" maxlength="800">${fbProcess.processResultLocal}</textarea>	
			           		</c:when>
			  				<c:when test="${XuLyFeedback == 'Y'}">
			  					<textarea class="wid100" id="processResultLocal" name="processResultLocal" maxlength="800">${processResultLocal}</textarea>
			  				</c:when>
			  				<c:otherwise></c:otherwise>
		  				</c:choose>
		  			</td>
		   		</tr>
		   		<tr>
		   			<td class="bgf1" style="vertical-align:middle;"><b><spring:message code="qLThongTinPhanAnh.processMotional"/></b></td>
		   			<td colspan="5">
		   				<c:choose>
	  						<c:when test="${XuLyFeedback == 'N'}">
			           			<textarea class="wid100" id="processMotional" name="processMotional" maxlength="800">${fbProcess.processMotional}</textarea>	
			           		</c:when>
			  				<c:when test="${XuLyFeedback == 'Y'}">
			  					<textarea class="wid100" id="processMotional" name="processMotional" maxlength="800">${processMotional}</textarea>
			  				</c:when>
			  				<c:otherwise></c:otherwise>
		  				</c:choose>
		  			</td>
		   		</tr>
		</table>
	</div>
	
	<div style="text-align: right; padding: 10px 0px 0px 0px;">
		<input type="submit" class="button" id="btnLuuLai" name="btnLuuLai" onclick="setAction('save')" value="<fmt:message key="global.button.luuLai" />">
		<!--<input type="submit" class="button" id="btnCapNhat" name="btnCapNhat" onclick="setAction('updateCentralFb')" value="<fmt:message key="button.capNhatVaoCentralFeedback" />"> -->
		<%-- <input type="button" class="button" id="btnBoQua" name="btnBoQua" onclick="history.back()" value="<fmt:message key="global.form.boQua" />"> --%>
		<input class="button" type="button" id="btnBoQua" name="btnBoQua" value="<fmt:message key="global.button.huyBo"/>" onClick='window.location="list.htm"'>
		<input type="hidden" id="action" name="action">
	</div>
</form>

<!-- <script type="text/javascript" src="/VMSC2-Alarm-Feedback/js/jquery/jquery-1.7.2.js"></script> -->
<link href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" rel="stylesheet">
<%-- <script src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min-1.8.9.js" type="text/javascript"></script> --%>

<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min-1.8.9.js"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.jcountdown1.3.js"></script>
<script type="text/javascript">

function get_alias() {
	var title = CKEDITOR.instances.responseContent.getData();
	var content = document.getElementById("content");
	
	if (title != '') {
		var str = change_alias(title);
		content.value = str;
	}
}

function change_alias(str)
{
	 
	str = str.replace(/&lsquo;/g, "‘");
	str = str.replace(/&rsquo;/g, "’"); 
	str = str.replace(/&sbquo;/g, "‚");
	str = str.replace(/&ldquo;/g, "“"); 
	str = str.replace(/&rdquo;/g, "”"); 
	str = str.replace(/&bdquo;/g, "„");
	str = str.replace(/&dagger;/g, "†");
	str = str.replace(/&Dagger;/g, "‡");
	str = str.replace(/&permil;/g, "‰");
	str = str.replace(/&lsaquo;/g, "‹");
	str = str.replace(/&rsaquo;/g, "›");
	str = str.replace(/&spades;/g, "♠");
	str = str.replace(/&clubs;/g, "♣");
	str = str.replace(/&hearts;/g, "♥");
	str = str.replace(/&diams;/g, "♦");
	str = str.replace(/&oline;/g, "‾");
	str = str.replace(/&larr;/g, "←");
	str = str.replace(/&uarr;/g, "↑");
	str = str.replace(/&rarr;/g, "→");
	str = str.replace(/&darr;/g, "↓");
	str = str.replace(/&copy;/g, "©");
	str = str.replace(/&trade;/g, "™");
	str = str.replace(/&amp;/g, "&");
	str = str.replace(/&middot;/g, "·");
	str = str.replace(/&lt;/g, "<");
	str = str.replace(/&gt;/g, ">");
	str = str.replace(/&deg;/g, "°");
	str = str.replace(/&plusmn;/g, "±");
	str = str.replace(/&sup2;/g, "²");
	str = str.replace(/&sup3;/g, "³");
	str = str.replace(/&acute;/g, "´");
	str = str.replace(/&micro;/g, "µ");
	str = str.replace(/&para;/g, "¶");
	str = str.replace(/&cedil;/g, "¸");
	str = str.replace(/&sup1;/g, "¹");
	str = str.replace(/&ordm;/g, "º");
	str = str.replace(/&frac14;/g, "¼");
	str = str.replace(/&frac12;/g, "½");
	str = str.replace(/&frac34;/g, "¾");
	str = str.replace(/&iquest;/g, "¿");
	str = str.replace(/&Agrave;/g, "À");
	str = str.replace(/&Aacute;/g, "Á");
	str = str.replace(/&Acirc;/g, "Â");
	str = str.replace(/&Atilde;/g, "Ã");
	str = str.replace(/&Auml;/g, "Ä");
	str = str.replace(/&Aring;/g, "Å");
	str = str.replace(/&AElig;/g, "Æ");
	str = str.replace(/&Ccedil;/g, "Ç");
	str = str.replace(/&Egrave;/g, "È");
	str = str.replace(/&Eacute;/g, "É");
	str = str.replace(/&Ecirc;/g, "Ê");
	str = str.replace(/&Euml;/g, "Ë");
	str = str.replace(/&Igrave;/g, "Ì");
	str = str.replace(/&Iacute;/g, "Í");
	str = str.replace(/&Icirc;/g, "Î");
	str = str.replace(/&Iuml;/g, "Ï");
	str = str.replace(/&ETH;/g, "Ð");
	str = str.replace(/&Ntilde;/g, "Ñ");
	str = str.replace(/&Ograve;/g, "Ò");
	str = str.replace(/&Oacute;/g, "Ó");
	str = str.replace(/&Ocirc;/g, "Ô");
	str = str.replace(/&Otilde;/g, "Õ");
	str = str.replace(/&Ouml;/g, "Ö");
	str = str.replace(/&times;/g, "×");
	str = str.replace(/&Oslash;/g, "Ø"); 
	str = str.replace(/&Ugrave;/g, "Ù"); 
	str = str.replace(/&Uacute;/g, "Ú"); 
	str = str.replace(/&Ucirc;/g, "Û"); 
	str = str.replace(/&Uuml;/g, "Ü"); 
	str = str.replace(/&Yacute;/g, "Ý"); 
	str = str.replace(/&THORN;/g, "Þ"); 
	str = str.replace(/&szlig;/g, "ß"); 
	str = str.replace(/&agrave;/g, "à"); 
	str = str.replace(/&aacute;/g, "á"); 
	str = str.replace(/&acirc;/g, "â"); 
	str = str.replace(/&atilde;/g, "ã"); 
	str = str.replace(/&auml;/g, "ä"); 
	str = str.replace(/&aring;/g, "å"); 
	str = str.replace(/&aelig;/g, "æ"); 
	str = str.replace(/&ccedil;/g, "ç"); 
	str = str.replace(/&egrave;/g, "è"); 
	str = str.replace(/&eacute;/g, "é"); 
	str = str.replace(/&ecirc;/g, "ê"); 
	str = str.replace(/&euml;/g, "ë");
	str = str.replace(/&igrave;/g, "ì"); 
	str = str.replace(/&iacute;/g, "í");
	str = str.replace(/&icirc;/g, "î");
	str = str.replace(/&iuml;/g, "ï");
	str = str.replace(/&eth;/g, "ð");
	str = str.replace(/&ntilde;/g, "ñ");
	str = str.replace(/&ograve;/g, "ò");
	str = str.replace(/&oacute;/g, "ó");
	str = str.replace(/&ocirc;/g, "ô");
	str = str.replace(/&otilde;/g, "õ");
	str = str.replace(/&ouml;/g, "ö");
	str = str.replace(/&divide;/g, "÷");
	str = str.replace(/&oslash;/g, "ø");
	str = str.replace(/&ugrave;/g, "ù");
	str = str.replace(/&uacute;/g, "ú");   
	str = str.replace(/&ucirc;/g,"û");  
	str = str.replace(/&uuml;/g,"ü");  
	str = str.replace(/&yacute;/g,"ý");  
	str = str.replace(/&thorn;/g,"þ");  
	str = str.replace(/&yuml;/g,"ÿ");
	return str;
}
</script>
<script type="text/javascript">
$(document).ready(function(){
	
	var tgConLai = "<c:out value='${fbProcess.tgConLai}' escapeXml='false' />";
	if(tgConLai == 'HOAN_THANH'){
		var tgXuLy = "<c:out value='${fbProcess.tgXuLy}' escapeXml='false' />";
		$("#timeLeftFirst").html(tgXuLy);
		
		}
	else{
		$("#timeLeftFirst").countdown({
			
			date: '<fmt:formatDate value="${fbProcess.deadline}" pattern="MM dd, yyyy HH:mm"/>', //Counting TO a date
			htmlTemplate: "%{d} <span class=\"cd-time\">ngày</span> %{h} <span class=\"cd-time\">:</span> %{m} <span class=\"cd-time\">:</span> %{s} <span class=\"cd-time\"></span>", 
			onChange: function( event, timer ){
				
			},
			onComplete: function( event ){
				var status = "<c:out value='${fbProcess.tgConLai}' escapeXml='false' />";
				if (status == 'HOAN_THANH') {
					$(this).html("Hoàn thành");
				} else {
					$(this).html("Quá hạn");
				}
				
			},
			leadingZero: true,
			direction: "down"
		});
	}
});
</script>

<script type="text/javascript">
function loadBscProvince(){
	$.ajax({
		  url: "${pageContext.request.contextPath}/feedback/quan-ly-thong-tin-lpa/ajax/loadBscProvince.htm",
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
		  data:{loaiMang: $('#networkType').val()}}).done(function( j ) {
			  var options = '';
				
			  for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].bscid + '">' + j[i].bscid+ '</option>';
				}
			$("#bscRnc").html(options);
			$('#bscRnc option:first').attr('selected', 'selected');
		    
		  });
		  
}

$('#networkType').change(function(){

	loadBscProvince();
});

function setAction(value) {
	var action = document.getElementById("action");
	action.value = value;
	get_alias();
	return true;
}

function loadDeptProcess(){
	$.ajax({
		  url: "${pageContext.request.contextPath}/feedback/quan-ly-thong-tin-lpa/ajax/loadDeptProcess.htm",
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
		  data:{provinceDistrict: encodeURI($('#district').val())}}).done(function( j ) {
			  var options = '';
			  options += '<option value=""></option>';
			  for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].deptCode + '">' + j[i].deptCode + '</option>';
				}
			$("#deptProcess").html(options);
			$('#deptProcess option:first').attr('selected', 'selected');
			if(j.length > 0){
				loadTeam(j[0].deptCode);
			}
		  });
}

function loadTeam(deptProcess){
	$.ajax({
		  url: "${pageContext.request.contextPath}/feedback/so-luong-pa-theo-to-vien-thong/loadTeam.htm",
		  beforeSend: function( ) {
		  },
		  data:{deptProcess: deptProcess}}).done(function(j) {
			  var options = '';
			  options += '<option value=""></option>';
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
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].village + '">' + j[i].villageName + '</option>';
			}
		$("#wards").html(options);
		$('#wards option:first').attr('selected', 'selected');
	    
	  });
	  
	loadDeptProcess();
});

$('#deptProcess').change(function(){
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
	loadTeam($('#deptProcess').val());
});
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">

var XuLyFeedback = '<c:out value="${XuLyFeedback}"/>';
if(XuLyFeedback == 'N'){
	Calendar.setup({
	    inputField		:	"timeProcess",	// id of the input field
	    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
	    button			:   "chooseTimeProcess",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	});
	
}


function focusIt()
{
	var errortimeProcess = '<c:out value="${errortimeProcess}"/>';
	if(errortimeProcess !="")
	{
			var mytext = document.getElementById("timeProcess");
			  mytext.focus();
	}
	else if(document.checkform.causedby.value==""){
		  var mytext = document.getElementById("causedby");
		  mytext.focus();
	}
	else if(document.checkform.responseContent.value=="")
	{
			var mytext = document.getElementById("responseContent");
			  mytext.focus();
	}
}

onload = focusIt;
</script>
