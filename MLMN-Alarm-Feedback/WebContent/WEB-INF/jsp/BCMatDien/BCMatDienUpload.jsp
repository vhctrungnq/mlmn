<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>${titleU}</title>

<content tag="heading">${titleU}</content>
 <form:form  method="post" action="upload.htm" enctype="multipart/form-data">
	<table class="simple2">	
	    	<tr style="height:20px;" >
	    		<td width="150px"><b><fmt:message key="cautruc.filepath"/> <font color="red">(*)</font></b></td>
	    		<td><input type="file" size="110" name="file" id="file" class="button" />
	    			<input type="submit" id="upload" class="button" value="<fmt:message key="global.button.import"/>" />
	    		</td>
	    	</tr>
	    	<tr style="height:100px;">
	    		<td><b>
	    			<fmt:message key="global.FileExample"/></b>
	    		</td>
	    		<td>
	    			<ul>
	    				<li><fmt:message key="global.formatFile"/></li>
						<li><fmt:message key="global.formatData"/>
							<code style="font-size:12;">
								&lt;<fmt:message key="vAlRbLossPower.bscid"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.cellid"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.sdate"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.edate"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.diffMD"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.ddhBaoMd"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.mllSdate"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.mllEdate"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.diffMLL"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.ddhBaoMll"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.dvtTeamProcess"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.dvtUserProcess"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.ungCuuMpd"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.nodeTruyenDan"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.acLow"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.mch1800"/>&gt;,
								&lt;<fmt:message key="vAlRbLossPower.description"/>&gt;
								&lt;<fmt:message key="qLPhongBan.region"/>&gt;
							</code></li>
						<li><fmt:message key="global.chuY1"/>
						</li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/VAlRbLossPower.xls" title="VAlRbLossPower" style="color: blue; ">VAlRbLossPower.xls</a></li>
	    			</ul>
	    		</td>
	    	</tr>
	    	
	    	<tr style="height:50px;">
	    		<td><b><fmt:message key="cautruc.insetFile"/> </b></td>
	    		<td><p><font color="red">${errorContent}</font></p></td>
	    	</tr>
	    	</table>
				<div style="overflow-y: auto; overflow-x: hidden; max-height: 500px;">
	    			<display:table name="${alarmList}" class="simple2" id="item" requestURI="">
					  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="bscid" titleKey="vAlRbLossPower.bscid" style="min-width:55px;max-width:55px;"/>
					  	<display:column property="cellid" titleKey="vAlRbLossPower.cellid" />
					  	<display:column property="sdateStrImport"  titleKey="vAlRbLossPower.sdate" format="{0,date,dd/MM/yyyy HH:mm}" style="min-width:105px;max-width:105px;"/>
						<display:column property="edateStrImport"  titleKey="vAlRbLossPower.edate" format="{0,date,dd/MM/yyyy HH:mm}" style="min-width:105px;max-width:105px;"/>
						
						<display:column property="tgMDImport" titleKey="vAlRbLossPower.diffMD" style="max-width:30px;"/>
						<display:column property="ddMDStrImport" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossPower.ddhBaoMd" style="min-width:105px;max-width:105px;" />
						<display:column property="mllsdateStrImport" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossPower.mllSdate" style="min-width:105px;max-width:105px;" />
						<display:column property="mlledateStrImport"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossPower.mllEdate" style="min-width:105px;max-width:105px;"/>
						<display:column property="tgMLLImport" titleKey="vAlRbLossPower.diffMLL" style="max-width:30px;"/>
						<display:column property="ddhMllStrImport" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="vAlRbLossPower.ddhBaoMll" style="min-width:105px;max-width:105px;"/>
						<display:column property="dvtTeamProcess" titleKey="vAlRbLossPower.dvtTeamProcess" style="max-width:40px;"/>
						<display:column property="dvtUserProcess"  titleKey="vAlRbLossPower.dvtUserProcess" style="max-width:50px;"/>
						<display:column property="ungCuuMpd"  titleKey="vAlRbLossPower.ungCuuMpd" style="max-width:10px;"/>
						<display:column property="nodeTruyenDan"  titleKey="vAlRbLossPower.nodeTruyenDan" style="max-width:10px;"/>
						<display:column property="acLow"  titleKey="vAlRbLossPower.acLow" style="max-width:10px;"/>
						<display:column property="mch1800"  titleKey="vAlRbLossPower.mch1800" style="max-width:10px;"/>
						<display:column property="description"  titleKey="vAlRbLossPower.description" style="max-width:50px;"/>
						<display:column property="region"  titleKey="qLPhongBan.region" style="max-width:50px;"/>
				
					</display:table>
	    		</div>
		<table>
			<tr>
				<td>
	               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm"'>
				</td>
			</tr>
		</table>
</form:form>