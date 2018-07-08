<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>lưu lượng hướng VMS - Doanh nghiệp khác</title>
<content tag="heading"><center><h1>LƯU LƯỢNG HƯỚNG VMS - DOANH NGHIỆP KHÁC</h1></center></content>

<div class="ui-tabs-panel">
	<form method="get" action="listDnk.htm">
		<table width="100%" class="form">
			<tr>
				<td align="left">
			        Chọn ngày :  <input value="${day}" name="day" id="day" size="10" maxlength="10" />
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	<br/>
	<center><h2>TRAFFIC THEO TỪNG DOANH NGHIỆP KHÁC</h2></center>
	<br/>
	<div style="overflow: auto;">
		<display:table name="${dyRouteCoresBhReport}" id="routeCore" requestURI="" pagesize="100" class="simple2" export="true">
			<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày" />
			<display:column property="fromNode" titleKey="MSCID" />
			<display:column property="toNode" titleKey="ROUTE NAME" />
			<display:column property="dev" titleKey="DEV" />
			<display:column property="e1" titleKey="E1" />
			<display:column property="routeTraf" titleKey="TRAFFIC" />
			<display:column property="outRouteTraf" titleKey="OUT_TRAFFIC" />
			<display:column property="inRouteTraf" titleKey="INC_TRAFFIC" />
			<display:column property="util" titleKey="UTILITY(%)" />
			<display:column property="busyHour" titleKey="BUSY HOUR" />
			<display:column property="bhTraffic" titleKey="BH TRAFFIC" />
		</display:table>
	</div>
	<br/>
	<table>
	<tr>
		<td><div id="routeTrafChart" style="width: 400px; height: 400px; margin: 0 auto"></div></td>
		<td style="width:10px"></td>
		<td><div id="routeInTrafChart" style="width: 400px; height: 400px; margin: 0 auto"></div></td>
		<td style="width:10px"></td>
		<td><div id="routeOutTrafChart" style="width: 400px; height: 400px; margin: 0 auto"></div></td>
	</tr>
	</table>
	<br/>
		<table width="100%" class="form">
			<tr>
				<td align="left">
			        Chọn route :  <input value="${routeid1}" name="routeid1" id="routeid1" size="10" maxlength="10" />
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	<br/>
	<c:forEach var="i" begin="0" end="${count1}" step="1">
	<div id="trafChartReport${i}" style="width: 1000px; margin: 1em auto"></div>
	</c:forEach>
	<br/>
	<br/>
	<center><h2>TRAFFIC THEO TỪNG ROUTE HƯỚNG DOANH NGHIỆP KHÁC</h2></center>
	<br/>
	<div style="overflow: auto;">
		<display:table name="${dyRouteCores}" id="routeCore1" requestURI="" pagesize="100" class="simple2" export="true">
			<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày" />
			<display:column property="fromNode" titleKey="MSCID" />
			<display:column property="routeid" titleKey="ROUTE ID" />
			<display:column property="toNode" titleKey="ROUTE NAME" />
			<display:column property="dev" titleKey="DEV" />
			<display:column property="e1" titleKey="E1" />
			<display:column property="routeTraf" titleKey="TRAFFIC" />
			<display:column property="outRouteTraf" titleKey="OUT_TRAFFIC" />
			<display:column property="inRouteTraf" titleKey="INC_TRAFFIC" />
			<display:column property="util" titleKey="UTILITY(%)" />
			<display:column property="busyHour" titleKey="BUSY HOUR" />
			<display:column property="bhTraffic" titleKey="BH TRAFFIC" />
		</display:table>
	</div>
	<br/>
		<table width="100%" class="form">
			<tr>
				<td align="left">
			        Chọn route :  <input value="${routeid2}" name="routeid2" id="routeid2" size="10" maxlength="10" />
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	<br/>
	<c:forEach var="i" begin="0" end="${count2}" step="1">
	<div id="trafChart${i}" style="width: 1000px; margin: 1em auto"></div>
	</c:forEach>
	<br/>
	</form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${routeTrafChart}
${routeInTrafChart}
${routeOutTrafChart}
${trafChart0}${trafChartReport0}${trafChart1}${trafChartReport1}${trafChart2}${trafChartReport2}${trafChart3}${trafChartReport3}${trafChart4}${trafChartReport4}${trafChart5}${trafChartReport5}${trafChart6}${trafChartReport6}${trafChart7}${trafChartReport7}${trafChart8}${trafChartReport8}${trafChart9}${trafChartReport9}${trafChart10}${trafChartReport10}${trafChart11}${trafChartReport11}${trafChart12}${trafChartReport12}${trafChart13}${trafChartReport13}${trafChart14}${trafChartReport14}${trafChart15}${trafChartReport15}${trafChart16}${trafChartReport16}${trafChart17}${trafChartReport17}${trafChart18}${trafChartReport18}${trafChart19}${trafChartReport19}${trafChart20}${trafChartReport20}${trafChart21}${trafChartReport21}${trafChart22}${trafChartReport22}${trafChart23}${trafChartReport23}${trafChart24}${trafChartReport24}${trafChart25}${trafChartReport25}${trafChart26}${trafChartReport26}${trafChart27}${trafChartReport27}${trafChart28}${trafChartReport28}${trafChart29}${trafChartReport29}${trafChart30}${trafChartReport30}${trafChart31}${trafChartReport31}${trafChart32}${trafChartReport32}${trafChart33}${trafChartReport33}${trafChart34}${trafChartReport34}${trafChart35}${trafChartReport35}${trafChart36}${trafChartReport36}${trafChart37}${trafChartReport37}${trafChart38}${trafChartReport38}${trafChart39}${trafChartReport39}${trafChart40}${trafChartReport40}${trafChart41}${trafChartReport41}${trafChart42}${trafChartReport42}${trafChart43}${trafChartReport43}${trafChart44}${trafChartReport44}${trafChart45}${trafChartReport45}${trafChart46}${trafChartReport46}${trafChart47}${trafChartReport47}${trafChart48}${trafChartReport48}${trafChart49}${trafChartReport49}${trafChart50}${trafChartReport50}${trafChart51}${trafChartReport51}${trafChart52}${trafChartReport52}${trafChart53}${trafChartReport53}${trafChart54}${trafChartReport54}${trafChart55}${trafChartReport55}${trafChart56}${trafChartReport56}${trafChart57}${trafChartReport57}${trafChart58}${trafChartReport58}${trafChart59}${trafChartReport59}${trafChart60}${trafChartReport60}${trafChart61}${trafChartReport61}${trafChart62}${trafChartReport62}${trafChart63}${trafChartReport63}${trafChart64}${trafChartReport64}${trafChart65}${trafChartReport65}${trafChart66}${trafChartReport66}${trafChart67}${trafChartReport67}${trafChart68}${trafChartReport68}${trafChart69}${trafChartReport69}${trafChart70}${trafChartReport70}${trafChart71}${trafChartReport71}${trafChart72}${trafChartReport72}${trafChart73}${trafChartReport73}${trafChart74}${trafChartReport74}${trafChart75}${trafChartReport75}${trafChart76}${trafChartReport76}${trafChart77}${trafChartReport77}${trafChart78}${trafChartReport78}${trafChart79}${trafChartReport79}${trafChart80}${trafChartReport80}${trafChart81}${trafChartReport81}${trafChart82}${trafChartReport82}${trafChart83}${trafChartReport83}${trafChart84}${trafChartReport84}${trafChart85}${trafChartReport85}${trafChart86}${trafChartReport86}${trafChart87}${trafChartReport87}${trafChart88}${trafChartReport88}${trafChart89}${trafChartReport89}${trafChart90}${trafChartReport90}${trafChart91}${trafChartReport91}${trafChart92}${trafChartReport92}${trafChart93}${trafChartReport93}${trafChart94}${trafChartReport94}${trafChart95}${trafChartReport95}${trafChart96}${trafChartReport96}${trafChart97}${trafChartReport97}${trafChart98}${trafChartReport98}${trafChart99}${trafChartReport99}${trafChart100}${trafChartReport100}${trafChart101}${trafChartReport101}${trafChart102}${trafChartReport102}${trafChart103}${trafChartReport103}${trafChart104}${trafChartReport104}${trafChart105}${trafChartReport105}${trafChart106}${trafChartReport106}${trafChart107}${trafChartReport107}${trafChart108}${trafChartReport108}${trafChart109}${trafChartReport109}${trafChart110}${trafChartReport110}${trafChart111}${trafChartReport111}${trafChart112}${trafChartReport112}${trafChart113}${trafChartReport113}${trafChart114}${trafChartReport114}${trafChart115}${trafChartReport115}${trafChart116}${trafChartReport116}${trafChart117}${trafChartReport117}${trafChart118}${trafChartReport118}${trafChart119}${trafChartReport119}${trafChart120}${trafChartReport120}${trafChart121}${trafChartReport121}${trafChart122}${trafChartReport122}${trafChart123}${trafChartReport123}${trafChart124}${trafChartReport124}${trafChart125}${trafChartReport125}${trafChart126}${trafChartReport126}${trafChart127}${trafChartReport127}${trafChart128}${trafChartReport128}${trafChart129}${trafChartReport129}${trafChart130}${trafChartReport130}${trafChart131}${trafChartReport131}${trafChart132}${trafChartReport132}${trafChart133}${trafChartReport133}${trafChart134}${trafChartReport134}${trafChart135}${trafChartReport135}${trafChart136}${trafChartReport136}${trafChart137}${trafChartReport137}${trafChart138}${trafChartReport138}${trafChart139}${trafChartReport139}${trafChart140}${trafChartReport140}${trafChart141}${trafChartReport141}${trafChart142}${trafChartReport142}${trafChart143}${trafChartReport143}${trafChart144}${trafChartReport144}${trafChart145}${trafChartReport145}${trafChart146}${trafChartReport146}${trafChart147}${trafChartReport147}${trafChart148}${trafChartReport148}${trafChart149}${trafChartReport149}${trafChart150}${trafChartReport150}${trafChart151}${trafChartReport151}${trafChart152}${trafChartReport152}${trafChart153}${trafChartReport153}${trafChart154}${trafChartReport154}${trafChart155}${trafChartReport155}${trafChart156}${trafChartReport156}${trafChart157}${trafChartReport157}${trafChart158}${trafChartReport158}${trafChart159}${trafChartReport159}${trafChart160}${trafChartReport160}${trafChart161}${trafChartReport161}${trafChart162}${trafChartReport162}${trafChart163}${trafChartReport163}${trafChart164}${trafChartReport164}${trafChart165}${trafChartReport165}${trafChart166}${trafChartReport166}${trafChart167}${trafChartReport167}${trafChart168}${trafChartReport168}${trafChart169}${trafChartReport169}${trafChart170}${trafChartReport170}${trafChart171}${trafChartReport171}${trafChart172}${trafChartReport172}${trafChart173}${trafChartReport173}${trafChart174}${trafChartReport174}${trafChart175}${trafChartReport175}${trafChart176}${trafChartReport176}${trafChart177}${trafChartReport177}${trafChart178}${trafChartReport178}${trafChart179}${trafChartReport179}${trafChart180}${trafChartReport180}${trafChart181}${trafChartReport181}${trafChart182}${trafChartReport182}${trafChart183}${trafChartReport183}${trafChart184}${trafChartReport184}${trafChart185}${trafChartReport185}${trafChart186}${trafChartReport186}${trafChart187}${trafChartReport187}${trafChart188}${trafChartReport188}${trafChart189}${trafChartReport189}${trafChart190}${trafChartReport190}${trafChart191}${trafChartReport191}${trafChart192}${trafChartReport192}${trafChart193}${trafChartReport193}${trafChart194}${trafChartReport194}${trafChart195}${trafChartReport195}${trafChart196}${trafChartReport196}${trafChart197}${trafChartReport197}${trafChart198}${trafChartReport198}${trafChart199}${trafChartReport199}${trafChart200}${trafChartReport200}
${trafChart201}${trafChartReport201}${trafChart202}${trafChartReport202}${trafChart203}${trafChartReport203}${trafChart204}${trafChartReport204}${trafChart205}${trafChartReport205}${trafChart206}${trafChartReport206}${trafChart207}${trafChartReport207}${trafChart208}${trafChartReport208}${trafChart209}${trafChartReport209}${trafChart210}${trafChartReport210}${trafChart211}${trafChartReport211}${trafChart212}${trafChartReport212}${trafChart213}${trafChartReport213}${trafChart214}${trafChartReport214}${trafChart215}${trafChartReport215}${trafChart216}${trafChartReport216}${trafChart217}${trafChartReport217}${trafChart218}${trafChartReport218}${trafChart219}${trafChartReport219}${trafChart220}${trafChartReport220}${trafChart221}${trafChartReport221}${trafChart222}${trafChartReport222}${trafChart223}${trafChartReport223}${trafChart224}${trafChartReport224}${trafChart225}${trafChartReport225}${trafChart226}${trafChartReport226}${trafChart227}${trafChartReport227}${trafChart228}${trafChartReport228}${trafChart229}${trafChartReport229}${trafChart230}${trafChartReport230}${trafChart231}${trafChartReport231}${trafChart232}${trafChartReport232}${trafChart233}${trafChartReport233}${trafChart234}${trafChartReport234}${trafChart235}${trafChartReport235}${trafChart236}${trafChartReport236}${trafChart237}${trafChartReport237}${trafChart238}${trafChartReport238}${trafChart239}${trafChartReport239}${trafChart240}${trafChartReport240}${trafChart241}${trafChartReport241}${trafChart242}${trafChartReport242}${trafChart243}${trafChartReport243}${trafChart244}${trafChartReport244}${trafChart245}${trafChartReport245}${trafChart246}${trafChartReport246}${trafChart247}${trafChartReport247}${trafChart248}${trafChartReport248}${trafChart249}${trafChartReport249}${trafChart250}${trafChartReport250}${trafChart251}${trafChartReport251}${trafChart252}${trafChartReport252}${trafChart253}${trafChartReport253}${trafChart254}${trafChartReport254}${trafChart255}${trafChartReport255}${trafChart256}${trafChartReport256}${trafChart257}${trafChartReport257}${trafChart258}${trafChartReport258}${trafChart259}${trafChartReport259}${trafChart260}${trafChartReport260}${trafChart261}${trafChartReport261}${trafChart262}${trafChartReport262}${trafChart263}${trafChartReport263}${trafChart264}${trafChartReport264}${trafChart265}${trafChartReport265}${trafChart266}${trafChartReport266}${trafChart267}${trafChartReport267}${trafChart268}${trafChartReport268}${trafChart269}${trafChartReport269}${trafChart270}${trafChartReport270}${trafChart271}${trafChartReport271}${trafChart272}${trafChartReport272}${trafChart273}${trafChartReport273}${trafChart274}${trafChartReport274}${trafChart275}${trafChartReport275}${trafChart276}${trafChartReport276}${trafChart277}${trafChartReport277}${trafChart278}${trafChartReport278}${trafChart279}${trafChartReport279}${trafChart280}${trafChartReport280}${trafChart281}${trafChartReport281}${trafChart282}${trafChartReport282}${trafChart283}${trafChartReport283}${trafChart284}${trafChartReport284}${trafChart285}${trafChartReport285}${trafChart286}${trafChartReport286}${trafChart287}${trafChartReport287}${trafChart288}${trafChartReport288}${trafChart289}${trafChartReport289}${trafChart290}${trafChartReport290}${trafChart291}${trafChartReport291}${trafChart292}${trafChartReport292}${trafChart293}${trafChartReport293}${trafChart294}${trafChartReport294}${trafChart295}${trafChartReport295}${trafChart296}${trafChartReport296}${trafChart297}${trafChartReport297}${trafChart298}${trafChartReport298}${trafChart299}${trafChartReport299}${trafChart300}${trafChartReport300}${trafChart301}${trafChartReport301}${trafChart302}${trafChartReport302}${trafChart303}${trafChartReport303}${trafChart304}${trafChartReport304}${trafChart305}${trafChartReport305}${trafChart306}${trafChartReport306}${trafChart307}${trafChartReport307}${trafChart308}${trafChartReport308}${trafChart309}${trafChartReport309}${trafChart310}${trafChartReport310}${trafChart311}${trafChartReport311}${trafChart312}${trafChartReport312}${trafChart313}${trafChartReport313}${trafChart314}${trafChartReport314}${trafChart315}${trafChartReport315}${trafChart316}${trafChartReport316}${trafChart317}${trafChartReport317}${trafChart318}${trafChartReport318}${trafChart319}${trafChartReport319}${trafChart320}${trafChartReport320}${trafChart321}${trafChartReport321}${trafChart322}${trafChartReport322}${trafChart323}${trafChartReport323}${trafChart324}${trafChartReport324}${trafChart325}${trafChartReport325}${trafChart326}${trafChartReport326}${trafChart327}${trafChartReport327}${trafChart328}${trafChartReport328}${trafChart329}${trafChartReport329}${trafChart330}${trafChartReport330}${trafChart331}${trafChartReport331}${trafChart332}${trafChartReport332}${trafChart333}${trafChartReport333}${trafChart334}${trafChartReport334}${trafChart335}${trafChartReport335}${trafChart336}${trafChartReport336}${trafChart337}${trafChartReport337}${trafChart338}${trafChartReport338}${trafChart339}${trafChartReport339}${trafChart340}${trafChartReport340}${trafChart341}${trafChartReport341}${trafChart342}${trafChartReport342}${trafChart343}${trafChartReport343}${trafChart344}${trafChartReport344}${trafChart345}${trafChartReport345}${trafChart346}${trafChartReport346}${trafChart347}${trafChartReport347}${trafChart348}${trafChartReport348}${trafChart349}${trafChartReport349}${trafChart350}${trafChartReport350}${trafChart351}${trafChartReport351}${trafChart352}${trafChartReport352}${trafChart353}${trafChartReport353}${trafChart354}${trafChartReport354}${trafChart355}${trafChartReport355}${trafChart356}${trafChartReport356}${trafChart357}${trafChartReport357}${trafChart358}${trafChartReport358}${trafChart359}${trafChartReport359}${trafChart360}${trafChartReport360}${trafChart361}${trafChartReport361}${trafChart362}${trafChartReport362}${trafChart363}${trafChartReport363}${trafChart364}${trafChartReport364}${trafChart365}${trafChartReport365}${trafChart366}${trafChartReport366}${trafChart367}${trafChartReport367}${trafChart368}${trafChartReport368}${trafChart369}${trafChartReport369}${trafChart370}${trafChartReport370}${trafChart371}${trafChartReport371}${trafChart372}${trafChartReport372}${trafChart373}${trafChartReport373}${trafChart374}${trafChartReport374}${trafChart375}${trafChartReport375}${trafChart376}${trafChartReport376}${trafChart377}${trafChartReport377}${trafChart378}${trafChartReport378}${trafChart379}${trafChartReport379}${trafChart380}${trafChartReport380}${trafChart381}${trafChartReport381}${trafChart382}${trafChartReport382}${trafChart383}${trafChartReport383}${trafChart384}${trafChartReport384}${trafChart385}${trafChartReport385}${trafChart386}${trafChartReport386}${trafChart387}${trafChartReport387}${trafChart388}${trafChartReport388}${trafChart389}${trafChartReport389}${trafChart390}${trafChartReport390}${trafChart391}${trafChartReport391}${trafChart392}${trafChartReport392}${trafChart393}${trafChartReport393}${trafChart394}${trafChartReport394}${trafChart395}${trafChartReport395}${trafChart396}${trafChartReport396}${trafChart397}${trafChartReport397}${trafChart398}${trafChartReport398}${trafChart399}${trafChartReport399}${trafChart400}${trafChartReport400}${trafChart401}${trafChartReport401}${trafChart402}${trafChartReport402}${trafChart403}${trafChartReport403}${trafChart404}${trafChartReport404}${trafChart405}${trafChartReport405}${trafChart406}${trafChartReport406}${trafChart407}${trafChartReport407}${trafChart408}${trafChartReport408}${trafChart409}${trafChartReport409}${trafChart410}${trafChartReport410}${trafChart411}${trafChartReport411}${trafChart412}${trafChartReport412}${trafChart413}${trafChartReport413}${trafChart414}${trafChartReport414}${trafChart415}${trafChartReport415}${trafChart416}${trafChartReport416}${trafChart417}${trafChartReport417}${trafChart418}${trafChartReport418}${trafChart419}${trafChartReport419}${trafChart420}${trafChartReport420}${trafChart421}${trafChartReport421}