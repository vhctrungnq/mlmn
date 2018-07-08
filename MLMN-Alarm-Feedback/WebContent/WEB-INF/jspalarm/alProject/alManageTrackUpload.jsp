<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<c:choose>
  <c:when test="${type == 'FOLLOW'}">
      <title><fmt:message key="title.alManageTrack.formUpload"/></title>
	<content tag="heading"><fmt:message key="title.alManageTrack.formUpload"/></content>
  </c:when>
  <c:when test="${type == 'UGRADE'}">
     <title><fmt:message key="Import dữ liệu nâng cấp phần mềm"/></title>
	<content tag="heading"><fmt:message key="Import dữ liệu nâng cấp phần mềm"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	<div>
    	
    	<input id="type" name="type" value="${type}" type="hidden" />
    </div>
	<table class="simple2">
		<tr>
			<td class="wid10 mwid140"><b><fmt:message key="qLNguoiDung.file"/></b></td>
			<td><input class="button" type="file" name="file" size="90"/>&nbsp;
			<input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.button.import"/>"/></td>
		</tr>
		<tr>
				<td>
				<b><fmt:message key="qLNguoiDung.dinhDangFile"/></b>
				</td>
				<td>
					<ul style="list-style-type: none;">
						<li>File import là file (.xls)</li>
						<c:choose>
							<c:when test="${type=='FOLLOW' }">
						<li>Dữ liệu trong file có dạng: 
							<code>
							&lt;<fmt:message key="alManageOnAir.projectId"/>&gt;<font color="red">(*)</font>,
							&lt;<fmt:message key="alManageProject.siteName"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="alManageProject.node"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="alManageProject.team"/>&gt;,
							&lt;<fmt:message key="alManageProject.finishRate"/>&gt;,
							&lt;<fmt:message key="alManageProject.deliveryPlanSDate"/>&gt;, 
							&lt;<fmt:message key="alManageProject.deliveryPlanEDate"/>&gt;, 
							&lt;<fmt:message key="alManageProject.deliveryPlanRate"/>&gt;, 
							&lt;<fmt:message key="alManageProject.deliveryPlanStatus"/>&gt;, 
							&lt;<fmt:message key="alManageProject.deliveryPlanVendorStaff"/>&gt;, 
							&lt;<fmt:message key="alManageProject.deliveryPlanVmsStaff"/>&gt;, 
							&lt;<fmt:message key="alManageProject.deliveryPlanDescription"/>&gt;, 
							&lt;<fmt:message key="alManageProject.installationPlanSDate"/>&gt;, 
							&lt;<fmt:message key="alManageProject.installationPlanEDate"/>&gt;, 
							&lt;<fmt:message key="alManageProject.installationPlanRate"/>&gt;, 
							&lt;<fmt:message key="alManageProject.installationPlanVendorStaff"/>&gt;, 
							&lt;<fmt:message key="alManageProject.installationPlanVmsStaff"/>&gt;,
							&lt;<fmt:message key="alManageProject.installationPlanStatus"/>&gt;,
							&lt;<fmt:message key="alManageProject.installationPlanDescription"/>&gt;, 
							&lt;<fmt:message key="alManageProject.commissioningPlanSDate"/>&gt;,
							&lt;<fmt:message key="alManageProject.commissioningPlanEDate"/>&gt;,
							&lt;<fmt:message key="alManageProject.integrationPlanSDate"/>&gt;,
							&lt;<fmt:message key="alManageProject.integrationPlanEDate"/>&gt;,
							&lt;<fmt:message key="alManageProject.acceptancePlanSDate"/>&gt;,
							&lt;<fmt:message key="alManageProject.acceptancePlanEDate"/>&gt;,
							&lt;<fmt:message key="alManageProject.installationSupervisor"/>&gt;,
							&lt;<fmt:message key="alManageProject.commissioningResponsible"/>&gt;,
							&lt;<fmt:message key="alManageProject.atnd"/>&gt;,
							&lt;<fmt:message key="alManageProject.manager"/>&gt;,
							&lt;<fmt:message key="alManageProject.supporter"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="AlManageTrackExample" href="${pageContext.request.contextPath}/upload/example/AlManageTrackExample.xls">AlManageTrackExample.xls</a>
						</li>
						</c:when>
						<c:otherwise>
						<li>Dữ liệu trong file có dạng: 
							<code>
							&lt;<fmt:message key="alManageOnAir.projectId"/>&gt;<font color="red">(*)</font>,
							&lt;<fmt:message key="alManageProject.siteName"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="alManageProject.node"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="alManageProject.team"/>&gt;,
							&lt;<fmt:message key="alManageProject.finishRate"/>&gt;,
							&lt;<fmt:message key="StartDate"/>&gt;, 
							&lt;<fmt:message key="EndDate"/>&gt;, 
							&lt;<fmt:message key="PlanRate"/>&gt;, 
							&lt;<fmt:message key="Status"/>&gt;, 
							&lt;<fmt:message key="Vendor"/>&gt;, 
							&lt;<fmt:message key="VMS"/>&gt;, 
							&lt;<fmt:message key="Software Version"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="UL_UpgradeSoftware_Example" href="${pageContext.request.contextPath}/upload/example/UL_UpgradeSoftware_Example.xls">UL_UpgradeSoftware_Example.xls</a>
						</li>
						</c:otherwise>
						</c:choose>
						<li>Chú ý:</li>
						<li>&nbsp;&nbsp;- Những thông tin được đánh dấu <font color="red">(*)</font> là thông tin nhập liệu bắt buộc. </li>
					</ul>
			</td>
		</tr>
	</table>
	<c:if test="${status != null}">
    	<div class="error">${status} ${statusExists}</div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu dự án tổng đài không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
							<display:column class="centerColumnMana" title="No." > <c:out value="${item1_rowNum}"/> </display:column>
							<display:column property="projectCode" titleKey="alManageOnAir.projectId" class="NOT_NULL"/>
							<display:column property="siteName" titleKey="alManageProject.siteName" class="NOT_NULL"/>
							<display:column property="node" titleKey="alManageProject.node" class="NOT_NULL"/>  
							<display:column property="team" titleKey="alManageProject.team" /> 
							<display:column property="finishRate" titleKey="alManageProject.finishRate" /> 
							<c:choose>
								<c:when test="${type =='UPGRADE' }">
									<display:column property="deliveryPlanDateStart" format="{0,date,dd/MM/yyyy}" titleKey="StartDate" />
									<display:column property="deliveryPlanDateEnd" format="{0,date,dd/MM/yyyy}" titleKey="EndDate" />
									<display:column property="deliveryPlanRate" titleKey="PlanRate" />
									<display:column property="deliveryPlanStatus" titleKey="Status" />
									<display:column property="deliveryPlanVendorStaff" titleKey="Vendor" />
									<display:column property="deliveryPlanVmsStaff" titleKey="VMS" />
									<display:column property="deliveryPlanDescription" titleKey="Software version" />
									
								</c:when>
								<c:otherwise>
									<display:column property="deliveryPlanDateStart" format="{0,date,dd/MM/yyyy}" titleKey="alManageProject.deliveryPlanSDate" />
									<display:column property="deliveryPlanDateEnd" format="{0,date,dd/MM/yyyy}" titleKey="alManageProject.deliveryPlanEDate" />
									<display:column property="deliveryPlanRate" titleKey="alManageProject.deliveryPlanRate" />
									<display:column property="deliveryPlanStatus" titleKey="alManageProject.deliveryPlanStatus" />
									<display:column property="deliveryPlanVendorStaff" titleKey="alManageProject.deliveryPlanVendorStaff" />
									<display:column property="deliveryPlanVmsStaff" titleKey="alManageProject.deliveryPlanVmsStaff" />
									<display:column property="deliveryPlanDescription" titleKey="alManageProject.deliveryPlanDescription" />
									<display:column property="installationPlanDateStart" format="{0,date,dd/MM/yyyy}" titleKey="alManageProject.installationPlanSDate" />
									<display:column property="installationPlanDateEnd" format="{0,date,dd/MM/yyyy}" titleKey="alManageProject.installationPlanEDate" />
									<display:column property="installationPlanRate" titleKey="alManageProject.installationPlanRate" />
									<display:column property="installationPlanVendorStaff" titleKey="alManageProject.installationPlanVendorStaff" />
									<display:column property="installationPlanVmsStaff" titleKey="alManageProject.installationPlanVmsStaff" />
									<display:column property="installationPlanStatus" titleKey="alManageProject.installationPlanStatus"/>
									<display:column property="installationPlanDescription" titleKey="alManageProject.installationPlanDescription" />
									<display:column property="commissioningPlanStart" format="{0,date,dd/MM/yyyy}"  titleKey="alManageProject.commissioningPlanSDate"/>	
									<display:column property="commissioningPlanEnd" format="{0,date,dd/MM/yyyy}"  titleKey="alManageProject.commissioningPlanEDate"/>
									<display:column property="integrationPlanStart" format="{0,date,dd/MM/yyyy}"  titleKey="alManageProject.integrationPlanSDate" />
									<display:column property="integrationPlanEnd" format="{0,date,dd/MM/yyyy}"  titleKey="alManageProject.integrationPlanEDate" />
									<display:column property="acceptancePlanStart" format="{0,date,dd/MM/yyyy}" titleKey="alManageProject.acceptancePlanSDate"/>
									<display:column property="acceptancePlanEnd" format="{0,date,dd/MM/yyyy}" titleKey="alManageProject.acceptancePlanEDate"/>
									<display:column property="supervisor" titleKey="alManageProject.installationSupervisor"/>
									<display:column property="commissioningResponsible" titleKey="alManageProject.commissioningResponsible"/>
									<display:column property="atnd" titleKey="alManageProject.atnd"/>
									<display:column property="manager" titleKey="alManageProject.manager"/>
									<display:column property="supporter" titleKey="alManageProject.supporter"/>	
								</c:otherwise>
							</c:choose>
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu dự án tổng đài hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
							<display:column class="centerColumnMana" title="No." > <c:out value="${item2_rowNum}"/> </display:column>
							<display:column property="projectCode" titleKey="alManageOnAir.projectId" class="NOT_NULL"/>
							<display:column property="siteName" titleKey="alManageProject.siteName" class="NOT_NULL"/>
							<display:column property="node" titleKey="alManageProject.node" class="NOT_NULL"/>  
							<display:column property="team" titleKey="alManageProject.team" /> 
							<display:column property="finishRate" titleKey="alManageProject.finishRate" />
							<c:choose>
								<c:when test="${type =='UPGRADE' }">
									<display:column property="deliveryPlanDateStart" format="{0,date,dd/MM/yyyy}" titleKey="StartDate" />
									<display:column property="deliveryPlanDateEnd" format="{0,date,dd/MM/yyyy}" titleKey="EndDate" />
									<display:column property="deliveryPlanRate" titleKey="PlanRate" />
									<display:column property="deliveryPlanStatus" titleKey="Status" />
									<display:column property="deliveryPlanVendorStaff" titleKey="Vendor" />
									<display:column property="deliveryPlanVmsStaff" titleKey="VMS" />
									<display:column property="deliveryPlanDescription" titleKey="Software version" />
									
								</c:when>
								<c:otherwise>
									<display:column property="deliveryPlanDateStart" format="{0,date,dd/MM/yyyy}" titleKey="alManageProject.deliveryPlanSDate" />
									<display:column property="deliveryPlanDateEnd" format="{0,date,dd/MM/yyyy}" titleKey="alManageProject.deliveryPlanEDate" />
									<display:column property="deliveryPlanRate" titleKey="alManageProject.deliveryPlanRate" />
									<display:column property="deliveryPlanStatus" titleKey="alManageProject.deliveryPlanStatus" />
									<display:column property="deliveryPlanVendorStaff" titleKey="alManageProject.deliveryPlanVendorStaff" />
									<display:column property="deliveryPlanVmsStaff" titleKey="alManageProject.deliveryPlanVmsStaff" />
									<display:column property="deliveryPlanDescription" titleKey="alManageProject.deliveryPlanDescription" />
									<display:column property="installationPlanDateStart" format="{0,date,dd/MM/yyyy}" titleKey="alManageProject.installationPlanSDate" />
									<display:column property="installationPlanDateEnd" format="{0,date,dd/MM/yyyy}" titleKey="alManageProject.installationPlanEDate" />
									<display:column property="installationPlanRate" titleKey="alManageProject.installationPlanRate" />
									<display:column property="installationPlanVendorStaff" titleKey="alManageProject.installationPlanVendorStaff" />
									<display:column property="installationPlanVmsStaff" titleKey="alManageProject.installationPlanVmsStaff" />
									<display:column property="installationPlanStatus" titleKey="alManageProject.installationPlanStatus"/>
									<display:column property="installationPlanDescription" titleKey="alManageProject.installationPlanDescription" />
									<display:column property="commissioningPlanStart" format="{0,date,dd/MM/yyyy}"  titleKey="alManageProject.commissioningPlanSDate"/>	
									<display:column property="commissioningPlanEnd" format="{0,date,dd/MM/yyyy}"  titleKey="alManageProject.commissioningPlanEDate"/>
									<display:column property="integrationPlanStart" format="{0,date,dd/MM/yyyy}"  titleKey="alManageProject.integrationPlanSDate" />
									<display:column property="integrationPlanEnd" format="{0,date,dd/MM/yyyy}"  titleKey="alManageProject.integrationPlanEDate" />
									<display:column property="acceptancePlanStart" format="{0,date,dd/MM/yyyy}" titleKey="alManageProject.acceptancePlanSDate"/>
									<display:column property="acceptancePlanEnd" format="{0,date,dd/MM/yyyy}" titleKey="alManageProject.acceptancePlanEDate"/>
									<display:column property="supervisor" titleKey="alManageProject.installationSupervisor"/>
									<display:column property="commissioningResponsible" titleKey="alManageProject.commissioningResponsible"/>
									<display:column property="atnd" titleKey="alManageProject.atnd"/>
									<display:column property="manager" titleKey="alManageProject.manager"/>
									<display:column property="supporter" titleKey="alManageProject.supporter"/>	
								</c:otherwise>
							</c:choose>
							
				</display:table>
			</div>
		</div>
	</c:if>
		<table>
		<tr>
			<td >
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm?type=${type}"'>
			</td>
		</tr>
	</table>
</form:form>
<script type="text/javascript">  
    $(function() {
    	$('#item2>tbody>tr').each(
    	    	 function(){
   					  ${highlight}
   						});

    	$('#item1>tbody>tr').each(
   	    	 function(){
  					  ${highlight}
  					});
		}); 
</script>