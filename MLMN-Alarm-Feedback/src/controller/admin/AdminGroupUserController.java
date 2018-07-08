package controller.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vo.SYS_PARAMETER;
import vo.SysGroupUser;

import controller.BaseController;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysGroupUserDAO;
/**
 * Function        : Quan ly nhom truy cap
 * Created By      : BUIQUANG
 * Create Date     :
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/admin/nhom-nguoi-dung/*")
public class AdminGroupUserController extends BaseController{

	@Autowired
	private SysGroupUserDAO sysGroupUserDao;
	@Autowired
	private MDepartmentDAO mDepartmentDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@RequestMapping(value="danh-sach")
    public ModelAndView list(@ModelAttribute("filter") SysGroupUser filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		
		if(filter.getSystem() != null)
			filter.setSystem(filter.getSystem().trim());
		if(filter.getGroupName() != null)
			filter.setGroupName(filter.getGroupName().trim());
		
		List<SYS_PARAMETER> systemList = mDepartmentDao.getSystemListBySp(); //sysParameterDao.getSysParameterByCode("NAME_SYSTEM");
		model.addAttribute("systemList", systemList);
		model.addAttribute("systemCBB", filter.getSystem());
		
		List<SYS_PARAMETER> titleSystem = sysGroupUserDao.titleSysGroupUser();//sysParameterDao.getSPByCodeAndName("TITLE_SYSTEM", "QUAN_LY_NHOM_TRUY_CAP");
		model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		
		int order = 1;
		String column = "ORDERING";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DSNhomNgDung_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		List<SysGroupUser> sysGroupUserList = sysGroupUserDao.getSysGroupUserFilter(filter.getSystem(), filter.getGroupName(), column, order == 1 ? "ASC" : "DESC");
		model.addAttribute("sysGroupUserList", sysGroupUserList);
		
		return new ModelAndView("jspadmin/groupUserList");
    }
	
	private void nhomNguoiDungAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("nhomNguoiDungAddEdit", "Y");
		else
			model.addAttribute("nhomNguoiDungAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		SysGroupUser sysGroupUser = (id == null) ? new SysGroupUser() : sysGroupUserDao.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("sysGroupUserForm", sysGroupUser);
		
		
		List<SYS_PARAMETER> systemList = mDepartmentDao.getSystemListBySp(); //sysParameterDao.getSysParameterByCode("NAME_SYSTEM");
		model.addAttribute("systemList", systemList);
		
		nhomNguoiDungAddEdit(id, model);
		
		model.addAttribute("systemCBB", sysGroupUser.getSystem());
		
		return "jspadmin/groupUserForm";
	}	
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, @ModelAttribute("sysGroupUserForm") @Valid SysGroupUser sysGroupUser, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		
		//Ném lỗi
		if (result.hasErrors()) {
			
			model.addAttribute("systemCBB", sysGroupUser.getSystem());
			nhomNguoiDungAddEdit(id, model);
			
			List<SYS_PARAMETER> systemList = mDepartmentDao.getSystemListBySp(); //sysParameterDao.getSysParameterByCode("NAME_SYSTEM");
			model.addAttribute("systemList", systemList);
			
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("orderingError", "orderingError");
			
            return "jspadmin/groupUserForm";
		}

		if(id == "")
		{
			sysGroupUser.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			
			if(sysGroupUserDao.checkUniqueGroupUser(sysGroupUser.getSystem(), sysGroupUser.getGroupName(), null).size() == 0){
				sysGroupUserDao.insert(sysGroupUser);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else
			{
				saveMessage(request, "Tên nhóm và hệ thống đã tồn tại.");
				
				model.addAttribute("systemCBB", sysGroupUser.getSystem());				
				List<SYS_PARAMETER> systemList = mDepartmentDao.getSystemListBySp(); //sysParameterDao.getSysParameterByCode("NAME_SYSTEM");
				model.addAttribute("systemList", systemList);
				nhomNguoiDungAddEdit(id,model);
				return "jspadmin/groupUserForm";
			}
		}
		else
		{
			try
			{
				sysGroupUser.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				if(sysGroupUserDao.checkUniqueGroupUser(sysGroupUser.getSystem(), sysGroupUser.getGroupName(), id).size() == 0){
					sysGroupUserDao.updateByPrimaryKey(sysGroupUser);
					saveMessageKey(request, "messsage.confirm.updatesuccess");
				}
				else{
					saveMessage(request, "Tên nhóm và hệ thống đã tồn tại.");
					
					model.addAttribute("systemCBB", sysGroupUser.getSystem());				
					List<SYS_PARAMETER> systemList = mDepartmentDao.getSystemListBySp(); //sysParameterDao.getSysParameterByCode("NAME_SYSTEM");
					model.addAttribute("systemList", systemList);
					nhomNguoiDungAddEdit(id,model);
					return "jspadmin/groupUserForm";
				}
			}
			catch(Exception e)
			{
				model.addAttribute("systemCBB", sysGroupUser.getSystem());
				nhomNguoiDungAddEdit(id, model);
				
				List<SYS_PARAMETER> systemList = mDepartmentDao.getSystemListBySp(); //sysParameterDao.getSysParameterByCode("NAME_SYSTEM");
				model.addAttribute("systemList", systemList);
				saveMessage(request, "Tên nhóm và hệ thống đã tồn tại.");
				return "jspadmin/groupUserForm";
			}
		}
		
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			sysGroupUserDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:danh-sach.htm";
	}
}
