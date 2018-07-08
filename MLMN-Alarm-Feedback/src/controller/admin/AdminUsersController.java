package controller.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import vo.CConfigAlarmType;
import vo.CHighlightConfigs;
import vo.HBsc;
import vo.HProvincesCode;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysBlacklist;
import vo.SysGroupUser;
import vo.SysResponsibilities;
import vo.SysUserArea;
import vo.SysUserEquipmentName;
import vo.SysUserRoles;
import vo.SysUsers;
import vo.SysUsersCogencyLevel;
import vo.alarm.utils.Helper;
import vo.alarm.utils.UploadTools;

import controller.BaseController;
import dao.CConfigAlarmTypeDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.HBscDAO;
import dao.HProvincesCodeDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysBlacklistDAO;
import dao.SysGroupUserDAO;
import dao.SysResponsibilitiesDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUserRolesDAO;
import dao.SysUsersCogencyLevelDAO;
import dao.SysUsersDAO;

/**
 * Function        : Quan ly nguoi dung
 * Created By      : BUIQUANG
 * Create Date     :
 * Modified By     : 
 * Modify Date     : 26/11/2013
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/admin/user/*")
public class AdminUsersController extends BaseController{
	
	@Autowired
	private SysUsersDAO sysUsersDao;
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	@Autowired
	private SysGroupUserDAO sysGroupUserDao;
	@Autowired
	private SysUserRolesDAO sysUserRolesDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private SysUsersCogencyLevelDAO sysUsersCogencyLevelDao;
	@Autowired
	private SysResponsibilitiesDAO sysResponsibilitiesDao;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	@Autowired
	private HBscDAO hBscDao;
	@Autowired
	private SysUserEquipmentNameDAO sysUserEquipmentNameDAO;
	@Autowired
	private CConfigAlarmTypeDAO cConfigAlarmTypeDAO;
	@Autowired
	private SysBlacklistDAO sysBlacklistDAO;
	
	@ModelAttribute("highlight")
	public String highlight() {
		String highlight = "";
		List<CHighlightConfigs> highlightConfigList2 = cHighlightConfigsDAO.getByKey("NOT_NULL");
		if (highlightConfigList2.size()>0)
		{ 
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfigList2.get(0).getStyle()+"});";
		}
		return highlight;
	}
	
	@RequestMapping(value="danh-sach")
    public ModelAndView list(@ModelAttribute("filter") SysUsers filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
	    
		LoadPhongBanByUser(model, filter, null);
		try{
			List<SYS_PARAMETER> titleSystem = sysUsersDao.titleSysUsers(); //sysParameterDao.getSPByCodeAndName("TITLE_SYSTEM", "QUAN_LY_NGUOI_DUNG");
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
		catch(Exception e){}
		
		int order = 1;
		String column = "USERNAME";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		if(filter.getMaPhong() != null)
			filter.setMaPhong(filter.getMaPhong().trim());
		if(filter.getUsername() != null)
			filter.setUsername(filter.getUsername().trim());
		if(filter.getEmail() != null)
			filter.setEmail(filter.getEmail().trim());
		if(filter.getFullname() != null)
			filter.setFullname(filter.getFullname().trim());
		if(filter.getPhone() != null)
			filter.setPhone(filter.getPhone().trim());
		
		List<SysUsers> users = sysUsersDao.getUsersFilter(filter.getMaPhong(),filter.getUsername(),filter.getEmail(), filter.getPhone(), filter.getIsEnable(), filter.getRolesAddUsers(), filter.getFullname(), column, order ==1 ? "ASC" : "DESC");
		model.addAttribute("usersList",users);
		
		updateTrangThai_HoatDong();
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DSNguoiDung_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		model.addAttribute("maPhongCBB", filter.getMaPhong());
		model.addAttribute("quyenTaoNDCBB", filter.getRolesAddUsers());
		model.addAttribute("trangThaiCBB", filter.getIsEnable());
		
		return new ModelAndView("jspadmin/usersList");
    }
	
	private void updateTrangThai_HoatDong()
	{
		// Update trạng thái hoạt động của username	
		Date date = new Date();
		
		List<SysUsers> userList = sysUsersDao.selectSysUsers();
		for(int i=0;i<userList.size();i++)
		{
			if(userList.get(i).getExpired() == null)
			{
				if(userList.get(i).getActiveDate() != null && userList.get(i).getActiveDate().compareTo(date) > 0)
				{
					updateIsEnable(userList.get(i).getUsername());
				}
			}
			else
			{
				if( userList.get(i).getExpired().compareTo(date) < 0)
				{
					updateIsEnable(userList.get(i).getUsername());
				}
				else
				{
					if(userList.get(i).getActiveDate() != null && userList.get(i).getActiveDate().compareTo(date) > 0)
					{
						updateIsEnable(userList.get(i).getUsername());
					}
				}
			}
		}
	}
	
	private void updateIsEnable(String str)
	{
		SysUsers usersIsEnable = new SysUsers();
		usersIsEnable.setUsername(str);
		usersIsEnable.setIsEnable("N");
		sysUsersDao.updateIsEnable(usersIsEnable);
	}
	
	// Lay danh sach to theo phong ban
   	@RequestMapping("loadTeam")
   	public @ResponseBody 
   	List<MDepartment> loadTeam(@RequestParam(required = false) String maPhong, HttpServletRequest request, HttpServletResponse response) {
   		List<MDepartment> loadTeamList = mDepartmentDAO.loadTeamByDepartment(maPhong);
   		return loadTeamList;
   	}
	
   	// Lay danh sach nhom theo phong ban
   	@RequestMapping("loadSubTeam")
   	public @ResponseBody 
   	List<MDepartment> loadSubTeam(@RequestParam(required = false) String team, HttpServletRequest request, HttpServletResponse response) {
   		List<MDepartment> loadTeamList = mDepartmentDAO.loadTeamByDepartment(team);
   		return loadTeamList;
   	}
   	
	// Load dữ liệu
	private void LoadPhongBanByUser(ModelMap model, SysUsers users, String id)
	{
		// Danh sách quyền tạo ND
		List<SYS_PARAMETER> quyenTaoNDList = sysUsersDao.getQuyenTaoNDList(); //sysParameterDao.getSysParameterByCode("QUYEN_TAO_ND");
		model.addAttribute("quyenTaoNDList", quyenTaoNDList);
		
		// Trạng thái của người dùng
		List<SYS_PARAMETER> trangThaiList = sysUsersDao.getTrangThaiList(); //sysParameterDao.getSysParameterByCode("USERS_TRANG_THAI");
		model.addAttribute("trangThaiList", trangThaiList);
		
		// Danh sách phòng ban
		List<MDepartment> maPhongList = mDepartmentDAO.getDepartmentForShiftList("1");
		model.addAttribute("maPhongList", maPhongList);
		
		String dept = "";
		if(users.getMaPhong() != null && !users.getMaPhong().equals(""))
			dept = users.getMaPhong();
		else if(maPhongList.size() > 0)
			dept = maPhongList.get(0).getDeptValue();
		
		List<MDepartment> teamList = mDepartmentDAO.loadTeamByDepartment(dept);
		model.addAttribute("teamList", teamList);
		
		String team = "";
		if(users.getTeam() != null && !users.getTeam().equals(""))
			team = users.getTeam();
		else if(teamList.size() > 0)
			team = teamList.get(0).getDeptValue();
		if(team != null && !team.equals("")){
			List<MDepartment> subTeamList = mDepartmentDAO.loadTeamByDepartment(team);
			model.addAttribute("subTeamList", subTeamList);
		}
		
		// Thông tin người đăng nhập
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("userLogin", userLogin);
		
		// Nhom truy cap
		List<SysGroupUser> sysGroupUserList = sysGroupUserDao.selectSysGroupUsersList();
		model.addAttribute("sysGroupUserList", sysGroupUserList);
		
		// Danh sach hinh thuc dang nhap
		List<SYS_PARAMETER> formsLoginList = sysParameterDao.getFormsLogin() ;
		model.addAttribute("formsLoginList", formsLoginList);
		
		// Danh sach chuc danh
		List<SYS_PARAMETER>  positionList =sysParameterDao.getPositionList();
		model.addAttribute("positionList", positionList);
		
		if(id != null && id != "")
		{
			List<SysUsers> usersListByMaPhong = sysUsersDao.getUsersByMaPhongDontId(users.getMaPhong(), Integer.parseInt(id));
			model.addAttribute("usersListByMaPhong", usersListByMaPhong);
		}
		else{
			List<SysUsers> usersListByMaPhong = sysUsersDao.getUserByMaPhong(users.getMaPhong());
			model.addAttribute("usersListByMaPhong", usersListByMaPhong);
		}
		
		if(users.getGroupId() != null){
			String[] groupIdChecked = users.getGroupId().split(",");
			model.addAttribute("groupIdCBB", groupIdChecked);
		}
		
		if(users.getRegionRole() != null){
			String[] regionChecked = users.getRegionRole().split(",");
			model.addAttribute("regionCBB", regionChecked);
		}
		
		if(users.getCcEmail() != null){
			String[] ccEmailChecked = users.getCcEmail().split(",");
			model.addAttribute("ccEmailCBB", ccEmailChecked);
		}
		if(users.getCcSms() != null){
			String[] ccSmsChecked = users.getCcSms().split(",");
			model.addAttribute("ccSmsCBB", ccSmsChecked);
		}
		
	}
	
	private void UsersAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("UsersAddEdit", "Y");
		else
			model.addAttribute("UsersAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@ModelAttribute("usersform") SysUsers users, @RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session, WebRequest webRequest) {
		
		SysUsers user = (id == null) ? new SysUsers() : sysUsersDao.selectByID(id);
		model.addAttribute("usersform", user);
		
		LoadPhongBanByUser(model, user, id);
		UsersAddEdit(id, model);
		
		if(id == null) //Thêm mới
		{	

		}
		else // Sửa
		{
					
			if(user.getCcEmail() != null){
				String[] ccEmailChecked = user.getCcEmail().split(",");
				model.addAttribute("ccEmailCBB", ccEmailChecked);
			}
			if(user.getCcSms() != null){
				String[] ccSmsChecked = user.getCcSms().split(",");
				model.addAttribute("ccSmsCBB", ccSmsChecked);
			}
			model.addAttribute("loginByCBB", user.getLoginBy());
			model.addAttribute("maPhongCBB", user.getMaPhong());
			model.addAttribute("positionCBB", user.getPosition());
			model.addAttribute("teamCBB", user.getTeam());
			model.addAttribute("subTeamCBB", user.getSubTeam());
			model.addAttribute("quyenTaoNDCBB", user.getRolesAddUsers());
			model.addAttribute("trangThaiCBB", user.getIsEnable());
			
			List<SysUserRoles> groupRolesList = sysUserRolesDao.selectSysUserRolesByUsername(user.getUsername());
			String temp = "";
			String groupList = "";
			for(int i=0;i<groupRolesList.size();i++){
				groupList += temp + groupRolesList.get(i).getGroupId();
				temp = ",";
			}
			model.addAttribute("groupIdCBB", groupList);
			
			List<SysUserRoles> regoinRolesList = sysUserRolesDao.selectRegionRoleByUsername(user.getUsername());
			temp = "";
			String regionList = "";
			if (regoinRolesList.size()>0){
				for(int i=0;i<regoinRolesList.size();i++){
					regionList += temp + regoinRolesList.get(i).getRegionCode();
					temp = ",";
				}
			}
			model.addAttribute("regionCBB", regionList);
			
		}
		return "jspadmin/usersForm";
	}

	
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			 @ModelAttribute("usersform") @Valid SysUsers users, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String password = users.getPassword();
		//Ném lỗi
		if (result.hasErrors()) {
			
			LoadPhongBanByUser(model, users, id);
			model.addAttribute("loginByCBB", users.getLoginBy());
			model.addAttribute("maPhongCBB", users.getMaPhong());
			model.addAttribute("teamCBB", users.getTeam());
			model.addAttribute("subTeanCBB", users.getSubTeam());
			model.addAttribute("trangThaiCBB", users.getIsEnable());
			model.addAttribute("groupIdCBB", users.getGroupId());
			model.addAttribute("positionCBB", users.getPosition());
			model.addAttribute("regionCBB", users.getRegionRole());
			
			UsersAddEdit(id, model);
			
			if(result.hasFieldErrors("expired"))
				model.addAttribute("expiredError", "expiredError");
			if (result.hasFieldErrors("activeDate"))
				model.addAttribute("activeDateError", "activeDateError");
			/*if(result.hasFieldErrors("email"))
				model.addAttribute("emailError", "emailError");
			if(result.hasFieldErrors("phone"))
				model.addAttribute("phoneError", "phoneError");*/
			
            return "jspadmin/usersForm";
		}
		
		if(users.getReceivingEmail() == null || users.getReceivingEmail() == "")
		{
			users.setReceivingEmail("N");
		}
		
		if( users.getReceivingSms() == null || users.getReceivingSms() == "")
		{
			users.setReceivingSms("N");
		}
		
		if( users.getRolesAddUsers() == null || users.getRolesAddUsers() == "")
		{
			users.setRolesAddUsers("N");
		}
		/*if( groupId != null && !groupId.equals(""))
		{
			users.setGroupId(groupId);
		}
		if( regionRole != null && !regionRole.equals(""))
		{
			users.setRegionRole(regionRole);
		}*/
		if(id == "")
		{
			
			if(sysUsersDao.selectByPrimaryKey(users.getUsername()) == null)
			{
				if(sysUsersDao.getCheckSysUsersByEmail(users.getEmail(),null).size() > 0){
					saveMessageKey(request, "message.qLNguoiDung.tonTaiEmail");
					
					LoadPhongBanByUser(model, users, id);
					model.addAttribute("loginByCBB", users.getLoginBy());
					model.addAttribute("maPhongCBB", users.getMaPhong());
					model.addAttribute("teamCBB", users.getTeam());
					model.addAttribute("subTeanCBB", users.getSubTeam());
					model.addAttribute("trangThaiCBB", users.getIsEnable());
					model.addAttribute("groupIdCBB", users.getGroupId());
					model.addAttribute("positionCBB", users.getPosition());
					model.addAttribute("regionCBB", users.getRegionRole());
					
					UsersAddEdit(id, model);
					
		            return "jspadmin/usersForm";
				}
				
				PasswordEncoder encoder = new Md5PasswordEncoder();
				String hashedPass = encoder.encodePassword(users.getPassword(), null);
				users.setPassword(hashedPass);
				
				users.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				
				if(users.getGroupId() != null && users.getGroupId() != "")
				{
					sysUsersDao.insertSelective(users); // Them moi nguoi dung
					
					// Them moi nhom nguoi dung
					String[] groupIdChecked = users.getGroupId().split(",");
					for(int i=0;i<groupIdChecked.length;i++)
					{
						SysUserRoles record = new SysUserRoles();
						record.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
						record.setUserName(users.getUsername());
						record.setGroupId(Integer.parseInt(groupIdChecked[i]));
						sysUserRolesDao.insert(record); 
					
					}
					
					// Them moi hoac update user voi menu id vao bang sys_respon
					sysResponsibilitiesDao.insertSrByUsernameGroupId( users.getGroupId(), users.getUsername(), SecurityContextHolder.getContext().getAuthentication().getName());
				
					// Them moi quyen quan ly khu vuc
					if (users.getRegionRole()!=null  && users.getRegionRole() != ""){
							String createdBy=SecurityContextHolder.getContext().getAuthentication().getName();
							sysUserRolesDao.insertRegionRole(users.getUsername(),users.getRegionRole(),createdBy); 
					}
				}
				else
				{
					saveMessageKey(request, "message.admin.nhomTruyCapKoTonTai");
					users.setPassword(password);
					LoadPhongBanByUser(model, users, id);
					model.addAttribute("loginByCBB", users.getLoginBy());
					model.addAttribute("maPhongCBB", users.getMaPhong());
					model.addAttribute("teamCBB", users.getTeam());
					model.addAttribute("subTeanCBB", users.getSubTeam());
					model.addAttribute("trangThaiCBB", users.getIsEnable());
					model.addAttribute("groupIdCBB", users.getGroupId());
					model.addAttribute("positionCBB", users.getPosition());
					model.addAttribute("regionCBB", users.getRegionRole());
					
					UsersAddEdit(id, model);
					
		            return "jspadmin/usersForm";
				}
				
				saveMessageKey(request, "messsage.confirm.addsuccess");
				
				
			}
			else
			{
				saveMessageKey(request, "message.admin.taiKhoanTonTai");
				users.setPassword(password);
				LoadPhongBanByUser(model, users, id);
				model.addAttribute("loginByCBB", users.getLoginBy());
				model.addAttribute("maPhongCBB", users.getMaPhong());
				model.addAttribute("teamCBB", users.getTeam());
				model.addAttribute("subTeanCBB", users.getSubTeam());
				model.addAttribute("groupIdCBB", users.getGroupId());
				model.addAttribute("trangThaiCBB", users.getIsEnable());
				model.addAttribute("positionCBB", users.getPosition());
				model.addAttribute("regionCBB", users.getRegionRole());
				
				UsersAddEdit(id, model);
				
	            return "jspadmin/usersForm";
			}
			
		}
		else
		{
			users.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			if(sysUsersDao.getCheckSysUsersByEmail(users.getEmail(),id).size() > 0){
				saveMessageKey(request, "message.qLNguoiDung.tonTaiEmail");
				users.setPassword(password);
				LoadPhongBanByUser(model, users, id);
				model.addAttribute("loginByCBB", users.getLoginBy());
				model.addAttribute("maPhongCBB", users.getMaPhong());
				model.addAttribute("teamCBB", users.getTeam());
				model.addAttribute("subTeanCBB", users.getSubTeam());
				model.addAttribute("trangThaiCBB", users.getIsEnable());
				model.addAttribute("groupIdCBB", users.getGroupId());
				model.addAttribute("positionCBB", users.getPosition());
				model.addAttribute("regionCBB", users.getRegionRole());
				
				UsersAddEdit(id, model);
				
	            return "jspadmin/usersForm";
			}
			
			//Kiểm tra mật khẩu
			SysUsers kiemTraMatKhau = sysUsersDao.selectByPrimaryKey(users.getUsername());
			if(!users.getPassword().equals(kiemTraMatKhau.getPassword()))
			{
				PasswordEncoder encoder = new Md5PasswordEncoder();
				String hashedPass = encoder.encodePassword(users.getPassword(), null);
				users.setPassword(hashedPass);
			}
			
			//Sửa nhóm người dùng
			if(users.getGroupId() != null && users.getGroupId() != ""){
				
				String[] groupIdChecked = users.getGroupId().split(",");
				List<SysUserRoles> surList = sysUserRolesDao.selectSysUserRolesByUsername(users.getUsername());
				
				for(int i=0;i< surList.size();i++)
					sysUserRolesDao.deleteByPrimaryKey(surList.get(i).getId());
				
				for(int i=0;i< groupIdChecked.length; i++)
				{
					SysUserRoles record = new SysUserRoles();
					record.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
					record.setGroupId(Integer.parseInt(groupIdChecked[i]));
					record.setUserName(users.getUsername());
					
					sysUserRolesDao.insert(record);	
				}
				// Them moi hoac update user voi menu id vao bang sys_respon
				sysResponsibilitiesDao.insertSrByUsernameGroupId( users.getGroupId(), users.getUsername(), SecurityContextHolder.getContext().getAuthentication().getName());
				// Them moi hoac update quyen quan ly khu vuc cua user
				if (users.getRegionRole()!=null  && users.getRegionRole() != ""){
						String createdBy=SecurityContextHolder.getContext().getAuthentication().getName();
						sysUserRolesDao.insertRegionRole(users.getUsername(),users.getRegionRole(),createdBy); 
				}
				// Sua nguoi dung
				sysUsersDao.updateByPrimaryKey(users);
				if(users.getReceivingSms()!= null && users.getReceivingSms().equals("Y")){
					try{
						sysUsersDao.smsAddIsdn(users.getPhone(), users.getUsername());
					}catch(Exception e){}
				}
				saveMessageKey(request, "messsage.confirm.updatesuccess");
				
			}
			else
			{
				saveMessageKey(request, "message.admin.nhomTruyCapKoTonTai");
				users.setPassword(password);
				LoadPhongBanByUser(model, users, id);
				model.addAttribute("loginByCBB", users.getLoginBy());
				model.addAttribute("maPhongCBB", users.getMaPhong());
				model.addAttribute("teamCBB", users.getTeam());
				model.addAttribute("subTeanCBB", users.getSubTeam());
				model.addAttribute("groupIdCBB", users.getGroupId());
				model.addAttribute("trangThaiCBB", users.getIsEnable());
				model.addAttribute("positionCBB", users.getPosition());
				model.addAttribute("regionCBB", users.getRegionRole());
				
				UsersAddEdit(id, model);
				
	            return "jspadmin/usersForm";
			}
			
		}
		
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			SysUsers user = (id == null) ? new SysUsers() : sysUsersDao.selectByID(id);
			if(user.getUsername() != null){
				
				List<SysUsersCogencyLevel> userCLList = sysUsersCogencyLevelDao.loadCogencyLevelByUsername(user.getUsername());
				for(int i=0;i<userCLList.size();i++)
					sysUsersCogencyLevelDao.deleteByPrimaryKey(userCLList.get(i).getId());
				
				sysUserRolesDao.deleteByUsername(user.getUsername());
				sysResponsibilitiesDao.deleteSrByUsername(user.getUsername());
				sysUsersDao.deleteRoleByUsername(user.getUsername());
			}
			
			sysUsersDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			
			}
		
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "jspadmin/usersUpload";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());				
					importContent(sheetData, model, request);

			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		
		return "jspadmin/usersUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<SysUsers> successList = new ArrayList<SysUsers>();
		List<SysUsers> failedList = new ArrayList<SysUsers>();
		List<SysUsers> success = new ArrayList<SysUsers>();
		
		String username;
		String password;
		String fullname;
		String sex;
		String position;
		String phone;
		String email;
		String isEnable;
		String activeDate;
		String expired;
		String description;
		String maPhong;
		String team;
		String subTeam;
		String groupName;
		String regionRole;

		String status1 ="";
		String status2 ="";
		String status3 ="";
		String status4 ="";
		String status5 ="";
		String status6 ="";
		String status7="";
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 16) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		
        		return "jspadmin/usersUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		SysUsers sysUsers;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			sysUsers = new SysUsers();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=16; j++) {
     					data.add("");
     				}
        			username				= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			password				= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			fullname				= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			sex						= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			position				= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			phone					= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			email					= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			isEnable				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			activeDate				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			expired					= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			description				= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			maPhong					= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			team					= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			subTeam					= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			groupName				= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			regionRole				= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			// Kiem tra loi
        			if (username == null || fullname == null || phone == null || email == null || maPhong == null || groupName == null
        					|| (username != null && sysUsersDao.selectByPrimaryKey(username) != null)
						) {
						error = true;
						status1 = "&nbsp;- Thiếu thông tin nhập liệu bắt buộc hoặc tài khoản đã tồn tại<br>";
					}
        			if(email != null){
        				if(!email.contains("@")){
        						error = true;
        						status2 = "&nbsp;- Định dạng email không chính xác<br>";
        				}
        				
        				if(sysUsersDao.getCheckSysUsersByEmail(email,null).size() > 0){
        					error = true;
        					status4 = "&nbsp;- Email đã tồn tại trong hệ thống<br>";
        				}
        			}
        			if(groupName != null){
        				groupName= groupName.replaceAll(",", ";");
        				String[] groupList = groupName.trim().split(";");
        				for(int j=0;j<groupList.length;j++)
    					{
    						if(sysGroupUserDao.selectByGroupNameAndDeparmentName(null, groupList[j].trim()).size() == 0){
    							error = true;
    							status5 = "&nbsp;- Nhóm truy cập không đúng<br>";
    							break;
    						}
    					}
					}
        			if (regionRole!=null){
        				regionRole= regionRole.replaceAll(";", ",");
        				if(sysParameterDao.checkRegionExit(regionRole) == false){
    							error = true;
    							status7 = "&nbsp;- Không tồn tại khu vực<br>";
        				}
        			}
        			
        			try
	    			{
        				if(activeDate != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(activeDate);
		    				sysUsers.setActiveDate(new SimpleDateFormat("dd/MM/yyyy").parse(activeDate));
		    				System.out.println(simple);
	    				}	
        				if(expired != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(expired);
		    				sysUsers.setExpired(new SimpleDateFormat("dd/MM/yyyy").parse(expired));
		    				System.out.println(simple);
	    				}
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    				status6 = "&nbsp;- Định dạng dữ liệu ngày không chính xác<br>";
	    			}
        			
        			//---------------------------------------------------------------------------
        			sysUsers.setUsername(username);

        			PasswordEncoder encoder = new Md5PasswordEncoder();
        			String hashedPass = encoder.encodePassword(password, null);
        			sysUsers.setPassword(hashedPass);
 
        			sysUsers.setFullname(fullname);
        			if(sex != null){
        				if(sex.toString().toLowerCase().equals("nữ"))
        					sysUsers.setSex("F");
        				else if(sex.toString().toLowerCase().equals("nam"))
        					sysUsers.setSex("M");
        				sysUsers.setSexStr(sex);	
        			}
        			sysUsers.setPosition(position);
        			sysUsers.setPhone(phone);
        			sysUsers.setEmail(email);
        			if(isEnable != null){
        				if(isEnable.toString().toLowerCase().equals("đang hoạt động"))
        					sysUsers.setIsEnable("Y");
        				else if(isEnable.toString().toLowerCase().equals("không hoạt động"))
        					sysUsers.setIsEnable("N");
        				
        				sysUsers.setNameTrangThai(isEnable);
        			}
        			sysUsers.setDescription(description);
        			sysUsers.setMaPhong(maPhong);
        			sysUsers.setTeam(team);
        			sysUsers.setSubTeam(subTeam);
        			sysUsers.setGroupName(groupName);
        			sysUsers.setRegionRole(regionRole);
        			if (username == null && fullname == null && phone == null && email == null && maPhong == null && groupName == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(sysUsers);
    					} else  {
    						
    						successList.add(sysUsers);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (SysUsers record: successList) {
			try {
					record.setCreatedBy(createdBy);
					sysUsersDao.insertSelective(record);
					success.add(record);
					
					//Them nhom truy cap
					String[] groupList = record.getGroupName().trim().split(";");
					String temp = "";
					String groupId = "";
					for(int j=0;j<groupList.length;j++)
					{
						SysUserRoles recordRoles = new SysUserRoles();
						recordRoles.setCreatedBy(createdBy);
						recordRoles.setUserName(record.getUsername());
						List<SysGroupUser> sysGRList = sysGroupUserDao.selectByGroupNameAndDeparmentName(null, groupList[j].trim());
						if(sysGRList.size() > 0){
							recordRoles.setGroupId(sysGRList.get(0).getId());
							sysUserRolesDao.insert(recordRoles);
							groupId += temp + recordRoles.getGroupId();
							temp = ",";
						}
					}
					
					// Them moi hoac update user voi menu id vao bang sys_respon
					sysResponsibilitiesDao.insertSrByUsernameGroupId( groupId, record.getUsername(), createdBy);
					
					//Them quyen quan ly khu vuc
					if (record.getRegionRole()!=null  && record.getRegionRole() != ""){
						sysUserRolesDao.insertRegionRole(record.getUsername(),record.getRegionRole(),createdBy); 
					}
			} catch (Exception ex) {
					failedList.add(record);
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			//model.addAttribute("status", "Dữ liệu người dùng không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép, định dạng dữ liệu không chính xác, tên tài khoản đã tồn tại hoặc mã phòng và nhóm truy cập không đúng.");	// Upload lỗi
			model.addAttribute("status", "Dữ liệu người dùng không hợp lệ do:<br>" + status1+status2+status3+status4+status5+status6+status7);
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspadmin/usersUpload";
	}
	
	// Nhom truy cap
	@RequestMapping("sysGroupUserList")
	public @ResponseBody
	List<SysGroupUser> sysGroupUserList(@RequestParam(required = false) String team) {
		List<SysGroupUser> sysGroupUserList = sysGroupUserDao.selectSysGroupUsersList();
		
		return 	sysGroupUserList;
	}
	
	// SMS khac
	@RequestMapping("smsOtherList")
	public @ResponseBody
	List<SYS_PARAMETER> smsOtherList() {
		// Nhom 1:
		List<SYS_PARAMETER> smsOtherList = sysParameterDao.getSmsOtherForSysUsers();
		return 	smsOtherList;
	}
	
	@RequestMapping(value = "details", method = RequestMethod.GET)
	public String details(@ModelAttribute("usersform") SysUsers users, 
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String groupID,
			@RequestParam(required = false) String system, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session, WebRequest webRequest) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers user = (id == null) ? new SysUsers() : sysUsersDao.selectByID(id);
		model.addAttribute("usersform", user);
		
		// Nhom 1:
		model.addAttribute("ccSmsCBB", user.getCcSms());

		// Nhom 2:
		List<HProvincesCode> provincesCodeList = hProvincesCodeDAO.getProvincesCodeToRoles(user.getUsername(), null, null, null,null);
		model.addAttribute("provincesCodeList", provincesCodeList);
		
		List<SysUserArea> sysUserAreaListByUsername = sysUserAreaDAO.getUsersAreaByUsername(user.getUsername(), null, null, null,null);
		model.addAttribute("sysUserAreaListByUsername", sysUserAreaListByUsername);
		
		List<HBsc> neList = hBscDao.getAllBscByUsername(user.getUsername(),null,null,null,null);
		model.addAttribute("neList", neList);
		
		List<SysUserEquipmentName>  equipmentListByUsername= sysUserEquipmentNameDAO.getEquipmentByUsername(user.getUsername(),null,null,null,null);
		model.addAttribute("equipmentListByUsername", equipmentListByUsername);
		
		// Nhom 3:
		List<CConfigAlarmType> cConfigAlarmTypeList = cConfigAlarmTypeDAO.getAlarmTypeByUser(user.getUsername(),null,null,null);
		model.addAttribute("cConfigAlarmTypeList", cConfigAlarmTypeList);
		
		List<SYS_PARAMETER> sysParameterAlarmIdList= sysParameterDao.getSysParaForBlacklist(user.getUsername(),null,null,null);
		model.addAttribute("sysParameterAlarmIdList", sysParameterAlarmIdList);
		
		List<SysBlacklist> sysBlacklist = sysBlacklistDAO.getSysBlacklistByUsername( user.getUsername(),null,null,null);
		model.addAttribute("sysBlacklist", sysBlacklist);
		
		// Nhom 4:
		List<SysGroupUser> sysGroupUserList = sysGroupUserDao.getSguByUsername(user.getUsername());
		model.addAttribute("sysGroupUserList", sysGroupUserList);
		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("MENU_COLOR");
		model.addAttribute("highlight", Helper.highLightCost(highlightConfigList, "item1"));
		
		if(groupID != null){
			
			// Insert nhung ban ghi moi trong nhom dong thoi tra ve 1 danh sach nhung chuc nang phan quyen cho nguoi dung
			List<SysResponsibilities> sysResponsibilitiesList = sysResponsibilitiesDao.loadSysResponByGroup_User( user.getUsername(), groupID, system, createdBy);
			model.addAttribute("sysResponsibilitiesList", sysResponsibilitiesList);
		}
		model.addAttribute("groupID", groupID);
		model.addAttribute("system", system);
		return "jspadmin/usersDetails";
	}
	
	/**
	 * Nut save cua phan quyen cho nguoi dung
	 * @param id
	 * @param users
	 * @param result
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "details", method = RequestMethod.POST)
	public String onDetailsSubmit(@RequestParam(required = false) String id,
			@RequestParam(required = false) String groupID,
			@RequestParam(required = false) String system,
			@RequestParam(required = false) String checkedAreaList,
			@RequestParam(required = false) String uncheckedAreaList,
			@RequestParam(required = false) String checkedEquipmentList,
			@RequestParam(required = false) String uncheckedEquipmentList,
			@RequestParam(required = false) String checkedAlarmIdList,
			@RequestParam(required = false) String uncheckedAlarmIdList,
			@RequestParam(required = false) String checkedList,
			@RequestParam(required = false) String uncheckedList,
			 @ModelAttribute("usersform") @Valid SysUsers users, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String modifiedBy = SecurityContextHolder.getContext().getAuthentication().getName();
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		try{
			// Phan quyen nhom 1
			/*if(users.getReceivingEmail() == null)
				users.setReceivingEmail("N");*/
			if( users.getIsRoleSystem() == null)
				users.setIsRoleSystem("N");
			if( users.getIsSmsLeaseline() == null)
				users.setIsSmsLeaseline("N");
			if( users.getIsRoleManager() == null)
				users.setIsRoleManager("N");
			if( users.getReceivingSms() == null)
				users.setReceivingSms("N");
			else{
				try{
					sysUsersDao.smsAddIsdn(users.getPhone(), users.getUsername());
				}catch(Exception e){}
			}
			if( users.getRolesAddUsers() == null)
				users.setRolesAddUsers("N");
			users.setModifiedBy(modifiedBy);
			sysUsersDao.updateRolesForUsername(users);
			
			// Phan quyen nhom 2
			if(checkedAreaList != null && checkedAreaList != ""){
				String[] checkList = checkedAreaList.split(";");
				for(int i=0;i<checkList.length;i++)
				{
					String[] checkListDetails = checkList[i].split("-");
					sysUsersDao.updateSysUserArea(checkListDetails[0], "Y", checkListDetails[1]);
				}
				
			}
			if(uncheckedAreaList != null && uncheckedAreaList != ""){
				String[] checkList = uncheckedAreaList.split(";");
				for(int i=0;i<checkList.length;i++)
				{
					String[] checkListDetails = checkList[i].split("-");
					sysUsersDao.updateSysUserArea(checkListDetails[0], "N", checkListDetails[1]);
				}
				
			}
			
			if(checkedEquipmentList != null && checkedEquipmentList != ""){
				String[] checkList = checkedEquipmentList.split(";");
				for(int i=0;i<checkList.length;i++)
				{
					String[] checkListDetails = checkList[i].split("-");
					sysUserEquipmentNameDAO.updateSysUserEquipment(checkListDetails[0], "Y", checkListDetails[1]);
				}
				
			}
			if(uncheckedEquipmentList != null && uncheckedEquipmentList != ""){
				String[] checkList = uncheckedEquipmentList.split(";");
				for(int i=0;i<checkList.length;i++)
				{
					String[] checkListDetails = checkList[i].split("-");
					sysUserEquipmentNameDAO.updateSysUserEquipment(checkListDetails[0], "N", checkListDetails[1]);
				}
				
			}
			
			// Phan quyen nhom 3
			if(checkedAlarmIdList != null && checkedAlarmIdList != ""){
				String[] checkList = checkedAlarmIdList.split(";");
				for(int i=0;i<checkList.length;i++)
				{
					String[] checkListDetails = checkList[i].split("-");
					sysBlacklistDAO.updateSysBlackList(checkListDetails[0], "Y", "0");
				}
				
			}
			if(uncheckedAlarmIdList != null && uncheckedAlarmIdList != ""){
				String[] checkList = uncheckedAlarmIdList.split(";");
				for(int i=0;i<checkList.length;i++)
				{
					String[] checkListDetails = checkList[i].split("-");
					sysBlacklistDAO.updateSysBlackList(checkListDetails[0], "N", "0");
				}
				
			}
			
			// Phan quyen nhom 4
			sysResponsibilitiesDao.updateCheckedByKeyList(checkedList, uncheckedList);
			
			saveMessageKey(request, "pQuyenNhomNguoiDung.capQuyenSysUsers");
		}
		catch(Exception ex){
			saveMessageKey(request, "pQuyenNhomNguoiDung.capQuyenSysUsersError");
		}
		
		// Load du lieu
		
		//Nhom 1
		model.addAttribute("ccSmsCBB", users.getCcSms());
		
		//Nhom 2
		List<HProvincesCode> provincesCodeList = hProvincesCodeDAO.getProvincesCodeToRoles(users.getUsername(), null, null, null,null);
		model.addAttribute("provincesCodeList", provincesCodeList);
		
		List<SysUserArea> sysUserAreaListByUsername = sysUserAreaDAO.getUsersAreaByUsername(users.getUsername(),null,null,null,null);
		model.addAttribute("sysUserAreaListByUsername", sysUserAreaListByUsername);
		
		List<HBsc> neList = hBscDao.getAllBscByUsername(users.getUsername(),null,null,null,null);
		model.addAttribute("neList", neList);
		
		List<SysUserEquipmentName>  equipmentListByUsername= sysUserEquipmentNameDAO.getEquipmentByUsername(users.getUsername(),null,null,null,null);
		model.addAttribute("equipmentListByUsername", equipmentListByUsername);
		
		// Nhom 3:
		List<CConfigAlarmType> cConfigAlarmTypeList = cConfigAlarmTypeDAO.getAlarmTypeByUser(users.getUsername(),null,null,null);
		model.addAttribute("cConfigAlarmTypeList", cConfigAlarmTypeList);
		
		List<SYS_PARAMETER> sysParameterAlarmIdList= sysParameterDao.getSysParaForBlacklist(users.getUsername(),null,null,null);
		model.addAttribute("sysParameterAlarmIdList", sysParameterAlarmIdList);
		
		List<SysBlacklist> sysBlacklist = sysBlacklistDAO.getSysBlacklistByUsername( users.getUsername(),null,null,null);
		model.addAttribute("sysBlacklist", sysBlacklist);
		//Nhom 4
		List<SysGroupUser> sysGroupUserList = sysGroupUserDao.getSguByUsername(users.getUsername());
		model.addAttribute("sysGroupUserList", sysGroupUserList);
		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("MENU_COLOR");
		model.addAttribute("highlight", Helper.highLightCost(highlightConfigList, "item1"));
		if(groupID != null && groupID != ""){
			
			// Insert nhung ban ghi moi trong nhom dong thoi tra ve 1 danh sach nhung chuc nang phan quyen cho nguoi dung
			List<SysResponsibilities> sysResponsibilitiesList = sysResponsibilitiesDao.loadSysResponByGroup_User( users.getUsername(), groupID, system, createdBy);
			model.addAttribute("sysResponsibilitiesList", sysResponsibilitiesList);
			model.addAttribute("groupID", groupID);
		}
		
		
		return "jspadmin/usersDetails";
	}
	
	@RequestMapping("sysUserAreaListByUsername")
	public @ResponseBody
	List<SysUserArea> sysUserAreaListByUsername(@RequestParam(required = false) String username, 
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String code,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String addCheckedList,
			@RequestParam(required = false) String removeCheckedList, HttpServletRequest request) {
		if(addCheckedList != null && !addCheckedList.equals(""))
			sysUserAreaDAO.insertAndUsersAreaByUser( username, addCheckedList);
		if(removeCheckedList != null && !removeCheckedList.equals(""))
			sysUserAreaDAO.deleteSysUsersAreaByCheck(removeCheckedList);
		List<SysUserArea> sysUserAreaListByUsername = sysUserAreaDAO.getUsersAreaByUsername(username,province,district,code,region);
		return sysUserAreaListByUsername;
	}
	
	@RequestMapping("hProvincesCodeByUsername")
	public @ResponseBody
	List<HProvincesCode> hProvincesCodeByUsername(@RequestParam(required = false) String username, @RequestParam(required = false) String province, 
			@RequestParam(required = false) String district, @RequestParam(required = false) String code,@RequestParam(required = false) String region, HttpServletRequest request) {
		
		List<HProvincesCode> provincesCodeList = hProvincesCodeDAO.getProvincesCodeToRoles(username, province, district, code,region);
		return provincesCodeList;
	}
	
	@RequestMapping("equipmentListByUsername")
	public @ResponseBody
	List<SysUserEquipmentName> equipmentListByUsername(@RequestParam(required = false) String username, 
			@RequestParam(required = false) String neType,
			@RequestParam(required = false) String neName,
			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String addCheckedList,
			@RequestParam(required = false) String removeCheckedList, HttpServletRequest request) {
		if(addCheckedList != null && !addCheckedList.equals(""))
			sysUserEquipmentNameDAO.insertUsersEquipmentByUser( username, addCheckedList);
		if(removeCheckedList != null && !removeCheckedList.equals(""))
			sysUserEquipmentNameDAO.deleteSysUsersEquipment(removeCheckedList);
		List<SysUserEquipmentName>  equipmentListByUsername= sysUserEquipmentNameDAO.getEquipmentByUsername(username,neType,neName,vendor,region);
		return equipmentListByUsername;
	}
	
	@RequestMapping("bscByUsername")
	public @ResponseBody
	List<HBsc> bscByUsername(@RequestParam(required = false) String username,
			@RequestParam(required = false) String neType,
			@RequestParam(required = false) String neName,
			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String region,
			HttpServletRequest request) {
		List<HBsc> neList = hBscDao.getAllBscByUsername(username,neType,neName,vendor,region);
		return neList;
	}
	
	@RequestMapping("insertBlacklistByAlarmType")
	public @ResponseBody
	int insertBlacklistByAlarmType(@RequestParam(required = false) String username, 
			@RequestParam(required = false) String addCheckedList,
			@RequestParam(required = false) String removeCheckedList,
			@RequestParam(required = false) String addCheckedSysParaList, HttpServletRequest request) {
		try{
			if(addCheckedList != null && !addCheckedList.equals(""))
				sysBlacklistDAO.insertSysBlacklistByUser( username, addCheckedList);
			if(removeCheckedList != null && !removeCheckedList.equals(""))
				sysBlacklistDAO.deleteSysBlacklist(removeCheckedList);
			if(addCheckedSysParaList != null && !addCheckedSysParaList.equals("") )
				sysBlacklistDAO.insertBlacklistByParameter( username, addCheckedSysParaList);
		}
		catch(Exception e){}
		return 1;
	}
	
	@RequestMapping("alarmTypeListByUsername")
	public @ResponseBody
	List<CConfigAlarmType> alarmTypeListByUsername(@RequestParam(required = false) String username,
			@RequestParam(required = false) String alarmType,
			@RequestParam(required = false) String alarmId,
			@RequestParam(required = false) String alarmName, HttpServletRequest request) {
		List<CConfigAlarmType> cConfigAlarmTypeList = cConfigAlarmTypeDAO.getAlarmTypeByUser(username,alarmType,alarmId,alarmName);
		return cConfigAlarmTypeList;
	}
	
	@RequestMapping("blacklistByUsername")
	public @ResponseBody
	List<SysBlacklist> blacklistByUsername(@RequestParam(required = false) String username,
			@RequestParam(required = false) String alarmType,
			@RequestParam(required = false) String alarmId,
			@RequestParam(required = false) String alarmName, HttpServletRequest request) {
		List<SysBlacklist> sysBlacklist = sysBlacklistDAO.getSysBlacklistByUsername( username,alarmType,alarmId,alarmName);
		return sysBlacklist;
	}
	
	@RequestMapping("alarmIdSysParaByUsername")
	public @ResponseBody
	List<SYS_PARAMETER> alarmIdSysParaByUsername(@RequestParam(required = false) String username, 
			@RequestParam(required = false) String alarmType,
			@RequestParam(required = false) String alarmId,
			@RequestParam(required = false) String alarmName,
			 HttpServletRequest request) {
		List<SYS_PARAMETER> sysParameterAlarmIdList= sysParameterDao.getSysParaForBlacklist(username,alarmType,alarmId,alarmName);	
		return sysParameterAlarmIdList;
	}
	
	@RequestMapping(value = "copyRole", method = RequestMethod.GET)
	public String copyRole(@ModelAttribute("usersform") SysUsers users, 
			@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session, WebRequest webRequest) {
		
		SysUsers user = (id == null) ? new SysUsers() : sysUsersDao.selectByID(id);
		model.addAttribute("usersform", user);
		
		return "jspadmin/usersCopyRoles";
	}
	
	@RequestMapping(value = "copyRole", method = RequestMethod.POST)
	public String onCopyRoleSubmit(@RequestParam(required = false) String id,
			@RequestParam(required = false) String usernameNew,
			@RequestParam(required = false) String passwordNew,
			@RequestParam(required = false) String emailNew,
			@ModelAttribute("usersform") @Valid SysUsers users, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("passwordNew", passwordNew);
		model.addAttribute("usernameNew", usernameNew);
		model.addAttribute("emailNew", emailNew);
		if(usernameNew == null || usernameNew == ""){
			saveMessageKey(request, "message.qLNguoiDung.chuaNhapUsername");
			return "jspadmin/usersCopyRoles";
		}
		if(emailNew == null || emailNew == ""){
			saveMessageKey(request, "message.qLNguoiDung.chuaNhapEmail");
			return "jspadmin/usersCopyRoles";
		}else{
			/*if(emailNew.length() > 11){
				String emailExtend = emailNew.substring(emailNew.length()-11, emailNew.length());
				if(!emailExtend.toLowerCase().equals("@vms.com.vn"))
				{
					saveMessageKey(request, "Pattern.usersform.email");
					return "jspadmin/usersCopyRoles";
				}
			}
			else{
				saveMessageKey(request, "message.qLNguoiDung.emailKhongHopLe");
				return "jspadmin/usersCopyRoles";
			}*/
			if(emailNew != null && emailNew.toUpperCase().indexOf("@") < 0){
				saveMessageKey(request, "message.qLNguoiDung.emailKhongHopLe");
				return "jspadmin/usersCopyRoles";
			}
		}
		if(sysUsersDao.selectByPrimaryKey(usernameNew) == null)
		{
			if(sysUsersDao.getCheckSysUsersByEmail(emailNew,null).size() > 0){
				saveMessageKey(request, "message.qLNguoiDung.tonTaiEmail");
				return "jspadmin/usersCopyRoles";
			}
			else{
				try{
					PasswordEncoder encoder = new Md5PasswordEncoder();
					String hashedPass = encoder.encodePassword(passwordNew, null);
					passwordNew = hashedPass;
					
					sysUsersDao.copyRoleToUsernameNew( users.getUsername(), createdBy, usernameNew, passwordNew, emailNew);
					saveMessageKey(request, "message.qLNguoiDung.copyRoleToUserNew");
				}
				catch(Exception e){}
			}
		}
		else{
			saveMessageKey(request, "message.admin.taiKhoanTonTai");
				
		}
		
		return "jspadmin/usersCopyRoles";
	}
	
}
