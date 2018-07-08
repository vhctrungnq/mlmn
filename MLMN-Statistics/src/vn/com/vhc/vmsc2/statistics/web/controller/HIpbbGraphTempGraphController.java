package vn.com.vhc.vmsc2.statistics.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar; 
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
 
import vn.com.vhc.vmsc2.statistics.dao.HIpbbGraphTemplateGraphDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO; 
import vn.com.vhc.vmsc2.statistics.domain.HIpbbBwlist;
import vn.com.vhc.vmsc2.statistics.domain.HIpbbGraphTemplateGraph;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig; 
import vn.com.vhc.vmsc2.statistics.web.utils.ExportTools;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;
import bsh.ParseException;


@Controller
@RequestMapping("/cauhinh/ipbbGraph/*")
public class HIpbbGraphTempGraphController extends BaseController {
	@Autowired
	private HIpbbGraphTemplateGraphDAO ipbbGraphTempGraphDao;
	@Autowired 
	private HighlightConfigDAO highlightConfigDAO;
	
	@ModelAttribute("highlight")
	public String highlight() {
		String highlight = "";
		List<HighlightConfig> highlightConfig= highlightConfigDAO.getByKey("NOT_NULL");
		if (highlightConfig.size()>0)
		{ 
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfig.get(0).getStyle()+"});";
		}
		return highlight;
	}
	
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required = false) String title,
			@RequestParam(required = false) String link,
			Model model,HttpServletRequest request) {
		
		List<HIpbbBwlist> linkList = ipbbGraphTempGraphDao.getLinkId(null);
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder(
					"ipbbList")
					.encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("ipbbList")
					.encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		List<HIpbbGraphTemplateGraph> ipbbList = ipbbGraphTempGraphDao.getData(title, link, column, order);
		
		model.addAttribute("linkList", linkList);
		model.addAttribute("ipbbList", ipbbList);
		model.addAttribute("title", title);
		model.addAttribute("link", link);
		
		return new ModelAndView("/cauhinh/ipbbGraphList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String id, HttpServletRequest request) {

		ipbbGraphTempGraphDao.deleteByPrimaryKey(Integer.parseInt(id));
		saveMessage(request, "Xóa thành công");

		return "redirect:list.htm";
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model) {

		HIpbbGraphTemplateGraph ipbbGraph = (id==null) ? new HIpbbGraphTemplateGraph() : ipbbGraphTempGraphDao.selectByPrimaryKey(Integer.parseInt(id));
		
		List<HIpbbBwlist> linkList = ipbbGraphTempGraphDao.getLinkId(null);
		
		model.addAttribute("linkList", linkList); 
		model.addAttribute("ipbbGraph", ipbbGraph); 

		return "/cauhinh/ipbbGraphForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("ipbbGraph")@Valid HIpbbGraphTemplateGraph ipbbGraph,BindingResult result, HttpServletRequest request,Model model) {
		
		List<HIpbbBwlist> linkList = ipbbGraphTempGraphDao.getLinkId(null); 
		
/*		try {
			ipbbGraph.setHostId(Integer.parseInt(ipbbGraph.getLink()));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception : "+e);
		}*/
		
		if (!result.hasErrors())
		{
			//Add New
			if(ipbbGraph.getId() == null){ 
				ipbbGraphTempGraphDao.insertSelective(ipbbGraph);
				saveMessage(request, "Thêm mới thành công.");
			}
			//Edit
			else{ 
				ipbbGraphTempGraphDao.updateByPrimaryKeySelective(ipbbGraph);
				saveMessage(request, "Cập nhật thành công.");
			}
			return "redirect:list.htm";
		}
		else
		{ 
			model.addAttribute("linkList", linkList);
			model.addAttribute("ipbbGraph", ipbbGraph);
			
			return "/cauhinh/ipbbGraphForm";
		}
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "/cauhinh/ipbbGraphUpload";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		if (!filePath.isEmpty()) {

			String[] ten =filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
					
					importContent(sheetData, model, request);
					
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		
		return "/cauhinh/ipbbGraphUpload";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {  
		
		List<HIpbbGraphTemplateGraph> successList = new ArrayList<HIpbbGraphTemplateGraph>();
		List<HIpbbGraphTemplateGraph> failedList = new ArrayList<HIpbbGraphTemplateGraph>();
		List<HIpbbGraphTemplateGraph> success = new ArrayList<HIpbbGraphTemplateGraph>();
		
		String localGraphId;
		String title;
		String link;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 3) {
        		saveMessageKey(request, "Số lượng cột dữ liệu không đúng.");
        		
        		return "/cauhinh/ipbbGraphUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		HIpbbGraphTemplateGraph ipbbGraph;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			ipbbGraph = new HIpbbGraphTemplateGraph();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=3; j++) {
     					data.add("");
     				}
        			 
        			localGraphId			= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			title					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			link					= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
	        		
        			//Kiem tra kieu du lieu
        			if((this.isNumber(localGraphId) == false)
    						){
    						error = true;
        			}
	        		//Kiem tra do dai
					if(title == null
							||(title != null && title.length() > 200
							|| link != null && link.length() > 200)
						){
						error = true;
					} 
        			
					// Get id by link name
					List<HIpbbBwlist> linkList = ipbbGraphTempGraphDao.getLinkId(link);
        			
					//Set value --------------------------------------------------------------------------- 
					
					ipbbGraph.setLink(link);
					ipbbGraph.setTitle(title);
					
					if(linkList.size() < 1){
						ipbbGraph.setHostId(Integer.parseInt("0"));
					}else{
						ipbbGraph.setHostId(linkList.get(0).getId());
					} 
					
					//-------------------------------------------------
					
					if (error) {
						ipbbGraph.setStrLocalGraphId(localGraphId); 
						
						failedList.add(ipbbGraph);
					}else{ 
						if(localGraphId == null){
							ipbbGraph.setLocalGraphId(Integer.parseInt("0"));
						}else{
							ipbbGraph.setLocalGraphId(Integer.parseInt(localGraphId));
						}
						
						successList.add(ipbbGraph);
					} 
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (HIpbbGraphTemplateGraph record: successList) {
			try { 
				ipbbGraphTempGraphDao.insertSelective(record); 
				success.add(record);
			} catch (Exception ex) {
					failedList.add(record);
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu Bsc không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép, định dạng dữ liệu không chính xác.");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "/cauhinh/ipbbGraphUpload";
	} 
	
	@RequestMapping("exportExcel")
  	public ModelAndView report(
  			   @RequestParam(required = false) String title,
			   @RequestParam(required = false) String link,   
			   HttpServletRequest request, HttpServletResponse response) throws Exception { 
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile); 
		
		List<HIpbbGraphTemplateGraph> ipbbList = ipbbGraphTempGraphDao.getData(title, link, null, 0);
		
		exportReport(tempFile, ipbbList);
		
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "IpbbGraphList_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<HIpbbGraphTemplateGraph> ipbbList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Ipbb Graph Template Graph", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Local Graph Id", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Title", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Link", cellFormatHeader);
			sheet.addCell(label2);
			i = 1;
			
			for (HIpbbGraphTemplateGraph record : ipbbList) {
				int j=0;
				Label record0 = new Label(j, i, String.valueOf(record.getLocalGraphId()), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getTitle(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getLink(), cellFormat);
				sheet.addCell(record2); 
				
				i++;
			}
			workbook.write();
			workbook.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private boolean isNumber(String number){
		try {
			Integer _number = Integer.parseInt(number);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}