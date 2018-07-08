package controller.assets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controller.BaseController;

import vo.AsCards;
import vo.AsProposedWarehouse;
import vo.AssetsTypes;
import vo.CHighlightConfigs;
import vo.alarm.utils.DateTools;

import dao.AsCardsDAO;
import dao.AsProposedWarehouseDAO;
import dao.AssetsManagementsDAO;
import dao.AssetsTypesDAO;
import dao.AssetsWarrantyDAO;
import dao.CHighlightConfigsDAO;
import dao.SYS_PARAMETERDAO;

/**
 * Ten file: AsCardsController.java
 * Muc dich: Quan ly card
 * @author QBu
 * Ngay tao: 10/7/2013
 * Lich su thay doi:
 * 
 */

@Controller
@RequestMapping("/assets/as-cards-for-user/*")
public class AsCardsForUserController extends BaseController{

	@Autowired
	private AsCardsDAO asCardsDAO;
	@Autowired
	private AssetsTypesDAO assetsTypesDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private AssetsWarrantyDAO assetsWarrantyDAO;
	@Autowired
	private AssetsManagementsDAO assetsManagementDao;
	@Autowired
	private AsProposedWarehouseDAO asProposedWarehouseDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
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
	
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required = false) String asTypesId,
							 @RequestParam(required = false) String importDateFrom,
							 @RequestParam(required = false) String importDateTo,
							 @RequestParam(required = false) String exportDateFrom,
							 @RequestParam(required = false) String exportDateTo,
							 @RequestParam(required = false) String messagesXuatKho,
							 @ModelAttribute("filter") AsCards filter, ModelMap model, HttpServletRequest request, HttpServletResponse response){
		if(filter.getProductCode() != null)
			filter.setProductCode(filter.getProductCode().trim());
		if(filter.getProductName() != null)
			filter.setProductName(filter.getProductName().trim());
		if(filter.getSerialNo() != null)
			filter.setSerialNo(filter.getSerialNo().trim());
		if(filter.getJect() != null)
			filter.setJect(filter.getJect().trim());
		if(filter.getVendor() != null)
			filter.setVendor(filter.getVendor().trim());
		if(filter.getOrganization() != null)
			filter.setOrganization(filter.getOrganization().trim());
		if(filter.getDepartmentId() != null)
			filter.setDepartmentId(filter.getDepartmentId().trim());
		if(filter.getVotesNo() != null)
			filter.setVotesNo(filter.getVotesNo().trim());
		if(filter.getUsersEx() != null)
			filter.setUsersEx(filter.getUsersEx().trim());
		if(filter.getWarehourse() != null)
			filter.setWarehourse(filter.getWarehourse().trim());
		
		List<AssetsTypes> asTypesIDList = assetsTypesDAO.getAssetsTypesList();
 		model.addAttribute("asTypesIDList", asTypesIDList);
 		model.addAttribute("asTypesIDCBB", asTypesId);
 		
		// Ngay thang
 		if (importDateFrom != null && !importDateFrom.equals("") && DateTools.isValid("dd/MM/yyyy", importDateFrom)==false) {			

			importDateFrom = df.format(DateTools.startMonth(new Date()));
		
		}
 		if (importDateTo != null && !importDateTo.equals("") && DateTools.isValid("dd/MM/yyyy", importDateTo)==false)
 		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			importDateTo = df.format(cal.getTime());
		}
 		if (exportDateFrom != null && !exportDateFrom.equals("") && DateTools.isValid("dd/MM/yyyy", exportDateFrom)==false) {			

 			exportDateFrom = df.format(DateTools.startMonth(new Date()));
		
		}
 		if (exportDateTo != null && !exportDateTo.equals("") && DateTools.isValid("dd/MM/yyyy", exportDateTo)==false)
 		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			exportDateTo = df.format(cal.getTime());
		}
 		
		int order = 1;
		String column = "STT";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
				
		List<AsCards> asCardsList = asCardsDAO.getAsCardsFilter(asTypesId, 
				filter.getProductCode(), filter.getSerialNo(), filter.getProductName(), 
				importDateFrom, importDateTo, filter.getJect(), filter.getVendor(), 
				filter.getOrganization(), filter.getDepartmentId(), exportDateFrom, exportDateTo, 
				filter.getVotesNo(), filter.getUsersEx(), filter.getWarehourse(), column, order==1?"ASC":"DESC");
		model.addAttribute("asCardsList", asCardsList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "NhapTaiSan_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		model.addAttribute("importDateFrom", importDateFrom);
		model.addAttribute("importDateTo", importDateTo);
		model.addAttribute("exportDateFrom", exportDateFrom);
		model.addAttribute("exportDateTo", exportDateTo);
		model.addAttribute("messagesXuatKho", messagesXuatKho);
		
		return new ModelAndView("jspassets/asCardsForUser/asCardsList");
	}
	
	
	@RequestMapping(value = "xuatKhoCards", method = RequestMethod.GET)
	public String xuatKhoCards(@RequestParam (required = true) Integer id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			AsCards count = asCardsDAO.getAmountDontUse(id.toString());
			int temp = count.getAmount();
			if(temp > 0){
				
				AsProposedWarehouse record = new AsProposedWarehouse();
				
				record.setProductCode(count.getProductCode());
				record.setProductName(count.getProductName());
				record.setSerialNo(count.getSerialNo());
				record.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				record.setSoLuong(1);
				record.setDonVi(count.getUnit());
				record.setNhaSx(count.getVendor());
				
				asProposedWarehouseDAO.insert(record);
				saveMessageKey(request, "message.confirm.xuatKhoThanhCong");
			}
			else{
				
				model.addAttribute("messagesXuatKho", "Y");
			}
			
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.xuatKhoThatBai");
			}
		
		return "redirect:list.htm";
	}
	
}
