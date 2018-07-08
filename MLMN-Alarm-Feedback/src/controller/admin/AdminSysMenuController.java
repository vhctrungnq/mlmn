package controller.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vo.CHighlightConfigs;
import vo.SYS_PARAMETER;
import vo.SysMenu;
import vo.SysResponsibilities;
import vo.alarm.utils.Helper;

import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysMenuDAO;
import dao.SysResponsibilitiesDAO;

/**
 * Function        : Quan ly menu
 * Created By      : BUIQUANG
 * Create Date     :
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/admin/menu-management/*")
public class AdminSysMenuController extends BaseController{

	@Autowired
	private SysMenuDAO sysMenuDao;
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	@Autowired
	private SysResponsibilitiesDAO sysResponsibilitiesDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@RequestMapping(value="danh-sach")
    public ModelAndView list(@ModelAttribute("filter") SysMenu filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
				
		List<SYS_PARAMETER> systemList = mDepartmentDAO.getSystemListBySp(); //sysParameterDao.getSysParameterByCode("NAME_SYSTEM");
		model.addAttribute("systemList", systemList);
		try{
			List<SYS_PARAMETER> titleSystem = sysMenuDao.titleSysMenu(); //sysParameterDao.getSPByCodeAndName("TITLE_SYSTEM", "QUAN_LY_MENU");
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
		catch(Exception e){}
		
		List<SysMenu> sysMenuSystemDistinct = sysMenuDao.getDistinctSystemOfSysMenu();
		model.addAttribute("sysMenuSystemDistinct", sysMenuSystemDistinct);
		
		List<SYS_PARAMETER> systemNotInSysMenu = sysParameterDao.getSystemNotInSysMenu();
		model.addAttribute("systemNotInSysMenu", systemNotInSysMenu);
		
		if(filter.getMenuName() != null)
			filter.setMenuName(filter.getMenuName().trim());
		
		List<SysMenu> sysMenuList = sysMenuDao.getSysMenuFilterPackage(filter.getSystem(), filter.getMenuName());
		model.addAttribute("sysMenuList", sysMenuList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DSMenu_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		model.addAttribute("systemCBB", filter.getSystem());
		model.addAttribute("menuChaCBB", filter.getMenuCha());
		
		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("MENU_COLOR");
		model.addAttribute("highlight", Helper.highLightCost(highlightConfigList, "item"));
		
		return new ModelAndView("jspadmin/sysMenuList");
    }
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		SysMenu sysMenu = (id == null) ? new SysMenu() : sysMenuDao.selectByPrimaryKey(id);
		model.addAttribute("sysMenuForm", sysMenu);
		
		List<SYS_PARAMETER> systemList = mDepartmentDAO.getSystemListBySp(); //sysParameterDao.getSysParameterByCode("NAME_SYSTEM");
		model.addAttribute("systemList", systemList);
		
		if(id == null)
		{		
			List<SysMenu> sysMenuList = sysMenuDao.selectSysMenuBySystem(systemList.get(0).getValue());
			model.addAttribute("sysMenuList", sysMenuList);
			if(sysMenu.getIsParent() == null)
			{
				sysMenu.setIsParent("Y");
			}
			model.addAttribute("systemCBB", systemList.get(0).getValue());
			
		}
		else
		{
			List<SysMenu> sysMenuList = sysMenuDao.getSysMenuBySystemDontId(sysMenu.getSystem(), id);
			model.addAttribute("sysMenuList", sysMenuList);
			model.addAttribute("systemCBB", sysMenu.getSystem());
			model.addAttribute("idSysMenuCBB", sysMenu.getIdHas());
		}
		
		sysMenuAddEdit(id, model);
		
		return "jspadmin/sysMenuForm";
	}
	
	private void sysMenuAddEdit(Integer id, ModelMap model){
		if(id != null)
			model.addAttribute("sysMenuAddEdit", "Y");
		else
			model.addAttribute("sysMenuAddEdit", "N");
	}

	// Load phòng ban theo user đăng nhập
	private void LoadPhongBanByUser(ModelMap model)
	{
		List<SYS_PARAMETER> systemList = mDepartmentDAO.getSystemListBySp(); //sysParameterDao.getSysParameterByCode("NAME_SYSTEM");
		model.addAttribute("systemList", systemList);
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) Integer id, @ModelAttribute("sysMenuForm") @Valid SysMenu sysMenu, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//Ném lỗi
		if (result.hasErrors()) {
			LoadPhongBanByUser(model);
			model.addAttribute("systemCBB", sysMenu.getSystem());
			model.addAttribute("idSysMenuCBB", sysMenu.getIdHas());
			sysMenuAddEdit(id, model);
			
			List<SysMenu> sysMenuList = sysMenuDao.selectSysMenuBySystem(sysMenu.getSystem());
			model.addAttribute("sysMenuList", sysMenuList);
			
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("orderingError", "orderingError");
            return "jspadmin/sysMenuForm";
		}
		
		sysMenu.setMenuLable(sysMenu.getAppCode());
		if(sysMenu.getMenuLable() != null  && sysMenu.getMenuLable() != "")
		{
			try{
				sysMenu.setMenuLable(sysMenu.getAppCode().substring(0,(sysMenu.getAppCode().lastIndexOf("/"))));
			}
			catch(Exception e){
				LoadPhongBanByUser(model);
				
				model.addAttribute("systemCBB", sysMenu.getSystem());
				model.addAttribute("idSysMenuCBB", sysMenu.getIdHas());
				sysMenuAddEdit(id, model);
				List<SysMenu> sysMenuList = sysMenuDao.selectSysMenuBySystem(sysMenu.getSystem());
				model.addAttribute("sysMenuList", sysMenuList);
				saveMessageKey(request, "message.maUngDungError");
	            return "jspadmin/sysMenuForm";
			}
		}
		
		if(sysMenu.getIsParent() == null)
		{
			sysMenu.setIsParent("N");
			
			SysMenu record = sysMenuDao.selectByPrimaryKey(sysMenu.getIdHas());
			if(record == null){		
				LoadPhongBanByUser(model);
				model.addAttribute("systemCBB", sysMenu.getSystem());
				model.addAttribute("idSysMenuCBB", sysMenu.getIdHas());
				sysMenuAddEdit(id, model);
				List<SysMenu> sysMenuList = sysMenuDao.selectSysMenuBySystem(sysMenu.getSystem());
				model.addAttribute("sysMenuList", sysMenuList);
				saveMessageKey(request, "qLMenu.isHasDontHave");
	            return "jspadmin/sysMenuForm";
			}
			else
				sysMenu.setModuleName(record.getMenuName());
		}
		else
		{
			sysMenu.setIdHas(1);
		}
		if(sysMenuDao.selectByPrimaryKey(sysMenu.getId())== null)
		{
			sysMenu.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			
			sysMenuDao.insert(sysMenu);
			saveMessageKey(request, "messsage.confirm.addsuccess");
		}
		else
		{
			try
			{
				sysMenu.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				
				sysMenuDao.updateByPrimaryKey(sysMenu);
				
				// Kiem tra neu no la menu cha thi pai kiem tra xem co menu con de chuyen sang cung 1 module
				List<SysMenu> record = sysMenuDao.selectMenuConById(sysMenu.getId());
				
				for(int i=0;i<record.size();i++){
					sysMenuDao.updateSystemForMenuCon(record.get(i).getId(), sysMenu.getSystem());
				}
				
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			catch(Exception e)
			{
			}
		}
		
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		
		int count = sysMenuDao.countRowSysMenuByIdHas(id);
		if(count > 0){
			saveMessageKey(request, "message.confirm.sysMenuSmallTonTai");
		}
		else
		{
			List<SysResponsibilities> record = sysResponsibilitiesDao.getSysResponsibilitiesByIdMenu(Integer.toString(id));
			for(int i=0;i<record.size();i++)
			{
				sysResponsibilitiesDao.deleteByPrimaryKey(record.get(i).getId());
			}
			try
			{
				sysMenuDao.deleteByPrimaryKey(id);
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch(Exception e)
			{
				saveMessageKey(request, "message.confirm.sysMenuSmallTonTai");
				}
		}
		
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping("ajax/copySystem")
	public @ResponseBody
	int copyPhongBan(@RequestParam(required = false) String systemFrom,
			@RequestParam(required = false) String systemTo, HttpServletRequest request) {
		if(systemFrom != "" && systemTo != "")
		{
			List<SysMenu> sysMenuListByMaPhong = sysMenuDao.selectSysMenuBySystemForCopy(systemFrom, null);
			
			try
			{
				for(int i=0;i<sysMenuListByMaPhong.size();i++)
				{
					SysMenu record = new SysMenu();
					if(sysMenuListByMaPhong.get(i).getIdHas().equals(1)){
					
						record.setMenuName(sysMenuListByMaPhong.get(i).getMenuName());
						record.setMenuLable(sysMenuListByMaPhong.get(i).getMenuLable());
						record.setModuleName(sysMenuListByMaPhong.get(i).getModuleName());
						record.setIsParent(sysMenuListByMaPhong.get(i).getIsParent());
						record.setAppCode(sysMenuListByMaPhong.get(i).getAppCode());
						record.setOrdering(sysMenuListByMaPhong.get(i).getOrdering());
						record.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
						record.setIdHas(sysMenuListByMaPhong.get(i).getIdHas());
						
						record.setSystem(systemTo);
						
						sysMenuDao.insert(record);
					}
					else{
						SysMenu menu_cha = sysMenuDao.selectByIdHas(sysMenuListByMaPhong.get(i).getIdHas());
						List<SysMenu> list_chaByMaPhong = sysMenuDao.selectSysMenuBySystemForCopy(systemTo, null);
						for(int j=0;j<list_chaByMaPhong.size();j++){
							if(menu_cha.getMenuName().equals(list_chaByMaPhong.get(j).getMenuName()))
							{
								record.setMenuName(sysMenuListByMaPhong.get(i).getMenuName());
								record.setMenuLable(sysMenuListByMaPhong.get(i).getMenuLable());
								record.setModuleName(sysMenuListByMaPhong.get(i).getModuleName());
								record.setIsParent(sysMenuListByMaPhong.get(i).getIsParent());
								record.setAppCode(sysMenuListByMaPhong.get(i).getAppCode());
								record.setOrdering(sysMenuListByMaPhong.get(i).getOrdering());
								record.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
								record.setIdHas(list_chaByMaPhong.get(j).getId());
								record.setSystem(systemTo);
								sysMenuDao.insert(record);
								break;
							}
						}
					}
					
				}
				
				saveMessageKey(request, "qLyMenu.copyMessage");
			}
			catch(Exception e){}
		}
		else
			saveMessageKey(request, "qLyMenu.copyMessageError");
		return 1;
	}
	
	@RequestMapping("ajax/loadsysMenuBySystem")
	public @ResponseBody
	List<SysMenu> loadsysMenuBySystem(@RequestParam(required = false) String system, HttpServletRequest request) {
		List<SysMenu> record = sysMenuDao.selectSysMenuBySystem(system);
		return record;
	}
}
