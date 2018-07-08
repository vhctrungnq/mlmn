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

import controller.BaseController;

import vo.FbVip;

import dao.FbVipDAO;
/**
 * Function        : Dinh nghia thong tin khach hang vip
 * Created By      : BUIQUANG
 * Create Date     : 
 * Modified By     : 
 * Modify Date     : 8/1/2014
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/feedback/dinh-nghia-truong-vip/*")
public class FbVipController extends BaseController{

	@Autowired
	private FbVipDAO fbVipDao;
	
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") FbVip filter, ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		if(filter.getVipCode() != null)
			filter.setVipCode(filter.getVipCode().trim());
		if(filter.getVipName() != null)
			filter.setVipName(filter.getVipName().trim());
		
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
		String exportFileName = "TruongVip_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		List<FbVip> fbVipList =  fbVipDao.getFbVipFilter(filter.getVipCode(), filter.getVipName(), column, order==1? "ASC" : "DESC");
		model.addAttribute("fbVipList", fbVipList);
		
		return new ModelAndView("jspfeedback/fbVipList");
	}
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		FbVip fbVip = (id == null ) ? new FbVip() : fbVipDao.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("fbVip", fbVip);
		
		fbVipAddEdit(id, model);
		
		return "jspfeedback/fbVipForm";
	}
	
	private void fbVipAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("fbVipAddEdit", "Y");
		else
			model.addAttribute("fbVipAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, @ModelAttribute("fbVip") @Valid FbVip fbVip, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//Ném lỗi
		if (result.hasErrors()) {
			fbVipAddEdit(id, model);
			
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("sapXepError", "sapXepError");
			return "jspfeedback/fbVipForm";
		}
		
		if(id == "")
		{
			if(fbVipDao.selectByVipCode(fbVip.getVipCode()).size() == 0){
				fbVip.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				fbVipDao.insert(fbVip);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else{
				saveMessageKey(request, "message.truongVip.maTruongVipTonTai");
				fbVipAddEdit(id, model);
				return "jspfeedback/fbVipForm";
			}
		}
		else{
			fbVip.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			
			fbVipDao.updateByPrimaryKey(fbVip);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			fbVipDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
}
