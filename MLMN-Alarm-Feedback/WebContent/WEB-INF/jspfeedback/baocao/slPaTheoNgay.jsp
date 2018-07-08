<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="baoCaoFeedback.baoCaoTheoNgay"/></title>
<content tag="heading"><fmt:message key="baoCaoFeedback.baoCaoTheoNgay"/></content>

<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'tong-hop'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/feedback/so-luong-pa-theo-ngay/tong-hop.htm"><span><fmt:message key="title.tabControls.baoCaoTongHop"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/feedback/so-luong-pa-theo-ngay/chi-tiet.htm"><span><fmt:message key="title.tabControls.baoCaoChiTiet"/></span></a></li>
	</c:when>
 	<c:when test="${function == 'chi-tiet'}">
		<li class=""><a href="${pageContext.request.contextPath}/feedback/so-luong-pa-theo-ngay/tong-hop.htm"><span><fmt:message key="title.tabControls.baoCaoTongHop"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/feedback/so-luong-pa-theo-ngay/chi-tiet.htm"><span><fmt:message key="title.tabControls.baoCaoChiTiet"/></span></a></li>
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<div class="ui-tabs-panel">
<form:form id="filterController" method="post" action="${function}.htm">
	<table border="0" width="80%" cellspacing="0" cellpadding="0" class="form">
		<tr>
			<td class="wid40">
				<fmt:message key="baoCaoSoLuongPA.tuNgay"/>&nbsp;
					<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 120px" maxlength="20"/>
   					<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
					<fmt:message key="baoCaoSoLuongPA.denNgay"/>&nbsp;
					<input type="text" id="endDate" name="endDate" value="${endDate}" style="width: 120px" maxlength="20"/>
   					<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
			</td>
			<td>
				<c:choose>
					<c:when test="${function == 'chi-tiet'}">
				
					<fmt:message key="baoCaoFeedback.baoCaoTheoNgay.loaiBaoCao"/>&nbsp;
					<select id="rpType" name="rpType">
						<option value="">--Tất cả--</option>
		 				<c:forEach var="items" items="${loaiBCList}">
		              	<c:choose>
		                <c:when test="${items.value == loaiBCCBB}">
		                    <option value="${items.value}" selected="selected">${items.name}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.name}</option>
		                </c:otherwise>
		              	</c:choose>
				    	</c:forEach>
			          </select>	    
				</c:when>
				<c:otherwise></c:otherwise>
				</c:choose>&nbsp;
			</td>
			
				<c:if test="${Center=='TT6' }">
				<td  class="wid10">
				 <spring:message code="qLThongTinPhanAnh.fbSendTelecom"/>
				 </td>
				 <td class="wid10"> 
				<div class="wid60 psr ovh select">
					<select name="fbSendTelecom" class="select" id="fbSendTelecom">
						<option value="">--Tất cả--</option>
          				<c:forEach var="status" items="${statusList}">
			              	<c:choose>
			                <c:when test="${status.name == fbSendTelecom}">
			                    <option value="${status.name}" selected="selected">${status.value}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${status.name}">${status.value}</option>
			                </c:otherwise>
			              	</c:choose>
				    	</c:forEach>
        			</select>
        			<input type="hidden" id="fbDistrict" name="fbDistrict" value="${fbDistrict}"/>
		         </div>
		         </td>
				</c:if>
			
			<td>
				<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />
			</td>
		</tr>
		</table>
 		<br>
		<c:choose>
			<c:when test="${function == 'tong-hop'}">
				
						<div style="width:100%;overflow:auto; " class="tableStandar">
							<display:table name="${soLuongFBList}"  id="item" requestURI="" pagesize="50" sort="external" export="true">
								<display:column class="centerColumnMana" style="width:40px;word-wrap: break-word;" titleKey="global.list.No"> <c:out value="${item_rowNum}"/> </display:column>
								
								<display:column property="fbDateStr" class="link" href="chi-tiet.htm" paramId="date" paramProperty="fbDateStr" media="html" titleKey="baoCaoFeedback.baoCaoTheoNgay.ngay" />				
								<display:column property="fbDateStr" titleKey="baoCaoFeedback.baoCaoTheoNgay.ngay" sortable="true" headerClass="hide" class="hide"/>
								
								<display:column class="rightColumnMana" property="daXuLy" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLy"/>
								<display:column class="rightColumnMana" property="dangXuLy" titleKey="baoCaoFeedback.baoCaoTheoNgay.dangXuLy"/>
								<display:column class="rightColumnMana" property="chuaXuLy" titleKey="baoCaoFeedback.baoCaoTheoNgay.chuaXuLy"/>
								<display:column class="rightColumnMana" property="total" titleKey="baoCaoFeedback.baoCaoTheoNgay.tong"/>
				    			
								<display:setProperty name="export.csv.include_header" value="true" />
								<display:setProperty name="export.excel.include_header" value="true" />
								<display:setProperty name="export.xml.include_header" value="true" />
								<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
								<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
								<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
									
							</display:table>
						</div>
					
			</c:when>
			<c:when test="${function == 'chi-tiet'}">
				<c:choose>
				<c:when test="${empty loaiBCCBB}">
					
						<div align="center"><h2><fmt:message key="baoCaoFeedback.baoCaoTheoNgay.baoCaoTheoLPA"/></h2></div>
					
							<div style="width:100%;overflow:auto; " class="tableStandar">
								<display:table name="${reportFbTypeList}" id="item1" requestURI="" pagesize="50" sort="external" export="true">
									<display:column class="centerColumnMana" style="width:40px;word-wrap: break-word;" titleKey="global.list.No"> <c:out value="${item1_rowNum}"/> </display:column>
									<display:column style="width:500px;word-wrap: break-word;" property="name" class="NAME" titleKey="qLThongTinPhanAnh.loaiPhanAnh" />				
									<display:column class="rightColumnMana" property="haiG" titleKey="baoCaoFeedback.baoCaoTheoNgay.2g"/>
									<display:column class="rightColumnMana" property="baG" titleKey="baoCaoFeedback.baoCaoTheoNgay.3g"/>
									<display:column class="rightColumnMana" property="other" titleKey="baoCaoFeedback.baoCaoTheoNgay.other"/>
									<display:column class="rightColumnMana" property="total" titleKey="baoCaoFeedback.baoCaoTheoNgay.tongSoFB"/>
					    			<display:column class="rightColumnMana" property="trongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyTrongHan"/>
									<display:column class="rightColumnMana" property="tlTrongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyTrongHan"/>
									<display:column class="rightColumnMana" property="quaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyQuaHan"/>
									<display:column class="rightColumnMana" property="tlQuaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyQuaHan"/>
									<display:column class="rightColumnMana" property="chuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.chuaXuly"/>
					    			<display:column class="rightColumnMana" property="tlChuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlChuaXuly"/>
									<display:setProperty name="export.csv.include_header" value="true" />
									<display:setProperty name="export.excel.include_header" value="true" />
									<display:setProperty name="export.xml.include_header" value="true" />
									<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
									<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
									<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
										
								</display:table>
							</div>
						
					
					
						<div align="center"><h2><fmt:message key="baoCaoFeedback.baoCaoTheoNgay.baoCaoTheoToVT"/></h2></div>
					
					
							<div style="width:100%;overflow:auto; " class="tableStandar">
								<display:table name="${reportDepartmentList}" id="item2" requestURI="" pagesize="50" sort="external" export="true">
									<display:column class="centerColumnMana" style="width:40px;word-wrap: break-word;" titleKey="global.list.No"> <c:out value="${item2_rowNum}"/> </display:column>
									<display:column style="width:300px;word-wrap: break-word;" property="name" titleKey="qLThongTinPhanAnh.phongDai" />	
									<display:column style="width:200px;word-wrap: break-word;" property="team" class="NAME" titleKey="baoCaoFeedback.baoCaoTheoNgay.khuVucToVT" />				
									<display:column class="rightColumnMana" property="haiG" titleKey="baoCaoFeedback.baoCaoTheoNgay.2g"/>
									<display:column class="rightColumnMana" property="baG" titleKey="baoCaoFeedback.baoCaoTheoNgay.3g"/>
									<display:column class="rightColumnMana" property="other" titleKey="baoCaoFeedback.baoCaoTheoNgay.other"/>
									<display:column class="rightColumnMana" property="total" titleKey="baoCaoFeedback.baoCaoTheoNgay.tongSoFB"/>
					    			<display:column class="rightColumnMana" property="trongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyTrongHan"/>
									<display:column class="rightColumnMana" property="tlTrongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyTrongHan"/>
									<display:column class="rightColumnMana" property="quaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyQuaHan"/>
									<display:column class="rightColumnMana" property="tlQuaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyQuaHan"/>
									<display:column class="rightColumnMana" property="chuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.chuaXuly"/>
					    			<display:column class="rightColumnMana" property="tlChuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlChuaXuly"/>
					    			
									<display:setProperty name="export.csv.include_header" value="true" />
									<display:setProperty name="export.excel.include_header" value="true" />
									<display:setProperty name="export.xml.include_header" value="true" />
									<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
									<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
									<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
										
								</display:table>
							</div>
						
					
					
						<div align="center"><h2><fmt:message key="baoCaoFeedback.baoCaoTheoNgay.baoCaoTheoQuanHuyen"/></h2></div>
					
							<div style="width:100%;overflow:auto; " class="tableStandar">
								<display:table name="${reportDistrictList}"  id="item3" requestURI="" pagesize="50" sort="external" export="true">
									<display:column class="centerColumnMana" style="width:40px;word-wrap: break-word;" titleKey="global.list.No"> <c:out value="${item3_rowNum}"/> </display:column>
									<display:column style="width:300px;word-wrap: break-word;" property="provinceName" titleKey="baoCaoFeedback.baoCaoTheoNgay.tinhThanh"/>
									<display:column style="width:200px;word-wrap: break-word;" property="name" class="NAME" titleKey="baoCaoFeedback.baoCaoTheoNgay.quanHuyen" />				
									<display:column class="rightColumnMana" property="haiG" titleKey="baoCaoFeedback.baoCaoTheoNgay.2g"/>
									<display:column class="rightColumnMana" property="baG" titleKey="baoCaoFeedback.baoCaoTheoNgay.3g"/>
									<display:column class="rightColumnMana" property="other" titleKey="baoCaoFeedback.baoCaoTheoNgay.other"/>
									<display:column class="rightColumnMana" property="total" titleKey="baoCaoFeedback.baoCaoTheoNgay.tongSoFB"/>
					    			<display:column class="rightColumnMana" property="trongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyTrongHan"/>
									<display:column class="rightColumnMana" property="tlTrongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyTrongHan"/>
									<display:column class="rightColumnMana" property="quaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyQuaHan"/>
									<display:column class="rightColumnMana" property="tlQuaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyQuaHan"/>
									<display:column class="rightColumnMana" property="chuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.chuaXuly"/>
					    			<display:column class="rightColumnMana" property="tlChuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlChuaXuly"/>
					    			
									<display:setProperty name="export.csv.include_header" value="true" />
									<display:setProperty name="export.excel.include_header" value="true" />
									<display:setProperty name="export.xml.include_header" value="true" />
									<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
									<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
									<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
										
								</display:table>
							</div>
						
					
					
						<div align="center"><h2><fmt:message key="baoCaoFeedback.baoCaoTheoNgay.baoCaoTheoNguyenNhan"/></h2></div>
					
							<div style="width:100%;overflow:auto; " class="tableStandar">
								<display:table name="${reportCausedbyList}"  id="item4" requestURI="" pagesize="50" sort="external" export="true">
									<display:column class="centerColumnMana" style="width:40px;word-wrap: break-word;" titleKey="global.list.No"> <c:out value="${item4_rowNum}"/> </display:column>
									<display:column style="width:500px;word-wrap: break-word;" property="name" class="NAME" titleKey="baoCaoFeedback.baoCaoTheoNgay.nguyenNhan" />				
									<display:column class="rightColumnMana" property="haiG" titleKey="baoCaoFeedback.baoCaoTheoNgay.2g"/>
									<display:column class="rightColumnMana" property="baG" titleKey="baoCaoFeedback.baoCaoTheoNgay.3g"/>
									<display:column class="rightColumnMana" property="other" titleKey="baoCaoFeedback.baoCaoTheoNgay.other"/>
									<display:column class="rightColumnMana" property="total" titleKey="baoCaoFeedback.baoCaoTheoNgay.tongSoFB"/>
					    			
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
					<c:choose>
					<c:when test="${loaiBCCBB == '1'}">
						
							<div align="center"><h2><fmt:message key="baoCaoFeedback.baoCaoTheoNgay.baoCaoTheoLPA"/></h2></div>
					
						
								<div style="width:100%;overflow:auto; " class="tableStandar">
									<display:table name="${reportFbTypeList}"  id="item1" requestURI="" pagesize="50" sort="external" export="true">
										<display:column class="centerColumnMana" style="width:40px;word-wrap: break-word;" titleKey="global.list.No"> <c:out value="${item1_rowNum}"/> </display:column>
										<display:column style="width:500px;word-wrap: break-word;" property="name" class="NAME" titleKey="baoCaoFeedback.baoCaoTheoNgay.loaiFeedback" />				
										<display:column class="rightColumnMana" property="haiG" titleKey="baoCaoFeedback.baoCaoTheoNgay.2g"/>
										<display:column class="rightColumnMana" property="baG" titleKey="baoCaoFeedback.baoCaoTheoNgay.3g"/>
										<display:column class="rightColumnMana" property="other" titleKey="baoCaoFeedback.baoCaoTheoNgay.other"/>
										<display:column class="rightColumnMana" property="total" titleKey="baoCaoFeedback.baoCaoTheoNgay.tongSoFB"/>
						    			<display:column class="rightColumnMana" property="trongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyTrongHan"/>
										<display:column class="rightColumnMana" property="tlTrongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyTrongHan"/>
										<display:column class="rightColumnMana" property="quaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyQuaHan"/>
										<display:column class="rightColumnMana" property="tlQuaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyQuaHan"/>
										<display:column class="rightColumnMana" property="chuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.chuaXuly"/>
						    			<display:column class="rightColumnMana" property="tlChuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlChuaXuly"/>
						    			
										<display:setProperty name="export.csv.include_header" value="true" />
										<display:setProperty name="export.excel.include_header" value="true" />
										<display:setProperty name="export.xml.include_header" value="true" />
										<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
										<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
										<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
											
									</display:table>
								</div>
							
					</c:when>
					<c:when test="${loaiBCCBB == '2'}">
						
							<div align="center"><h2><fmt:message key="baoCaoFeedback.baoCaoTheoNgay.baoCaoTheoToVT"/></h2></div>
						
						
								<div style="width:100%;overflow:auto; " class="tableStandar">
									<display:table name="${reportDepartmentList}" id="item2" requestURI="" pagesize="50" sort="external" export="true">
										<display:column class="centerColumnMana" style="width:40px;word-wrap: break-word;" titleKey="global.list.No"> <c:out value="${item2_rowNum}"/> </display:column>
										<display:column style="width:500px;word-wrap: break-word;" property="name" class="NAME" titleKey="baoCaoFeedback.baoCaoTheoNgay.khuVucToVT" />				
										<display:column class="rightColumnMana" property="haiG" titleKey="baoCaoFeedback.baoCaoTheoNgay.2g"/>
										<display:column class="rightColumnMana" property="baG" titleKey="baoCaoFeedback.baoCaoTheoNgay.3g"/>
										<display:column class="rightColumnMana" property="other" titleKey="baoCaoFeedback.baoCaoTheoNgay.other"/>
										<display:column class="rightColumnMana" property="total" titleKey="baoCaoFeedback.baoCaoTheoNgay.tongSoFB"/>
						    			<display:column class="rightColumnMana" property="trongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyTrongHan"/>
										<display:column class="rightColumnMana" property="tlTrongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyTrongHan"/>
										<display:column class="rightColumnMana" property="quaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyQuaHan"/>
										<display:column class="rightColumnMana" property="tlQuaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyQuaHan"/>
										<display:column class="rightColumnMana" property="chuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.chuaXuly"/>
						    			<display:column class="rightColumnMana" property="tlChuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlChuaXuly"/>
										<display:setProperty name="export.csv.include_header" value="true" />
										<display:setProperty name="export.excel.include_header" value="true" />
										<display:setProperty name="export.xml.include_header" value="true" />
										<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
										<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
										<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
											
									</display:table>
								</div>
							
					</c:when>
					<c:when test="${loaiBCCBB == '3'}">
						
							<div align="center"><h2><fmt:message key="baoCaoFeedback.baoCaoTheoNgay.baoCaoTheoQuanHuyen"/></h2></div>
						
								<div style="width:100%;overflow:auto; " class="tableStandar">
									<display:table name="${reportDistrictList}"  id="item3" requestURI="" pagesize="50" sort="external" export="true">
										<display:column class="centerColumnMana" style="width:40px;word-wrap: break-word;" titleKey="global.list.No"> <c:out value="${item3_rowNum}"/> </display:column>
										<display:column style="width:300px;word-wrap: break-word;" property="provinceName" titleKey="baoCaoFeedback.baoCaoTheoNgay.tinhThanh"/>
										<display:column style="width:200px;word-wrap: break-word;" property="name" class="NAME" titleKey="baoCaoFeedback.baoCaoTheoNgay.quanHuyen" />				
										<display:column class="rightColumnMana" property="haiG" titleKey="baoCaoFeedback.baoCaoTheoNgay.2g"/>
										<display:column class="rightColumnMana" property="baG" titleKey="baoCaoFeedback.baoCaoTheoNgay.3g"/>
										<display:column class="rightColumnMana" property="other" titleKey="baoCaoFeedback.baoCaoTheoNgay.other"/>
										<display:column class="rightColumnMana" property="total" titleKey="baoCaoFeedback.baoCaoTheoNgay.tongSoFB"/>
						    			<display:column class="rightColumnMana" property="trongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyTrongHan"/>
										<display:column class="rightColumnMana" property="tlTrongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyTrongHan"/>
										<display:column class="rightColumnMana" property="quaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyQuaHan"/>
										<display:column class="rightColumnMana" property="tlQuaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyQuaHan"/>
										<display:column class="rightColumnMana" property="chuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.chuaXuly"/>
						    			<display:column class="rightColumnMana" property="tlChuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlChuaXuly"/>
						    			
										<display:setProperty name="export.csv.include_header" value="true" />
										<display:setProperty name="export.excel.include_header" value="true" />
										<display:setProperty name="export.xml.include_header" value="true" />
										<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
										<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
										<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
											
									</display:table>
								</div>
							
					</c:when>
					<c:when test="${loaiBCCBB == '4'}">
						
							<div align="center"><h2><fmt:message key="baoCaoFeedback.baoCaoTheoNgay.baoCaoTheoNguyenNhan"/></h2></div>
						
						
								<div style="width:100%;overflow:auto; " class="tableStandar">
									<display:table name="${reportCausedbyList}" id="item4" requestURI="" pagesize="50" sort="external" export="true">
										<display:column class="centerColumnMana" style="width:40px;word-wrap: break-word;" titleKey="global.list.No"> <c:out value="${item4_rowNum}"/> </display:column>
										<display:column style="width:500px;word-wrap: break-word;" property="name" class="NAME" titleKey="baoCaoFeedback.baoCaoTheoNgay.nguyenNhan" />				
										<display:column class="rightColumnMana" property="haiG" titleKey="baoCaoFeedback.baoCaoTheoNgay.2g"/>
										<display:column class="rightColumnMana" property="baG" titleKey="baoCaoFeedback.baoCaoTheoNgay.3g"/>
										<display:column class="rightColumnMana" property="other" titleKey="baoCaoFeedback.baoCaoTheoNgay.other"/>
										<display:column class="rightColumnMana" property="total" titleKey="baoCaoFeedback.baoCaoTheoNgay.tongSoFB"/>
						    			
										<display:setProperty name="export.csv.include_header" value="true" />
										<display:setProperty name="export.excel.include_header" value="true" />
										<display:setProperty name="export.xml.include_header" value="true" />
										<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
										<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
										<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
											
									</display:table>
								</div>
							
					</c:when>
					<c:otherwise></c:otherwise>
					</c:choose>
				</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
	
</form:form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
Calendar.setup({
    inputField		:	"startDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"endDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

$(document).ready(function(){
	$('#item1>tbody>tr').each(
	    	 function(){
	    		 var value = $(this).find(".NAME").text();
	   	    	 if(value =='Total'){
	   	    		 $(this).find("td").css({ 'color' : 'red', 'font-weight': 'bold'});
	   	    	 }
	});

	$('#item2>tbody>tr').each(
	   	 function(){
	   			 var value = $(this).find(".NAME").text();
	  	    	 if(value =='Total'){
	  	    		 $(this).find("td").css({ 'color' : 'red', 'font-weight': 'bold'});
	  	    	 }
	});

	$('#item3>tbody>tr').each(
		   	 function(){
		   			 var value = $(this).find(".NAME").text();
		  	    	 if(value =='Total'){
		  	    		 $(this).find("td").css({ 'color' : 'red', 'font-weight': 'bold'});
		  	    	 }
	});

	$('#item4>tbody>tr').each(
		   	 function(){
		   			 var value = $(this).find(".NAME").text();
		  	    	 if(value =='Total'){
		  	    		 $(this).find("td").css({ 'color' : 'red', 'font-weight': 'bold'});
		  	    	 }
	});
	
});
	
function focusIt()
{
  var mytext = document.getElementById("startDate");
  mytext.focus();
}

onload = focusIt;

</script>