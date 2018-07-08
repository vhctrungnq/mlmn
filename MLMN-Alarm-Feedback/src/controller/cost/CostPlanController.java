package controller.cost;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

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
import vo.CostPlan;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.alarm.utils.Helper;
import vo.alarm.utils.UploadTools;

import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.CostExpensesDAO;
import dao.CostPlanDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;

@Controller
@RequestMapping("/cost/costPlan/*")
public class CostPlanController extends BaseController {
	
	@Autowired
	private CostExpensesDAO costExpensesDAO;
	
	@Autowired
	private CostPlanDAO costPlanDAO;
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@ModelAttribute("departmentList")
	public List<MDepartment> departmentList() {
		return mDepartmentDAO.getDepartmentList();
	}
	
	@ModelAttribute("highlight")
	public String highlight() {
		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("ROOT_NO");
		return Helper.highLightCost(highlightConfigList, "item");
		
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
    public String list(CostPlan costPlan,@RequestParam(required = false) String departmentId,
    		@RequestParam(required = false) Integer costYear,
    		@RequestParam(required = false) String expensesSupper,
    		@RequestParam(required = false) String checkAll,
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		List<SYS_PARAMETER> sysParemeterTitle = costPlanDAO.titleForm(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		
		if (costPlan.getExpensesName()!= null) {
			costPlan.setExpensesName(costPlan.getExpensesName().trim());
		}
		if (costPlan.getId() == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int month = cal.get(Calendar.MONTH);
			if (month<=6)
			{
				costPlan.setDistributionPlan1Str("Y");
				costPlan.setDistributionPlan2Str("Y");
				costPlan.setDistributionPlan3Str("Y");
				costPlan.setDistributionPlan4Str("Y");
				costPlan.setDistributionPlan5Str("Y");
				costPlan.setDistributionPlan6Str("Y");
			}
			else
			{
				costPlan.setDistributionPlan7Str("Y");
				costPlan.setDistributionPlan8Str("Y");
				costPlan.setDistributionPlan9Str("Y");
				costPlan.setDistributionPlan10Str("Y");
				costPlan.setDistributionPlan11Str("Y");
				costPlan.setDistributionPlan12Str("Y");
			}
			
			costPlan.setId(-1);
		}
		if (departmentId==null)
		{
			List<MDepartment> departL = departmentList();
			if (departL.size()>0)
			{
				departmentId = departL.get(0).getDeptCode();
				
			}
		}
		
		costPlan.setDepartmentId(departmentId);
		costPlan.setExpensesSupper(expensesSupper);
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter1= new SimpleDateFormat("yyyy");
		String year = formatter1.format(currentDate.getTime());
		if (costYear == null)
		{
			costYear = Integer.parseInt(year);
		}
		System.out.print("costYear:  "+costYear);
		
		costPlan.setCostYear(costYear);
		
		int order = 0;
		String column = "EXPENSES_CODE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		List<CostPlan> costPlanList = new ArrayList<CostPlan>();
		
		costPlanList = costPlanDAO.getCostPlanFilter(costPlan, column, order);
		
		List<CostExpenses> expensesSuperList = costExpensesDAO.getExpensesSuperAll();
		model.addAttribute("expensesSupperList", 	expensesSuperList);
		model.addAttribute("expensesSuper", 		expensesSupper);
		model.addAttribute("checkAll", 		checkAll);
		
		// Lay ten file export
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "KeHoachChiPhi_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
				
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("costYear", costYear);
		model.addAttribute("costPlan", costPlan);
		model.addAttribute("costPlanList", costPlanList);
		
		/*List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("ROOT_NO");
		model.addAttribute("highlight", Helper.highLightCost(highlightConfigList, "item"));
		*/
		return "jspcost/costPlanList";
		
	}
	
	@RequestMapping(value="report")
    public void report(@RequestParam(required = false) String departmentId,
    		@RequestParam(required = false) Integer costYear,
    		@RequestParam(required = false) String expensesSupper,
    		@RequestParam(required = false) String expensesName,
    		ModelMap model, HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		
		CostPlan costPlan = new CostPlan();
		List<MDepartment> departL = departmentList();
		String departName="";
		if (departmentId==null)
		{
			if (departL.size()>0)
			{
				departmentId = departL.get(0).getDeptCode();
				departName   = departL.get(0).getDeptName();
			}
		}
		else
		{
			for (MDepartment mDepartment : departL) {
				if (mDepartment.getDeptCode().equals(departmentId))
				{
					departName = mDepartment.getDeptName();
				}
			}
		}
		
		//costPlan.setExpensesName(expensesName);
		costPlan.setDepartmentId(departmentId);
		//costPlan.setExpensesSupper(expensesSupper);
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter1= new SimpleDateFormat("yyyy");
		String year = formatter1.format(currentDate.getTime());
		if (costYear == null)
		{
			costYear = Integer.parseInt(year);
		}
		System.out.print("costYear:  "+costYear);
		
		costPlan.setCostYear(costYear);
		
		List<CostPlan> costPlanList = new ArrayList<CostPlan>();
		
		costPlanList = costPlanDAO.getCostPlanFilter(costPlan, "EXPENSES_CODE", 1);
		// Lay ten file export
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
			
		Map<String, Object> parameters = new HashMap<String, Object>();
				
		parameters.put("costYear", costYear);
		parameters.put("deptName", departName);
		parameters.put("lastYear", costYear-1);
		parameters.put("title", "BẢNG ĐĂNG KÝ KẾ HOẠCH CHI PHÍ NĂM "+costYear);
		parameters.put("titlePB", "Đơn vị triển khai: "+departName);
		parameters.put("headerDaTHLast", "Ước TH "+(costYear-1));
		/*if (type.equals("Excel")) {*/
			//Doc du lieu do vao file report 
			InputStream inputStream = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/CostPlan.jrxml")); 
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(costPlanList);	
			//Map parameters = new HashMap();
			
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
			
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment; filename=\"BaoCaoKeHoachNam_"+dateNow+".xls\"");
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.OFFSET_X, 0);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.exportReport();	
			response.getOutputStream().flush();
			response.getOutputStream().close();
		/*}
		else
		{
			//Doc du lieu do vao file report 
			InputStream inputStream = new FileInputStream ("E://Work/Workspace/lbs/lbs-web/WebContent/report/ReportDungDo.jrxml");
			InputStream inputStream = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/BaoCaoDungDo.jrxml")); 
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);	
			//Map parameters = new HashMap();
			
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=\"BaoCaoDungDo_"+dateNow+".pdf\"");
			response.setCharacterEncoding("UTF-8");
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, "UTF-8");
			String fontFamily ="http://"+request.getLocalAddr()+":"+request.getLocalPort()+"/font/arial.ttf";
			JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", fontFamily);
			
			exporter.exportReport();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}*/

		
	}
	
	//Phan bo chi phi cho cac thang
	@RequestMapping(value="phanbo", method=RequestMethod.GET)
    public String phanboForm(@RequestParam(required = false) Integer id,@RequestParam(required = false) String code,
    		@RequestParam(required = false) String departmentId,
    		@RequestParam(required = false) String costYear,
    		ModelMap model ,HttpServletRequest request ) {
		
		List<SYS_PARAMETER> sysParemeterTitle = costPlanDAO.titleForm("PBO");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleP", sysParemeterTitle.get(0).getValue());
		}
		CostPlan newcostPlan;
		List<CostExpenses> expensesNameList = new ArrayList<CostExpenses>();
		List<CostExpenses> expensesSuperList = costExpensesDAO.getExpensesSuperAll();
		String expensesSup ="";
		expensesSup = code;
		newcostPlan = new CostPlan();
		
		if (costYear==null)
		{
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter1= new SimpleDateFormat("yyyy");
			costYear = formatter1.format(currentDate.getTime());
		}
		if (departmentId==null)
		{
			List<MDepartment> departL = departmentList();
			if (departL.size()>0)
			{
				departmentId = departL.get(0).getDeptCode();
			}
		}
		
		if (code !=null)
		{
			expensesNameList = costExpensesDAO.getExpensesChild(code);
			if (expensesNameList.size()<1)
			{
				CostExpenses exp = new CostExpenses();
				exp= costExpensesDAO.checkExit(code);
				expensesNameList.add(exp);
				model.addAttribute("expensesCode", 		expensesSup);
			}
				
		}
		List<CostPlan> planSameList = PlanSameList(departmentId,Integer.parseInt(costYear), code);
		model.addAttribute("costPlanList", 			planSameList);
		
		model.addAttribute("costYear", 	costYear);
		model.addAttribute("costPlan", 	newcostPlan);
		model.addAttribute("departmentId", 	departmentId);
		model.addAttribute("expensesSupper", 		code);
		model.addAttribute("expensesSupperList", 	expensesSuperList);
		model.addAttribute("expensesNameList", 	expensesNameList);
		
		/*List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("ROOT_NO");
		model.addAttribute("highlight", Helper.highLightCost(highlightConfigList, "item"));
		*/
		return "jspcost/costPlanPB";
	}
	
	@RequestMapping(value="phanbo", method=RequestMethod.POST)
    public String phanboFormPost(@ModelAttribute("costPlan") @Valid CostPlan costPlan, BindingResult result, 
    		@RequestParam(required = false) String action,
    		@RequestParam(required = true) String departmentId,
    		@RequestParam(required = false) Integer costYear,
    		@RequestParam(required = false) String expensesSupper,
    		@RequestParam(required = false) String expensesCode,
    		@RequestParam(required = false) String distributionPlan1Str,
    		@RequestParam(required = false) String distributionPlan2Str,
    		@RequestParam(required = false) String distributionPlan3Str,
    		@RequestParam(required = false) String distributionPlan4Str,
    		@RequestParam(required = false) String distributionPlan5Str,
    		@RequestParam(required = false) String distributionPlan6Str,
    		@RequestParam(required = false) String distributionPlan7Str,
    		@RequestParam(required = false) String distributionPlan8Str,
    		@RequestParam(required = false) String distributionPlan9Str,
    		@RequestParam(required = false) String distributionPlan10Str,
    		@RequestParam(required = false) String distributionPlan11Str,
    		@RequestParam(required = false) String distributionPlan12Str,
    		
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		List<SYS_PARAMETER> sysParemeterTitle = costPlanDAO.titleForm("PBO");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
			
		}
		System.out.println("action: "+action);
		
		
		List<CostExpenses> expensesSuperList = costExpensesDAO.getExpensesSuperAll();
		model.addAttribute("expensesSupperList", 	expensesSuperList);
		
		if (expensesSupper!=null && !expensesSupper.equals(""))
		{
			List<CostExpenses> expensesNameList = costExpensesDAO.getExpensesChild(expensesSupper);
			model.addAttribute("expensesNameList", 	expensesNameList);
		}
		
		List<CostPlan> planSameList = new ArrayList<CostPlan>();
		planSameList = PlanSameList(departmentId,costYear, expensesCode );
		model.addAttribute("costPlanList", 			planSameList);
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String expensesS = expensesSupper;
		if (result.hasErrors()) {
			model.addAttribute("costPlan", costPlan);
			model.addAttribute("departmentId", departmentId);
			model.addAttribute("costYear", costYear);
			model.addAttribute("expensesSupper", expensesSupper);
			model.addAttribute("expensesCode", expensesCode);
			
			saveMessageKey(request, "costPlan.InsertError");
			model.addAttribute("titleP", title);
			return "jspcost/costPlanPB";
		}
		else
		{
			if (expensesSupper.equals(expensesCode))
			{
				expensesSupper="0";
			}
			CostPlan costPlanExit = costPlanDAO.checkExit(costPlan.getDepartmentId(),costPlan.getCostYear(), expensesSupper,costPlan.getExpensesCode());
			costPlan.setDepartmentId(departmentId);
			costPlan.setCostYear(costYear);
			costPlan.setExpensesSupper(expensesSupper);
			costPlan.setExpensesCode(expensesCode);
			Integer count = costPlan.getQuantity();
			if (count==null||(count!=null&&count==0))
			{
				count=1;
			}
			Double unitCost = 0.0;
			
			if (costPlan.getDistributionYear()!=null && costPlan.getDistributionYear()>0 )
			{
				unitCost = costPlan.getDistributionYear()/count;
			}
			
			if (costPlan.getDistributionPlan1Str()!=null)
			{
				costPlan.setDistributionPlan1(unitCost);
			}
			
			if (costPlan.getDistributionPlan2Str()!=null)
			{
				costPlan.setDistributionPlan2(unitCost);
			}
			
			if (costPlan.getDistributionPlan3Str()!=null)
			{
				costPlan.setDistributionPlan3(unitCost);
			}
			
			if (costPlan.getDistributionPlan4Str()!=null)
			{
				costPlan.setDistributionPlan4(unitCost);
			}
			
			if (costPlan.getDistributionPlan5Str()!=null)
			{
				costPlan.setDistributionPlan5(unitCost);
			}
			
			if (costPlan.getDistributionPlan6Str()!=null)
			{
				costPlan.setDistributionPlan6(unitCost);
			}
			
			if (costPlan.getDistributionPlan7Str()!=null)
			{
				costPlan.setDistributionPlan7(unitCost);
			}
			
			if (costPlan.getDistributionPlan8Str()!=null)
			{
				costPlan.setDistributionPlan8(unitCost);
			}
			
			if (costPlan.getDistributionPlan9Str()!=null)
			{
				costPlan.setDistributionPlan9(unitCost);
			}
			
			if (costPlan.getDistributionPlan10Str()!=null)
			{
				costPlan.setDistributionPlan10(unitCost);
			}
			
			if (costPlan.getDistributionPlan11Str()!=null)
			{
				costPlan.setDistributionPlan11(unitCost);
			}
			
			if (costPlan.getDistributionPlan12Str()!=null)
			{
				costPlan.setDistributionPlan12(unitCost);
			}
			
			
			if (costPlan.getId() == null) {
				if (costPlanExit!=null)
				{
					model.addAttribute("costPlan", costPlan);
					model.addAttribute("departmentId", departmentId);
					model.addAttribute("costYear", costYear);
					model.addAttribute("expensesSupper", expensesS);
					model.addAttribute("expensesCode", expensesCode);
					saveMessageKey(request, "costPlan.exits");
					model.addAttribute("titleP", title);
					return "jspcost/costPlanPB";
				}
				costPlan.setCreatedBy(username);
				costPlan.setCreateDate(new Date());
				Double doneToLasty = 0.0;
				CostPlan lastYear = costPlanDAO.checkExit(costPlan.getDepartmentId(),costPlan.getCostYear()-1, expensesSupper,costPlan.getExpensesCode());
				if (lastYear!=null)
				{
					doneToLasty = 	(lastYear.getDeliveryPlan1()==null?0.0:lastYear.getDeliveryPlan1())+
									(lastYear.getDeliveryPlan2()==null?0.0:lastYear.getDeliveryPlan2())+
									(lastYear.getDeliveryPlan3()==null?0.0:lastYear.getDeliveryPlan3())+
									(lastYear.getDeliveryPlan4()==null?0.0:lastYear.getDeliveryPlan4())+
									(lastYear.getDeliveryPlan5()==null?0.0:lastYear.getDeliveryPlan5())+
									(lastYear.getDeliveryPlan6()==null?0.0:lastYear.getDeliveryPlan6())+
									(lastYear.getDeliveryPlan7()==null?0.0:lastYear.getDeliveryPlan7())+
									(lastYear.getDeliveryPlan8()==null?0.0:lastYear.getDeliveryPlan8())+
									(lastYear.getDeliveryPlan9()==null?0.0:lastYear.getDeliveryPlan9())+
									(lastYear.getDeliveryPlan10()==null?0.0:lastYear.getDeliveryPlan10())+
									(lastYear.getDeliveryPlan11()==null?0.0:lastYear.getDeliveryPlan12())+
									(lastYear.getDeliveryPlan12()==null?0.0:lastYear.getDeliveryPlan12());
					
					costPlan.setDoneToLasty(doneToLasty);
																													
				}
				costPlanDAO.insertSelective(costPlan);
				//Insert hoac update chi muc cha
				updateCostSuper(expensesSupper,departmentId,costYear);
				
				saveMessageKey(request, "costPlan.InsertSuccess");
				
			} 
			//thuc thi caulenh load lại form nào khi chon save hay saveAndAdd
			if (action.equals("saveAndAdd"))
			{	
				CostPlan cosNew = new CostPlan();
				model.addAttribute("costPlan", cosNew);
				model.addAttribute("titleP", title);
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter1= new SimpleDateFormat("yyyy");
				String costYearN = formatter1.format(currentDate.getTime());
				System.out.println("Chay vao day");
				model.addAttribute("costYear", 	costYearN);
				model.addAttribute("expensesSupper", "");
				model.addAttribute("expensesCode", "");
				
				List<CostPlan> costListN = new ArrayList<CostPlan>();
				model.addAttribute("costPlanList", costListN);
				
				model.addAttribute("expensesNameList", 	new ArrayList<CostExpenses>() );
				
				
				return "jspcost/costPlanPB";
			}
			
		}
		
		return "redirect:list.htm";
	}
	
	
	//Them sua chi phí cho cac thang
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String cableForm(@RequestParam(required = false) Integer id,
    		@RequestParam(required = false) String code,
    		@RequestParam(required = false) String departmentId,
    		@RequestParam(required = false) String costYear,
    		ModelMap model ,HttpServletRequest request ) {
		
		List<SYS_PARAMETER> sysParemeterTitle = costPlanDAO.titleForm("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		CostPlan costPlan;
		List<CostExpenses> expensesNameList = new ArrayList<CostExpenses>();
		List<CostExpenses> expensesSuperList = costExpensesDAO.getExpensesSuper();
		String expensesSup ="";
		String expensesSupName = "";
		if (costYear==null)
		{
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter1= new SimpleDateFormat("yyyy");
			costYear = formatter1.format(currentDate.getTime());
		}
		if (departmentId==null)
		{
			List<MDepartment> departL = departmentList();
			if (departL.size()>0)
			{
				departmentId = departL.get(0).getDeptCode();
			}
		}
		expensesSup = code;
		if (id == null) {
			costPlan = new CostPlan();
			if (code !=null)
			{
				costPlan.setExpensesSupper(code);
				expensesNameList = costExpensesDAO.getExpensesChild(code);
				
				List<CostPlan> planSameList = PlanSameList(departmentId,Integer.parseInt(costYear), code);
				model.addAttribute("costPlanList", 			planSameList);
				
			}
		} else {
			costPlan = costPlanDAO.selectById(id);
			if (costPlan!=null)
			{
				costYear= costPlan.getCostYear().toString();
				expensesSup = costPlan.getExpensesSupper();
				expensesSupName = costPlan.getExpensesSupperName();
				departmentId = costPlan.getDepartmentId();
				List<CostExpenses> childList = costExpensesDAO.getExpensesBySupper(costPlan.getExpensesCode());
				if (childList.size()>0)
				{
					saveMessageKey(request, "costPlan.NotEditSuper");
						return "redirect:list.htm";
					
				}
				else
				{
					
					if (expensesSup.equals("0"))
					{
						expensesSup = costPlan.getExpensesCode();
						expensesSupName = costPlan.getExpensesName();
						CostExpenses exp = new CostExpenses();
						exp= costExpensesDAO.checkExit(costPlan.getExpensesCode());
						expensesNameList.add(exp);
						model.addAttribute("expensesCode", 		expensesSup);
					}
					else
					{
						expensesNameList = costExpensesDAO.getExpensesChild(costPlan.getExpensesSupper());
						model.addAttribute("expensesCode", 			costPlan.getExpensesCode());
						
					}
					model.addAttribute("costYearOld", 			costPlan.getCostYear());
					model.addAttribute("expensesSuperOld", 		costPlan.getExpensesSupper());
					model.addAttribute("departmentIdOld", 		costPlan.getDepartmentId());
					model.addAttribute("costYearOld", 			costPlan.getCostYear());
					List<CostPlan> planSameList = PlanSameList(costPlan.getDepartmentId(),costPlan.getCostYear(), costPlan.getExpensesCode());
					model.addAttribute("costPlanList", 			planSameList);
				}
			}
		}
		model.addAttribute("costPlan", 	costPlan);
		
		
		model.addAttribute("costYear", 	costYear);
		model.addAttribute("departmentId", 	departmentId);
		System.out.println("expensesSupper"+code);
		System.out.println("departmentId"+departmentId);
		model.addAttribute("expensesSupper", 		code);
		
		
		model.addAttribute("expensesSupperList", 	expensesSuperList);
		model.addAttribute("expensesNameList", 	expensesNameList);
		model.addAttribute("expensesSupperName", 		expensesSupName);
		
		/*List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("ROOT_NO");
		model.addAttribute("highlight", Helper.highLightCost(highlightConfigList, "item"));
		*/
		return "jspcost/costPlanForm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String cableFormPost(@ModelAttribute("costPlan") @Valid CostPlan costPlan, BindingResult result, 
    		@RequestParam(required = false) String action,
    		@RequestParam(required = true) String departmentId,
    		@RequestParam(required = false) Integer costYear,
    		@RequestParam(required = false) String expensesSupper,
    		@RequestParam(required = false) String expensesCode,
    		@RequestParam(required = false) String expensesSuperOld,
    		@RequestParam(required = false) String departmentIdOld,
    		@RequestParam(required = false) Integer costYearOld,
    		
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		List<SYS_PARAMETER> sysParemeterTitle = costPlanDAO.titleForm("ADD");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
			
		}
		System.out.println("action: "+action);
		System.out.println("quality: "+costPlan.getQuantity());
		
		/*List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("ROOT_NO");
		model.addAttribute("highlight", Helper.highLightCost(highlightConfigList, "item"));
		*/
		List<CostExpenses> expensesSuperList = costExpensesDAO.getExpensesSuper();
		model.addAttribute("expensesSupperList", 	expensesSuperList);
		
		if (expensesSupper!=null && !expensesSupper.equals(""))
		{
			List<CostExpenses> expensesNameList = costExpensesDAO.getExpensesChild(expensesSupper);
			model.addAttribute("expensesNameList", 	expensesNameList);
		}
		
		List<CostPlan> planSameList = PlanSameList(departmentId,costYear, expensesCode );
		model.addAttribute("costPlanList", 			planSameList);
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String expensesS = expensesSupper;
		if (result.hasErrors()) {
			model.addAttribute("costPlan", costPlan);
			model.addAttribute("departmentId", departmentId);
			model.addAttribute("costYear", costYear);
			model.addAttribute("expensesSupper", expensesSupper);
			model.addAttribute("expensesCode", expensesCode);
			model.addAttribute("expensesSuperOld", expensesSuperOld);
			model.addAttribute("departmentIdOld", departmentIdOld);
			model.addAttribute("costYearOld", costYearOld);
			
			saveMessageKey(request, "costPlan.InsertError");
			model.addAttribute("titleF", title);
			return "jspcost/costPlanForm";
		}
		else
		{
			if (expensesSupper.equals(expensesCode))
			{
				expensesSupper="0";
			}
			CostPlan costPlanExit = costPlanDAO.checkExit(costPlan.getDepartmentId(),costPlan.getCostYear(), expensesSupper,costPlan.getExpensesCode());
			costPlan.setDepartmentId(departmentId);
			costPlan.setCostYear(costYear);
			costPlan.setExpensesSupper(expensesSupper);
			costPlan.setExpensesCode(expensesCode);
			if (costPlan.getId() == null) {
				if (costPlanExit!=null)
				{
					model.addAttribute("costPlan", costPlan);
					model.addAttribute("departmentId", departmentId);
					model.addAttribute("costYear", costYear);
					model.addAttribute("expensesSupper", expensesS);
					model.addAttribute("expensesCode", expensesCode);
					model.addAttribute("expensesSuperOld", expensesSuperOld);
					model.addAttribute("departmentIdOld", departmentIdOld);
					model.addAttribute("costYearOld", costYearOld);
					saveMessageKey(request, "costPlan.exits");
					model.addAttribute("titleF", title);
					return "jspcost/costPlanForm";
				}
				costPlan.setCreatedBy(username);
				costPlan.setCreateDate(new Date());
				
				Double doneToLasty = 0.0;
				CostPlan lastYear = costPlanDAO.checkExit(costPlan.getDepartmentId(),costPlan.getCostYear()-1, expensesSupper,costPlan.getExpensesCode());
				if (lastYear!=null)
				{
					doneToLasty = 	(lastYear.getDeliveryPlan1()==null?0.0:lastYear.getDeliveryPlan1())+
									(lastYear.getDeliveryPlan2()==null?0.0:lastYear.getDeliveryPlan2())+
									(lastYear.getDeliveryPlan3()==null?0.0:lastYear.getDeliveryPlan3())+
									(lastYear.getDeliveryPlan4()==null?0.0:lastYear.getDeliveryPlan4())+
									(lastYear.getDeliveryPlan5()==null?0.0:lastYear.getDeliveryPlan5())+
									(lastYear.getDeliveryPlan6()==null?0.0:lastYear.getDeliveryPlan6())+
									(lastYear.getDeliveryPlan7()==null?0.0:lastYear.getDeliveryPlan7())+
									(lastYear.getDeliveryPlan8()==null?0.0:lastYear.getDeliveryPlan8())+
									(lastYear.getDeliveryPlan9()==null?0.0:lastYear.getDeliveryPlan9())+
									(lastYear.getDeliveryPlan10()==null?0.0:lastYear.getDeliveryPlan10())+
									(lastYear.getDeliveryPlan11()==null?0.0:lastYear.getDeliveryPlan12())+
									(lastYear.getDeliveryPlan12()==null?0.0:lastYear.getDeliveryPlan12());
					
					costPlan.setDoneToLasty(doneToLasty);
																													
				}
				
				costPlanDAO.insertSelective(costPlan);
				//Insert hoac update chi muc cha
				updateCostSuper(expensesSupper,departmentId,costYear);
				
				saveMessageKey(request, "costPlan.InsertSuccess");
				
			} else {
				if (costPlanExit!=null)
				{
					if (costPlanExit.getId().intValue()!=costPlan.getId().intValue())
					{
						model.addAttribute("costPlan", costPlan);
						model.addAttribute("departmentId", departmentId);
						model.addAttribute("costYear", costYear);
						model.addAttribute("expensesSupper", expensesS);
						model.addAttribute("expensesCode", expensesCode);
						model.addAttribute("expensesSuperOld", expensesSuperOld);
						model.addAttribute("departmentIdOld", departmentIdOld);
						model.addAttribute("costYearOld", costYearOld);
						saveMessageKey(request, "costPlan.exits");
						model.addAttribute("titleF", title);
						return "jspcost/costPlanForm";
					}
				}
				costPlan.setModifiedBy(username);
				costPlan.setModifyDate(new Date());
				
				costPlanDAO.updateByPrimaryKey(costPlan);
				if (!expensesSuperOld.equals(costPlan.getExpensesSupper())|| !departmentIdOld.equals(costPlan.getDepartmentId())|| costYearOld != costPlan.getCostYear())
				{
					updateCostSuper(expensesSuperOld,departmentId,costYear);
				}
				//Insert hoac update chi muc cha
				updateCostSuper(expensesSupper,departmentId,costYear);
				saveMessageKey(request, "costPlan.UpdateSuccess");	

			}
			//thuc thi caulenh load lại form nào khi chon save hay saveAndAdd
			if (action.equals("saveAndAdd"))
			{	
				CostPlan cosNew = new CostPlan();
				model.addAttribute("costPlan", cosNew);
				model.addAttribute("titleF", title);
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter1= new SimpleDateFormat("yyyy");
				String costYearN = formatter1.format(currentDate.getTime());
				System.out.println("Chay vao day");
				model.addAttribute("costYear", 	costYearN);
				model.addAttribute("expensesSupper", "");
				model.addAttribute("expensesCode", "");
				
				List<CostPlan> costListN = new ArrayList<CostPlan>();
				model.addAttribute("costPlanList", costListN);
				
				model.addAttribute("expensesNameList", 	new ArrayList<CostExpenses>() );
				
				
				return "jspcost/costPlanForm";
			}
			
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="ajax/PlanSameList")
    public @ResponseBody  List<CostPlan> PlanSameList(String departmentId,Integer costYear,String expensesCode)
	{
		try
		{
			return costPlanDAO.getPlanSameList(departmentId, costYear, expensesCode);
		}
		catch(Exception exp)
		{
			return null;
		}
		
	}
	
	private void updateCostSuper(String expensesSupper, String departmentId, Integer costYear) {
		
		
		
			CostExpenses expenses = costExpensesDAO.checkExit(expensesSupper);
			if (expenses!=null)
			{
				costPlanDAO.updateCostSupper(expensesSupper,expenses.getExpensesSuper(),departmentId,costYear);
				
				if (!expenses.getExpensesSuper().equals("0"))
				{
					updateCostSuper(expenses.getExpensesSuper(),departmentId,costYear);
				}
			}
		
	}

	@RequestMapping(value="ajax/deletecostPlan")
    public @ResponseBody 
    Integer deletecostPlan(Integer id,String expensesSupper,String departmentId,Integer costYear, HttpServletRequest request, HttpServletResponse response) {	
		
		costPlanDAO.deleteCostPlan(id);
		//updateCostSuper(expensesSupper,departmentId,costYear);
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
	
	@RequestMapping(value="ajax/getExpensesChild")
    public @ResponseBody 
    List<CostExpenses> getExpensesChild(String expensesSupper, HttpServletRequest request, HttpServletResponse response) {	
		List<CostExpenses> expensesChildList = costExpensesDAO.getExpensesChild(expensesSupper);

		return 	expensesChildList;
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		
		List<SYS_PARAMETER> sysParemeterTitle = costPlanDAO.titleForm("UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter1= new SimpleDateFormat("yyyy");
		String year = formatter1.format(currentDate.getTime());
		model.addAttribute("costYear", 		year);
		return "jspcost/costPlanUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath,@RequestParam(required = true) String departmentId,
    		@RequestParam(required = false) Integer costYear, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		List<SYS_PARAMETER> sysParemeterTitle = costPlanDAO.titleForm("UPLOAD");
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
				importContent(sheetData,departmentId,costYear,model,request);
				
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		model.addAttribute("costYear", 		costYear);
		model.addAttribute("departmentId", 		departmentId);
		return "jspcost/costPlanUpload";
		
	}

	private String importContent(List sheetData, String departmentId,
			Integer costYear, Model model, HttpServletRequest request) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> sysParemeterTitle = costExpensesDAO.titleForm("UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		List<CostPlan> successList = new ArrayList<CostPlan>();
		List<CostPlan> failedList = new ArrayList<CostPlan>();
		List<CostPlan> success = new ArrayList<CostPlan>();
		
		String expensesCode;
		String expensesName;
		//String workId;
		String deliveryYearStr;
		String distributionYearStr;
		String distributionPlan1Str;
		String distributionPlan2Str;
		String distributionPlan3Str;
		String distributionPlan4Str;
		String distributionPlan5Str;
		String distributionPlan6Str;
		String distributionPlan7Str;
		String distributionPlan8Str;
		String distributionPlan9Str;
		String distributionPlan10Str;
		String distributionPlan11Str;
		String distributionPlan12Str;
		/*String deliveryPlan1Str;
		String deliveryPlan2Str;
		String deliveryPlan3Str;
		String deliveryPlan4Str;
		String deliveryPlan5Str;
		String deliveryPlan6Str;
		String deliveryPlan7Str;
		String deliveryPlan8Str;
		String deliveryPlan9Str;
		String deliveryPlan10Str;
		String deliveryPlan11Str;
		String deliveryPlan12Str;*/
		
		String description;
		
		
		Double deliveryYear;
		Double distributionYear;
		Double distributionPlan1;
		Double distributionPlan2;
		Double distributionPlan3;
		Double distributionPlan4;
		Double distributionPlan5;
		Double distributionPlan6;
		Double distributionPlan7;
		Double distributionPlan8;
		Double distributionPlan9;
		Double distributionPlan10;
		Double distributionPlan11;
		Double distributionPlan12;
		/*Double deliveryPlan1;
		Double deliveryPlan2;
		Double deliveryPlan3;
		Double deliveryPlan4;
		Double deliveryPlan5;
		Double deliveryPlan6;
		Double deliveryPlan7;
		Double deliveryPlan8;
		Double deliveryPlan9;
		Double deliveryPlan10;
		Double deliveryPlan11;
		Double deliveryPlan12;*/
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 18) {
        		saveMessageKey(request, "Định dạng tệp không đúng");
        		model.addAttribute("costYear", 		costYear);
        		model.addAttribute("departmentId", 		departmentId);
        		return "jspcost/costPlanUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		CostPlan costPlan;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			costPlan = new CostPlan();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=17; j++) {
     					data.add("");
     				}
        			expensesCode				= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			expensesName				= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			deliveryYearStr				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			distributionYearStr			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			distributionPlan1Str		= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			distributionPlan2Str		= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			distributionPlan3Str		= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			distributionPlan4Str		= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			distributionPlan5Str		= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			distributionPlan6Str		= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			distributionPlan7Str		= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			distributionPlan8Str		= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			distributionPlan9Str		= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			distributionPlan10Str		= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			distributionPlan11Str		= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			distributionPlan12Str		= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			description					= data.get(17).toString().trim().equals("")?"":data.get(17).toString().trim();
        			/*deliveryPlan1Str			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			deliveryPlan2Str			= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			deliveryPlan3Str			= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			deliveryPlan4Str			= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			deliveryPlan5Str			= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			deliveryPlan7Str			= data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
        			deliveryPlan8Str			= data.get(19).toString().trim().equals("")?null:data.get(19).toString().trim();
        			deliveryPlan9Str			= data.get(21).toString().trim().equals("")?null:data.get(21).toString().trim();
        			deliveryPlan10Str			= data.get(23).toString().trim().equals("")?null:data.get(23).toString().trim();
        			deliveryPlan11Str			= data.get(25).toString().trim().equals("")?null:data.get(25).toString().trim();
        			deliveryPlan12Str			= data.get(27).toString().trim().equals("")?null:data.get(27).toString().trim();
        			*/
        			costPlan.setExpensesCode(expensesCode);
        			costPlan.setExpensesName(expensesName);
        			costPlan.setDescription(description);
        			costPlan.setCostYear(costYear);
        			costPlan.setDepartmentId(departmentId);
        			costPlan.setDistributionPlan1Str(distributionPlan1Str);
        			costPlan.setDistributionPlan2Str(distributionPlan2Str);
        			costPlan.setDistributionPlan3Str(distributionPlan3Str);
        			costPlan.setDistributionPlan4Str(distributionPlan4Str);
        			costPlan.setDistributionPlan5Str(distributionPlan5Str);
        			costPlan.setDistributionPlan6Str(distributionPlan6Str);
        			costPlan.setDistributionPlan7Str(distributionPlan7Str);
        			costPlan.setDistributionPlan8Str(distributionPlan8Str);
        			costPlan.setDistributionPlan9Str(distributionPlan9Str);
        			costPlan.setDistributionPlan10Str(distributionPlan10Str);
        			costPlan.setDistributionPlan11Str(distributionPlan11Str);
        			costPlan.setDistributionPlan12Str(distributionPlan12Str);
        			costPlan.setDeliveryYearStr(deliveryYearStr);
        			costPlan.setDistributionYearStr(distributionYearStr);
        			/*costPlan.setDeliveryPlan1Str(deliveryPlan1Str);
        			costPlan.setDeliveryPlan2Str(deliveryPlan2Str);
        			costPlan.setDeliveryPlan3Str(deliveryPlan3Str);
        			costPlan.setDeliveryPlan4Str(deliveryPlan4Str);
        			costPlan.setDeliveryPlan5Str(deliveryPlan5Str);
        			costPlan.setDeliveryPlan6Str(deliveryPlan6Str);
        			costPlan.setDeliveryPlan7Str(deliveryPlan7Str);
        			costPlan.setDeliveryPlan8Str(deliveryPlan8Str);
        			costPlan.setDeliveryPlan9Str(deliveryPlan9Str);
        			costPlan.setDeliveryPlan10Str(deliveryPlan10Str);
        			costPlan.setDeliveryPlan11Str(deliveryPlan11Str);
        			costPlan.setDeliveryPlan12Str(deliveryPlan12Str);
        			*/
        			
        			if (expensesCode==null || expensesName == null 
							|| expensesCode.length() >= 20
							|| expensesName.length() >= 250
							|| (description != null && description.length() >= 500)
							|| (deliveryYearStr != null && deliveryYearStr.length() >= 20)
							|| (distributionYearStr != null && distributionYearStr.length() >= 20)
							|| (distributionPlan1Str != null && distributionPlan1Str.length() >= 20)
							|| (distributionPlan2Str != null && distributionPlan2Str.length() >= 20)
							|| (distributionPlan3Str != null && distributionPlan3Str.length() >= 20)
							|| (distributionPlan4Str != null && distributionPlan4Str.length() >= 20)
							|| (distributionPlan5Str != null && distributionPlan5Str.length() >= 20)
							|| (distributionPlan6Str != null && distributionPlan6Str.length() >= 20)
							|| (distributionPlan7Str != null && distributionPlan7Str.length() >= 20)
							|| (distributionPlan8Str != null && distributionPlan8Str.length() >= 20)
							|| (distributionPlan9Str != null && distributionPlan9Str.length() >= 20)
							|| (distributionPlan10Str != null && distributionPlan10Str.length() >= 20)
							|| (distributionPlan11Str != null && distributionPlan11Str.length() >= 20)
							|| (distributionPlan12Str != null && distributionPlan12Str.length() >= 20)
							/*|| (deliveryPlan1Str != null && deliveryPlan1Str.length() >= 20)
							|| (deliveryPlan2Str != null && deliveryPlan2Str.length() >= 20)
							|| (deliveryPlan3Str != null && deliveryPlan3Str.length() >= 20)
							|| (deliveryPlan4Str != null && deliveryPlan4Str.length() >= 20)
							|| (deliveryPlan5Str != null && deliveryPlan5Str.length() >= 20)
							|| (deliveryPlan6Str != null && deliveryPlan6Str.length() >= 20)
							|| (deliveryPlan7Str != null && deliveryPlan7Str.length() >= 20)
							|| (deliveryPlan8Str != null && deliveryPlan8Str.length() >= 20)
							|| (deliveryPlan9Str != null && deliveryPlan9Str.length() >= 20)
							|| (deliveryPlan10Str != null && deliveryPlan10Str.length() >= 20)
							|| (deliveryPlan10Str != null && deliveryPlan10Str.length() >= 20)
							|| (deliveryPlan12Str != null && deliveryPlan12Str.length() >= 20)*/
							
					) {
						
						error = true;
					}
        			
        			/*try
        			{*/
        				int count=0;
        				
        				deliveryYear= deliveryYearStr==null?0.0:Double.parseDouble(deliveryYearStr);
        				
        				distributionYear= distributionYearStr==null?0.0:Double.parseDouble(distributionYearStr);
        				distributionPlan1= distributionPlan1Str==null?0.0:Double.parseDouble(distributionPlan1Str);
        				distributionPlan2= distributionPlan2Str==null?0.0:Double.parseDouble(distributionPlan2Str);
        				distributionPlan3= distributionPlan3Str==null?0.0:Double.parseDouble(distributionPlan3Str);
        				distributionPlan4= distributionPlan4Str==null?0.0:Double.parseDouble(distributionPlan4Str);
        				distributionPlan5= distributionPlan5Str==null?0.0:Double.parseDouble(distributionPlan5Str);
        				distributionPlan6= distributionPlan6Str==null?0.0:Double.parseDouble(distributionPlan6Str);
        				distributionPlan7= distributionPlan7Str==null?0.0:Double.parseDouble(distributionPlan7Str);
        				distributionPlan8= distributionPlan8Str==null?0.0:Double.parseDouble(distributionPlan8Str);
        				distributionPlan9= distributionPlan9Str==null?0.0:Double.parseDouble(distributionPlan9Str);
        				distributionPlan10= distributionPlan10Str==null?0.0:Double.parseDouble(distributionPlan10Str);
        				distributionPlan11= distributionPlan11Str==null?0.0:Double.parseDouble(distributionPlan11Str);
        				distributionPlan12= distributionPlan12Str==null?0.0:Double.parseDouble(distributionPlan12Str);
        				/*deliveryPlan1= deliveryPlan1Str==null?0.0:Double.parseDouble(deliveryPlan1Str);
        				deliveryPlan2= deliveryPlan2Str==null?0.0:Double.parseDouble(deliveryPlan2Str);
        				deliveryPlan3= deliveryPlan3Str==null?0.0:Double.parseDouble(deliveryPlan3Str);
        				deliveryPlan4= deliveryPlan4Str==null?0.0:Double.parseDouble(deliveryPlan4Str);
        				deliveryPlan5= deliveryPlan5Str==null?0.0:Double.parseDouble(deliveryPlan5Str);
        				deliveryPlan6= deliveryPlan6Str==null?0.0:Double.parseDouble(deliveryPlan6Str);
        				deliveryPlan7= deliveryPlan7Str==null?0.0:Double.parseDouble(deliveryPlan7Str);
        				deliveryPlan8= deliveryPlan8Str==null?0.0:Double.parseDouble(deliveryPlan8Str);
        				deliveryPlan9= deliveryPlan9Str==null?0.0:Double.parseDouble(deliveryPlan9Str);
        				deliveryPlan10= deliveryPlan10Str==null?0.0:Double.parseDouble(deliveryPlan10Str);
        				deliveryPlan11= deliveryPlan11Str==null?0.0:Double.parseDouble(deliveryPlan11Str);
        				deliveryPlan12= deliveryPlan12Str==null?0.0:Double.parseDouble(deliveryPlan12Str);
        				*/
        				costPlan.setDistributionPlan1(distributionPlan1);
        				costPlan.setDistributionPlan2(distributionPlan2);
        				costPlan.setDistributionPlan3(distributionPlan3);
        				costPlan.setDistributionPlan4(distributionPlan4);
        				costPlan.setDistributionPlan5(distributionPlan5);
        				costPlan.setDistributionPlan6(distributionPlan6);
        				costPlan.setDistributionPlan7(distributionPlan7);
        				costPlan.setDistributionPlan8(distributionPlan8);
        				costPlan.setDistributionPlan9(distributionPlan9);
        				costPlan.setDistributionPlan10(distributionPlan10);
        				costPlan.setDistributionPlan11(distributionPlan11);
        				costPlan.setDistributionPlan12(distributionPlan12);
            			costPlan.setDeliveryYear(deliveryYear);
            			costPlan.setDistributionYear(distributionYear);
            			
            			count=	(distributionPlan1==0.0?0:1)+
            					(distributionPlan2==0.0?0:1)+
            					(distributionPlan3==0.0?0:1)+
            					(distributionPlan4==0.0?0:1)+
            					(distributionPlan5==0.0?0:1)+
            					(distributionPlan6==0.0?0:1)+
            					(distributionPlan7==0.0?0:1)+
            					(distributionPlan8==0.0?0:1)+
            					(distributionPlan9==0.0?0:1)+
            					(distributionPlan10==0.0?0:1)+
            					(distributionPlan11==0.0?0:1)+
            					(distributionPlan12==0.0?0:1);
            			costPlan.setQuantity(count);
            			/*costPlan.setDeliveryPlan1(deliveryPlan1);
            			costPlan.setDeliveryPlan2(deliveryPlan2);
            			costPlan.setDeliveryPlan3(deliveryPlan3);
            			costPlan.setDeliveryPlan4(deliveryPlan4);
            			costPlan.setDeliveryPlan5(deliveryPlan5);
            			costPlan.setDeliveryPlan6(deliveryPlan6);
            			costPlan.setDeliveryPlan7(deliveryPlan7);
            			costPlan.setDeliveryPlan8(deliveryPlan8);
            			costPlan.setDeliveryPlan9(deliveryPlan9);
            			costPlan.setDeliveryPlan10(deliveryPlan10);
            			costPlan.setDeliveryPlan11(deliveryPlan11);
            			costPlan.setDeliveryPlan12(deliveryPlan12);*/
            			
        			/*}
        			catch(Exception exp)
        			{
        				error = true;
        			}*/
        			
        			if (expensesCode == null && expensesName == null ) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(costPlan);
    					} else  {
    						successList.add(costPlan);
    					}
        			}
        		}
        	}
		}
		////Dang lam o day
		for (CostPlan cab: successList) {
			
			/*try {
				*/
				
				// Kiem tra chi tieu da ton tai trong cost expneses hay chua. Neu chua thi them moi vao cost expneses va cho cap nhat vao ke hoach. Neu co thi lay expenses code va kiem tra xem no la chi tieu cha hay con. Neu con thi cho cap nhat vao ke hoach, neu ko thi bo qua.
				CostExpenses costExpenses = costExpensesDAO.checkExit(cab.getExpensesCode());
				if (costExpenses == null)
				{
					//tim chi tieu cha
					String expensesSup ="";
					int last1 = cab.getExpensesCode().lastIndexOf(".");
					if (last1 >= 0)
					{
						expensesSup = cab.getExpensesCode().substring(0, last1);
					
					}
					else
					{
						int last2 = cab.getExpensesCode().lastIndexOf(",");
						if (last2 >= 0)
						{
							expensesSup = cab.getExpensesCode().substring(0, last2);
						
						}
						else
						{
							expensesSup = "0";
						}
					}
					System.out.println("Chi muc: " + cab.getExpensesCode() +"-- Chi muc cha: "+ expensesSup);
					CostExpenses cost = new CostExpenses();
					cost.setExpensesCode(cab.getExpensesCode());
					cost.setExpensesName(cab.getExpensesName());
					// Kiem tra chi tieu cha da ton tai hay chua? neu chua co thi chi tieu con nay la chi tieu cha.
					CostExpenses costSuper = costExpensesDAO.checkExit(expensesSup);
					if (costSuper == null )
					{
						cost.setExpensesSuper("0");
						cost.setRootNo(0);
						cab.setExpensesSupper("0");
					}
					else
					{
						
						cost.setExpensesSuper(expensesSup);
						cab.setExpensesSupper(expensesSup);
					}
					if (cost.getExpensesSuper().equals(cost.getExpensesCode()))
					{
						cost.setExpensesSuper("0");
						cab.setExpensesSupper("0");
					}
					costExpensesDAO.insertSelective(cost);
					if (!cost.getExpensesCode().equals("0"))
					{
						costExpensesDAO.updateRootNo(cost.getExpensesSuper(),1);
					}
					costPlanDAO.insertSelective(cab);
					if (!expensesSup.equals("0"))
					{
						updateCostSuper(expensesSup,cab.getDepartmentId(),cab.getCostYear());
						
					}
					System.out.println("Thêm mới hoan toan chi tieu");
					
				}
				else
				{
					
					//Kiem tra chi tieu la co phai la chi tieu con hoac vua la cha vua la con ko?
					cab.setExpensesSupper(costExpenses.getExpensesSuper());
					System.out.println("Super: "+ costExpenses.getExpensesSuper());
					List<CostExpenses> clist = costExpensesDAO.getExpensesBySupper(cab.getExpensesCode());
					if (clist.size()>0)
					{
					}
					else
					{
						Double doneToLasty = 0.0;
						CostPlan lastYear = costPlanDAO.checkExit(cab.getDepartmentId(),cab.getCostYear()-1, cab.getExpensesSupper(),cab.getExpensesCode());
						if (lastYear!=null)
						{
							doneToLasty = 	(lastYear.getDeliveryPlan1()==null?0.0:lastYear.getDeliveryPlan1())+
											(lastYear.getDeliveryPlan2()==null?0.0:lastYear.getDeliveryPlan2())+
											(lastYear.getDeliveryPlan3()==null?0.0:lastYear.getDeliveryPlan3())+
											(lastYear.getDeliveryPlan4()==null?0.0:lastYear.getDeliveryPlan4())+
											(lastYear.getDeliveryPlan5()==null?0.0:lastYear.getDeliveryPlan5())+
											(lastYear.getDeliveryPlan6()==null?0.0:lastYear.getDeliveryPlan6())+
											(lastYear.getDeliveryPlan7()==null?0.0:lastYear.getDeliveryPlan7())+
											(lastYear.getDeliveryPlan8()==null?0.0:lastYear.getDeliveryPlan8())+
											(lastYear.getDeliveryPlan9()==null?0.0:lastYear.getDeliveryPlan9())+
											(lastYear.getDeliveryPlan10()==null?0.0:lastYear.getDeliveryPlan10())+
											(lastYear.getDeliveryPlan11()==null?0.0:lastYear.getDeliveryPlan11())+
											(lastYear.getDeliveryPlan12()==null?0.0:lastYear.getDeliveryPlan12());
							
							cab.setDoneToLasty(doneToLasty);
																															
						}
						System.out.println("Khong la chi tieu cha");
						CostPlan costPlanExit = costPlanDAO.checkExit(cab.getDepartmentId(),cab.getCostYear(), cab.getExpensesSupper(),cab.getExpensesCode());
						
						if (costPlanExit == null) {
							System.out.println("Them moi");
							
							cab.setCreatedBy(username);
							cab.setCreateDate(new Date());
							
							costPlanDAO.insertSelective(cab);
							//Insert hoac update chi muc cha
							updateCostSuper( cab.getExpensesSupper(),cab.getDepartmentId(),cab.getCostYear());
							
						} else {
							cab.setId(costPlanExit.getId());
							cab.setModifiedBy(username);
							cab.setModifyDate(new Date());
							System.out.println("update");
							
							costPlanDAO.updateByPrimaryKeySelective(cab);
							//Insert hoac update chi muc cha
							if (!cab.getExpensesSupper().equals("0"))
							{
								updateCostSuper(cab.getExpensesSupper(),cab.getDepartmentId(),cab.getCostYear());
							}
							
						}
					}
					
				}
				success.add(cab);
			/*} catch (Exception ex) {
				failedList.add(cab);
			}*/
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
		String exportFileName = "LapKeHoachChiPhi_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
				
		model.addAttribute("costYear", 		costYear);
		model.addAttribute("departmentId", 		departmentId);
		return "jspcost/costPlanUpload";
	}

}
