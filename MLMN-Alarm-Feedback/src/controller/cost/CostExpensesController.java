package controller.cost;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import vo.CHighlightConfigs;
import vo.CostExpenses;
import vo.SYS_PARAMETER;
import vo.alarm.utils.Helper;
import vo.alarm.utils.UploadTools;

import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.CostExpensesDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;

@Controller
@RequestMapping("/cost/costExpenses/*")
public class CostExpensesController extends BaseController {

	@Autowired
	private CostExpensesDAO costExpensesDAO;
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@ModelAttribute("isEnableList")
	public List<SYS_PARAMETER> isEnableList() {
		return sysParameterDao.getSysParameterByCode("EXPENSES_STATUS");
	}
	@ModelAttribute("highlight1")
	public String highlight1() {
		String highlight = "";
		List<CHighlightConfigs> highlightConfigList2 = cHighlightConfigsDAO.getByKey("NOT_NULL");
		if (highlightConfigList2.size()>0)
		{
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfigList2.get(0).getStyle()+"});";
		}
		return highlight;
	}
	
	@RequestMapping(value="list")
    public String list(CostExpenses costExpenses,
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		List<SYS_PARAMETER> sysParemeterTitle = costExpensesDAO.titleForm(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		if (costExpenses.getExpensesName()!= null) {
			costExpenses.setExpensesName(costExpenses.getExpensesName().trim());
		}
		
		int order = 0;
		String column = "EXPENSES_CODE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		List<CostExpenses> expensesList = costExpensesDAO.getCostExpensesFilter(costExpenses, column, order);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DanhMucChiPhi_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
				
		model.addAttribute("costExpenses", costExpenses);
		
		model.addAttribute("expensesList", expensesList);
		
		List<CostExpenses> expensesSuperList = costExpensesDAO.getAll();
		model.addAttribute("expensesSuperList", 	expensesSuperList);
		
		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("ROOT_NO");
		model.addAttribute("highlight", Helper.highLightCost(highlightConfigList, "item"));
		
		return "jspcost/costExpensesList";
		
	}
	
	private void updateRootNo(String expensesCode,int num)
	{
		costExpensesDAO.updateRootNo(expensesCode,num);
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String cableForm(@RequestParam(required = false) Integer id, ModelMap model) {
		
		List<SYS_PARAMETER> sysParemeterTitle = costExpensesDAO.titleForm("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		CostExpenses costExpenses;
		String expensesSuperName="";
		List<CostExpenses> expensesSuperList = costExpensesDAO.getAll();
		
		if (id == null) {
			costExpenses = new CostExpenses();
		} else {
			costExpenses = costExpensesDAO.selectByPrimaryKey(id);
			model.addAttribute("expensesSuper", 	costExpenses.getExpensesSuper());
			model.addAttribute("isEnable", 	costExpenses.getIsEnable());
			for (CostExpenses costExpenses2 : expensesSuperList) {
				if (costExpenses2.getExpensesCode().equals(costExpenses.getExpensesSuper()))
				{
					expensesSuperName= costExpenses2.getExpensesName();
				}
				if (costExpenses.getExpensesSuper().equals("0"))
				{
					expensesSuperName= "Khoản mục cha";
				}
			}
			model.addAttribute("expensesSuperName", 	expensesSuperName);
		}
		model.addAttribute("costExpenses", 	costExpenses);
		
		
		model.addAttribute("expensesSuperList", 	expensesSuperList);
		
		//AddOrEdit(id,model);
		return "jspcost/costExpensesForm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String cableFormPost(@ModelAttribute("costExpenses") @Valid CostExpenses costExpenses, BindingResult result, 
    		@RequestParam(required = true) String isEnable,
    		@RequestParam(required = false) String expensesSuper,
    		@RequestParam(required = false) String expensesSuperName,
    		
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		List<SYS_PARAMETER> sysParemeterTitle = costExpensesDAO.titleForm("ADD");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
			
		}
		
		//AddOrEdit(costExpenses.getId(),model);
		List<CostExpenses> expensesSuperList = costExpensesDAO.getAll();
		model.addAttribute("expensesSuperList", 	expensesSuperList);
		model.addAttribute("expensesSuper", 	expensesSuper);
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (result.hasErrors()) {
			model.addAttribute("costExpenses", costExpenses);
			model.addAttribute("isEnable", isEnable);
			saveMessageKey(request, "costExpenses.ErrorField");
			model.addAttribute("expensesSuperName", expensesSuperName);
			model.addAttribute("titleF", title);
			return "jspcost/costExpensesForm";
		}
		else
		{
			CostExpenses costExpensesExit = costExpensesDAO.checkExit(costExpenses.getExpensesCode());
			costExpenses.setExpensesSuper(expensesSuper);
			
			if (costExpenses.getId() == null) {
				if (costExpensesExit!=null)
				{
					model.addAttribute("costExpenses", costExpenses);
					model.addAttribute("isEnable", isEnable);
					saveMessageKey(request, "costExpenses.exits");
					model.addAttribute("expensesSuperName", expensesSuperName);
					model.addAttribute("titleF", title);
					return "jspcost/costExpensesForm";
				}
				costExpenses.setCreatedBy(username);
				costExpenses.setCreateDate(new Date());
				costExpenses.setIsEnable(isEnable);
				if (costExpenses.getExpensesSuper().equals("0"))
				{
					costExpenses.setRootNo(0);
					costExpensesDAO.insertSelective(costExpenses);
				}
				else
				{
					costExpensesDAO.insertSelective(costExpenses);
					updateRootNo(expensesSuper,1);
				}
					
				
				saveMessageKey(request, "costExpenses.InsertSuccess");
				
			} else {
				if (costExpensesExit!=null)
				{
					if (costExpensesExit.getId().intValue()!=costExpenses.getId().intValue())
					{
					System.out.println("ID cable: "+costExpensesExit.getId()+"---id cableE1: "+costExpenses.getId());
					model.addAttribute("costExpenses", costExpenses);
					model.addAttribute("isEnable", 		isEnable);
					model.addAttribute("expensesSuperName", expensesSuperName);
					saveMessageKey(request, "costExpenses.exits");
					model.addAttribute("titleF", title);
					return "jspcost/costExpensesForm";
					}
				}
				costExpenses.setModifiedBy(username);
				costExpenses.setModifyDate(new Date());
				costExpenses.setIsEnable(isEnable);
				
				costExpensesDAO.updateByPrimaryKey(costExpenses);
				
				saveMessageKey(request, "costExpenses.UpdateSuccess");	

			}
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="ajax/get-costExpenses-inf-search")
    public @ResponseBody 
    List<String> ajaxGetCostExpensesInfoSearch(@RequestParam(required = false) String focus,@RequestParam(required = false) String term,
    		HttpServletRequest request, HttpServletResponse response) {
		
		List<String> paramList = new ArrayList<String>();
		paramList = costExpensesDAO.getCostExpensesSearch(focus, term);
		
		return paramList;
	}
	
	@RequestMapping(value="ajax/checkBeforeDalete")
    public @ResponseBody 
    Integer checkBeforeDalete(String expensesCode, HttpServletRequest request, HttpServletResponse response) {	
		
		int kt = costExpensesDAO.checkBeforeDalete(expensesCode);
		System.out.println("check delete: "+ kt);
		if (kt== 1)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	@RequestMapping(value="ajax/deleteCostExpenses")
    public @ResponseBody 
    Integer ajaxDeleteCableLine(Integer id,String expensesSuper, HttpServletRequest request, HttpServletResponse response) {	
		
			costExpensesDAO.deleteByPrimaryKey(id);
			updateRootNo(expensesSuper,-1);
			return 1;
		
	}
	
	@RequestMapping(value="ajax/checkIsSupper")
    public @ResponseBody 
    Integer checkIsSupper(String expensesCode, HttpServletRequest request, HttpServletResponse response) {	
		List<CostExpenses> childList = costExpensesDAO.getExpensesBySupper(expensesCode);
		if (childList.size()>0)
		{
			return 1;
		}
		else
			return 0;
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = false) String typeC,Model model) {
		model.addAttribute("typeC", 		typeC);
		List<SYS_PARAMETER> sysParemeterTitle = costExpensesDAO.titleForm("UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		return "jspcost/costExpensesUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		List<SYS_PARAMETER> sysParemeterTitle = costExpensesDAO.titleForm("UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				@SuppressWarnings("rawtypes")
				List sheetData = UploadTools.readXlsFileForCost(filePath.getInputStream());
				importContent(sheetData,model,request);
				
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		
		return "jspcost/costExpensesUpload";
		
	}

	private String importContent(List sheetData, Model model,
			HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> sysParemeterTitle = costExpensesDAO.titleForm("UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		List<CostExpenses> successList = new ArrayList<CostExpenses>();
		List<CostExpenses> failedList = new ArrayList<CostExpenses>();
		List<CostExpenses> success = new ArrayList<CostExpenses>();
		
		String expensesSuper;
		String expensesCode;
		String expensesName;
		//String workId;
		String description;
		String isEnable;
		String ordering;
		
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 7) {
        		saveMessageKey(request, "Định dạng tệp không đúng");
        		
        		return "jspcost/costExpensesUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		CostExpenses costExpenses;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			costExpenses = new CostExpenses();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=6; j++) {
     					data.add("");
     				}
        			expensesSuper		= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			expensesCode		= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			expensesName		= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			//workId				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			description			= data.get(4).toString().trim().equals("")?"":data.get(4).toString().trim();
        			isEnable			= data.get(5).toString().trim().equals("")?"":data.get(5).toString().trim();
        			ordering			= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim().toUpperCase();
        			
        			if (expensesSuper!=null && expensesCode!=null && expensesSuper.equals(expensesCode))
        			{
        				expensesSuper="0";
        			}
        			
        			costExpenses.setExpensesSuper(expensesSuper);
        			costExpenses.setExpensesCode(expensesCode);
        			costExpenses.setExpensesName(expensesName);
        			//costExpenses.setWorkId(workId);
        			costExpenses.setDescription(description);
        			costExpenses.setIsEnable(isEnable);
        			costExpenses.setOrderingStr(ordering);
        			
        			if (expensesCode==null || expensesName == null 
							|| expensesCode.length() >= 20
							|| expensesName.length() >= 250
							||	(description != null && description.length() >= 500)
							|| (isEnable != null && isEnable.length() >= 20)
							|| (ordering != null && ordering.length() >= 10)
							|| (expensesSuper != null && expensesSuper.length() >= 10)
					) {
						
						error = true;
					}
        			int orderingI = 0;
        			if (ordering!=null)
        			{
	        			try
	        			{
	        				orderingI = Integer.parseInt(ordering);
	        				costExpenses.setOrdering(orderingI);
	        			}
	        			catch(Exception exp)
	        			{
	        				error = true;
	        			}
        			}
        			
        			if (expensesCode == null && expensesName == null ) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(costExpenses);
    					} else  {
    						successList.add(costExpenses);
    					}
        			}
        		}
        	}
		}
		////Dang lam o day
		System.out.println("successList.size(): " + successList.size());
		for (CostExpenses cab: successList) {
			//Kiem tra danh muc cha da ton tai chua
			if (cab.getExpensesSuper()==null||cab.getExpensesSuper().equals(""))
			{
				cab.setExpensesSuper("0");
			}
			boolean kt= true;
			if (!cab.getExpensesSuper().equals("0"))
			{
				System.out.println("super:   "+cab.getExpensesSuper());
    			CostExpenses csuper = costExpensesDAO.checkExit(cab.getExpensesSuper());
    			if (csuper==null)
    			{
    				failedList.add(cab);
    				kt = false;
    			}
			}
			if (kt==true)
			{
				try {
					
					//CostExpenses costSuper = costExpensesDAO.checkExit(expensesSuper);
					//if (costSuper == null || (costSuper!=null && cab.getExpensesCode().equals(expensesSuper)))
					
					
					CostExpenses cost = costExpensesDAO.checkExit(cab.getExpensesCode());
	    			if (cost != null) {
	    				cab.setModifyDate(new Date());
	    				cab.setModifiedBy(username);
	    				cab.setId(cost.getId());
	    				costExpensesDAO.updateByPrimaryKey(cab);
	    			} 
	    			else
	    			{
	    				if (cab.getExpensesSuper() == null || cab.getExpensesSuper().equals("0"))
						{
							cab.setExpensesSuper("0");
							cab.setRootNo(0);
							
						}
	    				cab.setCreateDate(new Date());
	    				cab.setCreatedBy(username);
	    				costExpensesDAO.insertSelective(cab);
	    				updateRootNo(cab.getExpensesSuper(),1);
	    				
	    			}
					success.add(cab);
				} catch (Exception ex) {
					failedList.add(cab);
				}
			}
			
		}
		
		if (failedList.size() == 0 && success.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && success.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");					// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DanhMucChiPhi_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
				
		
		return "jspcost/costExpensesUpload";
		
	}
}
