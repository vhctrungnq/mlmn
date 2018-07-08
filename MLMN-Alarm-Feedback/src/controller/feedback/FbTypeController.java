package controller.feedback;

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

import vo.FbType;
import vo.SYS_PARAMETER;
import controller.BaseController;
import dao.FbTypeDAO;
import dao.SYS_PARAMETERDAO;
/**
 * Function        : Quan ly thong tin loai phan anh
 * Created By      : BUIQUANG
 * Create Date     : 
 * Modified By     : 
 * Modify Date     : 8/1/2014
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/feedback/quan-ly-loai-phan-anh/*")
public class FbTypeController extends BaseController{
	@Autowired
	private FbTypeDAO  FbtypDAO ;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") FbType filter, ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		
		if(filter.getCode() != null)
			filter.setCode(filter.getCode().trim());
		if(filter.getName() != null)
			filter.setName(filter.getName().trim());
		List<SYS_PARAMETER> sysParameterByCodeList = FbtypDAO.getTrangThai();//sysParameterDao.getSysParameterByCode("PB_LPA_TRANG_THAI");
		model.addAttribute("sysParameterByCodeList", sysParameterByCodeList);
		
		model.addAttribute("trangThaiCBB", filter.getIsEnable());
		
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
		String exportFileName = "Loaiphananh_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		
		List<FbType> Fbtype = FbtypDAO.Filter(filter.getCode(), filter.getName(), filter.getIsEnable(), column, order == 1 ? "ASC" : "DESC");
		model.addAttribute("Fbtype", Fbtype);

		return new ModelAndView("jspfeedback/FbTypelist");
	}	
	
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		FbType Fbtype = (id == null) ? new FbType() : FbtypDAO.selectByID(id);
		model.addAttribute("FbType", Fbtype);
		
		List<SYS_PARAMETER> sysParameterByCodeList = FbtypDAO.getTrangThai();
		model.addAttribute("sysParameterByCodeList", sysParameterByCodeList);
		
		model.addAttribute("trangThaiCBB", Fbtype.getIsEnable());
		
		maPAAddEdit(id, model);
		
		return "jspfeedback/FbTypeForm";
	}
	
	private void maPAAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("maPAAddEdit", "Y");
		else
			model.addAttribute("maPAAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, @ModelAttribute("FbType") @Valid FbType FbType, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		
		//Ném lỗi
		if (result.hasErrors()) {
			
			List<SYS_PARAMETER> sysParameterByCodeList = FbtypDAO.getTrangThai();
			model.addAttribute("sysParameterByCodeList", sysParameterByCodeList);
			
			model.addAttribute("trangThaiCBB", FbType.getIsEnable());
			
			maPAAddEdit(id, model);
			
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("sapXepError", "sapXepError");
			if (result.hasFieldErrors("timeProcess"))
				model.addAttribute("thoiHanXuLyError", "thoiHanXuLyError");
			
            return "jspfeedback/FbTypeForm";
		}
		
		if(id == "")
		{
			if(FbtypDAO.selectByPrimaryKey(FbType.getCode()) == null)
			{
				FbType.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				
				FbtypDAO.insert(FbType);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else
			{
				saveMessageKey(request, "message.loaiPhanAnh.maPhanAnhTonTai");
				
				List<SYS_PARAMETER> sysParameterByCodeList = FbtypDAO.getTrangThai();
				model.addAttribute("sysParameterByCodeList", sysParameterByCodeList);
				
				model.addAttribute("trangThaiCBB", FbType.getIsEnable());
				
				maPAAddEdit(id, model);
				
	            return "jspfeedback/FbTypeForm";
			}
		}
		else
		{
			FbType.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			
			FbtypDAO.updateByPrimaryKey(FbType);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		
		return "redirect:list.htm";
	}
	
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			FbtypDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
}
