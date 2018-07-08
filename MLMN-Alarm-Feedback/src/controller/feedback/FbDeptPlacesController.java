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

import vo.FbDeptPlaces;
import vo.HProvinceFb;
import vo.MDepartment;
import controller.BaseController;
import dao.FbDeptPlacesDAO;
import dao.FbProcessDAO;
import dao.HProvinceFbDAO;
import dao.VHProvincesCodeDAO;

@Controller
@RequestMapping("/feedback/quan-ly-to-xu-ly/*")
public class FbDeptPlacesController extends BaseController{
	@Autowired
	private FbDeptPlacesDAO  FbdepplaceDAO ;
	@Autowired
	private VHProvincesCodeDAO VHProvinceDao;
	@Autowired
 	private HProvinceFbDAO hProvinceFbDAO;
	@Autowired
 	private FbProcessDAO fbProcessDAO;
	
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") FbDeptPlaces filter, ModelMap model, HttpServletRequest request, HttpServletResponse response){
	
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "Toxuly_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		if(filter.getPlacesCode() != null && filter.getPlacesCode() != "")
		{
			String[] pDList = filter.getPlacesCode().split("//");
			filter.setPlacesCode(pDList[1]);
			filter.setProvinceCode(pDList[0]);
		}
		
		List<HProvinceFb> quanHuyenList = hProvinceFbDAO.selectProvincesCode();
 		model.addAttribute("quanHuyenList", quanHuyenList);
		
 		List<MDepartment> phongBanList = fbProcessDAO.getDepartmentFbList();
 		model.addAttribute("DepartmentList", phongBanList);
 		
 		int order = 1;
		String column = "ORDERING";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<FbDeptPlaces> FbDePlace = FbdepplaceDAO.Filter(filter.getDeptCode(), filter.getProvinceCode(), filter.getPlacesCode(), column, order == 1 ? "ASC" : "DESC");
		model.addAttribute("FbDePlace", FbDePlace);
		
		model.addAttribute("departmentCBB", filter.getDeptCode());
		model.addAttribute("tinhThanhCBB", filter.getProvinceCode());
		model.addAttribute("quanHuyenCBB", filter.getPlacesCode());
		
		return new ModelAndView("jspfeedback/FbDeptPlaceslist");
	}	
	
	private void toXuLyAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("toXuLyAddEdit", "Y");
		else
			model.addAttribute("toXuLyAddEdit", "N");
	}
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		FbDeptPlaces FbDePlace = (id == null) ? new FbDeptPlaces() : FbdepplaceDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("FbDePlace", FbDePlace);
		
		List<HProvinceFb> quanHuyenList = hProvinceFbDAO.selectProvincesCode();
 		model.addAttribute("quanHuyenList", quanHuyenList);
		
 		List<MDepartment> phongBanList = fbProcessDAO.getDepartmentFbList();
 		model.addAttribute("DepartmentList", phongBanList);
		
 		model.addAttribute("departmentCBB", FbDePlace.getDeptCode());
 		model.addAttribute("tinhThanhCBB", FbDePlace.getProvinceCode());
		model.addAttribute("quanHuyenCBB", FbDePlace.getPlacesCode());
		
		toXuLyAddEdit(id, model);
		
		return "jspfeedback/FbDePlaceForm";
	}

	private void loadListForm(FbDeptPlaces FbDePlace, ModelMap model, String id){
		List<HProvinceFb> quanHuyenList = hProvinceFbDAO.selectProvincesCode();
 		model.addAttribute("quanHuyenList", quanHuyenList);
		
 		List<MDepartment> phongBanList = fbProcessDAO.getDepartmentFbList();
 		model.addAttribute("DepartmentList", phongBanList);
 			
 		model.addAttribute("departmentCBB", FbDePlace.getDeptCode());
		model.addAttribute("tinhThanhCBB", FbDePlace.getProvinceCode());
		model.addAttribute("quanHuyenCBB", FbDePlace.getPlacesCode());
		toXuLyAddEdit(id, model);
	}
	
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, @ModelAttribute("FbDePlace") @Valid FbDeptPlaces FbDePlace, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		if(FbDePlace.getPlacesCode() != null && FbDePlace.getPlacesCode() != "")
		{
			String[] pDList = FbDePlace.getPlacesCode().split("//");
			FbDePlace.setPlacesCode(pDList[1]);
			FbDePlace.setProvinceCode(pDList[0]);
		}
		
		//Ném lỗi
		if (result.hasErrors()) {
			
			loadListForm(FbDePlace,model,id);
			
            return "jspfeedback/FbDePlaceForm";
		}
		
		
		if(id == "")
		{
			FbDePlace.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			
			
			try{
				int count = FbdepplaceDAO.countDepartmentAndPlaces(FbDePlace.getDeptCode(), FbDePlace.getProvinceCode(), FbDePlace.getPlacesCode(), null);
				if( count == 0){
				
					FbdepplaceDAO.insert(FbDePlace);
					saveMessageKey(request, "messsage.confirm.addsuccess");
				}
				else
				{
					loadListForm(FbDePlace,model,id);
					
					saveMessageKey(request, "message.phongBanVaToXuLyTonTai");	
		            return "jspfeedback/FbDePlaceForm";
				}
				
			}
			catch(Exception e){
				
				loadListForm(FbDePlace,model,id);
				
				return "jspfeedback/FbDePlaceForm";
			}
			
		}
		else
		{
			try
			{
				if(FbdepplaceDAO.countDepartmentAndPlaces(FbDePlace.getDeptCode(), FbDePlace.getProvinceCode(), FbDePlace.getPlacesCode(), id) == 0){
					
				FbDePlace.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				
				FbdepplaceDAO.updateByPrimaryKeySelective(FbDePlace);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
				
				}
				else
				{
					loadListForm(FbDePlace,model,id);
					saveMessageKey(request, "message.phongBanVaToXuLyTonTai");
					return "jspfeedback/FbDePlaceForm";
				}
			}
			catch(Exception e)
			{
				loadListForm(FbDePlace,model,id);
				return "jspfeedback/FbDePlaceForm";
			}
		}
		
		return "redirect:list.htm";
	}
	
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			FbdepplaceDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
}
