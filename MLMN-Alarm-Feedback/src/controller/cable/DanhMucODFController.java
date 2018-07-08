package controller.cable;

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

import controller.BaseController;

import vo.CableOdfTypes;
import vo.SysUsers;
import vo.alarm.utils.Helper;

import dao.CableOdfTypesDAO;
import dao.SysUsersDAO;
/**
 * 
 * @author QuangBD
 * @UpdateBy ThangPX
 * @UpdatedDate 01/04/2014
 * @Purpose Display Cable List and check role.
 *
 */
@Controller
@RequestMapping("/cable/danh-muc-odf-lien-tang/*")
public class DanhMucODFController extends BaseController{

	@Autowired
	private CableOdfTypesDAO cableOdfTypesDAO;
	@Autowired 
	private SysUsersDAO  sysUserDao;
	@RequestMapping(value="list")
    public String list(@RequestParam(required = false) String schemaName, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DanhMucODFLienTang_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		int order = 1;
		String column = "ORDERING";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		if(schemaName != null)
			schemaName = schemaName.trim();
		
		List<CableOdfTypes> odfTypesList = cableOdfTypesDAO.getCableOdfTypesFilter(schemaName, column, order == 1 ? "ASC" : "DESC");
		model.addAttribute("odfTypesList", odfTypesList);
		model.addAttribute("schemaName", schemaName);
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);			
		return "jspcable/danhMucODFLienTangList";
	}
	
	//Cable Odf Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		CableOdfTypes cableOdfTypes = (id == null) ? new CableOdfTypes() : cableOdfTypesDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("cableOdfTypesForm", cableOdfTypes);
		
		tenSoDoAddEdit(id, model);
		
		return "jspcable/danhMucODFLienTangForm";
	}
	
	private void tenSoDoAddEdit(String id, ModelMap model){
		if(id != null && id != "")
			model.addAttribute("tenSoDoAddEdit", "Y");
		else
			model.addAttribute("tenSoDoAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, @ModelAttribute("cableOdfTypesForm") @Valid CableOdfTypes cableOdfTypes, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		
		//Ném lỗi
		if (result.hasErrors()) {
			
			
			tenSoDoAddEdit(id, model);
			
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("sapXepError", "sapXepError");
			if (result.hasFieldErrors("locationPort"))
				model.addAttribute("locationPortError", "locationPortError");
			
            return "jspcable/danhMucODFLienTangForm";
		}
		
		if(id == "")
		{
			if(cableOdfTypesDAO.selectBySchemaName(cableOdfTypes.getSchemaName()) == null)
			{
				cableOdfTypes.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				
				cableOdfTypesDAO.insert(cableOdfTypes);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else
			{
				saveMessageKey(request, "message.schemaName.tenSoDoTonTai");
				
				tenSoDoAddEdit(id, model);
				
	            return "jspcable/danhMucODFLienTangForm";
			}
		}
		else
		{
			cableOdfTypes.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			
			cableOdfTypesDAO.updateByPrimaryKey(cableOdfTypes);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		
		return "redirect:list.htm";
	}
	
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			cableOdfTypesDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
	
}
