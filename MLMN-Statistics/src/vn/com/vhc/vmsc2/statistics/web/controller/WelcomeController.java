package vn.com.vhc.vmsc2.statistics.web.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.SYS_PARAMETERDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysParameterDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyIpbbInternetDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyMscMgwSummaryDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyMscSummaryDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyNodeChange2GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyNodeChange3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDySiteGrowth2G3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpipbbdataDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.SYS_PARAMETER;
import vn.com.vhc.vmsc2.statistics.domain.SysParameter;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBsc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBsc3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyIpbbInternet;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscMgwSummary;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscSummary;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyNodeChange2G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyNodeChange3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDySiteGrowth2G3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpipbbdata;
import vn.com.vhc.vmsc2.statistics.web.utils.AlarmSetting;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
public class WelcomeController extends BaseController {

	@Autowired
	private VRpDyBscDAO vRpDyBscDao;
	@Autowired
	private VRpDySiteGrowth2G3GDAO vRpDySiteGrowth2G3GDao;
	@Autowired
	private VRpDyNodeChange2GDAO vRpDyNodeChange2GDao;
	@Autowired
	private VRpDyNodeChange3GDAO vRpDyNodeChange3GDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private SysUsersDAO sysUsersDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private VRpipbbdataDAO vipbbdataDao;
	@Autowired
	private VRpDyIpbbInternetDAO vipbbInternetDao;
	@Autowired
	private VRpDyMscSummaryDAO vRpDyMscSummaryDAO;
	@Autowired
	private VRpDyMscMgwSummaryDAO vRpDyMscMgwSummaryDAO;
	@Autowired
	private VRpDyBsc3GDAO vRpDyBsc3GDao;
	@Autowired
	private SysParameterDAO sysParamterDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat dff = new SimpleDateFormat("dd/MM");

	@RequestMapping("/welcome")
	public ModelAndView welcome(@RequestParam(required = false) Date day, @RequestParam(required = false) String region, ModelMap model) {
		String title="",content="";
		
		long currentTime = System.currentTimeMillis();
		if (day == null)
			day = new Date(currentTime - 24 * 60 * 60 * 1000);

		model.addAttribute("day", df.format(day));
		region = "";
		
		title = "Thống kế chất lượng mạng lưới toàn miền nam";
		content = "THỐNG KÊ CHẤT LƯỢNG MẠNG LƯỚI TOÀN MIỀN NAM NGÀY "+df.format(day); 
		
		model.addAttribute("title", title);
		model.addAttribute("content",content);
		
		List<SysParameter> regionList = sysParamterDao.filter("REGION"); 
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight1", Helper.highLight(highlightConfigList, "vRpDyRegion"));
		model.addAttribute("highlight2", Helper.highLight(highlightConfigList, "vRpDyRegion3G"));
		model.addAttribute("highlight3", Helper.highLight(highlightConfigList, "vRpDyBscWelcome3G"));
		model.addAttribute("highlight4", Helper.highLight(highlightConfigList, "vRpDyBsc2G"));
		model.addAttribute("highlight5", Helper.highLight(highlightConfigList, "vRpDyDistrict3G"));
		model.addAttribute("highlight6", Helper.highLight(highlightConfigList, "vRpDyDistrict2G3G"));
		model.addAttribute("highlight7", Helper.highLight(highlightConfigList, "vRpDyBranch3G"));
		model.addAttribute("highlight8", Helper.highLight(highlightConfigList, "vRpDyBranch2G3G"));
		model.addAttribute("highlight9", Helper.highLightKpis(highlightConfigList, "vipbbInternet"));
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "user")); 
		
		model.addAttribute("regionList", regionList);
		model.addAttribute("region", region);
		

		DateTime dt = new DateTime(day);
		Date startDay = dt.minusDays(30).toDate();
		Date startDayCell = dt.minusDays(7).toDate();
		// IPBackBone data report
		List<VRpipbbdata> vipbbdata = vipbbdataDao.filter("","", df.format(day), df.format(day));
		model.addAttribute("vipbbdata", vipbbdata);
		// IPBackBone Internet report
		List<VRpDyIpbbInternet> vipbbInternet = vipbbInternetDao.filter(df.format(day), df.format(day), "", "");
		model.addAttribute("vipbbInternet", vipbbInternet);
		// CS Core - GSM report
		List<VRpDyMscSummary> vRpDyMscSummary = vRpDyMscSummaryDAO.filterCSCore(df.format(day), df.format(day));
		model.addAttribute("vRpDyMscSummary", vRpDyMscSummary);
		// CS Core - MGW report
		List<VRpDyMscMgwSummary> vRpDyMscMgwSummary = vRpDyMscMgwSummaryDAO.filterCSCoreMGW(df.format(day), df.format(day));
		model.addAttribute("vRpDyMscMgwSummary", vRpDyMscMgwSummary);
		// 2G report
		String bscid = "";
		List<VRpDyBsc> vRpDyBsc2G = vRpDyBscDao.filter(df.format(day), df.format(day), bscid, region);
		model.addAttribute("vRpDyBsc2GList", vRpDyBsc2G);
		
		List<VRpDyBsc3G> vRpDyBscWelcome3G = vRpDyBsc3GDao.filter(df.format(day), df.format(day), bscid, region);
		model.addAttribute("vRpDyBscWelcome3GList", vRpDyBscWelcome3G);
		
		// GAP site site growth
		List<VRpDySiteGrowth2G3G> vRpDySiteGrowth2G3G = vRpDySiteGrowth2G3GDao.filter(df.format(startDay), df.format(day), region);
		model.addAttribute("vRpDySiteGrowth2G3GList", vRpDySiteGrowth2G3G);

		List<HighlightConfig> highlightConfig = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfig, "user"));
		model.addAttribute("highlight9", Helper.highLightKpis(highlightConfig, "vipbbInternet"));
		// chart
		List<String> categoriesSiteGrowth = new ArrayList<String>();
		List<Float> tTraf2gList = new ArrayList<Float>();
		List<Float> tTraf3gList = new ArrayList<Float>();
		List<Float> cssr2gList = new ArrayList<Float>();
		List<Float> cssr3gList = new ArrayList<Float>();
		List<Float> dcr2gList = new ArrayList<Float>();
		List<Float> dcr3gList = new ArrayList<Float>();
		List<Float> sites2GList = new ArrayList<Float>();
		List<Float> sites3GList = new ArrayList<Float>();
		List<VRpDySiteGrowth2G3G> vRpDySiteGrowth2G3G1 = vRpDySiteGrowth2G3GDao.filter(df.format(startDay), df.format(day), region);
		model.addAttribute("vRpDySiteGrowth2G3GList", vRpDySiteGrowth2G3G1);
		
		List<VRpDySiteGrowth2G3G> vRpDySiteGrowth2G3GAsc = vRpDySiteGrowth2G3GDao.filterAsc(df.format(startDay), df.format(day), region);
	
		for (VRpDySiteGrowth2G3G dySiteGrowth2G3G : vRpDySiteGrowth2G3GAsc) {
			categoriesSiteGrowth.add(dff.format(dySiteGrowth2G3G.getDay()));
			
			tTraf2gList.add(dySiteGrowth2G3G.getTraffic());
			tTraf3gList.add(dySiteGrowth2G3G.getSpeechTraffic());
			
			cssr2gList.add(dySiteGrowth2G3G.getCssr());
			cssr3gList.add(dySiteGrowth2G3G.getSpeechCssr());
			
			dcr2gList.add(dySiteGrowth2G3G.getDcr());
			dcr3gList.add(dySiteGrowth2G3G.getSpeechDrpr());
			
			sites2GList.add(Helper.int2Float(dySiteGrowth2G3G.getSites2g()));
			sites3GList.add(Helper.int2Float(dySiteGrowth2G3G.getSites3g()));
		}

		Map<String, List<Float>> siteGrowthSeries = new LinkedHashMap<String, List<Float>>();
		siteGrowthSeries.put("Sites 2g-column-f5f54e", sites2GList);
		siteGrowthSeries.put("Sites 3g-column-d7833b", sites3GList);
		
		model.addAttribute("siteGrowthChart", Chart.multiColumnWelcome("siteGrowthChart", "Phát triển Site", categoriesSiteGrowth, siteGrowthSeries,"",""));
		List<VRpDyNodeChange2G> vRpDyNodeChange2G = vRpDyNodeChange2GDao.filter(df.format(startDayCell), df.format(day), region);
		model.addAttribute("vRpDyNodeChange2GList", vRpDyNodeChange2G);
		
		List<VRpDyNodeChange3G> vRpDyNodeChange3G = vRpDyNodeChange3GDao.filter(df.format(startDayCell), df.format(day), region);
		model.addAttribute("vRpDyNodeChange3GList", vRpDyNodeChange3G);
		return new ModelAndView("homePage");
	
			
	}
	@RequestMapping("/whitePage")
	public String whitePage(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		return "jsp/whitePage";
	}
	
	@RequestMapping(value="/help/list")
	public String help(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String docTypeNumber = getProperties("DOCUMENT_TYPE_NUMBER");
		String docNumber = "";
		String link="";
		String linkTitle = "";
		for(int j = 0; j < Integer.parseInt(docTypeNumber); j ++)
		{
			linkTitle += "<td style=\"background-color:#EEE9E9; border:1px solid #C0C0C0;color:gray;font-size:12px;font-weight:800;line-height:20px;padding:2px;\">";
			 linkTitle +=  getProperties("DOCUMENT_TYPE_TITLE_"+j  );
				linkTitle += "</td>";
			 
			
		}
		for(int j = 0; j < Integer.parseInt(docTypeNumber); j ++)
		{
			link += "<td style=\"background-color:ghostwhite; padding:10px;padding-right:80px;border:1px solid #C0C0C0;margin-right:5px;\">";
			docNumber =   getProperties("DOCUMENT_NUMBER_" + j) ;
			
			for(int i =0; i<Integer.parseInt(docNumber); i++)
			{
				 link += "<div><img width=\"22\" height=\"22\" alt=\"\" src=\""+getProperties("SRC_DOC_"+j+i)+"\">" +
							"<a style= \"font-size:12px;\" href=\""+getProperties("HREF_DOC_"+j+i)+"\">"+getProperties("TITLE_DOC_"+j+i)+"</a>" +
							"</div>"; 
			}
			link += "</td>";
		}
		
		
		
		
		model.addAttribute("link", link);
		model.addAttribute("linkTitle", linkTitle);
		return "help";
	}
	private String getProperties(String tag)
	{
		String propFileName = System.getProperty("jboss.server.home.dir") + "/conf/system_mlmn.properties";
		InputStream propsStream = null;
		try {
			propsStream = new FileInputStream(propFileName);
			Properties props = new Properties();
			props.load(propsStream);
			propsStream.close();

			return props.getProperty(tag);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return "notlink";
	}
	/**
	 * Kiem tra dang nhap
	 */
	@RequestMapping("/loginDefault")
	public String login(@RequestParam(required = false) String module,
			@RequestParam(required = false) String loggedout,
			@RequestParam(required = false) String authfailed,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//luu lai module
		RequestContextHolder.currentRequestAttributes().setAttribute("module", module, RequestAttributes.SCOPE_SESSION);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(module != null || AlarmSetting._MODULE != null){
			String record = "";
			if(module != null)
				record = module;
			else
				record = AlarmSetting._MODULE;
			
			List<SYS_PARAMETER> moduleValue= sysParameterDao.getModuleWhenLogin(record);
			if(moduleValue.size() > 0)
				model.addAttribute("moduleC2", moduleValue.get(0).getRemark());
		}
		
		
		if(!username.equals("anonymousUser")){
			
			return "redirect:temp.htm";
		}
		
		
		
		return "../../login";
	}
	
	/**
	 * Dang nhap thanh cong
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/temp")
	public String redirect(
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		boolean loginFlag = false;
		try {
			loginFlag = (Boolean) RequestContextHolder.currentRequestAttributes().getAttribute("loginFlag", RequestAttributes.SCOPE_SESSION);
		} catch (Exception ex) {}
		
		// remove attributes login
		if (loginFlag) {
			RequestContextHolder.currentRequestAttributes().removeAttribute("loginFlag", RequestAttributes.SCOPE_SESSION);
			RequestContextHolder.currentRequestAttributes().removeAttribute("globalData", RequestAttributes.SCOPE_SESSION);
			System.out.println("remove attributes login");
		}
		
		// get module da luu session
		// dua vao user va module -> home_page
		String module  = "";
		try {
			if (RequestContextHolder.currentRequestAttributes().getAttribute("module", RequestAttributes.SCOPE_SESSION) != null) {
				module = RequestContextHolder.currentRequestAttributes().getAttribute("module", RequestAttributes.SCOPE_SESSION).toString();
				
			}
			
		} catch(Exception ex) {}
		
		if (module.equals("") && AlarmSetting._MODULE != null) {
			module = AlarmSetting._MODULE;
		}
		
		System.out.println("Load module: " + module);
		
		String record = "";
		if(module != null)
			record = module;
		else
			record = AlarmSetting._MODULE;
		
		if(record != null && !record.equals("") && !sysUsersDao.getCountUserOfModule(SecurityContextHolder.getContext().getAuthentication().getName(), record)) {
			List<SYS_PARAMETER> moduleValue= sysParameterDao.getModuleWhenLogin(record);
			if(moduleValue.size() > 0)
				model.addAttribute("moduleC2", moduleValue.get(0).getRemark());
			
			model.addAttribute("noticeLogin", "Bạn không có quyền truy cập module. Vui lòng đăng nhập.");
			return "../../login";
		}
		
		String page = "";
		List<SYS_PARAMETER> homePage = sysParameterDao.getHomePage(module);
		if(homePage.size() > 0)
			page = homePage.get(0).getValue();
		return "redirect:"+ page +".htm";
	}
}
