package controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vo.CHighlightConfigs;
import vo.SYS_PARAMETER;
import vo.SysGroupUser;
import vo.SysResponsibilities;
import vo.alarm.utils.Helper;

import controller.BaseController;

import dao.CHighlightConfigsDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysGroupUserDAO;
import dao.SysMenuDAO;
import dao.SysResponsibilitiesDAO;

/**
 * Function        : Phan quyen nhom truy cap
 * Created By      : BUIQUANG
 * Create Date     :
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/admin/phan-quyen-nguoi-dung/*")
public class AdminRolesGroupUserController extends BaseController{

	@Autowired
	private SysGroupUserDAO sysGroupUserDao;
	@Autowired
	private SysResponsibilitiesDAO sysResponsibilitiesDao;
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	@Autowired
	private SysMenuDAO sysMenuDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@RequestMapping(value="danh-sach", method = RequestMethod.GET)
    public String list(@RequestParam(required = false) String groupID,
    		@RequestParam(required = false) String system, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> systemList = mDepartmentDAO.getSystemListBySp(); //sysParameterDao.getSysParameterByCode("NAME_SYSTEM");
		model.addAttribute("systemList", systemList);
		try{
			List<SYS_PARAMETER> titleSystem = sysGroupUserDao.titleRolesGroupUsers(); //sysParameterDao.getSPByCodeAndName("TITLE_SYSTEM", "PHAN_QUYEN_NHOM_TRUY_CAP");
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
		catch(Exception e){}
		
		if(groupID != null){
			
			// Them danh sach nhung menu moi duoc them vao theo username
			sysMenuDao.insertMenuByGroupSystem(groupID, system, username);
			
			// Hien thi danh sach nhom chuc nang
			List<SysResponsibilities> sysResponsibilitiesList = sysResponsibilitiesDao.getSysResponsibilitiesByMenuIdAndGroupId(null, groupID, system);
			model.addAttribute("sysResponsibilitiesList", sysResponsibilitiesList);
		}
		
		if(system == null)
		{
			model.addAttribute("systemCBB", systemList.get(0).getValue());
			system = systemList.get(0).getValue();
			
		}
		else
		{
			model.addAttribute("systemCBB", system);
		}
		
		model.addAttribute("groupID", groupID);
		List<SysGroupUser> sysGroupUserList = sysGroupUserDao.selectGroupUsersBySystem(system);
		model.addAttribute("sysGroupUserList", sysGroupUserList);
		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("MENU_COLOR");
		model.addAttribute("highlight", Helper.highLightCost(highlightConfigList, "item1"));
		return "jspadmin/rolesGroupUserList";
    }
	
	@RequestMapping("ajax/checkedList")
	public @ResponseBody
	int checkedList(@RequestParam(required = false) String checkedList,
			@RequestParam(required = false) String uncheckedList, HttpServletRequest request) {
		if(checkedList != "" || uncheckedList != "")
		{
			String[] checkList = checkedList.split("-");
			String[] uncheckList = uncheckedList.split("-");
			for(int i=0;i<checkList.length;i++)
			{
				sysResponsibilitiesDao.updateCheckedByPrimaryKey("1", checkList[i].toString());
			}
			
			for(int j=0;j<uncheckList.length;j++)
			{
				sysResponsibilitiesDao.updateCheckedByPrimaryKey("0", uncheckList[j].toString());
			}
			saveMessageKey(request, "pQuyenNhomNguoiDung.capQuyenTruyCap");
		}
		else
			saveMessageKey(request, "pQuyenNhomNguoiDung.capQuyenTruyCapError");
		return 1;
	}

}
