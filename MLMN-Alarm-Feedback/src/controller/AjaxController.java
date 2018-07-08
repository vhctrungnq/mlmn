package controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.qldn.QLDNThanhToanDienController;

import vo.AlAlarmWorks;
import vo.AlWorkCalendar;
import vo.CConfigAlarmType;
import vo.FbDeptPlaces;
import vo.FbProcessFilter;
import vo.FbType;
import vo.HProvinceFb;
import vo.HProvincesCode;
import vo.HRouter;
import vo.MDepartment;
import vo.QldnDonViThuHuong;
import vo.QldnThongTinTram;
import vo.QldnTramTTDien;
import vo.RAlarmLog;
import vo.SYS_PARAMETER;
import vo.SysDefineSmsEmail;
import vo.SysStandardizedError;
import vo.SysUserEquipmentName;
import vo.SysUsers;
import vo.SysUsersCogencyLevel;
import vo.VDyIpbbLink;
import vo.W_WORKING_FEEDBACKS;
import vo.W_WORKING_MANAGEMENTS;
import vo.W_WORKING_TYPES;
import vo.dictionary.SendMail;
import dao.A_ALERT_USERSDAO;
import dao.AlAlarmTypesDAO;
import dao.AlAlarmWorksDAO;
import dao.AlWorkCalendarDAO;
import dao.CConfigAlarmTypeDAO;
import dao.CHighlightConfigsDAO;
import dao.CableDropEt4directionDAO;
import dao.CableDropEt4tlDAO;
import dao.CableET4MasterDAO;
import dao.Cabledropet4tlmgwDAO;
import dao.FbDeptPlacesDAO;
import dao.FbProcessDAO;
import dao.FbTypeDAO;
import dao.HProvinceFbDAO;
import dao.HProvincesCodeDAO;
import dao.HQualityNetworkDAO;
import dao.HRouterDAO;
import dao.MDepartmentDAO;
import dao.QldnDonViThuHuongDAO;
import dao.QldnThongTinTramDAO;
import dao.QldnTramTTDienDAO;
import dao.RAlarmLogDAO;
import dao.SYS_PARAMETERDAO;
import dao.SmsContentsDAO;
import dao.SysDefineSmsEmailDAO;
import dao.SysStandardizedErrorDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersCogencyLevelDAO;
import dao.SysUsersDAO;
import dao.VAlHBscDAO;
import dao.VAlHCellDAO;
import dao.VAlHMscDAO;
import dao.VAlHRncDAO;
import dao.VAlWarningSystemDAO;
import dao.VDyIpbbLinkDAO;
import dao.VRpDyIbcDAO;
import dao.W_WORKING_FEEDBACKSDAO;
import dao.W_WORKING_MANAGEMENTSDAO;
import dao.W_WORKING_TYPESDAO;

@Controller
@RequestMapping("/ajax/*")
public class AjaxController extends BaseController{

	@Autowired
	private HRouterDAO hRouterDao;
	@Autowired
	private VAlHCellDAO vAlHCellDao;
	@Autowired
	private W_WORKING_FEEDBACKSDAO feedbacksDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private SysUsersDAO sysUsersDao;

	@Autowired
	private FbProcessDAO fbProcessDAO;

	@Autowired
	private FbTypeDAO FbTypeDAO;

	@Autowired
	private RAlarmLogDAO rAlarmLogDAO;

	@Autowired
	private W_WORKING_MANAGEMENTSDAO working_managementDao;

	@Autowired 
	private VAlWarningSystemDAO vAlWarningSystemDAO;

	@Autowired 
	private A_ALERT_USERSDAO alertUsersDao;

	@Autowired
	private FbDeptPlacesDAO  FbdepplaceDAO ;

	@Autowired
	private MDepartmentDAO mDepartmentDao;

	@Autowired
	private VAlHBscDAO vAlHBscDAO;

	@Autowired
	private VAlHMscDAO vAlHMscDAO;

	@Autowired 
	private VAlHRncDAO vAlHRncDAO;

	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;

	@Autowired 
	private HQualityNetworkDAO hQualityNetworkDAO;

	@Autowired 
	private HProvinceFbDAO hProvinceFbDAO;

	@Autowired
	private CableET4MasterDAO cableET4MasterDAO;

	@Autowired
	private CableDropEt4directionDAO cableET4directionDAO;
	@Autowired
	private CableDropEt4tlDAO cableet4tlDao;
	@Autowired
	private Cabledropet4tlmgwDAO cableET4MgwDao;
	@Autowired
	private AlAlarmTypesDAO alAlarmTypesDAO;

	@Autowired
	private SysUserEquipmentNameDAO sysUserEquipmentNameDAO;

	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;

	@Autowired
	private MDepartmentDAO mDepartmentDAO;

	@Autowired
	private CConfigAlarmTypeDAO cConfigAlarmTypeDAO;

	@Autowired
	private SysUsersCogencyLevelDAO sysUsersCogencyLevelDAO;

	@Autowired
	private AlAlarmWorksDAO alAlarmWorksDAO;

	@Autowired
	private SmsContentsDAO smsContentsDAO;

	@Autowired
	private AlWorkCalendarDAO alWorkCalendarDAO;

	@Autowired
	private W_WORKING_TYPESDAO working_typesDao;

	@Autowired
	private SysDefineSmsEmailDAO  sysDefineSmsEmailDAO ;

	@Autowired
	private SysStandardizedErrorDAO  sysStandardizedErrorDao;
	
	@Autowired
	private VDyIpbbLinkDAO vDyIpbbLinkDao;
	
	@Autowired
	private QldnDonViThuHuongDAO qldnDonViThuHuongDao;
	
	@Autowired
	private QldnThongTinTramDAO qldnThongTinTramDao;
	@Autowired
	private QldnTramTTDienDAO qldnTramTTDienDAO;
	
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDAO;
	@Autowired
	VRpDyIbcDAO vRpDyIbcDAO;
	
	@RequestMapping("getCommentOfUser")
	public @ResponseBody
	List<W_WORKING_FEEDBACKS> getCommentOfUser(@RequestParam(required = false) String id, 
			@RequestParam(required = false) String noiDungComment,
			@RequestParam(required = false) String userName,
			@RequestParam(required = false) Integer idProcesses) throws UnsupportedEncodingException {
		W_WORKING_FEEDBACKS works_feedback = new W_WORKING_FEEDBACKS();

		String encodedurl = URLDecoder.decode(noiDungComment, "UTF-8"); 
		String noiDungNew = encodedurl.replace("\n", "<br>");

		works_feedback.setCreatedBy(userName);
		works_feedback.setNguoiNhanXet(userName);
		works_feedback.setNoiDung(noiDungNew);
		works_feedback.setIdWorks(Integer.parseInt(id));
		works_feedback.setIdProcesses(idProcesses);

		if (noiDungComment != null && noiDungComment != "")
		{
			feedbacksDao.insert(works_feedback);		
		}

		return feedbacksDao.filter(works_feedback);
	}

	@RequestMapping("guiMailTB")
	public @ResponseBody
	int guiMailTB(@RequestParam(required = false) Integer id,
			HttpServletRequest request) throws ParseException {
		W_WORKING_MANAGEMENTS w_working_details = (id == null) ? new W_WORKING_MANAGEMENTS() : working_managementDao.selectByPrimaryKey(id);

		List<SYS_PARAMETER> sysParameterByCodeList = sysParameterDao.getSysParameterByCode("GUI_EMAIL_CONG_VIEC");

		String from = sysParameterByCodeList.get(0).getName();
		String password = sysParameterByCodeList.get(0).getDataType();
		String to = sysUsersDao.selectSysUsersByUsername(w_working_details.getNguoiGiaoViec()).getEmail();
		if(to != "" && to != null)
		{
			String subject = sysParameterByCodeList.get(0).getValue();
			/*String message = "Ban nhan duoc mot thong bao ve cong viec " + w_working_details.getTenCongViec() + ".\n";
			message += "Vui long kiem tra tien do cong viec do.";*/

			String message = sysParameterByCodeList.get(0).getRemark();

			SendMail sendMail = new SendMail(from, password, to, subject, message);
			sendMail.send();

			saveMessage(request, "Gửi mail thành công.");
		}
		else
		{
			saveMessage(request, "Không tồn tại mail để gửi.");
		}

		return 1;
	}

	@RequestMapping("deleteComment")
	public @ResponseBody
	List<W_WORKING_FEEDBACKS> deleteComment(@RequestParam(required = false) String id, 
			@RequestParam(required = false) String idComment, @RequestParam(required = false) Integer idProcesses) {
		W_WORKING_FEEDBACKS works_feedback = new W_WORKING_FEEDBACKS();

		works_feedback.setIdWorks(Integer.parseInt(id));

		works_feedback.setIdProcesses(idProcesses);

		if (idComment != null && idComment != "")
		{
			feedbacksDao.deleteByPrimaryKey(Integer.parseInt(idComment));	
		}
		List<W_WORKING_FEEDBACKS> feedbacksNew = feedbacksDao.filter(works_feedback);
		return feedbacksNew;

	}

	@RequestMapping("getSystemByTypeWarning")
	public @ResponseBody
	List<String> getSystemByTypeWarning(@RequestParam(required = false) String warningType) {
		List<String> mscList = null;
		if (warningType==null || warningType.equals(""))
		{		
			mscList = vAlWarningSystemDAO.getSystemByTypeWarning(null);
		}
		else
		{
			if (warningType.equals("PS_CORE"))
			{
				mscList = vAlWarningSystemDAO.getSystemByTypeWarning("MSC");
			}
			if (warningType.equals("IPBB"))
			{
				mscList = vAlWarningSystemDAO.getSystemByTypeWarning("MSC");
			}
			if (warningType.equals("SU_CO_LON"))
			{
				mscList = vAlWarningSystemDAO.getSystemByTypeWarning(null);
			}
		}
		return mscList;
	}
	@RequestMapping("getTeamList")
	public @ResponseBody
	List<MDepartment> getTeamList() {
		//return mDepartmentDAO.getDepartmentList();
		return mDepartmentDAO.getDepartmentList();


	}
	@RequestMapping("getUserByDeparment")
	public @ResponseBody
	List<SysUsers> getUserByDeparment(@RequestParam(required = false) String dept,@RequestParam(required = false) String team) {
		List<SysUsers> userList = new ArrayList<SysUsers>();
		userList = sysUsersDao.getUserAllByMaPhong(dept,team);
		return 	userList;

	}
	@RequestMapping("getAreaBydeptCode")
	public @ResponseBody
	List<FbDeptPlaces> getAreaBydeptCode(@RequestParam(required = false) String team) {
		List<FbDeptPlaces> areaList = new ArrayList<FbDeptPlaces>();
		areaList = FbdepplaceDAO.getAreaBydeptCode(team);

		return 	areaList;

	}
	@RequestMapping("selectAreaAll")
	public @ResponseBody
	List<String> selectAreaAll(@RequestParam(required = false) String team) {
		List<String> areaList = new ArrayList<String>();
		areaList = FbdepplaceDAO.selectAreaAll();
		return 	areaList;

	}
	@RequestMapping("getCell")
	public @ResponseBody
	List<String> getCell(@RequestParam String term) {
		return vAlHCellDao.getAllCellByBsc("2G",null,term);
	}

	@RequestMapping("getAllCellOfBsc")
	public @ResponseBody
	List<String> getAllCellOfBsc(@RequestParam String bscid) {
		return vAlHCellDao.getAllCellByBsc("2G",bscid,null);
	}

	@RequestMapping("getSystemByOperator")
	public @ResponseBody
	List<String> getSystemByOperator(@RequestParam String operator,@RequestParam String warningTp) {
		return vAlHCellDao.getAllCellByBsc(warningTp,operator,null);	
	}

	@RequestMapping("getBSCIDByTeam")
	public @ResponseBody
	List<String> getBSCIDByTeam(@RequestParam String team) {
		return vAlHBscDAO.getBSCIDByTeam("2G",team,null);
	}
	@RequestMapping("getBSC23GByTeam")
	public @ResponseBody
	List<String> getBSC23GByTeam(@RequestParam String team) {
		List<String> str = vAlHBscDAO.getBSCIDByTeam(null,team, null);
		return str;
	}

	@RequestMapping("getRouterName")
	public @ResponseBody
	List<HRouter> getRouterName(@RequestParam String routeName) {
		List<HRouter> str =hRouterDao.getRouterName(routeName);
		return str;
	}

	@RequestMapping("getBSCOfTeam")
	public @ResponseBody
	List<String> getBscOfTeam(@RequestParam String team) {
		List<String> obj =vAlHBscDAO.getBSCIDByTeam(null,team, null);
		return obj;
	}


	@RequestMapping("getRNCIDByTeam")
	public @ResponseBody
	List<String> getRNCIDByTeam(@RequestParam String team) {
		return vAlHBscDAO.getBSCIDByTeam("3G",team, null);
	}

	/*@RequestMapping("getDipList")
	public @ResponseBody
	List<String> getDipList(@RequestParam String term) {
		return alDyRpErrorDipDAO.getDip(term);
	}*/
	@RequestMapping("getBSCIDByTeamAndType")
	public @ResponseBody
	List<String> getBSCIDByTeamAndType(@RequestParam String team,@RequestParam String netWork) {
		if (netWork.equals("3G"))
		{
			return vAlHBscDAO.getBSCIDByTeam("3G",team, null);
		}
		else
			return vAlHBscDAO.getBSCIDByTeam("2G",team, null);
	}

	@RequestMapping("getBSC_RNCList")
	public @ResponseBody
	List<String> getBSC_RNCList(@RequestParam String alarmType,@RequestParam String term) {

		return vAlHBscDAO.getBSCIDByTeam(alarmType,null, term);
	}

	@RequestMapping("getBSC23GByTeamSearch")
	public @ResponseBody
	List<String> getBSC23GByTeamSearch(@RequestParam String term) {
		return vAlHBscDAO.getBSCIDByTeam(null,null, term);
	}

	@RequestMapping("getBSCIDByTeamSearch")
	public @ResponseBody
	List<String> getBSCIDByTeamSearch(@RequestParam String term) {
		return vAlHBscDAO.getBSCIDByTeam("2G",null, term);
	}

	@RequestMapping("getRNCSearch")
	public @ResponseBody
	List<String> getRNCSearch(@RequestParam String term) {
		return vAlHBscDAO.getBSCIDByTeam("3G",null, term);
	}


	@RequestMapping("getQualityByGroup")
	public @ResponseBody
	List<String> getQualityByGroup(@RequestParam String groupName) {
		return hQualityNetworkDAO.getQualityName(groupName);
	}

	@RequestMapping("getSite23GByBsc")
	public @ResponseBody
	List<String> getSite23GByBsc(@RequestParam String term) {
		return vAlHCellDao.getAllCellByBsc(null, null, term);
	}

	
	
	@RequestMapping("getSite2GByBsc")
	public @ResponseBody
	List<String> getSite2GByBsc(@RequestParam String term) {
		return vAlHCellDao.getAllCellByBsc("2G", null, term);
	}

	@RequestMapping("getSite3GByBsc")
	public @ResponseBody
	List<String> getSite3GByBsc(@RequestParam String term) {
		return vAlHCellDao.getAllCellByBsc("3G", null, term);
	}

	@RequestMapping("getMscByVendor")
	public @ResponseBody
	List<String> getMscByVendor(@RequestParam String team) {
		List<String> str =vAlHMscDAO.getMscByVendor(team);
		return str;
	}
	@RequestMapping("getTeamProcessByOperator")
	public @ResponseBody
	Map<String, Object> getTeamProcessByOperator(@RequestParam String operator,@RequestParam String warningTp) {

		Map<String, Object> data = new HashMap<String, Object>();

		String team="";
		team = vAlHBscDAO.selectTeamByBSC(warningTp,operator);
		data.put("value", team);

		return data;

	}

	@RequestMapping("getSystemByVendor")
	public @ResponseBody
	List<String> getSystemByVendor(@RequestParam String team) {
		List<String> str = vAlHBscDAO.getSystemByVendor(team);
		return str;
	}

	@RequestMapping("getBscByVendor")
	public @ResponseBody
	List<String> getBscByVendor(@RequestParam String team) {
		List<String> str = vAlHBscDAO.getBscByVendor(team);
		return str;
	}

	@RequestMapping("getRncByVendor")
	public @ResponseBody
	List<String> getRncByVendor(@RequestParam String team) {
		List<String> str = vAlHRncDAO.getRncByVendor(team);
		return str;
	}


	@RequestMapping("districtCodeList")
	public @ResponseBody
	List<HProvinceFb> districtCodeList(@RequestParam String provinceCode) {
		List<HProvinceFb> str = hProvinceFbDAO.getDistrictCodeList(provinceCode);
		return str;
	}

	@RequestMapping("getAllArea")
	public @ResponseBody
	List<String> getAllArea(@RequestParam String term) {
		return cableET4MasterDAO.getAllAreas(term);
	}

	@RequestMapping("getAllExchange")
	public @ResponseBody
	List<String> getAllExchange(@RequestParam String term) {
		return cableET4MasterDAO.getAllExchanges(term);
	}

	@RequestMapping("getAllCiruit")
	public @ResponseBody
	List<String> getAllCiruit(@RequestParam String term) {
		return cableET4directionDAO.getAllCiruits(term);
	}

	@RequestMapping("getAllAreas")
	public @ResponseBody
	List<String> getAllAreas(@RequestParam String term) {
		return cableet4tlDao.getAllAreas(term);
	}

	@RequestMapping("getAllDirection")
	public @ResponseBody
	List<String> getAllDirection(@RequestParam String term) {
		return cableet4tlDao.getAllDirections(term);
	}

	@RequestMapping("getAllSystem")
	public @ResponseBody
	List<String> getAllSystem(@RequestParam String term) {
		return cableET4MgwDao.getAllSystems(term);
	}

	/* VMSC6 ALARM . Author: VanAnhCT. Date: 30/08/2013*/
	@RequestMapping("getAlarmLogAtShift")
	public @ResponseBody
	List<RAlarmLog> getAlarmLogAtShift(@RequestParam String netWork,@RequestParam String day,@RequestParam String alarmType,@RequestParam String statusKT,@RequestParam String bscidTK,@RequestParam String cellidTK) {
		if (cellidTK!=null && !cellidTK.equals(""))
			cellidTK = cellidTK.replace(" ", ",");
		if (bscidTK!=null && !bscidTK.equals(""))
			bscidTK = bscidTK.replace(" ", ",");
		List<RAlarmLog> list =  new ArrayList<RAlarmLog>();
		try
		{
			list = rAlarmLogDAO.getAlarmLogAtShift(netWork,day,alarmType,statusKT,bscidTK,cellidTK);
		}
		catch(Exception exp)
		{

		}
		return list ;
	}
	//update ca truc xu ly cho canh bao
	@RequestMapping("updateShiftID_AlLog")
	public @ResponseBody
	String updateShiftID_AlLog(@RequestParam String id,@RequestParam String shiftId,@RequestParam String netWork) {
		rAlarmLogDAO.updateShiftID(netWork,id,shiftId);
		return "1";

	}

	//Lay danh sach getAlarmMappingName
	@RequestMapping("getAlarmMappingName")
	public @ResponseBody 
	List<CConfigAlarmType> getAlarmMappingName(@RequestParam String vendor,@RequestParam String alarmType,@RequestParam String network) {
		List<CConfigAlarmType> alarmList = new ArrayList<CConfigAlarmType>();
		alarmList = cConfigAlarmTypeDAO.getAlarmMappingName(vendor, alarmType,network);
		return alarmList;
	}

	//Lay danh sach getAlarmName
	@RequestMapping("getAlarmName")
	public @ResponseBody 
	List<CConfigAlarmType> getAlarmName(@RequestParam String vendor,@RequestParam String alarmType,@RequestParam String network) {
		List<CConfigAlarmType> alarmList = new ArrayList<CConfigAlarmType>();
		alarmList = cConfigAlarmTypeDAO.getAlarmName(vendor, alarmType,network);
		return alarmList;
	}
	//Lay danh sach alarm type	
	@RequestMapping("getAlarmType")
	public @ResponseBody 
	List<CConfigAlarmType> getAlarmType() {
		List<CConfigAlarmType> alarmList = new ArrayList<CConfigAlarmType>();
		alarmList = cConfigAlarmTypeDAO.getAlarmType(null,null);
		return alarmList;

	}
	// Lay danh sach vendor
	@RequestMapping("getVendor")
	public @ResponseBody 
	List<SYS_PARAMETER> getVendor() {
		List<SYS_PARAMETER> alarmList = new ArrayList<SYS_PARAMETER>();
		alarmList = sysParameterDao.getVendorList();

		return alarmList;
	}
	@RequestMapping("getCausebySys")
	public @ResponseBody 
	List<SYS_PARAMETER> getCausebySys() {
		List<SYS_PARAMETER> alarmList = new ArrayList<SYS_PARAMETER>();
		alarmList = sysParameterDao.getCausebySystem();
		return alarmList;

	}



	@RequestMapping("getSeverity")
	public @ResponseBody 
	List<SYS_PARAMETER> getSeverity() {
		List<SYS_PARAMETER> alarmList = new ArrayList<SYS_PARAMETER>();
		alarmList = sysParameterDao.getSeverityList();
		return alarmList;

	}

	@RequestMapping("getStatusViewList")
	public @ResponseBody 
	List<SYS_PARAMETER> getStatusViewList() {
		List<SYS_PARAMETER> alarmList = new ArrayList<SYS_PARAMETER>();
		alarmList = sysParameterDao.getStatusViewList();
		return alarmList;

	}

	@RequestMapping("getAckStatusList")
	public @ResponseBody 
	List<SYS_PARAMETER> getAckStatusList() {
		List<SYS_PARAMETER> alarmList = new ArrayList<SYS_PARAMETER>();
		alarmList = sysParameterDao.getAckStatusList();
		return alarmList;

	}

	@RequestMapping("getStatusFinishList")
	public @ResponseBody 
	List<SYS_PARAMETER> getStatusFinishList() {
		List<SYS_PARAMETER> alarmList = new ArrayList<SYS_PARAMETER>();
		alarmList = sysParameterDao.getStatusFinish();
		return alarmList;

	}

	@RequestMapping("getProvinceList")
	public @ResponseBody
	List<HProvinceFb> getProvinceList(String region) {
		List<HProvinceFb> str = hProvinceFbDAO.getProvinceByRegion(region);
		return str;
	}

	@RequestMapping("getNetworkList")
	public @ResponseBody 
	List<SYS_PARAMETER> getNetworkList() {
		List<SYS_PARAMETER> alarmList = new ArrayList<SYS_PARAMETER>();
		alarmList = sysParameterDao.getSPByCodeAndName("NETWORK_TYPE",null);
		return alarmList;

	}

	//lay danh sach cac alarmtype co mornitor='Y' and enable='Y'
	@RequestMapping("getAlarmTypeAtShift")
	public @ResponseBody 
	List<CConfigAlarmType> getAlarmTypeAtShift(String network) {
		List<CConfigAlarmType> alarmList = new ArrayList<CConfigAlarmType>();
		alarmList = cConfigAlarmTypeDAO.getAlarmType(network,"Y");
		return alarmList;

	}

	//lay danh sach cac loai su co xay ra trong ca truc
	@RequestMapping("getWarningType")
	public @ResponseBody 
	List<SYS_PARAMETER> getWarningType() {
		List<SYS_PARAMETER> alarmList = new ArrayList<SYS_PARAMETER>();
		alarmList = sysParameterDao.getWarningType();
		return alarmList;

	}

	//lay danh sach cac loai su co xay ra trong ca truc
	@RequestMapping("getBrandName")
	public @ResponseBody 
	List<SYS_PARAMETER> getBrandName() {
		List<SYS_PARAMETER> alarmList = new ArrayList<SYS_PARAMETER>();
		alarmList = sysParameterDao.getBrandName();
		return alarmList;

	}


	//lay danh sach cac nhom user nhan tin nhan sms ve su co
	@RequestMapping("getGroupSMS")
	public @ResponseBody 
	List<SysDefineSmsEmail> getGroupSMS() {
		List<SysDefineSmsEmail> alarmList = new ArrayList<SysDefineSmsEmail>();
		alarmList = sysDefineSmsEmailDAO.getListAll("SMS", null, "S");
		/*String groupArray="var groupList = new Array(";
		String cn="";
		for (SysDefineSmsEmail sys : alarmList) {
			groupArray = groupArray + cn +"\""+sys.getGroupName()+"\"";
			cn=",";
		}
		groupArray = groupArray+");";*/

		return alarmList;

	}

	//lay danh sach bsc theo network username
	@RequestMapping("getBSCByNetwork")
	public @ResponseBody 
	List<String> getBSCByNetwork(String network, String username, String vendor ) {
		List<String> bscList = sysUserEquipmentNameDAO.getBSCList(network,username,vendor);
		return bscList;
	}

	//lay danh sach district theo  username, province, district
	@RequestMapping("getDistrictList")
	public @ResponseBody 
	List<String> getDistrictList(String province, String username, String team ) {
		List<String> districtList = sysUserAreaDAO.getDistrictList(province,team,username);
		return districtList;
	}

	//lay danh sach team theo  username, province
	@RequestMapping("getSubTeamList")
	public @ResponseBody 
	List<String> getSubTeamList(String province, String username, String team ) {
		List<String> teamList = sysUserAreaDAO.getSubTeamList(province,username);
		return teamList;
	}

	//lay danh sach ca truc
	@RequestMapping("getShift")
	public @ResponseBody 
	List<SYS_PARAMETER> getShift() {
		List<SYS_PARAMETER> caList = new ArrayList<SYS_PARAMETER>();
		caList = sysParameterDao.getSysParameterByCode("CA_TRUC_NAME");
		return  caList;
	}

	//lay danh sach CELL theo network username
	@RequestMapping("getSiteByNetwork")
	public @ResponseBody 
	List<String> getSiteByNetwork(String network, String bscid, String district, String username) {
		List<String> cellList = sysUserEquipmentNameDAO.getCellList(network,bscid,district,username);
		return cellList;
	}
	
	@RequestMapping("getSiteList")
	public @ResponseBody 
	List<String> getSiteList() {
		return sysUserEquipmentNameDAO.getSiteList(null, null, null,null);
	}

	//Lay danh sach lien he ten/sdt
	@RequestMapping("getContactUser")
	public @ResponseBody 
	List<String> getContactUser() {
		List<String> contactList = sysUsersDao.getContactUser(null);
		return contactList;
	}

	//Lay danh sach type email master
	@RequestMapping("getTypeEmailHourList")
	public @ResponseBody 
	List<SYS_PARAMETER> getTypeEmailHourList() {
		List<SYS_PARAMETER> dataList = new ArrayList<SYS_PARAMETER>();
		dataList = sysParameterDao.getTypeEmailHourList();

		return dataList;
	}
	@RequestMapping("checkIsdn")
	public @ResponseBody
	Map<String, Object>  checkIsdn(@RequestParam String isdn) {
		String kt="";
		String con="";

		String[] groupList = isdn.split(",");
		for (String group : groupList) {

			if (group!=null && !group.equals(""))
			{
				System.out.println("send nhom");
				List<SysUsers> userList = new ArrayList<SysUsers>();
				userList = sysUsersDao.getUserByGroupSMS(group.trim());
				if (userList.size()>0)
				{
					for (SysUsers user : userList) {
						System.out.println("phone:"+user.getPhone());
						if (user.getPhone()!=null && !user.getPhone().equals(""))
						{
							/*Integer count= smsContentsDAO.checkIsdn(user.getPhone());
							if (count!=null && count==0)
							{*/
								kt = kt+con+user.getPhone();
								con=",";
							/*}*/
						}
					}
				}
				else
				{
					System.out.println("send sdt");
					/*Integer count= smsContentsDAO.checkIsdn(group.trim());
					if (count!=null && count==0)
					{*/
						kt = kt+con+group.trim();
						con=",";
					/*}*/
				}
			}
		}
		if (kt.equals(""))
		{
			kt="1";
		}
		//return kt;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("value", kt);
		return data;
	}
	// Lay thong tin ca truc theo ngay truc va ca truc

	@RequestMapping("getNextShift")
	public @ResponseBody
	Map<String, Object> getNextShift(@RequestParam String ngaytruc,@RequestParam String catruc,@RequestParam String region) {

		String catruong="";
		String cavien="";
		String uctt="";
		List<AlWorkCalendar> nextShift = alWorkCalendarDAO.getShift(ngaytruc,catruc,region);
		if (nextShift.size()>0)
		{
			String con1="";
			String con2="";
			String con3="";
			for (AlWorkCalendar alWorkCalendar : nextShift) {
				String funtion=alWorkCalendar.getFunction().toLowerCase();

				if (funtion.contains("trưởng ca")&&!catruong.contains(alWorkCalendar.getEmail()))
				{
					catruong+= con1+ alWorkCalendar.getEmail();
					con1=",";
				}
				/*else if (funtion.contains("ca viên")&&!nhanCaVien.contains(alWorkCalendar.getEmail()))*/
				else if (!cavien.contains(alWorkCalendar.getEmail()))
				{
					cavien+= con2+ alWorkCalendar.getEmail();
					con2=",";
				}
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("catruong", catruong);
		data.put("cavien", cavien);

		return data;

	}


	//lay ten cong viec dung de dinh nghia noi dung trong ca truc
	@RequestMapping("getTenCongViec")
	public @ResponseBody 
	List<W_WORKING_TYPES> getTenCongViec() {
		List<W_WORKING_TYPES> dataList = new ArrayList<W_WORKING_TYPES>();
		dataList = working_typesDao.getWorkingNameShift();
		return  dataList;
	}
	//Lay danh sach type email master
	@RequestMapping("getMailLevel")
	public @ResponseBody 
	List<SYS_PARAMETER> getMailLevel() {
		List<SYS_PARAMETER> dataList = new ArrayList<SYS_PARAMETER>();
		dataList = sysParameterDao.getMailLevel();

		return dataList;
	}   

	// Lay danh sach user kem so dien thoai

	@RequestMapping("getUserSMS")
	public @ResponseBody 
	List<SysUsers> getUserSMS(String maPhong) {
		List<SysUsers> dataList = new ArrayList<SysUsers>();
		dataList = sysUsersDao.getUserAllByMaPhong(maPhong,null);
		return dataList;
	}  

	// Lay danh sach user kem so dien thoai

	@RequestMapping("getEnable")
	public @ResponseBody 
	List<SYS_PARAMETER> getEnable() {
		List<SYS_PARAMETER> dataList = new ArrayList<SYS_PARAMETER>();
		SYS_PARAMETER sys1 = new SYS_PARAMETER();
		sys1.setName("Y");
		sys1.setValue("Được sử dụng");
		dataList.add(sys1);

		SYS_PARAMETER sys2 = new SYS_PARAMETER();
		sys2.setName("N");
		sys2.setValue("Không sử dụng");
		dataList.add(sys2);

		return dataList;
	} 
	// Danh sach phong ban 
	@RequestMapping("getDeptListAlarm")
	public @ResponseBody
	List<MDepartment> getDeptListAlarm() {
		return mDepartmentDao.getDepartmentForShiftList("1");

	}

	// Danh sach to nhom ko phan quyen quan ly thuoc user nao.
	@RequestMapping("getTeamListAlarm")
	public @ResponseBody
	List<String> getTeamListAlarm(String dept) {
		List<MDepartment> teamList = mDepartmentDao.loadTeamByDepartment(dept);
		List<String> teamL = new ArrayList<String>();
		for (MDepartment dis : teamList) {
			teamL.add(dis.getDeptValue());

		}
		return teamL;

	}
	//danh sach nhom theo dai, to nhom

	@RequestMapping("getGroupListAlarm")
	public @ResponseBody
	List<String> getGroupListAlarm(String dept,String team) {
		List<MDepartment> teamList = mDepartmentDao.getGroupByDeptTeam(dept,team);
		List<String> teamL = new ArrayList<String>();
		for (MDepartment dis : teamList) {
			teamL.add(dis.getDeptName());

		}
		return teamL;

	}
	@RequestMapping("getUserByMaPhong")
	public @ResponseBody
	List<SysUsers> getUserByMaPhong(String deptCode) {
		List<SysUsers> fullNameList = sysUsersDao.getUserByMaPhong(deptCode);
		return fullNameList;

	}

	//Lay danh sach trang thai cong viec
	@RequestMapping("getStatusWork")
	public @ResponseBody 
	List<SYS_PARAMETER> getStatusWork() {
		List<SYS_PARAMETER> dataList = new ArrayList<SYS_PARAMETER>();
		dataList = sysParameterDao.getSysParameterByCode("TINH_TRANG_WORKS_MANA");
		return dataList;
	}   


	// Danh sach to nhom ko phan quyen quan ly thuoc user nao.
	@RequestMapping("getUserByDeparmentTeam")
	public @ResponseBody
	List<String> getUserByDeparmentTeam(String dept,String team) {
		List<SysUsers> userList = new ArrayList<SysUsers>();
		userList = sysUsersDao.getUserAllByMaPhong(dept,team);

		List<String> userL = new ArrayList<String>();
		for (SysUsers dis : userList) {
			userL.add(dis.getUsername());

		}
		return userL;

	}


	// Danh sach to nhom ko phan quyen quan ly thuoc user nao.
	@RequestMapping("getTeamListAlarmFull")
	public @ResponseBody
	List<MDepartment> getTeamListAlarmFull(String dept) {
		List<MDepartment> teamList = mDepartmentDao.loadTeamByDepartment(dept);
		return teamList;

	}

	//lay user co chuc vu ngang bang hoac be hon nguoi giao viec
	@RequestMapping("getUserForWork")
	public @ResponseBody
	List<SysUsers> getUserForWork(String deptCode,String diliver) {
		List<SysUsers> fullNameList = sysUsersDao.getUserForWork(deptCode,diliver);
		return fullNameList;

	}

	// Danh sach đối tác truyền dẫn từ DB truyền dẫn. 02/10/2014. AnhCTV
	@RequestMapping("getLLProvider")
	public @ResponseBody
	List<SYS_PARAMETER>  getLLProvider() {

		List<SYS_PARAMETER> providerList = new ArrayList<SYS_PARAMETER>();
		providerList = sysParameterDao.getLLProvider();

		return providerList;

	}

	// Danh sach đối tác truyền dẫn từ DB truyền dẫn. 02/10/2014. AnhCTV
	@RequestMapping("getLeaseLine")
	public @ResponseBody
	List<SYS_PARAMETER>  getLeaseLine(String llNode, String llProvider) {
		List<SYS_PARAMETER> leaseLineList = new ArrayList<SYS_PARAMETER>();
		leaseLineList = sysParameterDao.getLeaseLine(llNode,llProvider);

		return leaseLineList;

	}
	// Danh sach khu vu. 03/09/2015. AnhCTV
	@RequestMapping("getRegionList")
	public @ResponseBody
	List<SYS_PARAMETER>  getRegionList() {
		List<SYS_PARAMETER> regionList = new ArrayList<SYS_PARAMETER>();
		regionList = sysParameterDao.getRegionList();

		return regionList;

	}

	@RequestMapping("getStatusUCTT")
	public @ResponseBody
	List<SYS_PARAMETER>  getStatusUCTT() {
		List<SYS_PARAMETER> statusUCTTList = new ArrayList<SYS_PARAMETER>();
		statusUCTTList = sysParameterDao.getStatusUCTT();

		return statusUCTTList;

	}

	@RequestMapping("getStandardName")
	public @ResponseBody
	List<SysStandardizedError>  getStandardName(String vendor, String network) {
		List<SysStandardizedError> statusUCTTList = new ArrayList<SysStandardizedError>();
		statusUCTTList = sysStandardizedErrorDao.getStandardName(vendor, network);

		return statusUCTTList;

	}
	//Lay thong tin thoi gian backup acquy moi nhat
	@RequestMapping("getBaterrySite")
	public @ResponseBody
	Integer  getBaterrySite(String site,String network) {
		return vAlHCellDao.getBaterrySite(site,network);
	}

	@RequestMapping("getFBHandleMethodList")
	public @ResponseBody
	List<FbProcessFilter>  getFBHandleMethodList(String fbType, String networkType) {
		FbProcessFilter filter = new FbProcessFilter();
		filter.setFbType(fbType);
		filter.setNetworkType(networkType);

		List<FbProcessFilter> handleList = new ArrayList<FbProcessFilter>();
		handleList = fbProcessDAO.getProcessList(filter,null, null);	

		return handleList;

	}

	@RequestMapping("getFBTypeList")
	public @ResponseBody
	List<FbType>  getFBTypeList(String vendor, String network) {
		List<FbType> loaiPAList = FbTypeDAO.getFBTypeList();
		return loaiPAList;

	}

	@RequestMapping("getStatusFBList")
	public @ResponseBody
	List<SYS_PARAMETER>  getStatusFBList(String vendor, String network) {
		List<SYS_PARAMETER> tranThaiList = fbProcessDAO.getQLTTFBList();
		//sysParameterDao.getSysParameterByCode("STATUS_OF_CENTRAL_FEEDBACK");
		return tranThaiList;

	}
	
	@RequestMapping("getSiteByRegion")
	public @ResponseBody
	List<String>  getSiteByRegion(String region){
		if (region.equals("MĐ")) {
			region = "MD";
		}
		List<VDyIpbbLink> directionList = new ArrayList<VDyIpbbLink>();
		if (region != null && region != "") {
			directionList = vDyIpbbLinkDao.getDirectionByRegion(region);
		}
		List<String> siteList = new ArrayList<String>();
		for(VDyIpbbLink direct : directionList) {
			int start = 0;
			int end = 0;
			String str = direct.getDirection();
			while(str.charAt(start) != '-' && str.charAt(start) != '_') {
				start++;
			}
			end = start + 1;
			while(str.charAt(end) != '-' && str.charAt(end) != '_') {
				end++;
			}
			if (!siteList.contains(str.substring(start+1, end))) {
				siteList.add(str.substring(start+1, end));
			}
		}
		return siteList;

	}
	
	 //lay danh sach cac nhom user nhan tin nhan sms ve su co
 	 @RequestMapping("getGroupEmail")
 	 	public @ResponseBody 
 	 	List<SysDefineSmsEmail> getGroupEmail() {
 		List<SysDefineSmsEmail> alarmList = new ArrayList<SysDefineSmsEmail>();
 		alarmList = sysDefineSmsEmailDAO.getListAll("MAIL", null, "S");
 		return alarmList;
 	 	 
 	   	}
 	 
 	//lay danh sach district FB theo province, region
 	@RequestMapping("getDistrictFBList")
 	public @ResponseBody 
 	List<String> getDistrictFBList(String region,String province ) {
 		List<HProvinceFb> districtOList = hProvinceFbDAO.getDistrictByRegionProvine(region,province);
 		List<String> districtList = new ArrayList<String>();
 		for (HProvinceFb dis : districtOList) {
			districtList.add(dis.getDistrict());
		}
 		return districtList;
 	}
 	
 	//lay danh sach team FB theo dept
 	 	@RequestMapping("getTeamFbList")
 	 	public @ResponseBody 
 	 	List<String> getTeamFbList(String dept ) {
 	 		List<HProvinceFb> teamOList = hProvinceFbDAO.getTeamFbList(dept);
	 		List<String> teamList = new ArrayList<String>();
 	 		for (HProvinceFb dis : teamOList) {
 	 			teamList.add(dis.getTeam());
 			}
 	 		return teamList;
 	 	}
 	 	
 	 	@RequestMapping("getDSTinh")
 		public @ResponseBody
 		List<HProvincesCode> getDSTinh(String region) {
 			List<HProvincesCode> str = hProvincesCodeDAO.getAllProvinceByRegion(region);
 			return str;
 		}
 	 	
 	 	@RequestMapping("getParameterByCode")
 		public @ResponseBody
 		List<SYS_PARAMETER>  getParameterByCode(String code) {
 			List<SYS_PARAMETER> dataList = sysParameterDao.getSysParameterByCode(code);
 			return dataList;
 		}
 		
 		@RequestMapping("getDonViThuHuong")
 		public @ResponseBody
 		List<QldnDonViThuHuong>  getDonViThuHuong() {
 			List<QldnDonViThuHuong> dataList = qldnDonViThuHuongDao.getDonViThuHuong();
 			return dataList;
 		}
 		// lay danh sach thong tin tram chua duoc thanh toan
 		@RequestMapping("getDSTramTTDien")
 		public @ResponseBody
 		List<QldnThongTinTram>  getDSTramTTDien(String trangThai,String tinh,String huyen,String tgttTqTK,String thangQuyTtTK,String namTtTK,String username,String type) {
 			List<QldnThongTinTram> dataList = qldnThongTinTramDao.getDSTramTTDien(trangThai,tinh,huyen,tgttTqTK,thangQuyTtTK,namTtTK,username,type);
 			return dataList;
 		}
 		
 		//lay thong tin chi tiet thong tin tram thanh toan dien
 		@RequestMapping("getTramTTDien")
 		public @ResponseBody
 		QldnTramTTDien  getTramTTDien(String idTram,String tgttTqTK, String thangQuyTtTK, String namTtTK) {
 			QldnTramTTDien dataList = qldnTramTTDienDAO.getTramTTDien(idTram,tgttTqTK,thangQuyTtTK,namTtTK);
 			return dataList;
 		}
 		
 		@RequestMapping("getCellIbc")
 		public @ResponseBody List<String> getCellIbc(@RequestParam(required = true) String network) {		
 			return vRpDyIbcDAO.getCellIbc(network);
 		}
 		
 		@RequestMapping("getDistrictByProvince")
 		public @ResponseBody
 			List<HProvincesCode> getDistrictByProvince(String region,String province) {
 			List<HProvincesCode> str = hProvincesCodeDAO.getDistrictByProvince(region,province);
 			return str;
 		}
 	 	
 	
}
